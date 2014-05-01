package music_test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class testing2
{
	/**	Flag for debugging messages.
	 *	If true, some messages are dumped to the console
	 *	during operation.	
	 */
	private static final boolean	DEBUG = false;

	/** The size of the temporary read buffer, in frames.
	 */
	private static final int	BUFFER_LENGTH = 4096;
	static PitchShift shifter = new PitchShift(44100);
	static Return_frequency freq = new Return_frequency(44100);

	public static void main(String[] args)
			throws Exception
			{
		File sourceFile = new File("ChromaticDown523.wav");
		File targetFile = new File("pleaaase.wav");


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
		int nBufferSize = BUFFER_LENGTH * audioFormat.getFrameSize();
		byte[]	abBuffer = new byte[nBufferSize];
		while (true)
		{
			if (DEBUG) { out("trying to read (bytes): " + abBuffer.length); }
			int	nBytesRead = inputAIS.read(abBuffer);
			if (DEBUG) { out("read (bytes): " + nBytesRead); }
			if (nBytesRead == -1)
			{
				break;
			}
			
			float[] CKey = {16.35f, 18.35f,20.6f,21.83f,24.5f,27.50f,30.87f,32.70f,36.71f,41.2f,43.65f,49.f,55.f,61.74f,65.41f, 73.42f,82.41f,87.31f,98.0f,110.f,123.47f,130.81f,146.83f,164.81f,174.61f,196.f,220.f,246.94f,261.63f, 293.66f,329.63f,349.23f,392.f,440.f,493.88f,523.25f,587.33f,659.26f,698.46f,783.99f,880.f,987.77f, 1046.5f,1174.66f,1318.51f,1396.91f,1567.98f,1760.f};

			ByteAndShort b1 = new ByteAndShort(abBuffer, false);
			ShortAndDouble s1 = new ShortAndDouble(b1.shortArray);
			double[] indata = s1.doubleArray;
			double[] outdata = new double[indata.length];
			float [] temp = new float[indata.length];
			
			for(int i = 0; i < indata.length; i++)
			{
				temp[i] = (float) indata [i];
			}
			
		    System.out.println(freq.DetectPitch(temp, temp.length));
            float change = 1;
            float detected = freq.DetectPitch(temp, temp.length);
            for (int q=0; q<CKey.length; q++) {
            	if (CKey[q]>detected) {
            		change = CKey[q]/detected;
            		break;
            	}
            }
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

		System.out.println("done");
			}

	private static void printUsageAndExit()
	{
		out("AudioDataBuffer: usage:");
		out("\tjava AudioDataBuffer <sourcefile> <targetfile>");
		System.exit(0);
	}

	private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}
}

