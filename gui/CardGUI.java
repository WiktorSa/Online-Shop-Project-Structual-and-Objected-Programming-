package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import client.Client;
import waysofpayments.Card;

public class CardGUI {
	
	private Client client;
	private JFrame jFrame;
	private JPanel titlePanel, cardPanel, buttonPanel;
	private JFormattedTextField[] cardNumberForm;
	private JFormattedTextField cvvNumberForm;
	private JSpinner monthSpinner, yearSpinner;
	
	public CardGUI(Client client) {
		
		System.out.println("Poprawna przykladowa karta 4556 7375 8689 9855");//NOTE: Czysto do pomocy w prezentacji

		this.client = client; 
		client.setWayOfPayment(new Card());
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Platnosc karta");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		titlePanel = createTitleForm();
		cardPanel = createCardForm();
		buttonPanel = createButtonPanel();
		
		jFrame.getContentPane().add(BorderLayout.NORTH,titlePanel);
		jFrame.getContentPane().add(BorderLayout.CENTER,cardPanel);
		jFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
		
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	private JPanel createTitleForm() {

		JPanel titlePanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(titlePanel, BoxLayout.Y_AXIS);
		titlePanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Wpisz dane karty kredytowej", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		titlePanel.add(titleJLabel);
		
		return titlePanel;
	}
	
	private JPanel createCardForm() {
		
		JPanel cardForm = new JPanel();
		GridBagLayout gridLayout = new GridBagLayout();
		GridBagConstraints gridBag = new GridBagConstraints();
		cardForm.setLayout(gridLayout);
			
		JLabel cardNumberLabel = new JLabel("Numer karty: ");
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		cardForm.add(cardNumberLabel, gridBag);
		

		cardNumberForm = new JFormattedTextField[4];
		for(int i = 0;i<cardNumberForm.length;i++) {
			cardNumberForm[i] = new JFormattedTextField(onlyAllowNaturalNumbersUpTo4Digits());
			cardNumberForm[i].setColumns(4);
			gridBag.gridx = i+2;
			gridBag.gridy = 0;
			gridBag.gridwidth = 1;
			cardForm.add(cardNumberForm[i], gridBag);
		}

		JLabel cvvNumberLabel = new JLabel("Numer cvv: ");
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		cardForm.add(cvvNumberLabel, gridBag);
		
		cvvNumberForm = new JFormattedTextField(onlyAllowNaturalNumbersUpTo3Digits());
		cvvNumberForm.setColumns(4);
		gridBag.gridx = 2;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		cardForm.add(cvvNumberForm, gridBag);
		
		JLabel dateLabel = new JLabel("Data waznosci (MM/RR): ");
		gridBag.gridx = 1;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		cardForm.add(dateLabel, gridBag);
		
		String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		SpinnerListModel monthModel = new SpinnerListModel(months);
		monthSpinner = new JSpinner(monthModel);
		gridBag.gridx = 2;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		JComponent editor = monthSpinner.getEditor();
		((JSpinner.DefaultEditor)editor).getTextField().setHorizontalAlignment(JTextField.RIGHT); //NOTE: Ustawienie do prawej tekstu
		cardForm.add(monthSpinner, gridBag);
		
		final int MAXYEAR = 27;
		final int CURRENTYEAR = 21;
		
		SpinnerNumberModel yearModel = new SpinnerNumberModel(CURRENTYEAR, CURRENTYEAR, MAXYEAR, 1);
		yearSpinner = new JSpinner(yearModel);
		gridBag.gridx = 3;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		cardForm.add(yearSpinner, gridBag);

		//NOTE: Ustawienie identycznego rozmiaru
		Dimension d = yearSpinner.getPreferredSize();
		monthSpinner.setPreferredSize(d);
		
		return cardForm;
		
	}
	
	private JPanel createButtonPanel() {
		
		JPanel buttonPanel = new JPanel();
		
		JButton goBackButton = new JButton("Anuluj platnosc");
		goBackButton.addActionListener(new GoBack());
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(goBackButton);
		
		JButton submitButton = new JButton("Zaplac");
		submitButton.addActionListener(new SubmitCardInfo());
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(submitButton);
		
		return buttonPanel;
	}
	
	public NumberFormatter onlyAllowNaturalNumbersUpTo4Digits()
	{
		// Nie pozwalam uzytkownikowi na wpisanie niczego innego oprocz liczb naturalnych
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMaximum(9999);
		formatter.setAllowsInvalid(false);
		return formatter;
	}
	
	public NumberFormatter onlyAllowNaturalNumbersUpTo3Digits()
	{
		// Nie pozwalam uzytkownikowi na wpisanie niczego innego oprocz liczb naturalnych
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMaximum(999);
		formatter.setAllowsInvalid(false);
		return formatter;
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new WaysOfPaymentSelectingCategoryGUI(client);
			client.setWayOfPayment(null);
			jFrame.dispose();
		}
	}
	
	class SubmitCardInfo implements ActionListener
	{
	
		private String cardNumber = "", cvv = "", date = "";

		public void actionPerformed(ActionEvent event) {
			
			cardNumber = "";
			cvv = "";
			date = "";
			
			for(int i = 0;i<cardNumberForm.length;i++) {
				
				cardNumber += cardNumberForm[i].getText();
				
			}
			
			cvv = cvvNumberForm.getText();
			date = monthSpinner.getValue().toString() + "/" +  yearSpinner.getValue();
			
			if(!cardNumber.equals("") && !cvv.equals("") && !date.equals("") && cardNumber.length() == 16 && cvv.length() == 3) {
				
				if(((Card)client.getWayOfPayment()).pay(client, new ArrayList<String>(Arrays.asList(cardNumber, cvv, date)))) {
					
					JOptionPane.showMessageDialog(null,"Dokonano platnosci");
					jFrame.dispose();
					new FinalTransactionGUI(client);
					
				}else {
					JOptionPane.showMessageDialog(null,"Bledne dane");
				}
				
			}
			
		}
	}

}
