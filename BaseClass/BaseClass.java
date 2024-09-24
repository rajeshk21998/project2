package com.omrbranch.baseclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
	public static WebDriver driver;

	public static void browserLaunch(String browserType) {
		switch (browserType) {
		case "CHROME":
			driver = new ChromeDriver();
			break;
		case "IE":
			driver = new InternetExplorerDriver();
			break;
		case "FIREFOX":
			driver = new FirefoxDriver();
			break;
		case "EDGE":
			driver = new EdgeDriver();
			break;
		case "SAFARI":
			driver = new SafariDriver();
			break;

		default:
			break;
		}

	}

	public String getProjectPath() {
		String path = System.getProperty("user.dir");
		return path;
	}

	public String getPropertyFileValue(String key) throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(getProjectPath() + "\\config\\config.properties"));
		Object object = properties.get(key);
		String value = (String) object;
		return value;
	}
	
	public void deleteCookies() {
		driver.manage().deleteAllCookies();
	}
	
	public byte[] takeScreenshot() {
		TakesScreenshot screenshot=(TakesScreenshot)driver;
		byte[] bs = screenshot.getScreenshotAs(OutputType.BYTES);
		return bs;
	}

	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	public void DismissAlert() {
		driver.switchTo().alert().dismiss();
	}

	public void refresh() {
		driver.navigate().refresh();
	}

	public void switchToChildWindow() {
		String pWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		for (String eachWindow : allWindows) {
			if (!pWindow.equals(eachWindow)) {
				driver.switchTo().window(eachWindow);
			}
		}

	}

	public void switchToFrameByIdOrName(String id) {
		driver.switchTo().frame(id);
	}

	public void switchToFrameByIndex(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToFrameByElement(WebElement element) {
		driver.switchTo().frame(element);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public void screenshot(String fName) throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File s = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(s, new File("C:\\Users\\DELL\\eclipse-workspace\\Cucumber\\Screenshots\\" + fName + ".png"));
	}

	public void screenshot(WebElement element, String fName) throws IOException {

		File s = element.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(s, new File("C:\\Users\\DELL\\eclipse-workspace\\Cucumber\\Screenshots\\" + fName + ".png"));
	}

	public void visibilityOfElement(WebElement element) {
		WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
		driverWait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void implicitWait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}

	public static void implicitWait(int secs) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(secs));
	}

	public static void browserLaunch() {
		driver = new ChromeDriver();
	}

	public static void enterApplnUrl(String url) {
		driver.get(url);
	}

	public static void maximizeWindow() {
		driver.manage().window().maximize();
	}

	public void elementSendKeys(WebElement element, String data) {
		visibilityOfElement(element);
		boolean elementIsEnabled = elementIsEnabled(element);
		boolean elementIsDisplayed = elementIsDisplayed(element);

		if (elementIsDisplayed && elementIsEnabled) {
			element.sendKeys(data);
		}
	}

	public void elementSendKeysEnter(WebElement element, String data) {
		visibilityOfElement(element);
		boolean elementIsEnabled = elementIsEnabled(element);
		boolean elementIsDisplayed = elementIsDisplayed(element);

		if (elementIsDisplayed && elementIsEnabled) {
			element.sendKeys(data, Keys.ENTER);
		}
	}

	public void elementSendKeysJs(WebElement element, String data) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].setAttribute('value','" + data + "')", element);

	}

	public void elementClick(WebElement element) {
		visibilityOfElement(element);
		boolean elementIsEnabled = elementIsEnabled(element);
		boolean elementIsDisplayed = elementIsDisplayed(element);

		if (elementIsDisplayed && elementIsEnabled) {
			element.click();
		}
	}

	public String getApplnTitle() {
		String title = driver.getTitle();
		return title;
	}

	public WebElement findLocatorById(String attributeValue) {
		WebElement element = driver.findElement(By.id(attributeValue));
		return element;
	}

	public WebElement findLocatorByName(String attributeValue) {
		WebElement element = driver.findElement(By.name(attributeValue));
		return element;

	}

	public WebElement findLocatorByCLassName(String attributeValue) {
		WebElement element = driver.findElement(By.className(attributeValue));
		return element;

	}

	public WebElement findLocatorByXpath(String xpathExp) {
		WebElement element = driver.findElement(By.xpath(xpathExp));
		return element;

	}

	public String elementGetText(WebElement element) {
		String text = null;

		visibilityOfElement(element);
		boolean elementIsEnabled = elementIsEnabled(element);
		boolean elementIsDisplayed = elementIsDisplayed(element);

		if (elementIsDisplayed && elementIsEnabled) {

			text = element.getText();
		}
		return text;
	}

	// 99%--->value
	public String elementGetAttributeValue(WebElement element) {
		visibilityOfElement(element);
		String attribute = element.getAttribute("value");
		return attribute;
	}

	// 1%--->?
	public String elementGetAttributeValue(WebElement element, String attributeName) {
		visibilityOfElement(element);
		String attribute = element.getAttribute(attributeName);
		return attribute;
	}

	public static void closeWindow() {
		driver.close();
	}

	public String getApplnUrl() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}

	public boolean elementIsDisplayed(WebElement element) {
		boolean displayed = element.isDisplayed();
		return displayed;
	}

	public boolean elementIsEnabled(WebElement element) {
		boolean displayed = element.isEnabled();
		return displayed;
	}

	public boolean elementIsSelected(WebElement element) {
		boolean displayed = element.isSelected();
		return displayed;
	}

	public void elementClearTextBox(WebElement element) {
		visibilityOfElement(element);
		element.clear();

	}

	public void selectDropDownOptionByText(WebElement element, String text) {
		visibilityOfElement(element);
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}

	public void selectDropDownOptionByValue(WebElement element, String text) {
		visibilityOfElement(element);
		Select select = new Select(element);
		select.selectByValue(text);
	}

	public void selectDropDownOptionByIndex(WebElement element, int index) {
		visibilityOfElement(element);
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	public void selectDropDownAllOption(WebElement element) {
		visibilityOfElement(element);
		Select select = new Select(element);
		List<WebElement> options = select.getOptions();
		for (int i = 1; i < options.size(); i++) {
			select.selectByIndex(i);
		}
	}

}