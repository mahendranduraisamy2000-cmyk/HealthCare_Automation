package com.egypt.page;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.egypt.utills.BaseClass;
import com.egypt.utills.ConfigReader;

/**
 * Office Location page object.
 * Navigation + Create Location form locators are verified against the QA DOM (Ant Design UI).
 * Confirm / Search / View / Modify locators are still to be verified once the QA server is stable.
 */
public class OfficeLocationPage extends BaseClass {

    // ---------- Navigation (verified) ----------
    private static final String PRACTICE_DETAILS_MENU = "//span[@class='sidebar-label' and normalize-space()='Practice Details']";
    private static final String OFFICE_LOCATION_MENU = "//a[contains(@class,'submenu-item') and contains(@href,'/location/create') and normalize-space()='Office Location']";
    private static final String BACK_TO_SEARCH_BTN = "//button[normalize-space()='Back to Search']";

    // ---------- Create Location form (verified) ----------
    private static final String COMPANY_NAME = "//input[@id='prName']";
    private static final String FACILITY_NAME = "//input[@id='oName']";
    private static final String FIRST_NAME = "//input[@id='cFirstName']";
    private static final String LAST_NAME = "//input[@id='cLastName']";
    private static final String ADDRESS1 = "//input[@id='oAddress1']";
    private static final String CITY = "//input[@id='oCity']";
    private static final String GOVERNORATE = "//input[@id='oStateCode']";
    private static final String POSTAL_CODE = "//input[@id='oZip']";
    private static final String DAY_PHONE = "//input[@id='oTelephone1']";
    private static final String EMAIL = "//input[@id='oEmail']";
    private static final String LOCAL_MEDICINE_DISCOUNT = "//input[@id='localDrugDiscount']";
    private static final String IMPORTED_MEDICINE_DISCOUNT = "//input[@id='importDrugDiscount']";
    private static final String LICENSE_ISSUE_DATE = "//input[@id='facilityIdMOHIssue']";
    private static final String LICENSE_EXPIRY_DATE = "//input[@id='facilityIdMOHExpiry']";

    // ---------- Buttons ----------
    private static final String CONTINUE_BTN = "//button[@type='submit' and normalize-space()='Continue']";
    private static final String CLEAR_BTN = "//button[normalize-space()='Clear']";
    private static final String CREATE_LOCATION_BTN = "//button[normalize-space()='Create Location'] | //span[normalize-space()='Create Location']/ancestor::button";
    private static final String BACK_TO_EDIT_BTN = "//*[normalize-space()='Back to Edit' and (self::button or self::span or self::a)]";
    private static final String SEARCH_AGAIN_BTN = "//*[normalize-space()='Search Again' and (self::button or self::span or self::input)]";
    private static final String SAVE_BTN = "//*[normalize-space()='Save' and (self::button or self::span or self::input)]";

    // ---------- Messages / results ----------
    private static final String CREATE_LOCATION_HEADER = "//h2[contains(normalize-space(),'Create Location')]";
    private static final String ACTIVE_STEP_TITLE = "//div[contains(@class,'ant-steps-item-active')]//div[@class='ant-steps-item-title']";
    private static final String ERROR_MESSAGES = "//div[contains(@class,'ant-form-item-explain-error')][normalize-space()!='']";
    private static final String FIELD_ERROR = "//*[@id='%s']/ancestor::div[contains(concat(' ',normalize-space(@class),' '),' ant-form-item ')][1]//div[contains(@class,'ant-form-item-explain-error')]";
    private static final String SUCCESS_MESSAGE = "//*[contains(normalize-space(),'successfully')]";
    private static final String LOCATION_ID = "//*[contains(normalize-space(),'Location ID')]";
    private static final String RESULT_ROWS = "//table//tbody/tr[not(contains(@class,'ant-table-placeholder')) and not(contains(@class,'measure'))]";
    private static final String NO_RECORDS = "//*[contains(@class,'ant-empty') or contains(normalize-space(),'No record') or contains(normalize-space(),'No Record') or contains(normalize-space(),'No data')]";
    private static final String VIEW_LINK = "(//table//tbody/tr//*[normalize-space()='View' or @aria-label='eye'])[1]";
    private static final String MODIFY_LINK = "(//table//tbody/tr//*[normalize-space()='Modify' or normalize-space()='Edit' or @aria-label='edit'])[1]";
    private static final String TAB = "//*[(@role='tab' or self::a or self::span)][normalize-space()='%s']";

    /** Feature-file field key -> input id */
    private static final Map<String, String> FIELD_IDS = Map.ofEntries(
            Map.entry("companyName", "prName"),
            Map.entry("facilityName", "oName"),
            Map.entry("firstName", "cFirstName"),
            Map.entry("lastName", "cLastName"),
            Map.entry("address1", "oAddress1"),
            Map.entry("city", "oCity"),
            Map.entry("governorate", "oStateCode"),
            Map.entry("postalCode", "oZip"),
            Map.entry("dayPhone", "oTelephone1"),
            Map.entry("email", "oEmail"),
            Map.entry("localMedicineDiscount", "localDrugDiscount"),
            Map.entry("importedMedicineDiscount", "importDrugDiscount"));

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

    @FindBy(xpath = BACK_TO_SEARCH_BTN)
    private WebElement backToSearchBtn;

    @FindBy(xpath = COMPANY_NAME)
    private WebElement companyName;

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

    @FindBy(xpath = GOVERNORATE)
    private WebElement governorate;

    @FindBy(xpath = POSTAL_CODE)
    private WebElement postalCode;

    @FindBy(xpath = DAY_PHONE)
    private WebElement dayPhone;

    @FindBy(xpath = EMAIL)
    private WebElement email;

