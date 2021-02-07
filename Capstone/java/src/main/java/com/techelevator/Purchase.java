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
	//-------------------------------------------------------INSTANCE VARIABLES-----------------------------------------------------------------------------------//

	private Menu purchaseMenu;
	private double currentMoney;
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY         = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT     = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
															PURCHASE_MENU_OPTION_SELECT_PRODUCT,
															PURCHASE_MENU_OPTION_FINISH_TRANSACTION
															};
	private Inventory itemInventory;
	//==========================================================================================================================================================//
	//----------------------------------------------------------GETTERS/SETTERS--------------------------------------------------------------------------------//	
	public Inventory getItemInventory() {
		return itemInventory;
	}
	public double getCurrentMoney() {
		return currentMoney;
	}
	//==========================================================================================================================================================//
	//------------------------------------------------------------CONSTRUCTORS----------------------------------------------------------------------------------//	
	public Purchase(Menu menu) throws FileNotFoundException {
		this.purchaseMenu = menu;
		itemInventory	  = new Inventory();
	}
	//==========================================================================================================================================================//
	//-----------------------------------------------------------MEMBER METHODS---------------------------------------------------------------------------------//	
	public void feedMoney() throws NumberFormatException, IOException {		//Method to take in money and keep track of it
		boolean continueFeed = true;										//Loop control variable
		System.out.println("Money remaining: " + String.format("%.2f", currentMoney)); //Print money currently in machine
		do {
			Scanner userInput = new Scanner(System.in);						//Define a scanner for user input
			System.out.println("Please insert a $1, $2, $5, or $10 bill.");	//Prompt user to insert money
			String moneyEntered = userInput.nextLine();						//Define variable to hold user input

			switch(moneyEntered) {											//Switch to add user input converted from a String to a double to current money total 
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
				System.out.println("This machine only accepts $1, $2, $5, or $10.");  //If user inputs amount that isn't accepted, print message. This sends the user back to the main menu.
			}
			System.out.println("$" + String.format("%.2f", currentMoney) + " entered."); //Print the current amount of money entered 
			System.out.println("Would you like to feed more money? (Y/N)");				 //Prompt the user to either insert more money or decline
			String response = userInput.nextLine();										 //Define variable to hold user's response 				 

			if(response.equalsIgnoreCase("Y")) {			//If the user answers Y or y, continueFeed will be true and the loop will continue
				continueFeed = true;						
			}
			else if(response.equalsIgnoreCase("N")) {		//If the user answers N or n, continueFeed will be false and the user goes back to purchase menu			
				continueFeed = false;
			}
			else {
				System.out.println("Not a valid choice, please enter Y or N."); //If an invalid answer is provided, continueFeed will be false and user goes back to purchase menu
				continueFeed = false;
			}
			auditEntry("FEED MONEY", Double.parseDouble(moneyEntered), currentMoney); //add "FEED MONEY", moneyEntered, and currentMoney as arguments to the auditEntry method
		} while(continueFeed);			//continue the do-loop while continueFeed is true


	}


	public String dispenseItem() throws IOException {
		double startingMoney = 0.0;			//Variable to hold amount of money before dispensing items
		double endingMoney = 0.0;			//Variable to hold amount leftover after dispensing items
		String nameLocation = "";			//Variable to hold name and location of the chosen item

		for(Map.Entry<String, Item> item : itemInventory.getItemInventoryMap().entrySet()) { //for-loop to iterate over values in inventory Map and print them out so the user can choose
			System.out.print(item.getKey() + ", ");		//Print the key of the item which is it's location 
			System.out.print(item.getValue().getName() + ", $");		//Print the name of the item 
			System.out.print(String.format("%.2f",item.getValue().getPrice()) + ", ");	//Print the formatted price 
			System.out.println(item.getValue().getQuantity() + " remaining");			//Print the quantity
			nameLocation = item.getValue().getName() + " " + item.getKey();				//Store the item's name and location inside nameLocation
		}	
		startingMoney = currentMoney;													//Store balance into startingMoney before dispensing

		System.out.println("\nPlease make a selection:");		//Prompt the user to select an item

		Scanner userInput = new Scanner(System.in);				//Define a Scanner for user input
		String itemChoice = userInput.nextLine();				//Store User input inside a String variable

		Item itemA = itemInventory.getItemInventoryMap().get(itemChoice.toUpperCase());		//Instantiate an item based on user's choice

		if (itemA == null) {												// if above returns null item print 'that doesn't exist'. user is taken back to the purchase menu
			System.out.println("That item doesn't exist.");					
		}
		else if(currentMoney < itemA.getPrice()) {							// if the amount of money inserted is less than the price of the item, inform the user. user is taken back to the purchase menu
			System.out.println("Not enough money!");
		}
		else if(itemA.getQuantity() == 0) {									// if there are no more of the chosen item left inform the customer and print their remaining total. user is taken back to the purchase menu
			System.out.println("SOLD OUT");
			System.out.println(itemA.toString() + ". You have $" + String.format("%.2f", currentMoney) + " left. " + itemA.getQuantity() + " remaining.");
		}
		else {
			currentMoney -= itemA.getPrice();		//if the user has enough money, subtract the price of the item from the current balance
			endingMoney = currentMoney;				//store balance after the transaction inside endingMoney 
			itemA.setQuantity(itemA.getQuantity() - 1); 	//decrease the item's quantity by one 
			itemA.printMessage();
			System.out.println(itemA.toString() + ". You have $" + String.format("%.2f", currentMoney) + " left. " + itemA.getQuantity() + " remaining."); //print remaining money and item quantity
		}

		auditEntry(nameLocation, startingMoney, endingMoney); // put the name and location of the purchased item, startingMoney, and ending money into auditEntry() as arguments and record them to log.txt


		return null;
	}


	public void giveChange() throws IOException {

		int quarters = 0; 								//initialize variables to hold quarters, dimes, nickels, and total change to be given back to the user
		int dimes = 0;
		int nickels = 0;
		int change = 0;

		auditEntry("GIVE CHANGE", currentMoney, 0.00); //record "GIVE CHANGE", current money as starting value, and 0.00 as ending for audit entry

		change = (int)(currentMoney * 100);			  //change to be given -- multiply the current balance to 100 to produce whole number and cast as an int. store in change 

		while(change > 0) {							  //as long as there is money, if the change to be given is more than 25cents
			if(change >= 25) {
				quarters++;							  //add one to the quarter counter	
				change -= 25;						  //subtract 25 from change to be given	
			}
			else if(change >= 10) {					  //if the total amount change to be given is less than 25 but greater than or equal to 10
				dimes++;							  //add one to the dimes counter 
				change -= 10;						  //subtract 10 from the total amount of change	
			}
			else if(change >= 5) {					  //if the total amount of change to be given is less than 10 but greater than or equal to 5
				nickels++;							  //add one to the nickels counter
				change -= 5;						  //subtract 5 from the change to be given
			}
		}										//print the user's change in quarters dimes and nickels
		System.out.println("Your change is " + (quarters + " quarter(s), " + dimes + " dime(s), and " + nickels + " nickel(s)"));
	}

	public void auditEntry(String transactionName, double startingMoney, double currentMoney) throws IOException {
		File auditReport = new File("./Log.txt"); 					//instantiate new file and define it's file path

		FileWriter theFile = new FileWriter(auditReport, true);		//File Writer that will append to the file

		BufferedWriter aBufferedWriter= new BufferedWriter(theFile);//BufferedWriter to use system.out methods
		PrintWriter aPrintWriter = new PrintWriter(aBufferedWriter);//PrintWriter to allow printing to the file 

		Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)); //Timestamp 
		
		//Writes to the file : timestamp, name of the transaction, balance before transaction, current balance
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
		System.out.println("Enjoy!");

	}
}
