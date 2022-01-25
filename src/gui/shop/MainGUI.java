package gui.shop;

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
import gui.chooseitems.BasketGUI;
import gui.chooseitems.BuyItemGUI;
import gui.chooseitems.EraseItemGUI;
import gui.chooseitems.SelectingItemsGUI;
import gui.client.LogInGUI;
import gui.client.RegisterGUI;
import gui.client.SetClientInfoGUI;
import gui.delivery.ParcelLockerSetInfo;
import gui.delivery.DeliveryGUI;
import gui.delivery.DeliverySelectingCategoryGUI;
import gui.payment.CardGUI;
import gui.payment.PaymentSelectingCategoryGUI;

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
		
		jFrame.getContentPane().add(BorderLayout.NORTH, northPanel); 
		cardPanel.setLayout(cardLayout);
		jFrame.getContentPane().add(BorderLayout.CENTER, cardPanel);
		
		jFrame.setResizable(true);
		jFrame.pack();
		jFrame.setVisible(true);
		setButtonCursor(cardPanel);
	}
	
	public void initialize()
	{
		changeLayoutOfNorthPanel();
		changeLayoutToSelectingItems();
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
	
	public void changeLayoutToSetClientInfo()
	{
		SetClientInfoGUI setInfoCategory = new SetClientInfoGUI(MainGUI.this);
		JPanel setInfoPanel = setInfoCategory.getjPanel();
		cardPanel.add(setInfoPanel,"Delivery Page");
		cardLayout.show(cardPanel, "Delivery Page");
	}
	
	public void changeLayoutToDeliverySelectingCategory()
	{
		DeliverySelectingCategoryGUI deliveryCategory = new DeliverySelectingCategoryGUI(MainGUI.this);
		JPanel deliveryPanel = deliveryCategory.getjPanel();
		cardPanel.add(deliveryPanel,"Delivery Page");
		cardLayout.show(cardPanel, "Delivery Page");
	}
	
	public void changeLayoutToPaymentSelectingCategory()
	{
		PaymentSelectingCategoryGUI goToPayment = new PaymentSelectingCategoryGUI(MainGUI.this);
		JPanel paymentPanel = goToPayment.getjPanel();
		cardPanel.add(paymentPanel,"Delivery Page");
		cardLayout.show(cardPanel, "Delivery Page");
	}
	
	public void changeLayoutToWaysOfDelivery()
	{
		DeliveryGUI wayOfDeliveryGUI = new DeliveryGUI(MainGUI.this);
		JPanel jPanel = wayOfDeliveryGUI.getjPanel();
		cardPanel.add(jPanel, "Delivery Page");
		cardLayout.show(cardPanel, "Delivery Page");
	}
	
	public void changeLayoutToParcelLocker()
	{
		ParcelLockerSetInfo goToParcelLocker = new ParcelLockerSetInfo(MainGUI.this);
		JPanel parcelLockerPanel = goToParcelLocker.getjPanel();
		cardPanel.add(parcelLockerPanel,"Delivery Page");
		cardLayout.show(cardPanel, "Delivery Page");
	}
	
	public void changeLayoutToCardPayment() {
		
		CardGUI cardGUI = new CardGUI(this);
		JPanel cardPaymentPanel = cardGUI.getjPanel();
		cardPanel.add(cardPaymentPanel, "Card Payment Page");
		cardLayout.show(cardPanel,"Card Payment Page");
		jFrame.setEnabled(true);
		
		
	}
	
	public void changeLayoutToFinalTransaction() 
	{
		FinalTransactionGUI finalGUI = new FinalTransactionGUI(MainGUI.this);
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
