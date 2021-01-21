package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import waysofpayments.Paypal;


//Klasa zaimplementowana przez Szymona Sawczuka
public class PayPalGUI {
	
	private Client client;
	private JFrame jFrame;
	private int mistakesCounter;
	private final int MISTAKESMAX = 3;
	private String code = "";
	private JPanel titlePanel, logginPanel, buttonPanel;
	private JLabel captchaLabel;
	private JTextField emailForm, captchaForm;
	private JPasswordField passwordForm;
	
	public PayPalGUI(Client client) {
				
		mistakesCounter = 0;

		this.client = client; 
		client.setWayOfPayment(new Paypal());
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("PayPal");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		titlePanel = createTitlePanel();
		logginPanel = createLogginPanel();
		buttonPanel = createButtonPanel();

		
		jFrame.getContentPane().add(BorderLayout.NORTH,titlePanel);
		jFrame.getContentPane().add(BorderLayout.CENTER,logginPanel);
		jFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
	

		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	private JPanel createTitlePanel() {
		
		JPanel titlePanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(titlePanel, BoxLayout.Y_AXIS);
		titlePanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Zaloguj sie za pomoca konta PayPal", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		titlePanel.add(titleJLabel);
		
		
		return titlePanel;
		
	}
	
	private JPanel createLogginPanel() {
		
		JPanel logginForm = new JPanel();
		GridBagLayout gridLayout = new GridBagLayout();
		GridBagConstraints gridBag = new GridBagConstraints();
		logginForm.setLayout(gridLayout);
			
		JLabel emailLabel = new JLabel("Email/nr tel.: ");
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		logginForm.add(emailLabel, gridBag);

		emailForm = new JTextField();
		emailForm.setColumns(20);
		gridBag.gridx = 2;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		logginForm.add(emailForm, gridBag);

		JLabel passwordLabel = new JLabel("Haslo:");
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		logginForm.add(passwordLabel, gridBag);
		
		
		passwordForm = new JPasswordField();
		passwordForm.setColumns(20);
		gridBag.gridx = 2;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		logginForm.add(passwordForm, gridBag);
		
		JLabel helper = new JLabel("(Poprawne haslo: haslo)");
		gridBag.gridx = 1;
		gridBag.gridy = 2;
		gridBag.gridwidth = 1;
		logginForm.add(helper, gridBag);
			
		captchaLabel = new JLabel();
		gridBag.gridx = 1;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		captchaLabel.setVisible(false);;
		logginForm.add(captchaLabel, gridBag);
		
		captchaForm = new JTextField();
		captchaForm.setColumns(20);
		gridBag.gridx = 2;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		captchaForm.setVisible(false);
		logginForm.add(captchaForm, gridBag);
		logginForm.add(Box.createRigidArea(new Dimension(0,40)),gridBag); 
		
		return logginForm;
	}
	
	private JPanel createButtonPanel() {
		
		JPanel buttonPanel = new JPanel();
		
		JButton goBackButton = new JButton("Anuluj platnosc");
		goBackButton.addActionListener(new GoBack());
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(goBackButton);
		
		JButton submitButton = new JButton("Zaloguj sie");
		submitButton.addActionListener(new Submit());
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(submitButton);
		
		return buttonPanel;
	}
	
	private class GoBack implements ActionListener{
		
		public void actionPerformed(ActionEvent event) 
		{
			new WaysOfPaymentSelectingCategoryGUI(client);
			client.setWayOfPayment(null);
			jFrame.dispose();
		}
	}
	
	private class Submit implements ActionListener{
		
		
		public void actionPerformed(ActionEvent event) {
			
			if(captchaForm.getText().equals(code) || code.equals("")) {
				
				if(!(emailForm.getText().equals("") || String.valueOf(passwordForm.getPassword()).equals(""))) {
					
					boolean isPaymentDoneCorrectly = ((Paypal)client.getWayOfPayment()).pay(client, new ArrayList<String>(Arrays.asList(emailForm.getText(),String.valueOf(passwordForm.getPassword()))));
					
					if(isPaymentDoneCorrectly) {
						
						JOptionPane.showMessageDialog(null,"Dokonano platnosci");
						jFrame.dispose();
						new FinalTransactionGUI(client);
						
					}else {
						
						JOptionPane.showMessageDialog(null,"Bledne dane");
						mistakesCounter++;
						
						if(mistakesCounter >= MISTAKESMAX) {
							
							code =  ((Paypal)client.getWayOfPayment()).captchaGeneretor();
							captchaLabel.setText("Przepisz kod:"+code);
							captchaLabel.setVisible(true);
							captchaForm.setVisible(true);
							captchaForm.setText("");
							
						}
						
						emailForm.setText("");
						passwordForm.setText("");
					}
				}
			}else {
				
				JOptionPane.showMessageDialog(null,"Wpisz poprawny kod Captcha");
				code = ((Paypal)client.getWayOfPayment()).captchaGeneretor();
				captchaLabel.setText("Przepisz kod:"+code);
				
				emailForm.setText("");
				passwordForm.setText("");
				captchaForm.setText("");

			}
		}
		
	}

	

}
