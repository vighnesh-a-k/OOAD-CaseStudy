package com.ilp.service;
 
import com.ilp.enitity.*;
import com.ilp.service.*;
import java.util.ArrayList;
import java.util.Scanner;
 
public class BankService {
 
    static Scanner scanner = new Scanner(System.in);
 
    // Function to create a new Service
    public static Service createService(ArrayList<Service> serviceList) {
        System.out.println("Enter service name:");
        String serviceName = scanner.nextLine();
        System.out.println("Enter service code:");
        String serviceCode = scanner.nextLine();
        while(true) {
        if(checkDuplicateService(serviceCode,serviceList)) {
        	  break;
        	  }
        System.out.println("Enter service code:");
        serviceCode = scanner.nextLine();

        }
        System.out.println("Enter service rate:");
        double rate = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character after reading the double
 
        // Creating a new Service object with the entered details
        Service service = new Service(serviceCode, serviceName, rate);
 
        return service;
    }
 
    // Function to create a new Product based on the type
    public static Product createProduct(String type, ArrayList<Service> mainServiceList) {
        Product product;
        ArrayList<Service> serviceList = new ArrayList<Service>();
        linkServices(mainServiceList, serviceList);
 
        if (type.equals("SavingsMax")) {
            String productCode = "101";
            String productName = "SavingsMax";
            int minimumBalance = 1000;
            product = new SavingsMaxAccount(productCode, productName, minimumBalance, serviceList);
        } else if (type.equals("Loan")) {
            String productCode = "102";
            String productName = "Loan";
            product = new LoanAccount(productCode, productName, serviceList);
        } else {
            String productCode = "103";
            String productName = "Current";
            product = new CurrentAccount(productCode, productName, serviceList);
        }
        return product;
    }
 
    // Function to link services to a product
    public static void linkServices(ArrayList<Service> mainServiceList, ArrayList<Service> serviceList) {
        String menuChoice = "Y";
        String serviceChoice;
        Service currentService = null;
        ArrayList<Service> localMainServiceList = (ArrayList<Service>) mainServiceList.clone();
 
        do {
            for (Service service : localMainServiceList) {
                System.out.println(service.toString());
            }
            System.out.println("Enter the service Code To Link To the Product:");
            serviceChoice = scanner.nextLine();
 
            for (Service service : localMainServiceList) {
                if (serviceChoice.equals(service.getServiceCode())) {
                    currentService = service;
                    serviceList.add(currentService);
                }
            }
 
            if (currentService != null)
                localMainServiceList.remove(currentService);
            if(localMainServiceList.isEmpty())
            	return;
            System.out.println("Enter Y to continue linking services");
            menuChoice = scanner.nextLine();
        } while (menuChoice.equals("Y"));
    }
 
    // Function to create an account based on the selected product
    public static void createAccount(ArrayList<Product> productList, ArrayList<Account> accountList) {
    	 int accountCount = accountList.size();
        do {
        	accountCount = accountList.size();
        	if(productList.size()==0) {
        		System.out.println("not possible to create more accounts");
        		return;
        	}
            System.out.println("Create Account:");
 
            for (int i = 0; i < productList.size(); i++) {
                System.out.println((i + 1) + ". " + productList.get(i).getProductName());
            }
 
            System.out.println("Enter the product no to select:");
            int productNumber = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume the newline character
 
            // Check if the selected product number is valid
            if (productNumber >= 0 && productNumber <= productList.size()) {
                Product selectedProduct = productList.get(productNumber);
                productList.remove(productNumber);
 
                // Use the selected product to create an account
                double balance = setbalance(selectedProduct);
                accountList.add(new Account(generateAccNo(accountCount), selectedProduct.getProductName(), balance,
                        selectedProduct));
 
            } else {
                System.out.println("Invalid product number. Please try again.");
                continue;
            }
            System.out.println("Add a new Account press Y");
        } while (scanner.nextLine().equals("Y"));
    }
 
    // Function to set the initial balance based on the product type
    public static double setbalance(Product product) {
        while (true) {
            System.out.println("Enter the initial amount");
            double initialAmount = scanner.nextDouble();
            scanner.nextLine();
 
            if (product instanceof SavingsMaxAccount) {
                if (initialAmount > 1000)
                    return initialAmount;
            } else {
                return initialAmount;
            }
        }
    }
 
    // Function to create a new customer
    public static Customer createCustomer(ArrayList<Product> productList, ArrayList<Account> accountList) {
        createAccount(productList, accountList);
 
        System.out.println("Enter customer code:");
        String customerCode = scanner.nextLine();
 
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();
 
        Customer newCustomer = new Customer(customerCode, customerName, accountList);
        return newCustomer;
    }
 
