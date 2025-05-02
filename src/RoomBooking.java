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
        List<Room> availableRooms;


        while (true) {
            System.out.print("Enter Room Type (Single/Double/Deluxe/Family/Executive): ");
            roomType = scanner.nextLine().trim();

            if (roomType.isEmpty()) {
                System.out.println("\nRoom Type cannot be empty.");
                continue;
            }

            String lowerType = roomType.toLowerCase();
            if (!lowerType.equals("single") && !lowerType.equals("double") &&
                    !lowerType.equals("deluxe") && !lowerType.equals("family") &&
                    !lowerType.equals("executive")) {
                System.out.println("\nInvalid room type. Please enter one of the listed room types!");
                continue;
            }


            availableRooms = roomManager.getAvailableRoomsByType(roomType);

            if (availableRooms.isEmpty()) {
                System.out.println("\n❌ No available rooms found for this type.");
                System.out.print("Would you like to choose another room type? (Y/N): ");
                String tryAgain = scanner.nextLine().trim();
                if (tryAgain.equalsIgnoreCase("Y")) {
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
        while (true) {
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
                else System.out.println("\nRoom number is not in the available list. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.");
            }
        }

        String checkInTime;
        while (true) {
            System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
            checkInTime = scanner.nextLine().trim();
            if (!checkInTime.isEmpty()) break;
            System.out.println("\nCheck-in date cannot be empty.");
        }

        String checkOutTime;
        while (true) {
            System.out.print("Enter Check-Out Date (yyyy-mm-dd): ");
            checkOutTime = scanner.nextLine().trim();
            if (!checkOutTime.isEmpty()) break;
            System.out.println("\nCheck-out date cannot be empty.");
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


// I have also changed ur RoomBooking class to make it so that it checks the availability on rooms based on the user's room type input (shows both unavailable and available)
// if thats ok, ur changes are still on master if u want to revert and also maybe use the new code as reference to make the room availability filter
