package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentDonePage {
    private final GUIDriver driver;
    public PaymentDonePage(GUIDriver driver) {
        this.driver = driver;
    }


    // variables

    private final String paymentDonePageEndpoint = "/payment_done/0";

    // locators
 private By confirmedMessage = By.xpath("//p[.='Congratulations! Your order has been confirmed!']");
 private By continueButton = By.xpath("//a[@data-qa='continue-button']");
 private By downloadInvoiceButton = By.xpath("//a[.='Download Invoice']");

 // actions

    @Step("Navigate to Payment Done Page")
    public PaymentDonePage navigateToPaymentDonePage() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + paymentDonePageEndpoint);
        return this;
    }
    @Step("Click on Continue Button")
    public NavigationBarComponent clickOnContinueButton(){
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }
@Step("Download Invoice")
public PaymentDonePage downloadInvoice(){
        driver.element().click(downloadInvoiceButton);
        return this;
}

// validations
@Step("Verify Order Confirmation Message")
public PaymentDonePage verifyOrderConfirmMessage(String expectedMessage){
        String actualMessage = driver.element().getText(confirmedMessage);
        driver.verification().Equals(actualMessage,expectedMessage,"Order confirmation message is not as expected");
        return this;
    }

    public PaymentDonePage verifyDownloadedFile(String invoiceName) {
        driver.validation().assertFileExists(invoiceName,"File isn't exists in downloads folder");
        return this;

    }
}
