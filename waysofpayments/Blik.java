package waysofpayments;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import client.Client;

//Klasa zimplementowana przez Szymona Sawczuka


public class Blik implements WaysOfPayments{

	
	private Timer timer; 
	private boolean isPaymentDone = false; //NOTE: Potrzebne aby moc poinformowac o dokonaniu platnosci
	private static boolean isActive = false; //NOTE: Static, aby moc wyslac ja do statycznej funkcji setActive()
	private int properCode;

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
	
//	NOTE: Generuje poprawny kod Blik
	public int generateCode() {
		
		if(Blik.isActive) {
			timer.cancel();
		}
		
		Random randomizer = new Random();
		int code = 0;
	
		code = randomizer.nextInt(900000)+100000;
		Blik.isActive = true;
		
		timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				
				Blik.setActive(false);
				timer.cancel();
				
			}
			
		};
		properCode = code;
		
		timer.schedule(timerTask, 120000); //NOTE: Po 2 minutach kod przestaje byc wazny 
		
		return code;
	}
	
	public void destroyTimer() {
		if(Blik.isActive) {
			this.timer.cancel();
			Blik.setActive(false);
		}
	}

	

	@Override
	public boolean pay(Client client, ArrayList<String> info) {
	
		if(isActive && info.get(0).equals(Integer.toString(properCode))) {
			isPaymentDone = true;
			return true;
		}
		
		return false;
	}
	


	
}
