package chooseitems;

import java.util.ArrayList;

public class Record extends Product
{
	private static final long serialVersionUID = 2L;
	// for instance classical
	private String subCategory;
	private String author;
	private String publisher;
	private String premiereDate;
	
	public Record(String category, String name, double price,  String subcategory, String author, String publisher, String premiereDate) 
	{
		super(category, name, price);
		this.subCategory = subcategory;
		this.author = author;
		this.publisher = publisher;
		this.premiereDate = premiereDate;
	}
	
	public Record(ArrayList<String> information)
	{
		super(information.get(0), information.get(1), Double.parseDouble(information.get(2)));
		this.subCategory = information.get(3);
		this.author = information.get(4);
		this.publisher = information.get(5);
		this.premiereDate = information.get(6);
	}

	public String getSubcategory() 
	{
		return subCategory;
	}

	public void setSubcategory(String subcategory) 
	{
		this.subCategory = subcategory;
	}
  
  	public String getAuthor()
	{
		return author;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public String getPublisher() 
	{
		return publisher;
	}

	public void setPublisher(String publisher) 
	{
		this.publisher = publisher;
	}

	public String getPremiereDate() 
	{
		return premiereDate;
	}

	public void setPremiereDate(String premiereDate) 
	{
		this.premiereDate = premiereDate;
	}

	public String toString()
	{
		String info = super.toString();
		info = info + "Autor: " + author + "\n";
		info = info + "Gatunek: " + subCategory + "\n";
		info = info + "Producent: " + publisher + "\n";
		info = info + "Data premiery: " + premiereDate + "\n";
		return info;
	}
}