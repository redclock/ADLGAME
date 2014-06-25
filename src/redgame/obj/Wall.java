package redgame.obj;
/*
 * Wall.java ���ߣ�Ҧ����
 */
 import java.awt.*;
 import redgame.engine.*;
/**
 * Wall���ǵ�ͼԪ��ǽ����������ײ��������
 * @author Ҧ����
 */
 public class Wall extends MapObject {
    private boolean m_bad;
    /**
     * ����ǽ
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
    public Wall(GameWorld game, Image img, 
					int x, int y, int w, int h,
	 				int tilew, int tileh, boolean bad) {
		super(game, img, x, y, w, h, tilew, tileh);
		m_bad = bad;
		m_blocked = true;
	}
    /**
     * ���ؼ�
     */
	public boolean isClimbable(){
		return false;
	}

    public boolean collision(AbstractObject obj, int direction){
        if (m_bad){
            if ( obj instanceof Player ){
                ((Player)obj).hurt(1, 1000);
                return true;
            }
        }
        //ִ�нű�
        if (m_script != null && obj instanceof Player){
            m_game.getScript().add(m_script, 
                                    new ScriptSource(this, obj, direction));
        }

        return false;
    }

}
