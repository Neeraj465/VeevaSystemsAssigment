package derived.product1.PageObject;

import automation.framework.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

public class HomePage {

    @FindBy(xpath = "//span[text()='Tickets']")
    public WebElement ticketTxt;

    @FindBy(xpath = "//button[text()='I Accept']")
            public WebElement acceptCookiesBtn;

    @FindBy(xpath = "//div[@role='group']/div[contains(@class, 'Game_game')]")
            public List<WebElement> gameSlides;
    @FindBy(xpath = "//div[contains(@class, 'Game_featured')]")
            public WebElement featuredGame;
    @FindBy(xpath = "//p[@data-testid='team'][1]/span/span[1]")
            public List<WebElement> titles;
    @FindBy(xpath = "//time[contains(@class, 'Game_game')]/span[1]")
            public List<WebElement> durations;




    Config testConfig;
    WebDriverWait wait;
    long timeout;
    public HomePage(Config testConf) {
        this.testConfig = testConf;
        PageFactory.initElements(testConfig.driver, this);
        timeout = Long.parseLong(testConfig.runTimeProperties.get("defaultTimeOut").toString());
        wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOf(ticketTxt));

        try {
            wait.until(ExpectedConditions.visibilityOf(acceptCookiesBtn));
            acceptCookiesBtn.click();
            Thread.sleep(2000);
        } catch (Exception e){

        }
    }

    /**
     * This method counts the number of slides present in homepage
     * @return - counts the number of slides present in homepage
     */
    public int countSlides(){
        return gameSlides.size();
    }

    /**
     * Method to fetch and validate titles of slides
     */
    public void getAndValidateTitle(){
        List<String> actualTitle = new LinkedList<>();
        for(WebElement title: titles){
                actualTitle.add(title.getAttribute("innerText").trim());
        }

        //getting dummy test data for titles from yaml file
        HashMap<String,String> testData = testConfig.getColumnValuesOfSpecificRow("EventDetails", 1);
        List<String> expectedData = new ArrayList<>(Arrays.asList(testData.get("titles").split(",")));

        Assert.assertTrue(actualTitle.equals(expectedData), "Titles mismatch");
    }

    /**
     * Method to fetch and validate duration of slides
     */
    public void getAndValidateDuration(){
        List<String> actualTitle = new LinkedList<>();
        for(WebElement duration: durations){
            actualTitle.add(duration.getAttribute("innerText").trim());
        }

        //getting dummy test data for duration from yaml file
        HashMap<String,String> testData = testConfig.getColumnValuesOfSpecificRow("EventDetails", 1);
        List<String> expectedData = new ArrayList<>(Arrays.asList(testData.get("durations").split("[|]")));

        Assert.assertTrue(actualTitle.equals(expectedData), "Duration mismatch");
    }
}
