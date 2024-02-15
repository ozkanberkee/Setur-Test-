package pages;

import static org.testng.AssertJUnit.assertEquals;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BaseTest {
	private WebDriver driver;
	CSVReader csvReader = new CSVReader();

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.baseMethods = new BaseMethod(driver); // BaseMethod nesnesini başlatın.

		PageFactory.initElements(driver, this);

	}

	String expectedUrl = "https://www.setur.com.tr/";
	String expectedTabValue = "Otel";
	private final By passionFlowerLocator = By.id("responsive-image-1689835577900100");
	private final By searchButtonLocator = By.xpath("//button[@class='sc-8de9de7b-0 dYTYAP']");
	private final By passionFlowerCloseButtonLocator = By.xpath("(//*[@id='close-button-1454703513202'])[2]");
	private final By cookieLocator = By.id("CybotCookiebotDialog");
	private final By cookieLocatorCloseButton = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinDeclineAll");
	private final By otelTabLocator = By.xpath("//span[text()='Otel']");
	private final By goingToLocator = By.xpath("//input[@placeholder='Otel Adı Veya Konum']");
	private final By firstMatchedValueLocator = By.xpath("(//div[@class='sc-f22c276b-4 jwQXsD'])[1]");
	private final By schedulePlanLocator = By.xpath("//div[text()='Giriş - Çıkış Tarihleri']");
	private final By nexthMonthButtonLocator = By.xpath("//button[@class='sc-8de9de7b-0 kCGMge sc-147d3380-2 cULZMP']");
	private final By actualMonthYearLocator = By.xpath(
			"//*[@id='__next']/div[3]/div[3]/div[1]/div[2]/div/div/div/div[2]/div[2]/div/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[3]/div/div/strong");
	private final By beforeAddParentLocator = By
			.xpath("//*[@id=\"__next\"]/div[3]/div[3]/div[1]/div[2]/div/div/div/div[2]/div[3]/div/div/div[1]/span[2]");
	private final By expandParentFieldLocator = By
			.xpath("//*[@id=\"__next\"]/div[3]/div[3]/div[1]/div[2]/div/div/div/div[2]/div[3]/div/div/div[1]/span[2]");
	private final By increaseParentLocator = By.xpath("(//button[@data-testid='increment-button'])[1]");

	public void checkUrl() {
		String currentUrl = driver.getCurrentUrl();
		assertEquals(currentUrl, expectedUrl);
	}

	public void closeRedundantInfo() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(passionFlowerLocator));
		WebElement passionFlowerCloseButton = driver.findElement(passionFlowerCloseButtonLocator);

		baseMethods.clickElement(passionFlowerCloseButton);

		WebElement cookieLocatorClose = driver.findElement(cookieLocatorCloseButton);

		wait.until(ExpectedConditions.visibilityOfElementLocated(cookieLocator));
		baseMethods.clickElement(cookieLocatorClose);

	}

	public void checkOtelTabisDefault() {
		String defaultTabValue = driver.findElement(otelTabLocator).getText();
		assertEquals(defaultTabValue, expectedTabValue);
	}

	public void takeStringValueFromCSV() {
		WebElement goingTo = driver.findElement(goingToLocator);
		String projectPath = System.getProperty("user.dir");
		String filePath = projectPath + "\\resources\\data.csv";

		String cityValue = csvReader.readCityValue(filePath);
		baseMethods.clickElement(goingTo);
		goingTo.sendKeys(cityValue);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.presenceOfElementLocated(firstMatchedValueLocator));
		WebElement firstMatchedValue = driver.findElement(firstMatchedValueLocator);

		baseMethods.clickElement(firstMatchedValue);
	}

	public void selectDate() throws InterruptedException {
		WebElement schedulePlan = driver.findElement(schedulePlanLocator);
		baseMethods.clickElement(schedulePlan);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(nexthMonthButtonLocator));
		WebElement nextMonthButton = driver.findElement(nexthMonthButtonLocator);

		while (true) {
			String actualMonthYearText = driver.findElement(actualMonthYearLocator).getText();
			if (actualMonthYearText.startsWith("Nisan")) {
				Thread.sleep(5000);
				break;
			} else
				baseMethods.clickElement(nextMonthButton);
		}
		selectScheduleDate(1);
		selectScheduleDate(7);

	}

	public void selectScheduleDate(int day) {
		WebElement pickScheduleElement = driver
				.findElement(By.xpath("(//span[@class='sc-1720695c-0 kNqhKR' and text()='" + day + "'])[2]"));
		Actions action = new Actions(driver);
		action.click(pickScheduleElement).perform();

	}

	public int checkParentCount() {
		String beforeAdded = driver.findElement(beforeAddParentLocator).getText();
		String[] childAndParent = beforeAdded.split(",");
		String trimmedChildAndParent = childAndParent[1].trim();
		int beforeAddedParentCount = Integer.parseInt(trimmedChildAndParent.substring(0, 1));
		return beforeAddedParentCount;
	}

	public void addParent() {
		WebElement expandParentField = driver.findElement(expandParentFieldLocator);
		baseMethods.clickElement(expandParentField);
		WebElement increaseParent = driver.findElement(increaseParentLocator);

		baseMethods.clickElement(increaseParent);
	}

	public void checkAfterAddedParent() {
		int beforeAddParentCount = checkParentCount();
		addParent();
		int afterAddParentCount = checkParentCount();
		assertEquals(afterAddParentCount, beforeAddParentCount + 1);
	}

	public void checkSearchButtonVisibility() throws InterruptedException {
		WebElement searchButton = driver.findElement(searchButtonLocator);
		baseMethods.scrollPageToElement(searchButton);

	}

	public void search() {
		WebElement searchButton = driver.findElement(searchButtonLocator);
		baseMethods.clickElement(searchButton);
	}

}
