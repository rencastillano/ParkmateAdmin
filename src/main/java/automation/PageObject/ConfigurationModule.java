package automation.PageObject;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

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
	
	@FindBy(xpath="//div/div/div[1]/div[2]/div[1]/div[2]/div")
	WebElement parkerNameDupMsg;
	
	@FindBy(xpath="//div/div/div[1]/div[2]/div[2]/div[2]/div")
	WebElement parkerCodeDupMsg;

	@FindBy(name = "parkerTypeId")
	WebElement parkerTypeIdDropdown;

	@FindBy(xpath = "//div[@class='text-sm font-henry-sans text-sm-default-text']")
	List<WebElement> parkerTypesCreated;

	@FindBy(css = ".border")
	List<WebElement> createdParkerTypesList;

	By parkerTypes = By.cssSelector(".border.rounded-lg.p-5.flex.justify-between.items-center");
	By pTypeName = By.cssSelector(".border>div.text-sm");
	By deleteParker = By.cssSelector(".border>div.flex>button.bg-sm-accent-pink-light");

	//@FindBy(xpath = "//button[text()='Delete']")
	@FindBy(css = "button.px-6")
	WebElement deleteParkerBtn;
	
	@FindBy(xpath = ("(//button[@class='bg-sm-accent-light-blue p-1 rounded-full'])[1]"))
	WebElement parkerEditBtn;

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
	
	@FindBy(css=".text-sm.font-henry-sans.text-sm-default-text")
	List<WebElement> pNameList;
	
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
		Thread.sleep(1500);
		submitButton.click();
	}
		
	private List<WebElement> getParkingAreaForPricing() {
	    return getElementsAfterAction(pricingAndFee::click, pricingSetup, () -> pricingSetupToParkingArea);
	}
	
	private WebElement getParkingByName(String parkerName) {
	    return getElementByName(getParkingAreaForPricing(), feeParkingName, parkerName);
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
	
	public void goToParkerEnrollment() {

		parkerEnrollement.click();
		createParkerTypeBtn.click();
		addParkerBtn.click();
		
	}
	
	public void getParkerTypeName(String parkerType) {
		parkerTypeLabel.clear();
		parkerTypeLabel.sendKeys(parkerType);
	}
	
	public void getParkerTypeCode(String parkerCode) {
		parkerTypeCode.clear();
		parkerTypeCode.sendKeys(parkerCode);
	}
	
	public void clickCreateButton() throws InterruptedException {
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
	
	private WebElement getParkerTypeByName(String parkerName) {
	    return getElementByName(getCreatedParkerTypes(), pTypeName, parkerName);
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
	
	public boolean ParkerCreationValidation(String parkerName) {
		String parkerNameSearch = pNameList.stream().map(WebElement::getText).filter(name -> name.contains(parkerName))
				.findFirst().orElse("");
		return parkerNameSearch.equalsIgnoreCase(parkerName);
	}

	public void deleteParkerType() {
		deleteParkerBtnIcon.click();
		waitForWebElementToBeClickable(deleteParkerBtn);
		deleteParkerBtn.click();
	}

	public String errorSpielValidation() {
		waitForWebElementToAppear(errorMsg);
		return errorMsg.getText();

	}
	
	private List<WebElement> getParkingAreas() {
	    return getElementsAfterAction(backArrowIconBtn::click, parkingAreas, () -> parkingAreaList);
	}
	
	private WebElement getParkingAreaByName(String parkingName) {
	    return getElementByName(getParkingAreas(), parkingAreaNames,  parkingName);
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
	
	public boolean parkerTypeAssigningWithMismatchParkingArea(String parkingName, String parkerName) throws InterruptedException {
	    WebElement name = getParkingAreaByName(parkingName);
	    if (name != null) {
	    	name.findElement(plusButton).click();
	        name.findElement(assignParkerBtn).click();
	        //selectParkerType.click();
	        
	        Select select = new Select(selectParkerType);

	        // Check if the "test" option is present
	        boolean isTestPresent = select.getOptions().stream()
	                                    .anyMatch(option -> option.getText().equals(parkerName));

	        if (!isTestPresent) {
	            System.out.println(parkerName+" is not available in the dropdown.");
	            return false;
	        } else {
	            // Select option by visible text
	            select.selectByVisibleText(parkerName);
	            return true;
	        }
	    }
		return false;
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
	
	private WebElement getElementByName(List<WebElement> elements, By locator, String name) {
	    return elements.stream()
	            .filter(element -> element.findElement(locator).getText().equalsIgnoreCase(name))
	            .findFirst()
	            .orElse(null);
	}
		
	public String handleDuplicate(String initialValue, Supplier<String> valueGenerator, Consumer<String> valueUpdater, Supplier<Boolean> condition) throws InterruptedException {
	    String value = initialValue;
	    try {
	        while (condition.get()) {
	            valueUpdater.accept(value = valueGenerator.get());
	            clickCreateButton();
	            Thread.sleep(4000);
	            System.out.println("New value generated: " + value);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Exception " + value);
	    }
	    return value;
	}

	public String handlingParkerNameDup(String initialParkerName) throws InterruptedException {
	    return handleDuplicate(initialParkerName,
	            () -> "SampleParker_" + generateRandomNumber(4),
	            this::getParkerTypeName,
	            () -> parkerNameDupMsg.isDisplayed());
	}

	public void handlingParkerCodeDup(String initialAreaCode) throws InterruptedException {
	     handleDuplicate(initialAreaCode,
	            () -> generateRandomChars(2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
	            this::getParkerTypeCode,
	            () -> parkerCodeDupMsg.isDisplayed());
	}
	
}
