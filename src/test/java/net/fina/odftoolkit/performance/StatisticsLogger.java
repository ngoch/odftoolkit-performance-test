package net.fina.odftoolkit.performance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticsLogger {

    private static ThreadLocal<StatisticsLogger> statLogger = new ThreadLocal<>();

    private List<StageInfo> stageInfos;

    private String title;

    public static StatisticsLogger getLogger() {
        return getLogger(true);
    }

    public static StatisticsLogger getLogger(boolean create) {
        StatisticsLogger logger = statLogger.get();
        if (logger == null && create) {
            logger = new StatisticsLogger();
            setLogger(logger);
        }
        return logger;
    }

    public static void setLogger(StatisticsLogger logger) {
        statLogger.set(logger);
    }

    public StatisticsLogger(String title) {
        this.stageInfos = new ArrayList<>();
        this.title = title;
    }

    public StatisticsLogger() {
        this("Statistics");
    }

    public void logStage(String stageName) {
        stageInfos.add(new StageInfo(stageName, System.currentTimeMillis()));
    }

    public void logSummary() {

        if (stageInfos.size() > 0) {

            StageInfo firstStage = stageInfos.get(0);
            long currentTime = System.currentTimeMillis();
            long totalDuration = currentTime - firstStage.getStartTime();

            String line = getLine(title.length() + 2);
            logMessage("/" + line + "/");
            logMessage("/ " + title + " /");
            logMessage("/" + line + "/");

            for (int i = 0; i < stageInfos.size(); i++) {

                StageInfo stage = stageInfos.get(i);
                long stageEndTime;
                if (i < stageInfos.size() - 1) {
                    stageEndTime = stageInfos.get(i + 1).getStartTime();
                } else {
                    stageEndTime = currentTime;
                }

                long duration = stageEndTime - stage.getStartTime();

                logMessage("Stage: \"" + stage.getName() + "\", duration: " + duration +
                        "ms (" + (int) ((double) duration / totalDuration * 100) + "%)");
            }
            logMessage("Stage total count: " + stageInfos.size() + ", Operation: " + title + ", Total time: " + totalDuration + "ms");

            stageInfos.clear();
        }
    }

    public void logMessage(String message) {
        System.out.println(message);
    }

    private String getLine(int size) {
        char[] chars = new char[size];
        Arrays.fill(chars, '-');
        return new String(chars);
    }

    class StageInfo {

        public String name;

        public long startTime;

        public StageInfo(String name, long startTime) {
            this.name = name;
            this.startTime = startTime;
        }

        public String getName() {
            return name;
        }

        public long getStartTime() {
            return startTime;
        }
    }
}