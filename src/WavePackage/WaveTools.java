/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WavePackage;

import java.io.*;
import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
//these imports are for file saving
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * WaveTools library for working with wave files and to use in MorsePlayer
 * library
 *
 * @author brian_000
 */
public class WaveTools {

    public static int sampleRate = 44100;
    public int channels = 1; //currently only supports on channel. Maybe Later 
    public double volume = 27040; // just set a volume for now.
/**
     * combineByteArray simply combines the first and the second byte array as
     * one.
     *
     * @param firstByte First byte array desired
     * @param secondByte Second byte array to follow first
     * @return Returns firstByte plus secondByte
     */
    public static byte[] combineByteArray(byte[] firstByte, byte[] secondByte) {
        byte[] combinedArray = new byte[(firstByte.length + secondByte.length)];
        ByteBuffer bb = ByteBuffer.wrap(combinedArray);
        
        bb.position(0);
        bb.put(firstByte);
        bb.put(secondByte);

        return combinedArray;
    }
    /**
     *
     * @param waveByteArray A byte array that you desire to save to a file.
     * @param filename The filename you wish the file to have.
     */
    public static void saveToWaveFile(byte[] waveByteArray, String filename) {
        FileOutputStream fop = null;
        File file;

        try {

            file = new File(filename);
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            fop.write(waveByteArray);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
            }
        }

    }

    class PcmHeader {

        int mSampleRate = 44100;
        short mChannels = 1;
        short mBitsPerSample = 16;
        String mWaveName;
        String mFileName = mWaveName + ".wav";
        //TODO this is for input into the createWaveHeaderForPcm sub!
        //TODO find out how to add a byte array to this !
    }

    /**
     * This method accepts a byte array and creates and attaches a wave header
     * to the data.
     *
     * @param pcmData Pure PCM data as a byte []
     * @param sampleRate Sample rate of PCM data. Usually 44100
     * @param bitsPerSample Bit rate of PCM data. Usually 16
     * @return Returns Byte Array with newly created wave header and PCM wave
     * data attached.
     */
    public static byte[] createWaveHeaderForPcm(byte[] pcmData, int sampleRate, short bitsPerSample) {
//TODO something isnt working right in this!        
//Initialize variables for wave header
        short numberChannels = 1;  // number of channels
        int byteRate = sampleRate * numberChannels * bitsPerSample / 8;
        short blockAlign = (short) (numberChannels * bitsPerSample / 8);
        int subChunk2Size = pcmData.length;
        //
        int pcmLength = pcmData.length;
        int waveLength = pcmData.length + 44;
        waveLength++;
        //conduct check of wave file length
        if (waveLength % 2 != 0) {
            waveLength++;
            pcmLength++;
            pcmLength++;
        }  //wave files must have an even number of bytes!
        System.out.println("waveLength Value : " + waveLength);
        ByteBuffer waveBuffer = ByteBuffer.allocate(pcmLength + 44);
        //byte[] completeWave = new byte[pcmLength + 44];
        waveBuffer.position(0);
        //Set to Big Endian for RIFF
        waveBuffer.order(ByteOrder.BIG_ENDIAN);
        //write ASCII 'RIFF' to Byte Buffer
        waveBuffer.put((byte) 0x52);
        waveBuffer.put((byte) 0x49);
        waveBuffer.put((byte) 0x46);
        waveBuffer.put((byte) 0x46);
        //Set to Little Endian for ChunkSize
        waveBuffer.order(ByteOrder.LITTLE_ENDIAN);
        //write Int Subchunk size
        waveBuffer.putInt(36 + subChunk2Size); //Should be calculated properly...This is jjust for testing
        //Set to Big Endian for WAVE
        waveBuffer.order(ByteOrder.BIG_ENDIAN);
        //write ASCII 'WAVE' to Byte Buffer
        //0x57415645 big-endian form
        byte[] asciiwave = {0x57, 0x41, 0x56, 0x45}; //wave in hex ASCII
        waveBuffer.put(asciiwave);
        byte[] asciifmt = {0x66, 0x6d, 0x74, 0x20};//  fmt in ASCII 0x666d7420 big-endian form).
        waveBuffer.put(asciifmt);
        //sub chunk size also bitrate = 16
        waveBuffer.order(ByteOrder.LITTLE_ENDIAN);
        waveBuffer.putInt(bitsPerSample);  //This is 16 for the bit-rate  ***WATCH FOR ERRORS HERE
        waveBuffer.putShort((short) 1);  //1 is for PCM 2   AudioFormat      PCM = 1 (i.e. Linear quantization)
        waveBuffer.putShort(numberChannels); //2   NumChannels      Mono = 1, Stereo = 2, etc.
        waveBuffer.putInt(sampleRate);  //sample rate
        waveBuffer.putInt(byteRate);//28        4   ByteRate         == SampleRate * NumChannels * BitsPerSample/8
        waveBuffer.putShort(blockAlign);//2   BlockAlign       == NumChannels * BitsPerSample/8
        waveBuffer.putShort(bitsPerSample);//2   BitsPerSample    8 bits = 8, 16 bits = 16, etc.
        waveBuffer.putShort((short) 0);  //extra parameter....not needed if PCM!
        //switch to big endian again
        waveBuffer.order(ByteOrder.BIG_ENDIAN);
        //Subchunk2ID      Contains the letters "data"
        byte[] asciidata = {0x64, 0x61, 0x74, 0x61};
        waveBuffer.put(asciidata);
        //switch back to little Endian
        waveBuffer.order(ByteOrder.LITTLE_ENDIAN);
        waveBuffer.putInt(subChunk2Size);
        // ADD PCM DATA TO HEADER
        waveBuffer.put(pcmData);
        //convert waveBuffer to byte[]
        byte[] completeWave = waveBuffer.array();
        return completeWave;
    }

    /**
     * Creates a smooth volume effect to a wave to prevent pops on play back.
     *
     * @param pcmData Byte Array of PCM data (16-bit only!)
     * @param fadeTime Fade Time is seconds
     * @param sampleRate Sample rate of PCM data Byte Array
     */
    public static void createHannWindow(byte[] pcmData, float fadeTime, int sampleRate) {

        ByteBuffer bb = ByteBuffer.wrap(pcmData);

        bb.asShortBuffer();
        bb.order(ByteOrder.LITTLE_ENDIAN);

        // Calculate duration, in samples, of fade time
        double numFadeSamples = fadeTime * sampleRate;
        short sliceValue;
        if (numFadeSamples > pcmData.length) {
            numFadeSamples = pcmData.length;
        }
        for (int s = 0; s < numFadeSamples; s++) {
            // Calculate weight based on Hann 'raised cosine' window
            float weight = 0.5f * ((float) ((1 - ((float) Math.cos((float) Math.PI * (float) s / (float) (numFadeSamples - 1))))));

            sliceValue = bb.getShort(s * 2);
            bb.putShort((s * 2), (short) (weight * sliceValue));     // Fade In
            bb.putShort((s * 2), (short) (weight * sliceValue));                       // Fade In
            
            sliceValue = bb.getShort((pcmData.length - 4 - (2 * s)));
            bb.putShort((pcmData.length - (s * 2) - 4), (short) (sliceValue * weight));  // Fade Out
        }

        //This code is in C for a hann window
        //http://www.labbookpages.co.uk/audio/wavGenFunc.html#tone
    }

    /**
     *
     * @param freq Desired frequency in hertz
     * @param duration_ms Duration of wave in milliseconds
     * @param ramp_ms depracated!
     * @param sampleRate Sample rate of PCM Data
     * @return
     */
    public static byte[] createSinePCM(short freq, short duration_ms, short ramp_ms, int sampleRate) //@TODO all these parameters should be changed to doubles.
    {
//@TODO add parameter for volume into function!

        short tempvolume = 28800; // test value of volume!
        // This may not work......just an initial test run
        double calculate;
        double calcSlices = (double)duration_ms / 1000D * (double)sampleRate;    //this may be wrong....just testing
int numberSlices = (int)calcSlices;        
//set up ByteBuffer
        ByteBuffer bb = ByteBuffer.allocate((numberSlices * 2)); //88200 is for testing purposes
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asShortBuffer();
        bb.position(0);
        //Loop for sine wave
        for (int step = 0; step < numberSlices; step++) {
            calculate = (Math.sin(freq * Math.PI * 2 * step / sampleRate) * tempvolume);
            bb.putShort((short) (calculate));
        }
        byte[] bytePCMsine = bb.array();
        /*
         //this is for testing only....FOR statment and brackets can b 
         // be deleted after testing shows it works.
         for (int slot = 0; slot <150; slot++)
         {short testout = bb.getShort(slot);
         System.out.println("Slot: " + slot + " Value : "+ testout);
         }
         */
        return bytePCMsine;

    }

    /**
     * Creates a wave format of silence for specified duration and sample rate
     *
     * @param duration_ms
     * @param sampleRate
     * @return An Output Stream is returned
     */
    public static byte[] createSilencePCM(double duration_ms, double sampleRate) {

        //TODO Measure the time in profile of this method vs twiddle!
        //SUCCESS!  This method is only 0.23 milliseconds compared to 11.6mSeconds! YAY!
        // byte[] bytePCMsilence = new byte[(duration_ms / 1000 * sampleRate) * 2];
        ByteBuffer bb = ByteBuffer.allocate((int)(duration_ms / 1000 * sampleRate) *2);
        bb.asShortBuffer();
        bb.position(0);
        //System.out.println("BB limit: " + bb.limit());
        //System.out.println((int)(sampleRate / duration_ms * 0.01));
        for (int slice = 0; slice < ((int)(duration_ms / 1000 * sampleRate))-2; slice++) {
            bb.putShort((short) 0);
            //System.out.println("Slice: "+ slice);

        }
        byte[] bytePCMsilence = new byte[(int)(duration_ms / 1000 * sampleRate) * 2];
        bytePCMsilence = bb.array();

        /*        short[] shortPCMsilence = new short[(duration_ms / 1000 * sampleRate)];

         for (int slice = 0; slice < (sampleRate / (duration_ms * 0.01)); slice++) {
         shortPCMsilence[slice] = (short) 0;
         }
         //TODO rewrite this procedure to use ByteArrayBuffer.putShort (slice) instead of Twiddle.
         // Current method is profiled at 11.6milliseconds using twiddle bits! (slow!)
        
         bytePCMsilence = ShortToByte_Twiddle_Method(shortPCMsilence);
         */
        return bytePCMsilence;
    }

