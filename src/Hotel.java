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
                System.out.println("\nNo full name entered, please try again: ");
            }
            else{
                break;
            }
        }

        CustomerManager detailsChecker = new CustomerManager();
        detailsChecker.checkCustomer(nameInput);

        String roomType;

        while(true){
            System.out.println("What room type would you like to book? (Single/Double/Deluxe/Family/Executive): ");
            roomType = scanner.nextLine();

            if (roomType.isEmpty()) {
                System.out.println("\nNo room type entered, please try again: ");
            }
            else if(roomType.equalsIgnoreCase("Single") || roomType.equalsIgnoreCase("Double")
                    || roomType.equalsIgnoreCase("Deluxe") || roomType.equalsIgnoreCase("Family")
                    || roomType.equalsIgnoreCase("Executive")){
                break;
            }
            else{
                System.out.println("\nInvalid room type, please try again: ");
            }
        }

        RoomManager roomChecker = new RoomManager();
        roomChecker.roomDetailsChecker(roomType);

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

        //FileManager bookingManager = new FileManager();
        //FileManager.bookingFileManager(nameInput, String.valueOf(roomNumber), checkInString, checkOutString);
    }
}
