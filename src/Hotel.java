import java.util.Scanner;

public class Hotel {
    public void run(){
        System.out.println("Welcome to the Hotel Booking System!");
        Scanner scanner = new Scanner(System.in);

        String nameInput;

        while(true){
            System.out.print("Enter your full name: ");
            nameInput = scanner.nextLine();

            if (nameInput.isEmpty()) {
                System.out.println("No full name entered, please try again: ");
            }
            else{
                break;
            }
        }

        CustomerManager nameChecker = new CustomerManager();
        nameChecker.checkCustomer(nameInput);

        System.out.println("Enter Room Type (Single/Double/Deluxe/Family/Executive): ");
        String roomType = scanner.nextLine();

        System.out.println("Enter Room Number to book (e.g., 101): ");
        int roomNumber = Integer.parseInt(scanner.nextLine());

        String fullRoom = roomType + ", " + roomNumber;

        FileManager roomManager = new FileManager();
        FileManager.roomFileManager(fullRoom);

        System.out.println("Enter Check-In Date: (yyyy-mm-dd): ");
        String checkInString = scanner.nextLine();
        System.out.println("Enter Check-Out Date: (yyyy-mm-dd): ");
        String checkOutString = scanner.nextLine();

        String fullBooking = checkInString + ", " + checkOutString;

        FileManager bookingManager = new FileManager();
        FileManager.bookingFileManager(nameInput, String.valueOf(roomNumber), checkInString, checkOutString);
    }
}
