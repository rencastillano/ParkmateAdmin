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
		ConfigurationModule config = loginToApplication();
		config.goToParkerEnrollment();
		config.getParkerTypeName(parkerName);
		config.getParkerTypeCode(parkerCode);
		config.clickCreateButton();
		// add duplicate validation
		String createdParkerName = config.handlingParkerNameDup(parkerName);
		config.handlingParkerCodeDup(parkerCode);
		Assert.assertTrue(config.ParkerCreationValidation(createdParkerName));

	}

	@Test(priority = 2)
	public void editParkerType() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.selectParkerTypeToEdit("SM Cherry Shaw");
		Assert.assertTrue(config.validateBannerMessage("Parker type has been updated successfully!"));
	}

	@Test(priority = 3)
	public void deleteParkerType() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.got0ListOfCreatedParkerTypes();
		config.deleteParkerType();
		Assert.assertTrue(config.validateBannerMessage("Parker type has been deleted successfully!"));
	}

	@Test(priority = 4)
	public void deleteParkerTypeWithPricingPackage() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.got0ListOfCreatedParkerTypes();
		config.clickDeleteParkerType("MSI Regular Parker");
		Assert.assertEquals(config.errorSpielValidation(), "Unable to delete parker type.");
	}

	@Test(priority = 5)
	public void assignParkerToParkingArea() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.goToParkerEnrollment();
		config.getParkerTypeName(parkerName);
		config.getParkerTypeCode(parkerCode);
		config.clickCreateButton();
		config.parkerTypeAssigning(parkingAreaName);
		Assert.assertTrue(config.validateBannerMessage("Parker Type successfully assigned to " + parkingAreaName));
	}

	@Test(priority = 6)
	public void assignParkerToParkingAreaWithMisMatchParkingArea() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.selectParkerTypeToEdit("SM Cherry Shaw");
		boolean res = config.parkerTypeAssigningWithMismatchParkingArea("Acer", parkerName);
		Assert.assertFalse(res);
	}

	@Test(priority = 7)
	public void duplicateParkerTypeName() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.goToParkerEnrollment();
		config.getParkerTypeName("MSI Regular Parker");
		config.getParkerTypeCode(parkerCode);
		config.clickCreateButton();
		Assert.assertEquals(config.duplicatedErrorMessage(), "This parker type name already exists");
	}

	@Test(priority = 8)
	public void duplicateParketTypeCode() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.goToParkerEnrollment();
		config.getParkerTypeName(parkerName + "_U");
		config.getParkerTypeCode("RP");
		config.clickCreateButton();
		Assert.assertEquals(config.duplicatedErrorMessage(), "This parker type code already exists");
	}

	// Pricing and Fee Test cases

	@Test(priority = 9)
	public void createPricingPackage() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.createFeePackage(baseFee);
		Assert.assertTrue(config.validateBannerMessage("Pricing Package has been added successfully!"));
	}

	@Test(priority = 10)
	public void updatePricingPackageWithoutChange() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.updatePricing(baseFee);
		Assert.assertEquals(config.errorSpielValidation(),
				"Could not edit pricing package since no values were changed.");
	}

	@Test(priority = 11)
	public void updatePricingFeePackage() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.updatePricing(baseFee + "5");
		Assert.assertTrue(config.validateBannerMessage("Pricing Package has been edited successfully!"));
	}

	@Test(priority = 12)
	public void assignPricingFeeToParkingArea() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.assigningPricing(parkingAreaName);
		Assert.assertTrue(config.validateBannerMessage("Pricing Package successfully assigned to " + parkingAreaName));
	}

	@Test(priority = 13)
	public void deletePricingPackage() throws InterruptedException {
		ConfigurationModule config = loginToApplication();
		config.deletePricing();
		Assert.assertTrue(config.validateBannerMessage("Pricing Package successfully deleted."));
	}

	// handle the login and navigation steps
	private ConfigurationModule loginToApplication() throws InterruptedException {
		landingPage.loginApplication("superuser", "SuperUser123!?");
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();

		return config;
	}

}