package com.egypt.hooks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.egypt.utills.BaseClass;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class Hooks extends BaseClass {

	private static final Path ALLURE_RESULTS = Paths.get("target", "allure-results");

	// Runs once before all scenarios - adds Environment and Categories to the Allure report
	@BeforeAll
	public static void setUpReport() throws IOException {
		Files.createDirectories(ALLURE_RESULTS);

		Files.writeString(ALLURE_RESULTS.resolve("environment.properties"),
				"Application=EgyptHealthBook\n"
				+ "Environment=QA\n"
				+ "URL=http://158.101.230.16:8484/ehb-QA/login\n"
				+ "Browser=Chrome\n"
				+ "OS=" + System.getProperty("os.name") + "\n"
				+ "Java=" + System.getProperty("java.version") + "\n");

		Files.writeString(ALLURE_RESULTS.resolve("categories.json"),
				"[\n"
				+ "  {\"name\": \"Element not found / Timeout\", \"matchedStatuses\": [\"broken\", \"failed\"], \"messageRegex\": \".*(TimeoutException|NoSuchElementException).*\"},\n"
				+ "  {\"name\": \"Assertion failures\", \"matchedStatuses\": [\"failed\"], \"messageRegex\": \".*(expected|AssertionError).*\"},\n"
				+ "  {\"name\": \"Other errors\", \"matchedStatuses\": [\"broken\"]}\n"
				+ "]\n");
	}

	// Screenshot only when a step fails - attached to the failed step in the report
	@AfterStep
	public void screenshotOnFailure(Scenario scenario) {
		if (scenario.isFailed() && driver != null) {
			try {
				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", "Failure Screenshot");
			} catch (Exception e) {
				System.out.println("Unable to capture screenshot: " + e.getMessage());
			}
		}
	}

	@After
	public void closeBrowser() {
		if (driver != null) {
			WebDriver currentDriver = driver;
			driver = null;
			currentDriver.quit();
		}
	}
}
