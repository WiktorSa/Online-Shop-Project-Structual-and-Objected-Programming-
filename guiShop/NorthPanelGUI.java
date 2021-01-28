package guiShop;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.UnregisteredClient;

public class NorthPanelGUI 
{
	private MainGUI mainGUI;
	private JPanel jPanel;
	
	public JPanel getjPanel() 
	{
		return jPanel;
	}
	
	public NorthPanelGUI(MainGUI mainGUI) 
	{
		this.mainGUI = mainGUI;
		
		jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();
		
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton homeButton = new JButton(new ImageIcon("Ikony/house.png"));
		homeButton.addActionListener(new Home());
		leftPanel.add(homeButton);
		homeButton.setToolTipText("Powroc na strone glowna"); // To dodaje taki tekst jak sie najedzie na znak
		homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // To sprawia, ze nasza myszka zmienia ksztalt

		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		Dimension buttonDimension = new Dimension();
		buttonDimension.setSize(120, 45);

		
		//if (client instanceof UnregisteredClient) {
		if (this.mainGUI.getClient() instanceof UnregisteredClient) {
			JButton loginButton = new JButton("Zaloguj sie");
			JButton registerButton = new JButton("Zarejestruj sie");
			
			loginButton.setPreferredSize(buttonDimension);
			loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			loginButton.addActionListener(new LogIn());
			
			registerButton.setPreferredSize(buttonDimension);
			registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			registerButton.addActionListener(new Register());
			
			rightPanel.add(loginButton);
			rightPanel.add(registerButton);
			
		} 
		
		else {
			
			JLabel clientEmailLabel = new JLabel(this.mainGUI.getClient().getEmail());

			JButton logOutButton = new JButton("Wyloguj");
			
			logOutButton.setPreferredSize(buttonDimension);
			logOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			logOutButton.addActionListener(new LogOut());
			
			rightPanel.add(clientEmailLabel);
			rightPanel.add(logOutButton);
			
		}
		
		JButton basketButton = new JButton(new ImageIcon("Ikony/shopping-basket.png"));

		basketButton.setToolTipText("Pokaz koszyk");
		basketButton.addActionListener(new BasketTrigger());
		basketButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
		rightPanel.add(basketButton);
		
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		jPanel.add(leftPanel, gridBag);
		
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.weightx = 1.0;
		gridBag.weighty = 1.0;
		gridBag.anchor = GridBagConstraints.FIRST_LINE_END;
		jPanel.add(rightPanel,gridBag);
		jPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
	}
	
	private class Home implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			mainGUI.changeLayoutToSelectingItems();
		}
		
	}
	
	private class BasketTrigger implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			mainGUI.changeLayoutToBasket();
		}
		
	}
	
	private class LogIn implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			mainGUI.changeLayoutToLogIn();
		}
		
	}
	
	private class Register implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			mainGUI.changeLayoutToRegister();
		}
		
	}
	
	private class LogOut implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			mainGUI.setClient(new UnregisteredClient(mainGUI.getClient()));
			mainGUI.changeLayoutOfNorthPanel();
			mainGUI.changeLayoutToSelectingItems();
		}
		
	}
}
