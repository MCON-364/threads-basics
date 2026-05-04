package edu.touro.mcon364.concurrency.test2;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

class ParallelReportBuilderTest {

    /** Helper: creates pageCount pages of consecutive integers starting at 1. */
    private List<List<Integer>> makePages(int pageCount, int pageSize) {
        List<List<Integer>> pages = new ArrayList<>();
        int value = 1;
        for (int p = 0; p < pageCount; p++) {
            List<Integer> page = new ArrayList<>();
            for (int i = 0; i < pageSize; i++) page.add(value++);
            pages.add(page);
        }
        return pages;
    }

    // ---- basic correctness --------------------------------------------------

    @Test
    void generateReportReturnsNonNull() throws InterruptedException, ExecutionException {
        ParallelReportBuilder b = new ParallelReportBuilder();
        assertNotNull(b.generateReport(makePages(2, 3), 2));
    }

    @Test
    void totalSumIsCorrect() throws InterruptedException, ExecutionException {
        // pages [1,2,3] and [4,5,6] -> total = 21
        ParallelReportBuilder b = new ParallelReportBuilder();
        var s = b.generateReport(makePages(2, 3), 2);
        assertEquals(21L, s.totalSum());
    }

    @Test
    void totalCountIsCorrect() throws InterruptedException, ExecutionException {
        // 3 pages x 4 values = 12
        ParallelReportBuilder b = new ParallelReportBuilder();
        var s = b.generateReport(makePages(3, 4), 3);
        assertEquals(12, s.totalCount());
    }

    @Test
    void globalMaxIsCorrect() throws InterruptedException, ExecutionException {
        // pages [1,2,3] and [4,5,6] -> max = 6
        ParallelReportBuilder b = new ParallelReportBuilder();
        var s = b.generateReport(makePages(2, 3), 2);
        assertEquals(6, s.globalMax());
    }

    @Test
    void globalMinIsCorrect() throws InterruptedException, ExecutionException {
        // pages [1,2,3] and [4,5,6] -> min = 1
        ParallelReportBuilder b = new ParallelReportBuilder();
        var s = b.generateReport(makePages(2, 3), 2);
        assertEquals(1, s.globalMin());
    }

    @Test
    void pagesProcessedEqualsPageCount() throws InterruptedException, ExecutionException {
        int pageCount = 5;
        ParallelReportBuilder b = new ParallelReportBuilder();
        var s = b.generateReport(makePages(pageCount, 10), 3);
        assertEquals(pageCount, s.pagesProcessed());
    }

    // ---- getProcessedPageCount getter ---------------------------------------

    @Test
    void getProcessedPageCountMatchesSummary() throws InterruptedException, ExecutionException {
        ParallelReportBuilder b = new ParallelReportBuilder();
        b.generateReport(makePages(4, 5), 4);
        assertEquals(4, b.getProcessedPageCount());
    }

    // ---- edge case: single page ---------------------------------------------

    @Test
    void singlePageSummaryIsCorrect() throws InterruptedException, ExecutionException {
        List<List<Integer>> pages = new ArrayList<>();
        pages.add(List.of(7, 3, 9, 1));
        ParallelReportBuilder b = new ParallelReportBuilder();
        var s = b.generateReport(pages, 1);
        assertEquals(20L, s.totalSum());
        assertEquals(4,   s.totalCount());
        assertEquals(9,   s.globalMax());
        assertEquals(1,   s.globalMin());
        assertEquals(1,   s.pagesProcessed());
    }

    // ---- larger parallel run ------------------------------------------------

    @Test
    void largeParallelRunProducesCorrectSum() throws InterruptedException, ExecutionException {
        // 6 pages x 100 values = values 1..600; sum = 600*601/2 = 180300
        int pageCount = 6;
        int pageSize  = 100;
        ParallelReportBuilder b = new ParallelReportBuilder();
        var s = b.generateReport(makePages(pageCount, pageSize), pageCount);
        int n = pageCount * pageSize;
        long expected = (long) n * (n + 1) / 2;
        assertEquals(expected, s.totalSum());
        assertEquals(n,  s.totalCount());
        assertEquals(n,  s.globalMax());
        assertEquals(1,  s.globalMin());
        assertEquals(pageCount, s.pagesProcessed());
    }
}

