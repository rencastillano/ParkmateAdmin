package automation.AdminTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.ConfigurationModule;

public class ConfigurationModuleTest extends BaseTest{
	
	//Parker Test cases
	
	String parkerName = "SampleParker_" + generateRandomNumber(4);
	String parkerCode = generateRandomChars(2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	
	@Test(priority=1)
	public void parkerTypeEnrollment() throws InterruptedException {
		
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.parkerEnrollment(parkerName, parkerCode);
		Assert.assertTrue(config.createParkerTypeValidation());
		
	}
	@Test(priority=2)
	public void editParkerType() throws InterruptedException {
		
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.selectParkerTypeToEdit("SM Cherry Shaw");
		Assert.assertTrue(config.updateValidation());
	}
	@Test(priority=3)
	public void deleteParkerType() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.got0ListOfCreatedParkerTypes();
		config.deleteParkerType();
		Assert.assertTrue(config.deleteValidation());
	}
	
	@Test(priority=4)
	public void deleteParkerTypeWithFeePackage() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.got0ListOfCreatedParkerTypes();
		config.clickDeleteParkerType("MSI Regular Parker");
		Assert.assertTrue(config.deleteParkerTypeWithFeePackageValidation());
	}
	
	@Test(priority=5)
	public void assignedParkerToParkingArea() throws InterruptedException {
	
	landingPage.loginApplication("superuser", "SuperUser123!?");
	ConfigurationModule config = new ConfigurationModule(driver);
	config.gotoConfigurationMod();
	config.parkerEnrollment(parkerName, parkerCode);
	config.parkerTypeAssigning("MSI");
	
	}
	@Test
	public void duplicateParkerTypeName() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.parkerEnrollment("MSI Regular Parker", parkerCode);
		Assert.assertEquals(config.duplicatedErrorMessage(), "This parker type name already exists");
	}
	@Test
	public void duplicateParketTypeCode() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.parkerEnrollment(parkerName, "RP");
		Assert.assertEquals(config.duplicatedErrorMessage(), "This parker type code already exists");
	}
	
	//Pricing and Fee Test cases
}
