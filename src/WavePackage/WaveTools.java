/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WavePackage;
//import java.io.*;
import java.io.*;
import javax.sound.sampled.*;
import java.math.BigDecimal;
//import WavePackage.*;

/**
 * WaveTools library for working with wave files and to use in MorsePlayer library
 * @author brian_000
 */
public class WaveTools {

   // WavePackage.WaveHeader();
   // public static WaveHeader CreateHeader;
    
    public double sampleRate = 44100;
public int channels = 1; //currently only supports on channel. Maybe Later 
public double volume = 27040; // just set a volume for now.
    private static BigDecimal truncateDecimal(double x,int numberofDecimals)
{
    if ( x > 0) {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
    } else {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
    }
}
    // remove static after testing!
    /**
     * Generates a sine wave based on 3 simple parameters.
     * @param freq  The desired frequency of the tone
     * @param msDuration The total duration of the tone in milli seconds.
     * @param msRamp The time in milliseconds of the desired volume ramp.
     * @return AudioInputStream
     */
    public static AudioInputStream createSineWave_PCM (int freq, int msDuration, int msRamp)
    {
//TODO check to make sure variables are now correct
final   double  TAU  = 2 * Math.PI;
int     msTrailingSilence = 0;
int     formatChunkSize  = 16;
int     headerSize  = 8;
short   formatType = 1;
short   tracks  = 1; //MONO Only for now!
int     samplesPerSecond = 44100;
short   bitsPerSample  = 16;
short   frameSize = 2;  //' manual input of 2 because it should be right! CShort(tracks * ((bitsPerSample + 7) \ 8))
int     bytesPerSecond  = samplesPerSecond * frameSize;
int     waveSize  = 2; // change from 4 to 2
int     total_samples  = (msDuration) * (samplesPerSecond/1000);// 'removed /1000 from both
double      rampSamples  = samplesPerSecond  * msRamp * 0.001; //'number of samples for ramp
double      full_amplitude_samples = total_samples - (rampSamples * 2);// 'number of samples at full amplitude
double      silenceSamples = samplesPerSecond * msTrailingSilence * 0.001;
int     dataChunkSize  = (int)(total_samples * 2) + (int)(silenceSamples * 2) ;//'frameSize --- changed 1 to 2 5/28/14
int     fileSize = 36 + dataChunkSize ;//'waveSize + headerSize + formatChunkSize + headerSize + dataChunkSize
//this is a test to write to the stream!


//TODO code for creating a sine wave and storing in a stream
//TODO  This code below converts a byte array to an input Stream.  SIMPLE!
return null;
    }
    public AudioInputStream createHahnWindow (int durationMilliSecs, boolean atBegining, boolean atEnding)
    {
        //TODO code will be changed to byte[] instead of AudioInputStream!
        //TODO code for modifiying existing values in a wave/PCM for a hahn window
    return  null;
    }

    
   public byte[] createSinePCM (short freq, short duration_ms, short ramp_ms, short sampleRate)
   //@TODO all these parameters should be changed to doubles.
   {
       byte [] bytePCMsine = null;
        //TODO finish createSinePCM
       return bytePCMsine;
   
   }
   private static byte [] ShortToByte_Twiddle_Method(short [] input)
           //http://stackoverflow.com/questions/10804852/how-to-convert-short-array-to-byte-array
{
  int short_index, byte_index;
  int iterations = input.length;

  byte [] buffer = new byte[input.length * 2];

  short_index = byte_index = 0;

  for(/*NOP*/; short_index != iterations; /*NOP*/)
  {
    buffer[byte_index]     = (byte) (input[short_index] & 0x00FF); 
    buffer[byte_index + 1] = (byte) ((input[short_index] & 0xFF00) >> 8);

    ++short_index; byte_index += 2;
  }

  return buffer;
}
   /**
    * 
    * @param duration_ms
    * @param sampleRate
    * @return 
    */
   public static OutputStream createSilenceWave  (int duration_ms, int sampleRate)
   {
       byte[] pcmWave = createSilencePCM(duration_ms, sampleRate);
       InputStream pcmDataStream = new ByteArrayInputStream(pcmWave);
       int pcmLength = pcmWave.length;
       
       WaveHeader CreateHeader = new WaveHeader ((short)1,(short)1, sampleRate, (short)16, (short)pcmLength);
       OutputStream header;     //initialize OutputStream
       header = new ByteArrayOutputStream(44);

       try{
       int error = CreateHeader.write(header);
          }
       catch (IOException e)
       {
           //TODO code to catch Exception
       }
       
       //Code to combine both header and PCM data into one
       ByteArrayOutputStream baosPCMWave = new ByteArrayOutputStream(pcmWave.length);
       baosPCMWave.write(pcmWave, 0, pcmWave.length);
       try{
       header.write(baosPCMWave.toByteArray());
       }
       catch (IOException e)
       {
           //TODO IOException code
       }
       
       return header;
       }
       
       
       
       
   
