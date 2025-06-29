package ma.skypay;

import ma.skypay.enums.RoomType;
import ma.skypay.service.Service;

import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();

        try {
            // Create test dates
            Calendar cal = Calendar.getInstance();

            // 30/06/2026
            cal.set(2026, 5, 30); // Month is 0-based
            Date date1 = cal.getTime();

            // 07/07/2026
            cal.set(2026, 6, 7);
            Date date2 = cal.getTime();

            // 08/07/2026
            cal.set(2026, 6, 8);
            Date date3 = cal.getTime();

            // 09/07/2026
            cal.set(2026, 6, 9);
            Date date4 = cal.getTime();

            System.out.println("=== HOTEL RESERVATION SYSTEM TEST ===\n");

            // Create 3 rooms
            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setRoom(2, RoomType.JUNIOR, 2000);
            service.setRoom(3, RoomType.SUITE, 3000);

            // Create 2 users
            service.setUser(1, 5000);
            service.setUser(2, 10000);

            System.out.println("\n=== BOOKING ATTEMPTS ===");

            // User 1 tries booking Room 2 from 30/06/2026 to 07/07/2026 (7 nights)
            System.out.println("\n1. User 1 booking Room 2 (30/06/2026 to 07/07/2026):");
            service.bookRoom(1, 2, date1, date2);

            // User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026 (invalid dates)
            System.out.println("\n2. User 1 booking Room 2 (07/07/2026 to 30/06/2026):");
            service.bookRoom(1, 2, date2, date1);

            // User 1 tries booking Room 1 from 07/07/2026 to 08/07/2026 (1 night)
            System.out.println("\n3. User 1 booking Room 1 (07/07/2026 to 08/07/2026):");
            service.bookRoom(1, 1, date2, date3);

            // User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights)
            System.out.println("\n4. User 2 booking Room 1 (07/07/2026 to 09/07/2026):");
            service.bookRoom(2, 1, date2, date4);

            // User 2 tries booking Room 3 from 07/07/2026 to 08/07/2026 (1 night)
            System.out.println("\n5. User 2 booking Room 3 (07/07/2026 to 08/07/2026):");
            service.bookRoom(2, 3, date2, date3);

            // setRoom(1, suite, 10000)
            System.out.println("\n6. Updating Room 1 to Suite with price 10000:");
            service.setRoom(1, RoomType.SUITE, 10000);

            System.out.println("\n" + "=".repeat(50));
            service.printAll();

            System.out.println("\n" + "=".repeat(50));
            service.printAllUsers();

        } catch (Exception e) {
            System.out.println("Error in test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}