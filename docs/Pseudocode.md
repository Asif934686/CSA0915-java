# SIMATS-style algorithmic pseudocode

## Algorithm 1: Allocate a suitable parking slot
**Input:** vehicle object, ordered floors  
**Output:** ticket or full-facility error

1. BEGIN
2. Validate vehicle and registration.
3. IF registration already has an active ticket, RETURN duplicate-registration error.
4. SET `requiresCharging` according to vehicle capability.
5. FOR each floor from first to last:
   1. FOR each slot from first to last:
      1. IF slot is free AND (`requiresCharging` is false OR slot supports charging):
         1. Occupy slot with vehicle.
         2. Create ticket with unique ID, entry timestamp, floor and slot.
         3. Store ticket by normalized registration.
         4. RETURN ticket.
6. RETURN no-suitable-slot error.
7. END

## Algorithm 2: Calculate and collect parking fee
**Input:** registration, exit timestamp, energy consumed, optional pass holder  
**Output:** total fee

1. BEGIN
2. Find active ticket using registration key.
3. IF ticket does not exist, RETURN ticket-not-found error.
4. IF exit timestamp is earlier than entry timestamp, RETURN invalid-time error.
5. Calculate elapsed whole minutes.
6. IF a valid one-day pass exists for exit date, select pass price.
7. ELSE IF vehicle is an e-bike, apply e-bike flat pricing.
8. ELSE apply standard vehicle pricing and applicable delivery-van peak rule.
9. IF vehicle supports charging, add energy-based charging fee.
10. Release the ticket's slot.
11. Remove active ticket and add fee to collection.
12. RETURN fee.
13. END
