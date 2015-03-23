package com.supernova1992.cloning;

import java.io.File;
import net.sourceforge.tess4j.*;

public class TesseractExample {

	public static void main(String[] args){
		File imageFile = new File(args[0]);
		
		Tesseract instance =Tesseract.getInstance();
		
		try{
			String result = instance.doOCR(imageFile);
			System.out.println(result);
		}catch (TesseractException ex){
			
			System.err.println(ex.getMessage());
			
		}
		
	}
	
}
