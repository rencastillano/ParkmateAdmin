package automation.PageObject;

import org.openqa.selenium.JavascriptExecutor;
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

	@FindBy(xpath = "//div/div/form/div[2]/button")
	WebElement searchBtn;

	@FindBy(xpath = "(//tr/td)[3]")
	WebElement accountSearchResult;
	
	@FindBy(xpath="//div[1]/button/div[2]/div[2]")
	WebElement ticketSearchResult;

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

	@FindBy(name = "search")
	WebElement encoderSearch;
	
	@FindBy(xpath="//section/div[1]/button[2]")
	WebElement viewParkedVehiclesTab;

	@FindBy(xpath = "//button[.='Search Vehicle']")
	WebElement searchVehicleButton;
	
	@FindBy(css="div.bg-white.mb-2.text-center.rounded-t-2xl.py-5 > div")
	WebElement ticketTagStatus;

	@FindBy(css = "h1")
	WebElement encoderSearchResult;

	@FindBy(xpath = "//section/div[2]/div[2]/section/button")
	WebElement markCompleteBtn;

	@FindBy(xpath = "//*[text()='Receive Parking Payment']")
	WebElement receiveParkingPaymentBtn;
	
	@FindBy(name="username")
	WebElement uname;
	
	@FindBy(name="password")
	WebElement pword;
	
	@FindBy(css=".btn")
	WebElement loginBtn;
	
	@FindBy(xpath="//div[3]/button/img")
	WebElement logout;
	
	
	public void loginToParkingAdmin(String userName, String password) throws InterruptedException {
		logout.click();
		waitForWebElementToAppear(loginBtn);
		uname.clear();
		uname.sendKeys(userName);
		Thread.sleep(500);
		pword.clear();
		pword.sendKeys(password);
		loginBtn.click();
		
	}

	public void navigateToEncoderDesktop() {

		try {

			openNewTabAndSwitch();
			driver.get("https://encoder.parking-stg.smop.asia/login/");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void navigateToEncoderMobileApp() {

		try {

			openNewTabAndSwitch();
			mobileAppSettings();
			driver.get("https://encoder.parking-stg.smop.asia/login/");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void openNewTabAndSwitch() {
		((JavascriptExecutor) driver).executeScript("window.open();");
		// Switch to the newly opened tab
		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
	}

	public void loginToEncoderApp(String userName, String pword) {

		username.sendKeys(userName);
		password.sendKeys(pword);
		login.click();
	}

	private void getEmailAddressAndSearch(String emailAddress) throws InterruptedException {
		search.sendKeys(emailAddress);
		String result;
		do {
			Thread.sleep(1000);
			result = accountSearchResult.getText();
		} while (!result.equalsIgnoreCase(emailAddress));
	}
	
	public boolean setAdminStatusToRestricted(String emailToSearch) throws InterruptedException {
		return setUserStatus("Restricted", emailToSearch);
	}

	public boolean setStatusToRestricted(String emailToSearch) throws InterruptedException {
		boolean setAcountStatus = setUserStatus("Restricted", emailToSearch);
		performToggleAction("true", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, null);
		return setAcountStatus;
	}

	public boolean setStatusToActiveForMobile(String emailToSearch) throws InterruptedException {
		boolean setAcountStatus = setUserStatus("Active", emailToSearch);
		performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, null);
		return setAcountStatus;
	}

	public boolean setStatusToActiveForDestop(String emailToSearch) throws InterruptedException {
		boolean setAcountStatus = setUserStatus("Active", emailToSearch);
		performToggleAction("true", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, null);
		return setAcountStatus;
	}

	public boolean setPaymentAcceptanceToFalse(String emailToSearch) throws InterruptedException {
		setUserStatus("Active", emailToSearch);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, null);
		return performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
	}

	public boolean setPaymentAcceptanceToTrue(String emailToSearch) throws InterruptedException {
		setUserStatus("Active", emailToSearch);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, null);
		return performToggleAction("true", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
	}

	public boolean setAllowExitToFalse(String emailToSearch) throws InterruptedException {
		setUserStatus("Active", emailToSearch);
		performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		return performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, null);
	}

	public boolean setAllowExitToTrue(String emailToSearch) throws InterruptedException {
		setUserStatus("Active", emailToSearch);
		performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		return performToggleAction("true", allowExitToggleStatus, allowExitToggleSwitch, null);
	}

	public boolean setUserStatus(String desiredStatus, String emailToSearch) throws InterruptedException {
		getEmailAddressAndSearch(emailToSearch);

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
		return validateButtonIsDisplayed(markCompleteBtn, ticketSearchResult, "SYNCH02");
	}

	public boolean PaymentAcceptanceSetToTrueLoginValidation() throws InterruptedException {
		return validateButtonIsDisplayed(receiveParkingPaymentBtn, null, "SYNCH03");
	}

	private boolean validateButtonIsDisplayed(WebElement button, WebElement searchResult, String vehicleNumber) throws InterruptedException {
		Thread.sleep(3000);
//		if(viewParkedVehicles != null) {
//			viewParkedVehicles.click();
//			Thread.sleep(2000);
//		}
		encoderSearch.sendKeys(vehicleNumber, Keys.ENTER);
		Thread.sleep(3000);
		//waitForWebElementToAppear(encoderSearchResult);
		
		if(searchResult != null) {
			searchResult.click();
			
		}
		
		try {
			waitForWebElementToAppear(ticketTagStatus);
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
