package parking.app;
import parking.model.*;
import parking.service.ParkingService;
import java.time.*;
public final class ParkwiseTest {
    private static int passed = 0, failed = 0;
    private static void check(String name, Runnable test) {
        try { test.run(); System.out.println("PASS | " + name); passed++; }
        catch (Throwable ex) { System.out.println("FAIL | " + name + " | " + ex.getMessage()); failed++; }
    }
    private static void expectThrows(Class<? extends Throwable> type, Runnable r) {
        try { r.run(); throw new AssertionError("Expected " + type.getSimpleName()); }
        catch (Throwable t) { if (!type.isInstance(t)) throw t; }
    }
    public static void main(String[] args) {
        LocalDate d = LocalDate.of(2026,11,10);
        ParkingService p = new ParkingService(new int[]{1,2,1,1,1}, new int[]{1,1,1,1,1});
        check("normal car fee rounds started hour", () -> {
            p.admit(new Car("CAR1001"), d.atTime(8,0));
            if (p.checkout("CAR1001", d.atTime(9,1),0,null) != 60.0) throw new AssertionError("expected 60");
        });
        check("duplicate active registration rejected", () -> {
            p.admit(new Car("CAR1002"), d.atTime(8,0));
            expectThrows(IllegalArgumentException.class, () -> p.admit(new Car("CAR1002"), d.atTime(8,1)));
        });
        check("zero-minute stay costs zero", () -> {
            p.admit(new TwoWheeler("BIKE1001"), d.atTime(8,0));
            if (p.checkout("BIKE1001",d.atTime(8,0),0,null) != 0.0) throw new AssertionError("expected 0");
        });
        check("EV receives charging slot and energy charge", () -> {
            p.admit(new ElectricCar("EV10001"),d.atTime(8,0));
            if (p.checkout("EV10001",d.atTime(9,0),2,null) != 54.0) throw new AssertionError("expected 54");
        });
        check("e-bike flat fee", () -> {
            p.admit(new EBike("EB10001"),d.atTime(8,0));
            if (p.checkout("EB10001",d.atTime(10,0),0,null) != 25.0) throw new AssertionError("expected 25");
        });
        check("delivery van peak rate doubles", () -> {
            p.admit(new DeliveryVan("VAN1001"),d.atTime(8,0));
            if (p.checkout("VAN1001",d.atTime(10,0),0,null) != 120.0) throw new AssertionError("expected 120");
        });
        check("one-day visitor pass applied", () -> {
            p.registerPass(new OneDayPass("GUEST",d,50));
            p.admit(new Car("GUEST01"),d.atTime(8,0));
            if (p.checkout("GUEST01",d.atTime(9,0),0,"GUEST") != 50.0) throw new AssertionError("expected 50");
        });
        check("missing ticket rejected", () -> expectThrows(java.util.NoSuchElementException.class,
                () -> p.checkout("NOPE01",d.atTime(9,0),0,null)));
        System.out.println("SUMMARY | passed=" + passed + " failed=" + failed);
        if (failed > 0) System.exit(1);
    }
}
