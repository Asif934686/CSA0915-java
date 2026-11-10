# Assignment 1 (CO1): PARKWISE — Campus Intelligent Parking Management System

**Student:** S. Asif  
**Register number:** 192424121  
**GitHub URL:** https://github.com/<YOUR_GITHUB_USERNAME>/CSA09-192424121-Assignment1  
**Java version:** JDK 21 (source is compatible with JDK 17+)

## Design overview
PARKWISE models the parking domain with objects rather than parallel arrays of unrelated values. A `ParkingService` coordinates `ParkingFloor`, `ParkingSlot`, `Vehicle` and `ParkingTicket` objects. A `HashMap` indexes active tickets by normalized registration number. Pricing is delegated to `PricingRule` implementations, separating billing policy from vehicle identity and parking allocation.

## Task 1 — Problem analysis and candidate objects
**Inputs:** registration number, vehicle category, entry/exit timestamp, floor configuration, charging energy, pass-holder identifier. **Outputs:** allocated floor/slot, unique ticket, parking/charging fee, released slot and collection total.

| Candidate | Responsibility and justification |
|---|---|
| `Vehicle` | Common registration identity and category contract; prevents duplicated identity logic. |
| `Car`, `TwoWheeler`, `ElectricCar`, `EBike`, `DeliveryVan` | Represent distinct vehicle categories and capabilities. |
| `ParkingFloor` | Owns the slots on one floor; floor capacities can differ. |
| `ParkingSlot` | Enforces one occupant and charging compatibility. |
| `ParkingTicket` | Preserves entry time, vehicle, location and unique ticket ID. |
| `ParkingService` | Coordinates admission, checkout, ticket lookup and collection. |
| `PricingRule` | Allows pricing policies to change independently of allocation. |
| `OneDayPass` | Encapsulates a visitor's date-bound pass. |
| `Chargeable` | Models charging as a capability rather than assuming every vehicle has it. |

**Business rules:** a registration can have only one active ticket; a slot has at most one occupant; an EV/chargeable vehicle is assigned a charging-enabled slot; invalid durations and timestamps are rejected; ticket IDs are generated monotonically; a slot is released only after successful checkout; a pass is valid only on its date. **Assumptions:** registration is case-insensitive; all started hours are billed as full hours; e-bike parking is a flat Rs.25 (charging energy, if any, is separate); EV charging is Rs.12/kWh for electric cars and Rs.4/kWh for e-bikes; a visitor day pass costs Rs.50 and replaces the parking charge but not a separately consumed charging fee. The delivery-van peak rule doubles its hourly rate when checkout occurs from 09:00 inclusive to 17:00 exclusive; this is an explicit simplifying assumption for the assignment demo.

**Alternative — parallel arrays:** `regNo[i]`, `type[i]`, `entryTime[i]` keep aligned records. Search is O(n), like a linear scan of an object list; adding an attribute requires another array and synchronized updates. The object design also uses O(n) references plus object fields, but keeps related data and validation together and is safer to extend.

## Task 2 — Data representation
Registration is `String`; floor/slot numbers are `int`; timestamps are `LocalDateTime`; elapsed duration is `long` minutes; fees are `double` for this educational example (production billing should use `BigDecimal`). Floors use a jagged array `ParkingFloor[]`, with each floor owning a `ParkingSlot[]`, because floor capacities differ. Input can be collected using `Scanner` in a console front end; the demonstration uses fixed sample inputs. Integer division truncates: `90 / 60` gives 1, undercharging a started-hour policy. The safe integer ceiling is `minutes == 0 ? 0 : (minutes + 59) / 60`. Avoid `(int)(minutes / 60.0)` because casting truncates.

**Alternative:** one flat array needs prefix offsets or a mapping table for unequal floor capacities; the simple `floor * slotsPerFloor + slot` formula only works with equal capacities. `ArrayList<ParkingSlot[]>` supports variable floor lengths naturally. Locating a known floor/slot is O(1) in all indexed representations; memory is O(S+F), where S is total slots and F is floors.

## Task 3 — Control logic and required program
**Allocation pseudocode:** `FOR each floor in order; FOR each slot in floor; IF slot is free AND charging requirement is satisfied THEN occupy slot; create ticket; RETURN ticket; END IF; END FOR; END FOR; REPORT no suitable slot.`

**Checkout pseudocode:** `FIND active ticket by registration; IF absent reject; VALIDATE exit >= entry; COMPUTE elapsed minutes; SELECT applicable pass/pricing strategy; ADD charging fee when applicable; RELEASE slot; REMOVE active ticket; UPDATE collection; RETURN fee.`

The standalone `Task03/ParkingFacility.java` preserves the supplied `main()` and implements both TODOs. A full scan is O(S) worst-case allocation and O(1) release; a free-slot stack can make allocation/release O(1) amortized at the cost of O(S) extra references and careful stale-entry handling.

## Task 4 — Classes, objects and constructors
Constructors validate required state and use `super(...)` for inherited registration identity. `ParkingFloor` uses a parameterized constructor because capacity is essential. `ParkingTicket` is immutable after creation; a setter-based partially initialized ticket is deliberately avoided. A copy constructor is not required because reissuing a ticket should create a new unique ID, not duplicate identity. References live in local variables/stack frames while objects are allocated on the heap; garbage collection reclaims unreachable tickets after checkout if no other reference remains. Static factory methods could name creation policies but do not improve this small model. Creating n tickets is O(n) time and O(n) total storage for all approaches.

