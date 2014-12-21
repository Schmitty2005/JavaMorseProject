/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

/**
 *
 * @author bill
 */
public class AndroidMorse {

    MorseContainer mContainer = new MorseContainer();

    public void setString(String mStringToPlay) {
        mContainer.stringToPlay = mStringToPlay;
        MorseWave mw = new MorseWave(mContainer);

    }

    AndroidMorse(int WPM, String stringToAudio) {
        mContainer.mWPM = WPM;
        mContainer.stringToPlay = stringToAudio;
        MorseWave mw = new MorseWave(mContainer);

    }

    AndroidMorse(int WPM, boolean FarnsworthEnabled, int FarnsWorthWPM, String stringToAudio) {
        mContainer.mWPM = WPM;
        mContainer.mFarnsEnabled = FarnsworthEnabled;
        mContainer.mFarnsWPM = FarnsWorthWPM;
        mContainer.stringToPlay = stringToAudio;
        MorseWave mw = new MorseWave(mContainer);

    }

}
