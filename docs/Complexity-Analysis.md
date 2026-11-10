# Complexity comparison summary
Let F be number of floors, S total slots, n active tickets and k vehicle categories.

| Operation / representation | Time | Extra space | Comment |
|---|---:|---:|---|
| Scan all slots to allocate | O(S) worst case | O(1) | Stops at first suitable free slot. |
| Release known floor/slot | O(1) | O(1) | Direct array indexing. |
| Stack of free slot coordinates | O(1) amortized | O(S) | Must avoid duplicate/stale stack entries. |
| Search active tickets in array/List | O(n) | O(n) total | Linear scan by registration. |
| HashMap ticket lookup | O(1) average, O(n) worst | O(n) | Hashing plus bucket storage. |
| Vehicle fee calculation | O(1) | O(1) | Fixed arithmetic and rule dispatch. |
| instanceof chain | O(k) worst | O(1) | More branches and central maintenance. |
| Add field to parallel arrays | O(n) migration/update | O(n) new array | All arrays must remain aligned. |
| Add field to Vehicle object | O(1) class change; O(n) if migrating existing records | O(1) per object field | Related data remains encapsulated. |

The asymptotic cost of a few arithmetic operations is constant. A `HashMap` uses more overhead per ticket than a compact array, trading memory for faster average lookup. These are theoretical bounds; any empirical benchmark should record hardware, Java version, warm-up and dataset size.
