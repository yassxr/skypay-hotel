// HotelReservationTest.java
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.RoomType;
import service.Service;

public class HotelReservationTest {
    public static void main(String[] args) {
        Service service = new Service();
        
        try {
            System.out.println("=== HOTEL RESERVATION SYSTEM TEST ===\n");
            
            // Create rooms
            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setRoom(2, RoomType.JUNIOR, 2000);
            service.setRoom(3, RoomType.SUITE, 3000);
            
            // Create users
            service.setUser(1, 5000);
            service.setUser(2, 10000);
            
            System.out.println("\n=== BOOKING ATTEMPTS ===");
            
            // Create dates for testing
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse("30/06/2026");
            Date date2 = sdf.parse("07/07/2026");
            Date date3 = sdf.parse("08/07/2026");
            Date date4 = sdf.parse("09/07/2026");
            
            // Test bookings
            service.bookRoom(1, 2, date1, date2); // User 1, Room 2, 7 nights
            service.bookRoom(1, 2, date2, date1); // Invalid date range
            service.bookRoom(1, 1, date2, date3); // User 1, Room 1, 1 night
            service.bookRoom(2, 1, date2, date4); // User 2, Room 1, 2 nights (should conflict)
            service.bookRoom(2, 3, date2, date3); // User 2, Room 3, 1 night
            
            // Update room 1
            System.out.println("\n=== UPDATING ROOM 1 ===");
            service.setRoom(1, RoomType.SUITE, 10000);
            
            // Print results
            service.printAll();
            service.printAllUsers();
            
        } catch (Exception e) {
            System.out.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
