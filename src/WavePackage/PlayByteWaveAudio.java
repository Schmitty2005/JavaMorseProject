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

/**
 *
 * @author bill
 */
public class PlayByteWaveAudio {

    public PlayByteWaveAudio() {
        System.err.println("Need Byte Array with wave file data! ");
    }

    public  PlayByteWaveAudio(byte[] playWave) {
        InputStream byteArray = new ByteArrayInputStream(playWave);
        System.out.println("ACCESSING WAVE BYTE PLAYER");
        

    //AudioSystem.write(ais, Type.WAVE, NewfilePath);
        try {
AudioInputStream ais = AudioSystem.getAudioInputStream(byteArray);
            Clip clip = AudioSystem.getClip();

            if (ais == null){}
            //This works but needs to sync with the rest of the program! WTF!
            //ais is not created quick enough!
            clip.open(ais);
            clip.start();
            clip.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println(e);
        }
        catch (UnsupportedAudioFileException e) {System.out.println(e);}
    }

}
