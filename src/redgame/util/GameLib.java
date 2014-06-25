package redgame.util;
import java.awt.*;
public class GameLib {
    private static GraphicsDevice gd;
    private static Frame m_win;
    private static Robot m_robot;
    
    private static void createRobot() {
    	try{
	    	if (gd == null) {
	    		m_robot = new Robot();
	    	}else {
	    		m_robot = new Robot(gd);
	    	}
    	}catch (AWTException ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static GraphicsDevice getGraphicsDevice() {
    	return gd;
    }
    /**
     * ��ʼ��
     * @param win ��Ϸ����
     */
    public static void Init(Frame win){
        if (win == null) return;
        GraphicsConfiguration gc = win.getGraphicsConfiguration();
        gd = gc.getDevice();
        m_win = win;
    }
    /**
     * �л�������ȫ��Ļ
     */
    public static void setFullScreen(boolean full){
        if (gd == null) return;
        System.out.println("Set Full Screen: " + full);
        if (full){
//            m_win.setVisible(false);
//            m_win.setdvalidate();
//            m_win.setUndecorated(true);
//            m_win.setVisible(true);
            gd.setFullScreenWindow( m_win );
            m_win.validate();
            m_win.repaint();
        }else{
//            m_win.setVisible(false);
//            m_win.setUndecorated(false);
//            m_win.setVisible(true);
            gd.setFullScreenWindow( null );
            m_win.validate();
            m_win.repaint();
        }
    }
    /**
     * ���ص�ǰ�Ƿ���ȫ��Ļ
     */
    public static boolean isFullScreen(Window win){
        if (gd == null) return false;
        return gd.getFullScreenWindow() == win;
    }
    /**
     * ȷ����ʾģʽ�Ƿ�֧��
     */
    public static boolean displayModeSupported(int width, int height, int bitDepth){
        if (gd == null) return false;
        DisplayMode[] modes = gd.getDisplayModes();
        for (int i = 0; i < modes.length; i++){
            if (width == modes[i].getWidth() &&
                height == modes[i].getHeight() &&
                bitDepth == modes[i].getBitDepth()){
                    return true;
                }
        }
        return false;        
    } 
    /**
     * ������ʾģʽ
     */
    public static boolean setMode(int width, int height, int bitDepth){
        if (gd == null) return false;
        gd.setDisplayMode(new DisplayMode(width, height, bitDepth, 0));
        return true;
    }

    public static DisplayMode getDisplayMode() {
        if (gd == null) return null;
        return gd.getDisplayMode();
    }    

    public static void waitTime(long time){
        long ot = System.currentTimeMillis();
        long t;
        do{
            t = System.currentTimeMillis();
            try{
            	Thread.sleep(10);
            }catch(Exception ex){
            	ex.printStackTrace();
            }
        }while ( t < time + ot );
    }
    
    public static void setMousePos(int x, int y) {
    	if (m_robot == null) createRobot();
    	if (m_robot != null) m_robot.mouseMove(x, y); 
    }
}
