package parking.pricing;
import parking.model.Vehicle;
import java.time.LocalDateTime;
public final class EBikeFlatPricing implements PricingRule {
    private final double flatFee;
    public EBikeFlatPricing(double flatFee) {
        if (flatFee < 0) throw new IllegalArgumentException("Flat fee cannot be negative");
        this.flatFee = flatFee;
    }
    @Override public double calculate(Vehicle vehicle, long minutes, LocalDateTime exit) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        return minutes == 0 ? 0 : flatFee;
    }
    @Override public String ruleName() { return "E-bike flat pricing"; }
}
