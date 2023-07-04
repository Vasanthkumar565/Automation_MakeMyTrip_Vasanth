package com.qa.makemytrip;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.event.MenuKeyEvent;

import org.aspectj.lang.annotation.After;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Holidays_packages {
	WebDriver driver;
	JavascriptExecutor js;
	Actions act;
	

	String st1 = "https://www.makemytrip.com/";

	@BeforeTest
	public void Set_up() {

		WebDriverManager.chromedriver().setup();
		ChromeOptions op = new ChromeOptions();
		op.addArguments("--disable-notifications");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get(st1);
		js = (JavascriptExecutor) driver;
		act = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(priority = 0)
	public void departure() throws InterruptedException, AWTException {

		driver.findElement(By.xpath("(//a[@class='makeFlex hrtlCenter column'])[3]")).click();
		driver.findElement(By.id("fromCity")).click();

		// dropdown to enter city departure
		List<WebElement> options = driver.findElements(By.xpath("//*[@class='autoSuggest_list']/ul/li"));

		String Suggestedname = "Bangalore";
		for (WebElement option : options) {
			String optionName = option.getText();
			if (optionName.equals(Suggestedname)) {
				option.click();
				break;
			}
		}

		// destination place selector
		driver.findElement(By.xpath("(//*[@class='appendBottom5 field-label '])[2]")).click();

		driver.findElement(By.xpath("(//*[@class='dest-search-input'])")).sendKeys("Singapore");
		Thread.sleep(3000);
		WebElement sing = driver.findElement(By.xpath("(//*[contains(text(),'Singapore')])[1]"));
		act.click(sing).build().perform();

		// datapicker on available data only
		List<WebElement> totaldatanum = driver.findElements(By.xpath("(//*[@class='DayPicker-Day'])/div/p"));

		String dataname = "5";
		for (WebElement dataselect : totaldatanum) {
			String storedata = dataselect.getText();
			if (storedata.equals(dataname)) {
				dataselect.click();
				break;
			}
		}
		// clicking on room applybtn
		driver.findElement(By.xpath("//*[contains(text(),'APPLY')]")).click();

		/*
		 * Thread.sleep(5000); WebElement drag1 =driver.findElement(By.
		 * xpath("(//*[@class='rc-slider-handle rc-slider-handle-2'])"));
		 * Thread.sleep(5000);
		 * 
		 * Actions act = new Actions(driver); Thread.sleep(3000);
		 * 
		 * act.dragAndDropBy(drag1, -500, 0).perform(); Thread.sleep(5000);
		 */

		// scrolling because of chat popup

		WebElement applybtn = driver.findElement(By.xpath("//*[contains(text(),'Hotel Category')]"));
		js.executeScript("arguments[0].scrollIntoView();", applybtn);
		Thread.sleep(3000);

		// clicking on filters

		driver.findElement(By.xpath("//*[contains(text(),'APPLY')]")).click();

		// scrolling back to search btn
		Thread.sleep(3000);
		WebElement applybtn2 = driver.findElement(By.xpath("(//*[contains(text(),'Destinations')])[1]"));
		js.executeScript("arguments[0].scrollIntoView();", applybtn2);
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[@id='search_button'])")).click();

	}

	@Test (priority = 1)
	public void pop_up_handle() throws InterruptedException {

		// clicking on popup skip btn
		driver.findElement(By.xpath("(//*[contains(text(),'SKIP')])")).click();

		// clicking on chatwith us form
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='close closeIcon'])")));
		element.click();

		Thread.sleep(3000);
		WebElement roomview = driver.findElement(By.xpath("(//*[contains(text(),'Specially curated Packages ')])"));
		js.executeScript("arguments[0].scrollIntoView();", roomview);
		Thread.sleep(3000);

		WebElement clickonhotel = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[1]/div/div[3]/div/div[2]/div/div/div/div/div[2]/div/div/div"));
		clickonhotel.click();

		// Switch to the new tab
		String currentWindowHandle = driver.getWindowHandle();
		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(currentWindowHandle)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
	}

	@Test(priority = 2)
	public void hotel_room_booking() throws InterruptedException {

		driver.findElement(By.xpath("(//*[contains(text(),'SKIP')])")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[@class='initerary-nav'])/li[2]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[@class='hotel-row-btns'])/span")).click();

		Thread.sleep(15000);
		WebElement changehotel2 = driver.findElement(By.xpath("//*[contains(text(),'Resorts World Sentosa - Genting Hotel Jurong')]"));
		js.executeScript("arguments[0].scrollIntoView();", changehotel2);
		Thread.sleep(3000);
	}

	@Test(priority = 3)
	public void change_room_booking() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		System.out.println(js.executeScript("return document.getElementsByClassName('hotelHeading')[9].innerText"));

		driver.findElement(By.xpath("(//*[contains(text(),'Select')])[14]")).click();

		driver.findElement(By.xpath("(//*[contains(text(),'Update Package')])")).click();

		WebElement valiadte = driver.findElement(By.xpath("(//*[contains(text(),'Park Regis Singapore')])"));
		String actualText = valiadte.getText();
		System.out.println(actualText);
		String expectedText = "Park Regis Singapore";

		Assert.assertEquals(actualText, expectedText, "Changed hotel is mismatch");

	}

	@Test(priority = 4)
	public void Add_activity() throws InterruptedException {

		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[@class='initerary-nav'])/li[4]")).click();
		
		
		Thread.sleep(3000);
		WebElement activity = driver.findElement(By.xpath("(//*[contains(text(),'Day 3')])"));
		js.executeScript("arguments[0].scrollIntoView();", activity);
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[contains(text(),'ADD ACTIVITY TO DAY')])[1]")).click();
		
		Thread.sleep(3000);
		WebElement exactactivity = driver.findElement(By.xpath("(//*[contains(text(),'River Wonders Tour- Shared Transfers')])"));

		js.executeScript("arguments[0].scrollIntoView();", exactactivity);
		Thread.sleep(3000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		System.out.println(js.executeScript("return document.getElementsByClassName('flex column')[5].innerText"));
		
		
		driver.findElement(By.xpath("(//*[contains(text(),'Select')])[8]")).click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[contains(text(),'Update Package')])")).click();
		
	}

	@Test(priority = 5)
	public void Activity_validate() throws InterruptedException

	{
		WebElement valiadte = driver.findElement(By.xpath("(//*[contains(text(),'Night Safari- Shared Transfers')])"));
		String actualText = valiadte.getText();
		System.out.println(actualText);
		String expectedText = "Night Safari- Shared Transfers";

		Assert.assertEquals(actualText, expectedText, "Activity is mismatch");
	}

	@Test(priority = 6)
	public void Selected_hotal_and_Activity_validate() throws InterruptedException 
	{
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[@class='initerary-nav'])/li[1]")).click();

		
		// Day1
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[contains(text(),'Night Safari- Shared Transfers')])")).click();
		WebElement valiadte = driver.findElement(By.xpath("(//*[contains(text(),'Night Safari- Shared Transfers')])"));
		String actualText = valiadte.getText();
		System.out.println(actualText);
		String expectedText = "Night Safari- Shared Transfers";

		Assert.assertEquals(actualText, expectedText, "Activity is mismatch");
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//*[@class='close'])")).click();
		// Day2

		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[contains(text(),'Guided City Tour- Shared Transfers')])")).click();
		WebElement valiadte1 = driver.findElement(By.xpath("(//*[contains(text(),'Guided City Tour- Shared Transfers')])"));
		String actualText1 = valiadte1.getText();
		System.out.println(actualText1);
		String expectedText1 = "Guided City Tour- Shared Transfers";

		Assert.assertEquals(actualText1, expectedText1, "Activity is mismatch");
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//*[@class='close'])")).click();

		// Day3
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[contains(text(),'Guided Sentosa Experience- Shared Transfers')])")).click();
		WebElement valiadte2 = driver.findElement(By.xpath("(//*[contains(text(),'Guided Sentosa Experience- Shared Transfers')])"));
		String actualText2 = valiadte2.getText();
		System.out.println(actualText2);
		String expectedText2 = "Guided Sentosa Experience- Shared Transfers";

		Assert.assertEquals(actualText2, expectedText2, "Activity is mismatch");
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//*[@class='close'])")).click();
		
		
	}
@AfterTest
public void end_test()
{
	driver.quit();
}

}
