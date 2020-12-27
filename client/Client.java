package client;

import java.util.Scanner;

import chooseitems.ChooseItems;
import waysofdelivery.Dostawa;
import waysofdelivery.Kurier;
import waysofdelivery.Osobisty;
import waysofdelivery.Paczkomat;
import waysofdelivery.WaysOfDelivery;
import waysofpayments.Blik;
import waysofpayments.Card;
import waysofpayments.Paypal;
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
	
	public void doShopping() 
	{
		ChooseItems chooseItems = new ChooseItems();
		basket = chooseItems.doShopping();
	}
	
	@SuppressWarnings("resource")
	public void setClientInfo()
	{
		boolean shouldStopSettingClientInfo = false;
		Scanner scanner = new Scanner(System.in);
		
		while (!shouldStopSettingClientInfo)
		{
			setCorrectFirstName();
			setCorrectLastName();
			setCorrectEmail();
			setCorrectPhoneNumber();
			
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
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Podaj imie");
		while (!shouldStopSettingClientInfo)
		{
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
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Podaj nazwisko");
		while (!shouldStopSettingClientInfo)
		{
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
		Scanner scanner = new Scanner(System.in);

		System.out.println("Podaj adres email");
		while (!shouldStopSettingClientInfo)
		{
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
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Podaj numer telefonu");
		while (!shouldStopSettingClientInfo)
		{
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

	@SuppressWarnings("resource")
	public void chooseWayOfDelivery()
	{
		WaysOfDelivery[] waysOfDelivery = {new Paczkomat(), new Kurier(), new Osobisty()};
		boolean wasWayOfDeliveryChosen = false;
		Scanner scanner = new Scanner(System.in);
		int chosenWayOfDelivery = 0;
		
		while (!wasWayOfDeliveryChosen)
		{
			for (int i=0; i<waysOfDelivery.length; i++)
			{
				System.out.println(i+1 + ". " + waysOfDelivery[i].getName() + " Cena: " + waysOfDelivery[i].getPrice());
			}
			System.out.println("Wybierz metode dostawy");
			
			try 
			{
				chosenWayOfDelivery = scanner.nextInt();
				scanner.nextLine();
				
				if (chosenWayOfDelivery>0 && chosenWayOfDelivery<=waysOfDelivery.length) {
					System.out.println("Wybrano metode dostawy");
					wasWayOfDeliveryChosen = true;
				}
				else {
					System.out.println("Nie ma takiej opcji dostawy");
				}
			} 
			
			catch (java.util.InputMismatchException e) 
			{
				System.out.println("Nie ma takiej opcji dostawy");
			}
		}
		
		this.wayOfDelivery = waysOfDelivery[chosenWayOfDelivery-1];
	}

	public void setDeliveryInfo()
	{
		System.out.println(((Dostawa) wayOfDelivery).provideDeliveryInformations(this));
	}

	@SuppressWarnings("resource")
	public void chooseWayOfPayment()
	{
		WaysOfPayments[] waysOfPayments = {new Blik(), new Card(), new Paypal()};
		boolean wasWayOfPaymentChosen = false;
		Scanner scanner = new Scanner(System.in);
		int chosenWayOfPayment = 0;
		
		while (!wasWayOfPaymentChosen)
		{
			for (int i=0; i<waysOfPayments.length; i++)
			{
				System.out.println(i+1 + ". " + waysOfPayments[i].getName());
			}
			System.out.println("Wybierz metode zaplaty");
			
			try 
			{
				chosenWayOfPayment = scanner.nextInt();
				scanner.nextLine();
				
				if (chosenWayOfPayment>0 && chosenWayOfPayment<=waysOfPayments.length) {
					System.out.println("Wybrano metode zaplaty");
					wasWayOfPaymentChosen = true;
				}
				else {
					System.out.println("Nie ma takiej opcji zaplaty");
				}
			} 
			
			catch (java.util.InputMismatchException e) 
			{
				System.out.println("Nie ma takiej opcji zaplaty");
			}
		}
		
		this.wayOfPayment = waysOfPayments[chosenWayOfPayment-1];
	} 
	
	public boolean Pay()
	{
		wayOfPayment.pay(this);
		return wayOfPayment.isPaymentDone();
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
			transactionInfo += "Nie dokonano platnosci za produkty";
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
