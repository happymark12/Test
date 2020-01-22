package demoJenkins;



import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginValidation {
	private WebDriver driver;
	
	@BeforeTest
	public void setup() throws InterruptedException {

		// initializing driver variable using ChromeDriver
		
		

		File file = new File("driver\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new CustomDriver();
//		driver = new ChromeDriver();
		// launching website on the browser
		driver.manage().deleteAllCookies();
		driver.get("http://localhost:8081/Website/");
		// maximized the browser window
		driver.manage().window().maximize();
		// 設定TimeoutException以及No Such Element Exception的時間
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}
	
	@DataProvider(name = "Pass")
	public Object[][] createData() {
	 return new Object[][] {
	   { "testtest", "Do!ng123" },
	   { "Anne", "error"},
	 };
	}
	 

	@Parameters({"account","password"})
	@Test(retryAnalyzer = MyRetry.class,priority = 1,description = "Test Case 9-正常案例-登入成功",invocationCount = 1,invocationTimeOut = 120000)
	public void test9(String account,String password) {
		driver.navigate().to("http://localhost:8081/Website/");
		driver.findElement(By.name("userName")).sendKeys(account);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("#login_button > .ui-button-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("welcome")));
		assertTrue(driver.findElements(By.id("welcome")).size()==1);
		
	}
	
	@Test(enabled = true, priority = 2,description = "Test Case 10-異常案例-登入失敗(帳號驗證)")
	public void test10() {

		driver.navigate().to("http://localhost:8081/Website/");
		driver.findElement(By.name("userName")).sendKeys("MacGyver.Lai");
		driver.findElement(By.name("password")).sendKeys("1qaz@WSX");
		driver.findElement(By.cssSelector("#login_button > .ui-button-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "帳號不存在，請確認帳號資料是否正確");
		alert.accept();
	}
	
	@Test(enabled = true, priority = 3,description = "Test Case 11-異常案例-登入失敗(密碼驗證)")
	public void test11() throws Exception {
		
		driver.navigate().to("http://localhost:8081/Website/");
		driver.findElement(By.name("userName")).sendKeys("testtest");
		driver.findElement(By.name("password")).sendKeys("error_password");
		driver.findElement(By.cssSelector("#login_button > .ui-button-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "密碼錯誤，請確認密碼資料是否正確");
		alert.accept();
	}
	
	@Test(enabled = true, priority = 4,description = "Test Case 12-異常案例-登入失敗(必填欄位驗證)")
	public void test12() throws Exception {
		
		driver.navigate().to("http://localhost:8081/Website/");
		driver.findElement(By.cssSelector("#login_button > .ui-button-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "請輸入帳號及密碼");
		alert.accept();
		
	}
	@Test(enabled = true, priority = 5,description = "Test Case 13-正常案例-登出成功")
	public void test13() throws Exception {
		driver.navigate().to("http://localhost:8081/Website/");
		driver.findElement(By.name("userName")).sendKeys("testtest");
		driver.findElement(By.name("password")).sendKeys("Do!ng123");
		driver.findElement(By.cssSelector("#login_button > .ui-button-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("welcome")));
		driver.findElement(By.linkText("登出")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_form")));
	    driver.navigate().back();
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body[contains(.,'Authorization ERROR!!')]")));
	    assertEquals(driver.findElement(By.cssSelector("body")).getText(), "Authorization ERROR!!");
	}
	
	@Test(enabled = true, priority = 6,description = "Test Case 14-executeAsyncScript()")				
	public void test14() throws Exception {	
	        		
	       		
	        //Creating the JavascriptExecutor interface object by Type casting		
	        JavascriptExecutor js = (JavascriptExecutor)driver;		
	       
	          //Set the Script Timeout to 20 seconds		
	          driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);			
	             
	          //Declare and set the start time		
	          long start_time = System.currentTimeMillis();			
	                   
	          driver.findElement(By.name("userName")).sendKeys("testtest");
	          
	          //Call executeAsyncScript() method to wait for 5 seconds		
	          js.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 5000);");			
	          		
	          driver.findElement(By.name("password")).sendKeys("Do!ng123");
	         //Get the difference (currentTime - startTime)  of times.		
	         System.out.println("Passed time: " + (System.currentTimeMillis() - start_time));					
	         
	         driver.get("http://demo.guru99.com/V4/");
	         String DomainName = js.executeScript("return document.domain;").toString();			
	         System.out.println("Domain name of the site = "+DomainName);					
	           		
	         //Fetching the URL of the site. Tostring() change object to name		
	         String url = js.executeScript("return document.URL;").toString();			
	         System.out.println("URL of the site = "+url);					
	           		
	        //Method document.title fetch the Title name of the site. Tostring() change object to name		
	        String TitleName = js.executeScript("return document.title;").toString();			
	        System.out.println("Title of the page = "+TitleName);					

	         		
	       //Navigate to new Page i.e to generate access page. (launch new url)		
	       js.executeScript("window.location = 'http://moneyboats.com/'");
	       
	     
	        //Maximize window		
	        driver.manage().window().maximize();		
	        		
	        //Vertical scroll down by 600  pixels		
	        js.executeScript("window.scrollBy(0,600)");	
	       
	       
	    }	
	
	@Test(enabled = true, priority = 7,description = "Test Case 15-find element")				
	public void test15() throws Exception {	
		
		
		//Creating the JavascriptExecutor interface object by Type casting		
		JavascriptExecutor js = (JavascriptExecutor)driver;		
		//Set the Script Timeout to 20 seconds		
		driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);			
		
		driver.get("https://www.w3schools.com/jsref/dom_obj_document.asp");
		driver.manage().window().maximize();
		
//		String text =driver.findElement(By.id("leftmenuinnerinner")).findElement(By.linkText("removeEventListener()")).getText();
//		String text =driver.findElement(By.linkText("removeEventListener()")).getText();
//		js.executeScript("alert(arguments[0]);", text);

        //Find element by link text and store in variable "Element"        		
        WebElement Element = driver.findElement(By.linkText("removeEventListener()"));

        //This will scroll the page till the element is found		
        js.executeScript("arguments[0].scrollIntoView();", Element);
		
//		String content = driver.findElement(By.cssSelector("html")).getText();
//		
//		String content2 = js.executeScript("return document.getElementsByTagName(\"html\")[0].innerText").toString();
//
//		assertFalse(content.contentEquals(content2));
		
	}		
	
	@AfterTest
	public void teardown() {
		// closes all the browser windows opened by web driver
		driver.quit();
	}
	
	

}
