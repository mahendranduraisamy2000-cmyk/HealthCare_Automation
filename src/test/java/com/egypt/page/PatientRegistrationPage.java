package com.egypt.page;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.egypt.utills.BaseClass;

public class PatientRegistrationPage extends BaseClass {

    // ---------- Sidebar Navigation ----------
    private static final String SEARCH_PATIENT_SIDEBAR_MENU = "//span[@class='sidebar-label' and normalize-space()='Search patient']";
    private static final String PATIENT_DETAILS_MENU = "//span[@class='sidebar-label' and normalize-space()='Patient Details']";
    private static final String PATIENT_REGISTRATION_SUBMENU = "//a[contains(@class,'submenu-item') and contains(@href,'/patient/search-register') and normalize-space()='Patient Registration']";

    // ---------- Search Patient Page (/patient/search-register) ----------
    private static final String SEARCH_PATIENT_HEADER = "//h2[contains(normalize-space(),'Search Patient')]";
    private static final String SEARCH_PATIENT_ID = "//input[@id='pid']";
    private static final String SEARCH_NID = "//input[@id='ssn']";
    private static final String SEARCH_FIRST_NAME = "//input[@id='pfnm']";
    private static final String SEARCH_LAST_NAME = "//input[@id='plnm']";
    private static final String SEARCH_USERNAME = "//input[@id='username']";
    private static final String SEARCH_DOB = "//input[@id='strdate']";
    private static final String SEARCH_PHONE = "//input[@id='phone']";
    private static final String SEARCH_SPORTS_TYPE_SELECT = "//input[@id='sportType']/ancestor::div[contains(@class,'ant-select')]";
    private static final String SEARCH_CLUB_SELECT = "//input[@id='sportclubname']/ancestor::div[contains(@class,'ant-select')]";
    private static final String GO_BACK_BTN = "//button[normalize-space()='Go Back' or .//span[normalize-space()='Go Back']]";
    private static final String CLEAR_BTN = "//button[normalize-space()='Clear' or .//span[normalize-space()='Clear']]";
    private static final String SEARCH_BTN = "//button[@type='submit' and (normalize-space()='Search' or .//span[normalize-space()='Search'])]";
    private static final String NEW_PATIENT_REG_BTN = "//button[contains(normalize-space(),'New patient Registration')]";
    private static final String NO_RECORDS_MSG = "//*[contains(normalize-space(),'No patient records matched the search criteria.')]";
    private static final String TOAST_MESSAGE = "//div[contains(@class,'ant-message-warning') or contains(@class,'ant-message-notice-warning')]";

    // ---------- Select Patient Page (/patient/search) ----------
    private static final String SELECT_PATIENT_HEADER = "//h2[contains(normalize-space(),'Select Patient')]";
    private static final String SELECT_PATIENT_FIRST_NAME = "//input[@id='firstName']";
    private static final String SELECT_PATIENT_LAST_NAME = "//input[@id='lastName']";
    private static final String SELECT_PATIENT_USERNAME = "//input[@id='usrname']";
    private static final String SELECT_PATIENT_MED_NO = "//input[@id='medno']";
    private static final String SELECT_PATIENT_DOB = "//input[@id='dob']";
    private static final String SELECT_PATIENT_ORDER_BY = "//input[@id='orderby']/ancestor::div[contains(@class,'ant-select')]";
    private static final String SELECT_PATIENT_CLUB_SELECT = "//input[@id='clubName']/ancestor::div[contains(@class,'ant-select')]";
    private static final String SELECT_BY_REGULAR_RADIO = "//input[@type='radio' and @value='Regular Search']";
    private static final String SELECT_BY_STATEMENT_RADIO = "//input[@type='radio' and @value='Statement']";
    private static final String NEW_PATIENT_BTN = "//button[normalize-space()='New patient' or .//span[normalize-space()='New patient']]";
    private static final String SELECT_PATIENT_NO_RESULTS = "//*[contains(normalize-space(),'Your search did not yield any results')]";
    private static final String SELECT_PATIENT_RESULTS_SECTION = "//div[contains(@class,'search-results-section')]";

