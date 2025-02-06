package automation.framework;

import automation.framework.Config;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.time.Duration;

public class TestBase {

    private static ThreadLocal<Config[]> threadLocalConfig = new ThreadLocal<>();

    /**
     * Get Configurations
     * @return
     */
    @DataProvider(name = "GetTestConfig")
    public Object[][] GetTestConfig(){
        Config testConfig = new Config();
        threadLocalConfig.set(new Config[] {testConfig});
        return new Object[][] {{testConfig}};
    }

    /**
     * Method to load data from properties files
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuiteSetup(){
        Config.loadPropertiesFile();
    }

    /**
     * Method to run tear down after each execution
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        Config[] testConfigs = threadLocalConfig.get();
        for(Config testConfig: testConfigs){
            testConfig.driver.quit();
            testConfig.runTimeProperties.clear();
            testConfig=null;
        }
    }



}
