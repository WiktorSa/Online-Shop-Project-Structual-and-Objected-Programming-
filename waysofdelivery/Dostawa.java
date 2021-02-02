package waysofdelivery;

import java.util.ArrayList;

// Interfejs stworzony przez Jana Skibi�skiego
public interface Dostawa {
	public String toString();
	public boolean isCorrectData(ArrayList<String> arrayList);
	public void setDeliveryInfo(ArrayList<String> arrayList);
	public boolean isCorrectMiasto(String miasto);
	public void setMiasto(String miasto);
}