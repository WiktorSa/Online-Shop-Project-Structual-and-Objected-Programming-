package waysofpayments;

import java.util.ArrayList;
import java.util.Arrays;
import client.Client;
import gui.shop.MainGUI;

public interface WaysOfPayments{
	public abstract String getName();
	public abstract boolean isPaymentDone();
	public abstract boolean pay(Client client,  ArrayList<String> t);
	public final ArrayList<String> TYPESOFPAYMENT = new ArrayList<String>(Arrays.asList("Blik","Platnosc karta","Paypal"));
	public abstract void starFrame(MainGUI main);

}