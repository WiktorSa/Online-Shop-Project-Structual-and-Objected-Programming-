package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import chooseitems.ChooseItems;
import client.Client;
import client.RegisteredClient;

//Klasa stworzona przez Wiktora Sadowego 
public class ChooseItemsSelectingCategoryGUI
{
	private Client client;
	private ChooseItems chooseItems;
	private ArrayList<JButton> selectCategory = new ArrayList<JButton>();
	private JFrame jFrame;
	private JButton goToBasketButton;
	private JButton goBackButton;
	
	public ChooseItemsSelectingCategoryGUI(Client client) 
	{
		this.client = client;
		this.chooseItems = new ChooseItems();
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Sklep");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Wybierz kategorie", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		
		for (String category : chooseItems.getCategories())
		{
			JButton jButton = new JButton(category);
			jButton.addActionListener(new SelectCategory());
			jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(jButton);
			selectCategory.add(jButton);
			
			// Zeby guziki na siebie nie nachodzily
			jPanel.add(Box.createRigidArea(new Dimension(0,7))); 
		}
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10))); 
		
		goToBasketButton = new JButton("Przejdz do koszyka");
		goToBasketButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goToBasketButton.addActionListener(new GoToBasket());
		jPanel.add(goToBasketButton);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		goBackButton = new JButton("Cofnij sie");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		
		if (client instanceof RegisteredClient) {
			JLabel RegisteredClientJLabel = new JLabel("Jestes zalogowany pod adresem email");
			RegisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			RegisteredClientJLabel.setBorder(new EmptyBorder(10,5,8,5));
			jPanel.add(RegisteredClientJLabel);
			
			JLabel emailJLabel = new JLabel(client.getEmail());
			emailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			emailJLabel.setBorder(new EmptyBorder(0,5,10,5));
			jPanel.add(emailJLabel);
		}
		
		else {
			JLabel unregisteredClientJLabel = new JLabel("Jestes niezalogowany");
			unregisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			unregisteredClientJLabel.setBorder(new EmptyBorder(10,5,10,5));
			jPanel.add(unregisteredClientJLabel);
		}
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	class SelectCategory implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			for (JButton jButton : selectCategory)
			{
				if (event.getSource() == jButton) {
					new ChooseItemsSelectingItemsGUI(client, chooseItems.getListOfProducts().get(jButton.getText()), 0);
					jFrame.dispose();
				}
			}
		}
	}
	
	class GoToBasket implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			// Nie wyswietlamy listy produktow gdy ich klient nie ma w koszyku
			if (client.getBasket().getProducts().size() == 0) {
				JOptionPane.showMessageDialog(new JFrame(), "Pusty koszyk. Prosimy o wybranie produktow");
			}
			
			else {
				new ChooseItemsBasketGUI(client);
				jFrame.dispose();
			}
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			if (client instanceof RegisteredClient) {
				new ShopRegisteredClientGUI(client);
			}
			else {
				new ShopUnregisteredClientGUI(client);
			}
			jFrame.dispose();
		}
	}
}
