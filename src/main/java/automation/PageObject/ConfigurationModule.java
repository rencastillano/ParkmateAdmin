package automation.PageObject;

import java.util.List;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

	@FindBy(css = ".fixed.top-10.right-10.z-50.bg-sm-white.pt-5.py-10.drop-shadow-lg.rounded-lg.flex.gap-x-2")
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
	
	@FindBy(css=".text-xs.text-sm-error")
	WebElement dupErrorMsg;
	
	
	//Pricing and Fee Packages
	
	@FindBy(xpath="//div/div[3]/div/div[2]/a/div[2]")
	WebElement pricingAndFee;
	
	@FindBy(xpath="//span[text()='Create Fee Package']")
	WebElement createFeePackageBtn;
	
	@FindBy(xpath="//span[text()='Add Pricing Package']")
	WebElement addPricingPackageBtn;
	
	@FindBy(css="#base-fee")
	WebElement inputBaseFee;
	
	@FindBy(css="button[type='submit']")
	WebElement submitButton;
	
	@FindBy(xpath="//div[2]/div[1]/div[2]/div[2]/button[1]")
	WebElement editPricingBtn;
	
	@FindBy(xpath="//div[2]/div[1]/div[2]/div[2]/button[2]")
	WebElement deletePricingBtn;
	
	@FindBy(css=".pt-5 > div > div:nth-child(4) >div")
	List<WebElement> pricingSetupToParkingArea;
	
	By pricingSetup = By.cssSelector(".pt-5 > div > div:nth-child(4) >div");
	By feeParkingName = By.cssSelector(".text-sm-electric-blue.text-lg.font-henry-sans-semibold");
	By assignPricing = By.cssSelector(".px-6.py-2.text-sm.rounded.border.w-auto.border-sm-electric-blue.text-sm-electric-blue.undefined");
	
	// Method to press the Backspace key multiple times
    private static void pressBackspaceMultipleTimes(WebElement element, int numberOfBackspaces) {
        for (int i = 0; i < numberOfBackspaces; i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }
	public void createFeePackage(String baseFee) throws InterruptedException {
		pricingAndFee.click();
		waitForWebElementToAppear(createFeePackageBtn);
		createFeePackageBtn.click();
		addPricingPackageBtn.click();
		int numberOfBackspaces = 4;
		pressBackspaceMultipleTimes(inputBaseFee, numberOfBackspaces);
		Thread.sleep(1000);
		inputBaseFee.sendKeys(baseFee);
		submitButton.click();
		
	}
	
	public void deletePricing() throws InterruptedException {
		pricingAndFee.click();
		waitForWebElementToAppear(createFeePackageBtn);
		createFeePackageBtn.click();
		deletePricingBtn.click();
		Thread.sleep(1000);
		submitButton.click();
	}

	public void gotoConfigurationMod() {
		waitForWebElementToAppear(smLogo);
		configurationModule.click();
		
	}
	
	public void updatePricing(String newPrice) throws InterruptedException {
		pricingAndFee.click();
		waitForWebElementToAppear(createFeePackageBtn);
		createFeePackageBtn.click();
		editPricingBtn.click();
		Thread.sleep(1000);
		int numberOfBackspaces = 5;
		pressBackspaceMultipleTimes(inputBaseFee, numberOfBackspaces);
		Thread.sleep(1500);
		inputBaseFee.sendKeys(newPrice);
		submitButton.click();
	}
		
	private List<WebElement> getParkingAreaForPricing() {
	    return getElementsAfterAction(pricingAndFee::click, pricingSetup, () -> pricingSetupToParkingArea);
	}
//	private List<WebElement> getParkingAreaForPricing() {
//		pricingAndFee.click();
//		waitForElementToAppear(pricingSetup);
//		return pricingSetupToParkingArea;
//	}

//	private WebElement getParkingByName(String parkerName) {
//		WebElement name = getParkingAreaForPricing().stream()
//				.filter(parker -> parker.findElement(feeParkingName).getText().equalsIgnoreCase(parkerName)).findFirst()
//				.orElse(null);
//		return name;
//	}
	
	private WebElement getParkingByName(String parkerName) {
	    return getElementByName(getParkingAreaForPricing(), parkerName, feeParkingName);
	}

	public void assigningPricing(String parkerName) {
		WebElement name = getParkingByName(parkerName);
		if (name != null) {
			name.findElement(assignPricing).click();
			assign.click();
		} else {
			System.out.println("Parker type with name '" + parkerName + "' not found.");
		}

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
	    return getElementsAfterAction(() -> {}, parkerTypes, () -> createdParkerTypesList);
	}
	
//	private List<WebElement> getCreatedParkerTypes() {
//		waitForElementToAppear(parkerTypes);
//		return createdParkerTypesList;
//	}

//	private WebElement getParkerTypeByName(String parkerName) {
//		WebElement name = getCreatedParkerTypes().stream()
//				.filter(parker -> parker.findElement(pTypeName).getText().equalsIgnoreCase(parkerName)).findFirst()
//				.orElse(null);
//		return name;
//	}
	
	private WebElement getParkerTypeByName(String parkerName) {
	    return getElementByName(getCreatedParkerTypes(), parkerName, pTypeName);
	}

	public void clickDeleteParkerType(String parkerName) {
		WebElement name = getParkerTypeByName(parkerName);
		if (name != null) {
			name.findElement(deleteParker).click();
			deleteParkerBtn.click();
		} else {
			System.out.println("Parker type with name '" + parkerName + "' not found.");
		}

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
	    return getElementsAfterAction(backArrowIconBtn::click, parkingAreas, () -> parkingAreaList);
	}

//	private List<WebElement> getParkingAreas() {
//	    backArrowIconBtn.click();
//	    waitForElementToAppear(parkingAreas);
//	    return parkingAreaList;
//	}

//	private WebElement getParkingAreaByName(String parkingName) {
//		WebElement name = getParkingAreas().stream()
//	            .filter(parking -> parking.findElement(parkingAreaNames).getText().equals(parkingName))
//	            .findFirst().orElse(null);
//	    return name;
//
//	}
	
	private WebElement getParkingAreaByName(String parkingName) {
	    return getElementByName(getParkingAreas(), parkingName, parkingAreaNames);
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
	
	public boolean validateBannerMessage(String expectedMessage) {
		//System.out.println(bannerMessage.getText());
	    return bannerMessage.getText().equalsIgnoreCase(expectedMessage);
	}
	
	private String getErrorMessage(WebElement element) throws InterruptedException {
		Thread.sleep(1000);
		return element.getText();
	}

	public String duplicatedErrorMessage() throws InterruptedException {
		waitForWebElementToAppear(dupErrorMsg);
		return getErrorMessage(dupErrorMsg);
	}
	
	private List<WebElement> getElementsAfterAction(Runnable action, By waitForElement, Supplier<List<WebElement>> listSupplier) {
	    action.run();
	    waitForElementToAppear(waitForElement);
	    return listSupplier.get();
	}
	
	private WebElement getElementByName(List<WebElement> elements, String name, By locator) {
	    return elements.stream()
	            .filter(element -> element.findElement(locator).getText().equalsIgnoreCase(name))
	            .findFirst()
	            .orElse(null);
	}

	
	
}
