package DiscoveryVideo.DiscoveryVideo;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.*;

public class DiscoveryAutomation {

	//driver Object
	WebDriver driver;	
	
	//XPATH
	public String Recommended_Link="//div[contains(text(),'Recommended')]"; 
	public String SectionPath="//div[contains(text(),'Recommended')]/../..//section";
	public String AddToRecommend="//div[contains(text(),'Recommended')]/../..//section//i";
	public String AddedVideos="//h2[contains(text(),'Favorite Shows')]/..//div//section";
	public String RemoveAddedVideos="//h2[contains(text(),'Favorite Shows')]/..//div//section//i";
	
	//calling before the test case
	@BeforeClass
	public void BeforeTest(){
		String path = System.getProperty("user.dir");
		String DriverPath=path+"\\Jars_Driver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver",DriverPath);
		driver =new ChromeDriver();
		driver.navigate().to("https://go.discovery.com/");
		driver.manage().window().maximize();
	}
	@AfterClass
	public void AfterTest() 
	{
		driver.close();
		driver.quit();
	}
	
	public void click(WebElement element)
	{
		try
		{
			element.click();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void ScrollintoElement(WebElement element) throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
	}
	
	@Test
	public void Verify_Video_Addedinto_Favorites() throws InterruptedException {
		WebElement ele= driver.findElement(By.xpath(Recommended_Link));
		ScrollintoElement(ele);
		List<WebElement> VideoList= driver.findElements(By.xpath(AddToRecommend));
		
		for(int i=0 ;i<2;i++) {
			click(VideoList.get(i));
			Thread.sleep(4000);
			boolean ClassName= VideoList.get(i).getAttribute("class").contains("minus");
			Assert.assertTrue(ClassName,"Video is added");	
		}
		driver.navigate().to("https://go.discovery.com/my-videos");
	
		List<WebElement> AddedVideoList= driver.findElements(By.xpath(AddedVideos));
		Assert.assertTrue(AddedVideoList.size() == 2 ,"Two videos are not added");
	}
}
