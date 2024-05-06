package automation.AdminTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.ConfigurationModule;

public class ConfigurationModuleTest extends BaseTest {

	// Parker Test cases

	String parkerName = "SampleParker_" + generateRandomNumber(4);
	String parkerCode = generateRandomChars(2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	String baseFee = generateRandomChars(4, "0123456789");
	String parkingAreaName = "MSI";

	@Test(priority = 1,dataProvider = "getData")
	public void parkerTypeEnrollment(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.goToParkerEnrollment();
		config.getParkerTypeName(parkerName);
		config.getParkerTypeCode(parkerCode);
		config.clickCreateButton();
		String createdParkerName = config.handlingParkerNameDup(parkerName);
		config.handlingParkerCodeDup(parkerCode);
		Assert.assertTrue(config.ParkerCreationValidation(createdParkerName));

	}

	@Test(priority = 2,dataProvider = "getData")
	public void editParkerType(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.selectParkerTypeToEdit("SM Cherry Shaw");
		Assert.assertTrue(config.validateBannerMessage("Parker type has been updated successfully!"));
	}

	@Test(priority = 3,dataProvider = "getData")
	public void deleteParkerType(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.got0ListOfCreatedParkerTypes();
		config.deleteParkerType();
		Assert.assertTrue(config.validateBannerMessage("Parker type has been deleted successfully!"));
	}

	@Test(priority = 4,dataProvider = "getData")
	public void deleteParkerTypeWithPricingPackage(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.got0ListOfCreatedParkerTypes();
		config.clickDeleteParkerType("MSI Regular Parker");
		Assert.assertEquals(config.errorSpielValidation(), "Unable to delete parker type.");
	}

	@Test(priority = 5,dataProvider = "getData")
	public void assignParkerToParkingArea(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.goToParkerEnrollment();
		config.getParkerTypeName(parkerName);
		config.getParkerTypeCode(parkerCode);
		config.clickCreateButton();
		config.handlingParkerCodeDup(parkerCode);
		config.parkerTypeAssigning(parkingAreaName);
		Assert.assertTrue(config.validateBannerMessage("Parker Type successfully assigned to " + parkingAreaName));
	}

	@Test(priority = 6,dataProvider = "getData")
	public void assignParkerToParkingAreaWithMisMatchParkingArea(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.selectParkerTypeToEdit("SM Cherry Shaw");
		boolean res = config.parkerTypeAssigningWithMismatchParkingArea("Acer", parkerName);
		Assert.assertFalse(res);
	}

	@Test(priority = 7,dataProvider = "getData")
	public void duplicateParkerTypeName(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.goToParkerEnrollment();
		config.getParkerTypeName("MSI Regular Parker");
		config.getParkerTypeCode(parkerCode);
		config.clickCreateButton();
		Assert.assertEquals(config.duplicatedErrorMessage(), "This parker type name already exists");
	}

	@Test(priority = 8,dataProvider = "getData")
	public void duplicateParketTypeCode(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.goToParkerEnrollment();
		config.getParkerTypeName(parkerName + "_U");
		config.getParkerTypeCode("RP");
		config.clickCreateButton();
		Assert.assertEquals(config.duplicatedErrorMessage(), "This parker type code already exists");
	}

	// Pricing and Fee Test cases

	@Test(priority = 9,dataProvider = "getData")
	public void createPricingPackage(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.createFeePackage(baseFee);
		Assert.assertTrue(config.validateBannerMessage("Pricing Package has been added successfully!"));
	}

	@Test(priority = 10,dataProvider = "getData")
	public void updatePricingPackageWithoutChange(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.updatePricing(baseFee);
		Assert.assertEquals(config.errorSpielValidation(),
				"Could not edit pricing package since no values were changed.");
	}

	@Test(priority = 11,dataProvider = "getData")
	public void updatePricingFeePackage(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.updatePricing(baseFee + "5");
		Assert.assertTrue(config.validateBannerMessage("Pricing Package has been edited successfully!"));
	}

	@Test(priority = 12,dataProvider = "getData")
	public void assignPricingFeeToParkingArea(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.assigningPricing(parkingAreaName);
		Assert.assertTrue(config.validateBannerMessage("Pricing Package successfully assigned to " + parkingAreaName));
	}

	@Test(priority = 13,dataProvider = "getData")
	public void deletePricingPackage(HashMap<String,String> input) throws InterruptedException {
		ConfigurationModule config = loginToApplication(input.get("username"), input.get("password"));
		config.deletePricing();
		Assert.assertTrue(config.validateBannerMessage("Pricing Package successfully deleted."));
	}

	// handle the login and navigation steps
	private ConfigurationModule loginToApplication(String username, String password) throws InterruptedException {
		landingPage.loginApplication(username, password);
		ConfigurationModule config = new ConfigurationModule(driver);
		config.gotoConfigurationMod();

		return config;
	}
	
	@DataProvider
	private Object[] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\automation\\AdminData\\AdminData.json");
		return new Object[] { data.get(0) };

	}

}