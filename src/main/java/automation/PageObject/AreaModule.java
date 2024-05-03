package automation.PageObject;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;

public class AreaModule extends AbstractComponent {

	WebDriver driver;

	public AreaModule(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory

	@FindBy(css = "li[class='mb-3'] button:nth-child(1)")
	WebElement parkingUsersTab;

	@FindBy(css = "h2")
	WebElement areaTab;

	@FindBy(css = "li:nth-child(2) button:nth-child(1)")
	WebElement parkingAreasModule;

	@FindBy(linkText = "CREATE")
	WebElement create;

	@FindBy(css = "input[placeholder='Enter Parking Area Name']")
	WebElement parkingNameInput;

	@FindBy(css = "img[alt='Dropdown']")
	WebElement selectBranch;

	@FindBy(xpath = "//ul/li[@class='px-4 py-2 hover:border']")
	List<WebElement> smList;

	@FindBy(xpath = "//li[@class='px-4 py-2 hover:border']")
	List<WebElement> smBranches;

	@FindBy(xpath = "(//input[@placeholder='0'])[1]")
	WebElement carCapacity;

	@FindBy(xpath = "(//input[@placeholder='0'])[2]")
	WebElement motorcycleCapacity;

	@FindBy(css = ":nth-child(2) > .grid-cols-1 > :nth-child(1) > .relative > .input")
	WebElement fixRate;

	@FindBy(css = ".lg\\:auto > .grid-rows-1 > .grid-cols-1 > :nth-child(1) > :nth-child(2) > .input")
	WebElement mallCode;

	@FindBy(css = "[placeholder$='Enter Area Code']")
	WebElement areaCodeInput;

	@FindBy(xpath = "(//input[@type='time'])[1]")
	WebElement openingTime;

	@FindBy(xpath = "(//input[@type='time'])[2]")
	WebElement closingTime;

	@FindBy(xpath = "//*[text()='SAVE']")
	WebElement saveBtn;

	@FindBy(xpath = "//*[contains(text(),'CANCEL')]")
	WebElement cancelBtn;

	@FindBy(css = ".w-full > .flex > .ml-auto")
	WebElement cancelMondalButton;

	@FindBy(css = ".flex > .mr-auto")
	WebElement proceedModalButton;

	@FindBy(xpath = "//tr/td[1][@class='td-item capitalize pt-4 pb-4 svelte-1bss4kg']")
	List<WebElement> areaNameList;

	@FindBy(css = "tbody tr:nth-child(5)")
	WebElement nameToBeEdited;

	@FindBy(css = "tr td:nth-child(1)")
	List<WebElement> areaNameTable;

	@FindBy(xpath = "(//button[text()='+'])[1]")
	WebElement carPlusBtn;

	@FindBy(xpath = "(//button[text()='+'])[2]")
	WebElement motorcyclePlusBtn;

	@FindBy(xpath = "(//button[text()='-'])[1]")
	WebElement carMinusBtn;

	@FindBy(xpath = "(//button[text()='-'])[2]")
	WebElement motorcycleMinusBtn;

	@FindBy(css = "input[placeholder='Search']")
	WebElement search;

	@FindBy(xpath = "//*[@class='flex-grow text-sm self-center w-3/4']")
	WebElement banner;

	By tableRowBy = By.cssSelector("tbody tr:nth-child(1) td:nth-child(1)");

	@FindBy(css = "tr td:first-child")
	WebElement firstRowData;

	@FindBy(css = "tr:nth-child(1) > td:nth-child(4)")
	WebElement firstAreaCodeData;

	@FindBy(css = "tbody td:nth-child(4)")
	List<WebElement> areaCodeList;

	@FindBy(xpath = "//div[2]/div/div[1]/div[2]/div/p")
	WebElement areaNameDupMsg;

	@FindBy(xpath = "//div[3]/div[2]/div/div[2]/div[2]/p")
	WebElement areaCodeDupMsg;
	
	@FindBy(css="p[class$='text-red-500 text-xs mt-1']")
	WebElement parkingDupErrMsg;

	@FindBy(xpath = "//div[2]/div[1]/div[1]/div[3]/p")
	WebElement fixedRateErrorMsg;

	@FindBy(xpath = "//div[1]/div[4]/div/div[1]/div[2]/p")
	WebElement carCapacityErrorMsg;

	@FindBy(xpath = "//div[1]/div[4]/div/div[2]/div[2]/p")
	WebElement motorcycleCapacityErrorMsg;

	@FindBy(css=":nth-child(1) > .capitalize")
	WebElement banner2;

	public FilterAndSearch goToAreaPage() {
		waitForWebElementToAppear(smLogo);
		waitForWebElementToBeClickable(parkingAreasModule);
		parkingAreasModule.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FilterAndSearch filterAndSearch = new FilterAndSearch(driver);
		return filterAndSearch;

	}

	public void clickCreate() {
		create.click();
	}

	public void genInfoParkingName(String areaName) {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parkingNameInput.click();
		parkingNameInput.clear();
		parkingNameInput.sendKeys(areaName);

	}

	public void genInfoSMList(String name) {
		selectBranch.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElement> filteredElements = smList.stream().filter(element -> element.getText().contains(name))
				.collect(Collectors.toList());

		if (!filteredElements.isEmpty()) {
			WebElement elementToClick = filteredElements.get(0);
			elementToClick.click();
			System.out.println(name+" is selected.");
			
		} else {
			System.out.println("No element containing "+name+" found.");
		}

		// selectSMFairview.click();
	}

	public void getCarCapacity(String value) {
		setVehicleCapacity(carCapacity, value);
	}

	public void getMotorcycleCapacity(String value) {
		setVehicleCapacity(motorcycleCapacity, value);
	}

	private void setVehicleCapacity(WebElement capacityElement, String value) {

		capacityElement.clear();
		capacityElement.sendKeys(value);

	}

	public void fixRate(String value) {
		fixRate.sendKeys(value);
	}

	public boolean smMallCode(String mallcode) {
		String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", mallCode);
		boolean match = value.equalsIgnoreCase(mallcode);
		return match;
	}

	public void getAreaCode(String areacode) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		areaCodeInput.click();
		areaCodeInput.clear();
		areaCodeInput.sendKeys(areacode);

	}

