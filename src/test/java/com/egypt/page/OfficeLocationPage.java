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
 * Office Location page object - all locators verified against the QA DOM (Ant Design UI).
 * Pages: Create (Location Details -> Confirm -> Complete), Search, View (tabs), Modify.
 */
public class OfficeLocationPage extends BaseClass {

    // ---------- Navigation ----------
    private static final String PRACTICE_DETAILS_MENU = "//span[@class='sidebar-label' and normalize-space()='Practice Details']";
    private static final String OFFICE_LOCATION_MENU = "//a[contains(@class,'submenu-item') and contains(@href,'/location/create') and normalize-space()='Office Location']";
    private static final String BACK_TO_SEARCH_BTN = "//button[normalize-space()='Back to Search']";

    // ---------- Create / Modify form ----------
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
    private static final String SELECT_OPTION = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'hidden'))]//div[contains(@class,'ant-select-item-option') and @title='%s']";

    // ---------- Create wizard buttons ----------
    private static final String CONTINUE_BTN = "//button[@type='submit' and normalize-space()='Continue']";
    private static final String CLEAR_BTN = "//button[normalize-space()='Clear']";
    private static final String CREATE_LOCATION_BTN = "//button[normalize-space()='Create Location']";
    private static final String BACK_TO_EDIT_BTN = "//button[normalize-space()='Back to Edit']";
    private static final String SUBMIT_BTN = "//button[@type='submit' and normalize-space()='Submit']";
    private static final String MODIFY_CONFIRM_BTN = "//button[contains(@class,'ant-btn-primary') and not(@type='submit') and not(contains(normalize-space(),'Back'))]";
    private static final String CHANGES_SAVED = "//*[contains(normalize-space(),'Changes saved') or contains(normalize-space(),'Updated Successfully') or contains(normalize-space(),'updated successfully')][not(contains(@class,'ant-steps'))]";

    // ---------- Search page ----------
    private static final String SEARCH_FACILITY_NAME = "//input[@id='facilityName']";
    private static final String SEARCH_CITY = "//input[@id='city']";
    private static final String SEARCH_GOVERNORATE = "//input[@id='stateCode']/ancestor::div[contains(@class,'ant-select-selector')]";
    private static final String SEARCH_AGAIN_BTN = "//button[normalize-space()='Search Again']";
    private static final String RESULT_COUNT = "//*[starts-with(normalize-space(),'Found') and contains(normalize-space(),'Records')]";
    private static final String RESULT_ROWS = "//tr[contains(@class,'ant-table-row')]";
    private static final String NO_DATA = "//*[contains(@class,'ant-empty-description') or normalize-space()='No data']";
    private static final String ROW_VIEW_BTN = "(//tr[contains(@class,'ant-table-row')]//button[normalize-space()='View'])[1]";
    private static final String ROW_EDIT_BTN = "(//tr[contains(@class,'ant-table-row')]//button[normalize-space()='Edit'])[1]";

    // ---------- View page ----------
    private static final String VIEW_HEADER = "//h1[starts-with(normalize-space(),'Location:')] | //h2[starts-with(normalize-space(),'Location:')] | //h3[starts-with(normalize-space(),'Location:')] | //h4[starts-with(normalize-space(),'Location:')]";
    private static final String MODIFY_BTN = "//button[normalize-space()='Modify']";
    private static final String TAB = "//div[@role='tab' and normalize-space()='%s']";
    private static final String ACTIVE_TAB_PANE = "//div[contains(@class,'ant-tabs-tabpane-active')]";

    // ---------- Messages ----------
    private static final String CREATE_LOCATION_HEADER = "//h2[contains(normalize-space(),'Create Location')]";
    private static final String CONFIRM_HEADER = "//*[normalize-space()='Confirm New Location']";
    private static final String ACTIVE_STEP_TITLE = "//div[contains(@class,'ant-steps-item-process')]//div[@class='ant-steps-item-title']";
    private static final String ERROR_MESSAGES = "//div[contains(@class,'ant-form-item-explain-error')][normalize-space()!='']";
    private static final String FIELD_ERROR = "//*[@id='%s']/ancestor::div[contains(concat(' ',normalize-space(@class),' '),' ant-form-item ')][1]//div[contains(@class,'ant-form-item-explain-error')]";
    private static final String ALERT_MESSAGE = "//div[contains(@class,'ant-message-notice') or contains(@class,'ant-notification-notice') or (@role='alert' and contains(@class,'ant-alert'))]";
    private static final String SUCCESS_MESSAGE = "//*[normalize-space()='Location Created Successfully']";
    private static final String LOCATION_ID = "//*[starts-with(normalize-space(),'Location ID:')]";

    /** Feature-file field key -> input id (Create / Modify form) */
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

    @FindBy(xpath = SUBMIT_BTN)
    private WebElement submitBtn;

    @FindBy(xpath = SEARCH_FACILITY_NAME)
    private WebElement searchFacilityName;

    @FindBy(xpath = SEARCH_CITY)
    private WebElement searchCity;

    @FindBy(xpath = SEARCH_GOVERNORATE)
    private WebElement searchGovernorate;

    @FindBy(xpath = SEARCH_AGAIN_BTN)
    private WebElement searchAgainBtn;

    @FindBy(xpath = ROW_VIEW_BTN)
    private WebElement rowViewBtn;

    @FindBy(xpath = ROW_EDIT_BTN)
    private WebElement rowEditBtn;

    @FindBy(xpath = MODIFY_BTN)
    private WebElement modifyBtn;

    @FindBy(xpath = SUCCESS_MESSAGE)
    private WebElement successMessage;

    @FindBy(xpath = LOCATION_ID)
    private WebElement locationId;

    private int explicitWait() {
        return ConfigReader.getInt("wait.explicit");
    }

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

    /** True only when the element exists AND is visible (collapsed menus exist in the DOM but are hidden). */
    public boolean isVisible(String xpath) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        return !elements.isEmpty() && displayedMethod(elements.get(0));
    }

    /** Practice Details > Office Location opens the Create Location page directly. */
    public void openCreateLocation() {
        if (!isPresent(FACILITY_NAME) || isPresent(SUBMIT_BTN) || isPresent(SUCCESS_MESSAGE)) {
            if (!isVisible(OFFICE_LOCATION_MENU)) {
                clickPracticeDetails();
                waitForSeconds();
            }
            if (isVisible(OFFICE_LOCATION_MENU)) {
                clickOfficeLocation();
            } else {
                goToUrl(ConfigReader.get("app.url").replaceAll("/$", "") + "/location/create");
            }
        }
        webdriverWait(explicitWait(), facilityName);
    }

    /** Opens the Office Location search page (direct URL - works from any page, incl. Create > Complete step). */
    public void openSearchLocation() {
        if (!isVisible(SEARCH_AGAIN_BTN)) {
            goToUrl(ConfigReader.get("app.url").replaceAll("/$", "") + "/location/search");
        }
        webdriverWait(explicitWait(), searchFacilityName);
    }

    /** Waits until the element located by the XPath is visible and returns it. */
    private WebElement waitFor(String xpath) {
        return new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(explicitWait()))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
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
        webdriverWait(explicitWait(), element);
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
        // TAB (not ENTER) - ENTER would submit the whole form
        type(licenseIssueDate, value);
        licenseIssueDate.sendKeys(Keys.TAB);
    }

    public void enterLicenseExpiryDate(String value) {
        type(licenseExpiryDate, value);
        licenseExpiryDate.sendKeys(Keys.TAB);
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

    // ---------- Create wizard buttons ----------
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
        webdriverWait(explicitWait(), createLocationBtn);
        doubleClick(createLocationBtn);
    }

    public void clickBackToEdit() {
        elementClick(backToEditBtn);
    }

    // ---------- Search ----------
    public void enterSearchFacilityName(String value) {
        type(searchFacilityName, value);
    }

    public void enterSearchCity(String value) {
        type(searchCity, value);
    }

    /** Search Governorate is a non-searchable Ant select: open it and click the option. */
    public void selectSearchGovernorate(String value) {
        // Options are virtualised (only ~8 rendered) - scroll with ARROW_DOWN until the option is rendered
        elementClick(searchGovernorate);
        String option = "//div[contains(@class,'ant-select-dropdown')]//div[contains(@class,'ant-select-item-option') and (@title='"
                + value + "' or @title='" + value + " Governorate')]";
        for (int i = 0; i < 60 && !isPresent(option); i++) {
            driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        }
        jsClick(waitFor(option));
        waitForSeconds();
    }

    public void clickSearchAgain() {
        elementClick(searchAgainBtn);
        waitForSeconds();
    }

    public int getResultCount() {
        String text = elementGetText(waitFor(RESULT_COUNT));
        return Integer.parseInt(text.replaceAll("\\D+", ""));
    }

    public List<WebElement> getResultRows() {
        return driver.findElements(By.xpath(RESULT_ROWS));
    }

    public boolean isNoRecordsDisplayed() {
        return getResultCount() == 0 || isPresent(NO_DATA);
    }

    public void clickView() {
        elementClick(rowViewBtn);
        waitFor(String.format(TAB, "Location Details"));
    }

    public void clickEdit() {
        elementClick(rowEditBtn);
        webdriverWait(explicitWait(), submitBtn);
    }

    // ---------- View / Modify ----------
    public boolean isViewPageDisplayed() {
        waitForSeconds();
        return isPresent(VIEW_HEADER) || getApplnUrl().matches(".*/location/\\d+$");
    }

    public void clickModify() {
        elementClick(modifyBtn);
        webdriverWait(explicitWait(), submitBtn);
    }

    /** Modify wizard: Edit Details -> Submit -> Confirm (Review changes) -> confirm button -> Complete (Changes saved). */
    public void clickSubmit() {
        elementClick(submitBtn);
        waitForSeconds();
        List<WebElement> confirmButtons = driver.findElements(By.xpath(MODIFY_CONFIRM_BTN));
        for (WebElement btn : confirmButtons) {
            if (displayedMethod(btn)) {
                elementClick(btn);
                waitForSeconds();
                break;
            }
        }
    }

    public boolean isChangesSaved() {
        return isVisible(CHANGES_SAVED) || "Complete".equals(getActiveStep());
    }

    public void clickTab(String tabName) {
        elementClick(waitFor(String.format(TAB, tabName)));
        waitForSeconds();
    }

    public String getActiveTabText() {
        return elementGetText(waitFor(ACTIVE_TAB_PANE));
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

    public String getAlertMessages() {
        StringBuilder sb = new StringBuilder();
        for (WebElement e : driver.findElements(By.xpath(ALERT_MESSAGE))) {
            sb.append(e.getText()).append(' ');
        }
        return sb.toString().trim();
    }

    public boolean isOnCreateLocationPage() {
        return isPresent(CREATE_LOCATION_HEADER) && "Location Details".equals(getActiveStep());
    }

    public String getActiveStep() {
        List<WebElement> step = driver.findElements(By.xpath(ACTIVE_STEP_TITLE));
        return step.isEmpty() ? "" : step.get(0).getText();
    }

    public boolean isConfirmationPageDisplayed() {
        waitForSeconds();
        return isPresent(CONFIRM_HEADER) || "Confirm".equals(getActiveStep());
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

    public boolean isSuccessDisplayed() {
        try {
            webdriverWait(explicitWait(), successMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessage() {
        return elementGetText(successMessage);
    }

    /** e.g. "Location ID: 10022007 - Auto Schoen Clinic 23689" */
    public String getLocationIdText() {
        return elementGetText(locationId);
    }
}
