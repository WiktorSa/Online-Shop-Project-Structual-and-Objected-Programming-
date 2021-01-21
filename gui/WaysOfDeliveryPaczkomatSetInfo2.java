package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import client.RegisteredClient;
import waysofdelivery.*;

//Klasa stworzona przez Jana Skibinskiego (jeszcze nieskonczona)
public class WaysOfDeliveryPaczkomatSetInfo2
{
	private Client client;
	private ArrayList<JButton> selectPaczkomat = new ArrayList<JButton>();
	private JFrame jFrame;
	private JButton goBackButton;
	private Paczkomat paczkomat;
	
	public WaysOfDeliveryPaczkomatSetInfo2(Client client,Paczkomat paczkomat) 
	{
		paczkomat.setPaczkomatList();
		this.client = client;
		this.paczkomat=paczkomat;
		if (this.client instanceof RegisteredClient) {
			((RegisteredClient) this.client).saveClient();
		}
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Wybor Dostawy");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Wybierz sposob dostawy", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
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
		
		goBackButton = new JButton("Cofnij sie");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		
		String text = "";
		if (client instanceof RegisteredClient) {
			text = "Jestes zalogowany pod adresem email: " + client.getEmail();
		}
		else {
			text = "Jestes niezalogowany";
		}
		JLabel infoAboutClientStatelJLabel = new JLabel(text);
		infoAboutClientStatelJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoAboutClientStatelJLabel.setBorder(new EmptyBorder(10,0,10,0));
		jPanel.add(infoAboutClientStatelJLabel);
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	

	class SelectCategory implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			for (JButton jButton : selectPaczkomat)
			{
				if (event.getSource() == jButton) {
					paczkomat.setPaczkomatCode(jButton.getText());
					client.setWayOfDelivery(paczkomat);
					new WaysOfPaymentSelectingCategoryGUI(client);
					jFrame.dispose();
				}
			}
		}
	}

	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new WaysOfDeliveryPaczkomatSetInfo1(client);
			jFrame.dispose();
		}
	}
}
