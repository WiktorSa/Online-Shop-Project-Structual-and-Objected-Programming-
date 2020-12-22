package client;

import java.util.Scanner;
import waysofdelivery.Dostawa;
import waysofdelivery.WaysOfDelivery;
import waysofpayments.WaysOfPayments;

public class Client
{
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String[][] basket;
	private WaysOfDelivery wayOfDelivery;
	private WaysOfPayments wayOfPayment;
	
	public Client() 
	{
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.phoneNumber = "";
		this.wayOfDelivery = null;
		this.wayOfPayment = null;
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
		return "Dane o kliencie: Imie: " + firstName + ", Nazwisko: " + lastName + ", Email: " + email + ", Numer telefonu: " + phoneNumber;
	}
	
	public void setBasket(String[][] basket) 
	{
		this.basket = basket;
	}

	public String[][] getBasket()
	{
		return basket;
	}
	
	@SuppressWarnings("resource")
	public void setClientInfo()
	{
		boolean shouldStopSettingClientInfo = false;
		
		while (!shouldStopSettingClientInfo)
		{
			System.out.println("Podaj imie");
			setCorrectFirstName();
			
			System.out.println("Podaj nazwisko");
			setCorrectLastName();
			
			System.out.println("Podaj adres email");
			setCorrectEmail();
			
			System.out.println("Podaj numer telefonu");
			setCorrectPhoneNumber();
			
			Scanner scanner = new Scanner(System.in);
			System.out.println(toString());
			System.out.println("Nacisnij 1 zeby potwierdzic swoje dane");
			String decision = scanner.nextLine();
			
			if (decision.equals("1")){
				shouldStopSettingClientInfo = true;
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void setCorrectFirstName()
	{
		boolean shouldStopSettingClientInfo = false;
		
		while (!shouldStopSettingClientInfo)
		{
			Scanner scanner = new Scanner(System.in);
			String firstName = scanner.nextLine();
			
			
			// Sprawdzam czy nie ma zadnych dziwnych znakow w imieniu
			char[] letters = firstName.toCharArray();
			for (int i=0; i<letters.length; i++)
			{
				if (!Character.isLetter(letters[i])) {
					System.out.println("Podaj poprawne imie");
					break;
				}
				
				// Sprawdzilismy cale imie i mozemy byc pewni, ze jest ono poprawne
				if (i==letters.length-1) {
					shouldStopSettingClientInfo = true;
				}
			}
			
			if (shouldStopSettingClientInfo) {
				this.firstName = firstName;
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void setCorrectLastName()
	{
		boolean shouldStopSettingClientInfo = false;
		
		while (!shouldStopSettingClientInfo)
		{
			Scanner scanner = new Scanner(System.in);
			String lastName = scanner.nextLine();
			
			// Sprawdzam czy nie ma zadnych dziwnych znakow w imieniu
			char[] letters = lastName.toCharArray();
			for (int i=0; i<letters.length; i++)
			{
				if (!Character.isLetter(letters[i])) {
					System.out.println("Podaj poprawne nazwisko");
					break;
				}
				
				// Sprawdzilismy cale nazwisko i mozemy byc pewni, ze jest ono poprawne
				if (i==letters.length-1) {
					shouldStopSettingClientInfo = true;
				}
			}
			
			if (shouldStopSettingClientInfo) {
				this.lastName = lastName;
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void setCorrectEmail()
	{
		boolean shouldStopSettingClientInfo = false;
		
		while (!shouldStopSettingClientInfo)
		{
			Scanner scanner = new Scanner(System.in);
			String email = scanner.nextLine();
			
			// Sprawdzam czy mail jest poprawny
			String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			if (email.matches(regex)) {
				this.email = email;
				shouldStopSettingClientInfo = true;
			}
			else {
				System.out.println("Podaj poprawny adres email");
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void setCorrectPhoneNumber()
	{
		boolean shouldStopSettingClientInfo = false;
		
		while (!shouldStopSettingClientInfo)
		{
			Scanner scanner = new Scanner(System.in);
			String phoneNumber = scanner.nextLine();
			
			if (phoneNumber.length() == 9) {
				char[] digits = phoneNumber.toCharArray();
				for (int i=0; i<9; i++)
				{
					if (!Character.isDigit(digits[i])){
						System.out.println("Podaj poprawny numer telefonu");
						break;
					}
					
					// Sprawdzilismy caly numer telefonu i mozemy byc pewni, ze to poprawny numer telefonu
					if (i==8) {
						shouldStopSettingClientInfo = true;
					}
				}
				
				if (shouldStopSettingClientInfo) {
					this.phoneNumber = phoneNumber;
				}
				
			}
			
			else {
				System.out.println("Podaj poprawny numer telefonu");
			}
		}
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

	public void setDeliveryInfo()
	{
		System.out.println(((Dostawa) wayOfDelivery).provideDeliveryInformations(this));
	}
	
	public void Pay()
	{
		wayOfPayment.pay(this);
	}
	
	public String getTransactionInfo()
	{
		String transactionInfo = "Informacje o transakcji\n";
		transactionInfo += getBasketContent();
		transactionInfo += ((Dostawa) wayOfDelivery).deliveryInfo() + "\n";
		transactionInfo = transactionInfo + "Cena: " + String.valueOf(getOverallPrice()) + "\n";
		
		if (wayOfPayment.isPaymentDone()) {
			transactionInfo += "Transakcja zostala oplacona";
		}
		
		else {
			transactionInfo += "Transakcja nie zostala oplacona. Zamowienie zostanie anulowane";
		}
		
		return transactionInfo;
	}
	
	private String getBasketContent()
	{
		String basketContent = "Zawartosc koszyka:\n";
		for (int i=0; i<basket.length; i++)
		{
			basketContent = basketContent + (i+1) + ". " + basket[i][1] + ", Cena: " + basket[i][2] + " Ilosc produktow: " + basket[i][3] + "\n"; 
		}
		return basketContent;
	}
	
	private double getOverallPrice()
	{
		double OverallPrice = 0;
		for (int i=0; i<basket.length; i++)
		{
			// Mnoze cene przez ilosc produktow
			OverallPrice = OverallPrice + Double.parseDouble(basket[i][2]) * Double.parseDouble(basket[i][3]);
		}
		OverallPrice += wayOfDelivery.getPrice();
		return OverallPrice;
	}
}
