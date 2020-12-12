import java.util.Scanner;

class Shop 
{
	@SuppressWarnings("resource")
	public static Client setClientInfo(Client client)
	{
		Scanner takingInput = new Scanner(System.in);
		System.out.println("Podaj swoje imie");
		client.setFirstName(takingInput.nextLine());
		System.out.println("Podaj swoje nazwisko");
		client.setLastName(takingInput.nextLine());
		System.out.println("Podaj swoj adres email");
		client.setEmail(takingInput.nextLine());
		System.out.println("Podaj swoj numer telefonu");
		client.setPhoneNumber(takingInput.nextLine());
		System.out.println(client.toString());
		System.out.println("Nacisnij 1 zeby potwierdzic swoje dane");
		String decision = takingInput.nextLine();
		if (decision.equals("1")){
			return client;
		}
		else {
			return setClientInfo(client);
		}
	}
	
	public static void main(String[] args) 
	{
		ChooseItems selectingItems = new ChooseItems();
		Client client = new Client();
		// Tutaj beda metody wyboru dostawy
		// Tutaj beda metody wyboru platnosci
		String transactionInfo = "";
		double cena = 0;
		// Ponizej sa wywolania metod
		selectingItems.doShopping();
		transactionInfo += selectingItems.toString();
		cena += selectingItems.getPrice();
		client = setClientInfo(client);
		transactionInfo = transactionInfo + client.toString() + "\n";
		// Tutaj bedzie dodawanie informacji o metodzie dostawy
		// Tutaj bedzie update ceny dostawy
		// Tutaj bedzie dodawanie prostej informacji platnosc zostala dokonana (mo¿e byæ boolean)
		System.out.println("");
		System.out.println(transactionInfo);
		System.out.println(cena);
	}
}
