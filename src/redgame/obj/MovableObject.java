package redgame.obj;
/*
 * MovableObject.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import redgame.engine.*;
/**
 * MovableObject���ǿ����ƶ������壬����������
 * ����һ�������࣬Ҫʹ�����������ࡣ
 * @author Ҧ����
 */
abstract public class MovableObject extends AbstractObject {
    /**
     *y����ķ��ٶ�
     */
	protected float m_vy = 0.0f;
    /**
     *x����ķ��ٶ�
     */
    protected float m_vx = 0.0f;
    /**
     * �Ƿ��������
     */
    protected boolean m_gravity = true; 
    /**
     * �������壬����������
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w ͼ��һ��Ŀ��
     * @param h ͼ��һ��ĸ߶�
     * @see Animation
     * @see GameWorld
     */
    
	public MovableObject(GameWorld game, Image img, int x, int y, int w, int h) {
		super(game, img, x, y, w, h);
		m_anim.start();
	}
    /**
     * ��������������
     */
    public void moveDown(long passedTime){
		if (! m_gravity ) return;
		float dy = 0;
		if (m_vy < 0){
			dy = - m_game.getMap().gotoUp(this, -passedTime*m_vy);
			//����������������Ҳ��ǵ��ɣ�����m_vy=0
			if (m_vy < 0 && dy > passedTime*m_vy) m_vy = 0;
		}else{
            if (m_vy == 0) m_game.getMap().gotoDown(this, 0.01f); //����ڵ���,̽��һ��
			else dy = m_game.getMap().gotoDown(this, passedTime*m_vy);
		}
		y += dy;
		if ( passedTime >0 && m_vy > 0 && dy == 0 ){
			m_vy = 0.0f;
		}else{
			m_vy += 0.001*passedTime;
		}
	}
	/*
	 * �õ�X������ٶ�
	 */
	public float getVX(){
	   return m_vx;
	}
    /*
     * �õ�Y������ٶ�
     */
    public float getVY(){
       return m_vy;
    }
    /*
     * ����Y������ٶ�
     */
    public void setVY(float vy){
       m_vy = vy;
    }
    /*
     * ����X������ٶ�
     */
    public void setVX(float vx){
       m_vx = vx;
    }
    public void update(long passedTime){
    }
    public void move(long passedTime){
    }
    public void meetSpring(){
    }
    public boolean getGravity(){
        return m_gravity;
    }
    public void setGravity(boolean g){
        m_gravity = g;
    }
}
