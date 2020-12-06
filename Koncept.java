/* Odgórne uwagi
 * Ten main będzie zawierał koncepcję programu
 * Wydaje mi się, że same interfejsy od was wystarczą, ale mogę się mylić
 * W miarę możliwości starajmy się pisać kod w języku angielskim (chyba, że bardzo wam przeszkadza to możemy po polsku)
 * Koncepcja może się zmienić wraz z czasem ale raczej wątpię by podczas zmiany bardzo wiele musielibyście zmieniać w swoim kodzie
 * Przestrzegajmy w miarę możliwości konwencji pisania nazw zmiennych itd. w Javie i komentujcie swój kod tak, żebyśmy byli w stanie siebie zrozumieć
 * https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html
 * Nie musicie mieć takie same nazwy metod jakie zaproponowałem
 */


// Tutaj będą wasze interfejsy (niech się one sensownie nazywają)
//import waysofpaying
//import waysofdelivery


class Client
{
	// Tutaj będą dane klienta (to ja sam zaimplementuję)
	// Możliwe, że tą klasę rozbuduję (może Stały klient?) ale to się zobaczy
}

class Shop 
{
	public static void main(String[] args) 
	{
		/* Metoda implementacji waszych interfejsów (może będą potrzebne klasy, ale to już musicie sami wiedzieć) (używamy poliformizmu)
		 * WaysOfPaying[] waysOfPaying = {metoda1, metoda2, metoda3}
		 * WaysOfDelivery[] waysOfDelivery = {metoda1, metoda2, metoda3}
		 * Jak zaimplementujecie jakąś nową metodę to po prostu dopiszcie ją do listy
		 * Nie będziecie musieli zmieniać kodu poniżej (ja zadbam o to, aby poprawnie użyć waszych metod)
		 */
		
		/* Wybór towaru
		 * System.out.println("Wybierz towar z listy")
		 * Tutaj ja zaimplementuję metodu wyboru towaru z listy
		 * Klient będzie mógł wybrać produkt z listy i dodać go do koszyka. 
		 * Gdy klient wybierze wszystkie towary to przejdzie do metodu wyboru dostawy
		 */
		
		
		/*
		 * Tutaj zaimplementuję metodę wpisania podstawowych danych klienta
		 */
		
		/* Wybór metody dostawy
		 * System.out.println("Wybierz metodę zamówienia")
		 * for (int i=0; i<waysOfDelivery.length; i++)
		 * {
		 * 		System.out.println(waysOfDelivery[i].getName) // Użytkownikowi ma się wyświetlać nazwa metody (np. Inpost), cena,  można też dodać czas dostawy
		 * }
		 * choice = scanner.nextInt() 
		 * Uwzględnię pewnie warunek na sprawdzanie czy użytkownik wybrał właściwą opcję
		 * choice -- // Wybór 1 to dla nas wybór 0
		 * price += waysOfDelivery[i].getPrice // Cena zwiększa się
		 * Fajnie by było gdyby były jakieś warunki sprawdzone (np. czy rozmiar przesyłki pozwala na taką dostawę itd.), ale nie jest to konieczne
		 * if (waysOfDelivery[i].isMeetingCondition){ 
		 * 		decyzja = waysOfDelivery[i].someAction
		 * 		Wywołanie odpowiednich metod - ważne jest to, żeby wynikiem końcowym tych działań był String, który można potem pokazać użytkownikowi
		 * 		tak aby mógł zatwierdzić swoją decyzję (ogólnie będę implementował wiele sprawdzań pt. czy jesteś pewny wprowadzenia swoich danych)
		 * }
		 */
		
		/* 	Wybór metody płatności
		 * System.out.println("Wybierz metodę płatności")
		 * for (int i=0; i<waysOfPaying.length; i++)
		 * {
		 * 		System.out.println(waysOfPaying[i].getName) // Użytkownikowi ma się wyświetlać nazwa metody (np. Blik)
		 * }
		 * choice = scanner.nextInt() 
		 * Uwzględnię pewnie warunek na sprawdzanie czy użytkownik wybrał właściwą opcję
		 * choice -- // Wybór 1 to dla nas wybór 0
		 * Tutaj uwzględnie warunek pytający czy użytkownik jest pewny wyboru swojej opcji
		 * isPaymentDone = waysOfPaying[i].doSomething // Funkcja powinna zwracać true lub false (true - płatność została dokonana)
		 * Korzystając z powyższej wiadomości albo wyświetlę pełną informację na temat zamówienia albo 
		 */
	
		/* Przykładowy output
		 * Zamówienie: 3x kalendarz, 2x naklejki
		 * Metoda wyboru zamówienia: Inpost
		 * Czas dostawy: 3 dni
		 * Koszt zamówienia - 150 zł
		 * Zapłacone - tak
		 */
	}
}
