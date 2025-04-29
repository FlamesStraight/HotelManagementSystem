import java.util.Scanner;

public class Hotel {
    public void startupMenu() {
        Scanner scanner = new Scanner(System.in);
        CustomerManager customerManager = new CustomerManager();
        Customer currentCustomer;

        System.out.println("\uD83C\uDFE8 Welcome to the Hotel Booking System!");

        while (true) {
            System.out.println("\n1) Register  2) Login  3) Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("\nEnter your full name to register: ");
                    String newName = scanner.nextLine();
                    currentCustomer = customerManager.checkCustomer(newName);
                    System.out.println("\uD83D\uDC4B Greetings " + currentCustomer.getName() + "!");

                    mainMenu(scanner, currentCustomer);
                    break;

                case "2":
                    System.out.print("\nEnter your registered full name to login: ");
                    String existingName = scanner.nextLine();
                    currentCustomer = customerManager.checkCustomer(existingName);
                    System.out.println("\uD83D\uDC4B Greetings " + currentCustomer.getName() + "!");

                    mainMenu(scanner, currentCustomer);
                    break;

                case "3":
                    System.out.println("\nGoodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("\n❌ Invalid option. Please try again.");
            }
        }
    }

    private void mainMenu(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("\n📋 Main Menu:");
            System.out.println("1) Book a Room");
            System.out.println("2) View My Bookings");
            System.out.println("3) Cancel a Booking");
            System.out.println("4) Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    RoomBooking.bookRoom(scanner, customer);
                    break;
                case "2":
                    RoomBooking.viewBookings(customer);
                    break;
                case "3":
                    RoomCancellation.cancelRoom(scanner, customer);  // 🆕
                    break;
                case "4":
                    System.out.println("\n✅ Logged out successfully!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("\n❌ Invalid option. Please try again.");
            }
        }
    }
}
