package redgame.status;
/*
 * AnimStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;
import redgame.engine.*;
import redgame.anim.*;
public class AnimStatus extends AbstractStatus{
    FileAnimation m_anim;
    AbstractParticleSystem m_p;

    /**
     * ����һ��AnimStatus
     * @param game ��Ϸ����
     * @param x    λ��
     * @param y    λ��
     */
    public AnimStatus(GameWorld game, int x, int y, String name, AbstractParticleSystem p){
        super(game);
        m_p = p;
        m_anim = new FileAnimation(game, x, y, name);
    }
    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    public int draw(long passedTime, Graphics g){
        m_anim.paint(g);
        if (m_p != null) m_p.paint(g);
        return 0;
    }

    
    public int update(long passedTime){
        if (m_p != null) m_p.move(passedTime);
        m_anim.update(passedTime);
        if (m_anim.isStop())
            return 1;
        else    
            return 0;
    }


}
