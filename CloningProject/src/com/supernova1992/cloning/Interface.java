package com.supernova1992.cloning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interface extends JFrame implements ActionListener{

	JPanel mypanel;
	JLabel name;
	JTextField nm;
	JLabel seq;
	JTextArea sequence;
	JLabel start;
	JTextField st;
	JLabel range;
	JTextField rn;
	JTextArea out1;
	JTextArea out2;
	JButton button;
	JLabel p1;
	JLabel p2;
	
	public Interface(){
		mypanel = new JPanel();
		name = new JLabel("Sequence Name: ");
		nm = new JTextField(15);
		seq = new JLabel("Sequence: ");
		sequence = new JTextArea(5,40);
		start = new JLabel("Start: ");
		st = new JTextField(8);
		range = new JLabel("Range:");
		rn = new JTextField(8);
		p1 = new JLabel("Left Primer: ");
		out1 = new JTextArea(2,40);
		p2 = new JLabel("Right Primer: ");
		out2 = new JTextArea(2,40);
		button = new JButton("Submit");
		button.addActionListener(this);
		
		sequence.setLineWrap(true);
		JScrollPane sp = new JScrollPane(sequence);
		out1.setLineWrap(true);
		out2.setLineWrap(true);
		
		
		mypanel.add(name);
		mypanel.add(nm);
		mypanel.add(seq);
		mypanel.add(sp);
		mypanel.add(start);
		mypanel.add(st);
		mypanel.add(range);
		mypanel.add(rn);
		mypanel.add(button, BorderLayout.SOUTH);
		mypanel.add(p1, BorderLayout.NORTH);
		mypanel.add(out1);
		mypanel.add(p2);
		mypanel.add(out2);
		
		this.add(mypanel);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource()== button){
			
			String seqname = nm.getText();
			String seq = sequence.getText();
			int strt = Integer.valueOf(st.getText());
			int rng =Integer.valueOf(rn.getText());
			
			try{
				String[][] primers = PrimerSelection.getPrimers(seqname, seq, strt, rng);
				System.out.println("Left Primer: "+Arrays.toString(primers[0]));
				System.out.println("Right Primer: "+Arrays.toString(primers[1]));
				out1.setText(Arrays.toString(primers[0]));
				out2.setText(Arrays.toString(primers[1]));
				
				float[] temps = CloningProject.AnnealTemp(Float.valueOf((primers[0][4]).replaceAll("[^\\d.]+|\\.(?!\\d)", "")),Float.valueOf((primers[1][4]).replaceAll("[^\\d.]+|\\.(?!\\d)", "")));
				System.out.print("Left Primer Anneal: " + temps[0] +"C ");
				System.out.print("Right Primer Anneal: "+ temps[1]+"C");
			}catch (IOException ex){
				
				System.out.print(ex);
			}
		}
		
	}

}
