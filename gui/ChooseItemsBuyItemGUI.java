package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import chooseitems.Basket;
import chooseitems.Product;
import client.Client;
import client.RegisteredClient;

//Klasa stworzona przez Wiktora Sadowego 
public class ChooseItemsBuyItemGUI
{
	private Client client;
	private Product product;
	private ArrayList<Product> items;
	private JFrame jFrame;
	private JTextField numberOfItems;
	private JButton buyButton;
	private JButton goBackButton;
	
	public ChooseItemsBuyItemGUI(Client client, Product product, ArrayList<Product> items) // ArrayList jest uzywany tylko do cofniecia sie do listy produktow
	{
		this.client = client;
		this.product = product;
		this.items = items;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Sklep");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(500, 350);
		jFrame.setResizable(false);
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		// Polecenie w HTML-u na ladne wyswietlanie tekstu (wstawiam entery we wlasciwych miejsach i umieszczam tekst na srodku)
		JLabel itemLabel = new JLabel("<html><div style='text-align: center;'>" + (this.product.toString()).replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</div></html>", SwingConstants.CENTER);
		itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		itemLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		jPanel.add(itemLabel);
		
		JLabel inputingANumberJLabel = new JLabel("Wpisz ile sztuk powyzszego produktu chcesz kupic");
		inputingANumberJLabel.setBorder(new EmptyBorder(0, 10, 5, 10));
		inputingANumberJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(inputingANumberJLabel);
		
		numberOfItems = new JTextField(20);
		numberOfItems.setText("1");
		numberOfItems.setMaximumSize(new Dimension(200, 30));
		numberOfItems.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(numberOfItems);
		
		// chce miec wolne miejsce pomiedzy JTextField a JButton
		jPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		buyButton = new JButton("KUP");
		buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buyButton.addActionListener(new BuyItem());
		jPanel.add(buyButton);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		goBackButton = new JButton("Cofnij sie");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		
		String text = "";
		if (client instanceof RegisteredClient) {
			text = "Jestes zalogowany pod adresem email: " + client.getEmail();
		}
		else {
			text = "Jestes niezalogowany";
		}
		JLabel infoAboutClientStatelJLabel = new JLabel(text);
		infoAboutClientStatelJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoAboutClientStatelJLabel.setBorder(new EmptyBorder(10,0,10,0));
		jPanel.add(infoAboutClientStatelJLabel);
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	class BuyItem implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			try 
			{
				int numberOfBoughtProducts = Integer.parseInt(numberOfItems.getText());
				
				// Liczba produktow musi byc dodatnia
				if (numberOfBoughtProducts <= 0) {
					JOptionPane.showMessageDialog(new JPanel(), "Wpisz poprawna liczbe produktow", "Error", JOptionPane.WARNING_MESSAGE);
				}
				
				else {
					Basket basket = client.getBasket();
					basket.addAProductToTheBasket(product, numberOfBoughtProducts);
					client.setBasket(basket);
					JOptionPane.showMessageDialog(new JFrame(), "Pomyslnie zakupiono przedmiot/-y");
					new ChooseItemsSelectingItemsGUI(client, items, 0);
					jFrame.dispose();
				}
			} 
			
			// Gdyby uzytkownik wpadl na pomysl wpisania sad1431sf
			catch (java.lang.NumberFormatException e) 
			{
				JOptionPane.showMessageDialog(new JPanel(), "Wpisz poprawna liczbe produktow", "Error", JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new ChooseItemsSelectingItemsGUI(client, items, 0);
			jFrame.dispose();
		}
	}
}
