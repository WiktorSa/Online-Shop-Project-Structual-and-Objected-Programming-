package waysofdelivery;

import guiShop.MainGUI;
//Klasa stworzona przez Jana Skibinskiego
public class Kurier extends WaysOfDelivery implements Dostawa {

	private String miasto;
	private String ulica;
	private String kodPocztowy;
	public Kurier(MainGUI main)
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
		return "Dostawa na adres: " +miasto+ "\n"+ ulica+ " Kod pocztowy: "+ kodPocztowy+" \nNa numer telefonu: "+ clientNumber;
	}
	
	public void changeLayout()
	{
		main.changeLayoutToKurier();
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
	
	public boolean areAllInputsValid(String m, String u, String k)
	{
		if(isCorrectMiasto(m) && isCorrectUlica(u) && isCorrectKodPocztowy(k)){
			return true;
		}else {
			return false;
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