////////////////////////////////////////////////////////////////////////////////
// Start of CODE From Stack Exchange 
//      http://stackoverflow.com/questions/9179536/writing-pcm-recorded-data-into-a-wav-file-java-android
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// End of CODE From Stack Exchange                                            //
////////////////////////////////////////////////////////////////////////////////
    /**
     * @param args No command line arguments available in class.
     */
    public static void main(String[] args) {

// TODO code application logic here
        byte[] testWave;
        byte[] pcmWave;
        byte[] waveWithHeader;
        OutputStream fullWave;

        testWave = createSilencePCM(1000, 44100); // create one second of PCM silence....
        pcmWave = createSilencePCM(1000, 44100);
        waveWithHeader = createWaveHeaderForPcm(pcmWave, 44100, (short) 16);

        System.out.println(waveWithHeader.length);
        InputStream inputWave = new ByteArrayInputStream(waveWithHeader);
        boolean support = inputWave.markSupported();
        System.out.println(support);
        System.out.println(inputWave.getClass());
        System.out.println(inputWave.toString());

//create PCM
        byte[] testPCMsine;
        testPCMsine = createSinePCM((short) 1000, (short) 1000, (short) 1000, 44100);

//Add wave Header to PCM
        byte[] testWaveWithHeader;
        testWaveWithHeader = createWaveHeaderForPcm(testPCMsine, (short) 44100, (short) 16);
        System.out.println(testWaveWithHeader.length);
        saveToWaveFile(testPCMsine, "testpcm.pcm");  // pcm data looks beautiful in audacity!
        saveToWaveFile(testWaveWithHeader, "testwave.wav"); // wave wont load :( in Audacity
        createHannWindow(testPCMsine, 0.005F, 44100);
        saveToWaveFile(testPCMsine, "pcmWithHann.pcm");
        
        byte[] ditByte;
        byte [] dahByte;
        byte [] interSpace ;
        byte [] characterSPace ;
        byte [] finishByte = null;
        byte [] workingByte;
        ditByte = createSinePCM((short)1000,(short)100, (short)0, 44100);
        createHannWindow(ditByte, 0.005F, sampleRate);
        dahByte = createSinePCM((short)1000,(short)300, (short)0, 44100);
        createHannWindow(dahByte, 0.005F, sampleRate);
        interSpace = createSilencePCM(100, 44100);
        characterSPace = createSilencePCM(700, sampleRate);
        
        workingByte = combineByteArray(ditByte, interSpace);
        finishByte=combineByteArray(workingByte, dahByte);
        workingByte = combineByteArray(finishByte, interSpace);
        
        saveToWaveFile(workingByte, "combined");
        
        
        
                    
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
