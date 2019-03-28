
package com.crossover.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Mahesh akoju
 */

public class ElementHelper {
	// private static WebDriver driver;
	private WebDriverWait wait;
	public static final int MAX_WAIT_TIME = 120;
	public static final int POLLING_TIME = 2;

	/**
	 * This method is used to wait for an element using Fluent Wait
	 *
	 * @param element
	 */
	public static WebElement waitUntilVisibilityOf(WebDriver driver, WebElement element) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(MAX_WAIT_TIME, TimeUnit.SECONDS)
				.pollingEvery(POLLING_TIME, TimeUnit.SECONDS).ignoring(java.util.NoSuchElementException.class);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * This method is used to wait for an element using By locator of Fluent Wait
	 *
	 * @param locator
	 * @return
	 */
	public static WebElement waitUntilVisibilityOf(WebDriver driver, By locator) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(MAX_WAIT_TIME, TimeUnit.SECONDS)
				.pollingEvery(POLLING_TIME, TimeUnit.SECONDS).ignoring(java.util.NoSuchElementException.class);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static List<WebElement> waitUntilvisibilityOfAllElements(WebDriver driver, WebElement element) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(MAX_WAIT_TIME, TimeUnit.SECONDS)
				.pollingEvery(POLLING_TIME, TimeUnit.SECONDS).ignoring(java.util.NoSuchElementException.class);
		return wait.until(ExpectedConditions.visibilityOfAllElements(element));
	}

	public static List<WebElement> waitUntilvisibilityOfAllElements(WebDriver driver, By locator) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(MAX_WAIT_TIME, TimeUnit.SECONDS)
				.pollingEvery(POLLING_TIME, TimeUnit.SECONDS).ignoring(java.util.NoSuchElementException.class);
		return wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(locator)));
	}

	/**
	 * Wait for presence of element located on page
	 *
	 * @param element WebElement
	 */
	public static void waitForPresenceOfElementLocated(WebDriver driver, By element) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(MAX_WAIT_TIME, TimeUnit.SECONDS)
				.pollingEvery(POLLING_TIME, TimeUnit.SECONDS).ignoring(java.util.NoSuchElementException.class);
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}
}