package redgame.anim;
/*
 * Animtion.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.image.*;
import redgame.engine.*;
/**
 * Animation���ǹ������ʾ��������.
 * ����ͼƬ�ֳɼ���С��,ÿһ�����һ֡
 * @author Ҧ����
 */

public class Animation {
	//ÿһ֡��ʱ��
	private int[] m_times;
	//��Ϸ����
	protected GameWorld m_game;
	//ͼ��
	protected Image m_img;
	//ÿ֡�Ĵ�С
	private int m_w, m_h;
	//���ݵ�֡����
	private int m_nx, m_ny;
	//��֡����
	private int m_imgCount;

	private boolean m_loop = true;
	private boolean m_stop = true;
	private Rectangle m_rect = new Rectangle(0,0,0,0);
	private int m_counter = 0;
	private int m_startCounter = 0;
	//ͼ�����,pix[i]Ϊ��i֡�ĵ���
    private int[][] pix;
    //��ʱͼ�����,pix[i]Ϊ��i֡�ĵ���
    private int[][] tmppix;

    //������Χ
    public int start, end;
    public int currIndex = -1;

    protected void setClips(){
		if (m_img == null) return;
		int iw = m_img.getWidth(m_game.getPanel());
		int ih = m_img.getHeight(m_game.getPanel());

		m_nx = (iw + m_w - 1)/ m_w;
//		System.out.println("iw = " + iw +", nx = " + m_nx);
		m_ny = (ih + m_h - 1)/ m_h;
		m_imgCount = m_nx*m_ny;
		m_times = new int[m_imgCount];
		m_rect = getRect(0); 
		pix = new int[m_imgCount][];
        tmppix = new int[m_imgCount][];
    }
	
	private Rectangle getRect(int index){
		return new Rectangle(index % m_nx * m_w, index / m_nx * m_h, m_w, m_h);
	}
    //ȡ��ͼ�����	
	private void grabPixels(int index){
        if (m_img == null) return;
        Rectangle rect = getRect(index);
        pix[index] = new int[rect.width * rect.height];
        tmppix[index] = new int[rect.width * rect.height];
        PixelGrabber pg = new PixelGrabber(m_img, rect.x, rect.y, rect.width, rect.height,
                                            pix[index], 0, rect.width);
        try{
            pg.grabPixels();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
	}
    /**
     * ����Animtion
     * @param game ��Ϸ�������
     * @param img ͼ��
     * @param ptw ͼ��һ��Ŀ��
     * @param pth ͼ��һ��ĸ߶�
     */
    public Animation(GameWorld game, Image img, int ptw, int pth){
		m_img = img;
		m_game = game;
		m_w = ptw;
		m_h = pth;
		currIndex = 0;
		m_loop = true;
		if (m_img != null)	setClips();
	}
	/**
	 * ���ö�����Χ:
	 * @param start ��ʼ������
     * @param end ����������
	 * @param time ÿ֡��ʱ��
	 */
	public void setRange(int start, int end, int time){
		this.start = start;
	    this.end = end;
		for (int i = 0; i < m_imgCount; i++){
			m_times[i] = (i+1)*time;
		}
		m_rect = getRect(currIndex);
		m_startCounter = start *time;
		m_counter = m_startCounter;
		resetToStart();
	}
	/**
	 * ����ͼƬ
	 */
	public void setImage(Image img){
		m_img = img;
		setClips();
	}
	/**
	 * ���¶���
	 */
	public void update(long passedTime){
		if (m_img == null || m_stop || m_imgCount == 0) return;
		if (currIndex > end) currIndex = end;
		else if (currIndex < start) currIndex = start;	
		m_counter += passedTime;
		if (m_counter > m_times[currIndex]){
			currIndex ++;
			if (currIndex > end)
				if (m_loop){
					currIndex = start;
					m_counter =  m_startCounter + m_counter - m_times[end];
				}else{
					currIndex = end;
				}
			m_rect = getRect(currIndex);
		}
	}
	/**
	 * ���ص�������ʼ״̬
	 */
	public void resetToStart(){
		if (currIndex != start){
			currIndex = start;
			m_counter =  m_times[start];
			m_rect = getRect(currIndex);
		}
		
	}

    /**
     * ��(x,y)λ�û�����, ����alpha����͸��, 
     * @param alpha:0.0-1.0
     */
    public void paint_alpha(Graphics g, int x, int y, float alpha){
        Graphics2D g2d = (Graphics2D) g;
        Composite oldac = g2d.getComposite();
        AlphaComposite ac = AlphaComposite.getInstance(
                       AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(ac);
        
        g.drawImage(m_img, x, y, x + m_w, y + m_h, 
            m_rect.x, m_rect.y, m_rect.x+m_w, m_rect.y+m_h,
            m_game.getPanel()
        );
        g2d.setComposite(oldac);
    }

    /**
     * ��(x,y)λ�û�����, ����colorΪ��ɫ��� 
     * 
     */
    public void paint_mask(Graphics g, int x, int y, int color){
        //ץȡ����
        if (pix[currIndex] == null) grabPixels(currIndex);
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 0; i < m_w * m_h; i++) {
            int alpha = cm.getAlpha(pix[currIndex][i]);
            tmppix[currIndex][i] = alpha<<24 | color;
        }
        ImageProducer ip = new MemoryImageSource(m_w, m_h, tmppix[currIndex], 0, m_w);
        Image cropped = m_game.getImageManager().createImage(ip);
        g.drawImage(cropped, x, y, x + m_w, y + m_h, 
            0, 0, m_w, m_h,
            m_game.getPanel());
    }
    /**
     * ��(x,y)λ�û�����, ����alpha����͸��,��colorΪ��ɫ��� 
     * 
     */
    public void paint_alpha_mask(Graphics g, int x, int y, int color, float alpha){
        //ץȡ����
        if (pix[currIndex] == null) grabPixels(currIndex);
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 0; i < m_w * m_h; i++) {
            int a = (int)( cm.getAlpha( pix[ currIndex ][ i ]) * alpha );
            tmppix[currIndex][i] = a << 24 | color;
        }
        ImageProducer ip = new MemoryImageSource(m_w, m_h, tmppix[currIndex], 0, m_w);
        Image cropped = m_game.getImageManager().createImage(ip);
        g.drawImage(cropped, x, y, x + m_w, y + m_h, 
            0, 0, m_w, m_h,
            m_game.getPanel());
    }    
    /**
	 * ��(x,y)λ�û�����
	 */
	public void paint(Graphics g, int x, int y){
		g.drawImage(m_img, x, y, x + m_w, y + m_h, 
			m_rect.x, m_rect.y, m_rect.x+m_w, m_rect.y+m_h,
			m_game.getPanel()
		);
	}
//	public Rectangle getRect(){
//		return m_rect;
//	}
	/**
	 * �����Ƿ�ѭ������
	 */
	public void setLooped(boolean b){
		m_loop = b;
	}
	/**
	 * ��ʼ����
	 */
	public void start(){
		m_stop = false;
	}
    /**
     * ֹͣ����
     */
    public void stop(){
        m_stop = true;
    }
    /**
	 * �����Ƿ�ֹͣ
	 */
	public boolean isStopped(){
		return m_stop;
	}
	
    public int getWidth(){
        return m_w;
    }

    public int getHeight(){
        return m_h;
    }
    
    public void setDelay(int delay){
        setRange(start, end, delay);
    }
}


