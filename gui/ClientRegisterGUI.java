package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
import client.HelperFunctionsClient;
import client.NormalClient;
import client.RegisteredClient;

public class ClientRegisterGUI 
{
	private Client client;
	private JFrame jFrame;
	private JTextField emailJTextField;
	private JPasswordField passwordJPasswordField;
	private JPasswordField passwordConfirmJPasswordField;
	private JButton registerButton;
	private JButton goBackButton;
	
	public ClientRegisterGUI(Client client) 
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
		
		JLabel giveInstructionsJLabel = new JLabel("Zarejestruj sie", SwingConstants.CENTER);
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
		emailJTextField.setMaximumSize(new Dimension(300, 25));
		emailJTextField.setPreferredSize(new Dimension(300, 25));
		jPanel.add(emailJTextField);
		
		JLabel passswordJLabel = new JLabel("Wpisz haslo", SwingConstants.CENTER);
		passswordJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passswordJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(passswordJLabel);
		
		passwordJPasswordField = new JPasswordField(); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		passwordJPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordJPasswordField.setHorizontalAlignment(JTextField.CENTER);
		passwordJPasswordField.setMaximumSize(new Dimension(300, 25));
		passwordJPasswordField.setPreferredSize(new Dimension(300, 25));
		jPanel.add(passwordJPasswordField);
		
		JLabel passswordConfirmJLabel = new JLabel("Potwierdz haslo", SwingConstants.CENTER);
		passswordConfirmJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passswordConfirmJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(passswordConfirmJLabel);
		
		passwordConfirmJPasswordField = new JPasswordField(); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		passwordConfirmJPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordConfirmJPasswordField.setHorizontalAlignment(JTextField.CENTER);
		passwordConfirmJPasswordField.setMaximumSize(new Dimension(300, 25));
		passwordConfirmJPasswordField.setPreferredSize(new Dimension(300, 25));
		jPanel.add(passwordConfirmJPasswordField);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		registerButton = new JButton("Zarejestruj sie");
		registerButton.addActionListener(new Register());
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(registerButton);
		
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
	
	class Register implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String email = emailJTextField.getText();
			String password = String.valueOf(passwordJPasswordField.getPassword());
			String passwordConfirm = String.valueOf(passwordConfirmJPasswordField.getPassword());
			
			if (HelperFunctionsClient.isCorrectEmail(email) && password.equals(passwordConfirm) && password.length() != 0) {
				File file = new File("Client_" + email + ".ser");
				
				if (file.exists()) {
					JOptionPane.showMessageDialog(null, "Juz istnieje konto na podany adres email.");
				}
				
				else {
					client = new NormalClient(client);
					client.setEmail(email);
					((RegisteredClient) client).setPassword(password);
					((RegisteredClient) client).saveClient();
					
					JOptionPane.showMessageDialog(null, "Rejestracja przebiegla pomyslnie. Zostaniesz automatycznie zalogowany");
					
					new ShopRegisteredClientGUI(client);
					jFrame.dispose();
				}
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Niepoprawne dane podane");
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
