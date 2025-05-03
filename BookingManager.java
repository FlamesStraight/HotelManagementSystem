import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingManager {
    public static final String BOOKINGS_FILE = "bookings.txt";


    public static void saveBooking(String customerName, int roomNumber, String checkInDate, String checkOutDate) {
        try (FileWriter fw = new FileWriter(BOOKINGS_FILE, true)) {
            fw.write(customerName + " booked in Room " + roomNumber + ", from " + checkInDate + " to " + checkOutDate + ".\n");
        } catch (IOException e) {
            System.out.println("\nError occurred when saving booking data!");
        }
    }

    public static List<String> getBookingsForCustomer(String customerName) {
        List<String> bookings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(BOOKINGS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.toLowerCase().startsWith(customerName.toLowerCase() + " ")) {
                    bookings.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("\nError occurred when reading from bookings file!");
        }
        return bookings;
    }
}