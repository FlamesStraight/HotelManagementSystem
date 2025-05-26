package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// BookingManager is responsible for managing booking operations like saving, canceling, and retrieving bookings
// This class implements the IBookingManager interface, which defines what methods a booking manager should have
public class BookingManager implements IBookingManager {

    // This list holds all bookings loaded from the file into memory
    private final List<Booking> bookings;
    public static final String BOOKINGS_FILE = "./hotelbookingresources/bookings.txt";

    // Constructor: When a BookingManager is created, it loads all bookings from the file
    public BookingManager() {
        bookings = loadBookings();
    }

    // This method loads all bookings from the bookings.txt and returns them as a list
    private List<Booking> loadBookings(){
        List<Booking> bookingList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(BOOKINGS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // We only consider lines that contain "booked in Room" to be valid booking records
                if (!line.isEmpty() && line.contains("booked in Room")) {
                    try {

                        // E.g. John Doe booked in Room 102, from 2025-05-01 to 2025-05-05.
                        String[] parts = line.split(" booked in Room ");
                        String name = parts[0].trim();

                        String[] roomParts = parts[1].split(", from ");
                        int roomNumber = Integer.parseInt(roomParts[0].trim());

                        String[] dates = roomParts[1].split(" to ");
                        LocalDate checkIn = LocalDate.parse(dates[0].trim());
                        LocalDate checkOut = LocalDate.parse(dates[1].replace(".", "").trim());

                        // Add a new booking object to the list
                        bookingList.add(new Booking(name, roomNumber, checkIn, checkOut));
                    } catch (NumberFormatException e) {
                        System.out.println("️ Invalid booking line format: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(" ️Bookings text file could not be found.⚠️");
        }
        return bookingList;
    }

    // Saves a new booking record to the bookings.txt file
    @Override
    public void saveBooking(String customerName, int roomNumber, String checkInDate, String checkOutDate) {
        try (FileWriter fw = new FileWriter(BOOKINGS_FILE, true)) {
            fw.write(customerName + " booked in Room " + roomNumber + ", from " + checkInDate + " to " + checkOutDate + ".\n");
        } catch (IOException e) {
            System.out.println("\nError occurred when saving booking data!");
        }
    }

    // Cancels a booking by removing the matching booking line from the file
    @Override
    public boolean cancelBooking(String customerName, int roomNumber) {
        boolean bookingFound = false;
        StringBuilder updatedBookings = new StringBuilder();

        try (Scanner fileScanner = new Scanner(new File(BOOKINGS_FILE))) {
            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();

                // Checks if this line matches the customer and room number exactly
                String expectedPrefix = customerName + " booked in Room " + roomNumber + ",";
                if (line.startsWith(expectedPrefix)) {
                    bookingFound = true; // Mark as found and skip adding it to the updated list
                }
                else{
                    updatedBookings.append(line + "\n"); // Keep all other bookings
                }
            }
        } catch (IOException e) {
            System.out.println("\nError occurred when reading bookings file!");
        }

        // If booking was found, overwrite the file with the remaining bookings
        if(bookingFound) {
            try(FileWriter writer = new FileWriter(BOOKINGS_FILE)){
                writer.write(updatedBookings.toString());
            }
            catch (IOException e) {
                System.out.println("\nError occurred when writing bookings file!");
            }
        }

        return bookingFound; // Return whether the booking was successfully found and removed
    }

    // Retrieves a list of all bookings for a given customer
    @Override
    public List<String> getCustomerBookings(String customerName) {
        List<String> bookings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(BOOKINGS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Only adds the line if it starts with the customer's full name
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
