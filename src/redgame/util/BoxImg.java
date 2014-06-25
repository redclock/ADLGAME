package redgame.util;

/**
 * BoxImg.java
 */

import java.awt.*;
import java.awt.image.*;

/**
 * BoxImg �Ի������ı߿�,�����з���Ч��
 * @author Ҧ����
 *
 */
public class BoxImg{
    //����ͼ��
	private BufferedImage img;
    /**
     * ��ʼλ��
     */
	public int bx, by;
    /**
     * ��ǰλ��
     */
	public int x, y;
	/**
	 * Ŀ��λ��
	 */
    public int dx, dy;
    /**
     * ��С
     */
    public int bw, bh;
    private int stop = 5, sleft = 5, sbottom = 5, sright = 5;
    
    //��ʼ��ͼƬ
    private BufferedImage createBox(int w, int h){
        BufferedImage box = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) box.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(50, 50, 50, 150));
        g2d.fillRoundRect(sleft, stop, w - sleft - sright, h - stop - sbottom, 10, 10);
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawRoundRect(sleft, stop, w - sleft - sright, h - stop - sbottom, 10, 10);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.dispose();
        return box;
    } 
    
    /**
     * 
     * @param x λ��
     * @param y λ��
     * @param w ��С
     * @param h ��С
     * @param destx Ŀ��λ��
     * @param desty Ŀ��λ��
     */
    public BoxImg(int x, int y, int w, int h, int destx, int desty){
        bx = x;
        by = y;
        this.x = x;
        this.y = y;
        bw = w;
        bh = h;
        dx = destx;
        dy = desty;
        img = createBox(w, h);

    }
    
    /**
     * ������Ļ��
     * @param g ͼ���豸
     * @param obs ImageObserver 
     */
    public void paint(Graphics g, ImageObserver obs){
        g.drawImage(img, (int)x, (int)y, obs);
    }
    
    /**
     * ���� �ƶ�
     * @param delta ����(0~1 1Ϊֱ�Ӵӳ�ʼ��Ŀ��)
     * @return �Ƿ��ƶ����
     */
    public boolean move(float delta){
        x += (dx - bx) * delta;
        y += (dy - by) * delta;
        boolean finished = true;
        //�����ƶ�
        if (delta > 0){
            if ((dx - bx) * (dx - x) <= 0 ) x = dx;
            else finished = false;
            if ((dy - by) * (dy  - y) <= 0 ) y = dy;
            else finished = false;
        }else {
        	//�����ƶ�
            if ((bx - dx) * (bx - x) <= 0 ) x = bx;
            else finished = false;
            if ((by - dy) * (by - y) <= 0 ) y = by;
            else finished = false;
        }
        return finished;
    }
    
    
} 

