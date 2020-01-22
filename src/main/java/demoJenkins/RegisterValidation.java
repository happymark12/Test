package demoJenkins;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterValidation {
	private WebDriver driver;

	@BeforeClass
	public void setup() throws InterruptedException {

		// initializing driver variable using ChromeDriver
		File file = new File("driver\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();
		// launching website on the browser
		driver.get("http://localhost:8081/Website/");
		// maximized the browser window
		driver.manage().window().maximize();
		// 設定TimeoutException以及No Such Element Exception的時間
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	@Test(enabled = true, priority = 1,description = "Test Case 1-註冊新使用者成功")
	public void test1() {

		driver.findElement(By.cssSelector("#reg_button > .ui-button-text")).click();
		driver.findElement(By.id("account")).sendKeys("Cindy.Li");
		driver.findElement(By.id("dialogPassword")).sendKeys("1qaz@WSX");
		driver.findElement(By.id("dialogPassword2")).sendKeys("1qaz@WSX");
		driver.findElement(By.id("dialogName")).sendKeys("李淑芬");
		driver.findElement(By.id("birth")).click();

		Select dropdown = new Select(driver.findElement(By.cssSelector(".ui-datepicker-year")));
		dropdown.selectByValue("1912");
		driver.findElement(By.linkText("12")).click();
		driver.findElement(By.id("phone")).click();
		driver.findElement(By.id("phone")).sendKeys("0975312459");
		driver.findElement(By.xpath("//div/button/span")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "註冊成功");
		alert.accept();
	}
	
	@Test(enabled = true, priority = 2,description = "Test Case 2-異常案例-註冊新使用者(必填欄位驗證) 當所有欄位為空白時的狀況")
	public void test2() {
		//點擊新使用者
		driver.findElement(By.cssSelector("#reg_button > .ui-button-text")).click();
		driver.findElement(By.xpath("//div/button/span")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "註冊失敗，帳號欄位不可為空白");
		alert.accept();
		driver.findElement(By.xpath("//span[contains(.,'close')]")).click();
		
	}
	
	@Test(enabled = true, priority = 3,description = "Test Case 3-異常案例-註冊新使用者(密碼強度驗證)")
	public void test3() throws Exception {
		//點擊新使用者
		driver.findElement(By.cssSelector("#reg_button > .ui-button-text")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		driver.findElement(By.id("account")).sendKeys("Cindy.Li1");
		driver.findElement(By.id("dialogPassword")).sendKeys("27228822");
		driver.findElement(By.id("dialogPassword2")).sendKeys("27228822");
		driver.findElement(By.id("dialogName")).sendKeys("李淑芬");
		driver.findElement(By.id("birth")).click();
		Select dropdown = new Select(driver.findElement(By.cssSelector(".ui-datepicker-year")));
		dropdown.selectByValue("1985");
		driver.findElement(By.linkText("12")).click();
		driver.findElement(By.id("phone")).click();
		driver.findElement(By.id("phone")).sendKeys("0926089653");
		driver.findElement(By.xpath("//div/button/span")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "註冊失敗，密碼長度應為8碼(含)以上，並應包含英文大寫、小寫、數字及特殊符號");
		alert.accept();
		driver.findElement(By.xpath("//span[contains(.,'close')]")).click();

	}
	
	@Test(enabled = true, priority = 4,description = "Test Case-4  異常案例-註冊新使用者(再次輸入密碼錯誤)")
	public void test4() throws Exception {
		//點擊新使用者
		driver.findElement(By.cssSelector("#reg_button > .ui-button-text")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		driver.findElement(By.id("account")).sendKeys("Cindy.Li1");
		driver.findElement(By.id("dialogPassword")).sendKeys("1qaz@WSX");
		driver.findElement(By.id("dialogPassword2")).sendKeys("2wsx#EDC");
		driver.findElement(By.id("dialogName")).sendKeys("李淑芬");
		driver.findElement(By.id("birth")).click();
		Select dropdown = new Select(driver.findElement(By.cssSelector(".ui-datepicker-year")));
		dropdown.selectByValue("1985");
		driver.findElement(By.linkText("1")).click();
		driver.findElement(By.id("phone")).click();
		driver.findElement(By.id("phone")).sendKeys("0926089653");
		driver.findElement(By.xpath("//div/button/span")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "註冊失敗，必須輸入和密碼欄相同的密碼");
		alert.accept();
		driver.findElement(By.xpath("//span[contains(.,'close')]")).click();
		
		
	}
	@Test(enabled = true, priority = 5,description = "Test Case 5-異常案例-註冊新使用者(帳號格式驗證)")
	public void test5() throws Exception {
		//點擊新使用者
		driver.findElement(By.cssSelector("#reg_button > .ui-button-text")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		driver.findElement(By.id("account")).sendKeys("test");
		driver.findElement(By.xpath("//div/button/span")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		
		assertEquals(alert.getText(), "註冊失敗，帳號長度應為8碼(含)以上，可包含英文大寫、小寫、數字及特殊符號");
		alert.accept();
		driver.findElement(By.xpath("//span[contains(.,'close')]")).click();

		
	}
	@Test(enabled = true, priority = 6,description = "Test Case 6-異常案例-註冊新使用者(姓名格式驗證)不可超過10個中文字")
	public void test6() throws Exception {
		//點擊新使用者
		driver.findElement(By.cssSelector("#reg_button > .ui-button-text")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		driver.findElement(By.id("account")).sendKeys("Cindy.Li1");
		driver.findElement(By.id("dialogPassword")).sendKeys("Do!ng123");
		driver.findElement(By.id("dialogPassword2")).sendKeys("Do!ng123");
		driver.findElement(By.id("dialogName")).sendKeys("李淑芬李淑芬李淑芬李淑芬");
		driver.findElement(By.xpath("//div/button/span")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "註冊失敗，姓名長度不可超過10個字");
		alert.accept();
		driver.findElement(By.xpath("//span[contains(.,'close')]")).click();
		
		
	}
	@Test(enabled = true, priority = 7,description = "Test Case 7-異常案例-註冊新使用者(帳號重複驗證)")
	public void test7() throws Exception {
		//點擊新使用者
		driver.findElement(By.cssSelector("#reg_button > .ui-button-text")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		driver.findElement(By.id("account")).sendKeys("testtest");
		driver.findElement(By.xpath("//div/button/span")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "註冊失敗，已有相同帳號存在，請以其他帳號進行註冊");
		alert.accept();
		driver.findElement(By.xpath("//span[contains(.,'close')]")).click();
		
		
	}
	
	@Test(enabled = true, priority = 8,description = "Test Case 8-正常案例-註冊新使用者(資料重置功能驗證)")
	public void test8() {
		
		driver.findElement(By.cssSelector("#reg_button > .ui-button-text")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		driver.findElement(By.id("account")).sendKeys("Cindy.Li");
		driver.findElement(By.id("dialogPassword")).sendKeys("Do!ng123");
		driver.findElement(By.id("dialogPassword2")).sendKeys("Do!ng123");
		driver.findElement(By.id("dialogName")).sendKeys("李淑芬");
		driver.findElement(By.id("birth")).click();

		Select dropdown = new Select(driver.findElement(By.cssSelector(".ui-datepicker-year")));
		dropdown.selectByValue("1912");
		driver.findElement(By.linkText("12")).click();
		driver.findElement(By.id("phone")).click();
		driver.findElement(By.id("phone")).sendKeys("0975312459");
		driver.findElement(By.xpath("//button[2]/span")).click();
		assertTrue(driver.findElement(By.id("account")).getText().length()==0);
		assertTrue(driver.findElement(By.id("dialogPassword")).getText().length()==0);
		assertTrue(driver.findElement(By.id("dialogPassword2")).getText().length()==0);
		assertTrue(driver.findElement(By.id("dialogName")).getText().length()==0);
		assertTrue(driver.findElement(By.id("birth")).getText().length()==0);
		assertTrue(driver.findElement(By.id("phone")).getText().length()==0);
		
		driver.findElement(By.xpath("//span[contains(.,'close')]")).click();
	}
	
	@AfterClass
	public void teardown() {
		// closes all the browser windows opened by web driver
		driver.quit();
	}
	
	
	
}
