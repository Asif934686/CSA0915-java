package parking.service;
import parking.model.*;
import parking.pricing.*;
import java.time.*;
import java.util.*;

public final class ParkingService {
    private final ParkingFloor[] floors;
    private final Map<String, ParkingTicket> activeTickets = new HashMap<>();
    private final Map<String, OneDayPass> dayPasses = new HashMap<>();
    private final PricingRule standardPricing = new StandardPricing();
    private final PricingRule eBikePricing = new EBikeFlatPricing(25.0);
    private double collection;

    public ParkingService(int[] capacities, int[] chargingSlots) {
        if (capacities == null || chargingSlots == null || capacities.length != chargingSlots.length)
            throw new IllegalArgumentException("Floor configuration mismatch");
        floors = new ParkingFloor[capacities.length];
        for (int i = 0; i < capacities.length; i++) floors[i] = new ParkingFloor(i + 1, capacities[i], chargingSlots[i]);
    }
    public void registerPass(OneDayPass pass) { dayPasses.put(pass.getPassHolder().toUpperCase(), pass); }
    public ParkingTicket admit(Vehicle vehicle, LocalDateTime entryTime) {
        Objects.requireNonNull(vehicle); Objects.requireNonNull(entryTime);
        String reg = vehicle.getRegistration();
        if (activeTickets.containsKey(reg)) throw new IllegalArgumentException("Duplicate active registration: " + reg);
        boolean needsCharging = vehicle instanceof Chargeable;
        for (ParkingFloor floor : floors) {
            ParkingSlot slot = floor.findFreeSlot(needsCharging);
            if (slot != null) {
                slot.park(vehicle);
                ParkingTicket ticket = new ParkingTicket(vehicle, floor.getFloorNumber(), slot.getNumber(), entryTime);
                activeTickets.put(reg, ticket);
                return ticket;
            }
        }
        throw new IllegalStateException("No suitable parking slot available");
    }
    public double checkout(String registration, LocalDateTime exitTime, int chargingKWh, String passHolder) {
        String key = registration.toUpperCase();
        ParkingTicket ticket = activeTickets.get(key);
        if (ticket == null) throw new NoSuchElementException("No active ticket for " + key);
        if (exitTime.isBefore(ticket.getEntryTime())) throw new IllegalArgumentException("Exit precedes entry");
        long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        Vehicle vehicle = ticket.getVehicle();
        OneDayPass pass = passHolder == null ? null : dayPasses.get(passHolder.toUpperCase());
        double fee;
        if (pass != null && pass.isValidOn(exitTime.toLocalDate())) fee = pass.getPrice();
        else if (vehicle instanceof EBike) fee = eBikePricing.calculate(vehicle, minutes, exitTime);
        else fee = standardPricing.calculate(vehicle, minutes, exitTime);
        if (vehicle instanceof Chargeable chargeable) fee += chargeable.chargingFee(chargingKWh);
        ParkingSlot slot = floors[ticket.getFloorNumber()-1].getSlots()[ticket.getSlotNumber()-1];
        slot.release(); activeTickets.remove(key); collection += fee;
        return fee;
    }
    public int activeVehicleCount() { return activeTickets.size(); }
    public double getCollection() { return collection; }
    public boolean isParked(String registration) { return activeTickets.containsKey(registration.toUpperCase()); }
}
