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
	private boolean isPaymentDone = false; //NOTE: Potrzebne aby m�c poinformowa� o dokonaniu p�atno�ci
	private static boolean isActive; //NOTE: Static, aby moc wys�a� j� do statycznej funkcji setActive()

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
		
		
		System.out.printf("Prosz� poda� 6-cyforwy kod(kod poprawny:%d, powr�t na stron� wpisz (-1)):%n", properCode);
		while(!done) {
			try {
				
				code = input.nextInt();
				
				if(code == -1)
					done=true; //NOTE: Powr�t na stron�
				
				else if(code<100000 || code>999999) //NOTE: Sprawdzam czy u�ytkownik poda� odpowiednio d�ugi kod
					throw new InputMismatchException(); 
				
				else if(code == properCode && isActive) {
					
					input.nextLine();
					System.out.print("Potwierd� p�atno�� na telefonie(Kliknij Enter, aby kontynuowa�)..."); //NOTE: nextLine() potrzebny jest aby zatrzyma� t� lini� p�ki nie zostanie klikni�ty ENTER
					input.nextLine();
					
					isPaymentDone = true;
					done = true;
					
				}else if(code != properCode)
					System.out.println("B��d: Podano b��dny kod");
				
				else {
					
					System.out.println("B��d: Kod straci� wa�no��");
					properCode = this.generateCode();
					System.out.printf("Nowy kod: %d%n",properCode);
					
					
				}
				
			}catch(InputMismatchException e) {
				input.nextLine(); //NOTE: Bez tego zap�tla si� w niesko�czono�� catch
				System.out.println("B��d: Prosz� poda� 6-cyfrowy kod");
				//e.getStackTrace(); NOTE: Tylko dla programisty
				
			}catch(NoSuchElementException e) {
			
				System.out.print("B��d: Dosz�o do krytycznego b��du programu");
				//e.getStackTrace(); NOTE: Tylko dla programisty
				System.exit(-1);
				
			}
			
		}
		
		System.out.println("Powr�t na stron� sklepu...");
		

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
		
		timer.schedule(timerTask, 120000); //NOTE: Po 2 minutach kod przestaje by� wa�ny 
	
		
		
		
		return code;
	}

	
	
}