package parking.app;
import parking.model.*;
import parking.service.ParkingService;
import java.time.*;
public final class ParkwiseDemo {
    public static void main(String[] args) {
        ParkingService system = new ParkingService(new int[]{3,2,4,3,2}, new int[]{1,1,1,1,1});
        LocalDate date = LocalDate.of(2026, 11, 10);
        system.registerPass(new OneDayPass("VISITOR-01", date, 50));
        ParkingTicket car = system.admit(new Car("TN01AB1234"), date.atTime(8,0));
        ParkingTicket ev = system.admit(new ElectricCar("TN02CD5678"), date.atTime(8,15));
        ParkingTicket bike = system.admit(new EBike("TN03EF9012"), date.atTime(9,0));
        ParkingTicket van = system.admit(new DeliveryVan("TN04GH3456"), date.atTime(10,0));
        System.out.println("PARKWISE CAMPUS PARKING — DEMO");
        System.out.println("Issued: " + car.getTicketId() + " | " + car.getVehicle());
        System.out.println("Issued: " + ev.getTicketId() + " | " + ev.getVehicle());
        System.out.println("Issued: " + bike.getTicketId() + " | " + bike.getVehicle());
        System.out.println("Issued: " + van.getTicketId() + " | " + van.getVehicle());
        System.out.printf("Car fee: Rs.%.2f%n", system.checkout("TN01AB1234", date.atTime(9,10), 0, null));
        System.out.printf("EV fee incl. 2 kWh: Rs.%.2f%n", system.checkout("TN02CD5678", date.atTime(10,15), 2, null));
        System.out.printf("E-bike flat fee: Rs.%.2f%n", system.checkout("TN03EF9012", date.atTime(11,0), 0, null));
        System.out.printf("Van peak fee: Rs.%.2f%n", system.checkout("TN04GH3456", date.atTime(11,0), 0, null));
        System.out.printf("Day collection: Rs.%.2f%n", system.getCollection());
        System.out.println("Active vehicles: " + system.activeVehicleCount());
    }
}
