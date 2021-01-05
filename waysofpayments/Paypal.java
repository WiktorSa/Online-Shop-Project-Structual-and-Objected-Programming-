package waysofpayments;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import client.Client;

//Klasa zimplementowana przez Szymona Sawczuka

public class Paypal implements WaysOfPayments{

	private boolean isPaymentDone = false;  //NOTE: Potrzebne aby moc poinformowac o dokonaniu platnosci
	
	@Override
	public String getName() {
		return "Paypal";
	}

	@Override
	public boolean isPaymentDone() {
		return isPaymentDone;
	}
	
	private String inputForm(int whichForm, String form, String properAccount[][], Scanner input) { //NOTE: whichForm -> 1 to email, 2 to haslo
		
		while(form == null || form.equals("pomoc")) { //NOTE: Sprawdzam czy nie jest potrzebne przypomnienie email'a
			
			switch(whichForm) {
			
			case 1: 
				if(form!=null && form.equals("pomoc"))
					System.out.printf("Adres email: %s%nNumer tel.:%s%n%n",properAccount[0][0],properAccount[0][1]);
				System.out.print("Adres email lub numer telefonu: ");
				break;
			case 2:
				if(form!=null && form.equals("pomoc"))
					System.out.printf("Haslo:%s%n%n",properAccount[1][0]);
				System.out.print("Haslo: ");
				break;
				
			}
			
			form = input.next();
			
			
			
			
		}
		
		return form;
	}

	@Override
	public void pay(Client client) {
		
		Scanner input = new Scanner(System.in);
		boolean done = false;
		String[] account = new String[2];
		String[][] properAccount = this.generateAccount(client); //NOTE: Nieregularna macierz w pierwszym wierszu dwie kolumny(email, tel), w drugim wierszu haslo
		int countOfMistakes = 0;

		System.out.printf("Zaloguj sie do swojego konta Paypal%n"
				+ "(poprawne haslo: %s)%n"
				+ "Przypomnij email/numer tel. lub haslo-> wpisz \"pomoc\"%n"
				+ "Powrot na Strone -> wpisz -1 w adresie email/numer tel.%n%n", properAccount[1][0]);
		
		while(!done) {
			
			try {
				
				if(countOfMistakes >= 3) {
					
					while(!this.captcha(input));
					
				}
				
				account[0] = inputForm(1, account[0], properAccount, input);
				
				
				if(!account[0].equals("-1")){ //NOTE: Sprawdzam czy nie chcemy wrocic na strone sklepu
				
					account[1] = inputForm(2, account[1], properAccount, input);
					
					
					if(account[0].equals("") || account[1].equals(""))
						System.out.println("Blad: Login ani haslo nie moga byc puste.\n"); //NOTE: Ogolnie nie powinno byc tej sytuacji, ale lepiej sie zabezpieczyc na wszelki wypadek
					
					
						
					if((account[0].equals(properAccount[0][0]) || account[0].equals(properAccount[0][1])) && account[1].equals(properAccount[1][0])) {
							
						isPaymentDone = true;
						done = true;
							
					}else {
							
						input.nextLine();
						System.out.println("Blad: Podane login lub haslo sa niepoprawne\n");
						account[0] = null;
						account[1] = null;
						countOfMistakes++;
							
					}
			}else 
				done = true;
			
				
			}catch(NoSuchElementException e) {
				
				System.out.println("Blad: Doszlo do krytycznego bledu programu");
//				e.getStackTrace(); NOTE: Dostepne tylko dla programisty
				System.exit(-1);
				
			}
		}
		
		System.out.println("Zalogowano pomyslnie");
		System.out.println("Powrot na strone sklepu...");

		
			
		
		
	}
	
	private String[][] generateAccount(Client client) {
		
		
	
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"; 
		
		String[][] account = {{"",""},{""}}; //NOTE: Nieregularna macierz w pierwszym wierszu dwie kolumny(email, tel), w drugim wierszu haslo
		Random randomizer = new Random();	
		final int lengthOfPassword = 10;
		StringBuilder builder = new StringBuilder(lengthOfPassword);
		
//		NOTE: Wczytanie email'a
			account[0][0] = client.getEmail();
			
//		NOTE: Wczytanie numeru tel.
			account[0][1] = client.getPhoneNumber();
		
//		NOTE: Generowanie hasla
		for(int j = 0;j<lengthOfPassword;j++) {
			
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
		final int lengthOfCode = 6;
		StringBuilder builder = new StringBuilder(lengthOfCode);
		
//		NOTE: Generowanie kodu Captcha
			for(int j = 0;j<lengthOfCode;j++) {
				
				builder.append(CHARACTERS.charAt(randomizer.nextInt(CHARACTERS.length()))); 
				
			}
			codeCaptcha = builder.toString();
			builder.delete(0,builder.length());
			
			
			System.out.printf("Przepisz kod Captcha(%s):",codeCaptcha);
			
			
			code = input.next();
			
			if(!code.equals(codeCaptcha)) {
				
				System.out.println("Kod nie zgadza sie z kodem Captcha");
				return false;
				
			}else {
				
				return true;
				
			}
			

		
	}
	
}