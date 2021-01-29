package guiShop;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import chooseitems.Basket;
import client.Client;
import client.RegisteredClient;



public class FinalTransactionGUI {
	
	private Client client;
	private JPanel jPanel, infoPanel;
	private BoxLayout boxLayout;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public FinalTransactionGUI(Client client) {
		
		this.client = client;
		
		jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		
		jPanel.add(Box.createVerticalGlue());
		
		infoPanel = createInfoPanel();

	
		jPanel.add(infoPanel, BorderLayout.CENTER);
		jPanel.add(Box.createVerticalGlue());

		
		if(client.getWayOfPayment().isPaymentDone()) {
			client.setBasket(new Basket());
			client.setWayOfDelivery(null);
			client.setWayOfPayment(null);
			if (this.client instanceof RegisteredClient) {
				((RegisteredClient) this.client).saveClient();
			}
		}
		
	}

	
	private JPanel createInfoPanel() {
		
		JPanel infoPanel = new JPanel();
		boxLayout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
		infoPanel.setLayout(boxLayout);
		
		JLabel infoLabel = new JLabel("<html><div style='text-align:center; font-size:30px;font-weight: bold;margin-bottom:20px;'>Informacje o transakcji:</div><div style='text-align:center;'>" 
				+ client.getTransactionInfo().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n", "<br/>" )+ "</div></html>", SwingConstants.CENTER);
		infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, 20));
		
		infoPanel.add(infoLabel);
		
		
		return infoPanel;
	}
	

	


}
