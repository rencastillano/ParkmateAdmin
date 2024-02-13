package automation.PageObject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import automation.AbstractComponents.AbstractComponent;


public class Pagenation extends AbstractComponent {

	WebDriver driver;

	public Pagenation(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory

	@FindBy(css = ".mr-16")
	WebElement nextBtn;

	@FindBy(css = ".mr-1")
	WebElement backtBtn;

	@FindBy(css = "#rowCount")
	WebElement rowCountDropdown;

	@FindBy(xpath = "//tr/td[1]")
	List<WebElement> tablerow;

	public boolean nextButton() throws InterruptedException {
		waitForWebElementToAppear(smLogo);
		 try {
			 waitForWebElementToBeClickable(nextBtn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (nextBtn.isEnabled()) {
	        while (nextBtn.isEnabled()) {
	            nextBtn.click();
	            Thread.sleep(2000);
	        }
	    }
		boolean nextButton = !nextBtn.isEnabled();
	    return nextButton;
	}

	public boolean previousButton() throws InterruptedException {
		 Thread.sleep(2000);
		if(backtBtn.isEnabled())
		while (backtBtn.isEnabled()) {
			// Click the button
			backtBtn.click();
			Thread.sleep(2000);
		}
		boolean backButton = !backtBtn.isEnabled();
		return backButton;
	}

	public boolean selectRowCount() throws InterruptedException {
		Thread.sleep(3000);
		waitForWebElementToBeClickable(rowCountDropdown);
		Select selectRowCount = new Select(rowCountDropdown);

		for (WebElement option : selectRowCount.getOptions()) {
			selectRowCount.selectByValue(option.getAttribute("value"));
			Thread.sleep(5000);
		}
		int tableRowCount = tablerow.size();
		System.out.println("Iteration is complete:" + tableRowCount);
		return tableRowCount == 50;

	}
}
