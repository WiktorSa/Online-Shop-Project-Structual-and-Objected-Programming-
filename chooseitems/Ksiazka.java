package chooseitems;

import java.util.ArrayList;

// Klasa stworzona przez Wiktora Sadowego
public class Ksiazka extends Product
{
	private static final long serialVersionUID = 1L;
	private String author;
	private String subcategory; // np. biograficzna itd.
	private String publisher;
	private String premiereDate;
	private int numberOfPages;
	
	public Ksiazka(String category, String name, double price, String author, String subcategory, String publisher, String premiereDate, int numberOfPages) 
	{
		super(category, name, price);
		this.author = author;
		this.subcategory = subcategory;
		this.publisher = publisher;
		this.premiereDate = premiereDate;
		this.numberOfPages = numberOfPages;
	}
	
	// Mozemy tez przekazac arraylist zawierajacy wlasciwe informacje
	public Ksiazka(ArrayList<String> information)
	{
		super(information.get(0), information.get(1), Double.parseDouble(information.get(2)));
		this.author = information.get(3);
		this.subcategory = information.get(4);
		this.publisher = information.get(5);
		this.premiereDate = information.get(6);
		this.numberOfPages = Integer.parseInt(information.get(7));
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getSubcategory() 
	{
		return subcategory;
	}

	public void setSubcategory(String subcategory) 
	{
		this.subcategory = subcategory;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPremiereDate() {
		return premiereDate;
	}

	public void setPremiereDate(String premiereDate) {
		this.premiereDate = premiereDate;
	}

	public int getNumberOfPages() 
	{
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) 
	{
		this.numberOfPages = numberOfPages;
	}
	
	public String toString()
	{
		String info = super.toString();
		info = info + "Autor: " + author + "\n";
		info = info + "Tematyka: " + subcategory + "\n";
		info = info + "Wydawnictwo: " + publisher + "\n";
		info = info + "Data premiery: " + premiereDate + "\n";
		info = info + "Liczba stron: " + numberOfPages + "\n";
		return info;
	}
}
