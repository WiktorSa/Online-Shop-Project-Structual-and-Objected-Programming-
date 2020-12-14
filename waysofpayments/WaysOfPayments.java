package waysofpayments;

import client.Client;

//Interfejs zaimplementowany przez Szymona Sawczuka

public interface WaysOfPayments {

	public abstract String getName();
	public abstract boolean isPaymentDone();
	public abstract void pay(Client client); //NOTE: Metoda dokonywania platnosci

}