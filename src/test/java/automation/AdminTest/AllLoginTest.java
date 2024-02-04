package automation.AdminTest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.AdminTestComponents.Retry;

public class AllLoginTest extends BaseTest {
	String errMsg = "Either your username or password is incorrect, please check and try again!";

	@Test(retryAnalyzer=Retry.class,groups= {"ErrorHandling"})
	public void loginWithInvalidUserName() throws InterruptedException, IOException {

		landingPage.loginApplication("invalid", "Password@1");
		Assert.assertEquals(landingPage.getErrorMsg(), errMsg);

	}
	@Test(groups= {"ErrorHandling"})
	public void loginWithInvalidPassword() throws InterruptedException, IOException {

		landingPage.loginApplication("riztest", "inval!Dp@ss1");
		Assert.assertEquals(landingPage.getErrorMsg(), errMsg);

	}
	@Test(groups= {"ErrorHandling"})
	public void loginWithoutAccess() throws InterruptedException, IOException {

		landingPage.loginApplication("withoutA", "SMSupermalls1!");
		Assert.assertEquals(landingPage.getErrorMsg(), errMsg);

	}
	@Test
	public void loginWithAccess() throws InterruptedException, IOException {

		landingPage.loginApplication("riztest", "Password@1");
		Assert.assertTrue(landingPage.successLogin());
		
		String confirmLogout = landingPage.logout();
		Assert.assertEquals(confirmLogout, "Welcome Back!");

	}


}
