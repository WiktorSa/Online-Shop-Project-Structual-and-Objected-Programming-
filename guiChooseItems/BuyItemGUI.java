package guiChooseItems;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import chooseitems.Product;
import client.RegisteredClient;
import guiShop.MainGUI;

//Klasa stworzona przez Wiktora Sadowego 
public class BuyItemGUI
{
	private JPanel jPanel;
	private JFormattedTextField numberOfItems;
	private MainGUI mainGUI;
	
	public JPanel getPanel()
	{
		return jPanel;
	}
	
	// ArrayList jest uzywany tylko do cofniecia sie do listy produktow
	public BuyItemGUI(MainGUI mainGUI, Product product, Image image)
	{
		this.mainGUI = mainGUI;
		
		jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
		
		JLabel imageJLabel = new JLabel(new ImageIcon(image.getScaledInstance(300, 450, Image.SCALE_SMOOTH)));
		imageJLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		imageJLabel.setBorder(new EmptyBorder(10, 30, 10, 10));
		jPanel.add(imageJLabel);
		
		JPanel insideJPanel = new JPanel();
		insideJPanel.setLayout(new BoxLayout(insideJPanel, BoxLayout.Y_AXIS));
		
		// Polecenie w HTML-u na ladne wyswietlanie tekstu (wstawiam entery we wlasciwych miejsach i umieszczam tekst na srodku)
		JLabel itemLabel = new JLabel("<html>" + (product.toString()).replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>", SwingConstants.CENTER);
		itemLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		itemLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		insideJPanel.add(itemLabel);
		
		insideJPanel.add(Box.createRigidArea(new Dimension(0, 25)));
		
		numberOfItems = new JFormattedTextField(onlyAllowNaturalNumbersUpTo999());
		numberOfItems.setText("1");
		numberOfItems.setMaximumSize(new Dimension(200, 30));
		numberOfItems.setAlignmentX(Component.CENTER_ALIGNMENT);
		insideJPanel.add(numberOfItems);
		
		// chce miec wolne miejsce pomiedzy JTextField a JButton
		insideJPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		JButton buyButton = new JButton("Dodaj do koszyka");
		buyButton.setPreferredSize(new Dimension(200, 30));
		buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buyButton.addActionListener(new BuyItem(product));
		insideJPanel.add(buyButton);
		
		jPanel.add(insideJPanel);
		
		jPanel.add(Box.createRigidArea(new Dimension(50,10)));
	
		this.mainGUI.setButtonCursor(jPanel);
	}
	

	public NumberFormatter onlyAllowNaturalNumbersUpTo999()
	{
		// Nie pozwalam uzytkownikowi na wpisanie niczego innego oprocz liczb naturalnych
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		// Daje limit 99 sztuk dla pojedynczej akcji kupna dla bezpieczenstwa
		formatter.setMaximum(99);
		formatter.setAllowsInvalid(false);
		return formatter;
	}
	
	class BuyItem implements ActionListener
	{
		private Product product;
		
		public BuyItem(Product product) 
		{
			this.product = product;
		}
		
		public void actionPerformed(ActionEvent event) 
		{
			int numberOfBoughtProducts = Integer.parseInt(numberOfItems.getText());
			mainGUI.getClient().addAProductToClientBasket(product, numberOfBoughtProducts);
			if (mainGUI.getClient() instanceof RegisteredClient) {
				((RegisteredClient) mainGUI.getClient()).saveClient();
			}
			mainGUI.changeLayoutToSelectingItems();
		}
	}
}
