public class RoomBooking extends Booking {

    public RoomBooking(Room room, Customer customer, java.time.LocalDate checkInDate, java.time.LocalDate checkOutDate)
    {
        super(room, customer, checkInDate, checkOutDate);
    }

    @Override
    public void processBooking() {
        if (!room.isAvailable()) {
            room.setAvailable(false);
            System.out.println("Room " + room.getRoomNumber() + " has been booked successfully.");
        }
        else {
            System.out.println("Room " + room.getRoomNumber() + "  is already available. No booking to cancel.");
        }
    }

}
