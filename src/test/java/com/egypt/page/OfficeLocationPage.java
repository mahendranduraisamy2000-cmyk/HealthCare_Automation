package com.egypt.page;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.egypt.utills.BaseClass;

/**
 * Office Location page object.
 * NOTE: Locators are written based on the visible labels in the manual test cases.
 * Verify / update each XPath against the real application DOM before execution.
 */
public class OfficeLocationPage extends BaseClass {

    // ---------- Navigation ----------
    private static final String PRACTICE_DETAILS_MENU = "//span[@class='sidebar-label' and normalize-space()='Practice Details']";
    private static final String OFFICE_LOCATION_MENU = "//a[contains(@class,'submenu-item') and contains(@href,'/location/create') and normalize-space()='Office Location']";
    private static final String CREATE_LOCATION_LINK = "//*[normalize-space()='Create Location' and (self::a or self::span or self::button)]";
    private static final String SEARCH_LOCATION_LINK = "//button[normalize-space()='Back to Search']";

    // ---------- Create Location form ----------
    private static final String FACILITY_NAME = "//input[@id='oName']";
    private static final String FIRST_NAME = "//input[@id='cFirstName']";
    private static final String LAST_NAME = "//input[@id='cLastName']";
    private static final String ADDRESS1 = "//input[@id='oAddress1']";
    private static final String CITY = "//input[@id='oCity']";
    private static final String EMAIL = "//input[@id='oEmail']";
    private static final String DAY_PHONE = "//input[@id='oTelephone1']";
    private static final String LOCAL_MEDICINE_DISCOUNT = "//input[@id='localDrugDiscount']";
    private static final String IMPORTED_MEDICINE_DISCOUNT = "//input[@id='importDrugDiscount']";
    private static final String LICENSE_ISSUE_DATE = "//input[@id='facilityIdMOHIssue']";
    private static final String LICENSE_EXPIRY_DATE = "//input[@id='facilityIdMOHExpiry']";
    private static final String GOVERNORATE = "//input[@id='oStateCode']";

    // ---------- Buttons ----------
    private static final String CONTINUE_BTN = "//button[@type='submit' and normalize-space()='Continue']";
    private static final String CREATE_LOCATION_BTN = "//button[normalize-space()='Create Location'] | //span[normalize-space()='Create Location']/ancestor::button";
    private static final String BACK_TO_EDIT_BTN = "//*[normalize-space()='Back to Edit' and (self::button or self::span or self::a)]";
    private static final String SEARCH_AGAIN_BTN = "//*[normalize-space()='Search Again' and (self::button or self::span or self::input)]";
    private static final String SAVE_BTN = "//*[normalize-space()='Save' and (self::button or self::span or self::input)]";

    // ---------- Messages / results ----------
    private static final String CREATE_LOCATION_HEADER = "//h2[contains(normalize-space(),'Create Location')]";
    private static final String CONFIRMATION_HEADER = "//*[contains(normalize-space(),'Confirm')]";
    private static final String ERROR_MESSAGES = "//div[contains(@class,'ant-form-item-explain-error')][normalize-space()!='']";
    private static final String SUCCESS_MESSAGE = "//*[contains(normalize-space(),'successfully')]";
    private static final String LOCATION_ID = "//*[contains(normalize-space(),'Location ID')]";
    private static final String RESULT_ROWS = "//table//tbody/tr";
    private static final String NO_RECORDS = "//*[contains(normalize-space(),'No record') or contains(normalize-space(),'No Record') or contains(normalize-space(),'No data')]";
    private static final String VIEW_LINK = "(//table//tbody/tr//*[normalize-space()='View'])[1]";
    private static final String MODIFY_LINK = "(//table//tbody/tr//*[normalize-space()='Modify' or normalize-space()='Edit'])[1]";
    private static final String TAB = "//*[(@role='tab' or self::a or self::span)][normalize-space()='%s']";

    private WebDriver driver;

