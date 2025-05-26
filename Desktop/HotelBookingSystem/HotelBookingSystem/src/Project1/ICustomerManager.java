package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

// Interface for managing customer-related operations
public interface ICustomerManager {

    // Checks if a customer exists; if not, registers them
    Customer checkCustomer(String fullName);

    // Saves a new customer to the file
    void saveCustomer(Customer customer);

    // Updates the customer file with any changes
    void updateCustomerFile();
}

