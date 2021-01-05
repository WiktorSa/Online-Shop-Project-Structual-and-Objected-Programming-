package waysofdelivery;
import java.util.Scanner;

import client.Client;
//Klasa stworzona przez Jana Skibinskiego
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
	public boolean provideDeliveryInformations(Client client)
	{
		//ustawienie/deklaracja zmiennych
		
		setClientNumber(client.getPhoneNumber());
		setClientEmail(client.getEmail());
		String decision;
		// scaner
		
		Scanner scan2= new Scanner(System.in);
		//branie danych
		
		System.out.println("Prosze podac swoje miasto:");
			miasto=scan2.nextLine();
		System.out.println("Prosze podac swoja ulice z numerem domu/mieszkania:");
			ulica=scan2.nextLine();
		setCorrectPostCode();
			
		System.out.println("Wpisz 1 by zatwierdzic dostawe na dany adres, lub nie 1 by anulowac.");
			decision=scan2.nextLine();
			
		if (decision.equals("1"))
		{
			System.out.println("Dostawa kurierska zostala ustawiona pomyslnie.");
			return true;
		}
		else
		{
			return false;
		}
		
	}
	public String deliveryInfo()
	{
		return "Dostawa zostala zamowiona do: " +miasto+ " "+ ulica+ " "+ kodPocztowy+" \nNa numer telefonu: "+ clientNumber;
	}
	@SuppressWarnings("resource")
	private void setCorrectPostCode() //pilnujemy by kod byl poprawny
	{
		boolean shouldStopSetingPostCode = false;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Podaj kod pocztowy");
		while (!shouldStopSetingPostCode)
		{
			String kodPocztowy = scanner.nextLine();
			//najpierw dlugosc
			if (kodPocztowy.length() == 6) {
				//a potem zawartosc
				char[] digits = kodPocztowy.toCharArray();
				for (int i=0; i<6; i++)
				{
					if(i!=2) 
					{
						if (!Character.isDigit(digits[i]))
						{
						System.out.println("Podaj poprawny kod pocztowy");
						break;
						}
					}
					if(i==2)
					{
						if(digits[i]!='-')
						{
							System.out.println("Podaj poprawny kod pocztowy");
							break;
						}
					}
					// Sprawdzilismy caly kod pocztowy, wiec jest dobry
					if (i==5) 
					{
						shouldStopSetingPostCode = true;
					}
				}
				if (shouldStopSetingPostCode) 
				{
					this.kodPocztowy = kodPocztowy;
				}
			}
			else 
			{
				System.out.println("Podaj poprawny kod pocztowy");
			}
		}
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