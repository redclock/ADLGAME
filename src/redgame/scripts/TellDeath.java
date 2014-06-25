package redgame.scripts;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import redgame.obj.*;
import redgame.anim.*;
import redgame.status.*;

public class TellDeath extends SimpleScript {
    private long m_text_counter;
    private Animation m_anim;
    private boolean textAnim(int x, int y, String text, float r, float g, float b) {
        Graphics2D g2d = (Graphics2D) game.getBufferGraphics();
        if (g2d != null) {
            int size;
            float alpha;
            if (m_text_counter < 500) {
                size = (int) m_text_counter / 25 + 5;
                alpha = 1.0f;
            } else if (m_text_counter < 1000) {
                size = (int) 25;
                alpha = 1.0f;
            } else {
                size = (int) (m_text_counter - 1000)/ 50 + 25;
                alpha = (2000 - m_text_counter) / 1000.0f;
                if (alpha < 0) alpha = 0;
                if (alpha > 1.0f) alpha = 1.0f;
            }   
            Font f = new Font("����", Font.BOLD, size);
            g2d.setFont(f);
            g2d.setColor(new Color(r, g, b, alpha));
            Rectangle rect = f.getStringBounds(text, g2d.getFontRenderContext()).getBounds();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString(text, x - rect.width / 2, y);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        m_text_counter += game.passedTime;
        return m_text_counter >2000;
    }
    
   
    protected void runScript(){
        Player b = (Player) source.cause;
        NPC    a = (NPC) source.owner;
        AbstractObject granny_down = game.getMap().findMapObject("grannydown");;
        NPC granny = (NPC)game.getMap().findActor("granny");;
        
        b.canControl = false;
        switch(m_counter){
            case 1: b.say("�󲮣�����ô�����"); break;
            case 2: a.say("��û�м�����������"); break;
            case 3: b.say("�ף�������ô�ˡ�����ȥ����"); break;
            case 4: a.say("���������û�л������ܵ��������¡�\n" +
                          "���Ծͳ�ȥ�����ˣ������Ұ�æ���ҡ�" ); break;
            case 5: b.say("�������ǵģ����ֲ���С�����ˡ�"); break;
            case 6: b.say("......"); 
                    
                    break;
            case 7: b.say("�һ���ȥ�������̰�"); break;
            case 8: 
                AbstractObject d = game.getMap().findStatic("door");
                if (d != null){
                    d.say("\ts30�����ˣ����������ˣ�");
                }
                game.stopMusic();
                game.playSound("sound/shock.mid");
                break;
            case 9: b.setFace(Actor.G_RIGHT);
                b.say("\te0");
                break;
            case 10: b.setDest(450, 10);
                break;        
            case 11: if (b.getX() < 400) m_counter --;
                break;
            case 12:
                b.setVisible(false);
                b.noDest();
                game.gotoMap("map/blank.txt", 1000, 0);            
                m_text_counter = 0;
                break;
            case 13:
                b.setVisible(false);
                m_text_counter += game.passedTime;
                if (m_text_counter < 1000) {
                    m_counter --;
                }else {
                    m_text_counter = 0;
                }
                break;
            case 14:
                if (! textAnim(300, 250, "��"+ b.getName()+"������!" , 1, 1, 1)) m_counter --;
                else m_text_counter = 0;
                break;
            case 15:
                if (! textAnim(250, 200, "��...��ô������." ,1 ,1 ,0)) m_counter --;
                else m_text_counter = 0;
                break;
            case 16:
                if (! textAnim(400, 150, "��˵��ȥ�ҳ�ȥ���" + b.getName() ,0 ,1 ,0)) m_counter --;
                else m_text_counter = 0;
                break;
            case 17:
                if (! textAnim(400, 150, "��������˹���, ��������" ,0 ,1 ,0)) m_counter --;
                else m_text_counter = 0;
                break;
            case 18:
                if (! textAnim(300, 250, "ҽ��, ������ô��?" ,1 ,1 ,1)) m_counter --;
                else m_text_counter = 0;
                break;
            case 19:
                if (! textAnim(300, 350, "����, ����......" ,0 ,1 ,1)) m_counter --;
                else m_text_counter = 0;
                break;
            case 20:
                if (! textAnim(350, 150, b.getName() + "����!" ,0 ,1 ,0)) m_counter --;
                else m_text_counter = 0;
                break;
            case 21:
                m_anim = new Animation(game, game.loadImage("image/player.png"), 
                                    32, 48);
                m_anim.setRange(0, 3, 100); 
                m_anim.start();
                m_text_counter = 0;                   
                break;
            case 22:
                m_anim.update(game.passedTime);
                m_anim.paint(game.getBufferGraphics(), 300, (int) m_text_counter/20);
                m_text_counter += game.passedTime;
                if (m_text_counter < 5000) m_counter --;
                break;
            case 23:
                m_anim = null;
                b.setFace(Actor.G_DOWN);
                b.setVisible(true);
                b.setGravity(false);
                b.setPosition(300 + 4, 250 + 4);
                break;
            case 24:
                b.say("��...����?.....");
                break;    
            case 25:
                b.say("����ô��, ����������?");
                break;    
            case 26:
                b.say("�����Ҳ���, �Ҳ��ó�ȥ.");
                break;    
            case 27:
                b.say("����...");
                break;    
            case 28:
                b.say("����, ����Ҫ��ѽ...");
                break;    
            case 29:
                game.stopMusic();
                b.say("\ts40����!");
                break;    
            case 30:
                if (granny_down != null) granny_down.say("\te2");
                break;    
            case 31:
                if (granny_down != null) granny_down.setVisible(false);
                if (granny != null) {
                    granny.setPosition(290, 300);
                    granny.setVisible(true);
                    granny.setGravity(false);
                    granny.say("ι, �����Һò���.\n�һ�û����.");
                }
                break;    
            case 32:
                b.say("��? ������û������?");
                break;    
            case 33:
                if (granny != null) {
                    granny.say("�����, �ҵ����ӻ�Ӳ�ʵĺ���.");
                }
                m_text_counter = 0;
                break;    
            case 34:
                if (!textAnim(300, 250, "�������ܵ�ֻ����΢��Ƥ����" ,0 ,1 ,1)) m_counter --;
                else m_text_counter = 0;
                break;    
            case 35:
                if (!textAnim(300, 250, "���ĺ���" ,0 ,1 ,1)) m_counter --;
                else m_text_counter = 0;
                break;    
            case 36:
                if (!textAnim(150, 200, "̫����, ̫����" ,1 ,1 ,1)) m_counter --;
                else m_text_counter = 0;
                break;
            case 37:
                if (!textAnim(400, 300, "�ղ����ǵ�������" ,1 ,1 ,0)) m_counter --;
                else {
                    m_text_counter = 0;
                    game.playSound("sound/win2.mid");
                }    
                break;
            case 38:
                b.say("����̫������!\te3");
                break;        
            case 39:
                game.pushStatus(new SwitchStatus(game, new CompleteAllStatus(game), 1));
                break;        
            default: 
                b.noDest();
                b.setGravity(true);
                b.canControl = true;
                stop();        
        }
    }
}
