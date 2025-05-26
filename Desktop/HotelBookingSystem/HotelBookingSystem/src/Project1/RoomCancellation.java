package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

//RoomCancellation handles all processes of cancelling room bookings within the system
public class RoomCancellation extends RoomBookingAction {
    private final IRoomManager roomManager;
    private final IBookingManager bookingManager;

    public RoomCancellation(String guestName, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate, IRoomManager roomManager, IBookingManager bookingManager) {
        super(guestName, roomNumber, checkInDate, checkOutDate);
        this.roomManager = roomManager;
        this.bookingManager = bookingManager;
    }

    //Overriding bookingAction method to handle room cancellation
    @Override
    public void bookingAction(Scanner scanner, Customer customer) {
        System.out.println("\n --- Cancel a Booking ---");

        System.out.print("Enter the Room Number you want to cancel: ");
        String roomToCancel = scanner.nextLine().trim();

        //Checks if room no. input is empty
        if (roomToCancel.isEmpty()) {
            System.out.println(" Room number cannot be empty.");
            return;
        }

        //Parse room no. input to finish room cancellation process
        try {
            int roomNumber = Integer.parseInt(roomToCancel);
            List<String> bookings = bookingManager.getCustomerBookings(customer.getName());

            boolean bookingFound = false;

            bookingFound = bookingManager.cancelBooking(customer.getName(), roomNumber);

            if (bookingFound) {
                System.out.println("\nYour booking for Room " + roomNumber + " has been successfully cancelled.");
                roomManager.updateRoomAvailability(roomNumber, "Available");
            } else {
                System.out.println(" No booking found for that room number.");
            }
        } catch (NumberFormatException e) {
            System.out.println(" Invalid room number format.");
        }
    }
}
