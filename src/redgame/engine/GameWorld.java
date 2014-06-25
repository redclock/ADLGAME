package redgame.engine;
/*
 * GameWorld.java ���ߣ�Ҧ����
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;

import redgame.obj.*;
import redgame.util.*;

import redgame.status.*;
/**
 * GameWorld������Ϸ����Ĺ����࣬�ṩ�����ӹ���֮�����ϵ��ͬʱ��Ҳ����Ⱦ���̡߳�
 * @author Ҧ����
 */

public class GameWorld implements Runnable{
    //��Ϸ�߳�
    private Thread  gameThread;            
    //�߳�ֹͣ��־
    private boolean isStopped = true;
    //�Ƿ���ͣ 
    private boolean isPaused = false;     
    //ͼ�񻺴�
    private BufferedImage backBuffer;  
    //��ǰʱ��      
    private long currTime = 0; 
    //���ʱ��         
    public long passedTime = 0;
    //���ʱ��
    private long m_playTime;
    //ͼ����������      
    private JPanel m_panel = null;
    //IO�ӿڵ�����      
    private MyIO m_io = null;
    //��Ϸ��ͼ����Ĵ�С
    private Dimension m_size;
    //��Ϸ��Ļ�ڵ�ͼ�ϵ�����
    private Rectangle m_screenArea;
    //״̬ջ
    private StatusStack m_sstack = new StatusStack(this);
    //ͼ�������
    private ImageManager m_imgMgr = new ImageManager(this);
    //��ǰ��ͼ
    private GameMap m_map;
    //��ͼ�б�
    private String[] m_maps;
    //�ű�������
    private ScriptManager m_scripts = new ScriptManager(this);
    //��ǰ�غ�
    private int m_currLevel;
    //�����,ʹ��ʱ��������
    private Random m_rand = new Random(new Date().getTime());
    //�Ƿ���Ҫ������Ļ
    private boolean m_needRefresh;
    //�Ƿ���Ҫ������Ļ
    private GameMouse m_mouse;
    
    private Graphics m_bufGraph;

    private Player m_player;

    private Player createPlayer(){
        GameConfig cfg = new GameConfig();
        cfg.load("player.txt", m_io);
        int x = cfg.getInt("x", 0);
        int y = cfg.getInt("y", 0);
        int w = cfg.getInt("w", 48);
        int h = cfg.getInt("h", 64);
        Image img = loadImage(cfg.getString("image", ""));    
        Player r = new Player(this, img, x, y, w, h);
        int stop    = cfg.getInt("skip.top", 0);
        int sbottom = cfg.getInt("skip.bottom", 0);
        int sleft   = cfg.getInt("skip.left", 0);
        int sright  = cfg.getInt("skip.right", 0);
        
        r.setBlockRect(new Rectangle(sleft, stop, w - sleft - sright, 
                                             h - sbottom - stop));
        return r;                                             
    }

