package homepage;

import pages.BaseTest;
import pages.HomePage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CaseFilterSearchTravel extends BaseTest {

	@Test
	@Order(1)
	public void testWebSiteUrl() {
		homePage.checkUrl();
	}

	@Test
	@Order(2)
	public void closeRedundantInformation() {
		homePage.closeRedundantInfo();
	}

	@Test
	@Order(3)
	public void testOtelField() {
		homePage.checkOtelTabisDefault();
	}

	@Test
	@Order(4)
	public void testDestinationField() {
		homePage.takeStringValueFromCSV();
	}

	@Test
	@Order(5)
	public void testDateField() throws InterruptedException {
		homePage.selectDate();
	}

	@Test
	@Order(6)
	public void testAddParentField() {
		homePage.checkParentCount();

		homePage.checkAfterAddedParent();
	}

	@Test
	@Order(7)
	public void testSearchButtonVisibility() throws InterruptedException {
		homePage.checkSearchButtonVisibility();
		homePage.search();
	}

	@Test
	@Order(8)
	public void testCityOnPage() throws InterruptedException {
		sortPage.checkPageLoad();
		sortPage.checkCityOnPage();
	}

	@Test
	@Order(9)
	public void testOrderFilterCount() throws InterruptedException {
		sortPage.clickRandomCheckbox();
	}

}