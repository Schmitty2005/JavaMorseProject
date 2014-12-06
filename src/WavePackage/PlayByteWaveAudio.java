/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WavePackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bill
 */
public class PlayByteWaveAudio {

    public PlayByteWaveAudio() {
        System.err.println("Need Byte Array with wave file data! ");
    }

    public PlayByteWaveAudio(byte[] playWave) {
        
            InputStream byteArray = new ByteArrayInputStream(playWave);

            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(byteArray);
                Clip clip = AudioSystem.getClip();

                clip.open(ais);
                        clip.setFramePosition(0);
                clip.start();
                System.out.println("Clip Length" + clip.getBufferSize());
                if (clip.isActive()){System.out.println("ACTIVE");}
                
                clip.drain();
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                System.out.println(e);
            }
        
    }
    
    }

