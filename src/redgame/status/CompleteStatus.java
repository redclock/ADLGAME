package redgame.status;
/*
 * CompleteStatus.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import java.awt.event.*;
import redgame.engine.*;
import redgame.util.*;

/**
 * CompleteStatus���ǹ���һ�ص���Ϸ״̬.
 * 
 * @see AbstractStatus
 * @author Ҧ����
 */
public class CompleteStatus extends AbstractStatus {
    private Image m_img;
    private int m_imgw, m_imgh;
    private int score;
    private String msgs;
    public CompleteStatus(GameWorld game) {
        super(game);
        m_img =  m_game.loadImage("image/cat.png");
        m_imgw = m_img.getWidth(m_game.getPanel());
        m_imgh = m_img.getHeight(m_game.getPanel());
        m_game.playSound("sound/enter.wav");
        m_game.playMusic("sound/win2.mid", false);


        msgs = "��һ������: "+ m_game.getMap().getCounter()/1000+" �룬";
        msgs += "�㱾�صĵ÷�Ϊ��"+score;

    }
    /**
     * �����˳�
     */
    
    public int update(long passedTime){
        if ( KeyManager.isKeyJustDown(KeyEvent.VK_SPACE)
            ||KeyManager.isKeyJustDown(KeyEvent.VK_ESCAPE)){
            m_game.goNextLevel();
        }
        return 0;
    }
    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    public int draw(long passedTime, Graphics g){
        Rectangle sc = m_game.getScreenArea();
        int x = sc.x;
        int y = sc.y;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        int w = sc.width;
        int h = sc.height;
        g.drawImage(m_img, x+(w - m_imgw)/2, y+(h - m_imgh)/2, m_game.getPanel());
        Font f = g.getFont();
        Font bigf = new Font("����", 0, 14);
        g.setFont(bigf);
        g.setColor(Color.BLACK);
        g.drawString(msgs, x+(w - m_imgw)/2+1, y+50+1);
        g.setColor(Color.WHITE);
        g.drawString(msgs, x+(w - m_imgw)/2, y+50);
        g.setFont(f);
        return 0;
    }
}