package automation.AdminTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.AreaModule;
import automation.PageObject.Pagenation;

public class AreaModuleTest extends BaseTest {

	String areaName = "Area_" + generateRandomString();
	String areaNameToSearch = "QA_Automation";

	@Test(groups = { "Creation" })
	public void areaCreation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.clickCreate();
		parkingArea.genInfoParkingName(areaName);
		parkingArea.genInfoSMList();
		parkingArea.getCarCapacity("100");
		parkingArea.getMotorcycleCapacity("100");
		parkingArea.fixRate("40");
		boolean match = parkingArea.smMallCode("SMCF");
		Assert.assertTrue(match);
		parkingArea.getAreaCode(generateRandomNumber(4));
		parkingArea.parkingHours("10:30AM", "11:00PM");
		parkingArea.clickSave();
		Assert.assertTrue( parkingArea.handlingDupAndValidation(generateRandomNumber(4), areaName));

	}

	@Test(dataProvider = "getData", groups = { "ErrorHandling" })
	public void areaNameDupValidation(HashMap<String, String> input) throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		String dupAreaName = parkingArea.getRandomAreaName();
		parkingArea.selectAreaToBeEdited();
		parkingArea.genInfoParkingName(dupAreaName);
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.errorMessage(),
				"Updated Parking name already exists. Kindly use a different name.");

	}

	@Test
	public void validateEditParkingArea() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		Assert.assertTrue(parkingArea.parkingAreaUpdate(areaNameToSearch));
	}

	@Test(groups = { "ErrorHandling" })
	public void areaCodeDupValidation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		parkingArea.getAreaCode("0127");
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.errorMessage(),
				"Updated area code already exists. Kindly use a different area code.");

	}

	@Test(groups = { "ErrorHandling" })
	public void fixedRateMaxLimit() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.clickCreate();
		parkingArea.genInfoParkingName("Area_FixedRateTest");
		parkingArea.genInfoSMList();
		parkingArea.getCarCapacity("100");
		parkingArea.getMotorcycleCapacity("100");
		parkingArea.fixRate("1000");
		parkingArea.getAreaCode(generateRandomNumber(4));
		parkingArea.parkingHours("10:30AM", "11:00PM");
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.errorMessage(), "Fixed rate value must be 1 - 999.99");
	}

	@Test
	public void exitCreationAlert() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.clickCreate();
		parkingArea.genInfoParkingName(areaName);
		parkingArea.genInfoSMList();
		parkingArea.getCarCapacity("100");
		parkingArea.getMotorcycleCapacity("100");
		Assert.assertTrue(parkingArea.exitCreationAlert());
	}

	@Test
	public void areaPageSelectRow() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		Pagenation selectRow = new Pagenation(driver);
		boolean tableCount = selectRow.selectRowCount();
		Assert.assertTrue(tableCount);

	}

	@Test
	public void areaPagePagenation() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		Pagenation page = new Pagenation(driver);
		boolean nextBtnDisabled = page.nextButton();
		Assert.assertTrue(nextBtnDisabled);
		boolean PreviousBtnDisabled = page.previousButton();
		Assert.assertTrue(PreviousBtnDisabled);
	}

	@Test
	public void carCapacityInputValidation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		parkingArea.getCarCapacity("1001");
		parkingArea.getMotorcycleCapacity("100");
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.errorMessage(), "Car Capacity must be 1 to 1,000.");
	}

	@Test
	public void motorcycleCapacityInputValidation() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		parkingArea.getCarCapacity("1000");
		parkingArea.getMotorcycleCapacity("1001");
		parkingArea.clickSave();
		Assert.assertEquals(parkingArea.errorMessage(), "Motorcycle Capacity must be 1 to 1,000.");
	}

	@Test
	public void setCarCapacityPlusButtonToDisable() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		Assert.assertTrue(parkingArea.increaseCarCapacity("995"));
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
		Assert.assertTrue(parkingArea.increaseMotorcycleCapacity("995"));
	}

	@Test
	public void setMotorcycleCapacityPlusButtonToDisable() throws InterruptedException {

		AreaModule parkingArea = loginToApplication();
		parkingArea.selectAreaToBeEdited();
		Assert.assertTrue(parkingArea.decreaseMotorcycleCapacity("5"));
	}

	// handle the login and area navigation steps
	private AreaModule loginToApplication() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaModule parkingArea = new AreaModule(driver);
		parkingArea.goToAreaPage();

		return parkingArea;
	}

	@DataProvider
	public Object[] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\automation\\AdminData\\ParkingData.json");
		return new Object[] { data.get(0) };

	}

}
