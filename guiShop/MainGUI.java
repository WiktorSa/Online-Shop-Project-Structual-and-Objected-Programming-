package guiShop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chooseitems.Product;
import client.Client;
import guiChooseItems.BasketGUI;
import guiChooseItems.BuyItemGUI;
import guiChooseItems.SelectingItemsGUI;
import guiClient.LogInGUI;
import guiClient.RegisterGUI;

/*
 * Informacje programistyczne
 * Ja bêdê próbowa³ zaimplementowaæ controllera
 * Jak obs³ugiwaæ to GUI
 * Tworzycie oddzieln¹ klasê 
 * Tam powinien byæ Jpanel oraz metoda, która pozwala na uzyskanie Jpanela
 * Do tej klasy przekazujecie MainGUI
 * Wewn¹trz tej klasy jest Jbutton, który ma actionlistenera a ten actionlistener odsyla do wlasciwej metody w MainGUI
 * Praktycznie w tej metodzie wystarczy skopiowac kod z innej metody i podmienic nazwy i klasy
 * To chyba wszystko wyjasnia
 */

public class MainGUI 
{
	private JFrame jFrame;
	private Client client;
	private JPanel northPanel = new JPanel();
	private JPanel cardPanel = new JPanel();
	private CardLayout cardLayout = new CardLayout();
	
	public MainGUI(Client client) 
	{
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
	
	public void changeToBasket()
	{
		BasketGUI basketGUI = new BasketGUI(client);//NOTE: pozniej trzeba bedzie zmienic client na MainGUI.this, aby moc zmienic cardLayout aby pokazywal odpowiedni panel 
		JPanel basketPanel = basketGUI.getJPanel();//NOTE: ustawiamy basketPanel na panel utworzony w klasie dalej tak samo ja w createCardPanel()
		cardPanel.add(basketPanel, "Basket Page");
		cardLayout.show(cardPanel, "Basket Page");
		jFrame.revalidate();
	}
	
	public void changeToLogIn()
	{
		LogInGUI logInGUI = new LogInGUI(MainGUI.this);
		JPanel logInPanel = logInGUI.getjPanel();
		cardPanel.add(logInPanel,"Log In Page");
		cardLayout.show(cardPanel, "Log In Page");
		jFrame.revalidate();
	}
	
	public void changeToRegister()
	{
		RegisterGUI registerGUI = new RegisterGUI(MainGUI.this);
		JPanel registerPanel = registerGUI.getjPanel();
		cardPanel.add(registerPanel,"Register Page");
		cardLayout.show(cardPanel, "Register Page");
		jFrame.revalidate();
	}
	
	public void changeLayoutToSelectingItems()
	{
		SelectingItemsGUI selectItems = new SelectingItemsGUI(MainGUI.this);
		JPanel selectItemsPanel = selectItems.getPanel();
		cardPanel.add(selectItemsPanel,"Home Page");
		cardLayout.show(cardPanel,"Home Page");
		jFrame.revalidate();
	}

	public void changeLayoutToBuyItem(Product product, Image image)
	{
		BuyItemGUI buyItemGUI = new BuyItemGUI(MainGUI.this, product, image);
		JPanel buyItemPanel = buyItemGUI.getPanel();
		cardPanel.add(buyItemPanel, "Buy Item");
		cardLayout.show(cardPanel, "Buy Item");
		jFrame.revalidate();
	}
}
