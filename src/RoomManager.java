import java.util.*;

public class RoomManager {
    private List<Room> rooms;

    public RoomManager() {
        rooms = FileManager.loadRooms();
    }

    public void roomDetailsChecker(String roomType) {
        roomType = roomType.trim().toLowerCase();

        switch (roomType.toLowerCase()){
            case "single":
                System.out.println("Checking for Single type rooms...");
                break;
            case "double":
                System.out.println("Checking for Double type rooms...");
                break;
            case "deluxe":
                System.out.println("Checking for Deluxe type rooms...");
                break;
            case "family":
                System.out.println("Checking for Family type rooms...");
                break;
            case "executive":
                System.out.println("Checking for Executive type rooms...");
                break;
            default:
                System.out.println("Room type not recognized.");
                return;
        }

        List<Room> filteredRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType)) {
                filteredRooms.add(room);
            }
        }

        if (filteredRooms.isEmpty()) {
            System.out.println("No " + roomType + " rooms available.");
            return;
        }

        filteredRooms.sort(Comparator.comparingInt(Room::getRoomNumber));

        System.out.println("Available " + roomType + " rooms:");
        for (Room room : filteredRooms) {
            System.out.println("Room " + room.getRoomNumber() + " available until " + room.getAvailableUntil());
        }

    }

    public void bookRoom(String roomType, String customerName, Scanner scanner){
        roomDetailsChecker(roomType);

        System.out.println("\nEnter the Room Number you want to book from the available rooms:");

        int roomNumber = Integer.parseInt(scanner.nextLine());

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.getRoomType().equalsIgnoreCase(roomType)) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom != null) {
            System.out.println("You have selected Room " + selectedRoom.getRoomNumber() + ".");

            System.out.println("Enter Check-In Date (yyyy-mm-dd): ");
            String checkInString = scanner.nextLine();
            System.out.println("Enter Check-Out Date (yyyy-mm-dd): ");
            String checkOutString = scanner.nextLine();

            FileManager.roomFileManager(roomType + ", " + roomNumber);
            FileManager.saveRoom(customerName, roomNumber);

            System.out.println("Booking for Room " + roomNumber + " has been confirmed!");
        } else {
            System.out.println("Invalid room number. Please try again.");
        }

    }

    public List<Room> getRooms(){
        return rooms;
    }
}
