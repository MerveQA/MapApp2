package tests;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
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


public class TC_001_V1 {

    WebDriver driver;



    @Parameters("browser")
    @Test(groups = {"Regression"})
    public void TC_001_V1(@Optional("chrome") String browser) {
        driver = Driver.getDriver(browser);

        HomePage homePage = new HomePage();

        String version = "v1";
        System.out.println("MapApp version= " + version+"\n");
        Log.info("MapApp version= " + version+"\n");

        Dimension dimension = new Dimension(400, 849);
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        ReusableMethods.checkAds(homePage,version, driver);

        Driver.quitDriver();
    }















/*


    @Parameters("browser")
    @Test(groups = {"Regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC_001_V1= "+"User can visit all pages assert ads\n")
    public void TC_001_V1(@Optional("chrome") String browser) {
        driver = Driver.getDriver(browser);

        HomePage homePage = new HomePage();

        String version = "v1";
        System.out.println("MapApp version= " + version+"\n");
        Log.info("MapApp version= " + version+"\n");

        Dimension dimension = new Dimension(400, 849);
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        ArrayList<String> userHashs = ReusableMethods.userHash();


        for (int userHashID = 0; userHashID < userHashs.size(); userHashID++) { //get each data from table
            String userHash = userHashs.get(userHashID);
            driver.get(
                    ConfigReader.getProperty("URL")
                            + userHash
                            + ConfigReader.getProperty("sub")
                            + version);

            Log.info("URL= " + ConfigReader.getProperty("URL")
                    + userHash
                    + ConfigReader.getProperty("sub")
                    + version);
            Log.info("Header= " + homePage.resourceDetailHeaderText.getText());


            Actions actions = new Actions(driver);
            ReusableMethods.waitFor(2);
            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);

            //First Ad
            try {
                Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                System.out.println("First Ad= Google Ad");
                Log.info("First Ad= Google Ad");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                System.out.println("First Ad= Rambly Ad");
                Log.info("First Ad= Rambly Ad");
            }

            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);
            homePage.nextButton.click();
            ReusableMethods.waitFor(3);
            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);

            //Second Ad
            try {
                Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                System.out.println("Second Ad= Google Ad");
                Log.info("Second Ad= Google Ad");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                System.out.println("Second Ad= Rambly Ad");
                Log.info("Second Ad= Rambly Ad");
            }

            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);
            homePage.nextButton.click();
            ReusableMethods.waitFor(1);
            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);

            //Third Ad
            try {
                Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                System.out.println("Third Ad= Google Ad");
                Log.info("Third Ad= Google Ad");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                System.out.println("Third Ad= Rambly Ad");
                Log.info("Third Ad= Rambly Ad");
            }
        }
        Driver.quitDriver();
    }

*/



}



