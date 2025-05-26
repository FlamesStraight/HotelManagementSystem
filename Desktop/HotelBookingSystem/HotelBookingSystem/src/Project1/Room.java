package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

public class Room
{
    private int roomNumber;
    private RoomTypes roomType;
    private String availability;

    // Room constructor to initialize a new booking with fields above
    public Room(int roomNumber, RoomTypes roomType, String availability) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.availability = availability;
    }

    // Getters and setters methods for a Room object
    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomTypes getRoomType() {
        return roomType;
    }

    public String getAvailability() {
        return availability;
    }

    public void setRoomType(RoomTypes roomType) {
        this.roomType = roomType;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getRoomInfo() {
        return "Room " + roomNumber + ", " + roomType.getRoomName() + " Room, " + availability;
    }

    @Override
    public String toString() {
        return getRoomInfo();
    }
}