package waysofpayments;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

//Klasa zimplementowana przez Szymona Sawczuka


public class Blik implements WaysOfPayments{
	
	private Timer timer; 
	private boolean isPaymentDone = false; //NOTE: Potrzebne aby móc poinformowaæ o dokonaniu p³atnoœci
	private static boolean isActive; //NOTE: Static, aby moc wys³aæ j¹ do statycznej funkcji setActive()

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
	public void pay() {
		
		int code = 0;
		int properCode = this.generateCode();
		boolean done = false;
		Scanner input = new Scanner(System.in);
		
		
		System.out.printf("Proszê podaæ 6-cyforwy kod(kod poprawny:%d, powrót na stronê wpisz (-1)):%n", properCode);
		while(!done) {
			try {
				
				code = input.nextInt();
				
				if(code == -1)
					done=true; //NOTE: Powrót na stronê
				
				else if(code<100000 || code>999999) //NOTE: Sprawdzam czy u¿ytkownik poda³ odpowiednio d³ugi kod
					throw new InputMismatchException(); 
				
				else if(code == properCode && isActive) {
					
					input.nextLine();
					System.out.print("PotwierdŸ p³atnoœæ na telefonie(Kliknij Enter, aby kontynuowaæ)..."); //NOTE: nextLine() potrzebny jest aby zatrzymaæ t¹ liniê póki nie zostanie klikniêty ENTER
					input.nextLine();
					
					isPaymentDone = true;
					done = true;
					
				}else if(code != properCode)
					System.out.println("B³¹d: Podano b³êdny kod");
				
				else {
					
					System.out.println("B³¹d: Kod straci³ wa¿noœæ");
					properCode = this.generateCode();
					System.out.printf("Nowy kod: %d%n",properCode);
					
					
				}
				
			}catch(InputMismatchException e) {
				input.nextLine(); //NOTE: Bez tego zapêtla siê w nieskoñczonoœæ catch
				System.out.println("B³¹d: Proszê podaæ 6-cyfrowy kod");
				//e.getStackTrace(); NOTE: Tylko dla programisty
				
			}catch(NoSuchElementException e) {
			
				System.out.print("B³¹d: Dosz³o do krytycznego b³êdu programu");
				//e.getStackTrace(); NOTE: Tylko dla programisty
				System.exit(-1);
				
			}
			
		}
		
		System.out.println("Powrót na stronê sklepu...");
		

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
		
		timer.schedule(timerTask, 120000); //NOTE: Po 2 minutach kod przestaje byæ wa¿ny 
	
		
		
		
		return code;
	}

	
	
}