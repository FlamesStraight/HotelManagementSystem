import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class RoomCancellation extends Booking {
    public RoomCancellation(String guestName, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        super(guestName, roomNumber, checkInDate, checkOutDate);
    }

    public static void cancelRoomBooking(Scanner scanner, Customer customer) {
        RoomManager roomManager = new RoomManager();

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

                    int roomNumber = Integer.parseInt(roomToCancel);
                    roomManager.updateRoomAvailability(roomNumber, "Available until 9999-12-31");
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
