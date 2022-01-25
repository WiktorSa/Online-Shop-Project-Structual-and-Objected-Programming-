package waysofpayments;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import client.Client;
import gui.payment.BlikGUI;
import gui.shop.MainGUI;


public class Blik implements WaysOfPayments{
	private Timer timer; 
	private boolean isPaymentDone = false;
	private static boolean isActive = false;
	private int properCode;

	// Static is used to give access to this function in TimerTask
	public static void setActive(boolean isActive) {
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

	@Override
	public void starFrame(MainGUI main) {
		
		new BlikGUI(main);
		
	}
}
