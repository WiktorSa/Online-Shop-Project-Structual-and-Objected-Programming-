package waysofdelivery;

import java.util.Scanner;

import client.Client;
// Klasa zaimplementowana przez Jana Skibinskiego
public class Paczkomat extends WaysOfDelivery implements Dostawa {

	private String paczkomatCode;
	public String [] paczkomatList={"WRO321","WRO412","WRO643","WAR312","WAR321"};
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
		System.out.println("Oto przykladowe paczkomaty do ktorych dostarczamy:\n" +paczkomatList[0]+"\n"+paczkomatList[1]+"\n"+paczkomatList[4]+"\n");
		System.out.println("Proszę podać kod paczkomatu do którego ma zostać dostarczony towar:");
		Scanner scan= new Scanner(System.in);
		paczkomatCode=scan.nextLine();
		while(isCodeValid(paczkomatCode)==-1)
		{
			System.out.println("Prosze podac poprawny kod paczkomatu!");
			paczkomatCode=scan.nextLine();
		}
		scan.close();
		return "Pomyślnie ustawiono informacje potrzebne do dostawy.";
	}
	public String deliveryInfo()
	{
		return "Dostawa zostala zamówiona do paczkomatu " + paczkomatCode + ".\nNa numer telefonu" + clientNumber + "\nOraz adres email:" +clientEmail;
	}
	private int isCodeValid(String code)
	{
		for(int i=0;i<paczkomatList.length;i++)
		{
			if(paczkomatList[i].equals(code))
			{
				return 1;
			}
			if(i==paczkomatList.length-1)
			{
				return -1;
			}
		}
		return 0;
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
