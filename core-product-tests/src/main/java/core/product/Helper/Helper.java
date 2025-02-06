package core.product.Helper;

import automation.framework.Config;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Helper {

    /**
     * Navigate to url
     * @param testConfig
     */
    public void openHomePage(Config testConfig){
        testConfig.openBrowser();
        String url = testConfig.runTimeProperties.get("url").toString();
        testConfig.driver.get(url);
    }

}
