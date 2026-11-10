package parking.model;
public interface Chargeable {
    double chargingFee(int kWh);
    default String chargingMessage() { return "EV charging service available"; }
}
