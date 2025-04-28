import java.util.Map;
import java.util.Scanner;

public class CustomerManager {
    private Map<String, Customer> customers;
    private final Scanner scanner = new Scanner(System.in);

    public CustomerManager() {
        customers = FileManager.loadCustomers();
    }

    public Customer checkCustomer(String fullName) {
        Customer customer;

        if(customers.containsKey(fullName.toLowerCase())) {
            customer = customers.get(fullName.toLowerCase());
            System.out.println("\nHere's your current information: \n" + customer.toString());

            while(true){
                String updateAnswer = checkInputValidity("\nWould you like to update it? (Y/N): ",false, false, true);
                if(updateAnswer.equalsIgnoreCase("Y")) {

                    while (true) {
                        String emailInput = checkInputValidity("\n Update your email: ", true, false, false);
                        if (emailInput.equalsIgnoreCase(customer.getEmail())) {
                            System.out.println("\nError: New email must be different from your current email. Please try again...");
                        }
                        else {
                            customer.setEmail(emailInput);
                            break;
                        }
                    }

                    while (true) {
                        String phoneNumberInput = checkInputValidity("\n Update your phone number: ", false, true, false);
                        if (phoneNumberInput.equalsIgnoreCase(customer.getPhoneNumber())) {
                            System.out.println("\n Error: New phone number must be different from your current phone number. Please try again...");
                        }
                        else {
                            customer.setPhoneNumber(phoneNumberInput);
                            break;
                        }
                    }

                    FileManager.updateCustomer(customers);
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

            FileManager.addCustomer(customer);
        }
        return customer;
    }

    private String checkNoInput(String userInput){
        String input;
        while(true){
            System.out.print(userInput);
            input = scanner.nextLine();
            if(input.isEmpty()){
                System.out.println("\nNo input provided, please try again...");
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
                if (input.matches("\\d{7,}")){
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
}
