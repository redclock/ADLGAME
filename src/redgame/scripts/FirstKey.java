package redgame.scripts;
import redgame.obj.*;


public class FirstKey extends SimpleScript {
    protected void runScript(){
        Player b = (Player) source.cause;
        
        switch(m_counter){
            case 1: b.say("����,�����õ���!"); game.playSound("sound/voice/haha.wav"); break;
            case 2: b.say("\tb\tc00990000����ǰ���ϼ�����\n�ſ�֮���ϼ�����"); 
                    break;
            default: 
                AbstractScript.setVar("meetgrandma", 5);
                stop();        
        }
    }
}
