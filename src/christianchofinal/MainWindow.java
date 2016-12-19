package christianchofinal;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JList transactionList;
	private JPanel transactionPanel;
	
	public MainWindow () {
		
		super ("Money Cents");
		
		setLayout(new FlowLayout());
		setSize(480,852);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		refresh();
		
	}
	
	public void refresh(){
		
		getContentPane().removeAll();

		// Panel containing transaction history and options
		/****************************
		 * CREATE JCOMPONENTS BELOW
		 ***************************/
		
		transactionPanel = new JPanel(new GridBagLayout());
		transactionPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		transactionPanel.setSize(470,852);
		
		JLabel title = new JLabel("<html><h1>MONEY ¢ENTS</h1></html>");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel expectedlabel = new JLabel("<html><h3>Expected amount: </h3></html>");
		expectedlabel.setHorizontalAlignment(SwingConstants.RIGHT);
		String EXPECTED = String.valueOf(MoneyCents.expectedAmount);
		EXPECTED = "$ " + EXPECTED;
		if (EXPECTED.substring(EXPECTED.length() - 2).contains("."))
			EXPECTED += "0";
		JTextArea expectedtext = new JTextArea(EXPECTED);
		expectedtext.setPreferredSize(new Dimension(220,25));
		expectedtext.setEditable(false);
		expectedtext.setFont(new Font("SansSerif",Font.PLAIN,18));
		
		JLabel postedlabel = new JLabel("<html><h3>Posted amount: </h3></html>");
		postedlabel.setHorizontalAlignment(SwingConstants.RIGHT);
		String POSTED = String.valueOf(MoneyCents.postedAmount);
		POSTED = "$ " + POSTED;
		if (POSTED.substring(POSTED.length() - 2).contains("."))
			POSTED += "0";
		JTextArea postedtext = new JTextArea(POSTED);
		postedtext.setPreferredSize(new Dimension(220,25));
		postedtext.setEditable(false);
		postedtext.setFont(new Font("SansSerif",Font.PLAIN,18));
		
		JLabel editT = new JLabel("Double-click a transaction to edit it.");
		editT.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton addTransaction = new JButton("Add transaction");
		addTransaction.setPreferredSize(new Dimension(460,40));
		JButton deleteTransaction = new JButton("Delete transaction");
		deleteTransaction.setPreferredSize(new Dimension(460,40));
		JButton saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(220,40));
		JButton exitButton = new JButton("Exit");
		exitButton.setPreferredSize(new Dimension(220,40));
		
		for (int i = 0; i < MoneyCents.transactions.size(); i++) {
			MoneyCents.transactionsContent[i] = MoneyCents.transactions.get(i).displayFormat();
		}
		
		transactionList = new JList(MoneyCents.transactionsContent);
		transactionList.setVisibleRowCount(10);
		transactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/*********************************
		 * CREATE ACTION LISTENERS BELOW
		 ********************************/
		
		transactionList.addMouseListener(new JListHandler());

		addTransaction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddEditWindow tWindow = new AddEditWindow(null);
				tWindow.setVisible(true);
			}
		});
		
		deleteTransaction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MoneyCents.place = transactionList.getSelectedIndex();
				DeleteConfirm dWindow = new DeleteConfirm(MoneyCents.transactions.get(transactionList.getSelectedIndex()));
				dWindow.setVisible(true);
			}
		});
			
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Write ArrayList to file
			System.out.println("Writing all data to file...");
				try {
					MoneyCents.writer = new BufferedWriter(new FileWriter("default.csv\\"));
				MoneyCents.writer.write("Date,Amount,Description,Posted\n");
					for (int j = 0; j < MoneyCents.transactions.size(); j++) {
						MoneyCents.transactions.get(j).writeToFile();
					}
					JOptionPane.showMessageDialog(null, "Saved!");
					System.out.println("Saved data to file.");
					MoneyCents.writer.close();
				} catch (IOException exception) {
					System.out.println("Error: Can't save to file.");
					JOptionPane.showMessageDialog(null,"ERROR: Can't save file.");
				}
			}
		});
		
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		/***********************
		 * CREATE LAYOUT BELOW
		 ***********************/
			
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		transactionPanel.add(title,c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		transactionPanel.add(expectedlabel,c);
		c.gridx = 1;
		c.gridy = 1;
		transactionPanel.add(expectedtext,c);
		c.gridx = 0;
		c.gridy = 2;
		transactionPanel.add(postedlabel,c);
		c.gridx = 1;
		c.gridy = 2;
		transactionPanel.add(postedtext,c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		transactionPanel.add(new JScrollPane(transactionList),c);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		transactionPanel.add(editT,c);
		c.gridx = 0;
		c.gridy = 5;
		transactionPanel.add(addTransaction,c);
		c.gridx = 0;
		c.gridy = 6;
		c.fill = GridBagConstraints.HORIZONTAL;
		transactionPanel.add(deleteTransaction,c);
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		transactionPanel.add(saveButton,c);
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 1;
		transactionPanel.add(exitButton,c);
		
		add(transactionPanel);
		revalidate();
		repaint();
	}
}
