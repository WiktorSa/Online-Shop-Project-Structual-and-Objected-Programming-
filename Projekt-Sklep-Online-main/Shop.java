import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import chooseitems.Basket;
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
		boolean done = false, isNewClient = false;
		String tmp; // NOTE(Szymon): Zmienna tymczasowa do przechwytywania odpowiedzi klienta

		while(!done) {
			
			System.out.println("Czy jesteś powracającym klientem?");
			System.out.print("[Y/N]: ");
			
			String email = null;
			String password;
			boolean isCorrectEmail = false;
			
			
		    tmp = scanner.next();
			if(tmp.equals("Y")) {
					
				
					while (!isCorrectEmail)
					{	
						System.out.print("Podaj email: ");
						 email = scanner.next();
						
						// Sprawdzam czy mail jest poprawny
						String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
						if (email.matches(regex)) {
							isCorrectEmail = true;
						}
						else {
							System.out.println("Podaj poprawny adres email");
						}

						try(ObjectInputStream outputStream = new ObjectInputStream(new FileInputStream("Client_"+email+".ser"))){
							
						  System.out.print("Podaj haslo: ");
						  password = scanner.next();
						  client = (Client) outputStream.readObject();
						  
						  if(!client.getPassword().equals(password)) {
							  client = null; // NOTE(Szymon): Zabezpieczenie, aby nie mozna bylo dostac sie do nieswojego konta
							  System.out.println("Bledne haslo");
						  }
						  else {
							  done = true;
						  }
							
						} catch (FileNotFoundException e) {
							scanner.nextLine();
							if(isCorrectEmail)
								System.out.println("Nie ma takiego konta");
							//e.printStackTrace(); NOTE: Dostepne tylko dla programisty
							isCorrectEmail = false;
						} catch (IOException e) {
							System.out.println("Doszlo do krytycznego bledu programu");
							//e.printStackTrace(); NOTE: Dostepne tylko dla programisty
							System.exit(-1);
						} catch (ClassNotFoundException e) {
							System.out.println("Doszlo do krytycznego bledu programu");
							//e.printStackTrace(); NOTE: Dostepne tylko dla programisty
							System.exit(-1);
						}		
					}
				
			}else if(tmp.equals("N")) {
				done = true;
				isNewClient = true;
				
			}else {
				System.out.println("Bledna opcja");
			}
		}
		
		// Klient dokonuje zakupow
		client.doShopping(isNewClient);
		
		// Klient wpisuje swoje podstawowe dane
		if(isNewClient)
			client.setClientInfo();
		else {
			System.out.println(client.toString());
			
			System.out.println("Czy chcesz zmienic dane?");
			System.out.print("[Y/N]:");
			tmp = scanner.next();
			done = false;
			while(!done) {
				if(tmp.equals("Y")) {
					client.setClientInfo();
					done = true;
					
				}else if(tmp.equals("N")) {
					done = true;
					
				}else {
					System.out.println("Bledna opcja");
				}
			}
			
		}
			
		scanner.nextLine(); // NOTE(Szymon): ustawiam skaner na odpowiednia linie
			
		
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
		
		boolean wasPaymentDone = false; // NOTE(Szymon): Wyciagnalem przed if aby moc dostac sie do tego przy czysczeniu koszyka
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
		
		//NOTE(Szymon): Jezeli zaplacono to czyszcze koszyk klienta
		if(wasPaymentDone) { 
			client.setBasket(new Basket[0]);
			client.setPrice(0);
		}
		
		//NOTE(Szymon): Ostatni raz zapisuje klienta przed wyjsciem
		if(client.getIsSaved())
			client.saveClient();
			
		// Zamykamy scannera
		scanner.close();
	}
}
