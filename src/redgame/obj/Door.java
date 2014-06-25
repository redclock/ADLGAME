package redgame.obj;
/*
 * Door.java ���ߣ�Ҧ����
 */
import java.awt.*;
import redgame.scripts.AbstractScript;
import redgame.engine.*;
/**
 * Door���Ǿ�ֹ������.
 * @author Ҧ����
 */
public class Door extends StaticObject {
	//�Ƿ��
	private boolean m_open;
	public String destMap;
	public int destX, destY;
	/**
	 * ��ӦԿ�׵�id
	 */
	public int id; 
    /**
     * ����Door
     * @param game ��Ϸ�������
     * @param img ����ͼ��
     * @param x ����λ�ú�����
     * @param y ����λ��������
     * @param w Ԫ�ؿ��
     * @param h Ԫ�ظ߶�
     */
    public Door(GameWorld game, Image img, 
					int x, int y, int w, int h, int id) {
		super(game, img, x, y, w, h, 0, 0);
		//��ֹ
		m_anim.setLooped(false);
		m_open = false;
		this.id = id;
		Player p = game.getPlayer();
		//�����ǰ�Ϳ�����
		if (p != null){
			if (p.openedDoor[id]) open();
		}
	}
	/**
	 * ����
	 */
	public void open(){
		if (!m_open){
			m_anim.setRange(0, 3, 100);
			m_anim.start();
			m_open = true;
		}
	}

    /**
     * ����
     */
    public void close(){
        if (m_open){
            m_anim.setRange(0, 3, 100);
            m_anim.resetToStart();
            m_anim.stop();
            m_open = false;
        }
    }

	/**
	 * �Ƿ����
	 */
	public boolean isOpen(){
		return m_open;
	}
	/**
	 * ������
	 */
	public void enter(Player p){
        p.setVisible(false);
        if ("_save_door".equals(m_name)){
            m_game.gotoMap(AbstractScript.getStrVar("_save_out_map"),
                           AbstractScript.getIntVar("_save_out_x"),
                           AbstractScript.getIntVar("_save_out_y"));
            
        }else{
            m_game.gotoMap(destMap, destX, destY);
        }
        if ( m_script != null  ){
            m_game.getScript().add(m_script, 
                        new ScriptSource(this, p, G_UP));
        }
        
	}	
}
