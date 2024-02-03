package automation.PageObject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;

public class Permissions extends AbstractComponent {

	WebDriver driver;

	public Permissions(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory

	@FindBy(css = "input[placeholder='Search']")
	WebElement search;

	@FindBy(xpath = "//*[@id='dropdownDefaultButton']")
	WebElement dropdownButton;

	@FindBy(xpath = "//*[@class='mr-2']")
	WebElement userStatus;

	@FindBy(xpath = "//*[@class='w-2 h-2 mr-2 mt-2']")
	WebElement statusChangeBtn;

	@FindBy(xpath = "//*[text()='Proceed']")
	WebElement proceedBtn;

	@FindBy(name = "username")
	WebElement username;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(css = ".btn")
	WebElement login;

	@FindBy(css = "[class*='text-base']")
	WebElement errorMsg;
	
	@FindBy(xpath="//div/div/form/div[2]/button")
	WebElement searchBtn;

	@FindBy(css = "tr td:first-child")
	WebElement searchResult;

	@FindBy(xpath = "//*[@class='text-base mb-6 text-sm-error']")
	WebElement errorMessage;

	@FindBy(xpath = "//tr/td[7]/div")
	WebElement paymentAcceptanceToggleStatus;

	@FindBy(xpath = "//tr/td[7]/div/label/div/div")
	WebElement paymentAcceptanceToggleSwitch;

	@FindBy(xpath = "//tr/td[8]/div")
	WebElement allowExitToggleStatus;

	@FindBy(xpath = "//tr/td[8]/div/label/div/div")
	WebElement allowExitToggleSwitch;

	@FindBy(xpath = "//input[@name='search']")
	WebElement encoderSearch;

	@FindBy(xpath = "//button[.='Search Vehicle']")
	WebElement searchVehicleButton;

	@FindBy(css = "h1")
	WebElement encoderSearchResult;

	@FindBy(xpath = "//div[2]/section/button")
	WebElement markCompleteBtn;

	@FindBy(xpath = "//div[2]/section/div[4]/button")
	WebElement receiveParkingPaymentBtn;

	public void navigateToEncoder() {

//		Actions actions = new Actions(driver);
//		actions.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).perform();
//
//		// Switch to the newly opened tab
//		String newTabHandle = driver.getWindowHandles().toArray()[1].toString();
//		driver.switchTo().window(newTabHandle);
//
		// Introduce a small delay (you can adjust this as needed)
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		driver.get("https://encoder.parking-stg.smop.asia/login/");

	}

	public void loginToEncoderApp(String userName, String pword) {

		username.sendKeys(userName);
		password.sendKeys(pword);
		login.click();
	}

	private void setEmailAddressAndSearch(String emailAddress) throws InterruptedException {
		search.sendKeys(emailAddress);
		String result;
		do {
			Thread.sleep(3000);
			result = searchResult.getText();
		} while (!result.equalsIgnoreCase("Status Change"));
	}

	public boolean setStatusToRestricted() throws InterruptedException {
		return setUserStatus("Restricted");
	}

	public boolean setStatusToActive() throws InterruptedException {
		return setUserStatus("Active");
	}

	public boolean setPaymentAcceptanceToFalse() throws InterruptedException {
		setUserStatus("Active");
		return performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
	}

	public boolean setPaymentAcceptanceToTrue() throws InterruptedException {
		setUserStatus("Active");
		return performToggleAction("true", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
	}

	public boolean setAllowExitToFalse() throws InterruptedException {
		setUserStatus("Active");
		performToggleAction("true", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		return performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, null);
	}

	public boolean setAllowExitToTrue() throws InterruptedException {
		setUserStatus("Active");
		performToggleAction("true", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		return performToggleAction("true", allowExitToggleStatus, allowExitToggleSwitch, null);
	}

	private boolean setUserStatus(String desiredStatus) throws InterruptedException {
		setEmailAddressAndSearch("statusChange@parkmate.com");

		if (!desiredStatus.equalsIgnoreCase(userStatus.getText())) {
			dropdownButton.click();
			statusChangeBtn.click();
			if ("Restricted".equalsIgnoreCase(desiredStatus)) {
				Thread.sleep(1000);
				proceedBtn.click();
			}
			Thread.sleep(5000);
		} else {
			System.out.println(userStatus.getText());
		}

		return userStatus.getText().equalsIgnoreCase(desiredStatus);
	}

	private boolean performToggleAction(String desiredStatus, WebElement toggleStatus, WebElement toggleSwitch,
			WebElement proceedButton) throws InterruptedException {

		String currentStatus = toggleStatus.getAttribute("aria-checked");
		if (!desiredStatus.equals(currentStatus)) {
			toggleSwitch.click();
			Thread.sleep(1000);
			if (proceedButton != null) {
				Thread.sleep(2000);
				proceedButton.click();
				Thread.sleep(2000);
			}

		} else {
			System.out.println(currentStatus);
		}
		return toggleStatus.getAttribute("aria-checked").equalsIgnoreCase(desiredStatus);
	}

	public boolean allowExitValidation() throws InterruptedException {
		return validateButtonIsDisplayed(markCompleteBtn, "SYNCH02");
	}

	public boolean PaymentAcceptanceSetToTrueLoginValidation() throws InterruptedException {
		return validateButtonIsDisplayed(receiveParkingPaymentBtn, "SYNCH03");
	}
	
	private boolean validateButtonIsDisplayed(WebElement button, String vehicleNumber) throws InterruptedException {

		encoderSearch.sendKeys(vehicleNumber);
		Thread.sleep(500);
		searchBtn.click();
		waitForWebElementToAppear(encoderSearchResult);

		try {
			boolean isDisplayed = button.isDisplayed();
			Thread.sleep(500);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			System.err.println("Button is not displayed: " + e.getMessage());
			return false;
		}
	}

	public boolean loginValidationForStatusChange() {

		return loginValidation(
				"Oops! You are blocked from accessing this page. To gain access, contact your admin for support.");

	}

	public boolean PaymentAcceptanceSetToFalseLoginValidation() {

		return loginValidation("Your account has been restricted. Please contact our support team for assistance.");
	}

	private boolean loginValidation(String expectedErrorMessage) {
		try {
		String actualErrorMessage = errorMessage.getText();
		return actualErrorMessage.equalsIgnoreCase(expectedErrorMessage);
		} catch (NoSuchElementException e) {
			System.err.println("Button is not displayed: " + e.getMessage());
			return false;
		}
	}

}
