# Task 3: Control logic

Allocation scans floors and slots in order, accepts only a free compatible slot and stops at the first match. Checkout validates ticket and time, prices, releases slot and removes ticket.

## Alternative concept and complexity
Full scan allocation O(S), release O(1). A stack of free positions gives O(1) amortized allocation/release with O(S) auxiliary storage.

**Evidence:** Refer to the corresponding section in `docs/Assignment-Report.md`. Execute the relevant standalone or integrated Java program and save your own terminal screenshot in this folder before submission.
