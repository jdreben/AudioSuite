package music_test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

//import autotune.Autotune;
import music_test.ShortAndDouble;

public class testing2
{
	private static final boolean	DEBUG = false;

	static Return_frequency freq = new Return_frequency(44100);
	
	/** The size of the temporary read buffer, in frames.
	 */
	private static final int	BUFFER_LENGTH = 4096;
	static PitchShift shifter = new PitchShift(44100);

	
	public testing2() {}
	
	public void main(String infile, String outfile, String key)
			throws Exception
			{
		File sourceFile = new File(infile);
		
		File targetFile = new File(outfile);

		/* Get the type of the source file. We need this information
		   later to write the audio data to a file of the same type.
		 */
		AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(sourceFile);
		AudioFileFormat.Type	targetFileType = fileFormat.getType();
		AudioFormat audioFormat = fileFormat.getFormat();

		/* Read the audio data into a memory buffer.
		 */
		AudioInputStream inputAIS = AudioSystem.getAudioInputStream(sourceFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int nBufferSize = BUFFER_LENGTH;
		byte[]	abBuffer = new byte[nBufferSize];
		while (true)
		{
			int	nBytesRead = inputAIS.read(abBuffer);
			if (nBytesRead == -1)
			{
				break;
			}

			ByteAndShort b1 = new ByteAndShort(abBuffer, false);
			ShortAndDouble s1 = new ShortAndDouble(b1.shortArray);
			double[] indata = s1.doubleArray;
			double[] outdata = new double[indata.length];
			
			//finds the correct frequencies of notes in key you want to tune to
			float[] Key = shifter.findKey(key);                 

            float[] temp = new float[indata.length];
            for(int i = 0; i < indata.length; i++) { temp[i] = (float) indata [i]; }
            
            //calculates how much to shift it based on the detected frequency and the list of correct frequencies in the key
            float change = 1;
            float detected = freq.DetectPitch(temp, temp.length);
            for (int q=0; q<Key.length; q++) {
            	if (Key[q]>detected) {
            		float diff1 = Math.abs(detected-Key[q]);
            		float diff2 = Math.abs(detected-Key[q-1]);
            		if (diff1 > diff2) {
            			change=Key[q-1]/detected;
            		}
            		else {
            			change=Key[q]/detected;
            		}
            		break;
            	}
            }
			
            // actually shifts the sample by the factor calculated
			shifter.setPitchShift(change);
			shifter.smbPitchShift(indata, outdata, 0, indata.length);
					
			ShortAndDouble s2 = new ShortAndDouble(outdata);
			ByteAndShort b2 = new ByteAndShort(s2.shortArray, false);

			baos.write(b2.byteArray, 0, nBytesRead);
		}

		/* Here's the byte array everybody wants.
		 */
		byte[] abAudioData = baos.toByteArray();

		/* And now, write it to a file again.
		 */
		ByteArrayInputStream bais = new ByteArrayInputStream(abAudioData);
		AudioInputStream outputAIS = new AudioInputStream(
				bais, audioFormat,
				abAudioData.length / audioFormat.getFrameSize());
		int	nWrittenBytes = AudioSystem.write(outputAIS,
				targetFileType,
				targetFile);
		if (DEBUG) { out("Written bytes: " + nWrittenBytes); }
			}

	private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}
	
}

