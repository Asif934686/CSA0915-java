package parking.pricing;
import parking.model.*;
import java.time.LocalDateTime;
public final class StandardPricing implements PricingRule {
    @Override public double calculate(Vehicle v, long minutes, LocalDateTime exit) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        if (minutes == 0) return 0;
        long hours = (minutes + 59) / 60;
        double rate = switch (v.category()) {
            case "TWO_WHEELER" -> 10;
            case "CAR", "ELECTRIC_CAR" -> 30;
            case "DELIVERY_VAN" -> 30;
            case "E_BIKE" -> 0;
            default -> throw new IllegalArgumentException("Unsupported vehicle category");
        };
        if (v instanceof DeliveryVan && !exit.toLocalTime().isBefore(java.time.LocalTime.of(9,0))
                && exit.toLocalTime().isBefore(java.time.LocalTime.of(17,0))) rate *= 2;
        return hours * rate;
    }
    @Override public String ruleName() { return "Standard hourly pricing"; }
}
