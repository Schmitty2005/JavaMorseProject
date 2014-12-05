/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

/*Sound_Timing class is used to calculate in milli-seconds the spacing used for dit's, dah's, inter-element,
 * and inter-character spacing
 *
 * @author
 * Brian S.
 */
public class Sound_Timing {

    public double dit_length;
    public double dah_length;
    public double interElementSpacing;
    public double interCharacterSpacing_normal;
    public double interCharacterSpacing_farnsworth;
    public double interWordSpacing;
    public boolean farnsworthSpacing ;//= false;
    public double farnsworthWPM = 5;

    // function used to calculate values of Sound_Timing class/enum
    /**
     *
     * @param wpm wpm is speed in words per minute
     */
    Sound_Timing(int wpm) {
        this.calculateSpacing(wpm);
    }

    Sound_Timing() {
        this.calculateSpacing(18);
    }

    /**
     *
     * @param wpm using words per minute
     * @param use_farnsworth boolean to use or not use farnsworth spacing
     */
    Sound_Timing(int wpm, boolean use_farnsworth) {
        this.farnsworthSpacing = use_farnsworth;
        this.calculateSpacing(wpm);
    }

    Sound_Timing(int wpm, int farnsWPM, boolean use_farnsworth) {
        this.farnsworthSpacing = use_farnsworth;
        this.farnsworthWPM = farnsWPM;
        this.calculateSpacing(wpm);
    }

    private void calculateSpacing(int wpm) {
        //routine to calc WPM dit and dah lengths
        this.dit_length = 1200 / wpm;
        this.dah_length = dit_length * 3;
        this.interElementSpacing = dit_length;
        this.interWordSpacing = dit_length * 7;
        this.interCharacterSpacing_normal = (dah_length - dit_length);
        this.interCharacterSpacing_farnsworth = (1200 / farnsworthWPM) * 7;

    }
}
