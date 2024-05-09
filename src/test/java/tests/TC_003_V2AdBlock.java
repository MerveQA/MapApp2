package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.Log;
import utilities.ReusableMethods;

import java.time.Duration;
import java.util.ArrayList;


public class TC_003_V2AdBlock {

    WebDriver driver;

    @Parameters("browser")
    @Test(groups = {"Regression"})// bu en son testler bitince karar verelim
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC_003_V2AdBlock= "+"User can visit all pages assert ads\n")
    public void TC_003_V2AdBlock(@Optional("adblockchrome") String browser) {
        driver = Driver.getDriver(browser);
        System.out.println("TC_003_V2AdBlock= "+"User can visit all pages assert ads\n");
        HomePage homePage = new HomePage();

        String version = "v2";
        Log.info("MapApp version= " + version+"\n");

        Dimension dimension = new Dimension(400, 849);
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(0));
        ReusableMethods.waitFor(1);

        ReusableMethods.checkAdsAdBlock(homePage,version,driver);

        Driver.quitDriver();
    }

/*

    @Parameters("browser")
    @Test(groups = {"Regression"})// bu en son testler bitince karar verelim
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC_003_V2AdBlock= "+"User can visit all pages assert ads\n")
    public void TC_003_V2AdBlock(@Optional("adblockchrome") String browser) {
        driver = Driver.getDriver(browser);
        System.out.println("TC_003_V2AdBlock= "+"User can visit all pages assert ads\n");
        HomePage homePage = new HomePage();

        String version = "v2";
        Log.info("MapApp version= " + version+"\n");

        Dimension dimension = new Dimension(400, 849);
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(0));
        ReusableMethods.waitFor(1);

        ArrayList<String> userHashs = ReusableMethods.userHash();


        for (int userHashID = 0; userHashID < userHashs.size(); userHashID++) { //get each data from table
            String userHash = userHashs.get(userHashID);
            driver.get(
                    ConfigReader.getProperty("URL")
                            + userHash
                            + ConfigReader.getProperty("sub")
                            + version);

            System.out.println("\n");
            Log.info("userHash = " + userHash);

            Log.info("URL= " + ConfigReader.getProperty("URL")
                    + userHash
                    + ConfigReader.getProperty("sub")
                    + version);
            ReusableMethods.waitFor(2);
            Log.info("After Ad Block URL= " + driver.getCurrentUrl());
            Log.info("Header= " + homePage.resourceDetailHeaderText.getText());


            Actions actions = new Actions(driver);
            ReusableMethods.waitFor(2);
            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);

            //First Ad
            try {
                Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                Log.info("First Ad= Rambly Ad");

            } catch (NoSuchElementException e) {
                Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                Log.info("First Ad= Google Ad");
            }

            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);
            homePage.nextButton.click();
            ReusableMethods.waitFor(3);
            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);

            //Second Ad
            try {
                Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                Log.info("Second Ad= Rambly Ad");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                Log.info("Second Ad= Google Ad");
            }

            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);
            homePage.nextButton.click();
            ReusableMethods.waitFor(1);
            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);

            //Third Ad
            try {
                Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                Log.info("Third Ad= Rambly Ad");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                Log.info("Third Ad= Google Ad");
            }
        }
        Driver.quitDriver();
    }

*/

}



