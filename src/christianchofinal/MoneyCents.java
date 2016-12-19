package christianchofinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MoneyCents {
	
	public static double expectedAmount = 0.0;
	public static double postedAmount = 0.0;
	
	public static ArrayList<Transaction> transactions;// = new ArrayList<Transaction>();
	public static String[] transactionsContent;// = new String[transactions.size()];
	
	public static BufferedWriter writer;
	public static BufferedReader reader;
	
	public static int place = 0; // Used to pass along through multiple classes to edit/add/delete transactions
	
	public static MainWindow mainwindow;
	public static Transaction tempTransaction = new Transaction();
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
				
		transactions = new ArrayList<Transaction>();
		
		while (true) {
		
			try {	
				
				reader = new BufferedReader (new FileReader("default.csv\\"));
				System.out.println("Successfully opened file stream to read.");

				// Populate ArrayList from file
				System.out.println("Populating ArrayList from file...");
				String tempString = reader.readLine();

				int i = 0;
				while ((tempString = reader.readLine()) != null){

					transactions.add(new Transaction(tempString));
					
					// As the ArrayList is being populated, the amounts are calculated
					expectedAmount += transactions.get(i).getamount();
					if (transactions.get(i).getposted()) {
						postedAmount += transactions.get(i).getamount();
					}
					i++;
				}

				System.out.println("Successfully calculated amounts.");
				break;
				
			} catch (FileNotFoundException e) {
				// If the program can't find the file, it will create a new one for the user.
				e.printStackTrace();
				System.out.println("Error: File not found. Creating file.");
				writer = new BufferedWriter(new FileWriter("default.csv\\"));
				break;

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("File I/O error. Program now terminating.");
				System.exit(0);
			}

		}
		
		transactionsContent = new String[transactions.size()];
		
		mainwindow = new MainWindow();
		mainwindow.setVisible(true);

		reader.close();
		
	}
}
