import java.io.*;
import java.util.*;

public class RoomManager {
    private List<Room> rooms;
    public static final String ROOM_FILE = "rooms.txt";

    public RoomManager() {
        rooms = loadRooms();
    }

    public void saveRoom(Room room) {
        try (FileWriter writer = new FileWriter(ROOM_FILE, true)) {
            writer.write(room.getRoomNumber() + ", " + room.getRoomType() + ", " + room.getAvailableUntil() + "\n");
        } catch (IOException e) {
            System.out.println("\nError occurred when saving room.");
        }
    }

    public void updateRoomAvailability(int roomNumber, String newAvailability) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setAvailableUntil(newAvailability);
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(ROOM_FILE)) {
            for (Room r : rooms) {
                writer.println(r.getRoomNumber() + ", " + r.getRoomType() + ", " + r.getAvailableUntil());
            }
        } catch (IOException e) {
            System.out.println("\nError occurred when writing to room file.");
        }
    }

    private List<Room> loadRooms() {
        List<Room> roomList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(ROOM_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 3) {
                    int number = Integer.parseInt(parts[0].trim());
                    String type = parts[1].trim();
                    String available = parts[2].trim();
                    roomList.add(new Room(number, type, available));
                }
            }
        } catch (IOException e) {
            System.out.println("\nError occurred when loading rooms.");
        }
        return roomList;
    }

    public List<Room> getAvailableRoomsByType(String roomType) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType) &&
                room.getAvailableUntil().toLowerCase().contains("available")){
                available.add(room);
            }
        }
        return available;
    }

}