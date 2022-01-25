package gui.payment;

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

import gui.shop.MainGUI;
import waysofpayments.*;

//Klasa zaimplementowana przez Szymona Sawczuka 
public class PaymentSelectingCategoryGUI {

	private JPanel jPanel;
	private MainGUI main;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public PaymentSelectingCategoryGUI(MainGUI main) {
		
		this.main = main; 

		jPanel = createPanel();
		this.main.setButtonCursor(jPanel);

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
			
			
			JButton button = new JButton(new ImageIcon("Ikony/" + way + "64.png"));
			button.setName(way);
			button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			button.addActionListener(new SelectWay(button));
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.setToolTipText(way);
			choicePanel.add(button);
		}
		choicePanel.setMaximumSize(new Dimension(400,36));
		jPanel.add(choicePanel);
		
		JPanel goBackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
		goBackButton.addActionListener(new GoBack());
		goBackPanel.add(goBackButton);
		jPanel.add(goBackPanel);
		
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie (musi byc na poczatku i koncu)
		
		return jPanel;
		
	}
	
	private class SelectWay implements ActionListener{
		
		private JButton jButton;
		
		public SelectWay(JButton jButton) {
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			main.getjFrame().setEnabled(false);
			
			if(jButton.getName().equals("Blik")) {
				
				main.getClient().setWayOfPayment(new Blik());
			
			}else if(jButton.getName().equals("Platnosc karta")) {
				
				main.getClient().setWayOfPayment(new Card());
			
			}else if(jButton.getName().equals("Paypal")) {
				
				main.getClient().setWayOfPayment(new Paypal());
			}
			
			main.getClient().getWayOfPayment().starFrame(main);
		}
	}
	
	private class GoBack implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			main.getClient().setWayOfDelivery(null);
			main.changeLayoutToDeliverySelectingCategory();
			
		}
		
		
		
	}
	
	
	
}