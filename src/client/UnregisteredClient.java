package client;

import java.io.Serializable;

public class UnregisteredClient extends Client implements Serializable
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
}
