/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

import WavePackage.WaveTools;

/**
 * This class contains all the PCM data for all the morse elements. The dit and
 * dah waves are created using the constructor. A user may set the frequency in
 * hertz, the desired words per minute, and whether or not farnsworth spacing is
 * to be enabled for the elements.
 *
 * MorseElement.ditElementPCM - a PCM sine wave dit MorseElement.dahElementPCM -
 * a PCM sine wave dah MorseElement.interCharacterPCM - a silent wave the length
 * of space between morse characters. MorseElement.interCharacterFarnsworthPCM -
 * a silent wave the length of space between morse characters in farnsworth
 * spacing
 *
 * @author bill
 */
public class MorseElements {

    public boolean farnsworthSpacing;// = false;
    public byte[] ditElementPCM;
    public byte[] dahElementPCM;
    public byte[] interCharacterPCM;
    public byte[] interCharacterFarnsworthPCM;
    public byte[] interWordSpacing;

    private int mWordsPerMinute = 18;
    private short mfreqInHz = 500;
    private int sample_rate = 44100;
    private int mFarnsWPM = 12;
    private byte[] constructionOnePCM;
    private byte[] constructionTwoPCM;

    public void setToneFrequency(int new_freq_hz) {
        this.mfreqInHz = (short) new_freq_hz;
       //MorseElements this  = new MorseElements(mWordsPerMinute, mFarnsWPM, farnsworthSpacing);
    }

    public short getToneFrequency() {
        return this.mfreqInHz;
    }

    public MorseElements() {
    }

    /**
     * Creates PCM wave data and sound timing information for speed(WPM) and
     * frequency.
     *
     * @param wordsPerMinute The desired speed of the morse code in words per
     * minute
     * @param freqInHz The desired frequency of the cw tone in hertz.
     * @param farnsworthSpacing True or False? Farnsworth spacing enabled?
     */
    public MorseElements(int wordsPerMinute, int farnsWPM, boolean boolSpacing, int freqHz) {

        this.mWordsPerMinute = wordsPerMinute;
        this.mFarnsWPM = farnsWPM;
        this.mfreqInHz = (short)freqHz;
//@TODO possibly make these multithreaded in the future!
        Sound_Timing timing = new Sound_Timing(wordsPerMinute, farnsWPM, boolSpacing);
        this.farnsworthSpacing = boolSpacing;

        constructionOnePCM = WavePackage.WaveTools.createSinePCM((short) mfreqInHz, (short) timing.dit_length, (short) 0, sample_rate);
        WaveTools.createHannWindow(constructionOnePCM, 0.004F, sample_rate);
        constructionTwoPCM = WavePackage.WaveTools.createSilencePCM(timing.interElementSpacing, sample_rate);
        ditElementPCM = WavePackage.WaveTools.combineByteArray(constructionOnePCM, constructionTwoPCM);

        constructionOnePCM = WavePackage.WaveTools.createSinePCM((short) mfreqInHz, (short) timing.dah_length, (short) 0, sample_rate);

        WaveTools.createHannWindow(constructionOnePCM, 0.004F, sample_rate);

        constructionTwoPCM = WavePackage.WaveTools.createSilencePCM(timing.interElementSpacing, sample_rate);
        dahElementPCM = WavePackage.WaveTools.combineByteArray(constructionOnePCM, constructionTwoPCM);

        interCharacterPCM = WavePackage.WaveTools.createSilencePCM(timing.interCharacterSpacing_normal, sample_rate);

        interCharacterFarnsworthPCM = WavePackage.WaveTools.createSilencePCM(timing.interCharacterSpacing_farnsworth, sample_rate);

        interWordSpacing = WavePackage.WaveTools.createSilencePCM(timing.interWordSpacing, sample_rate);

        //remeber to add code to dispose of unused byte arrays.
        System.gc();

    }

}
