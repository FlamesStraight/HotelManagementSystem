package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

public class HotelBookingSystem {

    public static void main(String[] args) {

        // Loads all room types (e.g., Single, Double, Deluxe)
        RoomTypeInitializer.initializeTypes();

        // Initializes the Interfaces that handle room and booking operations
        IRoomManager roomManager = new RoomManager();
        IBookingManager bookingManager = new BookingManager();

        // Creates an object to display the main menu
        HotelUI hotel = new HotelUI();
        hotel.startupMenu();
    }
}

