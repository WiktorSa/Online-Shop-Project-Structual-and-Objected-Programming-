package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import chooseitems.ChooseItems;
import chooseitems.Product;
import client.Client;
import client.RegisteredClient;

//Klasa stworzona przez Wiktora Sadowego 
public class ChooseItemsSelectingItemsGUI
{

	private ArrayList<Product> items ;
	// Integer to numer w indeksie
	private HashMap<JButton, Integer> selectItems = new LinkedHashMap<JButton, Integer>(); 
	private JPanel jPanel;
	private MainGUI main;
	
	public JPanel getPanel() {
		return jPanel;
	}
	
	// Uzywamy starting index, bo nie moge wyswietlic wszystkich produktow.
	// Uzytkownik wiec bedzie sobie wybieral jakie 3 produkty chce zobaczyc
	public ChooseItemsSelectingItemsGUI(MainGUI main) 
	{
		this.main = main;
		this.items = this.main.getChooseItems().getListOfProducts().get("Ksiazka");

		// Uzywamy GridBagLayout, zeby moc bezproblemowo stworzyc GUI
		jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
	
		int k = 0;
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		gbc.insets = new Insets(5, 5, 5, 0); // Odleglosc pomiedzy przedmiotami
		gbc.weightx = 0;
		gbc.weighty = 0;
		
		for (int i=0; i<items.size()/3; i++) 
		{
	
			gbc.gridx = 0;
			for(int j = 0;j<3;j++) {
				JPanel tmp = new JPanel();
				tmp.setPreferredSize(new Dimension(130,150));
				tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
//				tmp.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
				
				JLabel jLabel = new JLabel("<html><div style='text-align: center;'>" + ((k+1) + "." + "\n" + items.get(k).toString()).replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</div></html>");
				jLabel.setBorder(new EmptyBorder(10,10,10,10));
				jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				tmp.add(jLabel);
				
				JButton buyButton = new JButton("Kup Produkt");
				buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				buyButton.addActionListener(new BuyItems(buyButton));
				selectItems.put(buyButton, k);
				
				tmp.add(buyButton); // buyButton ma inna wysokosc niz jLabel
				jPanel.add(tmp, gbc);
				gbc.gridheight = 3;
				
				k++;	
				gbc.gridx += 1;
			}
			gbc.gridy+=3;
			
			
		}

		
		
	}
	
	
	class BuyItems implements ActionListener
	{
		private JButton button;
		public BuyItems(JButton button) {
			this.button = button;
		}
		
		public void actionPerformed(ActionEvent event) 
		{
//			new ChooseItemsBuyItemGUI(client, items.get(selectItems.get(button)), items);
			main.getClient().addAProductToClientBasket(items.get(selectItems.get(button)), 1);
			if (main.getClient() instanceof RegisteredClient) {
				((RegisteredClient) main.getClient()).saveClient();
			}
			//jFrame.dispose();		
				
		}
	}
	
	
	

}


