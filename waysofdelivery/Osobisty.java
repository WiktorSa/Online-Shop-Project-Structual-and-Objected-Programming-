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
		this.name="Odbiór osobisty";
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
		System.out.println("Odbior osobisty bêdzie mozliwy od dnia i godziny: "+ dt + " do koñca dnia nastêpnego dnia w godzinach 8-20");
		System.out.println("Wpisz 1 by zatwierdzic odbior osobisty, lub nie 1 by anulowac.");
		Scanner scan2= new Scanner(System.in);
		potwierdzenie=scan2.nextInt();
		if(potwierdzenie==1)
		{
			return "Odbiór osobisty towaru zosta³ umówiony na dzieñ "+dt+" .";
		}
		else
		{
			return "Odbiór osobisty zosta³ anulowany";
		}
	}
	public String deliveryInfo()
	{
		return "Odbiór osobisty zosta³ umówiony od dnia  "+dt+"\nGodnoœæ odbieraj¹cego: "+ firstName +" "+lastName;
	}	
}