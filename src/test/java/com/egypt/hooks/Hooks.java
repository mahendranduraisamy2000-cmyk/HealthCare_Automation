package com.egypt.hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.egypt.utills.BaseClass;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class Hooks extends BaseClass {

	@After
	public void afterScenario(Scenario scenario) {
		if (driver != null) {
			try {
				if (scenario.isFailed()) {
					byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
					scenario.attach(screenshot, "image/png", scenario.getName() + " - Failure Screenshot");
				}
			} finally {
				closeBrowser();
			}
		}
	}

	public void closeBrowser() {
		if (driver != null) {
			WebDriver currentDriver = driver;
			driver = null;
			currentDriver.quit();
		}
	}
}
