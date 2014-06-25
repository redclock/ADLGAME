package redgame.obj;
/*
 * NPC.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import redgame.engine.*;
/**
 * NPC���Ƿ���ҽ�ɫ,ʵ������ҽ�ɫҲ��������
 * ÿ�θ���,��update��������new_faceֵ,����move���������ƶ�
 * @author Ҧ����
 */

public class NPC extends Actor{
    /**�Ƿ��������ӡ�*/
    protected boolean m_isClimbing = false;  
    /**�Ƿ��ڻӽ�*/
    protected boolean m_isSwording = false; 
    //Ҫ�ߵ���Ŀ��λ�õĺ�����
    protected int m_destRangeStart;
    protected int m_destRangeEnd;
    /**
     *����
     */
    protected Sword m_sword;

    /**
     * ��new_face��Ϊ0~3����,ʹ����Գ�ĳһ������,
     * ��Ϊ-1��ֹ.
     * @see Actor#m_face
     */
	protected int new_face = -1;
    /**
     * ����NPC
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w ͼ��һ��Ŀ��
     * @param h ͼ��һ��ĸ߶�
     * @see Actor
     */

	public NPC(GameWorld game, Image img, int x, int y, int w, int h) {
		super(game, img, x, y, w, h);
		m_delay = 100;
		updateAnimation();
		noDest();
        HP = 3;
	}
    /**
     * ����Actor.move
     * @see Actor#move
     */
	public void move(long passedTime){
        //���˾Ͳ�����
        if (m_spark1 != null)
        {
        	m_spark1.move(passedTime);
        	if (m_spark1.done()) m_spark1 = null;
        }
        if (m_spark2 != null)
        {
            m_spark2.move(passedTime);
            if (m_spark2.done()) m_spark2 = null;
        }

        if (m_dead) return;

        float t = (passedTime * 10.0f/ m_delay);
		float dt;
        //������
        if (m_lull) {
            waitLull(passedTime);
            return;
        }
        //�˷ܼ���
        if (m_cordial) {
            m_delay = 50;
            waitCordial(passedTime);
        }
        //�ӽ���
        if (m_isSwording){
            if (m_sword.finished()){
                m_isSwording = false;  
                m_game.getMap().removeBullet(m_sword);
                m_sword = null;
                updateAnimation();
            }
            moveDown(passedTime);
            return;
        }else if (m_isClimbing){
            //�����Ƿ��������ӵ�״̬
            m_isClimbing = m_game.getMap().canClimb(this);
            if (!m_isClimbing){
                //�����������,���ѡ������
                m_face = (m_game.getRandom(2) == 0) ? G_LEFT: G_RIGHT;
                updateAnimation();
            }else{
                //������
                if (m_face != G_UP){
                    m_face = G_UP;
                    updateAnimation();
                }
                //����ʱ
                if (new_face == -1) {
                    m_anim.resetToStart();
                }
                //�ƶ�
                else if (new_face == G_UP){
                    dt = m_game.getMap().gotoUp(this, t);
                    y -= t;
                }
                else if (new_face == G_DOWN){
                    dt = m_game.getMap().gotoDown(this, t);
                    y += t;
                }
                else if (new_face == G_LEFT){
                    dt = m_game.getMap().gotoLeft(this, t);
                    x -= dt;
                }
                else if (new_face == G_RIGHT){
                    dt = m_game.getMap().gotoRight(this, t);
                    x += dt;
                }               
                m_anim.update(passedTime);
            }
        }else{
            //�����ƶ�                
            //new_face == -1��ʾ����
            if (new_face == -1) {
    			m_anim.resetToStart();
    		}else {
    		//��new_face�ƶ�  
    			if (m_face != new_face){
    				m_face = new_face;
    				updateAnimation();
    			}
    			else if (m_face == G_LEFT){
    				dt = m_game.getMap().gotoLeft(this, t);
    				if (dt < t) cannotGo();
    				x -= dt;
    			}
    			else if (m_face == G_RIGHT){
    				dt = m_game.getMap().gotoRight(this, t);
    				if (dt < t) cannotGo();
    				x += dt;
    			}
    			m_anim.update(passedTime);	
    			//new_face = -1;
    		}
    		moveDown(passedTime);
        }		
	}
    /**
     * ����collision
     * @see MovableObject#collision
     */
    
