package guiWaysOfDelivery;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import guiShop.MainGUI;
import guiWaysOfPayment.WaysOfPaymentSelectingCategoryGUI;
import waysofdelivery.*;

//Klasa stworzona przez Jana Skibinskiego
public class OsobistyGUI
{
	private JPanel jPanel;
	private Osobisty osobisty;
	// Zatwierdzanie lub cofanie
	private JButton confimButton;
	private JButton goBackButton;
	private Client client;
	private MainGUI main;
	
	private WaysOfDeliverySelectingCategoryGUI goBackCategory;
	private JPanel backPanel;
	private WaysOfPaymentSelectingCategoryGUI goToPayment;
	private JPanel paymentPanel;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	OsobistyGUI(MainGUI main)
	{
		this.main=main;
		client=main.getClient();
		osobisty = new Osobisty(client);
		osobisty.setTomDt();
		
		jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		jPanel.add(Box.createVerticalGlue());
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 7;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		
		JLabel titileJLabel = new JLabel("Odbior osobisty bedzie dostepny od:");
		titileJLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		titileJLabel.setFont(new Font("New Times Roman", Font.BOLD, 40));
		jPanel.add(titileJLabel, gbc);
		
		gbc.gridwidth = 5;
		gbc.gridy = 3;
		
		gbc.gridwidth = 7;
		
		JLabel dataJLabel = new JLabel(osobisty.getDt() + " do konca dnia nastepnego w godzinach 8-20");
		dataJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
		dataJLabel.setFont(new Font("New Times Roman", Font.BOLD, 24));
		jPanel.add(dataJLabel, gbc);
		
		gbc.gridy += 3;
		gbc.gridheight = 1;
		
		JLabel adresJLabel = new JLabel("Adres sklepu: Wroclaw  ul. Teczowa 3");
		adresJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
		adresJLabel.setFont(new Font("New Times Roman", Font.BOLD, 24));
		jPanel.add(adresJLabel, gbc);
		
		gbc.gridy += 3;
		gbc.gridheight = 1;
		
		confimButton = new JButton("Potwierdz");
		confimButton.addActionListener(new Confirm());
		jPanel.add(confimButton, gbc);
		
		gbc.gridy += 1;
		goBackButton = new JButton("Cofnij sie");
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton, gbc);
		gbc.gridy += 1;
		jPanel.add(Box.createVerticalGlue());
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			goBackCategory=new WaysOfDeliverySelectingCategoryGUI(main);
			backPanel=goBackCategory.getjPanel();
			main.getCardPanel().add(backPanel,"Delivery Page");
			main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
		}
	}

	class Confirm implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			client.setWayOfDelivery(osobisty);
			goToPayment=new WaysOfPaymentSelectingCategoryGUI(main);
			paymentPanel=goToPayment.getjPanel();
			main.getCardPanel().add(paymentPanel,"Delivery Page");
			main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
		}
	}
}