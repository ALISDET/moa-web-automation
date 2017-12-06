package com.cloud9ner.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.cloud9ner.qa.locators.CommonLocators;

public class LoginPage extends CommonLocators {

    protected WebDriver driver = null;

    // Instance
    public static LoginPage mInstance;

    public static LoginPage getInstance(WebDriver driver) {
        return mInstance == null ? mInstance = new LoginPage(driver) : mInstance;
    }

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // input = getClass().getClassLoader().getResourceAsStream("common.properties");
        // try {
        // properties.load(input);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        PageFactory.initElements(driver, this);
    }

    public void successfulLogin() throws Exception {
        waitForVisibilityOf(driver, username);
        sendTextToField(driver, username, "admin");
        sendTextToField(driver, password, "admin");
        waitForVisibilityOf(driver, logInBtn);
        logInBtn.click();
        boolean navigationBar = checkLocatorPresence(driver, navBar);
        compareValue("Login Successfull", true, navigationBar);

    }

}
