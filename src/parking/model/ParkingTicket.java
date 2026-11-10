package parking.model;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;
public final class ParkingTicket {
    private static final AtomicLong NEXT_ID = new AtomicLong(1000);
    private final String ticketId;
    private final Vehicle vehicle;
    private final int floorNumber, slotNumber;
    private final LocalDateTime entryTime;
    public ParkingTicket(Vehicle vehicle, int floorNumber, int slotNumber, LocalDateTime entryTime) {
        this.ticketId = "PW-" + NEXT_ID.incrementAndGet(); this.vehicle = vehicle;
        this.floorNumber = floorNumber; this.slotNumber = slotNumber; this.entryTime = entryTime;
    }
    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public int getFloorNumber() { return floorNumber; }
    public int getSlotNumber() { return slotNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
}
