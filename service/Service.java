// service/Service.java
package service;

import entity.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Service {
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private ArrayList<Booking> bookings;
    private int nextBookingId;
    
    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.nextBookingId = 1;
    }
    
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        try {
            if (roomPricePerNight < 0) {
                throw new IllegalArgumentException("Room price cannot be negative");
            }
            
            // Find existing room
            Room existingRoom = findRoomByNumber(roomNumber);
            
            if (existingRoom != null) {
                // Update existing room
                existingRoom.setRoomType(roomType);
                existingRoom.setRoomPricePerNight(roomPricePerNight);
                System.out.println("Room " + roomNumber + " updated successfully");
            } else {
                // Create new room
                Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
                rooms.add(newRoom);
                System.out.println("Room " + roomNumber + " created successfully");
            }
        } catch (Exception e) {
            System.out.println("Error setting room: " + e.getMessage());
        }
    }
    
    public void setUser(int userId, int balance) {
        try {
            if (balance < 0) {
                throw new IllegalArgumentException("User balance cannot be negative");
            }
            
            User existingUser = findUserById(userId);
            
            if (existingUser != null) {
                existingUser.setBalance(balance);
                System.out.println("User " + userId + " updated successfully");
            } else {
                User newUser = new User(userId, balance);
                users.add(newUser);
                System.out.println("User " + userId + " created successfully");
            }
        } catch (Exception e) {
            System.out.println("Error setting user: " + e.getMessage());
        }
    }
    
    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        try {
            // Validate dates
            if (checkIn.after(checkOut) || checkIn.equals(checkOut)) {
                System.out.println("Booking failed: Invalid date range. Check-in must be before check-out.");
                return;
            }
            
            // Find user and room
            User user = findUserById(userId);
            Room room = findRoomByNumber(roomNumber);
            
            if (user == null) {
                System.out.println("Booking failed: User " + userId + " not found");
                return;
            }
            
            if (room == null) {
                System.out.println("Booking failed: Room " + roomNumber + " not found");
                return;
            }
            
            // Calculate nights and cost
            int nights = calculateNights(checkIn, checkOut);
            int totalCost = nights * room.getRoomPricePerNight();
            
            // Check user balance
            if (user.getBalance() < totalCost) {
                System.out.println("Booking failed: Insufficient balance. Required: " + totalCost + ", Available: " + user.getBalance());
                return;
            }
            
            // Check room availability
            if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
                System.out.println("Booking failed: Room " + roomNumber + " is not available for the specified period");
                return;
            }
            
            // Create booking
            user.deductBalance(totalCost);
            Booking booking = new Booking(nextBookingId++, userId, roomNumber, checkIn, checkOut, 
                                        totalCost, nights, room.getRoomType(), room.getRoomPricePerNight(), user.getBalance());
            bookings.add(booking);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Booking successful: Room " + roomNumber + 
                             " booked for user " + userId + 
                             " from " + sdf.format(checkIn) + 
                             " to " + sdf.format(checkOut) + 
                             " (" + nights + " nights) - Total cost: " + totalCost);
            
        } catch (Exception e) {
            System.out.println("Error during booking: " + e.getMessage());
        }
    }

    public void printAll() {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("    ALL ROOMS AND BOOKINGS (Latest to Oldest)");
    System.out.println("=".repeat(60));
    
    // Print rooms (latest to oldest)
    System.out.println("\n--- ROOMS ---");
    List<Room> sortedRooms = new ArrayList<>(rooms);
    sortedRooms.sort((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()));
    
    if (sortedRooms.isEmpty()) {
        System.out.println("  No rooms available.");
    } else {
        for (Room room : sortedRooms) {
            System.out.println("  " + room);
        }
    }
    
    // Print bookings (latest to oldest)
    System.out.println("\n--- BOOKINGS ---");
    List<Booking> sortedBookings = new ArrayList<>(bookings);
    sortedBookings.sort((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()));
    
    if (sortedBookings.isEmpty()) {
        System.out.println("  No bookings found.");
    } else {
        for (int i = 0; i < sortedBookings.size(); i++) {
            if (i > 0) System.out.println(); // Add space between bookings
            System.out.println("  " + sortedBookings.get(i));
        }
    }
    
    System.out.println("\n" + "=".repeat(60));
}

public void printAllUsers() {
    System.out.println("\n" + "=".repeat(40));
    System.out.println("    ALL USERS (Latest to Oldest)");
    System.out.println("=".repeat(40));
    
    List<User> sortedUsers = new ArrayList<>(users);
    sortedUsers.sort((u1, u2) -> u2.getCreatedAt().compareTo(u1.getCreatedAt()));
    
    if (sortedUsers.isEmpty()) {
        System.out.println("  No users registered.");
    } else {
        for (User user : sortedUsers) {
            System.out.println("  " + user);
        }
    }
    
    System.out.println("=".repeat(40));
}
    
    // Helper methods
    private User findUserById(int userId) {
        return users.stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);
    }
    
    private Room findRoomByNumber(int roomNumber) {
        return rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst().orElse(null);
    }
    
    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        return bookings.stream()
                .filter(b -> b.getRoomNumber() == roomNumber)
                .noneMatch(b -> b.overlaps(checkIn, checkOut));
    }
    
    private int calculateNights(Date checkIn, Date checkOut) {
        long diffInMillies = checkOut.getTime() - checkIn.getTime();
        return (int) (diffInMillies / (1000 * 60 * 60 * 24));
    }
}