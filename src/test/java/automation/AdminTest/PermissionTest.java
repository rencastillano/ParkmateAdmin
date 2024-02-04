package automation.AdminTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.Permissions;
import automation.PageObject.UserModule;

public class PermissionTest extends BaseTest {

	@Test
	public void userWithRestrictedStatus() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setStatusToRestricted());
		navigateAndLoginToEncoder(permission);
		Assert.assertTrue(permission.loginValidationForStatusChange());

	}

	@Test
	public void userWithActiveStatus() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setStatusToActive());
		navigateAndLoginToEncoder(permission);
		Assert.assertFalse(permission.loginValidationForStatusChange());
	}

	@Test
	public void userWithPaymentAcceptanceSetToFalse() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setPaymentAcceptanceToFalse());
		navigateAndLoginToEncoder(permission);
		Assert.assertTrue(permission.PaymentAcceptanceSetToFalseLoginValidation());
	}

	@Test
	public void userWithPaymentAcceptanceSetToTrue() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setPaymentAcceptanceToTrue());
		navigateAndLoginToEncoder(permission);
		Assert.assertTrue(permission.PaymentAcceptanceSetToTrueLoginValidation());
	}

	@Test
	public void setAllowExitToFalse() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setAllowExitToFalse());
		navigateAndLoginToEncoder(permission);
		Assert.assertFalse(permission.allowExitValidation());
	}

	@Test
	public void setAllowExitToTrue() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setAllowExitToTrue());
		navigateAndLoginToEncoder(permission);
		Assert.assertTrue(permission.allowExitValidation());
	}

	// handle the login and navigation steps
	private Permissions loginToApplication() throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
		Permissions permission = userCreation.userPage();

		return permission;
	}

	private void navigateAndLoginToEncoder(Permissions permission) {
		permission.navigateToEncoder();
		permission.loginToEncoderApp("statusChange", "r@lgn5pIl<Fu");
	}
}
