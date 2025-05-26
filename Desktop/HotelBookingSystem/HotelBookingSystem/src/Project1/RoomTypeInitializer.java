package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

//RoomTypeInitializer class for registering all available room types, makes it open for extension
public class RoomTypeInitializer {
    public static void initializeTypes(){
        RoomTypeRegistry.registerRoomType(new SingleRoom());
        RoomTypeRegistry.registerRoomType(new DoubleRoom());
        RoomTypeRegistry.registerRoomType(new DeluxeRoom());
        RoomTypeRegistry.registerRoomType(new FamilyRoom());
        RoomTypeRegistry.registerRoomType(new ExecutiveRoom());
    }
}

