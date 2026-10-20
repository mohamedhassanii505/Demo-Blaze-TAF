package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.CartPage;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    private final GUIDriver driver;
    public CheckoutPage(GUIDriver driver){
        this.driver = driver;
    }

    // variables

private final String checkoutPageEndpoint = "/checkout";



    // locators

    private By placeOrderButton = By.xpath("//a[@class='btn btn-default check_out']");
    private By commentBox = By.xpath("//textarea");


    // order derails locators
    private By productName(String productName){
        return By.xpath("//a[.='"+productName+"']");
    }

    private By productUnitPrice(String productName){
        return By.xpath("(//h4/a[.='"+productName+"']//following::td[@class='cart_price']/p)[1]");
    }

    private By productQuantity(String productName){
        return By.xpath("(//h4/a[.='"+productName+"']//following::td[@class='cart_quantity']/button)[1]");
    }
    private By productTotalPrice(String productName) {
        return By.xpath("//a[.='" + productName + "']//following::p[@class='cart_total_price'][1]");
    }
    private By productTotalAmountPrice(String productName) {
        return By.xpath("//a[.='" + productName + "']//following::p[@class='cart_total_price'][2]");
    }




    // Delivery Address Locators

    private By deliveryName = By.xpath("//ul[@id='address_delivery']/li[@class='address_firstname address_lastname']");
    private By deliverycompany = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][1]");
    private By deliveryAddress1 = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][2]");
    private By deliveryAddress2 = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][3]");
    private By deliveryZipcodeCityState = By.xpath("//ul[@id='address_delivery']/li[@class='address_city address_state_name address_postcode']");
    private By deliveryCountry = By.xpath("//ul[@id='address_delivery']/li[@class='address_country_name']");
    private By deliveryMobileNumber = By.xpath("//ul[@id='address_delivery']/li[@class='address_phone']");


    // Billing Address Locators
    private By billingName = By.xpath("//ul[@id='address_invoice']/li[@class='address_firstname address_lastname']");
    private By billingcompany = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][1]");
    private By billingAddress1 = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][2]");
    private By billingAddress2 = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][3]");
    private By billingZipcodeCityState = By.xpath("//ul[@id='address_invoice']/li[@class='address_city address_state_name address_postcode']");
    private By billingCountry = By.xpath("//ul[@id='address_invoice']/li[@class='address_country_name']");
    private By billingMobileNumber = By.xpath("//ul[@id='address_invoice']/li[@class='address_phone']");



    // actions
@Step("Navigate to Checkout Page")
    public CheckoutPage navigateToCheckoutPage(){
        driver.browser().navigateTo(PropertyReader.getProperty("BaseUrlWeb")+checkoutPageEndpoint);
        return this;
    }
@Step("Click on Place Order Button")
    public PaymentPage clickOnPlaceOrderButton(String comment){
    driver.element().click(commentBox).type(commentBox,comment);
        driver.element().click(placeOrderButton);
        return new PaymentPage(driver);
    }



    // validations
    @Step("Verify Delivery Address Details")
    public CheckoutPage verifyDeliveryAddressDetails(String title,String fName,String lName, String company,String address1,
                                                     String address2,String zipcode,String city,String state,
                                                     String country,String mobileNumber){
    String actualName = driver.element().getText(deliveryName);
    String actualCompany = driver.element().getText(deliverycompany);
    String actualAddress1 = driver.element().getText(deliveryAddress1);
    String actualAddress2 = driver.element().getText(deliveryAddress2);
    String actualZipcodeCityState = driver.element().getText(deliveryZipcodeCityState);
    String actualCountry = driver.element().getText(deliveryCountry);
    String actualMobileNumber = driver.element().getText(deliveryMobileNumber);
    driver.validation().Equals(actualName,(title+". "+fName+" "+lName),"Delivery Name does not match")
            .Equals(actualCompany,company,"Delivery Company does not match")
            .Equals(actualAddress1,address1,"Delivery Address1 does not match")
            .Equals(actualAddress2,address2,"Delivery Address2 does not match")
            .Equals(actualZipcodeCityState,(zipcode+" "+city+" "+state),"Delivery City State Zipcode does not match")
            .Equals(actualCountry,country,"Delivery Country does not match")
            .Equals(actualMobileNumber,mobileNumber,"Delivery Mobile Number does not match");
    return this;
    }




    @Step("Verify Billing Address Details")
    public CheckoutPage verifyBillingAddressDetails(String title,String fName,String lName, String company,String address1,
                                                     String address2,String zipcode,String city,String state,
                                                     String country,String mobileNumber){

        driver.validation().Equals(driver.element().getText(billingName),(title+". "+fName+" "+lName),"Billing Name does not match")
                .Equals(driver.element().getText(billingcompany),company,"Billing Company does not match")
                .Equals(driver.element().getText(billingAddress1),address1,"Billing Address1 does not match")
                .Equals(driver.element().getText(billingAddress2),address2,"Billing Address2 does not match")
                .Equals(driver.element().getText(billingZipcodeCityState),(zipcode+" "+city+" "+state),"Billing City State Zipcode does not match")
                .Equals(driver.element().getText(billingCountry),country,"Billing Country does not match")
                .Equals(driver.element().getText(billingMobileNumber),mobileNumber,"Billing Mobile Number does not match");
        return this;
    }

    @Step("Validate product details in View Cart Page for product : {productName} , expected unit price : {expectedProductUnitPrice} " +
            ", expected quantity : {expectedQuantity} , expected total price : {expectedProductTotalPrice}")
    public CheckoutPage validateProductOfFullDetailsInViewCart(String productName, String expectedProductUnitPrice ,
                                                           String expectedQuantity, String expectedProductTotalPrice,String expectedProductTotalAmountPrice){
        String actualProductName = driver.element().getText(productName(productName));
        String actualUnitProductPrice =driver.element().getText(productUnitPrice(productName)) ;
        String actualQuantity = driver.element().getText(productQuantity(productName));
        String actualTotalProductPrice = driver.element().getText(this.productTotalPrice(productName));
        String actualTotalProductAmountPrice = driver.element().getText(this.productTotalAmountPrice(productName));

        driver.validation().Equals(actualProductName,productName,"product name does not match");

        driver.validation().Equals(actualUnitProductPrice,expectedProductUnitPrice,"product unit price does not match");
        driver.validation().Equals(actualQuantity,expectedQuantity,"product quantity does not match");
        driver.validation().Equals(actualTotalProductPrice,expectedProductTotalPrice,"product total price does not match");
        driver.validation().Equals(actualTotalProductAmountPrice,expectedProductTotalAmountPrice,"product total amount price does not match");

        return this;
    }
















}
