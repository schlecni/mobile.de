package schlechni.mobile.de;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import schlechni.mobile.de.driver.WebDriverProperties;
import schlechni.mobile.de.exception.BrowserDriverException;
import schlechni.mobile.de.page.MobilePage;

@SpringBootTest
class ApplicationTests {


	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTests.class);

	@Autowired
	private WebDriverProperties webDriverProperties;

	private WebDriver driver;

	@BeforeEach
	public void setup() {
		initWebDriver();
		LOGGER.info("Accessing website: {}.", webDriverProperties.getUrl());
	}

	@Test
	void searchForCar_vw_golf_v() throws InterruptedException {
		searchForCar("volkswagen", "golf", "golf 5");
	}

	@Test
	void searchForCar_toyota_auris() throws InterruptedException {
		searchForCar("toyota", "auris");
	}

	@Test
	void searchForCar_dacia_duster() throws InterruptedException {
		searchForCar("dacia", "duster");
	}

	private void initWebDriver() {
		System.setProperty(webDriverProperties.getDriverKey(), webDriverProperties.getDriverPath());

		if (BrowserType.CHROME.equals(webDriverProperties.getDriverName())) {
			driver = new ChromeDriver(buildOptions());
		}
		if (BrowserType.FIREFOX.equals(webDriverProperties.getDriverName())) {
			driver = new FirefoxDriver();
		}
		if (BrowserType.EDGE.equals(webDriverProperties.getDriverName())) {
			driver = new EdgeDriver();
		}

		if (driver == null) {
			throw new BrowserDriverException("Didn't match any type of driver!!!!");
		}

		driver.get(webDriverProperties.getUrl());
	}

	private void searchForCar(String manufacturer, String model) throws InterruptedException {
		searchForCar(manufacturer, model, null);
	}

	private void searchForCar(String manufacturer, String model, String modelDescription) throws InterruptedException {
		MobilePage mobilePage = PageFactory.initElements(driver, MobilePage.class);

		LOGGER.info("Accept cookies.");
		mobilePage.acceptCookies();
		LOGGER.info("Change language to RO.");
		mobilePage.changeLanguageToRo();
		LOGGER.info("Search for car -> manufacturer: {}, model: {}{}.", manufacturer, model,
				modelDescription != null ? ", description: " + modelDescription : "");
		mobilePage.searchCar(manufacturer, model, modelDescription);

		Assertions.assertNotNull(mobilePage.getSuccessMessage());
		LOGGER.info("Results: {}.", mobilePage.getSuccessMessage().getText().split(" ")[0]);
		closeAllTabs();
	}

	private ChromeOptions buildOptions() {
		return new ChromeOptions().addArguments(
//                "--headless",
				"--disable-dev-shm-usage",
				"--disable-gpu",
				"--window-size=1920,1080",
				"--allow-insecure-localhost",
				"--no-sandbox"
		);
	}

	/**
	 * It closes all browser tabs
	 */
	private void closeAllTabs() {
		LOGGER.info("Closing all tabs...");
		driver.getWindowHandles().forEach(window -> driver.switchTo().window(window).close());
	}

}
