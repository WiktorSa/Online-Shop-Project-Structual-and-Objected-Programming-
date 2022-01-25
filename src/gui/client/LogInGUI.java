package gui.client;

import java.awt.Component;
import java.awt.Cursor;
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

import chooseitems.ShoppingBasket;
import client.Client;
import client.RegisteredClient;
import gui.shop.MainGUI;

public class LogInGUI {
	private JPanel jPanel;
	private JTextField emailJTextField;
	private JPasswordField passwordJPasswordField;
	private JButton logInButton;
	private JButton remindPasswrodButton;
	private MainGUI main;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public LogInGUI(MainGUI main) {
		this.main = main;
		
		Dimension buttonSize = new Dimension(300,40);
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie (musi byc na poczatku i koncu)
		JLabel giveInstructionsJLabel = new JLabel("Zaloguj sie", SwingConstants.CENTER);
		giveInstructionsJLabel.setFont(new Font(giveInstructionsJLabel.getFont().getName(), Font.BOLD, 40));
		giveInstructionsJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(giveInstructionsJLabel);
		
		JLabel emaiJLabel = new JLabel("Wpisz swoj adres email", SwingConstants.CENTER);
		emaiJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		emaiJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(emaiJLabel);
		
		emailJTextField = new JTextField();
		emailJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailJTextField.setHorizontalAlignment(JTextField.CENTER);
		emailJTextField.setPreferredSize(new Dimension(300, 30));
		emailJTextField.setMaximumSize(new Dimension(300, 30));
		jPanel.add(emailJTextField);
		
		JLabel passswordJLabel = new JLabel("Wpisz haslo", SwingConstants.CENTER);
		passswordJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		passswordJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(passswordJLabel);
		
		passwordJPasswordField = new JPasswordField();
		passwordJPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
		passwordJPasswordField.setHorizontalAlignment(JTextField.CENTER);
		passwordJPasswordField.setPreferredSize(new Dimension(300, 30));
		passwordJPasswordField.setMaximumSize(new Dimension(300, 30));
		jPanel.add(passwordJPasswordField);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		JPanel logInButtonPanel = new JPanel();
		logInButtonPanel.setMaximumSize(buttonSize);
		logInButton = new JButton("Zaloguj sie");
		logInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logInButton.setPreferredSize(buttonSize);
		logInButton.addActionListener(new LogIn());
		logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		logInButtonPanel.add(logInButton);
		jPanel.add(logInButtonPanel);
		
		JPanel remindPasswrodButtonPanel = new JPanel();
		remindPasswrodButtonPanel.setMaximumSize(buttonSize);
		remindPasswrodButton = new JButton("Przypomnij sobie haslo");
		remindPasswrodButton.setPreferredSize(buttonSize);
		remindPasswrodButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		remindPasswrodButton.addActionListener(new RemindPassword());
		remindPasswrodButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		remindPasswrodButtonPanel.add(remindPasswrodButton);
		jPanel.add(remindPasswrodButtonPanel);
		jPanel.add(Box.createVerticalGlue());
	}
	
	class LogIn implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String email = emailJTextField.getText();
			String password = String.valueOf(passwordJPasswordField.getPassword());
			
			File file = new File("Client_" + email + ".ser");
			
			if (!file.exists()) {
				JOptionPane.showMessageDialog(null, "Nie ma konta na podany adres email");
			}
			
			else {
				try(ObjectInputStream outputStream = new ObjectInputStream(new FileInputStream(file))) {
					Client clientFromFile = (RegisteredClient) outputStream.readObject();
					ShoppingBasket oldBasket = main.getClient().getBasket();
					
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
				} catch (FileNotFoundException e) {
					System.exit(-1);
				} catch (IOException e) {
					System.exit(-1);
				} catch (ClassNotFoundException e) {
					System.exit(-1);
				}
			}
		}
	}

	class RemindPassword implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String email = emailJTextField.getText();
			
			File file = new File("Client_" + email + ".ser");
			
			if (!file.exists()) {
				JOptionPane.showMessageDialog(null, "Nie ma konta na podany adres email. Wpisz swoj adres email zanim poprosisz o przypomnienie hasla");
			}
			
			else {
				try(ObjectInputStream outputStream = new ObjectInputStream(new FileInputStream(file))){
					Client clientFromFile = (RegisteredClient) outputStream.readObject();
					JOptionPane.showMessageDialog(null, "Twoje haslo to: " + ((RegisteredClient) clientFromFile).getPassword());
				} catch (FileNotFoundException e) {
					System.exit(-1);
				} catch (IOException e) {
					System.exit(-1);
				} catch (ClassNotFoundException e) {
					System.exit(-1);
				}
			}
		}
	}
}
