package redgame.status;
/*
 * CompleteAllStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import redgame.engine.*;
import redgame.util.*;
/**
 * CompleteAllStatus����ͨ�ص���Ϸ״̬.
 * 
 * @see AbstractStatus
 * @author Ҧ����
 */

public class CompleteAllStatus extends AbstractStatus {
    //"congratulations"ͼ��
    private Image m_con_img;
    //ͼ���С
    private int m_imgw, m_imgh;
    /**
     * ����һ��CompleteAllStatus
     * @param game ��Ϸ����
     */
    public CompleteAllStatus(GameWorld game) {
        super(game);
        m_con_img = m_game.loadImage("image/congratulations.png");
        m_imgw = m_con_img.getWidth(m_game.getPanel());
        m_imgh = m_con_img.getHeight(m_game.getPanel());
        m_game.playSound("sound/passall.mid");
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
            m_game.startLogo();
        }
        m_counter += passedTime;
        return 0;
    }
    /**
     * ʲôҲ����
     */
    public int move(long passedTime){
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
        g.setColor(Color.BLACK);
        g.fillRect(x, y, w, h);
        Font f = g.getFont();
        int size = (int)(m_counter/30);
        if (size > 100) size = 100;
        int iw = m_imgw * size / 100;
        int ih = m_imgh * size / 100;
        g.drawImage(m_con_img, (w-iw)/2+x, y + 100, 
                                (w-iw)/2+x+iw, y+100+ih,
                                0, 0, 
                                m_imgw, m_imgh,
                                m_game.getPanel());
                                 
        if (size == 100){
            
            Font bigf = new Font("����", Font.BOLD, 32);
            Rectangle2D rt = bigf.getStringBounds("���Ѿ��ɹ�ͨ��", ((Graphics2D)g).getFontRenderContext());
            g.setColor(Color.YELLOW);
            g.setFont(bigf);
            //����Ļ���Ļ�
            g.drawString("���Ѿ��ɹ�ͨ��", x+(int)( w - rt.getWidth())/2, y+250);
        }        
        g.setFont(f);
        return 0;
    }
}