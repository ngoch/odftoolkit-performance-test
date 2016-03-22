package net.fina.odftoolkit.performance;

import org.junit.Test;

public class OdfToolkitPerformanceTest extends OdfToolkitPerformanceTestBase {

    private static final int COL_SIZE = 3;
    private static final int ROW_SIZE = 5_000;

    @Test
    public void test_1() throws Exception {
        process(1);
    }

    @Test
    public void test_2() throws Exception {
        process(2);
    }

    @Test
    public void test_10() throws Exception {
        process(10);
    }

    private void process(int testSheetsCount) throws Exception {
        processFile(testSheetsCount, COL_SIZE, ROW_SIZE);
    }
}
