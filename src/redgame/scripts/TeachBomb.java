package redgame.scripts;
import redgame.obj.*;
import redgame.ui.*;
import redgame.status.*;
import redgame.item.*;

public class TeachBomb extends SimpleScript {
    private AbstractMenu m;

    public void start(){
         super.start();
         if (source.direction == Actor.G_UP || source.direction == Actor.G_DOWN){
            stop();
            return;
        }
    }

    protected void runScript(){
        Player b = (Player) source.cause;
        Actor a = (Actor) source.owner;
        
        b.canControl = false;

        int x = getIntVar("meetteacherbomb");
        a.setFace(Actor.G_RIGHT);
        //first meet
        if ( x == 0){
            switch(m_counter){
                case 1: a.say("\te0"); break;
                case 2: a.say("������ô�����ģ�"); break;
                case 3: a.say("�ұ���������ܾ��ˣ�\n��û�м�һ��������"); break;
                case 4: b.say("���ҵ��˰�Կ�ף����Ŵ��ˡ�"); break;
                case 5: a.say("�����?\n\ts30\tcffff0088��̫����!\n"); break;
                case 6: a.say("Ϊ�˱���ҵ�л�⣬"); break;
                case 7: a.say("���͸��������Ϸ��������������:"); break;
                case 8: a.say("\ts40\tcff880088ը��!");
                        game.playSound("sound/explode.wav");
                        b.curItem = b.items[Player.ITEM_BOMB] = new BombItem(game); 
                        b.curItem.setCount(3);
                            
                        setVar("meetteacherbomb", 2);
                        m_counter = 0;
                        break;
                case 9: a.say("����������ʹ�õķ���:"); break;
                default: b.canControl = true; 
                        setVar("meetteacherbomb", 2);
                        stop();        
            }
        }else if (x == 10){
            switch(m_counter){
                case 1:
                    a.say("�����ͨ���˵���[ѡ������]װ��\n" +
                          "ը����Ҳ���԰����ּ�3����װ��"); 
                    break;      
                case 2:
                    a.say("����װ��ú�, ���½ǻ���ͼ��ָʾ"); 
                    break;
                case 3:
                    a.say("����԰�X������Ҫ�ĵط���ը����\n");
                    break;
                case 4:
                    a.say("��һ��ʱ���ը���ͻᱬը��\n");
                    break;
                case 5:
                    a.say("ը��Ҳ�����˺����Լ���\n����ҪС��ʹ��"); 
                    break;
                case 6:
                    a.say("��ʾһ�㣬ը�����������Ǻܴ��\n������ը�˵��ˣ���ʱ��ʯͷ����ը�顣"); 
                    break;
                case 7:
                    a.say("����һ���������3��ը����"); 
                    break;
                case 8:
                    a.say("ը������ʱ�����������ҡ�"); 
                    break;
                default:
                    b.canControl = true; 
                    stop();    
                    setVar("meetteacherbomb", 2);
            }
        }else { 
            switch(m_counter){
                case 1:
                    if (b.items[Player.ITEM_BOMB] != null 
                        && b.items[Player.ITEM_BOMB].getCount() < 3) {

                        a.say("ը����������"); 
                    }else {
                        m_counter = 99;
                    }
                    break;
                case 2:
                    b.items[Player.ITEM_BOMB].setCount(3);
                    a.say("���ˣ��Ҹ���װ����");        
                    break;
                case 3:
                    a.say("��ס����һ��ֻ��װ3��ը��");
                    break;    
                case 100: a.say("��Ҫ��ը����ʹ�÷�����");
                        break;
                case 101:
                    m = new TextMenu(game, new String[]{ "����һ��", "������"});
                    game.pushStatus(new ShowMenuStatus(game, 100, 60, m));
                    break;
                case 102:    
                    if (m.getIndex() == 1){
                        b.canControl = true;
                        stop();
                    }else{
                        m_counter = 0;
                        setVar("meetteacherbomb", 10);
                    }
                    break;
                default:
                    b.canControl = true; 
                    stop(); 
            }           
        } 
    }
}            