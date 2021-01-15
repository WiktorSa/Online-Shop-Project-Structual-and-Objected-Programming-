package gui;

import java.awt.Button;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
	private Client client;
	private ArrayList<Product> items;
	private HashMap<JButton, Integer> selectItems = new LinkedHashMap<JButton, Integer>(); // Integer to numer w indeksie
	private JFrame jFrame;
	private JTextField selectIndexJTextField;
	private JButton selectOtherItemsButton; 
	private JButton goBackButton;
	
	public ChooseItemsSelectingItemsGUI(Client client, ArrayList<Product> items, int startingIndex) 
	{
		this.client = client;
		this.items = items;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Sklep");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(1050, 400);
		jFrame.setResizable(false);
		
		// Uzywamy GridBagLayout, zeby moc bezproblemowo stworzyc GUI
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 30;
		gbc.insets = new Insets(5, 5, 5, 0); // Odleglosc pomiedzy przedmiotami
		gbc.weightx = 0;
		gbc.weighty = 0;
		
		for (int i=startingIndex; i<startingIndex+3; i++) 
		{
			JLabel jLabel = new JLabel("<html><div style='text-align: center;'>" + ((i+1) + "." + "\n" + items.get(i).toString()).replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</div></html>");
			jLabel.setBorder(new EmptyBorder(10,10,10,10));
			jPanel.add(jLabel, gbc);
			
			gbc.gridy = 31;
			gbc.gridheight = 2;
			
			JButton buyButton = new JButton("Kup Produkt");
			buyButton.addActionListener(new BuyItems());
			buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			selectItems.put(buyButton, i);
			
			jPanel.add(buyButton, gbc); // buyButton ma inna wysokosc niz jLabel
			
			gbc.gridheight = 30;
			gbc.gridy = 0;
		
			gbc.gridx += 10;
		}
		
		// Reszta guzikow
		gbc.gridx = 10;
		gbc.gridheight = 1;
		
		gbc.gridy = 34;
		JLabel filler1 = new JLabel("");
		filler1.setBorder(new EmptyBorder(15,0,10,0));
		jPanel.add(filler1, gbc);
		
		// Nie proponujemy indeksow ktore nie istnieja
		int nextIndex = startingIndex + 4;
		if (nextIndex > items.size()) {
			nextIndex = items.size()-2;
		}
		gbc.gridy = 35;
		
		// Chcemy aby JTextField pozwalal tylko na wpisanie intow
		
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		
		selectIndexJTextField = new JTextField(String.valueOf(nextIndex), 20);
		jPanel.add(selectIndexJTextField, gbc);
		
		gbc.gridy = 36;
		selectOtherItemsButton = new JButton("Przegladaj produkty od podanego indeksu");
		selectOtherItemsButton.addActionListener(new SelectIndexOfItems());
		jPanel.add(selectOtherItemsButton, gbc);
		
		gbc.gridy = 37;
		goBackButton = new JButton("Cofnij sie");
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton, gbc);
		
		gbc.gridy = 38;
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
	
	class BuyItems implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			for (JButton jButton : selectItems.keySet())
			{
				if (event.getSource() == jButton) {
					new ChooseItemsBuyItemGUI(client, items.get(selectItems.get(jButton)), items);
					jFrame.dispose();
				}
			}
		}
	}
	
	// Zawsze wyswietlamy 3 przedmioty na ekranie
	class SelectIndexOfItems implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			try 
			{
				// Inna jest numeracja w ArrayList a inna wyswietla sie uzytkownikowi. Dlatego odejmujemy 1
				int index = Integer.parseInt(selectIndexJTextField.getText()) - 1;
				
				// Dla ujemnego indeksu cofamy sie do pierwszego przedmiotu
				if (index < 0) {
					new ChooseItemsSelectingItemsGUI(client, items, 0);
					jFrame.dispose();
				}
				
				else {
					// Dla indeksu powyzej limitu przedmiotow wyswietlamy 3 ostatnie przedmioty
					if (index+3 > items.size()) {
						new ChooseItemsSelectingItemsGUI(client, items, items.size()-3);
						jFrame.dispose();
					}
					else {
						new ChooseItemsSelectingItemsGUI(client, items, index);
						jFrame.dispose();
					}
				}
			} 
			
			// Gdyby uzytkownik wpadl na pomysl wpisania sad1431sf
			catch (java.lang.NumberFormatException e) 
			{
				JOptionPane.showMessageDialog(new JPanel(), "Wpisz poprawny indeks", "Error", JOptionPane.WARNING_MESSAGE);
			}
			
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


