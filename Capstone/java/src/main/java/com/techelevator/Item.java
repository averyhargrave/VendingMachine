package com.techelevator;

public class Item {

	private String name; // actual name of item/brand
	private double price;
	private String category; // chips, candy, drink, gum
	
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
	
	public Item() {	
	}
	
	public Item(String category, String name, double price) {
		this.category = category;
		this.name = name;
		this.price = price;
	}

	public String printMessage(String category) {
		this.category = category;
		if(category.equalsIgnoreCase("Chip")) {
			return "Crunch Crunch, Yum!";
		}
		if(category.equalsIgnoreCase("Candy")) {
			return "Munch Munch, Yum!";
		}
		if(category.equalsIgnoreCase("Drink")) {
			return "Glug Glug, Yum!";
		}
		if(category.equalsIgnoreCase("Gum")) {
			return "Chew Chew, Yum!";
		}
		else return "";
	}
	
	@Override
	public String toString() {
		return name + ", $" + price;
	}

}
