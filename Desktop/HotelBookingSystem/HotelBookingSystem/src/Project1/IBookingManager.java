package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.util.List;

// Interface for managing bookings
public interface IBookingManager {

    // Save a new booking
    void saveBooking(String customerName, int roomNumber, String checkInDate, String checkOutDate);

    // Cancel a booking based on customer  name and room number
    boolean cancelBooking(String customerName, int roomNumber);

    // Get all bookings for a specific customer
    List<String> getCustomerBookings(String customerName);
}
