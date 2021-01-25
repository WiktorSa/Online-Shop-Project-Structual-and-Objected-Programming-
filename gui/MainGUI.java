package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import chooseitems.ChooseItems;
import client.Client;
import client.UnregisteredClient;


public class MainGUI {
	
	JFrame jFrame;
	Client client;
	CardLayout cardLayout = new CardLayout();
	JPanel northPanel, cardPanel = new JPanel(), testPanel, basketPanel, logInPanel, registerPanel;
	ChooseItems chooseItems;
	ChooseItemsSelectingItemsGUI test;
	ChooseItemsBasketGUI basketGUI;
	ClientLogInGUI logInGUI;
	ClientRegisterGUI registerGUI;
	
	public JPanel getCardPanel() {
		return cardPanel;
	}
	
	public ChooseItems getChooseItems() {
		return chooseItems;
	}
	
	public CardLayout getCardLayout() {
		return cardLayout;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public MainGUI(Client client) {
		this.client = client;
		chooseItems = new ChooseItems();
		test = new ChooseItemsSelectingItemsGUI(this);
		testPanel = test.getPanel();

		jFrame = new JFrame();
		jFrame.setTitle("Sklep");
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setMinimumSize(new Dimension(900,600));
		
		createNorthPanel();
		createCardPanel();

		jFrame.pack();
		jFrame.setVisible(true);
		
	}
	
	private void createNorthPanel(){
		
		northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();

		
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton homeButton = new JButton(new ImageIcon("Ikony/house.png"));
		homeButton.addActionListener(new Home());
		leftPanel.add(homeButton);
		homeButton.setToolTipText("Powroc na strone glowna");
		homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		Dimension buttonDimension = new Dimension();
		buttonDimension.setSize(120, 45);

		
		if(this.client instanceof UnregisteredClient) {
			JButton loginButton = new JButton("Zaloguj sie");
			JButton registerButton = new JButton("Zarejestruj sie");
			
			loginButton.setPreferredSize(buttonDimension);
			loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			loginButton.addActionListener(new LogIn());
			
			registerButton.setPreferredSize(buttonDimension);
			registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			registerButton.addActionListener(new Register());
			
			rightPanel.add(loginButton);
			rightPanel.add(registerButton);
		}else {
			
			JLabel clientEmailLabel = new JLabel(client.getEmail());

			JButton logOutButton = new JButton("Wyloguj");
			
			logOutButton.setPreferredSize(buttonDimension);
			logOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			logOutButton.addActionListener(new LogOut());
			
			rightPanel.add(clientEmailLabel);
			rightPanel.add(logOutButton);
			
		}
	
		
		JButton basketButton = new JButton(new ImageIcon("Ikony/shopping-basket.png"));

		basketButton.setToolTipText("Pokaz koszyk");
		basketButton.addActionListener(new BasketTrigger());
		basketButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
	
		rightPanel.add(basketButton);
		
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		northPanel.add(leftPanel, gridBag);
		
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.weightx = 1.0;
		gridBag.weighty = 1.0;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_END;
		northPanel.add(rightPanel,gridBag);
		northPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		jFrame.getContentPane().add(BorderLayout.NORTH, northPanel);

		
	}
	public void resetNorthPanel() {
		jFrame.getContentPane().remove(northPanel);
		createNorthPanel();
		jFrame.revalidate();
	}
	
	private class Home implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			chooseItems = new ChooseItems();
			test = new ChooseItemsSelectingItemsGUI(MainGUI.this);
			testPanel = test.getPanel();
			cardPanel.add(testPanel,"Home Page");
			cardLayout.show(cardPanel,"Home Page");
			jFrame.revalidate();
			
		}
		
	}
	
	private class BasketTrigger implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			basketGUI = new ChooseItemsBasketGUI(client);
			basketPanel = basketGUI.getJPanel();
			cardPanel.add(basketPanel, "Basket Page");
			cardLayout.show(cardPanel, "Basket Page");
			jFrame.revalidate();
			
		}
		
	}
	
	private class LogIn implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			logInGUI = new ClientLogInGUI(MainGUI.this);
			logInPanel = logInGUI.getjPanel();
			cardPanel.add(logInPanel,"Log In Page");
			cardLayout.show(cardPanel, "Log In Page");
			jFrame.revalidate();
		}
		
	}
	
	private class Register implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			registerGUI = new ClientRegisterGUI(MainGUI.this);
			registerPanel = registerGUI.getjPanel();
			cardPanel.add(registerPanel,"Register Page");
			cardLayout.show(cardPanel, "Register Page");
			jFrame.revalidate();
			
		}
		
	}
	
	private class LogOut implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			client = new UnregisteredClient();
			cardLayout.show(cardPanel, "Home Page");
			resetNorthPanel();

		}
		
	}
	
	private void createCardPanel() {

		
		cardPanel.setLayout(cardLayout);
		cardPanel.add(testPanel,"Home Page");
		cardLayout.show(cardPanel, "Home Page");
		jFrame.getContentPane().add(BorderLayout.CENTER, cardPanel);
		
		
	}
	
	

}
