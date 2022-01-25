package waysofdelivery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import gui.shop.MainGUI;

public class Personal extends WaysOfDelivery implements Delivery {
	private static Date dt = new Date();
	
	public Personal(MainGUI main) {
		this.main = main;
		this.firstName = main.getClient().getFirstName();
		this.lastName = main.getClient().getLastName();
		this.name = "Odbior osobisty";
		this.price = 0f;
	}
	
	public void setTomDt() {
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
	}
	
	public Date getDt() {
		return dt;
	}

	public String toString() {
		return "Odbior osobisty zostal umowiony od dnia  " + dt + "\nGodnosc odbierajacego: " + firstName + " " + lastName;
	}	
	
	public boolean isCorrectInput(String input) {
		return true;
	}
	
	@Override
	public boolean isCorrectData(ArrayList<String> data) {
		return true;
	}
	
	@Override
	public void setDeliveryInfo(ArrayList<String> arrayList) {
	}
}