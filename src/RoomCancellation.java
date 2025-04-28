import java.time.LocalDate;
import java.util.List;

public class RoomCancellation extends Booking {
    public RoomCancellation(String guestName, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        super(guestName, roomNumber, checkInDate, checkOutDate);
    }

    public boolean cancelBooking(Room room, List<Booking> bookingList){
        for(Booking booking : bookingList){
            if (booking.getRoomNumber() == room.getRoomNumber() &&
                    booking.getCheckInDate().equals(this.getCheckInDate()) &&
                    booking.getCheckOutDate().equals(this.getCheckOutDate())) {

                bookingList.remove(booking);
                System.out.println("Your booking was cancelled: " + booking);

                room.setAvailableUntil("9999-99-99");
                return true;
            }
        }

        System.out.println("No matching booking found.");
        return false;
    }
}
