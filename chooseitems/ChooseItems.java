package chooseitems;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.imageio.ImageIO;

// Klasa zaimplementowana przez Wiktora Sadowego oraz Szymona Sawczuka
public class ChooseItems 
{
	private String locationOfShopCatalog;
	private ArrayList<String> categories;
	// String - kategoria, reszta to informacje o produktach
	private TreeMap<String, ArrayList<Product>> listOfProducts;
	// Each product has an image attached to it. If we cannot find the picture we post a default one
	private TreeMap<Product, Image> imagesOfProducts = new TreeMap<Product, Image>();
	
	public ChooseItems()
	{
		this.locationOfShopCatalog = "Sklep/";
		this.categories = obtaincategories();
		this.listOfProducts = obtainListOfProducts(categories);
	}
	
	public String getLocationOfShopCatalog() 
	{
		return locationOfShopCatalog;
	}

	public void setLocationOfShopCatalog(String locationOfShopCatalog) 
	{
		this.locationOfShopCatalog = locationOfShopCatalog;
	}

	public ArrayList<String> getCategories() 
	{
		return categories;
	}

	public void setCategories(ArrayList<String> categories) 
	{
		this.categories = categories;
	}

	public TreeMap<String, ArrayList<Product>> getListOfProducts() 
	{
		return listOfProducts;
	}

	public void setListOfProducts(TreeMap<String, ArrayList<Product>> listOfProducts) 
	{
		this.listOfProducts = listOfProducts;
	}

	public TreeMap<Product, Image> getImagesOfProducts() 
	{
		return imagesOfProducts;
	}

	public void setImagesOfProducts(TreeMap<Product, Image> imagesOfProducts) 
	{
		this.imagesOfProducts = imagesOfProducts;
	}

	private ArrayList<String> obtaincategories()
	{
		BufferedReader categoriesReader = null;
		ArrayList<String> categories = new ArrayList<String>();
		
		try 
		{
			categoriesReader = new BufferedReader(new FileReader(locationOfShopCatalog + "kategorie.txt"));	
			
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
	
	// Sporzadzamy liste produktow, ktore odczytujemy
	private TreeMap<String, ArrayList<Product>> obtainListOfProducts(ArrayList<String> categories)
	{
		TreeMap<String, ArrayList<Product>> listOfProducts = new TreeMap<String, ArrayList<Product>>();
		
		for (String category : categories)
		{
			ArrayList<Product> products = obtainProducts(category);
			listOfProducts.put(category, products);
		}
		
		return listOfProducts;
	}
	
	// Idziemy do katalogu w ktorym znajduje sie produkty z podanej kategorii i zaczynamy odczytywac produkty
	private ArrayList<Product> obtainProducts(String category)
	{
		ArrayList<Product> products = new ArrayList<Product>();
		String locationOfCategoryCatalog = locationOfShopCatalog + category + "/";
		
		File[] files = new File(locationOfCategoryCatalog).listFiles();
		if (files != null) {
			for (int i=0; i<files.length; i++)
			{
				if (files[i].getName().substring(files[i].getName().length() - 3).equals("txt")) {
					
					Product product = obtainProduct(category, files[i]);
					if (product != null) {
						// Adding a product to the list of products
						products.add(product);
						
						// Trying to find the image for the given product
						String imageLocation = locationOfShopCatalog + category + "/" + files[i].getName().substring(0, files[i].getName().length() - 3) + "jpg";
						Image image = null;
						try 
						{
							image = ImageIO.read(new File(imageLocation));
						} 
						
						catch (IOException e) 
						{
							try 
							{
								image = ImageIO.read(new File(locationOfShopCatalog + category + "/notfound.jpg"));
							} 
							catch (IOException e2) 
							{
								System.exit(-1);
							}
						}
						
						imagesOfProducts.put(product, image);
					}
				}
			}
		}
		
		return products;
	}
	
	// Zdobywamy informacje o produkcie
	private Product obtainProduct(String category, File file)
	{
		BufferedReader bufferedReader = null;
		Product product = null;
		ArrayList<String> information = new ArrayList<String>();
		
		try 
		{
			bufferedReader = new BufferedReader(new FileReader(file));	
			
			String info = "";
			while ((info = bufferedReader.readLine()) != null)
			{
				try 
				{
					info = info.split(":")[1];
					information.add(info);
				} 
				catch (java.lang.ArrayIndexOutOfBoundsException e) 
				{
					// W pliku moga byc dodatkowe entery. Pomijamy te linijki
				}
			}

		} 
		catch (IOException e) 
		{
			return null;
		}
		finally 
		{
			try 
			{
				bufferedReader.close();
			} 
			catch (IOException e) 
			{
				return null;
			}
		}
		
		// Deklarujemy obiekt. Jezeli sa zle dane to danego produktu nie bedzie w sklepie
		try 
		{
			switch (category) 
			{
				case "Ksiazka":
					product = new Ksiazka(information);
					break;
					
				default:
					break;
			}
		} 
		catch (java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e) 
		{
			return null;
		}
		
		return product;
	}
	
}