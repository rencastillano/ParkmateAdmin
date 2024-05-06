package automation.PageObject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;

public class FilterAndSearch extends AbstractComponent {

	WebDriver driver;

	public FilterAndSearch(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory
	@FindBy(css = "input[placeholder='Search']")
	WebElement search;

	@FindBy(xpath = "//button[@class='absolute top-2.5 right-3 text-sm-placeholder']")
	WebElement clearBtn;

	@FindBy(xpath = "(//tr/td)[3]")
	WebElement userEmailSearched;

	@FindBy(xpath = "//tr/td[4]")
	WebElement searchAreaCode;

	@FindBy(css = "tr td:first-child")
	WebElement firstRowData;

	@FindBy(css = "h2[class='px-2']")
	WebElement filterBtn;

	@FindBy(css = "#roleCheckbox")
	WebElement roleCheckbox;

	@FindBy(xpath = "//*/div/button[.='Select Role ']")
	WebElement roleDropdown;

	@FindBy(xpath = "//*/ul/li[contains(.,'Encoder')]")
	WebElement selectEncoderRole;

	@FindBy(xpath = "//*/ul/li[contains(.,'Mall Admin')]")
	WebElement selectAdminRole;

	@FindBy(xpath = "//*/div/button[.='Apply']")
	WebElement clickApply;

	@FindBy(xpath = "//tr/td[2]")
	List<WebElement> roleColumn;

	private void performSearch(WebElement element, String searchData) throws InterruptedException {
		search.click();
		search.sendKeys(searchData);
		String result;
		do {
			Thread.sleep(1000);
			result = element.getText();
		} while (!result.contains(searchData));
	}

	public boolean emailAddSearch(String email) throws InterruptedException {
		waitForWebElementToAppear(smLogo);
		performSearch(userEmailSearched, email);
		return userEmailSearched.getText().equalsIgnoreCase(email);

	}

	public boolean userSearch(String value, String expectedNamePart) throws InterruptedException {
	    //waitForWebElementToAppear(smLogo);
		Thread.sleep(3000);
	    performSearch(firstRowData, value);
	    String name = firstRowData.getText();
	    String[] splitName = name.split(" ");
	    
	    // Use a switch statement to determine which part of the name to compare
	    switch (expectedNamePart.toLowerCase()) {
	        case "firstname":
	            return splitName[0].equalsIgnoreCase(value);
	        case "lastname":
	            return splitName[1].equalsIgnoreCase(value);
	        default:
	            throw new IllegalArgumentException("Invalid expectedNamePart value: " + expectedNamePart);
	    }
	}


	private boolean parkingAreaSearch(WebElement element, String searchValue) throws InterruptedException {

		performSearch(element, searchValue);
		Thread.sleep(3000);
		String data = element.getText();
		// System.out.println(data);
		return data.equalsIgnoreCase(searchValue);

	}

	public boolean parkingAreaNameSearch(String searchValue) throws InterruptedException {

		return parkingAreaSearch(firstRowData, searchValue);
	}
	
	public boolean parkingAreaCodeSearch(String searchValue) throws InterruptedException {

		return parkingAreaSearch(searchAreaCode, searchValue);
	}
	
	private boolean setFilterRole(WebElement selectRole, String role) throws InterruptedException {
		
		waitForWebElementToAppear(smLogo);
		Thread.sleep(3000);
		filterBtn.click();
		roleCheckbox.click();
		roleDropdown.click();
		selectRole.click();
		Thread.sleep(1000);
		clickApply.click();
		
		boolean allRole;
		do {
			 allRole = roleColumn.stream().map(WebElement::getText)
					.allMatch(text -> text.trim().equalsIgnoreCase(role));
			Thread.sleep(500);
		} while (!allRole);
		return allRole;
	}

	public boolean filterByEncoderRole() throws InterruptedException {
		return setFilterRole(selectEncoderRole, "Encoder");
		
	}
	
	public boolean filterByAdminRole() throws InterruptedException {
		return setFilterRole(selectAdminRole, "Admin");
		
	}
}
