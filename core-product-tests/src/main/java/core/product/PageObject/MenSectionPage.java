package core.product.PageObject;

import automation.framework.Config;
import core.product.Helper.Helper;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class MenSectionPage extends Helper {

    @FindBy(xpath = "//a//span[text()='Jackets']")
    public WebElement sortByJacketBtn;

    @FindBy(xpath = "(//div[@data-talos='pageCount'])[1]")
    public WebElement pageCountTxt;

    @FindBy(xpath = "(//li[@class='next-page'])[1]//i")
    public WebElement nextPageBtn;

    @FindBy(xpath = "(//li[contains(@class,  'show-for-large')])[1]")
    public WebElement pages;

    @FindBy(xpath = "//i[@aria-label='Close Pop-Up']")
    public WebElement closePopupBtn;

    public String columns = "//div[@class='column']";
    public String pages = "(//a[text()='{pageNum}'])[1]";

    Config testConfig;
    WebDriverWait wait;
    long timeout;
    public MenSectionPage(Config testConf){
        this.testConfig = testConf;
        PageFactory.initElements(testConfig.driver, this);
        timeout = Long.parseLong(testConfig.runTimeProperties.get("defaultTimeOut").toString());
        wait = new WebDriverWait(testConfig.driver, Duration.ofSeconds(timeout));

    }

    /**
     * This method sorts the items by Jacket and Iterate through result to get jacket details
     * and store the details in string
     * @throws Exception
     */
    public void storeJacketDetails() throws Exception{
        String currentHandle= testConfig.driver.getWindowHandle();

        //Switching to current window handle
        Set<String> handles=testConfig.driver.getWindowHandles();
        for(String actual: handles) {
            if (!actual.equalsIgnoreCase(currentHandle)) {
                //Switch to the opened tab
                testConfig.driver.switchTo().window(actual);
            }
        }
        try{
            wait.until(ExpectedConditions.elementToBeClickable(closePopupBtn));
            closePopupBtn.click();
        } catch (Exception e){

        }

        //sorting the result by jackets to get list of jackets
        wait.until(ExpectedConditions.elementToBeClickable(sortByJacketBtn));
        sortByJacketBtn.click();
        Thread.sleep(2000);
        String pageNums = pageCountTxt.getAttribute("innerHTML");
        int pageCount = Integer.parseInt( Character.toString(pageNums.charAt(pageNums.length()-1)) );

        int j=1;
        String fileInput="";

        //iterating through each page
        for (int i = 1; i <= pageCount; i++) {

            testConfig.driver.findElement(By.xpath(pages.replace("{pageNum}", Integer.toString(i)))).click();
            List<WebElement> jackets = testConfig.driver.findElements(By.xpath(columns));

            // getting details of each jacket in current page
            for(WebElement ele: jackets){
                String price = ele.findElement(By.xpath(".//span[@class='sr-only']")).getText();
                String title = ele.findElement(By.xpath(".//div[@class='product-card-title']")).getText();
                String message="";
                try{
                    message = ele.findElement(By.xpath(".//span[@class='top-seller-vibrancy-message']")).getText();
                } catch (Exception e){

                }

                fileInput += "Jacket: "+j++ +"\n" +"price: "+price
                        +"\n" + "title: "+title + "\n" + "message "+message +"\n\n";
            }

        }
        // storing the jacket details in runTimeProperties to utilise further
        testConfig.runTimeProperties.put("jackDetails", fileInput);
    }
}
