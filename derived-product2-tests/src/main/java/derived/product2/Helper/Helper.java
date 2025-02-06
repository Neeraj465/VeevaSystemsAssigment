package derived.product2.Helper;

import automation.framework.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Helper {

    /**
     * Method to navigate to homepage
     * @param testConfig
     */
    public void openHomePage(Config testConfig){
        testConfig.openBrowser();
        String url = testConfig.runTimeProperties.get("url").toString();
        testConfig.driver.get(url);
    }

    /**
     * Method to check duplicate links in given csv file
     * @param testConfig - Config class object
     * @param filename - csv file to check link data
     * @return - return String array with count of duplicate links and corresponding link url
     */
    public String[] verifyLinksDataInCsv(Config testConfig, String filename){
          List<String> list =  testConfig.readDataFromCsv(filename);
        int count=0;
        String duplicateLinks = "";
        Set<String> set = new HashSet<>();
        for(String link: list){
            if(set.contains(link)) {
                count++;
                duplicateLinks += link +",";
            }
            else
                set.add(link);
        }
        duplicateLinks = duplicateLinks.substring(0, duplicateLinks.length()-1);
        return new String[]{Integer.toString(count), duplicateLinks};

    }
}
