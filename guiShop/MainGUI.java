package guiShop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import chooseitems.ChooseItems;
import chooseitems.Product;
import client.Client;
import guiChooseItems.BasketGUI;
import guiChooseItems.BuyItemGUI;
import guiChooseItems.EraseItemGUI;
import guiChooseItems.SelectingItemsGUI;
import guiClient.LogInGUI;
import guiClient.RegisterGUI;
import guiWaysOfPayment.CardGUI;

public class MainGUI 
{
	private JFrame jFrame;
	private ChooseItems chooseItems;
	private Client client;
	private JPanel northPanel = new JPanel();
	private JPanel cardPanel = new JPanel();
	private CardLayout cardLayout = new CardLayout();
	
	public MainGUI(Client client) 
	{
		chooseItems = new ChooseItems();
		this.client = client;

		jFrame = new JFrame();
		jFrame.setTitle("Sklep");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setMinimumSize(new Dimension(900,600));
		jFrame.setExtendedState(jFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		// Dokonujemy tych zabiegow, aby potem nie bylo problemow przy tworzeniu interfejsu
		jFrame.getContentPane().add(BorderLayout.NORTH, northPanel); // Pozycjonowanie labela z wyprzedzeniem
		cardPanel.setLayout(cardLayout); //NOTE: ustawiam odpowiedni layout
		jFrame.getContentPane().add(BorderLayout.CENTER, cardPanel); // Pozycjonowanie labela z wyprzedzeniem
		
		changeLayoutOfNorthPanel();
		changeLayoutToSelectingItems();

		jFrame.setResizable(true);
		jFrame.pack();
		jFrame.setVisible(true);
		setButtonCursor(cardPanel);
		
	}
	
	public JFrame getjFrame() {
		return jFrame;
	}
	
	public JPanel getCardPanel() 
	{
		return cardPanel;
	}
	
	public CardLayout getCardLayout() 
	{
		return cardLayout;
	}
	
	public Client getClient() 
	{
		return client;
	}
	
	public void setClient(Client client) 
	{
		this.client = client;
	}
	
	public void changeLayoutOfNorthPanel()
	{
		jFrame.getContentPane().remove(northPanel);
		NorthPanelGUI northPanelGUI = new NorthPanelGUI(MainGUI.this);
		northPanel = northPanelGUI.getjPanel();
		jFrame.getContentPane().add(BorderLayout.NORTH, northPanel);
		jFrame.revalidate();//NOTE: odswieza frame'a
	}
	
	public void changeLayoutToBasket()
	{
		BasketGUI basketGUI = new BasketGUI(MainGUI.this);//NOTE: pozniej trzeba bedzie zmienic client na MainGUI.this, aby moc zmienic cardLayout aby pokazywal odpowiedni panel 
		JPanel basketPanel = basketGUI.getJPanel();//NOTE: ustawiamy basketPanel na panel utworzony w klasie dalej tak samo ja w createCardPanel()
		cardPanel.add(basketPanel, "Basket Page");
		cardLayout.show(cardPanel, "Basket Page");
		jFrame.revalidate();
	}
	
	public void changeLayoutToLogIn()
	{
		LogInGUI logInGUI = new LogInGUI(MainGUI.this);
		JPanel logInPanel = logInGUI.getjPanel();
		cardPanel.add(logInPanel,"Log In Page");
		cardLayout.show(cardPanel, "Log In Page");
		jFrame.revalidate();
	}
	
	public void changeLayoutToRegister()
	{
		RegisterGUI registerGUI = new RegisterGUI(MainGUI.this);
		JPanel registerPanel = registerGUI.getjPanel();
		cardPanel.add(registerPanel,"Register Page");
		cardLayout.show(cardPanel, "Register Page");
		jFrame.revalidate();
	}
	
	public void changeLayoutToSelectingItems()
	{
		SelectingItemsGUI selectItems = new SelectingItemsGUI(MainGUI.this, chooseItems);
		JPanel selectItemsPanel = selectItems.getPanel();
		cardPanel.add(selectItemsPanel,"Home Page");
		cardLayout.show(cardPanel,"Home Page");
		jFrame.revalidate();
	}

	public void changeLayoutToBuyItem(Product product)
	{
		BuyItemGUI buyItemGUI = new BuyItemGUI(MainGUI.this, product, chooseItems.getImagesOfProducts().get(product));
		JPanel buyItemPanel = buyItemGUI.getPanel();
		cardPanel.add(buyItemPanel, "Buy Item");
		cardLayout.show(cardPanel, "Buy Item");
		jFrame.revalidate();
	}

	public void changeLayoutToEraseItem(Product product, int maxNumberToErase)
	{
		EraseItemGUI eraseItemGUI = new EraseItemGUI(MainGUI.this, product, chooseItems.getImagesOfProducts().get(product), maxNumberToErase);
		JPanel buyItemPanel = eraseItemGUI.getPanel();
		cardPanel.add(buyItemPanel, "Buy Item");
		cardLayout.show(cardPanel, "Buy Item");
		jFrame.revalidate();
	}
	
	public void changeLayoutToCardPayment() {
		
		CardGUI cardGUI = new CardGUI(this);
		JPanel cardPaymentPanel = cardGUI.getjPanel();
		cardPanel.add(cardPaymentPanel, "Card Payment Page");
		cardLayout.show(cardPanel,"Card Payment Page");
		jFrame.setEnabled(true);
		
		
	}
	
	public void changeLayoutToFinalTransaction() {
		
		FinalTransactionGUI finalGUI = new FinalTransactionGUI(client);
		JPanel finalGUIPanel = finalGUI.getjPanel();
		cardPanel.add(finalGUIPanel, "Final Page");
		cardLayout.show(cardPanel,"Final Page");
		jFrame.setEnabled(true);
		
	}
	
	public void setButtonCursor(JComponent component) {
		
		 for (Component comp : component.getComponents()) {

		        if (comp instanceof JButton) {

		        	comp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		        } else if (comp instanceof JComponent) {

		            setButtonCursor((JComponent)comp);

		        }

		    }
	}
}
