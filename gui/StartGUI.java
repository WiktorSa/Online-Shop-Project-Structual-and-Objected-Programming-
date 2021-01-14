package gui;

import client.Client;
import client.UnregisteredClient;

// Klasa stworzona przez Wiktora Sadowego 
public class StartGUI
{
	// Drobne uwagi - na poczatku ignorujemy klienta zarejestrowanego az nie upewnimy sie, ze wszystkie inne algorytmy dzialaja
	public static void main(String[] args) 
	{
		Client client = new UnregisteredClient();
		new ShopGUI(client);
	}
}
