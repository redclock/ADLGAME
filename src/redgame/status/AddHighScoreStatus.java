package redgame.status;
/*
 * AddHighScoreStatus.java ���ߣ�Ҧ����
 */
import redgame.engine.*;

import java.awt.*;
import javax.swing.*;

//��ʾ�Ի��������һ���߳���.(��ʵ���Ϻ��񲢲�������?)
class DialogThread extends Thread{
    private boolean m_done = false;
    public String result;
    private String m_text;
    DialogThread(String text){
        m_text = text;
    }
    public synchronized void run(){
        result = JOptionPane.showInputDialog(null, m_text);
        m_done = true;
    }
    public boolean isDone(){
        return m_done;
    }
}

/**
 * AddHighScoreStatus������Ӹ߷ְ����Ϸ״̬.
 * ����ʱ����ʾһ���Ի���,Ҫ�������������
 * @see AbstractStatus
 * @author Ҧ����
 */

public class AddHighScoreStatus extends AbstractStatus {
    //�Ի����߳�
    private DialogThread m_dlg;
    //Ҫ��ӵĸ߷�
    private int m_score;

    /**
     * ����һ��AddHighScoreStatus
     * @param game ��Ϸ����
     * @param score �÷�
     */

    public AddHighScoreStatus(GameWorld game, int score) {
        super(game);
        m_score = score;
        m_dlg = new DialogThread("�����"+score+"��,���Խ���߷ְ�.\n�����´���:");
        m_dlg.start();
    }
    /**
     * ����״̬, ���������ֹͣ
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */

    public int update(long passedTime){
        if (m_dlg.isDone()){
            if (m_dlg.result != null && !m_dlg.result.trim().equals("")){
            //    m_game.getHighScore().addHighScore(m_score, m_dlg.result);
            }
            return -1;
        }
        return 0;
    }
    /**
     * ��ͼ����:ʲôҲ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    
    public  int draw(long passedTime, Graphics g){
        return 0;
    }
}