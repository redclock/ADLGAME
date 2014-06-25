package redgame.engine;
/*
 * ImageManager.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * ImageManager����ͼ�������
 * ������ͼ���ļ�, �����Է�ֹ�ظ�����
 * @author Ҧ����
 */

public class ImageManager{
    private Vector<ImageItem> m_images = new Vector<ImageItem>();
	private GameWorld m_game;
	
	private Image find(String filename){
		for(int i = 0; i < m_images.size(); i++)
			if (m_images.get(i).equalName(filename)){
				return m_images.get(i).getImage();
			}
		return null;	
	}
	/**
	 * ���ڴ���ɾ��ͼ��
	 */
	public void delete(Image img){
        for(int i = 0; i < m_images.size(); i++)
            if (m_images.get(i).getImage() == img){
                m_images.remove(i);
                return;
            }
	}
	
	public ImageManager(GameWorld game){
		m_game = game;
	}
	
	/**
	 * װ��ͼ��, ����Ѿ�װ�ع�, ֻ�����ô���
     * ʹ��Toolkit.getImage()
	 * @param filename �ļ���
	 * @return ������ͼ��
	 */
	public Image loadImage(String filename){
		Image img = find(filename);
		//����Ѿ�װ�ع�
		if (img != null) return img;
		img = m_game.getIO().loadImage(filename);
		MediaTracker tracker= new MediaTracker(m_game.getPanel());
		tracker.addImage(img, 0);
		try {
			tracker.waitForID(0);
		}
		catch (InterruptedException e){
			e.printStackTrace();
			return null;
		}
		m_images.add(new ImageItem(filename, img));
		return img;
	}

    /**
     * װ��ͼ��, ����Ѿ�װ�ع�, ֻ�����ô���
     * ����BufferedImag
     * ʹ��ImageIO
     * @param filename �ļ���
     * @return ������ͼ��
     */
    public BufferedImage loadBufferedImage(String filename){
        Image img = find(filename);
        //����Ѿ�װ�ع�
        if (img != null && img instanceof BufferedImage) 
            return (BufferedImage)img;
        try{
            
            BufferedInputStream stream = 
                    new BufferedInputStream(m_game.getIO().getInput(filename));
            BufferedImage bi = ImageIO.read(stream);
            stream.close();
            m_images.add(new ImageItem(filename, bi));
            return bi;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * װ��ͼ��,�����Ż��ļ���ͼ��
     * 
     * ����BufferedImag
     * ʹ��ImageIO
     * @param filename �ļ���
     * @param transparency ֵΪ
     *        Transparency.OPAQUE, Transparency.BITMASK, Transparency.TRANSLUCENT
     * @param size ����Ϊnull,��ǿ������Ϊsize
     * @return ������ͼ��
     */
    public Image loadAutomaticImage(
        String filename, 
        int transparency, 
        Dimension size){
            BufferedImage bi = loadBufferedImage(filename);
            if (bi == null) return null;
            GraphicsConfiguration gc = m_game.getPanel().getGraphicsConfiguration();
            if (gc == null) return bi;
            int w, h;
            if ( size == null ){
                w = bi.getWidth();
                h = bi.getHeight();
            }else{
                w = size.width;
                h = size.height;
            }
            BufferedImage abi = gc.createCompatibleImage(w, h, transparency);
            Graphics g = abi.getGraphics();
            if ( size == null ){
                g.drawImage(bi, 0, 0, m_game.getPanel());
            }else{
                g.drawImage(bi, 0, 0, w, h, m_game.getPanel());
            }
            g.dispose();
            bi.flush();
            return abi;
    }

    public Image createImage(ImageProducer ip){
        Image img = Toolkit.getDefaultToolkit().createImage(ip);
        
        MediaTracker tracker= new MediaTracker(m_game.getPanel());
        tracker.addImage(img, 0);
        try {
            tracker.waitForID(0);
        }
        catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }
        return img;
    }
   
    //ͼ���б�ĵ�λ
	private class ImageItem{
		//ͼ��
		private Image m_image;
		//�ļ���
		private String m_name;
		public ImageItem(String name, Image img){
			m_image = img;
			m_name = name;
		}
		public Image getImage(){
			return m_image;
		}
		public boolean equalName(String s){
			return m_name.equals(s);
		}
	}
}