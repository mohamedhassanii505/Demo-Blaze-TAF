package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.CheckoutPage;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {

    private  final GUIDriver driver ;
    public CartPage(GUIDriver driver) {
        this.driver = driver ;
    }

    // variables
    private final String viewCartPageEndpoint = "/view_cart";

// locators

    private By productName(String productName){
        return By.xpath("//a[.='"+productName+"']");
    }

    private By productUnitPrice(String productName){
        return By.xpath("(//h4/a[.='"+productName+"']//following::td[@class='cart_price']/p)[1]");
    }

    private By productQuantity(String productName){
        return By.xpath("(//h4/a[.='"+productName+"']//following::td[@class='cart_quantity']/button)[1]");
    }
    private By productTotalPrice(String productName){
        return By.xpath("//a[.='"+productName+"']//following::p[@class='cart_total_price'][1]");
    }
    private By productDeleteButton(String productName){
        return By.xpath("//a[.='"+productName+"']//following::a[@class='cart_quantity_delete'][1]");
    }
    private By proceedToCheckoutButton(){
        return By.xpath("//a[.='Proceed To Checkout']");
    }

    // actions

    @Step("Navigate to View Cart Page")
    public CartPage navigateToCartPage(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+ viewCartPageEndpoint);
        return this;
    }

    @Step("Click on Proceed To Checkout Button")
    public CheckoutPage clickOnProceedToCheckoutButton(){
        driver.element().click(proceedToCheckoutButton());
                return new CheckoutPage(driver);
    }

    @Step("Click on Delete Button for product : {productName}")
    public CartPage clickOnDeleteButtonForProduct(String productName){
        driver.element().click(productDeleteButton(productName));
        return this;
    }




    // validations
    @Step("Validate product details in View Cart Page for product : {productName} , expected unit price : {expectedProductUnitPrice} " +
            ", expected quantity : {expectedQuantity} , expected total price : {expectedProductTotalPrice}")
    public CartPage validateProductOfFullDetailsInViewCart(String productName, String expectedProductUnitPrice ,
                                                           String expectedQuantity, String expectedProductTotalPrice){
        String actualProductName = driver.element().getText(productName(productName));
        String actualUnitProductPrice =driver.element().getText(productUnitPrice(productName)) ;
        String actualQuantity = driver.element().getText(productQuantity(productName));
        String actualTotalProductPrice = driver.element().getText(this.productTotalPrice(productName));

        driver.validation().Equals(actualProductName,productName,"product name does not match");

        driver.validation().Equals(actualUnitProductPrice,expectedProductUnitPrice,"product unit price does not match");
        driver.validation().Equals(actualQuantity,expectedQuantity,"product quantity does not match");
        driver.validation().Equals(actualTotalProductPrice,expectedProductTotalPrice,"product total price does not match");
return this;
    }









}
