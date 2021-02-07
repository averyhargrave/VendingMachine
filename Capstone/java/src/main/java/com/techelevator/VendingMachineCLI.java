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
import java.util.Set;

/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*
*  It is the main process for the Vending Machine
*
*  THIS is where most, if not all, of your Vending Machine interactions should be coded
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code vending machine related code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu;         // Gain access to Menu class provided for the Capstone

public class VendingMachineCLI {

    // Main menu options defined as constants
	Menu menu = new Menu(System.in, System.out);
	
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT  = "Print Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT,
													    MAIN_MENU_OPTION_SALES_REPORT
													    };
	private Inventory itemInventory;
	private Purchase aPurchase;
	
	
	private Menu vendingMenu;              // Menu object to be used by an instance of this class
	
	public VendingMachineCLI(Menu menu) throws FileNotFoundException {  // Constructor - user will pas a menu for this class to use
		this.vendingMenu = menu;           // Make the Menu the user object passed, our Menu
		
		itemInventory = new Inventory();
		aPurchase = new Purchase(menu);
	}
	/**************************************************************************************************************************
	*  VendingMachineCLI main processing loop
	*  
	*  Display the main menu and process option chosen
	*
	*  It is invoked from the VendingMachineApp program
	*
	*
	*  THIS is where most, if not all, of your Vending Machine objects and interactions 
	*  should be coded
	*
	*  Methods should be defined following run() method and invoked from it
	 * @throws IOException 
	*
	***************************************************************************************************************************/

	
	public void run() throws IOException {

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayItems();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseItems();          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break; 
												// Exit switch statement
				case MAIN_MENU_OPTION_SALES_REPORT:
					salesReport();
					System.out.println("Now printing...");
					break;
			}	
		}
		return;                               // End method and return to caller
	}
/********************************************************************************************************
 * Methods used to perform processing
 ********************************************************************************************************/
	public void displayItems() {      // static attribute used as method is not associated with specific object instance
		for(Map.Entry<String, Item> item : itemInventory.getItemInventoryMap().entrySet()) {
			System.out.print(item.getKey() + ", ");
			System.out.print(item.getValue().getName() + ", $");
			System.out.print(String.format("%.2f", item.getValue().getPrice()) + ", ");
			System.out.print(item.getValue().getCategory() + ", ");
			System.out.println(item.getValue().getQuantity());
		}
	}
	
	public void purchaseItems() throws IOException {	 // static attribute used as method is not associated with specific object instance
		aPurchase.purchaseMenu();
	}
	
	public void salesReport() throws IOException {
		File salesReport = new File("./SalesReport.txt");
		
		FileWriter theFile = new FileWriter(salesReport, false);
		
		BufferedWriter aBufferedWriter= new BufferedWriter(theFile);
		PrintWriter aPrintWriter = new PrintWriter(aBufferedWriter);
	
		//get all the keys from the map
		Set<String> locations = itemInventory.getItemInventoryMap().keySet();
		String totalSalePrintout = "";
		//loop through the map using the keys to get the items
		
		double totalSale = 0;
		
		for (String location : locations) {
			
		Item anItem = aPurchase.getItemInventory().getItemInventoryMap().get(location);//get the item out of the map
		
		int quantitySold = (5 - anItem.getQuantity());
		
		if(quantitySold > 0) {
			totalSale += quantitySold * anItem.getPrice();
			totalSalePrintout = "Total Sales: $" + String.format("%.2f",totalSale);
		} else {
			totalSalePrintout = "Total Sales: $" + String.format("%.2f",totalSale);	
		}
		
		aPrintWriter.println(anItem.getName() + " | " + quantitySold); 
		
		}
		aPrintWriter.println(totalSalePrintout);
		aPrintWriter.close();
	
	}
	
	public void endMethodProcessing() { // static attribute used as method is not associated with specific object instance
		System.out.println("Thank you for using the Vendo-Matic 800");
		// Any processing that needs to be done before method ends
	}
}