## Task 5 — Static members and object life cycle
`ParkingTicket.NEXT_ID` is static because IDs must be unique across all ticket instances; `AtomicLong` also makes increments safe under concurrent calls. Collection and active occupancy belong to a particular `ParkingService`, not globally to every facility. After checkout, the ticket is removed from the active map and becomes eligible for garbage collection when no other references remain. Methods accept and return object references; Java passes the reference value by value. If the counter were per facility, two gates could issue colliding IDs; a process-wide static counter avoids that, while a distributed deployment would need a database sequence/UUID.

## Task 6 — Inheritance hierarchy
`Vehicle` is an abstract superclass. `Car` and `TwoWheeler` are direct subclasses; `ElectricCar` extends `Car`; `EBike` and `DeliveryVan` extend `Vehicle`. This is hierarchical plus multilevel inheritance. `ElectricCar` construction runs `Vehicle` then `Car` then `ElectricCar`; `super(registration)` initializes the shared registration. Composition with a `VehicleType` strategy would make runtime category changes easier and reduce subclass count, but the assignment explicitly assesses inheritance. UML is in `docs/UML.md`.

## Task 7 — Overriding and object methods
Each concrete vehicle overrides `category()`. `@Override` checks the method signature at compile time. Registration is final and immutable; `Vehicle.equals()` and `hashCode()` use normalized registration so map lookup is consistent. The integrated design makes these methods final to preserve identity semantics in subclasses. A switch-based pricing method is possible but couples pricing changes to central code; a strategy avoids that coupling.

## Task 8 — Polymorphism and supplied program
The standalone `Task08/FeeDemo.java` completes all TODOs and preserves the required expected output. `calculateFee(int)` is selected at compile time by its parameter signature; the actual vehicle object determines the overridden rate/category behavior at runtime. An array of `Vehicle` references can point to `TwoWheeler`, `Car` and `ElectricCar`. The final equality example prints true because the supplied equality definition compares registration only; this is acceptable only if registration is globally unique across all active vehicles and category is not part of identity. If business identity requires category too, equality must include it.

## Task 9 — Abstraction and interfaces
`Vehicle` is abstract because all vehicles share registration state but the base category is incomplete. `Chargeable` is an interface because charging is an optional capability and can be implemented by unrelated vehicle classes. Java permits a class to implement multiple interfaces. The interface's default `chargingMessage()` supplies a reusable optional message. Replacing Vehicle with an interface would lose ordinary instance fields and constructors; making Chargeable an abstract class would prevent a class from extending another class and the charging base simultaneously. Dispatch cost is effectively O(1) in either approach; object storage is dominated by instance fields.

## Task 10 — Packages and encapsulation
Packages are `parking.model`, `parking.pricing`, `parking.service` and `parking.app`. Fields are private; public methods form the validated API; constructors validate inputs; `protected` is limited to the abstract superclass constructor. Wrapper types are not needed in the core model, but `Integer` values in collections must be compared with `.equals()` rather than `==` because reference identity is not numeric equality. Public fields would allow invalid states and force callers to duplicate validation at every call site. Validation itself is O(1) per field update; centralizing it reduces duplicated checks and defects.

## Task 11 — Implementation and validation
The complete implementation is in `src/`. `ParkwiseTest` is a deterministic executable test runner. It checks hourly rounding, duplicate registration, zero-minute parking, EV charging, e-bike flat pricing, delivery-van peak pricing, one-day pass and missing-ticket rejection. Compile and run commands are in README. Test output is saved in `outputs/` after local execution. The design follows encapsulation, inheritance, polymorphism and abstraction. A limitation is that the peak pricing assumption is based on checkout time rather than prorating each minute inside the peak window; this can be improved by splitting a stay into peak/off-peak intervals.

**Alternative lookup structures:** scanning an array or `ArrayList` by registration is O(n); a `HashMap<String, ParkingTicket>` is O(1) average lookup and O(n) storage, with O(n) worst-case theoretical lookup. This is why the active-ticket map is used.

## Task 12 — Changed requirement and design decision
**Decision: MODIFY, not discard.** Add `EBike`, `DeliveryVan`, `OneDayPass`, and `EBikeFlatPricing`; preserve the floor/slot abstraction and use a five-element jagged floor array with capacities `{3,2,4,3,2}`. `PricingRule` keeps policy separate from ticket lifecycle. Existing allocation/ticket classes remain stable; the service is extended to choose the additional pricing strategy and pass. Adding a future vehicle requires a new subclass and usually a pricing policy, not edits to every existing vehicle. Trade-off: more small classes and configuration are introduced, but each has one clear responsibility.

**Validation evidence:** run `java -cp out parking.app.ParkwiseTest`; the test runner reports pass/fail for the new categories and pass. The demo calculates and prints the collection. Re-run after changing any pricing assumption and include your own terminal screenshot in `outputs/`.

## Final submission checklist
- [ ] Student name/register number and GitHub URL filled in.
- [ ] Every program compiled and executed on the student's machine.
- [ ] Own screenshots saved for each required program.
- [ ] Progressive commits made (at least one per task).
- [ ] Public repository or faculty collaborator access configured.
- [ ] GitHub URL posted in Google Classroom comments.
