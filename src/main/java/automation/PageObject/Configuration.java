package automation.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;

public class Configuration extends AbstractComponent{

	WebDriver driver;

	public Configuration(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory
	
	@FindBy(css = "a:nth-child(1) > span:nth-child(2)")
	WebElement configurationModule;
	
	@FindBy(xpath = "//a[@href='/configuration/parkers']//div[@class='py-5 self-center']//*[name()='svg']")
	WebElement parkerEnrollement;
}
