package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.time.Duration;

public class Hooks {
    private WebDriver driver;
    private static ExtentReports extentReports;




    @BeforeTest
    public void beforeScenario(Scenario scenario) {
        Log.info("Scenario is started: " + "\"" +scenario.getName()+"\"");

        System.out.println("scenario name =" + scenario.getName());
    }

    @AfterTest()
    public void afterScenario(Scenario scenario) {
        Log.info("Scenario: \""+scenario.getName()+"\"" + " --> Result: "+scenario.getStatus());
        if (scenario.isFailed()) {
            ExtentCucumberAdapter.getCurrentStep().log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Screenshot()).build());
        }
    }

    @AfterTest()
    public void tearDown(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", "Screenshots");
        }
    }


    public String getBase64Screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }


}
