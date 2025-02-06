package definitions;

import automation.framework.Config;
import derived.product2.Helper.Helper;
import derived.product2.PageObject.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class DP2Definitions {

    Config testConfig;
    Helper helper;
    HomePage homePage;
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

        testConfig.tearDown(testConfig);
    }

    @Given("User navigates to DP2 Homepage")
    public void openHomePage(){
        helper = new Helper();
        helper.openHomePage(testConfig);
        homePage = new HomePage(testConfig);
    }

    @When("User stores all hyperlinks in csv file")
    public void storeHyperLinks(){
        homePage.storeLinksFromFooter();
    }

    @Then("User verifies and reports if any duplicate hyperlinks are present")
    public void verifyIfLinksAreDuplicate(){
        homePage.validateLinks(helper, scenario);
    }
}
