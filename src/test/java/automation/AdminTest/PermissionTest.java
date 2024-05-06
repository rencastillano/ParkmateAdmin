package automation.AdminTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.AdminTestComponents.Retry;
import automation.PageObject.Permissions;
import automation.PageObject.UserModule;

public class PermissionTest extends BaseTest {

	@Test(priority = 1, retryAnalyzer=Retry.class, dataProvider = "getData")
	public void adminWithRestrictedStatus(HashMap<String,String> input) throws InterruptedException {

		Permissions permission = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(permission.setAdminStatusToRestricted("henry.salazar@parkmate.com"));
		permission.loginToParkingAdmin("henry.salazar", "Password@1");
		Assert.assertTrue(permission.loginValidationForStatusChangeAndChangeRole());

	}

	@Test(retryAnalyzer=Retry.class, dataProvider = "getData")
	public void encoderWithRestrictedStatusMobileAppLogin(HashMap<String,String> input) throws InterruptedException {

		Permissions permission = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(permission.setStatusToRestricted("statusChange@parkmate.com"));
		navigateAndLoginToEncoderMobileApp(permission);
		Assert.assertTrue(permission.loginValidationForStatusChangeAndChangeRole());

	}

	@Test(retryAnalyzer=Retry.class, dataProvider = "getData")
	public void encoderWithRestrictedStatusDesktopLogin(HashMap<String,String> input) throws InterruptedException {

		Permissions permission = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(permission.setStatusToRestricted("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertTrue(permission.loginValidationForStatusChangeAndChangeRole());

	}

	@Test(retryAnalyzer=Retry.class, dataProvider = "getData")
	public void encoderWithActiveStatus(HashMap<String,String> input) throws InterruptedException {

		Permissions permission = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(permission.setStatusToActiveForMobile("statusChange@parkmate.com"));;
	}
	
	@Test(retryAnalyzer = Retry.class, dataProvider = "getData")
	public void encoderWithNoRole(HashMap<String,String> input) throws InterruptedException {

		Permissions permission = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(permission.setPaymentAcceptanceToFalse("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertTrue(permission.loginValidationForStatusChangeAndChangeRole());
	}
	
	@Test(retryAnalyzer = Retry.class, dataProvider = "getData")
	public void encoderWithCaptureVehicleSetToTrue(HashMap<String,String> input) throws InterruptedException {

		Permissions permission = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(permission.setCaptureVehicleToTrue("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertTrue(permission.validationForEntryEncoder());
	}

	@Test(retryAnalyzer = Retry.class, dataProvider = "getData")
	public void encoderWithPaymentAcceptanceSetToTrue(HashMap<String,String> input) throws InterruptedException {

		Permissions permission = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(permission.setPaymentAcceptanceToTrue("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertTrue(permission.PaymentAcceptanceSetToTrueLoginValidation());
	}

	@Test(retryAnalyzer = Retry.class, dataProvider = "getData")
	public void encoderWithAllowExitToTrue(HashMap<String,String> input) throws InterruptedException {

		Permissions permission = loginToApplication(input.get("username"), input.get("password"));
		Assert.assertTrue(permission.setAllowExitToTrue("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertTrue(permission.allowExitValidation());
	}

	// handle the login and navigation steps
	private Permissions loginToApplication(String username, String password) throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication(username, password);
		Permissions permission = userCreation.userPage();

		return permission;
	}

	private void navigateAndLoginToEncoderDesktop(Permissions permission) throws InterruptedException {
		permission.navigateToEncoderDesktop();
		permission.loginToEncoderApp("statusChange", "Password@1");

	}

	private void navigateAndLoginToEncoderMobileApp(Permissions permission) throws InterruptedException {
		permission.navigateToEncoderMobileApp();
		Thread.sleep(3000);
		permission.loginToEncoderApp("statusChange", "Password@1");

	}
	
	@DataProvider
	private Object[] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\automation\\AdminData\\AdminData.json");
		return new Object[] { data.get(0) };

	}

}
