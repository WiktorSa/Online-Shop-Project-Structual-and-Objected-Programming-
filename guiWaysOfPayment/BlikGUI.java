package guiWaysOfPayment;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import client.Client;
import guiShop.FinalTransactionGUI;
import waysofpayments.Blik;

//Klasa zimplementowana przez Szymona Sawczuka
public class BlikGUI {
	
	private Client client;
	private JFrame jFrame;
	private BlikCodeGUI codeGUI;
	private JPanel titlePanel, buttonPanel;
	private JFormattedTextField blikForm;
	
	public BlikGUI(Client client) {

		this.client = client; 
		client.setWayOfPayment(new Blik());
		codeGUI = new BlikCodeGUI(client.getWayOfPayment());
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("BLIK");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);

		
		titlePanel = createtitlePanel();		
		blikForm = createBlikForm();
		buttonPanel = createButtonPanel();
		
		
		jFrame.getContentPane().add(BorderLayout.NORTH,titlePanel);
		jFrame.getContentPane().add(BorderLayout.CENTER,blikForm);
		jFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
	

		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	private JPanel createtitlePanel() {
		
		JPanel titlePanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(titlePanel, BoxLayout.Y_AXIS);
		titlePanel.setLayout(boxLayout);
		
		JLabel titleJLabel = new JLabel("Wpisz kod BLIK", SwingConstants.CENTER);
		titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		titlePanel.add(titleJLabel);
		
		return titlePanel;
	}
	
	private JFormattedTextField createBlikForm() {
		
		JFormattedTextField blikForm = new JFormattedTextField(onlyAllowNaturalNumbersUpTo999999());
		blikForm.setColumns(10);
		blikForm.setAlignmentX(Component.CENTER_ALIGNMENT);

		
		return blikForm;
	}
	
	private JPanel createButtonPanel() {
		
		JPanel buttonPanel = new JPanel();
		
		JButton goBackButton = new JButton("Anuluj platnosc");
		goBackButton.addActionListener(new GoBack());
		goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(goBackButton);
		
		JButton submitButton = new JButton("Zaplac");
		submitButton.addActionListener(new SubmitCode());
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(submitButton);
		
		return buttonPanel;
	}
	
	class GoBack implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			new WaysOfPaymentSelectingCategoryGUI(client);
			((Blik)client.getWayOfPayment()).destroyTimer();
			client.setWayOfPayment(null);
			jFrame.dispose();
			codeGUI.dispose();
		}
	}
	
	class SubmitCode implements ActionListener{
		
		

		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(!blikForm.getText().equals("")) {
				
				if(((Blik)client.getWayOfPayment()).pay(client, new ArrayList<String>(Arrays.asList(blikForm.getText())))) {
					
					JOptionPane.showMessageDialog(null,"Dokonano platnosci");
					((Blik)client.getWayOfPayment()).destroyTimer();
					codeGUI.dispose();
					jFrame.dispose();
					new FinalTransactionGUI(client);
					
					
				}else {
					JOptionPane.showMessageDialog(null,"Bledny kod blik");
				}
			}
			
		}
		
	}
	
	public NumberFormatter onlyAllowNaturalNumbersUpTo999999()
	{
		// Nie pozwalam uzytkownikowi na wpisanie niczego innego oprocz liczb naturalnych
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMaximum(999999);
		formatter.setAllowsInvalid(false);
		return formatter;
	}
	
	
}
