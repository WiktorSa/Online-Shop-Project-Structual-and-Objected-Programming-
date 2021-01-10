package waysofdelivery;

import java.util.Scanner;

import client.Client;
// Klasa zaimplementowana przez Jana Skibinskiego
public class Paczkomat extends WaysOfDelivery implements Dostawa {
	
	private String paczkomatCode;
	private String [] paczkomatList={"WRO321","WRO412","WRO643","WAR312","WAR321"};
	public Paczkomat()
	{
		this.paczkomatCode="";
		this.clientEmail="";
		this.clientNumber="";
		this.name="Paczkomat InPost";
		this.price=10.5f;
	}
	@SuppressWarnings("resource")
	public boolean provideDeliveryInformations(Client client)
	{
		//ustawianie+ deklaracja zmiennych
		
		setClientNumber(client.getPhoneNumber());
		setClientEmail(client.getEmail());
		String decision;
		//Informacja o przykladowych paczkomatach oraz branie kodu od uzytkownika
		
		System.out.println("Oto przykladowe paczkomaty do ktorych dostarczamy:\n" +paczkomatList[0]+"\n"+paczkomatList[1]+"\n"+paczkomatList[4]+"\n");
		System.out.println("Prosze podac kod paczkomatu do ktorego ma zostac dostarczony towar:");
			Scanner scan= new Scanner(System.in);
		paczkomatCode=scan.nextLine();
		while(isCodeValid(paczkomatCode)==-1)
		{
			System.out.println("Prosze podac poprawny kod paczkomatu!");
			paczkomatCode=scan.nextLine();
		}
		//Decyzja uzytownika
		
		System.out.println("Wpisz 1 by zatwierdzic dostawe do danego paczkomatu, lub nie 1 by anulowac.");
			decision=scan.nextLine();
		if (decision.equals("1"))
		{
			System.out.println("Dostawa do paczkomatu zostala ustawiona pomyslnie.");
			return true;
		}
		else
		{
			return false;
		}
	}
	public String deliveryInfo()
	{
		return "Dostawa zostala zamowiona do paczkomatu " + paczkomatCode + ".\nNa numer telefonu: " + clientNumber + "\nOraz adres email:" +clientEmail;
	}
	
	private int isCodeValid(String code)
	{
		//sprawdzamy czy kod podany znajduje sie na liscie paczkomatow
		
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
	//seter
	
	public void setPaczkomatCode(String kod)
	{
		paczkomatCode=kod;
	}
	//geter
	
	public String getPaczkomatCode()
	{
		return paczkomatCode;
	}
}
