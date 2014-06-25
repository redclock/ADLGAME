package redgame.scripts;

import redgame.scripts.*;
import redgame.engine.*;
import redgame.ui.*;
import redgame.obj.*;
import redgame.status.*;
import redgame.util.*;
import redgame.anim.*;


public class SaveFairy extends SimpleScript {

    private AbstractMenu m;
    private String[] s = { "��Ҫ����", "������"};
    AbstractObject a; 

    public void start(){
       	 super.start();
         m = new TextMenu(game, s);
    }
    
    protected void runScript(){
        Player b = (Player)source.cause; 
        int meet = AbstractScript.getIntVar("meetFairy");
        if (source.direction != AbstractObject.G_RIGHT){
        	stop();
        	return;
        }
        if (a == null){
            a = (AbstractObject) game.getMap().findMapObject("fairy");
            if (a == null){
                stop();
                return;
            }    
        }
        switch(m_counter){
            case 1:  
            	a.say("��ӭ����ħ���ݣ�");   
        	case 2: if (meet == 0){
					m_counter = 199;
        		}
        		break;	
            case 3: a.say("��Ҫ�������¼�㵱ǰ����;��?"); break;
            case 4: game.pushStatus(new ShowMenuStatus(game, 450, 150, m)); break;
            case 5: if (m.getIndex() == 0) m_counter = 99; 
                   else a.say("ף��һ·ƽ����..."); 
                   if (meet == 0) m_counter = 149;
                   
                   break;
                   
            //if select yes        
            case 100: game.pushStatus(new InputStatus(game, 400, 150, "������Ҫ���̵�����:"));
                    break;
            case 101: 
                if (InputStatus.result.equals("")){
                    a.say("�㲻���������ǲ��ܱ����");
                }else{
                    GameSaveLoad gsl = new GameSaveLoad(game, InputStatus.result);
                    if ( gsl.saveGame("map/magicroom.txt") ){
						m_counter = 299;
                    }else{
                    	a.say("����ʧ���ˡ�\n\tb�㾿��������ʲô��");
                    }
                }

                break;
            //first meet fairy    
            case 200:  
            	b.say("�ۣ��㸡�����治����ͷ����");   
            	break;
            case 201:  
            	a.say("......");  
            	break;
            case 202:  
            	a.say("�Ǻǣ��ҿ�����Ů");   
            	break;
            case 203:  
            	a.say("С����, ������̤��ð�����̵�׼������?");   
            	break;
            case 204:
            	a.say("ǰ���·;���ǳ�����������.");   
            	break;
            case 205:
            	b.say("�ޣ�����˵������Ȥ�ġ�\n����һ��Ҫ��ȥ������");   
            	break;
            case 206:
            	a.say("̫���ˣ��������ó����˽�չ��\n�����˵������������һ��¼����Ĺ���");   
            	break;
            case 207:
                a.say("���������¼��,�������ESC\n" + 
                          "�����Ĳ˵���ѡ��[������Ϸ],\n" +
                          "ѡ���¼���������ð��");
                m_counter = 2;
                AbstractScript.setVar("meetFairy", 1);    
                break;
            
           	//save successfully
           	case 300:
                AnimStatus s = new AnimStatus(game, 430, 230, "image/anim/light2.txt", 
                            new SparkParticleSystem(game, game.loadImage("image/star2.png"), 
                                410, 230, 30, 
                                64, 64, 0, 0, 100,
                                12, 15, 0.1f));
                game.playSound("sound/magic.wav");
                game.pushStatus(s);                     
				break;
           	case 301:
				a.say("����,�����!");	
            default: 
                stop();
        }
    }
}
