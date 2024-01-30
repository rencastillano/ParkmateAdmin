package automation.PageObject;

import java.util.List;
import java.util.NoSuchElementException;
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

	@FindBy(css = "input[placeholder='Enter Parking Area Name']")
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
	
	@FindBy(xpath="//*[contains(text(),'CANCEL')]")
	WebElement cancelBtn;
	
	@FindBy(css = ".w-full > .flex > .ml-auto")
	WebElement cancelMondalButton;
	
	@FindBy(css = ".flex > .mr-auto")
	WebElement proceedModalButton;

	@FindBy(xpath = "//tr/td[1][@class='td-item capitalize pt-4 pb-4 svelte-1bss4kg']")
	List<WebElement> areaNameList;

	@FindBy(css = ".text-red-500.text-xs.mt-1")
	WebElement errMsgText;

	@FindBy(css = "tbody tr:nth-child(5)")
	WebElement nameToBeEdited;

	@FindBy(css = "tr td:nth-child(1)")
	List<WebElement> areaNameTable;

	@FindBy(css = "tbody tr:nth-child(1) td:nth-child(1)")
	WebElement firstRowSearch;

	@FindBy(xpath = "(//button[text()='+'])[1]")
	WebElement firstPlusBtn;

	@FindBy(xpath = "(//button[text()='+'])[2]")
	WebElement secondPlusBtn;

	@FindBy(xpath = "(//button[text()='-'])[1]")
	WebElement firstMinusBtn;

	@FindBy(xpath = "(//button[text()='-'])[2]")
	WebElement secondMinusBtn;

	@FindBy(css = "input[placeholder='Search']")
	WebElement search;

	@FindBy(xpath = "//*[@class='flex-grow text-sm self-center w-3/4']")
	WebElement banner;

	By tableRowBy = By.cssSelector("tbody tr:nth-child(1) td:nth-child(1)");
	
	@FindBy(css = "tr td:first-child")
	WebElement firstRowData;

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

	public void genInfoParkingName(String areaName) throws InterruptedException {
		Thread.sleep(5000);
		enterParkingName.click();
		enterParkingName.clear();
		enterParkingName.sendKeys(areaName);
		System.out.println(areaName);

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

	public void getAreaCode(String areacode) throws InterruptedException {
		Thread.sleep(2000);
		areaCode.click();
		areaCode.clear();
		areaCode.sendKeys(areacode);
	}

	public void parkingHours(String opentime, String closingtime) {
		openingTime.sendKeys(opentime);
		closingTime.sendKeys(closingtime);
	}

	public void clickSave() {
		waitForWebElementToBeClickable(saveBtn);
		saveBtn.click();

	}
		
	public void exitCreationAlert() throws InterruptedException {
		cancelBtn.click();
		Thread.sleep(500);
		cancelMondalButton.click();
		genInfoSMList();
		cancelBtn.click();
		Thread.sleep(500);
		proceedModalButton.click();
	}

	public void refresh() throws InterruptedException {
		waitForWebElementToAppear(areaTab);
		parkingUsersTab.click();
	}

	public boolean handlingDupAndValidation(String areacode, String areaName) throws InterruptedException {

		try {
			
			 while (errMsgText.isDisplayed()){
					getAreaCode(areacode);
					clickSave();
					Thread.sleep(4000);
					}
			
		} catch (NoSuchElementException e) {
			// Handle NoSuchElementException if duplicateErrMsgOnCreation is not displayed
			e.printStackTrace();			
		} catch (Exception e) {
		// Handle any other exceptions or log a message
		e.printStackTrace();
	}
			List<String> filteredNames = areaNameList.stream().map(WebElement::getText)
					.filter(name -> name.contains(areaName)).collect(Collectors.toList());
			String res = filteredNames.isEmpty() ? "" : filteredNames.get(0);
			System.out.println(res);
			boolean result = res.equalsIgnoreCase(areaName);
			return result;
	}

	public String errorMessage() {
		return errMsgText.getText();

	}

	public void areaNameToBeEdited() throws InterruptedException {
		Thread.sleep(3000);
		nameToBeEdited.click();

	}

	public String getRandomAreaName() throws InterruptedException {
		waitForElementToAppear(tableRowBy);
		Thread.sleep(5000);
		Random random = new Random();

		// Define the excluded index
		int excludedIndex = 5;

		int randomIndex;
		do {
			randomIndex = random.nextInt(areaNameTable.size());
		} while (randomIndex == excludedIndex);

		WebElement areaName = areaNameTable.get(randomIndex);
		String text = areaName.findElement(By.xpath("(//tr/td[1])[" + (randomIndex + 1) + "]")).getText();
		
		return text;

	}
	private void performSearch(String searchData, String searchResult) throws InterruptedException {
	    search.sendKeys(searchData);
	    String result;
	    do {
	        Thread.sleep(3000);
	        result = firstRowData.getText();
	    } while (!result.equalsIgnoreCase(searchResult));
	}
	public boolean parkingAreaUpdate() throws InterruptedException {
		performSearch("0127", "QA_Automation");
		Thread.sleep(5000);
		firstRowSearch.click();
		Thread.sleep(3000);

		performButtonClicks(firstPlusBtn, 5);
		performButtonClicks(secondPlusBtn, 5);
		performButtonClicks(firstMinusBtn, 3);
		performButtonClicks(secondMinusBtn, 3);

		clickSave();
		return runValidation();
	}
	
	private boolean runValidation() {
		
		waitForWebElementToAppear(banner);
		String bannerText = banner.getText();
		System.out.println(bannerText);
		return bannerText.equalsIgnoreCase("QA_Automation is successfully updated!");
		
	}

	private void performButtonClicks(WebElement button, int count) throws InterruptedException {
		for (int i = 0; i < count; i++) {
			Thread.sleep(200);
			button.click();
		}
	}

}
