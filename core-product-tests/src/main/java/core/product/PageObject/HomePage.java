package core.product.PageObject;

import automation.framework.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    @FindBy(xpath = "//button[text()='I Accept']")
    public WebElement acceptCookieBtn;

    @FindBy(xpath = "//div[text()='x']")
    public WebElement closeSignUpPopUpBtn;

    @FindBy(xpath = "//ul[@role='menubar']//a//span[text()='Shop']")
    public WebElement shopMenuLink;

    @FindBy(xpath = "//ul[@role='menubar']//a[@title=\"Men's\"]")
    public WebElement menSectionLink;

    @FindBy(xpath = "//li[@class='menu-item']//a//span[text()='...']")
    public WebElement ThreeDotMenuLink;

    @FindBy(xpath = "(//ul[@role='menu']//li[contains(.,'News & Features')])[1]")
    public WebElement newsAndFeatureLink;




    Config testConfig;
    WebDriverWait wait;
    long timeout;
    public HomePage(Config testConf){
        this.testConfig = testConf;
        PageFactory.initElements(testConfig.driver, this);
        timeout = Long.parseLong(testConfig.runTimeProperties.get("defaultTimeOut").toString());
        wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOf(shopMenuLink));

        try{
            wait.until(ExpectedConditions.visibilityOf(closeSignUpPopUpBtn));
            closeSignUpPopUpBtn.click();
            wait.until(ExpectedConditions.visibilityOf(acceptCookieBtn));
            acceptCookieBtn.click();

        } catch (NoSuchElementException e){
            try {
                wait.until(ExpectedConditions.visibilityOf(acceptCookieBtn));
                acceptCookieBtn.click();

            } catch (NoSuchElementException ex){

            }
        }
    }

    /**
     * Method to hover over Shop Menu
     */
    public void hoverOverShopMenu(){
        testConfig.hoverOverElement(shopMenuLink);
    }

    /**
     * Method to click on Men's Section
     */
    public void clickOnMenSection(){
        wait.until(ExpectedConditions.elementToBeClickable(menSectionLink));
        menSectionLink.click();
    }

    /**
     * Method to hover over extended Menu in homepage
     */
    public void hoverOver3dotMenu(){
        testConfig.hoverOverElement(ThreeDotMenuLink);
    }

    /***
     * Method to click on New & Features link from Menu
     * @return - Object of class NewsPage
     */
    public NewsPage clickNewsLink(){
        wait.until(ExpectedConditions.elementToBeClickable(newsAndFeatureLink));
        try {
            testConfig.hoverOverElement(newsAndFeatureLink);
            newsAndFeatureLink.click();
        } catch (Exception e){
            newsAndFeatureLink.click();
        }
        return new NewsPage(testConfig);
    }





}
