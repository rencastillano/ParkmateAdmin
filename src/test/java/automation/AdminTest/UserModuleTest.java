package automation.AdminTest;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.Pagenation;
import automation.PageObject.UserModule;

public class UserModuleTest extends BaseTest {

	String baseEmail = "parkmatehub." + generateRandomNumber(5);
	String completeEmail = baseEmail + "@parkmate.com";
	String forDupEmail = "parkmatehub." + generateRandomNumber(4) + "@parkmate.com";

	@Test(priority = 1, groups = { "Creation" })
	public void adminEnrollment() throws InterruptedException, UnsupportedFlavorException, IOException {

		UserModule parkingUser = landingPage.loginApplication("riztest", "Password@1");
		Assert.assertTrue(parkingUser.userPage());

		parkingUser.clickEnroll();
		String userRole = parkingUser.selectAdminRole();
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		parkingUser.getEmailDetails(completeEmail);
		parkingUser.emailDuplicateValidator(forDupEmail);
		parkingUser.getMobileDetails();
		Assert.assertTrue(parkingUser.getUsername(baseEmail));
		parkingUser.generatePassword();
		parkingUser.getParkingStation();
		parkingUser.clickSave();
		Assert.assertTrue(parkingUser.bannerValidation(userRole));
		Assert.assertTrue(parkingUser.enrollmentValidation(completeEmail, userRole));

	}

	@Test(priority = 2, groups = { "Creation" })
	public void encoderEnrollment() throws InterruptedException, UnsupportedFlavorException, IOException {

		UserModule parkingUser = landingPage.loginApplication("riztest", "Password@1");
		Assert.assertTrue(parkingUser.userPage());

		parkingUser.clickEnroll();
		String userRole = parkingUser.selectEncoderRole();
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		parkingUser.getEmailDetails(completeEmail);
		parkingUser.emailDuplicateValidator(forDupEmail);
		parkingUser.getMobileDetails();
		Assert.assertTrue(parkingUser.getUsername(baseEmail));
		parkingUser.generatePassword();
		parkingUser.getParkingStation();
		parkingUser.clickSave();
		Assert.assertTrue(parkingUser.bannerValidation(userRole));
		Assert.assertTrue(parkingUser.enrollmentValidation(completeEmail, userRole));

	}

	@Test(dataProvider = "getData", groups = { "ErrorHandling" })
	public void userEmailDupValidation(HashMap<String, String> input) throws InterruptedException {
		UserModule parkingUser = landingPage.loginApplication(input.get("username"), input.get("password"));
		parkingUser.userPage();
		String existingEmail = parkingUser.getRandomEmail();

		parkingUser.clickEnroll();
		parkingUser.getEmailDetails(existingEmail);
		Assert.assertTrue(parkingUser.duplicateEmail());
		Assert.assertTrue(parkingUser.duplicateUserName());
	}

	@Test
	public void exitEnrollmentAlert() throws InterruptedException {

		UserModule parkingUser = landingPage.loginApplication("riztest", "Password@1");
		boolean user = parkingUser.userPage();
		Assert.assertTrue(user);

		parkingUser.clickEnroll();
		parkingUser.getPersonalDetails("Sam", "O", "Medina");
		Assert.assertTrue(parkingUser.exitEnrollmentAlert());

	}

	@Test(priority = 3)
	public void editUserAccount() throws InterruptedException {
		UserModule parkingUser = landingPage.loginApplication("riztest", "Password@1");
		boolean user = parkingUser.userPage();
		Assert.assertTrue(user);

		Assert.assertTrue(parkingUser.userAccountUpdate());
		
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
		UserModule parkingUser = landingPage.loginApplication("riztest", "Password@1");
		boolean user = parkingUser.userPage();
		Assert.assertTrue(user);
		
		parkingUser.performSearch("forAutomationEdit@parkmate.com", "Automation EditTesting");
		Assert.assertTrue(parkingUser.getCopyUserName());

	}
	@Test
	public void getCopyUserPassword() throws InterruptedException, UnsupportedFlavorException, IOException {
		UserModule parkingUser = landingPage.loginApplication("riztest", "Password@1");
		boolean user = parkingUser.userPage();
		Assert.assertTrue(user);
		
		parkingUser.performSearch("forAutomationEdit@parkmate.com", "Automation EditTesting");
		parkingUser.generatePassword();
		Assert.assertTrue(parkingUser.getCopyPassword());

	}

	@DataProvider
	public Object[] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\automation\\AdminData\\ParkingData.json");
		return new Object[] { data.get(0) };

	}

}
