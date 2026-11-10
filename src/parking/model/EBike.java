package parking.model;
public final class EBike extends Vehicle implements Chargeable {
    public EBike(String registration) { super(registration); }
    @Override public String category() { return "E_BIKE"; }
    @Override public double chargingFee(int kWh) {
        if (kWh < 0) throw new IllegalArgumentException("Energy cannot be negative");
        return kWh * 4.0;
    }
}
