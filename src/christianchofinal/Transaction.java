package christianchofinal;

import java.io.IOException;

public class Transaction {
	
	private String date;
	private double amount; // The amount of the transaction.
	private String description; // A description of the transaction for the user to fill in.
	private String deposit; // true if a deposit. false if a withdrawal.
	private boolean posted; // A posted transaction is one that has been fully processed by a bank.
	
	// Setters
	public void setdate (String s) { date = s; }
	public void setamount (double d) { amount = d; }
	public void setdescription (String s) { description = s; }
	public void setdeposit () {
		if (amount < 0) 
			deposit = "W";
		else if (amount > 0)
			deposit = "D";
		else
			deposit = "N";
	}
	public void setposted (boolean b) { posted = b; }
	
	// Getters
	public String getdate () { return date; }
	public double getamount () { return amount; }
	public String getdescription () { return description; }
	public String getdeposit () { return deposit; }
	public boolean getposted () { return posted; }

	// Default constructor
	public Transaction () {
		date = "00000000";
		amount = 0.0;
		description = "No description.";
		setdeposit();
		posted = true;
	}
	
	// Constructor based on an entire String from a line in CSV file
	public Transaction (String s) {
		String[] data = s.split(",");
		date = data[0];
		amount = Double.parseDouble(data[1]);
		description = data[2];
		setdeposit();
		posted = Boolean.parseBoolean(data[3]);
	}
	
	public void print () {
		System.out.print("\t" + date + "\t" + amount + "\t" + "Posted? ");
		if (posted)
			System.out.println("Yes");
		else
			System.out.println("No");
		System.out.println("\t" + description);
		System.out.println("------------------------------------------------------------");
	}
	
	// Copy another Transaction
	public void copyFrom(Transaction t){
		date = t.getdate();
		amount = t.getamount();
		description = t.getdescription();
		posted = t.getposted();
	}
	
	// Writes the transaction to the file and in order of ID
	public void writeToFile() throws IOException {

		try {
			
			MoneyCents.writer.write(
					date + "," +
					String.valueOf(amount) + "," +
					description + "," +
					String.valueOf(posted)
					);
			MoneyCents.writer.newLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Converts data into String with formatting to display on JScrollPane
	public String displayFormat() {
		
		String content = "";
		String temp = "";
		content += "<html><pre>Date: " + date + "	"; // Add date content

		// Formatting for amount
		// Change font color depending on whether the amount is a deposit or a withdrawal
		if (amount < 0)
			temp = "<font color=red>";
		else if (amount > 0)
			temp = "<font color=green>";
		content += temp + "$ " + String.valueOf(amount); // Add amount content
		// Look at the last two chars of the amount as String to check if it has a decimal
		// Formatting to show two 0's instead of one
		temp = String.valueOf(amount);
		temp = temp.substring(temp.length() - 2);
		if (temp.contains("."))
			temp = "0";
		else
			temp = "";
		content += temp  + "</font>	";
		if (String.valueOf(amount).length() < 5)
			content += "	";
			
		if (posted) // Add posted content
			content += "POSTED";
		else
			content += "UNPOSTED";
		
		content += "<br><i>" + description + "</i></pre></html>"; // Add description content
		
		return content;
	}
	
}
