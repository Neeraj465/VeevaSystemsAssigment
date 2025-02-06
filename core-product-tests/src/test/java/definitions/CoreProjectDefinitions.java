package definitions;

import automation.framework.Config;
import automation.framework.TestBase;
import core.product.Helper.Helper;
import core.product.PageObject.HomePage;
import core.product.PageObject.MenSectionPage;
import core.product.PageObject.NewsPage;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonFormat;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class CoreProjectDefinitions {

    Config testConfig;
    Helper helper;
    HomePage homePage;
    MenSectionPage menSectionPage;
    NewsPage newsPage;
    Scenario scenario;
    @Before
    public void suiteSetup(Scenario scenario){
        testConfig = new Config();
        this.scenario = scenario;
    }

    @After
    public void tearDownMethod(Scenario sc){
        if(sc.isFailed()){
            TakesScreenshot ts = (TakesScreenshot) testConfig.driver;

            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            sc.attach(src, "image/png", "screenshot");
        }
        if(testConfig.runTimeProperties.get("jackDetails") != null){
            sc.attach(testConfig.runTimeProperties.get("jackDetails").toString(), "txt", "JacketDetails");
        }

        testConfig.tearDown(testConfig);
    }

    @Given("User logs in to CP homepage")
    public void openHomePage(){
        helper = new Helper();
        helper.openHomePage(testConfig);
        homePage = new HomePage(testConfig);

    }

    @When("User hovers over Shop Menu")
    public void shopMenu(){
        homePage.hoverOverShopMenu();
    }

    @And("User navigates to Men's section")
    public void menSection(){
        homePage.clickOnMenSection();
    }

    @Then("User stores Jacket details")
    public void storeJacket() throws Exception{
        menSectionPage = new MenSectionPage(testConfig);
        menSectionPage.storeJacketDetails();

    }

    @When("User hovers over ... Menu Item")
    public void hoverOverExtraMenuItem(){
        homePage.hoverOver3dotMenu();
    }

    @And("User clicks on New & Features")
    public void clickNewsAndFeaturesLink(){
        newsPage = homePage.clickNewsLink();
    }

    @Then("User counts total number of Videos Feeds")
    public void countTotalNewsFeed(){
        int count = newsPage.countNewsFeeds();
        scenario.log("Total New Feeds: "+count);
    }

    @And("^User counts the videos feeds present in the page from more than \"(.)\" days$")
    public void countDaySpecificNewsFeeds(String day){
        int count = newsPage.countNewsFeeds(Integer.parseInt(day));
        scenario.log("Total New Feeds from more than "+ day + " day(s): "+count);
    }



}
