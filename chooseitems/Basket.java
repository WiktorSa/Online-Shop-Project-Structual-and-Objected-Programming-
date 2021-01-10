package chooseitems;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

//Klasa zaimplementowana przez Szymona Sawczuka i Wiktora Sadowego
public class Basket implements Serializable
{
	private static final long serialVersionUID = 4369378650407739091L;
	
	HashMap<Product, Integer> products; // Produkt i ilosc produktu
	private double price;

	public Basket() 
	{
		this.products = new LinkedHashMap<Product, Integer>();
		this.price = 0;
	}
	
	public HashMap<Product, Integer> getProducts() 
	{
		return products;
	}

	public void setProducts(HashMap<Product, Integer> products) 
	{
		this.products = products;
	}

	public double getPrice() 
	{
		return price;
	}
	
	public void setPrice(double price) 
	{
		this.price = price;
	}

	public String toString()
	{
		String info = "";
		Object[] items = products.keySet().toArray();
		for (int i=0; i<items.length; i++)
		{
			info = info + (i+1) + ". Nazwa: " + ((Product) items[i]).getName() + " Cena: " + ((Product) items[i]).getPrice() + " Ilosc: " + products.get(items[i]) + "\n";
		}
		info = info + "Cena koncowa: " + String.format("%.2f", price);
		return info;
	}
	
	public String toStringWithoutPrice()
	{
		String info = "";
		Object[] items = products.keySet().toArray();
		for (int i=0; i<items.length; i++)
		{
			info = info + (i+1) + ". Nazwa: " + ((Product) items[i]).getName() + " Cena: " + ((Product) items[i]).getPrice() + " Ilosc: " + products.get(items[i]) + "\n";
		}
		return info;
	}
	
	public void addAProductToTheBasket(Product product, int numberOfProducts)
	{
		if (products.containsKey(product)){
			products.put(product, products.get(product) + numberOfProducts);
		}
		else {
			products.put(product, numberOfProducts);
		}
		price = price + numberOfProducts * product.getPrice();
	}

	public void eraseAProductFromTheBasket(Product product, int numberOfProducts)
	{
		products.put(product, products.get(product) - numberOfProducts);
		price = price - numberOfProducts * product.getPrice();
		if (products.get(product) == 0) {
			products.remove(product);
		}
	}
}