package com.AudioSuite;

import java.io.FileInputStream;

import javazoom.jl.decoder.*;
import javazoom.jl.converter.*;

public class MP3Decoder {

	public static void main(String args[]) {
		//try {
		//Bitstream bitStream = new Bitstream(new FileInputStream("/Users/James/Documents/workspace/Audio/Resources/believe.wav"));
		
		try {
		Converter converter = new Converter();
		converter.convert("/Users/James/Documents/workspace/Audio/Resources/believe.wav",
				"/Users/James/Documents/workspace/Audio/Resources/believe.mp3");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		/*
		while(true){
		    Decoder decoder = new Decoder();
		    SampleBuffer samples = (SampleBuffer)decoder.decodeFrame(bitStream.readFrame(), bitStream); //returns the next 2304 samples
		    System.out.println(samples.getSampleFrequency());
		    bitStream.closeFrame();
		    //do whatever with your samples
		}
		} catch(Exception e) {
			System.out.println(e);
		}
		*/
	}
}
