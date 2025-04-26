public class RoomCancellation extends Booking {
    public RoomCancellation(Room room, Customer customer, java.time.LocalDate checkInDate, java.time.LocalDate checkOutDate) {
        super(room, customer, checkInDate, checkOutDate);
    }

    @Override
    public void processBooking() {
        if (!room.isAvailable()) {
            room.setAvailable(true);
            System.out.println("Booking for room " + room.getRoomNumber() + " has been canceled and the room is now available.");
        } else {
            System.out.println("Room " + room.getRoomNumber() + " is already available. No booking to cancel.");
        }
    }
}
