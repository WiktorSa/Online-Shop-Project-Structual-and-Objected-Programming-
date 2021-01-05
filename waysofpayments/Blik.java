package waysofpayments;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import client.Client;

//Klasa zimplementowana przez Szymona Sawczuka


public class Blik implements WaysOfPayments{
	
	private Timer timer; 
	private boolean isPaymentDone = false; //NOTE: Potrzebne aby moc poinformowac o dokonaniu platnosci
	private static boolean isActive; //NOTE: Static, aby moc wyslac ja do statycznej funkcji setActive()

	public static void setActive(boolean isActive) {  //NOTE: Static potrzebny jest, aby dac dostep do tej funkcji w timerTask
		Blik.isActive = isActive;
	}
	
	@Override
	public String getName() {
		return "Blik";
	}

	@Override
	public boolean isPaymentDone() {
		return isPaymentDone;
	}
	
	
	@SuppressWarnings("resource")
	@Override
	public void pay(Client client) {
		
		int code = 0;
		int properCode = this.generateCode();
		boolean done = false;
		Scanner input = new Scanner(System.in);
		
		
		System.out.printf("Prosze podac 6-cyforwy kod(kod poprawny:%d, powrot na strone wpisz (-1)):%n", properCode);
		while(!done) {
			try {
				
				code = input.nextInt();
				
				if(code == -1)
					done=true; //NOTE: Powrot na strone
				
				else if(code<100000 || code>999999) //NOTE: Sprawdzam czy uzytkownik podal odpowiednio dlugi kod
					throw new InputMismatchException(); 
				
				else if(code == properCode && isActive) {
					
					input.nextLine();
					System.out.print("Potwierdz platnosc na telefonie(Kliknij Enter, aby kontynuowac)..."); //NOTE: nextLine() potrzebny jest aby zatrzymac ta linie poki nie zostanie klikniety ENTER
					input.nextLine();
					
					isPaymentDone = true;
					done = true;
					
				}else if(code != properCode)
					System.out.println("Blad: Podano bledny kod");
				
				else {
					
					System.out.println("Blad: Kod stracil waznosc");
					properCode = this.generateCode();
					System.out.printf("Nowy kod: %d%n",properCode);
					
					
				}
				
			}catch(InputMismatchException e) {
				input.nextLine(); //NOTE: Bez tego zapetla sie w nieskonczonosc catch
				System.out.println("Blad: Prosze podac 6-cyfrowy kod");
				//e.getStackTrace(); NOTE: Tylko dla programisty
				
			}catch(NoSuchElementException e) {
			
				System.out.print("Blad: Doszlo do krytycznego bledu programu");
				//e.getStackTrace(); NOTE: Tylko dla programisty
				System.exit(-1);
				
			}
			
		}
		
		System.out.println("Powrot na strone sklepu...");
		

		timer.cancel();
			
		
	}

//	NOTE: Generuje poprawny kod Blik
	private int generateCode() {
		Random randomizer = new Random();
		int code = 0;
	
		code = randomizer.nextInt(900000)+100000;
		isActive = true;
		
		timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				
				Blik.setActive(false);
				timer.cancel();
				
			}
			
		};
		
		timer.schedule(timerTask, 120000); //NOTE: Po 2 minutach kod przestaje byc wazny 
	
		
		
		
		return code;
	}

	
	
}
