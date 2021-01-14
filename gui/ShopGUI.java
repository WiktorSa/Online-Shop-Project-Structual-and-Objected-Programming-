package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class ShopGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Client client;
	private JButton startShoppingButton;
	private JButton logInOrRegisterButton;
	
	public ShopGUI(Client client) 
	{
		this.client = client;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
		setLocationRelativeTo(null); // Pseudo-centrowanie. Zamiast w lewym gornym oknie frame pojawi sie mniej wiecej na srodku ekranu
		setTitle("Sklep");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 220);
		setResizable(false);
		
		// Tekst bedzie sie wyswietlal od gory do dolu
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel jLabel1 = new JLabel("Witamy w sklepie", SwingConstants.CENTER);
		jLabel1.setFont(new Font("Times New Roman", Font.BOLD, 24));
		jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(jLabel1);
		
		JLabel jLabel2 = new JLabel("Wybierz opcje");
		jLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		jLabel2.setBorder(new EmptyBorder(25,0,10,0)); //top,left,bottom,right
		jPanel.add(jLabel2);
		
		startShoppingButton = new JButton("Rozpocznij zakupy jako niezalogowany klient");
		startShoppingButton.addActionListener(this);
		startShoppingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(startShoppingButton);
		
		// Chce dodac wolne miejsce pomiedzy wyborami
		JLabel filler = new JLabel("");
		filler.setAlignmentX(Component.CENTER_ALIGNMENT);
		filler.setBorder(new EmptyBorder(0,0,7,0));
		jPanel.add(filler);
		
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
		JLabel jLabel3 = new JLabel(text);
		jLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
		jLabel3.setBorder(new EmptyBorder(20,0,0,0));
		jPanel.add(jLabel3);
		
		add(jPanel);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource() == startShoppingButton) {
			new ChooseItemsSelectingCategoryGUI(client, new ChooseItems());
			dispose(); 
		}
		
		if (event.getSource() == logInOrRegisterButton) {
			logInOrRegisterButton.setText("Jeszcze nie zaimplementowane");
		}
	}
}
