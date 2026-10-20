package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.JsonReader;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class PaymentPage {
    private final GUIDriver driver;
    public PaymentPage(GUIDriver driver) {
        this.driver = driver;
    }
// variables

private final String paymentPageEndPoint = "/payment";


    // locators
    private By nameOnCardField = By.xpath("//input[@data-qa='name-on-card']");
    private By cardNumberField = By.xpath("//input[@data-qa='card-number']");
    private By cvcField = By.xpath("//input[@data-qa='cvc']");
    private By expirationMonthField = By.xpath("//input[@data-qa='expiry-month']");
    private By expirationYearField = By.xpath("//input[@data-qa='expiry-year']");
    private By payAndConfirmOrderButton = By.xpath("//button[@data-qa='pay-button']");
    private By successMessage = By.xpath("//p[@class='alert-success']");



    // actions

    @Step("navigate to Payment Page")
    public PaymentPage navigateToPaymentPage(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+paymentPageEndPoint);
        return this;
    }

@Step("Input Payment Information")
    public PaymentDonePage inputPaymentInfo(String nameOnCard,String cardNumber,String cvc,String expirationMonth,String expirationYear){

       driver.element().type(nameOnCardField,nameOnCard)
               .type(cardNumberField,cardNumber)
               .type(cvcField,cvc)
               .type(expirationMonthField,expirationMonth)
               .type(expirationYearField,expirationYear);
       driver.element().click(payAndConfirmOrderButton);
        return new PaymentDonePage(driver);
}


    // validations
    /*@Step("Verify Placement of Order is Successful")
    public PaymentPage verifyPlacementOfOrderIsSuccessful(String expectedMessage){
        String actualMessage = driver.element().getText(successMessage);
        driver.verification().Equals(actualMessage,expectedMessage,"order placement success message is not as expected");
        return this;
    }
*/
















}



