# Task 9: Abstraction

Vehicle holds common registration state and abstract category behavior. Chargeable is an interface because charging is optional and cross-cutting.

## Alternative concept and complexity
An interface cannot provide ordinary per-instance fields or constructors. An abstract Chargeable base class restricts multiple class inheritance.

**Evidence:** Refer to the corresponding section in `docs/Assignment-Report.md`. Execute the relevant standalone or integrated Java program and save your own terminal screenshot in this folder before submission.
