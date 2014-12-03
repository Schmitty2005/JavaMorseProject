/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * PlayMorse is used to translate a string of characters into morse code.
 *
 * @author bill
 */
public class PlayMorse {

    int mWPM;
    //Sound_Timing timing = new Sound_Timing(18);
    private MorseElements elements = new MorseElements(32, 800, false);
    private MorseDictionary morseDict = new MorseDictionary();

    /**
     * PlayMorse wpm.
     *
     * @param mWPM WPM for the morse code speed.
     */
    public PlayMorse(int mWPM) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 800, false);
    }

    ;
  
    /**
     * Initialize sounds for morse code playback.
     * @param mWPM WPM of desired code playback.
     * @param freq_hz desired frequency of morse code.
     */
    PlayMorse(int mWPM, int freq_hz) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, freq_hz, false);
    }

    ;
  
    PlayMorse(int mWPM, int freq_hz, boolean farnsworthSpacing) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 800, farnsworthSpacing);
    }

    ;
  
    PlayMorse(int mWPM, int freq_hz, boolean farnsworthSpacing, String playString) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, freq_hz, farnsworthSpacing);
        //@TODO call routine to play string in morse here!

    }

    ;
  
    PlayMorse(String playString) {
//@TODO call routine to play string in morse here!
    }

    ;
    PlayMorse(int mWPM, String mplayString) {
        //Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 500, false);
        playString(mplayString);
        //@TODO call routine to play string in morse here!
    }

    ;
public void playString(String playString) {
//@TODO code to play string in morse :)
        //@TODO NEEDS ByteArrayOutpuStream to work!
        ByteArrayOutputStream bbout = new ByteArrayOutputStream();

        char charToPlay;
        int sLength = playString.length();
        byte[] playWave = new byte[32];
        //byte[]  constructionOne;
        //ByteBuffer bb = ByteBuffer.wrap(playWave);
        for (int step = 0; step < sLength; step++) {
            charToPlay = playString.charAt(step);
            if (charToPlay == ' ') {
                if (elements.farnsworthSpacing) {
//@TODO code to insert farnsworth spaced silence
                    bbout.write(WavePackage.WaveTools.combineByteArray(playWave, elements.interCharacterFarnsworthPCM), 0, elements.interCharacterFarnsworthPCM.length);
                } else {
                    try {
                        bbout.write(WavePackage.WaveTools.combineByteArray(playWave, elements.interCharacterPCM));
                    } catch (IOException e) {
                        System.err.println("e");
                    }

                    //playWave = constructionOne;
                }
            }
            char toLower = Character.toLowerCase(charToPlay);
            String morseString = morseDict.morseDictionary.get(toLower);

            for (int x = 0; x < (morseString.length()); x++) {
                char ditOrDah = morseString.charAt(x);
                if (ditOrDah == '.') {
                    try {

                        bbout.write(WavePackage.WaveTools.combineByteArray(playWave, elements.ditElementPCM));
                    } catch (IOException e) {
                        System.err.println(e);
                    }

                    //playWave = constructionOne;
//@TODO code to place dit in playWave
                }
                if (ditOrDah == '-') {
                    try {
                        bbout.write(WavePackage.WaveTools.combineByteArray(playWave, elements.dahElementPCM));
                    } catch (IOException e) {
                        System.err.println(e);
                    }
//playWave = constructionOne;
//@TODO code to place dah in playWave
                }

            }
            try {
                bbout.write(elements.interCharacterPCM);
            } catch (IOException e) {
                System.err.println(e);
            }

//@TODO code to combine dit, dah, and spacing PCM's into new byte array
        }

        playWave = bbout.toByteArray();
        byte[] header = WavePackage.WaveTools.createWaveHeaderForPcm(playWave, 44100, (short) 16);
        //byte [] saveWave = WavePackage.WaveTools.combineByteArray(header, playWave);
        WavePackage.WaveTools.saveToWaveFile(header, "firstTest.wav");
        WavePackage.WaveTools.saveToWaveFile(elements.ditElementPCM, "ditELementPCM.pcm");
        WavePackage.WaveTools.saveToWaveFile(elements.dahElementPCM, "dahElementPCM.pcm");

    }

    public static void main(String[] args) {
        PlayMorse morstest = new PlayMorse(38, "wkrp cq cq cq de wkrp");
        //morstest.playString("ke7gbt cq cq cq ke7gbt dx de ke7gbt cq cq cq");
    }
}
