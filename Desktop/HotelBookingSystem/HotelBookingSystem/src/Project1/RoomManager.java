package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.io.*;
import java.util.*;

// RoomManager handles loading, saving, and updating rooms
public class RoomManager implements IRoomManager{
    private final List<Room> rooms;
    public static final String ROOM_FILE = "./hotelbookingresources/rooms.txt";

    public RoomManager() {
        rooms = loadRooms();
    }

    // Loads room data from "rooms.txt" and returns a list of Room objects
    private List<Room> loadRooms() {
        List<Room> roomList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(ROOM_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 3) {
                    int number = Integer.parseInt(parts[0].trim());
                    String type = parts[1].trim();
                    String available = parts[2].trim();

                    // Validates room type using registry, then adds it to the list
                    if(RoomTypeRegistry.isValid(type)){
                        RoomTypes roomTypes = RoomTypeRegistry.getTypeName(type);
                        roomList.add(new Room(number, roomTypes, available));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(" ️Room text file could not be be found. ️");
        }
        return roomList;
    }

    // Saves a new room entry to "rooms.txt"
    @Override
    public void saveRoom(Room room) {
        try (FileWriter writer = new FileWriter(ROOM_FILE, true)) {
            writer.write(room.getRoomNumber() + ", " + room.getRoomType().getRoomName() + ", " + room.getAvailability() + "\n");
        } catch (IOException e) {
            System.out.println("Error occurred when saving room.");
        }
    }

    // Updates the availability of a specific room
    @Override
    public void updateRoomAvailability(int roomNumber, String newAvailability) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setAvailability(newAvailability); // Sets new availability
                break;
            }
        }

        // Overwrites the rooms file with updated room availabilities
        try (PrintWriter writer = new PrintWriter(ROOM_FILE)) {
            for (Room r : rooms) {
                writer.println(r.getRoomNumber() + ", " + r.getRoomType().getRoomName() + ", " + r.getAvailability());
            }
        } catch (IOException e) {
            System.out.println("Error occurred when writing to room file.");
        }
    }

    // Returns all rooms of the given type that are currently available
    @Override
    public List<Room> getAvailableRoomsByType(String typeName) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType().getRoomName().equalsIgnoreCase(typeName) &&
                room.getAvailability().toLowerCase().contains("available")){
                available.add(room);
            }
        }
        return available;
    }
}
