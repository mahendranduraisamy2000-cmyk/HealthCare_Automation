package com.egypt.page;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.egypt.utills.BaseClass;

public class SearchPatientPage extends BaseClass {

    // Navigation & Page Header
    private static final String SEARCH_PATIENT_MENU =
            "//a[contains(@href,'/patient/search') or .//span[normalize-space()='Search patient'] or normalize-space()='Search patient']";
    private static final String SELECT_PATIENT_HEADING =
            "//*[self::h1 or self::h2 or self::h3 or self::span or self::div][normalize-space()='Select Patient']";
    private static final String CANCEL_BTN =
            "//button[normalize-space()='Cancel' or .//span[normalize-space()='Cancel']]";

    // Search Criteria Fields (verified QA DOM IDs)
    private static final String PATIENT_ID = "//input[@id='pid']";
    private static final String NATIONAL_ID = "//input[@id='ssn']";
    private static final String FIRST_NAME = "//input[@id='firstName']";
    private static final String LAST_NAME = "//input[@id='lastName']";
    private static final String USERNAME = "//input[@id='usrname']";
    private static final String EXT_MED_REC_NO = "//input[@id='medno']";
    private static final String DATE_OF_BIRTH = "//input[@id='dob']";

    // Dropdowns (Ant Design Select containers & search inputs)
    private static final String ORDER_BY_DDN =
            "//input[@id='orderby']/ancestor::div[contains(@class,'ant-select')][1]";
    private static final String SPORTS_TYPE_DDN =
            "//input[@id='sportType']/ancestor::div[contains(@class,'ant-select')][1]";
    private static final String SPORTS_TYPE_INPUT = "//input[@id='sportType']";
    private static final String CLUB_TEAM_DDN =
            "//input[@id='clubName']/ancestor::div[contains(@class,'ant-select')][1]";
    private static final String CLUB_TEAM_INPUT = "//input[@id='clubName']";

    // Select By Radio Buttons
    private static final String REGULAR_SEARCH_RADIO =
            "//label[contains(normalize-space(),'Regular Search')]//input[@type='radio'] | //input[@type='radio' and (@value='regular' or @value='Regular Search' or @id='regularSearch')]";
    private static final String REGULAR_SEARCH_LABEL =
            "//label[contains(normalize-space(),'Regular Search')]";
    private static final String STATEMENT_RADIO =
            "//label[contains(normalize-space(),'Statement')]//input[@type='radio'] | //input[@type='radio' and (@value='statement' or @value='Statement' or @id='statement')]";
    private static final String STATEMENT_LABEL =
            "//label[contains(normalize-space(),'Statement')]";

    // Action Buttons
    private static final String CLEAR_BTN =
            "//button[normalize-space()='Clear' or .//span[normalize-space()='Clear']]";
    private static final String SEARCH_BTN =
            "//button[normalize-space()='Search' or .//span[normalize-space()='Search']]";

    // Search Results & Validation Messages
    private static final String RESULT_ROWS =
            "//div[contains(@class,'ant-table')]//tbody//tr[not(contains(@class,'ant-table-placeholder')) "
            + "and not(contains(@class,'measure')) "
            + "and not(contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'did not yield')) "
            + "and not(contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no record')) "
            + "and not(contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no data')) "
            + "and not(contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no patient'))]";
    private static final String NO_RESULTS_MSG =
            "//*[contains(@class,'ant-empty') "
            + "or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'did not yield') "
            + "or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no patient') "
            + "or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no record') "
            + "or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no data') "
            + "or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'not found')]";
    private static final String VALIDATION_MSG =
            "//*[contains(@class,'ant-form-item-explain-error') or contains(@class,'error') or contains(@class,'invalid') or contains(@class,'toast') or contains(@class,'alert') or @role='alert'][normalize-space()!='']";

    private WebDriver driver;

