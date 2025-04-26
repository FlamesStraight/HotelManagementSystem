
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomManager {
    private HashMap<Integer, Room> rooms = new HashMap<>();

    public void addRoom(Room room) {
        rooms.put(room.getRoomNumber(), room); // Correct: adds to the HashMap called 'rooms.txt'
    }


    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public List<Room> getAvailableRoomsByType(String type) {
        List<Room> filteredRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (room.isAvailable() && room.getType().equalsIgnoreCase(type)) {
                filteredRooms.add(room);
            }
        }
        return filteredRooms;
    }

    public boolean bookRoom(int roomNumber){
        Room room = rooms.get(roomNumber);
        if (room != null && room.isAvailable()) {
            room.setAvailable(false);
            return true;
        }
        return false;
    }

    public Room getRoom(int roomNumber) {
        return rooms.get(roomNumber);
    }
}
