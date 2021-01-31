package waysofpayments;
import java.util.ArrayList;
import java.util.Random;


import client.Client;
import guiShop.MainGUI;
import guiWaysOfPayment.PayPalGUI;

//Klasa zimplementowana przez Szymona Sawczuka

public class Paypal implements WaysOfPayments{

	private boolean isPaymentDone = false;  //NOTE: Potrzebne aby moc poinformowac o dokonaniu platnosci
	private String codeCaptcha;
	
	@Override
	public String getName() {
		return "Paypal";
	}

	@Override
	public boolean isPaymentDone() {
		return isPaymentDone;
	}

	@Override
	public boolean pay(Client client, ArrayList<String> info) {
		
		if((client.getEmail().equals(info.get(0)) || client.getPhoneNumber().equals(info.get(0))) && info.get(1).equals("haslo")) {
			isPaymentDone = true;
			return true;
		}
		return false;

	}
	
	public String captchaGeneretor() {
		
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"; 
		
		codeCaptcha = "";		
		Random randomizer = new Random();	
		final int lengthOfCode = 6;
		StringBuilder builder = new StringBuilder(lengthOfCode);
		
//		NOTE: Generowanie kodu Captcha
			for(int j = 0;j<lengthOfCode;j++) {
				
				builder.append(CHARACTERS.charAt(randomizer.nextInt(CHARACTERS.length()))); 
				
			}
			codeCaptcha = builder.toString();
			builder.delete(0,builder.length());
			
		return codeCaptcha;
		
	}

	@Override
	public void starFrame(MainGUI main) {
		
		new PayPalGUI(main);
		
	}

	
}
