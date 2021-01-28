package guiClient;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

import client.HelperFunctionsClient;
import client.NormalClient;
import client.RegisteredClient;
import guiShop.MainGUI;

public class RegisterGUI 
{

	private JPanel jPanel;
	private JTextField emailJTextField;
	private JPasswordField passwordJPasswordField;
	private JPasswordField passwordConfirmJPasswordField;
	private JButton registerButton;
	private MainGUI main;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public RegisterGUI(MainGUI main) 
	{
		this.main = main;
		
		
		// Tekst bedzie sie wyswietlal od gory do dolu
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie (musi byc na poczatku i koncu)
		JLabel giveInstructionsJLabel = new JLabel("Zarejestruj sie", SwingConstants.CENTER);
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
		
		//jPanel.add(Box.createRigidArea(new Dimension(0,20)));
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie 
		

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
					main.setClient(new NormalClient(main.getClient()));
					main.getClient().setEmail(email);
					((RegisteredClient) main.getClient()).setPassword(password);
					((RegisteredClient) main.getClient()).saveClient();
					
					JOptionPane.showMessageDialog(null, "Rejestracja przebiegla pomyslnie. Zostaniesz automatycznie zalogowany");
					main.getCardLayout().show(main.getCardPanel(), "Home Page");
					main.changeLayoutOfNorthPanel();
			
				}
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Niepoprawne dane podane");
			}
		}
	}

}
