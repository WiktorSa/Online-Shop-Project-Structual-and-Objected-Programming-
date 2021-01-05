package chooseitems;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import client.Client;

// Klasa zaimplementowana przez Wiktora Sadowego oraz Szymona Sawczuka
public class ChooseItems 
{
	private String locationOfTheCatalog;
	private String[] categories;
	private Product[][] products; // NOTE(Szymon):Sa potrzebne dwa [] (podzial na kategorie i produkty informacje o produkcie (kategoria, nazwa, cena) sa w klasie)
	private Basket basket[]; //(Szymon): Wszystkie informacje sa zawarte w klasie
	private double price;
	
	public ChooseItems(Client client) //NOTE(Szymon): Konstruktor w przypadku powracajacego klienta
	{
		this.locationOfTheCatalog = "Zakupy/";
		this.categories = obtaincategories();
		this.products = obtainproducts(categories);
		this.basket = client.getBasket();
		this.price = client.getPrice();
	}
	
	public ChooseItems() //NOTE(Szymon): Konstruktor w przypadku nowego klienta
	{
		this.locationOfTheCatalog = "Zakupy/";
		this.categories = obtaincategories();
		this.products = obtainproducts(categories);
		this.basket = new Basket[0];
		this.price = 0;
	}
	
	public double getPrice() 
	{
		return price;
	}
	
