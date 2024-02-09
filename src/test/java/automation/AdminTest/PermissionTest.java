package automation.AdminTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.AdminTestComponents.Retry;
import automation.PageObject.Permissions;
import automation.PageObject.UserModule;

public class PermissionTest extends BaseTest {

	@Test
	public void adminWithRestrictedStatus() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setAdminStatusToRestricted("henry.salazar@parkmate.com"));
		permission.loginToParkingAdmin("henry.salazar", "!(s^yij5b_6I");
		Assert.assertTrue(permission.loginValidationForStatusChange());

	}

	@Test
	public void encoderWithRestrictedStatusMobileAppLogin() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setStatusToRestricted("statusChange@parkmate.com"));
		navigateAndLoginToEncoderMobileApp(permission);
		Assert.assertTrue(permission.loginValidationForStatusChange());

	}

	@Test
	public void encoderWithRestrictedStatusDesktopLogin() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setStatusToRestricted("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertTrue(permission.loginValidationForStatusChange());

	}

	@Test
	public void encoderWithActiveStatusMobileAppLogin() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setStatusToActiveForMobile("statusChange@parkmate.com"));
		navigateAndLoginToEncoderMobileApp(permission);
		Assert.assertFalse(permission.loginValidationForStatusChange());
	}

	@Test
	public void encoderWithActiveStatusDesktopLogin() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setStatusToActiveForDestop("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertFalse(permission.loginValidationForStatusChange());
	}

	@Test
	public void encoderWithPaymentAcceptanceSetToFalse() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setPaymentAcceptanceToFalse("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertTrue(permission.PaymentAcceptanceSetToFalseLoginValidation());
	}

	@Test(retryAnalyzer = Retry.class)
	public void encoderWithPaymentAcceptanceSetToTrue() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setPaymentAcceptanceToTrue("statusChange@parkmate.com"));
		navigateAndLoginToEncoderDesktop(permission);
		Assert.assertTrue(permission.PaymentAcceptanceSetToTrueLoginValidation());
	}

	@Test(retryAnalyzer = Retry.class)
	public void encoderWithAllowExitToFalse() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setAllowExitToFalse("statusChange@parkmate.com"));
		navigateAndLoginToEncoderMobileApp(permission);
		Assert.assertFalse(permission.allowExitValidation());
	}

	@Test
	public void encoderWithAllowExitToTrue() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setAllowExitToTrue("statusChange@parkmate.com"));
		navigateAndLoginToEncoderMobileApp(permission);
		Assert.assertTrue(permission.allowExitValidation());
	}

	// handle the login and navigation steps
	private Permissions loginToApplication() throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
		Permissions permission = userCreation.userPage();

		return permission;
	}

	private void navigateAndLoginToEncoderDesktop(Permissions permission) {
		permission.navigateToEncoderMobileApp();
		permission.loginToEncoderApp("statusChange", "Password@1");

	}

	private void navigateAndLoginToEncoderMobileApp(Permissions permission) {
		permission.navigateToEncoderMobileApp();
		permission.loginToEncoderApp("statusChange", "Password@1");

	}

}
