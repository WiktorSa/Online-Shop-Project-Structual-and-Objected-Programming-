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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.RegisteredClient;
import guiShop.MainGUI;
import guiWaysOfPayment.WaysOfPaymentSelectingCategoryGUI;
import waysofdelivery.*;

public class PaczkomatSetInfo1
{

	private JPanel jPanel;
	private JTextField miasto;
	private JTextField paczkomatCode;
	private JButton confirmMiasto;
	private JButton confirmCode;
	private JButton goBack;
	private MainGUI main;
	private Paczkomat paczkomat;
	private Client client;
	
	private WaysOfDeliverySelectingCategoryGUI goBackCategory;
	private JPanel backPanel;
	private WaysOfPaymentSelectingCategoryGUI goToPayment;
	private JPanel paymentPanel;
	private PaczkomatSetInfo2 goToPaczkomat2;
	private JPanel paczkomat2Panel;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public PaczkomatSetInfo1(MainGUI main) 
	{
		this.main = main;
		client=main.getClient();
		paczkomat=new Paczkomat(main.getClient());
		// Tekst bedzie sie wyswietlal od gory do dolu
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie (musi byc na poczatku i koncu)
		
		JLabel giveInstructionsJLabel = new JLabel("Podaj informacje do wysylki paczkomatem.", SwingConstants.CENTER);
		giveInstructionsJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		giveInstructionsJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(giveInstructionsJLabel);
		
		JLabel miastoJLabel = new JLabel("Podaj swoje miasto a znajdziemy paczkomaty blisko Ciebie.", SwingConstants.CENTER);
		miastoJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		miastoJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(miastoJLabel);
		
		miasto = new JTextField(); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		miasto.setAlignmentX(Component.CENTER_ALIGNMENT);
		miasto.setHorizontalAlignment(JTextField.CENTER);
		miasto.setPreferredSize(new Dimension(300, 25));
		miasto.setMaximumSize(new Dimension(300, 25));
		jPanel.add(miasto);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		confirmMiasto = new JButton("Potwierdz");
		confirmMiasto.addActionListener(new ConfirmMiasto());
		confirmMiasto.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(confirmMiasto);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		JLabel kodJlabel = new JLabel("Podaj kod paczkomatu.", SwingConstants.CENTER);
		kodJlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		kodJlabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(kodJlabel);
		
		paczkomatCode = new JTextField(); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		paczkomatCode.setAlignmentX(Component.CENTER_ALIGNMENT);
		paczkomatCode.setHorizontalAlignment(JTextField.CENTER);
		paczkomatCode.setPreferredSize(new Dimension(300, 25));
		paczkomatCode.setMaximumSize(new Dimension(300, 25));
		jPanel.add(paczkomatCode);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		confirmCode = new JButton(new ImageIcon("Ikony/forward.png"));
		confirmCode.addActionListener(new ConfirmCode());
		confirmCode.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(confirmCode);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		goBack = new JButton(new ImageIcon("Ikony/goBack.png"));
		goBack.addActionListener(new GoBack());
		goBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(goBack);
		
		//jPanel.add(Box.createRigidArea(new Dimension(0,20)));
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie 
		
	
		

	}
	
	class ConfirmCode implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String code = paczkomatCode.getText();
			
			if (isCorrectPaczkomatCode(code))
			{
				paczkomat.setPaczkomatCode(code);
				if(JOptionPane.showConfirmDialog(null, "Czy to twoje dane do dostawy: " + paczkomat.toString(), "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					if (client instanceof RegisteredClient) {
						((RegisteredClient) client).saveClient();
					}
					main.getClient().setWayOfDelivery(paczkomat);
					goToPayment=new WaysOfPaymentSelectingCategoryGUI(main);
					paymentPanel=goToPayment.getjPanel();
					main.getCardPanel().add(paymentPanel,"Delivery Page");
					main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
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
	class ConfirmMiasto implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String miast = miasto.getText();
			
			if (isCorrectMiasto(miast)) {
				if(JOptionPane.showConfirmDialog(null, "Czy to na pewno jest twoje miasto? " + miast, "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					client.setWayOfDelivery(paczkomat);
					if (client instanceof RegisteredClient) {
						((RegisteredClient) client).saveClient();
					}
					paczkomat.setMiasto(miast);
					goToPaczkomat2=new PaczkomatSetInfo2(main,paczkomat);
					paczkomat2Panel=goToPaczkomat2.getjPanel();
					main.getCardPanel().add(paczkomat2Panel,"Delivery Page");
					main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
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
			goBackCategory=new WaysOfDeliverySelectingCategoryGUI(main);
			backPanel=goBackCategory.getjPanel();
			main.getCardPanel().add(backPanel,"Delivery Page");
			main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
		}
	}
}
