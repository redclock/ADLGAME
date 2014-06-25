package redgame.scripts;
import redgame.obj.*;
import redgame.ui.*;
import redgame.status.*;
import redgame.item.*;

public class Teach2 extends SimpleScript {
    private AbstractMenu m;

    public void start(){
         super.start();
    }
    protected void runScript(){
        Player b = (Player) source.cause;
        Actor a = (Actor) game.getMap().findActor("teacher");
        if (source.direction == Actor.G_UP || source.direction == Actor.G_DOWN){
            stop();
            return;
        }
        
        b.canControl = false;

        int x = getIntVar("meetteacher");
        //first meet
        if ( x == 0){
            switch(m_counter){
                case 1: a.say("������, ��Ը��Ը������ҵĲ���?"); break;
                case 2: a.say("������Կ���ѵ���㴳������������."); break;
                case 3: a.say("����,ͨ�����Ժ��һ����һ����������."); break;
                case 4: a.say("����"); break;
                case 5: a.say("�����ʧ����,�����ʧȥ����."); break;
                case 6: a.say("�����������������ս��?"); break;
                case 7: b.say("����˵����ô����...\n�ҵÿ��ǿ���"); break;
                case 8: b.say("Ը��Ը����?"); break;
                case 9:   
                    m = new TextMenu(game, new String[]{ "ͬ�����", "��Ը��"});

                    game.pushStatus(new ShowMenuStatus(game, 70, 150, m));break;
                case 10: 
                        if (m.getIndex() == 1){
                            a.say("��Ը����?\n�һ���Ϊ���Ǹ��е���������.");
                            b.canControl = true;
                            stop();
                        }else{
                            a.say("�Ǻ�, ���ǿ�ʼ");
                        }
                        break;
                case 11: game.gotoMap("map/trains1.txt", 17, 150);
                        
                        break;
                case 12: a.say("��Ҫͨ������·, ��Ҫ��������������.\n������Բȵ���������"); 
                        b.setFace(Actor.G_RIGHT);
                        break;
                case 13: a.say("�������������."); 
                        a.setFace(Actor.G_LEFT);
                        break;
                case 14: a.setPosition(6, 240); 
                        a.setFace(Actor.G_RIGHT);
                        break;
                default: b.canControl = true; 
                        setVar("meetteacher", x+1);
                        stop();        
            }
        //finish train    
        }else if (x == 1){
            switch(m_counter){
                case 1: a.say("������, �ɵĲ���!"); break;
                case 2: b.say("��̫����!"); break;
                case 3: game.gotoMap("map/room3.txt", 33, 110);
                        break;
                case 4: a.say("������, ��Ϊ����, ����һ�ѱ���");
                        b.curItem = b.items[Player.ITEM_SWORD] = new SwordItem(game); 
                        break;
                case 5: a.say("��Ҫ���Ҹ��㽲����ʹ�÷�����");
                    
                        break;
                case 6:
                    m = new TextMenu(game, new String[]{ "����һ��", "������"});
                    game.pushStatus(new ShowMenuStatus(game, 70, 150, m));
                    break;
                case 7:    
                    if (m.getIndex() == 1){
                        setVar("meetteacher", 2);
                        a.say("�ðɣ������������������");
                        b.canControl = true;
                        stop();
                    }else{
                        m_counter = 0;
                        setVar("meetteacher", 10);
                    }
                    break;
                default: b.canControl = true; 
                        setVar("meetteacher", 2);
                        stop();        
            }
        //teach sword  
        }else if (x == 10){
            switch(m_counter){
                case 1:
                    a.say("�����ͨ���˵���[ѡ������]װ��\n" +
                          "��Ľ���Ҳ���԰����ּ�1����װ��"); 
                    break;      
                case 2:
                    a.say("����װ��ú�, ���½ǻ���ͼ��ָʾ"); 
                    break;
                case 3:
                    a.say("ʹ����ѽ���Ҫ�����Ļ��\n" +
                          "�������Ž�ʱ�����������ͻ���������"); 
                    break;
                case 4:
                    a.say("���������һ������ʱ������԰�X���ӽ���\n" +
                          "�����Ὣ���˺������ĵ��ˡ�"); 
                    break;
                case 5:
                    a.say("��Ҳ���ԵȻ�����������ʱ����C���ӽ���\n" +
                          "����Ի�ø���Ĺ������͹�����Χ��"); 
                    break;
                case 6:
                    a.say("�ҽ̸���ľ���ô���ˡ�\nʣ�µľ�Ҫ�����Լ�ʵ���ˡ�"); 
                    break;
                default:
                    b.canControl = true; 
                    stop();    
                    setVar("meetteacher", 2);
            } 
        //default        
        }else if (x == 3){
            switch(m_counter){
                case 1: a.say("������, �ɵĲ���!"); break;
                case 2: b.say("��̫����!"); break;
                case 3: game.gotoMap("map/room3.txt", 33, 110);
                        break;
                default: b.canControl = true; 
                        setVar("meetteacher", 2);
                        stop();        
            }            
        }else {
            switch(m_counter){
                case 1:
                    a.say("�㻹����ʲô��"); 
                    break;
                case 2:
                    m = new TextMenu(game, new String[]{ "����ѧϰ����ʹ�÷���", "�����ٴν��ܲ���", "�����תת"});
                    game.pushStatus(new ShowMenuStatus(game, 70, 150, m));
                    break;
                case 3:    
                    if (m.getIndex() == 2){
                        b.canControl = true;
                        stop();
                    }else if (m.getIndex() == 0){
                        m_counter = 0;
                        setVar("meetteacher", 10);
                    }    
                    break;
                case 4:
                    a.say("�����㻹����δ���ء�"); 
                    break;
                case 5:
                    a.say("�õģ�������������ʹ�����\tcffff0000\tb��"); 
                    break;
                case 6: game.gotoMap("map/trains1.txt", 17, 150);
                        
                        break;
                case 7: a.say("���ϴ�һ������Ҫͨ������·��"); 
                        b.setFace(Actor.G_RIGHT);
                        break;
                case 8: a.say("�������������."); 
                        a.setFace(Actor.G_LEFT);
                        break;
                case 9: a.setPosition(6, 240); 
                        a.setFace(Actor.G_RIGHT);
                        break;
                default: b.canControl = true; 
                        setVar("meetteacher", 3);
                        stop();        
            }    
        }    
    }
}