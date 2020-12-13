import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

//Klasa zimplementowana przez Szymona Sawczuka

public class Paypal implements WaysOfPayments{

	private boolean isPaymentDone = false;  //NOTE: Potrzebne aby móc poinformować o dokonaniu płatności
	
	@Override
	public String getName() {
		return "Paypal";
	}

	@Override
	public boolean isPaymentDone() {
		return isPaymentDone;
	}
	
	private String inputForm(int whichForm, String form, String properAccount[][], Scanner input) { //NOTE: whichForm -> 1 to email, 2 to hasło
		
		while(form == null || form.equals("pomoc")) { //NOTE: Sprawdzam czy nie jest potrzebne przypomnienie email'a
			
			switch(whichForm) {
			
			case 1: 
				if(form!=null && form.equals("pomoc"))
					System.out.printf("Adres email: %s%nNumer tel.:%s%n%n",properAccount[0][0],properAccount[0][1]);
				System.out.print("Adres email lub numer telefonu: ");
				break;
			case 2:
				if(form!=null && form.equals("pomoc"))
					System.out.printf("Hasło:%s%n%n",properAccount[1][0]);
				System.out.print("Hasło: ");
				break;
				
			}
			
			form = input.next();
			
			
			
			
		}
		
		return form;
	}

	@Override
	public void pay() {
		
		Scanner input = new Scanner(System.in);
		boolean done = false;
		String[] account = new String[2];
		String[][] properAccount = this.generateAccount(); //NOTE: Nieregularna macierz w pierwszym wierszu dwie kolumny(email, tel), w drugim wierszu hasło
		int countOfMistakes = 0;

		System.out.printf("Zaloguj się do swojego konta Paypal%n"
				+ "(poprawny adres email: %s, numer tel.: %s, hasło: %s)%n"
				+ "Przypomnij email/numer tel. lub hasło-> wpisz \"pomoc\"%n"
				+ "Powrót na Stronę -> wpisz -1 w adresie email/numer tel.%n%n", properAccount[0][0], properAccount[0][1], properAccount[1][0]);
		
		while(!done) {
			
			try {
				
				if(countOfMistakes >= 3) {
					
					while(!this.captcha(input));
					
				}
				
				account[0] = inputForm(1, account[0], properAccount, input);
				
				
				if(!account[0].equals("-1")){ //NOTE: Sprawdzam czy nie chcemy wrócić na stronę sklepu
				
					account[1] = inputForm(2, account[1], properAccount, input);
					
					
					if(account[0].equals("") || account[1].equals(""))
						System.out.println("Login ani hasło nie mogą być puste.\n"); //NOTE: Ogólnie nie powinno być tej sytuacji, ale lepiej się zabezpieczyć na wszelki wypadek
					
					
						
					if((account[0].equals(properAccount[0][0]) || account[0].equals(properAccount[0][1])) && account[1].equals(properAccount[1][0])) {
							
						isPaymentDone = true;
						done = true;
							
					}else {
							
						input.nextLine();
						System.out.println("Podane login lub hasło są niepoprawne\n");
						account[0] = null;
						account[1] = null;
						countOfMistakes++;
							
					}
			}else 
				done = true;
			
				
			}catch(NoSuchElementException e) {
				
				System.out.println("Błąd: Doszło do krytycznego błędu programu");
//				e.getStackTrace(); NOTE: Dostępne tylko dla programisty
				input.close();
				System.exit(-1);
				
			}
		}
		
		System.out.println("Powrót na stronę sklepu...");

		
			
		
		
	}
	
	private String[][] generateAccount() {
		
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"; 
		
		String[][] account = {{"",""},{""}}; //NOTE: Nieregularna macierz w pierwszym wierszu dwie kolumny(email, tel), w drugim wierszu hasło
		Random randomizer = new Random();	
		StringBuilder builder = new StringBuilder(10);
		
//		NOTE: Generowanie email'a
			for(int j = 0;j<5;j++) {
				
				builder.append(CHARACTERS.charAt(randomizer.nextInt(CHARACTERS.length()))); 
				
			}
			
			builder.append("@gmail.com");
			account[0][0] = builder.toString();
			builder.delete(0, builder.length());
			
//		NOTE: Generowanie numeru tel.
		account[0][1] = Integer.toString(randomizer.nextInt(999999999) + 100000000);
		
//		NOTE: Generowanie hasła
		for(int j = 0;j<10;j++) {
			
			builder.append(CHARACTERS.charAt(randomizer.nextInt(CHARACTERS.length()))); 
			
		}
		
		account[1][0] = builder.toString();
		
		return account;
		
	}

	

	private boolean captcha(Scanner input) {
		
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"; 
		
		String codeCaptcha = "";	
		String code = "";		
		Random randomizer = new Random();	
		StringBuilder builder = new StringBuilder(6);
		
//		NOTE: Generowanie kodu Captcha
			for(int j = 0;j<6;j++) {
				
				builder.append(CHARACTERS.charAt(randomizer.nextInt(CHARACTERS.length()))); 
				
			}
			codeCaptcha = builder.toString();
			builder.delete(0,builder.length());
			
			
			System.out.printf("Przepisz kod Captcha(%s):",codeCaptcha);
			
			
			code = input.next();
			
			if(!code.equals(codeCaptcha)) {
				
				System.out.println("Kod nie zgadza się z kodem Captcha");
				return false;
				
			}else {
				
				return true;
				
			}
			

		
	}
	
}
