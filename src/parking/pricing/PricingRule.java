package parking.pricing;
import parking.model.Vehicle;
import java.time.LocalDateTime;
public interface PricingRule {
    double calculate(Vehicle vehicle, long parkedMinutes, LocalDateTime exitTime);
    String ruleName();
}
