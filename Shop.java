import java.util.Scanner;

import client.Client;

// Klasa zaimplementowana przez Wiktora Sadowego
class Shop 
{
	public static void main(String[] args) 
	{
		// Scanner do pobierania inputu
		Scanner scanner = new Scanner(System.in);
		
		// Klient wchodzi do sklepu
		Client client = new Client();
		
		// Klient dokonuje zakupow
		client.doShopping();
		
		// Klient wpisuje swoje podstawowe dane
		client.setClientInfo();
		
		/*
		 * Klient wybiera metode dostawy
		 * Jak jej nie wybierze oraz nie poda wlasciwych danych to zamowienie zostanie anulowane
		 */
		
		boolean wasWayOfDeliveryChosen = false;
		boolean didHeDecide=false;
		boolean isClientDelivering=true;
		while (isClientDelivering && !didHeDecide)
		{
			// Klient wybiera metode dostawy. 
			if (!wasWayOfDeliveryChosen) {
				client.chooseWayOfDelivery();
				wasWayOfDeliveryChosen = true;
			}
			
			//Tutaj podaje informacje potrzebne do dostawy
			didHeDecide = client.setDeliveryInfo();
			
			// Tutaj klient moze zrezygnowac z dostawy tym samym anulujac zamowienie lub wybrac inna metode dostawy
			if (!didHeDecide) {
				System.out.println("Anulowales ta metode dostawy, wpisz 0, by anulowac zamowienie, 1 by wybrac inna metode lub cokolwiek innego by ponownie wprowadzic dane.");
				String decision = scanner.nextLine();
				
				if (decision.equals("0")) {
					System.out.println("Zamowienie zostalo anulowane.");
					isClientDelivering = false;
					client.getWaysOfDelivery().setisItDone(isClientDelivering);
				}
				
				else if (decision.equals("1")) {
					wasWayOfDeliveryChosen = false;
					System.out.println("Prosze ponownie wybrac metode dostawy.");
				}
			}
			
			else{
				client.getWaysOfDelivery().setisItDone(true);
			}
		}
		
		// Nie mozesz zaplacic za produkt jezeli nie wybrales metody dostawy
		if(isClientDelivering == true)
		{	
			/* 
			 * Klient powinien wybrac metode platnosci 
			 * Potem powinien zaplacic za produkt (ma prawo do zmiany decyzji jezeli wybral zla opcje) 
			 * Moze tez nie zaplacic i wowczas zamowienie zostanie anulowane 
			*/
			
			boolean wasWayOfPaymentChosen = false;
			boolean isClientPaying = true;
			boolean wasPaymentDone = false;
			while (isClientPaying && !wasPaymentDone)
			{
				// Klient wybiera metode platnosci. 
				if (!wasWayOfPaymentChosen) {
					client.chooseWayOfPayment();
					wasWayOfPaymentChosen = true;
				}
				
				// Klient placi za produkt
				wasPaymentDone = client.Pay();
				
				// Tutaj klient moze zrezygnowac z platnosci tym samym anulujac zamowienie lub wybrac inna metode platnosci
				if (!wasPaymentDone) {
					System.out.println("Platnosc nie zostala dokonana. Nacisnij 0 jezeli nie chcesz ponowic platnosci, 1 by zmienic metode platnosci, cokolwiek innego by ponowic platnosc");
					String decision = scanner.nextLine();
					
					if (decision.equals("0")) {
						isClientPaying = false;
						
						System.out.println("Zamowienie zostalo anulowane");
					}
					
					else if (decision.equals("1")) {
						wasWayOfPaymentChosen = false;
						System.out.println("Prosze ponownie wybrac metode platnosci ");
					}
				}
			}
		}
		
		
		// Pobieramy informacje o calym zamowieniu
		String transactionInfo = client.getTransactionInfo();
		
		// Pokazujemy informacje na temat zamowienia
		System.out.println();
		System.out.println(transactionInfo);
			
		// Zamykamy scannera
		scanner.close();
	}
}
