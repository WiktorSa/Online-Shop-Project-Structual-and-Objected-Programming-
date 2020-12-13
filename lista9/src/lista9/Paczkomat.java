package lista9;
import java.util.Scanner;
public class Paczkomat extends WaysOfDelivery implements Dostawa {

	private String paczkomatCode;
	public Paczkomat()
	{
		this.paczkomatCode="";
		this.clientEmail="";
		this.clientNumber="";
		this.name="Paczkomat InPost";
		this.price=10.5f;
	}
	public String provideDeliveryInformations(Client client)
	{
		setClientNumber(client.getPhoneNumber());
		setClientEmail(client.getEmail());
		System.out.println("Proszê podaæ kod paczkomatu do którego ma zostaæ dostarczony towar:");
		Scanner scan= new Scanner(System.in);
		paczkomatCode=scan.nextLine();
		scan.close();
		return "Pomyœlnie ustawiono informacje potrzebne do dostawy.";
	}
	public String deliveryInfo()
	{
		return "Dostawa zosta³a zamówiona do paczkomatu " + paczkomatCode + ".\nNa numer telefonu" + clientNumber + "\nOraz adres email:" +clientEmail;
	}
	
	public void setPaczkomatCode(String kod)
	{
		paczkomatCode=kod;
	}
	
	public String getPaczkomatCode()
	{
		return paczkomatCode;
	}
}
