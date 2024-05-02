package automation.AdminTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.ConfigurationModule;

public class ConfigurationModuleTest extends BaseTest {

	// Parker Test cases

	String parkerName = "SampleParker_" + generateRandomNumber(4);
	String parkerCode = generateRandomChars(2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	String baseFee = generateRandomChars(4, "0123456789");
	String parkingAreaName = "MSI";

	@Test(priority = 1)
	public void parkerTypeEnrollment() throws InterruptedException {

		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.parkerEnrollment(parkerName, parkerCode);
		Assert.assertTrue(config.validateBannerMessage("Parker type has been added successfully!\n×"));

	}

	@Test(priority = 2)
	public void editParkerType() throws InterruptedException {

		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.selectParkerTypeToEdit("SM Cherry Shaw");
		Assert.assertTrue(config.validateBannerMessage("Parker type has been updated successfully!"));
	}

	@Test(priority = 3)
	public void deleteParkerType() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.got0ListOfCreatedParkerTypes();
		config.deleteParkerType();
		Assert.assertTrue(config.validateBannerMessage("Parker type has been deleted successfully!"));
	}

	@Test(priority = 4)
	public void deleteParkerTypeWithPricingPackage() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.got0ListOfCreatedParkerTypes();
		config.clickDeleteParkerType("MSI Regular Parker");
		Assert.assertTrue(config.deleteParkerTypeWithFeePackageValidation());
	}

	@Test(priority = 5)
	public void assignParkerToParkingArea() throws InterruptedException {

		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.parkerEnrollment(parkerName, parkerCode);
		config.parkerTypeAssigning(parkingAreaName);
		Assert.assertTrue(config.validateBannerMessage("Parker Type successfully assigned to " + parkingAreaName));
	}

	@Test(priority = 6)
	public void duplicateParkerTypeName() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.parkerEnrollment("MSI Regular Parker", parkerCode);
		Assert.assertEquals(config.duplicatedErrorMessage(), "This parker type name already exists");
	}

	@Test(priority = 7)
	public void duplicateParketTypeCode() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		// add parker type name dup checker
		config.parkerEnrollment(parkerName + "_U", "RP");
		Assert.assertEquals(config.duplicatedErrorMessage(), "This parker type code already exists");
	}

	public void updateParketTypeCodeWithoutChanges() {
	}

	// Pricing and Fee Test cases

	@Test(priority = 8)
	public void createPricingPackage() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.createFeePackage(baseFee);
		Assert.assertTrue(config.validateBannerMessage("Pricing Package has been added successfully!"));
	}

	@Test(priority = 9)
	public void updatePricingFeePackage() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.updatePricing(baseFee + "5");
		Assert.assertTrue(config.validateBannerMessage("Pricing Package has been edited successfully!"));
	}

	@Test(priority = 10)
	public void assignPricingToParkingArea() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.assigningPricing(parkingAreaName);
		Assert.assertTrue(config.validateBannerMessage("Pricing Package successfully assigned to " + parkingAreaName));
	}

	@Test(priority = 11)
	public void deletePricingPackage() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();
		config.deletePricing();
		Assert.assertTrue(config.validateBannerMessage("Pricing Package successfully deleted."));
	}

}