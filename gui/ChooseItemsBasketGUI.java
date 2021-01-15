package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chooseitems.Product;
import client.Client;
import client.RegisteredClient;

//Klasa stworzona przez Wiktora Sadowego (Ta klasa jeszcze nie dziala
public class ChooseItemsBasketGUI
{
	private Client client;
	private JFrame jFrame;
	// Bedziemy przekazywac informacje o produkcie i jego ilosci potem, zeby klient mogl skasowac produkty z koszyka
	private HashMap<JButton, Map.Entry<Product, Integer>> productButtons = new LinkedHashMap<JButton, Map.Entry<Product, Integer>>();
	
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
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 5;
		gbc.gridheight = 3;
		// Chcemy wypelnienia jPanela po osi OX
		gbc.weightx = 1;
		gbc.weighty = 0;
		
		// Zdobywamy liste produktow
		Iterator<Entry<Product, Integer>> listOfBoughtItems = client.getBasket().getProducts().entrySet().iterator();
		
		while(listOfBoughtItems.hasNext())
		{
			Entry<Product, Integer> entry = listOfBoughtItems.next();
			
			JLabel jLabel = new JLabel(entry.getKey().toStringOneLine() + " Lista produktow: " + entry.getValue());
			jLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
			jPanel.add(jLabel, gbc);
			
			gbc.gridx = 5;
			gbc.gridwidth = 2;
			
			JButton jButton = new JButton("Skasuj produkt z koszyka");
			productButtons.put(jButton, entry);
			jPanel.add(jButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridwidth = 7;
			gbc.gridy += 3;
			
			//jPanel.add(Box.createRigidArea(new Dimension(0,10)), gbc);
			
			gbc.gridwidth = 5;
			gbc.gridy += 1;
		}
		
		
		jFrame.add(jPanel);
		jFrame.setVisible(true);
	}
}
