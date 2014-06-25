package redgame.scripts;
import redgame.engine.*;
import java.util.*;
/**
 *
 * �ű�
 * 
 */
public abstract class AbstractScript{
    private static Map<String, String> vars = new HashMap<String, String>(); 
    
    public static int getIntVar(String name){
        String s = vars.get(name);
        if (s == null) return 0;
        return Integer.parseInt(s);
    }
    
    public static float getFloatVar(String name){
        String s = vars.get(name);
        if (s == null) return 0;
        return Float.parseFloat(s);
    }
    
    public static String getStrVar(String name){
        String s = vars.get(name);
        return s;
    }
    
    public static void setVar(String name, int var){
        vars.put(name, String.valueOf(var));
    }

    public static void setVar(String name, float var){
        vars.put(name, String.valueOf(var));
    }

    public static void setVar(String name, String var){
        vars.put(name, String.valueOf(var));
    }
    public static void clearVars(){
        vars.clear();
    }
    public static void showVars(){
        Object[] keys = vars.keySet().toArray();
        for (int i = 0; i < keys.length; i++){
            System.out.println(keys[i]+" = "+vars.get(keys[i]));
        }
    }
    public static Map getVars(){
        return vars;
    }    
    //�Ƿ���������
    protected boolean m_running = false;
    /**
     * ��Ϸ����
     */
    public GameWorld game;
    /**
     * �ű���Դ��Ϣ
     */
    public ScriptSource source;
    /**
     * �ű�����
     */
    public String[] args;
    
    public AbstractScript(){
        game = null;
    }
    /**
     * �Ƿ���������
     */
    public boolean isRunning(){
        return m_running;
    }
    
    /**
     * ��ʼ�ű�
     */
    public abstract void start();
    /**
     * ǿ��ֹͣ
     */
    public abstract void stop();
    /**
     * ִ�нű�
     */
    public abstract int update();

}
