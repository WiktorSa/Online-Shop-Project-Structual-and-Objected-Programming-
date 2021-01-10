package chooseitems;

import java.io.Serializable;

//Klasa zaimplementowana przez Szymona Sawczuka
// toString dodany przez Wiktora Sadowego
public abstract class Product implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String category;
	private String name;
	private double price;
	
	public Product(String category, String name, double price) 
	{
		this.category = category;
		this.name = name;
		this.price = price;
	}
	
	public String getCategory() 
	{
		return category;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public double getPrice() 
	{
		return price;
	}
	
	public void setCategory(String category) 
	{
		this.category = category;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public void setPrice(double price) 
	{
		this.price = price;
	}

	public String toString()
	{
		String info = "";
		info = info + "Nazwa: " + name + "\n"; 
		info = info + "Cena: " + price + "\n";
		return info;
	}
}