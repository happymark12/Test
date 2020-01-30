package demoJenkins;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Admin
 *
 */
public class ReadWriteExcel
{
	WebDriver driver;
	WebDriverWait wait;
	Workbook workbook;
	Sheet sheet;
	Cell cell;

 @BeforeTest
 public void TestSetup()
{
	 File file = new File("driver\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();
	
	// Enter url.
	driver.get("http://www.linkedin.com/");
	driver.manage().window().maximize();
	
	wait = new WebDriverWait(driver,30);
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
}
 
 @Test
 public void ReadData() throws IOException, InvalidFormatException
 {
	 // Import excel sheet.
	 File src=new File("data\\TestData.xlsx");
	 
	
	 // Load he workbook.
	 workbook = WorkbookFactory.create(src);
	 FileOutputStream fileOutput =null;
     // Load the sheet in which data is stored.
	 sheet= workbook.getSheetAt(0);
	 
	 for(int i=1; i<=sheet.getLastRowNum(); i++)
	 {
		 // Import data for Email.
		 cell = sheet.getRow(i).getCell(1);
		 cell.setCellType(CellType.STRING);
		 driver.findElement(By.name("session_key")).sendKeys(cell.getStringCellValue());
		 
		 // Import data for password.
		 cell = sheet.getRow(i).getCell(2);
		 cell.setCellType(CellType.STRING);
		 driver.findElement(By.name("session_password")).sendKeys(cell.getStringCellValue());
		 
		 // Write data in the excel.
	    fileOutput=new FileOutputStream(src,true);
		
		// Specify the message needs to be written.
		String message = "Data Imported Successfully.";
		
		// Create cell where data needs to be written.
		CellUtil.getCell(sheet.getRow(i), 3).setCellValue(message);
//		((XSSFSheet)sheet).getRow(i).createCell(4)
		 
	    // finally write content
	    workbook.write(fileOutput);
		
	    	
	 }
	 workbook.close();
     // close the file
     fileOutput.close();
	 driver.close();
 } 
}