package gui.delivery;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gui.shop.MainGUI;
import waysofdelivery.Delivery;
import waysofdelivery.DeliveryMan;
import waysofdelivery.Personal;
import waysofdelivery.ParcelLocker;

public class DeliveryGUI {
	private MainGUI main;
	private JPanel jPanel;
	private JButton confirmCity;
	private JButton confirmButton;
	private JButton goBackButton;
	private ArrayList<JTextField> jTextFields = new ArrayList<JTextField>();
	private JTextField city;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public DeliveryGUI(MainGUI main) {
		this.main = main;
		
		if (this.main.getClient().getWaysOfDelivery() instanceof DeliveryMan) {
			jPanel = new JPanel();
			BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
			jPanel.setLayout(boxLayout);
			jPanel.add(Box.createVerticalGlue());
			
			JLabel titleJLabel = new JLabel("Wpisz dane potrzebne do dostawy kurierskiej", SwingConstants.CENTER);
			titleJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
			titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			titleJLabel.setBorder(new EmptyBorder(5,10,15,10));
			jPanel.add(titleJLabel);
			
			JLabel cityJLabel = new JLabel("Wpisz swoje miasto", SwingConstants.CENTER);
			cityJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			cityJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
			jPanel.add(cityJLabel);
			
			JTextField cityJTextField = new JTextField(((DeliveryMan) this.main.getClient().getWaysOfDelivery()).getMiasto()); 
			cityJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
			cityJTextField.setHorizontalAlignment(JTextField.CENTER);
			cityJTextField.setMaximumSize(new Dimension(200, 60));
			jTextFields.add(cityJTextField);
			jPanel.add(cityJTextField);
			
			JLabel streetJLabel = new JLabel("Wpisz swoja ulica z numerem domu/mieszkania", SwingConstants.CENTER);
			streetJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			streetJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
			jPanel.add(streetJLabel);
			
			JTextField streetJTextField = new JTextField(((DeliveryMan) this.main.getClient().getWaysOfDelivery()).getUlica());
			streetJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
			streetJTextField.setHorizontalAlignment(JTextField.CENTER);
			streetJTextField.setMaximumSize(new Dimension(200, 60));
			jTextFields.add(streetJTextField);
			jPanel.add(streetJTextField);
			
			JLabel emaiJLabel = new JLabel("Wpisz swoj kod pocztowy (bez myslnika)", SwingConstants.CENTER);
			emaiJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			emaiJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
			jPanel.add(emaiJLabel);
			
			JTextField postcodeJTextField = new JTextField(((DeliveryMan) this.main.getClient().getWaysOfDelivery()).getKodPocztowy());
			postcodeJTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
			postcodeJTextField.setHorizontalAlignment(JTextField.CENTER);
			postcodeJTextField.setMaximumSize(new Dimension(200, 60));
			jTextFields.add(postcodeJTextField);
			jPanel.add(postcodeJTextField);
			
			jPanel.add(Box.createRigidArea(new Dimension(5, 15)));
			
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
			goBackButton.addActionListener(new GoBack());
			goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(goBackButton);
			
			confirmButton = new JButton(new ImageIcon("Ikony/forward.png"));
			confirmButton.addActionListener(new ConfirmData());
			confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(confirmButton);

			jPanel.add(buttonPanel);
			
			jPanel.add(Box.createVerticalGlue());
			this.main.setButtonCursor(jPanel);
		}
		
		else if(this.main.getClient().getWaysOfDelivery() instanceof Personal) {
			((Personal) this.main.getClient().getWaysOfDelivery()).setTomDt();
			
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			jPanel.add(Box.createVerticalGlue());
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 7;
			gbc.gridheight = 3;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.insets = new Insets(5, 5, 5, 5);
			
			
			JLabel titileJLabel = new JLabel("Odbior osobisty bedzie dostepny od:");
			titileJLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
			titileJLabel.setFont(new Font("New Times Roman", Font.BOLD, 40));
			jPanel.add(titileJLabel, gbc);
			
			gbc.gridwidth = 5;
			gbc.gridy = 3;
			
			gbc.gridwidth = 7;
			
			JLabel dataJLabel = new JLabel(((Personal) this.main.getClient().getWaysOfDelivery()).getDt() + " do konca dnia nastepnego w godzinach 8-20");
			dataJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
			dataJLabel.setFont(new Font("New Times Roman", Font.BOLD, 24));
			jPanel.add(dataJLabel, gbc);
			
			gbc.gridy += 3;
			gbc.gridheight = 1;
			
			JLabel adresJLabel = new JLabel("Adres sklepu: Wroclaw  ul. Teczowa 3");
			adresJLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
			adresJLabel.setFont(new Font("New Times Roman", Font.BOLD, 24));
			jPanel.add(adresJLabel, gbc);
			
			gbc.gridy += 3;
			gbc.gridheight = 1;
			
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
			goBackButton.addActionListener(new GoBack());
			buttonPanel.add(goBackButton);
			
			confirmButton = new JButton(new ImageIcon("Ikony/forward.png"));
			confirmButton.addActionListener(new ConfirmData());
			buttonPanel.add(confirmButton);
			

			jPanel.add(buttonPanel,gbc);
			
			jPanel.add(Box.createVerticalGlue());
			this.main.setButtonCursor(jPanel);
		}
		
		else if (this.main.getClient().getWaysOfDelivery() instanceof ParcelLocker) {
			jPanel = new JPanel();
			BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
			jPanel.setLayout(boxLayout);
			jPanel.add(Box.createVerticalGlue());
			
			JLabel giveInstructionsJLabel = new JLabel("Podaj informacje do wysylki paczkomatem.", SwingConstants.CENTER);
			giveInstructionsJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
			giveInstructionsJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(giveInstructionsJLabel);
			
			JLabel miastoJLabel = new JLabel("Podaj swoje miasto a znajdziemy paczkomaty blisko Ciebie.", SwingConstants.CENTER);
			miastoJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			miastoJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
			jPanel.add(miastoJLabel);
			
			city = new JTextField(); 
			city.setAlignmentX(Component.CENTER_ALIGNMENT);
			city.setHorizontalAlignment(JTextField.CENTER);
			city.setPreferredSize(new Dimension(300, 25));
			city.setMaximumSize(new Dimension(300, 25));
			jPanel.add(city);
			
			jPanel.add(Box.createRigidArea(new Dimension(0,7)));
			
			confirmCity = new JButton("Potwierdz");
			confirmCity.addActionListener(new ConfirmCity());
			confirmCity.setAlignmentX(Component.CENTER_ALIGNMENT);
			jPanel.add(confirmCity);
			
			jPanel.add(Box.createRigidArea(new Dimension(0,7)));
			
			JLabel kodJlabel = new JLabel("Podaj kod paczkomatu.", SwingConstants.CENTER);
			kodJlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			kodJlabel.setBorder(new EmptyBorder(15, 0, 5, 0));
			jPanel.add(kodJlabel);
			
			JTextField paczkomatCode = new JTextField(); 
			paczkomatCode.setAlignmentX(Component.CENTER_ALIGNMENT);
			paczkomatCode.setHorizontalAlignment(JTextField.CENTER);
			paczkomatCode.setPreferredSize(new Dimension(300, 25));
			paczkomatCode.setMaximumSize(new Dimension(300, 25));
			jTextFields.add(paczkomatCode);
			jPanel.add(paczkomatCode);
			
			jPanel.add(Box.createRigidArea(new Dimension(0,7)));
			
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			goBackButton = new JButton(new ImageIcon("Ikony/goBack.png"));
			goBackButton.addActionListener(new GoBack());
			goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(goBackButton);
			
			confirmButton = new JButton(new ImageIcon("Ikony/forward.png"));
			confirmButton.addActionListener(new ConfirmData());
			confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(confirmButton);

			jPanel.add(buttonPanel);
			jPanel.add(Box.createVerticalGlue());
			
			this.main.setButtonCursor(jPanel);
		}
	}
	
