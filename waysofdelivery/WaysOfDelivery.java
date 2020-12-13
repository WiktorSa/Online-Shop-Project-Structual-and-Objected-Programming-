package waysofdelivery;

// Klasa stworzona przez Jana Skibinskiego
public abstract class WaysOfDelivery  {
	public String clientNumber;
	public String clientEmail;
	public String firstName;
	public String lastName;
	public double price;
	public String name;
	public double getPrice()
	{
		return price;
	}
	public String getName()
	{
		return name;
	}
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
}