
package com.egypt.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/Feature/TC001_Login.feature",
    glue = "com.egypt",

    tags = "@TC001",

    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/json-reports/MainRunner/CucumberTestReport.json",
        "rerun:target/cucumber-reports/rerun-reports/rerun.txt",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },

    monochrome = true
)

public class TestRunnerClass extends AbstractTestNGCucumberTests {
}
