package redgame.obj;
import java.util.*;
/*
 * Sword.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import redgame.engine.*;
/**
 * Sword����������
 * 
 * @author Ҧ����
 */
public class Sword extends MovableObject {
    public Rectangle rect;
    private Actor m_owner;
    private int m_damage;
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
     
    public Sword(GameWorld game, Actor owner, Image img, int x, int y, int w, int h,
                    int begin, int end, int damage) {
        super(game, img, x, y, w, h);
        m_owner = owner;
        m_anim.setRange(begin, end, m_delay);
        m_anim.setLooped(false);
        m_anim.start();
        m_blocked = false;
        m_damage = damage;
    }
    /**
     * �ƶ�
     */   
    public void move(long passedTime){
        m_anim.update(passedTime);
    }
    
    public void update(long passedTime){

        Vector actors = m_game.getMap().getActors();
        for (int i = 0; i < actors.size(); i ++){
            Actor a = (Actor)actors.get(i);
            if (!a.isdead() && a.checkCollision(rect))
                a.hurt(m_damage, 1000);
        }
    }
    public boolean finished(){
        return m_anim.end == m_anim.currIndex;
    }
    
    public void makeRect(){
        rect = new Rectangle((int)x, (int)y, m_block.width, m_block.height);
    }
    
}
