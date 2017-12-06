package com.cloud9ner.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.cloud9ner.qa.locators.CommonLocators;

public class Author extends CommonLocators {

    protected WebDriver driver = null;

    // Instance
    public static Author mInstance;

    public static Author getInstance(WebDriver driver) {
        return mInstance == null ? mInstance = new Author(driver) : mInstance;
    }

    public Author(WebDriver driver) {
        this.driver = driver;
        // input = getClass().getClassLoader().getResourceAsStream("common.properties");
        // try {
        // properties.load(input);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        PageFactory.initElements(driver, this);
    }

    public void createNewAuthor() throws Exception {

        openElementFromTab(driver, phamodisMenu, authorSelection);
        boolean createAuthorButton = checkLocatorPresence(driver, createAuthor);
        compareValue("Create New Author button visibility", true, createAuthorButton);
        createAuthor.click();
        boolean authorDialogueDisplayed = checkLocatorPresence(driver, authorDialogue);
        compareValue("Create New Author Dialogue Displayed", true, authorDialogueDisplayed);

        List<WebElement> dialogueFields = authorDialogue.findElements(By.className("form-group"));
        compareValue("Number Of Author Fields", 13, dialogueFields.size());

        sendTextToField(driver, authorNameField, "Automation Test");
        sendTextToField(driver, authorNationalIdField, "11111111");
        sendTextToField(driver, authorWorkEmailField, "a@a.com");
        sendTextToField(driver, authorPersonalEmailField, "a@a.com");
        sendTextToField(driver, authorTelephoneField, "01000000");
        sendTextToField(driver, authorMobileField, "01000000");
        sendTextToField(driver, authorAddressField, "Test Adsress");
        sendTextToField(driver, authorWebsiteField, "Test Website");
        sendTextToField(driver, qualification1, "Test qualification");
        sendTextToField(driver, qualification2, "Test qualification");
        sendTextToField(driver, qualification3, "Test qualification");

        List<WebElement> DropDowns = driver.findElements(By.tagName("select"));
        Select workField1 = new Select(DropDowns.get(0));
        workField1.selectByIndex(1);

        Select institutionSellect = new Select(institutionDropDown);
        institutionSellect.selectByIndex(1);

        WebElement saveButton = authorDialogue.findElement(By.className("btn-primary"));
        clickButton(saveButton);

        Thread.sleep(5000);
        WebElement lastAuthor = getFirstAuthor(driver);
        String authorName = lastAuthor.getText();
        boolean authorAdded = authorName.contains("Automation Test");
        compareValue("Author added to list", true, authorAdded);

    }

    public void editAuthor() throws Exception {

        waitForVisibilityOf(driver, driver.findElement(By.tagName("tbody")));
        WebElement firstAuthorline = getFirstAuthor(driver);
        WebElement EditBtn = firstAuthorline.findElement(By.className("btn-primary"));
        clickButton(EditBtn);
        waitForVisibilityOf(driver, authorNameField);
        authorNameField.clear();
        authorNationalIdField.clear();
        authorWorkEmailField.clear();
        authorPersonalEmailField.clear();
        authorTelephoneField.clear();
        authorMobileField.clear();
        authorAddressField.clear();
        authorWebsiteField.clear();
        sendTextToField(driver, authorNameField, "Edit test");
        sendTextToField(driver, authorNationalIdField, "12345678");
        sendTextToField(driver, authorWorkEmailField, "Edit@test.com");
        sendTextToField(driver, authorPersonalEmailField, "Edit@test.com");
        sendTextToField(driver, authorTelephoneField, "12345678");
        sendTextToField(driver, authorMobileField, "12345678");
        sendTextToField(driver, authorAddressField, "Edit Address");
        sendTextToField(driver, authorWebsiteField, "Edit Website");
        WebElement saveButton = authorDialogue.findElement(By.className("btn-primary"));
        clickButton(saveButton);

        Thread.sleep(5000);
        WebElement editlast = getFirstAuthor(driver);
        String editedAuthorName = editlast.getText();
        boolean authoredited = editedAuthorName.contains("Edit test");
        compareValue("author edited success", true, authoredited);

    }

    public void deleteAuthor() throws Exception {

        waitForVisibilityOf(driver, driver.findElement(By.tagName("tbody")));
        WebElement firstAuthorline = getFirstAuthor(driver);
        WebElement deleteBtn = firstAuthorline.findElement(By.className("fa-remove"));
        clickButton(deleteBtn);

        Thread.sleep(2000);
        waitForVisibilityOf(driver, driver.findElement(By.className("modal-body")));
        WebElement deleteBody = driver.findElement(By.className("modal-body"));
        boolean deleteHeader = deleteBody.getText().contains("Edit test");
        compareValue("deleting new added user", true, deleteHeader);
        WebElement btnParent = driver.findElement(By.className("modal-footer"));
        clickButton(btnParent.findElement(By.className("btn-danger")));

        Thread.sleep(5000);
        waitForVisibilityOf(driver, driver.findElement(By.tagName("tbody")));
        WebElement firstAuthorlineAfterDelete = getFirstAuthor(driver);
        String editedAuthorName = firstAuthorlineAfterDelete.getText();
        boolean authoredited = editedAuthorName.contains("Edit test");
        compareValue("author edited success", false, authoredited);

    }

}
