package parking.model;
public final class ParkingFloor {
    private final int floorNumber;
    private final ParkingSlot[] slots;
    public ParkingFloor(int floorNumber, int slotCount, int chargingSlots) {
        if (floorNumber < 1 || slotCount < 0 || chargingSlots < 0 || chargingSlots > slotCount)
            throw new IllegalArgumentException("Invalid floor configuration");
        this.floorNumber = floorNumber; slots = new ParkingSlot[slotCount];
        for (int i = 0; i < slotCount; i++) slots[i] = new ParkingSlot(i + 1, i < chargingSlots);
    }
    public int getFloorNumber() { return floorNumber; }
    public ParkingSlot[] getSlots() { return slots.clone(); }
    public ParkingSlot findFreeSlot(boolean needsCharging) {
        for (ParkingSlot slot : slots)
            if (slot.isFree() && (!needsCharging || slot.isChargingEnabled())) return slot;
        return null;
    }
}
