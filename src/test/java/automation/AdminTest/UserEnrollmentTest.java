package automation.AdminTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.PageObject.Pagenation;
import automation.PageObject.UserEnrollment;

public class UserEnrollmentTest extends BaseTest {
	
	String email = "parkmatehub."+generateRandomNumber(5);
	String completeEmail = email+"@parkmate.com";
	
	@Test(groups= {"Creation"})
	public void userEnrollment() throws InterruptedException {
		
		UserEnrollment userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);
		
		userCreation.clickEnroll();
		String userRole = userCreation.selectAdminRole();
		userCreation.getPersonalDetails("Sam", "O", "Medina");
		userCreation.getEmailDetails(completeEmail);
		userCreation.getMobileDetails("987654321");
		boolean match = userCreation.getAccountDetails(email);
		Assert.assertTrue(match);
		userCreation.getParkingStation();
		userCreation.clickSave();
		boolean successEnrollment = userCreation.enrollmentValidation(userRole);
		Assert.assertTrue(successEnrollment);

	}
	@Test(dataProvider="getData",groups= {"ErrorHandling"})
	public void userEmailDupValidation(HashMap<String,String> input) throws InterruptedException {
		UserEnrollment userCreation = landingPage.loginApplication(input.get("username"), input.get("password"));
		userCreation.userPage();
		String existingEmail = userCreation.getRandomEmail();
		
		userCreation.clickEnroll();
		userCreation.getEmailDetails(existingEmail);
		boolean dupEmail = userCreation.dupEmail();
		Assert.assertTrue(dupEmail);
		boolean dupUserName = userCreation.dupUserName();
		Assert.assertTrue(dupUserName);
	}
	@Test
	public void exitEnrollmentAlert() throws InterruptedException {
		
		UserEnrollment userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);
		
		userCreation.clickEnroll();
		userCreation.getPersonalDetails("Sam", "O", "Medina");
		userCreation.exitEnrollmentAlert();
		
	}
	@Test
	public void editUserAccount() throws InterruptedException {
		UserEnrollment userCreation = landingPage.loginApplication("riztest", "Password@1");
		boolean user = userCreation.userPage();
		Assert.assertTrue(user);
		
		boolean successUpdate = userCreation.userAccountUpdate();
		Assert.assertTrue(successUpdate);
	}
	@Test
	public void userPageSelectRow() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		Pagenation selectRow = new Pagenation(driver);
		boolean tableCount = selectRow.selectRowCount();
		Assert.assertTrue(tableCount);
		
	}
	@Test
	public void userPagePagenation() throws InterruptedException {
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
