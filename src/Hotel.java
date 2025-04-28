import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Hotel
{

    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        CustomerManager customerManager = new CustomerManager();
        boolean loggedIn = false;
        Customer currentCustomer = null;

        System.out.println("🏨 Welcome to the Hotel Management System!");

        while (!loggedIn)
        {
            System.out.println("\n1) Register  2) Login  3) Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    System.out.print("\nEnter your full name to register: ");
                    String newName = scanner.nextLine();
                    currentCustomer = customerManager.checkCustomer(newName);
                    loggedIn = true;
                    System.out.println("✅ Registered and logged in as: " + currentCustomer.getName());
                    break;

                case "2":
                    System.out.print("\nEnter your full name to login: ");
                    String existingName = scanner.nextLine();
                    currentCustomer = customerManager.checkCustomer(existingName);
                    loggedIn = true;
                    System.out.println("✅ Logged in as: " + currentCustomer.getName());
                    break;

                case "3":
                    System.out.println("\nGoodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("\n❌ Invalid option. Please try again.");
            }
        }

        // After login
        mainMenu(scanner, currentCustomer);
        scanner.close();
    }

    private void mainMenu(Scanner scanner, Customer customer)
    {
        boolean running = true;
        while (running)
        {
            System.out.println("\n📋 Main Menu:");
            System.out.println("1) Book a Room");
            System.out.println("2) View My Bookings");
            System.out.println("3) Cancel a Booking");
            System.out.println("4) Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    bookRoom(scanner, customer);
                    break;
                case "2":
                    viewBookings(customer);
                    break;
                case "3":
                    cancelBooking(scanner, customer);  // 🆕
                    break;
                case "4":
                    System.out.println("\n✅ Logged out successfully!");
                    running = false;
                    break;
                default:
                    System.out.println("\n❌ Invalid option. Please try again.");
            }
        }
    }

    private void bookRoom(Scanner scanner, Customer customer)
    {
        System.out.println("\n🏨 --- Book a Room ---");

        System.out.print("Enter Room Type (Single/Double/Deluxe/Family/Executive): ");
        String roomType = scanner.nextLine();

        System.out.print("Enter Room Number: ");
        String roomNumberInput = scanner.nextLine();
        int roomNumber = Integer.parseInt(roomNumberInput);

        System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
        String checkInInput = scanner.nextLine();

        System.out.print("Enter Check-Out Date (yyyy-mm-dd): ");
        String checkOutInput = scanner.nextLine();

        // Save Room info into rooms.txt
        String fullRoomInfo = roomType + ", " + roomNumber;
        FileManager.roomFileManager(fullRoomInfo);

        // Save Booking info into bookings.txt
        FileManager.bookingFileManager(customer.getName(), String.valueOf(roomNumber), checkInInput, checkOutInput);

        System.out.println("✅ Booking complete for Room " + roomNumber + ".");
    }

    private void viewBookings(Customer customer)
    {
        System.out.println("\n📖 --- Your Bookings ---");

        try
        {
            File bookingFile = new File("bookings.txt");
            Scanner fileScanner = new Scanner(bookingFile);

            boolean foundBooking = false;
            while (fileScanner.hasNextLine())
            {
                String line = fileScanner.nextLine();
                if (line.toLowerCase().contains(customer.getName().toLowerCase()))
                {
                    System.out.println("• " + line);
                    foundBooking = true;
                }
            }

            if (!foundBooking)
            {
                System.out.println("🔵 You have no bookings yet.");
            }

            fileScanner.close();
        }
        catch (IOException e)
        {
            System.out.println("❌ Error reading bookings file: " + e.getMessage());
        }
    }

    /**
     * 🆕 Cancel a booking based on room number
     */
    private void cancelBooking(Scanner scanner, Customer customer)
    {
        System.out.println("\n❌ --- Cancel a Booking ---");

        try
        {
            File bookingFile = new File("bookings.txt");
            Scanner fileScanner = new Scanner(bookingFile);
            StringBuilder updatedBookings = new StringBuilder();

            boolean bookingFound = false;

            System.out.print("Enter the Room Number you want to cancel: ");
            String roomToCancel = scanner.nextLine();

            while (fileScanner.hasNextLine())
            {
                String line = fileScanner.nextLine();
                if (line.toLowerCase().contains(customer.getName().toLowerCase()) && line.contains("Room " + roomToCancel))
                {
                    bookingFound = true;

                    // Simulate Room Cancellation
                    Room dummyRoom = new Room(Integer.parseInt(roomToCancel), "Unknown", 0.0);
                    RoomCancellation cancellation = new RoomCancellation(dummyRoom, customer, LocalDate.now(), LocalDate.now());
                    cancellation.processBooking(); // Process cancellation (make room available again)

                    System.out.println("\n✅ Booking for Room " + roomToCancel + " canceled.");
                }
                else
                {
                    updatedBookings.append(line).append("\n"); // Keep other bookings
                }
            }
            fileScanner.close();

            if (bookingFound)
            {
                // Rewrite updated bookings
                FileWriter writer = new FileWriter("bookings.txt");
                writer.write(updatedBookings.toString());
                writer.close();
            }
            else
            {
                System.out.println("❌ You have no current bookings yet.");
            }
        }
        catch (IOException e)
        {
            System.out.println("❌ Error processing booking cancellation: " + e.getMessage());
        }
    }
}
