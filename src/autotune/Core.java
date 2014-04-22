package autotune;

import java.io.FileInputStream;

import javazoom.jl.decoder.*;
import javazoom.jl.player.*;

public class Core {
	
	public static void main (String args[]) {
		//sanity check, plays music
		/*try {
		 * 
			Player playa = new Player(new FileInputStream("/Users/James/Documents/workspace/Audio/Resources/Canon.mp3"));
			playa.play();
		} catch (Exception e) { System.out.println(e); } */
		
		//	double d = 5.0;
		//	short s = (new Double(d)).shortValue();	 
			
		
		try {
			Bitstream bitStream = new Bitstream(new FileInputStream("/Users/James/Documents/workspace/Audio/Resources/Canon.mp3"));
			boolean eof = false;
			while(!eof) {
				Header head =  bitStream.readFrame();
				if (head == null) { eof=true; }
				Decoder dec = new Decoder();
				
				SampleBuffer samples = (SampleBuffer)dec.decodeFrame(head, bitStream); 
				
				
			/*	for (int i=0;i<samples.getBufferLength();i++) {
					System.out.print("before mod : "); System.out.print(array[i]+"\n");
				} */
				
				// implement autotune on frame
				Autotune tune = new Autotune(head, samples);
				SampleBuffer samp = (SampleBuffer)tune.getTuned();    

				System.out.println("Things happened successfully...");
				
				System.out.print("after mod : "); System.out.print(samp.getBuffer()[1]+"\n"); 
				/*
				 *  unsure how to convert from buffer back to mp3 file 
				 */
			}
		} catch (Exception e) {
			System.out.println(e);
		} 
		
 	}
}