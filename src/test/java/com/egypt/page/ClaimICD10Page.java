package com.egypt.page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.egypt.utills.BaseClass;
import com.egypt.utills.ConfigReader;

/**
 * Page Object for Billing Details -> Claim ICD10 (/claims/create-10).
 * Locators are verified against the QA Ant Design DOM.
 */
public class ClaimICD10Page extends BaseClass {

	// ---------- Sidebar Navigation ----------
	private static final String BILLING_DETAILS_MENU = "//span[contains(@class,'sidebar-label') and normalize-space()='Billing Details']";
	private static final String CLAIM_ICD10_MENU = "//a[contains(@class,'submenu-item') and contains(@href,'/claims/create-10') and normalize-space()='Claim ICD10']";

	// ---------- Claim Details (ICD10) Header & Top Form ----------
	private static final String PAGE_HEADER = "//h4[contains(normalize-space(),'Claim Details (ICD10)')]";
	private static final String PATIENT_ID_INPUT = "//input[@id='patientId']";
	private static final String SEARCH_PATIENT_BTN = "//button[contains(normalize-space(),'Search Patient')]";
	private static final String PATIENT_NAME_INPUT = "//input[@id='patientName']";
	private static final String FILE_REF_NO_INPUT = "//input[@id='fileRefNo']";

	// ---------- Patient Search Page (/patient/search?source=newclaim10) ----------
	private static final String PATIENT_SEARCH_FIRST_NAME = "//input[@id='firstName']";
	private static final String PATIENT_SEARCH_PID = "//input[@id='pid']";
	private static final String PATIENT_SEARCH_SUBMIT_BTN = "//button[@type='submit' and normalize-space()='Search']";
	private static final String PATIENT_RESULT_ROWS = "//table//tbody/tr[not(contains(@class,'ant-table-placeholder')) and not(contains(@class,'ant-table-measure-row'))]";

	// ---------- Diagnosis Code (ICD-10) ----------
	private static final String DIAGNOSIS_INPUT_TEMPLATE = "(//input[contains(@class,'nc10-diag-input')])[%d]";
	private static final String DIAGNOSIS_ICD_BTN_TEMPLATE = "(//div[contains(@class,'nc10-diag-input-group')]//button[normalize-space()='ICD'])[%d]";
	private static final String ACTIVE_MODAL = "(//div[contains(@class,'ant-modal-content')])[last()]";
	private static final String ICD_MODAL_TITLE = "//div[contains(@class,'ant-modal-title') and normalize-space()='Search ICD Code']";
	private static final String CPT_MODAL_TITLE = "//div[contains(@class,'ant-modal-title') and normalize-space()='Search CPT Code']";
	private static final String MODAL_CLOSE_BTN = "(//div[contains(@class,'ant-modal-content')])[last()]//button[contains(@class,'ant-modal-close')]";

	// ---------- Service Details (Row 1) ----------
	private static final String ROW1_FROM_DATE = "(//tr[@data-row-key='1']//div[contains(@class,'nc10-date-picker')]//input)[1]";
	private static final String ROW1_TO_DATE = "(//tr[@data-row-key='1']//div[contains(@class,'nc10-date-picker')]//input)[2]";
	private static final String ROW1_POS_INPUT = "//tr[@data-row-key='1']//input[contains(@class,'nc10-pos-input')]";
	private static final String ROW1_CPT_INPUT = "//tr[@data-row-key='1']//input[contains(@class,'nc10-cpt-input')]";
	private static final String ROW1_CPT_INFO_BTN = "//tr[@data-row-key='1']//div[contains(@class,'nc10-cpt-group')]//button";
	private static final String ROW1_CHARGES_INPUT = "(//tr[@data-row-key='1']//input[contains(@class,'ant-input-number-input')])[1]";
	private static final String ROW1_UNIT_INPUT = "(//tr[@data-row-key='1']//input[contains(@class,'ant-input-number-input')])[2]";
	private static final String CLAIM_TOTAL_CHARGES_INPUT = "//*[contains(normalize-space(),'Claim Charges')]/following::input[1]";

