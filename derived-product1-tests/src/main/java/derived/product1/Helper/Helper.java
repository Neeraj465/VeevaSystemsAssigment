package derived.product1.Helper;

import automation.framework.Config;

public class Helper {

    /**
     * Method to navigate to homepage url
     * @param testConfig
     */
    public void openHomePage(Config testConfig){
        testConfig.openBrowser();
        String url = testConfig.runTimeProperties.get("url").toString();
        testConfig.driver.get(url);
    }
}
