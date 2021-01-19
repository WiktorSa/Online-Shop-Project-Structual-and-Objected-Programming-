package waysofdelivery;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import client.Client;
// Klasa zaimplementowana przez Jana Skibinskiego
public class Paczkomat extends WaysOfDelivery implements Dostawa {
	
	private String paczkomatCode;
	private String miasto;
	public ArrayList<String> PACZKOMATLIST;
	public Paczkomat(Client client)
	{
		this.paczkomatCode="";
		this.clientEmail=client.getEmail();
		this.clientNumber=client.getPhoneNumber();
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
}
