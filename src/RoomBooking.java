import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RoomBooking extends Booking {

    public RoomBooking(String guestName, int roomNumber, LocalDate checkInTime, LocalDate checkOutTime) {
        super(guestName, roomNumber, checkInTime, checkOutTime);
    }

    public static void bookRoom(Scanner scanner, Customer customer) {
        RoomManager roomManager = new RoomManager();
        BookingManager bookingManager = new BookingManager();

        System.out.println("\n🏨 --- Book a Room ---");

        String roomType;
        while(true) {
            System.out.print("Enter Room Type (Single/Double/Deluxe/Family/Executive): ");
            roomType = scanner.nextLine().trim();

            if (roomType.isEmpty()) {
                System.out.println("\nRoom Type cannot be empty.");
                continue;
            }

            String lowerType = roomType.toLowerCase();
            if (lowerType.equals("single") || lowerType.equals("double") || lowerType.equals("deluxe")
                    || lowerType.equals("family") || lowerType.equals("executive")) {
                break;
            } else {
                System.out.println("\nInvalid room type. Please enter one of the listed room types!");
            }

        }

        int roomNumber;
        while(true){
            System.out.print("Enter Room Number: (NEED TO ADD AVAILABLE ROOM NUMBERS FOR ROOM TYPE INPUTTED, IGNORE THIS FOR NOW PLS TY)");
            String roomInput = scanner.nextLine().trim();

            if (roomInput.isEmpty()) {
                System.out.println("\nRoom number cannot be empty.");
                continue;
            }

            try {
                roomNumber = Integer.parseInt(roomInput);

                boolean isValid = switch (roomType.toLowerCase()) {
                    case "executive" -> roomNumber >= 1 && roomNumber <= 5;
                    case "family" -> roomNumber >= 6 && roomNumber <= 10;
                    case "deluxe" -> roomNumber >= 11 && roomNumber <= 15;
                    case "double" -> roomNumber >= 16 && roomNumber <= 20;
                    case "single" -> roomNumber >= 21 && roomNumber <= 25;
                    default -> false;
                };

                if (isValid) break;
                else System.out.println("\nRoom number is not valid for the selected room type.");

            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.");
            }
        }

        String checkInTime;
        while(true){
            System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
            checkInTime = scanner.nextLine().trim();
            if (!checkInTime.isEmpty()) break;
            System.out.println("\nCheck-in date cannot be empty.");

        }

        String checkOutTime;
        while(true){
            System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
            checkOutTime = scanner.nextLine().trim();
            if (!checkOutTime.isEmpty()) break;
            System.out.println("\nCheck-in date cannot be empty.");

        }

        bookingManager.saveBooking(customer.getName(), roomNumber, checkInTime, checkOutTime);

        Room newRoom = new Room(roomNumber, roomType, "Unavailable until " + checkOutTime);
        roomManager.saveRoom(newRoom);

        roomManager.updateRoomAvailability(roomNumber, "Unavailable until " + checkOutTime);

        System.out.println("\n✅ Booking complete for Room " + roomNumber + ".");
    }


    public static void viewBookings(Customer customer) {
        System.out.println("\n📖 --- Your Bookings ---");
        BookingManager bookingManager = new BookingManager();

        List<String> bookings = bookingManager.getBookingsForCustomer(customer.getName());

        if (bookings.isEmpty()) {
            System.out.println("\nYou have no bookings to view.");
        } else {
            for (String booking : bookings) {
                System.out.println(booking);
            }
        }
    }
}
