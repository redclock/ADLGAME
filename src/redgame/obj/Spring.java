package redgame.obj;
import java.awt.*;
import redgame.engine.*;
import redgame.anim.*;
//��������ϵͳ
class StarParticleSystem extends AbstractParticleSystem{
    private int m_y;
    private int m_x;
    private int m_w;
    StarParticleSystem(GameWorld game, Image image, 
                    int count, int pw, int ph,
                    int begin, int end, int delay, int x, int y, int w){
        super(game, image, count, pw, ph, begin, end, delay);
        m_x = x;
        m_y = y;
        m_w = w;
        resetall();
    }
    protected void reset(int i){
         
        Particle p = m_p.get(i);
        p.x = m_game.getRandom(m_w*50)/50+m_x;
        p.y = -m_game.getRandom(500)/50+m_y;
        //m_vx = 1.0f;
        //m_x += m_vx;
        //m_y += m_vy;
        //m_vy += 0.005;
        p.vx = 0;//(float)m_game.getRandom(100)/100-0.5f;
        p.vy = (float)m_game.getRandom(100)/100-1.0f;
        p.alpha = 1.0f;
        p.life = m_game.getRandom(1500);
        p.accx = 0;
        p.accy = -(float)m_game.getRandom(100)/300;
        p.alpha = 1.0f;
        p.alphasp = 0.02f;
        p.start();
    }
}/**
 * Spring���ǵ�ͼԪ�ص��ɣ�������ǽ������������Ե�����
 * @author Ҧ����
 */
 public class Spring extends MapObject {
    private StarParticleSystem m_sps;
    /**
     * ���쵯��
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w Ԫ�ؿ��
     * @param h Ԫ�ظ߶�
     * @param tilew ͼ��һ��Ŀ��
     * @param tileh ͼ��һ��ĸ߶�
     * @see Animation
     * @see GameWorld
     * @see MapObject#MapObject
     */
    public Spring(GameWorld game, Image img, 
                    int x, int y, int w, int h,
                    int tilew, int tileh) {
        super(game, img, x, y, w, h, tilew, tileh);
        m_blocked = true;
        m_sps = new StarParticleSystem(game, 
                                game.loadImage("image/star.png"), 
                                100, 4, 4, 0, 0, 50, 
                                (int)getX(), (int)getY()+8, getW());
    }
    /**
     * ���ؼ�
     */
    public boolean isClimbable(){
        return false;
    }
    public boolean collision(AbstractObject obj, int direction){
        if (direction == AbstractObject.G_DOWN && obj instanceof MovableObject){
            MovableObject m = (MovableObject)obj;
            if (m.getVY() > 0){
                m.setVY(-m.getVY());
                m.meetSpring();
                if (m.getVY() < -0.05f )  m_game.playSound("sound/jump2.wav");
            } 
        }
        return false;
    }
    public void paint(Graphics g){
        super.paint(g);
        if (inScreen()){
            m_sps.move(m_game.passedTime);
            m_sps.paint(g);
        }
    }    
}