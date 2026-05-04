package edu.touro.mcon364.concurrency.test2;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class ConnectionPoolTest {

    @Test
    void constructorRejectsZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new ConnectionPool(0));
    }

    @Test
    void constructorRejectsNegativeCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new ConnectionPool(-3));
    }

    @Test
    void initiallyAllConnectionsAvailable() {
        ConnectionPool pool = new ConnectionPool(3);
        assertEquals(3, pool.getAvailableCount());
        assertEquals(0, pool.getActiveCount());
    }

    @Test
    void acquireReturnsNonNullConnection() throws InterruptedException {
        ConnectionPool pool = new ConnectionPool(2);
        ConnectionPool.Connection c = pool.acquire();
        assertNotNull(c);
        pool.release(c);
    }

    @Test
    void acquireDecreasesAvailableCount() throws InterruptedException {
        ConnectionPool pool = new ConnectionPool(3);
        ConnectionPool.Connection c = pool.acquire();
        assertEquals(2, pool.getAvailableCount());
        assertEquals(1, pool.getActiveCount());
        pool.release(c);
    }

    @Test
    void releaseRestoresAvailableCount() throws InterruptedException {
        ConnectionPool pool = new ConnectionPool(2);
        ConnectionPool.Connection c = pool.acquire();
        pool.release(c);
        assertEquals(2, pool.getAvailableCount());
        assertEquals(0, pool.getActiveCount());
    }

    @Test
    void canAcquireAllConnectionsThenReleaseAll() throws InterruptedException {
        int cap = 4;
        ConnectionPool pool = new ConnectionPool(cap);
        List<ConnectionPool.Connection> held = new ArrayList<>();
        for (int i = 0; i < cap; i++) held.add(pool.acquire());
        assertEquals(0,   pool.getAvailableCount());
        assertEquals(cap, pool.getActiveCount());
        for (ConnectionPool.Connection c : held) pool.release(c);
        assertEquals(cap, pool.getAvailableCount());
        assertEquals(0,   pool.getActiveCount());
    }

    @Test
    void activeConnectionsNeverExceedCapacity() throws InterruptedException {
        int capacity = 3;
        int threadCount = 12;
        ConnectionPool pool = new ConnectionPool(capacity);
        AtomicInteger maxObserved = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(() -> {
                try {
                    ConnectionPool.Connection c = pool.acquire();
                    maxObserved.updateAndGet(prev -> Math.max(prev, pool.getActiveCount()));
                    Thread.sleep(20);
                    pool.release(c);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();
        assertTrue(maxObserved.get() <= capacity,
                "Peak active connections exceeded capacity: " + maxObserved.get());
        assertEquals(0, pool.getActiveCount());
    }

    @Test
    void allThreadsEventuallyGetAConnection() throws InterruptedException {
        int threadCount = 10;
        ConnectionPool pool = new ConnectionPool(3);
        AtomicInteger served = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(() -> {
                try {
                    ConnectionPool.Connection c = pool.acquire();
                    served.incrementAndGet();
                    Thread.sleep(10);
                    pool.release(c);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();
        assertEquals(threadCount, served.get(),
                "Every thread must eventually be served a connection");
    }
}

