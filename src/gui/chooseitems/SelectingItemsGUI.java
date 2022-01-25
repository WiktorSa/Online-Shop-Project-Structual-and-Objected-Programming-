package gui.chooseitems;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import chooseitems.ChooseItems;
import chooseitems.Book;
import chooseitems.Product;
import gui.shop.MainGUI;

public class SelectingItemsGUI{
	private JPanel outsidejPanel;
	private MainGUI main;
	
	public JPanel getPanel() {
		return outsidejPanel;
	}
	
	public SelectingItemsGUI(MainGUI main, ChooseItems chooseItems) {
		this.main = main;
		ArrayList<Product> items = chooseItems.getListOfProducts().get("Ksiazka");
		TreeMap<Product, Image> images = chooseItems.getImagesOfProducts();
		
		outsidejPanel = new JPanel();
		outsidejPanel.setLayout(new BoxLayout(outsidejPanel, BoxLayout.X_AXIS));
		
		// Rows of items are one under another
		JPanel allItemsJPanel = new JPanel();
		allItemsJPanel.setLayout(new BoxLayout(allItemsJPanel, BoxLayout.Y_AXIS));
		
		Iterator<Product> productsIterator = items.iterator();
		
		allItemsJPanel.add(Box.createRigidArea(new Dimension(0,10)));
		while (productsIterator.hasNext()){
			// There are three items in a row
			JPanel itemsJPanel = new JPanel();
			itemsJPanel.setLayout(new BoxLayout(itemsJPanel, BoxLayout.X_AXIS));
			
			for(int j = 0; j<5; j++) {
				Product product = productsIterator.next();
				Image image = images.get(product);
				
				// The simple description of the item is in the column
				JPanel singleItemJPanel = new JPanel();
				singleItemJPanel.setLayout(new BoxLayout(singleItemJPanel, BoxLayout.Y_AXIS));
				
				JLabel imageJLabel = new JLabel(new ImageIcon(image.getScaledInstance(190, 280, Image.SCALE_SMOOTH)));
				imageJLabel.setBorder(new EmptyBorder(5, 25, 5, 25));
				imageJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				singleItemJPanel.add(imageJLabel);
				
				JLabel namejLabel = new JLabel(product.getName());
				namejLabel.setBorder(new EmptyBorder(5,25,5,25));
				namejLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				singleItemJPanel.add(namejLabel);
				
				JLabel authorJLabel = new JLabel(((Book) product).getAuthor());
				authorJLabel.setBorder(new EmptyBorder(5,25,5,25));
				authorJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				singleItemJPanel.add(authorJLabel);
				
				JLabel pricejJLabel = new JLabel(String.valueOf(product.getPrice()) + " zl");
				pricejJLabel.setBorder(new EmptyBorder(5,25,5,25));
				pricejJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				singleItemJPanel.add(pricejJLabel);
				
				JButton buyButton = new JButton("Dodaj do koszyka");
				buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				buyButton.addActionListener(new BuyItems(product, image));
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
		jScrollPane.setAutoscrolls(true);
		outsidejPanel.add(jScrollPane);
		
		this.main.setButtonCursor(outsidejPanel);
	}
	
	class BuyItems implements ActionListener{
		private Product product;
	
		public BuyItems(Product product, Image image) {
			this.product = product;
		}
		
		public void actionPerformed(ActionEvent event) {
			main.changeLayoutToBuyItem(product);
		}
	}
}


