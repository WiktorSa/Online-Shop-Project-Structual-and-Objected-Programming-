package waysofpayments;

import java.util.ArrayList;
import java.util.Arrays;

import client.Client;

//Interfejs zaimplementowany przez Szymona Sawczuka

public interface WaysOfPayments{

	public abstract String getName();
	public abstract boolean isPaymentDone();
	public abstract void pay(Client client); //NOTE: Metoda dokonywania platnosci
	public final ArrayList<String> TYPESOFPAYMENT = new ArrayList<String>(Arrays.asList("Blik","Platnosc karta","Paypal"));

}