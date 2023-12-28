package com.ilp.enitity;

import java.util.ArrayList;

public class SavingsMaxAccount extends Product {
	
	public SavingsMaxAccount(String productCode, String productName,int minimumBalance, ArrayList<Service> serviceList) {
		super(productCode, productName, serviceList);
		this.minimumBalance=minimumBalance;
		// TODO Auto-generated constructor stub
	}

	private int minimumBalance=1000;

	public int getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(int minimumBalance) {
		this.minimumBalance = minimumBalance;
	}
	

}
