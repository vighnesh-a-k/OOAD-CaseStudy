package com.ilp.enitity;
import java.util.ArrayList;


abstract public class Product {
	
	public Product(String productCode, String productName, ArrayList<Service> serviceList) {
		
		this.productCode = productCode;
		this.productName = productName;
		this.serviceList = serviceList;
	}
	private String productCode;
	private String productName;
	private ArrayList<Service> serviceList = new ArrayList<Service>();
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public ArrayList<Service> getServiceList() {
		return serviceList;
	}
	public void setCars(ArrayList<Service> serviceList) {
		this.serviceList = serviceList;
	}
	public String toString() {
        return "Product:" +
               "productCode='" + productCode + '\'' +
               ", productName='" + productName + '\'' ;
    }
	
}
