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
		final JPanel panel = new JPanel();
		
		frame.setTitle("Digit Recognition System");
		frame.setSize(WINDOW_HEIGHT,WINDOW_WIDTH);
	//	frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		final Container pane = frame.getContentPane();
		// layout manager g1 which provides methods to manage child components
		// GroupLayout g1 = new GroupLayout(pane);
		
		
		pane.setLayout(null);
		
		//panel.setLayout(new FlowLayout());
		
		pane.add(panel,BorderLayout.NORTH);
		//add a button
		 appButton = new JButton("Start");
		appButton.setBounds((WINDOW_WIDTH - 950), (WINDOW_HEIGHT - 850), BUTTON_LENGTH, BUTTON_HEIGHT);
		
		 openButton = new JButton("Open");
		openButton.setBounds((WINDOW_WIDTH - 950), (WINDOW_HEIGHT - 950), BUTTON_LENGTH, BUTTON_HEIGHT);
		
		pane.add(appButton);
		
		pane.add(openButton);
		
		JLabel j2 = new JLabel("INPUT");
		//input label
		pane.add(j2);
		j2.setBounds((WINDOW_WIDTH - 750), (WINDOW_HEIGHT - 950), 75, 25);
		
		appButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JButton j = new JButton();
				pane.add(j);
				j.setBounds(0, 0, 40, 70);
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
		            	   
		            	   ImageIcon icon = new ImageIcon(file.getAbsolutePath());
		            	   JLabel j1 = new JLabel(icon);
		            	   //image
		            	   pane.add(j1);
		            	   j1.setBounds((WINDOW_WIDTH - 700), (WINDOW_HEIGHT - 950), 100, 85);
		               }
		               else
		            	   System.out.println("No image found");
		                //This is where a real application would open the file.
		               
		            } else {
		            	
		            }
		            
		            
		         
			}
			}
		});
       
        
	}
	
	
}