   public static byte[] createSilencePCM (int duration_ms, int sampleRate)
   {
       
       byte [] bytePCMsilence = new byte[(duration_ms/1000 * sampleRate)*2];
       short [] shortPCMsilence = new short[(duration_ms/1000 * sampleRate)];
  
       for (int slice =0; slice < (sampleRate/(duration_ms*0.01)); slice++)
        {
            shortPCMsilence [slice] = (short)0 ;
        }
       bytePCMsilence = ShortToByte_Twiddle_Method(shortPCMsilence);
       
       return bytePCMsilence;
   }
   public static byte [] createTestPCM (double duration, double samplerate, double freq)
   {
      //@TODO Implement Volume as percentage! 100% = 32567 and so on....
       //@TODO implement method of Twiddle Method straight into conversion for 
        //     improved speed.  Current profile speed is 0.280 to 0.320 milliseconds
       
       byte [] pcmTEST;
       double arraySize = ((duration/1000)*samplerate);
       short [] pcmShortArray = new short[(int)arraySize];
       //short slice;
       double TAU = Math.PI * 2;
       double theta = freq * TAU / (samplerate); 
       
       for (int step = 0; step < arraySize; step++)
        {
            short slice  =  (short)((24867 * Math.sin(theta * step))); //24867 is volume.  Volume is between 0 and 32???(32000 ish)
            pcmShortArray[step] = slice;
            System.out.println ("STEP : " + step + "  SLICE : " + slice + "//");
       //TODO add code to twiddle in here for improved speed
        }
       pcmTEST = ShortToByte_Twiddle_Method(pcmShortArray); 
       return pcmTEST;
   }
    /**
     * @param args No command line arguments available in class.
     */
    public static void main(String[] args) 
    {
    // TODO code application logic here
        byte[] pooptest=createSilencePCM(1000, 44100);
        System.out.println(pooptest.length);
        byte [] otherpoop;   
        otherpoop = createTestPCM(100, 44100, 800);
        System.out.println("Length of Fake Wave PCM : " +  otherpoop.length);
        short length = (short)otherpoop.length;
            //This line properly intializes the new class! YAY!
        WaveHeader CreateHeader = new WaveHeader((short)1,(short) 1, 44100, (short)16, (length));
     OutputStream header;
     header = new ByteArrayOutputStream(44);
     //ByteArrayOutputStream header = new ByteArrayOutputStream();
       // header = new OutputStream(ByteArrayOutputStream(CreateHeader.write(header)));
        
     try{
         int error = CreateHeader.write(header);
     }
     catch (IOException e){}
     
      //  CreatHeader is null for some reason.?
        /*    
        CreateHeader.setBitsPerSample((short)16);
            CreateHeader.setFormat((short)1);
            CreateHeader.setNumChannels((short)1);
            CreateHeader.setSampleRate(44100);
            CreateHeader.setNumBytes((int)length);
        */
        OutputStream craptester; 
        craptester = createSilenceWave(1000, 44100);
        System.out.println(CreateHeader.toString());
        //TODO write simple code to output ByteArrayStream to file.  Import into Audacity and see how wave looks!
    }
    
    }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//////END OF JAVA CODE!//////////////////////END OF JAVA CODE!//////////////////
