package com.ilp.utility;
import com.ilp.enitity.*;

import java.util.ArrayList;
import java.util.Scanner;

import com.ilp.enitity.Account;
import com.ilp.enitity.Customer;
import com.ilp.enitity.Product;
import com.ilp.enitity.Service;
import com.ilp.service.BankService;



public class CustomerUtility {

    private static ArrayList<Service> serviceList = new ArrayList<>();
    private static ArrayList<Product> productList = new ArrayList<>();
    private static ArrayList<Account> accountList = new ArrayList<>();
    private static Customer customer = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    createService();
                    break;

                case 2:
                    createProduct();
                    break;

                case 3:
                    createOrManageCustomer();
                    break;

                case 4:
                    manageAccounts();
                    break;

                case 5:
                    displayCustomer();
                    break;

                case 6:
                    exitProgram();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("*********************************");
        System.out.println("*      Welcome To The Bank      *");
        System.out.println("*********************************");
        System.out.println("1. Create Service");
        System.out.println("2. Create Product");
        System.out.println("3. Create or Manage Customer");
        System.out.println("4. Manage Accounts");
        System.out.println("5. Display Customer");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return choice;
    }

    private static void createService() {
        Service service = BankService.createService(serviceList);
        serviceList.add(service);
        System.out.println("Service created successfully!");
    }

    private static void createProduct() {
        Product product = null;
        String menuChoice = "Y";

        for (Product p : productList) {
            System.out.println("Product: " + p.getProductName() + p.getProductCode());
        }

        while (menuChoice.equals("Y")) {
            System.out.println("***** Account Type Menu *****");
            System.out.println("1. Savings Max Account");
            System.out.println("2. Loan Account");
            System.out.println("3. Current Account");

            int innerChoice = getUserChoice();

            switch (innerChoice) {
                case 1:
                    product = BankService.createProduct("SavingsMax", serviceList);
                    break;

                case 2:
                    product = BankService.createProduct("Loan", serviceList);
                    break;

                case 3:
                    product = BankService.createProduct("Current", serviceList);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println("Product created: " + product.toString());
            productList.add(product);

            System.out.print("Enter Y to add a new product: ");
            menuChoice = scanner.nextLine();
        }
    }

    private static void createOrManageCustomer() {
        if (productList.isEmpty()) {
            System.out.print("Product list is empty. Add products to continue.");
            return;
        }

        if (customer == null) {
            customer = BankService.createCustomer(productList, accountList);
            System.out.println("Customer created successfully!");
        } else {
            System.out.println("Customer Already Created");
            BankService.createAccount(productList, accountList);
            System.out.println("Account added to existing customer.");
        }
    }

    private static void manageAccounts() {
        BankService.manageAccounts(customer);
    }

    private static void displayCustomer() {
        BankService.displayCustomer(customer);
    }

    private static void exitProgram() {
        System.out.println("*********************************");
        System.out.println("* Exiting the program. Goodbye! *");
        System.out.println("*********************************");
        System.exit(0);
    }
}


