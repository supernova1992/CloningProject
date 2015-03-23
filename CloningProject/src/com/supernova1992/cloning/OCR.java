package com.supernova1992.cloning;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader;
import net.sourceforge.javaocr.scanner.PixelImage;

public class OCR {
	
	private Image image;
	private static OCRScanner scanner;
	
	public OCR(){
		
		scanner = new OCRScanner();
	}
	
	public static void loadTrainingImages(String trainingImageDir){
		
		if(!trainingImageDir.endsWith(File.separator)){
			
			trainingImageDir += File.separatorChar;
		}try{
			
			/*scanner.clearTrainingImages();*/
			TrainingImageLoader loader = new TrainingImageLoader();
			HashMap<Character, ArrayList<TrainingImage>> trainingImageMap = new HashMap<Character, ArrayList<TrainingImage>>();
			
			loader.load(trainingImageDir+ "ascii.png", new CharacterRange('!','~'), trainingImageMap);
		}catch(IOException ex){
			
			ex.printStackTrace();
			System.exit(2);
		}
		
	}
	
	public void process(String imageFilename){
		try{
			image = ImageIO.read(new File(imageFilename));
			
		
		}catch (IOException e){
			
			e.printStackTrace();
		}
		if(image == null){
			System.err.println("Cannot find image file: " + imageFilename);
			return;
		}
		@SuppressWarnings("deprecation")
		PixelImage pixelImage = new PixelImage(image);
		pixelImage.toGrayScale(true);
		pixelImage.filter();
		
		CharacterRange[] az = {new CharacterRange('A'), new CharacterRange('C'), new CharacterRange('T'), new CharacterRange('G')};
		
		System.out.println(imageFilename + ":");
		String text = scanner.scan(image, 0, 0, 0, 0, null);
		System.out.println("[" + text +"]");
		
	}
	
	public static void main(String[] args){
		if(args.length < 1){
			System.err.println("please specify one or more image filenames.");
			System.exit(1);
		}
		String trainingImageDir = "C:"+File.separator+"Users"+File.separator+"Jim"+File.separator+"Desktop"+File.separator+"trainingImages"+File.separator;
		/*if (trainingImageDir == null){
			System.err.println("Please specify -DTRAINING_IMAGE_DIR=<dir> on "+"the java command line.");
			return;
		}*/
		OCR demo = new OCR();
		OCR.loadTrainingImages(trainingImageDir);
		for (int i = 0; i < args.length; i++){
			demo.process(args[i]);
			System.out.println(args[i]);
		}
		System.out.println("done.");
	}
	private static final Logger LOG = Logger.getLogger(OCR.class.getName());
}
