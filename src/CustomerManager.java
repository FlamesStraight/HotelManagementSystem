import java.io.*;
import java.util.*;

public class CustomerManager {
    private Map<String, Customer> customers;
    private final Scanner scanner = new Scanner(System.in);

    public CustomerManager() {
        customers = loadCustomers();
    }

    private Map<String, Customer> loadCustomers() {
        Map<String, Customer> customerMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("customers.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String name = parts[0].trim();
                        String email = parts[1].trim();
                        String phone = parts[2].trim();
                        customerMap.put(name.toLowerCase(), new Customer(name, email, phone));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers.");
        }
        return customerMap;
    }

    public void saveCustomer(Customer customer) {
        try (FileWriter writer = new FileWriter("customers.txt", true)) {
            writer.write(customer.toFileString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving customer.");
        }
    }

    public void updateCustomerFile() {
        try (PrintWriter pw = new PrintWriter("customers.txt")) {
            for (Customer c : customers.values()) {
                pw.println(c.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error updating customer file.");
        }
    }

    public Customer checkCustomer(String fullName) {
        Customer customer;
        if (customers.containsKey(fullName.toLowerCase())) {
            customer = customers.get(fullName.toLowerCase());
            System.out.println("\nHere's your current information: \n" + customer);

            while (true) {
                String update = checkInputValidity("\nWould you like to update it? (Y/N): ", false, false, true);
                if (update.equalsIgnoreCase("Y")) {

                    while (true) {
                        String email = checkInputValidity("Update your email: ", true, false, false);
                        if (email.equalsIgnoreCase(customer.getEmail())) {
                            System.out.println("Error: New email must be different.");
                        } else {
                            customer.setEmail(email);
                            break;
                        }
                    }

                    while (true) {
                        String phone = checkInputValidity("Update your phone number: ", false, true, false);
                        if (phone.equalsIgnoreCase(customer.getPhoneNumber())) {
                            System.out.println("Error: New phone number must be different.");
                        } else {
                            customer.setPhoneNumber(phone);
                            break;
                        }
                    }

                    updateCustomerFile();

                    break;
                } else if (update.equalsIgnoreCase("N")) {
                    break;
                }
            }
        } else {
            System.out.println("\nNew customer detected. Please add details.");

            System.out.print("Your name (press Enter to keep \"" + fullName + "\"): ");
            String confirmedName = scanner.nextLine().trim();

            if (confirmedName.trim().isEmpty()) {
                confirmedName = fullName;
            }

            String email = checkInputValidity("Enter your email: ", true, false, false);
            String phone = checkInputValidity("Enter your phone number: ", false, true, false);

            customer = new Customer(confirmedName, email, phone);
            customers.put(confirmedName.toLowerCase(), customer);
            saveCustomer(customer);
        }
        return customer;
    }

    private String checkInputValidity(String prompt, boolean isEmail, boolean isPhone, boolean isYesNo) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            if (isEmail && input.contains("@") && input.contains(".")) return input;
            if (isPhone && input.matches("\\d{6,}")) return input;
            if (isYesNo && (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N"))) return input;

            System.out.println("Invalid input. Try again.");
        }
    }
}
