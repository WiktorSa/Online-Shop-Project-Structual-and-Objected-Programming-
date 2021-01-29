package guiShop;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

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
		
		jPanel = new JPanel(new BorderLayout());
		
		
		infoPanel = createInfoPanel();

	
		jPanel.add(infoPanel, BorderLayout.CENTER);


		
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
	

	


}
