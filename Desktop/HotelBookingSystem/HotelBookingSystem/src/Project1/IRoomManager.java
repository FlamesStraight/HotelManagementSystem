package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.util.List;

// Interface for managing room-related operations
public interface IRoomManager {

    // Saves a new room to the rooms file
    void saveRoom(Room room);

    // Updates the availability status of a specific room
    void updateRoomAvailability(int roomNumber, String availability);

    // Returns a list of available rooms filtered by the given room type
    List<Room> getAvailableRoomsByType(String roomType);
}

