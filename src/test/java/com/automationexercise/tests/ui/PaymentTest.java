package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.listeners.TestNGListeners;
import com.automationexercise.pages.CheckoutPage;
import com.automationexercise.pages.PaymentDonePage;
import com.automationexercise.pages.components.CartPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.pages.components.ProductsPage;
import com.automationexercise.pages.components.SignupLoginPage;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(TestNGListeners.class)
@Epic("Automation Exercise")
@Feature("UI Payment Management")
@Story("Payment Details")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohamed Hassan")
@UITest

public class PaymentTest extends BaseTest {
    String timeStamp = TimeManager.getSimpleTimeStamp();


    @Test
    public void registerNewAccount(){



        // precondition > create user account using API

        new UserManagementAPI().createRegisterUserAccount(testData.getJsonData("name"),
                        testData.getJsonData("email") + timeStamp + "@gmail.com"  ,
                        testData.getJsonData("password"),
                        testData.getJsonData("titleMale"),

                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber"))
                .verifyUserAccountCreatedSuccessfully();

    }
    @Test(dependsOnMethods = "registerNewAccount")
    public void loginToAccount(){
        new SignupLoginPage(driver).navigateToSignupLoginPage()
                .enterLoginEmail(testData.getJsonData("email")+ timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent
                .verifyUserLabel(testData.getJsonData("name"));

    }
    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount"})
    public void addProductsToCart(){
        new ProductsPage(driver).navigateToProductPage()
                .clickOnAddToCartButton(testData.getJsonData("product1.name"))
                .validateItemAddedLabel(testData.getJsonData("messages.addedToCart"))
                .clickOnViewCartButton()
                .validateProductOfFullDetailsInViewCart(testData.getJsonData("product1.name")
                        ,testData.getJsonData("product1.price")
                        ,testData.getJsonData("product1.quantity")
                        ,testData.getJsonData("product1.totalPrice"));

    }
    @Test(dependsOnMethods = {"addProductsToCart", "loginToAccount", "registerNewAccount"})
    public void checkout(){

        new CartPage(driver)
                .clickOnProceedToCheckoutButton()
                .verifyDeliveryAddressDetails(testData.getJsonData("titleMale")
                        ,testData.getJsonData("firstName")
                        ,testData.getJsonData("lastName")
                        ,testData.getJsonData("company")
                        ,testData.getJsonData("address1")
                        ,testData.getJsonData("address2")
                        ,testData.getJsonData("zipcode")
                        ,testData.getJsonData("city")
                        ,testData.getJsonData("state")
                        ,testData.getJsonData("country")
                        ,testData.getJsonData("mobileNumber")
                )
                .verifyBillingAddressDetails(testData.getJsonData("titleMale")
                        ,testData.getJsonData("firstName")
                        ,testData.getJsonData("lastName")
                        ,testData.getJsonData("company")
                        ,testData.getJsonData("address1")
                        ,testData.getJsonData("address2")
                        ,testData.getJsonData("zipcode")
                        ,testData.getJsonData("city")
                        ,testData.getJsonData("state")
                        ,testData.getJsonData("country")
                        ,testData.getJsonData("mobileNumber"));


    }



    @Test(dependsOnMethods = {"addProductsToCart", "loginToAccount", "registerNewAccount","checkout"})
    public void inputPaymentInfo(){
        new CheckoutPage(driver)
                .clickOnPlaceOrderButton(testData.getJsonData("messages.commentOfOrder"))
                .inputPaymentInfo(testData.getJsonData("paymentData.nameOnCard"),
                        testData.getJsonData("paymentData.cardNumber"),
                        testData.getJsonData("paymentData.cvc"),
                        testData.getJsonData("paymentData.expirationMonth"),
                        testData.getJsonData("paymentData.expirationYear"))
                .verifyOrderConfirmMessage(testData.getJsonData("messages.orderConfirmed"));

    }
    @Test(dependsOnMethods = {"inputPaymentInfo","checkout", "addProductsToCart", "loginToAccount", "registerNewAccount"})
    public void deleteAccountAsPostCondition(){
        new UserManagementAPI().deleteUserAccount(testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserAccountDeletedSuccessfully();
    }



    /*@Test(dependsOnMethods = {"addProductsToCart", "loginToAccount", "registerNewAccount","checkout","inputPaymentInfo"})
    public void continueShoppingAfterPayment(){
        new PaymentDonePage(driver).clickOnContinueButton();
    }

*/
































    @BeforeClass
    public void setup() {

        testData = new JsonReader("payment-data");
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
    }



    @AfterClass
    public void tearDown(){
        driver.quitDriver();

    }
}
