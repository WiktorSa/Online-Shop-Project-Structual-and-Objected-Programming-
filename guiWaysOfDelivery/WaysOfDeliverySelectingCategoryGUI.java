package guiWaysOfDelivery;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
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
import waysofdelivery.*;

//Klasa zaimplementowana przez Szymona Sawczuka 
public class WaysOfDeliverySelectingCategoryGUI {

	private JPanel jPanel;
	private MainGUI main;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	
	public WaysOfDeliverySelectingCategoryGUI(MainGUI main) {
		
		this.main = main; 
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());	
	
		
		JLabel titleJLabel = new JLabel("Wybierz sposob dostawy", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		
		for(String way : WaysOfDelivery.getCategories()) {
			JButton button = new JButton(new ImageIcon("Ikony/" + way + ".png"));
			button.setName(way);
			button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			button.addActionListener(new SelectWay(button));
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			switch(way)
			{
				case "Odbior osobisty":
					button.setToolTipText("Odbior osobisty cena: 0 PLN");
					break;
				case "Paczkomat":
					button.setToolTipText("Paczkomat cena: 10.5 PLN");
					break;
				case "Kurier":
					button.setToolTipText("Kurier cena: 12.5 PLN");
					break;
				default:
					button.setToolTipText("Cos sie popsulo");
					break;
			}
			
			jPanel.add(button);
			// Zeby guziki na siebie nie nachodzily
			jPanel.add(Box.createRigidArea(new Dimension(0,7))); 
			
		}
		
		jPanel.add(Box.createRigidArea(new Dimension(0,20))); 
		
		JButton goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
		goBackButton.addActionListener(new GoBack());
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(goBackButton);
		jPanel.add(Box.createVerticalGlue());
		this.main.setButtonCursor(jPanel);
	}
	
	class SelectWay implements ActionListener{
		
		private JButton jButton;
		
		public SelectWay(JButton jButton) {
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			
			switch(jButton.getName())
			{
				case "Odbior osobisty":
					main.getClient().setWayOfDelivery(new Osobisty(main));
					break;
				case "Paczkomat":
					main.getClient().setWayOfDelivery(new Paczkomat(main));
					break;
				case "Kurier":
					main.getClient().setWayOfDelivery(new Kurier(main));
					break;
				default:
					System.out.println("Cos sie popsulo");;
					break;
			}
			main.changeLayoutToWaysOfDelivery();
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			main.changeLayoutToBasket();
		}
	}
	
}