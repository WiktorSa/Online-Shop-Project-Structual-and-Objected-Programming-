package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import chooseitems.Product;
import client.Client;
import client.RegisteredClient;

public class ChooseItemsEraseItemGUI 
{
	private Client client;
	private Product product;
	private JFrame jFrame;
	private JFormattedTextField numberOfItems;
	private JButton eraseButton;
	private JButton goBackButton;
	
	public ChooseItemsEraseItemGUI(Client client, Product product, int maxNumberToErase) // ArrayList jest uzywany tylko do cofniecia sie do listy produktow
	{
		this.client = client;
		this.product = product;
		
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
		
		JLabel inputingANumberJLabel = new JLabel("Wpisz ile sztuk powyzszego produktu chcesz usunac z koszyka");
		inputingANumberJLabel.setBorder(new EmptyBorder(0, 10, 5, 10));
		inputingANumberJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(inputingANumberJLabel);
		
		numberOfItems = new JFormattedTextField(onlyAllowNaturalNumbersUpToNumberOfItems(maxNumberToErase));
		numberOfItems.setText("1");
		numberOfItems.setMaximumSize(new Dimension(200, 30));
		numberOfItems.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(numberOfItems);
		
		// chce miec wolne miejsce pomiedzy JTextField a JButton
		jPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		eraseButton = new JButton("Usun z koszyka");
		eraseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		eraseButton.addActionListener(new EraseItem());
		jPanel.add(eraseButton);
		
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
	
	public NumberFormatter onlyAllowNaturalNumbersUpToNumberOfItems(int maxNumberToErase)
	{
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(maxNumberToErase);
		formatter.setAllowsInvalid(false);
		return formatter;
	}
	
	class EraseItem implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			int numberOfErasedProducts = Integer.parseInt(numberOfItems.getText().replaceAll("\\s+",""));
			client.changeContentOfTheBasket(false, product, numberOfErasedProducts);
			if (client instanceof RegisteredClient) {
				((RegisteredClient) client).saveClient();
			}
			JOptionPane.showMessageDialog(null, "Pomyslnie skasowano przedmiot w ilosci: " + numberOfErasedProducts);
			
			// Jezeli klient skasowal cala zawartosc koszyka to go cofamy do wyboru kategorii
			if (client.getBasket().getProducts().size() == 0) {
				new ChooseItemsSelectingCategoryGUI(client);
			}
			else {
				new ChooseItemsBasketGUI(client);
			}
			
			jFrame.dispose();
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new ChooseItemsBasketGUI(client);
			jFrame.dispose();
		}
	}
}
