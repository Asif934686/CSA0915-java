# Task 4: Constructors and objects

Constructors establish valid immutable state. Vehicle subclasses chain to Vehicle with super; ParkingFloor receives its capacity; ParkingTicket creates a unique ID.

## Alternative concept and complexity
Setter construction can expose partially initialized objects. Factories can centralize named creation. Creating n tickets is O(n) time and O(n) storage.

**Evidence:** Refer to the corresponding section in `docs/Assignment-Report.md`. Execute the relevant standalone or integrated Java program and save your own terminal screenshot in this folder before submission.
