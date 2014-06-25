package redgame.obj;
/*
 * StaticObject.java ���ߣ�Ҧ����
 */
import java.awt.*;
import redgame.engine.*;
/**
 * StaticObject���Ǿ�ֹ����.
 * ��Ĭ��Ϊ����
 * @author Ҧ����
 */
public class StaticObject extends AbstractObject {
    /*
     * �Ƿ�����
     */
    protected boolean   m_dead;
    /*
     * ��ʱ�����ļ�ʱ��
     */
    protected long   m_deadCounter;
    /**
     * ����StaticObject
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w Ԫ�ؿ��
     * @param h Ԫ�ظ߶�
     * @param begin ͼ��ʼ������
     * @param end ͼ�����������
     */

	public StaticObject(GameWorld game, Image img, 
					int x, int y, int w, int h, int begin, int end
	 				) {
		super(game, img, x, y, w, h);
		m_anim.setRange(begin, end, m_delay);
		m_anim.start();
		m_blocked = false;
	}

	/**
	 * ���¶���
	 */
	public void move(long passedTime){
		m_anim.update(passedTime);
	}
    /**
     * �Ƿ�����
     */
    public boolean isdead(){
        return m_dead;
    }
    public void die(){
        m_dead = true;
        m_deadCounter = 0;
    } 

    public void paint(Graphics g){
        if ( !m_visible ) return;
        if ( !m_dead ){
            super.paint(g);
        }else{
            int yy = (int)( m_deadCounter * 50 / 1000 );
            m_anim.paint_alpha(g, (int)x - m_block.x, (int)y - m_block.y - yy, 
                    1.0f - (float)m_deadCounter / 1000.0f);
            m_deadCounter += m_game.passedTime;
            if (m_deadCounter >= 900) m_visible = false;
        }
    }

}
