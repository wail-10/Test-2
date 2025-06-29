package ma.skypay.entity;

import ma.skypay.enums.RoomType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;
    private int totalCost;
    private Date bookingDate;
    private RoomType roomTypeAtBooking;
    private int pricePerNightAtBooking;
    private int userBalanceAtBooking;

    private static int bookingCounter = 1;

    public Booking(int userId, int roomNumber, Date checkIn, Date checkOut,
                   int totalCost, RoomType roomType, int pricePerNight, int userBalance) {
        this.bookingId = bookingCounter++;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
        this.bookingDate = new Date();
        this.roomTypeAtBooking = roomType;
        this.pricePerNightAtBooking = pricePerNight;
        this.userBalanceAtBooking = userBalance;
    }

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

    public Date getBookingDate() {
        return bookingDate;
    }

    public RoomType getRoomTypeAtBooking() {
        return roomTypeAtBooking;
    }

    public int getPricePerNightAtBooking() {
        return pricePerNightAtBooking;
    }

    public int getUserBalanceAtBooking() {
        return userBalanceAtBooking;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Booking{bookingId=" + bookingId + ", userId=" + userId +
                ", roomNumber=" + roomNumber + ", checkIn=" + sdf.format(checkIn) +
                ", checkOut=" + sdf.format(checkOut) + ", totalCost=" + totalCost +
                ", roomType=" + roomTypeAtBooking + ", pricePerNight=" + pricePerNightAtBooking +
                ", userBalanceAtBooking=" + userBalanceAtBooking + "}";
    }
}
