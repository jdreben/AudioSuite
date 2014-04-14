package com.AudioSuite;

import java.util.Scanner;

import com.AudioSuite.StdAudio;
import com.AudioSuite.Analysis;
import com.AudioSuite.WaveformDemo;

/*
 * 	Some helpful StdAudio functions
 * static double[] read(String file) --> Reads a WAV file into an array of doubles
 * static void save(String file, double[] input) --> Save an array of doubles (samples) into a WAV file
 * static void play(String file) --> Play a WAV file
 * static void play(double[] input) --> Play an array of doubles (samples)
 * 
 */

public class Core {
	public static void main (String args[]) {
		int choice = 42;
		/*  Welcome  */
		Scanner reader = new Scanner(System.in);
		System.out.println("Welcome to AudioHub (TM)\n"
				+ "Enter 1 to play the audio normal speed\n"
				+ "Enter 2 to play the audio at 2x speed\n"
				+ "Enter 3 to play a tone\n"
				+ "Enter 5 to open a waveform visualizer UI\n"
				+ "Enter 0 to quit AudioHub");
		//Loading a .wav file		
		String file = "/believe.wav";
		double[] music = StdAudio.read(file);
		//while loop to end program
		while (choice !=0) {
			System.out.print("input~ ");
			//deal with user input
			choice = reader.nextInt();		
			if (choice == 1 | choice == 2) {
				if (choice == 1) {
					StdAudio.play(music);
				}
				if (choice == 2) {
					double[] faster = new double[music.length/2];
					for (int i=0; i<faster.length; i++)
					{ faster[i]=music[2*i]; }
					StdAudio.play(faster);
				}
			}
			if (choice == 3) {
				/* Creates and plays a pitch:
				 * plays pitch A440 for 1 second */
				double[] sound = new double[StdAudio.SAMPLE_RATE+1];
				for (int i=0; i<sound.length; i++) {
					sound[i]=Math.sin(2*Math.PI*i*440/StdAudio.SAMPLE_RATE);
				}
				StdAudio.play(sound);
			}
			if (choice == 4) {
				/* Implements Analysis.writeOut */
				String bomb_string = "/bomb.wav";
				double[] bomb = StdAudio.read(bomb_string);
				System.out.println("\nWarning: This may take some time.");
				String file_data = "";
				for (int i=0; i<bomb.length; i++) {
					file_data+=bomb[i];
				}
				Analysis.writeOut(file_data);
				System.out.println("\n");
			}
			if (choice == 5) {
				new WaveformDemo();
			}
		}//while 
	}//main  
}//core

