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


/**
 * 
 * @author bill
 */
public class PlayByteWaveAudio {
/**
 * 
 * @param playWave 
 */
    public PlayByteWaveAudio(byte[] playWave) {

        InputStream byteArray = new ByteArrayInputStream(playWave);

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(byteArray);
            Clip clip = AudioSystem.getClip();

            clip.open(ais);

            clip.start();

            clip.drain();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println(e);
        }

    }

}
