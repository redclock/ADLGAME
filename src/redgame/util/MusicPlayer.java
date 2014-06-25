package redgame.util;
/*
 * MusicPlayer.java
 * ���ļ��ĺ����㷨ժ��: http://www.gameres.com/bbs/showthread.asp?threadid=19304
 */

import java.io.*;
import javax.sound.sampled.*;


/**
 * MusicPlayer���ǲ�����������.
 * ���Զ����ļ���չ���ж���wav����midi
 * @see MidiPlayer
 */

public class MusicPlayer implements Runnable {
    //�����߳�
    private Thread musicThread;
    //midi������
    private MidiPlayer m_midi = null;           
    //�Ƿ�ѭ��
    private boolean m_looped = false;   
    //�Ƿ��Ѿ�ֹͣ        
    private boolean m_stopped = false; 
    //�ļ���         
    private String m_filename;
    //���ߺ���:�����չ��
    private String getExt(String filename){
        int i = filename.lastIndexOf('.');
        if (i<0) return "";
        else return filename.substring(i+1);
    }
    //ȡ����Ƶ����
    private byte[] getAudioSamples( AudioInputStream MusicStream, AudioFormat format )
    {
        int AudioSampleLengh = ( int )( MusicStream.getFrameLength() *
                                      format.getFrameSize() );
        byte aAudioSamples[] = new byte[ AudioSampleLengh ];
        DataInputStream dataInputStream = new DataInputStream( MusicStream );
  
        try{
            dataInputStream.readFully( aAudioSamples );
        }catch( Exception e ){
            e.printStackTrace();
        }
  
        return aAudioSamples;
    }
  
    //����au,aiff,wav������, �������������ȫΪ�����ϵĴ���
    private synchronized void play()
    {
          ByteArrayInputStream aMusicInputStream;
          AudioFormat format;
          AudioInputStream musicInputStream;
          byte[] audioSamples;
          SourceDataLine line;
          try {
              File MusicFile = new File(m_filename);
      
              musicInputStream
                 = AudioSystem.getAudioInputStream( MusicFile ); //ȡ���ļ�����Ƶ������
              format = musicInputStream.getFormat(); //ȡ����Ƶ�������ĸ�ʽ
              audioSamples = getAudioSamples( musicInputStream, format );//ȡ����Ƶ����
      
              aMusicInputStream
                       = new ByteArrayInputStream( audioSamples );
              int bufferSize = format.getFrameSize() *
                      Math.round(format.getSampleRate() / 10);
              byte[] buffer = new byte[bufferSize];
              try {
                  DataLine.Info info =
                      new DataLine.Info(SourceDataLine.class, format);
                  line = (SourceDataLine)AudioSystem.getLine(info);
                  line.open(format, bufferSize);
              }
              catch ( LineUnavailableException e ) {
                  e.printStackTrace();
                  return;
              }
      
              if( !line.isRunning() )
              {
                  line.start();
              }
      
             int numBytesRead = 0;
             while (numBytesRead != -1 && !m_stopped) {
                 numBytesRead =
                     aMusicInputStream.read(buffer, 0, buffer.length);
                 if (numBytesRead != -1) {
                    line.write(buffer, 0, numBytesRead);
                 }
             }
             line.drain();
             line.close();
          }catch(Exception e){
             e.printStackTrace();
          }
    }

    /**
     * ����һ��MusicPlayer
     * @param filename �ļ���
     * @param looped �Ƿ�ѭ��
     */
    public MusicPlayer(String filename, boolean looped){  
          m_looped = looped;                          
          m_stopped = false;  
          m_filename = filename;  
          //�����չ��Ϊmid�Ļ�...                      
          if (getExt(filename).equalsIgnoreCase("mid")){
              m_midi = new MidiPlayer();
          }
    }
    /**
     * ��ʼ�����߳�
     */
    public void start(){
        //midi��wav��Ҫ��ͬ�ķ���
        if (m_midi == null){
            musicThread = new Thread(this);
            musicThread.start();
            m_stopped = false;
        }else{
            m_midi.InitSequence(m_filename);
            m_midi.play(m_looped);
        }    
    }
    /**
     * ���������߳�
     */
    public void stop(){
        if (m_midi == null){
            musicThread.interrupt();
            musicThread = null;
            m_stopped = true; 
        }else{
            m_midi.stop();
            m_midi.close();
        }    
    }
    /**
     * ���������߳�
     */
    public void run(){
        //��ͣ����
        do{
            play();
        }while(m_looped && !m_stopped);       
    }
}

