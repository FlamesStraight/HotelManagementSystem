package Project1;

/**
 *
 * @author wgv5947 & jkn2963
 */

//RoomTypes interface and the implements below for different room types in the hotel's system
//Helps with future extension, i.e, for when new room types are added to the system
public interface RoomTypes {
    String getRoomName();
}

class SingleRoom implements RoomTypes {
    @Override
    public String getRoomName(){
        return "Single";
    }
}

class DoubleRoom implements RoomTypes{
    @Override
    public String getRoomName(){
        return "Double";
    }
}

class DeluxeRoom implements RoomTypes{
    @Override
    public String getRoomName(){
        return "Deluxe";
    }
}

class FamilyRoom implements RoomTypes{
    @Override
    public String getRoomName(){
        return "Family";
    }
}

class ExecutiveRoom implements RoomTypes{
    @Override
    public String getRoomName(){
        return "Executive";
    }
}
