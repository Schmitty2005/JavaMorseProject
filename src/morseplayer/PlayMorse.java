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
    private MorseElements elements = new MorseElements(32, 12, false);
    private MorseDictionary morseDict = new MorseDictionary();

    /**
     * PlayMorse wpm.
     *
     * @param mWPM WPM for the morse code speed.
     */
    public PlayMorse(int mWPM) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 13, false);
    }

    ;
  
    /**
     * Initialize sounds for morse code playback.
     * @param mWPM WPM of desired code playback.
     * @param freq_hz desired frequency of morse code.
     */
    PlayMorse(int mWPM, int wpmFarns) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, wpmFarns, false);
    }

    ;
  
    PlayMorse(int mWPM, boolean farnsworthSpacing) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 13, farnsworthSpacing); // 13 can be changed to default value later mDefaulFarnsWPM = 13
    }

    ;
  
    PlayMorse(int mWPM, int wpmFarns, boolean use_farnsworth, String playString) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, wpmFarns, use_farnsworth);
        playString(playString);
        //@TODO call routine to play string in morse here!

    }

    ;
  
    PlayMorse(String playString) {
//@TODO call routine to play string in morse here!
    }

    ;
    PlayMorse(int mWPM, String mplayString) {
        //Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 15, false);
        playString(mplayString);
        //@TODO call routine to play string in morse here!
    }

    ;
private void playString(String playString) {
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
                bbout.write(WavePackage.WaveTools.combineByteArray(playWave, this.elements.interWordSpacing), 0, elements.interWordSpacing.length);

            } else {
                if (this.elements.farnsworthSpacing) {
//@TODO code to insert farnsworth spaced silence
                    bbout.write(WavePackage.WaveTools.combineByteArray(playWave, this.elements.interCharacterFarnsworthPCM), 0, elements.interCharacterFarnsworthPCM.length);
                } else {
                    try {
                        bbout.write(WavePackage.WaveTools.combineByteArray(playWave, this.elements.interCharacterPCM));
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

                        bbout.write(WavePackage.WaveTools.combineByteArray(playWave, this.elements.ditElementPCM));
                    } catch (IOException e) {
                        System.err.println(e);
                    }

                    //playWave = constructionOne;
//@TODO code to place dit in playWave
                }
                if (ditOrDah == '-') {
                    try {
                        bbout.write(WavePackage.WaveTools.combineByteArray(playWave, this.elements.dahElementPCM));
                    } catch (IOException e) {
                        System.err.println(e);
                    }
//playWave = constructionOne;
//@TODO code to place dah in playWave
                }

            }
            try {
                bbout.write(this.elements.interCharacterPCM);
            } catch (IOException e) {
                System.err.println(e);
            }

//@TODO code to combine dit, dah, and spacing PCM's into new byte array
        }

        playWave = bbout.toByteArray();
        byte[] header = WavePackage.WaveTools.createWaveHeaderForPcm(playWave, 44100, (short) 16);
        //byte [] saveWave = WavePackage.WaveTools.combineByteArray(header, playWave);
        WavePackage.WaveTools.saveToWaveFile(header, "firstTest.wav");
        WavePackage.WaveTools.saveToWaveFile(this.elements.ditElementPCM, "ditELementPCM.pcm");
        WavePackage.WaveTools.saveToWaveFile(this.elements.dahElementPCM, "dahElementPCM.pcm");

    }

    private static void printElementInfo() {
        /*
         Sound_Timing testTime = new Sound_Timing(24, 12, true);
        
         System.out.println("Dit Spacing        : " + testTime.dit_length);
         System.out.println("Dah Spacing        : "+ testTime.dah_length);
         System.out.println("Element Spacing    : "+ testTime.interElementSpacing);
         System.out.println("Farnswooth Boolean : "+ testTime.farnsworthSpacing);
         System.out.println("Farnsworth WPM     : "+testTime.farnsworthWPM);
         System.out.println("Farnsworth spacing : "+testTime.interCharacterSpacing_farnsworth);
         System.out.println("Regular Spacing    : "+testTime.interCharacterSpacing_normal);
         System.out.println("Word Spacing       : "+testTime.interWordSpacing);
         */
    }

    public static void main(String[] args) {
        //printElementInfo();
        PlayMorse morstest = new PlayMorse(24, 12, false, "andrea is calling");
        //morstest.playString("ke7gbt cq cq cq ke7gbt dx de ke7gbt cq cq cq");
    }

}
