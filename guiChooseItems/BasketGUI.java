package guiChooseItems;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chooseitems.Product;
import guiClient.SetClientInfoGUI;
import guiShop.MainGUI;

//Klasa stworzona przez Wiktora Sadowego
public class BasketGUI
{
	private JPanel jPanel;
	private MainGUI main;
	
	public JPanel getJPanel() 
	{
		return jPanel;
	}
	
	public BasketGUI(MainGUI main)
	{
		this.main = main;
		
		Dimension buttonSize = new Dimension(300,40);

		jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 7;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		JLabel titileJLabel = new JLabel("Zawartosc koszyka");
		titileJLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		titileJLabel.setFont(new Font("New Times Roman", Font.BOLD, 40));
		jPanel.add(titileJLabel, gbc);
		
		gbc.gridwidth = 5;
		gbc.gridy = 3;
		
		// Zdobywamy liste produktow, zeby moc ja wypisac na ekran
		Iterator<Entry<Product, Integer>> listOfBoughtItems = this.main.getClient().getBasket().getProducts().entrySet().iterator();
		
		while(listOfBoughtItems.hasNext())
		{
			Entry<Product, Integer> entry = listOfBoughtItems.next();
			
			JLabel jLabel = new JLabel(entry.getKey().toStringOneLine() + " Lista produktow: " + entry.getValue());
			jLabel.setBorder(new EmptyBorder(0, 10, 0, 5));
			jPanel.add(jLabel, gbc);
			
			gbc.gridx = 5;
			gbc.gridwidth = 2;
			
			JButton jButton = new JButton("Skasuj produkt z koszyka");
			jButton.addActionListener(new EraseItems(entry.getKey(), entry.getValue()));
			jPanel.add(jButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridwidth = 5;
			gbc.gridy += 3;
		}
		
		gbc.gridwidth = 7;
		
		JLabel priceJLabel = new JLabel("Cena koncowa: " + String.format("%.2f", this.main.getClient().getBasket().getPrice()) + " zl");
		priceJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
		priceJLabel.setFont(new Font("New Times Roman", Font.BOLD, 27));
		jPanel.add(priceJLabel, gbc);
		
		gbc.gridy += 3;
		gbc.gridheight = 1;
		
		JButton giveBasicInfoJButton = new JButton("Dokoncz transakcje");
		giveBasicInfoJButton.setPreferredSize(buttonSize);
		giveBasicInfoJButton.addActionListener(new GiveBasicInfo());
		if (this.main.getClient().getBasket().getProducts().size() == 0) {
			giveBasicInfoJButton.setEnabled(false);
		}
		jPanel.add(giveBasicInfoJButton, gbc);
		
		this.main.setButtonCursor(jPanel);
	
	}

	class EraseItems implements ActionListener
	{
		private Product product;
		private int maxNumberToErase;
		
		public EraseItems(Product product, int maxNumberToErase) 
		{
			this.product = product;
			this.maxNumberToErase = maxNumberToErase;
		}
		
		public void actionPerformed(ActionEvent event) 
		{
			main.changeLayoutToEraseItem(product, maxNumberToErase);
		}
	}
	
	class GiveBasicInfo implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			SetClientInfoGUI setInfoCategory = new SetClientInfoGUI(main);
			JPanel setInfoPanel = setInfoCategory.getjPanel();
			main.getCardPanel().add(setInfoPanel,"Delivery Page");
			main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
		}
	}
}
