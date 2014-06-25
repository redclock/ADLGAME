package redgame.engine;

import java.util.*;
import java.lang.reflect.Constructor;
import redgame.scripts.AbstractScript;

public class ScriptManager{
    private Vector<AbstractScript> m_scripts = new Vector<AbstractScript>();
    private GameWorld m_game; 
    private String[] args;
    public ScriptManager(GameWorld game){
        m_game = game;
    }
    private String splitName(String name){
        String [] r = name.split(":");
        if ( r.length > 0 ){
            args = new String[r.length - 1]; 
            System.arraycopy(r, 1, args, 0, r.length - 1);
            return r[0];
        }else{
            args = new String [0];
            return "";
        }
    }
    /**
     * ��������ӽű�
     */
    public boolean add(String name, ScriptSource source){
        Class c;
        //�ֽ����ƺͲ���
        name = "redgame.scripts."+splitName(name);
        for (AbstractScript s: m_scripts){
            if(s.getClass().getName().equals(name)){
                return false;
            }
        }
        //if (! isEmpty() ) return false;
        try{
            //������
            c = Class.forName(name);
        }catch(ClassNotFoundException e){
            //û�������
            e.printStackTrace();
            return false;
        }     
        Constructor con;
        try{
            //���ҹ��췽��
            con = c.getConstructor(new Class[0]);
        }catch(NoSuchMethodException e){
            //�Ҳ������췽��
            e.printStackTrace();
            return false;
        }
        AbstractScript script;          
        //������ʵ��
        try{
            script = (AbstractScript)con.newInstance(new Object[0]);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        //��������
        script.game = m_game;
        script.source = source;
        script.args = args;
        
        m_scripts.add(script);
        script.start();
        
        System.out.println("Script start:"+name);
        
        return true;
    }
    
    public boolean isEmpty(){
        return m_scripts.isEmpty();    
    }
    /***
     * ִ����װ�صĽű�
     */
    public void update(){
        if (m_scripts.isEmpty()) return;
        //����ֹͣ��ֹͣ
        int i = m_scripts.size() - 1;
        while (i >= 0){
            AbstractScript script = m_scripts.get(i);
            if ( script.isRunning() == false ){
                script.stop();
                System.out.println("Script end:"+m_scripts.get(i));
                m_scripts.remove(i);
            }  
            i--;  
        }
        //���½ű�
        for (AbstractScript script: m_scripts){
            script.update();
        }
    }
    /**
     * ������нű�
     */
    public void clear(){
        for (AbstractScript s: m_scripts){
            s.stop();
        }
        m_scripts.clear();
        AbstractScript.clearVars();
    }
}