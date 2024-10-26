package demo.saucedemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginPage 
{
	WebDriver driver;
	final static String url = "https://www.saucedemo.com/v1/";
	@BeforeClass
	public void beforeClass() {
		ChromeOptions co=new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		driver=new ChromeDriver(co);
	}
	@BeforeMethod
	public void beforeMethod() {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	@Test(priority=1)
    public void loginPage() throws IOException
    {
		driver.get(url);
		// Verify the status code is 200
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();       
        int responseCode = connection.getResponseCode();
        System.out.println("Status code: " + responseCode);
        Assert.assertEquals(responseCode, 200, "Status code is not 200");
    }
	@AfterMethod
	public void afterMethod() {
		
	}
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
}
