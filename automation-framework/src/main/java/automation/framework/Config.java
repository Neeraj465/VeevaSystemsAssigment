package automation.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class Config {

    public static Properties staticProperties = null;
    public Properties runTimeProperties;
    public WebDriver driver;
    public List<Map<String,String>> yamlData = null;

    public Config(){
        beforeSuiteSetup();
    }

    /**
     * Load properties defined in config.properties
     */
    public static void loadPropertiesFile(){

        staticProperties = new Properties();
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "Config.properties";
        String message = "Reading file from path: "+path;
        System.out.println(message);
        try {
            FileInputStream fn = new FileInputStream(path);
            staticProperties.load(fn);
            fn.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Open Browser using stored parameter
     */
    public void openBrowser(){
        String browser = runTimeProperties.get("Browser").toString();

        switch (browser.toLowerCase()){
            case "firefox":
                this.driver = new FirefoxDriver();
                break;
            case "chrome":
                this.driver = new ChromeDriver(doChromeSetting());
                break;
        }

    }

    /**
     * Method to form chromeOptions
     * @return - returns ChromeOptions
     */
    public ChromeOptions doChromeSetting(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-dv-shm-usage");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        return chromeOptions;
    }

    /**
     * Method to get run-time property using key
     * @param key - property to fetch
     * @return - return value of key
     */
    public String getRunTimeProperty(String key){
        String value = "";
        try{
            value = this.runTimeProperties.get(key).toString();
        } catch (Exception e){
            return null;
        }
        return value;
    }

    /**
     * Method to setup suite config before executions
     */
    public void beforeSuiteSetup(){
        loadPropertiesFile();
        this.runTimeProperties = new Properties();
        Enumeration<Object> em = staticProperties.keys();
        while(em.hasMoreElements()){
            String str = (String) em.nextElement();
            this.runTimeProperties.put(str, staticProperties.get(str).toString());
        }

        String browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
        this.runTimeProperties.put("Browser", browser);
    }

    /**
     * Method to close all config after execution
     * @param testConfig - Config class object
     */
    public void tearDown(Config testConfig){
        testConfig.driver.quit();
        testConfig.runTimeProperties.clear();

    }

    /**
     * Method to hover over an element
     * @param element - WebElement to hover on
     */
    public void hoverOverElement(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    /**
     * Method to read yaml file
     * @param fileName - yaml file to read
     */
    public void readYamlData(String fileName){
        this.yamlData = (new CommonHelper()).readYamlfile(fileName);
    }

    /**
     * Method to fetch coulmn and row data from yaml file of specific row
     * @param fileName - yaml file to process
     * @param row - row number whose column and its values has to be fetched
     * @return - returns key-value pair of columns and its value
     */
    public HashMap<String,String> getColumnValuesOfSpecificRow(String fileName, int row){
        if(this.yamlData == null)
            this.yamlData = (new CommonHelper()).readYamlfile(fileName);
        HashMap<String,String> data = new HashMap<>();

        try{
            Set<String> headerRow = this.yamlData.get(row-1).keySet();
            for(String column: headerRow){
                data.put(column, this.yamlData.get(row-1).get(column).trim());
            }

        } catch(NullPointerException e){
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Method to scroll to the element
     * @param element - WebElement
     */
    public void scrollToElement(WebElement element){
        Actions action = new Actions(this.driver);
        action.moveToElement(element).perform();
    }

    /**
     * Method to store data in csv file
     * @param headers - columns of csv file
     * @param data - rows of csv file
     * @param fileName - csv file name
     */
    public void storeDataInCsv(String[] headers, List<String[]> data, String fileName){
        (new CommonHelper()).writeAndStoreDataInCsvFile(headers, data, fileName);
    }

    /**
     * Method to read csv file
     * @param fileName - csv file to read
     * @return - return list of rows as String
     */
    public List<String> readDataFromCsv(String fileName){
        return (new CommonHelper()).readCsvFile(fileName);
    }
}
