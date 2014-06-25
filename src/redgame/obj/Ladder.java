package redgame.obj;
/*
 * Ladder.java ���ߣ�Ҧ����
 */
import java.awt.*;
import redgame.engine.*;
/**
 * Ladder���ǵ�ͼԪ�����ӣ�����������������ײ
 * @author Ҧ����
 */

public class Ladder extends MapObject {
    /**
     * ��������
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
     * @see MapObject#MapObject
     */
    public Ladder(GameWorld game, Image img, 
					int x, int y, int w, int h,
	 				int tilew, int tileh) {
		super(game, img, x, y, w, h, tilew, tileh);
		m_blocked = false;
	}
    /**
     * ������
     */
	public boolean isClimbable(){
		return true;
	}
}
	