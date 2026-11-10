package parking.model;
import java.time.LocalDate;
public final class OneDayPass {
    private final String passHolder;
    private final LocalDate validDate;
    private final double price;
    public OneDayPass(String passHolder, LocalDate validDate, double price) {
        if (passHolder == null || passHolder.isBlank() || validDate == null || price < 0)
            throw new IllegalArgumentException("Invalid one-day pass");
        this.passHolder = passHolder; this.validDate = validDate; this.price = price;
    }
    public boolean isValidOn(LocalDate date) { return validDate.equals(date); }
    public double getPrice() { return price; }
    public String getPassHolder() { return passHolder; }
}
