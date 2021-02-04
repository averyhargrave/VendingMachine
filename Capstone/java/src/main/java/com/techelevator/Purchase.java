package com.techelevator;

import java.util.Scanner;
import com.techelevator.view.Menu;

public class Purchase {

	private Menu purchaseMenu;
	private double currentMoney;
	
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY         = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT     = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Fnish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
													   	PURCHASE_MENU_OPTION_SELECT_PRODUCT,
													    PURCHASE_MENU_OPTION_FINISH_TRANSACTION
													    };
	
	public Purchase() {
	}
	
	public double getCurrentMoney() {
		return currentMoney;
	}
	
	public void feedMoney() {
		boolean continueFeed = true;
		// initiate a scanner object to take user input
		System.out.println("Money remaining: " + currentMoney + "0");
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
		System.out.println("$" + currentMoney + "0 remaining");
		System.out.println("Would you like to feed more money? (Y/N)");
		String response = userInput.nextLine();
		
		if(response.equalsIgnoreCase("Y")) {
			continueFeed = true;
		}
			else if(response.equalsIgnoreCase("N")) {
			continueFeed = false;
		}
	} while(continueFeed);
	}
	
	public String dispenseItem(Item desiredItem) {
		
		return null;
	}
	
	public double giveChange() {
		
		return 0;
	}
	
	public String salesReport() {
		
		return null;
	}
	
	public void purchaseMenu() {

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case PURCHASE_MENU_OPTION_FEED_MONEY:
					feedMoney();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
					          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
			
				case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
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
