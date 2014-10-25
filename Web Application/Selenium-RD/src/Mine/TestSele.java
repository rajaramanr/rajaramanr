package Mine;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


public class TestSele
{
	WebDriver driver = new ChromeDriver();
	
	@Test
	public void TestMethod() throws InterruptedException, IOException
	{
		driver.get("http://google.com");
		WebElement element = driver.findElement(By.id("gbqfq"));
		element.sendKeys("Kala");
		WebElement submit = driver.findElement(By.id("gbqfb"));
		submit.submit();
		Thread.sleep(2000);
		WebElement elements = driver.findElement(By.partialLinkText("Wiki"));
		elements.click();
	}
	
	@Test
	public void TestMethod2() throws InterruptedException, IOException
	{
		driver.get("http://gmail.com");
		driver.close();
	}
}