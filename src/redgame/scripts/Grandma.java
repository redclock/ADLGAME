package redgame.scripts;

import redgame.obj.*;


public class Grandma extends SimpleScript {
    protected void runScript(){
        NPC a = (NPC) source.owner;
        Player b = (Player) source.cause;

        if (source.direction == Actor.G_DOWN){
            game.playSound("sound/voice/wei.wav");
            a.say("С������, ���ʲô��\te2");
            b.setVY(-0.4f);
            stop();
            return;
        }
        
        if (getIntVar("open") == 0){
            int m = getIntVar("meetgrandma");
            if (m == 0){
                switch(m_counter){
                    case 1: 
                            b.say("����,�������ɫ��Խ��Խ����."); break;
                    case 2: a.say("�ѵ�����������\n�ǲ�����ʲô����?"); break;
                    case 3: b.say("��...���������ܲ���."); break;
                    case 4: a.say("����?\n��������첻���ȥ��."); break;
                    case 5: b.say("\te5\ts20\tcffff00ffΪʲô?"); break;
                    case 6: a.say("��Ҳ֪�����������кö���"); break;
                    case 7: a.say("��һ���˳�ȥ��̫Σ���ˡ�"); break;
                    case 8: b.say("\te2"); 
                            b.setDest(110, 10);
                            b.canControl = false;
                            break;
                    case 9: if (b.isMoving()){ m_counter --; break; }
                            b.say("����������취"); 
                            b.noDest();
                            b.canControl = true;
                            break;
                    default: setVar("meetgrandma", m+1); stop();
                }
            }else if (m == 1){
                b.canControl = false;
                switch(m_counter){
                    case 1: b.say("\ts20\tb����!"); break;
                    case 2: a.say("ʲô��?"); break;
                    case 3: b.say("��..�Ǹ�..."); break;
                    case 4: b.say("��,�ղŴ嶫ͷ�����������齫ȥ!"); break;
                    case 5: game.playSound("sound/voice/wa.wav");
                            a.say("\ts26\tcffff0000���ѽ!!\n\ts16\tcff000000�ҾͰ����齫");
                            
                            a.setVY(-0.3f);
                            a.setFace(3 - source.direction);                            
                            break;
                    case 6: if (a.getVY() != 0) m_counter --;  break;      
                    case 7: b.say("��ѽ��ѽ��ȥ��!"); break;
                    case 8: a.say("��������!"); 
                            a.setVY(-0.3f);
                            a.setDest(155, 10);
                            break;
                    case 9: if (a.isMoving()) 
                            {
                                m_counter --;
                                if (a.getVY() == 0) a.setVY(-0.4f);
                                break;
                            }else {
                            	if (a.getVY() != 0) m_counter --;
                            }
                            break;
                            
                    case 10: a.say("\ts20�ȵ�..."); b.setFace(Actor.G_RIGHT); break; 
                    case 11: a.setFace(Actor.G_LEFT);
                             a.say("����������ϸ���ȥ������.");
                             break; 
 
                    case 12: b.say("��...���...\tcff009900-_-!");
                             break;
                    case 13: b.say("�����������...���Դ���!");
                             break; 
                    case 14: game.playSound("sound/voice/heng.wav");
                             a.say("����ƭ��,\te2������ȥ��!");
                             
                             break; 
                    case 15: b.say("��ʧ����...");
                             break; 
                    default: b.canControl = true;
                             setVar("meetgrandma", m+1); stop();
                }
            }else if (m == 2){
                switch(m_counter){
                    case 1: b.say("����~~~"); break;
                    case 2: a.say("������Ҳû��.����Ϊ��á�\n���Ѿ��ѿ��ŵ�Կ�׷�����\n�ò����ĵط���."); break;
                    case 3: b.say("......"); 
                            b.setDest(50, 10);
                            b.canControl = false;
                            break;
                    case 4: if (b.isMoving()){ m_counter --; break; }
                            b.say("�������Լ��ҵ�Կ�׿�����\n\tb\tc00990000���Ƿ�Կ�׵ĵط�̫����");
                            b.noDest();
                            b.canControl = true;
                            break;
                    case 5: b.say("��ô��?"); break;
                    
                    default: setVar("meetgrandma", m+1); stop();
                }
            }else if (m == 3){
                switch(m_counter){
                    case 1: b.say("����, �ſ��ж���!"); break;
                    case 2: a.say("����, ��ȥ����"); 
                            a.setDest(375, 5);
                            break;
                    case 3: if (a.isMoving()){ m_counter --; break; }
                            a.say("ʲôҲû��ѽ"); 
                            a.noDest();
                            break;
                    case 4: b.say("ʱ������!"); break;
                    default: setVar("meetgrandma", m+1); stop();
                }
            }else if (m == 4){
                switch(m_counter){
                    case 1: a.setFace(3 - source.direction);
                            a.say("��˵��ʲô����?"); break;
                    case 2: b.say("����������?��������!"); break;
                    default: stop();
                }
            }else if (m == 5){
                switch(m_counter){
                    case 1: a.setFace(3 - source.direction);
                            a.say("��Ȼ����ĵ��ˡ�"); break;
                    case 2: b.say("�Բ������̣��ҳ�ȥһ����ͻ�����"); break;
                    case 3: a.say("������û�취��\n�������ǹܲ�������"); break;
                    case 4: a.say("�Ǻðɡ�����ɲ����뿪���ӡ�"); break;
                    default: setVar("meetgrandma", m+1); stop();
                }
            }else if (m >= 6){
                switch(m_counter){
                    case 1: a.setFace(3 - source.direction);
                            a.say("һ��ҪС�ġ�"); break;
                    default:stop();
                }
            }    
        }    
    }
}
