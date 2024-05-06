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

	@FindBy(css = "#loginForm > button")//.btn
	WebElement login;

	@FindBy(css = "[class*='text-base']")
	WebElement errorMsg;

	@FindBy(xpath = "//div/div/form/div[2]/button")
	WebElement searchBtn;

	@FindBy(xpath = "(//tr/td)[3]")
	WebElement accountSearchResult;

	@FindBy(xpath = "//div[1]/button/div[2]/div[2]")
	WebElement ticketSearchResult;

	@FindBy(xpath = "//*[@class='text-base mb-6 text-sm-error']")
	WebElement errorMessage;
	
	@FindBy(xpath="//tr/td[7]/div")
	WebElement captureVehicleToggleStatus;
	
	@FindBy(xpath="//td[7]/div/label/div")
	WebElement captureVehicleToggleSwitch;

	@FindBy(xpath = "//tr/td[8]/div")
	WebElement paymentAcceptanceToggleStatus;

	@FindBy(xpath = "//td[8]/div/label/div")
	WebElement paymentAcceptanceToggleSwitch;

	@FindBy(xpath = "//tr/td[9]/div")
	WebElement allowExitToggleStatus;

	@FindBy(xpath = "//td[9]/div/label/div")
	WebElement allowExitToggleSwitch;

	@FindBy(css = "input[placeholder='Input Parking Details']")//(name = "search")
	WebElement encoderSearch;

	@FindBy(xpath = "//section/div[1]/button[2]")
	WebElement viewParkedVehiclesTab;

	@FindBy(xpath = "//button[.='Search Vehicle']")
	WebElement searchVehicleButton;

	@FindBy(xpath = "//*[@id=\"page-content\"]/section/div[1]/div")
	WebElement ticketTagStatus;

	@FindBy(css = "h1")
	WebElement encoderSearchResult;

	@FindBy(xpath = "//*[@id=\"page-content\"]/section/div[7]/button")
	WebElement markCompleteBtn;

	@FindBy(xpath = "//*[text()='Receive Parking Payment']")
	WebElement receiveParkingPaymentBtn;

	@FindBy(name = "username")
	WebElement uname;

	@FindBy(name = "password")
	WebElement pword;

	@FindBy(css = "#loginForm > button > span")
	WebElement loginBtn;

	@FindBy(xpath = "//button[@class='w-8']//*[name()='svg']")
	WebElement logout;

	public void loginToParkingAdmin(String userName, String password) throws InterruptedException {
		
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
			driver.get("https://encoder.uat.parkmate.ai/login/");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void navigateToEncoderMobileApp() {

		try {

			openNewTabAndSwitch();
			mobileAppSettings();
			driver.get("https://encoder.uat.parkmate.ai/login/");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void openNewTabAndSwitch() {
		((JavascriptExecutor) driver).executeScript("window.open();");
		// Switch to the newly opened tab
		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
	}

	public void loginToEncoderApp(String userName, String pword) throws InterruptedException {
		Thread.sleep(3500);
		username.sendKeys(userName);
		Thread.sleep(500);
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
		boolean restrictedStatus = setUserStatus("Restricted", emailToSearch);
		logout.click();
		return restrictedStatus;
	}

	public boolean setStatusToRestricted(String emailToSearch) throws InterruptedException {
		boolean setAcountStatus = setUserStatus("Restricted", emailToSearch);
		performToggleAction("true", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, null);
		return setAcountStatus;
	}

	public boolean setStatusToActiveForMobile(String emailToSearch) throws InterruptedException {
		boolean setAcountStatus = setUserStatus("Active", emailToSearch);
		performToggleAction("true", captureVehicleToggleStatus, captureVehicleToggleSwitch, proceedBtn);
		performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, proceedBtn);//null_proceedBtn
		return setAcountStatus;
	}
	
	public boolean setCaptureVehicleToTrue(String emailToSearch) throws InterruptedException {
		setUserStatus("Active", emailToSearch);
		performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, proceedBtn);
		boolean status = performToggleAction("true", captureVehicleToggleStatus, captureVehicleToggleSwitch, proceedBtn);
		Thread.sleep(2000);
		return status;
	}

	public boolean setPaymentAcceptanceToFalse(String emailToSearch) throws InterruptedException {
		setUserStatus("Active", emailToSearch);
		performToggleAction("false", captureVehicleToggleStatus, captureVehicleToggleSwitch, proceedBtn);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, proceedBtn);
		boolean status = performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		Thread.sleep(2000);
		return status;
	}

	public boolean setPaymentAcceptanceToTrue(String emailToSearch) throws InterruptedException {
		setUserStatus("Active", emailToSearch);
		performToggleAction("false", captureVehicleToggleStatus, captureVehicleToggleSwitch, proceedBtn);
		performToggleAction("false", allowExitToggleStatus, allowExitToggleSwitch, proceedBtn);
		boolean status = performToggleAction("true", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		Thread.sleep(3000);
		return status;
	}

	public boolean setAllowExitToTrue(String emailToSearch) throws InterruptedException {
		setUserStatus("Active", emailToSearch);
		performToggleAction("false", captureVehicleToggleStatus, captureVehicleToggleSwitch, proceedBtn);
		performToggleAction("false", paymentAcceptanceToggleStatus, paymentAcceptanceToggleSwitch, proceedBtn);
		boolean status = performToggleAction("true", allowExitToggleStatus, allowExitToggleSwitch, proceedBtn);
		Thread.sleep(2000);
		return status;
	}

	public boolean setUserStatus(String desiredStatus, String emailToSearch) throws InterruptedException {
		getEmailAddressAndSearch(emailToSearch);

		if (!desiredStatus.equalsIgnoreCase(userStatus.getText())) {
			dropdownButton.click();
			Thread.sleep(500);
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
			proceedButton.click();
			Thread.sleep(2000);
//			if (proceedButton != null) {
//				Thread.sleep(2000);
//				proceedButton.click();
//				Thread.sleep(2000);
//			}

		} else {
			System.out.println(currentStatus);
		}
		return toggleStatus.getAttribute("aria-checked").equalsIgnoreCase(desiredStatus);
	}
	
	public boolean validationForEntryEncoder() throws InterruptedException {
		waitForWebElementToAppear(viewParkedVehiclesTab);
		viewParkedVehiclesTab.click();
		Thread.sleep(2000);
		encoderSearch.sendKeys("SYNCH02", Keys.ENTER);
		waitForWebElementToAppear(ticketSearchResult);
		ticketSearchResult.click();
		waitForWebElementToAppear(ticketTagStatus);
		return encoderSearchResult.isDisplayed();
	}

	public boolean allowExitValidation() throws InterruptedException {
		return validateButtonIsDisplayed(markCompleteBtn, "SYNCH04");
	}
	
	public boolean PaymentAcceptanceSetToTrueLoginValidation() throws InterruptedException {
		
		boolean isDisplayed = receiveParkingPaymentBtn.isEnabled();
		return isDisplayed;
	}

	@FindBy(xpath = "//div[2]/div[2]/div[2]/div[1]/button")
	WebElement searchResult;
	
	private boolean validateButtonIsDisplayed(WebElement button,
			String vehicleNumber) throws InterruptedException {
		Thread.sleep(3000);
		encoderSearch.sendKeys(vehicleNumber, Keys.ENTER);
		Thread.sleep(3000);
		// waitForWebElementToAppear(encoderSearchResult);

		if (searchResult != null) {
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

	public boolean loginValidationForStatusChangeAndChangeRole() {

		return loginValidation(
				"Oops! You are blocked from accessing this page. To gain access, contact your admin for support.");

	}

	public boolean paymentAcceptanceSetToFalseLoginValidation() {

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
