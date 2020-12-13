package lista9;

import java.util.Scanner;
class Shop 
{
	public static void main(String[] args) 
	{
		Client client = new Client();
		// Tutaj beda metody wyboru dostawy
		// Tutaj beda metody wyboru platnosci
		// Ponizej sa wywolania metod
		
		WaysOfDelivery[] waysOfDelivery = new WaysOfDelivery[3];
		waysOfDelivery[0]=new Paczkomat();
		waysOfDelivery[1]=new Kurier();
		waysOfDelivery[2]=new Osobisty();
		int wybor;
		for(int i=0;i<waysOfDelivery.length;i++)
		{
			System.out.println(i+1+". "+waysOfDelivery[i].getName()+" Cena: "+waysOfDelivery[i].getPrice()+" z³");
		}
		System.out.println("Prosze wybrac numer sposobu dostawy");
		Scanner scan=new Scanner(System.in);
		wybor=scan.nextInt();
		System.out.println(((Dostawa) waysOfDelivery[wybor-1]).provideDeliveryInformations(client));
		scan.close();
	}
}
