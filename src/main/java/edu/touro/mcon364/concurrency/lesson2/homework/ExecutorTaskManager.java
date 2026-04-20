package edu.touro.mcon364.concurrency.lesson2.homework;

import edu.touro.mcon364.concurrency.common.model.Priority;
import edu.touro.mcon364.concurrency.common.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Homework — Executor-backed task manager with atomic IDs.
 *
 * Extend the task-manager from Lesson 1 so that tasks are executed through a
 * thread pool, IDs are generated atomically, and results are returned via
 * {@link Future}.
 *
 * Requirements (read each TODO carefully):
 *
 * 1. ID generation
 *    - {@link #nextId()} must use an {@link AtomicInteger} to generate IDs.
 *    - IDs start at 1 and increase monotonically, even under concurrent calls.
 *
 * 2. Submitting work
 *    - {@link #submit(String, Priority)} must:
 *        a. Call {@code nextId()} to obtain a unique ID.
 *        b. Build a {@link Task} record with that ID, the given description, and priority.
 *        c. Submit a {@link Callable} to the pool that "processes" the task
 *           (for now, just sleep 10 ms and return the task).
 *        d. Return the resulting {@link Future<Task>}.
 *
 * 3. Collecting results
 *    - {@link #awaitAll(List)} must call {@code get()} on every future in order
 *      and return the list of completed {@link Task} objects.
 *    - Wrap checked exceptions in {@link RuntimeException}.
 *
 * 4. Shutdown
 *    - {@link #shutdown()} must call {@code pool.shutdown()} followed by
 *      {@code pool.awaitTermination(30, TimeUnit.SECONDS)}.
 *
 * 5. Where a lock is needed
 *    - The {@code completedTasks} list is written by worker threads.
 *      Protect it with a {@link java.util.concurrent.locks.ReentrantLock}
 *      (or a thread-safe alternative) in {@link #recordCompleted(Task)}.
 *      Add a comment explaining WHY a lock is needed there.
 *
 * 6. Synchronizer choice (comment required)
 *    - In the Javadoc comment just below "SYNCHRONIZER CHOICE", explain in
 *      1–3 sentences which synchronizer from the lesson you would use if you
 *      needed to wait for a batch of tasks to finish before starting the next
 *      batch, and why.
 */
public class ExecutorTaskManager {

    /* ── SYNCHRONIZER CHOICE ────────────────────────────────────────────────
     * TODO: In 1–3 sentences, explain which synchronizer you would add to
     *       wait for a complete batch before the next batch starts, and why.
     * ──────────────────────────────────────────────────────────────────────*/

    private static final int POOL_SIZE = 4;

    // TODO: declare a private final ExecutorService field (fixed pool, POOL_SIZE threads)

    // TODO: declare a private final AtomicInteger for ID generation, starting at 0

    // List of tasks that have finished — written by worker threads, so needs protection
    private final List<Task> completedTasks = new ArrayList<>();

    // TODO: declare a private final ReentrantLock to protect completedTasks

    // ── ID generation ────────────────────────────────────────────────────────

    /**
     * Returns a unique, auto-incremented task ID.
     * TODO: use AtomicInteger.incrementAndGet() — do NOT use synchronized
     */
    public int nextId() {
        // TODO: implement
        return 0;
    }

    // ── task submission ──────────────────────────────────────────────────────

    /**
     * Creates a {@link Task} and submits it to the thread pool for execution.
     *
     * @param description task description (must be non-blank)
     * @param priority    task priority
     * @return a {@link Future<Task>} that will hold the completed task
     */
    public Future<Task> submit(String description, Priority priority) {
        // TODO (a): int id = nextId();

        // TODO (b): Task task = new Task(id, description, priority);

        // TODO (c): return pool.submit(Callable) — lambda that sleeps 10 ms,
        //           calls recordCompleted(task), and returns the task
        return null;
    }

    // ── recording completion ─────────────────────────────────────────────────

    /**
     * Records a finished task.
     *
     * This method is called from worker threads, so the list must be protected.
     * TODO: acquire completedLock before adding to completedTasks, release in finally.
     *       Add a comment explaining WHY a lock is needed here.
     */
    private void recordCompleted(Task task) {
        // TODO: implement
    }

    // ── collecting results ───────────────────────────────────────────────────

    /**
     * Waits for every future in {@code futures} to complete and returns the
     * resulting {@link Task} objects in submission order.
     *
     * TODO: iterate futures, call get() on each, collect results into a list.
     *       Wrap InterruptedException and ExecutionException in RuntimeException.
     */
    public List<Task> awaitAll(List<Future<Task>> futures) {
        // TODO: implement
        return new ArrayList<>();
    }

    // ── lifecycle ────────────────────────────────────────────────────────────

    /**
     * Shuts down the pool and waits up to 30 seconds for all tasks to finish.
     *
     * TODO: call pool.shutdown() then pool.awaitTermination(30, TimeUnit.SECONDS).
     */
    public void shutdown() throws InterruptedException {
        // TODO: implement
    }

    // ── observability ────────────────────────────────────────────────────────

    /** Returns a snapshot of the tasks that have completed so far. */
    public List<Task> getCompletedTasks() {
        // TODO: acquire completedLock, return List.copyOf(completedTasks), release in finally
        return List.copyOf(completedTasks);
    }

    /** Returns the most recently generated ID (useful for assertions). */
    public int getLastIssuedId() {
        // TODO: return idCounter.get()
        return 0;
    }
}
