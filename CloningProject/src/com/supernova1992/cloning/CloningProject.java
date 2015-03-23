package com.supernova1992.cloning;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class CloningProject{

	public static void main(String[] args){
		
		String[] test = {"images/test.png"};
		TesseractExample.main(test);
		try{
		ImageLoader.main(test);
		}catch(IOException ex){
			
			ex.printStackTrace();
		}
		/*Interface test = new Interface();
		test.setTitle("PrimerPicker");
		test.setSize(500,300);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setVisible(true);*/
		
		/*Scanner input = new Scanner(System.in);
		
		System.out.println("Name: ");
		String name = input.next();
		System.out.println("Sequence: ");
		String seq = input.next();
		System.out.println("Start: ");
		int start = input.nextInt();
		System.out.println("Range: ");
		int range = input.nextInt();
		
		try{
			String[][] primers = PrimerSelection.getPrimers(name, seq, start, range);
			System.out.println("Left Primer: "+Arrays.toString(primers[0]));
			System.out.println("Right Primer: "+Arrays.toString(primers[1]));
			
			
			float[] temps = AnnealTemp(Float.valueOf((primers[0][4]).replaceAll("[^\\d.]+|\\.(?!\\d)", "")),Float.valueOf((primers[1][4]).replaceAll("[^\\d.]+|\\.(?!\\d)", "")));
			System.out.print("Left Primer Anneal: " + temps[0] +"C ");
			System.out.print("Right Primer Anneal: "+ temps[1]+"C");
		}catch (IOException ex){
			
			System.out.print(ex);
		}*/
		
	
	}
	
	public static float[] AnnealTemp(float lTm, float rTm){
		float la = lTm -5;
		float ra = rTm - 5;
		
		float[] temps = {la,ra};
		
		return temps;
	}
}
