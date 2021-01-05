package chooseitems;

import java.io.Serializable;

//Klasa zaimplementowana przez Szymona Sawczuka

public class Basket implements Serializable{
	
	
	private static final long serialVersionUID = 4369378650407739091L;
	private String category, name;
	private double price;
	private int amountOfProduct;
	
	public Basket(String category, String name, double price, int amountOfProduct) {
		this.category = category;
		this.name = name;
		this.price = price;
		this.amountOfProduct = amountOfProduct;
	}
	
	public int getAmountOfProduct() {
		return amountOfProduct;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setAmountOfProduct(int amountOfProduct) {
		this.amountOfProduct = amountOfProduct;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	

}