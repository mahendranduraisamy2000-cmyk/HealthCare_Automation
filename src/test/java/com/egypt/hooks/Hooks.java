package com.egypt.hooks;

import org.openqa.selenium.WebDriver;

import com.egypt.utills.BaseClass;

import io.cucumber.java.After;

public class Hooks extends BaseClass{

	@After
	public void closeBrowser() {
		if (driver != null) {
			WebDriver currentDriver = driver;
			driver = null;
			currentDriver.quit();
		}
	}
}
