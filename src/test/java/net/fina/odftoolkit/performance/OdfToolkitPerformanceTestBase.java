package net.fina.odftoolkit.performance;


import org.junit.Rule;
import org.junit.rules.TestName;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public abstract class OdfToolkitPerformanceTestBase {

    @Rule
    public TestName name = new TestName();

    public void processFile(int testSheetsCount, int colSize, int roxSize) throws Exception {
        String logName = name.getMethodName();
        StatisticsLogger statLog = new StatisticsLogger(logName);
        statLog.logMessage("Start - " + logName);

        statLog.logStage("Read template");
        byte[] template = Files.readAllBytes(new File("./src/test/resources/template.ods").toPath());

        statLog.logStage("Create Empty Document");
        SpreadsheetDocument storageSpreadsheetDocument = SpreadsheetDocument.newSpreadsheetDocument();

        for (int sheetIndex = 0; sheetIndex < testSheetsCount; sheetIndex++) {
            String logPrefix = (sheetIndex + 1) + "/" + testSheetsCount + " - ";

            statLog.logStage(logPrefix + "Load Document");
            try (ByteArrayInputStream in = new ByteArrayInputStream(template)) {
                SpreadsheetDocument spreadsheetDocument = SpreadsheetDocument.loadDocument(in);

                Table currentSheet = spreadsheetDocument.getSheetByIndex(0);

                statLog.logStage(logPrefix + "Write test data");
                for (int x = 0; x < colSize; x++) {
                    for (int y = 0; y < roxSize; y++) {
                        currentSheet.getCellByPosition(x, y).setStringValue("Demo Data");
                    }
                }

                statLog.logStage(logPrefix + "Append sheet");
                storageSpreadsheetDocument.appendSheet(currentSheet, "test" + (sheetIndex + 1));
            }
        }

        statLog.logSummary();

    }
}
