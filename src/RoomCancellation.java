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

    public static void cancelRoomBooking(Scanner scanner, Customer customer) {
        RoomManager roomManager = new RoomManager();
        BookingManager bookingManager = new BookingManager();

        System.out.println("\n❌ --- Cancel a Booking ---");

        System.out.print("Enter the Room Number you want to cancel: ");
        String roomToCancel = scanner.nextLine().trim();

        if (roomToCancel.isEmpty()) {
            System.out.println("❌ Room number cannot be empty.");
            return;
        }

        try {
            int roomNumber = Integer.parseInt(roomToCancel);
            List<String> bookings = bookingManager.getBookingsForCustomer(customer.getName());

            boolean bookingFound = false;
            StringBuilder updatedBookings = new StringBuilder();

            Scanner fileScanner = new Scanner(new File("bookings.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();

                String expectedPrefix = customer.getName() + " booked in Room " + roomNumber + ",";
                if (line.startsWith(expectedPrefix)) {
                    bookingFound = true;
                    System.out.println("\n✅ Booking for Room " + roomNumber + " canceled.");
                    roomManager.updateRoomAvailability(roomNumber, "Available");
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

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid room number format.");
        } catch (IOException e) {
            System.out.println("❌ Error reading or writing booking file: " + e.getMessage());
        }

    }
}
