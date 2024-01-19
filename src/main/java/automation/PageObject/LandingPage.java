package automation.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;


public class LandingPage extends AbstractComponent {

	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmails = driver.findElement(By.id("userEmail"));
	//PageFactory
	
	@FindBy(name="username")
	WebElement username;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(css=".btn")
	WebElement login;
	
	@FindBy(css="button[class='w-8']")
	WebElement logout;
	
	@FindBy(css=".text-xl.mb-12.mt-12")
	WebElement validateLogout;
	
	@FindBy (css=".text-base.mb-6.text-sm-error")
	WebElement errorMsg;
	
	
	public UserEnrollment loginApplication(String userName, String pword) {
		username.sendKeys(userName);
		password.sendKeys(pword);
		login.click();
		UserEnrollment userCreationPage = new UserEnrollment(driver);
		return userCreationPage;
		
	}
	
	public void goTo() {
		driver.get("https://hub.parking-stg.smop.asia/");
	}
	
	public boolean successLogin() {
		waitForWebElementToAppear(smLogo);
		boolean logo = smLogo.isDisplayed();
		return logo;
	}

	public String getErrorMsg() {
		return errorMsg.getText();
	}
	
	public String logout() {
		logout.click();
		String confirmLogout = validateLogout.getText();
		return confirmLogout;
		
	}
}
