package chooseitems;

//Klasa zaimplementowana przez Szymona Sawczuka

public class Product {
	
	private String category, name;
	private double price;
	
	public Product(String category, String name, double price) {
		
		this.category = category;
		this.name = name;
		this.price = price;
		
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
