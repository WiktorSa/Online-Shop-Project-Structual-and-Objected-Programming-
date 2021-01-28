package guiWaysOfPayment;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import guiShop.MainGUI;
import guiWaysOfDelivery.WaysOfDeliverySelectingCategoryGUI;
import waysofpayments.WaysOfPayments;

//Klasa zaimplementowana przez Szymona Sawczuka 
public class WaysOfPaymentSelectingCategoryGUI {

	private JPanel jPanel;
	private MainGUI main;
	private JPanel cardPaymentPanel;
	private CardGUI cardGUI;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public WaysOfPaymentSelectingCategoryGUI(MainGUI main) {
		
		this.main = main; 

		jPanel = createPanel();

	}
	
	private JPanel createPanel() {
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie (musi byc na poczatku i koncu)
		
		JLabel titleJLabel = new JLabel("Wybierz sposob platnosci", SwingConstants.CENTER);
		titleJLabel.setFont(new Font(titleJLabel.getFont().getName(), Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		
		JPanel choicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		for(String way : WaysOfPayments.TYPESOFPAYMENT) {
			
			
			JButton button = new JButton(new ImageIcon("Ikony/" + way + ".png"));
			button.setName(way);
			button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			button.addActionListener(new SelectWay(button));
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			switch(way)
			{
			case "Blik":
				button.setToolTipText("Blik");
				break;
			case "Platnosc karta":
				button.setToolTipText("Platnosc karta");
				break;
			case "Paypal":
				button.setToolTipText("Paypal");
				break;
			default:
				button.setToolTipText("Cos sie popsulo");
				break;
			}
			choicePanel.add(button);

			if(way.equals("Blik")) {
				button.setBorder(new EmptyBorder(0,0,0,0));
			}
			button.setSize(36, 36);
			
			// Zeby guziki na siebie nie nachodzily
			choicePanel.add(Box.createRigidArea(new Dimension(7,0))); 
			
		}
		jPanel.add(choicePanel);
		
		
		
		return jPanel;
		
	}
	
	class SelectWay implements ActionListener{
		
		private JButton jButton;
		
		public SelectWay(JButton jButton) {
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			main.getjFrame().setEnabled(false);
			
			if(jButton.getName().equals("Blik")) {
				
				new BlikGUI(main);
			
			}else if(jButton.getName().equals("Platnosc karta")) {
				
				main.changeLayoutToCardPayment();
			
			}else if(jButton.getName().equals("Paypal")) {
				
				new PayPalGUI(main);
			}
			
			
		}
	}
	
	
	
}
