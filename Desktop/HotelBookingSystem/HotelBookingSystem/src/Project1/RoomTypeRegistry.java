package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

import java.util.HashMap;
import java.util.Map;

//RoomTypeRegistry class manages available room types in the Hotel Booking System
public class RoomTypeRegistry {
    private static final Map<String, RoomTypes> roomTypes = new HashMap<>();

    //Registers a new room type to the system
    public static void registerRoomType(RoomTypes type){
        roomTypes.put(type.getRoomName().toLowerCase(), type);
    }

    //Checks if given roomType already exists within the system
    public static boolean isValid(String name){
        return roomTypes.containsKey(name.toLowerCase());
    }

    //Outputs the proper name of the given roomType
    public static RoomTypes getTypeName(String name){
        return roomTypes.get(name.toLowerCase());
    }

    //Outputs a list of all registered room types
    public static String listTypes(){
        return String.join("/", roomTypes.keySet());
    }
}
