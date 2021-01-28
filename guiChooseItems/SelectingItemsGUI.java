package guiChooseItems;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import chooseitems.ChooseItems;
import chooseitems.Ksiazka;
import chooseitems.Product;
import guiShop.MainGUI;

//Klasa stworzona przez Wiktora Sadowego 
public class SelectingItemsGUI
{
	// Lista wszystkich produktow
	private ArrayList<Product> items;
	// Lista obrazow do produktow
	private HashMap<Product, Image> images;
	// Do kazdego guzika jest przypisany przedmiot
	private HashMap<JButton, Product> selectItems = new LinkedHashMap<JButton, Product>();
	private JPanel outsidejPanel;
	private MainGUI main;
	
	public JPanel getPanel() 
	{
		return outsidejPanel;
	}
	
	public SelectingItemsGUI(MainGUI main) 
	{
		this.main = main;
		ChooseItems chooseItems = new ChooseItems();
		this.items = chooseItems.getListOfProducts().get("Ksiazka");
		this.images = chooseItems.getImagesOfProducts();
		
		outsidejPanel = new JPanel();
		outsidejPanel.setLayout(new BoxLayout(outsidejPanel, BoxLayout.X_AXIS));
		
		// Rows of items are one under another
		JPanel allItemsJPanel = new JPanel();
		allItemsJPanel.setLayout(new BoxLayout(allItemsJPanel, BoxLayout.Y_AXIS));
		
		Iterator<Product> productsIterator = items.iterator();
		
		allItemsJPanel.add(Box.createRigidArea(new Dimension(0,10)));
		while (productsIterator.hasNext())
		{
			// There are three items in a row
			JPanel itemsJPanel = new JPanel();
			itemsJPanel.setLayout(new BoxLayout(itemsJPanel, BoxLayout.X_AXIS));
			
			for(int j = 0; j<5; j++) 
			{
				Product product = productsIterator.next();
				
				// The simple description of the item is in the column
				JPanel singleItemJPanel = new JPanel();
				singleItemJPanel.setLayout(new BoxLayout(singleItemJPanel, BoxLayout.Y_AXIS));
				
				JLabel imageJLabel = new JLabel(new ImageIcon(images.get(product).getScaledInstance(190, 280, Image.SCALE_SMOOTH)));
				imageJLabel.setBorder(new EmptyBorder(5, 25, 5, 25));
				imageJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				singleItemJPanel.add(imageJLabel);
				
				JLabel namejLabel = new JLabel(product.getName());
				namejLabel.setBorder(new EmptyBorder(5,25,5,25));
				namejLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				singleItemJPanel.add(namejLabel);
				
				JLabel authorJLabel = new JLabel(((Ksiazka) product).getAuthor());
				authorJLabel.setBorder(new EmptyBorder(5,25,5,25));
				authorJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				singleItemJPanel.add(authorJLabel);
				
				JLabel pricejJLabel = new JLabel(String.valueOf(product.getPrice()) + " zl");
				pricejJLabel.setBorder(new EmptyBorder(5,25,5,25));
				pricejJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				singleItemJPanel.add(pricejJLabel);
				
				JButton buyButton = new JButton("Kup");
				buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				buyButton.addActionListener(new BuyItems(buyButton));
				selectItems.put(buyButton, product);
				singleItemJPanel.add(buyButton); 
				
				itemsJPanel.add(singleItemJPanel);
				
				// If there are no more items to display on row we quit the loop
				if (!productsIterator.hasNext()) {
					break;
				}
			}
			
			allItemsJPanel.add(itemsJPanel);
			allItemsJPanel.add(Box.createRigidArea(new Dimension(0, 25)));
			
		}
		
		allItemsJPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JScrollPane jScrollPane = new JScrollPane(allItemsJPanel);
		jScrollPane.setAutoscrolls(true); // allows to autoscroll through items
		outsidejPanel.add(jScrollPane);
	}
	
	class BuyItems implements ActionListener
	{
		private JButton button;
		
		// In this way we can easily assign a button to a product
		public BuyItems(JButton button) 
		{
			this.button = button;
		}
		
		public void actionPerformed(ActionEvent event) 
		{
			main.changeLayoutToBuyItem(selectItems.get(button), images.get(selectItems.get(button)));
		}
	}
}


