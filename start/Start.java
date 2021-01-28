package start;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import client.Client;
import client.UnregisteredClient;
import guiShop.MainGUI;

// Klasa stworzona przez Wiktora Sadowego 
public class Start
{
	final static String LOOKANDFEEL = "Metal";
	final static String THEME = "Ocean";
	// Ten kod pozwala na ustawienie domyslnej czcionki calego programu
	public static void setUIFont (javax.swing.plaf.FontUIResource f)
	{
	    @SuppressWarnings("rawtypes")
		java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) 
	    {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource) {
	    	  UIManager.put (key, f);
	      }
	    }
	} 
	
	public static void main(String[] args) 
	{
		// Ustawianie czcionki calego programu przed jego rozpoczeciem
		setUIFont (new javax.swing.plaf.FontUIResource("Dialog",Font.PLAIN,14));
		
		try {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// Klient na poczatku nie jest zalogowany
		Client client = new UnregisteredClient();
		
		new MainGUI(client);
	}
	
	// Kilka uwag dla programisty
	// javax.swing.plaf.FontUIResource[family=Dialog,name=Dialog,style=bold,size=12] - to jest domyslna czcionka
}
