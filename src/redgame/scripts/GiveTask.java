package redgame.scripts;

import redgame.obj.*;

public class GiveTask extends SimpleScript {
    protected void runScript(){
        if (source.direction == Actor.G_DOWN || source.direction == Actor.G_UP){
            stop();
            return;
        }    
        Player b = (Player) source.cause;
        Actor a = (Actor) source.owner;
        Actor c = (Actor) game.getMap().findActor("girl");
        Door d = (Door) game.getMap().findStatic("door");
        int x = getIntVar("meettask");
        b.canControl = false;

        if ( x == 0){
            switch(m_counter){
                case 1: b.say("����, ��������ʲô�óԵ�?"); break;
                case 2: a.say("ԭ����"+b.getName()+"��"); break;
                case 3: a.say("......");  
                        break;
                case 4: a.say("�����������...");  
                        break;
                case 5: b.say("\te1"); break;
                default: b.canControl = true; 
                         setVar("meettask", x+1);
                         stop();
            }             
        }else if ( x == 1){
            switch(m_counter){
                case 1: a.say("����Ϊ��Ů�����·����ء�"); break;
                case 2: b.say("Ŷ��"); 
                		game.playSound("sound/voice/o.wav");
                		break;
                case 3: b.say("��ô�˰�?"); break;
                case 4: a.say("��Ů������˵����Ҫ����ػ���.\n�ɵ����ڻ�û�е�."); break;
                case 5: a.say("���������������ͻȻ����������."); break;
                case 6: a.say("����..."); break;
                case 7: a.say("��һ..."); break;
                case 8: a.say("��ʲô��..."); break;
                case 9: a.say("��......"); break;
                case 10: a.say("��~~~"); break;
                case 11: b.say("\te6"); break;
                case 12: b.say("����������ˡ��ҳ�ȥ�Ӱ��ΰ�"); break;
                case 13: a.say("����̫����\n^_^"); break;
                case 14: a.say("���ҵ���һ���������������ѽ��\te3"); break;
                case 15: b.say("......"); break;
                default: b.canControl = true; 
                         setVar("meettask", x+1);
                         stop();
            }
        }else if ( x == 2){
            switch(m_counter){
                case 1: a.say("��ô����������Ů������"); break;
                case 2: b.say("��û��......"); 
                        break;
                default: b.canControl = true; 
                         stop();
            }            
        }else if ( x ==10 ){    //�Ѿ��һ�����
            switch(m_counter){
                case 1: a.say("��л�����Ů���ӻ�����\te3"); 
                        break;
                case 2: b.say("������ûʲô��\n^,^");
                        break;
                case 3: a.say("û����İ��������ǲ��һ����ġ�\n�Ǻ�");
                        break;
                case 4: b.say("�Ǻ�...");
                        if (d != null){
                            d.open();    
                            game.playSound("sound/dooropen.wav");
                        }
                        break;
                case 5: 
                        c.setBlocked(true);
                        c.setVisible(true);
                        c.setPosition(460, 65);
                        c.say("�ҲŲ��ǲ����أ�");
                        game.playSound("sound/voice/wei.wav");
                        break;
                case 6: 
                        b.setFace(Actor.G_RIGHT);
                        b.say("\te0");
                        break;
                case 7: 
                        c.say("���ǲ����л��ģ�\n\te2");
                        break;
                case 8: 
                        c.setFace(Actor.G_UP);
                        a.say("����, ����ô����ô˵��");
                        break;
                case 9: c.setVisible(false);
                        c.setBlocked(false);
                        if (d != null){
                            d.close();    
                            game.playSound("sound/dooropen.wav");
                        }
                        break;
                case 10: b.say("Ϊʲô����ô��Ƣ���أ�"); break;
                case 11:
                      a.say("������˼����\n����Ů��������ò��"); 
                      break; 
                case 12:
                      b.setFace(Actor.G_LEFT);
                      b.say("������û��ϵ�ġ�"); 
                      break; 
                case 13:
                      a.say("���ˣ�" + b.getName() +"��������ó�ʱ���ˡ�\n�����̲�������"); 
                      break; 
                case 14:
                      b.say("˵��Ҳ�ǡ��������ڻؼ��ˡ�"); 
                      break; 
               
                default: b.canControl = true; 
                         setVar("meettask", x+1);
                         setVar("process", 3);
                         stop();
            }            
        }else {
            switch(m_counter){
                case 1:
                      a.say("�Ժ����桫"); 
                      
                      break; 
                default: b.canControl = true; 
                         stop();
            }            
        }        
                
    }    
}