/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

import morseplayer.MorseDictionary;

/**
 *
 * @author bill
 */
public class PlayMorse {

    int mWPM;
    Sound_Timing timing = new Sound_Timing(18);
    MorseElements elements = new MorseElements(18, 800, false);
    private MorseDictionary morseDict;

    public PlayMorse(int mWPM) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        MorseElements elements = new MorseElements(mWPM, 800, false);
    }

    ;
  
    PlayMorse(int WPM, int freq_hz) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        MorseElements elements = new MorseElements(mWPM, freq_hz, false);
    }

    ;
  
    PlayMorse(int WPM, int freq_hz, boolean farnsworthSpacing) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        MorseElements elements = new MorseElements(mWPM, 800, farnsworthSpacing);
    }

    ;
  
    PlayMorse(int WPM, int freq_hz, boolean farnsworthSpacing, String playString) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        MorseElements elements = new MorseElements(mWPM, freq_hz, farnsworthSpacing);
        //@TODO call routine to play string in morse here!

    }

    ;
  
    PlayMorse(String playString) {
//@TODO call routine to play string in morse here!
    }

    ;
    PlayMorse(int WPM, String playString) {
        Sound_Timing timing = new Sound_Timing(mWPM);
        MorseElements elements = new MorseElements(mWPM, 800, false);
        //@TODO call routine to play string in morse here!
    }

    ;
public void playString(String playString) {
//@TODO code to play string in morse :)
        int sLength = playString.length();
        byte[] playWave;

        for (int step = 0; step < sLength; step++) {
            char charToPlay = playString.charAt(step);
            if (charToPlay == ' ') {
                if (true) {//true is just a placeholder!
                    //@TODO code to insert farnsworth spaced silence
                } else {
                    //@TODO code to insert regular word space byte array
                }
                
                String morseString = morseDict.morseDictionary.get(charToPlay);
                
                for (int x = 0; x < (morseString.length()); x++) {
                    char ditOrDah = morseString.charAt(x);
                    if (ditOrDah == '.') {

                        //@TODO code to place dit in playWave
                    }
                    if (ditOrDah == '-') {
                        //@TODO code to place dah in playWave
                    }

                }

//@TODO code to combine dit, dah, and spacing PCM's into new byte array
            }
        }

}

}
