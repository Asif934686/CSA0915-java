package parking.model;
public class Car extends Vehicle {
    public Car(String registration) { super(registration); }
    @Override public String category() { return "CAR"; }
}