    // ---------- New Patient Registration Page (/patient/registration) ----------
    private static final String PATIENT_SEARCH_BREADCRUMB = "//nav[contains(@class,'ant-breadcrumb')]//a[normalize-space()='Patient Search']";
    private static final String PATIENT_REG_HEADER = "//h2[normalize-space()='Patient Registration']";
    private static final String BACK_BTN = "//button[normalize-space()='Back' or .//span[normalize-space()='Back']]";
    private static final String PREFIX_SELECT = "//input[@id='prefix']/ancestor::div[contains(@class,'ant-select')]";
    private static final String REG_FIRST_NAME = "//input[@id='firstName']";
    private static final String REG_MIDDLE_NAME = "//input[@id='middleName']";
    private static final String REG_LAST_NAME = "//input[@id='lastName']";
    private static final String SUFFIX_SELECT = "//input[@id='suffix']/ancestor::div[contains(@class,'ant-select')]";
    private static final String REG_FATHER_NAME = "//input[@id='fatherName']";
    private static final String REG_MOTHER_NAME = "//input[@id='motherName']";
    private static final String REG_DOB = "//input[@id='dob']";
    private static final String REG_AGE = "//input[@id='age']";
    private static final String SEX_SELECT = "//input[@id='sex']/ancestor::div[contains(@class,'ant-select')]";
    private static final String REG_NID = "//input[@id='ssn']";
    private static final String REG_MED_NO = "//input[@id='medno']";
    private static final String REG_ADDRESS1 = "//input[@id='address1']";
    private static final String REG_ADDRESS2 = "//input[@id='address2']";
    private static final String REG_CITY = "//input[@id='city']";
    private static final String REG_STATE_SELECT = "//input[@id='state']/ancestor::div[contains(@class,'ant-select')]";
    private static final String REG_COUNTRY_SELECT = "//input[@id='country']/ancestor::div[contains(@class,'ant-select')]";
    private static final String REG_ZIP = "//input[@id='zip']";
    private static final String REG_CELL_PHONE = "//input[@id='cellPhone']";
    private static final String REG_EVE_PHONE = "//input[@id='evePhone']";
    private static final String REG_EXT = "//input[@id='ext']";
    private static final String WHATSAPP_YES_RADIO = "//div[@id='whatsAppNo']//input[@value='Yes']";
    private static final String WHATSAPP_NO_RADIO = "//div[@id='whatsAppNo']//input[@value='No']";
    private static final String REG_USERNAME = "//input[@id='username']";
    private static final String REG_PASSWORD = "//input[@id='password']";
    private static final String REG_PASSWORD_EYE_ICON = "//span[contains(@class,'ant-input-password-icon')]";
    private static final String REG_EMAIL = "//input[@id='email']";
    private static final String REG_PRACTICE_READONLY = "//label[normalize-space()='Practice']/ancestor::div[contains(@class,'ant-form-item')]//input[@readonly]";
    private static final String REG_LOCATION_SELECT = "//input[@id='locationId']/ancestor::div[contains(@class,'ant-select')]";
    private static final String REG_PHYSICIAN_SELECT = "//input[@id='physicianId']/ancestor::div[contains(@class,'ant-select')]";
    private static final String INSURED_YES_RADIO = "//div[@id='insuredStatus']//input[@value='1']";
    private static final String INSURED_SELF_RADIO = "//div[@id='insuredStatus']//input[@value='2']";
    private static final String VIP_YES_RADIO = "//div[@id='vipStatus']//input[@value='Y']";
    private static final String VIP_NO_RADIO = "//div[@id='vipStatus']//input[@value='N']";
    private static final String SUBMIT_BTN = "//button[@type='submit' and (normalize-space()='Submit' or .//span[normalize-space()='Submit'])]";
    private static final String CANCEL_BTN = "//button[normalize-space()='Cancel' or .//span[normalize-space()='Cancel']]";

    // ---------- Common Ant Design Validation & Success ----------
    private static final String FORM_ERRORS = "//div[contains(@class,'ant-form-item-explain-error')][normalize-space()!='']";
    private static final String VISIBLE_DROPDOWN_OPTION = "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'ant-select-dropdown-hidden'))]//div[contains(@class,'ant-select-item-option') and normalize-space()='%s']";
    private static final String FIRST_VISIBLE_DROPDOWN_OPTION = "(//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'ant-select-dropdown-hidden'))]//div[contains(@class,'ant-select-item-option')])[1]";
    private static final String SUCCESS_ALERT = "//div[contains(@class,'ant-alert-success')]";
    private static final String ADMIT_TO_BED_BTN = "//div[contains(@class,'ant-alert-success')]//button[normalize-space()='Admit to Bed' or .//span[normalize-space()='Admit to Bed']]";
    private static final String DOB_DISABLED_FUTURE_CELLS = "//div[contains(@class,'ant-picker-dropdown') and not(contains(@class,'ant-picker-dropdown-hidden'))]//td[contains(@class,'ant-picker-cell-in-view') and contains(@class,'ant-picker-cell-disabled')]";
    private static final String TABLE_DATA_ROWS = "//table//tbody//tr[not(contains(@class,'ant-table-placeholder'))]";
    private static final String ADD_TO_PRACTICE_BTN = "//table//tbody//button[normalize-space()='Add to Practice' or .//span[normalize-space()='Add to Practice']]";
    private static final String HEADER_PRACTICE_BANNER = "//*[not(*) and contains(normalize-space(),'HOSPVALUE01') and contains(normalize-space(),'--')]";

