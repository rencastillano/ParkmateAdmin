package automation.AdminTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.AdminTestComponents.Retry;
import automation.PageObject.AreaCreation;
import automation.PageObject.FilterAndSearch;
import automation.PageObject.UserEnrollment;


public class FilterAndSearchTest extends BaseTest {

	String email = "renier.jimenez@gmail.com";
	String name = "Renier";
	String lastName ="Castillano";
	String areaName = "Wack Wack";
	
	@Test
	public void userEmailSearch() throws InterruptedException {

		UserEnrollment userCreationPage = landingPage.loginApplication("renAdmin", "Password1!");
		boolean user = userCreationPage.userPage();
		Assert.assertTrue(user);

		FilterAndSearch search = new FilterAndSearch(driver);
		boolean result = search.emailAddSearch(email);
		Assert.assertTrue(result);
	}
	@Test
	public void userNameSearch() throws InterruptedException {

		UserEnrollment userCreationPage = landingPage.loginApplication("renAdmin", "Password1!");
		boolean user = userCreationPage.userPage();
		Assert.assertTrue(user);

		FilterAndSearch search = new FilterAndSearch(driver);
		boolean result = search.userFirstNameSearch(name);
		Assert.assertTrue(result);
	}
	@Test
	public void userLastNameSearch() throws InterruptedException {

		UserEnrollment userCreationPage = landingPage.loginApplication("renAdmin", "Password1!");
		boolean user = userCreationPage.userPage();
		Assert.assertTrue(user);

		FilterAndSearch search = new FilterAndSearch(driver);
		boolean result = search.userLastnameSearch(lastName);
		Assert.assertTrue(result);
	}
	@Test
	public void parkingNameSearch() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaCreation parkingCreation = new AreaCreation(driver);
		FilterAndSearch search =parkingCreation.goToAreaPage();
		boolean result = search.parkingNameSearch(areaName);
		Assert.assertTrue(result);
				
	}
	@Test(retryAnalyzer=Retry.class)
	public void parkingAreaCodeSearch() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		AreaCreation parkingCreation = new AreaCreation(driver);
		FilterAndSearch search =parkingCreation.goToAreaPage();
		boolean result = search.parkingAreaCodeSearch();
		Assert.assertTrue(result);
				
	}
	@Test
	public void filterByEncoderRole() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		FilterAndSearch search = new FilterAndSearch(driver);
		boolean result = search.filterByEncoderRole();
		Assert.assertTrue(result);
	}
	@Test
	public void filterByAdminRole() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password1!");
		FilterAndSearch search = new FilterAndSearch(driver);
		boolean result = search.filterByAdminRole();
		Assert.assertTrue(result);
	}


}
