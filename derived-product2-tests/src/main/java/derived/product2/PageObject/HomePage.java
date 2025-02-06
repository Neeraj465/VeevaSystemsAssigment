package derived.product2.PageObject;

import automation.framework.Config;
import derived.product2.Helper.Helper;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    @FindBy(xpath = "//span[text()='Teams']")
            public WebElement teamTxt;

    @FindBy(xpath = "//div[@class='mb-2 font-bold']")
            public WebElement footerTxt;
    @FindBy(xpath = "//footer//a")
            public List<WebElement> footerLinks;


    Config testConfig;
    WebDriverWait wait;
    long timeout;
    public HomePage(Config testConf) {
        this.testConfig = testConf;
        PageFactory.initElements(testConfig.driver, this);
        timeout = Long.parseLong(testConfig.runTimeProperties.get("defaultTimeOut").toString());
        wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOf(teamTxt));

    }

    /**
     * Method to scroll down to footer element
     */
    public void scrollToFooter(){
        wait.until(ExpectedConditions.visibilityOf(footerTxt));
        testConfig.scrollToElement(footerTxt);
    }

    /**
     * Method to get all links present in footer and store them in csv file
     */
    public void storeLinksFromFooter(){
            String[] links = new String[footerLinks.size()];
            int i=0;

            //storing link urls in String array
            for(WebElement link: footerLinks){
                links[i++] = link.getAttribute("href").trim();
            }

            // storing String array in a list
            List<String[]> linkData = new ArrayList<>();
            linkData.add(links);

            //creating a csv file which link url
            testConfig.storeDataInCsv(new String[] {"links"}, linkData, "HyperLinks");
    }

    /**
     * Method to verify if duplicate links are present in csv file and log the result
     * @param helper - Helper class object
     * @param scenario - Scenario class object to log result
     */
    public void validateLinks(Helper helper, Scenario scenario){
        String[] result = helper.verifyLinksDataInCsv(testConfig, "HyperLinks");
        if(result[0].equals("0"))
            scenario.log("There are no duplicate links");
        else {
            scenario.log("Total number of duplicate links: "+result[0]);
            scenario.log("Duplicate links: "+result[1]);
        }

    }
}
