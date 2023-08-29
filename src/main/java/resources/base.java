package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class base {

	public WebDriver driver;
	public Properties prop;

	/**
	 * @return
	 * @throws IOException
	 */
	public WebDriver initializeDriver() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream("./src/main/java/resources/data.properties");

		prop.load(fis);
		//String browserName = prop.getProperty("browser");
		String browserName = System.getProperty("browser");
		
		System.out.println(browserName);
//mvn clean install test -Dbrowser=chrome
		if (browserName.contains("chrome")) {
			ChromeOptions opt = new ChromeOptions();
			if(browserName.contains("headless")) {
				opt.addArguments("--headless");
			}
			System.setProperty("webdriver.chrome.driver", "./server/chromedriver.exe");
			driver = new ChromeDriver(opt);
			// execute in chrome driver

		} else if (browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "./server/msedgedriver.exe");
			driver = new EdgeDriver();
			// firefox code
		} else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", "./server/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			// IE code
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;

	}

	public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}

}