	// ---------- Footer Buttons & Feedback ----------
	private static final String SUBMIT_BTN = "//button[@type='button' and normalize-space()='Submit']";
	private static final String CHECK_RULE_BTN = "//button[@type='button' and normalize-space()='Check Rule']";
	private static final String CLEAR_BTN = "//button[@type='button' and normalize-space()='Clear']";
	private static final String NOTIFICATION_SUCCESS = "//div[contains(@class,'ant-message-success') or contains(@class,'ant-notification-notice-success') or contains(@class,'ant-alert-success') or contains(@class,'ant-modal-confirm-success') or contains(@class,'ant-result-success')]";
	private static final String NOTIFICATION_WARNING_OR_ERROR = "//div[contains(@class,'ant-message-error') or contains(@class,'ant-message-warning') or contains(@class,'ant-notification-notice-error') or contains(@class,'ant-notification-notice-warning') or contains(@class,'ant-alert-error') or contains(@class,'ant-alert-warning') or contains(@class,'ant-modal-confirm-warning') or contains(@class,'ant-modal-confirm-error')]";
	private static final String NOTIFICATION_ANY = "//div[contains(@class,'ant-message-notice-content') or contains(@class,'ant-notification-notice-message') or contains(@class,'ant-notification-notice-description') or contains(@class,'ant-modal-confirm-body') or contains(@class,'ant-result-title')] | //div[contains(@class,'ant-modal-wrap') and not(contains(@style,'display: none'))]//div[contains(@class,'ant-modal-content')]";
	private static final String FORM_ERRORS = "//div[contains(@class,'ant-form-item-explain-error')][normalize-space()!='']";
	private static final String MODAL_CONFIRM_OK_BTN = "//div[contains(@class,'ant-modal-confirm')]//button[contains(@class,'ant-btn-primary') or normalize-space()='OK' or normalize-space()='Yes']";
	private static final String DUPLICATE_PROCEED_BTN = "//div[contains(@class,'ant-modal-wrap') and not(contains(@style,'display: none'))]//button[normalize-space()='Proceed']";

	private final WebDriver driver;
	private final WebDriverWait explicitWait;

