/*MorsePlayerPackage
 * Package Description 
 */
package morseplayer;

/**
 * This is a description.
 *
 * @author bill
 */

/**
 * Class Descripition added right before class
 */
class MorsePlayer {

    /**
     * MorsePlayer Class is used to playback morse code. Strings and Characters
     * are played back at a speed determined in words per minute
     *
     * @param frequency is the frequency of the tone that is desired
     * @param wordsPerMinute is the speed in words per minute. Default is 15 WPM
     * @param farnsworthSpacing determines is farnsworth spacing will be used.
     * @param charToPlay is used to play a single character in morse code.
     * @param stringToPlay is used to play a word or sentence in morse code.
     *     
	 *
     */

    Sound_Timing soundDurations = new Sound_Timing(12, 6, false);
    MorseDictionary DecodeDictionary = new MorseDictionary();

    private int frequency = 800;
    private int wordsPerMinute = 15;
    private boolean farnsworthSpacing = false;
    private char charToPlay;
    private String wordToPlay;

    MorsePlayer() {
        //Default Constructor
    }

    MorsePlayer(int WPM) {
        this.wordsPerMinute = WPM;
    }

    MorsePlayer(int WPM, boolean use_farnsworth, int freq_hz) {
        this.wordsPerMinute = WPM;
        this.farnsworthSpacing = use_farnsworth;
        this.frequency = freq_hz;
    }

    MorsePlayer(int WPM, double freq_hz) {
        this.frequency = (int) freq_hz;
        this.wordsPerMinute = WPM;
    }

    MorsePlayer(boolean use_farnsworth) {
        this.farnsworthSpacing = use_farnsworth;
    }

    private static void playDit() {
        System.out.println("Beep     - Dit beep");
        //TODO code to play dit sound
    }

    private static void playDah() {
        System.out.println("Beeeeeep - Dah beep");
        //TODO code to play dah sound
    }

    public void playChar(char characterToPlay) {
        //TODO for some reason, Dictionary was not working properly....may need '' instead of ""
        String morseString = DecodeDictionary.morseDictionary.get(characterToPlay);
        //String morseString = "...-.-";
        int index = 0;
        int stringLength = morseString.length();
        while (index < stringLength) {
            char morseElement = morseString.charAt(index);
            if (morseElement == '.') {
                playDit();
            } else if (morseElement == '-') {
                playDah();
            }
        }
    }

    public void playWord(String wordToPlay) {
        int stringLength = wordToPlay.length();
        String wordToPlayLowerCase = wordToPlay.toLowerCase();
        int index = 0;

        while (index < stringLength) {
            char xCharToPlay = wordToPlayLowerCase.charAt(index);
            playChar(xCharToPlay);
            System.out.println("===================");
            index++;
            //TODO remember to add code that accounts for spacing between words!
        }//TODO code to play string in morse	
    }

    public static void main(String[] args) {

    //String poop = DecodeDictionary.morseDictionary.get ("a");
//These are testing lines
/*
          
         */
//Testing lines end here.....
//TODO main code....initialize sounds (create waveforms)
    }
}
