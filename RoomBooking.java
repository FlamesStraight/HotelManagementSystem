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

        List<Room> availableRooms;
        String roomType;

        while(true) {
            System.out.print("Enter Room Type (Single/Double/Deluxe/Family/Executive): ");
            roomType = scanner.nextLine().trim();

            if (roomType.isEmpty()) {
                System.out.println("\nRoom Type cannot be empty.");
                continue;
            }

            String lowerType = roomType.toLowerCase();
            if (!lowerType.equals("single") && !lowerType.equals("double") && !lowerType.equals("deluxe")
                    && !lowerType.equals("family") && !lowerType.equals("executive")) {
                System.out.println("\nInvalid room type. Please enter one of the listed room types!");
                continue;
            }

            availableRooms = roomManager.getAvailableRoomsByType(roomType);

            if(availableRooms.isEmpty()){
                System.out.println("\nNo available rooms for the given room type.");
                System.out.print("Would you like to choose another room type? (Y/N): ");
                String tryAgain = scanner.nextLine().trim();
                if (tryAgain.equalsIgnoreCase("Y")){
                    continue;
                } else {
                    return;
                }
            }

            System.out.println("\nAvailable " + roomType.toLowerCase() + " Rooms:");
            for (Room room : availableRooms) {
                System.out.println(" - Room " + room.getRoomNumber() + " (" + room.getAvailableUntil() + ")");
            }
            break;
        }


        int roomNumber;
        while(true){
            System.out.print("Enter Room Number from the list above: ");
            String roomInput = scanner.nextLine().trim();

            if (roomInput.isEmpty()) {
                System.out.println("\nRoom number cannot be empty.");
                continue;
            }

            try {
                roomNumber = Integer.parseInt(roomInput);

                boolean found = false;
                for (Room room : availableRooms) {
                    if (room.getRoomNumber() == roomNumber) {
                        found = true;
                        break;
                    }
                }


                if (found) break;
                else System.out.println("\nRoom number is not valid for the selected room type. Try again.");

            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.");
            }
        }

        LocalDate checkInTime;
        LocalDate availableUntil = null;
        for (Room room : availableRooms) {
            if(room.getRoomNumber() == roomNumber){
                String untilString = room.getAvailableUntil();
                if(untilString.toLowerCase().startsWith("unavailable until")){
                    String datePart = untilString.substring("unavailable until ".length()).trim();
                    try{
                        availableUntil = LocalDate.parse(datePart);
                    }
                    catch(DateTimeParseException e){
                        System.out.println("\nCould not parse date.");
                    }
                }
                break;
            }
        }

        while(true){
            System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
            String input = scanner.nextLine().trim();
            try{
                checkInTime = LocalDate.parse(input);

                if(availableUntil != null && !checkInTime.isAfter(availableUntil)){
                    System.out.println("\nRoom is currently unavailable until " + availableUntil + ". Please choose a different date.");
                    continue;
                }
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

        List<String> existingBookings = bookingManager.getBookingsForCustomer(customer.getName());
        for (String booking : existingBookings) {
            if (booking.contains("Room " + roomNumber)) {
                System.out.println("\n⚠️ You already have a booking for Room " + roomNumber + ".");
                System.out.println("Please cancel the current booking before making a new one for this room.");
                return;
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