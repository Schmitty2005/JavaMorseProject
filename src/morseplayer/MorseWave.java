/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *MorseWave class is made for getting the byte array wave of a programmed string.
 * 
 *      This can be used for android applications.  It is to get a byte array wave file of the desired string.
 * This will allow for easy recall and playback in an android application.
 *
 * @author bill
 */
public class MorseWave {

    int mWPM;
    //Sound_Timing timing = new Sound_Timing(18);
    private MorseElements elements = new MorseElements(32, 12, false, 800);
    final private MorseDictionary morseDict = new MorseDictionary();

    public MorseWave(MorseContainer morse_container) {
        //TODO add code to translate container into playback!
        Sound_Timing timing = new Sound_Timing(morse_container.mWPM);
        this.elements = new MorseElements(morse_container.mWPM, morse_container.mFarnsWPM, morse_container.mFarnsEnabled, morse_container.mFreq);
        morse_container.waveByteArray = createMorseWave(morse_container.stringToPlay);

    }

    private byte[] createMorseWave(String playString) {
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

        return header;

        //WavePackage.WaveTools.saveToWaveFile(header, "firstTest.wav");
        //WavePackage.PlayByteWaveAudio player = new WavePackage.PlayByteWaveAudio(header);
    }

    public static void main(String[] args) {
        
        MorseContainer mc = new MorseContainer();
        mc.mFarnsEnabled = false;
        mc.stringToPlay = "TEST TEST CQ CQ CQ TEST TEST TEST CQ CQ CQ";
        mc.mWPM = 25;
        mc.mFarnsWPM = 12;
        mc.mFreq = 400;

        mc.playContainer();
        
        MorseWave wave = new MorseWave(mc);

        try {
            Thread.sleep((long) 1000);
            //mc.playContainer();
        } catch (InterruptedException ex) {
            Logger.getLogger(MorseWave.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
