package waysofdelivery;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import client.Client;
// Klasa zaimplementowana przez Jana Skibinskiego
public class Osobisty extends WaysOfDelivery implements Dostawa {

	private Date dt = new Date();
	private int potwierdzenie;
	public Osobisty()
	{
		this.potwierdzenie=-1;
		this.firstName="";
		this.lastName="";
		this.name="Odbi�r osobisty";
		this.price=0f;
	}
	@SuppressWarnings("resource")
	public String provideDeliveryInformations(Client client)
	{
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		setFirstName(client.getFirstName());
		setLastName(client.getLastName());
		System.out.println("Odbior osobisty b�dzie mozliwy od dnia i godziny: "+ dt + " do ko�ca dnia nast�pnego dnia w godzinach 8-20");
		System.out.println("Wpisz 1 by zatwierdzic odbior osobisty, lub nie 1 by anulowac.");
		Scanner scan2= new Scanner(System.in);
		potwierdzenie=scan2.nextInt();
		if(potwierdzenie==1)
		{
			return "Odbi�r osobisty towaru zosta� um�wiony na dzie� "+dt+" .";
		}
		else
		{
			return "Odbi�r osobisty zosta� anulowany";
		}
	}
	public String deliveryInfo()
	{
		return "Odbi�r osobisty zosta� um�wiony od dnia  "+dt+"\nGodno�� odbieraj�cego: "+ firstName +" "+lastName;
	}	
}