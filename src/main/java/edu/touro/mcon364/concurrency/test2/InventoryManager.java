package edu.touro.mcon364.concurrency.test2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ══════════════════════════════════════════════════════════════
 *  IN-CLASS TEST — Problem 1 of 3                  (~25 minutes)
 * ══════════════════════════════════════════════════════════════
 *
 * SCENARIO
 * --------
 * A warehouse system lets many threads update item stock simultaneously.
 * You must make the {@code InventoryManager} thread-safe so that no stock
 * updates are lost and stock never goes negative.
 *
 * REQUIREMENTS  (read every TODO carefully — each is graded)
 *
 * 1. {@code addStock(String item, int qty)}
 *    - Adds {@code qty} units of {@code item} to the inventory.
 *    - {@code qty} must be > 0; throw {@link IllegalArgumentException} otherwise.
 *    - Must be thread-safe under concurrent calls.
 *    - Also increments the running {@code totalUnitsAdded} counter atomically.
 *
 * 2. {@code removeStock(String item, int qty)}
 *    - Removes {@code qty} units of {@code item} if sufficient stock exists.
 *    - Returns {@code true} if the removal succeeded, {@code false} if the
 *      current stock is less than {@code qty} (do NOT go negative).
 *    - {@code qty} must be > 0; throw {@link IllegalArgumentException} otherwise.
 *    - Must be thread-safe: two threads must not both succeed when only one
 *      unit of stock remains and both try to remove one unit.
 *    - Hint: {@link ConcurrentHashMap#compute} or
 *            {@link ConcurrentHashMap#merge} can do this atomically.
 *
 * 3. {@code getStock(String item)}
 *    - Returns the current stock for {@code item}, or {@code 0} if the item
 *      has never been added.
 *
 * 4. {@code getTotalUnitsAdded()}
 *    - Returns the running total of every unit ever added across all items.
 *    - Must reflect concurrent additions accurately — use {@link AtomicInteger}.
 *
 * 5. {@code getSnapshot()}
 *    - Returns an unmodifiable copy of the current inventory so callers
 *      cannot mutate internal state.
 *    - Hint: {@link Map#copyOf(Map)}.
 *
 * ALLOWED APIs (only what was taught in class):
 *   ConcurrentHashMap, AtomicInteger, synchronized blocks/methods,
 *   Map.copyOf(), Collections.unmodifiableMap()
 *
 * DO NOT use any other concurrency utilities.
 */
public class InventoryManager {

    // TODO: declare a ConcurrentHashMap<String, Integer> for per-item stock
    private final ConcurrentHashMap<String, Integer> stock = new ConcurrentHashMap<>();

    // TODO: declare an AtomicInteger for the running total of units added
    private final AtomicInteger totalUnitsAdded = new AtomicInteger(0);

    /**
     * Adds {@code qty} units of {@code item} to inventory.
     *
     * @param item the item name (non-null, non-blank)
     * @param qty  number of units to add (must be > 0)
     * @throws IllegalArgumentException if qty ≤ 0
     */
    public void addStock(String item, int qty) {
        // TODO: validate qty > 0

        // TODO: atomically add qty to the item's current stock
        //       Hint: ConcurrentHashMap.merge(key, value, remappingFunction) is perfect here

        // TODO: atomically add qty to totalUnitsAdded
    }

    /**
     * Removes {@code qty} units of {@code item} if sufficient stock exists.
     *
     * @param item the item name
     * @param qty  number of units to remove (must be > 0)
     * @return {@code true} if removal succeeded; {@code false} if insufficient stock
     * @throws IllegalArgumentException if qty ≤ 0
     */
    public boolean removeStock(String item, int qty) {
        // TODO: validate qty > 0

        // TODO: atomically check-and-decrement.
        //       If current stock >= qty, subtract qty and return true.
        //       Otherwise, leave stock unchanged and return false.
        //       Hint: ConcurrentHashMap.compute() lets you read and write atomically.
        //             Use a boolean[] flag to communicate the result out of the lambda.
        return false;
    }

    /**
     * Returns the current stock for {@code item}, or 0 if unknown.
     */
    public int getStock(String item) {
        // TODO: implement
        return 0;
    }

    /**
     * Returns the cumulative number of units ever added (all items combined).
     */
    public int getTotalUnitsAdded() {
        // TODO: implement
        return 0;
    }

    /**
     * Returns an unmodifiable snapshot of the current inventory.
     * Callers cannot use the returned map to change internal state.
     */
    public Map<String, Integer> getSnapshot() {
        // TODO: return a defensive copy
        return null;
    }
}

