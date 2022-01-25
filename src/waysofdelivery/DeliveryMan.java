package waysofdelivery;

import java.util.ArrayList;

import gui.shop.MainGUI;

public class DeliveryMan extends WaysOfDelivery implements Delivery {
	private String miasto;
	private String ulica;
	private String kodPocztowy;
	public DeliveryMan(MainGUI main)
	{
		this.main=main;
		this.miasto="";
		this.ulica="";
		this.kodPocztowy="";
		this.clientEmail=main.getClient().getEmail();
		this.clientNumber=main.getClient().getPhoneNumber();
		this.name="Kurier DPD";
		this.price=12.5f;
	}
	
	public String toString()
	{
		return "Dostawa do miasta: " +miasto+ "\nNa ulice:"+ ulica+ "\n Kod pocztowy: "+ kodPocztowy+" \nNa numer telefonu: "+ clientNumber;
	}
	
	public boolean isCorrectMiasto(String miasto)
	{
		if (miasto.isEmpty()) {
			return false;
		}
		
		char[] letters = miasto.toCharArray();
		for (int i=0; i<letters.length; i++)
		{
			if (!Character.isLetter(letters[i])) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isCorrectUlica(String ulica)
	{
		if (ulica.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean isCorrectKodPocztowy(String kodPocztowy)
	{
		if (kodPocztowy.length() == 5)
		{
			char[] digits = kodPocztowy.toCharArray();
			for (int i=0; i<5; i++)
			{
				if (!Character.isDigit(digits[i])){
					return false;
				}
			}
			
			return true;
		}
		else
		{
			return false;
		}		
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

	@Override
	public boolean isCorrectData(ArrayList<String> arrayList) 
	{
		return (isCorrectMiasto(arrayList.get(0)) && isCorrectUlica(arrayList.get(1)) && isCorrectKodPocztowy(arrayList.get(2)));
	}

	@Override
	public void setDeliveryInfo(ArrayList<String> arrayList) 
	{
		miasto = arrayList.get(0);
		ulica = arrayList.get(1);
		kodPocztowy = arrayList.get(2);
	}
}