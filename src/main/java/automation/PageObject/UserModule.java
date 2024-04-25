package automation.PageObject;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;

public class UserModule extends AbstractComponent {

	WebDriver driver;

	public UserModule(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory
	@FindBy(linkText = "ENROLL")
	WebElement enroll;

	@FindBy(css = "h2[class*='pt-1']")
	WebElement usersTab;

	@FindBy(css = ".flex.capitalize")
	WebElement roleDropdown;

	@FindBy(xpath = "//div[1]/button[2]/div")
	WebElement adminRole;

	@FindBy(name = "firstName")
	WebElement fname;

	@FindBy(name = "middleInitial")
	WebElement mname;

	@FindBy(name = "lastName")
	WebElement lname;

	@FindBy(name = "email")
	WebElement email;

	@FindBy(name = "mobileNo")
	WebElement mobile;

	@FindBy(css = "input[name='username']")
	WebElement userName;

	@FindBy(name = "password")
	WebElement pword;

	@FindBy(xpath = "(//*[name()='svg'][@class='w-6 h-auto'])[1]")
	WebElement generate;

	@FindBy(css = "img[alt='Copy']")
	WebElement copyPassword;
	
	@FindBy(xpath="//button[normalize-space()='COPY']")
	WebElement copyUserNameBtn;

	@FindBy(css = "div:nth-child(4) > div > div > button")
	WebElement selectArea;

	@FindBy(css = "//li/button")
	List<WebElement> parkingAreas;

	@FindBy(css = "ul[role='menu'] li")
	List<WebElement> areaStation;

	@FindBy(xpath = "//button[normalize-space()='SAVE']")
	WebElement saveBtn;

	@FindBy(css = "div:nth-child(2) > p.mt-1.font-henry-sans-light.text-xs.text-sm-error")
	WebElement duplicateEmailErrMsg;

	@FindBy(css = "div:nth-child(1) > p.mt-1.font-henry-sans-light.text-xs.text-sm-error")
	WebElement duplicateUserNameErrMsg;

	@FindBy(xpath = "//div[2]/div[3]/button")
	WebElement backButton;

	@FindBy(css = ".w-full > .flex > .ml-auto")
	WebElement cancelMondalButton;

	@FindBy(css = ".flex > .mr-auto")
	WebElement proceedModalButton;

	@FindBy(xpath = "//tr/td[3][@class='td-item pt-4 pb-4 svelte-12oyxwx']")
	List<WebElement> userEmailList;

	@FindBy(css = "input[placeholder='Search']")
	WebElement search;

	@FindBy(css = "tr td:nth-child(3)")
	List<WebElement> emailAddressTable;

	@FindBy(css = "tbody tr:nth-child(1)")
	WebElement firstDataRow;

	//@FindBy(xpath = "//*[@class='flex-grow text-sm self-center w-3/4']")
	@FindBy(css=".flex-grow.col-span-3.text-sm.self-center.pr-5")
	WebElement banner;

	By firstUserEmail = By.xpath("(//tr/td[3])[1]");

	@FindBy(xpath = "(//tr/td)[3]")
	WebElement userEmailSearched;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(css = "tr:nth-child(1) > td:nth-child(2)")
	WebElement userRole;

	@FindBy(css = "tr:nth-child(1) > td:nth-child(3)")
	WebElement newEmailAddress;

	@FindBy(xpath = "//div[4]/div/div/ul")
	WebElement stationDataList;

	public Permissions userPage() {
		waitForWebElementToAppear(smLogo);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Permissions permission = new Permissions(driver);
		
		return permission;
	}

	public String selectAdminRole() {
		return selectRole("admin");
	}

//	public String selectEncoderRole() {
//		return selectRole("encoder");
//	}
	
	public String selectCashierRole() {
		return selectRole("cashier");
	}
	
	public String selectManagerRole() {
		return selectRole("manager");
	}

	private String selectRole(String role) {
		roleDropdown.click();
		// Click on the specified role
		WebElement selectedRole = driver.findElement(By.xpath("//*[text()='" + role + "']"));
		selectedRole.click();

		return roleDropdown.getText();
	}

	public void clickEnroll() {
		enroll.click();
	}

	public void getPersonalDetails(String firstName, String middleName, String LastName) {

		fname.sendKeys(firstName);
		mname.sendKeys(middleName);
		lname.sendKeys(LastName);

	}
	public void getEmailDuplicate(String emailAddress) throws InterruptedException {
		
		Thread.sleep(3000);
		email.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, emailAddress);
		//waitForWebElementToAppear(duplicateEmailErrMsg);
		mobile.clear();
	}

	public String getEmailDetails(String emailAddress) throws InterruptedException {
	    Thread.sleep(3000);
	    email.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, emailAddress);

	    try {
	        if (duplicateEmailErrMsg.isDisplayed()) {
	            return emailDuplicateValidator();
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception or handle it appropriately
	    }

	    return email.getAttribute("value");
	}

