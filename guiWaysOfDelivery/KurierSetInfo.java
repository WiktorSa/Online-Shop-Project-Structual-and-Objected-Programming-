package guiWaysOfDelivery;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import client.RegisteredClient;
import guiShop.MainGUI;
import waysofdelivery.Kurier;


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
	private Kurier kurier;

	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public KurierSetInfo(MainGUI main) 
	{
		this.main=main;
		kurier=new Kurier(this.main.getClient());
		
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
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
		goBackButton.addActionListener(new GoBack());
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(goBackButton);
		
		confirmDataButton = new JButton(new ImageIcon("Ikony/forward.png"));
		confirmDataButton.addActionListener(new ConfirmData());
		confirmDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(confirmDataButton);

		jPanel.add(buttonPanel);
		
		jPanel.add(Box.createVerticalGlue());
		this.main.setButtonCursor(jPanel);
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
					main.getClient().setWayOfDelivery(kurier);
					if (main.getClient() instanceof RegisteredClient) {
						((RegisteredClient) main.getClient()).saveClient();
					}
					
					main.changeLayoutToWaysOfPaymentSelectingCategory();
				}
			}
			
			else {
				if(!isCorrectMiasto(miasto)) {
					JOptionPane.showMessageDialog(new JFrame(), "Niepoprawne miasto, na pewno zawiera tylko litery?");
				}
				else if(!isCorrectUlica(ulica)) {
					JOptionPane.showMessageDialog(new JFrame(), "Niepoprawna ulica, wypadaloby cos tam wpisac.");
				}
				else if(!isCorrectKodPocztowy(kodPocztowy)) {
					JOptionPane.showMessageDialog(new JFrame(), "Niepoprawny kod pocztowy, powinien zawierac 5 cyfr bez spacji.");
				}
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
			main.changeLayoutToWaysOfDeliverySelectingCategory();
		}
	}
}