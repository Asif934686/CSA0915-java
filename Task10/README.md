# Task 10: Encapsulation and packages

Packages separate model, pricing, service and app. Fields are private, constructors validate and public methods expose safe operations.

## Alternative concept and complexity
Public fields move validation burden to every caller and make invalid state possible. Central validation costs O(1) per update and avoids duplicated checks.

**Evidence:** Refer to the corresponding section in `docs/Assignment-Report.md`. Execute the relevant standalone or integrated Java program and save your own terminal screenshot in this folder before submission.
