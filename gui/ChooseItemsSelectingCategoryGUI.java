package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class ChooseItemsSelectingCategoryGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Client client;
	private ChooseItems chooseItems;
	private ArrayList<JButton> selectCategory = new ArrayList<JButton>();
	private JButton goBackButton;
	
	public ChooseItemsSelectingCategoryGUI(Client client, ChooseItems chooseItems) 
	{
		this.client = client;
		this.chooseItems = chooseItems;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
		setLocationRelativeTo(null);
		setTitle("Sklep");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 170 + 30*chooseItems.getCategories().size());
		setResizable(false);
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel jLabel1 = new JLabel("Wybierz kategorie", SwingConstants.CENTER);
		jLabel1.setFont(new Font("Times New Roman", Font.BOLD, 24));
		jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		jLabel1.setBorder(new EmptyBorder(0,0,20,0)); //top,left,bottom,right
		jPanel.add(jLabel1);
		
		for (String category : chooseItems.getCategories())
		{
			JButton jButton = new JButton(category);
			jButton.addActionListener(this);
			jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(jButton);
			selectCategory.add(jButton);
			
			JLabel filler = new JLabel("");
			filler.setAlignmentX(Component.CENTER_ALIGNMENT);
			filler.setBorder(new EmptyBorder(0,0,7,0));
			jPanel.add(filler);
		}
		
		String text = "";
		if (client instanceof RegisteredClient) {
			text = "Jestes zalogowany pod adresem email: " + client.getEmail();
		}
		else {
			text = "Jestes niezalogowany";
		}
		JLabel jLabel2 = new JLabel(text);
		jLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		jLabel2.setBorder(new EmptyBorder(10,0,10,0));
		jPanel.add(jLabel2);
		
		goBackButton = new JButton("Cofnij sie");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(this);
		jPanel.add(goBackButton);
		
		add(jPanel);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) 
	{
		for (JButton jButton : selectCategory)
		{
			if (event.getSource() == jButton) {
				new ChooseItemsSelectingItemsGUI(client, chooseItems.getListOfProducts().get(jButton.getText()), 0, chooseItems);
				dispose();
			}
		}
		
		if (event.getSource() == goBackButton) {
			new ShopGUI(client);
			dispose();
		}
	}
}
