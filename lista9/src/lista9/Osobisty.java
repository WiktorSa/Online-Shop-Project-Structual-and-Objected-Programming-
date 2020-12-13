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
		this.name="Odbi�r osobisty";
		this.price=0f;
	}
	public String provideDeliveryInformations(Client client)
	{
		setFirstName(client.getFirstName());
		setLastName(client.getLastName());
		System.out.println("Prosz� poda� dat� odbioru przedmiotu:");
		Scanner scan2= new Scanner(System.in);
		data=scan2.nextLine();
		System.out.println("Prosz� poda� godzin� odbioru przedmiotu:");
		godzina=scan2.nextLine();
		scan2.close();
		return "Odbi�r osobisty towaru zosta� um�wiony na dzie� "+data+" oraz godzin� "+godzina+".";
	}
	public String deliveryInfo()
	{
		return "Odbi�r osobisty zosta� um�wiony na dzie� "+data+" oraz godzin� "+godzina+"\nGodno�� odbieraj�cego: "+ firstName +" "+lastName;
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
