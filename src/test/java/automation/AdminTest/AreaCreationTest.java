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
		createArea.areaCode(generateRandomNumber(4));
		createArea.parkingHours("10:30AM", "11:00PM");
		createArea.SaveCreation();
		createArea.dupErrMessageOnCreation(generateRandomNumber(4));
		
		
	}
	@Test(dependsOnMethods= {"areaCreation"})
	public void areaCreationValidation() throws InterruptedException {
		landingPage.loginApplication("riztest", "Password@1");
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		
		boolean areaMatch = createArea.creationValidation(areaName);
		Assert.assertTrue(areaMatch);
		
	}
	@Test(dataProvider="getData",groups= {"ErrorHandling"})
	public void areaNameDupValidation(HashMap<String,String> input) throws InterruptedException {
		
		landingPage.loginApplication(input.get("username"), input.get("password"));
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		String dupAreaName = createArea.getRandomAreaName();
		
		createArea.areaNameToBeEdited();
		createArea.genInfoParkingName(dupAreaName);
		createArea.SaveCreation();
		boolean err = createArea.dupErrMessage();
		Assert.assertTrue(err);
		
	}
	@Test(groups= {"ErrorHandling"})
	public void areaCodeDupValidation() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaCreation createArea = new AreaCreation(driver);
		createArea.goToAreaPage();
		
		createArea.areaNameToBeEdited();
		createArea.areaCode("7308");
		createArea.SaveCreation();
		boolean err = createArea.dupErrMessage();
		Assert.assertTrue(err);
		
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
