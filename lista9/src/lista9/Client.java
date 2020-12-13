package lista9;

class Client
{
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	
	public Client() 
	{
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.phoneNumber = "";
	}
	
	public String getFirstName() 
	{
		return firstName;
	}
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	public String getLastName() 
	{
		return lastName;
	}
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() 
	{
		return "Dane o kliencie: Imie: " + firstName + ", Nazwisko: " + lastName + ", Email: " + email + ", Numer telefonu: " + phoneNumber;
	}
}