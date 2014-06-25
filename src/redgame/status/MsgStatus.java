package redgame.status;
/*
 * MsgStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.event.*;
import redgame.engine.*;
import redgame.util.*;

/**
 * MsgStatus������ʾ��Ϣ����Ϸ״̬.
 * @see AbstractStatus
 * @author Ҧ����
 */

public class MsgStatus extends AbstractStatus{
	private String m_text;
	private int m_x, m_y;
    /**
     * ����һ��MsgStatus
     * @param game ��Ϸ����
     * @param x
     * @param y ��ʾ��λ��
     * @param text ��Ϣ����
     */
    public MsgStatus(GameWorld game, int x, int y, String text) {
		super(game);
		m_text = text;
		m_x = x;
		m_y = y;
	}	
	/**
     * �����˳�
	 */
	public int update(long passedTime){
		if ( KeyManager.isKeyJustDown(KeyEvent.VK_SPACE)
			||KeyManager.isKeyJustDown(KeyEvent.VK_ESCAPE)){
			return -1;
		}
		return 0;
	}
    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */

	public int draw(long passedTime, Graphics g){
		Font f = g.getFont();
		Font msgf = new Font("����", 0, 18);
		g.setFont(msgf);
		g.setColor(Color.BLACK);
		g.drawString(m_text, m_x + 1, m_y + 1);
		g.setColor(Color.WHITE);
		g.drawString(m_text, m_x, m_y);
		g.setFont(f);
		return 0;
	}
}
