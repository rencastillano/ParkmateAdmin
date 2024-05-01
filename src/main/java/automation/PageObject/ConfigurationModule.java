package automation.PageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;

public class ConfigurationModule extends AbstractComponent {

	WebDriver driver;

	public ConfigurationModule(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory

	@FindBy(xpath = "//span[normalize-space()='Configuration']")
	WebElement configurationModule;

	@FindBy(xpath = "//a[@href='/configuration/parkers']//div[@class='py-5 self-center']//*[name()='svg']")
	WebElement parkerEnrollement;

	@FindBy(xpath = "//a[normalize-space()='Create Parker Type']")
	WebElement createParkerTypeBtn;

	@FindBy(xpath = "//button[normalize-space()='Add Parker']")
	WebElement addParkerBtn;

	@FindBy(xpath = "//input[@id='name']")
	WebElement parkerTypeLabel;

	@FindBy(xpath = "//input[@id='code']")
	WebElement parkerTypeCode;

	@FindBy(xpath = "//div[3]/div[2]/div/input")
	WebElement assignedMallsDropdown;

	@FindBy(xpath = "//button[normalize-space()='Create']")
	WebElement createBtn;

	@FindBy(name = "parkerTypeId")
	WebElement parkerTypeIdDropdown;

	@FindBy(xpath = "//div[@class='text-sm font-henry-sans text-sm-default-text']")
	List<WebElement> parkerTypesCreated;

	@FindBy(css = ".border.rounded-lg.p-5.flex.justify-between.items-center")
	List<WebElement> createdParkerTypesList;

	By parkerTypes = By.cssSelector(".border.rounded-lg.p-5.flex.justify-between.items-center");
	By pTypeName = By.cssSelector(".text-sm.font-henry-sans.text-sm-default-text");
	By deleteParker = By.xpath("//div[@class='flex gap-2']/button[last()]");

	@FindBy(xpath = ("(//button[@class='bg-sm-accent-light-blue p-1 rounded-full'])[1]"))
	WebElement parkerEditBtn;

	@FindBy(xpath = "//button[normalize-space()='Delete']")
	WebElement deleteParkerBtn;

	@FindBy(xpath = "//div[@class='text-base']")
	WebElement errorMsg;

	@FindBy(xpath = "(//div[@class='hover:bg-slate-100 cursor-pointer'])[1]")
	WebElement mallToAssign;

	@FindBy(xpath = "//button[text()='Update']")
	WebElement updateBtn;

	@FindBy(xpath = "//div/div[4]/div[2]/div[1]/div[2]/button[2]")
	WebElement deleteParkerBtnIcon;

	@FindBy(css = "a[class='flex items-center space-x-2 text-blue-500 hover:underline']")
	WebElement backArrowIconBtn;

	@FindBy(xpath = "//div[@class='fixed top-10 right-10 z-50 bg-sm-white pt-5 py-10 drop-shadow-lg rounded-lg flex gap-x-2']")
	WebElement bannerMessage;
	
	@FindBy(css = ".flex.flex-col.gap-3 > div")
	List<WebElement> parkingAreaList;
	
	By parkingAreas = By.cssSelector(".flex.flex-col.gap-3 > div");
	By parkingAreaNames = By.cssSelector(".flex.gap-2.items-center > div");
	By assignParkerBtn = By.cssSelector(".border.border-sm-electric-blue.py-2.px-5.rounded-md.text-sm.text-sm-electric-blue");
	By plusButton = By.cssSelector(".flex.gap-2.items-center > button > svg");
	
	@FindBy(css = "#parkerTypeId")
	WebElement selectParkerType;

	@FindBy(xpath = "//*[@id='parkerTypeId']/option[2]")
	WebElement selectOption2;
	
	@FindBy(xpath="//button[text()='Assign']")
	WebElement assign;
	
	//Pricing and Fee Packages
	
	@FindBy(xpath="//div/div[3]/div/div[2]/a/div[2]")
	WebElement pricingAndFee;

	public void gotoConfigurationMod() {
		waitForWebElementToAppear(smLogo);
		configurationModule.click();

	}

	public void parkerEnrollment(String parkerType, String parkerCode) throws InterruptedException {

		parkerEnrollement.click();
		createParkerTypeBtn.click();
		addParkerBtn.click();
		parkerTypeLabel.sendKeys(parkerType);
		parkerTypeCode.sendKeys(parkerCode);
		createBtn.click();
		Thread.sleep(2000);

	}

	public void selectParkerTypeToEdit(String mall) {
		parkerEnrollement.click();
		createParkerTypeBtn.click();
		parkerEditBtn.click();
		assignedMallsDropdown.click();
		assignedMallsDropdown.sendKeys(mall);
		mallToAssign.click();
		updateBtn.click();

	}

	public void got0ListOfCreatedParkerTypes() {
		parkerEnrollement.click();
		createParkerTypeBtn.click();
	}

	private List<WebElement> getCreatedParkerTypes() {
		waitForElementToAppear(parkerTypes);
		return createdParkerTypesList;
	}

	private WebElement getParkerTypeByName(String parkerName) {
		WebElement name = getCreatedParkerTypes().stream()
				.filter(parker -> parker.findElement(pTypeName).getText().equalsIgnoreCase(parkerName)).findFirst()
				.orElse(null);
		return name;
	}

	public void clickDeleteParkerType(String parkerName) {
		WebElement name = getParkerTypeByName(parkerName);
		if (name != null) {
			name.findElement(deleteParker).click();
		} else {
			System.out.println("Parker type with name '" + parkerName + "' not found.");
		}

		deleteParkerBtn.click();
	}

	public void deleteParkerType() {
		deleteParkerBtnIcon.click();
		deleteParkerBtn.click();
	}

	public boolean deleteParkerTypeWithFeePackageValidation() {
		waitForWebElementToAppear(errorMsg);
		return errorMsg.getText().equalsIgnoreCase("Unable to delete parker type.");

	}

	private List<WebElement> getParkingAreas() {
	    backArrowIconBtn.click();
	    waitForElementToAppear(parkingAreas);
	    return parkingAreaList;
	}

	private WebElement getParkingAreaByName(String parkingName) {
		
		WebElement name = getParkingAreas().stream()
	            .filter(parking -> parking.findElement(parkingAreaNames).getText().equals(parkingName))
	            .findFirst().orElse(null);
	    return name;

	}

	public void parkerTypeAssigning(String parkingName) throws InterruptedException {
	    WebElement name = getParkingAreaByName(parkingName);
	    if (name != null) {
	    	name.findElement(plusButton).click();
	        name.findElement(assignParkerBtn).click();
	        selectParkerType.click();
	        selectOption2.click();
	        Thread.sleep(1000);
	        assign.click();
	    } else {
	        System.out.println("Parker type with name '" + parkingName + "' not found.");
	    }
	}

	public boolean createParkerTypeValidation() {
		//getParkerTypeByName(parkerName).isDisplayed();
		return bannerMessage.getText().equalsIgnoreCase("Parker type has been added successfully!");
	}

	public boolean updateValidation() {

		return bannerMessage.getText().equalsIgnoreCase("Parker type has been updated successfully!");

	}

	public boolean deleteValidation() {

		return bannerMessage.getText().equalsIgnoreCase("Parker type has been deleted successfully!");

	}
	
	@FindBy(css=".text-xs.text-sm-error")
	WebElement dupErrorMsg;
	
	private String getErrorMessage(WebElement element) throws InterruptedException {
		Thread.sleep(1000);
		return element.getText();
	}

	public String duplicatedErrorMessage() throws InterruptedException {

		return getErrorMessage(dupErrorMsg);
	}

	
	
}
