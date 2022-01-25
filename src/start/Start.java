package start;

import java.awt.Font;

import javax.swing.UIManager;

import client.Client;
import client.UnregisteredClient;
import gui.shop.MainGUI;

public class Start {
	final static String LOOKANDFEEL = "Metal";
	final static String THEME = "Ocean";
	// Set default font for the whole programme
	public static void setUIFont (javax.swing.plaf.FontUIResource f) {
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
	
	public static void main(String[] args) {
		setUIFont (new javax.swing.plaf.FontUIResource("Dialog",Font.PLAIN,14));
		try 
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); 
		}
		catch(Exception e) 
		{
			System.exit(-1);
		}
		// Client is not registered at the beginning
		Client client = new UnregisteredClient();
		MainGUI gui = new MainGUI(client);
		gui.initialize();
	}
}
