package redgame.obj;
/*
 * Enmey.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import redgame.engine.*;

/**
 * Enemy���ǵ��˽�ɫ
 * 
 * @author Ҧ����
 */

public class Enemy extends NPC{
    private boolean m_harmful = true;
    private Bonus m_bonus;

    /**
     * ����Enemy
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w ͼ��һ��Ŀ��
     * @param h ͼ��һ��ĸ߶�
     * @see Actor
     */

    public Enemy(GameWorld game, Image img, int x, int y, int w, int h) {
        super(game, img, x, y, w, h);
    }
    /**
     * ����collision
     * @see NPC#collision
     */

    public boolean collision(AbstractObject obj, int direction){
        if (obj instanceof Bounce){
            if (m_harmable){
                //if (!m_lull){
                    if (!m_dead) startLull(5000);
                // }else{
                //    stopLull();
                // }     
            }
            m_game.getMap().removeBullet(obj);
            return true;
        }else {
            return super.collision(obj, direction);
        }
    } 
    
    public void die() {
        super.die();
        if (m_bonus != null) {
            m_bonus.setPosition (getX(), getY());
            m_game.getMap().addStatic(m_bonus);
        }        
    }
    
    public boolean getHarmful(){
        return m_harmful;
    }

    public void setHarmful(boolean h){
        m_harmful = h;
    }
    
    public Bonus getBonus() {
        return m_bonus;
    }
    
    public void setBonus(Bonus b) {
        m_bonus = b;
    }
}
