
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class OpenNewTab
{
	static String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	static String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	static String LocalSeleniumServer = "http://localhost:4444/wd/hub";
	static String LocalAppiumServer = "http://localhost:4723/wd/hub";
	static String SauceAppiumServer = "https://" + SAUCE_USERNAME + ":" +SAUCE_ACCESS_KEY + "@ondemand.saucelabs.com/wd/hub";
	static String TestObjectAppiumServer = "https://us1.appium.testobject.com/wd/hub";

	@Test
	public void inSafari_onMac_usingLocalhost() throws Exception
	{
		URL url = new URL(LocalSeleniumServer);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", "safari");
		capabilities.setCapability("safari.options", new SafariOptions());

		RemoteWebDriver driver = new RemoteWebDriver(url, capabilities);

		switchWindows(driver);

		driver.quit();
	}

	@Test
	public void inSafari_onIPad_usingLocalhost() throws Exception
	{
		URL url = new URL(LocalAppiumServer);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "11.4");
		capabilities.setCapability("deviceName", "iPad Pro (12.9-inch) (2nd generation)");
		capabilities.setCapability("browserName", "safari");
		capabilities.setCapability("nativeWebTap", true);

		RemoteWebDriver driver = new IOSDriver<WebElement>(url, capabilities);

		switchWindows(driver);

		driver.quit();
	}

	@Test
	public void inSafari_onMac_usingSauceLabs() throws Exception
	{
		URL url = new URL(SauceAppiumServer);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "MacOS 10.13");
		capabilities.setCapability("browserName", "safari");
		capabilities.setCapability("name", getTestName());
		RemoteWebDriver driver = new RemoteWebDriver(url, capabilities);

		switchWindows(driver);

		driver.quit();
	}

	@Test
	public void inSafari_onIPad_usingSauceLabs() throws Exception
	{
		URL url = new URL(SauceAppiumServer);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("appiumVersion", "1.8.1");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion","11.3");
		capabilities.setCapability("deviceName","iPad Pro (12.9 inch) (2nd generation) Simulator");
		capabilities.setCapability("deviceOrientation", "portrait");
		capabilities.setCapability("nativeWebTap", true); // without nativeWebTab it won't click, but while it clicks, it still won't switch
		capabilities.setCapability("browserName", "Safari");
		capabilities.setCapability("name", getTestName());

		RemoteWebDriver driver = new IOSDriver<WebElement>(url, capabilities);

		switchWindows(driver);

		driver.quit();
	}

	public void switchWindows(RemoteWebDriver driver)
	{
		System.out.println("----- open page: ");
		driver.get("https://sauceaaron.github.io/open-new-tab/");
		sleep(10);

		System.out.println(driver.getWindowHandles());
		System.out.println(driver.getWindowHandle());
		System.out.println(driver.getTitle());

		System.out.println("----- click on link: ");
		driver.findElementByTagName("a").click();
		sleep(10);

		System.out.println(driver.getWindowHandles());
		System.out.println(driver.getWindowHandle());
		System.out.println(driver.getTitle());

		driver.getWindowHandles().forEach( handle -> {
			System.out.println("----- switch to: ");
			driver.switchTo().window(handle);
			sleep(10);

			System.out.println(driver.getWindowHandles());
			System.out.println(driver.getWindowHandle());
			System.out.println(driver.getTitle());
		});
	}

	public String getTestName()
	{
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String classname = element.getClassName();
		String methodname = element.getMethodName();

		return classname + "." + methodname;
	}

	public void sleep(int seconds)
	{
		try
		{
			Thread.sleep(seconds * 1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
