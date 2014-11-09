/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wavetools;
//import java.io.*;
import javax.sound.sampled.*;

/**
 *
 * @author Brian S
 */
public class WaveTools {
public double sampleRate = 44100;
public int channels = 1; //currently only supports on channel. Maybe Later 
public double volume = 27040; // just set a volume for now.
    
    public AudioInputStream createSineWave (int freq, int durationMilliSecs){
    AudioInputStream  modifiedStream = null;
    //TODO code for creating a sine wave and storing in a stream
        return modifiedStream;
    }
    public AudioInputStream createHahnWindow (int durationMilliSecs, boolean atBegining, boolean atEnding)
    {
        AudioInputStream modifiedStream;
        //TODO code for modifiying existing values in a wave/PCM for a hahn window
    return modifiedStream = null;
    }
    /**
     * @param args No command line arguments available in class.
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
