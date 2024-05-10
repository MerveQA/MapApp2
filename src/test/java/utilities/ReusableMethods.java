package utilities;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.HomePage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class ReusableMethods {
    public ReusableMethods(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    static WebDriver driver;
    static String kimlikNo;


    //========Switching Window=====//
    public static void switchToWindow(String targetTitle) {
        String origin = driver.getWindowHandle();

        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().equals(targetTitle)) {
                return;
            }
        }
        // Driver.getDriver().switchTo().window(origin);
    }

//    public static void switchToWindow(String targetTitle) {
//        String origin = Driver.getDriver().getWindowHandle();
//
//        for (String handle : Driver.getDriver().getWindowHandles()) {
//            Driver.getDriver().switchTo().window(handle);
//            if (Driver.getDriver().getTitle().equals(targetTitle)) {
//                return;
//            }
//        }
//        Driver.getDriver().switchTo().window(origin);
//    }

    //========Hover Over=====//
    public static void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    //==========Return a list of string given a list of Web Element====////
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //========Returns the Text of the element given an element locator==//
    public static List<String> getElementsText(By locator) {
        List<WebElement> elems = driver.findElements(locator);
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //   HARD WAIT ==> THREAD.SLEEP ile
//   waitFor(5);  => waits for 5 second
    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //===============Explicit Wait==============//
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }


    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = driver -> {
            assert driver != null;
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
        }
    }

    //======Fluent Wait====//
    public static WebElement fluentWait(final WebElement webElement, int timeinsec) {

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(3))
                .pollingEvery(Duration.ofSeconds(1));

        WebElement element;
        element = wait.until(driver -> webElement);

        return element;
    }


    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = 1 + random.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }


    // BRSH
    public static int getRandomNumberBetweenRange(int min, int max) {

        return (int) (Math.random() * (max - min) + min);
    }


    public static Boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();

    }

    //a method calculates days between two dates
    public static Boolean daysBetweenDates(String dateStr) {
        boolean newborn = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        LocalDate currentDate = LocalDate.now();
        int daysBetween = (int) ChronoUnit.DAYS.between(localDate, currentDate);
        if (daysBetween <= 30 && daysBetween >= 0) {
            newborn = true;
        }
        return newborn;
    }

    public static Boolean isNumeric(String str) {
        //is numeric method
        boolean numeric = false;

        int length = str.length();
        for (int i = 0; i < length; i++) {

            if (Character.isDigit((str.charAt(i)))) {
                numeric = true;
                break;
            }
        }
        return numeric;
    }


    public static boolean isIdValid(String tckn) {
        boolean isValid;
        {
            isValid = false;
            if (tckn != null && tckn.length() == 11 && !tckn.startsWith("0") && isNumeric(tckn)) {
                long tcNo = Long.parseLong(tckn);
                long ad, a1, a2, a3, a4, a5, a6, a7, a8, a9, b1, b2;
                b2 = tcNo % 10;
                b1 = (tcNo / 10) % 10;
                ad = tcNo / 100;
                a9 = ad % 10;
                ad = ad / 10;
                a8 = ad % 10;
                ad = ad / 10;
                a7 = ad % 10;
                ad = ad / 10;
                a6 = ad % 10;
                ad = ad / 10;
                a5 = ad % 10;
                ad = ad / 10;
                a4 = ad % 10;
                ad = ad / 10;
                a3 = ad % 10;
                ad = ad / 10;
                a2 = ad % 10;
                ad = ad / 10;
                a1 = ad % 10;
                System.out.println("b2: " + b2 + " b1: " + b1 + " a1: " + a1 + " a2: " + a2 + " a3: " + a3 + " a4: " + a4 + " a5: " + a5 + " a6: " + a6 + " a7: " + a7 + " a8: " + a8 + " a9: " + a9);
                if (((a1 + a3 + a5 + a7 + a9) * 7 - (a2 + a4 + a6 + a8)) % 10 == b1 && ((a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 + a9 + b1)) % 10 == b2) {
                    isValid = true;
                }
            }

            return isValid;
        }

    }

    public static String generateValidId() {

        Faker faker = new Faker();
        String validId = faker.number().randomDigitNotZero() + faker.number().digits(8);
        int a10 = ((validId.charAt(0) - '0' + validId.charAt(2) - '0' + validId.charAt(4) - '0' + validId.charAt(6) - '0' + validId.charAt(8) - '0') * 7 -
                (validId.charAt(1) - '0' + validId.charAt(3) - '0' + validId.charAt(5) - '0' + validId.charAt(7) - '0')) % 10;
        int a11 = (validId.charAt(0) - '0' + validId.charAt(1) - '0' + validId.charAt(2) - '0' + validId.charAt(3) - '0' + validId.charAt(4) - '0' + validId.charAt(5) - '0' +
                validId.charAt(6) - '0' + validId.charAt(7) - '0' + validId.charAt(8) - '0' + a10) % 10;
        validId = validId + a10 + a11;
        return validId;
    }

    public static String getElementText(WebElement element) {
        System.out.println(element.getText());

        return element.getText();
    }

    public static void jseWithClick(WebDriver driver, WebElement element) {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click();", element);

    }

    public static void doubleClickWithJS(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('dblclick', { bubbles: true }));", element);
    }

    public static void scrollPageDownWithJS(WebDriver driver) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");

