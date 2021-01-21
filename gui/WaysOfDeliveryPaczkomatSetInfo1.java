package gui;

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
import client.RegisteredClient;
import waysofdelivery.*;



// Klasa stworzona przez Jana Skibinskiego
public class WaysOfDeliveryPaczkomatSetInfo1
{
	private Client client;
	private JFrame jFrame;
	private JTextField paczkomatCodeJTextField;
	private JTextField miastoJTextField;
	private JButton confirmDataButton;
	private JButton confirmMiastoButton;
	private JButton goBackButton;
	private Paczkomat paczkomat;
	
	public WaysOfDeliveryPaczkomatSetInfo1(Client client) 
	{
		this.client = client;
		paczkomat=new Paczkomat(client);
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Paczkomat");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Wybierz paczkomat do ktorego dostarczymy paczke.", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,15,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		
		JLabel miastoJLabel = new JLabel("Wpisz swoje miasto, a znajdziemy paczkomaty blisko Ciebie.", SwingConstants.CENTER);
		miastoJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		miastoJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(miastoJLabel);
		
		miastoJTextField = new JTextField(paczkomat.getMiasto()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		miastoJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		miastoJTextField.setHorizontalAlignment(JTextField.CENTER);
		miastoJTextField.setMaximumSize(new Dimension(200, 60));
		jPanel.add(miastoJTextField);
		
		jPanel.add(Box.createRigidArea(new Dimension(5, 15)));
		
		confirmMiastoButton = new JButton("Potwierdz dane");
		confirmMiastoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		confirmMiastoButton.addActionListener(new ConfirmMiastoButton());
		jPanel.add(confirmMiastoButton);
		
		jPanel.add(Box.createRigidArea(new Dimension(5, 15)));
		
		JLabel kodPaczkomatu = new JLabel("Albo podaj kod paczkomatu do ktorego dostarczymy przesylke.", SwingConstants.CENTER);
		kodPaczkomatu.setAlignmentX(Component.CENTER_ALIGNMENT);
		kodPaczkomatu.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(kodPaczkomatu);
		
		paczkomatCodeJTextField = new JTextField(paczkomat.getPaczkomatCode()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		paczkomatCodeJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		paczkomatCodeJTextField.setHorizontalAlignment(JTextField.CENTER);
		paczkomatCodeJTextField.setMaximumSize(new Dimension(200, 60));
		jPanel.add(paczkomatCodeJTextField);
		
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
			String paczkomatCode = paczkomatCodeJTextField.getText();
			
			if (isCorrectPaczkomatCode(paczkomatCode))
			{
				paczkomat.setPaczkomatCode(paczkomatCode);
				if(JOptionPane.showConfirmDialog(null, "Czy to twoje dane do dostawy: " + paczkomat.toString(), "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (client instanceof RegisteredClient) {
						((RegisteredClient) client).saveClient();
					}
					client.setWayOfDelivery(paczkomat);
					new WaysOfPaymentSelectingCategoryGUI(client);
					jFrame.dispose();
				}
			}
			
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Niepoprawne dane. Wprowadz poprawne dane");
			}
		}
		private boolean isCorrectPaczkomatCode(String paczkomatCode)
		{
			if(paczkomatCode.isEmpty())
			{
				return false;
			}
			if (paczkomatCode.length() == 6)
			{
				char[] letters = paczkomatCode.toCharArray();
				for (int i=0; i<paczkomatCode.length(); i++)
				{
					if(i<3)
					{
						if(!Character.isLetter(letters[i]))
						{
							return false;
						}
					}
					else
					{
						if(!Character.isDigit(letters[i]))
						{
							return false;
						}
					}	
				}
				return true;
			}
			else {
				return false;
			}
		}
	}
	class ConfirmMiastoButton implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String miasto = miastoJTextField.getText();
			
			if (isCorrectMiasto(miasto)) {
				if(JOptionPane.showConfirmDialog(null, "Czy to na pewno jest twoje miasto? " + miasto, "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					client.setWayOfDelivery(paczkomat);
					if (client instanceof RegisteredClient) {
						((RegisteredClient) client).saveClient();
					}
					paczkomat.setMiasto(miasto);
					new WaysOfDeliveryPaczkomatSetInfo2(client,paczkomat);
					jFrame.dispose();
				}
			}
			
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Niepoprawne dane. Wprowadz poprawne dane");
			}
		}
		
		private boolean isCorrectMiasto(String miasto)
		{
			if (miasto.isEmpty()) {
				return false;
			}
			if(miasto.length()<3)
			{
				return false;
			}
			char[] letters = miasto.toCharArray();
			for (int i=0; i<letters.length; i++)
			{
				if (!Character.isLetter(letters[i])) {
					return false;
				}
			}
			return true;
		}
		
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new WaysOfDeliverySelectingCategoryGUI(client);
			jFrame.dispose();
		}
	}
}
