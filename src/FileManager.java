import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;

public class FileManager {
    public static void customerFileManager(String fullInfo){
        HashSet<String> customers = new HashSet<>();

        try {
            FileInputStream customerFIS = new FileInputStream("customers.txt");
            Scanner customerScanner = new Scanner(customerFIS);

            while (customerScanner.hasNextLine()) {
                String line = customerScanner.nextLine().trim();
                customers.add(line.toLowerCase());
            }

            customerScanner.close();
            customerFIS.close();
        } catch (IOException e) {
            System.out.println("No such file or directory.");
        }

        if (customers.contains(fullInfo.toLowerCase())) {
            System.out.println("Customer already exists.");
        } else {
            try {
                FileWriter writer = new FileWriter("customers.txt", true); // append = true
                writer.write(fullInfo + "\n");
                writer.close();
                System.out.println("Customer added.");
            } catch (IOException e) {
                System.out.println("Error saving customer: " + e.getMessage());
            }
        }
    }

    public static void roomFileManager(String fullRoom) {
        HashSet<String> rooms = new HashSet<>();

        try {
            FileInputStream roomFIS = new FileInputStream("rooms.txt");
            Scanner roomScanner = new Scanner(roomFIS);

            while (roomScanner.hasNextLine()) {
                String line = roomScanner.nextLine().trim();
                if (line.startsWith(roomScanner + ",")) {
                    rooms.add(line.split(",")[0].trim());
                }
            }

            roomScanner.close();
            roomFIS.close();
        } catch (IOException e) {
            System.out.println("No such file or directory.");
        }

        String newRoomNumber = fullRoom.split(",")[0].trim();

        if (rooms.contains(newRoomNumber)) {
            System.out.println("Room already exists.");
        } else {
            try {
                FileWriter writer = new FileWriter("rooms.txt", true);
                writer.write(fullRoom + "\n");
                writer.close();
                System.out.println("Room added.");
            } catch (IOException e) {
                System.out.println("Error saving room: " + e.getMessage());
            }
        }
    }

    public static void bookingFileManager(String fullName, String roomNumber, String checkInDate, String checkOutDate) {
        HashSet<String> bookings = new HashSet<>();
        String completeBookingInfo = fullName + ", Room " + roomNumber + ", " + checkInDate + " to " + checkOutDate;

        try{
            FileInputStream bookingFIS = new FileInputStream("bookings.txt");
            Scanner bookingScanner = new Scanner(bookingFIS);

            while (bookingScanner.hasNextLine()) {
                String line = bookingScanner.nextLine().trim();
                bookings.add(line.toLowerCase());
            }

            bookingScanner.close();
            bookingFIS.close();
        } catch(IOException e){
            System.out.println("No such file or directory.");
        }

        if (!bookings.contains(completeBookingInfo.toLowerCase())) {
            try {
                FileWriter writer = new FileWriter("bookings.txt", true);
                writer.write(completeBookingInfo + "\n");
                writer.close();
                System.out.println("Booking successful.");
            } catch (IOException e) {
                System.out.println("Error saving booking: " + e.getMessage());
            }
        } else {
            System.out.println("Booking already exists.");
        }
    }
}
