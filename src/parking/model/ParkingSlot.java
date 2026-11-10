package parking.model;
public final class ParkingSlot {
    private final int number;
    private final boolean chargingEnabled;
    private Vehicle occupant;
    public ParkingSlot(int number, boolean chargingEnabled) {
        if (number < 1) throw new IllegalArgumentException("Slot number must be positive");
        this.number = number; this.chargingEnabled = chargingEnabled;
    }
    public int getNumber() { return number; }
    public boolean isChargingEnabled() { return chargingEnabled; }
    public boolean isFree() { return occupant == null; }
    public Vehicle getOccupant() { return occupant; }
    public void park(Vehicle vehicle) {
        if (!isFree()) throw new IllegalStateException("Slot already occupied");
        if (vehicle instanceof Chargeable && !chargingEnabled)
            throw new IllegalStateException("EV requires a charging-enabled slot");
        occupant = vehicle;
    }
    public void release() {
        if (isFree()) throw new IllegalStateException("Slot is already free");
        occupant = null;
    }
}
