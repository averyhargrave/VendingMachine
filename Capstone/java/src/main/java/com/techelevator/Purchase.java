package com.techelevator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Scanner;
import com.techelevator.view.Menu;

public class Purchase  {


	private Menu purchaseMenu;
	private double currentMoney;
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY         = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT     = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
													   	PURCHASE_MENU_OPTION_SELECT_PRODUCT,
													    PURCHASE_MENU_OPTION_FINISH_TRANSACTION
													    };
	private Inventory itemPrintOut;
	
//	File auditReport = new File("./Log.txt");
//	FileWriter theFile = new FileWriter(auditReport, true);
//	BufferedWriter aBufferedWriter= new BufferedWriter(theFile);
//	PrintWriter aPrintWriter = new PrintWriter(aBufferedWriter);
	
	
	public Purchase(Menu menu) throws FileNotFoundException {
			this.purchaseMenu = menu;
			itemPrintOut = new Inventory();
	}
	
	public double getCurrentMoney() {
		return currentMoney;
	}
	
	public void feedMoney() throws NumberFormatException, IOException {
		boolean continueFeed = true;
		System.out.println("Money remaining: " + String.format("%.2f", currentMoney));
		do {
			Scanner userInput = new Scanner(System.in);
			System.out.println("Please insert a $1, $2, $5, or $10 bill.");
			String moneyEntered = userInput.nextLine();

			switch(moneyEntered) {
			case("1.00"):
				currentMoney += Double.parseDouble(moneyEntered);
			break;
			case("1"):
				currentMoney += Double.parseDouble(moneyEntered);
			break;
			case("2.00"):
				currentMoney += Double.parseDouble(moneyEntered);
			break;
			case("2"):
				currentMoney += Double.parseDouble(moneyEntered);
			break;
			case("5.00"):
				currentMoney += Double.parseDouble(moneyEntered);
			break;
			case("5"):
				currentMoney += Double.parseDouble(moneyEntered);
			break;
			case("10.00"):
				currentMoney += Double.parseDouble(moneyEntered);
			break;
			case("10"):
				currentMoney += Double.parseDouble(moneyEntered);
			break;
			default:
				System.out.println("This machine only accepts $1, $2, $5, or $10.");
			}
			System.out.println("$" + String.format("%.2f", currentMoney) + " remaining");
			System.out.println("Would you like to feed more money? (Y/N)");
			String response = userInput.nextLine();

			if(response.equalsIgnoreCase("Y")) {
				continueFeed = true;
			}
			else if(response.equalsIgnoreCase("N")) {
				continueFeed = false;
			}
			else {
				System.out.println("Not a valid choice please enter Y or N.");
				continueFeed = false;
			}
			auditEntry("FEED MONEY", Double.parseDouble(moneyEntered), currentMoney);
		} while(continueFeed);
		
		
	}

	
	public String dispenseItem() throws IOException {
		double startingMoney = 0.0;
		double endingMoney = 0.0;
		String nameLocation = "";
		
		for(Map.Entry<String, Item> item : itemPrintOut.getSlot().entrySet()) {
			System.out.print(item.getKey() + ", ");
			System.out.print(item.getValue().getName() + ", $");
			System.out.print(String.format("%.2f",item.getValue().getPrice()) + ", ");
			System.out.println(item.getValue().getQuantity() + " remaining");
			nameLocation = item.getValue().getName() + " " + item.getKey();
		}
		startingMoney = currentMoney;
		
		System.out.println("\nPlease make a selection:");
		
		Scanner userInput = new Scanner(System.in);
		String itemChoice = userInput.nextLine();

		Item itemA = itemPrintOut.getSlot().get(itemChoice.toUpperCase());

		if (itemA == null) {												// if above returns null item doesnt exist
			System.out.println("That item doesn't exist.");
		}
		else if(currentMoney < itemA.getPrice()) {
			System.out.println("Not enough money!");
		}
		else {
			currentMoney -= itemA.getPrice();
			endingMoney = currentMoney;
			itemA.setQuantity(itemA.getQuantity() - 1);
			itemA.printMessage();
			System.out.println(itemA.toString() + ". You have $" + String.format("%.2f", currentMoney) + " left. " + itemA.getQuantity() + " remaining.");
		}
		
		auditEntry(nameLocation, startingMoney, endingMoney);
		
		
		return null;
	}
	
	
	
	public void giveChange() throws IOException {

		double quarter = 0.25;
		double nickel = 0.05;
		double dime = 0.10;
		auditEntry("GIVE CHANGE", currentMoney, 0.00);
		double changeDue = ((double)((int) Math.round((currentMoney)*100)) / 100.0);
		double modQuarters = ((double)((int) Math.round((changeDue % quarter)*100)) / 100.0);//potentially come back
		double modDimes = ((double)((int) Math.round((modQuarters % dime)*100)) / 100.0);
		double modNickels = ((double)((int) Math.round((modQuarters % nickel)*100)) / 100.0);

		int numQuarters = (int)((changeDue - modQuarters) / (quarter));
		int numDimes = (int)((modQuarters - modDimes) / (dime));
		int numNickels = (int)((modDimes - modNickels) / (nickel));

		String total = (numQuarters + " quarter(s), " + numDimes + " dime(s), and " + numNickels + " nickel(s)");
		
		System.out.println("Your change is " + total);
	}


	//public void auditEntry() throws IOException {
	public void auditEntry(String transactionName, double startingMoney, double currentMoney) throws IOException {
		File auditReport = new File("./Log.txt");
		
		FileWriter theFile = new FileWriter(auditReport, true);
		
		BufferedWriter aBufferedWriter= new BufferedWriter(theFile);
		PrintWriter aPrintWriter = new PrintWriter(aBufferedWriter);
		
		Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
	
		//aPrintWriter.println(timestampNow.toString()); 
		aPrintWriter.println(timestampNow.toString() + " " + transactionName + ": $" + String.format("%.2f",startingMoney) + " $" + String.format("%.2f",currentMoney)); 
		aPrintWriter.close();
	}
	
	
	
	public void purchaseMenu() throws IOException {

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case PURCHASE_MENU_OPTION_FEED_MONEY:
					feedMoney();              // invoke method to display items in Vending Machine
					
					break;                    // Exit switch statement
			
				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
					dispenseItem();			  // invoke method to purchase items from Vending Machine
					//auditEntry();
					break;                    // Exit switch statement
			
				case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
					giveChange();
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
			}	
		}
		return;                               // End method and return to caller
	}
	
	
	
	public void endMethodProcessing() {
		
	}
}
