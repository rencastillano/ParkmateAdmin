package automation.AbstractComponents;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponent {

	private final WebDriver driver;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "div.flex.justify-between.mb-5.min-h-12 > svg")//"img[alt='SM Logo']")
	protected WebElement smLogo;

	@FindBy(xpath = "//li[@class='mb-3']")
	WebElement parkingUsersModule;

	@FindBy(xpath = "//li[2]//button[1]")
	WebElement parkingAreasMosule;

	public void waitForElementToAppear(By findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	public void waitForWebElementToAppear(WebElement findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}

	public void waitForWebElementToBeClickable(WebElement findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait.until(ExpectedConditions.elementToBeClickable(findBy));
	}

	public String generateRandomNumber(int length) {
		Random random = new Random();
		int randomNumber = random.nextInt((int) Math.pow(10, length));
		return String.format("%0" + length + "d", randomNumber);
	}

	public String generateRandomString() {
		String randomLetters = generateRandomChars(4, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		String randomNumbers = generateRandomChars(3, "0123456789");
		return randomLetters + randomNumbers;
	}

	private String generateRandomChars(int length, String source) {
		return ThreadLocalRandom.current().ints(length, 0, source.length()).mapToObj(source::charAt)
				.map(Object::toString).collect(Collectors.joining());
	}

	public void mobileAppSettings() {
		// Cast WebDriver to ChromeDriver to access DevTools
		ChromeDriver chromeDriver = (ChromeDriver) driver;
		DevTools devTools = chromeDriver.getDevTools();

		// Create DevTools session
		devTools.createSession();
		
		Map<String, Object> deviceMetrics = new HashMap<String, Object>();
		
		deviceMetrics.put("width", 360);
		deviceMetrics.put("height", 760);
		deviceMetrics.put("deviceScaleFactor", 2.625);
		deviceMetrics.put("mobile", true);
		
		chromeDriver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
//		devTools.send(Emulation.setDeviceMetricsOverride(360, 760, 2.625, true, Optional.empty(), Optional.empty(),
//				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
//				Optional.empty(), Optional.empty()));
	}
}