    //��һ֡
    private void drawFrame(){
        Graphics g = m_panel.getGraphics();
        //��ʱgΪ��
        if (g!=null && m_size.width > 0 && m_size.height > 0)
        {
            if (m_needRefresh){
                m_panel.repaint();
                m_needRefresh = false;
            }    
            if (backBuffer == null ||
                backBuffer.getWidth(m_panel) != m_size.width ||
                backBuffer.getHeight(m_panel) != m_size.height){
                backBuffer = (BufferedImage)m_panel.createImage(m_size.width, m_size.height);
            }
            if (backBuffer != null) {
                calcScreenArea();
                Graphics g2 = backBuffer.getGraphics();
                m_bufGraph = g2;
                //���ú�ɫ����Ļ
                g2.setColor(Color.BLACK);
//              g2.fillRect(0, 0, m_size.width, m_size.height);
                //�����״̬
                m_sstack.updateStatus(passedTime, g2);
                //дFPS
                String FPS;
                try{
                    FPS = "FPS:"+Float.toString(1000/(float)passedTime); 
                }catch(ArithmeticException e){
                    FPS = "0";
                }
                int x = m_screenArea.x;
                if (x < 0) x = 0;
                int y = m_screenArea.y;
                if (y < 0) y = 0;
                y += m_screenArea.height - 15;
                g2.setFont(new Font("Default", 0, 10));
                g2.setColor(Color.BLACK);
                g2.drawString(FPS, x, y);
                g2.setColor(Color.WHITE);
                g2.drawString(FPS, x, y - 1);
                m_mouse.update(passedTime);
                //m_mouse.paint(g2);
                m_bufGraph = null;
                g2.dispose();
                //�����滭����Ļ��
//                g.drawImage(backBuffer, 0, 0, 
//                            m_screenArea.width,
//                            m_screenArea.height,
//                            m_screenArea.x, 
//                            m_screenArea.y, 
//                            m_screenArea.x+m_screenArea.width,
//                            m_screenArea.y+m_screenArea.height,
//                            null);
                g.drawImage(backBuffer, 
                            -m_screenArea.x, 
                            -m_screenArea.y, 
                            m_panel);
                
            }
        }else{
//          if (m_size.width == 0)
//              System.out.println("size is 0");
//          else
//              System.out.println("panel is null");
        }
    }
    
    /**
     * @param   panel   Ҫ�����ﻭͼ���á�
     * @see     AppFrame
     */
    public GameWorld(JPanel panel, MyIO MyIO){
            m_panel = panel;
            m_io = MyIO;
            m_maps = MapFileReader.readMapList("map/maplist.txt", this);
            m_mouse = new ImageMouse(this, loadImage("image/Ocarina.png"), 10, 10);
            m_panel.addMouseListener(m_mouse);
            m_panel.addMouseMotionListener(m_mouse);
    }

    public void gotoMap(String filename, int x, int y){
        SwitchStatus s = new SwitchStatus(this, new StageStatus(this, filename, x, y), 1);
        s.needRepaint = true;
        m_sstack.push(s);
    }
    /**
     * װ�ص�ͼ
     * @see     GameMap 
     */
    public void loadMap(String filename){
        currTime = System.currentTimeMillis();
        backBuffer = null;
        MapFileReader mfr = new MapFileReader();
        m_map = mfr.readMap(this, filename);
        m_player.setVisible(true);
        m_map.addPlayer(m_player);
        m_size = new Dimension(m_map.getWidth(), m_map.getHeight());
        //����ͼ������ʾ����
//        m_frame.leftpane.txtName.setText(m_map.getName());
        //��ʼ��
        //������״̬ɾ��
        m_sstack.deleteUntil(2);
        KeyManager.clearKeyState();
        calcScreenArea();
        //m_sstack.push(new MapIntroStatus(this));
    }
    /**
     * ���¿�ʼ��ǰ��ͼ
     * @see     GameMap 
     */
    public void reset(){
        gotoMap(m_maps[m_currLevel], (int)m_player.getX(), (int)m_player.getY());
        //String s = GameConfig.defaultConfig.getString("StartScript", "");
        //if (!s.equals("")){
        //    m_scripts.add(s, null);
        //}
    }

    /**
     * ������һ�أ������ǰΪ���һ�أ��ͽ����һ��
     */
    public void goNextLevel(){
        m_currLevel++;              //�غŵ���
        //�����������,�ӵ�һ�ؿ�ʼ
        if (m_currLevel >= m_maps.length){
            pushStatus(new SwitchStatus(this, new CompleteAllStatus(this), 1));
        }else{
            gotoMap(m_maps[m_currLevel], (int)m_player.getX(), (int)m_player.getY());
        }
    }

   /**
     * ������Ϸ�߳�
     * @see     #stop
     */
    public void start(){
        isStopped = false;
        KeyManager.clearKeyState();
        gameThread = new Thread(this);
        gameThread.start();
    }
    /**
     * ֹͣ��Ϸ�߳�
     * @see     #start
     */
    public void stop(){
        isStopped = true;
        gameThread.interrupt();
        gameThread = null;
        stopMusic();
    }
    /**
     * ��ͣ��Ϸ�߳�
     * @see     #start
     */
    public void pause(){
        isPaused = true;
    }
    /**
     * �ظ���Ϸ�߳�
     * @see     #start
     */
    public void resume(){
        isPaused = false;
        currTime = System.currentTimeMillis();
    }
    
