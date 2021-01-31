package guiWaysOfPayment;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import guiShop.MainGUI;

public class CardGUI {
	
	private JPanel jPanel;
	private JFrame jFrame;
	private JFormattedTextField[] cardNumberForm;
	private JFormattedTextField cvvNumberForm;
	private JSpinner monthSpinner, yearSpinner;
	private MainGUI main;
	
	public JPanel getjPanel() {
		return jPanel;
	}
	
	public CardGUI(MainGUI main) {
		
		this.main = main;
		
		jFrame = new JFrame();
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle(main.getClient().getWayOfPayment().getName());
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jFrame.addWindowListener(new WindowClose());
		jFrame.setResizable(false);
		
		jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		
		JPanel normalPanel = new JPanel();
		JPanel innerPanel = new JPanel();

		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

		
		jPanel.add(Box.createVerticalGlue());
		innerPanel.add(createTitleForm());
		innerPanel.add(createCardForm());
		innerPanel.add(createSouthPanel());
		normalPanel.add(innerPanel);
		jPanel.add(normalPanel);
		jPanel.add(Box.createVerticalGlue());

		jFrame.add(jPanel);
		jFrame.pack();
		jFrame.setVisible(true);

		this.main.setButtonCursor(jPanel);
	
		
	}
	
	private JLabel createTitleForm() {

		
		
		JLabel titleJLabel = new JLabel("Wpisz dane karty kredytowej", SwingConstants.CENTER);
		titleJLabel.setFont(new Font(titleJLabel.getFont().getName(), Font.BOLD, 40));
		titleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleJLabel.setBorder(new EmptyBorder(5,10,20,10)); //top,left,bottom,right
		
		return titleJLabel;
	}
	
	private JPanel createCardForm() {
		
		JPanel cardForm = new JPanel();
		GridBagLayout gridLayout = new GridBagLayout();
		GridBagConstraints gridBag = new GridBagConstraints();
		cardForm.setLayout(gridLayout);
			
		JLabel cardNumberLabel = new JLabel("Numer karty: ");
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		cardForm.add(cardNumberLabel, gridBag);
		

		cardNumberForm = new JFormattedTextField[4];
		for(int i = 0;i<cardNumberForm.length;i++) {
			cardNumberForm[i] = new JFormattedTextField(onlyAllowNaturalNumbersUpTo4Digits());
			cardNumberForm[i].setColumns(4);
			gridBag.gridx = i+2;
			gridBag.gridy = 0;
			gridBag.gridwidth = 1;
			cardForm.add(cardNumberForm[i], gridBag);
		}

		JLabel cvvNumberLabel = new JLabel("Numer cvv: ");
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		cardForm.add(cvvNumberLabel, gridBag);
		
		cvvNumberForm = new JFormattedTextField(onlyAllowNaturalNumbersUpTo3Digits());
		cvvNumberForm.setColumns(4);
		gridBag.gridx = 2;
		gridBag.gridy = 1;
		gridBag.gridwidth = 1;
		cardForm.add(cvvNumberForm, gridBag);
		
		JLabel dateLabel = new JLabel("Data waznosci (MM/RR): ");
		gridBag.gridx = 1;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		cardForm.add(dateLabel, gridBag);
		
		String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		SpinnerListModel monthModel = new SpinnerListModel(months);
		monthSpinner = new JSpinner(monthModel);
		monthSpinner.setValue(String.format("0%d", Calendar.getInstance().get(Calendar.MONTH)+1));
		gridBag.gridx = 2;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		JComponent editor = monthSpinner.getEditor();
		((JSpinner.DefaultEditor)editor).getTextField().setHorizontalAlignment(JTextField.RIGHT); //NOTE: Ustawienie do prawej tekstu
		cardForm.add(monthSpinner, gridBag);
		
		final int CURRENTYEAR = Calendar.getInstance().get(Calendar.YEAR)%100;
		final int MAXYEAR = CURRENTYEAR + 6;
		
		SpinnerNumberModel yearModel = new SpinnerNumberModel(CURRENTYEAR, CURRENTYEAR, MAXYEAR, 1);
		yearSpinner = new JSpinner(yearModel);
		gridBag.gridx = 3;
		gridBag.gridy = 3;
		gridBag.gridwidth = 1;
		cardForm.add(yearSpinner, gridBag);

		//NOTE: Ustawienie identycznego rozmiaru
		Dimension d = yearSpinner.getPreferredSize();
		monthSpinner.setPreferredSize(d);
		
		return cardForm;
		
	}
	
	private JPanel createSouthPanel() {
		
		Dimension buttonSize = new Dimension(200,40);
		
		JPanel southPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(southPanel, BoxLayout.Y_AXIS);
		southPanel.setLayout(boxLayout);
		
		JPanel buttonPanel = new JPanel();
		
		JButton submitButton = new JButton("Zaplac");
		submitButton.setPreferredSize(buttonSize);
		submitButton.addActionListener(new SubmitCardInfo());
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(submitButton);
		
		JLabel helper = new JLabel("Poprawna przykladowa karta 4556 7375 8689 9855");
		helper.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		southPanel.add(buttonPanel);
		southPanel.add(helper);
		
		return southPanel;
	}
	

	
	public NumberFormatter onlyAllowNaturalNumbersUpTo4Digits()
	{
		// Nie pozwalam uzytkownikowi na wpisanie niczego innego oprocz liczb naturalnych
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMaximum(9999);
		formatter.setAllowsInvalid(false);
		return formatter;
	}
	
	public NumberFormatter onlyAllowNaturalNumbersUpTo3Digits()
	{
		// Nie pozwalam uzytkownikowi na wpisanie niczego innego oprocz liczb naturalnych
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMaximum(999);
		formatter.setAllowsInvalid(false);
		return formatter;
	}
	
	class WindowClose extends WindowAdapter{
		
		@Override
	    public void windowClosing(WindowEvent e) {

			main.getjFrame().setEnabled(true);
			main.getClient().setWayOfPayment(null);
			jFrame.dispose();
	
	    }
		
	}
	
	
	class SubmitCardInfo implements ActionListener
	{
	
		private String cardNumber = "", cvv = "", date = "";

		public void actionPerformed(ActionEvent event) {
			
			cardNumber = "";
			cvv = "";
			date = "";
			
			for(int i = 0;i<cardNumberForm.length;i++) {
				
				cardNumber += cardNumberForm[i].getText();
				
			}
			
			cvv = cvvNumberForm.getText();
			date = monthSpinner.getValue().toString() + "/" +  yearSpinner.getValue();
			
			if(!cardNumber.equals("") && !cvv.equals("") && !date.equals("") && cardNumber.length() == 16 && cvv.length() == 3) {
				
				if(main.getClient().getWayOfPayment().pay(main.getClient(), new ArrayList<String>(Arrays.asList(cardNumber, cvv, date)))) {
					
					JOptionPane.showMessageDialog(null,"Dokonano platnosci");
					
					main.changeLayoutToFinalTransaction();
					
				}else {
					JOptionPane.showMessageDialog(null,"Bledne dane");
				}
				
			}
			
		}
	}

}
