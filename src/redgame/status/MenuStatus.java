package redgame.status;
/*
 * MenuStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.event.*;
import redgame.engine.*;
import redgame.util.*;
import redgame.ui.*;

/**
 * MenuStatus������Ϸ����ʾ�˵�����Ϸ״̬.
 * ����ʱ����ʾһ���˵�,�����ѡ��
 * @see AbstractStatus
 * @author Ҧ����
 */
public class MenuStatus extends AbstractStatus{
    //ѡ���б�
    private String[] m_items = 
        {"������Ϸ", 
         "���״̬",
         "ѡ������", 
         "�������", 
         "�ص����˵�"
         };
    
    //�˵���С
    private int mw = 200, mh = 200;
    
    //�˵�����
    private TextMenuEx m_menu;
    /**
     * ����һ��MenuStatus
     * @param game ��Ϸ����
     */
    public MenuStatus(GameWorld game) {
        super(game);
        m_menu = new TextMenuEx(game, "�˵�", m_items, mw, mh);
    }
    
    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    public int draw(long passedTime, Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        Rectangle sc = m_game.getScreenArea();
        int x = sc.x;
        int y = sc.y;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        m_menu.paint(g2d, x + (sc.width - mw)/2, 
                y + (sc.height - mh)/2);
        return 0;
    }

    /**
     * ����״̬, �������¼�ѡ��,�ո��ȷ��
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */
     public int update(long passedTime){
        Rectangle sc = m_game.getScreenArea();
        int x = sc.x;
        int y = sc.y;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (KeyManager.isKeyJustDown(KeyEvent.VK_ESCAPE)) return -1;
        m_menu.update(passedTime);
        if ( m_menu.done() ){
            switch(m_menu.getIndex()){
                case 0: return -1;                   //"������Ϸ"
//                case 1: m_game.reset(); break;       // "���¿�ʼ����"
                case 1: 
                    m_game.popStatus(); 
                    m_game.pushStatus(new ShowStateStatus(m_game));//"���״̬"
                    return 0;
                case 2:  
                    m_game.popStatus(); 
                    m_game.pushStatus(new SelectWeaponStatus(m_game));//"ѡ������"
                    return 0;       
//                case 2: m_game.goNextLevel();break;  //"������һ��"
                case 3:
                    m_game.popStatus(); 
                    m_game.pushStatus(new LoadGameStatus(m_game));//"�������"
                    return 0;                      
                case 4: m_game.backToTitle();break;  //"�ص����˵�"
            }
            
            
        }
        return 0;
    }
    
	public int move(long passedTime) {
		m_menu.move( passedTime );
		return 0;
	}
    
}
