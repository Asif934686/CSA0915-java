package parking.model;
public final class ElectricCar extends Car implements Chargeable {
    public ElectricCar(String registration) { super(registration); }
    @Override public String category() { return "ELECTRIC_CAR"; }
    @Override public double chargingFee(int kWh) {
        if (kWh < 0) throw new IllegalArgumentException("Energy cannot be negative");
        return kWh * 12.0;
    }
}
