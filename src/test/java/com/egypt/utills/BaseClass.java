package com.egypt.utills;

import java.io.File;
//import java.io.IOException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
	public static  WebDriver driver;
	Alert alert;
	JavascriptExecutor js;
	Select s;
	TakesScreenshot ts;
	Actions actions;
	
	int milliSeconds= 5000;
	//Create Cell 
//	public void createCellAndSetData(String sheetName, int rownum, int cellnum, String data) throws IOException {
//		File file = new File(
//				"C:\\Users\\User\\eclipse-workspacemagi\\MavenProject_9_30Am\\Excel Sheet\\place order.xlsx");
//		FileInputStream fileInputStream = new FileInputStream(file);
//		Workbook workbook = new XSSFWorkbook(fileInputStream);
//		Sheet sheet = workbook.getSheet(sheetName);
//		Row row = sheet.getRow(rownum);
//		Cell cell = row.createCell(cellnum);
//		cell.setCellValue(data);
//		FileOutputStream fileOutputStream = new FileOutputStream(file);
//		workbook.write(fileOutputStream);
//		workbook.close();
//
//	}
//	
//	// Update Cell Value
//	public void updateCellData(String sheetName, int rownum, int cellnum, String oldData, String newData)
//			throws IOException {
//		File file = new File(
//				"C:\\Users\\Velmurugan\\eclipse-workspace\\FrameworkClass930AMBatch\\Excel Sheet\\TestData.xlsx");
//		FileInputStream fileInputStream = new FileInputStream(file);
//		Workbook workbook = new XSSFWorkbook(fileInputStream);
//		Sheet sheet = workbook.getSheet(sheetName);
//		Row row = sheet.getRow(rownum);
//		Cell cell = row.getCell(cellnum);
//		String value = cell.getStringCellValue();
//		if (value.equals(oldData)) {
//			cell.setCellValue(newData);
//		}
//		FileOutputStream fileOutputStream = new FileOutputStream(file);
//		workbook.write(fileOutputStream);
//	}
//	
//	// Get the Cell Data
//	public static String getCellData(String sheetName, int rownum, int cellnum) throws IOException, InterruptedException {
//		String res = null;
//		File file = new File(
//				"C:\\Users\\User\\eclipse-workspacemagi\\MavenProject_9_30Am\\Excel Sheet\\place order.xlsx");
//		FileInputStream fileInputStream = new FileInputStream(file);
//		Workbook workbook = new XSSFWorkbook(fileInputStream);
//		Sheet sheet = workbook.getSheet(sheetName);
//		Row row = sheet.getRow(rownum);
//		Cell cell = row.getCell(cellnum);
//		CellType cellType = cell.getCellType();
//		switch (cellType) {
//		case STRING:
//			res = cell.getStringCellValue();
//
//			break;
//		case NUMERIC:
//			if (DateUtil.isCellDateFormatted(cell)) {
//				Date dateCellValue = cell.getDateCellValue();
//				SimpleDateFormat dateForma = new SimpleDateFormat("dd-MMM-yy");
//				res = dateForma.format(dateCellValue);
//			} else {
//				double numericCellValue = cell.getNumericCellValue();
//
//				long round = Math.round(numericCellValue);
//				if (round == numericCellValue) {
//					res = String.valueOf(round);
//				} else {
//					res = String.valueOf(numericCellValue);
//				}
//
//			}
//
//			break;
//
//		default:
//			break;
//		}
//		return res;
//	}
	//1. Chrome Driver
		public static  void browserLaunch() {
			driver = new ChromeDriver();
		}

	//2. get Url
		public static  void enterApplnUrl(String url) {
			driver.get(url);
		}

		
	//3. maximize
		public static   void maximizeWindow() {
			driver.manage().window().maximize();
		}

	//4.sendkeys
		public  void elementSendKeys(WebElement element, String data) {
			webdriverWait(20, element);
			//if (enabledMethod(element) && displayedMethod(element)) {
				element.sendKeys(data);
			//}

		}

	//5.Click
		public  void elementClick(WebElement element) {
			webdriverWait(10, element);
			if (displayedMethod(element) && enabledMethod(element)) {
				element.click();
			}

		}

	//6. Get title
		public String getTitle() {
			String title = driver.getTitle();
			return title;
		}

	//7. find the locator by Id
		public WebElement findLocatorById(String attributeValue) {
			WebElement element = driver.findElement(By.id(attributeValue));
			return element;
		}

	//8. find the locator by Name
		public WebElement findLocatorByName(String attributeValue) {
			WebElement element = driver.findElement(By.name(attributeValue));
			return element;
		}

	//9.find the locator by ClassName
		public WebElement findLocatorByClassName(String attributeValue) {
			WebElement element = driver.findElement(By.className(attributeValue));
			return element;
		}

	//10. find the locator by Xpath
		public  WebElement findLocatorByXpath(String exp) {
			WebElement element = driver.findElement(By.xpath(exp));
			return element;
		}

	//11. Current Url
		public String getApplnUrl() {
			String currentUrl = driver.getCurrentUrl();
			return currentUrl;
		}

	//12. Close Window
		public static void closeWindow() {
			driver.quit();
		}

	//13. Get Text
		public String elementGetText(WebElement element) {
			webdriverWait(10, element);
			String text = element.getText();
			System.out.println(text);
			return text;
		}

	//14. Attribute Value
	//99%-->value is fixed
		public String getDomProperty(WebElement element) {
			webdriverWait(10, element);
			String domProperty = element.getDomProperty("value");
			return domProperty;
		}

		// 1%-->value is NOT fixed
		public String getDomProperty(WebElement element, String attributeName) {
			String domProperty = element.getDomProperty(attributeName);
			return domProperty;
		}

		// 16. Alert accept
		public void alertDriverAccept() {
			alert = driver.switchTo().alert();
			alert.accept();

		}

		//16.1 Atert Accept
