# Task 1: Problem analysis

Identify actors, inputs, outputs, candidate classes and invariants. Vehicle, slot, floor, ticket and service are separate because identity, occupancy, capacity, visit history and coordination have different responsibilities.

## Alternative concept and complexity
Parallel arrays still require O(n) registration search; adding a field requires a new aligned array. Objects encapsulate fields and validation.

**Evidence:** Refer to the corresponding section in `docs/Assignment-Report.md`. Execute the relevant standalone or integrated Java program and save your own terminal screenshot in this folder before submission.
