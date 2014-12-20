/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

/**
 * This class contains all the necessary information to pass to the PlayMorse
 * class and some subroutines.
 *
 * @author bill
 */
public class MorseContainer {

    protected int mWPM = 18;
    protected int mFreq = 800;
    protected boolean mFarnsEnabled = false;
    protected int mFarnsWPM = 12;
    public String stringToPlay = "";
    public byte[] waveByteArray;
    public int repeats = 1;

    public void playContainer() {
        PlayMorse mtp = new PlayMorse(this);

    }

}
