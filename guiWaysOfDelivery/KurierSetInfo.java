package guiWaysOfDelivery;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import guiShop.MainGUI;
import client.Client;
import client.RegisteredClient;
import guiWaysOfPayment.WaysOfPaymentSelectingCategoryGUI;
import waysofdelivery.*;


// Klasa stworzona przez Jana Skibinskiego
public class KurierSetInfo
{
	private MainGUI main;
	private JPanel jPanel;
	private JTextField miastoJTextField;
	private JTextField ulicaJTextField;
	private JTextField kodPocztowyJTextField;
	private JButton confirmDataButton;
	private JButton goBackButton;
	private Client client;
	private Kurier kurier;
	
	private WaysOfDeliverySelectingCategoryGUI goBackCategory;
	private JPanel backPanel;
	private WaysOfPaymentSelectingCategoryGUI goToPayment;
	private JPanel paymentPanel;

	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public KurierSetInfo(MainGUI main) 
	{
		this.main=main;
		client = main.getClient();
		kurier=new Kurier(client);
		
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());
		
		JLabel titleJLabel = new JLabel("Wpisz dane potrzebne do dostawy kurierskiej", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,15,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		
		JLabel miastoJLabel = new JLabel("Wpisz swoje miasto", SwingConstants.CENTER);
		miastoJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		miastoJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(miastoJLabel);
		
		miastoJTextField = new JTextField(kurier.getMiasto()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		miastoJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		miastoJTextField.setHorizontalAlignment(JTextField.CENTER);
		miastoJTextField.setMaximumSize(new Dimension(200, 60));
		jPanel.add(miastoJTextField);
		
		JLabel ulicaJLabel = new JLabel("Wpisz swoja ulica z numerem domu/mieszkania", SwingConstants.CENTER);
		ulicaJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		ulicaJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(ulicaJLabel);
		
		ulicaJTextField = new JTextField(kurier.getUlica()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		ulicaJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		ulicaJTextField.setHorizontalAlignment(JTextField.CENTER);
		ulicaJTextField.setMaximumSize(new Dimension(200, 60));
		jPanel.add(ulicaJTextField);
		
		JLabel adresEmailJLabel = new JLabel("Wpisz swoj kod pocztowy (bez myslnika)", SwingConstants.CENTER);
		adresEmailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		adresEmailJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(adresEmailJLabel);
		
		kodPocztowyJTextField = new JTextField(kurier.getKodPocztowy()); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		kodPocztowyJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		kodPocztowyJTextField.setHorizontalAlignment(JTextField.CENTER);
		kodPocztowyJTextField.setMaximumSize(new Dimension(200, 60));
		jPanel.add(kodPocztowyJTextField);
		
		jPanel.add(Box.createRigidArea(new Dimension(5, 15)));
		
		confirmDataButton = new JButton(new ImageIcon("Ikony/forward.png"));
		confirmDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		confirmDataButton.addActionListener(new ConfirmData());
		jPanel.add(confirmDataButton);
		
		jPanel.add(Box.createRigidArea(new Dimension(5, 20)));
		
		goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		jPanel.add(Box.createVerticalGlue());
	}
	
	class ConfirmData implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String miasto = miastoJTextField.getText();
			String ulica = ulicaJTextField.getText();
			String kodPocztowy = kodPocztowyJTextField.getText();
			
			if (isCorrectMiasto(miasto) && isCorrectUlica(ulica) && isCorrectKodPocztowy(kodPocztowy)) {
				kurier.setMiasto(miasto);
				kurier.setUlica(ulica);
				kurier.setKodPocztowy(kodPocztowy);
				
				if(JOptionPane.showConfirmDialog(null, "Czy to twoje dane do dostawy: " + kurier.toString(), "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					client.setWayOfDelivery(kurier);
					if (client instanceof RegisteredClient) {
						((RegisteredClient) client).saveClient();
					}
					goToPayment=new WaysOfPaymentSelectingCategoryGUI(main);
					paymentPanel=goToPayment.getjPanel();
					main.getCardPanel().add(paymentPanel,"Delivery Page");
					main.getCardLayout().show(main.getCardPanel(), "Delivery Page");;
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
			
			char[] letters = miasto.toCharArray();
			for (int i=0; i<letters.length; i++)
			{
				if (!Character.isLetter(letters[i])) {
					return false;
				}
			}
			return true;
		}
		private boolean isCorrectUlica(String ulica)
		{
			if (ulica.isEmpty()) {
				return false;
			}
			return true;
		}
		
		
		private boolean isCorrectKodPocztowy(String kodPocztowy)
		{
			if (kodPocztowy.length() == 5)
			{
				char[] digits = kodPocztowy.toCharArray();
				for (int i=0; i<5; i++)
				{
					if (!Character.isDigit(digits[i])){
						return false;
					}
				}
				
				return true;
			}
			else
			{
				return false;
			}		
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			goBackCategory=new WaysOfDeliverySelectingCategoryGUI(main);
			backPanel=goBackCategory.getjPanel();
			main.getCardPanel().add(backPanel,"Delivery Page");
			main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
		}
	}
}