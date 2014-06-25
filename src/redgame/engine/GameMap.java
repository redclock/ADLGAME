package redgame.engine;
/*
 * Player.java ���ߣ�Ҧ����
 *
 */
import java.awt.*;
import java.util.*;
import java.awt.event.*;

import redgame.util.*;
import redgame.obj.*;
import redgame.status.*;


/**
 * GameMap������Ϸ�ĵ�ͼ��ÿһ�ض���һ���µĵ�ͼ��
 * @author Ҧ����
 * @see MapFileReader
 * @see GameWorld
 */
public class GameMap {
    private int m_w;
    private int m_h;
    private int m_picw;
    private int m_pich;
    private int m_needPrize;
    private long m_counter;
    private String[] m_intro;
    private AbstractObject m_lastObject = null;
    
    private String m_name;
    
   
    private Vector m_actors = new Vector(10); 
    
    private Vector m_objs = new Vector(10); 

    private Vector m_statics = new Vector(10); 
    
    private Vector m_bullets = new Vector(10); 
    private GameWorld m_game;
    private Image m_bkImg;
    private Dimension m_bkSize;
    
    private String startScript;
    
    /**
     * �����ͼ
     * @param game ��Ϸ�������
     * @see MapFileReader
     * @see GameWorld
     */
        
    public GameMap(GameWorld game) {
        m_game = game;
    }

    /**
     * �õ���ͼ���
     */
    public int getWidth() {
        return m_w;
    }

    /**
     * �õ���ͼ�߶�
     */
    public int getHeight() {
        return m_h;
    }
    
    /**
     * ���õ�ͼ��С
     */
    public void setSize(int w, int h){
        m_w = w; m_h = h;
    }
    
    /**
     * �õ���ͼ��
     */

    public String getName(){
        return m_name;
    }
    
    /**
     * ���õ�ͼ��
     */
    public void setName(String name){
        m_name = name;
    }
   
    /**
     * ���õ�ͼ����
     */
    public void setBkImg(Image img) {
        m_bkImg = img;
        m_picw = m_bkImg.getWidth(m_game.getPanel());
        m_pich = m_bkImg.getHeight(m_game.getPanel());
        m_bkSize = new Dimension(m_picw, m_pich);
        if (m_picw <=0 || m_pich <=0) m_bkImg = null;
    }

    /**
     * ���õ�ͼ����, ���Ϊ"<none>"���ʾ����Ϊ��
     */
    public void setBkImg(String filename) {
        if (filename.equals("<none>")) {
            m_bkImg = null;
            return;
        }
        m_bkImg = m_game.getImageManager().loadAutomaticImage(
            filename, Transparency.OPAQUE, m_bkSize);
        setBkImg(m_bkImg);
        m_game.deleteImage(m_bkImg);        
    }

    /**
     * ���õ�ͼ������С
     */
    public void setBkSize(int w, int h){
        m_bkSize = new Dimension(w, h);
    }
    /**
     * ȡ�ÿ�ʼʱ�Ľű� 
     * @return ��ʼʱ�Ľű���
     */
    public String getStartScript() {
    	return startScript;
    }
    /**
     * ���ÿ�ʼʱ�Ľű� 
     * @param name �ű���
     */
    public void setStartScript(String name) {
    	startScript = name;
    }
    /**
     * ���ùؿ����
     */
    public void setIntro(String[] intro){
        m_intro = intro;
    }
    /**
     * �õ��ؿ����
     */
    public String[] getIntro(){
        if (m_intro == null){
            m_intro = new String[1];
            m_intro[0] = "����û�н���";
        }
        return m_intro;
    }
    
    /**
     * ȡ�ü�ʱ��
     */
    public long getCounter(){
        return m_counter;
    }
    /**
     * ����Ƿ�Ӵ����˾�̬����
     * @param obj ����������
     * @return ����Ӵ����˾�̬���壬���ؾ�̬����ʵ�������򷵻�null
     */
    public StaticObject reachStatic(AbstractObject obj){
        for (int i = 0; i < m_statics.size(); i ++){
            StaticObject s = (StaticObject)m_statics.get(i);
            if (s.getVisible() && obj.checkCollision(s))
                return s;
        }
        return null;
    }
    
    /**
     * �������
     */
    public void addActor(Actor act){
        m_actors.add(act);
    }

    /**
     * ����������
     */
    public void addPlayer(Player player){
        m_actors.insertElementAt(player, 0);
    }

    /**
     * ��Ӿ�̬����
     */
    public void addStatic(StaticObject s){
        m_statics.add(s);
        if (s instanceof Bonus) m_needPrize++;
    }

    /**
     * ��ӵ�ͼԪ�أ���ǽ������
     */
    public void addMapObject(MapObject mobj){
        m_objs.add(mobj);
    }
    
    /**
     * ����ӵ�
     */
    public void addBullet(MovableObject s){
        m_bullets.add(s);
    }
   /**
     * ɾ��ָ����һ���ӵ�
     */
    public void removeBullet(AbstractObject b){
        for (int i = 0; i < m_bullets.size(); i++)
        {
            if (b == m_bullets.get(i)){
                m_bullets.remove(i);
            }
        }
    }

