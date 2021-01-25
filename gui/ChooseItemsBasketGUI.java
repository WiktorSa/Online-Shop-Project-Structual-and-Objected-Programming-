package gui;

import java.awt.Component;
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

import chooseitems.Product;
import client.Client;
import client.RegisteredClient;

//Klasa stworzona przez Wiktora Sadowego
public class ChooseItemsBasketGUI
{
	private Client client;
	private JPanel jPanel;
	// Bedziemy przekazywac informacje o produkcie i jego ilosci potem, zeby klient mogl skasowac produkty z koszyka
	private HashMap<JButton, Product> eraseProductButtons = new LinkedHashMap<JButton, Product>();
	private JButton giveBasicInfoJButton;
	private JButton goBackButton;
	
	public JPanel getJPanel() {
		return jPanel;
	}
	
	public ChooseItemsBasketGUI(Client client)
	{
		this.client = client;

		jPanel = new JPanel();
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
		titileJLabel.setFont(new Font("New Times Roman", Font.BOLD, 40));
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
		
		JLabel priceJLabel = new JLabel("Cena koncowa: " + String.format("%.2f", this.client.getBasket().getPrice()) + " zl");
		priceJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
		priceJLabel.setFont(new Font("New Times Roman", Font.BOLD, 27));
		jPanel.add(priceJLabel, gbc);
		
		gbc.gridy += 3;
		gbc.gridheight = 1;
		
		giveBasicInfoJButton = new JButton("Zakoncz transakcje");
		giveBasicInfoJButton.addActionListener(new GiveBasicInfo());
		jPanel.add(giveBasicInfoJButton, gbc);
	
		
		
	
	}
	
	class EraseItems implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			for (JButton jButton : eraseProductButtons.keySet()) 
			{
				if (event.getSource() == jButton) {
					// Biore klienta, produkt z HashMapa oraz ilosc danego przedmiotu z koszyka klienta
					new ChooseItemsEraseItemGUI(client, eraseProductButtons.get(jButton), client.getBasket().getProducts().get(eraseProductButtons.get(jButton)));
				
				}
			}
		}
	}
	
	class GiveBasicInfo implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new ClientSetClientInfoGUI(client);
		
		}
	}
	
	
}
