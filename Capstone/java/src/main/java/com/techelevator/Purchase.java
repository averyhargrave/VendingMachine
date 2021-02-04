package com.techelevator;

import java.util.Scanner;

public class Purchase {

	private double currentMoney;
	
	public Purchase() {
	}
	
	public double getCurrentMoney() {
		return currentMoney;
	}
	
	public void feedMoney() {
		boolean continueFeed = true;
		// initiate a scanner object to take user input
		do {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Please insert a $1, $2, $5, or $10 bill.");
		String moneyEntered = userInput.nextLine();
		
		switch(moneyEntered) {
		case("1.00"):
			currentMoney += Double.parseDouble(moneyEntered);
			break;
		case("2.00"):
			currentMoney += Double.parseDouble(moneyEntered);
			break;
		case("5.00"):
			currentMoney += Double.parseDouble(moneyEntered);
			break;
		case("10.00"):
			currentMoney += Double.parseDouble(moneyEntered);
			break;
		case("1"):
			currentMoney += Double.parseDouble(moneyEntered);
			break;
		case("2"):
			currentMoney += Double.parseDouble(moneyEntered);
			break;
		case("5"):
			currentMoney += Double.parseDouble(moneyEntered);
			break;
		case("10"):
			currentMoney += Double.parseDouble(moneyEntered);
			break;
		default:
			System.out.println("This machine only accepts $1, $2, $5, or $10.");
		}
		System.out.println("$" + currentMoney + " remaining");
		System.out.println("Would you like to feed more money? (Y/N)");
		String response = userInput.nextLine();
		
		if(response.equalsIgnoreCase("Y")) {
			continueFeed = true;
		if(response.equalsIgnoreCase("N")) {
			continueFeed = false;
		}
		}
	} while(!continueFeed);
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

	
}
