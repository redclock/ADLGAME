package redgame.util;
/*
 * KeyManager.java ���ߣ�Ҧ����
 */

import java.awt.event.*;

/**
 * KeyManager����ͼ�������
 * ����������Ϣ, ����Դ�Ǵ���ļ����¼�
 * @author Ҧ����
 */

public class KeyManager implements KeyListener{
    private static KeyManager m_default = new KeyManager();
    
    public static KeyManager getDefault() {
    	return m_default;
    }
    
	public void keyPressed(KeyEvent evt){
       setKeyState(evt.getKeyCode(), true);
    }
    public void keyReleased(KeyEvent evt){
        setKeyState(evt.getKeyCode(), false);
    }
    public void keyTyped(KeyEvent evt){
        addText(evt.getKeyChar());
    }

    //��һ�ε���Ϣ
	private static boolean m_lastKeys[] = new boolean[256];
    //��һ�ε���Ϣ
    private static boolean m_currKeys[] = new boolean[256];
	
	/**
	 * ����ĳ������״̬
	 * @param key ��ID
	 * @param down �ǰ��»���̧��
	 */
	public synchronized static void setKeyState(int key, boolean down) {
		if (key < 256){
			m_lastKeys[key] = m_currKeys[key];
			m_currKeys[key] = down;
		}
	}
    /**
     * ������м���״̬
     */
    public synchronized static void clearKeyState() {
		for (int key = 0; key < 256; key ++){
			m_lastKeys[key] = false;
			m_currKeys[key] = false;
		}
	}
	/**
	 * �Ƿ���һ��δ̧�����һ��Ϊ����
	 */
	public synchronized static boolean isKeyJustDown(int key) {
		if (key < 256){
			boolean r = m_lastKeys[key] == false &&
					m_currKeys[key] == true;
			if (r) m_lastKeys[key] = m_currKeys[key];
			return r;
		}
		return false;
	}
    /**
     * �Ƿ���һ��Ϊ����
     */
    public synchronized static boolean isKeyDown(int key) {
		if (key < 256){
			return m_currKeys[key];
		}
		return false;
	}	
    /**
     * �Ƿ���һ�ΰ���δ����һ��Ϊ̧��
     */
    public synchronized static boolean isKeyJustUp(int key) {
		if (key < 256){
			boolean r = m_lastKeys[key] == true &&
					m_currKeys[key] == false;
			if (r) m_lastKeys[key] = m_currKeys[key];
			return r;
		}
		return false;
	}
	
	private static String received = "";
    private static boolean receiving = false;
    /**
	 * ��ʼ���ܼ��������ַ�
	 */
	public static void startReceive(){
	   received = "";
	   receiving = true;
	}
    /**
     * ���ܼ��������ַ�
     */
    public static void addText(char c){
       if ( receiving ) received += c;
    }
    /**
     * �������������ַ�
     */
    public static void endReceive(){
       receiving = false;
    }
    /**
     * ȡ�ü��������ַ�,����ջ���
     */
    public static String getReceived(){
       String r = received;
       received = "";
       return r;
    }
}
