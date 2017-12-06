package com.cloud9ner.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cloud9ner.qa.locators.CommonLocators;

public class Institution extends CommonLocators {

    protected WebDriver driver = null;

    // Instance
    public static Institution mInstance;

    public static Institution getInstance(WebDriver driver) {
        return mInstance == null ? mInstance = new Institution(driver) : mInstance;
    }

    public Institution(WebDriver driver) {
        this.driver = driver;
        // input = getClass().getClassLoader().getResourceAsStream("common.properties");
        // try {
        // properties.load(input);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        PageFactory.initElements(driver, this);
    }

    public void createNewInstitution() throws Exception {

        openElementFromTab(driver, phamodisMenu, Institutionselection);
        boolean createinstitutionbutton = checkLocatorPresence(driver, createNewInst);
        compareValue("create new instituton button displaed", true, createinstitutionbutton);
        createNewInst.click();

        boolean institutionmenuapeared = checkLocatorPresence(driver, institutionmenu);
        compareValue("institution menue appeared", true, institutionmenuapeared);
        List<WebElement> institiutionMenueFields = institutionmenu.findElements(By.className("form-group"));
        compareValue("Number of Institution Fields ", 10, institiutionMenueFields.size());

        sendTextToField(driver, InstitutionShortName, "Test Institution ShortName");
        sendTextToField(driver, InstitutionName, "Test Institution Name");
        sendTextToField(driver, institutionDivision, "Test Institution Division");
        sendTextToField(driver, institutionCity, "Test Institution City");
        sendTextToField(driver, institutionStreet, "Test Institution Street");
        sendTextToField(driver, institutionTelephone, "Test Institution Telephone");
        sendTextToField(driver, institutionFax, "Test Institution Fax");

        WebElement menuefooter = driver.findElement(By.className("modal-footer"));
        WebElement savebutton = menuefooter.findElement(By.className("btn-primary"));
        savebutton.click();

        Thread.sleep(5000);
        WebElement firstInstitution = getFirstInstitution(driver);
        String institutionName = firstInstitution.getText();
        boolean institutionAdded = institutionName.contains("Test Institution Name");
        compareValue("Institution added to list", true, institutionAdded);

    }

    public void editInstitution() throws Exception {

        WebElement firstInstitutionline = getFirstInstitution(driver);
        WebElement Editbtn = firstInstitutionline.findElement(By.className("btn-primary"));
        clickButton(Editbtn);
        InstitutionShortName.clear();
        InstitutionName.clear();
        institutionDivision.clear();
        institutionCity.clear();
        institutionStreet.clear();
        institutionTelephone.clear();
        institutionFax.clear();
        sendTextToField(driver, InstitutionShortName, "Edit Test Institution ShortName");
        sendTextToField(driver, InstitutionName, "Edit Test Institution Name");
        sendTextToField(driver, institutionDivision, "Edit Test Institution Division");
        sendTextToField(driver, institutionCity, "Edit Test Institution City");
        sendTextToField(driver, institutionStreet, "Edit Test Institution Street");
        sendTextToField(driver, institutionTelephone, "Edit Test Institution Telephone");
        sendTextToField(driver, institutionFax, "Edit Test Institution Fax");
        WebElement saveButton = authorDialogue.findElement(By.className("btn-primary"));
        clickButton(saveButton);

        Thread.sleep(5000);
        WebElement editFirst = getFirstInstitution(driver);
        String editedInstitutionName = editFirst.getText();
        boolean Institutionedited = editedInstitutionName.contains("Edit Test Institution Name");
        compareValue("author edited success", true, Institutionedited);

    }

    public void deleteFirstInstitution() throws Exception {

        WebElement firstInstitutionline = getFirstInstitution(driver);
        String institutionName = firstInstitutionline.getText();
        boolean institutiondelete = institutionName.contains("Edit Test Institution Name");
        compareValue("Institution added to list", true, institutiondelete);

        WebElement deleteButton = firstInstitutionline.findElement(By.className("btn-danger"));
        deleteButton.click();
        Thread.sleep(2000);
        waitForVisibilityOf(driver, driver.findElement(By.className("modal-footer")));
        WebElement footer = driver.findElement(By.className("modal-footer"));
        WebElement confirmDelete = footer.findElement(By.className("btn-danger"));
        confirmDelete.click();

        Thread.sleep(5000);
        WebElement secondInstitutionline = getFirstInstitution(driver);
        String institutionName2 = secondInstitutionline.getText();
        boolean institutiondeleted = institutionName2.contains("Edit Test Institution Name");
        compareValue("Institution added to list", false, institutiondeleted);
    }

}
