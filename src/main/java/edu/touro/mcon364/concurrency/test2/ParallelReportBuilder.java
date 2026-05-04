package edu.touro.mcon364.concurrency.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * IN-CLASS TEST - Problem 3 of 3                                   (~35 minutes)
 * ============================================================================
 *
 * SCENARIO
 * --------
 * A reporting system receives multiple "pages" of integer data and must
 * compute statistics on them concurrently using a thread pool.
 *
 * REQUIREMENTS  (each TODO is graded)
 *
 * 1. generateReport(List<List<Integer>> pages, int workers)
 *
 *    (A) Create a CountDownLatch with count = workers.
 *    (B) Create a fixed thread pool of "workers" threads.
 *    (C) For EACH page submit a Callable<PageStats> that:
 *          1. Calls latch.countDown()   -- signals this worker is ready.
 *          2. Calls latch.await()       -- waits until ALL workers are ready (coordinated start).
 *          3. Iterates through the page to compute: sum, count, max, min.
 *          4. Increments processedPageCount atomically.
 *          5. Returns new PageStats(pageIndex, sum, count, max, min).
 *    (D) Collect ALL Future<PageStats> objects BEFORE calling get() on any
 *        (so the tasks actually run in parallel).
 *    (E) Iterate the futures and call get() on each; fold the results into
 *        running totals: totalSum, totalCount, globalMax, globalMin.
 *    (F) Shut down the pool.
 *    (G) Return new ReportSummary(totalSum, totalCount, globalMax, globalMin,
 *                                 processedPageCount.get()).
 *
 * 2. getProcessedPageCount()
 *    - Returns the current value of processedPageCount.
 *
 * ALLOWED APIs (only what was taught in class):
 *   ExecutorService, Executors.newFixedThreadPool,
 *   Callable, Future, CountDownLatch, AtomicInteger
 *
 * DO NOT use Streams, parallelStream, or any other concurrency utility.
 */
public class ParallelReportBuilder {

    /** Statistics for a single page. Do not modify. */
    public record PageStats(int pageIndex, long sum, int count, int max, int min) {}

    /** Combined statistics across all pages. Do not modify. */
    public record ReportSummary(long totalSum, int totalCount,
                                int globalMax, int globalMin,
                                int pagesProcessed) {}

    // TODO: declare a private final AtomicInteger named processedPageCount, initialised to 0
    private final AtomicInteger processedPageCount = new AtomicInteger(0);

    /**
     * Processes each page concurrently and combines the results.
     *
     * @param pages   list of pages; each page is a non-empty list of integers
     * @param workers number of threads in the pool AND the initial latch count
     * @return a ReportSummary aggregating all pages
     * @throws InterruptedException if interrupted while waiting
     * @throws ExecutionException   if a worker task threw an exception
     */
    public ReportSummary generateReport(List<List<Integer>> pages, int workers)
            throws InterruptedException, ExecutionException {

        // TODO: (A) create a CountDownLatch with count = workers

        // TODO: (B) create a fixed thread pool with workers threads

        List<Future<PageStats>> futures = new ArrayList<>();

        for (int i = 0; i < pages.size(); i++) {
            int pageIndex = i;
            List<Integer> page = pages.get(i);

            // TODO: (C) submit a Callable<PageStats> for this page.
            //       Inside the callable:
            //         1. latch.countDown()   -- signal this worker is ready
            //         2. latch.await()       -- wait for ALL workers to be ready
            //         3. compute sum, count, max, min by iterating over the page
            //         4. processedPageCount.incrementAndGet()
            //         5. return new PageStats(pageIndex, sum, count, max, min)
            //
            // TODO: add the returned Future to the futures list
        }

        // TODO: (E) initialise running totals, then for each future call get()
        //       and fold its PageStats into totalSum, totalCount, globalMax, globalMin
        long totalSum   = 0;
        int  totalCount = 0;
        int  globalMax  = Integer.MIN_VALUE;
        int  globalMin  = Integer.MAX_VALUE;

        // TODO: iterate futures and accumulate

        // TODO: (F) shut down the pool

        // TODO: (G) return a ReportSummary with the combined values
        return null;
    }

    /** Returns the number of pages processed so far. */
    public int getProcessedPageCount() {
        // TODO: implement
        return 0;
    }
}
