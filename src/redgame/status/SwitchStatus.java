/////////////////////////////////////////
//SwitchStatus extends AbstractStatus
//��"��ǰ״̬"�л���"����״̬"��״̬
////////////////////////////////////////
package redgame.status;
/*
 * SwitchStatus.java ���ߣ�Ҧ����
 */

import java.awt.*;
import redgame.engine.*;
/**
 * SwitchStatus���Ǵ�"��ǰ״̬"�л���"����״̬"��״̬.
 * ������ʹ����״̬�Ĺ��ɸ�ƽ��.��������������:
 * 
 * Ҫ��״̬A���ɵ�B,��ʱ״̬ջΪ:<br>
 * ..., A <br>
 * ��SΪSwitchStatus,��ʼ����ʱ,���������׶�: <br>
 * ..., A, S <br>
 * ����������, ��S��A����, ��ѹ��B��S, ���뽥��׶�: <br>
 * ..., B, S <br>
 * ���: <br>
 * ..., B <br>
 * 
 * @see AbstractStatus
 * @author Ҧ����
 */
public class SwitchStatus extends AbstractStatus {
	private AbstractStatus m_next;   //�л�֮���״̬
	private boolean isBack = false;	 //�Ƿ��Ѿ����һ�룬�����˻ص���һ״̬
	public long MaxCounter;        //������̽���ʱ��ĺ���
	private int m_index;			//�л���ʽ 0 - ֱ��
									//1-���뵭�� 2-����
    public boolean needRepaint = false;
    /**
     * ����һ��SwitchStatus
     * @param game ��Ϸ����
     * @param nextStatus ��һ��״̬
     * @param index �л���ʽ: 0 - ֱ�� 1-���뵭�� 2-����
     */
	public SwitchStatus(GameWorld game, AbstractStatus nextStatus, int index) {
		super(game);
		m_next = nextStatus;
		m_index = index;
		MaxCounter = 0;
                                
        switch(m_index){
			case 1: 
			    MaxCounter = 1000; 
			    break;
			case 2: MaxCounter = 2000; break;
		}
	}
    /**
     * ����״̬, ʲôҲ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */
    
	public int update(long passedTime){
	
		return 0;
	}

	//���л���ʽ:���뵭��
	private void draw_fade(long passedTime, Graphics g){
        Rectangle sc = m_game.getScreenArea();
        int x = sc.x;
        int y = sc.y;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        int w = sc.width;
		int h = sc.height;
		float t = (float) m_counter / (float) MaxCounter;
		int alpha = (int)(255 * t);
		if (alpha < 0 ) alpha = 0;
		else if(alpha > 255) alpha = 255; 
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(x, y, w, h);
	}
	
    //���л���ʽ:����
	private void draw_rect(long passedTime, Graphics g){
        Rectangle sc = m_game.getScreenArea();
        int x = sc.x;
        int y = sc.y;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        int w = sc.width;
        int h = sc.height;
		float t = (float) m_counter / (float) MaxCounter;
		g.setColor(Color.BLACK);
		g.fillRect(x, y, (int)(t*w/2), h);
		g.fillRect(x, y, w, (int)(t*h/2));
		g.fillRect(x+w - (int)(t*w/2), y, (int)(t*w/2), h);
		g.fillRect(x, y+h - (int)(t*h/2), w, (int)(t*h/2));
	}

    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    public int draw(long passedTime, Graphics g){
		switch(m_index){
			case 1: draw_fade(passedTime, g); break;
			case 2: draw_rect(passedTime, g); break;
		}
		return 0;
	}

    /**
     * ���¼�ʱ��
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */

    public int move(long passedTime) {
        if (isBack){
			m_counter -= passedTime;
			if (m_counter <= 0) return -1;
		}else{
			m_counter += passedTime;
			if (m_counter >= MaxCounter){
                try{
                    m_game.getPanel().repaint();
                }catch(Exception e){
                    e.printStackTrace();
                }
                isBack = true;
				m_game.popStatus();
				m_game.pushStatus(m_next);
				m_game.pushStatus(this);
				m_next.start();
			}
		}
		return 0;
	}	
}