package com.ilp.enitity;

public class Service {
	private String serviceCode;
	private String serviceName;
	private double rate;
	
	public Service(String serviceCode, String serviceName, double rate) {
		
		this.serviceCode = serviceCode;
		this.serviceName = serviceName;
		this.rate = rate;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public String toString() {
	       
	        return this.serviceCode+"\t\t"+this.serviceName+"\t\t"+this.rate ;
	    }

}
