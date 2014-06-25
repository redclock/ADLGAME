package redgame.scripts;
import redgame.obj.*;

public class TestGoTo extends SimpleScript {
    Friend a;
    
    protected void runScript(){
        if (m_counter > 1){
            Friend a = (Friend) source.owner;
            
            if (!a.isMoving()){
                a.say("����");
                stop();
            }
            return;
        }
        Friend a = (Friend) source.owner;
        if (source.direction == Actor.G_UP){
            //a.say("��!");
            a.setVY(-0.4f);
            stop();
        }else{
            if (a.getX() < 200){
                a.say("��\tb��\tB��!");
                a.setDest(300, 10);
            }else{
                a.say("��\tb��\tB��!");
                a.setDest(100, 10);
            }
           
        }    
    }
}
