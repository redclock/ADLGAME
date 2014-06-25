package redgame.status;
/*
 * SayStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.event.*;
import redgame.engine.*;
import redgame.obj.*;
import redgame.util.*;
/**
 * SayStatus������ʾ�߷ְ����Ϸ״̬.
 * @see AbstractStatus
 * @author Ҧ����
 */
public class SayStatus extends AbstractStatus {
    private Image m_bk_img;
    private int m_imgw, m_imgh;
    final private int MAXW = 500, MAXH = 500;

    private int stop = 5, sleft = 5, sbottom = 5, sright = 5;
    private int sstop = 5, ssleft = 5, ssbottom = 5, ssright = 5;
    private int m_x, m_y;

    public static Color color1 = Color.WHITE;
    public static Color color2 = new Color(100, 100, 100); 
    
    public static Color DefaultColor1 = Color.WHITE; 
    public static Color DefaultColor2 = new Color(128, 128, 255); 
    /**
     * ����һ��SayStatus
     * @param game  ��Ϸ����
     * @param owner ˵������
     * @param text  Ҫ˵�Ļ�
     */
    public SayStatus(GameWorld game, AbstractObject owner, String text) {
        super(game);
        Image emotion = m_game.loadImage("image/emotion.png");

        Font font = new Font("����", Font.PLAIN, 16);
        m_bk_img = new BufferedImage(MAXW + sleft + sright + ssleft + ssright,
                                     MAXH + stop + sbottom + sstop + ssbottom,
                                     BufferedImage.TYPE_INT_ARGB);
        TextRenderer tr = new TextRenderer();

        Graphics2D g2d = (Graphics2D)m_bk_img.getGraphics();

        g2d.setFont(font);                 
        Dimension d = tr.drawText(sleft + ssleft, stop + sstop, g2d, text, emotion);

        m_bk_img = new BufferedImage(d.width + sleft + sright + ssleft + ssright,
                                     d.height + stop + sbottom + sstop + ssbottom,
                                     BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D)m_bk_img.getGraphics();
        
        m_imgw = m_bk_img.getWidth(m_game.getPanel());
        m_imgh = m_bk_img.getHeight(m_game.getPanel());
        int[] px;
        int[] py;
        Rectangle sc = new Rectangle(m_game.getScreenArea());    
        //����λ��
        if (owner.getY() - d.height > 10){
            //top
            m_y = (int)owner.getY() - m_imgh;
            m_x = (int)owner.getX() + owner.getW()/2 - m_imgw / 2;
            if (sc.x < 0 ) sc.x = 0;
            if ( m_x < sc.x ) m_x = sc.x;
            if ( m_x + m_imgw > sc.x + sc.width) m_x = sc.x + sc.width - m_imgw;
            
            int sx = owner.getW()/2 +  (int)owner.getX() - m_x;
            px = new int[]{ sx - 2, sx, sx + 2 };
            py = new int[]{ m_imgh - sbottom, m_imgh, m_imgh - sbottom };
        }else{
            //bottom
            m_y = (int)owner.getY() + (int)owner.getH();
            m_x = (int)owner.getX() + (int)owner.getW()/2 - m_imgw / 2;
            if (sc.x < 0 ) sc.x = 0;
            if ( m_x < sc.x ) m_x = sc.x;
            if ( m_x + m_imgw > sc.x + sc.width) m_x = sc.x + sc.width - m_imgw;
            int sx = owner.getW()/2 +  (int)owner.getX() - m_x;
            px = new int[]{ sx - 2, sx, sx + 2 };
            py = new int[]{ stop+1, 0, stop+1 };
        }
         
        Paint p = new GradientPaint(0, 0, color1, m_imgw, 0, color2);
        g2d.setPaint(p);
        g2d.fillRoundRect(  sleft, stop, 
                            d.width + ssleft + ssright, 
                            d.height + sstop + ssbottom, 
                            10, 10
                         );
        g2d.setColor(Color.BLACK);                 
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawRoundRect(  sleft, stop, 
                            d.width + ssleft + ssright, 
                            d.height + sstop + ssbottom, 
                            10, 10
                         );
        
        g2d.setPaint(p);
        g2d.fillPolygon(px, py, 3);                 
        g2d.setColor(Color.BLACK);                 
        g2d.drawLine(px[0], py[0], px[1], py[1]);
        g2d.drawLine(px[1], py[1], px[2], py[2]);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.setFont(font);                 
        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        tr.drawText(sleft + ssleft, stop + sstop, g2d, text, emotion);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.dispose();    
    }

    /**
     * �����˳�
     */
     public int update(long passedTime){
        if ( KeyManager.isKeyJustDown(KeyEvent.VK_SPACE)
        	||KeyManager.isKeyJustDown(KeyEvent.VK_ENTER)
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
        if (m_counter > 300)
            g.drawImage(m_bk_img, m_x - sleft, m_y - stop, m_game.getPanel());
        else{
            Graphics2D g2d = (Graphics2D)g;
            Composite oldac = g2d.getComposite();
            AlphaComposite ac = AlphaComposite.getInstance(
                           AlphaComposite.SRC_OVER, (float)m_counter / 300);
            g2d.setComposite(ac);
            g.drawImage(m_bk_img, m_x - sleft, m_y - stop, m_game.getPanel());
            
            g2d.setComposite(oldac);
        }    
        return  0;
    }
}