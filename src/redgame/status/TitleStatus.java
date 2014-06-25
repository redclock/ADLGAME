package redgame.status;
/*
 * TitleStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.event.*;
import redgame.engine.*;
import redgame.anim.*;
import redgame.util.*;

//��������ϵͳ
class FireParticleSystem extends AbstractParticleSystem{
    private int m_y;
    private int m_x;
    private boolean isfirst = true;
    FireParticleSystem(GameWorld game, Image image, 
                    int count, int pw, int ph,
                    int begin, int end, int delay){
        super(game, image, count, pw, ph, begin, end, delay);
        resetall();
        isfirst = false;
    }
    protected void reset(int i){
        Particle p = m_p.get(i);
        p.x = m_game.getRandom(500)/50+m_x;
        p.y = m_game.getRandom(500)/50+m_y;
        //m_vx = 1.0f;
        //m_x += m_vx;
        //m_y += m_vy;
        //m_vy += 0.005;
        p.vx = (float)m_game.getRandom(100)/100-0.5f;
        p.vy = (float)m_game.getRandom(100)/100-1.0f;
        p.alpha = 1.0f;
        if (!isfirst)
            p.life = m_game.getRandom(1500);
        else
            p.life = 0;    
        p.accx = 0;
        p.accy = -(float)m_game.getRandom(100)/300;
        p.alpha = 0.5f;
        p.alphasp = 0.01f;
        p.start();
    }
    
    public void setPosition(int x, int y){
        m_x = x;
        m_y = y;
    }
}

/**
 * TitleStatus������Ϸ����ʾ���⼰���˵�����Ϸ״̬.
 * ����ʱ����ʾһ���˵�,�����ѡ��
 * @see AbstractStatus
 * @author Ҧ����
 */
public class TitleStatus extends AbstractStatus{
    //���Ż���
    private FireParticleSystem m_fps1;
    private FireParticleSystem m_fps2;

    //��ǰѡ��
    private int m_curIndex;
    //ѡ���б�
//    private Image m_menu_img;
    //����
//    private Image m_title_img;
    //����ͼ
    private Image m_bk_img;
    
    
    //ǰ������
//    private Image m_bk_ren;
    
    private int FX1 = 290;
    private int FX2 = 500;
  
    private int FY = 190;
    
    private int FDY = 60;  
    /**
     * ����һ��TitleStatus
     * @param game ��Ϸ����
     */
    public TitleStatus(GameWorld game) {
        super(game);
        m_fps1 = new FireParticleSystem(game, game.loadImage("image/fire1.png"),
                  50, 32, 64, 0, 3, 100);
        m_fps2 = new FireParticleSystem(game, game.loadImage("image/fire1.png"),
                  50, 32, 64, 0, 3, 100);
        m_fps1.setPosition(FX1, FY); 
        m_fps2.setPosition(FX2, FY); 
        m_curIndex = 0;  
//        m_menu_img = game.loadImage("image/menu1.png");   
//        m_title_img = game.loadImage("image/title.png"); 
        m_bk_img =  game.loadImage("image/cover_back.jpg"); 
//        m_bk_ren =  game.loadImage("image/cover_ren.png");
        game.deleteImage(m_bk_img); 
//        game.deleteImage(m_bk_ren); 
        m_game.playMusic("music/open.mid", true);            
    }

    /**
     * ����ǰһ��״̬
     */
    public void setPrior(AbstractStatus prior){
        m_prior = null;
    }
    
    /**
     * ����״̬, �������¼�ѡ��,�ո��ȷ��
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */
    
    public int update(long passedTime){
        if ( KeyManager.isKeyJustDown(KeyEvent.VK_SPACE)
            ||KeyManager.isKeyJustDown(KeyEvent.VK_ENTER)){
            switch(m_curIndex){
                case 0: m_game.reset(); break;      //"��ʼ��Ϸ"
                case 1: m_game.pushStatus(new LoadGameStatus(m_game)); break;      
                case 2: System.exit(0); break;      //"�˳�"
            }
           
        }else if ( KeyManager.isKeyJustDown(KeyEvent.VK_UP)){
            if ( m_curIndex <= 0 ) m_curIndex = 2;
            else m_curIndex --;
            m_fps1.setPosition(FX1, m_curIndex * FDY + FY); 
            m_fps2.setPosition(FX2, m_curIndex * FDY + FY); 
        }else if ( KeyManager.isKeyJustDown(KeyEvent.VK_DOWN)){
            if ( m_curIndex >= 2 ) m_curIndex = 0;
            else m_curIndex ++;
            m_fps1.setPosition(FX1, m_curIndex * FDY + FY); 
            m_fps2.setPosition(FX2, m_curIndex * FDY + FY); 
        }
        return 0;
    }
    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    
    public int draw(long passedTime, Graphics g){
        Graphics2D g2 = (Graphics2D)g; 
//        g2.setPaint( new GradientPaint(0, 0, Color.BLACK, 0, 240, new Color(0, 0, 100)));
//        g2.fillRect(0,0,640,240);
//        g2.setPaint( new GradientPaint(0, 480, Color.BLACK, 0, 240, new Color(0, 0, 100)));
//        g2.fillRect(0,240,640,480);
//        g2.setPaint(null);
//        int c = (int) m_counter / 15 % 640;
//        if (c == 0) {
//            g2.drawImage(m_bk_img, 0, 0, m_game.getPanel());
//        }else{
//            g2.drawImage(m_bk_img, 0, 0, 640 - c, 480, c, 0, 640, 480, m_game.getPanel());
//            g2.drawImage(m_bk_img, 640 - c, 0, 640, 480, 0, 0, c, 480, m_game.getPanel());
//        };
//        g2.setColor(Color.BLACK);
//        g2.fillRect(0, 0, 640, 40);
//        g2.fillRect(0, 420, 640, 60);
       // g2.drawImage(m_title_img, 0, 0, m_game.getPanel());
        //g2.drawImage(m_menu_img, 330, 150, m_game.getPanel());
        g2.drawImage(m_bk_img, 0, 0, m_game.getPanel());
        m_fps1.paint(g2);
        m_fps2.paint(g2);
        return 0;
    }
    /**
     * ���¼�ʱ��
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */

    public int move(long passedTime) {
        m_fps1.move(passedTime);
        m_fps2.move(passedTime);
        m_counter += passedTime;
        return 0;
    }
}