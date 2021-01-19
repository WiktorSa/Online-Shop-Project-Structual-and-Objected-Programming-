package gui;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.RegisteredClient;

public class ClientLogInGUI 
{
	private Client client;
	private JFrame jFrame;
	private JTextField emailJTextField;
	private JPasswordField passwordJPasswordField;
	private JButton logInButton;
	private JButton remindPasswrodButton;
	private JButton goBackButton;
	
	public ClientLogInGUI(Client client) 
	{
		this.client = client;
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Sklep");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(350, 350);
		jFrame.setResizable(false);
		
		// Tekst bedzie sie wyswietlal od gory do dolu
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel giveInstructionsJLabel = new JLabel("Zaloguj sie", SwingConstants.CENTER);
		giveInstructionsJLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
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
		emailJTextField.setSize(new Dimension(300, 25));
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
		
		jPanel.add(Box.createRigidArea(new Dimension(0,20)));
		
		goBackButton = new JButton("Cofnij sie");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		
		JLabel unregisteredClientJLabel = new JLabel("Jestes niezalogowany");
		unregisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		unregisteredClientJLabel.setBorder(new EmptyBorder(20,5,10,5));
		jPanel.add(unregisteredClientJLabel);
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
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
					
					if (((RegisteredClient) clientFromFile).getPassword().equals(password)) {
						client = clientFromFile;
						((RegisteredClient) client).saveClient();
						JOptionPane.showMessageDialog(null, "Poprawnie zalogowales sie");
						new ShopRegisteredClientGUI(client);
						jFrame.dispose();
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
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new ShopUnregisteredClientGUI(client);
			jFrame.dispose();
		}
	}
}
