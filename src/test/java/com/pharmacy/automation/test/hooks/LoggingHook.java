package com.pharmacy.automation.test.hooks;

import com.intuit.karate.RuntimeHook;
import com.intuit.karate.Suite;
import com.intuit.karate.core.FeatureRuntime;
import com.intuit.karate.core.ScenarioResult;
import com.intuit.karate.core.ScenarioRuntime;

/**
 * Simple runtime hook to track lifecycle events for Karate executions.
 */
public class LoggingHook implements RuntimeHook {

    @Override
    public void beforeSuite(Suite suite) {
        System.out.printf("[KarateSuite] Starting suite with %d features%n", suite.getFeatures().size());
    }

    @Override
    public boolean beforeFeature(FeatureRuntime fr) {
        System.out.printf("[KarateFeature] Starting %s%n", fr.feature.getRelativePath());
        return true;
    }

    @Override
    public boolean beforeScenario(ScenarioRuntime sr) {
        System.out.printf("[KarateScenario] Starting: %s%n", sr.scenario.getName());
        return true;
    }

    @Override
    public void afterScenario(ScenarioRuntime sr, ScenarioResult result) {
        String outcome = result.isFailed() ? "FAILED" : "PASSED";
        System.out.printf("[KarateScenario] Completed: %s (%s)%n", sr.scenario.getName(), outcome);
    }

    @Override
    public void afterFeature(FeatureRuntime fr) {
        System.out.printf("[KarateFeature] Completed %s%n", fr.feature.getRelativePath());
    }

    @Override
    public void afterSuite(Suite suite) {
        System.out.printf("[KarateSuite] Completed suite with %d features%n", suite.getFeatures().size());
    }
}
