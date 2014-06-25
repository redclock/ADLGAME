package redgame.anim;
/*
 * AbstractParticleSystem.java ���ߣ�Ҧ����
 */
import java.util.Vector;
import java.awt.*;


import redgame.engine.*;

/**
 * AbstractParticleSystem������Ϸ������ϵͳ�ĸ��࣬����������ܡ�
 * ����һ�������࣬Ҫʹ�����������ࡣ
 * @author Ҧ����
 */
abstract public class AbstractParticleSystem{
	//�����б�
	protected Vector<Particle> m_p;
	//��Ϸ����
	protected GameWorld m_game;
    /**
     * ��������ϵͳ
     * @param game ��Ϸ�������
     * @param image ����ͼ��
     * @param count ���Ӹ���
     * @param pw ͼ��һ��Ŀ��
     * @param ph ͼ��һ��ĸ߶�
     * @param begin ͼƬ�ָ���ʼ������
     * @param end   ͼƬ�ָ�����������
     * @param delay ÿһ֡���ӳ�
     */

	public AbstractParticleSystem(GameWorld game, Image image, 
					int count, int pw, int ph, int begin, 
					int end, int delay){
		m_p = new Vector<Particle>(count);
		for (int i = 0; i < count; i++){
			Particle p = new Particle(game, image, pw, ph);
			m_p.add(p);
			p.setRange(begin, end, delay);
		}
		m_game = game;
	}
	/**
	 * ������������
	 */
	public void resetall(){
		for (int i = 0; i < m_p.size(); i++){
			reset(i);
		}
	}
	/**
	 * ���õ�i������
	 */
	abstract protected void reset(int i);
	/**
	 * ������
	 */
	public void paint(Graphics g){
		
		for (int i = 0; i < m_p.size(); i++){
			m_p.get(i).paint(g);
		}
	}
	/**
	 * ��������
	 */
	public void move(long passedTime){
		for (int i = 0; i < m_p.size(); i++){
			Particle p = m_p.get(i);
			p.move(passedTime);
			if (p.life <= 0) reset(i);
		}
	}
	
}

