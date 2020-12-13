import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Klasa zimplementowana przez Szymona Sawczuka

public class Card implements WaysOfPayments{

	private boolean isPaymentDone = false;  //NOTE: Potrzebne aby móc poinformować o dokonaniu płatności
	
	@Override
	public String getName() {
		return "Płatność kartą";
	}

	@Override
	public boolean isPaymentDone() {
		return isPaymentDone;
	}
	
	private Map<String,int[]> cardNumberInput(Scanner input){
		
		String cardNumber = null;
		int[] number = new int[16];
		String[] tmp;
		Map<String, int[]> card = new HashMap<>();
		
		while(cardNumber == null) {
			
			System.out.print("Numer karty: ");
			cardNumber = input.nextLine();
			cardNumber = cardNumber.replaceAll("\\s+", ""); //NOTE: Usuwam wszystkie przerwy miedzy cyframi numeru
			tmp = cardNumber.split("");

			if(tmp.length != 16 && !cardNumber.equals("-1")) {
				
				System.out.println("Błędny rozmiar numeru karty");
				cardNumber = null;
				
			}
			else if(!cardNumber.equals("-1")){
				
				try {
	
					for(int i = 0;i<number.length;i++) {
						number[i] += Integer.parseInt(tmp[i]);
	
					}
					
				}catch(NumberFormatException e) { //NOTE: Jeśli w numerze karty jest np.litera
					
					System.out.println("Podaj poprawny numer karty");
					cardNumber = null;
//					e.getStackTrace(); NOTE: Dostępne tylko dla programisty
					
				}
			}
		}
		
		StringBuilder builder = new StringBuilder();
		if(cardNumber.equals("-1"))
		{
			number = new int[1];
			number[0] = -1;
			builder.append("-1");
		}

		if(!cardNumber.equals("-1")) { //NOTE: Gdy numer nie będzie -1 to zmieniam wygląd numeru karty w postaci Stringa dodając co 4 element spacje
			
			for(int i = 0;i<cardNumber.length();i+=4) {
				builder.append(cardNumber.substring(i, i+4));
				builder.append(" ");
			}
			
		}
		
		card.put(builder.toString(), number);		
		return card;
		
	}
	
	private int cvvInput(Scanner input, int cvv) {
		
		while((cvv <100 || cvv>999) && cvv!=-1) {
			
			System.out.print("Numer CVV: ");
			
			try {
				cvv = input.nextInt();
				
				if((cvv<100 || cvv>999) && cvv!= -1)
					System.out.println("Podaj poprawny numer CVV");
			
			}catch(InputMismatchException e) {
				
				input.nextLine();
				System.out.println("Podaj poprawny numer CVV");
//				e.getStackTrace(); NOTE: Dostępne tylko dla programisty

				
			}
			
		}
		
		return cvv;
		
	}
	

	@Override
	public void pay() {
		
		Scanner input = new Scanner(System.in);
		String expirationDate = null;
		int cvv = 0;
		boolean done = false;
		Map<String,int[]> card; //NOTE: Chce mieć numer karty w dwóch postaciach (String i int[]);
	
		
		System.out.println("Podaj dane karty kredytowej/debetowej (Aby wrócić wpisz w którymś z pól (-1)):");
		System.out.println("Przykładowa poprawna karta: 4556 7375 8689 9855");
		
		try {
			while(!done) {
				
				System.out.println();
			
				done = false;
				expirationDate = null; //NOTE: Wyzerowuje zmienne, aby móc zapętlić po odrzuceniu wpisanych danych
				cvv = 0;
				
				card = cardNumberInput(input);
				done = (card.get(card.keySet().toArray()[0])[0] == -1);
				
				if(!done && isValid(card.get(card.keySet().toArray()[0]))) {
					
					cvv = cvvInput(input, cvv);
					done = (cvv == -1);
	
					while(!done && (expirationDate == null || !isValidDate(expirationDate))) {
							
						System.out.print("Data ważności: ");
						expirationDate = input.next();
						
						done = expirationDate.equals("-1");

						if(!done && (!isValidDate(expirationDate) || expirationDate.length() != 5) ) {
							
							System.out.println("Podaj poprawną datę(np. 01/21)");
							expirationDate = null;
								
						}
						
					}
	
				}else if(!done) {
					System.out.println("Podaj poprawny numer karty");
				}
				
				if(expirationDate != null && !expirationDate.equals("-1") && card.entrySet().toArray()[0] != null && cvv != 0) {//NOTE: Wypisanie danych końcowych, aby móc zatwierdzić
					
					input.nextLine();
					
					System.out.printf("%nPodane dane:%n"
							+ "Numer karty: %s%n"
							+ "Numer CVV: %d%n"
							+ "Data ważności: %s%n%n"
							+ "Kilkij Enter, aby zatwierdzić%n"
							+ "Wpisz N, aby wpisać od nowa dane%n", card.keySet().toArray()[0], cvv, expirationDate);
					
						if(!input.nextLine().equals("N")) {
							
							isPaymentDone = true;
							done = true;
							
						}
						
						
				}
			}
	
		}catch(NoSuchElementException e) {
				
			System.out.println("Błąd: Doszło do krytycznego błedu programu");
			//e.getStackTrace(); NOTE: Tylko dla programisty
			input.close();
			System.exit(-1);
				
		}
		
		
		
			System.out.println("Powrót na stronę sklepu...");

			
		
	}
	
	private boolean isValid(int[] number) {
		
		int sum = 0;
	
		
		for(int i = number.length - 2;i>=0;i--) { //NOTE: Algorytm na sprawdzenie czy numer karty jest poprawny
			
			if((i+1)%2!=0) {
				number[i]*=2;
				
			}
			if(number[i]>9)
				number[i]-=9;
			
			sum+= number[i];
			
		}
		
		return sum%10 == number[number.length-1];
	}

	private boolean isValidDate(String date) {
		
		String month = date.substring(0,2);
		String year = date.substring(3,5);
		
		int monthInteger = Integer.parseInt(month);
		int yearInteger = Integer.parseInt(year);
		
		return monthInteger > 0 && monthInteger < 13 && yearInteger > 20; //NOTE: Założyłem, że karty o ważności do 2020 są już nie ważne 
	}
}
