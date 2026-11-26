package com.egypt.page;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
 * Page Object for Billing Details -> Claim for Day-ICD10 (/claims/for-the-day-10).
 * All locators verified against the live QA Ant Design DOM.
 */
public class ClaimForDayICD10Page extends BaseClass {

	private static final DateTimeFormatter US_DATE = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	// ---------- Sidebar Navigation ----------
	private static final String BILLING_DETAILS_MENU = "//span[contains(@class,'sidebar-label') and normalize-space()='Billing Details']";
	private static final String CLAIM_FOR_DAY_MENU = "//a[contains(@class,'submenu-item') and contains(@href,'/claims/for-the-day-10') and normalize-space()='Claim for Day-ICD10']";

	// ---------- Page Header & Actions ----------
	private static final String PAGE_HEADER = "//h2[normalize-space()='Claims for the Day']";
	private static final String SEARCH_FILTERS_SECTION = "//*[contains(normalize-space(),'Search Filters')]";
	private static final String CLAIMS_LIST_SECTION = "//*[contains(normalize-space(),'Claims List')]";
	private static final String GO_BACK_BTN = "//button[@type='button' and normalize-space()='Go Back']";

	// ---------- Search Filters ----------
	private static final String LOCATION_INPUT_ID = "locationId";
	private static final String PHYSICIAN_INPUT_ID = "physicianId";
	private static final String PAYOR_INPUT_ID = "insuranceId";
	private static final String DISPLAY_INPUT_ID = "displayBy";
	private static final String START_DATE_INPUT = "//input[@id='startDate']";
	private static final String END_DATE_INPUT = "//input[@id='endDate']";
	private static final String INCLUDE_NOSHOW_NO_RADIO = "//input[@name='includeNoShow' and @value='N']";
	private static final String INCLUDE_NOSHOW_YES_RADIO = "//input[@name='includeNoShow' and @value='Y']";
	private static final String RESET_BTN = "//button[@type='button' and normalize-space()='Reset']";
	private static final String SUBMIT_BTN = "//button[@type='submit' and normalize-space()='Submit']";

	// ---------- Claims List Table & Pagination ----------
	private static final String CLAIMS_TABLE = "//table";
	private static final String ITEM_ROWS = "//table//tbody/tr[starts-with(@data-row-key,'item-')]";
	private static final String LOADING_SPINNER = "//div[contains(@class,'ant-spin-spinning')]";
	private static final String EMPTY_STATE = "//div[contains(@class,'ant-empty')] | //td[contains(normalize-space(),'No data') or contains(normalize-space(),'No record')]";
	private static final String PAGINATION_TOTAL_TEXT = "//li[contains(@class,'ant-pagination-total-text')]";
	private static final String PAGINATION_NEXT_BTN = "//li[contains(@class,'ant-pagination-next')]//button";
	private static final String PAGINATION_PREV_BTN = "//li[contains(@class,'ant-pagination-prev')]//button";
	private static final String PAGINATION_ACTIVE_PAGE = "//li[contains(@class,'ant-pagination-item-active')]";
	private static final String SORT_BTN_TEMPLATE = "//button[contains(@class,'claims-sortable-header-btn') and normalize-space()='%s']";

	// ---------- Feedback / Errors ----------
	private static final String FORM_ERRORS = "//div[contains(@class,'ant-form-item-explain-error')][normalize-space()!='']";
	private static final String ALERT_OR_TOAST = "//div[contains(@class,'ant-message-notice-content') or contains(@class,'ant-notification-notice') or contains(@class,'ant-alert-error') or contains(@class,'ant-alert-warning') or contains(@class,'ant-modal-confirm-body')]";

	private final WebDriver driver;
	private final WebDriverWait explicitWait;

