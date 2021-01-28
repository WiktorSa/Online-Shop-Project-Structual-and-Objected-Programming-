package guiWaysOfDelivery;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.RegisteredClient;
import guiShop.MainGUI;
import guiWaysOfPayment.WaysOfPaymentSelectingCategoryGUI;
import waysofdelivery.*;

//Klasa stworzona przez Jana Skibinskiego
public class PaczkomatSetInfo2
{
	private Client client;
	private ArrayList<JButton> selectPaczkomat = new ArrayList<JButton>();
	private JPanel jPanel;
	private JButton goBackButton;
	private Paczkomat paczkomat;
	private MainGUI main;
	
	private PaczkomatSetInfo1 goBackCategory;
	private JPanel backPanel;
	private WaysOfPaymentSelectingCategoryGUI goToPayment;
	private JPanel paymentPanel;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public PaczkomatSetInfo2(MainGUI main,Paczkomat paczkomat) 
	{
		this.main=main;
		this.paczkomat=paczkomat;
		paczkomat.setPaczkomatList();
		client=main.getClient();
		
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());
		
		JLabel titleJLabel = new JLabel("Wybierz sposob dostawy", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		for (String category : paczkomat.getPACZKOMATLIST())
		{
			JButton jButton = new JButton(category);
			jButton.addActionListener(new SelectCategory());
			jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(jButton);
			selectPaczkomat.add(jButton);
			
			// Zeby guziki na siebie nie nachodzily
			jPanel.add(Box.createRigidArea(new Dimension(0,7))); 
		}
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10))); 
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		goBackButton = new JButton("Cofnij sie");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		jPanel.add(Box.createVerticalGlue());
	}
	

	class SelectCategory implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			for (JButton jButton : selectPaczkomat)
			{
				if (event.getSource() == jButton) {
					paczkomat.setPaczkomatCode(jButton.getText());
					client.setWayOfDelivery(paczkomat);
					goToPayment=new WaysOfPaymentSelectingCategoryGUI(main);
					paymentPanel=goToPayment.getjPanel();
					main.getCardPanel().add(paymentPanel,"Delivery Page");
					main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
				}
			}
		}
	}

	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			goBackCategory=new PaczkomatSetInfo1(main);
			backPanel=goBackCategory.getjPanel();
			main.getCardPanel().add(backPanel,"Delivery Page");
			main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
		}
	}
}
