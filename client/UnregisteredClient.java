package client;
import waysofdelivery.Paczkomat;
import java.io.Serializable;


// Klasa zaimportowana przez Wiktora Sadowego uzywajac kodu Szymona Sawczuka
public class UnregisteredClient extends Client implements Serializable,Observer
{
	private static final long serialVersionUID = 8311207197449109437L;
	public UnregisteredClient() 
	{
		super();
	}
	
	public UnregisteredClient(Client client)
	{
		super(client);
	}
	public void update(Paczkomat paczkomat)
	{
		paczkomat.getClient().setWayOfDelivery(paczkomat);
	}
}
