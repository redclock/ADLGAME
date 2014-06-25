package redgame.scripts;
import redgame.obj.*;
import redgame.engine.*;
import redgame.status.*;
import redgame.util.*;

public class TaskGirl extends SimpleScript {
    
    protected void runScript(){
        if (source.direction == Actor.G_DOWN || source.direction == Actor.G_UP) {
            stop();
            return;
        }    
        Player b = (Player) source.cause;
        NPC a = (NPC) source.owner;
        int x = getIntVar("meettask");
        b.canControl = false;
        switch(m_counter){
            case 1:
                a.say("......");
                break;
            case 2:
                a.say("��ô���أ�");
                break;
            case 3:
                a.say("\te4");
                break;
            case 4:
                b.say("ι�����Ǻ��¹��ﲻ�һ�ȥ�˰ɡ�");
                break;
            case 5:
                a.setFace(Actor.G_LEFT);
                a.say("����\n��...����ô�������");
                break;
            case 6:
                b.say("�����赣����ز�ȥ������������");
                break;
            case 7:
                a.say("���ң��ѵ����Լ������ȥ��");
                break;
            case 8:
                b.say("\te2\nʲô̬���");
                break;
            case 9:
                b.say("���ˣ������軹�ڵ����ء�\n�Ͽ�ؼҰɡ�");
                break;
            case 10:
                a.say("ƾʲô��˵�߾��ߣ�\n�һ�Ҫ����һ�����");
                break;
            case 11:
                b.say("\te2");
                break;
            case 12:
                b.say("�Ǻã����Ȼ�ȥ�ˡ�");
                b.setDest(2750, 10);
                break;
            case 13:
                a.say("��\te5......");
                break;
            case 14:
                a.setFace(Actor.G_DOWN);
                if (b.getX() > 2830) m_counter --;
                break;
            case 15:
                a.setFace(Actor.G_LEFT);
                a.say("�ȵ���......");
                game.playSound("sound/voice/wei.wav");
                a.setDest(2750, 10);
                break;
            case 16:
                if (a.getX() > 2860) m_counter --;
                break;
            case 17: 
                b.noDest();
                game.gotoMap("map/room5.txt", 50, 70);
                break;
            default:
                AbstractScript.setVar("meettask", 10);
                b.canControl = true;
                stop();    
        }
    }
    
}


