package com.techelevator;

public class Item {

	private String name; // actual name of item/brand
	private double price;
	private String category; // chips, candy, drink, gum
	private int quantity = 5;
	
	public String getName() {
		return name;
	}	
	public void setName(String name) {
		this.name = name;
	}	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getQuantity() {
		if(quantity < 0) {
			System.out.println("SOLD OUT");
			quantity = 0;
		}
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Item() {	
	}
	
	public Item(String category, String name, double price) {
		this.category = category;
		this.name = name;
		this.price = price;
	}

	public void printMessage() {
		//this.category = category;
		if(category.equalsIgnoreCase("Chip")) {
			System.out.println("Crunch Crunch, Yum!");
		}
		if(category.equalsIgnoreCase("Candy")) {
			System.out.println("Munch Munch, Yum!");
		}
		if(category.equalsIgnoreCase("Drink")) {
			System.out.println("Glug Glug, Yum!");
		}
		if(category.equalsIgnoreCase("Gum")) {
			System.out.println("Chew Chew, Yum!");
		}
		
	}
	
	@Override
	public String toString() {
		return name + ", $" + String.format("%.2f", price);
	}

}
