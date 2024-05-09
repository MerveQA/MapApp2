package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HomePage {
    public HomePage() {
        PageFactory.initElements(Driver.getDriver("browser"), this);
    }

    @FindBy(xpath = "/html/body/div[1]/main/div/div/div[1]/div/div[1]/span[2]")
    public WebElement resourceDetailHeaderText;
    @FindBy(xpath = "//ins[@class='adsbygoogle undefined']")
    public WebElement googleAdvertisement;
    @FindBy(className = "styles_wallBlock__p_pli")
    public WebElement modaramoAdvertisement;
    @FindBy(xpath = "//button[text()='Next']")
    public WebElement nextButton;
    @FindBy(xpath = "//button[text()='Previous']")
    public WebElement previousButton;

}