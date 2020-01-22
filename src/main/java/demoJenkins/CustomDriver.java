package demoJenkins;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CustomDriver extends ChromeDriver{

	@Override
	public WebElement findElement(By by) {
//	    try {
//	        Thread.sleep(1000);
//	    } catch (InterruptedException e) {
//	        e.printStackTrace();
//	    }
	    return super.findElement(by);
	}

}
