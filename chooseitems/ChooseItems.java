package chooseitems;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import client.Client;

// Klasa zaimplementowana przez Wiktora Sadowego oraz Szymona Sawczuka
public class ChooseItems 
{
	private String locationOfShopCatalog;
	private ArrayList<String> categories;
	private TreeMap<String, ArrayList<Product>> listOfProducts; // String - kategoria, reszta to informacje o produktach
	private Basket basket;
	
	public ChooseItems(Client client) //NOTE(Szymon): Konstruktor w przypadku powracajacego klienta
	{
		this.locationOfShopCatalog = "Sklep/";
		this.categories = obtaincategories();
		this.listOfProducts = obtainListOfProducts(categories);
		this.basket = client.getBasket();
	}
	
	public ChooseItems() //NOTE(Szymon): Konstruktor w przypadku nowego klienta
	{
		this.locationOfShopCatalog = "Sklep/";
		this.categories = obtaincategories();
		this.listOfProducts = obtainListOfProducts(categories);
		this.basket = new Basket();
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
				Product product = obtainProduct(category, files[i]);
				if (product != null) {
					products.add(product);
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
	
	// Klient rozpoczyna zakupy
	@SuppressWarnings("resource")
	public Basket doShopping()
	{
		System.out.println("Witamy. Zyczymy udanych zakupow"); 
		
		boolean isShopping = true;
		
		Scanner scanner = new Scanner(System.in);
		while (isShopping)
		{
			doShoppingSelectingCategory();
			
			if (basket.getPrice() == 0) {
				
				System.out.println("Czy naprawde nic nie chcesz kupic (wpisz 1 jesli tak)");
				String decision = scanner.nextLine();
				if (decision.equals("1")) {
					System.out.println("Dziekujemy za zakupy w naszym sklepie");
					System.exit(0);
				}
				
			}
			else {
				doShopingErasingItems();
				
				System.out.println("Stan koszyka");
				System.out.println(basket.toString());
				
				System.out.println("Napisz 1 jesli chcesz potwierdzic zawartosc koszyka (tej decyzji nie mozna cofnac)");
				String decision = scanner.nextLine();
				if (decision.equals("1")) {
					isShopping = false;
				}
			}
		}
		
		return basket;
	}
	
	// Klient wybiera kategorie
	@SuppressWarnings("resource")
	private void doShoppingSelectingCategory() 
	{
		boolean shouldStopSelectingCategory = false;
		
		while (!shouldStopSelectingCategory)
		{
			// Wypisywanie mozliwych kategorii do wybrou
			for (int i=0; i<categories.size(); i++)
			{
				System.out.println((i+1) + ". " + categories.get(i));
			}
			System.out.println("Wpisz numer kategorii, ktora chcesz przegladnac");
			System.out.println("Wpisz 0 aby zakonczyc kupowanie produktow");
			
			try
			{
				Scanner scanner = new Scanner(System.in);
				int numberOfCategory = scanner.nextInt();
				scanner.nextLine();
				
				if (numberOfCategory == 0) {
					shouldStopSelectingCategory = true;
				}
				
				else if (numberOfCategory>0 && numberOfCategory<=categories.size()) {
					doShoppingBuyingItems(categories.get(numberOfCategory-1));
				}
				
				else {
					System.out.println("Nie ma takiej kategorii. Wybierz ponownie");
				}
			}
			
			catch (java.util.InputMismatchException e) 
			{
				System.out.println("Nie ma takiej kategorii. Wybierz ponownie");
			}
		}
		
	}
	
	// Klient wybiera produkty i je kupuje
	@SuppressWarnings("resource")
	private void doShoppingBuyingItems(String category)
	{
		System.out.println("Przegladasz teraz produkty z kategorii: " + category);
		
		ArrayList<Product> products = listOfProducts.get(category);
		if (products.size() != 0) {
			
			boolean shouldStopBuyingItems = false;
			while (!shouldStopBuyingItems)
			{
				// Wypisywanie produktow
				for (int i=0; i<products.size(); i++)
				{
					System.out.println((i+1) + "\n" + products.get(i));
				}
				System.out.println("Wpisz numer produktu, aby go kupic");
				System.out.println("Wpisz 0 jesli chcesz cofnac sie do wyboru kategorii");
				
				try
				{
					Scanner scanner = new Scanner(System.in);
					
					int numberOfItem = 0;
					numberOfItem = scanner.nextInt();
					scanner.nextLine();
					
					if (numberOfItem == 0) {
						shouldStopBuyingItems = true;
					}
					
					else {
						if (numberOfItem>0 && numberOfItem<=products.size()) {
							System.out.println("Ile chcesz kupic produktow o nazwie: " + products.get(numberOfItem-1).getName());
							int numberOfProducts = 0;
							
							try
							{
								numberOfProducts = scanner.nextInt();
								scanner.nextLine();
								
								if (numberOfProducts>0) {
									basket.addAProductToTheBasket(products.get(numberOfItem-1), numberOfProducts);
									System.out.println("Prawidlowo zakupiono produkt/-y\n");
								}
							}
							
							// Jezeli uzytkownik wpisze s jako liczbe produktow to zakladamy, ze nie chce tego produktu kupic
							catch (java.util.InputMismatchException e) 
							{
								System.out.println("Prosze podac prawidlowa liczbe produktow");
							}
						}
					}
				}
				
				catch (java.util.InputMismatchException e) 
				{
					System.out.println("Nie ma takiego numeru produktu");
				}
			}
		}
		
		// Nie ma produktow w danej kategorii
		else {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Brak produktow w podanej kategorii");
			System.out.println("Nacisnij enter aby sie cofnac");
			scanner.nextLine();
		}
		
	}
	
	// Klient wybiera produkty, ktore chce skasowac z koszyka
	@SuppressWarnings("resource")
	private void doShopingErasingItems()
	{
		boolean isErasingItems = true;
		while (isErasingItems)
		{
			System.out.println("Obecna zawartosc koszyka"); 
			System.out.println(basket.toString());
			System.out.println("Wpisz numer produktu, aby sie go pozbyc z koszyka");
			System.out.println("Wpisz 0 jezeli chcesz przejsc to potwierdzenia zawartosci koszyka");
			
			try
			{
				Scanner scanner = new Scanner(System.in);
				int decision = scanner.nextInt();
				scanner.nextLine();
				
				if (decision == 0) {
					isErasingItems = false;
				}
				
				else if (decision > 0 && decision <= basket.getProducts().keySet().toArray().length) {
					System.out.println("Ile produktow chcesz usunac z koszyka?");
					int numberOfProducts = 0;
					
					try
					{
						numberOfProducts = scanner.nextInt();
						scanner.nextLine();
						
						if (numberOfProducts > 0 && numberOfProducts <= basket.getProducts().get(basket.getProducts().keySet().toArray()[decision-1])) {
							basket.eraseAProductFromTheBasket((Product) basket.getProducts().keySet().toArray()[decision-1], numberOfProducts);
							System.out.println("Prawidlowo skasowano produkty z koszyka");
						}
						
						else {
							System.out.println("Wybierz jeszcze raz produkt i wpisz prawidlowa liczbe produktow");
						}
						
					}
					catch (java.util.InputMismatchException e) 
					{
						System.out.println("Wybierz jeszcze raz produkt i wpisz prawidlowa liczbe produktow");
					}
				}
				
				else {
					System.out.println("Wybierz jeszcze raz produkt i wpisz prawidlowa liczbe produktow");
				}
				
			}
			
			catch (java.util.InputMismatchException e) 
			{
				System.out.println("Wybierz jeszcze raz produkt i wpisz prawidlowa liczbe produktow");
			}
		}
	}
}