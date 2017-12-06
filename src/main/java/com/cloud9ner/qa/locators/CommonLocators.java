package com.cloud9ner.qa.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cloud9ner.qa.setup.AbstractSeleniumSetup;

public class CommonLocators extends AbstractSeleniumSetup {

    @FindBy(id = "username")
    protected WebElement username;

    @FindBy(id = "password")
    protected WebElement password;

    @FindBy(className = "btn-block")
    protected WebElement logInBtn;

    @FindBy(id = "entity-menu")
    protected WebElement entityMenu;

    @FindBy(id = "navbarResponsive")
    protected WebElement navBar;

    @FindBy(linkText = "Author")
    protected WebElement authorSelection;

    @FindBy(className = "create-author")
    protected WebElement createAuthor;

    @FindBy(name = "editForm")
    protected WebElement authorDialogue;

    @FindBy(id = "field_name")
    protected WebElement authorNameField;

    @FindBy(id = "field_nationalId")
    protected WebElement authorNationalIdField;

    @FindBy(id = "field_workEmail")
    protected WebElement authorWorkEmailField;

    @FindBy(id = "field_personalEmail")
    protected WebElement authorPersonalEmailField;

    @FindBy(id = "field_telephone")
    protected WebElement authorTelephoneField;

    @FindBy(id = "field_mobile")
    protected WebElement authorMobileField;

    @FindBy(id = "field_address")
    protected WebElement authorAddressField;

    @FindBy(id = "field_webSite")
    protected WebElement authorWebsiteField;

    @FindBy(className = "ng-touched")
    protected WebElement workField1Locator;

    @FindBy(id = "phamodis-menu")
    protected WebElement phamodisMenu;

    @FindBy(id = "field_qualification1")
    protected WebElement qualification1;

    @FindBy(id = "field_qualification2")
    protected WebElement qualification2;

    @FindBy(id = "field_qualification3")
    protected WebElement qualification3;

    /*
     * 
     */
    @FindBy(id = "field_institution")
    protected WebElement institutionDropDown;

    @FindBy(linkText = "Institution")
    protected WebElement Institutionselection;

    @FindBy(className = "create-institution")
    protected WebElement createNewInst;

    @FindBy(className = "modal-body")
    protected WebElement institutionmenu;

    @FindBy(id = "field_shortName")
    protected WebElement InstitutionShortName;

    @FindBy(id = "field_name")
    protected WebElement InstitutionName;

    @FindBy(id = "field_division")
    protected WebElement institutionDivision;

    @FindBy(id = "field_city")
    protected WebElement institutionCity;

    @FindBy(id = "field_street")
    protected WebElement institutionStreet;

    @FindBy(id = "field_telephone")
    protected WebElement institutionTelephone;

    @FindBy(id = "field_fax")
    protected WebElement institutionFax;

    @FindBy(name = "editForm")
    protected WebElement institutionDialogue;

}
