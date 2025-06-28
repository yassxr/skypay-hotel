// entity/Booking.java
package entity;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;
    private int totalCost;
    private int nights;
    private Date bookingDate;
    
    // Snapshot of room and user data at booking time
    private RoomType roomTypeAtBooking;
    private int roomPriceAtBooking;
    private int userBalanceAfterBooking;
    
    public Booking(int bookingId, int userId, int roomNumber, Date checkIn, Date checkOut, 
                   int totalCost, int nights, RoomType roomType, int roomPrice, int userBalance) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
        this.nights = nights;
        this.bookingDate = new Date();
        this.roomTypeAtBooking = roomType;
        this.roomPriceAtBooking = roomPrice;
        this.userBalanceAfterBooking = userBalance;
    }
    
    // Getters
    public int getBookingId() { 
        return bookingId; 
    }
    
    public int getUserId() { 
        return userId; 
    }
    
    public int getRoomNumber() { 
        return roomNumber; 
    }
    
    public Date getCheckIn() { 
        return checkIn; 
    }
    
    public Date getCheckOut() { 
        return checkOut; 
    }
    
    public int getTotalCost() { 
        return totalCost; 
    }
    
    public int getNights() { 
        return nights; 
    }
    
    public Date getBookingDate() { 
        return bookingDate; 
    }
    
    public RoomType getRoomTypeAtBooking() { 
        return roomTypeAtBooking; 
    }
    
    public int getRoomPriceAtBooking() { 
        return roomPriceAtBooking; 
    }
    
    public int getUserBalanceAfterBooking() { 
        return userBalanceAfterBooking; 
    }
    
    // Check if booking overlaps with given period
    public boolean overlaps(Date checkIn, Date checkOut) {
        return !(this.checkOut.before(checkIn) || this.checkIn.after(checkOut));
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Booking{id=" + bookingId + 
               ", userId=" + userId + 
               ", roomNumber=" + roomNumber +
               ", checkIn=" + sdf.format(checkIn) + 
               ", checkOut=" + sdf.format(checkOut) +
               ", nights=" + nights +
               ", totalCost=" + totalCost +
               ", roomType=" + roomTypeAtBooking +
               ", roomPrice=" + roomPriceAtBooking +
               ", userBalanceAfter=" + userBalanceAfterBooking + "}";
    }
}