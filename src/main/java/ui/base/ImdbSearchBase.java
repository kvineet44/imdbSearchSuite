package ui.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author vineetkumar
 * created 6/12/2020
 * Base class for browser intialiization and constant implementation.
 */

public class ImdbSearchBase implements CONSTANT {
    public static WebDriver driver;
    public static Utility utility = new Utility();
    static Logger logger = LoggerFactory.getLogger(ImdbSearchBase.class);

    /**
     * Method to Start the Browser
     *
     * @param browserName
     */
    public WebDriver startBrowser(String browserName) {
        if (browserName.equalsIgnoreCase(CHROME)) {
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put(NOTIFICATION_SETTING, 2);
            options.setExperimentalOption("prefs", prefs);
            System.setProperty(CHROME_WEBDRIVER, CHROME_DRIVER_PATH);
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase(FIREFOX)) {
            System.setProperty(FIREFOX_WEBDRIVER, FIREFOX_DRIVER_PATH);
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase(IO)) {
            System.setProperty(IO_WEBDRIVER, IO_DRIVER_PATH);
            driver = new InternetExplorerDriver();
        } else
            logger.error("Please Enter a Valid BrowserName");
        setImplicitTimeoutInSeconds(60, TimeUnit.SECONDS);
        maximizeScreen();
        return driver;
    }

    /**
     * Method to set implicitTime and unit
     *
     * @param value
     * @param time
     */
    public static void setImplicitTimeoutInSeconds(int value, TimeUnit time) {
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(value, TimeUnit.SECONDS);
        }
    }

    /**
     * Maximise screen
     */
    public static void maximizeScreen() {
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }

    /**
     * Method to GoTo particular URL
     *
     * @param url
     */
    public void goToURL(String url) {
        driver.get(url);
    }

    /**
     * Method to Scroll
     *
     * @param x
     * @param y
     */
    public void scrollBy(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(" + x + "," + y + ")");
    }

    /**
     * Method to Dismiss Alert
     */
    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    /**
     * Method to Dismiss Alert
     */
    public void acceptAlert() {
        driver.switchTo().alert().dismiss();
    }
}
