package pages;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

	protected WebDriver driver;


	public BaseMethod baseMethods;

	public HomePage homePage;
	public SortPage sortPage;

	@BeforeAll
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://www.setur.com.tr");
		baseMethods = new BaseMethod(driver);
		homePage = new HomePage(driver);
		sortPage = new SortPage(driver);

	}

	@AfterAll
	public void tearDown() {
		driver.quit();
	}
}
