package redgame.scripts;

import redgame.obj.*;

public class TeachSpring extends SimpleScript {
    static private int c = 0;
    protected void runScript(){
        Actor a = (Actor) source.owner;
        if (source.direction == Actor.G_DOWN){
            stop();
            return;
        }    
        switch(c % 3){
            case 0: a.say("���Թ������ɵ���?\n���漫��"); break;
            case 1: a.say("�ڵ��ɵ�������ĺܸ�\n�п��ܵ���ƽ���������ĵط�"); break;
            case 2: a.say("�ڵ��ɵ��ϲ�ͣ����, ���Խ��Խ��!"); break;
        }
        c++;
        stop();
    }   

}
