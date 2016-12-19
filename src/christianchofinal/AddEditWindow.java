package christianchofinal;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class AddEditWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**************************************
	 * JCOMPONENTS START HERE
	 *************************************/

	private JLabel datelabel;
	private JLabel amountlabel;
	
	private	JTextArea datetext;
	private	JTextArea amounttext;
	private	JTextArea descriptiontext;
	
	private	JRadioButton depositrb;
	private	JRadioButton withdrawalrb;
	private	JCheckBox postedcheck;
	private	JButton okay;
	private	JButton cancel;
	
	// This constructor takes a Transaction as a parameter so that it can also edit recorded transactions
	public AddEditWindow(Transaction t) {
		
		super ("Add/Edit Transaction");

		final Transaction passedT = new Transaction();
		if (t != null)
			passedT.copyFrom(t);
		final boolean newTransaction;
		
		if (t == null)
			newTransaction = true;
		else
			newTransaction = false;
		
		setLayout(new FlowLayout());
		setSize(480,200);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel addEditPanel = new JPanel(new GridBagLayout());
		addEditPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		addEditPanel.setSize(480,200);
	
		datelabel = new JLabel("Date: ");
		datelabel.setHorizontalAlignment(SwingConstants.RIGHT);
		amountlabel = new JLabel ("Amount: ");
		amountlabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		okay = new JButton("Okay");
		cancel = new JButton("Cancel");		
		
		// A null Transaction will populate the window with the default text
		
		if (t == null) {
			datetext = new JTextArea("");
			amounttext = new JTextArea("");
			descriptiontext = new JTextArea("Description");
			depositrb = new JRadioButton("Deposit");
			withdrawalrb = new JRadioButton("Withdrawal");
			postedcheck = new JCheckBox("Posted?",false);
		}
		// If a Transaction was passed, it would populate with the Transactions data
		else {
			datetext = new JTextArea(t.getdate());
			String temp = String.valueOf(Math.abs(t.getamount()));
			if (temp.substring(temp.length() - 2).contains("."))
				temp += "0";
			amounttext = new JTextArea(temp);
			descriptiontext = new JTextArea(t.getdescription());
			
			if (t.getamount() > 0) {
				depositrb = new JRadioButton("Deposit",true);
				withdrawalrb = new JRadioButton("Withdrawal",false);
			}
			else {
				depositrb = new JRadioButton("Deposit",false);
				withdrawalrb = new JRadioButton("Withdrawal",true);
			}
				
			if (t.getposted())
				postedcheck = new JCheckBox("Posted?",true);
			else
				postedcheck = new JCheckBox("Posted?",false);
		}
		
		ButtonGroup WDgroup = new ButtonGroup();
		WDgroup.add(depositrb);
		WDgroup.add(withdrawalrb);
		
		datelabel.setPreferredSize(new Dimension(220,17));
		amountlabel.setPreferredSize(new Dimension(220,17));
		
		datetext.setPreferredSize(new Dimension(220,17));
		amounttext.setPreferredSize(new Dimension(220,17));
		descriptiontext.setPreferredSize(new Dimension(220,17));
		depositrb.setPreferredSize(new Dimension(120,17));
		withdrawalrb.setPreferredSize(new Dimension(50,17));
		
		postedcheck.setPreferredSize(new Dimension(50,17));
		postedcheck.setHorizontalAlignment(SwingConstants.RIGHT);
		
		okay.setPreferredSize(new Dimension(220,25));
		cancel.setPreferredSize(new Dimension(220,25));
		
		/**************************************
		 * FORMATTING STARTS HERE
		 *************************************/
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		addEditPanel.add(datelabel,c);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 2;
		addEditPanel.add(datetext, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		addEditPanel.add(amountlabel,c);
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 2;
		addEditPanel.add(amounttext, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		addEditPanel.add(descriptiontext, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		addEditPanel.add(depositrb, c);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		addEditPanel.add(withdrawalrb, c);
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 2;
		addEditPanel.add(postedcheck, c);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		addEditPanel.add(okay, c);
		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 2;
		addEditPanel.add(cancel, c);
		
		descriptiontext.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
				descriptiontext.setText("");
			}
			public void focusLost(FocusEvent e) {
			}
		});
		
		okay.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
			
				// Check for format of all text
				boolean formatproblem = false;
				
				// Check date format
				SimpleDateFormat dateformat = new SimpleDateFormat("YYYY/MM/dd");
				try {
					dateformat.parse(datetext.getText());
				} catch (ParseException err) {
					formatproblem = true;
					JOptionPane.showMessageDialog(null,"Date must be in format YYYY/MM/DD. Example: 2013/05/12");
				}
				
				// Check amount format
				try {
					Double.parseDouble(amounttext.getText());
					if (Double.parseDouble(amounttext.getText()) <= 0){
						JOptionPane.showMessageDialog(null,"Amount must be positive.");
						formatproblem = true;
					}
				} catch (NumberFormatException err){
					JOptionPane.showMessageDialog(null,"Enter a valid number.");
					formatproblem = true;
				}
				
				// Check for deposit/withdrawal selection
				if (!depositrb.isSelected() && !withdrawalrb.isSelected()){
					JOptionPane.showMessageDialog(null,"Please select \"Deposit\" or \"Withdrawal\".");
					formatproblem = true;
				}
				
				if (!formatproblem){
					String s = "";
					s += datetext.getText() + ",";
					if (depositrb.isSelected())
						s += amounttext.getText() + ",";
					else
						s += "-" + amounttext.getText() + ",";
					s += descriptiontext.getText() + ",";
					if (postedcheck.isSelected())
						s += "TRUE";
					else
						s += "FALSE";
				
					Transaction tempTransaction = new Transaction(s);
				
					// Delete the original if it's being edited
					if (!newTransaction){
						if (passedT.getposted())
							MoneyCents.postedAmount -= passedT.getamount();
						MoneyCents.expectedAmount -= passedT.getamount();
						MoneyCents.transactions.remove(MoneyCents.place);
					}
					// Add a new transaction or add the edited version of an old one
					MoneyCents.place = 0;
					while (!MoneyCents.transactions.isEmpty()) {
						if (Integer.parseInt(MoneyCents.transactions.get(MoneyCents.place).getdate().substring(0,4))
								<= Integer.parseInt(tempTransaction.getdate().substring(0,4))){ // Check against year
							if (Integer.parseInt(MoneyCents.transactions.get(MoneyCents.place).getdate().substring(5,7))
									<= Integer.parseInt(tempTransaction.getdate().substring(5,7))){ // Check against month
								if (Integer.parseInt(MoneyCents.transactions.get(MoneyCents.place).getdate().substring(8))
										<= Integer.parseInt(tempTransaction.getdate().substring(8))){ // Check against day
									break;
								}
							}
						}
						MoneyCents.place++;
					}
					MoneyCents.transactions.add(MoneyCents.place,tempTransaction);
					System.out.println("Added item:");
					tempTransaction.print();
					
					MoneyCents.transactionsContent = new String[MoneyCents.transactions.size()];
					for (int i = 0; i < MoneyCents.transactions.size(); i++) {
						MoneyCents.transactionsContent[i] = MoneyCents.transactions.get(i).displayFormat();
					}
					
					if (tempTransaction.getposted())
						MoneyCents.postedAmount += tempTransaction.getamount();
					MoneyCents.expectedAmount += tempTransaction.getamount();
					MoneyCents.mainwindow.refresh();
					dispose();

				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		add(addEditPanel);
	}
}
