# Test 2 - Hotel Reservation System

## Question 1:

No, this is not the recommended approach. Putting all functions in a single service violates the Single Responsibility Principle 
and creates poor maintainability as the system grows. It results in tight coupling between different functionalities,
makes it hard to test individual components, and prevents independent scaling of different operations. 
A better approach would be to separate concerns into different services like UserService for user operations, 
RoomService for room operations, and BookingService for booking operations that coordinates with other services. 
This provides better organization, easier testing, independent scaling, and code reusability.

## Question 2:

Another way would be to allow setRoom(..) to update all existing bookings with the new room information, 
so that when room properties change, all past and future bookings reflect the updated values. 
My recommendation is Room Versioning - creating a new room version instead of updating the existing one. 
This preserves historical accuracy by keeping the original room data for past bookings while using the new version 
for future bookings, providing a complete audit trail and supporting real-world scenarios like price changes 
over time.

## Output example :

![img.png](img.png)