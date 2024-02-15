package pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SortPage extends BaseTest {
	public SortPage(WebDriver driver) {
		this.driver = driver;
		this.baseMethods = new BaseMethod(driver);

		PageFactory.initElements(driver, this);

	}

	private final By lastCityValueLocator = By
			.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div/div[1]/div[2]/div[6]/div[last()]");

	private final By AntalyaListLocator = By
			.xpath("//*[@id='__next']/div[3]/div[2]/div/div[1]/div[2]/div[6]//span[contains(text(), 'Antalya')]");

	private final By firstCheckboxLocator = By.xpath("(//div[@class='sc-2569635-3 buefhp']/div)[1]");

	private final By checkboxesLocator = By.xpath("//div[@class='sc-2569635-3 buefhp']/div/span");

	private final By filteredCityCountLocator = By
			.xpath("//*[@id='__next']/div[3]/div[2]/div/div[1]/div[1]/div[2]/div/h3");

	private final By nextPageButtonFieldLocator = By
			.xpath("//*[@id='__next']/div[3]/div[2]/div/div[1]/div[2]/div[7]/div[1]/span[2]");

	public void checkPageLoad() throws InterruptedException {

		Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class);
		WebElement lastCityValue = driver.findElement(lastCityValueLocator);
		baseMethods.scrollPageToElement(lastCityValue);

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AntalyaListLocator));

	}

	public void checkCityOnPage() {
		List<WebElement> locationInfos = driver.findElements(AntalyaListLocator);

		for (int i = 0; i < locationInfos.size(); i++) {
			String expectedCity = "Antalya";
			String locationInfoString = locationInfos.get(i).getText();
			assertTrue(locationInfoString.contains(expectedCity));

		}

	}

	public void clickRandomCheckbox() throws InterruptedException {
		WebElement firstCheckbox = driver.findElement(firstCheckboxLocator);
		List<WebElement> checkboxes = driver.findElements(checkboxesLocator);

		baseMethods.scrollPageToElement(firstCheckbox);

		WebElement randomCheckbox = getRandomCheckbox(checkboxes);
		String randomCheckboxText = randomCheckbox.getText();
		int totalNumber = extractNumberFromText(randomCheckboxText);

		baseMethods.clickElement(randomCheckbox);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		WebElement filteredCityCount = driver.findElement(filteredCityCountLocator);

		wait.until(ExpectedConditions.visibilityOf(filteredCityCount));

		verifyOrderCount(totalNumber);
	}

	private WebElement getRandomCheckbox(List<WebElement> checkboxes) {
		Random random = new Random();
		int randomIndex = random.nextInt(checkboxes.size());
		return checkboxes.get(randomIndex);
	}

	private int extractNumberFromText(String text) {
		int index = text.indexOf("(");
		int lastIndex = text.indexOf(")");
		String numberStr = text.substring(index + 1, lastIndex);
		return Integer.parseInt(numberStr);
	}

	private void verifyOrderCount(int totalNumber) throws InterruptedException {
		baseMethods.scrollPageToBottom();
		List<WebElement> ordersName = driver.findElements(AntalyaListLocator);
		int ordersCount = ordersName.size();

		if (totalNumber < 21) {
			assertEquals(ordersCount, totalNumber);
			System.out.println("Actual Value: " + ordersCount + "\n" + "Expected Value: " + totalNumber);
		} else {
			String maxOrder = driver.findElement(nextPageButtonFieldLocator).getText();
			int maxOrderNumber = Integer.parseInt(maxOrder);
			assertEquals(maxOrderNumber, totalNumber);
			System.out.println("Actual Value: " + maxOrderNumber + "\n" + "Expected Value: " + totalNumber);
		}
	}

}
