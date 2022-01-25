package client;

import java.io.Serializable;

public class NormalClient extends RegisteredClient implements Serializable {
	private static final long serialVersionUID = 1L;

	public NormalClient() {
		super();
	}
	
	public NormalClient(Client client) {
		super(client);
	}
}
