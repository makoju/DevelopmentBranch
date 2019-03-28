package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.TestCase;

public class GMailTest extends TestCase {
	private WebDriver driver;
	private Properties properties = new Properties();
	public static final int MAX_WAIT_TIME = 25000;
	public static final int MIN_WAIT_TIME = 5000;

	@BeforeClass
	public void setUp() throws Exception {

		properties.load(new FileReader(new File("src/test/resources/test.properties")));
		// Dont Change below line. Set this value in test.properties file incase you
		// need to change it..
		System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));

		driver = new ChromeDriver();
	}

	@AfterClass
	public void tearDown() throws Exception {
		 driver.quit();
	}

	/*
	 * Please focus on completing the task
	 * 
	 */
	@Test(priority = 0)
	public void testSendEmail() throws Exception {

		String url = "https://mail.google.com/";
		driver.get(url);
		driver.manage().window().maximize();

		WebElement userElement = driver.findElement(By.id("identifierId"));

		userElement.sendKeys(properties.getProperty("username"));

		driver.findElement(By.id("identifierNext")).click();
		Thread.sleep(MIN_WAIT_TIME);
		WebElement passwordElement = driver.findElement(By.xpath("//*[@id='password']/div[1]/div/div[1]/input"));

		passwordElement.sendKeys(properties.getProperty("password"));
		driver.findElement(By.id("passwordNext")).click();

		Thread.sleep(MAX_WAIT_TIME);
		WebElement compose_button = driver.findElement(By.xpath("//div[@class='z0']/div"));
		compose_button.click();

		Thread.sleep(MAX_WAIT_TIME);
		WebElement toElement = driver.findElement(By.xpath("//textarea[@class='vO']"));// To mail
		toElement.sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
		toElement.sendKeys(Keys.TAB);

		String emailSubject = properties.getProperty("email.subject");// Subject
		driver.findElement(By.className("aoT")).sendKeys(emailSubject);

		Thread.sleep(MAX_WAIT_TIME);

		String emailBody = properties.getProperty("email.body"); // Email body
		Thread.sleep(MAX_WAIT_TIME);
		driver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(emailBody);
		driver.findElement(By.xpath("//div[@role='button'][@data-tooltip='More options']//div[@class='J-J5-Ji J-JN-M-I-JG']"))
				.click();
		Thread.sleep(MAX_WAIT_TIME);
		// hover
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath("//div[contains(text(),'Label')]"));

		//
		action.moveToElement(we).build().perform();
		Thread.sleep(MAX_WAIT_TIME);
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//div[@class='J-M-JJ asg']//input[@type='text']")));
		actions.click();
		actions.sendKeys("Social");
		actions.build().perform();
		Thread.sleep(MIN_WAIT_TIME);

		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//div[@role='menuitemcheckbox'][@title='Social']")));
		a.click();
		a.sendKeys("Social");
		a.build().perform();

		Thread.sleep(MAX_WAIT_TIME);
		a.moveToElement(driver.findElement(By.xpath("//div[contains(text(),'Apply')]")));
		a.click();
		a.build().perform();

		Thread.sleep(MAX_WAIT_TIME);
		driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();
	}

	@Test(dependsOnMethods = { "testSendEmail" }, priority = 1)
	public void verifyReceivedSocialMail() throws InterruptedException {
		Thread.sleep(MAX_WAIT_TIME);
		driver.findElement(By.xpath("//div[contains(text(),'Social')]")).click();
		Thread.sleep(MAX_WAIT_TIME);

		By by = By.xpath("//div//span[contains(text(),'" + properties.getProperty("email.subject")
				+ "')]/ancestor::*//tr[@jscontroller='ZdOxDb'][@class='zA zE']");
		List<WebElement> inboxEmails = driver.findElements(by);
		Thread.sleep(MAX_WAIT_TIME);

		System.out.println("inboxEmails : " + inboxEmails);
		String originalSubject = properties.getProperty("email.subject");
		for (WebElement email : inboxEmails) {
			
			if (email != null && email.getText().contains(originalSubject)) {
				System.out.println("email : " + email.getText());
				email.click();

				WebElement subjectElement = ElementHelper.waitUntilVisibilityOf(driver,
						By.xpath("//h2[contains(text(),'" + properties.getProperty("email.subject") + "')]"));
				WebElement bodyElement = ElementHelper.waitUntilVisibilityOf(driver,
						By.xpath("//div[contains(text(),'" + properties.getProperty("email.body") + "')]"));

				subjectElement = driver.findElement(
						By.xpath("//h2[contains(text(),'" + properties.getProperty("email.subject") + "')]"));
				bodyElement = driver.findElement(
						By.xpath("//div[contains(text(),'" + properties.getProperty("email.body") + "')]"));

				System.out.println("Mail Subject : " + subjectElement.getText());
				System.out.println("Mail Content : " + bodyElement.getText());

				System.out.println("Mail Subject from Properties : " + properties.getProperty("email.subject"));
				System.out.println("Mail Content from Properties : " + properties.getProperty("email.body"));

				Assert.assertEquals(properties.getProperty("email.subject"), subjectElement.getText().trim());
				addStar();
				break;
			}
		}

	}

	public void addStar() throws InterruptedException {
		Thread.sleep(MAX_WAIT_TIME);
		WebElement we = driver.findElement(By.xpath("//div[@class=' G-atb D E']//div[@role='button']/span[@class='asa bjy']"));
		Thread.sleep(MAX_WAIT_TIME);
		we.click();
		// hover
		Actions action = new Actions(driver);
		we = driver.findElement(By.xpath("//div[contains(text(),'Add star')]"));
		action.moveToElement(we).click().build().perform();
		Thread.sleep(MAX_WAIT_TIME);
	}

}