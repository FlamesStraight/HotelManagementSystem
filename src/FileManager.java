import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class FileManager {
    public static Map<String, Customer> loadCustomers() {
        Map<String, Customer> customers = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("customers.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] customerInfo = line.split(",");
                    if (customerInfo.length >= 3) {
                        String fullName = customerInfo[0].trim();
                        String email = customerInfo[1].trim();
                        String phoneNumber = customerInfo[2].trim();
                        customers.put(fullName.toLowerCase(), new Customer(fullName, email, phoneNumber));
                    } else {
                        System.out.println("Customer information is incorrect!");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from customer file.");
        }

        return customers;
    }

    public static void addCustomer(Customer customer){
        try(FileWriter fw = new FileWriter("customers.txt", true)){
            fw.write(customer.toFileString() + "\n");
            System.out.println("\nYour information has been successfully added.\n");
        }
        catch(IOException e){
            System.out.println("Error occurred when saving data!");
        }
    }

    public static void updateCustomer(Map<String, Customer> customers){
        try(PrintWriter pw = new PrintWriter(new FileOutputStream("customers.txt"))){
            for (Customer customer : customers.values()){
                pw.println(customer.toFileString());
            }
            System.out.println("\nYour information has been successfully updated.\n");
        }
        catch(IOException e){
            System.out.println("Error occurred when saving data!");
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
