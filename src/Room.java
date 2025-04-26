public class Room
{
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;
    private String type;

    public Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getRoomInfo() {
        return "Room" + roomNumber + " (" + roomType + ") - $" + pricePerNight + " per night - " + (isAvailable ? "Available" : "Not Available");
    }

    public String toFileString() {
        return roomNumber + "," + roomType + "," + pricePerNight + "," + isAvailable;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return getRoomInfo();
    }
}
