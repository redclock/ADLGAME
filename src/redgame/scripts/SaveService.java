package redgame.scripts;
import redgame.*;
import redgame.obj.*;
import redgame.ui.*;
import redgame.engine.*;
import redgame.status.*;
import redgame.util.*;

public class SaveService extends SimpleScript {
    private AbstractMenu m;
    private String[] s = { "��Ҫ����", "������"};
    NPC a; 

    public void start(){
       	 super.start();
         m = new TextMenu(game, s);
    }
    
    protected void runScript(){
        Player b = (Player)source.cause; 
        if (a == null){
            a = (NPC) game.getMap().findActor("mf");
            if (a == null){
                stop();
                return;
            }    
        }
        switch(m_counter){
            case 1: a.say("��Ҫ�ڼ�¼�㵱ǰ����;��?"); break;
            case 2: game.pushStatus(new ShowMenuStatus(game, 100, 130, m)); break;
            case 3:if (m.getIndex() == 0) m_counter = 99; 
                   else a.say("��ô˵..."); 
                   break;
            case 4: a.say("��Ҫ������һ��?\n\ts20̫����!"); break;
            case 5: b.say("��, ����...\n�����תת"); break;
            //if select yes        
            case 100: game.pushStatus(new InputStatus(game, 100, 130, "������Ҫ���̵�����:"));
                    break;
            case 101: 
                if (InputStatus.result.equals("")){
                    a.say("�㲻���������ǲ��ܱ����");
                }else{
                    GameSaveLoad gsl = new GameSaveLoad(game, InputStatus.result);
                    if ( gsl.saveGame("map/saveroom.txt") ){
                    	a.say("����,�����!");	
                    }else{
                    	a.say("����ʧ���ˡ�\n\tb�㾿��������ʲô��");
                    }
                    break;
                }
            default: 
                    stop();
        }
    }
}        
