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
	private static final int	BUFFER_LENGTH = 1024;
	static PitchShift shifter = new PitchShift(44100);

	public static void main(String[] args)
			throws Exception
			{
		File sourceFile = new File("believe.wav");
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

			ByteAndShort b1 = new ByteAndShort(abBuffer, false);
			ShortAndDouble s1 = new ShortAndDouble(b1.shortArray);
			double[] indata = s1.doubleArray;
			double[] outdata = new double[indata.length];

			shifter.setPitchShift(1);
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

