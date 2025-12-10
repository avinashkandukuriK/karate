package com.pharmacy.automation.test.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.intuit.karate.Results;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class ExtentReportGenerator {

    private ExtentReportGenerator() {
    }

    public static void generate(String reportDir, Results results) {
        Path output = Paths.get(reportDir, "extent-report.html");
        ExtentSparkReporter spark = new ExtentSparkReporter(output.toString());
        spark.config().setReportName("Karate API Automation");
        spark.config().setDocumentTitle("Karate Extent Report");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);

        ExtentTest summary = extent.createTest("Execution Summary");
        int scenarioCount = results.getScenarioCount();
        int failed = results.getFailCount();
        int passed = Math.max(0, scenarioCount - failed);

        summary.info("Features executed: " + results.getFeatureCount());
        summary.info("Scenarios executed: " + scenarioCount);
        summary.pass("Scenarios passed: " + passed);
        if (failed > 0) {
            summary.fail("Scenarios failed: " + failed);
            results.getErrorMessages().forEach(summary::fail);
        }

        extent.flush();
    }
}