	public ClaimForDayICD10Page(WebDriver driver) {
		if (driver == null) {
			throw new IllegalArgumentException(
					"WebDriver is NULL. Initialize driver before creating ClaimForDayICD10Page.");
		}
		this.driver = driver;
		this.explicitWait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("wait.explicit")));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = BILLING_DETAILS_MENU)
	private WebElement billingDetailsMenu;

	@FindBy(xpath = CLAIM_FOR_DAY_MENU)
	private WebElement claimForDayMenu;

	@FindBy(xpath = PAGE_HEADER)
	private WebElement pageHeader;

	@FindBy(xpath = GO_BACK_BTN)
	private WebElement goBackBtn;

	@FindBy(xpath = START_DATE_INPUT)
	private WebElement startDateInput;

	@FindBy(xpath = END_DATE_INPUT)
	private WebElement endDateInput;

	@FindBy(xpath = RESET_BTN)
	private WebElement resetBtn;

	@FindBy(xpath = SUBMIT_BTN)
	private WebElement submitBtn;

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

	private void waitForTableIdle() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
		}
		try {
			new WebDriverWait(driver, Duration.ofSeconds(15))
					.until(d -> d.findElements(By.xpath(LOADING_SPINNER)).isEmpty());
		} catch (Exception ignored) {
		}
	}

	private void selectAntDropdown(String inputId, String optionText, boolean preferNonAllIfBlank) {
		WebElement input = explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.id(inputId)));
		WebElement selector = input.findElement(By.xpath("./ancestor::div[contains(@class,'ant-select-selector')]"));
		safeClick(selector);

		By visibleOptions = By.xpath(
				"//div[contains(@class,'ant-select-dropdown') and not(contains(@class,'ant-select-dropdown-hidden'))]"
						+ "//div[contains(@class,'ant-select-item-option') and not(contains(@class,'ant-select-item-option-disabled'))]");
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(visibleOptions));
		List<WebElement> options = driver.findElements(visibleOptions);

		if (optionText != null && !optionText.trim().isEmpty()) {
			String wanted = optionText.trim().toLowerCase();
			for (WebElement opt : options) {
				String text = opt.getText().trim().toLowerCase();
				if (text.equals(wanted) || text.contains(wanted)) {
					jsClick(opt);
					return;
				}
			}
		}

		if (preferNonAllIfBlank) {
			for (WebElement opt : options) {
				if (!opt.getText().trim().equalsIgnoreCase("All")) {
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

	private void setDatePickerValue(WebElement input, String dateValue) {
		explicitWait.until(ExpectedConditions.visibilityOf(input));
		scrollToCenter(input);
		// Click Ant Design clear icon if present on hover/focus
		try {
			input.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].focus(); arguments[0].click();", input);
		}
		input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
		if (dateValue != null && !dateValue.isEmpty()) {
			input.sendKeys(dateValue);
			input.sendKeys(Keys.ENTER);
		} else {
			// Also click Ant Picker clear icon if value remains
			List<WebElement> clearIcons = input.findElements(
					By.xpath("./ancestor::div[contains(@class,'ant-picker')]//span[contains(@class,'ant-picker-clear')]"));
			if (!clearIcons.isEmpty()) {
				jsClick(clearIcons.get(0));
			}
			input.sendKeys(Keys.TAB);
		}
	}

	// ---------- Navigation ----------

	public void clickBillingDetailsMenu() {
		explicitWait.until(ExpectedConditions.elementToBeClickable(billingDetailsMenu));
		safeClick(billingDetailsMenu);
	}

	public void openClaimForDayICD10() {
		List<WebElement> items = driver.findElements(By.xpath(CLAIM_FOR_DAY_MENU));
		boolean visible = !items.isEmpty() && items.get(0).isDisplayed();
		if (!visible) {
			clickBillingDetailsMenu();
		}
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(CLAIM_FOR_DAY_MENU)));
		safeClick(driver.findElement(By.xpath(CLAIM_FOR_DAY_MENU)));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PAGE_HEADER)));
		waitForTableIdle();
	}

	public void openClaimForDayUrlDirectly() {
		String baseUrl = ConfigReader.get("app.url").replaceAll("/+$", "");
		goToUrl(baseUrl + "/claims/for-the-day-10");
		waitForTableIdle();
	}

	public boolean isClaimForDayPageDisplayed() {
		return getApplnUrl().contains("/claims/for-the-day-10")
				&& !driver.findElements(By.xpath(PAGE_HEADER)).isEmpty()
				&& !driver.findElements(By.xpath(SEARCH_FILTERS_SECTION)).isEmpty()
				&& !driver.findElements(By.xpath(CLAIMS_LIST_SECTION)).isEmpty()
				&& !driver.findElements(By.xpath(CLAIMS_TABLE)).isEmpty();
	}

	// ---------- Filter Getters & Setters ----------

	public String getSelectedLocation() {
		return getAntSelectValue(LOCATION_INPUT_ID);
	}

	public String getSelectedPhysician() {
		return getAntSelectValue(PHYSICIAN_INPUT_ID);
	}

	public String getSelectedPayor() {
		return getAntSelectValue(PAYOR_INPUT_ID);
	}

	public String getSelectedDisplay() {
		return getAntSelectValue(DISPLAY_INPUT_ID);
	}

	public String getStartDateValue() {
		return getDomProperty(startDateInput, "value");
	}

	public String getEndDateValue() {
		return getDomProperty(endDateInput, "value");
	}

	public boolean isIncludeNoShowYesSelected() {
		return driver.findElement(By.xpath(INCLUDE_NOSHOW_YES_RADIO)).isSelected();
	}

	public boolean isIncludeNoShowNoSelected() {
		return driver.findElement(By.xpath(INCLUDE_NOSHOW_NO_RADIO)).isSelected();
	}

	public void selectLocation(String locationText) {
		selectAntDropdown(LOCATION_INPUT_ID, locationText, true);
	}

	public void selectPhysician(String physicianText) {
		selectAntDropdown(PHYSICIAN_INPUT_ID, physicianText, true);
	}

	public void selectPayor(String payorText) {
		selectAntDropdown(PAYOR_INPUT_ID, payorText, true);
	}

	public void selectDisplay(String displayOption) {
		selectAntDropdown(DISPLAY_INPUT_ID, displayOption, false);
	}

	public void setStartDate(String date) {
		setDatePickerValue(startDateInput, date);
	}

	public void setEndDate(String date) {
		setDatePickerValue(endDateInput, date);
	}

	public void clearStartDate() {
		setDatePickerValue(startDateInput, "");
	}

	public void clearEndDate() {
		setDatePickerValue(endDateInput, "");
	}

	public void selectIncludeNoShow(String option) {
		WebElement radio = option.equalsIgnoreCase("Yes")
				? driver.findElement(By.xpath(INCLUDE_NOSHOW_YES_RADIO))
				: driver.findElement(By.xpath(INCLUDE_NOSHOW_NO_RADIO));
		WebElement label = radio.findElement(By.xpath("./ancestor::label[contains(@class,'ant-radio-wrapper')]"));
		safeClick(label);
	}

	// ---------- Action Buttons ----------

	public void clickSubmit() {
		explicitWait.until(ExpectedConditions.elementToBeClickable(submitBtn));
		safeClick(submitBtn);
		waitForTableIdle();
	}

	public void clickSubmitRapidly(int times) {
		explicitWait.until(ExpectedConditions.elementToBeClickable(submitBtn));
		scrollToCenter(submitBtn);
		for (int i = 0; i < times; i++) {
			jsClick(submitBtn);
		}
		waitForTableIdle();
	}

	public void clickReset() {
		explicitWait.until(ExpectedConditions.elementToBeClickable(resetBtn));
		safeClick(resetBtn);
		waitForTableIdle();
	}

	public void clickResetRapidly(int times) {
		explicitWait.until(ExpectedConditions.elementToBeClickable(resetBtn));
		scrollToCenter(resetBtn);
		for (int i = 0; i < times; i++) {
			jsClick(resetBtn);
		}
		waitForTableIdle();
	}

	public void clickGoBack() {
		explicitWait.until(ExpectedConditions.elementToBeClickable(goBackBtn));
		safeClick(goBackBtn);
		waitForTableIdle();
	}

	// ---------- Claims List Inspection, Sorting, Pagination & Links ----------

	public List<WebElement> getClaimItemRows() {
		return driver.findElements(By.xpath(ITEM_ROWS));
	}

	public int getClaimItemRowCount() {
		return getClaimItemRows().size();
	}

	public List<String> getColumnValues(int oneBasedColIndex) {
		List<String> values = new ArrayList<>();
		for (WebElement row : getClaimItemRows()) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			if (cells.size() >= oneBasedColIndex) {
				values.add(cells.get(oneBasedColIndex - 1).getText().trim());
			}
		}
		return values;
	}

	public boolean areAllDisplayedDatesWithinRange(String startDateStr, String endDateStr) {
		LocalDate start = LocalDate.parse(startDateStr, US_DATE);
		LocalDate end = LocalDate.parse(endDateStr, US_DATE);
		for (String dateText : getColumnValues(2)) {
			if (dateText.isEmpty()) {
				continue;
			}
			LocalDate rowDate = LocalDate.parse(dateText, US_DATE);
			if (rowDate.isBefore(start) || rowDate.isAfter(end)) {
				return false;
			}
		}
		return true;
	}

	public void clickSortColumn(String columnName) {
		WebElement sortBtn = explicitWait.until(
				ExpectedConditions.elementToBeClickable(By.xpath(String.format(SORT_BTN_TEMPLATE, columnName))));
		safeClick(sortBtn);
		waitForTableIdle();
	}

	public String getPaginationTotalText() {
		List<WebElement> els = driver.findElements(By.xpath(PAGINATION_TOTAL_TEXT));
		return els.isEmpty() ? "" : els.get(0).getText().trim();
	}

	public String getActivePageNumber() {
		List<WebElement> els = driver.findElements(By.xpath(PAGINATION_ACTIVE_PAGE));
		return els.isEmpty() ? "" : els.get(0).getText().trim();
	}

	public boolean isNextPageEnabled() {
		List<WebElement> btns = driver.findElements(By.xpath(PAGINATION_NEXT_BTN));
		return !btns.isEmpty() && btns.get(0).isEnabled();
	}

	public void clickNextPage() {
		WebElement nextBtn = driver.findElement(By.xpath(PAGINATION_NEXT_BTN));
		safeClick(nextBtn);
		waitForTableIdle();
	}

	public void clickPreviousPage() {
		WebElement prevBtn = driver.findElement(By.xpath(PAGINATION_PREV_BTN));
		safeClick(prevBtn);
		waitForTableIdle();
	}

	public void clickFirstClaimRowActionCell(int oneBasedColIndex) {
		List<WebElement> rows = getClaimItemRows();
		if (rows.isEmpty()) {
			throw new IllegalStateException("No claim rows available to click in Claims List");
		}
		WebElement cell = rows.get(0).findElements(By.tagName("td")).get(oneBasedColIndex - 1);
		List<WebElement> inner = cell.findElements(By.xpath(".//a | .//span | .//button"));
		WebElement target = inner.isEmpty() ? cell : inner.get(0);
		safeClick(target);
		waitForTableIdle();
	}

	public boolean isEmptyStateOrZeroEntries() {
		if (getClaimItemRowCount() == 0) {
			return true;
		}
		return !driver.findElements(By.xpath(EMPTY_STATE)).isEmpty();
	}

	public boolean isLoadingSpinnerVisible() {
		return !driver.findElements(By.xpath(LOADING_SPINNER)).isEmpty();
	}

	// ---------- Validation / Error Inspection & Network Failure Simulation ----------

	public String getVisibleFeedbackOrErrors() {
		StringBuilder sb = new StringBuilder();
		for (WebElement el : driver.findElements(By.xpath(FORM_ERRORS))) {
			try {
				if (el.isDisplayed() && !el.getText().trim().isEmpty()) {
					if (!sb.isEmpty()) {
						sb.append("; ");
					}
					sb.append(el.getText().trim());
				}
			} catch (Exception ignored) {
			}
		}
		for (WebElement el : driver.findElements(By.xpath(ALERT_OR_TOAST))) {
			try {
				if (el.isDisplayed() && !el.getText().trim().isEmpty()) {
					if (!sb.isEmpty()) {
						sb.append("; ");
					}
					sb.append(el.getText().trim());
				}
			} catch (Exception ignored) {
			}
		}
		return sb.toString();
	}

	public boolean isServerErrorPageDisplayed() {
		String body = driver.findElement(By.tagName("body")).getText().toLowerCase();
		return body.contains("whitelabel error page") || body.contains("http status 500")
				|| body.contains("application error: a client-side exception");
	}

	/**
	 * Intercepts browser fetch/XHR calls to simulate a 500 Internal Server Error on next Submit.
	 */
	public void enableSimulated500Error() {
		((JavascriptExecutor) driver).executeScript(
				"if (!window.__origFetch) { window.__origFetch = window.fetch; }"
						+ "window.fetch = function() {"
						+ "  return Promise.resolve(new Response(JSON.stringify({message:'Simulated 500 Server Error'}), {status:500, statusText:'Internal Server Error', headers:{'Content-Type':'application/json'}}));"
						+ "};"
						+ "if (!window.__origXhrSend) { window.__origXhrSend = XMLHttpRequest.prototype.send; }"
						+ "XMLHttpRequest.prototype.send = function() {"
						+ "  var self = this;"
						+ "  setTimeout(function() {"
						+ "    Object.defineProperty(self, 'status', { value: 500, configurable: true });"
						+ "    Object.defineProperty(self, 'readyState', { value: 4, configurable: true });"
						+ "    Object.defineProperty(self, 'responseText', { value: '{\"message\":\"Simulated 500 Server Error\"}', configurable: true });"
						+ "    Object.defineProperty(self, 'response', { value: '{\"message\":\"Simulated 500 Server Error\"}', configurable: true });"
						+ "    if (typeof self.onreadystatechange === 'function') self.onreadystatechange();"
						+ "    if (typeof self.onload === 'function') self.onload();"
						+ "    self.dispatchEvent(new Event('load'));"
						+ "    self.dispatchEvent(new Event('loadend'));"
						+ "  }, 50);"
						+ "};");
	}

	public void disableSimulated500Error() {
		((JavascriptExecutor) driver).executeScript(
				"if (window.__origFetch) { window.fetch = window.__origFetch; }"
						+ "if (window.__origXhrSend) { XMLHttpRequest.prototype.send = window.__origXhrSend; }");
	}
}
