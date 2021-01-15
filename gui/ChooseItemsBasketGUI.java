package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chooseitems.ChooseItems;
import chooseitems.Product;
import client.Client;
import client.RegisteredClient;

//Klasa stworzona przez Wiktora Sadowego=
public class ChooseItemsBasketGUI
{
	private Client client;
	private JFrame jFrame;
	// Bedziemy przekazywac informacje o produkcie i jego ilosci potem, zeby klient mogl skasowac produkty z koszyka
	private HashMap<JButton, Product> eraseProductButtons = new LinkedHashMap<JButton, Product>();
	private JButton giveBasicInfoJButton;
	private JButton goBackButton;
	
	ChooseItemsBasketGUI(Client client)
	{
		this.client = client;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Sklep");
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
		
		JLabel titileJLabel = new JLabel("Zawartosc koszyka");
		titileJLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		titileJLabel.setFont(new Font("New Times Roman", Font.BOLD, 24));
		jPanel.add(titileJLabel, gbc);
		
		gbc.gridwidth = 5;
		gbc.gridy = 3;
		
		// Zdobywamy liste produktow, zeby moc ja wypisac na ekran
		Iterator<Entry<Product, Integer>> listOfBoughtItems = this.client.getBasket().getProducts().entrySet().iterator();
		
		while(listOfBoughtItems.hasNext())
		{
			Entry<Product, Integer> entry = listOfBoughtItems.next();
			
			JLabel jLabel = new JLabel(entry.getKey().toStringOneLine() + " Lista produktow: " + entry.getValue());
			jLabel.setBorder(new EmptyBorder(0, 10, 0, 5));
			jPanel.add(jLabel, gbc);
			
			gbc.gridx = 5;
			gbc.gridwidth = 2;
			
			JButton jButton = new JButton("Skasuj produkt z koszyka");
			jButton.addActionListener(new EraseItems());
			eraseProductButtons.put(jButton, entry.getKey());
			jPanel.add(jButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridwidth = 5;
			gbc.gridy += 3;
		}
		
		gbc.gridwidth = 7;
		
		JLabel priceJLabel = new JLabel(String.format("%.2f", this.client.getBasket().getPrice()));
		priceJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
		priceJLabel.setFont(new Font("New Times Roman", Font.BOLD, 18));
		jPanel.add(priceJLabel, gbc);
		
		gbc.gridy += 3;
		gbc.gridheight = 1;
		
		giveBasicInfoJButton = new JButton("Zakoncz transakcje");
		giveBasicInfoJButton.addActionListener(new GiveBasicInfo());
		jPanel.add(giveBasicInfoJButton, gbc);
		
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
	
	class EraseItems implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			for (JButton jButton : eraseProductButtons.keySet()) 
			{
				if (event.getSource() == jButton) {
					new ChooseItemsEraseItemGUI(client, eraseProductButtons.get(jButton), client.getBasket().getProducts().get(eraseProductButtons.get(jButton)));
					jFrame.dispose();
				}
			}
		}
	}
	
	class GiveBasicInfo implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new ClientSetClientInfoGUI(client);
			jFrame.dispose();
		}
	}
	
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new ChooseItemsSelectingCategoryGUI(client, new ChooseItems());
			jFrame.dispose();
		}
	}
}
