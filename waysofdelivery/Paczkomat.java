package waysofdelivery;

import java.util.Scanner;

import client.Client;
// Klasa zaimplementowana przez Jana Skibinskiego
public class Paczkomat extends WaysOfDelivery implements Dostawa {

	private String paczkomatCode;
	public Paczkomat()
	{
		this.paczkomatCode="";
		this.clientEmail="";
		this.clientNumber="";
		this.name="Paczkomat InPost";
		this.price=10.5f;
	}
	@SuppressWarnings("resource")
	public String provideDeliveryInformations(Client client)
	{
		setClientNumber(client.getPhoneNumber());
		setClientEmail(client.getEmail());
		System.out.println("Prosze podac kod paczkomatu do którego ma zostac dostarczony towar:");
		Scanner scan= new Scanner(System.in);
		paczkomatCode=scan.nextLine();
		return "Pomyslnie ustawiono informacje potrzebne do dostawy.";
	}
	public String deliveryInfo()
	{
		return "Dostawa zostala zamówiona do paczkomatu " + paczkomatCode + ".\nNa numer telefonu" + clientNumber + "\nOraz adres email:" +clientEmail;
	}
	
	public void setPaczkomatCode(String kod)
	{
		paczkomatCode=kod;
	}
	
	public String getPaczkomatCode()
	{
		return paczkomatCode;
	}
}