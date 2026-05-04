package edu.touro.mcon364.concurrency.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * IN-CLASS TEST - Problem 2 of 3                                   (~30 minutes)
 * ============================================================================
 *
 * SCENARIO
 * --------
 * A server maintains a fixed pool of database Connection objects.
 * Threads borrow a connection, use it, then return it.  No more than
 * "capacity" connections may be in use at the same time; threads that
 * request a connection when the pool is exhausted must BLOCK until one
 * becomes available.
 *
 * REQUIREMENTS  (each TODO is graded)
 *
 * 1. Constructor  ConnectionPool(int capacity)
 *    - Create and store exactly "capacity" Connection objects (Connection(1), Connection(2), ...).
 *    - Initialise the Semaphore with capacity permits.
 *    - capacity must be >= 1; throw IllegalArgumentException otherwise.
 *
 * 2. acquire()
 *    - Block until a permit is available (call semaphore.acquire()).
 *    - Then take and return one Connection from the "available" list.
 *    - The list access MUST be protected by the ReentrantLock.
 *    - Increment activeCount after acquiring.
 *
 * 3. release(Connection c)
 *    - Lock, add c back to "available", decrement activeCount, unlock in a finally block.
 *    - Release one Semaphore permit in a SEPARATE finally block so that
 *      a waiting thread can proceed even if the lock section threw.
 *
 * 4. getActiveCount()   - return current number of connections in use.
 * 5. getAvailableCount() - return capacity minus activeCount.
 *
 * ALLOWED APIs (only what was taught in class):
 *   Semaphore, ReentrantLock, AtomicInteger
 *
 * DO NOT use any other concurrency utilities.
 */
public class ConnectionPool {

    /** Represents a single pooled database connection. Do not modify. */
    public record Connection(int id) {}

    private final int capacity;

    // TODO: declare a private final Semaphore
    private final Semaphore semaphore;

    // TODO: declare a private final ReentrantLock
    private final ReentrantLock lock;

    // The list of available (idle) connections
    private final List<Connection> available;

    // TODO: declare a private final AtomicInteger for the active connection count
    private final AtomicInteger activeCount;

    /**
     * Creates a pool with capacity connections pre-populated.
     *
     * @param capacity maximum concurrent connections (must be >= 1)
     * @throws IllegalArgumentException if capacity < 1
     */
    public ConnectionPool(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException("capacity must be >= 1");
        this.capacity = capacity;

        this.available = new ArrayList<>();

        // TODO: initialise the Semaphore with capacity permits
        // TODO: initialise the ReentrantLock
        // TODO: initialise activeCount to zero
        // TODO: create capacity Connection objects numbered 1..capacity and add to available

        // REPLACE the three null assignments below with your real initialisations
        this.semaphore   = null;
        this.lock        = null;
        this.activeCount = null;
    }

    /**
     * Borrows a connection from the pool. Blocks if none is free.
     *
     * @return a Connection for the caller's exclusive use
     * @throws InterruptedException if interrupted while waiting
     */
    public Connection acquire() throws InterruptedException {
        // TODO: acquire one permit from the semaphore (blocks if pool exhausted)

        // TODO: lock the list, remove and return the last Connection,
        //       increment activeCount, unlock in a finally block
        return null;
    }

    /**
     * Returns a borrowed connection to the pool.
     *
     * @param c the connection to return (must not be null)
     */
    public void release(Connection c) {
        // TODO: lock the list, add c back to available,
        //       decrement activeCount, unlock in a finally block

        // TODO: release one semaphore permit in a SEPARATE finally block
    }

    /** Returns the number of connections currently in use. */
    public int getActiveCount() {
        // TODO: implement
        return 0;
    }

    /** Returns the number of connections not currently in use. */
    public int getAvailableCount() {
        // TODO: implement
        return 0;
    }

    /** Returns the fixed capacity this pool was created with. */
    public int getCapacity() {
        return capacity;
    }
}
