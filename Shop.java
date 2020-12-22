import java.util.Scanner;

import chooseitems.ChooseItems;
import client.Client;
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
	public static WaysOfDelivery chooseWayOfDelivery()
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
		
		return waysOfDelivery[chosenWayOfDelivery-1];
	}
	
	@SuppressWarnings("resource")
	public static WaysOfPayments chooseWayOfPayment()
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
		
		return waysOfPayments[chosenWayOfPayment-1];
	}
	
	public static void main(String[] args) 
	{
		// Klient dokonuje zakupow
		ChooseItems chooseItems = new ChooseItems();
		chooseItems.doShopping();
		
		// Przypisujemy koszyk do klienta, ktory przed chwila zrobil zakupy
		Client client = new Client();
		client.setBasket(chooseItems.getBasket());
		
		// Zbieramy podstawowe informacje o klience
		client.setClientInfo();
		
		// Klient wybiera metode dostawy. Wybrana metode przypisujemy do jego konta a potem prosimy klienta by podazal za instrukacjami
		WaysOfDelivery wayOfDelivery =  chooseWayOfDelivery();
		client.setWayOfDelivery(wayOfDelivery);
		client.setDeliveryInfo();
		
		// Klient wybiera metode platnosci. Wybrana metode przypisujemy do jego konta a potem prosimy klienta, by podazal za instrukcjami
		WaysOfPayments wayOfPayment = chooseWayOfPayment();
		client.setWayOfPayment(wayOfPayment);
		client.Pay();
		
		// Zwracamy cala informacje na temat zamowienia
		System.out.println();
		System.out.println(client.getTransactionInfo());
	}
}
