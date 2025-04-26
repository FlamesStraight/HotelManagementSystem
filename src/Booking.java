import java.time.LocalDate;

public abstract class Booking {
    protected Room room;
    protected Customer customer;
    protected LocalDate checkInDate;
    protected LocalDate checkOutDate;


    public Booking(Room room, Customer customer, LocalDate checkInDate, LocalDate checkOutDate) {
        this.room = room;
        this.customer = customer;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public abstract void processBooking();

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
}
