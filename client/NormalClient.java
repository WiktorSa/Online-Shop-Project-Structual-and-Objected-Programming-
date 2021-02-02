package client;

import java.io.Serializable;

import waysofdelivery.Paczkomat;

// Klasa zaimplementowana przez Wiktora Sadowego uzywajac kodu Szymona Sawczuka
public class NormalClient extends RegisteredClient implements Serializable, Observer
{
	private static final long serialVersionUID = 1L;

	public NormalClient() 
	{
		super();
	}
	
	public NormalClient(Client client) 
	{
		super(client);
	}
	public void update(Paczkomat paczkomat)
	{
		paczkomat.getClient().setWayOfDelivery(paczkomat);
	}
}
