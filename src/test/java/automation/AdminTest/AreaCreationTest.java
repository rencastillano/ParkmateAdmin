package automation.AdminTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.AreaCreation;
import automation.PageObject.Pagenation;



public class AreaCreationTest extends BaseTest {
	
	String areaName = "Area_"+generateRandomString();
	
	@Test(groups= {"Creation"})
	public void areaCreation() throws InterruptedException {
		
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		
		createArea.clickCreate();
		createArea.genInfoParkingName(areaName);
		createArea.genInfoSMList();
		createArea.genInfocarCapacity("100");
		createArea.genInfomotorcycleCapacity("100");
		createArea.fixRate("40");
		boolean match = createArea.smMallCode("SMCF");
		Assert.assertTrue(match);
		createArea.getAreaCode(generateRandomNumber(4));
		createArea.parkingHours("10:30AM", "11:00PM");
		createArea.clickSave();
		
		boolean bol = createArea.handlingDupAndValidation(generateRandomNumber(4), areaName);
		Assert.assertTrue(bol);
		
	}
	@Test(dataProvider="getData",groups= {"ErrorHandling"})
	public void areaNameDupValidation(HashMap<String,String> input) throws InterruptedException {
		
		landingPage.loginApplication(input.get("username"), input.get("password"));
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		String dupAreaName = createArea.getRandomAreaName();
		
		createArea.areaNameToBeEdited();
		createArea.genInfoParkingName(dupAreaName);
		createArea.clickSave();
		Assert.assertEquals(createArea.errorMessage(),
				"Updated Parking name already exists. Kindly use a different name.");
		
	}
	@Test
	public void editParkingArea() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		Assert.assertTrue(createArea.parkingAreaUpdate());
	}
	@Test(groups= {"ErrorHandling"})
	public void areaCodeDupValidation() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		
		createArea.areaNameToBeEdited();
		createArea.getAreaCode("0127");
		createArea.clickSave();
		Assert.assertEquals(createArea.errorMessage(),
				"Updated area code already exists. Kindly use a different area code.");
		
	}
	@Test(groups= {"ErrorHandling"})
	public void fixedRateMaxLimit() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		
		createArea.clickCreate();
		createArea.genInfoParkingName("Area_FixedRateTest");
		createArea.genInfoSMList();
		createArea.genInfocarCapacity("100");
		createArea.genInfomotorcycleCapacity("100");
		createArea.fixRate("1000");
		createArea.getAreaCode(generateRandomNumber(4));
		createArea.parkingHours("10:30AM", "11:00PM");
		createArea.clickSave();
		Assert.assertEquals(createArea.errorMessage(),
				"Fixed rate value must be 1 - 999.99");
	}
	@Test
	public void exitCreationAlert() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		
		createArea.clickCreate();
		createArea.genInfoParkingName(areaName);
		createArea.genInfoSMList();
		createArea.genInfocarCapacity("100");
		createArea.genInfomotorcycleCapacity("100");
		createArea.exitCreationAlert();
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
	
	@DataProvider
	public Object[] getData() throws IOException
	{
		
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\automation\\AdminData\\ParkingData.json");
		return new Object[]  {data.get(0)} ;
		
	}

}
