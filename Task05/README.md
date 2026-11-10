# Task 5: Static members and life cycle

Ticket ID counter is static because it is shared by all ticket instances. Facility collection and active occupancy are instance state. A ticket becomes collectible after removal from the active map and loss of all references.

## Alternative concept and complexity
A per-facility counter duplicates state and may collide across gates. One static counter uses O(1) space; distributed systems should use UUID/database sequences.

**Evidence:** Refer to the corresponding section in `docs/Assignment-Report.md`. Execute the relevant standalone or integrated Java program and save your own terminal screenshot in this folder before submission.
