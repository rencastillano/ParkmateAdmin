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
	WebElement userEmail;
	
	@FindBy(xpath="//tr/td[4]")
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
	WebElement selEncoderRole;

	@FindBy(xpath = "//*/ul/li[contains(.,'Mall Admin')]")
	WebElement selAdminRole;

	@FindBy(xpath = "//*/div/button[.='Apply']")
	WebElement clickApply;

	@FindBy(xpath = "//tr/td[2]")
	List<WebElement> roleColumn;
	
	private void performSearch(String searchData, String searchResult) throws InterruptedException {
	    search.sendKeys(searchData);
	    String result;
	    do {
	        Thread.sleep(2000);
	        result = firstRowData.getText();
	    } while (!result.equalsIgnoreCase(searchResult));
	}

	public boolean emailAddSearch(String email, String searchResult) throws InterruptedException {
		
		performSearch(email,searchResult);
		return userEmail.getText().equalsIgnoreCase(email);
		
	}

	public boolean userFirstNameSearch(String fname, String searchResult) throws InterruptedException {
		
		performSearch(fname, searchResult);
		String uname = firstRowData.getText();
		String[] sliptname = uname.split(" ");
		//System.out.println(sliptname[0]);
		return sliptname[0].equalsIgnoreCase(fname);
		
	}

	public boolean userLastnameSearch(String lname, String searchResult) throws InterruptedException {
		
		performSearch(lname, searchResult);
		String uname = firstRowData.getText();
		String[] sliptname = uname.split(" ");
		return sliptname[1].equalsIgnoreCase(lname);
		
	}

	public boolean parkingNameSearch(String areaName) throws InterruptedException {

		performSearch(areaName, areaName);
		Thread.sleep(1000);
		String aname = firstRowData.getText();
		//System.out.println(aname);
		return aname.equalsIgnoreCase(areaName);
		 
	}

	public boolean parkingAreaCodeSearch(String searchResult) throws InterruptedException {

		performSearch("0846", searchResult);
		Thread.sleep(3000);
		String areaCode = searchAreaCode.getText();
		//System.out.println(areaCode);
		return areaCode.equalsIgnoreCase("0846");
		
	}

	public boolean filterByEncoderRole() throws InterruptedException {
		waitForWebElementToAppear(smLogo);
		filterBtn.click();
		roleCheckbox.click();
		roleDropdown.click();
		selEncoderRole.click();
		clickApply.click();
		Thread.sleep(5000);
		boolean allEncoder = roleColumn.stream().map(WebElement::getText)
				.allMatch(text -> text.trim().equalsIgnoreCase("encoder"));
		return allEncoder;

	}

	public boolean filterByAdminRole() throws InterruptedException {
		waitForWebElementToAppear(smLogo);
		filterBtn.click();
		roleCheckbox.click();
		roleDropdown.click();
		selAdminRole.click();
		clickApply.click();
		Thread.sleep(5000);
		boolean allAdmin = roleColumn.stream().map(WebElement::getText)
				.allMatch(text -> text.trim().equalsIgnoreCase("admin"));
		return allAdmin;

	}

}
