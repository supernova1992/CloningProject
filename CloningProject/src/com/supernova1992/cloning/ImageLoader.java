package com.supernova1992.cloning;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.sourceforge.javaocr.scanner.DocumentScanner;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader;
import net.sourceforge.javaocr.scanner.PixelImage;

public class ImageLoader {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException{
		
		OCRScanner scanner = new OCRScanner();
		TrainingImageLoader loader = new TrainingImageLoader();
		HashMap<Character, ArrayList<TrainingImage>> trainingImageMap = new HashMap<Character, ArrayList<TrainingImage>>();
		
		
		
		loader.load("C:/Users/Jim/Desktop/trainingImages/ascii.png", new CharacterRange('!','~'), trainingImageMap);
		loader.load("C:/Users/Jim/Desktop/trainingImages/hpljPica.jpg", new CharacterRange('!','~'), trainingImageMap);
		loader.load("C:/Users/Jim/Desktop/trainingImages/digits.jpg", new CharacterRange('0','9'), trainingImageMap);
		//loader.load("C:/Users/Jim/Desktop/trainingImages/training3.png", new CharacterRange('!','~'), trainingImageMap);
		loader.load("C:/Users/Jim/Desktop/trainingImages/A.png", new CharacterRange('A') , trainingImageMap);
		loader.load("C:/Users/Jim/Desktop/trainingImages/C.png", new CharacterRange('C') , trainingImageMap);
		loader.load("C:/Users/Jim/Desktop/trainingImages/T.png", new CharacterRange('T') , trainingImageMap);
		loader.load("C:/Users/Jim/Desktop/trainingImages/G.png", new CharacterRange('G') , trainingImageMap);
		
		scanner.addTrainingImages(trainingImageMap);
		
		
		File input = new File(args[0]);
		Image image = ImageIO.read(input);
		if(image == null){
			System.err.println("Cannot find image file: " + input);
			return;
		}
		
		PixelImage pixelImage = new PixelImage(image);
		pixelImage.toGrayScale(true);
		pixelImage.filter();
		
		String text = scanner.scan(image, 0, 0, 0, 0, null);
		System.out.println(text);
		
	}
	
}
