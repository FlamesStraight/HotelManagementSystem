package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.util.Scanner;

public class HotelUI {
    // This method displays the initial menu where the user can Register, Login, or Exit
    public void startupMenu() {
        Scanner scanner = new Scanner(System.in);

        // This interface (ICustomerManager) is used to manage customer registration/login
        ICustomerManager customerManager = new CustomerManager();
        Customer currentCustomer;

        System.out.println("\n ---------------------------------------------------");
        System.out.println("        Welcome to the Hotel Booking System!    ");
        System.out.println(" ---------------------------------------------------");


        // This loops to keep asking the user to Register/Login/Exist until a valid action is taken
        while (true) {
            System.out.println("\n1) Register  2) Login  3) Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            // Using a switch function to choose the user action
            switch (choice) {
                case "1":
                    System.out.print("\nEnter your full name to register: ");
                    String newName = scanner.nextLine();

                    // Checks if user exists or needs to be registered
                    currentCustomer = customerManager.checkCustomer(newName);

                    System.out.println("------------------------------------------");
                    System.out.printf ("       Welcome, " + currentCustomer.getName() + "!\n");
                    System.out.println("------------------------------------------");

                    // Go to the main menu after registration
                    mainMenu(scanner, currentCustomer);
                    break;

                case "2":
                    //Log in an existing customer
                    System.out.print("\nEnter your registered full name to login: ");
                    String existingName = scanner.nextLine();

                    // Checks if the customer is already registered
                    currentCustomer = customerManager.checkCustomer(existingName);
                    System.out.println("\uD83D\uDC4B Greetings " + currentCustomer.getName() + "!");

                    mainMenu(scanner, currentCustomer);
                    break;

                case "3":
                    System.out.println("\nGoodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    // Catch invalid menu options
                    System.out.println("\n Invalid option. Please try again.");
            }
        }
    }


    // This method shows the Main Menu after Login/Register
    // It allows the user to book, view, or cancel rooms or logout
    private void mainMenu(Scanner scanner, Customer customer) {

        // Interfaces to handle room and booking functionalities
        IRoomManager roomManager = new RoomManager();
        IBookingManager bookingManager = new BookingManager();

        // Infinite loop so user can keep interacting with the system until they choose to Logout
        while (true) {
            System.out.println("\n Main Menu:");
            System.out.println("1) Book a Room");
            System.out.println("2) View My Bookings");
            System.out.println("3) Cancel a Booking");
            System.out.println("4) Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            // Handle user's selected action
            switch (choice) {
                case "1":

                    // Create a booking using RoomBooking subclass of RoomBookingAction
                    RoomBookingAction addBooking = new RoomBooking(customer.getName(), 0, null, null, roomManager, bookingManager);
                    addBooking.bookingAction(scanner, customer);
                    break;
                case "2":
                    // Call static method to view customer's bookings
                    RoomBookingAction.viewBookings(customer);
                    break;
                case "3":
                    // Create a cancellation using RoomCancellation subclass of RoomBookingAction
                    RoomBookingAction cancelBooking = new RoomCancellation(customer.getName(), 0, null, null, roomManager, bookingManager);
                    cancelBooking.bookingAction(scanner, customer);
                    break;
                case "4":
                    System.out.println("\n Logged out successfully!");
                    System.exit(0);
                default:
                    // Handle invalid inputs
                    System.out.println("\n Invalid option. Please try again.");
            }
        }
    }
}