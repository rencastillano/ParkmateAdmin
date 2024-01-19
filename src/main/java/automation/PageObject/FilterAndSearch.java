package automation.PageObject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;


public class FilterAndSearch extends AbstractComponent{
	
WebDriver driver;
	
	public FilterAndSearch(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//PageFactory	
	@FindBy(css="input[placeholder='Search']")
	WebElement search;
	
	@FindBy(xpath="//button[@class='absolute top-2.5 right-3 text-sm-placeholder']")
	WebElement clearBtn;
	
	@FindBy(xpath = "(//tr/td)[3]")
	WebElement userEmail;
	
	@FindBy(xpath="(//tr/td[@class='td-item capitalize pt-4 pb-4 svelte-1bss4kg'])[1]")
	WebElement firstRowAreaData;
	
	@FindBy(xpath="(//tr/td[1])[1]")
	WebElement firstRowUserData;
	
	@FindBy(css="h2[class='px-2']")
	WebElement filterBtn;
	
	@FindBy(css="#roleCheckbox")
	WebElement roleCheckbox;
	
	@FindBy(xpath="//*/div/button[.='Select Role ']")
	WebElement roleDropdown;
	
	@FindBy(xpath="//*/ul/li[contains(.,'Encoder')]")
	WebElement selEncoderRole;
	
	@FindBy(xpath="//*/ul/li[contains(.,'Mall Admin')]")
	WebElement selAdminRole;
	
	@FindBy(xpath="//*/div/button[.='Apply']")
	WebElement clickApply;
	
	@FindBy(xpath="//tr/td[2]")
	List<WebElement> roleColumn;
	
	public boolean emailAddSearch(String email) throws InterruptedException {
		waitForWebElementToAppear(smLogo);
		search.sendKeys(email);
		Thread.sleep(4000);
		boolean result = userEmail.getText().equalsIgnoreCase(email);
		return result;
	}
	
	public boolean userFirstNameSearch(String name) throws InterruptedException {
		waitForWebElementToAppear(smLogo);
		search.sendKeys(name);
		Thread.sleep(4000);
		String uname = firstRowUserData.getText();
		String[] sliptname = uname.split(" ");
		System.out.println(sliptname[0]);
		boolean result = sliptname[0].equalsIgnoreCase(name);
		return result;
	}
	public boolean userLastnameSearch(String name) throws InterruptedException {
		waitForWebElementToAppear(smLogo);
		search.sendKeys(name);
		Thread.sleep(4000);
		String uname = firstRowUserData.getText();
		String[] sliptname = uname.split(" ");
		boolean result = sliptname[2].equalsIgnoreCase(name);
		return result;
	}
	public boolean parkingNameSearch(String areaName) throws InterruptedException  {
		
		//waitForWebElementToAppear(smLogo);
		waitForWebElementToAppear(firstRowUserData);
		//Thread.sleep(5000);
		search.sendKeys(areaName);
		Thread.sleep(5000);
		String aname = firstRowAreaData.getText();
		System.out.println(aname);
		boolean result = aname.equalsIgnoreCase(areaName);
		return result;
				
	}
	public boolean filterByEncoderRole() throws InterruptedException {
		waitForWebElementToAppear(smLogo);
		filterBtn.click();
		roleCheckbox.click();
		roleDropdown.click();
		selEncoderRole.click();
		clickApply.click();
		Thread.sleep(5000);
		boolean allEncoder = roleColumn.stream()
                .map(WebElement::getText)
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
		boolean allAdmin = roleColumn.stream()
                .map(WebElement::getText)
                .allMatch(text -> text.trim().equalsIgnoreCase("admin"));
		return allAdmin;
		
	}

}