//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(0,250)", "");
    }

    public static void scrollPageTopWithJS(WebDriver driver) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(document.body.scrollHeight, 0)");

//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(0,250)", "");
    }


    public static String generateUserName() {
        Faker faker = new Faker();
        // String userName = faker.name().lastName().toLowerCase();
        String firstName = faker.name().lastName().toLowerCase();
        String lastName = faker.name().lastName().toLowerCase();
        String userName = firstName + lastName;
        // String userName = String.valueOf(faker.funnyName());
        return userName;
    }

    public static String generateEmail() {
        Faker faker = new Faker();
        // String userName = faker.name().lastName().toLowerCase();
        String firstName = faker.name().lastName().toLowerCase();
        String lastName = faker.name().lastName().toLowerCase();
        String email = firstName + lastName + "@ramblytest.com";
        // String userName = String.valueOf(faker.funnyName());
        return email;
    }

    public static void sendKeysWithJS(WebDriver driver, WebElement element, String text) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value = arguments[1]", element, text);
    }

    public static long periodBetweenDates(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        LocalDate currentDate = LocalDate.now();
        Period period = localDate.until(currentDate);
        return period.getYears() * 365L + period.getMonths() * 30L + period.getDays();
    }

    public static void continueIf(WebElement element) {
        ReusableMethods.waitFor(3);
        try {
            if (element.isDisplayed()) {
                element.click();
            }
        } catch (NoSuchElementException e) {
        }
        ReusableMethods.waitFor(1);
    }

    public static void clickUntilInvisible(WebElement element, int sec) {
        boolean visible = true;
        while (visible) {
            try {
                if (element.isDisplayed()) {
                    ReusableMethods.waitFor(sec);
                    element.click();
                }
            } catch (NoSuchElementException e) {
                visible = false;
            }
        }
    }

    public static void scrollDownUntilVisible(WebElement element, int sec) {
        boolean visible = true;
        while (visible) {
            try {
                if (!element.isDisplayed()) {
                    scrollPageDownWithJS(driver);
                    ReusableMethods.waitFor(sec);
                }
            } catch (NoSuchElementException e) {
                visible = false;
                System.out.println(element.getText());
            }
        }
    }

    public static void categoryHandle(String name) {
        WebElement animations = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[1]"));
        WebElement artsAndCrafts = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[2]"));
        WebElement community = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[3]"));
        WebElement dance = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[4]"));
        WebElement diy = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[5]"));
        WebElement education = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[6]"));
        WebElement entertainment = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[7]"));
        WebElement finance = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[8]"));
        WebElement foodAndDrink = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[9]"));
        WebElement gaming = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[10]"));
        WebElement healthAndFitness = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[11]"));
        WebElement lifestyle = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[12]"));
        WebElement music = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[13]"));
        WebElement photography = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[14]"));
        WebElement scienceAndTech = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[15]"));
        WebElement spirituality = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[16]"));
        WebElement travel = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[17]"));
        WebElement writing = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[18]"));
        WebElement other = driver.findElement(By.xpath("(//div[@class='styles_container__MrwE4'])[19]"));
        List<WebElement> categories = new ArrayList<>();
        categories.add(animations);
        categories.add(artsAndCrafts);
        categories.add(community);
        categories.add(dance);
        categories.add(diy);
        categories.add(education);
        categories.add(entertainment);
        categories.add(finance);
        categories.add(foodAndDrink);
        categories.add(gaming);
        categories.add(healthAndFitness);
        categories.add(lifestyle);
        categories.add(music);
        categories.add(photography);
        categories.add(scienceAndTech);
        categories.add(spirituality);
        categories.add(travel);
        categories.add(writing);
        categories.add(other);

    }

    public static void scrollToElementJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static ArrayList userHash() {
        ArrayList<String> userHashs = new ArrayList<String>();
        userHashs.add("4d099387ab32737f62f1bd7ece178232");
      // userHashs.add("ff3af992bc0ee81660abce44f4751d8e");
      // userHashs.add("6526843767c0df67efd981b19946c857");
      // userHashs.add("411b95aea44fff454d897a647a49937c");
      // userHashs.add("34e3d7e9eec22c2d7b0ff9bbc120d117");
      // userHashs.add("6f78248edbc1679b1529953b5eb82892");
      // userHashs.add("402007e3f7b8ec5ebd5b63b9c122f08b");
      // userHashs.add("25c791c646dac62810b42ba2d48ee43b");
      // userHashs.add("4d099387ab32737f62f1bd7ece178232");
      // userHashs.add("148267f02b92f9975af0fc024734f6c3");
      // userHashs.add("20ddb9b9e25052e93fa3cf76f3251e7b");
      // userHashs.add("2f9535cc6f24883dd01e21bc6ab06c60");
      // userHashs.add("21c7b413cd2311e778f80ba803e69033");
      // userHashs.add("065acebc220d73e4390e5125ac1999ab");
      // userHashs.add("63ad1d46f96e6a1f9babcd3c9bdf5f71");
      // userHashs.add("56a107954cabe3006c7e900b3fa9f8aa");
      // userHashs.add("90c21a4bc8484ea2d653b2013b9539d0");
      // userHashs.add("0d954b9f08f5d725ba7c6266752c3268");
      // userHashs.add("771230f5d1ee38c4d5158e0fff77a0f7");
      // userHashs.add("f8dab5afe7e54c6d70d5dcc227a491d5");
      // userHashs.add("01505c8b4f5ce365d2278862422944f2");
      // userHashs.add("5899f7883bcd57864755d427287108f6");
      // userHashs.add("e780096c474b193bb662886871d92716");
      // userHashs.add("dd7982a6f43ee8155b27d3f07908f5c9");
      // userHashs.add("99da74632a0edf64cb0b812b5d3637f5");
      // userHashs.add("61bf72860e137484b34a47191663430d");
      // userHashs.add("4a913d84ab9a759fb17001351f8c6d1b");
      // userHashs.add("602f9d3615c0e80946ea0d10cc2302f1");
      // userHashs.add("b6c864cfdff684199ad77e263fe48814");
      // userHashs.add("87ee0cd7a7230e47cae356a90a100c3a");
      // userHashs.add("8646d02ed01644eebb2f43a4767656c8");
      // userHashs.add("05ee0d664efb75f2f467cfb649685e24");
      // userHashs.add("065acebc220d73e4390e5125ac1999ab");
      // userHashs.add("31dd6368b46155cbdff56645b7c86709");
      // userHashs.add("7f8d046b4e3a9eac02325f8f54ff4368");
      // userHashs.add("7a787700a71ccbdd0e38e61cba35a098");
      // userHashs.add("7a57078618d1a8a91e408a4e9c12850b");
      // userHashs.add("1e3e2e7733a348eccfb0550af1026b17");
      // userHashs.add("9a438afa418502bea68e40f773596c8e");
      // userHashs.add("2c437dc272e2f5726f3352f967828d79");
      // userHashs.add("ef455e744b71f395bf41b766762de54b");
      // userHashs.add("c544d280dd59f9bf86b9c2171e7f9c8f");
      // userHashs.add("24e1a9dc6fd15a19ad44d9bc4892eb33");
      // userHashs.add("f0b7cd3713aab05f472e06315d89e089");
      // userHashs.add("bdd4694979083c3b9f2d0ccfcc4e0f1f");
      // userHashs.add("7f8d046b4e3a9eac02325f8f54ff4368");
      // userHashs.add("0611d6f1f7770cf225642ee3aeaee9b4");
        return userHashs;
    }

    public static void checkAds(HomePage homePage,String version, WebDriver driver){
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        SoftAssert softAssert = new SoftAssert();
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
            //Log.info("After Ad Block URL= " + driver.getCurrentUrl());
            Log.info("Header= " + homePage.resourceDetailHeaderText.getText());


            Actions actions = new Actions(driver);
            ReusableMethods.waitFor(2);
            actions.scrollToElement(homePage.nextButton).perform();
            ReusableMethods.waitFor(1);

            //First Ad
            try {
                //Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                softAssert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                Log.info("First Ad= Rambly Ad");

            } catch (NoSuchElementException e) {
                //Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                softAssert.assertTrue(homePage.googleAdvertisement.isDisplayed());
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
                //Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                softAssert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                Log.info("Second Ad= Rambly Ad");
            } catch (NoSuchElementException e) {
                //Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                softAssert.assertTrue(homePage.googleAdvertisement.isDisplayed());
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
                //Assert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                softAssert.assertTrue(homePage.modaramoAdvertisement.isDisplayed());
                Log.info("Third Ad= Rambly Ad");
            } catch (NoSuchElementException e) {
                //Assert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                softAssert.assertTrue(homePage.googleAdvertisement.isDisplayed());
                Log.info("Third Ad= Google Ad");
            }
        }
        softAssert.assertAll();
    }


    public static void checkAdsAdBlock(HomePage homePage,String version, WebDriver driver){
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

    }
}