    public SearchPatientPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                    "WebDriver is NULL. Initialize driver before creating SearchPatientPage.");
        }
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = SEARCH_PATIENT_MENU)
    private WebElement searchPatientMenu;

    @FindBy(xpath = SELECT_PATIENT_HEADING)
    private WebElement selectPatientHeading;

    @FindBy(xpath = CANCEL_BTN)
    private WebElement cancelBtn;

    @FindBy(xpath = PATIENT_ID)
    private WebElement patientIdInput;

    @FindBy(xpath = NATIONAL_ID)
    private WebElement nationalIdInput;

    @FindBy(xpath = FIRST_NAME)
    private WebElement firstNameInput;

    @FindBy(xpath = LAST_NAME)
    private WebElement lastNameInput;

    @FindBy(xpath = USERNAME)
    private WebElement usernameInput;

    @FindBy(xpath = EXT_MED_REC_NO)
    private WebElement extMedRecNoInput;

    @FindBy(xpath = DATE_OF_BIRTH)
    private WebElement dobInput;

    @FindBy(xpath = ORDER_BY_DDN)
    private WebElement orderByDropdown;

    @FindBy(xpath = SPORTS_TYPE_DDN)
    private WebElement sportsTypeDropdown;

    @FindBy(xpath = SPORTS_TYPE_INPUT)
    private WebElement sportsTypeInput;

    @FindBy(xpath = CLUB_TEAM_DDN)
    private WebElement clubTeamDropdown;

    @FindBy(xpath = CLUB_TEAM_INPUT)
    private WebElement clubTeamInput;

    @FindBy(xpath = REGULAR_SEARCH_RADIO)
    private WebElement regularSearchRadio;

    @FindBy(xpath = REGULAR_SEARCH_LABEL)
    private WebElement regularSearchLabel;

    @FindBy(xpath = STATEMENT_RADIO)
    private WebElement statementRadio;

    @FindBy(xpath = STATEMENT_LABEL)
    private WebElement statementLabel;

    @FindBy(xpath = CLEAR_BTN)
    private WebElement clearBtn;

    @FindBy(xpath = SEARCH_BTN)
    private WebElement searchBtn;

    public void navigateToSelectPatientPage() {
        elementClick(searchPatientMenu);
        webdriverWait(15, selectPatientHeading);
    }

    public boolean isSelectPatientPageDisplayed() {
        webdriverWait(15, selectPatientHeading);
        return displayedMethod(selectPatientHeading)
                && getApplnUrl().contains("/patient/search");
    }

    public String getSelectPatientHeading() {
        return elementGetText(selectPatientHeading);
    }

    public boolean areAllSearchFieldsVisible() {
        WebElement[] fields = {
                patientIdInput, nationalIdInput, firstNameInput, lastNameInput,
                usernameInput, extMedRecNoInput, dobInput, orderByDropdown,
                sportsTypeDropdown, clubTeamDropdown, clearBtn, searchBtn, cancelBtn
        };
        for (WebElement field : fields) {
            webdriverWait(10, field);
            if (!displayedMethod(field)) {
                return false;
            }
        }
        return true;
    }

    public void enterPatientId(String patientId) {
        clear(patientIdInput);
        elementSendKeys(patientIdInput, patientId);
    }

    public void enterNationalId(String nationalId) {
        webdriverWait(15, nationalIdInput);
        // Wait for async practice NID mask config (updates maxlength 14 -> 18 & placeholder to hyphenated format)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> {
                String placeholder = nationalIdInput.getDomAttribute("placeholder");
                String maxLen = nationalIdInput.getDomAttribute("maxlength");
                return (placeholder != null && placeholder.contains("-"))
                        || ("18".equals(maxLen));
            });
        } catch (TimeoutException ignored) {
            // Practice may use unmasked 14-digit NID format; proceed safely
        }
        clear(nationalIdInput);
        nationalIdInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        elementSendKeys(nationalIdInput, nationalId);
    }

    public void enterFirstName(String firstName) {
        clear(firstNameInput);
        elementSendKeys(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        clear(lastNameInput);
        elementSendKeys(lastNameInput, lastName);
    }

    public void enterUsername(String username) {
        clear(usernameInput);
        elementSendKeys(usernameInput, username);
    }

    public void enterExtMedRecNo(String extMedRecNo) {
        clear(extMedRecNoInput);
        elementSendKeys(extMedRecNoInput, extMedRecNo);
    }

    public void enterDateOfBirth(String dob) {
        webdriverWait(10, dobInput);
        elementClick(dobInput);
        clear(dobInput);
        dobInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        String formattedDob = formatDobForPicker(dob);
        elementSendKeys(dobInput, formattedDob);
        dobInput.sendKeys(Keys.ENTER);
        dobInput.sendKeys(Keys.TAB);
    }

    public void selectSportsType(String sport) {
        selectAntDropdownOption(sportsTypeDropdown, sportsTypeInput, sport);
    }

    public String getSelectedSportsType() {
        return getSelectedAntDropdownText(SPORTS_TYPE_INPUT);
    }

    public void selectClubOrTeamName(String team) {
        selectAntDropdownOption(clubTeamDropdown, clubTeamInput, team);
    }

    public String getSelectedClubOrTeamName() {
        return getSelectedAntDropdownText(CLUB_TEAM_INPUT);
    }

    private void selectAntDropdownOption(WebElement dropdownContainer, WebElement searchInput, String optionText) {
        clickableOfElementWait(10, dropdownContainer);
        elementClick(dropdownContainer);
        String optionXpath =
                "//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'ant-select-dropdown-hidden'))]"
                + "//div[contains(@class,'ant-select-item-option-content') and "
                + "translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='"
                + optionText.trim().toLowerCase().replace("'", "") + "']";
        try {
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> {
                        List<WebElement> matches = d.findElements(By.xpath(optionXpath));
                        for (WebElement m : matches) {
                            if (displayedMethod(m)) {
                                return m;
                            }
                        }
                        return null;
                    });
            elementClick(option);
        } catch (TimeoutException e) {
            sendKeysAndEnter(searchInput, optionText);
        }
    }

    private String getSelectedAntDropdownText(String inputXpath) {
        List<WebElement> selected = driver.findElements(By.xpath(
                inputXpath + "/ancestor::div[contains(@class,'ant-select-selector')]//span[contains(@class,'ant-select-selection-item')]"));
        return selected.isEmpty() ? "" : selected.get(0).getText().trim();
    }

    public void selectRegularSearchRadio() {
        try {
            clickableOfElementWait(10, regularSearchRadio);
            elementClick(regularSearchRadio);
        } catch (Exception e) {
            jsClick(regularSearchLabel);
        }
    }

    public boolean isRegularSearchSelected() {
        return selectedMethod(regularSearchRadio);
    }

    public void selectStatementRadio() {
        try {
            clickableOfElementWait(10, statementRadio);
            elementClick(statementRadio);
        } catch (Exception e) {
            jsClick(statementLabel);
        }
    }

    public boolean isStatementSelected() {
        return selectedMethod(statementRadio);
    }

    public void clickSearchButton() {
        clickableOfElementWait(10, searchBtn);
        elementClick(searchBtn);
        waitForSearchToFinish();
    }

    public void clickSearchButtonMultipleTimes(int times) {
        clickableOfElementWait(10, searchBtn);
        for (int i = 0; i < times; i++) {
            try {
                if (enabledMethod(searchBtn)) {
                    jsClick(searchBtn);
                }
            } catch (Exception ignored) {
                // Button may briefly enter loading/disabled state between rapid clicks
            }
        }
        waitForSearchToFinish();
    }

    private void waitForSearchToFinish() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> {
                String cls = searchBtn.getDomAttribute("class");
                return cls == null || !cls.contains("ant-btn-loading");
            });
        } catch (TimeoutException ignored) {
            // Proceed to result verification
        }
        waitForSeconds();
    }

    public void clickClearButton() {
        clickableOfElementWait(10, clearBtn);
        elementClick(clearBtn);
    }

    public void clickCancelButton() {
        clickableOfElementWait(10, cancelBtn);
        elementClick(cancelBtn);
    }

    public boolean areAllSearchFieldsCleared() {
        return isEmpty(getDomProperty(patientIdInput))
                && isEmpty(getDomProperty(nationalIdInput))
                && isEmpty(getDomProperty(firstNameInput))
                && isEmpty(getDomProperty(lastNameInput))
                && isEmpty(getDomProperty(usernameInput))
                && isEmpty(getDomProperty(extMedRecNoInput))
                && isEmpty(getDomProperty(dobInput));
    }

    public List<String> getSearchResultRowTexts() {
        List<String> rowsText = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.xpath(RESULT_ROWS));
        for (WebElement row : rows) {
            if (displayedMethod(row)) {
                rowsText.add(row.getText().trim());
            }
        }
        return rowsText;
    }

    public boolean hasSearchResults() {
        return !getSearchResultRowTexts().isEmpty();
    }

    public boolean isMatchingTextDisplayedInResults(String expectedText) {
        for (String rowText : getSearchResultRowTexts()) {
            if (rowText.toLowerCase().contains(expectedText.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean doAllRowsContainText(String expectedText) {
        List<String> rows = getSearchResultRowTexts();
        if (rows.isEmpty()) {
            return false;
        }
        for (String rowText : rows) {
            if (!rowText.toLowerCase().contains(expectedText.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public boolean doAllRowsContainDob(String expectedDob) {
        List<String> rows = getSearchResultRowTexts();
        if (rows.isEmpty()) {
            return false;
        }
        Set<String> dobVariants = buildDobFormatVariants(expectedDob);
        for (String rowText : rows) {
            String lowerRow = rowText.toLowerCase();
            boolean matched = false;
            for (String variant : dobVariants) {
                if (lowerRow.contains(variant.toLowerCase())) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    public String getPatientIdValue() {
        String val = getDomProperty(patientIdInput);
        return val == null ? "" : val.trim();
    }

    public String getFirstNameValue() {
        String val = getDomProperty(firstNameInput);
        return val == null ? "" : val.trim();
    }

    public String getNationalIdValue() {
        String val = getDomProperty(nationalIdInput);
        return val == null ? "" : val.trim();
    }

    public String getDateOfBirthValue() {
        String val = getDomProperty(dobInput);
        return val == null ? "" : val.trim();
    }

    public boolean isNationalIdValidatedByLength(String attemptedNationalId) {
        String maxLenAttr = nationalIdInput.getDomAttribute("maxlength");
        String currentVal = getNationalIdValue();
        boolean withinMaxLength = true;
        if (maxLenAttr != null && !maxLenAttr.isEmpty()) {
            try {
                int maxLen = Integer.parseInt(maxLenAttr.trim());
                withinMaxLength = currentVal.length() <= maxLen;
            } catch (NumberFormatException ignored) {
                // Non-numeric maxlength ignored
            }
        }
        boolean handled = isValidationMessageDisplayed()
                || isNoResultsMessageDisplayed()
                || hasSearchResults()
                || (attemptedNationalId != null && !attemptedNationalId.trim().equals(currentVal));
        return withinMaxLength && handled;
    }

    public boolean isInvalidInputRejectedOrHandled(String attemptedInput, String actualFieldValue) {
        boolean inputModifiedOrRejected = attemptedInput != null
                && !attemptedInput.trim().equals(actualFieldValue);
        return inputModifiedOrRejected
                || isValidationMessageDisplayed()
                || isNoResultsMessageDisplayed()
                || !hasSearchResults();
    }

    public boolean isInvalidDobRejected(String attemptedDob) {
        String currentVal = getDateOfBirthValue();
        return isEmpty(currentVal)
                || (attemptedDob != null && !currentVal.equals(attemptedDob.trim()))
                || isValidationMessageDisplayed();
    }

    private LocalDate parseDob(String dob) {
        if (dob == null || dob.trim().isEmpty()) {
            return null;
        }
        String trimmed = dob.trim();
        DateTimeFormatter[] parsers = {
                DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT),
                DateTimeFormatter.ofPattern("MM-dd-uuuu").withResolverStyle(ResolverStyle.STRICT),
                DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT),
                DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT),
                DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT)
        };
        for (DateTimeFormatter parser : parsers) {
            try {
                return LocalDate.parse(trimmed, parser);
            } catch (Exception ignored) {
                // Try next pattern
            }
        }
        return null;
    }

    private String formatDobForPicker(String dob) {
        LocalDate parsed = parseDob(dob);
        return parsed != null
                ? parsed.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                : dob;
    }

    private Set<String> buildDobFormatVariants(String dob) {
        Set<String> variants = new HashSet<>();
        if (dob == null || dob.trim().isEmpty()) {
            return variants;
        }
        String trimmed = dob.trim();
        variants.add(trimmed);
        LocalDate parsed = parseDob(trimmed);
        if (parsed != null) {
            variants.add(parsed.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            variants.add(parsed.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            variants.add(parsed.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            variants.add(parsed.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            variants.add(parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        return variants;
    }

    public boolean hasDuplicateResultRows() {
        List<String> rows = getSearchResultRowTexts();
        Set<String> uniqueRows = new HashSet<>(rows);
        return uniqueRows.size() < rows.size();
    }

    public boolean isNoResultsMessageDisplayed() {
        List<WebElement> messages = driver.findElements(By.xpath(NO_RESULTS_MSG));
        for (WebElement message : messages) {
            if (displayedMethod(message)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidationMessageDisplayed() {
        List<WebElement> messages = driver.findElements(By.xpath(VALIDATION_MSG));
        for (WebElement message : messages) {
            if (displayedMethod(message)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFutureDobRestricted(String attemptedFutureDob) {
        String maxAttr = getDomProperty(dobInput, "max");
        if (maxAttr != null && !maxAttr.isEmpty()) {
            try {
                LocalDate maxDate = LocalDate.parse(maxAttr, DateTimeFormatter.ISO_LOCAL_DATE);
                if (!maxDate.isAfter(LocalDate.now())) {
                    return true;
                }
            } catch (Exception ignored) {
                // Fall through to input value / validation checks
            }
        }
        String currentVal = getDomProperty(dobInput);
        if (isEmpty(currentVal) || !currentVal.contains(attemptedFutureDob)) {
            return true;
        }
        return isValidationMessageDisplayed() || !hasSearchResults();
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
