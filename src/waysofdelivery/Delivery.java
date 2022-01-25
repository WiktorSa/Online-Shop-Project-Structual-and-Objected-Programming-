package waysofdelivery;

import java.util.ArrayList;

public interface Delivery {
	public String toString();
	public boolean isCorrectData(ArrayList<String> arrayList);
	public void setDeliveryInfo(ArrayList<String> arrayList);
}