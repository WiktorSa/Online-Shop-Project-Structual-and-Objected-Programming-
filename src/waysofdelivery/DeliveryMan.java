package waysofdelivery;

import java.util.ArrayList;

import gui.shop.MainGUI;

public class DeliveryMan extends WaysOfDelivery implements Delivery {
	private String city;
	private String street;
	private String postcode;
	
	public DeliveryMan(MainGUI main){
		this.main = main;
		this.city = "";
		this.street = "";
		this.postcode = "";
		this.clientEmail = main.getClient().getEmail();
		this.clientNumber = main.getClient().getPhoneNumber();
		this.name = "Kurier DPD";
		this.price = 12.5f;
	}
	
	public String toString() {
		return "Dostawa do miasta: " + city + "\nNa ulice:" + street + "\n Kod pocztowy: " + postcode +" \nNa numer telefonu: " + clientNumber;
	}
	
	public boolean isCorrectCity(String city)
	{
		if (city.isEmpty()) {
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
	
	public boolean isCorrectStreet(String street)
	{
		if (street.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean isCorrectPostcode(String postcode)
	{
		if (postcode.length() == 5){
			char[] digits = postcode.toCharArray();
			for (int i=0; i<5; i++){
				if (!Character.isDigit(digits[i])){
					return false;
				}
			}
			
			return true;
		}
		else{
			return false;
		}		
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public void setStreet(String street){
		this.street = street;
	}
	
	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getCity(){
		return city;
	}
	
	public String getStreet(){
		return street;
	}
	
	public String getPostcode(){
		return postcode;
	}

	@Override
	public boolean isCorrectData(ArrayList<String> arrayList) {
		return (isCorrectCity(arrayList.get(0)) && isCorrectStreet(arrayList.get(1)) && isCorrectPostcode(arrayList.get(2)));
	}

	@Override
	public void setDeliveryInfo(ArrayList<String> arrayList) {
		city = arrayList.get(0);
		street = arrayList.get(1);
		postcode = arrayList.get(2);
	}
}