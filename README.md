# HealthCare Automation

This repository contains a Selenium and Cucumber UI test for the healthcare application's login flow.

## Requirements

- Java 17
- Maven
- Google Chrome
- Network access to the application configured in `src/test/java/com/egypt/step/TC001_Login_StepDefination.java`

WebDriverManager resolves the matching ChromeDriver when the browser test starts.

## Run tests

Run the browser-free utility tests:

```shell
mvn -Dtest=BaseClassTest test
```

Run the Cucumber login scenario and utility tests:

```shell
mvn test
```

The login scenario uses the credentials in its feature file and requires the configured application to be reachable. Cucumber and Allure results are written under `target/`.
