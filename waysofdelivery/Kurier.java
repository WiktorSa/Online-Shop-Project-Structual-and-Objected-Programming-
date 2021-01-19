package waysofdelivery;

import client.Client;
//Klasa stworzona przez Jana Skibinskiego
public class Kurier extends WaysOfDelivery implements Dostawa {

	private String miasto;
	private String ulica;
	private String kodPocztowy;
	public Kurier(Client client)
	{
		this.miasto="";
		this.ulica="";
		this.kodPocztowy="";
		this.clientEmail=client.getEmail();
		this.clientNumber=client.getPhoneNumber();
		this.name="Kurier DPD";
		this.price=12.5f;
	}
	
	public String toString()
	{
		return "Dostawa na adres: " +miasto+ "\n"+ ulica+ " Kod pocztowy: "+ kodPocztowy+" \nNa numer telefonu: "+ clientNumber;
	}
	//setery
	
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
	//getery
	
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