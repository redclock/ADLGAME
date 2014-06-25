package redgame.obj;
/*
 * MapObject.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import redgame.engine.*;
import redgame.anim.*;
/**
 * MapObject���ǵ�ͼԪ�أ�����ͼ����СͼƬƽ�̳ɴ��
 * ����һ�������࣬Ҫʹ�����������ࡣ
 * @author Ҧ����
 */
abstract public class MapObject extends AbstractObject{
    protected boolean m_damagable = false;
    /**
     * �����ͼԪ��
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w Ԫ�ؿ��
     * @param h Ԫ�ظ߶�
     * @param tilew ͼ��һ��Ŀ��
     * @param tileh ͼ��һ��ĸ߶�
     * @see Animation
     * @see GameWorld
     * @see AbstractObject#AbstractObject
     */
    public MapObject(GameWorld game, Image img, 
					int x, int y, int w, int h,
	 				int tilew, int tileh) {
		super(game, null, x, y, w, h);
        //��ʹ��Ĭ�ϵĶ�������ʹ��TiledAnimation
		m_anim = new TiledAnimation(game, img, w, h, tilew, tileh);
	}	
	/**
	 * Ĭ�ϴ�����ײ��ʲôҲ����
	 */
	public boolean collision(AbstractObject obj, int direction){
		
		return false;
	} 
	/**
	 * ��������Ƿ����
	 */
	abstract public boolean isClimbable();
	
	public void move(long passedTime){
	    m_anim.update(passedTime);
	}
    
    public boolean getDamagable(){
        return m_damagable;
    }

    public void setDamagable(boolean d){
        m_damagable = d;
    }
    
    public void destory(){
        m_visible = false;
        m_blocked = false;
    }
}
