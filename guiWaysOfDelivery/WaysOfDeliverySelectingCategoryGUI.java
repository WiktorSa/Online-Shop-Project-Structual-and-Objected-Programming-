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

import guiChooseItems.BasketGUI;
import guiShop.MainGUI;
import waysofdelivery.*;

//Klasa zaimplementowana przez Szymona Sawczuka 
public class WaysOfDeliverySelectingCategoryGUI {

	private JPanel jPanel;
	private MainGUI main;
	private PaczkomatSetInfo1 paczkomatCategory;
	private OsobistyGUI osobistyCategory;
	private KurierSetInfo kurierCategory;
	private JPanel deliveryOption;
	
	private BasketGUI goBackCategory;
	private JPanel backPanel;
	
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
		
		JButton goBackButton = new JButton("Cofnij sie");
		goBackButton.addActionListener(new GoBack());
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(goBackButton);
		jPanel.add(Box.createVerticalGlue());
	}
	
	class SelectWay implements ActionListener{
		
		private JButton jButton;
		
		public SelectWay(JButton jButton) {
			this.jButton = jButton;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(jButton.getName().equals("Odbior osobisty")) {
				
				osobistyCategory=new OsobistyGUI(main);
				deliveryOption=osobistyCategory.getjPanel();
				main.getCardPanel().add(deliveryOption,"Delivery Page");
				main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
				
			}else if(jButton.getName().equals("Paczkomat")) {
				
				paczkomatCategory=new PaczkomatSetInfo1(main);
				deliveryOption=paczkomatCategory.getjPanel();
				main.getCardPanel().add(deliveryOption,"Delivery Page");
				main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
				
			}else if(jButton.getName().equals("Kurier")) {
				kurierCategory=new KurierSetInfo(main);
				deliveryOption=kurierCategory.getjPanel();
				main.getCardPanel().add(deliveryOption,"Delivery Page");
				main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
			}
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			goBackCategory=new BasketGUI(main);
			backPanel=goBackCategory.getJPanel();
			main.getCardPanel().add(backPanel,"Delivery Page");
			main.getCardLayout().show(main.getCardPanel(), "Delivery Page");
		}
	}
	
}