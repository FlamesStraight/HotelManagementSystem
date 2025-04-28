import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private Map<String, Map<Integer, Room>> rooms;

    public RoomManager() {
        rooms = new HashMap<>();
        initializeRooms();
    }

    private void initializeRooms() {
        try (BufferedReader reader = new BufferedReader(new FileReader("rooms.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    int roomNumber = Integer.parseInt(parts[0]);
                    String roomType = parts[1].trim();
                    String availableUntil = parts[2].replace("Available until ", "").trim();

                    Room room = new Room(roomNumber, roomType, availableUntil);

                    rooms.computeIfAbsent(roomType, k -> new HashMap<>()).put(roomNumber, room);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading rooms file.");
        }
    }


    public Room roomDetailsChecker(String roomType) {
        roomType = roomType.trim();

        if(!rooms.containsKey(roomType)) {
            System.out.println("\nRoom " + roomType + " is not available at this hotel.");
            return null;
        }

        Map<Integer, Room> roomList = rooms.get(roomType);

        for(Room room : roomList.values()){
            if(room.getAvailableUntil().compareTo("9999-99-99") > 0){
                System.out.println("Room " + room.getRoomNumber() + " is available.");
                return room;
            }
        }

        System.out.println("\nNo room found for type " + roomType);
        return null;
    }
}
