package automation.AdminTest;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.AdminTestComponents.Retry;
import automation.PageObject.Pagenation;
import automation.PageObject.UserModule;

public class UserModuleTest extends BaseTest {

	String emailAddress = "parkmatehub." + generateRandomNumber(5)+ "@parkmate.com";
	String mobileNumber = "+6399"+generateRandomNumber(8);
	String emailToSearch = "forAutomationEdit@parkmate.com";
	
	@Test(priority = 1, retryAnalyzer=Retry.class, groups = { "Creation" })
	public void adminEnrollment() throws InterruptedException, UnsupportedFlavorException, IOException {

		UserModule parkingUser = loginToApplication();
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

	@Test(priority = 2, groups = { "Creation" })
	public void encoderEnrollment() throws InterruptedException, UnsupportedFlavorException, IOException {

		UserModule parkingUser = loginToApplication();
		parkingUser.clickEnroll();
		String userRole = parkingUser.selectEncoderRole();
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

	@Test( groups = { "ErrorHandling" })
	public void userEmailDupValidation() throws InterruptedException {

		UserModule parkingUser = loginToApplication();
		String existingEmail = parkingUser.getRandomEmail();
		parkingUser.clickEnroll();
		parkingUser.getEmailDuplicate(existingEmail);
		Assert.assertTrue(parkingUser.isDuplicateEmail());
		Assert.assertTrue(parkingUser.isDuplicateUserName());
	}

	@Test
	public void exitEnrollmentAlert() throws InterruptedException {

		UserModule parkingUser = loginToApplication();
		parkingUser.clickEnroll();
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		Assert.assertTrue(parkingUser.exitEnrollmentAlert());

	}

	@Test(priority = 3)//, retryAnalyzer = Retry.class)
	public void editUserAccount() throws InterruptedException {
		
		UserModule parkingUser = loginToApplication();
		Assert.assertTrue(parkingUser.updateUserAccount(emailToSearch));
		
	}

	@Test
	public void userPageSelectRow() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		Pagenation selectRow = new Pagenation(driver);
		boolean tableCount = selectRow.selectRowCount();
		Assert.assertTrue(tableCount);

	}

	@Test
	public void userPagePagenation() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		Pagenation page = new Pagenation(driver);
		boolean nextBtnDisabled = page.nextButton();
		Assert.assertTrue(nextBtnDisabled);
		boolean PreviousBtnDisabled = page.previousButton();
		Assert.assertTrue(PreviousBtnDisabled);
	}
	@Test
	public void getCopyUserName() throws InterruptedException, UnsupportedFlavorException, IOException {
		
		UserModule parkingUser = loginToApplication();
		parkingUser.performSearch("forAutomationEdit@parkmate.com");
		Assert.assertTrue(parkingUser.getCopyUserName());

	}
	@Test
	public void getCopyUserPassword() throws InterruptedException, UnsupportedFlavorException, IOException {
		
		UserModule parkingUser = loginToApplication();
		parkingUser.performSearch("forAutomationEdit@parkmate.com");
		parkingUser.generatePassword();
		Assert.assertTrue(parkingUser.getCopyPassword());

	}
	// handle the login and navigation steps
	private UserModule loginToApplication() throws InterruptedException {
		UserModule parkingUser = landingPage.loginApplication("riztest", "Password@1");
		parkingUser.userPage();

		return parkingUser;
	}

	

}
