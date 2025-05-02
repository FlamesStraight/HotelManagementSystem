import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingManager {

    public static void saveBooking(String customerName, int roomNumber, String checkInDate, String checkOutDate) {
        try (FileWriter fw = new FileWriter("bookings.txt", true)) {
            fw.write(customerName + ", " + roomNumber + ", " + checkInDate + ", " + checkOutDate + "\n");
        } catch (IOException e) {
            System.out.println("\nError occurred when saving booking data!");
        }
    }

    public static List<String> getBookingsForCustomer(String customerName) {
        List<String> bookings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("bookings.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Fixed the overlapping users when checking 'View my Bookings' on log-ins with similar letters
                // Now checks if the line starts exactly with the user's name, followed by a comma
                // So that it only matches your exact name, not part of someone else's ---- UR A GERD
                if (line.toLowerCase().startsWith(customerName.toLowerCase() + ",")) {
                    bookings.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("\nError occurred when reading from bookings file!");
        }
        return bookings;
    }
}
