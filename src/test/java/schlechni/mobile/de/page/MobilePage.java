package schlechni.mobile.de.page;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.stream.Collectors;

@Getter
public class MobilePage {

    @FindBy(xpath = "/html/body/div[3]/div/header/div[2]/nav/div/ul[1]/li[3]")
    private WebElement changeLanguageMenu;

    @FindBy(css = "#main-header > div.mde-react-header__navbar.clearfix > nav > div > ul:nth-child(1) > li.header-meta-actions-item.dropdown-is-open > ul > li:nth-child(7) > a")
    private WebElement romanianLanguageOption;

    @FindBy(id = "makeModelVariant1Make")
    private WebElement manufacturer;

    @FindBy(id = "makeModelVariant1Model")
    private WebElement model;

    @FindBy(id = "makeModelVariant1Description")
    private WebElement modelDescription;

    @FindBy(css = "body > div.g-content > div > section.quicksearch-row.u-margin-bottom-18 > div.g-row > div > div:nth-child(3) > form > div.g-row.u-display-flex.u-align-bottom > div:nth-child(2) > input")
    private WebElement submit;

    @FindBy(css = "#sticky-wrapper > div > input")
    private WebElement descriptionSubmit;

    @FindBy(css = "#mde-consent-modal-container > div > div.sc-jSFjdj.egQsJM > button")
    private WebElement acceptCookiesButton;

    @FindBy(css = "body > div > div > div.u-display-flex.u-margin-top-18 > section > section.result-block-header.g-row > div > h1")
    private WebElement successMessage;

    public void acceptCookies() {
        acceptCookiesButton.click();
    }

    public void changeLanguageToRo() {
        changeLanguageMenu.click();
        romanianLanguageOption.click();
    }

    public void searchCar(String manufacturer, String model) throws InterruptedException {
        searchCar(manufacturer, model, null);
    }

    public void searchCar(String manufacturer, String model, String modelDescription)
            throws InterruptedException {

        selectManufacturer(manufacturer);
        selectModel(model);
        submit.click();

        if (modelDescription != null) {
            Thread.sleep(500);
            this.modelDescription.sendKeys(modelDescription);
            descriptionSubmit.click();
        }
    }

    private void selectManufacturer(String input) {
        Select selectManufacturer = new Select(this.manufacturer);
        selectManufacturer.getOptions()
                .stream()
                .filter(webElement -> webElement.getText().equalsIgnoreCase(input))
                .collect(Collectors.toList())
                .get(0)
                .click();
    }

    private void selectModel(String input) throws InterruptedException {
        Select selectModel = new Select(this.model);
        Thread.sleep(500);
        selectModel.getOptions()
                .stream()
                .filter(webElement -> webElement.getText().trim().equalsIgnoreCase(input))
                .collect(Collectors.toList())
                .get(0)
                .click();
    }

}
