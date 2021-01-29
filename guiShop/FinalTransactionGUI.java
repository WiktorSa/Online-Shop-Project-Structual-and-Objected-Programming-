package guiShop;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import chooseitems.Basket;
import client.RegisteredClient;



public class FinalTransactionGUI {
	
	private MainGUI main;
	private JPanel jPanel, infoPanel;
	private BoxLayout boxLayout;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public FinalTransactionGUI(MainGUI main) {
		this.main = main;
		
		jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		
		jPanel.add(Box.createVerticalGlue());
		
		infoPanel = createInfoPanel();

	
		jPanel.add(infoPanel, BorderLayout.CENTER);
		jPanel.add(Box.createVerticalGlue());

		
		if(this.main.getClient().getWayOfPayment().isPaymentDone()) {
			this.main.getClient().setBasket(new Basket());
			this.main.getClient().setWayOfDelivery(null);
			this.main.getClient().setWayOfPayment(null);
			if (this.main.getClient() instanceof RegisteredClient) {
				((RegisteredClient) this.main.getClient()).saveClient();
			}
		}
		
	}

	
	private JPanel createInfoPanel() {
		
		JPanel infoPanel = new JPanel();
		boxLayout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
		infoPanel.setLayout(boxLayout);
		
		JLabel infoLabel = new JLabel("<html><div style='text-align:center; font-size:30px;font-weight: bold;margin-bottom:20px;'>Informacje o transakcji:</div><div style='text-align:center;'>" 
				+ main.getClient().getTransactionInfo().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n", "<br/>" )+ "</div></html>", SwingConstants.CENTER);
		infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, 20));
		
		infoPanel.add(infoLabel);
		
		
		return infoPanel;
	}
	

	


}
