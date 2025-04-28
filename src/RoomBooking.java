import java.time.LocalDate;
import java.util.List;


public class RoomBooking extends Booking {
    public RoomBooking(String guestName, int roomNumber, LocalDate checkInTime, LocalDate checkOutTime)
    {
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
}
