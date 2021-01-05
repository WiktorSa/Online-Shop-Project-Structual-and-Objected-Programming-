package client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

import chooseitems.Basket;
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

public class Client implements Serializable
{
	private static final long serialVersionUID = 4480120372403237397L;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
	private Basket[] basket;
	private double price; //NOTE(Szymon): Potrzebne, aby zapisac informacje o koszcie zakupow klienta
	private boolean isSaved; //NOTE(Szymon): Informacja czy dany klient jest juz zapisany
	transient private WaysOfDelivery wayOfDelivery; //NOTE(Szymon): Nie zapisujemy
	transient private WaysOfPayments wayOfPayment;  //NOTE(Szymon): Nie zapisujemy
	
	public Client() 
	{
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.phoneNumber = "";
		this.password = "";
		this.isSaved = false;
		this.wayOfDelivery = null;
		this.wayOfPayment = null;
		this.price = 0;
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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsSaved() {
		return isSaved;
	}

	public void setWillBeSaved(boolean willBeSaved) {
		this.isSaved = willBeSaved;
	}

	public String toString() 
	{
		return "Dane o kliencie: Imie: " + firstName + ", Nazwisko: " + lastName + ", Email: " + email + ", Numer telefonu: " + phoneNumber;
	}
	
	public void setBasket(Basket[] basket) 
	{
		this.basket = basket;
	}

	public Basket[] getBasket()
	{
		return basket;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
	
	public void doShopping(boolean isNewClient) 
	{
		ChooseItems chooseItems = null;
		
		if(!isNewClient)
			 chooseItems = new ChooseItems(this); //NOTE(Szymon): Wywolanie dla powracajacego klienta
		else
			 chooseItems = new ChooseItems();  //NOTE(Szymon): Wywolanie dla nowego klienta
		
		basket = chooseItems.doShopping();
		price = chooseItems.getPrice();
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
			if(!isSaved) //NOTE(Szymon): W przypadku powracajacego klienta nie mozna bedzie zmienic maila, gdyz jest on informacja rozpoznawcza w bazie
				setCorrectEmail();
			setCorrectPhoneNumber();
			
			System.out.println(toString());
			
			
			
			System.out.println("Nacisnij 1 zeby potwierdzic swoje dane");
			String decision = scanner.nextLine();
			
			if (decision.equals("1")){
				shouldStopSettingClientInfo = true;
			}
		}
		
		//NOTE(Szymon): W przypadku nowego klienta pytamy czy chce zapisac konto
		if(!isSaved) {
			System.out.println("Czy chcesz utworzyc konto?");
			System.out.print("[Y/N]: ");
			String tmp = scanner.next();
			
			boolean done = false;
			while(!done) {
				
				if(tmp.equals("Y")) {
					
					setCorrectPassword();
					saveClient();
					isSaved = true;
					done = true;
					
				}else if(tmp.equals("N")) {
					done = true;
					
				}else {
					System.out.println("Nie ma takiej opcji!");
				}
				
			}
		}
		
	}
	
	@SuppressWarnings("resource")
	private void setCorrectPassword()
	{
		boolean shouldStopSettingClientInfo = false;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Podaj haslo: ");
		
		while(!shouldStopSettingClientInfo) 
		{
			String password = scanner.nextLine();
			
			// Sprawdzam czy hasla sie zgadzaja
			System.out.println("Potwierdz haslo: ");
			String confirm = scanner.nextLine();
				
			if (password.equals(confirm)) {
				shouldStopSettingClientInfo = true;
			}
		
			if (shouldStopSettingClientInfo) {
				this.password = password;
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

	public boolean setDeliveryInfo()
	{
		return ((Dostawa) wayOfDelivery).provideDeliveryInformations(this);
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
		transactionInfo = transactionInfo + "Cena: " + String.valueOf(price) + " (z wliczona dostawa)\n";
		
		if (WaysOfDelivery.isDeliveryDone()){
			transactionInfo += ((Dostawa) wayOfDelivery).deliveryInfo() + "\n";
			transactionInfo += "Metoda dostawy zostala zatwierdzona oraz ";
			
			if (wayOfPayment.isPaymentDone()){
				transactionInfo += "transakcja zostala oplacona";
			}
			
			else {
				transactionInfo += "nie dokonano platnosci za produkty";
			}
		}
		
		else {
			transactionInfo += "Nie zatwierdzono metody dostawy";
		}

		return transactionInfo;
	}
	
	private String getBasketContent()
	{
		String basketContent = "Zawartosc koszyka:\n";
		for (int i=0; i<basket.length; i++)
		{
			basketContent = basketContent + (i+1) + ". " + basket[i].getName() + ", Cena: " + basket[i].getPrice() + " Ilosc produktow: " + basket[i].getAmountOfProduct() + "\n"; 
		}
		return basketContent;
	}
	
	//NOTE(Szymon): Metoda zapisu klienta do pliku
	public void saveClient() {
		
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Client_"+email+".ser")))
		{
			outputStream.writeObject(this);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Doszlo do krytycznego bledu programu");
			System.exit(-1);
		} 
		catch (IOException e) 
		{
			System.out.println("Doszlo do krytycznego bledu programu");
			System.exit(-1);
		}
	}
}
