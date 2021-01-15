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

import chooseitems.ChooseItems;
import client.Client;
import client.RegisteredClient;


//chooseOptionJLabel.setBorder(new EmptyBorder(25,0,10,0)); //top,left,bottom,right
//Klasa stworzona przez Wiktora Sadowego 
public class ShopGUI implements ActionListener
{
	private Client client;
	private JFrame jFrame;
	private JButton startShoppingButton;
	private JButton logInOrRegisterButton;
	
	public ShopGUI(Client client) 
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
		
		startShoppingButton = new JButton("Rozpocznij zakupy jako niezalogowany klient");
		startShoppingButton.addActionListener(this);
		startShoppingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(startShoppingButton);
		
		// Chce dodac wolne miejsce pomiedzy guzikami
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		logInOrRegisterButton = new JButton("Zarejestruj sie lub zaloguj");
		logInOrRegisterButton.addActionListener(this);
		logInOrRegisterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(logInOrRegisterButton);
		
		String text = "";
		if (client instanceof RegisteredClient) {
			text = "Jestes zalogowany pod adresem email: " + client.getEmail();
		}
		else {
			text = "Jestes niezalogowany";
		}
		JLabel infoAboutClientStatelJLabel = new JLabel(text);
		infoAboutClientStatelJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoAboutClientStatelJLabel.setBorder(new EmptyBorder(20,0,0,0));
		jPanel.add(infoAboutClientStatelJLabel);
		
		jFrame.add(jPanel);
		jFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource() == startShoppingButton) {
			new ChooseItemsSelectingCategoryGUI(client, new ChooseItems());
			jFrame.dispose(); 
		}
		
		if (event.getSource() == logInOrRegisterButton) {
			logInOrRegisterButton.setText("Jeszcze nie zaimplementowane");
		}
	}
}
