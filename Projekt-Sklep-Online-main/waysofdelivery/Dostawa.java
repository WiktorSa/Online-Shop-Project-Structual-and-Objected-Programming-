package waysofdelivery;
import client.Client;

// Interfejs stworzony przez Jana Skibińskiego
public interface Dostawa {
	public boolean provideDeliveryInformations(Client client);
	public String deliveryInfo();
}