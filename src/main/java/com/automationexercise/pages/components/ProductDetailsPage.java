package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;


public class ProductDetailsPage {


    private final GUIDriver driver ;
    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
    }

    // variables
    private final String productDetailsPageEndpoint = "/product_details/";

    // locators

    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By reviewTextArea = By.id("review");
    private final By submitReviewButton = By.id("button-review");
    private final By reviewSuccessMessage = By.xpath("//div[@class='alert-success alert']//span");


    private final By quantityField = By.xpath("//input[@name='quantity']");

    private final By addToCartButton = By.xpath("//button[@class='btn btn-default cart']");

private final By addedToCartLabel = By.xpath("//div[@class='modal-body']/p[1]");

    private final By viewCartButton = By.cssSelector("p > [href=\"/view_cart\"]");


// dynamic locators

    private By productName(String productName){
        return By.xpath("// div[@class='col-sm-7']//h2[.='"+productName+"']");
    }
    private By productPrice(String productName){
        return By.xpath("//div[@class='product-information']//h2[.='"+productName+"']//following-sibling::span/span");
    }







    // actions
    @Step("Navigate to Product Details Page ")

    public ProductDetailsPage navigateToProductDetailsPage(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+productDetailsPageEndpoint);
        return this;
    }
@Step("Set product quantity to : {quantity}")
    public ProductDetailsPage setProductQuantity(String quantity){
        driver.element().type(quantityField,quantity);

        return this;
    }
@Step("Click on Add to Cart button")
    public ProductDetailsPage clickOnAddToCartButton(){
        driver.element().click(addToCartButton);
        return this ;
    }

    @Step("Click on View Cart button")
    public CartPage clickOnViewCartButton(){
        driver.element().click(viewCartButton);
        return new CartPage(driver);
    }


    @Step("Write a review with name : {name}, email : {email} and review : {review}")
    public ProductDetailsPage submitReview(String name,String email,String review){
        driver.element().type(this.name,name)
                .type(this.email,email)
                .type(this.reviewTextArea,review)
                .click(submitReviewButton);
        return this;
    }


    // validation methods
@Step("Validate item added to cart message : {expectedMessage}")
    public ProductDetailsPage validateItemAddedLabel(String expectedMessage){
        String actualMessage = driver.element().getText(addedToCartLabel);
        LogsManager.info("Validating added to cart message. Expected: " + expectedMessage + ", Actual: " + actualMessage);
        driver.verification().Equals(actualMessage,expectedMessage,"Added to cart message validation failed");
        return this;
    }


    @Step("Validate product details from product details page  : {productName} with price : {productPrice} ")
    public ProductDetailsPage validateProductDetails(String productName, String productPrice){
        String actualProductName = driver.element().getText(productName(productName));
        String actualProductPrice = driver.element().getText(this.productPrice(productName));
        LogsManager.info("Validating product details for product: " + actualProductName + " with price: " + actualProductPrice);
        driver.validation().Equals(actualProductName,productName,"Product name does not match . Expected product name: " + productName + ", Actual: " + actualProductName);
        driver.validation().Equals(actualProductPrice,productPrice,"Product price does not match. Expected product price: " + productPrice + ", Actual: " + actualProductPrice);
        return this;


    }


    @Step("Validate review submission success message : {expectedMessage}")
    public ProductDetailsPage validateReviewSubmissionSuccessMessage(String expectedMessage){
        String actualMessage = driver.element().getText(reviewSuccessMessage);
        driver.verification().Equals(actualMessage,expectedMessage,"Review message does not match. Expected: " + expectedMessage + ", Actual: " + actualMessage);
   return this;
    }


























}