    /**
     * ʹ��Ϸ״̬�ص����� 
     */
     
    public void backToTitle(){
        m_size = new Dimension(640, 480);
        m_sstack.clear();
        m_scripts.clear();
        KeyManager.clearKeyState();
        m_currLevel = 0;
        pushStatus(new TitleStatus(this));
        m_needRefresh = true;
        m_player = createPlayer();
    }
    
    public void startLogo(){
        m_size = new Dimension(640, 480);
        m_sstack.clear();
        KeyManager.clearKeyState();
        m_currLevel = 0;
        KeyManager.clearKeyState();
        pushStatus(new SwitchStatus(this, new LogoStatus(this), 1));
        m_needRefresh = true;
        m_player = createPlayer();
    }
    /**
     * ������Ϸ�߳�
     * @see     #start
     */ 
    public void run(){
        long framerate = 10;
        m_screenArea = new Rectangle();
        //backToTitle();
        startLogo();
        System.out.println("Game begin");
        passedTime = 0;
        //��Ϸѭ��
        while( !isStopped ){
//            if (KeyManager.isKeyDown(KeyEvent.VK_ALT) 
//                        && KeyManager.isKeyJustDown(KeyEvent.VK_F)) {
//                m_io.toggleFullScreen();
//            }
//            if (KeyManager.isKeyDown(KeyEvent.VK_ALT) 
//                        && KeyManager.isKeyJustDown(KeyEvent.VK_D)) {
//                GameLib.setFullScreen(false);
//            }
            currTime = System.currentTimeMillis(); 
            if (!isPaused){
            	drawFrame();
                m_playTime += passedTime;
            }
            try{
                if (passedTime < framerate){
                    Thread.sleep(framerate - passedTime);
                }else{
                    Thread.sleep(5);
                }
            } catch( InterruptedException ex ) {
                break;
            }
            passedTime = System.currentTimeMillis() - currTime;
            //��ֹ��������
            if (passedTime > 100) passedTime = 100;
            
        }
        m_scripts.clear();
        System.out.println("Game end");
    }

    /**
     * ȡ�õ�ǰ��ͼ��С
     */
    public Dimension getSize(){
        return m_size;
    }
    
    /**
     * ȡ����Ļ����
     */
    public Rectangle getScreenArea(){
        return m_screenArea;
    }
    
