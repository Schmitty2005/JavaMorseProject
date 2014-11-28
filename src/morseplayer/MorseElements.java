/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

import WavePackage.WaveTools;

/**
 * This class contains all the variables for a morse element.
 *
 * @author bill
 */
public class MorseElements {

    int wordsPerMinute = 18;
    short freqInHz = 800;
    boolean farnsworthSpacing = false;
    int sample_rate = 44100;
    public byte[] ditElementPCM;
    public byte[] dahElementPCM;
    public byte[] interCharacterPCM;
    byte[] constructionOnePCM;
    byte[] constructionTwoPCM;
    public byte[] interCharacterFarnsworthPCM;

    public MorseElements() {
    }
    
    class ElementInfo {
    short freq;
    int sample_rate;
    short duration_ms;
    
    
    };

    
    public MorseElements(int wordsPerMinute, int freqInHz, boolean farnsworthSpacing) {

        Sound_Timing timing = new Sound_Timing(wordsPerMinute, freqInHz, farnsworthSpacing);

        constructionOnePCM = WavePackage.WaveTools.createSinePCM((short) freqInHz, (short) timing.dit_length, (short) 0, sample_rate);
        WaveTools.createHannWindow(constructionOnePCM, 0.05F, sample_rate);
        constructionTwoPCM = WavePackage.WaveTools.createSilencePCM(timing.interCharacterSpacing_farnsworth, sample_rate);
        ditElementPCM = WavePackage.WaveTools.combineByteArray(constructionOnePCM, constructionTwoPCM);

        constructionOnePCM = WavePackage.WaveTools.createSinePCM((short) freqInHz, (short) timing.dah_length, (short) 0, sample_rate);
        WaveTools.createHannWindow(constructionOnePCM, 0.05F, sample_rate);
        
        constructionTwoPCM = WavePackage.WaveTools.createSilencePCM(timing.interElementSpacing, sample_rate);
        dahElementPCM = WavePackage.WaveTools.combineByteArray(constructionOnePCM, constructionTwoPCM);

        interCharacterPCM = WavePackage.WaveTools.createSilencePCM(timing.interCharacterSpacing_normal, sample_rate);

        interCharacterFarnsworthPCM = WavePackage.WaveTools.createSilencePCM(timing.interCharacterSpacing_farnsworth, sample_rate);

    }

}
