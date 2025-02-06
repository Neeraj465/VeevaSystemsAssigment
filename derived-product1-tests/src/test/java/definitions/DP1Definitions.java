package definitions;

import automation.framework.Config;
import derived.product1.Helper.Helper;
import derived.product1.PageObject.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class DP1Definitions {

    Config testConfig;
    Scenario scenario;
    Helper helper;
    HomePage homePage;
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

    @Given("User logs in to DP1 homepage")
    public void openDP1Homepage(){
        helper = new Helper();
        helper.openHomePage(testConfig);
        homePage = new HomePage(testConfig);

    }

    @When("User counts number of slide present in ticket menu")
    public void countNoOfSlides(){
        int slideCount = homePage.countSlides();
        scenario.log("Total Number of Slides: "+slideCount);
    }

    @Then("User validates title of each slide")
    public void validateSlideDetails(){
        homePage.getAndValidateTitle();
    }

    @And("User validates duration of each slide with expected duration")
    public void validateSLideDuration(){
        homePage.getAndValidateDuration();
    }
}
