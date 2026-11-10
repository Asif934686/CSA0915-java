package parking.model;
public final class TwoWheeler extends Vehicle {
    public TwoWheeler(String registration) { super(registration); }
    @Override public String category() { return "TWO_WHEELER"; }
}