    private float checkLeft(Vector v, AbstractObject obj, float min){
        for (int i = 0; i < v.size(); i ++){
            AbstractObject obj2 = (AbstractObject)v.get(i);
            if (obj == obj2 || !obj2.isBlocked()) continue; 
            
            if ( obj2.getY() < obj.getY()+obj.getH()
                && obj2.getY()+obj2.getH() > obj.getY()
                ){
                float nlen = obj.getX() - obj2.getX() - obj2.getW();
                if (nlen >= 0 && nlen < min){
                    m_lastObject = obj2;
                    min = nlen;
                }
            }
        }
        return min;
    } 
    
    private float checkRight(Vector v, AbstractObject obj, float min){
        for (int i = 0; i < v.size(); i ++){
            AbstractObject obj2 = (AbstractObject)v.get(i);
            if (obj == obj2 || !obj2.isBlocked()) continue; 
            
            if (obj2.isBlocked() && obj2.getY() < obj.getY()+obj.getH()
                && obj2.getY()+obj2.getH() > obj.getY()){
                float nlen = obj2.getX() - obj.getX() - obj.getW();
                if (nlen >= 0 && nlen < min){
                    m_lastObject = obj2;
                    min = nlen;
                }
            }
        }
        return min;
    } 
    private float checkUp(Vector v, AbstractObject obj, float min){
        for (int i = 0; i < v.size(); i ++){
            AbstractObject obj2 = (AbstractObject)v.get(i);
            if (obj == obj2 || !obj2.isBlocked()) continue; 
            
            if (obj2.getX() < obj.getX()+obj.getW()
                && obj2.getX()+obj2.getW() > obj.getX()){
                float nlen = obj.getY() - obj2.getY() - obj2.getH();
                if (nlen >= 0 && nlen < min){
                    m_lastObject = obj2;
                    min = nlen;
                }
            }
        }
        return min;
    } 
    private float checkDown(Vector v, AbstractObject obj, float min){
        for (int i = 0; i < v.size(); i ++){
            AbstractObject obj2 = (AbstractObject)v.get(i);
            if (obj == obj2 || !obj2.isBlocked()) continue; 
            
            if (obj2.getX() < obj.getX()+obj.getW()
                && obj2.getX()+obj2.getW() > obj.getX()){
                float nlen = obj2.getY() - obj.getY() - obj.getH();
                if (nlen >= 0 && nlen < min){
                    m_lastObject = obj2;
                    min = nlen;
                }
            }
        }
        return min;
    } 
   /**
    * һ������Ҫ������һ�����룬Ҫ���������ײ�����������������巢�ͱ�����Ϣ
    * @see AbstractObject#collision
    * @param obj ����
    * @param len ����ϣ���ߵľ���
    * @return ������ײ��ԭ�򣬶���������ߵľ���
    */
    public float gotoLeft(AbstractObject obj, float len) {
        float min = obj.getX();
        m_lastObject = null;
        if (obj.isBlocked()){
            min = checkLeft(m_actors, obj, min);
            min = checkLeft(m_objs, obj, min);
            min = checkLeft(m_bullets, obj, min);
        }
        if (min < len && m_lastObject != null){
            m_lastObject.collision(obj, AbstractObject.G_LEFT);
        }
        return (min < len)? min : len;
    }

   /**
    * һ������Ҫ������һ�����룬Ҫ���������ײ�����������������巢�ͱ�����Ϣ
    * @see AbstractObject#collision
    * @param obj ����
    * @param len ����ϣ���ߵľ���
    * @return ������ײ��ԭ�򣬶���������ߵľ���
    */
    public float gotoRight(AbstractObject obj, float len) {
        float min = m_w - obj.getX() - obj.getW();
        m_lastObject = null;
        if (obj.isBlocked()){
            min = checkRight(m_actors, obj, min);
            min = checkRight(m_objs, obj, min);
            min = checkRight(m_bullets, obj, min);
        }
        if (min < len && m_lastObject != null){
            m_lastObject.collision(obj, AbstractObject.G_RIGHT);
        }
        return (min < len)? min : len;
    }

   /**
    * һ������Ҫ������һ�����룬Ҫ���������ײ�����������������巢�ͱ�����Ϣ
    * ������һ���ص㣬û�б߽磬����������ܵ���Ļ֮��
    * @see AbstractObject#collision
    * @param obj ����
    * @param len ����ϣ���ߵľ���
    * @return ������ײ��ԭ�򣬶���������ߵľ���
    */
   public float gotoUp(AbstractObject obj, float len) {
        //�������⴦��û�б߽�
        float min = len;
        //float min = obj.getY(); //���Ϊ�б߽�
        m_lastObject = null;
        
        if (obj.isBlocked()){
            min = checkUp(m_actors, obj, min);
            min = checkUp(m_objs, obj, min);
            min = checkUp(m_bullets, obj, min);
        }
        
        if (min < len && m_lastObject != null){
            m_lastObject.collision(obj, AbstractObject.G_UP);
        }
        return (min < len)? min : len;
    }

