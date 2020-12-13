package lista9;
import java.util.Scanner;
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
	public String provideDeliveryInformations(Client client)
	{
		setClientNumber(client.getPhoneNumber());
		setClientEmail(client.getEmail());
		System.out.println("Proszê podaæ swoje miasto:");
		Scanner scan2= new Scanner(System.in);
		miasto=scan2.nextLine();
		System.out.println("Proszê podaæ swoj¹ ulicê z numerem domu/mieszkania:");
		ulica=scan2.nextLine();
		System.out.println("Proszê podaæ swój kod pocztowy:");
		kodPocztowy=scan2.nextLine();
		scan2.close();
		return "Dostawa kurierska do "+miasto+ " " + ulica+ " "+kodPocztowy+"\nZosta³a ustawiona pomyœlnie.";
	}
	public String deliveryInfo()
	{
		return "Dostawa zosta³a zamówiona na:" +miasto+ " "+ ulica+ " "+ kodPocztowy+" \nNa numer telefonu: "+ clientNumber;
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
	
}