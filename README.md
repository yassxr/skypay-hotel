# Hotel Reservation System

A simplified Java-based hotel reservation system that manages rooms, users, and bookings with core business logic implementation.

## 🏗️ System Architecture

### Entities

#### User Entity
- **Purpose**: Represents customers who can make bookings
- **Attributes**: 
  - `userId`: Unique identifier
  - `balance`: Available funds for bookings
  - `createdAt`: Timestamp for creation tracking

#### Room Entity
- **Purpose**: Represents hotel rooms available for booking
- **Attributes**:
  - `roomNumber`: Unique room identifier
  - `roomType`: Enum (STANDARD, JUNIOR, SUITE)
  - `roomPricePerNight`: Cost per night
  - `createdAt`: Timestamp for creation tracking

#### Booking Entity
- **Purpose**: Represents a reservation with historical data preservation
- **Attributes**:
  - Booking details: ID, user, room, dates, cost
  - **Snapshot data**: Room type and price at booking time
  - User balance after booking
  - Booking creation timestamp

### Service Layer
- **Service.java**: Central service handling all business operations
- Uses ArrayList collections for data storage (no repositories as per requirements)
- Implements comprehensive validation and error handling

## 🚀 Example

```java
Service service = new Service();

// Create rooms
service.setRoom(1, RoomType.STANDARD, 1000);
service.setRoom(2, RoomType.JUNIOR, 2000);

// Create users
service.setUser(1, 5000);
service.setUser(2, 10000);

// Make bookings
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
Date checkIn = sdf.parse("30/06/2026");
Date checkOut = sdf.parse("07/07/2026");
service.bookRoom(1, 2, checkIn, checkOut);

// Print results
service.printAll();
service.printAllUsers();
```

## 🏆 Design Questions (Bonus)

### 1/- Suppose we put all the functions inside the same service. Is this the recommended approach ? Please explain.

**No, because this would go against several design principles:**

#### Problems with Current Approach:
- **Single Responsibility Principle Violation**: One service handles user, room, and booking operations
- **Tight Coupling**: All operations are interdependent
- **Scalability Issues**: Difficult to scale individual components
- **Testing Complexity**: Hard to unit test individual functionalities
- **Maintenance Burden**: Changes in one area affect others

#### Recommended Alternative Architecture:

```
├── UserService
│   ├── createUser()
│   ├── updateUser()
│   └── validateBalance()
├── RoomService
│   ├── createRoom()
│   ├── updateRoom()
│   └── checkAvailability()
├── BookingService
│   ├── createBooking()
│   ├── validateBooking()
│   └── checkConflicts()
└── ReservationFacade
    └── coordinateBooking() // Orchestrates all services
```

#### Benefits of Separation:
- **Single Responsibility**: Each service has one clear purpose
- **Loose Coupling**: Services can evolve independently
- **Testability**: Easy to mock and test individual components
- **Reusability**: Services can be reused in different contexts
- **Scalability**: Can scale services independently based on load

### 2/- In this design, we chose to have a function setRoom(..) that should not impact the previous bookings. What is another way ? What is your recommendation ? Please explain and justify

#### Alternative 1: Update all Bookings
```java
// Update existing bookings when room changes
updateExistingBookings(roomNumber, newPrice, newType);
```


#### Alternative 2: Immutable Rooms
```java
// Create new room instead of updating
if (roomExists) {
    throw new Exception("Cannot modify existing room");
}
```


#### Alternative 3: Room Versioning
```java
class Room {
    private int version;
    private Date effectiveFrom;
    // Create new version for changes
}
```

### Current Approach: Snapshot Pattern

The current implementation using the **Snapshot Pattern** is the optimal solution for this business domain because:

1. **Real-World Alignment**: Hotels don't change past booking prices
2. **Regulatory Compliance**: Maintains accurate financial records
3. **Customer Trust**: Bookings remain as originally agreed
4. **Simplicity**: Easy to implement and understand
5. **Performance**: No complex queries for historical data

## 📁 Project Structure

```
skypay-hotel/
├── entity/
│   ├── Booking.java
│   ├── Room.java
│   ├── RoomType.java
│   └── User.java
├── service/
│   └── Service.java
├── HotelReservationTest.java
└── README.md
```

## 🛠️ Setup & Running

1. **Prerequisites**: Java 8 or higher
2. **Compilation**: 
   ```bash
   javac -d . entity/*.java service/*.java HotelReservationTest.java
   ```
3. **Execution**:
   ```bash
   java HotelReservationTest
   ```
