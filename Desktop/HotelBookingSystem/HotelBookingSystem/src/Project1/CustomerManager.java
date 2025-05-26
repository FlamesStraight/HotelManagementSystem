package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// This class manages all customer-related operations such as loading, saving
// updating customer information, and handling customer registration or login
public class CustomerManager implements ICustomerManager {

    // Stores all customers in a map wherer the key is their lowercase name
    private final Map<String, Customer> customers;
    private final Scanner scanner = new Scanner(System.in);

    // The file where customer records are stored
    private static final String CUSTOMER_FILE = "./hotelbookingresources/customers.txt";

    // Constructor: intializes the manager by loading customer data from file
    public CustomerManager() {
        customers = loadCustomers();
    }

    // Loads customer data from the file into a map for quick access
    private Map<String, Customer> loadCustomers() {
        Map<String, Customer> customerMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(CUSTOMER_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String name = parts[0].trim();
                        String email = parts[1].trim();
                        String phone = parts[2].trim();

                        // Store the customer in lowercase format for easy lookup
                        customerMap.put(name.toLowerCase(), new Customer(name, email, phone));
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(" ️Customer text file could not be found. ️");
        }
        return customerMap;
    }

    // Saves a new customer's information to the customer file
    @Override
    public void saveCustomer(Customer customer) {
        try (FileWriter writer = new FileWriter(CUSTOMER_FILE, true)) {
            writer.write(customer.toFileString() + "\n");
        } catch (IOException e) {
            System.out.println("Error occurred when saving customer.");
        }
    }

    // Rewrites the entire customer file with updated customer data
    @Override
    public void updateCustomerFile() {
        try (PrintWriter pw = new PrintWriter(CUSTOMER_FILE)) {
            for (Customer c : customers.values()) {
                pw.println(c.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error occurred when updating customer file.");
        }
    }

    // Main method to check if a customer exists. Handles both login and registration
    @Override
    public Customer checkCustomer(String fullName) {
        Customer customer;

        // Ensure name is not blank
        while (fullName.isEmpty()) {
            System.out.print("\n  Name cannot be empty. Please enter your full name: ");
            fullName = scanner.nextLine().trim();
        }

        // If the name exists in the map, treat as returning customer
        if (customers.containsKey(fullName.toLowerCase())) {
            customer = customers.get(fullName.toLowerCase());
            System.out.println("\nHere's your current information: \n" + customer);

            // Ask user if they want to update their details
            while (true) {
                String update = checkInputValidity("\nWould you like to update it? (Y/N): ", false, false, true);
                if (update.equalsIgnoreCase("Y")) {

                    // Update email
                    while (true) {
                        String email = checkInputValidity("Update your email: ", true, false, false);
                        if (email.equalsIgnoreCase(customer.getEmail())) {
                            System.out.println("Error: New email must be different.");
                        } else {
                            customer.setEmail(email);
                            break;
                        }
                    }

                    // Update phone
                    while (true) {
                        String phone = checkInputValidity("Update your phone number: ", false, true, false);
                        if (phone.equalsIgnoreCase(customer.getPhoneNumber())) {
                            System.out.println("Error: New phone number must be different.");
                        } else {
                            customer.setPhoneNumber(phone);
                            break;
                        }
                    }

                    // Save changes to the file
                    updateCustomerFile();
                    break;
                } else if (update.equalsIgnoreCase("N")) {
                    break;
                }
            }

            // If the name is new, treat as a new customer
        } else {
            System.out.println("\nNew customer detected. Please add details.");

            // Confirm or change name
            System.out.print("Your name (press Enter to keep \"" + fullName + "\"): ");
            String confirmedName = scanner.nextLine().trim();

            if (confirmedName.trim().isEmpty()) {
                confirmedName = fullName;
            }

            // Get and validate email and phone number
            String email = checkInputValidity("Enter your email: ", true, false, false);
            String phone = checkInputValidity("Enter your phone number: ", false, true, false);

            // Create and store new customer
            customer = new Customer(confirmedName, email, phone);
            customers.put(confirmedName.toLowerCase(), customer);
            saveCustomer(customer);
        }
        return customer;
    }

    // Utility method to validate user input based on required format
    private String checkInputValidity(String prompt, boolean isEmail, boolean isPhone, boolean isYesNo) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()){
                System.out.println("\n Input cannot be empty. Please try again.");
                continue;
            }

            // Validate based on type of input expected
            if (isEmail && input.contains("@") && input.contains(".")) return input;
            if (isPhone && input.matches("\\d{6,}")) return input;
            if (isYesNo && (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N"))) return input;

            System.out.println("\n Invalid input. Try again.");
        }
    }
}