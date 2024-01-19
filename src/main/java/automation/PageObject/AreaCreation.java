package automation.PageObject;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.AbstractComponents.AbstractComponent;



public class AreaCreation extends AbstractComponent {

	WebDriver driver;

	public AreaCreation(WebDriver driver) {
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

	@FindBy(xpath = "(//*[@type='text'])[1]")
	WebElement enterParkingName;

	@FindBy(css = "img[alt='Dropdown']")
	WebElement selectBranch;

	@FindBy(xpath = "//li[@class='px-4 py-2 hover:border'][4]")
	WebElement selectSMFairview;

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
	WebElement areaCode;

	@FindBy(xpath = "(//input[@type='time'])[1]")
	WebElement openingTime;

	@FindBy(xpath = "(//input[@type='time'])[2]")
	WebElement closingTime;

	@FindBy(xpath = "//*[text()='SAVE']")
	WebElement saveBtn;

	@FindBy(xpath = "//tr/td[1][@class='td-item capitalize pt-4 pb-4 svelte-1bss4kg']")
	List<WebElement> areaNameList;

	@FindBy(css = ".text-red-500.text-xs.mt-1")
	WebElement duplicateErrMsgOnUpdate;

	@FindBy(css = ".text-red-500.text-xs.mt-1")
	WebElement duplicateErrMsgOnCreation;

	@FindBy(css = "tbody tr:nth-child(5)")
	WebElement nameToBeEdited;

	@FindBy(css = "tr td:nth-child(1)")
	List<WebElement> areaNameTable;

	By tableRowBy = By.cssSelector("tbody tr:nth-child(1) td:nth-child(1)");

	public FilterAndSearch goToAreaPage() {

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
		waitForWebElementToAppear(smLogo);
		create.click();
	}

	public void genInfoParkingName(String areaName) throws InterruptedException {
		Thread.sleep(5000);
		enterParkingName.clear();
		enterParkingName.sendKeys(areaName);
		// System.out.println(areaName);

	}

	public void genInfoSMList() {
		selectBranch.click();
		selectSMFairview.click();
	}

	public void genInfocarCapacity(String value) {
		carCapacity.sendKeys(value);
	}

	public void genInfomotorcycleCapacity(String value) {
		motorcycleCapacity.sendKeys(value);
	}

	public void fixRate(String value) {
		fixRate.sendKeys(value);
	}

	public boolean smMallCode(String mallcode) {
		String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", mallCode);
		boolean match = value.equalsIgnoreCase(mallcode);
		return match;
	}

	public void areaCode(String areacode) throws InterruptedException {
		Thread.sleep(3000);
		areaCode.clear();
		areaCode.sendKeys(areacode);
	}

	public void parkingHours(String opentime, String closingtime) {
		openingTime.sendKeys(opentime);
		closingTime.sendKeys(closingtime);
	}

	public void SaveCreation() {
		saveBtn.click();

	}

	public void refresh() throws InterruptedException {
		waitForWebElementToAppear(areaTab);
		parkingUsersTab.click();
	}

	public boolean creationValidation(String areaName) throws InterruptedException {

		//waitForWebElementToAppear(smLogo);
		Thread.sleep(3000);
		List<String> filteredNames = areaNameList.stream().map(WebElement::getText)
				.filter(name -> name.contains(areaName)).collect(Collectors.toList());
		String res = filteredNames.isEmpty() ? "" : filteredNames.get(0);
		System.out.println(res);
		boolean result = res.equalsIgnoreCase(areaName);
		return result;

	}

	public boolean dupErrMessage() {
		boolean errMsg = duplicateErrMsgOnUpdate.isDisplayed();
		return errMsg;
	}

	public void dupErrMessageOnCreation(String areacode) throws InterruptedException {
		try {
			if (!duplicateErrMsgOnCreation.isDisplayed()) {
				refresh();
			} else {
				areaCode(areacode);
				SaveCreation();
			}
		} catch (Exception e) {
			// Handle any exceptions or log a message
			e.printStackTrace();
		}
	}

	public void areaNameToBeEdited() throws InterruptedException {
		Thread.sleep(3000);
		nameToBeEdited.click();

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

		WebElement station = areaNameTable.get(randomIndex);
		String text = station.getText();
		return text;

	}
}