	class ConfirmData implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			ArrayList<String> deliveryInfo = new ArrayList<String>();
			for (JTextField jTextField : jTextFields){
				deliveryInfo.add(jTextField.getText());
			}
			
			if (((Delivery) main.getClient().getWaysOfDelivery()).isCorrectData(deliveryInfo)) {
				((Delivery) main.getClient().getWaysOfDelivery()).setDeliveryInfo(deliveryInfo);
				
				if(JOptionPane.showConfirmDialog(null, "Czy to twoje dane do dostawy: " + main.getClient().getWaysOfDelivery().toString(), "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					main.changeLayoutToPaymentSelectingCategory();
				}
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Niepoprawne dane");
			}
		}
	}
	
	class GoBack implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			main.changeLayoutToDeliverySelectingCategory();
		}
	}
	
	class ConfirmCity implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String miast = city.getText();
			if (((ParcelLocker) main.getClient().getWaysOfDelivery()).isCorrectMiasto(miast)) {
				if(JOptionPane.showConfirmDialog(null, "Czy to na pewno jest twoje miasto? " + miast, "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					((ParcelLocker) main.getClient().getWaysOfDelivery()).setMiasto(miast);
					main.changeLayoutToParcelLocker();
				}
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Niepoprawne dane. Wprowadz poprawne dane");
			}
		}
	}
}
