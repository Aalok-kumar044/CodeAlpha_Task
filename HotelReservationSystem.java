import java.io.*;
import java.util.*;

class Room {
    private int roomNumber;
    private String category;
    private boolean isBooked;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public boolean isBooked() { return isBooked; }
    public void bookRoom() { isBooked = true; }
    public void cancelBooking() { isBooked = false; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - " + (isBooked ? "Booked" : "Available");
    }
}

class Reservation {
    private String customerName;
    private int roomNumber;
    private String category;

    public Reservation(String customerName, int roomNumber, String category) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Reservation: " + customerName + " booked Room " + roomNumber + " (" + category + ")";
    }
}

public class HotelReservationSystem {
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Reservation> reservations = new ArrayList<>();
    private static final String FILE_NAME = "reservations.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeRooms();
        loadReservations();

        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewRooms();
                case 2 -> bookRoom(sc);
                case 3 -> cancelReservation(sc);
                case 4 -> viewReservations();
                case 5 -> {
                    saveReservations();
                    System.out.println("Exiting... Data saved.");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
    }

    private static void viewRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    private static void bookRoom(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter room number to book: ");
        int roomNum = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNum) {
                if (!r.isBooked()) {
                    r.bookRoom();
                    Reservation res = new Reservation(name, roomNum, r.getCategory());
                    reservations.add(res);
                    System.out.println("Booking successful!");
                } else {
                    System.out.println("Room already booked!");
                }
                return;
            }
        }
        System.out.println("Room not found!");
    }

    private static void cancelReservation(Scanner sc) {
        System.out.print("Enter room number to cancel: ");
        int roomNum = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNum && r.isBooked()) {
                r.cancelBooking();
                reservations.removeIf(res -> res.toString().contains("Room " + roomNum));
                System.out.println("Reservation cancelled!");
                return;
            }
        }
        System.out.println("No reservation found for this room!");
    }

    private static void viewReservations() {
        System.out.println("\nCurrent Reservations:");
        if (reservations.isEmpty()) {
            System.out.println("No reservations yet.");
        } else {
            for (Reservation res : reservations) {
                System.out.println(res);
            }
        }
    }

    private static void saveReservations() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Reservation res : reservations) {
                pw.println(res);
            }
        } catch (IOException e) {
            System.out.println("Error saving reservations.");
        }
    }

    private static void loadReservations() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                System.out.println("Loaded: " + sc.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error loading reservations.");
            
        }
    }
}

