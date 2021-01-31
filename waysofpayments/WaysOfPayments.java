package waysofpayments;

import java.util.ArrayList;
import java.util.Arrays;

import client.Client;
import guiShop.MainGUI;

//Interfejs zaimplementowany przez Szymona Sawczuka

public interface WaysOfPayments{

	public abstract String getName();
	public abstract boolean isPaymentDone();
	public abstract boolean pay(Client client,  ArrayList<String> t); //NOTE: Metoda dokonywania platnosci
	public final ArrayList<String> TYPESOFPAYMENT = new ArrayList<String>(Arrays.asList("Blik","Platnosc karta","Paypal"));
	public abstract void starFrame(MainGUI main);

}