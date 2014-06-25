package redgame.status;
/*
 * ShowMenuStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;

import redgame.engine.*;
import redgame.ui.*;
/**
 * ShowMenuStatus������Ϸ����ʾ�˵�����Ϸ״̬.
 * ����ʱ����ʾһ���˵�,�����ѡ��
 * @see AbstractStatus
 * @author Ҧ����
 */
public class ShowMenuStatus extends AbstractStatus{
    //�˵�
    private AbstractMenu m_menu;
    //�˵�λ��
    private int mx = 200, my = 200;
    /**
     * ����һ��ShowMenuStatus
     * @param game ��Ϸ����
     */
    public ShowMenuStatus(GameWorld game, int x, int y, AbstractMenu menu) {
        super(game);
        m_menu = menu;
        mx = x;
        my = y;
    }
    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    public int draw(long passedTime, Graphics g){
        m_menu.paint((Graphics2D)g, mx, my);
        return 0;
    }

    /**
     * ����״̬, �������¼�ѡ��,�ո��ȷ��
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */
    
    public int update(long passedTime){
        m_menu.update(passedTime);
        if (m_menu.done()) return 1;
        return 0;
    }
    /**
     * 
     */
    public int move(long passedTime){
        m_menu.move(passedTime);

        return 0;
    }
}
