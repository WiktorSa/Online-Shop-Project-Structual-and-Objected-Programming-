package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import chooseitems.ChooseItems;
import chooseitems.Product;
import client.Client;
import client.RegisteredClient;

public class ChooseItemsSelectingItemsGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Client client;
	private ChooseItems chooseItems;
	private ArrayList<Product> items;
	private HashMap<JButton, Integer> selectItems = new LinkedHashMap<JButton, Integer>();
	private JTextField selectIndexJTextField;
	private JButton selectOtherItemsButton; 
	private JButton goBackButton;
	
	public ChooseItemsSelectingItemsGUI(Client client, ArrayList<Product> items, int startingIndex, ChooseItems chooseItems) 
	{
		this.client = client;
		this.items = items;
		this.chooseItems = chooseItems;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
		setLocationRelativeTo(null);
		setTitle("Sklep");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 400);
		setResizable(false);
		
		// Uzywamy GridBagLayout, zeby moc bezproblemowo stworzyc GUI
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 30;
		// Chcemy wypelnienia calego jPanela
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		for (int i=startingIndex; i<startingIndex+3; i++) 
		{
			JLabel jLabel = new JLabel("<html>" + ((i+1) + "." + "\n" + items.get(i).toString()).replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
			jLabel.setBorder(new EmptyBorder(10,10,10,10));
			jPanel.add(jLabel, gbc);
			
			JButton buyButton = new JButton("Kup Produkt");
			buyButton.addActionListener(new SelectItems());
			selectItems.put(buyButton, i);
			gbc.gridy = 31;
			gbc.gridheight = 2;
			jPanel.add(buyButton, gbc); // buyButton ma inna wysokosc niz jLabel
			gbc.gridheight = 30;
			gbc.gridy = 0;
		
			gbc.gridx += 10;
		}
		
		gbc.gridx = 10;
		gbc.gridy = 35;
		gbc.gridheight = 1;
		selectIndexJTextField = new JTextField(20);
		selectIndexJTextField.setText(String.valueOf(startingIndex + 3));
		jPanel.add(selectIndexJTextField, gbc);
		
		gbc.gridy = 36;
		selectOtherItemsButton = new JButton("Przegladaj produkty od podanego indeksu");
		
		add(jPanel);
		setVisible(true);
	}
	
	// Few notes
	// First - if the user decides to search for a number with a negative index he will be left with the first three items
	// Second - if the user decides to search for a number that is above the number of items he will be left with the three last items
	// Remember - always display three items on screen
	// You will use Integer in HashMap to insure that the correct item will be sold to the user
}

class SelectItems implements ActionListener
{
	public void actionPerformed(ActionEvent event) 
	{
		// TODO Auto-generated method stub
	}
}
