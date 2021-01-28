package guiWaysOfPayment;

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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.RegisteredClient;
import guiWaysOfDelivery.WaysOfDeliverySelectingCategoryGUI;
import waysofpayments.WaysOfPayments;

//Klasa zaimplementowana przez Szymona Sawczuka 
public class WaysOfPaymentSelectingCategoryGUI {

	private Client client;
	private JFrame jFrame;
	private JPanel mainPanel;
	
	
	public WaysOfPaymentSelectingCategoryGUI(Client client) {
		
		this.client = client; 
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Wybor Platnosci");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		mainPanel = createPanel();
		
		jFrame.add(mainPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	
		
	}
	
	private JPanel createPanel() {
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Wybierz sposob platnosci", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		
		for(String way : WaysOfPayments.TYPESOFPAYMENT) {
			
			JButton button = new JButton(way);
			button.addActionListener(new SelectWay(button));
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(button);
			
			// Zeby guziki na siebie nie nachodzily
			jPanel.add(Box.createRigidArea(new Dimension(0,7))); 
			
		}
		
		jPanel.add(Box.createRigidArea(new Dimension(0,20))); 
		
		JButton goBackButton = new JButton("Cofnij sie");
		goBackButton.addActionListener(new GoBack());
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(goBackButton);
		
		String text = "";
		if (client instanceof RegisteredClient) {
			text = "Jestes zalogowany pod adresem email: " + client.getEmail();
		}
		else {
			text = "Jestes niezalogowany";
		}
		
		JLabel loggedLabel = new JLabel(text);
		loggedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		loggedLabel.setBorder(new EmptyBorder(5,10,20,10));	//top,left,bottom,right
		jPanel.add(loggedLabel);
		
		return jPanel;
		
	}
	
	class SelectWay implements ActionListener{
		
		private JButton jButton;
		
		public SelectWay(JButton jButton) {
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(jButton.getText().equals("Blik")) {
				new BlikGUI(client);
			}else if(jButton.getText().equals("Platnosc karta")) {
				new CardGUI(client);
			}else if(jButton.getText().equals("Paypal")) {
				new PayPalGUI(client);
			}
			
			jFrame.dispose();
			
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new WaysOfDeliverySelectingCategoryGUI(client);
			client.setWayOfDelivery(null);
			jFrame.dispose();
		}
	}
	
}