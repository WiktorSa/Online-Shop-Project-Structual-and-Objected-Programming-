package waysofdelivery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Klasa stworzona przez Jana Skibinskiego
public abstract class WaysOfDelivery {
	
	//zmienne
	private static String locationOfShopCatalog= "Sklep/";
	public String clientNumber;
	public String clientEmail;
	public String firstName;
	public String lastName;
	public double price;
	public String name;
	private static ArrayList <String> categories=WODobtainCategories();
	//getery
	
	public double getPrice()
	{
		return price;
	}
	public String getName()
	{
		return name;
	}
	//setery
	
	public void setClientNumber(String number)
	{
		clientNumber=number;
	}
	public void setClientEmail(String email)
	{
		clientEmail=email;
	}
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	
	public static ArrayList<String> getCategories()
	{
		return categories;
	}
	private static ArrayList<String> WODobtainCategories()
	{
		BufferedReader categoriesReader = null;
		ArrayList<String> categories = new ArrayList<String>();
		
		try 
		{
			categoriesReader = new BufferedReader(new FileReader(locationOfShopCatalog + "WODcategories.txt"));	
			
			String categoryName = "";
			while ((categoryName = categoriesReader.readLine()) != null)
			{
				categories.add(categoryName);
			}

		} 
		catch (IOException e) 
		{
			System.out.println("Nastapil krytyczny blad w dzialaniu aplikacji.");
			System.exit(-1);
		}
		finally 
		{
			try 
			{
				categoriesReader.close();
			} 
			catch (IOException e) 
			{
				System.out.println("Krytyczny blad w dzialaniu aplikacji");
				System.exit(-1);
			}
		}
		
		return categories;
	}
}
