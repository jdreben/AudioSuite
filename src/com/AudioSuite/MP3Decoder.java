package com.AudioSuite;

import java.io.FileInputStream;

import javazoom.jl.decoder.*;

public class MP3Decoder {

	public static void main(String args[]) {
		try {
		Bitstream bitStream = new Bitstream(new FileInputStream("/Users/James/Documents/workspace/Audio/Resources/believe.wav"));

		while(true){
		    Decoder decoder = new Decoder();
		    SampleBuffer samples = (SampleBuffer)decoder.decodeFrame(bitStream.readFrame(), bitStream); //returns the next 2304 samples
		    System.out.println(samples.getBuffer());
		    bitStream.closeFrame();
		    //do whatever with your samples
		}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
