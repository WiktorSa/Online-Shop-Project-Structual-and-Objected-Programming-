package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;


//chooseOptionJLabel.setBorder(new EmptyBorder(25,0,10,0)); //top,left,bottom,right
//Klasa stworzona przez Wiktora Sadowego 
public class ShopUnregisteredClientGUI
{
	private Client client;
	private JFrame jFrame;
	private JButton startShoppingButton;
	private JButton logInButton;
	private JButton registerButton;
	
	public ShopUnregisteredClientGUI(Client client) 
	{
		this.client = client;
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null); // Pseudo-centrowanie. Zamiast w lewym gornym oknie frame pojawi sie mniej wiecej na srodku ekranu
		jFrame.setTitle("Sklep");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(350, 220);
		jFrame.setResizable(false);
		
		// Tekst bedzie sie wyswietlal od gory do dolu
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel welcomeJLabel = new JLabel("Witamy w sklepie", SwingConstants.CENTER);
		welcomeJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		welcomeJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(welcomeJLabel);
		
		JLabel chooseOptionJLabel = new JLabel("Wybierz opcje");
		chooseOptionJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		chooseOptionJLabel.setBorder(new EmptyBorder(25,0,10,0)); //top,left,bottom,right
		jPanel.add(chooseOptionJLabel);
		
		startShoppingButton = new JButton("Rozpocznij zakupy jako niezalogowany klient");
		startShoppingButton.addActionListener(new StartShopping());
		startShoppingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(startShoppingButton);
		
		// Chce dodac wolne miejsce pomiedzy guzikami
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		logInButton = new JButton("Zaloguj sie");
		logInButton.addActionListener(new GoToLogIn());
		logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(logInButton);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		registerButton = new JButton("Zarejestruj sie");
		registerButton.addActionListener(new GoToRegister());
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(registerButton);
		
		JLabel unregisteredClientJLabel = new JLabel("Jestes niezalogowany");
		unregisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		unregisteredClientJLabel.setBorder(new EmptyBorder(20,5,10,5));
		jPanel.add(unregisteredClientJLabel);
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}

	class StartShopping implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new ChooseItemsSelectingCategoryGUI(client);
			jFrame.dispose(); 
		}
	}
	
	class GoToLogIn implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			//new ClientLogInGUI(client);
			jFrame.dispose();
		}
	}
	
	class GoToRegister implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			//new ClientRegisterGUI(client);
			jFrame.dispose();
		}
	}
}
