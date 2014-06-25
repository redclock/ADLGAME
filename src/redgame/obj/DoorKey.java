package redgame.obj;
import java.awt.*;
import redgame.engine.*;

public class DoorKey extends StaticObject {
    /**
     * Կ�׵�����
     */
    public int id;
    /**
     * ����DoorKey
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w Ԫ�ؿ��
     * @param h Ԫ�ظ߶�
     * @param begin ͼ��ʼ������
     * @param end ͼ�����������
     */

    public DoorKey(GameWorld game, Image img, 
                    int x, int y, int w, int h, int begin, int end, int id
                    ) {
        super(game, img, x, y, w, h, begin, end);
        this.id = id;
		Player p = game.getPlayer();
		if (p != null){
			//����Ѿ��õ���,������
			if (p.hasKey[id])
			{
				m_dead = true;
				m_visible = false;
			}
		}
        
    }    
}