    @FindBy(xpath = LOCAL_MEDICINE_DISCOUNT)
    private WebElement localMedicineDiscount;

    @FindBy(xpath = IMPORTED_MEDICINE_DISCOUNT)
    private WebElement importedMedicineDiscount;

    @FindBy(xpath = LICENSE_ISSUE_DATE)
    private WebElement licenseIssueDate;

    @FindBy(xpath = LICENSE_EXPIRY_DATE)
    private WebElement licenseExpiryDate;

    @FindBy(xpath = CONTINUE_BTN)
    private WebElement continueBtn;

    @FindBy(xpath = CLEAR_BTN)
    private WebElement clearBtn;

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

    public void clickBackToSearch() {
        elementClick(backToSearchBtn);
    }

    public boolean isPresent(String xpath) {
        return !driver.findElements(By.xpath(xpath)).isEmpty();
    }

    /** Practice Details > Office Location opens the Create Location page directly. */
    public void openCreateLocation() {
        if (!isPresent(FACILITY_NAME)) {
            if (!isPresent(OFFICE_LOCATION_MENU)) {
                clickPracticeDetails();
            }
            clickOfficeLocation();
        }
        webdriverWait(ConfigReader.getInt("wait.explicit"), facilityName);
    }

    /** Create Location page > Back to Search opens the Office Location search page. */
    public void openSearchLocation() {
        if (!isPresent(SEARCH_AGAIN_BTN)) {
            openCreateLocation();
            clickBackToSearch();
        }
    }

    // ---------- Form actions ----------
    private WebElement field(String key) {
        String id = FIELD_IDS.get(key);
        if (id == null) {
            throw new IllegalArgumentException("Unknown field: " + key);
        }
        return findLocatorById(id);
    }

    /** Clears the field with Ctrl+A / Delete (React-controlled inputs ignore WebElement.clear()). */
    private void clearField(WebElement element) {
        webdriverWait(ConfigReader.getInt("wait.explicit"), element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
    }

    private void type(WebElement element, String value) {
        clearField(element);
        if (value != null && !value.isEmpty()) {
            elementSendKeys(element, value);
        }
    }

    public void enterField(String key, String value) {
        if ("governorate".equals(key)) {
            selectGovernorate(value);
        } else {
            type(field(key), value);
        }
    }

    /** Fills every key in the map (governorate is selected from the dropdown). */
    public void enterLocationDetails(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (entry.getValue() != null) {
                enterField(entry.getKey(), entry.getValue());
            }
        }
    }

    public void clearMandatoryFields() {
        for (String key : List.of("facilityName", "firstName", "lastName", "address1", "city", "dayPhone")) {
            clearField(field(key));
        }
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

    public void enterPostalCode(String value) {
        type(postalCode, value);
    }

    public void enterDiscount(String fieldName, String value) {
        if (fieldName.equalsIgnoreCase("Local Medicine")) {
            type(localMedicineDiscount, value);
        } else {
            type(importedMedicineDiscount, value);
        }
    }

    public void enterLicenseIssueDate(String value) {
        type(licenseIssueDate, value);
        licenseIssueDate.sendKeys(Keys.ENTER);
    }

    public void enterLicenseExpiryDate(String value) {
        type(licenseExpiryDate, value);
        licenseExpiryDate.sendKeys(Keys.ENTER);
    }

    /** Ant Design searchable select: type the option and press Enter. */
    public void selectGovernorate(String value) {
        elementClick(governorate);
        sendKeysAndEnter(governorate, value);
    }

    public String getFieldValue(String key) {
        if ("governorate".equals(key)) {
            return getSelectedGovernorate();
        }
        return getDomProperty(field(key), "value");
    }

    public String getSelectedGovernorate() {
        List<WebElement> selected = driver.findElements(By.xpath(
                GOVERNORATE + "/ancestor::div[contains(@class,'ant-select-selector')]//span[contains(@class,'ant-select-selection-item')]"));
        return selected.isEmpty() ? "" : selected.get(0).getText();
    }

    public int getMaxLength(String key) {
        String max = getDomProperty(field(key), "maxLength");
        return max == null || max.isEmpty() ? -1 : Integer.parseInt(max);
    }

    public boolean isReadOnly(String key) {
        return "true".equals(getDomProperty(field(key), "readOnly"));
    }

    // ---------- Buttons ----------
    public void clickContinue() {
        elementClick(continueBtn);
    }

    public void clickClear() {
        elementClick(clearBtn);
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

    public boolean isFieldErrorDisplayed(String key) {
        waitForSeconds();
        return isPresent(String.format(FIELD_ERROR, FIELD_IDS.get(key)));
    }

    public String getFieldError(String key) {
        List<WebElement> errors = driver.findElements(By.xpath(String.format(FIELD_ERROR, FIELD_IDS.get(key))));
        return errors.isEmpty() ? "" : errors.get(0).getText();
    }

    public boolean isOnCreateLocationPage() {
        return isPresent(FACILITY_NAME) || isPresent(CREATE_LOCATION_HEADER);
    }

    public String getActiveStep() {
        List<WebElement> step = driver.findElements(By.xpath(ACTIVE_STEP_TITLE));
        return step.isEmpty() ? "" : step.get(0).getText();
    }

    public boolean isConfirmationPageDisplayed() {
        waitForSeconds();
        return "Confirm".equals(getActiveStep()) || isPresent(CREATE_LOCATION_BTN);
    }

    public boolean isSearchPageDisplayed() {
        waitForSeconds();
        return isPresent(SEARCH_AGAIN_BTN) || getApplnUrl().contains("/location/search");
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
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
        return isPresent(NO_RECORDS) || getResultRows().isEmpty();
    }
}
