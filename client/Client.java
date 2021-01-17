package client;

import java.io.Serializable;
import chooseitems.Basket;
import chooseitems.Product;
import waysofdelivery.WaysOfDelivery;
import waysofpayments.WaysOfPayments;

// Klase tworzyl Wiktor Sadowy i Szymon Sawczuk i Jan Skibinski
public abstract class Client implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String phoneNumber;
	protected Basket basket;
	transient protected WaysOfDelivery wayOfDelivery; //NOTE(Szymon): Nie zapisujemy
	transient protected WaysOfPayments wayOfPayment;  //NOTE(Szymon): Nie zapisujemy
	protected boolean wasDeliveryChosen;
	
	public Client() 
	{
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.phoneNumber = "";
		this.basket = new Basket();
		this.wayOfDelivery = null;
		this.wayOfPayment = null;
		this.wasDeliveryChosen = false;
	}
	
	public Client(Client client)
	{
		this.firstName = client.getFirstName();
		this.lastName = client.getLastName();
		this.email = client.getEmail();
		this.phoneNumber = client.getPhoneNumber();
		this.basket = client.getBasket();
		this.wayOfDelivery = client.getWaysOfDelivery();
		this.wayOfPayment = client.getWayOfPayment();
		this.wasDeliveryChosen = client.getWasDeliveryChosen();
	}
	
	public String getFirstName() 
	{
		return firstName;
	}
	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	public String getLastName() 
	{
		return lastName;
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() 
	{
		return "Imie: " + firstName + ", Nazwisko: " + lastName + ", Email: " + email + ", Numer telefonu: " + phoneNumber;
	}
	
	public void setBasket(Basket basket) 
	{
		this.basket = basket;
	}

	public Basket getBasket()
	{
		return basket;
	}
	
	public void setWayOfDelivery(WaysOfDelivery wayOfDelivery)
	{
		this.wayOfDelivery = wayOfDelivery;
	}
	
	public WaysOfDelivery getWaysOfDelivery()
	{
		return wayOfDelivery;
	}
	
	public void setWayOfPayment(WaysOfPayments wayOfPayment)
	{
		this.wayOfPayment = wayOfPayment;
	}

	public WaysOfPayments getWayOfPayment()
	{
		return wayOfPayment;
	}
	
	public boolean getWasDeliveryChosen()
	{
		return wasDeliveryChosen;
	}
	
	public void setDeliveryChosen(boolean wasDeliveryChosen)
	{
		this.wasDeliveryChosen = wasDeliveryChosen;
	}
	
	public void changeContentOfTheBasket(boolean didBuy, Product product, int numberOfProducts)
	{
		if(didBuy) {
			basket.addAProductToTheBasket(product, numberOfProducts);
		}
		else {
			basket.eraseAProductFromTheBasket(product, numberOfProducts);
		}
	}
	
	public abstract void chooseWayOfDelivery();

	public abstract boolean setDeliveryInfo();

	public abstract void chooseWayOfPayment();
	
	public abstract boolean initiatePay();
	
	public abstract String getTransactionInfo();
}
