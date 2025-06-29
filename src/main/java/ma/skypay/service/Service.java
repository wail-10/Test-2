package ma.skypay.service;

import ma.skypay.entity.Booking;
import ma.skypay.entity.Room;
import ma.skypay.entity.User;
import ma.skypay.enums.RoomType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class Service {
    ArrayList<Room> rooms;
    ArrayList<User> users;
    ArrayList<Booking> bookings;

    public Service() {
        rooms = new ArrayList<>();
        users = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        try {
            // Check if room already exists
            Room existingRoom = findRoomByNumber(roomNumber);
            if (existingRoom != null) {
                // Update existing room
                existingRoom.setRoomType(roomType);
                existingRoom.setPricePerNight(roomPricePerNight);
                System.out.println("Room " + roomNumber + " updated successfully.");
            } else {
                // Create new room
                Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
                rooms.add(newRoom);
                System.out.println("Room " + roomNumber + " created successfully.");
            }
        } catch (Exception e) {
            System.out.println("Error setting room: " + e.getMessage());
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        try {
            // Validate inputs
            if (checkIn.after(checkOut)) {
                throw new IllegalArgumentException("Check-in date cannot be after check-out date");
            }

            // Find user and room
            User user = findUserById(userId);
            Room room = findRoomByNumber(roomNumber);

            if (user == null) {
                throw new IllegalArgumentException("User with ID " + userId + " not found");
            }
            if (room == null) {
                throw new IllegalArgumentException("Room with number " + roomNumber + " not found");
            }

            // Calculate nights and total cost
            long nights = calculateNights(checkIn, checkOut);
            int totalCost = (int) (nights * room.getPricePerNight());

            // Check if user has enough balance
            if (user.getBalance() < totalCost) {
                throw new IllegalArgumentException("Insufficient balance. Required: " + totalCost +
                        ", Available: " + user.getBalance());
            }

            // Check if room is available
            if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
                throw new IllegalArgumentException("Room " + roomNumber + " is not available for the specified period");
            }

            // Create booking and update user balance
            Booking booking = new Booking(userId, roomNumber, checkIn, checkOut, totalCost,
                    room.getRoomType(), room.getPricePerNight(), user.getBalance());
            bookings.add(booking);
            user.setBalance(user.getBalance() - totalCost);

            System.out.println("Booking successful! Booking ID: " + booking.getBookingId());

        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    public void printAll() {
        System.out.println("=== ALL ROOMS AND BOOKINGS (Latest to Oldest) ===");

        // Print rooms (latest to oldest)
        System.out.println("\nROOMS:");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            System.out.println(rooms.get(i));
        }

        // Print bookings (latest to oldest)
        System.out.println("\nBOOKINGS:");
        ArrayList<Booking> sortedBookings = new ArrayList<>(bookings);
        sortedBookings.sort((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()));
        for (Booking booking : sortedBookings) {
            System.out.println(booking);
        }
    }

    public void setUser(int userId, int balance) {
        try {
            if (balance < 0) {
                throw new IllegalArgumentException("Balance cannot be negative");
            }

            User existingUser = findUserById(userId);
            if (existingUser != null) {
                existingUser.setBalance(balance);
                System.out.println("User " + userId + " balance updated to " + balance);
            } else {
                User newUser = new User(userId, balance);
                users.add(newUser);
                System.out.println("User " + userId + " created with balance " + balance);
            }
        } catch (Exception e) {
            System.out.println("Error setting user: " + e.getMessage());
        }
    }

    public void printAllUsers() {
        System.out.println("=== ALL USERS (Latest to Oldest) ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            System.out.println(users.get(i));
        }
    }

    // Helper methods
    private User findUserById(int userId) {
        return users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
    }

    private Room findRoomByNumber(int roomNumber) {
        return rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst().orElse(null);
    }

    private long calculateNights(Date checkIn, Date checkOut) {
        LocalDate checkInLocal = checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOutLocal = checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(checkInLocal, checkOutLocal);
    }

    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                // Check for date overlap
                if (!(checkOut.before(booking.getCheckIn()) || checkIn.after(booking.getCheckOut()))) {
                    return false;
                }
            }
        }
        return true;
    }

}
