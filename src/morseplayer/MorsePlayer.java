/*MorsePlayerPackage
 * Package Description 
 */
package morseplayer;


/**
 *This is a description.
 * @author bill
 */
import javax.sound.sampled.*;
import java.io.*;
import jdk.nashorn.internal.codegen.CompilerConstants;
import sun.font.TrueTypeFont;


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

/**Class Descripition added right before class
 */
class MorsePlayer {
/**MorsePlayer Class is used to playback morse code.  
 * Strings and Characters are played back at a speed determined in words per minute
 * 
 * @param frequency is the frequency of the tone that is desired
 * @param wordsPerMinute is the speed in words per minute.  Default is 15 WPM
 * @param farnsworthSpacing determines is farnsworth spacing will be used.
 * @param charToPlay is used to play a single character in morse code.
 * @param stringToPlay is used to play a word or sentence in morse code.
 * 
* 
*/
 class elementSpacing extends Sound_Timing{

        public elementSpacing(int wpm, int farnsWPM, boolean use_farnsworth) {
            super(wpm, farnsWPM, use_farnsworth);
        }
 
 };



    private int frequency = 800;
	private int wordsPerMinute = 15;
	private boolean farnsworthSpacing = false;
	private char charToPlay;
	private String wordToPlay;
	
	
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
	private void playDit ()
        {
            System.out.println("Beep    -   Dit beep");
            //TODO code to play dit sound
	}
	private void playDah ()
        {
            System.out.println("Beeeeeep - Dah beep");
            //TODO code to play dah sound
	}
	public void playChar (char characterToPlay){
	
//TODO code to play morse character
	}
	public void playWord (String WordtoPlay){
		//TODO code to play string in morse	
	}
      public static void main (String [] args){
//This line below properly initialised a new class of Sound_Timing! YAY!            
Sound_Timing soundDurations = new Sound_Timing(12, 6, false);
//These are testing lines
double poop = soundDurations.dit_length;
double crap = soundDurations.dah_length;
double wpm = soundDurations.interWordSpacing;
boolean farnsBool = soundDurations.farnsworthSpacing;
double bullshit = soundDurations.farnsworthWPM;
    
          
//Testing lines end here.....
//TODO main code....initialize sounds (create waveforms)
	}  
}
	
