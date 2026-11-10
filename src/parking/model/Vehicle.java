package parking.model;

public abstract class Vehicle {
    private final String registration;
    protected Vehicle(String registration) {
        if (registration == null || !registration.matches("[A-Za-z0-9-]{4,15}"))
            throw new IllegalArgumentException("Registration must be 4-15 letters, digits or hyphens");
        this.registration = registration.toUpperCase();
    }
    public final String getRegistration() { return registration; }
    public abstract String category();
    @Override public final boolean equals(Object other) {
        return other instanceof Vehicle v && registration.equals(v.registration);
    }
    @Override public final int hashCode() { return registration.hashCode(); }
    @Override public String toString() { return category() + "[" + registration + "]"; }
}
