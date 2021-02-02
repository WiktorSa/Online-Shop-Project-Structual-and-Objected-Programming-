package waysofdelivery;
import java.util.ArrayList;
import guiShop.MainGUI;
import java.util.concurrent.ThreadLocalRandom;
// Klasa zaimplementowana przez Jana Skibinskiego
public class Paczkomat extends WaysOfDelivery implements Dostawa {
	
	private String paczkomatCode;
	private String miasto;
	private ArrayList<String> PACZKOMATLIST;
	public Paczkomat(MainGUI main)
	{
		this.main=main;
		this.paczkomatCode="";
		this.clientEmail=main.getClient().getEmail();
		this.clientNumber=main.getClient().getPhoneNumber();
		this.name="Paczkomat InPost";
		this.price=10.5f;
	}

	public String toString()
	{
		return "Dostawa zostala zamowiona do paczkomatu " + paczkomatCode + ".\nNa numer telefonu: " + clientNumber + "\nOraz adres email:" +clientEmail;
	}
	
	public String closeByPaczkomat()
	{
		int randomNum = ThreadLocalRandom.current().nextInt(100, 998 + 1);
		String code=Character.toString(Character.toUpperCase(miasto.charAt(0)))+Character.toUpperCase((miasto.charAt(1)))+Character.toUpperCase(miasto.charAt(2));
		return code+String.valueOf(randomNum);
	}
	
	public boolean isCorrectCode(String paczkomatCode)
	{
		if(paczkomatCode.isEmpty())
		{
			return false;
		}
		if (paczkomatCode.length() == 6)
		{
			char[] letters = paczkomatCode.toCharArray();
			for (int i=0; i<paczkomatCode.length(); i++)
			{
				if(i<3)
				{
					if(!Character.isLetter(letters[i]))
					{
						return false;
					}
				}
				else
				{
					if(!Character.isDigit(letters[i]))
					{
						return false;
					}
				}	
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isCorrectMiasto(String miasto)
	{
		if (miasto.isEmpty()) {
			return false;
		}
		if(miasto.length()<3)
		{
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
	 
	//seter
	
	public void setPaczkomatCode(String kod)
	{
		paczkomatCode=kod;
	}
	public void setMiasto(String miasto)
	{
		this.miasto=miasto;
	}
	public void setPaczkomatList()
	{
		PACZKOMATLIST=null;
		PACZKOMATLIST=new ArrayList<String>();
		PACZKOMATLIST.add(closeByPaczkomat());
		PACZKOMATLIST.add(closeByPaczkomat());
		PACZKOMATLIST.add(closeByPaczkomat());
	}
	//geter
	
	public String getPaczkomatCode()
	{
		return paczkomatCode;
	}
	public String getMiasto()
	{
		return miasto;
	}
	public ArrayList <String> getPACZKOMATLIST()
	{
		return PACZKOMATLIST;
	}

	@Override
	public boolean isCorrectData(ArrayList<String> arrayList) {
		return isCorrectCode(arrayList.get(0));
	}

	@Override
	public void setDeliveryInfo(ArrayList<String> arrayList) 
	{
		paczkomatCode = arrayList.get(0);
	}
}
