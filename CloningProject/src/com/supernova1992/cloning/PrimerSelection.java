package com.supernova1992.cloning;


import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLOptionElement;



public class PrimerSelection {


	String[] primers;
	
	public static HtmlPage initialize(){
		
		final WebClient webClient = new WebClient();
		
		//get Primer3Plus Page
		try{
		 final HtmlPage P3P = webClient.getPage("http://www.bioinformatics.nl/cgi-bin/primer3plus/primer3plus.cgi");
		 return P3P;
		}catch(IOException ex){
			
			final HtmlPage P3P = null;
		}
		
		return null;
	}
	
	
	public static String[][] getPrimers(String name, String sequence, int start, int range) throws IOException{
		
		//final HtmlPage P3P;
		final int[] target = {start,range};
		final HtmlPage P3P = initialize();
			
		//Get the form, find submit and fields
		final HtmlForm form = P3P.getFormByName("mainForm");
		//Get Submit button
		final HtmlSubmitInput button = form.getInputByName("Pick_Primers");
		//Change task to cloning
		final HtmlSelect task = form.getSelectByName("SCRIPT_TASK");
		task.setSelectedAttribute("Cloning", true);
		//get sequence name
		final HtmlTextInput sname = form.getInputByName("PRIMER_SEQUENCE_ID");
		//get sequence
		final HtmlTextArea seqfield = form.getTextAreaByName("SEQUENCE");
		//get target field
		final HtmlTextInput tarfield = form.getInputByName("INCLUDED_REGION");
		
		//Change values to those entered
		sname.setValueAttribute(name);
		seqfield.setText(sequence);
		tarfield.setValueAttribute(target[0]+","+target[1]);
		
		//submit form to bring up results
		final HtmlPage results = button.click();
		
		//get left and right primers
		final HtmlTextInput p0 = results.getElementByName("PRIMER_0_NAME");
		final String leftName = p0.getValueAttribute();
		final HtmlTextInput p0seq = results.getElementByName("PRIMER_0_SEQUENCE"); 
		final String leftSeq = p0seq.getText();
		final List<DomElement> table =  results.getElementsByTagName("table");
		final HtmlTable t1 = (HtmlTable) table.get(1);
		
		/*int x = 0;
		for(final HtmlTableRow row : t1.getRows()){
			System.out.println("found row " + x);
			x++;
			for(final HtmlTableCell cell : row.getCells()){
				System.out.println("Found cell: " +cell.asText());
			}
		}*/
		
		final String lstart = t1.getCellAt(3,0).asText();
		final String lLength = t1.getCellAt(3,1).asText();
		final String lTm = t1.getCellAt(3,2).asText();
		final String lGC = t1.getCellAt(3,3).asText();
		final String lANY = t1.getCellAt(3,4).asText();
		final String lself = t1.getCellAt(3,5).asText();
		final String lwarn = "Warning: "+ t1.getCellAt(4,0).asText();
		
		
		final HtmlTextInput p1 = results.getElementByName("PRIMER_1_NAME");
		final String rightName = p1.getText();
		final HtmlTextInput p1seq = results.getElementByName("PRIMER_1_SEQUENCE"); 
		final String rightSeq = p1seq.getText();
		
		final String rstart ;
		final String rLength;
		final String rTm ;
		final String rGC ;
		final String rANY ;
		final String rself ;
		final String rwarn;
		
		if(t1.getCellAt(4,0) == null){
		 rstart = t1.getCellAt(7,0).asText();
		 rLength = t1.getCellAt(7,1).asText();
		 rTm = t1.getCellAt(7,2).asText();
		 rGC = t1.getCellAt(7,3).asText();
		 rANY = t1.getCellAt(7,4).asText();
		 rself = t1.getCellAt(7,5).asText();
		 rwarn = "Warning: "+ t1.getCellAt(8,0).asText();
		}else{
			 rstart = t1.getCellAt(8,0).asText();
			 rLength = t1.getCellAt(8,1).asText();
			 rTm = t1.getCellAt(8,2).asText();
			 rGC = t1.getCellAt(8,3).asText();
			 rANY = t1.getCellAt(8,4).asText();
			 rself = t1.getCellAt(8,5).asText();
			 rwarn = "Warning: "+ t1.getCellAt(9,0).asText();
		}
		String leftprimer[] = {leftName ,leftSeq,lstart,lLength,lTm,lGC,lANY,lself,lwarn };
		String rightprimer[] = {rightName ,rightSeq,rstart,rLength,rTm,rGC,rANY,rself,rwarn };
		String[] primers[] = {leftprimer , rightprimer};
		
		return primers;
		
	}
	
}
