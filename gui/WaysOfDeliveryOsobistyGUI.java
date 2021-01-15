package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.RegisteredClient;
import waysofdelivery.*;

//Klasa stworzona przez Jana Skibinskiego=
public class WaysOfDeliveryOsobistyGUI
{
	private Client client;
	private JFrame jFrame;
	// Zatwierdzanie lub cofanie
	private JButton confimButton;
	private JButton goBackButton;
	
	WaysOfDeliveryOsobistyGUI(Client client)
	{
		this.client = client;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Odbior Osobisty");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(650, 600);
		jFrame.setResizable(false);
		
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
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
		titileJLabel.setFont(new Font("New Times Roman", Font.BOLD, 24));
		jPanel.add(titileJLabel, gbc);
		
		gbc.gridwidth = 5;
		gbc.gridy = 3;
		
		gbc.gridwidth = 7;
		
		JLabel dataJLabel = new JLabel(Osobisty.getDt() + " do konca dnia nastepnego w godzinach 8-20");
		dataJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
		dataJLabel.setFont(new Font("New Times Roman", Font.BOLD, 16));
		jPanel.add(dataJLabel, gbc);
		
		gbc.gridy += 3;
		gbc.gridheight = 1;
		
		JLabel adresJLabel = new JLabel("Adres sklepu: Wroc³aw  ul. Teczowa 3");
		adresJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
		adresJLabel.setFont(new Font("New Times Roman", Font.BOLD, 16));
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
		String text = "";
		if (client instanceof RegisteredClient) {
			text = "Jestes zalogowany pod adresem email: " + client.getEmail();
		}
		else {
			text = "Jestes niezalogowany";
		}
		JLabel Info = new JLabel(text);
		Info.setBorder(new EmptyBorder(10,0,10,0));
		jPanel.add(Info, gbc);
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new WaysOfDeliverySelectingCategoryGUI(client);
			jFrame.dispose();
		}
	}

	
	class Confirm implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new WaysOfDeliverySelectingCategoryGUI(client);
			client.setWayOfDelivery(new Osobisty());
			jFrame.dispose();
		}
	}
}