package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public abstract class RoomBookingAction extends Booking{
    //Constructor to initialize a RoomBookingAction object
    public RoomBookingAction(String guestName, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        super(guestName, roomNumber, checkInDate, checkOutDate);
    }

    //Abstract method for implementing booking actions (cancelling and saving bookings)
    public abstract void bookingAction(Scanner scanner, Customer customer);

    //View all bookings for a specific customer
    public static void viewBookings(Customer customer) {
        System.out.println("\n --- Your Bookings ---");
        BookingManager bookingManager = new BookingManager();

        List<String> bookings = bookingManager.getCustomerBookings(customer.getName());

        if (bookings.isEmpty()) {
            System.out.println("\n You have no bookings to view.");
        } else {
            for (String booking : bookings) {
                System.out.println(booking);
            }
        }
    }
}
