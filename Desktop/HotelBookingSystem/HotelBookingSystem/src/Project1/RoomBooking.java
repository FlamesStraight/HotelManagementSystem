package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

//RoomBooking handles all processes of saving valid room bookings within the system
public class RoomBooking extends RoomBookingAction{
    private final IRoomManager roomManager;
    private final IBookingManager bookingManager;

    public RoomBooking(String guestName, int roomNumber, LocalDate checkInTime, LocalDate checkOutTime, IRoomManager roomManager, IBookingManager bookingManager) {
        super(guestName, roomNumber, checkInTime, checkOutTime);
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
    }

    //Overriding bookingAction method to handle room booking
    @Override
    public void bookingAction(Scanner scanner, Customer customer) {
        System.out.println("\n --- Book a Room ---");
        System.out.println("Enter 'x' at any time to return to the main menu!");
        
        List<Room> availableRooms;
        RoomTypes selectedRoomType;

        while(true) {
            System.out.print("Enter Room Type (" + RoomTypeRegistry.listTypes() + "): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("x")) return;

            
            //Checks if room no. input is empty
            if (input.isEmpty()) {
                System.out.println("\n Room Type cannot be empty.");
                continue;
            }

            //Checks if room type input is valid
            if (!RoomTypeRegistry.isValid(input)){
                System.out.println("\n Invalid room type. Please enter one of the listed room types!");
                continue;
            }

            //Checks if there are available rooms for given room type, if there are none, prompts the user again
            selectedRoomType = RoomTypeRegistry.getTypeName(input);
            availableRooms = roomManager.getAvailableRoomsByType(selectedRoomType.getRoomName());

            if(availableRooms.isEmpty()){
                System.out.println("\n No available rooms for the given room type.");
                System.out.print("Would you like to choose another room type? (Y/N): ");
                String tryAgain = scanner.nextLine().trim();
                if (tryAgain.equalsIgnoreCase("Y")){
                    continue;
                } else {
                    return;
                }
            }

            // Displays all available rooms for that room type
            System.out.println("\nAvailable " + selectedRoomType.getRoomName() + " Rooms:");
            for (Room room : availableRooms) {
                System.out.println(" - Room " + room.getRoomNumber() + " (" + room.getAvailability() + ")");
            }
            break;
        }

        // Lets the user choose a room number
        int roomNumber;
        while(true){
            System.out.print("Enter Room Number from the list above: ");
            String roomInput = scanner.nextLine().trim();
            
            if (roomInput.equalsIgnoreCase("x")) return;
            
            if (roomInput.isEmpty()) {


                System.out.println("\n Room number cannot be empty.");
                continue;
            }

            try {
                roomNumber = Integer.parseInt(roomInput);

                boolean found = false;

                // Make sure chosen room is from the list
                for (Room room : availableRooms) {
                    if (room.getRoomNumber() == roomNumber) {
                        found = true;
                        break;
                    }
                }

                if (found) break;
                else System.out.println("\nRoom number is not valid for the selected room type. Try again.");

            } catch (NumberFormatException e) {
                System.out.println("\n Please enter a valid number.");
            }
        }


        // Extract the "unavailable until" date if the room is currently booked
        LocalDate checkInTime;
        LocalDate availableUntil = null;
        for (Room room : availableRooms) {
            if(room.getRoomNumber() == roomNumber){
                String untilString = room.getAvailability();
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

        // Get check-in date and validate against today and availability
        while(true){
            System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("x")) return;
            
            try{
                checkInTime = LocalDate.parse(input);

                if (checkInTime.isBefore(LocalDate.now())) {
                    System.out.println("\n Check-in date must be after today's date. Please choose a valid date.");
                    continue;
                }

                if(availableUntil != null && !checkInTime.isAfter(availableUntil)){
                    System.out.println("\n Room is currently unavailable until " + availableUntil + ". Please choose a different date.");
                    continue;
                }
                break;
            } catch(DateTimeParseException e){
                System.out.println("\n Please enter a valid check-in date, use yyyy-mm-dd format.");
            }
        }

        // Get Check-out date and make sure it's after check-in
        LocalDate checkOutTime;
        while(true){
            System.out.print("Enter Check-Out Date (yyyy-mm-dd): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("x")) return;

            try{
                checkOutTime = LocalDate.parse(input);
                if(checkOutTime.isAfter(checkInTime)){
                    break;
                }
                else{
                    System.out.println("\n Check-Out date must be after check-in date.");
                }
            } catch(DateTimeParseException e){
                System.out.println("\n Please enter a valid check-out date, use yyyy-mm-dd format.");
            }

        }

        // Checks if this customer already has this room booked
        List<String> existingBookings = bookingManager.getCustomerBookings(customer.getName());
        for (String booking : existingBookings) {
            if (booking.contains("Room " + roomNumber)) {
                System.out.println("\n️ You already have a booking for Room " + roomNumber + ".");
                System.out.println("Please cancel the current booking before making a new one for this room.");
                return;
            }
        }

        //Saves the room booking and updates the room availability
        bookingManager.saveBooking(customer.getName(), roomNumber, checkInTime.toString(), checkOutTime.toString());

        // Saves the room as now unavailable
        Room newRoom = new Room(roomNumber, selectedRoomType, "Unavailable until " + checkOutTime);
        roomManager.saveRoom(newRoom);

        roomManager.updateRoomAvailability(roomNumber, "Unavailable until " + checkOutTime);

        System.out.println("\n Booking complete for Room " + roomNumber + ".");
    }
}