	public ClaimICD10Page(WebDriver driver) {
		if (driver == null) {
			throw new IllegalArgumentException(
					"WebDriver is NULL. Initialize driver before creating ClaimICD10Page.");
		}
		this.driver = driver;
		this.explicitWait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("wait.explicit")));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = BILLING_DETAILS_MENU)
	private WebElement billingDetailsMenu;

	@FindBy(xpath = CLAIM_ICD10_MENU)
	private WebElement claimIcd10Menu;

	@FindBy(xpath = PAGE_HEADER)
	private WebElement pageHeader;

	@FindBy(xpath = PATIENT_ID_INPUT)
	private WebElement patientIdInput;

	@FindBy(xpath = SEARCH_PATIENT_BTN)
	private WebElement searchPatientBtn;

	@FindBy(xpath = PATIENT_NAME_INPUT)
	private WebElement patientNameInput;

	@FindBy(xpath = FILE_REF_NO_INPUT)
	private WebElement fileRefNoInput;

	@FindBy(xpath = ROW1_FROM_DATE)
	private WebElement row1FromDate;

	@FindBy(xpath = ROW1_TO_DATE)
	private WebElement row1ToDate;

	@FindBy(xpath = ROW1_POS_INPUT)
	private WebElement row1PosInput;

	@FindBy(xpath = ROW1_CPT_INPUT)
	private WebElement row1CptInput;

	@FindBy(xpath = ROW1_CPT_INFO_BTN)
	private WebElement row1CptInfoBtn;

	@FindBy(xpath = ROW1_CHARGES_INPUT)
	private WebElement row1ChargesInput;

	@FindBy(xpath = ROW1_UNIT_INPUT)
	private WebElement row1UnitInput;

	@FindBy(xpath = SUBMIT_BTN)
	private WebElement submitBtn;

	@FindBy(xpath = CHECK_RULE_BTN)
	private WebElement checkRuleBtn;

	@FindBy(xpath = CLEAR_BTN)
	private WebElement clearBtn;

	// ---------- Helpers ----------

	private void scrollToCenter(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView({behavior:'instant', block:'center', inline:'nearest'});", element);
	}

	private void safeClick(WebElement element) {
		explicitWait.until(ExpectedConditions.visibilityOf(element));
		scrollToCenter(element);
		try {
			element.click();
		} catch (Exception e) {
			jsClick(element);
		}
	}

	private void clearAndType(WebElement element, String value) {
		explicitWait.until(ExpectedConditions.visibilityOf(element));
		scrollToCenter(element);
		try {
			element.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].focus(); arguments[0].click();", element);
		}
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
		if (value != null && !value.isEmpty()) {
			element.sendKeys(value);
		}
	}

	private void selectAntDropdown(String inputId, String preferredOptionText) {
		WebElement input = explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.id(inputId)));
		WebElement selector = input.findElement(By.xpath("./ancestor::div[contains(@class,'ant-select-selector')]"));
		safeClick(selector);

		By visibleOptions = By.xpath(
				"//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'ant-select-dropdown-hidden'))]"
						+ "//div[contains(@class,'ant-select-item-option') and not(contains(@class,'ant-select-item-option-disabled'))]");
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(visibleOptions));
		List<WebElement> options = driver.findElements(visibleOptions);

		if (preferredOptionText != null && !preferredOptionText.trim().isEmpty()) {
			for (WebElement opt : options) {
				if (opt.getText().trim().equalsIgnoreCase(preferredOptionText.trim())
						|| opt.getText().trim().toLowerCase().contains(preferredOptionText.trim().toLowerCase())) {
					jsClick(opt);
					return;
				}
			}
		}

		if (!options.isEmpty()) {
			jsClick(options.get(0));
		}
	}

	private String getAntSelectValue(String inputId) {
		List<WebElement> selected = driver.findElements(By.xpath(
				"//input[@id='" + inputId
						+ "']/ancestor::div[contains(@class,'ant-select-selector')]//span[contains(@class,'ant-select-selection-item')]"));
		return selected.isEmpty() ? "" : selected.get(0).getText().trim();
	}

	// ---------- Navigation ----------

	public void clickBillingDetailsMenu() {
		explicitWait.until(ExpectedConditions.elementToBeClickable(billingDetailsMenu));
		scrollToCenter(billingDetailsMenu);
		elementClick(billingDetailsMenu);
	}

	public void clickClaimICD10Menu() {
		if (driver.findElements(By.xpath(CLAIM_ICD10_MENU)).isEmpty()) {
			clickBillingDetailsMenu();
		}
		explicitWait.until(ExpectedConditions.elementToBeClickable(claimIcd10Menu));
		scrollToCenter(claimIcd10Menu);
		elementClick(claimIcd10Menu);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PATIENT_ID_INPUT)));
	}

	public boolean isClaimICD10PageDisplayed() {
		return getApplnUrl().contains("/claims/create-10")
				&& !driver.findElements(By.xpath(PAGE_HEADER)).isEmpty();
	}

	// ---------- Patient Search ----------

	public void searchAndSelectPatient(String searchText) {
		explicitWait.until(ExpectedConditions.elementToBeClickable(searchPatientBtn));
		scrollToCenter(searchPatientBtn);
		elementClick(searchPatientBtn);

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PATIENT_SEARCH_FIRST_NAME)));

		if (searchText != null && !searchText.trim().isEmpty()) {
			String term = searchText.trim();
			if (term.matches("\\d+")) {
				clearAndType(driver.findElement(By.xpath(PATIENT_SEARCH_PID)), term);
			} else {
				clearAndType(driver.findElement(By.xpath(PATIENT_SEARCH_FIRST_NAME)), term);
			}
		}

		WebElement searchBtn = explicitWait.until(
				ExpectedConditions.elementToBeClickable(By.xpath(PATIENT_SEARCH_SUBMIT_BTN)));
		scrollToCenter(searchBtn);
		searchBtn.click();

		explicitWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(PATIENT_RESULT_ROWS), 0));
		WebElement firstRow = driver.findElements(By.xpath(PATIENT_RESULT_ROWS)).get(0);

		List<WebElement> clickables = firstRow.findElements(By.xpath(".//a | .//button"));
		if (!clickables.isEmpty()) {
			scrollToCenter(clickables.get(0));
			jsClick(clickables.get(0));
		} else {
			scrollToCenter(firstRow);
			firstRow.click();
		}

		explicitWait.until(ExpectedConditions.urlContains("/claims/create-10"));
		explicitWait.until(d -> {
			List<WebElement> pid = d.findElements(By.xpath(PATIENT_ID_INPUT));
			if (pid.isEmpty()) {
				return false;
			}
			String val = pid.get(0).getDomProperty("value");
			return val != null && !val.trim().isEmpty();
		});
	}

	public String getSelectedPatientId() {
		return getDomProperty(patientIdInput, "value");
	}

	public String getSelectedPatientName() {
		return getDomProperty(patientNameInput, "value");
	}

	// ---------- Claim Details & Physician/Location ----------

	public void enterFileRefNo(String fileRefNo) {
		clearAndType(fileRefNoInput, fileRefNo);
	}

	public void clearFileRefNo() {
		clearAndType(fileRefNoInput, "");
	}

	public String getFileRefNo() {
		return getDomProperty(fileRefNoInput, "value");
	}

	public void selectMandatoryPhysiciansAndLocation(String rendPhysician, String location, String prodPhysician) {
		if (getAntSelectValue("doid").isEmpty() || (rendPhysician != null && !rendPhysician.isEmpty())) {
			selectAntDropdown("doid", rendPhysician);
		}
		if (getAntSelectValue("locid").isEmpty() || (location != null && !location.isEmpty())) {
			selectAntDropdown("locid", location);
		}
		if (getAntSelectValue("prdoid").isEmpty() || (prodPhysician != null && !prodPhysician.isEmpty())) {
			selectAntDropdown("prdoid", prodPhysician);
		}
	}

	// ---------- Diagnosis Code (ICD-10 Lookup Menu) ----------

	public void chooseICDCodeFromMenu(String icdCodeOrLetter) {
		chooseICDCodeFromMenu(1, icdCodeOrLetter);
	}

	public void chooseICDCodeFromMenu(int slotIndex, String icdCodeOrLetter) {
		By btnBy = By.xpath(String.format(DIAGNOSIS_ICD_BTN_TEMPLATE, slotIndex));
		By inputBy = By.xpath(String.format(DIAGNOSIS_INPUT_TEMPLATE, slotIndex));
		WebElement icdBtn = explicitWait.until(ExpectedConditions.elementToBeClickable(btnBy));
		safeClick(icdBtn);

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ICD_MODAL_TITLE)));
		WebElement modal = driver.findElement(By.xpath(ACTIVE_MODAL));

		String term = icdCodeOrLetter == null ? "A" : icdCodeOrLetter.trim();
		if (term.length() == 1 && Character.isLetter(term.charAt(0))) {
			WebElement alphaBtn = modal.findElement(By.xpath(
					".//button[contains(@class,'nc10-alpha-btn') and normalize-space()='" + term.toUpperCase() + "']"));
			alphaBtn.click();
		} else {
			List<WebElement> byCodeRadio = modal.findElements(By.xpath(
					".//label[contains(@class,'ant-radio-wrapper')][.//span[normalize-space()='By Code']]"));
			if (!byCodeRadio.isEmpty()) {
				byCodeRadio.get(0).click();
			}
			WebElement searchInput = modal.findElement(By.xpath(".//input[@placeholder='Enter ICD search term...']"));
			clearAndType(searchInput, term);
			modal.findElement(By.xpath(".//button[contains(@class,'ant-input-search-button')]")).click();
		}

		By modalRows = By.xpath(ACTIVE_MODAL + "//tbody/tr[contains(@class,'nc10-clickable-row')]");
		explicitWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(modalRows, 0));

		List<WebElement> exactLinks = driver.findElements(By.xpath(
				ACTIVE_MODAL + "//a[contains(@class,'nc10-code-link') and normalize-space()='" + term + "']"));
		if (!exactLinks.isEmpty()) {
			jsClick(exactLinks.get(0));
		} else {
			jsClick(driver.findElements(modalRows).get(0));
		}

		explicitWait.until(d -> {
			String val = d.findElement(inputBy).getDomProperty("value");
			return val != null && !val.trim().isEmpty();
		});
	}

	public void searchICDCodeInModal(String searchTerm) {
		By btnBy = By.xpath(String.format(DIAGNOSIS_ICD_BTN_TEMPLATE, 1));
		WebElement icdBtn = explicitWait.until(ExpectedConditions.elementToBeClickable(btnBy));
		scrollToCenter(icdBtn);
		elementClick(icdBtn);

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ICD_MODAL_TITLE)));
		WebElement modal = driver.findElement(By.xpath(ACTIVE_MODAL));

		List<WebElement> byCodeRadio = modal.findElements(By.xpath(
				".//label[contains(@class,'ant-radio-wrapper')][.//span[normalize-space()='By Code']]"));
		if (!byCodeRadio.isEmpty()) {
			byCodeRadio.get(0).click();
		}
		WebElement searchInput = modal.findElement(By.xpath(".//input[@placeholder='Enter ICD search term...']"));
		clearAndType(searchInput, searchTerm);
		modal.findElement(By.xpath(".//button[contains(@class,'ant-input-search-button')]")).click();

		// Wait briefly for search API to complete
		waitForSeconds();
	}

	public boolean isLookupModalNoRecordsDisplayed() {
		List<WebElement> rows = driver.findElements(
				By.xpath(ACTIVE_MODAL + "//tbody/tr[contains(@class,'nc10-clickable-row')]"));
		if (!rows.isEmpty()) {
			return false;
		}
		List<WebElement> emptyState = driver.findElements(
				By.xpath(ACTIVE_MODAL + "//*[contains(@class,'ant-empty') or contains(normalize-space(),'No data') or contains(normalize-space(),'No record')]"));
		return !emptyState.isEmpty() || rows.isEmpty();
	}

	public void closeActiveModalIfOpen() {
		List<WebElement> closeBtns = driver.findElements(By.xpath(MODAL_CLOSE_BTN));
		for (WebElement cb : closeBtns) {
			try {
				if (cb.isDisplayed()) {
					cb.click();
				}
			} catch (Exception ignored) {
			}
		}
	}

	public String getSelectedDiagnosisCode() {
		return getSelectedDiagnosisCode(1);
	}

	public String getSelectedDiagnosisCode(int slotIndex) {
		WebElement input = driver.findElement(By.xpath(String.format(DIAGNOSIS_INPUT_TEMPLATE, slotIndex)));
		return getDomProperty(input, "value");
	}

	// ---------- Service Details (Row 1) ----------

	public void enterServiceDates(String fromDate, String toDate) {
		scrollToCenter(row1FromDate);
		clearAndType(row1FromDate, fromDate);
		row1FromDate.sendKeys(Keys.ENTER);

		clearAndType(row1ToDate, toDate);
		row1ToDate.sendKeys(Keys.ENTER);
	}

	public void enterServicePos(String pos) {
		if (pos != null && !pos.trim().isEmpty()) {
			clearAndType(row1PosInput, pos.trim());
		}
	}

	public void chooseServiceCPTFromMenu(String cptCodeOrLetter) {
		explicitWait.until(ExpectedConditions.elementToBeClickable(row1CptInfoBtn));
		safeClick(row1CptInfoBtn);

		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CPT_MODAL_TITLE)));
		WebElement modal = driver.findElement(By.xpath(ACTIVE_MODAL));

		String term = cptCodeOrLetter == null ? "A" : cptCodeOrLetter.trim();
		if (term.length() == 1 && Character.isLetter(term.charAt(0))) {
			WebElement alphaBtn = modal.findElement(By.xpath(
					".//button[contains(@class,'nc10-alpha-btn') and normalize-space()='" + term.toUpperCase() + "']"));
			alphaBtn.click();
		} else {
			List<WebElement> byCodeRadio = modal.findElements(By.xpath(
					".//label[contains(@class,'ant-radio-wrapper')][.//span[normalize-space()='By Code']]"));
			if (!byCodeRadio.isEmpty()) {
				byCodeRadio.get(0).click();
			}
			WebElement searchInput = modal.findElement(By.xpath(".//input[@placeholder='Enter CPT search term...']"));
			clearAndType(searchInput, term);
			modal.findElement(By.xpath(".//button[contains(@class,'ant-input-search-button')]")).click();
		}

		By modalRows = By.xpath(ACTIVE_MODAL + "//tbody/tr[contains(@class,'nc10-clickable-row')]");
		explicitWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(modalRows, 0));

		List<WebElement> exactLinks = driver.findElements(By.xpath(
				ACTIVE_MODAL + "//a[contains(@class,'nc10-code-link') and normalize-space()='" + term + "']"));
		if (!exactLinks.isEmpty()) {
			jsClick(exactLinks.get(0));
		} else {
			jsClick(driver.findElements(modalRows).get(0));
		}

		explicitWait.until(d -> {
			String val = d.findElement(By.xpath(ROW1_CPT_INPUT)).getDomProperty("value");
			return val != null && !val.trim().isEmpty();
		});
	}

	public void selectDiagnosisPointer(int pointerNumber) {
		String checkboxXpath = "(//tr[@data-row-key='1']//div[contains(@class,'nc10-ptr-cell')]//input[@type='checkbox'])["
				+ pointerNumber + "]";
		WebElement checkbox = explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkboxXpath)));
		scrollToCenter(checkbox);
		if (!checkbox.isSelected()) {
			jsClick(checkbox);
		}
	}

	public void enterServiceChargesAndUnit(String charges, String unit) {
		if (charges != null && !charges.trim().isEmpty()) {
			clearAndType(row1ChargesInput, charges);
			row1ChargesInput.sendKeys(Keys.TAB);
		}
		if (unit != null && !unit.trim().isEmpty()) {
			clearAndType(row1UnitInput, unit);
			row1UnitInput.sendKeys(Keys.TAB);
		}
	}

	public String getRow1CptCode() {
		return getDomProperty(row1CptInput, "value");
	}

	public String getRow1Charges() {
		return getDomProperty(row1ChargesInput, "value");
	}

	public double getClaimTotalCharges() {
		List<WebElement> totalInputs = driver.findElements(By.xpath(CLAIM_TOTAL_CHARGES_INPUT));
		if (totalInputs.isEmpty()) {
			return 0.0;
		}
		String raw = totalInputs.get(0).getDomProperty("value");
		if (raw == null || raw.trim().isEmpty()) {
			return 0.0;
		}
		return Double.parseDouble(raw.replace(",", "").trim());
	}

	// ---------- Footer Actions & Verification ----------

	public void clickSubmit() {
		explicitWait.until(ExpectedConditions.elementToBeClickable(submitBtn));
		safeClick(submitBtn);
	}

	public void clickCheckRule() {
		explicitWait.until(ExpectedConditions.elementToBeClickable(checkRuleBtn));
		safeClick(checkRuleBtn);
	}

	public void clickClear() {
		explicitWait.until(ExpectedConditions.elementToBeClickable(clearBtn));
		safeClick(clearBtn);

		// Confirm if Clear opens a confirmation dialog
		List<WebElement> okBtns = driver.findElements(By.xpath(MODAL_CONFIRM_OK_BTN));
		if (!okBtns.isEmpty() && okBtns.get(0).isDisplayed()) {
			jsClick(okBtns.get(0));
		}
	}

	private String getFirstVisibleFeedbackText() {
		List<WebElement> msgs = driver.findElements(By.xpath(NOTIFICATION_ANY));
		for (WebElement el : msgs) {
			try {
				if (el.isDisplayed()) {
					String txt = el.getText();
					if (txt != null && !txt.trim().isEmpty()) {
						return txt.trim();
					}
				}
			} catch (Exception ignored) {
			}
		}
		return getAllValidationErrors();
	}

	public String waitForSubmissionFeedback() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("wait.explicit")));
		try {
			wait.until(d -> !getFirstVisibleFeedbackText().isEmpty()
					|| !d.getCurrentUrl().contains("/claims/create-10"));
			return getFirstVisibleFeedbackText();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * If submitting triggers the "Duplicate Claim Warning" confirmation modal,
	 * clicks Proceed and waits for the subsequent submission result.
	 */
	public String waitForSubmissionFeedbackProceedingIfDuplicate() {
		String feedback = waitForSubmissionFeedback();
		if (feedback.toLowerCase().contains("duplicate claim")
				|| feedback.toLowerCase().contains("claim already exists")) {
			List<WebElement> proceedBtns = driver.findElements(By.xpath(DUPLICATE_PROCEED_BTN));
			if (!proceedBtns.isEmpty()) {
				safeClick(proceedBtns.get(0));
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("wait.explicit")));
				try {
					wait.until(d -> {
						if (!d.getCurrentUrl().contains("/claims/create-10")) {
							return true;
						}
						String current = getFirstVisibleFeedbackText().toLowerCase();
						return !current.contains("duplicate claim") && !current.contains("claim already exists");
					});
				} catch (Exception ignored) {
				}
				return waitForSubmissionFeedback();
			}
		}
		return feedback;
	}

	public boolean isSubmissionSuccess(String feedback) {
		if (!driver.findElements(By.xpath(NOTIFICATION_SUCCESS)).isEmpty()) {
			return true;
		}
		if (!getApplnUrl().contains("/claims/create-10")) {
			return true;
		}
		String lower = feedback == null ? "" : feedback.toLowerCase();
		return lower.contains("success") || lower.contains("created") || lower.contains("submitted")
				|| lower.contains("saved") || lower.contains("claim id") || lower.contains("claim #");
	}

	public boolean isValidationOrWarningDisplayed(String feedback) {
		if (hasValidationErrors()) {
			return true;
		}
		if (!driver.findElements(By.xpath(NOTIFICATION_WARNING_OR_ERROR)).isEmpty()) {
			return true;
		}
		String lower = feedback == null ? "" : feedback.toLowerCase();
		return !lower.isEmpty() && !isSubmissionSuccess(feedback);
	}

	public boolean hasValidationErrors() {
		return !driver.findElements(By.xpath(FORM_ERRORS)).isEmpty();
	}

	public String getAllValidationErrors() {
		List<WebElement> errors = driver.findElements(By.xpath(FORM_ERRORS));
		StringBuilder sb = new StringBuilder();
		for (WebElement err : errors) {
			if (!sb.isEmpty()) {
				sb.append("; ");
			}
			sb.append(err.getText().trim());
		}
		return sb.toString();
	}
}
