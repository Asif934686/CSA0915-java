# Task 11: Implementation and testing

The integrated application uses HashMap active-ticket lookup and tests normal exit, duplicate registration, zero duration, EV energy fee, e-bike flat fee, van peak fee, pass and missing ticket.

## Alternative concept and complexity
ArrayList lookup is O(n); HashMap lookup is O(1) average and O(n) worst-case. Both use O(n) storage.

**Evidence:** Refer to the corresponding section in `docs/Assignment-Report.md`. Execute the relevant standalone or integrated Java program and save your own terminal screenshot in this folder before submission.
