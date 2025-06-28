// entity/Room.java
package entity;

import java.util.Date;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private int roomPricePerNight;
    private Date createdAt;
    
    public Room(int roomNumber, RoomType roomType, int roomPricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomPricePerNight = roomPricePerNight;
        this.createdAt = new Date();
    }
    
    // Getters and Setters
    public int getRoomNumber() { 
        return roomNumber; 
    }
    
    public RoomType getRoomType() { 
        return roomType; 
    }
    
    public void setRoomType(RoomType roomType) { 
        this.roomType = roomType; 
    }
    
    public int getRoomPricePerNight() { 
        return roomPricePerNight; 
    }
    
    public void setRoomPricePerNight(int roomPricePerNight) { 
        this.roomPricePerNight = roomPricePerNight; 
    }
    
    public Date getCreatedAt() { 
        return createdAt; 
    }
    
    @Override
    public String toString() {
        return "Room{roomNumber=" + roomNumber + 
               ", type=" + roomType + 
               ", pricePerNight=" + roomPricePerNight + "}";
    }
}