package automation.AdminTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.Permissions;
import automation.PageObject.UserModule;

public class PermissionTest extends BaseTest {

	@Test
	public void userWithRestrictedStatus() throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);

		Permissions permission = new Permissions(driver);
		Assert.assertTrue(permission.setStatusToRestricted());
		permission.navigateTo();
		permission.loginToEncoderApp("statusChange", "Password@1");
		Assert.assertTrue(permission.loginValidationForStatusChange());

	}
	@Test
	public void userWithActiveStatus() throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);

		Permissions permission = new Permissions(driver);
		Assert.assertTrue(permission.setStatusToActive());
		permission.navigateTo();
		permission.loginToEncoderApp("statusChange", "Password@1");
		Assert.assertFalse(permission.loginValidationForStatusChange());
	}
	@Test
	public void userWithPaymentAcceptanceSetToFalse() throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);
		Permissions permission = new Permissions(driver);
		Assert.assertTrue(permission.setPaymentAcceptanceToFalse());
		permission.navigateTo();
		permission.loginToEncoderApp("statusChange", "Password@1");
		Assert.assertTrue(permission.PaymentAcceptanceSetToFalseLoginValidation());
	}
	@Test
	public void userWithPaymentAcceptanceSetToTrue() throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);
		Permissions permission = new Permissions(driver);
		Assert.assertTrue(permission.setPaymentAcceptanceToTrue());
		permission.navigateTo();
		permission.loginToEncoderApp("statusChange", "Password@1");
		Assert.assertTrue(permission.PaymentAcceptanceSetToTrueLoginValidation());
	}
	@Test
	public void setAllowExitToFalse() throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);
		Permissions permission = new Permissions(driver);
		Assert.assertTrue(permission.setAllowExitToFalse());
		permission.navigateTo();
		permission.loginToEncoderApp("statusChange", "Password@1");
		Assert.assertFalse(permission.allowExitValidation());
	}
	@Test
	public void setAllowExitToTrue() throws InterruptedException {
		UserModule userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);
		Permissions permission = new Permissions(driver);
		Assert.assertTrue(permission.setAllowExitToTrue());
		permission.navigateTo();
		permission.loginToEncoderApp("statusChange", "Password@1");
		Assert.assertTrue(permission.allowExitValidation());
	}
}
