import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

//Klasa zimplementowana przez Szymona Sawczuka


public class Blik implements WaysOfPayments{
	
	private Timer timer; 
	private boolean isPaymentDone = false; //NOTE: Potrzebne aby móc poinformować o dokonaniu płatności
	private static boolean isActive; //NOTE: Static, aby móc wysłać ją do statycznej funkcji setActive()

	public static void setActive(boolean isActive) {  //NOTE: Static potrzebny jest, aby dać dostęp do tej funkcji w timerTask
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
	
	
	@Override
	public void pay() {
		
		int code = 0;
		int properCode = this.generateCode();
		boolean done = false;
		Scanner input = new Scanner(System.in);
		
		
		System.out.printf("Proszę podać 6-cyforwy kod(kod poprawny:%d, powrót na stronę wpisz (-1)):%n", properCode);
		while(!done) {
			try {
				
				code = input.nextInt();
				
				if(code == -1)
					done=true; //NOTE: Powrót na stronę
				
				else if(code<100000 || code>999999) //NOTE: Sprawdzam czy użytkownik podał odpowiednio długi kod
					throw new InputMismatchException(); 
				
				else if(code == properCode && isActive) {
					
					input.nextLine();
					System.out.print("Potwierdź płatność na telefonie(Kliknij Enter, aby kontynuować)..."); //NOTE: nextLine() potrzebny jest aby zatrzymać tą linię póki nie zostanie kliknięty ENTER
					input.nextLine();
					
					isPaymentDone = true;
					done = true;
					
				}else if(code != properCode)
					System.out.println("Błąd: Podano błędny kod");
				
				else {
					
					System.out.println("Błąd: Kod stracił ważność");
					properCode = this.generateCode();
					System.out.printf("Nowy kod: %d%n",properCode);
					
					
				}
				
			}catch(InputMismatchException e) {
				input.nextLine(); //NOTE: Bez tego zapętla się w nieskończoność catch
				System.out.println("Błąd: Proszę podać 6-cyfrowy kod");
				//e.getStackTrace(); NOTE: Tylko dla programisty
				
			}catch(NoSuchElementException e) {
			
				System.out.print("Błąd: Doszło do krytycznego błędu programu");
				//e.getStackTrace(); NOTE: Tylko dla programisty
				input.close();
				System.exit(-1);
				
			}
			
		}
		
		System.out.println("Powrót na stronę sklepu...");
		

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
		
		timer.schedule(timerTask, 120000); //NOTE: Po 2 minutach kod przestaje być ważny 
	
		
		
		
		return code;
	}

	
	
}


