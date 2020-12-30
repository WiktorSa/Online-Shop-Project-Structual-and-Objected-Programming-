package waysofdelivery;

// Klasa stworzona przez Jana Skibinskiego
public abstract class WaysOfDelivery  {
	//zmienne
	
	public String clientNumber;
	public String clientEmail;
	public String firstName;
	public String lastName;
	public double price;
	public String name;
	public static boolean isItDone;
	//getery
	
	public double getPrice()
	{
		return price;
	}
	public String getName()
	{
		return name;
	}
	public static boolean isDeliveryDone()
	{
		return isItDone;
	}
	//setery
	
	public void setClientNumber(String number)
	{
		clientNumber=number;
	}
	public void setClientEmail(String email)
	{
		clientEmail=email;
	}
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	public void setisItDone(boolean isItDone)
	{
		this.isItDone=isItDone;
	}
}