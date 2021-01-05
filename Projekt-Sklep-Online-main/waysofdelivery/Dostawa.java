package waysofdelivery;
import client.Client;

// Interfejs stworzony przez Jana Skibiñskiego
public interface Dostawa {
	public boolean provideDeliveryInformations(Client client);
	public String deliveryInfo();
}