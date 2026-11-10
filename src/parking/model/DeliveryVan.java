package parking.model;
public final class DeliveryVan extends Vehicle {
    public DeliveryVan(String registration) { super(registration); }
    @Override public String category() { return "DELIVERY_VAN"; }
}
