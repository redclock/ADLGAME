package redgame.engine;
/*
 * StatusStack.java ���ߣ�Ҧ����
 */

import java.awt.*;
import java.util.*;
import redgame.status.*;
/**
 * AbstractStatus����ʢ����Ϸ״̬��ջ.
 * ����״̬������Ϸ״̬ջ��,ջ��״̬Ϊ��ǰ�״̬.
 * @see AbstractStatus
 * @author Ҧ����
 */

public class StatusStack{
	//��Ϸ����
	private GameWorld m_game = null;
	//ϵͳջ
    private Stack<AbstractStatus> m_stack = new Stack<AbstractStatus>();
	
    /**
     * ����һ��״̬ջ
     * @param game ��Ϸ����
     */
    public StatusStack(GameWorld game){
		m_game = game;
	}
	/**
	 * �õ���ǰ״̬
	 */
    public AbstractStatus getCurrStatus(){
		if (m_stack.isEmpty())
			return null;
		else
			return m_stack.peek();
	}
	/**
	 * ���ջ
	 */
	public void clear(){
		m_stack.clear();
	}
    /**
     * ѹ��һ��״̬
     */
	public int push(AbstractStatus s){
		s.setPrior(getCurrStatus());
		m_stack.push(s);
        System.out.println("push "+s.getClass()+" "+m_stack.size());
		return m_stack.size() - 1;
	} 
	/**
	 * ����״̬
	 */
	public AbstractStatus pop(){
        System.out.println("pop "+m_stack.size());
        if (m_stack.isEmpty())
			return null;
		else
			return m_stack.pop();
	}
	
	/**
	 * ����״̬, ���ջ����draw, move, update����ֵ�ĺͲ�Ϊ0,�򵯳���״̬
	 */
	public void updateStatus(long passedTime, Graphics g){
		AbstractStatus s = getCurrStatus();
		if (s == null) return;
		s.updatePrior(passedTime, g);
		int r = s.draw(passedTime, g);
        r += s.move(passedTime);
        r += s.update(passedTime);
		if (r != 0){
            System.out.println("pop "+m_stack.size());
            m_stack.pop();
		}
	}
	/**
	 * ֻ�������n��,����ɾ��
	 */
	public void deleteUntil(int n){
	   while ( m_stack.size() > n ){
	       m_stack.remove(0);
	   }
	}
}