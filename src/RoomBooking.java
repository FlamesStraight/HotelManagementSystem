import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

        List<Room> availableRooms = roomManager.getRoomsByType(roomType);

        if(availableRooms.isEmpty()){
            System.out.println("\nThere are no available rooms for the given room type.");
            return;
        }
        else{
            System.out.println("\nHere are the availabilities for " + roomType + " rooms.");
            for (Room room : availableRooms) {
                System.out.println(room.getRoomInfo());
            }
        }

        int roomNumber;
        while(true){
            System.out.print("Enter Room Number: ");
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

        LocalDate checkInTime;
        while(true){
            System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
            String input = scanner.nextLine().trim();
            try{
                checkInTime = LocalDate.parse(input);
                break;
            } catch(DateTimeParseException e){
                System.out.println("\nPlease enter a valid check-in date, use yyyy-mm-dd format.");
            }

        }

        LocalDate checkOutTime;
        while(true){
            System.out.print("Enter Check-Out Date (yyyy-mm-dd): ");
            String input = scanner.nextLine().trim();
            try{
                checkOutTime = LocalDate.parse(input);
                if(checkOutTime.isAfter(checkInTime)){
                    break;
                }
                else{
                    System.out.println("\nCheck-Out date must be after check-in date.");
                }
            } catch(DateTimeParseException e){
                System.out.println("\nPlease enter a valid check-out date, use yyyy-mm-dd format.");
            }

        }

        bookingManager.saveBooking(customer.getName(), roomNumber, checkInTime.toString(), checkOutTime.toString());

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
