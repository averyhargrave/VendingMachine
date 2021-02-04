package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Inventory {

	private String location;
	private int quantity = 5;
	private TreeMap<String, Item> slot;

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Map<String, Item> getSlot() {
		return slot;
	}
	public void setSlot(TreeMap<String, Item> slot) {
		this.slot = slot;
	}
	
	public Inventory() throws FileNotFoundException {
		quantity = 5;
		this.slot = new TreeMap<String, Item>();
		loadFile();
	}
	
	private void loadFile() throws FileNotFoundException {
		File inventoryFile = new File("./vendingmachine.csv"); // Define a File object for the file
		Scanner inputDataFile = new Scanner(inventoryFile);  // Define a Scanner for the File object
		
		String location = "";
		String name = "";
		double price = 0.0;
		String category = "";
		
		while(inputDataFile.hasNext()) {
			String itemDetail = inputDataFile.nextLine();
			String[] itemAttributes = itemDetail.split("\\|");
			
			location = itemAttributes[0];
			name = itemAttributes[1];
			price = Double.parseDouble(itemAttributes[2]);
			category = itemAttributes[3];
			
			Item anItem = new Item(category, name, price);
			
			slot.put(location, anItem);
		}
		
		inputDataFile.close();
	}	
	
	
		
	
}
