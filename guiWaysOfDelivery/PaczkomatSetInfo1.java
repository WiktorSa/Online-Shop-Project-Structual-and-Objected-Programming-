package guiWaysOfDelivery;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import guiShop.MainGUI;
import waysofdelivery.Paczkomat;

public class PaczkomatSetInfo1
{

	private JPanel jPanel;
	private JTextField miasto;
	private JTextField paczkomatCode;
	private JButton confirmMiasto;
	private JButton confirmCode;
	private JButton goBack;
	private MainGUI main;
	private Paczkomat paczkomat;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public PaczkomatSetInfo1(MainGUI main) 
	{
		this.main = main;
		paczkomat=(Paczkomat)main.getClient().getWaysOfDelivery();
		// Tekst bedzie sie wyswietlal od gory do dolu
		jPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie (musi byc na poczatku i koncu)
		
		JLabel giveInstructionsJLabel = new JLabel("Podaj informacje do wysylki paczkomatem.", SwingConstants.CENTER);
		giveInstructionsJLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		giveInstructionsJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(giveInstructionsJLabel);
		
		JLabel miastoJLabel = new JLabel("Podaj swoje miasto a znajdziemy paczkomaty blisko Ciebie.", SwingConstants.CENTER);
		miastoJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		miastoJLabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(miastoJLabel);
		
		miasto = new JTextField(); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		miasto.setAlignmentX(Component.CENTER_ALIGNMENT);
		miasto.setHorizontalAlignment(JTextField.CENTER);
		miasto.setPreferredSize(new Dimension(300, 25));
		miasto.setMaximumSize(new Dimension(300, 25));
		jPanel.add(miasto);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		confirmMiasto = new JButton("Potwierdz");
		confirmMiasto.addActionListener(new ConfirmMiasto());
		confirmMiasto.setAlignmentX(Component.CENTER_ALIGNMENT);
		jPanel.add(confirmMiasto);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		JLabel kodJlabel = new JLabel("Podaj kod paczkomatu.", SwingConstants.CENTER);
		kodJlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		kodJlabel.setBorder(new EmptyBorder(15, 0, 5, 0));
		jPanel.add(kodJlabel);
		
		paczkomatCode = new JTextField(); // Zeby uzytkownik nie musial ponownie wpisywac swoich danych
		paczkomatCode.setAlignmentX(Component.CENTER_ALIGNMENT);
		paczkomatCode.setHorizontalAlignment(JTextField.CENTER);
		paczkomatCode.setPreferredSize(new Dimension(300, 25));
		paczkomatCode.setMaximumSize(new Dimension(300, 25));
		jPanel.add(paczkomatCode);
		
		jPanel.add(Box.createRigidArea(new Dimension(0,7)));
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		goBack = new JButton(new ImageIcon("Ikony/goBack.png"));
		goBack.addActionListener(new GoBack());
		goBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(goBack);
		
		confirmCode = new JButton(new ImageIcon("Ikony/forward.png"));
		confirmCode.addActionListener(new ConfirmCode());
		confirmCode.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(confirmCode);

		jPanel.add(buttonPanel);
		
		
		//jPanel.add(Box.createRigidArea(new Dimension(0,20)));
		jPanel.add(Box.createVerticalGlue());//NOTE: Centrowanie 
		
	
		this.main.setButtonCursor(jPanel);

	}
	
	class ConfirmCode implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String code = paczkomatCode.getText();
			
			if (paczkomat.isCorrectCode(code))
			{
				paczkomat.setPaczkomatCode(code);
				if(JOptionPane.showConfirmDialog(null, "Czy to twoje dane do dostawy: " + paczkomat.toString(), "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					main.getClient().setWayOfDelivery(paczkomat);
					
					main.changeLayoutToWaysOfPaymentSelectingCategory();
				}
			}
			
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Niepoprawne dane. Wprowadz poprawne dane");
			}
		}
	}
	
	class ConfirmMiasto implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			String miast = miasto.getText();
			
			if (paczkomat.isCorrectMiasto(miast)) {
				if(JOptionPane.showConfirmDialog(null, "Czy to na pewno jest twoje miasto? " + miast, "Potwierdz dane", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					main.getClient().setWayOfDelivery(paczkomat);
					paczkomat.setMiasto(miast);
					
					main.changeLayoutToPaczkomat2(paczkomat);
				}
			}
			
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Niepoprawne dane. Wprowadz poprawne dane");
			}
		}
	}	
	class GoBack implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			main.changeLayoutToWaysOfDeliverySelectingCategory();
		}
	}
}
