package com.egypt.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/Feature",
    glue = {"com.egypt.step", "com.egypt.hooks"},

    tags = "@TC01 or @TC02 or @TC03 or @TC04 or @TC05 or @TC06 or @TC07 or @TC08 or @TC09 or @TC10 or @TC11 or @TC12 or @TC13 or @TC14 or @TC15 or @TC16 or @TC17 or @TC18 or @TC19 or @OfficeLocation or @ClaimICD10 or @PatientRegistration",

    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },

    monochrome = true
)

public class TestRunnerClass extends AbstractTestNGCucumberTests {
}
