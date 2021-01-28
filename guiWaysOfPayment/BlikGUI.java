package guiWaysOfPayment;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import gui.FinalTransactionGUI;
import guiShop.MainGUI;
import waysofpayments.Blik;
import waysofpayments.WaysOfPayments;

//Klasa zimplementowana przez Szymona Sawczuka
public class BlikGUI {

	private JFrame jFrame;
	private JPanel titlePanel, blikCodePanel, blikFormPanel, refreshPanel;
	private JLabel codeLabel, timerLabel;
	private JProgressBar progressBar;
	private JFormattedTextField blikForm;
	private GenerateCode generator;
	private JButton refreshButton;
	
	private MainGUI main;
	private WaysOfPayments payment;
	
	private final int TIMELOAD = 1000; //Okres odswiezania
	private final int MAXTIME = 120 * 1000/TIMELOAD; //NOTE: W sekundach - wystarczy zmienic tylko pierwsza wartosc
	private int minutes = MAXTIME/60, seconds = MAXTIME - minutes*60;
	
	private Timer timer;
	
	
	public BlikGUI(MainGUI main) {


		this.main = main; 
		main.getClient().setWayOfPayment(new Blik());
		payment = main.getClient().getWayOfPayment();
		generator = new GenerateCode();
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("BLIK");
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jFrame.addWindowListener(new WindowClose());
		jFrame.setResizable(false);

		jFrame.setMinimumSize(new Dimension(350,200));
		
		titlePanel = createtitlePanel();		
		createBlikCodePanel();
		
		try {
			createBlikFormPanel();
		} catch (ParseException e) {
//			e.printStackTrace();
		}
		
		jFrame.add(titlePanel,BorderLayout.NORTH);
		jFrame.add(blikCodePanel, BorderLayout.WEST);
		jFrame.add(blikFormPanel, BorderLayout.EAST);
		
		generator.actionPerformed(null);

		jFrame.pack();
		
		jFrame.setVisible(true);
	}
	
	private JPanel createtitlePanel() {
		
		JPanel titlePanel = new JPanel();
		
		titlePanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();
		
		
		JPanel logoPanel = new JPanel();
		logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		ImageIcon logo = new ImageIcon("Ikony/logoBlik.png");
		Image image = logo.getImage(); 
		image = image.getScaledInstance(75, 36, Image.SCALE_SMOOTH);
		logo = new ImageIcon(image);
		
		JLabel titleJLabel = new JLabel(logo);
		titleJLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		titleJLabel.setPreferredSize(new Dimension(105,51));
		titleJLabel.setBorder(new EmptyBorder(10,20,5, 20)); //top,left,bottom,right
		logoPanel.add(titleJLabel);
		
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.weightx = 1.0;
		gridBag.weighty = 1.0;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_START;
		titlePanel.add(logoPanel,gridBag);
		
		JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel priceLabel = new JLabel(String.format("%.2f zł", main.getClient().getBasket().getPrice() + main.getClient().getWaysOfDelivery().getPrice()));
		priceLabel.setBorder(new EmptyBorder(11,0,0, 10));
		priceLabel.setFont(new Font(priceLabel.getFont().getName(),Font.BOLD,30));
		pricePanel.add(priceLabel);
		
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.weightx = 1.0;
		gridBag.weighty = 1.0;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_END;
		titlePanel.add(pricePanel,gridBag);
		
		return titlePanel;
	}
	
	private void createBlikCodePanel() {
		
		blikCodePanel = new JPanel();
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();
		
		JPanel codeLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		codeLabel = new JLabel("");
		codeLabel.setFont(new Font(codeLabel.getFont().getName(),Font.BOLD,25));
		codeLabelPanel.add(codeLabel);
		
		
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.weightx = 1.0;
		gridBag.weighty = 1.0;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_START;
		northPanel.add(codeLabelPanel,gridBag);
		
		refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		refreshButton = new JButton(new ImageIcon("Ikony/refresh.png"));
		refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refreshButton.setBorder(new EmptyBorder(10,0,0,0));
		refreshButton.addActionListener(generator);
		refreshButton.setVisible(false);
		
		refreshPanel.add(refreshButton);
		refreshPanel.setPreferredSize(new Dimension(40,31));

		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.weightx = 1.0;
		gridBag.weighty = 1.0;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_END;
		northPanel.add(refreshPanel,gridBag);
	
		JPanel timerLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		timerLabel = new JLabel("2m 00s");
		timerLabelPanel.add(timerLabel);
		

		progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,MAXTIME);
		progressBar.setValue(MAXTIME);
		progressBar.setPreferredSize(new Dimension(160,10));
		progressBar.setBorder(new EmptyBorder(0,10,0,0));
		
