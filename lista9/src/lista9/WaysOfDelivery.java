package lista9;

public abstract class WaysOfDelivery  {
	public String clientNumber;
	public String clientEmail;
	public String firstName;
	public String lastName;
	public float price;
	public String name;
	public float getPrice()
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