    /*
     * ��PANEL��С����ͼ��С,����λ�ü������Ļ����
     */    
    private void calcScreenArea(){
        Dimension psize = m_panel.getSize();
        if (psize.width >= m_size.width){ 
            m_screenArea.x = -(psize.width - m_size.width)/2;
            m_screenArea.width = m_size.width;
        }else {
            m_screenArea.width = psize.width;
            if ( m_map ==null ||m_player == null ){
                m_screenArea.x = 0;
            }else{
                int px = (int)(m_player.getX()+m_player.getW()/2);
                if (px <= psize.width/2)
                    m_screenArea.x = 0;
                else if (m_size.width - px <= psize.width/2)
                    m_screenArea.x = m_size.width - psize.width;
                else
                    m_screenArea.x = px - psize.width/2;
           }
        }            
        if (psize.height >= m_size.height){ 
            m_screenArea.y = -(psize.height - m_size.height)/2;
            m_screenArea.height = m_size.height;
        }else {
            m_screenArea.height = psize.height;
            if ( m_map ==null || m_player == null ){
                m_screenArea.y = 0;
            }else{
                int py = (int)(m_player.getY()+m_player.getH()/2);
                if (py <= psize.height/2)
                    m_screenArea.y = 0;
                else if (m_size.height - py <= psize.height/2)
                    m_screenArea.y = m_size.height - psize.height;
                else
                    m_screenArea.y = py - psize.height/2;
           }
        }            
    }    
    /**
     * ȡ�����
     */
    public JPanel getPanel(){
        return m_panel;
    }
    /**
     * ȡ�õ�ǰ��ͼ
     */
    public GameMap getMap(){
        return m_map;
    }
    /**
     * ȡ����������
     */
    public BufferedImage getBuffer(){
        return backBuffer;
    }
    /**
     * ȡ�ýű�������
     */
    public ScriptManager getScript(){
        return m_scripts;
    }
    /**
     * ȡ��ͼƬ������
     */
    public ImageManager getImageManager(){
        return m_imgMgr;
    }
    /**
     * ȡ�����������Χ��0~n-1
     */
    public int getRandom(int n){
        return m_rand.nextInt(n);
    }
    /**
     * ȡ����ҽ�ɫ
     */
    public Player getPlayer(){
        return m_player;
    }
    /**
     * ����������
     */
    public void setPlayer(Player player){
        m_player = player;
    }
    /**
     * ������Ļ
     */
    public synchronized void refreshScreen(){
       //��m_needRefresh��Ϊtrue,��һ֡ʱ������Ļ
       m_needRefresh = true; 
    }    
    /**
     * ��״̬ջ��ѹ��һ��״̬
     * @param s Ҫѹ���״̬ʾ��������
     * @see #popStatus
     * @see StatusStack#push
     */
    public void pushStatus(AbstractStatus s){
        m_sstack.push(s);
    }

    /**
     * ��״̬ջ�е���һ��״̬
     * @return ״̬ջ��������״̬�����״̬ջΪ�գ�����null
     * @see #pushStatus
     * @see StatusStack#pop
     */
    public AbstractStatus popStatus(){
        return m_sstack.pop();
    }
    /**
     * ����ָ�������ļ�����ֹͣ��ǰ���֡���ʽΪau��aiff��wav
     * @param filename Ҫ���ŵ������ļ�
     * @param looped �Ƿ�ѭ��
     * @see #stopMusic
     * @see #playSound
     * @see MusicPlayer#play
     */
    
    public void playMusic(String filename, boolean looped){
        if ( GameConfig.defaultConfig.getBoolean("Music", true) ){
            m_io.playMusic(filename, looped);
        }
    }
    /**
     * ����ָ�������ļ�������ֹͣ��ǰ���֡���ʽΪau��aiff��wav
     * @param filename Ҫ���ŵ������ļ�
     * @see #playMusic
     * @see MusicPlayer#play
     */
    public void playSound(String filename){
        if ( GameConfig.defaultConfig.getBoolean("Sound", true) ){
            m_io.playSound(filename);
        }
    }
    /**
     * ֹͣ��ǰ����
     * @see #playMusic
     */
    public void stopMusic(){
        m_io.stopMusic();
    }
    /**
     * װ��ָ��ͼ���ļ�����ʽΪpng��jpg��gif��bmp
     * @param filename Ҫװ�ص�ͼ���ļ�
     * @return װ�غ��ͼ��
     * @see ImageManager#loadImage
     */
    public Image loadImage(String filename){
        return m_imgMgr.loadImage(filename);
    }
    /**
     * ���ڴ���ɾ��ͼ��
     */
    public void deleteImage(Image img){
        m_imgMgr.delete(img);
    }
    public MyIO getIO(){
        return m_io;
    }
	/**
	 * @param playTime The playTime to set.
	 */
	public void setPlayTime(long playTime) {
		this.m_playTime = playTime;
	}
	/**
	 * @return Returns the playTime.
	 */
	public long getPlayTime() {
		return m_playTime;
	}

	/**
	 * @param mouse �������.
	 */
	public void setMouse(GameMouse mouse) {
		m_mouse = mouse;
	}

	/**
	 * @return ȡ�����.
	 */
	public GameMouse getMouse() {
		return m_mouse;
	}   
    /**
     * ȡ��ͼ�λ���
     */
    public Graphics getBufferGraphics() {
        return m_bufGraph;
    }
}
