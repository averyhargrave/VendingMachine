package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Inventory {
//-----------------------------------------------------------INSTANCE VARIABLES-------------------------------------------------------------------------//
	private String location;
	private TreeMap<String, Item> itemInventoryMap;
//=====================================================================================================================================================//
//-----------------------------------------------------------GETTERS/SETTERS--------------------------------------------------------------------------//	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Map<String, Item> getItemInventoryMap() {
		return itemInventoryMap;
	}
	public void setItemInventoryMap(TreeMap<String, Item> itemInventoryMap) {
		this.itemInventoryMap = itemInventoryMap;
	}
//=======================================================================================================================================================//
//----------------------------------------------------------------CONSTRUCTORS--------------------------------------------------------------------------//	
	public Inventory() throws FileNotFoundException {
		this.itemInventoryMap = new TreeMap<String, Item>();
		loadFile();													// A new Inventory will read a file for it's information
	}
//======================================================================================================================================================//	
//-----------------------------------------------------------------ADDITIONAL MEMBER METHODS-----------------------------------------------------------//
	private void loadFile() throws FileNotFoundException {
		File inventoryFile = new File("./vendingmachine.csv"); 	 	// Define a File object for the file
		Scanner inputDataFile = new Scanner(inventoryFile);  		// Define a Scanner for the File object
		
		String location = "";										// Place holder for key in the map known as location
		String name 	= "";										// Place holder for the name of the item in the inventory
		double price 	= 0.0;										// Place holder for the price of the item in the inventory
		String category = "";										// Place holder for the category of the item in the inventory
		
		while(inputDataFile.hasNext()) {							// While our inventory file has data continue the loop
			String itemDetail = inputDataFile.nextLine();			// itemDetail contains the next line of the data file
			String[] itemAttributes = itemDetail.split("\\|");		// Split itemDetail based on the Pipe character and place it into our array called itemAttributes
			
			location = itemAttributes[0];							// The first element in our array is the location
			name 	 = itemAttributes[1];							// The next element is the item name
			price	 = Double.parseDouble(itemAttributes[2]);		// the third element is the item price which must be parsed as a double since it is currently a String
			category = itemAttributes[3];							// the item category is the final element
			
			Item anItem = new Item(category, name, price);			// After getting the 3 attributes build an Item object with our 3 argument constructor
			
			
			itemInventoryMap.put(location, anItem);					// put the new item object into the map based on the location
		}
		
		inputDataFile.close();										// close our file reader
	}	
}
