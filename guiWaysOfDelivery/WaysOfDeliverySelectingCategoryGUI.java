package guiWaysOfDelivery;

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
import guiClient.SetClientInfoGUI;
import waysofdelivery.*;

//Klasa stworzona przez Jana Skibinskiego (jeszcze nieskonczona)
public class WaysOfDeliverySelectingCategoryGUI
{
	private Client client;
	private ArrayList<JButton> selectCategory = new ArrayList<JButton>();
	private JFrame jFrame;
	private JButton goBackButton;
	
	public WaysOfDeliverySelectingCategoryGUI(Client client) 
	{
		this.client = client;
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Wybor Dostawy");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		JPanel jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Wybierz sposob dostawy", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		jPanel.add(titleJLabel);
		for (String category : WaysOfDelivery.getCategories())
		{
			JButton jButton = new JButton(category);
			jButton.addActionListener(new SelectCategory());
			jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(jButton);
			selectCategory.add(jButton);
			
			// Zeby guziki na siebie nie nachodzily
			jPanel.add(Box.createRigidArea(new Dimension(0,7))); 
		}
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10))); 
		
		jPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		goBackButton = new JButton("Cofnij sie");
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		goBackButton.addActionListener(new GoBack());
		jPanel.add(goBackButton);
		
		if (client instanceof RegisteredClient) {
			JLabel RegisteredClientJLabel = new JLabel("Jestes zalogowany pod adresem email");
			RegisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			RegisteredClientJLabel.setBorder(new EmptyBorder(10,5,8,5));
			jPanel.add(RegisteredClientJLabel);
			
			JLabel emailJLabel = new JLabel(client.getEmail());
			emailJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			emailJLabel.setBorder(new EmptyBorder(0,5,10,5));
			jPanel.add(emailJLabel);
		}
		
		else {
			JLabel unregisteredClientJLabel = new JLabel("Jestes niezalogowany");
			unregisteredClientJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			unregisteredClientJLabel.setBorder(new EmptyBorder(10,5,10,5));
			jPanel.add(unregisteredClientJLabel);
		}
		
		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	class SelectCategory implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			for (JButton jButton : selectCategory)
			{
				if (event.getSource() == jButton) {
					if(jButton.getText().equals("Odbior osobisty"))
					{
						new OsobistyGUI(client);
						jFrame.dispose();
					}
					if(jButton.getText().equals("Kurier"))
					{
						new KurierSetInfo(client);
						jFrame.dispose();
					}
					if(jButton.getText().equals("Paczkomat"))
					{
						new PaczkomatSetInfo1(client);
						jFrame.dispose();
					}
				}
			}
		}
	}
	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			new SetClientInfoGUI(client);
			jFrame.dispose();
		}
	}
}