import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class RoomBooking extends Booking {
    public RoomBooking(String guestName, int roomNumber, LocalDate checkInTime, LocalDate checkOutTime) {
        super(guestName, roomNumber, checkInTime, checkOutTime);
    }

    public boolean bookRoom(Room room, List<Booking> bookingList) {
        for(Booking existingBooking : bookingList) {
            if(existingBooking.getRoomNumber() == room.getRoomNumber() &&
                    (existingBooking.getCheckInDate().isBefore(this.getCheckOutDate())) &&
                    (existingBooking.getCheckOutDate().isAfter(this.getCheckInDate()))) {
                System.out.println("Sorry: Room " + room.getRoomNumber() + " is already booked during those times.");
                return false;
            }
        }

        bookingList.add(this);
        room.setAvailableUntil(this.getCheckOutDate().toString());
        return true;
    }

    public static void bookRoom(Scanner scanner, Customer customer) {
        System.out.println("\n🏨 --- Book a Room ---");

        System.out.print("Enter Room Type (Single/Double/Deluxe/Family/Executive): ");
        String roomType = scanner.nextLine();

        System.out.print("Enter Room Number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
        String checkInInput = scanner.nextLine();

        System.out.print("Enter Check-Out Date (yyyy-mm-dd): ");
        String checkOutInput = scanner.nextLine();

        FileManager.saveRoom(roomType, roomNumber);
        FileManager.saveBooking(customer.getName(), roomNumber, checkInInput, checkOutInput);

        System.out.println("\n✅ Booking complete for Room " + roomNumber + ".");
        FileManager.updateRoomAvailability(roomNumber, "Unavailable until " + checkOutInput);
    }

    public static void viewBookings(Customer customer){
        System.out.println("\n📖 --- Your Bookings ---");

        List<String> bookings = FileManager.getBookingsForCustomer(customer.getName());

        if(bookings.isEmpty()){
            System.out.println("\nYou have no bookings to view.");
        }
        else{
            for(String booking : bookings){
                System.out.println(booking);
            }
        }
    }
}

