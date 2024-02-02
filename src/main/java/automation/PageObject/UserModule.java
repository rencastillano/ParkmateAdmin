package automation.PageObject;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
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

	@FindBy(css = "#roleDropdownButton")
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

	@FindBy(name = "username")
	WebElement userName;

	@FindBy(name = "password")
	WebElement pword;

	@FindBy(css = "img[alt='Generate']")
	WebElement generate;

	@FindBy(css = "img[alt='Copy']")
	WebElement copyPassword;
	
	@FindBy(xpath="//div[3]/div/div/div[1]/div/div/button")
	WebElement copyUserNameBtn;

	@FindBy(css = "div:nth-child(4) > div > div > button")
	WebElement selectArea;

	@FindBy(css = "//li/button")
	List<WebElement> parkingAreas;

	@FindBy(css = "ul[role='menu'] li")
	List<WebElement> areaStation;

	@FindBy(xpath = "//div/div/button[.='SAVE']")
	WebElement saveBtn;

	@FindBy(css = "div:nth-child(2) > p.mt-1.font-henry-sans-light.text-xs.text-sm-error")
	WebElement duplicateEmail;

	@FindBy(css = "div:nth-child(1) > p.mt-1.font-henry-sans-light.text-xs.text-sm-error")
	WebElement duplicateUserName;

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

	@FindBy(xpath = "//*[@class='flex-grow text-sm self-center w-3/4']")
	WebElement banner;

	By firstUserEmail = By.xpath("(//tr/td[3])[1]");

	@FindBy(css = "tr td:first-child")
	WebElement firstRowData;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(css = "tr:nth-child(1) > td:nth-child(2)")
	WebElement userRole;

	@FindBy(css = "tr:nth-child(1) > td:nth-child(3)")
	WebElement newEmailAddress;

	@FindBy(xpath = "//div[4]/div/div/ul")
	WebElement stationDataList;

	public boolean userPage() {
		waitForWebElementToAppear(smLogo);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean utab = usersTab.isDisplayed();
		return utab;
	}

	public String selectAdminRole() {
		return selectRole("admin");
	}

	public String selectEncoderRole() {
		return selectRole("encoder");
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

	public String getEmailDetails(String emailAddress) throws InterruptedException {
	    Thread.sleep(3000);
	    email.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, emailAddress);

	    try {
	        if (duplicateEmail.isDisplayed()) {
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
	        while (duplicateEmail.isDisplayed()) {
	            email.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, forDupEmail);
	            Thread.sleep(3000);
	            System.out.println("dup email: " + forDupEmail);
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception or handle it appropriately
	    }

	    return email.getAttribute("value");
	}




	public void getMobileDetails() {
		mobile.click();
		mobile.sendKeys("987654321");

	}

	public boolean getUsername(String email) {
		String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", userName);
		String[] sliptname = email.split("@");
		return value.equalsIgnoreCase(sliptname[0]);

	}

	public void generatePassword() throws UnsupportedFlavorException, IOException {
		
		generate.click();
	}
	
	public boolean getCopyUserName() throws UnsupportedFlavorException, IOException, InterruptedException {
	    return getCopyValueAndCompare(userName, copyUserNameBtn);
	}

	public boolean getCopyPassword() throws UnsupportedFlavorException, IOException, InterruptedException {
	    return getCopyValueAndCompare(password, copyPassword);
	}
	
	private boolean getCopyValueAndCompare(WebElement element, WebElement copyButton) throws UnsupportedFlavorException, IOException, InterruptedException {
	    Thread.sleep(5000);
	    String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", element);
	    copyButton.click();
	    Thread.sleep(2000);
	    return value.equals(getTextFromClipboard());
	}

	// Helper method to retrieve text from clipboard
	private static String getTextFromClipboard() throws UnsupportedFlavorException, IOException {
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    return (String) clipboard.getData(DataFlavor.stringFlavor);
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

	public boolean duplicateEmail() {
		waitForWebElementToAppear(duplicateEmail);
		String dup = duplicateEmail.getText();
		return dup.equalsIgnoreCase("Email already exists.");

	}

	public boolean duplicateUserName() {
		waitForWebElementToAppear(duplicateUserName);
		String dup = duplicateUserName.getText();
		return dup.equalsIgnoreCase("Username already exists.");

	}

	public boolean exitEnrollmentAlert() throws InterruptedException {
		backButton.click();
		cancelMondalButton.click();
		getParkingStation();
		backButton.click();
		proceedModalButton.click();
		waitForWebElementToAppear(enroll);
		return enroll.isDisplayed();
	}

	public void performSearch(String searchData, String searchResult) throws InterruptedException {
		search.sendKeys(searchData);
		String result;
		do {
			Thread.sleep(3000);
			result = firstRowData.getText();
		} while (!result.equalsIgnoreCase(searchResult));
		
		firstDataRow.click();
	}

	public boolean userAccountUpdate() throws InterruptedException {

		performSearch("forAutomationEdit@parkmate.com", "Automation EditTesting");
		Thread.sleep(5000);
		getParkingStation();
		saveBtn.click();
		waitForWebElementToAppear(banner);
		String bannerText = banner.getText();
		return bannerText.equalsIgnoreCase("forAutomationEdit@parkmate.com is successfully updated!");

	}

	public boolean bannerValidation(String userRole) {
		waitForWebElementToAppear(banner);
		String bannerText = banner.getText();
		// System.out.println(bannerText);
		return bannerText.equalsIgnoreCase(userRole + " successfully created!");
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
