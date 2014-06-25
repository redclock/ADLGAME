package redgame.status;
/*
 * LogoStatus.java ���ߣ�Ҧ����
 */
import java.awt.*;
import java.awt.event.*;
import redgame.engine.*;
import redgame.anim.*;
//��������ϵͳ
class FlowerParticleSystem extends AbstractParticleSystem{
    private int x, y, w, h;
    private boolean isfirst = true;
    FlowerParticleSystem(GameWorld game, Image image, 
                    int count, int pw, int ph,
                    int begin, int end, int delay){
        super(game, image, count, pw, ph, begin, end, delay);
        Rectangle sc = game.getScreenArea();
        x = sc.x;
        y = sc.y;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        w = 640;//sc.width;
        h = 480;// sc.height;
        resetall();
        isfirst = false;
    }
    protected void reset(int i){
         
        Particle p = m_p.get(i);
        p.x = m_game.getRandom((w - 50) * 10) / 10 + x + 50;
        if (isfirst){
            p.y = m_game.getRandom(h * 10) / 10 + y;
        }else{
            p.y = 0;
        }
        p.vx = -(float)m_game.getRandom(100)/100 - 0.5f;
        p.vy = (float)m_game.getRandom(500)/100 + 1.0f;
        p.life = m_game.getRandom(6000)+ 1000;
        p.accx = 0;
        p.accy = (float)m_game.getRandom(100)/1000;
        p.alpha = (float)m_game.getRandom(100)/100;
        p.alphasp = 0.001f;
        p.rotate_angle_sp = (float)m_game.getRandom(1000)/100 * 0.01f - 0.05f;
        if (Math.abs(p.rotate_angle_sp) < 0.01f) p.rotate_angle_sp = 0.01f;

        p.start();

    }
}


/**
 * LogoStatus������Ϸ����ʾ���⼰���˵�����Ϸ״̬.
 * ����ʱ����ʾһ���˵�,�����ѡ��
 * @see AbstractStatus
 * @author Ҧ����
 */
public class LogoStatus extends AbstractStatus{
    private Image m_img;
    private FlowerParticleSystem m_fps;
    public LogoStatus(GameWorld game) {
        super(game);
        m_img = game.loadImage("image/logo1.png");
        game.deleteImage(m_img);
        m_fps = new FlowerParticleSystem(m_game, m_game.loadImage("image/fire.png"), 
                              20, 48, 48, 0, 0, 500);
    }

    
    public int update(long passedTime){
        m_counter += passedTime;
        if (m_counter > 0000){
            SwitchStatus s = new SwitchStatus(m_game, new TitleStatus(m_game), 1);
            s.MaxCounter = 5000;
            s.needRepaint = true;
            m_game.pushStatus(s);
        }
        
        return 0;
    }
    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    
    public int draw(long passedTime, Graphics g){
        Graphics2D g2 = (Graphics2D)g; 
        g2.setPaint( Color.BLACK );
        g2.fillRect(0,0,640,480);
        m_fps.paint(g);
        g2.drawImage(m_img, 0, 0, m_game.getPanel());
        return 0;
    }
    /**
     * ���¼�ʱ��
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */

    public int move(long passedTime) {
        m_fps.move(passedTime);
        return 0;
    }
}