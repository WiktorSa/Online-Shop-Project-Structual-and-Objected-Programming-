import java.util.Scanner;

import chooseitems.ChooseItems;
import client.Client;
import waysofdelivery.Dostawa;
import waysofdelivery.Kurier;
import waysofdelivery.Osobisty;
import waysofdelivery.Paczkomat;
import waysofdelivery.WaysOfDelivery;
import waysofpayments.Blik;
import waysofpayments.Card;
import waysofpayments.Paypal;
import waysofpayments.WaysOfPayments;

// Klasa zaimplementowana przez Wiktora Sadowego
class Shop 
{
	@SuppressWarnings("resource")
	public static Client setClientInfo(Client client)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Podaj swoje imie");
		client.setFirstName(scanner.nextLine());
		System.out.println("Podaj swoje nazwisko");
		client.setLastName(scanner.nextLine());
		System.out.println("Podaj swoj adres email");
		client.setEmail(scanner.nextLine());
		System.out.println("Podaj swoj numer telefonu");
		client.setPhoneNumber(scanner.nextLine());
		System.out.println(client.toString());
		System.out.println("Nacisnij 1 zeby potwierdzic swoje dane");
		String decision = scanner.nextLine();
		if (decision.equals("1")){
			return client;
		}
		else {
			return setClientInfo(client);
		}
	}

	@SuppressWarnings("resource")
	public static int setDeliveryInfo(Client client, WaysOfDelivery[] waysOfDelivery)
	{
		Scanner scanner = new Scanner(System.in);
		for (int i=0; i<waysOfDelivery.length; i++)
		{
			System.out.println(i+1 + ". " + waysOfDelivery[i].getName() + " Cena: " + waysOfDelivery[i].getPrice());
		}
		System.out.println("Wybierz opcje dostawy");
		try 
		{
			int decision = scanner.nextInt();
			scanner.nextLine();
			if (decision>0 && decision<=waysOfDelivery.length) {
				System.out.println(((Dostawa) waysOfDelivery[decision-1]).provideDeliveryInformations(client));
				return decision;
			}
			else {
				return setDeliveryInfo(client, waysOfDelivery);
			}
		} 
		catch (java.util.InputMismatchException e) 
		{
			return setDeliveryInfo(client, waysOfDelivery);
		}
	}

	@SuppressWarnings("resource")
	public static boolean getPaymentInfo(WaysOfPayments[] waysOfPayments)
	{
		Scanner scanner = new Scanner(System.in);
		for (int i=0; i<waysOfPayments.length; i++)
		{
			System.out.println((i+1) + ". " + waysOfPayments[i].getName());
		}
		System.out.println("Wybierz opcje dostawy");
		try 
		{
			int decision = scanner.nextInt();
			scanner.nextLine();
			if (decision>0 && decision<=waysOfPayments.length) {
				waysOfPayments[decision-1].pay();
				return waysOfPayments[decision-1].isPaymentDone();
			}
			else {
				return getPaymentInfo(waysOfPayments);
			}
		} 
		catch (java.util.InputMismatchException e) 
		{
			return getPaymentInfo(waysOfPayments);
		}
	}
	
	public static void main(String[] args) 
	{
		// Inicjalizacja wszystkich potrzebnych klas
		ChooseItems chooseItems = new ChooseItems();
		Client client = new Client();
		WaysOfDelivery[] waysOfDelivery = {new Paczkomat(), new Kurier(), new Osobisty()};
		WaysOfPayments[] waysOfPayments = {new Blik(), new Card(), new Paypal()};
		// Inicjalizacja zmiennych
		String transactionInfo = "";
		double cena = 0;
		// Robienie zakupow
		chooseItems.doShopping();
		transactionInfo += chooseItems.toString();
		cena += chooseItems.getPrice();
		// Ustawianie danych klienta
		client = setClientInfo(client);
		// Wybieranie metody dostawy
		int decision = setDeliveryInfo(client, waysOfDelivery);
		transactionInfo += ((Dostawa) waysOfDelivery[decision-1]).deliveryInfo();
		cena += waysOfDelivery[decision-1].getPrice();
		// Wybieranie metody platnosci
		boolean wasTransactionPaid = getPaymentInfo(waysOfPayments);
		if (wasTransactionPaid) {
			System.out.println("");
			System.out.println(transactionInfo);
			System.out.println("Koszt zamowienia + " + cena);
		}
		else {
			System.out.println("Za transakcje nie zaplacono. Zamowienie zostalo anulowane");
		}
	}
}
