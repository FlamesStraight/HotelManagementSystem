import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CustomerManager {
    private Map<String, Customer> customers = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public CustomerManager() {
        loadCustomersData();
    }

    private void loadCustomersData() {
        try(Scanner scanner = new Scanner(new File("customers.txt"))) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if(!line.isEmpty()) {
                    String[] customerDetails = line.split(",");

                    if(customerDetails.length >= 3) {
                        String fullName = customerDetails[0].trim();
                        String email = customerDetails[1].trim();
                        String phoneNumber = customerDetails[2].trim();

                        Customer customer = new Customer(fullName, email, phoneNumber);
                        customers.put(fullName.toLowerCase(), customer);
                    }
                    else{
                        System.out.println("Saved customer details is invalid!");
                    }
                }
            }
        } catch(IOException e){
            System.out.println("Error reading from customer file.");
        }
    }

    public Customer checkCustomer(String fullName) {
        Customer customer;

        if(customers.containsKey(fullName.toLowerCase())) {
            customer = customers.get(fullName.toLowerCase());
            System.out.println("\nHere's your current information: \n" + customer.toString());

            while(true){
                String updateAnswer = checkInputValidity("\nWould you like to update it? (Y/N): ",false, false, true);
                if(updateAnswer.equalsIgnoreCase("Y")) {
                    String emailInput = checkInputValidity("\nUpdate your email: ", true, false, false);
                    String phoneNumberInput = checkInputValidity("\nUpdate your phone number: ", false, true, false);

                    customer.setEmail(emailInput);
                    customer.setPhoneNumber(phoneNumberInput);

                    overwriteCustomerData();
                    break;
                }
                else if(updateAnswer.equalsIgnoreCase("N")) {
                    break;
                }
                else{
                    System.out.println("No input, please input either 'Y' or 'N'...");
                }
            }
        }

        else{
            System.out.println("\nNew customer detected, please add additional information: ");

            String emailInput = checkInputValidity("\nEnter your email: ", true, false, false);
            String phoneNumberInput = checkInputValidity("\nEnter your phone number: ", false, true, false);

            customer = new Customer(fullName, emailInput, phoneNumberInput);
            customers.put(fullName.toLowerCase(), customer);

            saveCustomersData(customer);
        }
        return customer;
    }

    private String checkNoInput(String userInput){
        String input;
        while(true){
            System.out.print(userInput);
            input = scanner.nextLine();
            if(input.isEmpty()){
                System.out.println("No input provided, please try again...");
            }
            else{
                return input;
            }
        }
    }


    private String checkInputValidity(String userInput, boolean anEmail, boolean aPhoneNumber, boolean aYesOrNo) {
        String input;
        while(true){
            input = checkNoInput(userInput);
            if(anEmail){
                if(input.contains("@") && input.contains(".")){
                    return input;
                }
                else{
                    System.out.println("\nInvalid email format, please try again...");
                }
            }
            if(aPhoneNumber){
                if(input.matches("\\d+")){
                    return input;
                }
                else{
                    System.out.println("\nInvalid phone number format, please try again...");
                }
            }
            if(aYesOrNo){
                if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")){
                    return input;
                }
                System.out.println("\nInvalid input, please enter 'Y' or 'N'...");
            }
        }
    }

    private void saveCustomersData(Customer customer) {
        try(FileWriter writer = new FileWriter("customers.txt", true)) {
            writer.write(customer.toFileString() + "\n");
            System.out.println("\nYour information has been saved.\n");
        } catch (IOException e) {
            System.out.println("Error occurred when saving customer data.");
        }
    }

    private void overwriteCustomerData() {
        String fileName = "customers.txt";
        try (FileOutputStream fOut = new FileOutputStream(fileName);
             PrintWriter pw = new PrintWriter(fOut)) {

            for (Customer customer : customers.values()) {
                pw.println(customer.toFileString());
            }
            System.out.println("\nYour information has been saved.\n");
        } catch (IOException ex) {
            System.out.println("Error occurred when saving customer data.");
        }

    }
}
