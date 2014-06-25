package redgame.obj;
/*
 * Patrol.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import redgame.engine.*;
/**
 * Patrol���ǿ���Ѳ�ߵĵ���
 * 
 * @author Ҧ����
 */
public class Patrol extends Enemy {
	//Ѳ�ߵķ�Χ
	private int m_min, m_max;
	private boolean m_follow = false;
    private boolean m_canSword = false;
    private boolean m_canJump = false;
    private boolean m_still = false;
    
    /**
     * ����Patrol
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w ͼ��һ��Ŀ��
     * @param h ͼ��һ��ĸ߶�
     * @param pmin Ѳ�߷�Χ����Сֵ 
     * @param pmax Ѳ�߷�Χ�����ֵ 
     * @see Actor
     */
     
    public Patrol(GameWorld game, Image img, 
				int x, int y, int w, int h, 
				int pmin, int pmax) {
		super(game, img, x, y, w, h);
		m_min = pmax > pmin ? pmin:pmax;
		m_max = pmax < pmin ? pmin:pmax;
	}
	/**
	 * �����Ƿ�ֹ����
	 */
	public void setStill(boolean still){
	   m_still = still;
	}
	/**
	 * ȡ���Ƿ�ֹ����
	 */
    public boolean getStill(){
       return m_still;
    }
    /**
	 * �����Ƿ��ܸ���
	 */
	public void setFollow(boolean follow){
	   m_follow = follow;
	}
    /**
     * ȡ���Ƿ��ܸ���
     */
    public boolean getFollow(){
       return m_follow;
    }
    /**
     * �����Ƿ��ܻӽ�
     */
    public void setCanSword(boolean sword){
       m_canSword = sword;
    }
    /**
     * ȡ���Ƿ��ܻӽ�
     */
    public boolean getCanSword(){
       return m_canSword;
    }
    /**
     * �����Ƿ�����Ծ
     */
    public void setCanJump(boolean jump){
       m_canJump = jump;
    }
    /**
     * ȡ���Ƿ�����Ծ
     */
    public boolean getCanJump(){
       return m_canJump;
    }
    /**
	 * ��������ǰ��ʱ, �ͻ�ͷ
	 */
	public void cannotGo(){
		m_face = 3 - m_face;
		updateAnimation();
	}
	/**
	 * ����״̬
	 */
	public void update(long passedTime){
        if (m_dead) return;
        super.update(passedTime);
        if (new_face == -1) new_face = m_face;
        if ( m_follow ) { 
            Player p = m_game.getPlayer();
            if (!m_lull && p != null && p.getVisible()){
                if ( 
                    getY() < p.getY() + p.getH() &&
                    getY() + getH() > p.getY() &&
                    p.getX() + p.getW() > m_min && p.getX() < m_max){
                        new_face = (p.getX() < getX())? G_LEFT : G_RIGHT; 
                    }
            }
        }
        if (getX() <= m_min){
            new_face = G_RIGHT;
        }else if (getX()+getW() >= m_max){
            new_face = G_LEFT;
        }
        Player p = m_game.getPlayer();
        if (m_canSword && !m_lull && p != null && p.getVisible() &&
            ! m_isSwording && 
            getY() < p.getY() + p.getH() &&
            getY() + getH() > p.getY()){
            
            int d = (int)(p.getX() - getX() - getW ());
            if (d > 0 && d < 15){
                new_face = G_RIGHT;
                m_face = G_RIGHT;
                updateAnimation();
                doSword(300, false);
                
            }else{
                d = (int)(getX() - p.getX() - p.getW ());
                if (d > 0 && d < 15){
                    new_face = G_LEFT;
                    m_face = G_LEFT;
                    updateAnimation();
                    doSword(300, false);
                }
            }        
        }
        if (m_still) new_face = -1;
	}	
}
