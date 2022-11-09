package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import supports.WebUI;
import java.io.File;

public class BaseTest {
    public WebDriver driver;
    public WebUI webUI;

    @Parameters({"BrowserName"})
    @BeforeClass
    public void createDriver(@Optional("chrome") String browserName) {
        driver = createBrowser(browserName);
        webUI = new WebUI(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriver createBrowser(String browserName) {
        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                System.out.println("Started Chrome Browser: " + driver);
                driver.manage().window().maximize();
                break;
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                System.out.println("Started Safari Browser: " + driver);
                driver.manage().window().maximize();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                System.out.println("Started Firefox Browser: " + driver);
                driver.manage().window().maximize();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                System.out.println("Started Chrome Browser with default: " + driver);
                driver.manage().window().maximize();
                break;
        }
        return driver;
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        Thread.sleep(1000);
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                File theDir = new File("./screenshots/");
                if (!theDir.exists()) {
                    theDir.mkdirs();
                }
                FileHandler.copy(source, new File("./Screenshots/" + result.getName() + ".png"));
                saveScreenshot(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES));
                System.out.println("Takes Screenshot: " + result.getName());
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
    }

    public void clear(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.SUCCESS) {
                System.out.println("TEST PASSED");
            } else if (result.getStatus() == ITestResult.FAILURE) {
                System.out.println("TEST FAILED");
//                takeScreenShot(result.getInstance().toString());
                saveScreenshot(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES));
            } else if (result.getStatus() == ITestResult.SKIP) {
                System.out.println("TEST SKIPPED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    @AfterClass
    public void closeDriver() {
        driver.quit();
        System.out.println("Closed Driver");
    }
}
