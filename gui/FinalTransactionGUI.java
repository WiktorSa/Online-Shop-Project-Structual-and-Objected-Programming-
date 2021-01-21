package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import chooseitems.Basket;
import client.Client;
import client.RegisteredClient;


public class FinalTransactionGUI {
	
	private Client client;
	private JFrame jFrame;
	private JPanel infoPanel, buttonPanel;
	private BoxLayout boxLayout;
	
	public FinalTransactionGUI(Client client) {
		
		this.client = client;
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Informacje o transakcji");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		
		infoPanel = createInfoPanel();
		buttonPanel = createButtonPanel();

		
	
		jFrame.getContentPane().add(BorderLayout.CENTER,infoPanel);
		jFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
	

		jFrame.pack();
		jFrame.setVisible(true);
		
		if(client.getWayOfPayment().isPaymentDone()) {
			client.setBasket(new Basket());
			if (this.client instanceof RegisteredClient) {
				((RegisteredClient) this.client).saveClient();
			}
		}
		
	}

	
	private JPanel createInfoPanel() {
		
		JPanel infoPanel = new JPanel();
		boxLayout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
		infoPanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("<html>" + client.getTransactionInfo().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n", "<br/>" )+ "</html>", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Arial", Font.BOLD, 30));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		infoPanel.add(titleJLabel);
		
		return infoPanel;
	}
	
	private JPanel createButtonPanel() {
		
		JPanel buttonPanel = new JPanel();
		
		JButton goBackButton = new JButton("Wroc na strone glowna");
		goBackButton.addActionListener(new GoBack());
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(goBackButton);
		
		
		return buttonPanel;
	}
	
	private class GoBack implements ActionListener{
		
		public void actionPerformed(ActionEvent event) 
		{
			if (client instanceof RegisteredClient) {
				new ShopRegisteredClientGUI(client);
			}else {
				new ShopUnregisteredClientGUI(client);
			}

			jFrame.dispose();
		}
	}

}
