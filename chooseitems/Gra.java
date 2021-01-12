package chooseitems;

import java.io.Serializable;
import java.util.ArrayList;

//Klasa utworzona przez Szymona Sawczuka
public class Gra extends Product implements Serializable{
	
	private String subCategory; //np. RPG, survival itd. 
	private String author;
	private String publisher;
	private String premiereDate;
	private String platform;
	
	private static final long serialVersionUID = 3L;

	public Gra(String category, String name, double price,  String subcategory, String author, String publisher, String premiereDate, String platform) {
		
		super(category, name, price);
		this.subCategory = subcategory;
		this.author = author;
		this.publisher = publisher;
		this.premiereDate = premiereDate;
		this.platform = platform;
		
	}
	
	public Gra(ArrayList<String> information){
		
		super(information.get(0), information.get(1), Double.parseDouble(information.get(2)));
		this.subCategory = information.get(3);
		this.author = information.get(4);
		this.publisher = information.get(5);
		this.premiereDate = information.get(6);
		this.platform = information.get(7);
		
	}
	
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPlatform() {
		return platform;
	}
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public String getPremiereDate() {
		return premiereDate;
	}
	
	public void setPremiereDate(String premiereDate) {
		this.premiereDate = premiereDate;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getSubCategory() {
		return subCategory;
	}
	
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	
	@Override
	public String toString() {
		String info = super.toString();
		info = info + "Autor: " + author + "\n";
		info = info + "Gatunek: " + subCategory + "\n";
		info = info + "Producent: " + publisher + "\n";
		info = info + "Data premiery: " + premiereDate + "\n";
		info = info + "Platforma: " + platform + "\n";
		return info;
	}
	
	
	

}
