package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;

public class UserInterface extends JFrame {
	
	final int WINDOW_HEIGHT = 1000, WINDOW_WIDTH = 1000, BUTTON_LENGTH = 95, BUTTON_HEIGHT = 20;
	JButton openButton = null ,appButton = null;
	static private final String newline = "\n";
	main main_obj = new main();
	ImageIcon image = null;
    JFileChooser fc;
    
	

	public void initUI() {
		
		/// area in winfow where we will pace the child components
		final JFrame frame = new JFrame(); 
		final JPanel panel = new JPanel(new FlowLayout());
		
	
		frame.setSize(WINDOW_HEIGHT,WINDOW_WIDTH);
	//	frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 frame.setVisible(true);
		Container pane = frame.getContentPane();
		// layout manager g1 which provides methods to manage child components
		// GroupLayout g1 = new GroupLayout(pane);
		
		
		pane.setLayout(null);
		
		panel.setLayout(new FlowLayout());
		
		pane.add(panel,BorderLayout.NORTH);
		//add a button
		 appButton = new JButton("Start Smoothing");
		appButton.setBounds((WINDOW_WIDTH - 950), (WINDOW_HEIGHT - 350), BUTTON_LENGTH, BUTTON_HEIGHT);
		
		 openButton = new JButton("Open");
		openButton.setBounds((WINDOW_WIDTH - 950), (WINDOW_HEIGHT - 450), BUTTON_LENGTH, BUTTON_HEIGHT);
		
		pane.add(appButton);
		
		pane.add(openButton);
		appButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				main_obj.smoothing();
			}
		});
		
	//	pack();
		// set gaps between every componenet we add
		//g1.setAutoCreateContainerGaps(true);
        
		// a pair of placement axis where the button and other components will be added
        /*
		g1.setHorizontalGroup(g1.createSequentialGroup().addComponent(appButton));

        g1.setVerticalGroup(g1.createSequentialGroup().addComponent(appButton));
        
        g1.setHorizontalGroup(g1.createSequentialGroup().addComponent(openButton));
        
        g1.setVerticalGroup(g1.createSequentialGroup().addComponent(openButton));
          */  
        fc = new JFileChooser();
        
        
        openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource() == openButton) {
		            int returnVal = fc.showOpenDialog(UserInterface.this);
		 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
		                
		                // starts the pre processing a\techniques 
		                // entry point to the attern recognition system
		                image = new ImageIcon(file.getAbsolutePath());
		                System.out.println(file.getAbsolutePath());
		               // JLabel label = new JLabel("Input is inout outgrhmfirmfjfdlfs",image,
		               // 		JLabel.CENTER);
		               // label.setIcon(image);
		               if(image!=null){
		            	   
		            	   panel.add(new JLabel("Input is inout outgrhmfirmfjfdlfs",image,
		            			   JLabel.CENTER));
		            	   JButton j = new JButton("Hello There");
		            	   j.setBounds((WINDOW_WIDTH - 950), (WINDOW_HEIGHT - 150), BUTTON_LENGTH, BUTTON_HEIGHT);
		            	   panel.add(j);
		               }
		               else
		            	   System.out.println("No image found");
		                //This is where a real application would open the file.
		               
		            } else {
		            	
		            }
		            panel.validate();
		            panel.repaint();
		            
		         
			}
			}
		});
        pane.add(panel,BorderLayout.NORTH);
        
	}
	
	
}


