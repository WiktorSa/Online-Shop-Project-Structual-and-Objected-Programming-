package lista9;
import java.util.Scanner;
public class Osobisty extends WaysOfDelivery implements Dostawa {

	private String data;
	private String godzina;
	public Osobisty()
	{
		this.data="";
		this.godzina="";
		this.firstName="";
		this.lastName="";
		this.name="Odbiór osobisty";
		this.price=0f;
	}
	public String provideDeliveryInformations(Client client)
	{
		setFirstName(client.getFirstName());
		setLastName(client.getLastName());
		System.out.println("Proszê podaæ datê odbioru przedmiotu:");
		Scanner scan2= new Scanner(System.in);
		data=scan2.nextLine();
		System.out.println("Proszê podaæ godzinê odbioru przedmiotu:");
		godzina=scan2.nextLine();
		scan2.close();
		return "Odbiór osobisty towaru zosta³ umówiony na dzieñ "+data+" oraz godzinê "+godzina+".";
	}
	public String deliveryInfo()
	{
		return "Odbiór osobisty zosta³ umówiony na dzieñ "+data+" oraz godzinê "+godzina+"\nGodnoœæ odbieraj¹cego: "+ firstName +" "+lastName;
	}
	
	public void setData(String data)
	{
		this.data=data;
	}
	public void setGodzina(String godzina)
	{
		this.godzina=godzina;
	}

	public String getData()
	{
		return data;
	}
	public String getGodzina()
	{
		return godzina;
	}
	
}
