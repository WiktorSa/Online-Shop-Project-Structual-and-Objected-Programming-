package guiWaysOfDelivery;

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

import guiShop.MainGUI;
import waysofdelivery.Paczkomat;

//Klasa stworzona przez Jana Skibinskiego
public class PaczkomatSetInfo2
{
	private ArrayList<JButton> selectPaczkomat = new ArrayList<JButton>();
	private JPanel jPanel;
	private JButton goBackButton;
	private Paczkomat paczkomat;
	private MainGUI main;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public PaczkomatSetInfo2(MainGUI main,Paczkomat paczkomat) 
	{
		this.main=main;
		this.paczkomat=paczkomat;
		paczkomat.setPaczkomatList();
		
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());
		
		JLabel titleJLabel = new JLabel("Wybierz sposob dostawy", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		for (String category : paczkomat.getPACZKOMATLIST())
		{
			JButton jButton = new JButton(category);
			jButton.addActionListener(new SelectCategory());
			jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(jButton);
			selectPaczkomat.add(jButton);
			
			// Zeby guziki na siebie nie nachodzily
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
	

	class SelectCategory implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			for (JButton jButton : selectPaczkomat)
			{
				if (event.getSource() == jButton) {
					paczkomat.setPaczkomatCode(jButton.getText());
					main.getClient().setWayOfDelivery(paczkomat);
					
					main.changeLayoutToWaysOfPaymentSelectingCategory();
				}
			}
		}
	}

	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			main.changeLayoutToPaczkomat1();
		}
	}
}
