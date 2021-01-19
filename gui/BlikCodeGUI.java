package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import waysofpayments.Blik;
import waysofpayments.WaysOfPayments;

//Klasa zimplementowana przez Szymona Sawczuka
public class BlikCodeGUI {
	
	private JFrame jFrame;
	private JPanel northPanel, timerPanel, buttonPanel;
	private JLabel codeLabel, timerLabel;
	private WaysOfPayments payment;

	public BlikCodeGUI(WaysOfPayments payment) {
	
		this.payment = payment;
		jFrame = new JFrame();
		jFrame.setLocation(200,200);
		jFrame.setTitle("Kod BLIK");
		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		northPanel = createNorthPanel();
		timerPanel = createTimerPanel();
		buttonPanel = createButtonPanel();

				
		
		

		jFrame.getContentPane().add(BorderLayout.NORTH,northPanel);
		jFrame.getContentPane().add(BorderLayout.CENTER,buttonPanel);
		jFrame.getContentPane().add(BorderLayout.SOUTH,timerPanel);
	

		jFrame.pack();
		jFrame.setVisible(true);
		
	}
	
	private JPanel createButtonPanel() {
		
		JPanel buttonPanel = new JPanel();

		JButton generateButton = new JButton("Wygeneruj kod");
		generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		generateButton.addActionListener(new GenerateCode(codeLabel,payment, timerLabel));
	
		buttonPanel.add(generateButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,50))); 
		
		return buttonPanel;
		
	}
	
	private JPanel createNorthPanel() {
		
		JPanel northPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(northPanel, BoxLayout.Y_AXIS);
		northPanel.setLayout(boxLayout);
			
		codeLabel = new JLabel();
		codeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		codeLabel.setBorder(new EmptyBorder(5,10,10,10)); //top,left,bottom,right
		northPanel.add(codeLabel);
		
		return northPanel;
	}
	
	private JPanel createTimerPanel() {
		
		JPanel timerPanel = new JPanel();
		
		timerLabel = new JLabel(" ");
		timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		timerPanel.add(timerLabel);	
		
		return timerPanel; 
	}
	
	public void dispose() {
		jFrame.dispose();
	}
	
	private class GenerateCode implements ActionListener{

		private JLabel codeLabel, timerLabel;
		private WaysOfPayments payment;
		private int time;
		private Timer timer;
		
		
		public GenerateCode(JLabel codeLabel, WaysOfPayments payment, JLabel timerLabel) {
			
			this.codeLabel = codeLabel;
			this.payment = payment;
			this.timerLabel = timerLabel;

		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			codeLabel.setText(Integer.toString(((Blik)payment).generateCode()));
			
			//NOTE: zeruje timer przy kliknieciu przycisku
			if(timer != null && timer.isRunning()) {
				timer.stop();	
			}

			time = 120;
			timerLabel.setText(Integer.toString(time--));
			
			timer=new Timer(1000,new ActionListener(){
		         public void actionPerformed(ActionEvent e){
		        	 
		        if(time == 0) {
		        	timerLabel.setText("Kod przestal byc wazny");
		        	timer.stop();
		        }else
		        	timerLabel.setText(Integer.toString(time--));		       
		        
		     }
		     });
			timer.start();

		}
		
	}
	
}
