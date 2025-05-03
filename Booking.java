import java.time.LocalDate;

public class Booking {
    private String guestName;
    private int roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Booking(String guestName, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String bookingInfo(){
        return guestName + ", Room: " + roomNumber + ", From: " + checkInDate + ", To: " + checkOutDate;
    }
    @Override
    public String toString(){
        return bookingInfo();
    }
}