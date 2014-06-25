package redgame.scripts;
import redgame.obj.*;

public class TestScript extends ThreadScript{
    protected void runScript() throws ThreadTerminated{
        Actor  a = (Actor) source.owner;
        Actor  b = (Actor) source.cause;
        
        if (source.direction == Actor.G_DOWN){
            game.playSound("sound/voice/wei.wav");
            a.say("\ts20\tbι����\n\ts14\tB�㲻Ҫ���ҵ�ͷ��");
            unlock();
            b.setVY(-0.4f);
        }else{
            game.playSound("sound/voice/nihao.wav");
            a.say("��á�");
            unlock();
        }
        waitTime(1500);
    }
}
