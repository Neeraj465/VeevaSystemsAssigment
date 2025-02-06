package core.product.PageObject;

import automation.framework.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;
import java.util.List;

public class NewsPage {

    @FindBy(xpath = "//h3[text()='NEWS']")
    public WebElement newsTxt;

    @FindBy(xpath = "//h3[text()='NEWS']/ancestor::div[@class='w-full']//ul//li")
    public List<WebElement> newsFeeds;

    Config testConfig;
    WebDriverWait wait;
    long timeout;
    public NewsPage(Config testConf){
        this.testConfig = testConf;
        PageFactory.initElements(testConfig.driver, this);
        timeout = Long.parseLong(testConfig.runTimeProperties.get("defaultTimeOut").toString());
        wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOf(newsTxt));
        wait.until(ExpectedConditions.visibilityOfAllElements(newsFeeds) );
    }

    /**
     *  This method counts total number of News Feed present in page
     */
    public int countNewsFeeds(){
        wait.until(ExpectedConditions.visibilityOfAllElements(newsFeeds));
        return newsFeeds.size();
    }

    /**
     * This method counts number of News Feed which are older than given day(s)
     * @param day - Number of days
     * @return - return count of feeds >= number of days
     */
    public int countNewsFeeds(int day){
        int count =0;
        wait.until(ExpectedConditions.visibilityOfAllElements(newsFeeds));
        try{
            Thread.sleep(2000);

        } catch (Exception e){

        }
        for(WebElement news: newsFeeds){
            // getting text inside time attribute in every news feed
            String time = news.findElement(By.xpath(".//time//span")).getText();
            if(time.charAt(time.length()-1) == 'd' && (Integer.parseInt(time.replaceAll("[a-z]","")) ) >= day )
                count++;
        }
        return count;
    }

}
