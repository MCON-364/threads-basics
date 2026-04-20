package edu.touro.mcon364.concurrency.lesson2.exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Exercise 5 — Returning values from concurrent tasks with {@link Callable} and
 * {@link Future}.
 *
 * Scenario: split a list of integers across several workers, have each worker
 * compute the sum of its slice, then combine the partial results into a total.
 *
 * Your tasks:
 *
 * (A) Implement {@link #parallelSum(List, int)}.
 *     - Create a fixed thread pool with {@code workers} threads.
 *     - Divide {@code numbers} into {@code workers} roughly-equal slices.
 *     - Submit each slice as a {@code Callable<Long>} that returns the sum of
 *       that slice.
 *     - Collect all {@link Future<Long>} objects BEFORE calling {@code get()} on
 *       any of them (to keep the work concurrent).
 *     - After all futures are collected, call {@code get()} on each and add
 *       partial results to a running total.
 *     - Shut down the pool and return the total.
 *
 * Note: calling {@code get()} immediately after each {@code submit()} turns
 * concurrent work back into sequential work — avoid that pattern here.
 */
public class ParallelSumCalculator {

    /**
     * Computes the sum of {@code numbers} by splitting the work across
     * {@code workers} {@link Callable} tasks submitted to a thread pool.
     *
     * @param numbers list of values to sum
     * @param workers number of pool threads / partitions
     * @return the total sum
     */
    public long parallelSum(List<Integer> numbers, int workers)
            throws InterruptedException, ExecutionException {

        // TODO: create ExecutorService with Executors.newFixedThreadPool(workers)

        // TODO: split numbers into workers roughly-equal slices
        //       Hint: int chunkSize = (numbers.size() + workers - 1) / workers;

        // TODO: for each slice, submit a Callable<Long> that sums the slice
        //       and add the returned Future<Long> to a list — do NOT call get() yet
        List<Future<Long>> futures = new ArrayList<>();

        // TODO: iterate futures and call get() on each to accumulate the total
        long total = 0;

        // TODO: pool.shutdown()
        return total;
    }
}
