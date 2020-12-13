package waysofpayments;
//Interfejs zaimplementowany przez Szymona Sawczuka

public interface WaysOfPayments {

	public abstract String getName();
	public abstract boolean isPaymentDone();
	public abstract void pay(); //NOTE: Metoda dokonywania p³atnoœci

}