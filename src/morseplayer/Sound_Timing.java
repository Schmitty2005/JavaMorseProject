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
	 // function used to calculate values of Sound_Timing class/enum
	 private double calculateSpacing (int WPM){
             double calculated = 0;
//TODO code to calulcate spacing based on WPM
            return calculated;
         
	 }
}

