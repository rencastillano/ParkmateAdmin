package automation.AdminTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.AdminTestComponents.BaseTest;
import automation.AdminTestComponents.Retry;
import automation.PageObject.AreaModule;
import automation.PageObject.FilterAndSearch;

public class FilterAndSearchTest extends BaseTest {

	String email = "renier.jimenez@gmail.com";
	String name = "Renier";
	String lastName = "Castillano";
	String areaName = "Wack Wack";
	String areaCode = "0846";

	@Test(priority = 1, retryAnalyzer=Retry.class)
	public void userEmailSearch() throws InterruptedException {

		FilterAndSearch search = loginToApplicationForSearching();
		Assert.assertTrue(search.emailAddSearch(email));
	}

	@Test
	public void userFirstNameSearch() throws InterruptedException {

		FilterAndSearch search = loginToApplicationForSearching();
		Assert.assertTrue(search.userSearch(name,  "firstname"));
	}

	@Test
	public void userLastNameSearch() throws InterruptedException {

		FilterAndSearch search = loginToApplicationForSearching();
		Assert.assertTrue(search.userSearch(lastName,  "lastname"));
	}

	@Test
	public void parkingNameSearch() throws InterruptedException {
		
		landingPage.loginApplication("renAdmin", "Password@1");
		AreaModule parkingCreation = new AreaModule(driver);
		FilterAndSearch search = parkingCreation.goToAreaPage();
		Assert.assertTrue(search.parkingAreaNameSearch(areaName));
		

	}

	@Test
	public void parkingAreaCodeSearch() throws InterruptedException {
		
		landingPage.loginApplication("renAdmin", "Password@1");
		AreaModule parkingCreation = new AreaModule(driver);
		FilterAndSearch search = parkingCreation.goToAreaPage();
		Assert.assertTrue(search.parkingAreaCodeSearch(areaCode));

	}

	@Test(retryAnalyzer = Retry.class)
	public void filterByEncoderRole() throws InterruptedException {
		
		FilterAndSearch search = loginToApplicationForSearching();
		Assert.assertTrue(search.filterByEncoderRole());
	}

	@Test
	public void filterByAdminRole() throws InterruptedException {
		
		FilterAndSearch search = loginToApplicationForSearching();
		Assert.assertTrue(search.filterByAdminRole());
	}

	// handle the login and navigation steps
	private FilterAndSearch loginToApplicationForSearching() throws InterruptedException {
		landingPage.loginApplication("renAdmin", "Password@1");
		FilterAndSearch search = new FilterAndSearch(driver);
		
		return search;
	}

}
