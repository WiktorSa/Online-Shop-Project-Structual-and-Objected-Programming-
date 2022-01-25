package gui.delivery;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gui.shop.MainGUI;
import waysofdelivery.ParcelLocker;

public class ParcelLockerSetInfo {
	private ArrayList<JButton> selectParcelLocker = new ArrayList<JButton>();
	private JPanel jPanel;
	private JButton goBackButton;
	private MainGUI main;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public ParcelLockerSetInfo(MainGUI main) {
		this.main=main;
		((ParcelLocker) this.main.getClient().getWaysOfDelivery()).setPaczkomatList();
		
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());
		
		JLabel titleJLabel = new JLabel("Wybierz sposob dostawy", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		for (String category : ((ParcelLocker) this.main.getClient().getWaysOfDelivery()).getPACZKOMATLIST()) {
			JButton jButton = new JButton(category);
			jButton.addActionListener(new SelectCategory());
			jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(jButton);
			selectParcelLocker.add(jButton);
			jPanel.add(Box.createRigidArea(new Dimension(0,7))); 
		}
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10))); 
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		jPanel.add(Box.createVerticalGlue());
		this.main.setButtonCursor(jPanel);
	}
	
	class SelectCategory implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			for (JButton jButton : selectParcelLocker){
				if (event.getSource() == jButton) {
					((ParcelLocker) main.getClient().getWaysOfDelivery()).setPaczkomatCode(jButton.getText());
					main.changeLayoutToPaymentSelectingCategory();
				}
			}
		}
	}

	class GoBack implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			main.changeLayoutToWaysOfDelivery();;
		}
	}
}
