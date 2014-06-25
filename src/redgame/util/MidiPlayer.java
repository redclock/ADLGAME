package redgame.util;
/*
 * MidiPlayer.java
 * ���ļ��ĺ����㷨ժ��: http://www.gameres.com/bbs/showthread.asp?threadid=19304
 */
import java.io.*;
import javax.sound.midi.*;

/**
 * MidiPlayer���ǲ���MIDI���ֵ���
 */

public class MidiPlayer implements MetaEventListener{

    //������
    private Sequence sequence;
    private Sequencer sequencer;

    private boolean isLoop   = false;

    private File MidiFile;

    private static final int END_OF_TRACK_MESSAGE = 47; //�ŵ���Ϣβ

    /**
     * ������
     * @param filename
     */
    public void InitSequence( String filename )
    {
        try{
            MidiFile = new File( filename );
            sequence = MidiSystem.getSequence( MidiFile ); //��MidiStream��ȡ������
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener( this );
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * ��������
     * @param isLoop �Ƿ�ѭ������
     */
    public void play( boolean isLoop )
    {
        if( sequencer != null && sequence != null && sequencer.isOpen() )
        {
            try {
                sequencer.setSequence( sequence );
                sequencer.start();
                this.isLoop = isLoop;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * ʵ��MetaEventListener�ӿ�
     */
    public void meta( MetaMessage metaMessage ) {
        //ѭ������
        if( metaMessage.getType() == END_OF_TRACK_MESSAGE )
        {
//            System.out.println("loop midi");
            if( sequencer != null && sequencer.isOpen() && isLoop )
            {
                sequencer.stop();
                try{
                    sequence = MidiSystem.getSequence( MidiFile ); //��MidiStream��ȡ������
                    sequencer = MidiSystem.getSequencer();
                    sequencer.open();
                    sequencer.addMetaEventListener( this );
                    play(true);
                }catch( Exception e ){
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * ֹͣ����
     */
    public void stop()
    {
        if( sequencer != null && sequencer.isOpen() )
        {
//            System.out.println("stop midi");
            sequencer.stop();
            isLoop = false;
        }
    }
    /**
     * �ر�����
     */
    public void close()
    {
        if( sequencer != null && sequencer.isOpen() )
        {
            sequencer.close();
        }
    }

}

