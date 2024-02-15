package pages;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseMethod {
	private final WebDriver driver;
	private final WebDriverWait wait;

	public BaseMethod(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}

	public void clickElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		element.click();
	}

	public void scrollPageToElement(WebElement element) throws InterruptedException {
		try {
			if (element != null) {
				((JavascriptExecutor) driver).executeScript(
						"arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", element);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			Assert.fail("SEARCH BUTONU GORUNMUYOR !");
		}
	}

	public void scrollPageToBottom() throws InterruptedException {
		try {
			// Scroll to the bottom of the page using JavaScript
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
			Thread.sleep(1000);
		} catch (Exception e) {
			Assert.fail("Sayfanın en altına kaydırma işlemi başarısız oldu!");
		}
	}

}