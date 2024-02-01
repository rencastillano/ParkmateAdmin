package automation.PageObject;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

	@FindBy(css = ".font-henry-sans-light.text-sm.pl-5.pb-2.capitalize")
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
	WebElement copy;

	@FindBy(css = "button[class*='px-4']")
	WebElement selectArea;

	@FindBy(css = "//li/button")
	List<WebElement> parkingAreas;

	@FindBy(css = "ul[role='menu'] li")
	List<WebElement> areaStation;

	@FindBy(xpath = "//div/div/button[.='SAVE']")
	WebElement saveBtn;

	@FindBy(xpath = "//*[text()='Email already exists.']")
	WebElement duplicateEmail;

	@FindBy(xpath = "//*[text()='Username already exists.']")
	WebElement duplicateUserName;

	@FindBy(xpath = "//div/button[@class='flex items-center space-x-2 text-blue-500 hover:underline']")
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

		roleDropdown.click();
		adminRole.click();
		return roleDropdown.getText();

	}

	public void clickEnroll() {
		waitForWebElementToAppear(smLogo);
		enroll.click();
	}

	public void getPersonalDetails(String firstName, String middleName, String LastName) {

		fname.sendKeys(firstName);
		mname.sendKeys(middleName);
		lname.sendKeys(LastName);

	}

	public void getEmailDetails(String emailAddress) {
		waitForWebElementToAppear(email);
		email.clear();
		email.sendKeys(emailAddress);

	}

	public void getMobileDetails(String mobileNum) {
		mobile.click();
		mobile.sendKeys(mobileNum);

	}

	public boolean getAccountDetails(String email) {
		String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", userName);
		boolean match = value.equalsIgnoreCase(email);
		generate.click();
		copy.click();
		return match;
	}

	public void getParkingStation() throws InterruptedException {
		waitForWebElementToBeClickable(selectArea);
		selectArea.click();

		Random random = new Random();
		int excludedStart = 0;
		int excludedEnd = 5;

		int randomIndex;
		do {
			randomIndex = random.nextInt(areaStation.size());
		} while (randomIndex >= excludedStart && randomIndex <= excludedEnd);

		WebElement station = areaStation.get(randomIndex);
		Thread.sleep(4000);
		station.click();
	}

	public void clickSave() {
		saveBtn.click();
		waitForWebElementToAppear(usersTab);
	}

	public boolean dupEmail() {
		waitForWebElementToAppear(duplicateEmail);
		String de = duplicateEmail.getText();
		boolean dupEmail = de.equalsIgnoreCase("Email already exists.");
		return dupEmail;

	}

	public boolean dupUserName() {
		waitForWebElementToAppear(duplicateUserName);
		String du = duplicateUserName.getText();
		boolean dupUsername = du.equalsIgnoreCase("Username already exists.");
		return dupUsername;

	}

	public void exitEnrollmentAlert() throws InterruptedException {
		backButton.click();
		cancelMondalButton.click();
		getParkingStation();
		backButton.click();
		proceedModalButton.click();
	}

//	public boolean enrollmentValidation(String email) {
//
//		waitForElementToAppear(firstUserEmail);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<String> filteredEmails = userEmailList.stream().map(WebElement::getText).filter(name -> name.equals(email))
//				.collect(Collectors.toList());
//		String res = filteredEmails.isEmpty() ? "empty" : filteredEmails.get(0);
//		System.out.println(res);
//		boolean result = res.equalsIgnoreCase(email);
//		return result;
//	}
	private void performSearch(String searchData, String searchResult) throws InterruptedException {
		search.sendKeys(searchData);
		String result;
		do {
			Thread.sleep(3000);
			result = firstRowData.getText();
		} while (!result.equalsIgnoreCase(searchResult));
	}

	public boolean userAccountUpdate() throws InterruptedException {

		performSearch("forAutomationEdit@parkmate.com", "Automation EditTesting");
		firstDataRow.click();
		Thread.sleep(5000);
		getParkingStation();
		saveBtn.click();
		waitForWebElementToAppear(banner);
		String bannerText = banner.getText();
		boolean match = bannerText.equalsIgnoreCase("forAutomationEdit@parkmate.com is successfully updated!");
		return match;
	}

	public boolean enrollmentValidation(String email) {
		waitForWebElementToAppear(banner);
		String bannerText = banner.getText();
		System.out.println(bannerText);
		boolean match = bannerText.equalsIgnoreCase(email + " successfully created!");
		return match;
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
		String text = station.getText();
		return text;

	}
}
