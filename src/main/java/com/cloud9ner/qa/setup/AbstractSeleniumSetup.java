package com.cloud9ner.qa.setup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class AbstractSeleniumSetup {

    protected Properties properties = new Properties();

    protected InputStream input = getClass().getClassLoader().getResourceAsStream("prop.properties");

    protected WebDriver driver = null;

    private String browser;

    private String browserLanguage;

    public Logger log = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    protected void setUp() {

        try {
            properties.load(input);
            browser = properties.getProperty("browser");
            browserLanguage = properties.getProperty("browserLang");

        } catch (IOException e) {

            e.printStackTrace();
        }

        initDriver(browser, browserLanguage);

    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() throws Exception {
        driver.quit();
        driver = null;
    }

    /**
     * Close browser.
     */
    protected void closeBrowser() throws Exception {
        driver.quit();
        driver = null;
    }

    /**
     * Initialize the selenium driver
     *
     * @param browser - the browser to be used
     * @param browserDefaultLanguage - default language of the browser
     */
    private void initDriver(final String browser, final String browserDefaultLanguage) {

        final String os = System.getProperty("os.name");
        final String userDirectory = System.getProperty("user.dir");

        switch (browser) {

            case "chrome":
                final ChromeOptions chromeOptions = new ChromeOptions();
                final StringBuilder chromeDriverPath = new StringBuilder();
                chromeDriverPath.append(userDirectory);
                chromeDriverPath.append(
                        File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "chromedriver" + File.separator);

                if (os.contains("Windows")) {
                    chromeDriverPath.append("win32" + File.separator + "chromedriver.exe");
                    chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
                } else if (os.contains("Mac OS")) {
                    chromeDriverPath.append("mac64" + File.separator + "chromedriver");
                    chromeOptions.setBinary("/Volumes/Macintosh HD/Applications/Google Chrome.app");
                } else {
                    log.info("No implementation found for Operating System: " + os);
                    return;
                }

                System.setProperty("webdriver.chrome.driver", chromeDriverPath.toString());
                chromeOptions.addArguments("--lang=" + browserDefaultLanguage);

                try {
                    driver = new ChromeDriver(chromeOptions);
                } catch (final Exception e) {
                    log.error(e.getMessage());
                }
                break;

            case "firefox":
            default:
                final StringBuilder geckoDriverPath = new StringBuilder();

                geckoDriverPath.append("src" + File.separator + "main" + File.separator + "resources" + File.separator + "geckodriver" + File.separator);

                if (os.contains("Windows")) {
                    geckoDriverPath.append("win64" + File.separator + "geckodriver.exe");
                } else if (os.contains("Mac OS")) {
                    geckoDriverPath.append("mac64" + File.separator + "geckodriver");
                } else {
                    log.info("No implementation found for Operating System: " + os);
                    return;
                }
                System.setProperty("webdriver.gecko.driver", geckoDriverPath.toString());

                final FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages", browserDefaultLanguage);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip,application/octet-stream");
                profile.setPreference("browser.download.manager.showWhenStarting", false);
                profile.setPreference("browser.tabs.remote.autostart.2", false);

                final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("marionette", true);
                capabilities.setCapability("marionette.logging", "ERROR");
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability(FirefoxDriver.PROFILE, profile);

                try {
                    driver = new FirefoxDriver(capabilities);
                } catch (final Exception e) {
                    log.error(e.getMessage());
                }
                break;
        }

    }

    /**
     * @param baseUrl
     * @return
     * @throws Exception
     */
    public WebDriver openBaseUrl(final String baseUrl) throws Exception {

        if (driver == null) {
            log.info("Driver not initialized.");
            return null;
        }

        driver.get(baseUrl);
        driver.manage().window().maximize();

        if (baseUrl.equals(driver.getCurrentUrl())) {
            log.info("Driver '" + driver.getClass().getName() + "' successfully opened url '" + baseUrl + "' with browser '" + browser + "'");
        } else {

            if (driver.getCurrentUrl() != null) {
                log.warn("Driver '" + driver.getClass().getName() + "' opened url '" + driver.getCurrentUrl() + " instead of requested baseUrl " + baseUrl
                        + "' with browser '" + browser + "'");
            } else {

                throw new Exception("Driver '" + driver.getClass().getName() + "' was unable to open url '" + baseUrl + "' with browser '" + browser + "'");
            }
        }

        return driver;
    }

    /**
     * get the current Selenium web driver
     *
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * @return
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * Uses the Selenium Sleeper and catches exceptions.
     *
     * @param amount
     * @param timeUnit
     */
    public static void sleep(final long amount, final TimeUnit timeUnit) {

        try {
            Sleeper.SYSTEM_SLEEPER.sleep(new Duration(amount, timeUnit));
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Check control presence using it's locator.
     */

    public boolean checkLocatorPresence(WebDriver driver, final By by) {
        try {
            final WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (final TimeoutException e) {
            return false;
        }

        return true;

    }

    /*
     * Check control presence using the webElement class.
     */

    public boolean checkLocatorPresence(WebDriver driver, final WebElement webElement) {
        try {
            final WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOf(webElement));
        } catch (final TimeoutException | NoSuchElementException e) {
            return false;
        }

        return true;

    }

    /*
     * Check control not present using it's locator.
     */

    public boolean checkLocatorNotPresent(WebDriver driver, final By by) {
        try {
            final WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (final TimeoutException e) {
            return false;
        }

        return true;

    }

    /*
     * Check if the URL contains the expected text.
     */
    public boolean checkURL(WebDriver driver, final String expectedText) {
        try {
            final WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.urlContains(expectedText));
        } catch (final TimeoutException e) {
            return false;
        }
        return true;

    }

    /*
     * Click Button By locator
     */
    public void clickButton(WebDriver driver, final By byBtn) throws InterruptedException {

        driver.findElement(byBtn).click();
    }

    /*
     * Click Button By element
     */
    public void clickButton(WebElement webElement) throws InterruptedException {

        webElement.click();
    }

    /**
     * Compares two values. The key is required for the logging.
     *
     * @param key Key, needed for logging
     * @param expected expected value
     * @param current current value
     * @return true when expected matches current
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends Comparable> boolean compareValue(final String key, final T expected, final T current) {

        log.info("Comparing object value of " + key + ". expected: " + expected + ", actual: " + current);

        if (expected == null) {
            if (current != null) {
                log.error(key + " is not null. " + expected + " / " + current);
                Assert.fail(key + " is not null. " + expected + " / " + current);
                return false;

            } else {
                return true;
            }
        }
        if (current == null) {
            log.error(key + " does not match (null). " + expected + " / " + current);
            Assert.fail(key + " does not match (null). " + expected + " / " + current);
            return false;
        }
        if (expected.compareTo(current) != 0) {
            log.error(key + " does not match. " + expected + " / " + current);
            Assert.fail(key + " does not match. " + expected + " / " + current);
            return false;
        }

        return true;
    }

    /**
     * Compares if two values are not equal. The key is required for the logging. When expected is null, the current
     * value must not be null.
     *
     * @param testCase - the testCase used
     * @param key - string used for displaying on console output
     * @param expected - the expected value to check against
     * @param current - the actual value to check against
     * @return true or false
     */
    public boolean compareNotEqual(final String key, final String expected, final String current) {
        log.info("Comparing Not equal of " + key + ". expected: " + expected + ", actual: " + current);
        if (expected == null || expected.equals("null")) {
            if (current == null || current.equals("null")) {
                log.error(key + " is equal, expected: " + expected + " , actual: " + current);
                return false;
            } else {
                return true;
            }
        }

        if (expected.equals(current)) {
            log.error(key + " is equal, expected: " + expected + " , actual: " + current);
            return false;
        }
        return true;
    }

    /* Parent method to wait for a certain view to be visible on screen. */

    public void waitForVisibilityOf(WebDriver driver, WebElement webElement) {
        final WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void sendTextToField(WebDriver driver, WebElement webElement, String inputValue) {
        waitForVisibilityOf(driver, webElement);
        webElement.sendKeys(inputValue);
    }

    public void openElementFromTab(WebDriver driver, WebElement tabParent, WebElement tabChild) throws Exception {
        waitForVisibilityOf(driver, tabParent);
        clickButton(tabParent);
        waitForVisibilityOf(driver, tabChild);
        clickButton(tabChild);

    }

    public WebElement getFirstAuthor(WebDriver driver) throws InterruptedException {
        waitForVisibilityOf(driver, driver.findElement(By.tagName("tbody")));
        WebElement authorListParent = driver.findElement(By.tagName("tbody"));
        List<WebElement> authors = authorListParent.findElements(By.tagName("tr"));
        WebElement firstAuthor = authors.get(0);
        return firstAuthor;
    }

    public WebElement getFirstInstitution(WebDriver driver) throws InterruptedException {
        waitForVisibilityOf(driver, driver.findElement(By.tagName("tbody")));
        WebElement institutionListParent = driver.findElement(By.tagName("tbody"));
        List<WebElement> institutions = institutionListParent.findElements(By.tagName("tr"));
        WebElement FirstInstitution = institutions.get(0);
        return FirstInstitution;
    }

}
