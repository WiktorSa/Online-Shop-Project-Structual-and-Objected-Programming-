package waysofdelivery;
import java.util.Scanner;

import client.Client;
//Klasa stworzona przez Jana Skibiñskiego
public class Kurier extends WaysOfDelivery implements Dostawa {

	private String miasto;
	private String ulica;
	private String kodPocztowy;
	public Kurier()
	{
		this.miasto="";
		this.ulica="";
		this.kodPocztowy="";
		this.clientEmail="";
		this.clientNumber="";
		this.name="Kurier DPD";
		this.price=12.5f;
	}
	@SuppressWarnings("resource")
	public String provideDeliveryInformations(Client client)
	{
		setClientNumber(client.getPhoneNumber());
		setClientEmail(client.getEmail());
		System.out.println("Prosze podac swoje miasto:");
		Scanner scan2= new Scanner(System.in);
		miasto=scan2.nextLine();
		System.out.println("Prosze podac swoja ulice z numerem domu/mieszkania:");
		ulica=scan2.nextLine();
		System.out.println("Prosze podac swój kod pocztowy:");
		kodPocztowy=scan2.nextLine();
		return "Dostawa kurierska do "+miasto+ " " + ulica+ " "+kodPocztowy+"\nZostala ustawiona pomyslnie.";
	}
	public String deliveryInfo()
	{
		return "Dostawa zostala zamówiona na: " +miasto+ " "+ ulica+ " "+ kodPocztowy+" \nNa numer telefonu: "+ clientNumber;
	}
	
	public void setMiasto(String miasto)
	{
		this.miasto=miasto;
	}
	public void setUlica(String ulica)
	{
		this.ulica=ulica;
	}
	public void setKodPocztowy(String kodPocztowy)
	{
		this.kodPocztowy=kodPocztowy;
	}
	
	public String getMiasto()
	{
		return miasto;
	}
	public String getUlica()
	{
		return ulica;
	}
	public String getKodPocztowy()
	{
		return kodPocztowy;
	}
	
}