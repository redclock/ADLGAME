package redgame.status;
/*
 * StageStatus.java ���ߣ�Ҧ����
 */

import java.awt.*;
import java.awt.event.*;
import redgame.engine.*;
import redgame.ui.*;
import redgame.util.*;
/**
 * StageStatus������ʾ�͸��µ�ͼ����Ϸ״̬.
 * @see AbstractStatus
 * @author Ҧ����
 */
public class StageStatus extends AbstractStatus {
    //״̬��
    private StateBar m_bar;
    private String m_mapName;
    private int m_x, m_y;

    /**
     * ����һ��StageStatus
     * @param game ��Ϸ����
     * @param mapName Ҫװ�صĵ�ͼ
     * @param x ���˹�����λ��
     * @param y ���˹�����λ��
     */
    public StageStatus(GameWorld game, String mapName, int x, int y) {
        super(game);
        m_bar = new StateBar(game);
        m_mapName = mapName;
        m_x = x;
        m_y = y;
    }
    public void setPrior(AbstractStatus prior){
        m_prior = null;
    }
    public int start(){
        m_game.loadMap(m_mapName);
        m_game.getPlayer().setPosition(m_x, m_y);
        if (m_game.getMap().getStartScript() != null) {
        	m_game.getScript().add(m_game.getMap().getStartScript(), null);
        	
        }
        return 0;
    }
    /**
     * ����״̬, ���������ֹͣ
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */
    public int update(long passedTime){
        m_game.getScript().update();
        m_game.getMap().update(passedTime);
        m_game.getMap().moveActors(passedTime);
        if (KeyManager.isKeyDown(KeyEvent.VK_W))
            m_game.pushStatus(new SelectWeaponStatus(m_game));
        return 0;
    }

    /**
     * ��ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    public int draw(long passedTime, Graphics g){
        m_game.getMap().paint(g);
        m_bar.draw(g);
        return 0;
    }
    /**
     * ���¼�ʱ��
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */

    public int move(long passedTime) {
        m_game.getMap().move(passedTime);
        m_bar.move(passedTime);
        return 0;
    }
}