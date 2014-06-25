package redgame.scripts;
import redgame.obj.*;
import redgame.status.*;

public class Teach extends SimpleScript {
    NPC a; 
    protected void runScript(){
        Player b = (Player)source.cause; 
        
        if (a == null){
            a = (NPC) game.getMap().findActor("mf");
            if (a == null){
                stop();
                return;
            }    
        }

        int x = getIntVar("meetteacher_save");
        if (x == 0){
            switch(m_counter){
                case 1: a.say("\te0"); 
                        break;
                case 2: a.say("��λ����, ����ӡ�����, \n����Ҫ�ߺ���"); break;
                case 3: a.say("�Ȼ�����Ϸ��������һ��..."); break;
                case 4: 
                        b.say("\te6"); 
                        break;
                case 5: b.say("��, �㿴�����, ����!"); break;
                case 6: a.say("��?"); break;
                case 7: a.say("ԭ����"+b.getName()+"ѽ!\n�һ����п�����\n�������̫��..."); break;
                case 8: b.say("��, �㻹�ڸ���ƭ�˵Ļ�ѽ!"); break;
                case 9: a.say("\te2"); break;
                case 10: a.say("����С��, ˵����ô��С��!\n������...Ԥ������!�����ճԷ�!\n��ô��˵��ƭ��?"); break;
                case 11: b.say("^_^����, ��������"); break;
                case 12: a.say("������Щ�������������Щ����,\n" + 
                               "�������һ������Ҳû���ɡ�" ); break;
                default: 
                    setVar("meetteacher_save", x+1);
                    stop();
            }   
        }else if ( x == 1){
            switch(m_counter){
                case 1: a.say("ȥ��ȥ�������������⡣"); 
                        break;
                default: 
                    stop();
            }    
        }else{
            stop();
        }
    }     
}