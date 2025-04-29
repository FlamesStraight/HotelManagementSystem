/* import java.io.*;
import java.util.*;

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
            System.out.println("Error occurred when adding customer data!");
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
            System.out.println("Error occurred when saving customer data!");
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

    public static List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("rooms.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] roomInfo = line.split(",");
                    if (roomInfo.length >= 3) {
                        int roomNumber = Integer.parseInt(roomInfo[0].trim());
                        String roomType = roomInfo[1].trim();
                        String availableUntil = roomInfo[2].trim();
                        rooms.add(new Room(roomNumber, roomType, availableUntil));
                    } else {
                        System.out.println("Room information is incorrect!");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from room file.");
        }
        return rooms;
    }

    public static void saveRoom(String roomType, int roomNumber){
        try (FileWriter fw = new FileWriter("rooms.txt", true)){
            fw.write(roomType + ", " + roomNumber + "\n");
        }
        catch(IOException e){
            System.out.println("\nError occurred when saving room data!");
        }
    }

    public static void saveBooking(String customerName, int roomNumber, String checkInDate, String checkOutDate){
        try (FileWriter fw = new FileWriter("bookings.txt", true)){
            fw.write(customerName + ", " + roomNumber + ", " + checkInDate + ", " + checkOutDate + "\n");
        }
        catch(IOException e){
            System.out.println("\nError occurred when saving booking data!");
        }
    }

    public static List<String> getBookingsForCustomer(String customerName){
        List<String> bookings = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File("bookings.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.toLowerCase().contains(customerName.toLowerCase())){
                    bookings.add(line);
                }
            }
        }
        catch(IOException e){
            System.out.println("\nError occurred when reading from bookings file!");
        }
        return bookings;
    }

    public static void updateRoomAvailability(int roomNumber, String availability){
        List<Room> rooms = loadRooms();
        boolean updated = false;

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setAvailableUntil(availability);
                updated = true;
                break;
            }
        }

        if (updated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("rooms.txt"))) {
                for (Room room : rooms) {
                    writer.println(room.getRoomNumber() + ", " + room.getRoomType() + ", " + room.getAvailableUntil());
                }
            } catch (IOException e) {
                System.out.println("Error writing to rooms file: " + e.getMessage());
            }
        } else {
            System.out.println("Room does not exist.");
        }
    }
}

 */
