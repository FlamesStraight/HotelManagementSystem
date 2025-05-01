public class Room
{
    private int roomNumber;
    private String roomType;
    private String availableUntil;

    public Room(int roomNumber, String roomType, String availableUntil) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.availableUntil = availableUntil;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(String availableUntil) {
        this.availableUntil = availableUntil;
    }

    public String getRoomInfo() {
        return "Room " + roomNumber + ", " + roomType + " Room, " + availableUntil;
    }

    @Override
    public String toString() {
        return getRoomInfo();
    }
}
