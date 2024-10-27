package demo.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LoginPage 
{
	WebDriver driver;
	final static String url = "https://www.saucedemo.com/v1/";
	final static String loginurl="https://www.saucedemo.com/v1/inventory.html";
	@DataProvider(name="loginTestData")
	public Object[][] loginTestData(){
		return new Object[][] {
			//POSITIVE TESTCASE
			{"standard_user", "secret_sauce", true},
			//NEGATIVE TESTCASE
			{"standard_user", "abcd", false},
			{"", "", false},
			{"abcd", "secret_sauce", false},
			{"", "secret_sauce", false},
			{"standard_user", "", false},
			{"standard", "abcd", false}
		};
	}
	@BeforeClass
	public void beforeClass() {
	}
	@BeforeMethod
	public void beforeMethod() {
		ChromeOptions co=new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		driver=new ChromeDriver(co);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	@Test(priority=1)
    public void urlVerify() throws IOException
    {
		driver.get(url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();       
        int responseCode = connection.getResponseCode();
        Assert.assertEquals(responseCode, 200, "Status code is not 200");
    }
	@Test(dataProvider="loginTestData",priority=2)
	public void loginTest(String username, String password, boolean result) {
		try {
			driver.get(url);
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			WebElement userName=driver.findElement(By.xpath("//input[1]"));
			userName.clear();
			userName.sendKeys(username);
			WebElement passWord=driver.findElement(By.xpath("//input[2]"));
			passWord.clear();
			passWord.sendKeys(password);
			WebElement submit=driver.findElement(By.xpath("//input[3]"));
			submit.click();
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	        // Assertion based on expected result
	        if (result) {
	            // Positive case: Check for successful login, assuming "Products" page is displayed
	            String str = driver.getCurrentUrl();
	            Assert.assertEquals(str, loginurl);
	        } else {
	            // Negative case: Check for error message
	            WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));
	            Assert.assertTrue(errorMessage.isDisplayed(), "Login should fail, but it succeeded.");
	        }
		}catch(Exception e) {
			e.printStackTrace();
			Assert.fail("An exception occurred: " + e.getMessage());
		}
	}
	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
	@AfterClass
	public void afterClass() {
	}
}
