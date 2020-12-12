

class Client
{
	
}

class Shop 
{
	// Think with friends if its a good idea to only use one package or you need to use many packages
	public static void main(String[] args) 
	{
		ChooseItems selectingItems = new ChooseItems();
		String transactionInfo = "";
		double cena = 0;
		selectingItems.doShopping();
		System.out.println("Koniec testu");
	}
}
