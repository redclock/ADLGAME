package redgame.status;
/*
 * HighScoreStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.event.*;
import redgame.engine.*;
import redgame.util.*;

/**
 * HighScoreStatus������ʾ�߷ְ����Ϸ״̬.
 * @see AbstractStatus
 * @author Ҧ����
 */
public class HighScoreStatus extends AbstractStatus {
    private Image m_bk_img;
    private HighScoreManager m_highScore;
    /**
     * ����һ��AddHighScoreStatus
     * @param game ��Ϸ����
     */
    public HighScoreStatus(GameWorld game) {
        super(game);
        m_bk_img = m_game.loadImage("image/score.png");
        m_game.playMusic("music/score.mid", true);
    }

    /**
     * ����ǰ��״̬:��ֹ����º���ʾ
     */
    public void updatePrior(long passedTime, Graphics g){
    }
    /**
     * �����˳�
     */
     public int update(long passedTime){
        if ( KeyManager.isKeyJustDown(KeyEvent.VK_SPACE)
            ||KeyManager.isKeyJustDown(KeyEvent.VK_ESCAPE)){
            m_game.backToTitle();
        }
        m_counter += passedTime;
        return 0;
    }
    /**
     * ʲôҲ����
     */
    public  int move(long passedTime){
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
        g.drawImage(m_bk_img, x, y, m_game.getPanel());
        Font f = g.getFont();
        Font bigf = new Font("����", 0, 14);
        g.setColor(Color.green);
        g.setFont(bigf);
        for (int i = 0; i < m_highScore.count; i++){
            g.drawString(m_highScore.names[i], 140, 175+30*i);
            g.drawString(m_highScore.scores[i]+"", 470, 175+30*i);
        }
        g.setFont(f);
        return 0;
    }
}