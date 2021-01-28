package guiClient;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import chooseitems.Basket;
import client.Client;
import client.RegisteredClient;
import guiShop.MainGUI;

public class LogInGUI 
{

	private JPanel jPanel;
	private JTextField emailJTextField;
	private JPasswordField passwordJPasswordField;
	private JButton logInButton;
	private JButton remindPasswrodButton;
	private MainGUI main;
	
	public JPanel getjPanel() 
	{
		return jPanel;
	}
	
	public LogInGUI(MainGUI main) 
	{
		this.main = main;
		
		// Tekst bedzie sie wyswietlal od gory do dolu
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie (musi byc na poczatku i koncu)
		JLabel giveInstructionsJLabel = new JLabel("Zaloguj sie", SwingConstants.CENTER);
		giveInstructionsJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		giveInstructionsJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(giveInstructionsJLabel);
		
		JLabel emaiJLabel = new JLabel("Wpisz swoj adres email", SwingConstants.CENTER);
		emaiJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		emaiJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(emaiJLabel);
		
		emailJTextField = new JTextField(); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		emailJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailJTextField.setHorizontalAlignment(JTextField.CENTER);
		emailJTextField.setPreferredSize(new Dimension(300, 25));
		emailJTextField.setMaximumSize(new Dimension(300, 25));
		jPanel.add(emailJTextField);
		
		JLabel passswordJLabel = new JLabel("Wpisz haslo", SwingConstants.CENTER);
		passswordJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passswordJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(passswordJLabel);
		
		passwordJPasswordField = new JPasswordField(); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		passwordJPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordJPasswordField.setHorizontalAlignment(JTextField.CENTER);
		passwordJPasswordField.setPreferredSize(new Dimension(300, 25));
		passwordJPasswordField.setMaximumSize(new Dimension(300, 25));
		jPanel.add(passwordJPasswordField);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		logInButton = new JButton("Zaloguj sie");
		logInButton.addActionListener(new LogIn());
		logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(logInButton);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		remindPasswrodButton = new JButton("Przypomnij sobie haslo");
		remindPasswrodButton.addActionListener(new RemindPassword());
		remindPasswrodButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(remindPasswrodButton);
		
		//jPanel.add(Box.createRigidArea(new Dimension(0,20)));
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie 
	}
	
	class LogIn implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String email = emailJTextField.getText();
			String password = String.valueOf(passwordJPasswordField.getPassword());
			
			File file = new File("Client_" + email + ".ser");
			
			if (!file.exists()) {
				JOptionPane.showMessageDialog(null, "Nie ma konta na podany adres email");
			}
			
			else {
				try(ObjectInputStream outputStream = new ObjectInputStream(new FileInputStream(file)))
				{
					Client clientFromFile = (RegisteredClient) outputStream.readObject();
					Basket oldBasket = main.getClient().getBasket();
					
					// Jezeli klient wypelnil towar produktami i potem sie zaloguje to kasujemy zawartosc z koszyka zalogowanego klienta i zastepujemy nowym koszykiem
					if (oldBasket.getProducts().size() != 0) {
						clientFromFile.setBasket(oldBasket);
					}
					
					
					if (((RegisteredClient) clientFromFile).getPassword().equals(password)) {
						main.setClient(clientFromFile);
						((RegisteredClient) main.getClient()).saveClient();
						JOptionPane.showMessageDialog(null, "Poprawnie zalogowales sie");
						main.getCardLayout().show(main.getCardPanel(), "Home Page");
						main.changeLayoutOfNorthPanel();
					}
					
					else {
						JOptionPane.showMessageDialog(null, "Zle haslo");
					}
				} 
				catch (FileNotFoundException e) 
				{
					System.exit(-1);
				} 
				catch (IOException e) 
				{
					System.exit(-1);
				} 
				catch (ClassNotFoundException e) 
				{
					System.exit(-1);
				}
			}
		}
	}

	class RemindPassword implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String email = emailJTextField.getText();
			
			File file = new File("Client_" + email + ".ser");
			
			if (!file.exists()) {
				JOptionPane.showMessageDialog(null, "Nie ma konta na podany adres email. Wpisz swoj adres email zanim poprosisz o przypomnienie hasla");
			}
			
			else {
				try(ObjectInputStream outputStream = new ObjectInputStream(new FileInputStream(file)))
				{
					Client clientFromFile = (RegisteredClient) outputStream.readObject();
					JOptionPane.showMessageDialog(null, "Twoje haslo to: " + ((RegisteredClient) clientFromFile).getPassword());
				} 
				catch (FileNotFoundException e) 
				{
					System.exit(-1);
				} 
				catch (IOException e) 
				{
					System.exit(-1);
				} 
				catch (ClassNotFoundException e) 
				{
					System.exit(-1);
				}
			}
		}
	}
}
