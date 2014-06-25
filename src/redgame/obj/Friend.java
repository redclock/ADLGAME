package redgame.obj;
/*
 * Friend.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import redgame.engine.*;

/**
 * Friend���Ƿǵ��˽�ɫ
 * 
 * @author Ҧ����
 */

public class Friend extends NPC{
    /**
     * ����Friend
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w ͼ��һ��Ŀ��
     * @param h ͼ��һ��ĸ߶�
     * @see Actor
     */
    
    public Friend(GameWorld game, Image img, int x, int y, int w, int h) {
        super(game, img, x, y, w, h);
        setFace(G_DOWN);
        HP = 10000000;
    }
    /**
     * ����collision
     * @see NPC#collision
     */
    
    public boolean collision(AbstractObject obj, int direction){
        boolean b = super.collision(obj, direction);
        return b;
    } 
    
}
