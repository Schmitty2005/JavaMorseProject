/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;
// To change sample rate, you must change MorseElements.java PlayMorse.java and WaveTools.java to the same rate.  Currently at 16000 for android!
//import WavePackage.PlayByteWaveAudio;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PlayMorse is used to translate a string of characters into morse code.
 *
 * @author bill
 */
public class PlayMorse {

    int mWPM;
    //Sound_Timing timing = new Sound_Timing(18);
    private MorseElements elements = new MorseElements(32, 12, false, 800);
   final private MorseDictionary morseDict = new MorseDictionary();

    /**
     * PlayMorse wpm.
     *
     * @param mWPM WPM for the morse code speed.
     */
    public PlayMorse(int mWPM) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 13, false, 800);
    }

    ;
  
    /**
     * Initialize sounds for morse code playback.
     * @param mWPM WPM of desired code playback.
     * @param freq_hz desired frequency of morse code.
     */
    PlayMorse(int mWPM, int wpmFarns) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, wpmFarns, false, 800);
    }

    ;
  
    PlayMorse(int mWPM, boolean farnsworthSpacing) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 13, farnsworthSpacing, 800); // 13 can be changed to default value later mDefaulFarnsWPM = 13
    }

    ;
  
    PlayMorse(int mWPM, int wpmFarns, boolean use_farnsworth, String playString) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, wpmFarns, use_farnsworth, 800);
        synchronized (this) {
            playString(playString);
        }
        //@TODO call routine to play string in morse here!

    }
    
    PlayMorse(int mWPM, int wpmFarns, boolean use_farnsworth, int freqHz,  String playString) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, wpmFarns, use_farnsworth, freqHz);
        synchronized (this) {
            playString(playString);
        }
        //@TODO call routine to play string in morse here!

    }

    ;
  
    PlayMorse(String playString) {
//@TODO call routine to play string in morse here!
    }

    ;
    PlayMorse(int mWPM, String mplayString) {
        //Sound_Timing timing = new Sound_Timing(mWPM);
        this.elements = new MorseElements(mWPM, 15, false, 800);
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

                }
                if (ditOrDah == '-') {
                    try {
                        bbout.write(WavePackage.WaveTools.combineByteArray(playWave, this.elements.dahElementPCM));
                    } catch (IOException e) {
                        System.err.println(e);
                    }

                }

            }

        }

        playWave = bbout.toByteArray();
        byte[] header = WavePackage.WaveTools.createWaveHeaderForPcm(playWave, 16000, (short) 16);

        WavePackage.WaveTools.saveToWaveFile(header, "firstTest.wav");

        WavePackage.PlayByteWaveAudio player = new WavePackage.PlayByteWaveAudio(header);

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
        PlayMorse morstest = new PlayMorse(45, 25, false, "Andrea is calling");
        System.out.println("Waiting for enter.....");
        
        
        PlayMorse othertest = new PlayMorse (18, 10, true, "Ke7gbt cq cq cq de ke7gbt");
        
        PlayMorse newTester = new PlayMorse (52, 12, false, 1500, "THIS is a test of morse");
        PlayMorse abnewTester = new PlayMorse (23, 12, false, 400, "this is another test");
        PlayMorse bnewTester = new PlayMorse (31, 12, false, 834, "how many can play at once");
        PlayMorse cnewTester = new PlayMorse (18, 12, false, 1032, "another bunch of morse code");
        
        
        try {
            System.in.read();

            //morstest.playString("ke7gbt cq cq cq ke7gbt dx de ke7gbt cq cq cq");
        } catch (IOException ex) {
            Logger.getLogger(PlayMorse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
