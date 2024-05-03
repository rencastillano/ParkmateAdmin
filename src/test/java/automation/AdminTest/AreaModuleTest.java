package automation.AdminTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.AdminTestComponents.Retry;
import automation.PageObject.AreaModule;
import automation.PageObject.Pagenation;

public class AreaModuleTest extends BaseTest {

	String areaName = "Area_" + generateRandomString();
	String areaNameToSearch = "QA_Automation";
	String areaCode = generateRandomNumber(4);

	@Test(priority = 1, retryAnalyzer=Retry.class, groups = { "Creation" })
	public void areaCreation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.clickCreate();
		parkingArea.genInfoParkingName(areaName);
		parkingArea.genInfoSMList("SM City Marikina");
		parkingArea.getCarCapacity("100");
		parkingArea.getMotorcycleCapacity("100");
		parkingArea.fixRate("40");
		boolean match = parkingArea.smMallCode("SMMK");
		Assert.assertTrue(match);
		parkingArea.getAreaCode("0127");
		parkingArea.parkingHours("10:30AM", "11:00PM");
		parkingArea.clickSave();
		String createdAreaName = parkingArea.handlingAreaNameDup(areaName);
		String createdAreaCode = parkingArea.handlingAreaCodeDup(areaCode);
		Assert.assertTrue(parkingArea.areaCreationValidation(createdAreaName, createdAreaCode));
	}

	@Test( groups = { "ErrorHandling" })
	public void areaNameDupValidation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		String dupAreaName = parkingArea.getRandomAreaName();
		parkingArea.selectAreaToBeEdited();
		parkingArea.genInfoParkingName(dupAreaName);
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.parkingErrorMessage(),
				"Updated Parking name already exists. Kindly use a different name.");

	}

	@Test(groups = { "ErrorHandling" })
	public void areaCodeDupValidation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		parkingArea.getAreaCode("0127");
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.parkingErrorMessage(),
				"Updated area code already exists. Kindly use a different area code.");

	}
	
	@Test
	public void validateUpdateParkingArea() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		//Assert.assertTrue(
				parkingArea.parkingAreaUpdate(areaNameToSearch);//);
	}

	@Test(groups = { "ErrorHandling" })
	public void fixedRateMaxLimit() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.clickCreate();
		parkingArea.genInfoParkingName("Area2 SMAU");
		parkingArea.genInfoSMList("SM City Marikina");
		parkingArea.getCarCapacity("100");
		parkingArea.getMotorcycleCapacity("100");
		parkingArea.fixRate("1000");
		parkingArea.getAreaCode(generateRandomNumber(4));
		parkingArea.parkingHours("10:30AM", "11:00PM");
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.fixedRateErrorMessage(),
				"Fixed rate value must be 1 - 999.99");
	}

	@Test
	public void exitCreationAlert() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.clickCreate();
		parkingArea.genInfoParkingName(areaName);
		Assert.assertTrue(parkingArea.exitCreationAlert());
	}

	@Test
	public void areaPageSelectRow() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password@1");
		Pagenation selectRow = new Pagenation(driver);
		Assert.assertTrue(selectRow.selectRowCount());

	}

	@Test
	public void areaPagePagenation() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password@1");
		Pagenation page = new Pagenation(driver);
		Assert.assertTrue(page.nextButton());
		Assert.assertTrue(page.previousButton());
	}

	@Test
	public void carCapacityInputValidation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		parkingArea.getCarCapacity("100000");
		parkingArea.getMotorcycleCapacity("100");
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.carCapacityErrorMessage(),
				"Car Capacity must be 1 to 99,999.");
	}

	@Test
	public void motorcycleCapacityInputValidation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		parkingArea.getCarCapacity("1000");
		parkingArea.getMotorcycleCapacity("100000");
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.motorcycleCapacityErrorMessage(),
				"Motorcycle Capacity must be 1 to 99,999.");
	}

	@Test
	public void setCarCapacityPlusButtonToDisable() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		Assert.assertTrue(parkingArea.increaseCarCapacity("99995"));
	}

	@Test
	public void setCarCapacityMinusButtonToDisable() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		Assert.assertTrue(parkingArea.decreaseCarCapacity("5"));
	}

	@Test
	public void setCMotorcycleCapacityPlusButtonToDisable() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		Assert.assertTrue(parkingArea.increaseMotorcycleCapacity("99995"));
	}

	@Test
	public void setMotorcycleCapacityPlusButtonToDisable() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		Assert.assertTrue(parkingArea.decreaseMotorcycleCapacity("5"));
	}

	// handle the login and area navigation steps
	private AreaModule loginToApplication() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password@1");
		AreaModule parkingArea = new AreaModule(driver);
		parkingArea.goToAreaPage();

		return parkingArea;
	}	

}