	private String[] obtaincategories()
	{
		FileReader categoriesFile = null;
		BufferedReader categoriesReader = null;
		String[] categories = null;
		
		try 
		{
			categoriesFile = new FileReader(locationOfTheCatalog + "kategorie.txt");
			categoriesReader = new BufferedReader(categoriesFile);	
			
			String categoryName = "";
			categories = new String[0];
			while ((categoryName = categoriesReader.readLine()) != null)
			{
				categories = increaseCategoriesArraySize(categories, categoryName);
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
	
	private String[] increaseCategoriesArraySize(String[] array, String categoryName)
	{
		String[] newArray = new String[array.length+1];
		
		for (int i=0; i<array.length; i++)
		{
			newArray[i] = array[i];
		}
		
		newArray[array.length] = categoryName;
		
		return newArray;
	}
	
	// Otrzymujac wszystkie informacje o produkcie (nazwa kategorii, nazwa produktu, cena)
	private Product[][] obtainproducts(String[] categories)
	{
		Product[][] products = new Product[categories.length][0];
		
		for (int i=0; i<categories.length; i++)
		{
			FileReader productsFile = null;
			BufferedReader productsReader = null;
			
			try 
			{
				productsFile = new FileReader(locationOfTheCatalog + categories[i] + ".txt");
				productsReader = new BufferedReader(productsFile);	
				String productsInformation = "";
				
				while ((productsInformation = productsReader.readLine()) != null)
				{
					String[] words = productsInformation.split("\\s+");
					String productName = words[0];
					// Nazwa produktu nie musi sie skladac z jednego slowa
					for (int j=1; j<words.length-1; j++)
					{
						productName = productName + " " + words[j];
					}
					
					String price = words[words.length-1];
					
					products[i] = increaseProductsArraySize(products[i], categories[i], productName, Double.parseDouble(price));
				}
			} 
			
			// Jezeli byl blad podczas odczytu pliku to tez zakladamy, ze nie ma zadnych produktow w podanej kategorii 
			catch (IOException e) 
			{
				products[i] = null;
			}
			
			finally 
			{
				try 
				{
					productsReader.close();
				}
				// Jezeli byl blad podczas zamykania to zakladamy, ze doszlo do powaznego bledu i zakladamy, ze nie ma produktow w podanej kategorii
				catch (Exception e) 
				{
					products[i] = null;
				}
			}
		}
		
		return products;
	}

	// NOTE(Szymon):Gdy mamy klase Product to mozna dac price jako double/float
	private Product[] increaseProductsArraySize(Product[] array, String categoryName, String productName, double price)
	{
		// Kazdy produkt zawiera kategorie, nazwe oraz cene
		Product[] newArray = new Product[array.length+1];
		
		for (int i=0; i<array.length; i++)
		{
			newArray[i] = array[i];
		}
		
		newArray[array.length] = new Product(categoryName, productName, price);
		
		return newArray;
	}
	
	@SuppressWarnings("resource")
	public Basket[] doShopping()
	{
		System.out.println("Witamy. Zyczymy udanych zakupow"); 
		
		boolean isShopping = true;
		
		Scanner scanner = new Scanner(System.in);
		while (isShopping)
		{
			doShoppingSelectingCategory();
			
			if (this.basket.length == 0) {
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
				System.out.println(getInfoAboutBasket());
				
				System.out.println("Napisz 1 jesli chcesz potwierdzic zawartosc koszyka (tej decyzji nie mozna cofnac)");
				String decision = scanner.nextLine();
				if (decision.equals("1")) {
					isShopping = false;
				}
			}
		}
		
		return basket;
	}
	
	// Klient moze kupowac produkty ile chce
	@SuppressWarnings("resource")
	private void doShoppingSelectingCategory() 
	{
		boolean shouldStopSelectingCategory = false;
		
		while (!shouldStopSelectingCategory)
		{
			// Wypisywanie mozliwych kategorii do wybrou
			for (int i=0; i<categories.length; i++)
			{
				System.out.println((i+1) + ". " + categories[i]);
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
				
				else if (numberOfCategory>0 && numberOfCategory<=categories.length) {
					System.out.println("Przegladasz teraz produkty z kategorii: " + categories[numberOfCategory-1]);
					doShoppingBuyingItems(products[numberOfCategory-1]);
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
	
	@SuppressWarnings("resource")
	private void doShoppingBuyingItems(Product[] products2)
	{
		if (products2 != null) {
			
			boolean shouldStopBuyingItems = false;
			while (!shouldStopBuyingItems)
			{
				// Wypisywanie produktow
				for (int i=0; i<products2.length; i++)
				{
					System.out.println((i+1) + ". " + products2[i].getName() + ", Cena - " + products2[i].getPrice());
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
						if (numberOfItem>0 && numberOfItem<=products2.length) {
							System.out.println("Ile chcesz kupic produktow o nazwie: " + products2[numberOfItem-1].getName());
							int numberOfProducts = 0;
							
							try
							{
								numberOfProducts = scanner.nextInt();
								scanner.nextLine();
							}
							
							// Jezeli uzytkownik wpisze s jako liczbe produktow to zakladamy, ze nie chce tego produktu kupic
							catch (java.util.InputMismatchException e) 
							{
								System.out.println("Prosze podac prawidlowa liczbe produktow");
								numberOfProducts = 0;
							}
							
							finally 
							{
								if (numberOfProducts>0) {
									addAProductToTheBasket(products2[numberOfItem-1], numberOfProducts);
									System.out.println("Prawidlowo zakupiono produkt/-y");
								}
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
		
		// Doszlo do bledu w czasie otwierania pliku zawierajacego nazwy produktow w podanej kategorii
		else {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Brak produktow w podanej kategorii");
			System.out.println("Nacisnij enter aby sie cofnac");
			scanner.nextLine();
		}
		
	}
	
	// Jezeli produkt juz jest w koszyku to zmieniamy liczbe zamowionych produktow. Jezeli nie to dodajemy go do koszyka
	private void addAProductToTheBasket(Product productInfo, int numberOfProducts)
	{
		Basket[] newBasket = new Basket[this.basket.length+1];
		this.price = this.price + productInfo.getPrice() * (double)(numberOfProducts); 
		
		for (int i=0; i<this.basket.length; i++)
		{
			// Produkt juz byl w koszyku
			if (this.basket[i].getName().equals(productInfo.getName())) {
				int numberOfProductsInBasket = this.basket[i].getAmountOfProduct();
				numberOfProductsInBasket += numberOfProducts;
				this.basket[i].setAmountOfProduct(numberOfProductsInBasket);
				
				return;
			}
			
			else {
				newBasket[i] = this.basket[i];
			}
			
		}
		
		newBasket[newBasket.length-1] = new Basket(productInfo.getCategory(), productInfo.getName(), productInfo.getPrice(), numberOfProducts);
	
		this.basket = newBasket;
	}

	@SuppressWarnings("resource")
	private void doShopingErasingItems()
	{
		System.out.println("Obecna zawartosc koszyka"); 
		System.out.println(getInfoAboutBasket());
		System.out.println("Wpisz numer produktu, aby sie go pozbyc z koszyka");
		System.out.println("Wpisz 0 jezeli chcesz przejsc to potwierdzenia zawartosci koszyka");
		try
		{
			Scanner scanner = new Scanner(System.in);
			int decision = scanner.nextInt();
			scanner.nextLine();
			if (decision == 0) {
				return;
			}
			else if (decision>0 && decision<=this.basket.length) {
				System.out.println("Ile produktow chcesz sie pozbyc?");
				int numberOfItems = 0;
				try
				{
					numberOfItems = scanner.nextInt();
					scanner.nextLine();
					// Jezeli uzytkownik wprowadzil wieksza liczbê produktow niz jest w koszyku
					if (numberOfItems>this.basket[decision-1].getAmountOfProduct()) {numberOfItems = this.basket[decision-1].getAmountOfProduct();}
				}
				catch (java.util.InputMismatchException e) {numberOfItems = 0;}
				// Zmieniamy zawartosc koszyka wtedy gdy uzytkownik wykresla jakis produkt
				if (numberOfItems>0) {eraseProductFromTheBasket(this.basket[decision-1], numberOfItems);}
			}
			doShopingErasingItems();
		}
		catch (java.util.InputMismatchException e) {doShopingErasingItems();}
	}
	
	private void eraseProductFromTheBasket(Basket productInfo, int numberOfProducts)
	{
		Basket[] newBasket = new Basket[this.basket.length-1];
		this.price = this.price - productInfo.getPrice() * (double)(numberOfProducts); 
		int p=0;
		
		for (int i=0; i<this.basket.length; i++)
		{
			// Znalezlismy szukany produkt
			if (this.basket[i].getName().equals(productInfo.getName())) {
				int numberOfProductsInBasket = this.basket[i].getAmountOfProduct();
				numberOfProductsInBasket -= numberOfProducts;
				// Jezeli uzytkownik zdecydowal, ze nie skasuje wszystkich produktow bedacych w koszyku to po prostu zmieniamy ilosc produktow w this.basket i tyle
				if (numberOfProductsInBasket != 0) {
					this.basket[i].setAmountOfProduct(numberOfProductsInBasket); 
					return;
				}
			}
			else {
				newBasket[p] = this.basket[i];
				p++;
			}
		}
		this.basket = newBasket;
	}
	
	private String getInfoAboutBasket()
	{
		String data = "";
		
		for (int i=0; i<this.basket.length; i++)
		{
			data = data + (i+1) + ". " + this.basket[i].getName() + ", Cena: " + this.basket[i].getPrice() + " Ilosc produktow: " + this.basket[i].getAmountOfProduct() + "\n"; 
		}
		data = data + "Cena koncowa: " + this.price;
		return data;
	}
}