    private WebDriver driver;

    public PatientRegistrationPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver is NULL. Initialize driver before creating PatientRegistrationPage."
            );
        }
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Navigation Elements ----------
    @FindBy(xpath = SEARCH_PATIENT_SIDEBAR_MENU)
    private WebElement searchPatientSidebarMenu;

    @FindBy(xpath = PATIENT_DETAILS_MENU)
    private WebElement patientDetailsMenu;

    @FindBy(xpath = PATIENT_REGISTRATION_SUBMENU)
    private WebElement patientRegistrationSubmenu;

    // ---------- Search Patient Elements ----------
    @FindBy(xpath = SEARCH_PATIENT_HEADER)
    private WebElement searchPatientHeader;

    @FindBy(xpath = SEARCH_PATIENT_ID)
    private WebElement searchPatientId;

    @FindBy(xpath = SEARCH_NID)
    private WebElement searchNid;

    @FindBy(xpath = SEARCH_FIRST_NAME)
    private WebElement searchFirstName;

    @FindBy(xpath = SEARCH_LAST_NAME)
    private WebElement searchLastName;

    @FindBy(xpath = SEARCH_USERNAME)
    private WebElement searchUsername;

    @FindBy(xpath = SEARCH_DOB)
    private WebElement searchDob;

    @FindBy(xpath = SEARCH_PHONE)
    private WebElement searchPhone;

    @FindBy(xpath = SEARCH_SPORTS_TYPE_SELECT)
    private WebElement searchSportsTypeSelect;

    @FindBy(xpath = SEARCH_CLUB_SELECT)
    private WebElement searchClubSelect;

    @FindBy(xpath = GO_BACK_BTN)
    private WebElement goBackBtn;

    @FindBy(xpath = CLEAR_BTN)
    private WebElement clearBtn;

    @FindBy(xpath = SEARCH_BTN)
    private WebElement searchBtn;

    @FindBy(xpath = NEW_PATIENT_REG_BTN)
    private WebElement newPatientRegBtn;

    // ---------- Select Patient Elements ----------
    @FindBy(xpath = SELECT_PATIENT_HEADER)
    private WebElement selectPatientHeader;

    @FindBy(xpath = SELECT_PATIENT_FIRST_NAME)
    private WebElement selectPatientFirstName;

    @FindBy(xpath = SELECT_PATIENT_LAST_NAME)
    private WebElement selectPatientLastName;

    @FindBy(xpath = SELECT_PATIENT_USERNAME)
    private WebElement selectPatientUsername;

    @FindBy(xpath = SELECT_PATIENT_MED_NO)
    private WebElement selectPatientMedNo;

    @FindBy(xpath = SELECT_PATIENT_DOB)
    private WebElement selectPatientDob;

    @FindBy(xpath = SELECT_PATIENT_ORDER_BY)
    private WebElement selectPatientOrderBy;

    @FindBy(xpath = SELECT_PATIENT_CLUB_SELECT)
    private WebElement selectPatientClubSelect;

    @FindBy(xpath = SELECT_BY_REGULAR_RADIO)
    private WebElement selectByRegularRadio;

    @FindBy(xpath = SELECT_BY_STATEMENT_RADIO)
    private WebElement selectByStatementRadio;

    @FindBy(xpath = NEW_PATIENT_BTN)
    private WebElement newPatientBtn;

    // ---------- New Patient Registration Elements ----------
    @FindBy(xpath = PATIENT_SEARCH_BREADCRUMB)
    private WebElement patientSearchBreadcrumb;

    @FindBy(xpath = PATIENT_REG_HEADER)
    private WebElement patientRegHeader;

    @FindBy(xpath = BACK_BTN)
    private WebElement backBtn;

    @FindBy(xpath = PREFIX_SELECT)
    private WebElement prefixSelect;

    @FindBy(xpath = REG_FIRST_NAME)
    private WebElement regFirstName;

    @FindBy(xpath = REG_MIDDLE_NAME)
    private WebElement regMiddleName;

    @FindBy(xpath = REG_LAST_NAME)
    private WebElement regLastName;

    @FindBy(xpath = SUFFIX_SELECT)
    private WebElement suffixSelect;

    @FindBy(xpath = REG_FATHER_NAME)
    private WebElement regFatherName;

    @FindBy(xpath = REG_MOTHER_NAME)
    private WebElement regMotherName;

    @FindBy(xpath = REG_DOB)
    private WebElement regDob;

    @FindBy(xpath = REG_AGE)
    private WebElement regAge;

    @FindBy(xpath = SEX_SELECT)
    private WebElement sexSelect;

    @FindBy(xpath = REG_NID)
    private WebElement regNid;

    @FindBy(xpath = REG_MED_NO)
    private WebElement regMedNo;

    @FindBy(xpath = REG_ADDRESS1)
    private WebElement regAddress1;

    @FindBy(xpath = REG_ADDRESS2)
    private WebElement regAddress2;

    @FindBy(xpath = REG_CITY)
    private WebElement regCity;

    @FindBy(xpath = REG_STATE_SELECT)
    private WebElement regStateSelect;

    @FindBy(xpath = REG_COUNTRY_SELECT)
    private WebElement regCountrySelect;

    @FindBy(xpath = REG_ZIP)
    private WebElement regZip;

    @FindBy(xpath = REG_CELL_PHONE)
    private WebElement regCellPhone;

    @FindBy(xpath = REG_EVE_PHONE)
    private WebElement regEvePhone;

    @FindBy(xpath = REG_EXT)
    private WebElement regExt;

    @FindBy(xpath = WHATSAPP_YES_RADIO)
    private WebElement whatsAppYesRadio;

    @FindBy(xpath = WHATSAPP_NO_RADIO)
    private WebElement whatsAppNoRadio;

    @FindBy(xpath = REG_USERNAME)
    private WebElement regUsername;

    @FindBy(xpath = REG_PASSWORD)
    private WebElement regPassword;

    @FindBy(xpath = REG_PASSWORD_EYE_ICON)
    private WebElement regPasswordEyeIcon;

    @FindBy(xpath = REG_EMAIL)
    private WebElement regEmail;

    @FindBy(xpath = REG_PRACTICE_READONLY)
    private WebElement regPracticeReadonly;

    @FindBy(xpath = REG_LOCATION_SELECT)
    private WebElement regLocationSelect;

    @FindBy(xpath = REG_PHYSICIAN_SELECT)
    private WebElement regPhysicianSelect;

    @FindBy(xpath = INSURED_YES_RADIO)
    private WebElement insuredYesRadio;

    @FindBy(xpath = INSURED_SELF_RADIO)
    private WebElement insuredSelfRadio;

    @FindBy(xpath = VIP_YES_RADIO)
    private WebElement vipYesRadio;

    @FindBy(xpath = VIP_NO_RADIO)
    private WebElement vipNoRadio;

    @FindBy(xpath = SUBMIT_BTN)
    private WebElement submitBtn;

    @FindBy(xpath = CANCEL_BTN)
    private WebElement cancelBtn;

    // ---------- Helpers ----------
    private void scrollAndClick(WebElement element) {
        webdriverWait(15, element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        try {
            clickableOfElementWait(10, element).click();
        } catch (ElementClickInterceptedException e) {
            jsClick(element);
        }
    }

    private void clearAndType(WebElement element, String value) {
        webdriverWait(15, element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        elementSendKeys(element, value);
    }

    private void openAntSelect(WebElement selectBox) {
        webdriverWait(15, selectBox);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", selectBox);
        WebElement selector = selectBox.findElement(By.cssSelector(".ant-select-selector"));
        try {
            clickableOfElementWait(10, selector).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('mousedown', {bubbles:true})); arguments[0].click();",
                selector
            );
        }
    }

    private void selectAntOption(WebElement selectBox, String optionText) {
        openAntSelect(selectBox);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath(String.format(VISIBLE_DROPDOWN_OPTION, optionText))
        ));
        try {
            option.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(option);
        }
    }

    // ---------- Navigation Actions ----------
    public void navigateToPatientRegistration() {
        scrollAndClick(patientDetailsMenu);
        scrollAndClick(patientRegistrationSubmenu);
        webdriverWait(20, searchPatientHeader);
    }

    public void navigateToSelectPatient() {
        scrollAndClick(searchPatientSidebarMenu);
        webdriverWait(20, selectPatientHeader);
    }

    // ---------- Search Patient Actions (/patient/search-register) ----------
    public boolean areSearchPatientElementsDisplayed() {
        webdriverWait(15, searchPatientHeader);
        return displayedMethod(searchPatientHeader)
            && displayedMethod(searchPatientId)
            && displayedMethod(searchNid)
            && displayedMethod(searchFirstName)
            && displayedMethod(searchLastName)
            && displayedMethod(searchUsername)
            && displayedMethod(searchDob)
            && displayedMethod(searchPhone)
            && displayedMethod(searchSportsTypeSelect)
            && displayedMethod(searchClubSelect)
            && displayedMethod(goBackBtn)
            && displayedMethod(clearBtn)
            && displayedMethod(searchBtn)
            && displayedMethod(newPatientRegBtn);
    }

    public void enterSearchPatientId(String pid) {
        clearAndType(searchPatientId, pid);
    }

    public void enterSearchNid(String nid) {
        clearAndType(searchNid, nid);
    }

    public void enterSearchFirstName(String firstName) {
        clearAndType(searchFirstName, firstName);
    }

    public void enterSearchLastName(String lastName) {
        clearAndType(searchLastName, lastName);
    }

    public void enterSearchUsername(String username) {
        clearAndType(searchUsername, username);
    }

    public void enterSearchPhone(String phone) {
        clearAndType(searchPhone, phone);
    }

    public void selectSportsType(String sportName) {
        selectAntOption(searchSportsTypeSelect, sportName);
    }

    public void selectClubTeamName(String clubName) {
        selectAntOption(searchClubSelect, clubName);
    }

    public String getSelectedSportsType() {
        return elementGetText(searchSportsTypeSelect).trim();
    }

    public String getSelectedClubTeamName() {
        return elementGetText(searchClubSelect).trim();
    }

    public void clickSearch() {
        scrollAndClick(searchBtn);
    }

    public void clickClear() {
        scrollAndClick(clearBtn);
    }

    public void clickGoBack() {
        scrollAndClick(goBackBtn);
    }

    public void clickNewPatientRegistration() {
        scrollAndClick(newPatientRegBtn);
        webdriverWait(20, patientRegHeader);
    }

    public String getSearchFieldValue(String field) {
        switch (field) {
            case "pid": return getDomProperty(searchPatientId);
            case "pfnm": return getDomProperty(searchFirstName);
            case "plnm": return getDomProperty(searchLastName);
            case "username": return getDomProperty(searchUsername);
            default: throw new IllegalArgumentException("Unknown search field: " + field);
        }
    }

    public String getToastMessageText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TOAST_MESSAGE)));
        return toast.getText().trim();
    }

    public String getNoRecordsMessageText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement noRecords = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(NO_RECORDS_MSG)));
        return noRecords.getText().trim();
    }

    // ---------- Select Patient Actions (/patient/search) ----------
    public boolean areSelectPatientElementsDisplayed() {
        webdriverWait(15, selectPatientHeader);
        return displayedMethod(selectPatientHeader)
            && displayedMethod(searchPatientId)
            && displayedMethod(searchNid)
            && displayedMethod(selectPatientFirstName)
            && displayedMethod(selectPatientLastName)
            && displayedMethod(selectPatientUsername)
            && displayedMethod(selectPatientMedNo)
            && displayedMethod(selectPatientDob)
            && displayedMethod(selectPatientOrderBy)
            && displayedMethod(searchSportsTypeSelect)
            && displayedMethod(selectPatientClubSelect)
            && displayedMethod(clearBtn)
            && displayedMethod(searchBtn)
            && displayedMethod(newPatientBtn)
            && displayedMethod(cancelBtn);
    }

    public boolean areSelectPatientDefaultsValid() {
        return elementGetText(selectPatientOrderBy).contains("Last Name")
            && selectedMethod(selectByRegularRadio)
            && !selectedMethod(selectByStatementRadio)
            && isSelectPatientClubDisabled();
    }

    public boolean isSelectPatientClubDisabled() {
        String cls = webdriverWait(10, selectPatientClubSelect).getAttribute("class");
        return cls != null && cls.contains("ant-select-disabled");
    }

    public void enterSelectPatientFirstName(String firstName) {
        clearAndType(selectPatientFirstName, firstName);
    }

    public void enterSelectPatientLastName(String lastName) {
        clearAndType(selectPatientLastName, lastName);
    }

    public void enterSelectPatientUsername(String username) {
        clearAndType(selectPatientUsername, username);
    }

    public void enterSelectPatientMedNo(String medNo) {
        clearAndType(selectPatientMedNo, medNo);
    }

    public String getSelectPatientFieldValue(String field) {
        switch (field) {
            case "pid": return getDomProperty(searchPatientId);
            case "firstName": return getDomProperty(selectPatientFirstName);
            case "lastName": return getDomProperty(selectPatientLastName);
            case "usrname": return getDomProperty(selectPatientUsername);
            case "medno": return getDomProperty(selectPatientMedNo);
            default: throw new IllegalArgumentException("Unknown select patient field: " + field);
        }
    }

    public void selectOrderBy(String orderByText) {
        selectAntOption(selectPatientOrderBy, orderByText);
    }

    public String getSelectedOrderBy() {
        return elementGetText(selectPatientOrderBy).trim();
    }

    public void selectPatientClubTeamName(String clubName) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> !isSelectPatientClubDisabled());
        selectAntOption(selectPatientClubSelect, clubName);
    }

    public String getSelectedPatientClubTeamName() {
        return elementGetText(selectPatientClubSelect).trim();
    }

    public void selectSelectByOption(String optionValue) {
        WebElement radio = "Statement".equalsIgnoreCase(optionValue) ? selectByStatementRadio : selectByRegularRadio;
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", radio);
        jsClick(radio);
    }

    public boolean isSelectBySelected(String optionValue) {
        WebElement radio = "Statement".equalsIgnoreCase(optionValue) ? selectByStatementRadio : selectByRegularRadio;
        return selectedMethod(radio);
    }

    public String getSelectPatientNoResultsText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SELECT_PATIENT_RESULTS_SECTION)));
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SELECT_PATIENT_NO_RESULTS)));
        return msg.getText().trim();
    }

    public void clickNewPatientButton() {
        scrollAndClick(newPatientBtn);
    }

    // ---------- New Patient Registration Actions (/patient/registration) ----------
    public boolean areNewPatientRegistrationElementsDisplayed() {
        webdriverWait(15, patientRegHeader);
        return displayedMethod(patientRegHeader)
            && displayedMethod(prefixSelect)
            && displayedMethod(regFirstName)
            && displayedMethod(regMiddleName)
            && displayedMethod(regLastName)
            && displayedMethod(suffixSelect)
            && displayedMethod(regFatherName)
            && displayedMethod(regMotherName)
            && displayedMethod(regDob)
            && displayedMethod(regAge)
            && displayedMethod(sexSelect)
            && displayedMethod(regNid)
            && displayedMethod(regCellPhone)
            && displayedMethod(regUsername)
            && displayedMethod(regPassword)
            && displayedMethod(regEmail)
            && displayedMethod(regLocationSelect)
            && displayedMethod(regPhysicianSelect)
            && displayedMethod(submitBtn)
            && displayedMethod(clearBtn)
            && displayedMethod(cancelBtn)
            && displayedMethod(backBtn);
    }

    public boolean areDefaultSelectionsValid() {
        return elementGetText(prefixSelect).contains("Mr")
            && elementGetText(sexSelect).contains("Male")
            && selectedMethod(whatsAppYesRadio)
            && selectedMethod(insuredSelfRadio)
            && selectedMethod(vipNoRadio);
    }

    public void enterRegFirstName(String firstName) {
        clearAndType(regFirstName, firstName);
    }

    public void enterRegLastName(String lastName) {
        clearAndType(regLastName, lastName);
    }

    public void enterRegDob(String dobDdMmYyyy) {
        webdriverWait(15, regDob);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", regDob);
        regDob.click();
        regDob.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        regDob.sendKeys(dobDdMmYyyy);
        regDob.sendKeys(Keys.ENTER);
    }

    public boolean isAgeFieldDisabled() {
        return !enabledMethod(regAge);
    }

    public String getCalculatedAge() {
        return getDomProperty(regAge);
    }

    public void waitForCountryFormatReady() {
        new WebDriverWait(driver, Duration.ofSeconds(25))
            .until(d -> {
                String ph = regNid.getAttribute("placeholder");
                return ph != null && ph.contains("-");
            });
    }

    public void enterRegNid(String nid) {
        clearAndType(regNid, nid);
    }

    public void enterRegCellPhone(String cellPhone) {
        clearAndType(regCellPhone, cellPhone);
    }

    public void enterRegUsername(String username) {
        clearAndType(regUsername, username);
    }

    public void enterRegPassword(String password) {
        clearAndType(regPassword, password);
    }

    public void enterRegEmail(String email) {
        clearAndType(regEmail, email);
    }

    public String getRegFieldValue(String field) {
        switch (field) {
            case "firstName": return getDomProperty(regFirstName);
            case "lastName": return getDomProperty(regLastName);
            case "username": return getDomProperty(regUsername);
            case "email": return getDomProperty(regEmail);
            default: throw new IllegalArgumentException("Unknown registration field: " + field);
        }
    }

    public void waitForLocationDropdownEnabled() {
        new WebDriverWait(driver, Duration.ofSeconds(35))
            .until(d -> !regLocationSelect.getAttribute("class").contains("ant-select-disabled"));
    }

    public boolean isPhysicianDropdownDisabled() {
        String cls = webdriverWait(10, regPhysicianSelect).getAttribute("class");
        return cls != null && cls.contains("ant-select-disabled");
    }

    public String getPracticeFieldValue() {
        waitForLocationDropdownEnabled();
        return getDomProperty(regPracticeReadonly);
    }

    public boolean isPracticeFieldReadOnly() {
        return "true".equalsIgnoreCase(getDomProperty(regPracticeReadonly, "readOnly"));
    }

    public void selectFirstAvailableLocation() {
        waitForLocationDropdownEnabled();
        openAntSelect(regLocationSelect);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement firstLoc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(FIRST_VISIBLE_DROPDOWN_OPTION)));
        try {
            firstLoc.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(firstLoc);
        }
    }

    public void selectFirstAvailablePhysician() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> !isPhysicianDropdownDisabled());
        openAntSelect(regPhysicianSelect);
        WebElement firstPhys = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(FIRST_VISIBLE_DROPDOWN_OPTION)));
        try {
            firstPhys.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(firstPhys);
        }
    }

    public void selectFirstAvailableLocationAndPhysician() {
        selectFirstAvailableLocation();
        selectFirstAvailablePhysician();
    }

    public void clickRegPasswordVisibilityIcon() {
        scrollAndClick(regPasswordEyeIcon);
    }

    public String getRegPasswordInputType() {
        return getDomProperty(regPassword, "type");
    }

    public void selectPrefix(String prefix) {
        selectAntOption(prefixSelect, prefix);
    }

    public void selectSuffix(String suffix) {
        selectAntOption(suffixSelect, suffix);
    }

    public void selectSex(String sex) {
        selectAntOption(sexSelect, sex);
    }

    public String getSelectedPrefix() {
        return elementGetText(prefixSelect).trim();
    }

    public String getSelectedSuffix() {
        return elementGetText(suffixSelect).trim();
    }

    public String getSelectedSex() {
        return elementGetText(sexSelect).trim();
    }

    public String getRegNidValue() {
        return getDomProperty(regNid);
    }

    public String getRegCellPhoneValue() {
        return getDomProperty(regCellPhone);
    }

    public void selectWhatsAppOption(String option) {
        WebElement radio = "No".equalsIgnoreCase(option) ? whatsAppNoRadio : whatsAppYesRadio;
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", radio);
        jsClick(radio);
    }

    public boolean isWhatsAppOptionSelected(String option) {
        return selectedMethod("No".equalsIgnoreCase(option) ? whatsAppNoRadio : whatsAppYesRadio);
    }

    public void selectInsuredOption(String option) {
        WebElement radio = "Insured".equalsIgnoreCase(option) ? insuredYesRadio : insuredSelfRadio;
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", radio);
        jsClick(radio);
    }

    public boolean isInsuredOptionSelected(String option) {
        return selectedMethod("Insured".equalsIgnoreCase(option) ? insuredYesRadio : insuredSelfRadio);
    }

    public void selectVipOption(String option) {
        WebElement radio = "Yes".equalsIgnoreCase(option) ? vipYesRadio : vipNoRadio;
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", radio);
        jsClick(radio);
    }

    public boolean isVipOptionSelected(String option) {
        return selectedMethod("Yes".equalsIgnoreCase(option) ? vipYesRadio : vipNoRadio);
    }

    public void clickBack() {
        scrollAndClick(backBtn);
    }

    public void enterRegMiddleName(String val) { clearAndType(regMiddleName, val); }
    public void enterRegFatherName(String val) { clearAndType(regFatherName, val); }
    public void enterRegMotherName(String val) { clearAndType(regMotherName, val); }
    public void enterRegMedNo(String val) { clearAndType(regMedNo, val); }
    public void enterRegAddress1(String val) { clearAndType(regAddress1, val); }
    public void enterRegAddress2(String val) { clearAndType(regAddress2, val); }
    public void enterRegCity(String val) { clearAndType(regCity, val); }
    public void selectRegGovernorate(String val) {
        waitForLocationDropdownEnabled();
        openAntSelect(regStateSelect);
        WebElement searchInput = driver.findElement(By.xpath("//input[@id='state']"));
        searchInput.sendKeys(val);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(FIRST_VISIBLE_DROPDOWN_OPTION)));
        try {
            option.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(option);
        }
    }
    public void enterRegZip(String val) { clearAndType(regZip, val); }
    public void enterRegExt(String val) { clearAndType(regExt, val); }

    public String getOptionalDemographicValue(String field) {
        switch (field) {
            case "middleName": return getDomProperty(regMiddleName);
            case "fatherName": return getDomProperty(regFatherName);
            case "motherName": return getDomProperty(regMotherName);
            case "medno": return getDomProperty(regMedNo);
            case "address1": return getDomProperty(regAddress1);
            case "address2": return getDomProperty(regAddress2);
            case "city": return getDomProperty(regCity);
            case "governorate": return elementGetText(regStateSelect).trim();
            case "zip": return getDomProperty(regZip);
            case "ext": return getDomProperty(regExt);
            default: throw new IllegalArgumentException("Unknown optional field: " + field);
        }
    }

    public void clickSubmit() {
        scrollAndClick(submitBtn);
    }

    public void clickCancel() {
        scrollAndClick(cancelBtn);
    }

    public List<String> getFormValidationMessages() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FORM_ERRORS)));
        List<WebElement> elements = driver.findElements(By.xpath(FORM_ERRORS));
        List<String> messages = new ArrayList<>();
        for (WebElement el : elements) {
            if (el.isDisplayed()) {
                String text = el.getText().trim();
                if (!text.isEmpty()) {
                    messages.add(text);
                }
            }
        }
        return messages;
    }

    public String getRegistrationSuccessText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUCCESS_ALERT)));
        return alert.getText().trim();
    }

    public void enterRegEvePhone(String val) {
        waitForCountryFormatReady();
        clearAndType(regEvePhone, val);
    }

    public void enterRegPostalCode(String val) {
        waitForCountryFormatReady();
        clearAndType(regZip, val);
    }

    public void clickPatientSearchBreadcrumb() {
        scrollAndClick(patientSearchBreadcrumb);
    }

    public void openRegDobPicker() {
        scrollAndClick(regDob);
    }

    public int getDisabledFutureDateCellCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DOB_DISABLED_FUTURE_CELLS)));
        return driver.findElements(By.xpath(DOB_DISABLED_FUTURE_CELLS)).size();
    }

    public String getRegPasswordValue() {
        return getDomProperty(regPassword);
    }

    public void selectRegCountry(String countryName) {
        waitForLocationDropdownEnabled();
        openAntSelect(regCountrySelect);
        WebElement searchInput = driver.findElement(By.xpath("//input[@id='country']"));
        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        searchInput.sendKeys(countryName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath(String.format(VISIBLE_DROPDOWN_OPTION, countryName))
        ));
        try {
            option.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(option);
        }
        wait.until(ExpectedConditions.textToBePresentInElement(regCountrySelect, countryName));
    }

    public String getSelectedRegCountry() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@id='country']/ancestor::div[contains(@class,'ant-select')]//span[contains(@class,'ant-select-selection-item')]")
        ));
        return item.getText().trim();
    }

    public String getSelectedRegGovernorate() {
        return elementGetText(regStateSelect).trim();
    }

    public void clickAdmitToBedButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement admitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ADMIT_TO_BED_BTN)));
        scrollAndClick(admitBtn);
    }

    // ---------- Search Results & Legacy Parity Helpers ----------
    public String getFirstSearchResultRowText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" + TABLE_DATA_ROWS + ")[1]")));
        return row.getText().replaceAll("\\s+", " ").trim();
    }

    public boolean isAddToPracticeButtonDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_TO_PRACTICE_BTN)));
        return displayedMethod(btn);
    }

    public boolean areSelectPatientRowActionsDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement firstRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" + TABLE_DATA_ROWS + ")[1]")));
        String rowText = firstRow.getText();
        return rowText.contains("Appt.")
            && rowText.contains("Demo.")
            && rowText.contains("Sports Details")
            && rowText.contains("Copay")
            && rowText.contains("S.Bill")
            && rowText.contains("Trans.")
            && rowText.contains("Print")
            && rowText.contains("View")
            && rowText.contains("Change");
    }

    public String getSelectPatientFirstRowDob() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement dobCell = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(" + TABLE_DATA_ROWS + ")[1]/td[3]")
        ));
        return dobCell.getText().trim();
    }

    public int getVisibleInsuranceFieldCount() {
        String xpath = "//label[contains(normalize-space(),'Primary Insurance') or contains(normalize-space(),'Group No') or contains(normalize-space(),'Policy No') or contains(normalize-space(),'Policy Type') or contains(normalize-space(),'Eff From Date') or contains(normalize-space(),'Eff End Date') or contains(normalize-space(),'Relation')]";
        List<WebElement> labels = driver.findElements(By.xpath(xpath));
        int count = 0;
        for (WebElement el : labels) {
            if (el.isDisplayed()) {
                count++;
            }
        }
        return count;
    }

    public boolean isSeparateWhatsAppNumberInputDisplayed() {
        String xpath = "//input[@id='whatsappNo' or @name='whatsappNo' or contains(translate(@id,'WHATSAPP','whatsapp'),'whatsapp') or contains(translate(@placeholder,'WHATSAPP','whatsapp'),'whatsapp')]";
        List<WebElement> inputs = driver.findElements(By.xpath(xpath));
        for (WebElement el : inputs) {
            String type = el.getAttribute("type");
            if (el.isDisplayed() && !"radio".equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    public String getHeaderPracticeBannerText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement banner = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(HEADER_PRACTICE_BANNER)));
        return banner.getText().trim();
    }
}