//		public static String getAlertMsg() {
//
//			String msg = null;
//
//			try {
//				Alert alert1 = driver.switchTo().alert();
//				msg = alert1.getText();
//				alert1.accept();
//			} catch (Exception e) {
//				msg = null;
//			}
//
//			System.out.println("Alert Msg: " + msg);
//
//			return msg;
//
//		}
		// 17. Alert dismiss

		public void alertDriverDismiss() {
			alert = driver.switchTo().alert();
			alert.dismiss();
		}

		// 18. Select By Visisble Text
		public void selectByVisiableText(WebElement dropdown, String visibleText) {
			webdriverWait(10, dropdown);
			if (displayedMethod(dropdown) && enabledMethod(dropdown)) {
				s = new Select(dropdown);
				s.selectByVisibleText(visibleText);
			}

		}

		// 19. SelectByValue
		public void selectByValue(WebElement dropdown, String value) {
			webdriverWait(10, dropdown);
			if (displayedMethod(dropdown) && enabledMethod(dropdown)) {
				s = new Select(dropdown);
				s.selectByValue(value);
			}

		}
		// 20. selectByIndex

		public void selectByIndex(WebElement dropdown, int index) {
			webdriverWait(10, dropdown);
			if (displayedMethod(dropdown) && enabledMethod(dropdown)) {
				s = new Select(dropdown);
				s.selectByIndex(index);
			}

		}

		// 21. JS insert Value
		public void jsInsertValue(WebElement element, String data) {
			webdriverWait(10, element);

			js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('value', '" + data + "')", element);

		}

		// 22. JS click
		public void jsClick(WebElement element) {

			js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", element);
		}

		// 23. Frame Using by Index
		public void frameIndex(int index) {

			driver.switchTo().frame(index);
		}

		// 24. Frame Using by name
		public void frameName(String name) {
			driver.switchTo().frame(name);

		}

		// 25. Frame Using by element
		public void frameElement(WebElement element) {
			driver.switchTo().frame(element);
		}
		// 26. Enabled

		public static boolean enabledMethod(WebElement element) {
			boolean enabled = element.isEnabled();
			return enabled;
		}

	//27. Selected
		public boolean selectedMethod(WebElement element) {
			boolean select = element.isSelected();
			return select;
		}
		// 28. Displayed

		public static boolean displayedMethod(WebElement element) {
			boolean display = element.isDisplayed();
			return display;
		}

		// 29. get all options
		public List<String> getAllOptions(WebElement ddn) {

			if (displayedMethod(ddn) && enabledMethod(ddn)) {

				List<String> li = new ArrayList<String>();
				s = new Select(ddn);
				List<WebElement> allSelectedOptions = s.getAllSelectedOptions();
				for (WebElement webElement : allSelectedOptions) {
					String text = webElement.getText();
					boolean b = li.add(text);

				}
				return li;
			}
			;
			return null;
		}

		// 30. get all optons using value
		public List<String> getAllOptionsValue(WebElement ddn, String value) {
			webdriverWait(10, ddn);
			if (displayedMethod(ddn)&&enabledMethod(ddn)) {
				
			
			List<String> li = new ArrayList<String>();
			s = new Select(ddn);
			List<WebElement> allSelectedOptions = s.getOptions();
			for (WebElement webElement : allSelectedOptions) {
				@Nullable
				String attribute = webElement.getDomProperty(value);
				boolean b = li.add(attribute);

			}
			return li;
			}
			return null;
		}

		// 31. get first option

		public String getfirstOption(WebElement element) {
			if (displayedMethod(element) && enabledMethod(element)) {

				s = new Select(element);
				WebElement first = s.getFirstSelectedOption();
				String text = first.getText();
				return text;
			}
			return null;
		}

		// 32. impllicitly wait

		public static  void implicitlyWait(long sec) {

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(sec));
		}
		
		// Thread sleep
		public void waitForSeconds() {
	        try {
	            Thread.sleep(milliSeconds); // converting seconds to milliseconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

		// 33. WebdriveWait

		public  WebElement webdriverWait(long sec, WebElement element) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
			WebElement time = wait.until(ExpectedConditions.visibilityOf(element));
			return time;
		}
		
		//33.1. Webdriver Wait to be click
		
		public WebElement clickableOfElementWait(long sec, WebElement element) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
			return wait.until(ExpectedConditions.visibilityOf(element));
		}

		// 34. FluentWait
		public void fluentWait(long sec, long sec1) {
			Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(sec)).pollingEvery(Duration.ofSeconds(sec1))
					.ignoring(Throwable.class);
		}

	//35. deselectbyIndex
		public void deselectByIndex(WebElement element, int index) {
			webdriverWait(10, element);
			if (displayedMethod(element)&&enabledMethod(element)) {
				
			
			s = new Select(element);
			s.deselectByIndex(index);
			}
		}

		// 36. deselectbyValue
		public void deselectByValue(WebElement element, String value) {
			webdriverWait(10, element);
			if (displayedMethod(element)&&enabledMethod(element)) {
			s = new Select(element);
			s.deselectByValue(value);
			}
		}

		// 37. deselectbyVisibleText
		public void deselectByVisibleText(WebElement element, String text) {
			webdriverWait(10, element);
			if (displayedMethod(element)&&enabledMethod(element)) {
			s = new Select(element);
			s.deselectByVisibleText(text);
			}
		}

		// 38. deselectAll
		public void deselectAll(WebElement element) {
			webdriverWait(10, element);
			if (displayedMethod(element)&&enabledMethod(element)) {
			s = new Select(element);
			s.deselectAll();
			}
		}

		// 39. clear
		public void clear(WebElement element) {
			webdriverWait(10, element);
			element.click();
		}

		// 40. screenshot
		public void getScreenShot(String name) throws IOException {

			ts = (TakesScreenshot) driver;
			File screenshotAs = ts.getScreenshotAs(OutputType.FILE);
			
			FileUtils.copyFile(screenshotAs,
					new File("C:\\Users\\User\\eclipse-workspacemagi\\OMRBranchSeleniumTasks_Mahendran\\"+name+".png"));
			
		}

		// 41. single element screenshot
		public void getScreenShotElement(WebElement element, String name, String formate) throws IOException {
			webdriverWait(20, element);
			Date date = new Date();
			SimpleDateFormat simple = new SimpleDateFormat(formate);
			File screenshotAs = element.getScreenshotAs(OutputType.FILE);
//			FileUtils.copyFile(screenshotAs,
//					new File("C:\\Users\\User\\eclipse-workspacemagi\\OMRBranchSeleniumTasks_Mahendran\\"+name+".png"));
			//FileUtils.copyFile(screenshotAs, System.getProperty("user.dir") +simple.format(date)+".png");
			
		}

		// 42. MouseOverActions
		public void moveToElement(WebElement element) {
			webdriverWait(20, element);
			actions = new Actions(driver);
			actions.moveToElement(element).perform();
		}

		// 43. dragAndDrop
		public void dragAndDrop(WebElement element, WebElement element2) {
			webdriverWait(20, element);
			actions = new Actions(driver);
			actions.dragAndDrop(element, element2).perform();
		}

		// 44. RightClick
		public void rightClick(WebElement element) {
			webdriverWait(20, element);
			actions = new Actions(driver);
			actions.contextClick(element).perform();
		}

		// 45.doubleClick
		public void doubleClick(WebElement element) {
			webdriverWait(20, element);
			actions = new Actions(driver);
			actions.doubleClick(element).perform();
		}

		// 46. sendkey with enter
		public void sendKeysAndEnter(WebElement element, String text) {
			webdriverWait(20, element);
			if (enabledMethod(element) && displayedMethod(element)) {
				element.sendKeys(text+ Keys.ENTER);
			}

		}

		// 47. Navigation Commands
		public void goToUrl(String url) {
			driver.navigate().to(url);
		}

		public void back() {
			driver.navigate().back();
		}

		public void forward() {
			driver.navigate().forward();
		}

		public void refresh() {
			driver.navigate().refresh();
		}

		// 48. Scroll down and Scroll up
		public void scrollDownAndScrollUp(boolean con, WebElement element) {
			js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(" + con + ")", element);
		}
		// 49. Window Handle

		public String ParentWindow() {
			String windowHandle = driver.getWindowHandle();
			return windowHandle;
		}

		public Set<String> allWindows() {

			Set<String> windowHandles = driver.getWindowHandles();
			return windowHandles;
		}

		public boolean windowHandle() {
			String windowHandle = ParentWindow();
			Set<String> windowHandles = allWindows();
			for (String string : windowHandles) {
				if (!windowHandle.equals(windowHandles)) {
					driver.switchTo().window(string);
					return true;
				}
			}
			return false;
		}
		

	

}
