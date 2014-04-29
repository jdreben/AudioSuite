package music_test;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

import music_test.PitchShift;
import music_test.ByteAndShort;
import music_test.ShortAndDouble;

import javax.sound.sampled.*;

public class testing{

	private static File soundFile;
	private static File targetFile;
	static byte[] dataSound;
	static byte[] targetSound;
	static double[] outdata;
	static AudioFormat audioFormat;
	static AudioFormat targetFormat;
	static int numberOfSample;
	static PitchShift shifter = new PitchShift(16000);

	public testing(String inF, String targetF){
		soundFile = new File(inF);
		targetFile = new File(targetF);
	}

	//load audio file into input stream
	public static void getSoundData() {

		AudioInputStream audioInputStream;

		try {		
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);			
			audioFormat = audioInputStream.getFormat();
			dataSound = getData(audioInputStream);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	//load target audio file into input stream
	public static void getTargetData () {

		AudioInputStream audioInputStream;

		try {
			audioInputStream = AudioSystem.getAudioInputStream(targetFile);			
			targetFormat = audioInputStream.getFormat(); 
			targetSound = getData(audioInputStream); 
		}
		catch (Exception e){
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

	public void pitch_shift() throws IOException {
		//choose audio file
		getSoundData();
		getTargetData(); 
		double[] indata;    	
		double[] compdata;
		double shift_factor;
		double sound_freq = 0;
		double target_freq = 0;

		ByteAndShort b1 = new ByteAndShort(dataSound, false);
		ShortAndDouble s1 = new ShortAndDouble(b1.shortArray);
		indata = s1.doubleArray;
		outdata = new double[indata.length];

		ByteAndShort bc = new ByteAndShort(targetSound, false);
		ShortAndDouble sc = new ShortAndDouble(bc.shortArray);
		compdata = sc.doubleArray;

		// will find a value and see how many times it occurs in the wave -> frequency
		double sound_val = indata[indata.length - 1];
		double target_val = compdata[compdata.length - 1];
		System.out.println(String.valueOf(indata.length));
		System.out.println(String.valueOf(compdata.length));
		for (int i = 0, len1 = indata.length, len2 = compdata.length; i < len1 - 1 && i < len2 - 1; i++) {

			if ((compdata[i] >= target_val && compdata[i+1] < target_val) || (compdata[i] <= target_val && compdata[i+1] > target_val)) {
				target_freq = target_freq + 1.0;
			}

			if ((indata[i] >= sound_val && indata[i+1] < sound_val) || (indata[i] <= sound_val && indata[i+1] > sound_val)) {             		
				sound_freq = sound_freq + 1.0;
			}
		}
		System.out.println(String.valueOf(sound_freq));
		System.out.println(String.valueOf(target_freq));

		shift_factor = target_freq/sound_freq;
		System.out.println(String.valueOf(shift_factor));

		shifter.setPitchShift(.5);
		shifter.smbPitchShift(indata, outdata, 0, indata.length);


		ShortAndDouble s2 = new ShortAndDouble(outdata);
		ByteAndShort b2 = new ByteAndShort(s2.shortArray, false);

		ByteArrayInputStream bais = new ByteArrayInputStream(b2.byteArray);
		long length = (long)(b2.byteArray.length / audioFormat.getFrameSize());
		AudioInputStream audioInputStreamTemp = new AudioInputStream(bais, audioFormat, length);
		File fileOut = new File("transmitted.wav");
		AudioFileFormat.Type fileType =  AudioFileFormat.Type.WAVE;
		if (AudioSystem.isFileTypeSupported(fileType,
				audioInputStreamTemp)) {
			System.out.println("Trying to write");
			AudioSystem.write(audioInputStreamTemp, fileType, fileOut);
			System.out.println("Written successfully");
		}
	}





}