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
                if (line.toLowerCase().contains(customerName.toLowerCase())) {
                    bookings.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("\nError occurred when reading from bookings file!");
        }
        return bookings;
    }

    public static boolean cancelBooking(String customerName, int roomNumber) {
        boolean found = false;
        StringBuilder updatedBookings = new StringBuilder();

        try (Scanner scanner = new Scanner(new File("bookings.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.toLowerCase().contains(customerName.toLowerCase()) && line.contains("Room " + roomNumber)) {
                    found = true;
                    continue;  // skip this line (cancelled)
                }
                updatedBookings.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading bookings file: " + e.getMessage());
        }

        if (found) {
            try (PrintWriter writer = new PrintWriter("bookings.txt")) {
                writer.write(updatedBookings.toString());
            } catch (IOException e) {
                System.out.println("Error updating bookings file: " + e.getMessage());
            }
        }

        return found;
    }
}
