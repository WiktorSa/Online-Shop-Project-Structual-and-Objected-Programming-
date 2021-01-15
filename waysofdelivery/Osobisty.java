package waysofdelivery;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import client.Client;
// Klasa zaimplementowana przez Jana Skibinskiego
public class Osobisty extends WaysOfDelivery implements Dostawa {

	private static Date dt = new Date();
	public Osobisty()
	{
		this.firstName="";
		this.lastName="";
		this.name="Odbior osobisty";
		this.price=0f;
	}
	public static Date getDt()
	{
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		return dt;
	}
	@SuppressWarnings("resource")
	public boolean provideDeliveryInformations(Client client)
	{
		//deklaracja zmiennych i ustawienie daty
		String decision;
		
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		
		setFirstName(client.getFirstName());
		setLastName(client.getLastName());
		
		System.out.println("Odbior osobisty bedzie mozliwy od dnia i godziny: "+ dt + " do konca dnia nastepnego w godzinach 8-20");
		//potwierdzenie
		
		Scanner scan2= new Scanner(System.in);
		System.out.println("Wpisz 1 by zatwierdzic dostawe na dany adres, lub nie 1 by anulowac.");
			decision=scan2.nextLine();
		if (decision.equals("1"))
		{
			System.out.println("Odbior osobisty zostal ustawiony pomyslnie.");
			return true;
		}
		else
		{
			return false;
		}
		
	}
	public String deliveryInfo()
	{
		return "Odbior osobisty zostal umowiony od dnia  "+dt+"\nGodnosc odbierajacego: "+ firstName +" "+lastName;
	}	
}