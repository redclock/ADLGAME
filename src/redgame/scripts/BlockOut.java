package redgame.scripts;

import redgame.scripts.*;

import redgame.engine.*;
import redgame.ui.*;
import redgame.obj.*;
import redgame.status.*;
import redgame.util.*;

public class BlockOut extends SimpleScript {
    
    protected void runScript(){
        NPC    a = (NPC)source.owner;
        Player b = (Player)source.cause; 
        switch(m_counter){
            case 1:  
            	a.say("��,��Ҫ������ȥ��?");   
        		break;	
        	case 2:
            	a.say("�������װ��,����ȫѽ.");   
        		break;	
        	case 3:
            	a.say("�����Σ��,��û���㹻��װ���ǲ��ܳ�ȥ��.");   
        		break;	
        	case 4:
            	a.say("���ڴ�����תһת��.");   
        		break;	
            default: 
                stop();
        }
    }
}
