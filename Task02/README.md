# Task 2: Data representation

Use String registration, int floor/slot, LocalDateTime timestamps, long elapsed minutes and double demo fees. A jagged array is suitable because floors have unequal capacities.

## Alternative concept and complexity
Flat indexing is simple only for equal capacities; jagged arrays and ArrayList of floor arrays naturally support unequal sizes. Search for an indexed slot is O(1); storage O(S+F).

**Evidence:** Refer to the corresponding section in `docs/Assignment-Report.md`. Execute the relevant standalone or integrated Java program and save your own terminal screenshot in this folder before submission.
