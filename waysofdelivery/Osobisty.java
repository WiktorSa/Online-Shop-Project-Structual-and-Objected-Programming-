package waysofdelivery;
import java.util.Calendar;
import java.util.Date;
import client.Client;
// Klasa zaimplementowana przez Jana Skibinskiego
public class Osobisty extends WaysOfDelivery implements Dostawa {

	private static Date dt = new Date();
	public Osobisty(Client client)
	{
		this.firstName=client.getFirstName();
		this.lastName=client.getLastName();
		this.name="Odbior osobisty";
		this.price=0f;
	}
	public void setTomDt()
	{
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
	}
	public Date getDt()
	{
		return dt;
	}

	public String toString()
	{
		return "Odbior osobisty zostal umowiony od dnia  "+dt+"\nGodnosc odbierajacego: "+ firstName +" "+lastName;
	}	
}