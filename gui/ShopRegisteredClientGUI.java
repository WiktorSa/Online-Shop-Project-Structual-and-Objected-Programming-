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
import client.RegisteredClient;
import client.UnregisteredClient;

public class ShopRegisteredClientGUI implements ActionListener
{
	private Client client;
	private JFrame jFrame;
	private JButton startShoppingButton;
	private JButton logOffButton;
	
	public ShopRegisteredClientGUI(Client client) 
	{
		this.client = client;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
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
		welcomeJLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		welcomeJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(welcomeJLabel);
		
		JLabel chooseOptionJLabel = new JLabel("Wybierz opcje");
		chooseOptionJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		chooseOptionJLabel.setBorder(new EmptyBorder(25,0,10,0)); //top,left,bottom,right
		jPanel.add(chooseOptionJLabel);
		
		startShoppingButton = new JButton("Rozpocznij zakupy jako zalogowany klient");
		startShoppingButton.addActionListener(this);
		startShoppingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(startShoppingButton);
		
		// Chce dodac wolne miejsce pomiedzy guzikami
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		logOffButton = new JButton("Wyloguj sie");
		logOffButton.addActionListener(this);
		logOffButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(logOffButton);
		
		JLabel RegisteredClientJLabel = new JLabel("Jestes zalogowany pod adresem email");
		RegisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		RegisteredClientJLabel.setBorder(new EmptyBorder(10,5,8,5));
		jPanel.add(RegisteredClientJLabel);
		
		JLabel emailJLabel = new JLabel(client.getEmail());
		emailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailJLabel.setBorder(new EmptyBorder(0,5,10,5));
		jPanel.add(emailJLabel);
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource() == startShoppingButton) {
			new ChooseItemsSelectingCategoryGUI(client);
			jFrame.dispose(); 
		}
		
		if (event.getSource() == logOffButton) {
			client = new UnregisteredClient(client);
			new ShopUnregisteredClientGUI(client);
			jFrame.dispose();
		}
	}
}
