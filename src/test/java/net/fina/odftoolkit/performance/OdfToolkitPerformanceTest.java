package net.fina.odftoolkit.performance;

import org.junit.Test;

public class OdfToolkitPerformanceTest extends OdfToolkitPerformanceTestBase {

    private static final int COL_SIZE = 3;
    private static final int ROW_SIZE = 5_000;

    @Test
    public void test_1() throws Exception {
        processFile(1, COL_SIZE, ROW_SIZE, "test 1");
    }

    @Test
    public void test_2() throws Exception {
        processFile(2, COL_SIZE, ROW_SIZE, "test 2");
    }

    @Test
    public void test_10() throws Exception {
        processFile(10, COL_SIZE, ROW_SIZE, "test 10");
    }
}