   /**
    * һ������Ҫ������һ�����룬Ҫ���������ײ�����������������巢�ͱ�����Ϣ
    * @see AbstractObject#collision
    * @param obj ����
    * @param len ����ϣ���ߵľ���
    * @return ������ײ��ԭ�򣬶���������ߵľ���
    */
    public float gotoDown(AbstractObject obj, float len) {
        float min = m_h - obj.getY() - obj.getH();
        m_lastObject = null;
        if (obj.isBlocked()){
            min = checkDown(m_actors, obj, min);
            min = checkDown(m_objs, obj, min);
            min = checkDown(m_bullets, obj, min);
        }
        if (min < len && m_lastObject != null){
            m_lastObject.collision(obj, AbstractObject.G_DOWN);
        }
        
        return (min < len)? min : len;
    }
   /**
    * ���嵱ǰ����λ���Ƿ������������ 
    * @param obj ����
    */
    public boolean canClimb(AbstractObject obj){
        float mid = obj.getX() + obj.getW() * 0.5f;
        float miny = obj.getY(), maxy = obj.getY() + obj.getH();
        for (int i = 0; i < m_objs.size(); i++){
            MapObject o = (MapObject)m_objs.get(i);
            if ( o.isClimbable() && 
                Math.abs(o.getX()+o.getW()*0.5f - mid)<= o.getW()*0.5f - 5
                && maxy > o.getY() && miny < o.getY() + o.getH())
                return true; 
        }
        return false;
    }

   /**
    * ����ͼ�����������
    */
   public void paint(Graphics g) {
        if (m_bkImg == null){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, m_w, m_h);
        }else {
            Rectangle sa = m_game.getScreenArea();
            int x, y;
            if (m_w == sa.getWidth())
                x = 0;
            else    
                x = (int) (sa.getX() * (m_w - m_bkSize.getWidth()) / (m_w - sa.getWidth()));
            if (m_h == sa.getHeight())
                y = 0;
            else
                y = (int) (sa.getY() * ( m_h - m_bkSize.getHeight()) / (m_h - sa.getHeight()));
            g.drawImage(m_bkImg, 
                        x, y,
                        x + (int) m_bkSize.getWidth(), y + (int) m_bkSize.getHeight(), 
                        0, 0, m_picw, m_pich, 
                        m_game.getPanel()
                        );
        }
        for (int i = 0; i < m_objs.size(); i++){
            ((MapObject)m_objs.get(i)).paint(g);            
        }
        for (int i = 0; i < m_statics.size(); i++){
            ((StaticObject)m_statics.get(i)).paint(g);          
        }

        for (int i = 0; i < m_actors.size(); i++){
            ((Actor)m_actors.get(i)).paint(g);          
        }
        for (int i = 0; i < m_bullets.size(); i++){
            ((MovableObject)m_bullets.get(i)).paint(g);          
        }

    }

    /**
     * ���¼�ʱ��
     * @param passedTime �ϴε��õ�������õĺ�����
     */
    public void move(long passedTime){
        for (int i = 0; i < m_statics.size(); i++){
            ((StaticObject)m_statics.get(i)).move(passedTime);          
        }
        for (int i = 0; i < m_objs.size(); i++){
            ((MapObject)m_objs.get(i)).move(passedTime);          
        }
    }
    /**
     * ����״̬
     * @param passedTime �ϴε��õ�������õĺ�����
     */

    public void update(long passedTime){
        m_counter += passedTime;
        for (int i = 0; i < m_actors.size(); i++){
            ((Actor)m_actors.get(i)).update(passedTime);            
        }
        for (int i = 0; i < m_bullets.size(); i++){
            ((MovableObject)m_bullets.get(i)).update(passedTime);            
        }
        if (KeyManager.isKeyJustDown(KeyEvent.VK_ESCAPE)){
            m_game.pushStatus(new MenuStatus(m_game));
        }
    }
    /**
     * ʹ�������ﶯ����
     * @param passedTime �ϴε��õ�������õĺ�����
     */
    public void moveActors(long passedTime){
        for (int i = 0; i < m_actors.size(); i++){
            ((Actor)m_actors.get(i)).move(passedTime);          
        }
        for (int i = 0; i < m_bullets.size(); i++){
            ((MovableObject)m_bullets.get(i)).move(passedTime);          
        }
    }
    
    public Vector getActors(){
        return m_actors;
    }
    
    public Vector getMapObjs(){
        return m_objs;
    }

    public Vector getWeapons(){
        return m_bullets;    
    }
    
    public Vector getStatics(){
        return m_statics;
    }

    private AbstractObject findObject(Vector v, String name){
        for (Object x: v){
            AbstractObject a = (AbstractObject) x;
            String n = a.getName();
            if (n != null && n.equals(name)){
                return a;
            }
        }
        return null;
    }
    
    public Actor findActor(String name){
        return (Actor) findObject(m_actors, name);
    }
    public MapObject findMapObject(String name){
        return (MapObject) findObject(m_objs, name);
    }
    public StaticObject findStatic(String name){
        return (StaticObject) findObject(m_statics, name);
    }
}
