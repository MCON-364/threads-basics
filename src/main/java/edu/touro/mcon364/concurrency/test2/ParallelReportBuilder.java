package edu.touro.mcon364.concurrency.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Problem 3 of 3
 *
 * A reporting system processes multiple pages of integers concurrently
 * and combines the results into a single ReportSummary.
 *
 * TODO 1 — generateReport(List<List<Integer>> pages, int workers)
 *   Create a CountDownLatch with count = workers.
 *   Create a fixed thread pool of workers threads.
 *   For each page submit a Callable<PageStats> that:
 *     (a) calls latch.countDown()
 *     (b) calls latch.await()  (wait for all workers before computing)
 *     (c) computes sum, count, max, min for the page
 *     (d) increments processedPageCount
 *     (e) returns new PageStats(pageIndex, sum, count, max, min)
 *   Collect ALL futures before calling get() on any.
 *   Combine the results into a ReportSummary and shut down the pool.
 *
 * TODO 2 — getProcessedPageCount()
 *   Return the current value of processedPageCount.
 */
public class ParallelReportBuilder {

    /** Do not modify. */
    public record PageStats(int pageIndex, long sum, int count, int max, int min) {}

    /** Do not modify. */
    public record ReportSummary(long totalSum, int totalCount,
                                int globalMax, int globalMin,
                                int pagesProcessed) {}

    // TODO: declare and initialise processedPageCount — thread-safe, no synchronized

    public ReportSummary generateReport(List<List<Integer>> pages, int workers)
            throws InterruptedException, ExecutionException {

        // TODO 1
        List<Future<PageStats>> futures = new ArrayList<>();

        long totalSum   = 0;
        int  totalCount = 0;
        int  globalMax  = Integer.MIN_VALUE;
        int  globalMin  = Integer.MAX_VALUE;

        return null;
    }

    public int getProcessedPageCount() {
        // TODO 2
        return 0;
    }
}
