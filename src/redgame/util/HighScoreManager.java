package redgame.util;
/*
 * HighScoreManager.java ���ߣ�Ҧ����
 */
import java.io.*;
/**
 * HighScoreManager���Ǹ߷ְ������
 * @author Ҧ����
 */

public class HighScoreManager {
    
    private String m_filename;
    //���7��
    final private int MAX = 7;
    /**
     * ÿ�������
     */
    public String[] names = new String[MAX + 1];
    /**
     * ÿ�����
     */
    public int[] scores = new int[MAX + 1];
    /**
     * �߷ְ��й��м���
     */
    public int count;
    //���ļ�����
    private void readData(){
        count = 0;
        String s;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(m_filename));
            while (count < MAX && (s = reader.readLine()) != null){
                names[count] = new String(s);
                scores[count] = Integer.parseInt(reader.readLine());
                count++;
            } 
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }    
    }
    //д�뵽�߷ְ�
    private void writeData(){
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(m_filename));
            
            for (int i = 0; i < count; i++){
                writer.println(names[i]);
                writer.println(scores[i]);
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }    
    }
    /**
     * ����߷ְ�
     * @param filename �߷ְ��ļ���
     */
    public HighScoreManager(String filename) {
        m_filename = filename;
        readData();
    }
    /**
     * �ж�score�Ƿ񹻵��ϸ߷�
     */
    public boolean isHighScore(int score){
        //�����û�������, ��Ȼ����
        if (count < MAX) return true;
        //����ֻ�����һ��
        return scores[count - 1] < score;
    }

    /**
     * ��߷ְ�����뵱ǰ����
     * ������
     * @param name �������
     */
    
    public void addHighScore(int score, String name){
        int i;
        //��һ�����ʵĵط����ȥ
        for (i = count - 1; i >=0; i--){
            if (scores[i] < score) {
                scores[i+1] = scores[i];
                names[i+1] = names[i];
            }else{
                break;
            }    
        }
        if (count < MAX) count ++;
        scores[i+1] = score;
        names[i+1] = name; 
        writeData();
    }    
}