	public void parkingHours(String opentime, String closingtime) {
		openingTime.sendKeys(opentime);
		closingTime.sendKeys(closingtime);
	}

	public void clickSave() {
		waitForWebElementToBeClickable(saveBtn);
		saveBtn.click();

	}

	public boolean exitCreationAlert() throws InterruptedException {
		cancelBtn.click();
		Thread.sleep(500);
		cancelMondalButton.click();
		genInfoSMList("SM City Marikina");
		cancelBtn.click();
		Thread.sleep(500);
		proceedModalButton.click();
		return create.isDisplayed();
	}	
	
	public String handleDuplicate(String initialValue, Supplier<String> valueGenerator, Consumer<String> valueUpdater, Supplier<Boolean> condition) throws InterruptedException {
	    String value = initialValue;
	    try {
	        while (condition.get()) {
	            valueUpdater.accept(value = valueGenerator.get());
	            clickSave();
	            Thread.sleep(4000);
	            System.out.println("New value generated: " + value);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Exception " + value);
	    }
	    return value;
	}

	public String handlingAreaNameDup(String initialAreaName) throws InterruptedException {
	    return handleDuplicate(initialAreaName,
	            () -> "Area_" + generateRandomString(),
	            this::genInfoParkingName,
	            () -> areaNameDupMsg.isDisplayed());
	}

	public String handlingAreaCodeDup(String initialAreaCode) throws InterruptedException {
	    return handleDuplicate(initialAreaCode,
	            () -> generateRandomNumber(4),
	            this::getAreaCode,
	            () -> areaCodeDupMsg.isDisplayed());
	}
		
	public boolean areaCreationValidation(String areaName, String areaCode) {
		String nameResult = areaNameList.stream().map(WebElement::getText).filter(name -> name.contains(areaName))
				.findFirst().orElse("");

		String codeResult = areaCodeList.stream().map(WebElement::getText).filter(code -> code.contains(areaCode))
				.findFirst().orElse("");

		return nameResult.equalsIgnoreCase(areaName) && codeResult.equalsIgnoreCase(areaCode);
	}

//	public String validateBanner(String areaName) {
//		try {
//			return banner.getText();
//		} catch (Exception e) {
//			e.printStackTrace(); // Log the exception or handle it appropriately
//			return areaName; // Return an empty string or another appropriate default value
//		}
//	}

	public void selectAreaToBeEdited() throws InterruptedException {
		Thread.sleep(2000);
		nameToBeEdited.click();
		Thread.sleep(3000);

	}

	public String getRandomAreaName() throws InterruptedException {
		waitForElementToAppear(tableRowBy);
		Thread.sleep(4000);
		Random random = new Random();

		// Define the excluded index
		int excludedIndex = 5;

		int randomIndex;
		do {
			randomIndex = random.nextInt(areaNameTable.size());
		} while (randomIndex == excludedIndex);

		WebElement areaName = areaNameTable.get(randomIndex);
		return areaName.findElement(By.xpath("(//tr/td[1])[" + (randomIndex + 1) + "]")).getText();

	}

	private void performSearch(WebElement element, String searchData) throws InterruptedException {
		search.sendKeys(searchData);
		String result;
		do {
			Thread.sleep(2000);
			result = element.getText();
		} while (!result.equalsIgnoreCase(searchData));
		firstRowData.click();
	}

	public boolean parkingAreaUpdate(String areaNameToSearch) throws InterruptedException {
		performSearch(firstRowData, areaNameToSearch);
		Thread.sleep(3000);

		performButtonClicks(carPlusBtn, 5);
		performButtonClicks(carMinusBtn, 3);
		performButtonClicks(motorcyclePlusBtn, 5);
		performButtonClicks(motorcycleMinusBtn, 3);

		clickSave();
		return runValidation(areaNameToSearch);
	}

	private boolean runValidation(String areaNameUpdated) {

		waitForWebElementToAppear(banner2);
		String bannerText = banner2.getText();
		System.out.println(bannerText);
		return bannerText.equalsIgnoreCase(areaNameUpdated + " is successfully updated!");

	}

	private void performButtonClicks(WebElement button, int count) throws InterruptedException {
		for (int i = 0; i < count; i++) {
			Thread.sleep(200);
			button.click();
		}
	}

	private boolean adjustCapacity(WebElement capacityElement, WebElement pushButton, String count)
			throws InterruptedException {

		getCapacity(capacityElement, count);

		while (pushButton.isEnabled()) {
			pushButton.click();
			Thread.sleep(500);
		}

		return !pushButton.isEnabled();

	}

	public boolean increaseCarCapacity(String count) throws InterruptedException {
		return adjustCapacity(carCapacity, carPlusBtn, count);
	}

	public boolean decreaseCarCapacity(String count) throws InterruptedException {
		return adjustCapacity(carCapacity, carMinusBtn, count);
	}

	public boolean increaseMotorcycleCapacity(String count) throws InterruptedException {
		return adjustCapacity(motorcycleCapacity, motorcyclePlusBtn, count);
	}

	public boolean decreaseMotorcycleCapacity(String count) throws InterruptedException {
		return adjustCapacity(motorcycleCapacity, motorcycleMinusBtn, count);
	}

	private void getCapacity(WebElement capacityElement, String count) {

		setVehicleCapacity(capacityElement, count);
	}

	private String getErrorMessage(WebElement element) throws InterruptedException {
		Thread.sleep(1000);
		return element.getText();
	}

	public String parkingErrorMessage() throws InterruptedException {
		return getErrorMessage(parkingDupErrMsg);
	}

	public String fixedRateErrorMessage() throws InterruptedException {
		return getErrorMessage(fixedRateErrorMsg);
	}

	public String carCapacityErrorMessage() throws InterruptedException {
		System.out.println(carCapacityErrorMsg);
		return getErrorMessage(carCapacityErrorMsg);
	}

	public String motorcycleCapacityErrorMessage() throws InterruptedException {
		return getErrorMessage(motorcycleCapacityErrorMsg);
	}

}