    // Function to generate an account number
    public static String generateAccNo(int counter) {
        return "ACC" + counter;
    }
 
    // Function to display customer information
    public static void displayCustomer(Customer customer) {
    	if(customer==null) {
    		System.out.println("Create Customer First");
    		return;
    	}
        System.out.println("Customer Information:");
        System.out.println("Name: " + customer.getCustomerName());
        System.out.println("ID: " + customer.getCustomerCode());
 
        System.out.println("Accounts:");
 
        for (Account account : customer.getAccountList()) {
            System.out.println(account.toString());
            for(Service service:account.getProduct().getServiceList())
            System.out.println(service.toString());
        }
    }
 
    // Function to manage account actions (deposit, withdraw, display balance)
    public static void manageAccounts(Customer customer) {
    	if(customer==null) {
    		   System.out.println("customer not created");
    		   return;
    	}
        Account account = chooseAccount(customer);
        char menuChoice = 'y';
 
        while (menuChoice == 'y' || menuChoice == 'Y') {
            System.out.println("\n1. Deposit\n2. Withdraw\n3. Display Balance\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
 
            if (account == null) {
                break;
            }
 
            switch (choice) {
                case 1:
                    System.out.print("Enter the amount to be deposited: ");
                    double depositAmount = scanner.nextDouble();
                    deposit(account, depositAmount);
                    break;
                case 2:
                    System.out.print("Enter the amount to be withdrawn: ");
                    double withdrawAmount = scanner.nextDouble();
                    withdraw(account, withdrawAmount);
                    break;
                case 3:
                    System.out.println(account.toString());
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
 
            System.out.println("Do you want to do more actions on the account? Enter yes");
            menuChoice = scanner.next().charAt(0);
        }
    }
 
    // Function to choose an account from the customer's account list
    private static Account chooseAccount(Customer customer) {
        System.out.println("Select an account:");
        int index = 1;
 
        for (Account account : customer.getAccountList()) {
            System.out.println(index + ". " + account.getAccountType());
            index++;
        }
 
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
 
        // Validate the user's choice
        if (choice >= 1 && choice <= customer.getAccountList().size()) {
            return customer.getAccountList().get(choice - 1);
        } else {
            System.out.println("Invalid choice. Returning null.");
            return null;
        }
    }
 
    // Function to withdraw funds from an account
    public static void withdraw(Account account, double withdrawAmount) {
        if (account.getProduct() instanceof SavingsMaxAccount) {
            if (account.getBalance() >= withdrawAmount + 1000) {
                account.setBalance(account.getBalance() - withdrawAmount);
                System.out.println("Transaction Successful");
                System.out.println("Account Balance: " + account.getBalance());
            } else {
                System.out.println("Sorry, insufficient funds in Savings Max Account");
            }
        } else {
            if (account.getBalance() >= withdrawAmount) {
                account.setBalance(account.getBalance() - withdrawAmount);
                System.out.println("Transaction Successful");
                System.out.println("Account Balance: " + account.getBalance());
            } else {
                System.out.println("Sorry, insufficient funds");
            }
        }
    }
 
    // Function to deposit funds into an account
    public static void deposit(Account account, double depositAmount) {
        if (account.getProduct() instanceof LoanAccount) {
            System.out.println("Select deposit type: (1) Cash (2) Check");
            int depositTypeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
 
            if (depositTypeChoice == 1) {
                // Perform cash deposit for LoanAccount
                account.setBalance(account.getBalance() + depositAmount);
                System.out.println("Transaction Successful");
                System.out.println("Account Balance: " + account.getBalance());
            } else if (depositTypeChoice == 2) {
                depositAmount -= depositAmount * 0.03;
                account.setBalance(account.getBalance() + depositAmount);
                System.out.println("Transaction Successful");
                System.out.println("Account Balance: " + account.getBalance());
            }
        } else {
            // Perform deposit for other account types
            account.setBalance(account.getBalance() + depositAmount);
            System.out.println("Transaction Successful");
            System.out.println("Account Balance: " + account.getBalance());
        }
    }
    public static boolean checkDuplicateService(String serviceCode,ArrayList<Service> serviceList){

    	for(Service service:serviceList) {
    		if(service.getServiceCode().equals(serviceCode)){
    		      System.out.println("duplicate service code");
    			return false;
    		}
    	}
    	return true;

    }
}
