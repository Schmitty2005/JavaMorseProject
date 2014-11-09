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
import javax.sound.sampled.*;
import java.io.*;


/* class Sound_Timing is used to hold variables for creation of morse
 * 
 * This class is basically an enum that holds the values assosiated with the timing of dit's and dah's
 *@author
 *Brian S.
 *@version 
 *Alpha
 */
class DecodeMorse extends MorseDictionary{}


class MorseTiming extends Sound_Timing{
	//TODO explanation is the class needs to be private for MorsePlayer.  No need to be public in Morse_Player class
	//Morse_Player class
	 
 }


class MorsePlayer {
	private int frequency = 800;
	private int wordsPerMinute = 15;
	private boolean farnsworthSpacing = false;
	private char charToPlay;
	private String wordToPlay = null;
	
	
	MorsePlayer (int WPM){
		this.wordsPerMinute = WPM;
	}
	
	MorsePlayer (int WPM, boolean use_farnsworth, int freq_hz){
		this.wordsPerMinute = WPM;
		this.farnsworthSpacing = use_farnsworth;
		this.frequency = freq_hz;
	}
	MorsePlayer (int WPM, double freq_hz){
		this.frequency = (int)freq_hz;
                this.wordsPerMinute = WPM;
	}
	MorsePlayer (boolean use_farnsworth){
		this.farnsworthSpacing = use_farnsworth;
	}
	public static void main (String [] args){
	//TODO main code....initialize sounds (create waveforms)
	}
	
	
	
	private void playDih (){
	//TODO code to play dih sound
	}
	private void playDah () {
		//TODO code to play dah sound
	}
	public void playChar (char characterToPlay){
	
//TODO code to play morse character
	}
	public void playWord (String WordtoPlay){
		//TODO code to play string in morse	
	}
}
	
