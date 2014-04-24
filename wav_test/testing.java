package music_test;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

import javax.sound.sampled.*;

public class testing{
        
        private boolean opt;
        private static File soundFile = new File("/Users/melovett2013/Downloads/Audio/Resources/believe.wav");
        private static File newfile;
        static byte[] dataSound;
        double[] outdata;
        InputStream in;
        SourceDataLine sourceDataLine;
    	static AudioFormat audioFormat;
    	static int numberOfSample;
    	static double pitchShift;
    	static int osample;
    	static int fftFrameSize;
    	static PitchShift shifter = new PitchShift(16000);

  //load audio file into input stream
  public static void getSoundData() {
  	
  	AudioInputStream audioInputStream;
		
		try {		
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);			
			audioFormat = audioInputStream.getFormat();
			numberOfSample = (int) audioInputStream.getFrameLength();		
			dataSound = getData(audioInputStream);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
  }

  //convert audio into byte array
  private static byte[] getData(AudioInputStream audioStream) {
  	int length = (int) (audioStream.getFrameLength() * audioFormat
	        .getFrameSize());
	    
	    byte[] data = new byte[length];
	    DataInputStream is = new DataInputStream(audioStream);
	    try {
	      is.readFully(data);
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	    return data;	  
  }

public void pitch_shift() {
         //choose audio file
                 getSoundData();
                 double[] indata;    	
             	
             	ByteAndShort b1 = new ByteAndShort(dataSound, false);
             	ShortAndDouble s1 = new ShortAndDouble(b1.shortArray);
             	indata = s1.doubleArray;
             	
             	outdata = new double[indata.length];
           
             	
             	shifter.setPitchShift(10 / 12);
                shifter.smbPitchShift(indata, outdata, 0, numberOfSample);
 

               ShortAndDouble s2 = new ShortAndDouble(outdata);
               ByteAndShort b2 = new ByteAndShort(s2.shortArray, false);
                	
               ByteArrayInputStream bais = new ByteArrayInputStream(b2);
               long length = (long)(b2.length / audioFormat.getFrameSize());
               AudioInputStream audioInputStreamTemp = new AudioInputStream(bais, audioFormat, length);
                 File fileOut = new File("transmitted.wav");
                 AudioFileFormat.Type fileType = AudioSystem.getAudioFileFormat(audioFile).getType();
                 if (AudioSystem.isFileTypeSupported(fileType,
                     audioInputStreamTemp)) {
                     System.out.println("Trying to write");
                     AudioSystem.write(audioInputStreamTemp, fileType, fileOut);
                     System.out.println("Written successfully");
                 }
               }catch(Exception e){
                   System.out.println(e.getMessage());
               }
}



}
  
// end piano()