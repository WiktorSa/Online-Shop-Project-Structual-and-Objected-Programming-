package client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

// Klasa stworzona przez Wiktora Sadowego uzywajac kodu Szymona Sawczuka
public abstract class RegisteredClient extends Client implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected String password;
	
	public RegisteredClient() 
	{
		super();
		this.password = "";
	}
	
	public RegisteredClient(Client client)
	{
		super(client);
		password = "";
	}
	
	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	@SuppressWarnings("resource")
	public void setCorrectPassword()
	{
		boolean shouldStopSettingClientInfo = false;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Podaj haslo: ");
		
		while(!shouldStopSettingClientInfo) 
		{
			String password = scanner.nextLine();
			
			// Sprawdzam czy hasla sie zgadzaja
			System.out.println("Potwierdz haslo: ");
			String confirm = scanner.nextLine();
				
			if (password.equals(confirm)) {
				shouldStopSettingClientInfo = true;
			}
		
			if (shouldStopSettingClientInfo) {
				this.password = password;
			}
		}
	}

	//NOTE(Szymon): Metoda zapisu klienta do pliku
	public void saveClient() 
	{
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Client_"+email+".ser")))
		{
			outputStream.writeObject(this);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Doszlo do krytycznego bledu programu");
			System.exit(-1);
		} 
		catch (IOException e) 
		{
			System.out.println("Doszlo do krytycznego bledu programu");
			System.exit(-1);
		}
	}
	
	public abstract void initiateShopping();
	
	public abstract void setClientInfo();
	
	public abstract void chooseWayOfDelivery();

	public abstract boolean setDeliveryInfo();

	public abstract void chooseWayOfPayment();
	
	public abstract boolean initiatePay();
	
	public abstract String getTransactionInfo();
}
