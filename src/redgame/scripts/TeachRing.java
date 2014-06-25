package redgame.scripts;
import redgame.obj.*;
import redgame.ui.*;
import redgame.status.*;
import redgame.item.*;

public class TeachRing extends SimpleScript {
    private AbstractMenu m;

    public void start(){
       	 super.start();
    }
    protected void runScript(){
        if (source.direction == Actor.G_DOWN){
            stop();
            return;
        }    
        Player b = (Player) source.cause;
        Actor a = (Actor) game.getMap().findActor("teacher");
        b.canControl = false;

        int x = getIntVar("meetteacher_ring");
        if ( x == 0){
            switch(m_counter){
                case 1: a.say("������, ���ܵ��������ǲ�����."); break;
                case 2: a.say("��������Ĺ������."); break;
                case 3: if (b.canSword() == false){
                            a.say("ʲô?�㻹û�ҵ�һ��������?");
                            m_counter = 99; //jump to case 100
                        }else{
                            a.say("��Ը������ҵĲ�����?");
                        }
                        break;
                case 4: b.say("����˵����ô����...\n�ҵÿ��ǿ���"); 
                    break;
                case 5: b.say("Ը��Ը����?"); 
                    break;
                case 6:   
                    m = new TextMenu(game, new String[]{ "ͬ�����", "��Ը��"});
                    game.pushStatus(new ShowMenuStatus(game, 895, 32, m));
                    break;
                case 7: 
                        if (m.getIndex() == 1){
                            a.say("��Ը����?\n�һ���Ϊ���Ǹ��е���������.");
                            b.canControl = true;
                            stop();
                        }else{
                            a.say("�Ǻ�, ���ǿ�ʼ");
                        }
                        break;
                case 8: game.gotoMap("map/trains2.txt", 560, 370);
                        break;
                case 9: a.say("�����ս�Ǵ���⼸��������"); break;
                case 10: a.say("С��Ϊ��ѽ!�Ǻ�"); 
                        setVar("meetteacher_ring", x+1);
                        b.canControl = true; 
                        stop();
                        break;
                
                //if no sword
                case 100: a.say("��̫��ϧ��...\n���ҵ����ʵ�����֮���������Ұ�"); 
                        break;
                default: b.canControl = true; 
                        stop();        
            }
            //in training
        }else if ( x == 1){
            if (game.getMap().findActor("e1") != null &&
                (game.getMap().findActor("e1").getVisible() ||
                game.getMap().findActor("e2").getVisible() ||
                game.getMap().findActor("e3").getVisible() ||
                game.getMap().findActor("e4").getVisible() ||
                game.getMap().findActor("e5").getVisible())){
                   a.say("Ҫ�������˶������\n����ѽ!"); 
                   b.canControl = true; 
                   stop();
            }else{
                //succeed
                switch(m_counter){
                    case 1: a.say("������, �ɵĲ���!"); break;
                    case 2: b.say("��̫����!"); break;
                    case 3: game.gotoMap("map/room4.txt", 806, 20); break;
                    case 4: a.say("��ͨ���˲���, ����һ������!"); break;
                    case 5: a.say("\tcffff0000\tb\ts20����ħ�����Ȧ������!"); 
                        b.curItem = b.items[Player.ITEM_RING] = new RingItem(game);
                        b.items[Player.ITEM_RING].setCount(3);
                        break;
                    case 6: a.say("�������������Ȧ!\n�����ҵ�����ͷ��\n��ʹ�����һ��ʱ��"); 
                        break;
                    case 7: a.say("����������ϸ��ʹ�÷�����");
                        break;    
                    case 8:   
                        m = new TextMenu(game, new String[]{ "��������", "������"});
                        game.pushStatus(new ShowMenuStatus(game, 895, 40, m));
                        break;
                    case 9:
                        if (m.getIndex() == 1){
                            b.canControl = true;
                            setVar("meetteacher_ring", x+1);
                            stop();
                        }else{
                            m_counter = 0;
                            setVar("meetteacher_ring", 3);
                        }
                        break;
                            
                    default: b.canControl = true; 
                             setVar("meetteacher_ring", x+1);
                            stop();        
                }
            }
        }else if ( x == 2){
            switch(m_counter){
                case 1: a.say("���������Ȧ��ʹ�÷�����");
                    break;    
                case 2:   
                    m = new TextMenu(game, new String[]{ "��������", "������"});
                    game.pushStatus(new ShowMenuStatus(game, 895, 32, m));
                    break;
                case 3:
                    if (m.getIndex() == 1){
                        b.canControl = true;
                        stop();
                    }else{
                        m_counter = 0;
                        setVar("meetteacher_ring", 3);
                    }
                    break;
                default: b.canControl = true; 
                        stop();        
            }
        }else if ( x == 3){
            switch(m_counter){
                case 1: a.say("�����������Ȧ��ʹ�÷���:"); break;
                case 2:
                    a.say("�����ͨ���˵���[ѡ������]װ��\n" +
                          "���Ȧ��Ҳ���԰����ּ�2����װ��"); 
                    break;      
                case 3:
                    a.say("����װ��ú�, ���½ǻ���ͼ��ָʾ"); 
                    break;
                case 4: a.say("��סX�����ţ�������ɫ׼�Ǻ󰴡�����\n" +
                              "�������ʵ�λ�ã��ſ�X����һ��������\n" +
                              "�Ե����Ȧ�ͷ����ȥ��"); 
                        break;
                case 5: a.say("�����ҵ�����ͷ��\n��ʹ�����һ��ʱ��"); 
                case 6: a.say("��ֻ�����޸����Ȧ, \n���½ǵ�ͼ����ʾ������Ȧ��"); 
                        break;
                case 7: a.say("�������Ȧ����ʱ��C��, ���Խ������ա�"); 
                        break;
                default: b.canControl = true; 
                         setVar("meetteacher_ring", 2);
                         stop();        
            }
        }  
    }
}