    public boolean collision(AbstractObject obj, int direction){
        if(!m_isSwording && !m_lull && new_face != -1 && direction == G_DOWN && obj instanceof MovableObject)
        //���Ŵ��ϱ���������
        {
            float t = (m_game.passedTime * 10.0f/ m_delay);
            float dt;
            if (m_face == G_LEFT){
                dt = m_game.getMap().gotoLeft(obj, t);
                obj.setPosition(obj.getX() - dt, obj.getY());
            }
            else if (m_face == G_RIGHT){
                dt = m_game.getMap().gotoRight(obj, t);
                obj.setPosition(obj.getX() + dt, obj.getY());
            }
        }
        //ִ�нű�
        if (m_script != null && obj instanceof Player){
            m_game.getScript().add(m_script, 
                                    new ScriptSource(this, obj, direction));
        }

        return false;
    } 
    /**
     * �������ϰ�ʱ����
     */
	public void cannotGo(){
	}
	
	public void update(long passedTime){
        if (getX() < m_destRangeStart) new_face = G_RIGHT;
        else if (getX() > m_destRangeEnd) new_face = G_LEFT;
        else new_face = -1;
	}
	/**
	 * �ӽ�
	 */
	public void doSword(int delay, boolean special){
       if (! m_isSwording ){
            m_isSwording = true;
            int x, y, b, e, w;
            Image img;
            if (special){
                if (m_face == G_LEFT){
                    x = (int)getX() - 35;
                    y = (int)getY();
                    b = 0;
                    e = 4;
                    w = 18;
                    img = m_game.loadImage("image/sword2_l.png");
                }else{
                    x = (int)(getX() + getW()) + 15;
                    y = (int)getY();
                    b = 0;
                    e = 4;
                    w = 24;
                    img = m_game.loadImage("image/sword2_r.png");
                }
            }else {
                if (m_face == G_LEFT){
                    x = (int)getX() - 15;
                    y = (int)getY();
                    b = 0;
                    e = 4;
                    w = 18;
                    img = m_game.loadImage("image/sword.png");
                }else{
                    x = (int)(getX() + getW()) + 15;
                    y = (int)getY();
                    b = 5;
                    e = 9;
                    w = 24;
                    img = m_game.loadImage("image/sword.png");
                }
            }
            if (special) {
                m_sword = new Sword(m_game, this, img, x, y, 64, 64, b, e, 3);
                m_sword.setBlockRect(new Rectangle(w, 15, 16, 48));            
            } else {
                m_sword = new Sword(m_game, this, img, x, y, 48, 64, b, e, 1);
                m_sword.setBlockRect(new Rectangle(w, 15, 6, 34));            
            }
            m_sword.setAnimDelay(delay);            
            m_sword.makeRect(); 
            m_game.getMap().addBullet(m_sword);
        }
	}
	/**
	 * ����
	 */
	public void doShoot(int ox, int oy, float vx, float vy){
        Bounce bullet = new Bounce(m_game, m_game.loadImage("image/icon1.png"),
                   ox, oy, 24, 24);
        bullet.setBlockRect(new Rectangle(0, 0, 24,24));                                    
        bullet.setSpeed(vx, vy);                        
        m_game.getMap().addBullet(bullet);                        
	}	
    /**
     * ����
     */
    public void doShoot(float angle, float r, float v){
        int ox = (int)(getX()+ getW()/2 + r*Math.cos(angle)-12);
        int oy = (int)(getY()+10 + r*Math.sin(angle));
        float vx = (float)(v*Math.cos(angle));
        float vy = (float)(v*Math.sin(angle));
        doShoot(ox, oy, vx, vy);       
    }
    /**
     * ��ը��
     */
    public void doBomb(int x, int y){
        Bomb bomb = new Bomb(m_game, m_game.loadImage("image/bomb.png"),
                   x, y, 32, 48, 0, 8);
        m_game.getMap().addBullet(bomb);                        
    } 
    /**
     * ��������
     */    
    public void die(){
        super.die();
        if (m_isSwording){
            m_game.getMap().removeBullet(m_sword);
            m_sword = null;
        }
    }
    
    public void setDest(int x, int width){
        m_destRangeStart = x - width / 2;
        m_destRangeEnd   = x + width / 2;
    }
    
    public boolean isMoving(){
        return (getX() < m_destRangeStart) || (getX() > m_destRangeEnd);
    }
    
    
    public void noDest(){
        m_destRangeStart = 0;
        m_destRangeEnd = Integer.MAX_VALUE;   
    }
}