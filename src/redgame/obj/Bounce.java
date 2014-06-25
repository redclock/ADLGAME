package redgame.obj;
/*
 * Bounce.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import redgame.engine.*;
/**
 * Bounce���ǿ��Ե��������壬������ǽ�ڿ��Ե���
 * 
 * @author Ҧ����
 */
public class Bounce extends MovableObject {
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
    
    public Bounce(GameWorld game, Image img, int x, int y, int w, int h) {
        super(game, img, x, y, w, h);
        m_blocked = true;
    }
    /**
     * �ƶ�
     */   
    public void move(long passedTime){
        m_anim.update(passedTime);
        moveDown(passedTime);
    }
    /**
     * ���ó��ٶ�
     */   
    public void setSpeed(float vx, float vy){
        m_vx = vx;
        m_vy = vy;
    }
    
    /**
     * ��������������
     */
    public void moveDown(long passedTime){
        float dy;
        float dx;
        boolean b = false;
        if (m_vy < 0){
            dy = m_game.getMap().gotoUp(this, -passedTime*m_vy);
            y -= dy;       
            if (dy < - passedTime*m_vy){
                m_vy = - m_vy*0.99f;
                b = true;
            }   
        }else{
            dy = m_game.getMap().gotoDown(this, passedTime*m_vy);
            y += dy;
            if (dy < passedTime*m_vy){
                m_vy = - m_vy*0.99f;
                b = true;
            }    
        }
        if (m_vx < 0){
            dx = m_game.getMap().gotoLeft(this, -passedTime*m_vx);
            x -= dx;
            if (dx < -passedTime*m_vx){
                m_vx = - m_vx*0.99f;
            }       
        }else{
            dx = m_game.getMap().gotoRight(this, passedTime*m_vx);
            x += dx;
            if (dx < passedTime*m_vx){
                m_vx = - m_vx*0.99f;
             }    
        }
        if (!b) m_vy += 0.001*passedTime;
    }   
}
