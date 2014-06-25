package redgame.anim;
/*
 * TiledAnimtion.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.image.*;

import redgame.engine.*;
/**
 * TiledAnimation���ǹ������ʾƽ�̶�������.
 * �������ͼƬ��ʱ, ����ƽ����ʾ
 * @author Ҧ����
 */

public class TiledAnimation extends Animation {
    private int m_frame;
    /**
     * ����TiledAnimtion
     * @param game ��Ϸ�������
     * @param img ͼ��
     * @param w �����
     * @param h �����
     * @param tilew ͼ��һ��Ŀ��
     * @param tileh ͼ��һ��ĸ߶�
     */
    public TiledAnimation(GameWorld game, Image img, int w, 
							int h, int tilew, int tileh){
		super(game, null, w, h);
        int imgw = img.getWidth(m_game.getPanel());
        int imgh = img.getHeight(m_game.getPanel());
      
        m_frame = imgw / tilew;
		BufferedImage bi = new BufferedImage(w, h * m_frame, BufferedImage.TYPE_INT_ARGB);
		Graphics2D big = bi.createGraphics();
        
		for (int f = 0; f < m_frame; f++){
    		big.setClip(0, f * h, w, f * h + h);
    		for (int i = 0; i < w + tilew; i += tilew){
    			for (int j = 0; j< h + tileh; j += tileh){
    			 
    				if (j == 0 || (imgh <= tileh))
                        big.drawImage(img, i, h * f + j, i + tilew, h * f + j + tileh, 
                                    tilew * f , 0, tilew * f + tilew, tileh, m_game.getPanel());
    				else				
                        big.drawImage(img, i, h * f + j, i + tilew, h * f + j + tileh, 
                                    tilew * f , tileh, tilew * f + tilew, 2 * tileh, m_game.getPanel());
    			}
    		}
    	}	
		m_img = bi;
		setClips();
        setRange(0, m_frame - 1, 100);
		if (m_frame > 1){
		    //System.out.println("frame = "+m_frame);
		    start();
		}  
//		m_nx = 1;
//		m_ny = 1;
	}
	
//	public void paint(Graphics g, int x, int y){
//		
//		Graphics2D g2 = (Graphics2D)g; 
//		g2.drawImage(m_img, x, y, m_game.getPanel());
//	}

}
