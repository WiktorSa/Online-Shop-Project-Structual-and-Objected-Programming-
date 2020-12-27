import java.util.Scanner;

import client.Client;

// Klasa zaimplementowana przez Wiktora Sadowego
class Shop 
{
	@SuppressWarnings("resource")
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
		
		// Klient wybiera metode dostawy.
		client.chooseWayOfDelivery();
		
		// Klient podaje potrzebne informacje do przesylki
		client.setDeliveryInfo();
		
		
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
		
		// Pobieramy informacje o calym zamowieniu
		String transactionInfo = client.getTransactionInfo();
		
		// Pokazujemy informacje na temat zamowienia
		System.out.println();
		System.out.println(transactionInfo);
	}
}
