package client;

import java.util.Scanner;

public class Client
{
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	
	public Client() 
	{
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.phoneNumber = "";
	}
	
	public String getFirstName() 
	{
		return firstName;
	}
	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	public String getLastName() 
	{
		return lastName;
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() 
	{
		return "Dane o kliencie: Imie: " + firstName + ", Nazwisko: " + lastName + ", Email: " + email + ", Numer telefonu: " + phoneNumber;
	}

	@SuppressWarnings("resource")
	public void setClientInfo()
	{
		System.out.println("Podaj imie");
		setCorrectFirstName();
		System.out.println("Podaj nazwisko");
		setCorrectLastName();
		System.out.println("Podaj adres email");
		setCorrectEmail();
		System.out.println("Podaj numer telefonu");
		setCorrectPhoneNumber();
		Scanner scanner = new Scanner(System.in);
		System.out.println(toString());
		System.out.println("Nacisnij 1 zeby potwierdzic swoje dane");
		String decision = scanner.nextLine();
		if (!decision.equals("1")){
			setClientInfo();
		}
	}
	
	@SuppressWarnings("resource")
	private void setCorrectFirstName()
	{
		Scanner scanner = new Scanner(System.in);
		String firstName = scanner.nextLine();
		boolean isCorrectData = true;
		// Sprawdzam czy nie ma zadnych dziwnych znakow w imieniu
		char[] letters = firstName.toCharArray();
		for (int i=0; i<letters.length; i++)
		{
			if (!Character.isLetter(letters[i])) {
				System.out.println("Podaj poprawne imie");
				isCorrectData = false;
				break;
			}
		}
		if (isCorrectData) {
			this.firstName = firstName;
		}
		else {
			setCorrectFirstName();
		}
	}
	
	@SuppressWarnings("resource")
	private void setCorrectLastName()
	{
		Scanner scanner = new Scanner(System.in);
		String lastName = scanner.nextLine();
		boolean isCorrectData = true;
		// Sprawdzam czy nie ma zadnych dziwnych znakow w imieniu
		char[] letters = lastName.toCharArray();
		for (int i=0; i<letters.length; i++)
		{
			if (!Character.isLetter(letters[i])) {
				System.out.println("Podaj poprawne nazwisko");
				isCorrectData = false;
				break;
			}
		}
		if (isCorrectData) {
			this.lastName = lastName;
		}
		else {
			setCorrectLastName();
		}
	}
	
	@SuppressWarnings("resource")
	private void setCorrectEmail()
	{
		Scanner scanner = new Scanner(System.in);
		String email = scanner.nextLine();
		// Sprawdzam czy mail jest poprawny
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (email.matches(regex)) {
			this.email = email;
		}
		else {
			System.out.println("Podaj poprawny adres email");
			setCorrectEmail();
		}
	}
	
	@SuppressWarnings("resource")
	private void setCorrectPhoneNumber()
	{
		Scanner scanner = new Scanner(System.in);
		String phoneNumber = scanner.nextLine();
		if (phoneNumber.length() == 9) {
			boolean isCorrectData = true;
			char[] digits = phoneNumber.toCharArray();
			for (int i=0; i<9; i++)
			{
				if (!Character.isDigit(digits[i])){
					System.out.println("Podaj poprawny numer telefonu");
					isCorrectData = false;
					break;
				}
			}
			if (isCorrectData) {
				this.phoneNumber = phoneNumber;
			}
			else {
				setCorrectPhoneNumber();
			}
		}
		else {
			System.out.println("Podaj poprawny numer telefonu");
			setCorrectPhoneNumber();
		}
	}
}