		innerPanel.add(northPanel);
		innerPanel.add(timerLabelPanel);
		innerPanel.add(progressBar);
		blikCodePanel.add(innerPanel);
		blikCodePanel.setBorder(new EmptyBorder(10,10,0,20)); //top,left,bottom,right
	}
	

	
	private void createBlikFormPanel() throws ParseException {
		
		blikFormPanel = new JPanel();
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
		
		JPanel blikFormInnerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		blikForm = new JFormattedTextField(new MaskFormatter("### ###"));
		blikForm.setPreferredSize(new Dimension(120,45));;
		blikForm.setFont(new Font(blikForm.getFont().getName(),Font.PLAIN,25));
		
		blikFormInnerPanel.add(blikForm);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton submitButton = new JButton("Zaplac");
		submitButton.addActionListener(new SubmitCode());
		submitButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		submitButton.setPreferredSize(new Dimension(120, 45));
		submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
		
		buttonPanel.add(submitButton);
		
		innerPanel.add(blikFormInnerPanel);
		innerPanel.add(buttonPanel);
		blikFormPanel.add(innerPanel);
		
		
	}
	
	class WindowClose extends WindowAdapter{
		
		@Override
	    public void windowClosing(WindowEvent e) {

			if(timer != null && timer.isRunning()) {
				timer.stop();	
			}
			((Blik)payment).destroyTimer();
			main.getjFrame().setEnabled(true);
			jFrame.dispose();
	
	    }
		
	}
	
	class SubmitCode implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			
			String text = blikForm.getText().replaceAll("\\s+", "");
			
			if(!text.equals("")) {
				
				if(((Blik)main.getClient().getWayOfPayment()).pay(main.getClient(), new ArrayList<String>(Arrays.asList(text)))) {
					
					timer.stop();
					JOptionPane.showMessageDialog(null,"Dokonano platnosci");
					((Blik)main.getClient().getWayOfPayment()).destroyTimer();
					main.getjFrame().setEnabled(true);
					jFrame.dispose();
					main.changeLayoutToFinalTransaction();
					
					
				}else {
					JOptionPane.showMessageDialog(null,"Bledny kod blik");
				}
			}
			
		}
		
	}
	
	private class GenerateCode implements ActionListener{

		private int time;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			refreshButton.setVisible(false);
			
			String codeTmp = Integer.toString(((Blik)payment).generateCode());
			
			progressBar.setValue(MAXTIME);
			codeLabel.setText(String.format("%.3s %s", codeTmp,codeTmp.substring(3)));

			minutes = MAXTIME/60;
			seconds = MAXTIME - minutes*60;
			timerLabel.setText(String.format("%dm %02ds", minutes,seconds));
			
			//NOTE: zeruje timer przy kliknieciu przycisku
			if(timer != null && timer.isRunning()) {
				timer.stop();	
			}

			time = MAXTIME;
			
			timer=new Timer(TIMELOAD,new ActionListener(){
		         public void actionPerformed(ActionEvent e){
		        	 if(seconds==0 && minutes != 0) {
		        		 
		        		 seconds = 60;
		        		 minutes--;
		        		 
		        	 }
		        	 
		        	 seconds--;
		        	 time--;
		        	 
		        	 progressBar.setValue(time);
		        	 timerLabel.setText(String.format("%dm %02ds", minutes,seconds));
		        	 
			        if(time == 0) {
			        	
			        	refreshButton.setVisible(true);
			        	jFrame.revalidate();
			        	timer.stop();
			        }

		         }
		     });
			timer.start();

		}
		
	}
	
	
	
}
