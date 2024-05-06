package automation.AdminTest;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.AdminTestComponents.Retry;
import automation.PageObject.Pagenation;
import automation.PageObject.UserModule;

public class UserModuleTest extends BaseTest {

	String emailAddress = "parkmatehub." + generateRandomNumber(5)+ "@parkmate.com";
	String mobileNumber = "+6399"+generateRandomNumber(8);
	String emailToSearch = "forAutomationEdit@parkmate.com";
	
	@Test(priority = 1, retryAnalyzer=Retry.class, groups = { "Creation" },dataProvider = "getData")
	public void adminEnrollment(HashMap<String,String> input) throws InterruptedException, UnsupportedFlavorException, IOException {

		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		parkingUser.clickEnroll();
		String userRole = parkingUser.selectAdminRole();
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		String email = parkingUser.getEmailDetails(emailAddress);
		parkingUser.getMobileDetails(mobileNumber);
		Assert.assertTrue(parkingUser.getUsername(email));
		parkingUser.generatePassword();
		parkingUser.getParkingStation();
		parkingUser.clickSave();
		Assert.assertTrue(parkingUser.validateBanner(userRole));
		Assert.assertTrue(parkingUser.enrollmentValidation(email, userRole));

	}

	@Test(priority = 2, groups = { "Creation" },dataProvider = "getData")
	public void encoderEnrollment(HashMap<String,String> input) throws InterruptedException, UnsupportedFlavorException, IOException {

		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		parkingUser.clickEnroll();
		String userRole = "Encoder";
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		String email = parkingUser.getEmailDetails(emailAddress);
		parkingUser.getMobileDetails(mobileNumber);
		Assert.assertTrue(parkingUser.getUsername(email));
		parkingUser.generatePassword();
		parkingUser.getParkingStation();
		parkingUser.clickSave();
		Assert.assertTrue(parkingUser.validateBanner(userRole));
		Assert.assertTrue(parkingUser.enrollmentValidation(email, userRole));

	}
	
	@Test(priority = 2, groups = { "Creation" },dataProvider = "getData")
	public void cashierEnrollment(HashMap<String,String> input) throws InterruptedException, UnsupportedFlavorException, IOException {

		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		parkingUser.clickEnroll();
		String userRole = parkingUser.selectCashierRole();
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		String email = parkingUser.getEmailDetails(emailAddress);
		parkingUser.getMobileDetails(mobileNumber);
		Assert.assertTrue(parkingUser.getUsername(email));
		parkingUser.generatePassword();
		parkingUser.getParkingStation();
		parkingUser.clickSave();
		Assert.assertTrue(parkingUser.validateBanner(userRole));
		Assert.assertTrue(parkingUser.enrollmentValidation(email, userRole));

	}
	
	@Test(priority = 2, groups = { "Creation" },dataProvider = "getData")
	public void managerEnrollment(HashMap<String,String> input) throws InterruptedException, UnsupportedFlavorException, IOException {

		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		parkingUser.clickEnroll();
		String userRole = parkingUser.selectManagerRole();
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		String email = parkingUser.getEmailDetails(emailAddress);
		parkingUser.getMobileDetails(mobileNumber);
		Assert.assertTrue(parkingUser.getUsername(email));
		parkingUser.generatePassword();
		parkingUser.getParkingStation();
		parkingUser.clickSave();
		Assert.assertTrue(parkingUser.validateBanner(userRole));
		Assert.assertTrue(parkingUser.enrollmentValidation(email, userRole));

	}

	@Test(groups = { "ErrorHandling" },dataProvider = "getData")
	public void userEmailDupValidation(HashMap<String,String> input) throws InterruptedException {

		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		String existingEmail = parkingUser.getRandomEmail();
		parkingUser.clickEnroll();
		parkingUser.getEmailDuplicate(existingEmail);
		Assert.assertTrue(parkingUser.isDuplicateEmail());
		Assert.assertTrue(parkingUser.isDuplicateUserName());
	}

	@Test(dataProvider = "getData")
	public void exitEnrollmentAlert(HashMap<String,String> input) throws InterruptedException {

		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		parkingUser.clickEnroll();
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		Assert.assertTrue(parkingUser.exitEnrollmentAlert());

	}

	@Test(priority = 3, dataProvider = "getData")//, retryAnalyzer = Retry.class)
	public void editUserAccount(HashMap<String,String> input) throws InterruptedException {
		
		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(parkingUser.updateUserAccount(emailToSearch));
		
	}

	@Test
	public void userPageSelectRow() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password@1");
		Pagenation selectRow = new Pagenation(driver);
		boolean tableCount = selectRow.selectRowCount();
		Assert.assertTrue(tableCount);

	}

	@Test
	public void userPagePagenation() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password@1");
		Pagenation page = new Pagenation(driver);
		boolean nextBtnDisabled = page.nextButton();
		Assert.assertTrue(nextBtnDisabled);
		boolean PreviousBtnDisabled = page.previousButton();
		Assert.assertTrue(PreviousBtnDisabled);
	}
	@Test(dataProvider = "getData")
	public void getCopyUserName(HashMap<String,String> input) throws InterruptedException, UnsupportedFlavorException, IOException {
		
		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		parkingUser.performSearch(emailToSearch);
		Assert.assertTrue(parkingUser.getCopyUserName());

	}
	@Test(dataProvider = "getData")
	public void getCopyUserPassword(HashMap<String,String> input) throws InterruptedException, UnsupportedFlavorException, IOException {
		
		UserModule parkingUser = loginToApplication(input.get("username"), input.get("password"));
		parkingUser.performSearch(emailToSearch);
		parkingUser.generatePassword();
		Assert.assertTrue(parkingUser.getCopyPassword());

	}
	// handle the login and navigation steps
	private UserModule loginToApplication(String username, String password) throws InterruptedException {
		UserModule parkingUser = landingPage.loginApplication(username, password);
		parkingUser.userPage();

		return parkingUser;
	}
	
	@DataProvider
	private Object[] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\automation\\AdminData\\AdminData.json");
		return new Object[] { data.get(0) };

	}

	

}
