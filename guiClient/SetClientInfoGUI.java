package guiClient;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.HelperFunctionsClient;
import client.RegisteredClient;
import guiChooseItems.BasketGUI;
import guiWaysOfDelivery.WaysOfDeliverySelectingCategoryGUI;


// Klasa stworzona przez Wiktora Sadowego
public class SetClientInfoGUI 
{
	private Client client;
	private JFrame jFrame;
	private JTextField firstNameJTextField;
	private JTextField lastNameJTextField;
	private JTextField emailJTextField;
	private JTextField phoneNumberJTextField;
	private JButton confirmDataButton;
	private JButton goBackButton;
	
	public SetClientInfoGUI(Client client) 
	{
		this.client = client;
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Sklep");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Wpisz swoje podstawowe dane", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,15,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		
		JLabel firstNameJLabel = new JLabel("Wpisz swoje imie", SwingConstants.CENTER);
		firstNameJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		firstNameJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(firstNameJLabel);
		
		firstNameJTextField = new JTextField(client.getFirstName()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		firstNameJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		firstNameJTextField.setHorizontalAlignment(JTextField.CENTER);
		firstNameJTextField.setMaximumSize(new Dimension(250, 90));
		jPanel.add(firstNameJTextField);
		
		JLabel lastNameJLabel = new JLabel("Wpisz swoje nazwisko", SwingConstants.CENTER);
		lastNameJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lastNameJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(lastNameJLabel);
		
		lastNameJTextField = new JTextField(client.getLastName()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		lastNameJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		lastNameJTextField.setHorizontalAlignment(JTextField.CENTER);
		lastNameJTextField.setMaximumSize(new Dimension(250, 90));
		jPanel.add(lastNameJTextField);
		
		JLabel adresEmailJLabel = new JLabel("Wpisz swoj adres email", SwingConstants.CENTER);
		adresEmailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		adresEmailJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(adresEmailJLabel);
		
		emailJTextField = new JTextField(client.getEmail()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		emailJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailJTextField.setHorizontalAlignment(JTextField.CENTER);
		emailJTextField.setMaximumSize(new Dimension(250, 90));
		if (client instanceof RegisteredClient) {
			emailJTextField.setEditable(false); // Jak uzytkownik jest zalogowany to nie moze zmienic swojego adresu email
		}
		jPanel.add(emailJTextField);
		
		JLabel phoneNumberEmailJLabel = new JLabel("Wpisz swoj numer telefonu", SwingConstants.CENTER);
		phoneNumberEmailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		phoneNumberEmailJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(phoneNumberEmailJLabel);
		
		phoneNumberJTextField = new JTextField(client.getPhoneNumber()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		phoneNumberJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		phoneNumberJTextField.setHorizontalAlignment(JTextField.CENTER);
		phoneNumberJTextField.setMaximumSize(new Dimension(250, 90));
		jPanel.add(phoneNumberJTextField);
		
		jPanel.add(Box.createRigidArea(new Dimension(5, 15)));
		
		confirmDataButton = new JButton("Potwierdz dane");
		confirmDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		confirmDataButton.addActionListener(new ConfirmData());
		jPanel.add(confirmDataButton);
		
		jPanel.add(Box.createRigidArea(new Dimension(5, 20)));
		
		goBackButton = new JButton("Cofnij sie");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		
		if (client instanceof RegisteredClient) {
			JLabel RegisteredClientJLabel = new JLabel("Jestes zalogowany pod adresem email");
			RegisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			RegisteredClientJLabel.setBorder(new EmptyBorder(10,5,8,5));
			jPanel.add(RegisteredClientJLabel);
			
			JLabel emailJLabel = new JLabel(client.getEmail());
			emailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			emailJLabel.setBorder(new EmptyBorder(0,5,10,5));
			jPanel.add(emailJLabel);
		}
		
		else {
			JLabel unregisteredClientJLabel = new JLabel("Jestes niezalogowany");
			unregisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			unregisteredClientJLabel.setBorder(new EmptyBorder(10,5,10,5));
			jPanel.add(unregisteredClientJLabel);
		}
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	class ConfirmData implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String firstName = firstNameJTextField.getText();
			String lastName = lastNameJTextField.getText();
			String email = emailJTextField.getText();
			String phoneNumber = phoneNumberJTextField.getText();
			
			if (HelperFunctionsClient.isCorrectName(firstName) && HelperFunctionsClient.isCorrectName(lastName) 
					&& HelperFunctionsClient.isCorrectEmail(email) && HelperFunctionsClient.isCorrectPhoneNumber(phoneNumber)) {
				
				client.setFirstName(firstName);
				client.setLastName(lastName);
				client.setEmail(email);
				client.setPhoneNumber(phoneNumber);
				
				if(JOptionPane.showConfirmDialog(null, "Czy to s¹ twoje dane: " + client.toString(), "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (client instanceof RegisteredClient) {
						((RegisteredClient) client).saveClient();
					}
					new WaysOfDeliverySelectingCategoryGUI(client);
					jFrame.dispose();
				}
			}
			
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Niepoprawne dane. Wprowadz poprawne dane");
			}
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new BasketGUI(client);
			jFrame.dispose();
		}
	}
}
