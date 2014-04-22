package autotune;

import java.io.FileInputStream;

import javazoom.jl.decoder.*;
public class Core {

	public static void main (String args[]) {
		try {
			Bitstream bitStream = new Bitstream(new FileInputStream("/Users/James/Documents/workspace/Audio/Resources/believe.m4a"));
			boolean eof = false;
			while(!eof) {
				Header head =  bitStream.readFrame();
				if (head == null) { eof=true; }
				Decoder dec = new Decoder();
				SampleBuffer samples = (SampleBuffer)dec.decodeFrame(head, bitStream); 

				System.out.println(samples.getBuffer());
				
				// implement autotune on frame
				Autotune tune = new Autotune(head, samples);
				SampleBuffer samp = (SampleBuffer)tune.getTuned();    

				System.out.println(samp.getBuffer());
				/*
				 *  unsure how to convert from buffer back to mp3 file 
				 */
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}

