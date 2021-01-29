package guiWaysOfPayment;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import guiShop.MainGUI;
import waysofpayments.Paypal;


//Klasa zaimplementowana przez Szymona Sawczuka
public class PayPalGUI {
	
	private JFrame jFrame;
	private int mistakesCounter;
	private final int MISTAKESMAX = 3;
	private String code = "";
	private JPanel titlePanel, logginPanel;
	private JLabel captchaLabel;
	private JTextField emailForm, captchaForm;
	private JPasswordField passwordForm;
	private String emailText = "";
	private String passwordText = "";
	private MainGUI main;
	
	public PayPalGUI(MainGUI main) {
				
		mistakesCounter = 0;

		this.main = main; 
		main.getClient().setWayOfPayment(new Paypal());
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("PayPal");
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jFrame.addWindowListener(new WindowClose());
		jFrame.setResizable(false);
		
		titlePanel = createTitlePanel();
		logginPanel = createLogginPanel();
	

		
		jFrame.getContentPane().add(BorderLayout.NORTH,titlePanel);
		jFrame.getContentPane().add(BorderLayout.CENTER,logginPanel);
		


		jFrame.pack();
		jFrame.setVisible(true);
		
		emailForm.transferFocus();
		passwordForm.transferFocus();
	
	}
	
	private JPanel createTitlePanel() {
		
		JPanel titlePanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(titlePanel, BoxLayout.Y_AXIS);
		titlePanel.setLayout(boxLayout);
		
		ImageIcon logo = new ImageIcon("Ikony/paypalLogo.png");
		Image image = logo.getImage(); 
		image = image.getScaledInstance(128, 64, Image.SCALE_SMOOTH);
		logo = new ImageIcon(image);
		
		JLabel titleJLabel = new JLabel(logo);
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(20,150,5,150)); //top,left,bottom,right
		titlePanel.add(titleJLabel);
		
		
		return titlePanel;
		
	}
	
	private JPanel createLogginPanel() {
		
		JPanel logginForm = new JPanel();
		GridBagLayout gridLayout = new GridBagLayout();
		GridBagConstraints gridBag = new GridBagConstraints();
		logginForm.setLayout(gridLayout);
		
		Dimension sizeOfForms = new Dimension(300,40);

		emailForm = new JTextField();
		emailForm.setPreferredSize(sizeOfForms);
		emailForm.setText("Email/nr tel.");
		emailForm.addFocusListener(new FocusDetector());

		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		logginForm.add(emailForm, gridBag);

		
		
		passwordForm = new JPasswordField();
		passwordForm.setPreferredSize(sizeOfForms);
		passwordForm.setText("Haslo");
		passwordForm.setEchoChar((char)0);
		passwordForm.addFocusListener(new FocusDetector());
	
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		logginForm.add(passwordForm, gridBag);
		
		JLabel helper = new JLabel("(Poprawne haslo: haslo)");
		gridBag.gridx = 1;
		gridBag.gridy = 2;
		gridBag.gridwidth = 1;
		logginForm.add(helper, gridBag);
		
		JButton submitButton = new JButton("Zaloguj sie");
		submitButton.addActionListener(new Submit());
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		submitButton.setPreferredSize(sizeOfForms);
		gridBag.gridx = 1;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		logginForm.add(submitButton, gridBag);
		logginForm.setBorder(new EmptyBorder(0,0,20,0));
		submitButton.requestFocusInWindow();
		
		captchaForm = new JTextField();
		gridBag.gridx = 1;
		gridBag.gridy = 4;
		gridBag.gridwidth = 1;
		captchaForm.setVisible(false);
		captchaForm.setPreferredSize(sizeOfForms);
		logginForm.add(captchaForm, gridBag);
		
		captchaLabel = new JLabel("sdasd");
		gridBag.gridx = 1;
		gridBag.gridy = 5;
		gridBag.gridwidth = 1;
		captchaLabel.setVisible(false);
		logginForm.add(captchaLabel, gridBag);

		return logginForm;
	}
	

	

	
	private class Submit implements ActionListener{
		
		
		public void actionPerformed(ActionEvent event) {
			
			if(captchaForm.getText().equals(code) || code.equals("")) {
				
				if(!(emailText.equals("") || passwordText.equals(""))) {
					
					boolean isPaymentDoneCorrectly = ((Paypal)main.getClient().getWayOfPayment()).pay(main.getClient(), new ArrayList<String>(Arrays.asList(emailText, passwordText)));
					
					if(isPaymentDoneCorrectly) {
						
						JOptionPane.showMessageDialog(null,"Dokonano platnosci");
						jFrame.dispose();
						main.getjFrame().setEnabled(true);
						main.changeLayoutToFinalTransaction();
						
					}else {
						
						JOptionPane.showMessageDialog(null,"Bledne dane");
						mistakesCounter++;
						
						if(mistakesCounter >= MISTAKESMAX) {
							
							code =  ((Paypal)main.getClient().getWayOfPayment()).captchaGeneretor();
							captchaLabel.setText("Przepisz kod:"+code);
							captchaLabel.setVisible(true);
							captchaForm.setVisible(true);
							captchaForm.setText("");
							jFrame.revalidate();
							jFrame.setSize(jFrame.getWidth(), jFrame.getHeight() + 90);
							
						}
						emailForm.setText("Email/nr tel.");
						passwordForm.setText("Haslo");
						passwordForm.setEchoChar((char)0);
						emailText = "";
						passwordText = "";
					}
				}
			}else {
				
				JOptionPane.showMessageDialog(null,"Wpisz poprawny kod Captcha");
				code = ((Paypal)main.getClient().getWayOfPayment()).captchaGeneretor();
				captchaLabel.setText("Przepisz kod:"+code);
				emailForm.setText("Email/nr tel.");
				passwordForm.setText("Haslo");
				passwordForm.setEchoChar((char)0);
				emailText = "";
				passwordText = "";
				captchaForm.setText("");

			}
		}
		
	}
	
	class WindowClose extends WindowAdapter{
		
		@Override
	    public void windowClosing(WindowEvent e) {

			main.getjFrame().setEnabled(true);
			jFrame.dispose();
	
	    }
		
	}
	
	private class FocusDetector implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			
			if(e.getSource() == emailForm) {
				emailForm.setText(emailText);
			}else if(e.getSource() == passwordForm) {
				passwordForm.setText(passwordText);
				passwordForm.setEchoChar('*');
			}
			
		
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			
			if(e.getSource() == emailForm ) {
				emailText = emailForm.getText();
				
				if(emailText.equals(""))
					emailForm.setText("Email/nr tel.");
				
			
			}else if(e.getSource() == passwordForm) {
				passwordText = String.valueOf(passwordForm.getPassword());
				
				if(passwordText.equals("")) {
					passwordForm.setText("Haslo");
					passwordForm.setEchoChar((char)0);
				}
			
			}
			
		}
		
	}

	

}
