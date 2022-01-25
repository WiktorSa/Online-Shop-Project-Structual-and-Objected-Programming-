package waysofdelivery;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import gui.shop.MainGUI;

public class ParcelLocker extends WaysOfDelivery implements Delivery {
	private String parcelLockerCode;
	private String city;
	private ArrayList<String> PARCELLOCKERLIST;
	
	public ParcelLocker(MainGUI main){
		this.main = main;
		this.parcelLockerCode = "";
		this.clientEmail = main.getClient().getEmail();
		this.clientNumber = main.getClient().getPhoneNumber();
		this.name = "Paczkomat InPost";
		this.price = 10.5f;
	}

	public String toString(){
		return "Dostawa zostala zamowiona do paczkomatu " + parcelLockerCode + ".\nNa numer telefonu: " + clientNumber + "\nOraz adres email:" + clientEmail;
	}
	
	public String closeByPaczkomat() {
		int randomNum = ThreadLocalRandom.current().nextInt(100, 998 + 1);
		String code = Character.toString(Character.toUpperCase(city.charAt(0))) + Character.toUpperCase((city.charAt(1))) + Character.toUpperCase(city.charAt(2));
		return code + String.valueOf(randomNum);
	}
	
	public boolean isCorrectCode(String parcelLockerCode){
		if(parcelLockerCode.isEmpty()) {
			return false;
		}
		
		if (parcelLockerCode.length() == 6){
			char[] letters = parcelLockerCode.toCharArray();
			for (int i=0; i<parcelLockerCode.length(); i++) {
				if(i<3) {
					if(!Character.isLetter(letters[i])) {
						return false;
					}
				}
				else{
					if(!Character.isDigit(letters[i])) {
						return false;
					}
				}	
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isCorrectCity(String city) {
		if (city.isEmpty()) {
			return false;
		}
		
		if(city.length()<3) {
			return false;
		}
		
		char[] letters = city.toCharArray();
		for (int i=0; i<letters.length; i++) {
			if (!Character.isLetter(letters[i])) {
				return false;
			}
		}
		return true;
	}
	 
	public void setParcelLockerCode(String parcelLockerCode){
		this.parcelLockerCode = parcelLockerCode;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setParcelLockerList() {
		PARCELLOCKERLIST = null;
		PARCELLOCKERLIST = new ArrayList<String>();
		PARCELLOCKERLIST.add(closeByPaczkomat());
		PARCELLOCKERLIST.add(closeByPaczkomat());
		PARCELLOCKERLIST.add(closeByPaczkomat());
	}
	
	public String getParcelLockerCode() {
		return parcelLockerCode;
	}
	
	public String getMiasto() {
		return city;
	}
	
	public ArrayList <String> getPARCELLOCKERLIST() {
		return PARCELLOCKERLIST;
	}

	@Override
	public boolean isCorrectData(ArrayList<String> data) {
		return isCorrectCode(data.get(0));
	}

	@Override
	public void setDeliveryInfo(ArrayList<String> data) {
		parcelLockerCode = data.get(0);
	}
}
