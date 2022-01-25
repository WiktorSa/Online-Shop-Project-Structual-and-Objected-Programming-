package client;

import java.io.Serializable;
import chooseitems.ShoppingBasket;
import chooseitems.Product;
import waysofdelivery.WaysOfDelivery;
import waysofpayments.WaysOfPayments;

public abstract class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String phoneNumber;
	protected ShoppingBasket basket;
	transient protected WaysOfDelivery wayOfDelivery;
	transient protected WaysOfPayments wayOfPayment;
	
	public Client() {
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.phoneNumber = "";
		this.basket = new ShoppingBasket();
		this.wayOfDelivery = null;
		this.wayOfPayment = null;
	}
	
	public Client(Client client) {
		this.firstName = client.getFirstName();
		this.lastName = client.getLastName();
		this.email = client.getEmail();
		this.phoneNumber = client.getPhoneNumber();
		this.basket = client.getBasket();
		this.wayOfDelivery = client.getWaysOfDelivery();
		this.wayOfPayment = client.getWayOfPayment();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() {
		return "Imie: " + firstName + ", Nazwisko: " + lastName + ", Email: " + email + ", Numer telefonu: " + phoneNumber;
	}
	
	public void setBasket(ShoppingBasket basket) {
		this.basket = basket;
	}

	public ShoppingBasket getBasket() {
		return basket;
	}
	
	public void setWayOfDelivery(WaysOfDelivery wayOfDelivery) {
		this.wayOfDelivery = wayOfDelivery;
	}
	
	public WaysOfDelivery getWaysOfDelivery() {
		return wayOfDelivery;
	}
	
	public void setWayOfPayment(WaysOfPayments wayOfPayment) {
		this.wayOfPayment = wayOfPayment;
	}

	public WaysOfPayments getWayOfPayment() {
		return wayOfPayment;
	}
	
	public void addAProductToClientBasket(Product product, int numberOfProducts) {
		basket.addProductToTheBasket(product, numberOfProducts);
	}
	
	public void eraseAProductFromClientBasket(Product product, int numberOfProducts){
		basket.eraseAProductFromTheBasket(product, numberOfProducts);
	}

	public String getTransactionInfo() {
		String transactionInfo = "";
		transactionInfo += getBasket().toString();
		transactionInfo = transactionInfo + "Do zaplaty: " + String.format("%.2f", basket.getPrice() + wayOfDelivery.getPrice()) + " (z wliczona dostawa)\n\n";
		transactionInfo += wayOfDelivery.toString() + "\n";
		return transactionInfo;
	}
}
