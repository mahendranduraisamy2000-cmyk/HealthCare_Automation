
package com.egypt.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.egypt.utills.BaseClass;

public class LoginPage extends BaseClass {

    private static final String USERNAME = "//input[@id='loginNm']";
    private static final String PASS = "//input[@id='password']";
    private static final String LOGIN_BTN = "//span[text()='Authorized Login']";
    private static final String DESBOARD_MESSAGE1 = "//span[text()='HYUNDAIPRO01']";
    private static final String DESBOARD_MESSAGE2 = "//span[text()='Office Manager']";

    private WebDriver driver;

    // Constructor
    public LoginPage(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver is NULL. Initialize driver before creating LoginPage."
            );
        }

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = USERNAME)
    private WebElement userName;

    @FindBy(xpath = PASS)
    private WebElement pass;

    @FindBy(xpath = LOGIN_BTN)
    private WebElement login_Btn;

    @FindBy(xpath = DESBOARD_MESSAGE1)
    private WebElement desnoardMessage1;

    @FindBy(xpath = DESBOARD_MESSAGE2)
    private WebElement desnoardMessage2;

    public void userNameAndPass(String user, String password)
            throws InterruptedException {

        elementSendKeys(userName, user);
        elementSendKeys(pass, password);
    }

    public void clickBtn() {
        elementClick(login_Btn);
    }

    public String suicessMessage() {
        return elementGetText(desnoardMessage1);
    }
}
