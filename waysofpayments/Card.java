package waysofpayments;
import java.util.ArrayList;
import java.util.Calendar;

import client.Client;
import guiShop.MainGUI;
import guiWaysOfPayment.CardGUI;

//Klasa zimplementowana przez Szymona Sawczuka

public class Card implements WaysOfPayments{

	private boolean isPaymentDone = false;  //NOTE: Potrzebne aby moc poinformowac o dokonaniu platnosci

	@Override
	public String getName() {
		return "Platnosc karta";
	}

	@Override
	public boolean isPaymentDone() {
		return isPaymentDone;
	}
	
	@Override
	public boolean pay(Client client, ArrayList<String> info) {
		
		int[] tmpNumber = new int[16];
		
		for(int i = 0;i < info.get(0).length();i++) {
			tmpNumber[i] = info.get(0).charAt(i) - '0';
		}

		if(isValid(tmpNumber) && isValidDate(info.get(2))) {
			isPaymentDone = true;
			return true;
		}
		
		return false;
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
		
		return monthInteger >= Calendar.getInstance().get(Calendar.MONTH)+1  && monthInteger < 13 && yearInteger >= (Calendar.getInstance().get(Calendar.YEAR)%100); 
	}

	@Override
	public void starFrame(MainGUI main) {
		
		new CardGUI(main);
		
	}
}
