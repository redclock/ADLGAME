package redgame.obj;
/*
 * AbstractObject.java ���ߣ�Ҧ����
 *
 */
 import java.awt.*;
 import redgame.engine.*;
 import redgame.anim.*;
 import redgame.status.*;
 
/**
 * AbstractObject������Ϸ����������ĸ��࣬����������ܡ�
 * ����һ�������࣬Ҫʹ�����������ࡣ
 * @author Ҧ����
 */

abstract public class AbstractObject{
    /**
     * ������
     */	
	final static public int G_DOWN	= 0;
    /**
     * ������
     */ 
    final static public int G_LEFT  = 1;
    /**
     * ������
     */ 
    final static public int G_RIGHT = 2;
    /**
     * ������
     */ 
    final static public int G_UP    = 3;

    /**
     * ͼƬx����
     */ 
    protected float x;
    /**
     * ͼƬy����
     */ 
    protected float y;
    /**
     * ͼƬһ֡���
     */ 
    protected int w;
    /**
     * ͼƬһ֡�߶�
     */ 
    protected int h;
    /**
     * ��Ϸ������
     */ 
    protected GameWorld m_game;
    /**
     * ������
     */ 
    protected Animation m_anim;
	/*
	 * ��ײ����
	 */
	protected Rectangle m_block;
    /*
     * ͼƬÿһ֡���ӳ�
     */
    protected int       m_delay; 
    /*
     * �Ƿ�ɼ�
     */
    protected boolean   m_visible;
    
    /*
     * �Ƿ����ײ
     */
    protected boolean   m_blocked = true; 
    /**
     *  ����
     */
    protected String m_name; 
    /**
     *  ��Ӧ�Ľű�
     */
    protected String m_script;
      
    /**
     * ��������
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w ͼ��һ��Ŀ��
     * @param h ͼ��һ��ĸ߶�
     * @see Animation
     * @see GameWorld
     */

	public AbstractObject(GameWorld game, Image img, int x, int y, int w, int h){
		m_game = game;
		this.x = x;  this.y = y;
		this.w = w;  this.h = h;
		m_block = new Rectangle(0, 0, w, h);
		m_delay = 100;
		m_visible = true;
		if (img != null)
			m_anim = new Animation(game, img, w, h);
	}
	/**
	 * �õ�Xλ��
	 */
	public float getX(){
		return x;
	} 
    /**
     * �õ�Yλ��
     */
    public float getY(){
		return y;
	} 
    /**
     * �õ���ײ���
     */
    public int getW(){
		return m_block.width;
	}
	/**
	 * �õ�ͼ����
	 */ 
	public int getImgW(){
	   return m_anim.getWidth();
	} 
    /**
     * �õ�ͼ��߶�
     */ 
    public int getImgH(){
       return m_anim.getHeight();
    } 
    /**
     * �õ���ײ�߶�
     */
    public int getH(){
		return m_block.height;
	} 
    /**
     * ����λ��
     */
    public void setPosition(float ax, float ay){
		x = ax; y = ay;
	} 
    /**
     * ���ô�С
     */
    public void setSize(int aw, int ah){
		w = aw; y = ah;
	}
    /**
     * ����Ƿ�������������ײ
     * @param obj Ҫ������һ������
     */
    
	public boolean checkCollision(AbstractObject obj){
		return (obj.getX() + obj.getW() > getX() && 
		        obj.getX() < getX() + getW() )&&
		       (obj.getY() + obj.getH() > getY() && 
		        obj.getY() < getY() + getH());
	}
    /**
     * ����Ƿ��������ײ
     * @param rect Ҫ���ľ���
     */
    
    public boolean checkCollision(Rectangle rect){
        return (rect.x + rect.width > getX() && 
                rect.x < getX() + getW() )&&
               (rect.y + rect.height > getY() && 
                rect.y < getY() + getH());
    }
    /**
	 * �Ƿ�����Ļ��
	 *
	 */
    public boolean inScreen(){
        Rectangle sa = m_game.getScreenArea();
        return (
                (sa.width >= m_game.getSize().width || 
                  ( x - m_block.x + m_anim.getWidth() >= sa.x && 
                    x - m_block.x <= sa.x + sa.width
                  )
                )&&
                (sa.height >= m_game.getSize().height ||
                  ( y - m_block.y + m_anim.getHeight() >= sa.y && 
                    y - m_block.y <= sa.y + sa.height
                  )
                )
               );
    }

    /**
     * ֪ͨĳ������ײ���Լ�������������Ը������������������ײ����
     * @param obj ײ���Ķ���
     * @param direction ���� 
     * @return �Ƿ��Ѵ���
     */
    public boolean collision(AbstractObject obj, int direction){
		//System.out.println(this.getClass()+" meets "+obj.getClass()
		//					+" dir = "+direction);
		return false;
	} 
    /**
     * ������ײ����������ͼ�����ܽ�����ײ������һ�����֡�
     * @param block �µ���ײ����
     */
    
	public void setBlockRect(Rectangle block){
		m_block = block;
	}
	
    /**
     * �����塣���ͼ������򻭳���ǰ֡������һ������
     * @param g Ҫ�����ĵط�
     */
    public void paint(Graphics g){
        //������ɼ��Ͳ���
		if (!m_visible || !inScreen()) return;
		//�������Ϊ��,�򵥻�һ������
		if (m_anim == null){
			g.setColor(new Color(100,100,100,100));
            g.fillRect((int)x - m_block.x,(int)y  - m_block.y, m_block.width, m_block.height);
		}else{
		  //���򻭶���ͼ��
            m_anim.paint(g, (int)x - m_block.x, (int)y - m_block.y);
        }
	}
    /**
     * �����Ƿ�ɼ�
     */
    public boolean getVisible(){
		return m_visible;
	}
    /**
     * �����Ƿ�ɼ�
     */
    public void setVisible(boolean visible){
		m_visible = visible;
	}

    /**
     * �����Ƿ�������ײ
     */
    public void setBlocked(boolean b){
        m_blocked = b;
    }
    /**
     * �Ƿ�������ײ
     */
    public boolean isBlocked(){
	   return m_blocked && m_visible;
	};
	/**
	 * ˵��
	 */
	public void say(String text){
        m_game.pushStatus(new SayStatus(m_game, this, text));
	}
	/**
	 * �õ�����
	 */
	public String getName(){
	   return m_name;
	}
	/**
	 * ��������
	 */
	public void setName(String name){
	   m_name = name;
	}  
    /**
     * �õ��ű�
     */
    public String getScript(){
       return m_script;
    }
    /**
     * ���ýű�
     */
    public void setScript(String script){
       m_script = script;
    }
    /**
     * ������ʱ
     */
    public void setDelay(int delay){
        m_delay = delay;
        setAnimDelay(delay);
    }  
    /**
     * ���ö���ÿ֡��ʱ
     */
    public void setAnimDelay(int delay){
        m_anim.setDelay(delay);
    }  
    
    public Animation getAnim() {
        return m_anim;
    }
}