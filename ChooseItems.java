import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

// Klasa stworzona przez Wiktor Sadowy
// Notka do siebie - dokoncz implementacje kupna towarow (klient wybiera ilosc towarow)
// Potem umozliw klientowi zakonczenie kupna przedmiotow (testem bedzie wypisanie zawartosci koszyka po zrobieniu zakupow
// Zaimplementuj metodê skasowania produktów z koszyka (w dowolnej iloœci)
// Potem mo¿esz przejœæ do implementacji klasy Klient
class ChooseItems 
{
	private Map<String, Integer> basket; // Móg³bym tutaj zastosowaæ tablicê Stringów i mieæ ka¿dy przedmiot zapisany w tej tablicy w takiej iloœci jak¹ klient chce albo mieæ dwie oddzielne tablice i je jakoœ ze sob¹ powi¹zaæ (co komplikuje kod z oczywistych przyczyn)
	private String locationOfTheCatalog;
	private String[] categoriesNames;
	private String[][][] productsNames; // S¹ potrzebne trzy [] (podzia³ na kategorie, produkty oraz informacje o produkcie)
	
	public ChooseItems() 
	{
		this.basket = null;
		this.locationOfTheCatalog = "Zakupy/";
		this.categoriesNames = obtainCategoriesNames();
		this.productsNames = obtainProductsNames(categoriesNames);
	}
	
	private String[] obtainCategoriesNames()
	{
		try 
		{
			FileReader categoriesFile = new FileReader(locationOfTheCatalog + "kategorie.txt");
			BufferedReader categoriesReader = new BufferedReader(categoriesFile);	
			String categoryName = "";
			String[] categoriesNames = new String[0];
			while ((categoryName = categoriesReader.readLine()) != null)
			{
				categoriesNames = increaseCategoriesArraySize(categoriesNames, categoryName);
			}
			categoriesReader.close();
			categoriesFile.close();
			return categoriesNames;
		} 
		catch (IOException e) 
		{
			System.out.println("Nast¹pi³ krytyczny b³¹d w dzia³aniu aplikacji.");
		//	e.printStackTrace();  This should only be visible to the developer
			System.exit(-1);
			return null;
		}
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
	
	// Otrzymujê wszystkie informacje o produkcie (nazwa kategorii, nazwa produktu, cena)
	private String[][][] obtainProductsNames(String[] categoriesNames)
	{
		String[][][] productsNames = new String[categoriesNames.length][0][0];
		for (int i=0; i<categoriesNames.length; i++)
		{
			try 
			{
				FileReader productsFile = new FileReader(locationOfTheCatalog + categoriesNames[i] + ".txt");
				BufferedReader productsReader = new BufferedReader(productsFile);	
				String productsInformation = "";
				while ((productsInformation = productsReader.readLine()) != null)
				{
					String[] words = productsInformation.split("\\s+");
					String productName = words[0];
					// Nazwa produktu nie musi siê sk³adaæ z jednego s³owa
					for (int j=1; j<words.length-1; j++)
					{
						productName = productName + " " + words[j];
					}
					String price = words[words.length-1];
					productsNames[i] = increaseProductsArraySize(productsNames[i], categoriesNames[i], productName, price);
				}
				productsReader.close();
				productsFile.close();
				// Gdy plik jest pusty to zak³adamy, ¿e nie ma ¿adnych produktów w podanej kategorii (u¿ytkownikowi siê wyœwietli w³aœciwa informacja)
				if (productsNames[i].length == 0) {
					productsNames[i] = null;
				}
			} 
			// Je¿eli by³ b³¹d podczas odczytu pliku to te¿ zak³adamy, ¿e nie ma ¿adnych produktów w podanej kategorii 
			catch (IOException e) 
			{
				productsNames[i] = null;
			}
		}
		return productsNames;
	}

	// Cena to wyj¹tkowo String!!! Wynika to z tego, ¿e array musi siê sk³adaæ z obiektów tego samego typu
	private String[][] increaseProductsArraySize(String[][] array, String categoryName, String productName, String price)
	{
		// Ka¿dy produkt zawiera kategoriê, nazwê oraz cenê
		String[][] newArray = new String[array.length+1][3];
		for (int i=0; i<array.length; i++)
		{
			newArray[i] = array[i];
		}
		newArray[array.length][0] = categoryName;
		newArray[array.length][1] = productName;
		newArray[array.length][2] = price;
		return newArray;
	}
	
	// Notka do siebie - powinnyœmy umo¿liwiæ u¿ytkownikowi mo¿liwoœæ 
	public void doShopping()
	{
		System.out.println("Witamy w sklepie .... Zyczymy udanych zakupow"); //Jak mam nazwaæ sklep? XD
		System.out.println("Dzisiaj proponujemy panstwu kupno"); // To jest do implementacji (system proponowania zamówienia powinien byæ oparty na tym, ¿e proponowany jest jeden losowy produkt z tych najczesciej wybieranych przez klienta)
		boolean isShopping = true;
		Scanner takingInput = new Scanner(System.in);
		while (isShopping)
		{
			for (int i=0; i<categoriesNames.length; i++)
			{
				System.out.println((i+1) + ". " + categoriesNames[i]);
			}
			System.out.println("Wpisz numer kategorii, ktora chcesz przegladnac");
			System.out.println("Wpisz cokolwiek innego aby wyjsc ze sklepu");
			try
			{
				int numberOfCategory = takingInput.nextInt();
				// Musisz dac nextLine inaczej Scanner nie bêdzie poprawnie wykonywa³ swoich funkcji
				takingInput.nextLine();
				// Czy u¿ytkownik
				if (numberOfCategory>0 && numberOfCategory<=categoriesNames.length) {
					System.out.println("Przegladasz teraz produkty z kategorii: " + categoriesNames[numberOfCategory-1]);
					doShoppingBuyingItems(productsNames[numberOfCategory-1]);
				}
				else {
					System.out.println("Dziekujemy za odwiedzenie naszego sklepu");
					System.exit(0);
				}
			}
			catch (java.util.InputMismatchException e) 
			{
				System.out.println("Dziekujemy za odwiedzenie naszego sklepu");
				System.exit(0);
			}
			
			isShopping = false;
		}
		takingInput.close();
	}
	
	private void doShoppingBuyingItems(String[][] items)
	{
		Scanner takingInput = new Scanner(System.in);
		for (int i=0; i<items.length; i++)
		{
			System.out.println((i+1) + ". " + items[i][1] + ", Cena - " + items[i][2]);
		}
		System.out.println("Wpisz numer produktu, aby go kupic");
		System.out.println("Wpisz cokolwiek innego jesli chcesz cofnac sie do wyboru kategorii");
		try
		{
			int numberOfItem = takingInput.nextInt();
			takingInput.nextLine();
			if (numberOfItem>0 && numberOfItem<=items.length) {
				System.out.println("Ile chcesz kupic produktow o nazwie: " + items[numberOfItem-1][1]);
				try
				{
					
				}
				catch (java.util.InputMismatchException e) 
				{
					
				}
			}
		}
		catch (java.util.InputMismatchException e) {}
	}
}