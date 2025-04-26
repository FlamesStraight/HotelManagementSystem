import java.util.Scanner;

public class Hotel {
    public void run(){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your full name: ");
        String nameInput = scanner.nextLine();

        System.out.print("Enter your email: ");
        String emailInput = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumberInput = scanner.nextLine();

        String fullInfo = nameInput + ", " + emailInput + ", " + phoneNumberInput;

        FileManager customerManager = new FileManager();
        FileManager.customerFileManager(fullInfo);

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
