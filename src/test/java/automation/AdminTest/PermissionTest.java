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
		permission.navigateToEncoder();
		permission.loginToEncoderApp("statusChange", "r@lgn5pIl<Fu");
		Assert.assertTrue(permission.loginValidationForStatusChange());

	}
	@Test
	public void userWithActiveStatus() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setStatusToActive());
		permission.navigateToEncoder();
		permission.loginToEncoderApp("statusChange", "r@lgn5pIl<Fu");
		Assert.assertFalse(permission.loginValidationForStatusChange());
	}
	@Test
	public void userWithPaymentAcceptanceSetToFalse() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setPaymentAcceptanceToFalse());
		permission.navigateToEncoder();
		permission.loginToEncoderApp("statusChange", "r@lgn5pIl<Fu");
		Assert.assertTrue(permission.PaymentAcceptanceSetToFalseLoginValidation());
	}
	@Test
	public void userWithPaymentAcceptanceSetToTrue() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setPaymentAcceptanceToTrue());
		permission.navigateToEncoder();
		permission.loginToEncoderApp("statusChange", "r@lgn5pIl<Fu");
		Assert.assertTrue(permission.PaymentAcceptanceSetToTrueLoginValidation());
	}
	@Test
	public void setAllowExitToFalse() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setAllowExitToFalse());
		permission.navigateToEncoder();
		permission.loginToEncoderApp("statusChange", "r@lgn5pIl<Fu");
		Assert.assertFalse(permission.allowExitValidation());
	}
	@Test
	public void setAllowExitToTrue() throws InterruptedException {

		Permissions permission = loginToApplication();
		Assert.assertTrue(permission.setAllowExitToTrue());
		permission.navigateToEncoder();
		permission.loginToEncoderApp("statusChange", "r@lgn5pIl<Fu");
		Assert.assertTrue(permission.allowExitValidation());
	}
	
	// handle the login and navigation steps
		private Permissions loginToApplication() throws InterruptedException {
			UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
			Permissions permission = userCreation.userPage();
			
			return permission;
		}
}
