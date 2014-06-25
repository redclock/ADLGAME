package redgame.status;
/*
 * AbstractStatus.java ���ߣ�Ҧ����
 */
import redgame.engine.*;

/**
 * AbstractStatus����"��Ϸ״̬"�Ļ���."��Ϸ״̬"Ϊ��Ϸ��ǰ����ʲô.
 * ����״̬������Ϸ״̬ջ��,ջ��״̬Ϊ��ǰ�״̬.
 * ����һ�������࣬Ҫʹ������������
 * @see StatusStack
 * @author Ҧ����
 */
abstract public class AbstractStatus {
	//��Ϸ����
	GameWorld m_game = null;
	//��ʱ��
	long m_counter = 0;
	//״̬ջ�е�ǰһ��״̬
	protected AbstractStatus m_prior;
	/**
     * ����һ��AbstractStatus
     * @param game ��Ϸ����
     */
	public AbstractStatus(GameWorld game) {
		m_game = game;
	}
	/**
	 * ����ǰһ��״̬
	 */
	public void setPrior(AbstractStatus prior){
		m_prior = prior;
	}
	/**
	 * ����ǰһ��״̬,Ĭ����ΪΪ����draw,move,��������update
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
	 */
	public void updatePrior(long passedTime, java.awt.Graphics g){
		if (m_prior != null) {
			m_prior.updatePrior(passedTime, g);
			m_prior.draw(passedTime, g);
			m_prior.move(passedTime);
//			m_prior.update(passedTime);
		}
	}
	public int start(){
	   return 0;
	}
	/**
	 * �����������, �������״̬����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */
	public abstract int update(long passedTime);


    /**
     * �����������, ���뻭ͼ����
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     * @param g          ������ͼ������ 
     */
    public abstract int draw(long passedTime, java.awt.Graphics g);

    /**
     * ���¼�ʱ��
     * @param passedTime ����һ�ε��õ����ڵ�ʱ��
     */
    public int move(long passedTime) {
		m_counter += passedTime;
		return 0;
	}	
}
