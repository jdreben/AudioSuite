package autotune;

import java.io.FileInputStream;

import javazoom.jl.decoder.*;
import javazoom.jl.player.*;

public class Core {
	
	public static void main (String args[]) {
		
		System.out.println("Program initiated");
		//sanity check, plays music
	/*	try {
			Player playa = new Player(new FileInputStream("/Users/James/Documents/workspace/Audio/Resources/Canon.mp3"));
			playa.play();
		} catch (Exception e) { System.out.println(e); } 
	*/
		short[] outData = {};
		try {
			Bitstream bitStream = new Bitstream(new FileInputStream("C:/Users/Asus/Documents/GitHub/AudioSuite/Resources/believe.wav"));
			boolean eof = false;
			
			while(!eof) {
				Header head =  bitStream.readFrame();
				if (head == null) { eof=true; }
				Decoder dec = new Decoder();
				
				SampleBuffer samples = (SampleBuffer)dec.decodeFrame(head, bitStream); 
				
				Autotune tune = new Autotune(head, samples);
				SampleBuffer samp = (SampleBuffer)tune.getTuned();    
				
				samp.getBuffer();
				short[] nextBlock = samp.getBuffer();
                outData = concatArrays(outData, nextBlock);
                bitStream.closeFrame();
                /*
                 *  unsure how to convert from buffer back to mp3 file 
                 */
			}
		} catch (Exception e) {
			//System.out.println(outData);
			System.out.println(e);
		} 
		//System.out.println(outData);
		
 	}
	private static short[] concatArrays(short[] A, short[] B) {

        int aLen = A.length;
        int bLen = B.length;
        short[] C= new short[aLen+bLen];

        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);

        return C;
    }
}