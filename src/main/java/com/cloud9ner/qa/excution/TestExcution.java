package com.cloud9ner.qa.excution;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cloud9ner.qa.pages.Author;
import com.cloud9ner.qa.pages.Institution;
import com.cloud9ner.qa.pages.LoginPage;
import com.cloud9ner.qa.setup.AbstractSeleniumSetup;

public class TestExcution extends AbstractSeleniumSetup {

    protected WebDriver driver;

    protected String baseUrl;

    @Override
    @BeforeClass(alwaysRun = true)
    protected void setUp() {
        super.setUp();

        baseUrl = properties.getProperty("baseUrl");
        try {
            openBaseUrl(baseUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver = getDriver();

    }

    @Test(priority = 1, enabled = true)
    public void successfulLogin() throws Exception {

        LoginPage.getInstance(driver).successfulLogin();
    }

    @Test(priority = 2, enabled = true, dependsOnMethods = { "successfulLogin" })
    public void createAuthor() throws Exception {

        Author.getInstance(driver).createNewAuthor();
    }

    @Test(priority = 3, enabled = true, dependsOnMethods = { "createAuthor" })
    public void editAuthor() throws Exception {

        Author.getInstance(driver).editAuthor();
    }

    @Test(priority = 3, enabled = true, dependsOnMethods = { "editAuthor" })
    public void deleteAuthor() throws Exception {

        Author.getInstance(driver).deleteAuthor();
    }

    @Test(priority = 4, enabled = true, dependsOnMethods = { "successfulLogin" })
    public void createNewInstitution() throws Exception {

        Institution.getInstance(driver).createNewInstitution();
    }

    @Test(priority = 5, enabled = true, dependsOnMethods = { "createNewInstitution" })
    public void editInstitution() throws Exception {

        Institution.getInstance(driver).editInstitution();
    }

    @Test(priority = 6, enabled = true, dependsOnMethods = { "editInstitution" })
    public void deleteInstitution() throws Exception {

        Institution.getInstance(driver).deleteFirstInstitution();
    }

    @Override
    @AfterClass(alwaysRun = true)
    protected void tearDown() throws Exception {
        super.tearDown();
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
