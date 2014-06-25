package redgame.scripts;
/**
 * ÿ���ű���һ���߳�,������ʱ���̵߳ȴ�,���߳�����ʱ�ű��ȴ�
 */
public abstract class ThreadScript extends AbstractScript implements Runnable{
    //��ű�ǿ�ƽ������쳣
    protected class ThreadTerminated extends Exception{}
    //�Ƿ����ڵȴ�
    private boolean m_suspended = true;
    //�Ƿ��Ѿ�ֹͣ
    private boolean m_stopped = false;
    //�Ƿ�������
    private boolean m_sleeping = false;
    //�߳�
    private Thread m_thread;
    
    /**
     * �Ƿ����ڵȴ�
     */
    public synchronized boolean isSuspended(){
        return m_suspended;
    }
    
    /**
     * �Ƿ���������
     */
    public synchronized boolean isSleeping(){
        return m_sleeping;
    }

    /**
     * �ӵȴ��лظ�
     */
    public synchronized void spResume(){
        m_suspended = false;
    }
    /**
     * ����ȴ�
     */
    public synchronized void spSuspend(){
        m_suspended = true;
    }
    /**
     * ��ʼ�ű�
     */
    public void start(){
        m_thread = new Thread(this);
        m_thread.start();
        //�ȴ��߳̿�ʼ
        while (!m_running && m_thread.isAlive()){
        }
    }
    /**
     * ֹͣ�߳�
     */
    public synchronized void stop(){
        m_stopped = true;    
    }
    
    /**
     * ִ��
     */
    public int update(){
       if (! m_sleeping ){
            m_suspended = false;
            //�ȴ�
            while( !m_suspended && m_thread.isAlive()){
            }
        }
        return 0;
    } 

    protected void unlock() throws ThreadTerminated{
        spSuspend();
        while (m_suspended){
            if ( m_stopped ){
                throw new ThreadTerminated();
            }
        }
    }
    /**
     * ����Runnable��run()
     */
    public void run(){
        m_running = true;
        try{
            runScript();
        }catch(ThreadTerminated e){
        }    
        m_running = false;
    }
    /**
     * �ű�����
     */
    protected abstract void runScript() throws ThreadTerminated;
    
    protected void waitTime(long m) throws ThreadTerminated{
        m_sleeping = true;
        m_suspended = true;
        try{
            Thread.sleep(m);
        }catch(InterruptedException e){
            e.printStackTrace();
        }    
        m_sleeping = false;
        unlock();
    }
}