	private String emailDuplicateValidator() throws InterruptedException {
	    String forDupEmail = "parkmatehub." + generateRandomNumber(5) + "@parkmate.com";
	    try {
	        while (duplicateEmailErrMsg.isDisplayed()) {
	            email.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, forDupEmail);
	            Thread.sleep(4000);
	            System.out.println("dup email: " + forDupEmail);
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception or handle it appropriately
	    }

	    return email.getAttribute("value");
	}

	public void getMobileDetails(String mobileNumber) {
		mobile.clear();
		mobile.sendKeys(mobileNumber);

	}

	public boolean getUsername(String email) {
		String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", userName);
		String[] sliptname = email.split("@");
		return value.equalsIgnoreCase(sliptname[0]);

	}

	public void generatePassword() throws UnsupportedFlavorException, IOException {
		waitForWebElementToBeClickable(generate);
		generate.click();
	}
	
	public boolean getCopyUserName() throws UnsupportedFlavorException, IOException, InterruptedException {
		Thread.sleep(3000);
	    return getCopyValueAndCompare(userName, copyUserNameBtn);
	}

	public boolean getCopyPassword() throws UnsupportedFlavorException, IOException, InterruptedException {
		Thread.sleep(1000);
	    return getCopyValueAndCompare(password, copyPassword);
	}
	
	private boolean getCopyValueAndCompare(WebElement element, WebElement copyButton) throws UnsupportedFlavorException, IOException, InterruptedException {
		do {
	        Thread.sleep(1000);
	    } while (!email.getAttribute("value").equalsIgnoreCase("forAutomationEdit@parkmate.com"));

	    String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", element);

	    // Copy value using Toolkit
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
	    
	    copyButton.click();
	    Thread.sleep(1000);

	    return value.equals(getTextFromClipboard());
	}

	// Helper method to retrieve text from clipboard
	private String getTextFromClipboard() throws UnsupportedFlavorException, IOException {
		return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
	}


	public void getParkingStation() throws InterruptedException {

		do {
			Thread.sleep(1000);
			selectArea.click();
		} while (!stationDataList.isDisplayed());

		Random random = new Random();
		int excludedStart = 0;
		int excludedEnd = 5;

		int randomIndex;
		do {
			randomIndex = random.nextInt(areaStation.size());
		} while (randomIndex >= excludedStart && randomIndex <= excludedEnd);

		WebElement station = areaStation.get(randomIndex);
		Thread.sleep(1000);
		station.click();
	}

	public void clickSave() {
		saveBtn.click();

	}

	public boolean isDuplicateEmail() {
	    return isDuplicateMessageShown(duplicateEmailErrMsg, "Email already exists.");
	}

	public boolean isDuplicateUserName() {
	    return isDuplicateMessageShown(duplicateUserNameErrMsg, "Username already exists.");
	}

	private boolean isDuplicateMessageShown(WebElement element, String expectedMessage) {
	    waitForWebElementToAppear(element);
	    String message = element.getText();
	    return message.equalsIgnoreCase(expectedMessage);
	}


	public boolean exitEnrollmentAlert() throws InterruptedException {
		backButton.click();
		Thread.sleep(500);
		cancelMondalButton.click();
		Thread.sleep(500);
		getParkingStation();
		Thread.sleep(500);
		backButton.click();
		Thread.sleep(500);
		proceedModalButton.click();
		waitForWebElementToAppear(enroll);
		return enroll.isDisplayed();
	}
	
	public void performSearch(String searchData) throws InterruptedException {
		search.sendKeys(searchData);
		String result;
		do {
			Thread.sleep(3000);
			result = userEmailSearched.getText();
		} while (!result.equalsIgnoreCase(searchData));
		
		firstDataRow.click();
	}

	public boolean updateUserAccount(String emailToSearch) throws InterruptedException {

		performSearch(emailToSearch);
		do {
	        Thread.sleep(1000);
	    } while (!email.getAttribute("value").equalsIgnoreCase(emailToSearch));
		getMobileDetails("+6399"+generateRandomNumber(8));
		Thread.sleep(3000);
		saveBtn.click();
		waitForWebElementToAppear(banner);
		return banner.getText().equalsIgnoreCase(emailToSearch+ " is successfully updated!");

	}

	public boolean validateBanner(String userRole) {
		waitForWebElementToAppear(banner);
		return banner.getText().equalsIgnoreCase(userRole + " successfully created!");
	}

	public String getRandomEmail() throws InterruptedException {
		waitForElementToAppear(firstUserEmail);
		Thread.sleep(3000);
		Random random = new Random();

		// Define the excluded index
		int excludedIndex = 5;

		int randomIndex;
		do {
			randomIndex = random.nextInt(emailAddressTable.size());
		} while (randomIndex == excludedIndex);

		WebElement station = emailAddressTable.get(randomIndex);
		return station.getText();

	}

	public boolean enrollmentValidation(String email, String expectedRole) {
		// System.out.println(userRole.getText());
		// System.out.println(newEmailAddress.getText());
		return userRole.getText().equals(expectedRole) && newEmailAddress.getText().equals(email);
	}

}