    // Constructor
    public OfficeLocationPage(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver is NULL. Initialize driver before creating OfficeLocationPage."
            );
        }

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = PRACTICE_DETAILS_MENU)
    private WebElement practiceDetailsMenu;

    @FindBy(xpath = OFFICE_LOCATION_MENU)
    private WebElement officeLocationMenu;

    @FindBy(xpath = CREATE_LOCATION_LINK)
    private WebElement createLocationLink;

    @FindBy(xpath = SEARCH_LOCATION_LINK)
    private WebElement searchLocationLink;

    @FindBy(xpath = FACILITY_NAME)
    private WebElement facilityName;

    @FindBy(xpath = FIRST_NAME)
    private WebElement firstName;

    @FindBy(xpath = LAST_NAME)
    private WebElement lastName;

    @FindBy(xpath = ADDRESS1)
    private WebElement address1;

    @FindBy(xpath = CITY)
    private WebElement city;

    @FindBy(xpath = EMAIL)
    private WebElement email;

    @FindBy(xpath = DAY_PHONE)
    private WebElement dayPhone;

    @FindBy(xpath = LOCAL_MEDICINE_DISCOUNT)
    private WebElement localMedicineDiscount;

    @FindBy(xpath = IMPORTED_MEDICINE_DISCOUNT)
    private WebElement importedMedicineDiscount;

    @FindBy(xpath = LICENSE_ISSUE_DATE)
    private WebElement licenseIssueDate;

    @FindBy(xpath = LICENSE_EXPIRY_DATE)
    private WebElement licenseExpiryDate;

    @FindBy(xpath = GOVERNORATE)
    private WebElement governorate;

    @FindBy(xpath = CONTINUE_BTN)
    private WebElement continueBtn;

    @FindBy(xpath = CREATE_LOCATION_BTN)
    private WebElement createLocationBtn;

    @FindBy(xpath = BACK_TO_EDIT_BTN)
    private WebElement backToEditBtn;

    @FindBy(xpath = SEARCH_AGAIN_BTN)
    private WebElement searchAgainBtn;

    @FindBy(xpath = SAVE_BTN)
    private WebElement saveBtn;

    @FindBy(xpath = SUCCESS_MESSAGE)
    private WebElement successMessage;

    @FindBy(xpath = LOCATION_ID)
    private WebElement locationId;

    @FindBy(xpath = VIEW_LINK)
    private WebElement viewLink;

    @FindBy(xpath = MODIFY_LINK)
    private WebElement modifyLink;

    // ---------- Navigation actions ----------
    public void clickPracticeDetails() {
        elementClick(practiceDetailsMenu);
    }

    public void clickOfficeLocation() {
        elementClick(officeLocationMenu);
    }

    public void clickCreateLocationLink() {
        elementClick(createLocationLink);
    }

    public void clickSearchLocationLink() {
        elementClick(searchLocationLink);
    }

    public boolean isPresent(String xpath) {
        return !driver.findElements(By.xpath(xpath)).isEmpty();
    }

    /** Navigates to Create Location from anywhere after login. */
    public void openCreateLocation() {
        // Practice Details > Office Location opens the Create Location page directly
        if (!isPresent(FACILITY_NAME)) {
            if (!isPresent(OFFICE_LOCATION_MENU)) {
                clickPracticeDetails();
            }
            clickOfficeLocation();
        }
        webdriverWait(30, facilityName);
    }

    /** Navigates to Office Location search from anywhere after login. */
    public void openSearchLocation() {
        if (!isPresent(OFFICE_LOCATION_MENU)) {
            clickPracticeDetails();
        }
        clickOfficeLocation();
        if (isPresent(SEARCH_LOCATION_LINK)) {
            clickSearchLocationLink();
        }
    }

    // ---------- Form actions ----------
    private void type(WebElement element, String value) {
        clear(element);
        if (value != null && !value.isEmpty()) {
            elementSendKeys(element, value);
        }
    }

    public void enterLocationDetails(Map<String, String> data) {
        type(facilityName, data.get("facilityName"));
        type(firstName, data.get("firstName"));
        type(lastName, data.get("lastName"));
        type(address1, data.get("address1"));
        type(city, data.get("city"));
        type(email, data.get("email"));
    }

    public void clearMandatoryFields() {
        clear(facilityName);
        clear(firstName);
        clear(lastName);
        clear(address1);
        clear(city);
        clear(email);
    }

    public void enterFacilityName(String value) {
        type(facilityName, value);
    }

    public void enterCity(String value) {
        type(city, value);
    }

    public void enterEmail(String value) {
        type(email, value);
    }

    public void enterDayPhone(String value) {
        type(dayPhone, value);
    }

    public void enterDiscount(String field, String value) {
        if (field.equalsIgnoreCase("Local Medicine")) {
            type(localMedicineDiscount, value);
        } else {
            type(importedMedicineDiscount, value);
        }
    }

    public void enterLicenseIssueDate(String value) {
        type(licenseIssueDate, value);
    }

    public void enterLicenseExpiryDate(String value) {
        type(licenseExpiryDate, value);
    }

    public void selectGovernorate(String value) {
        elementSendKeys(governorate, value);
        governorate.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public String getFieldValue(String field) {
        switch (field) {
        case "facilityName": return facilityName.getDomProperty("value");
        case "firstName": return firstName.getDomProperty("value");
        case "lastName": return lastName.getDomProperty("value");
        case "address1": return address1.getDomProperty("value");
        case "city": return city.getDomProperty("value");
        case "email": return email.getDomProperty("value");
        default: throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

    // ---------- Buttons ----------
    public void clickContinue() {
        elementClick(continueBtn);
    }

    public void clickCreateLocationBtn() {
        elementClick(createLocationBtn);
    }

    public void doubleClickCreateLocationBtn() {
        webdriverWait(10, createLocationBtn);
        doubleClick(createLocationBtn);
    }

    public void clickBackToEdit() {
        elementClick(backToEditBtn);
    }

    public void clickSearchAgain() {
        elementClick(searchAgainBtn);
    }

    public void clickSave() {
        elementClick(saveBtn);
    }

    public void clickView() {
        elementClick(viewLink);
    }

    public void clickModify() {
        elementClick(modifyLink);
    }

    public void clickTab(String tabName) {
        elementClick(findLocatorByXpath(String.format(TAB, tabName)));
    }

    // ---------- Verifications ----------
    public boolean isValidationDisplayed() {
        waitForSeconds();
        return isPresent(ERROR_MESSAGES);
    }

    public int validationCount() {
        return driver.findElements(By.xpath(ERROR_MESSAGES)).size();
    }

    public boolean isOnCreateLocationPage() {
        return isPresent(FACILITY_NAME) || isPresent(CREATE_LOCATION_HEADER);
    }

    public boolean isConfirmationPageDisplayed() {
        return isPresent(CONFIRMATION_HEADER) || isPresent(CREATE_LOCATION_BTN);
    }

    public String getPageText() {
        return driver.findElement(By.tagName("body")).getText();
    }

    public String getSuccessMessage() {
        return elementGetText(successMessage);
    }

    public String getLocationIdText() {
        return elementGetText(locationId);
    }

    public List<WebElement> getResultRows() {
        waitForSeconds();
        return driver.findElements(By.xpath(RESULT_ROWS));
    }

    public boolean isNoRecordsDisplayed() {
        waitForSeconds();
        return isPresent(NO_RECORDS) || driver.findElements(By.xpath(RESULT_ROWS)).isEmpty();
    }
}