/*WORKING CODE FROM MY VB.NET PROJECT
Function createWave(ByRef genStream As MemoryStream, ByVal frequency As Double, ByVal msDuration As Integer, _
Optional ByVal msTrailingSilence As Integer = 0, Optional msRamp As Integer = 4, Optional ByVal volume As UInt16 = 16383) ' 16383
' createWave generates a sine wave in the form of a memory stream to be passed to windows.media.player
' frequency is frequency in Hertz, msDuration is tone duration in milliseconds, msRamp is the beginning and ending
' volume ramp (5ms is standard CW)
'set variables
Dim writer As New BinaryWriter(genStream)
Dim TAU As Double = 2 * Math.PI
Dim formatChunkSize As Integer = 16
Dim headerSize As Integer = 8
Dim formatType As Short = 1
Dim tracks As Short = 1
Dim samplesPerSecond As Integer = 44100
Dim bitsPerSample As Short = 16
Dim frameSize As Short = 2 ' manual input of 2 because it should be right! CShort(tracks * ((bitsPerSample + 7) \ 8))
Dim bytesPerSecond As Integer = samplesPerSecond * frameSize
Dim waveSize As Integer = 2 ' change from 4 to 2
Dim total_samples As Integer = CInt(Math.Truncate(CType(samplesPerSecond, [Decimal]) * CDbl(msDuration * 0.001))) 'removed /1000 from both
Dim rampSamples As Integer = CInt(Math.Truncate(CType(samplesPerSecond, [Decimal]) * msRamp * 0.001)) 'number of samples for ramp
Dim full_amplitude_samples As Integer = total_samples - (rampSamples * 2) 'number of samples at full amplitude
Dim silenceSamples As Integer = CInt(Math.Truncate(CType(samplesPerSecond, [Decimal]) * CDbl(msTrailingSilence * 0.001)))
Dim dataChunkSize As Integer = (total_samples * 2) + (silenceSamples * 2) 'frameSize --- changed 1 to 2 5/28/14
Dim fileSize As Integer = 36 + dataChunkSize 'waveSize + headerSize + formatChunkSize + headerSize + dataChunkSize
' var encoding = new System.Text.UTF8Encoding();
writer.Write(&H46464952) ' = encoding.GetBytes("RIFF")
writer.Write(fileSize)
writer.Write(&H45564157) ' = encoding.GetBytes("WAVE")
writer.Write(&H20746D66) ' = encoding.GetBytes("fmt ")
writer.Write(formatChunkSize) ' = 16 for 'PCM'
writer.Write(formatType) ' 1 for 'PCM'
writer.Write(tracks) ' number of channels
writer.Write(samplesPerSecond) ' sample rate
writer.Write(bytesPerSecond) ' ByteRate == SampleRate * NumChannels * BitsPerSample/8
writer.Write(frameSize) ' AKA 'Block Align' according to https://ccrma.stanford.edu/courses/422/projects/WaveFormat/
writer.Write(bitsPerSample) ' 8 bit or 16 bit sound sample etc....
writer.Write(&H61746164) ' = encoding.GetBytes("data")
writer.Write(dataChunkSize) ' == NumSamples * NumChannels * BitsPerSample/8
' removed -1 from samples per second. CDbl(samplesPerSecond -1)
' 'volume' is UInt16 with range 0 thru Uint16.MaxValue ( = 65 535)
' we need 'amp' to have the range of 0 thru Int16.MaxValue ( = 32 767)
Dim amp As Double = volume >> 2 ' so we simply set amp = volume / 2
Dim rampAmp As Double = 0
'create amplification ramp of wave for number of ramp samples (duration of msRamp)
For [step] As Integer = 0 To rampSamples
rampAmp = CDbl([step]) / CDbl(rampSamples)
Dim s As Short = CShort(((amp) * Math.Sin(theta * CDbl([step]))))
s = s * rampAmp
writer.Write(s)
Next [step]
'amp = volume >> 2 uncommented on 5/30/14 should be OKAY
' create regular amplitude wave for full duration minus beginning and ending ramp
For [step] As Integer = (rampSamples + 1) To (full_amplitude_samples + rampSamples)
Dim s As Short = CDbl((amp * Math.Sin(theta * CDbl([step]))))
writer.Write(s)
Next [step]
'create ending ramp amplfication from full volume to 0
For [step] As Integer = (rampSamples + full_amplitude_samples + 1) To total_samples + 1
rampAmp = (total_samples + 1 - [step]) / CDbl(rampSamples)
Dim s As Short = CShort(((amp) * Math.Sin(theta * CDbl([step]))))
s = CShort(s * rampAmp)
writer.Write(s)
Next [step]
'routine to add trailing silence to waves for cleaner sound. Ex. Incorporate interspace at end of dit or dah
' instead of separate interspace wave....interspace wave may be causing clicks and pops in audio playback.
If msTrailingSilence <> 0 Then
For [step] As Integer = 0 To silenceSamples
writer.Write(0)
Next
End If
If genStream.Length Mod 2 <> 0 Then writer.Write(0) ' add extra sample to keep samples even
Return genStream
End Function
*/