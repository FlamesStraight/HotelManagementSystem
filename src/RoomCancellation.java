import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

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

                room.setAvailableUntil("Available until 9999-99-99");
                return true;
            }
        }

        System.out.println("No matching booking found.");
        return false;
    }

    public static void cancelRoom(Scanner scanner, Customer customer) {
        System.out.println("\n❌ --- Cancel a Booking ---");

        try {
            File bookingFile = new File("bookings.txt");
            Scanner fileScanner = new Scanner(bookingFile);
            StringBuilder updatedBookings = new StringBuilder();

            boolean bookingFound = false;

            System.out.print("Enter the Room Number you want to cancel: ");
            String roomToCancel = scanner.nextLine().trim();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();


                if (line.toLowerCase().contains(customer.getName().toLowerCase()) &&
                        line.contains(", " + roomToCancel + ",")) {
                    bookingFound = true;
                    System.out.println("\n✅ Booking for Room " + roomToCancel + " canceled.");

                } else {
                    updatedBookings.append(line).append("\n");
                }
            }

            fileScanner.close();

            if (bookingFound) {
                FileWriter writer = new FileWriter("bookings.txt");
                writer.write(updatedBookings.toString());
                writer.close();
            } else {
                System.out.println("❌ No booking found for that room number.");
            }

        } catch (IOException e) {
            System.out.println("❌ Error processing booking cancellation: " + e.getMessage());
        }
    }
}
