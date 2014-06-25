package redgame.scripts;

import redgame.obj.*;
import redgame.status.*;
import redgame.util.*;

public class Beginning extends SimpleScript {
   Player b;

    NPC c;
    NPC a;

    public void start() {
        super.start();
        b = game.getPlayer();
        c = (NPC) game.getMap().findActor("mf");
        a = (NPC) game.getMap().findActor("grandma");
        int x = getIntVar("process");
        if (x != 3) {
            if (c != null) {
                c.setVisible(false);
                c.setBlocked(false);
            }
        }else{
            if (a != null) {
                a.setVisible(false);
                a.setBlocked(false);
            }
        }
    }
    
    protected void runScript(){
        int x = getIntVar("process");
        
        if (x == 0){
            switch(m_counter){
                case 1: 
                    b.canControl = false;
                    game.playSound("sound/voice/nihao.wav");
                    b.say("\ts25��Һ�!��һ�κʹ�Ҽ���"); 
                    b.setFace(Actor.G_DOWN);
                    break;
                case 2: 
                    b.say("�����ҽ���, �ҵ�������..."); 
                    break;
                case 3: 
                    b.say("ѽ, ����, �һ�û��������!"); 
                    b.setFace(Actor.G_DOWN);
                    break;
                case 4:
                    game.pushStatus(new InputStatus(game, 220, 170, "������ȡһ�����ְ�:"));
                    break;
                case 5:
                    if (InputStatus.result.trim().equals("")){
                        b.setName("����");
                    }else{
                        b.setName(InputStatus.result.trim());
                    }
                    KeyManager.clearKeyState();
                    break;
               case 6:     
                    b.say("����, �ҽ�"+b.getName()+".\te3");
                    break;
                case 7:     
                    b.say("��ô�ҽ���������һ�»���������!"); 
                    break;
                case 8: 
                    b.say("\tb\tc00990000��Z������Ծ!");
                    break;
                case 9: game.playSound("sound/voice/wa.wav");
                        b.setVY(-0.3f);
                        break;  
                case 10: if(b.getVY() != 0) {m_counter --; break;  } 
                    b.say("\tb\tc00990000��������ʱ���ϼ�,�Ϳ�����������");
                    break;
                case 11: 
                    b.setFace(Actor.G_RIGHT);
                    b.say("�����������ǳ�ȥ��ĺ�������");        
                    break;
                case 12: 
                    b.say("��,�Ż�������,��������ҪԿ����");
                    break;
                default:
                    b.canControl = true; 
                    setVar("process", 1);
                    stop();        
            }
        }else {
            stop();
        }
            
    }
}
