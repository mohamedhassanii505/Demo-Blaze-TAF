package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsPage {
    private final GUIDriver driver;

    public NavigationBarComponent navigationBar;
    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    // variables
    private final String productPageEndpoint = "/products";

    // locators

    private  final By searchField = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By itemAddedToCartLabel = By.cssSelector(".modal-body > p");
    private final By viewCartButton = By.cssSelector("p > [href=\"/view_cart\"]");
    private final By continueShoppingButton = By.cssSelector(" .modal-footer > button");
// dynamic locators

    private By productName(String productName){
        return By.xpath("//div[@class='overlay-content']/p[.='" + productName + "']");
    }
    private By productPrice(String productName){
        return By.xpath("//div[@class='overlay-content']/p[.='"+ productName +"']//preceding-sibling::h2");
    }

    private By hoverOnProduct(String productName){
        return By.xpath("//div[@class='productinfo text-center']/p[.='"+ productName +"']");
    }

    private By addToCartButton(String productName){
        return By.xpath("//div[@class='overlay-content']/p[.='"+ productName +"']//following-sibling::a");
    }

    private By viewProductButton(String productName){
        return By.xpath("//p[.='"+ productName +"']//following::div[@class='choose'][1]");
    }


    // actions
@Step("Navigate to Products Page")
    public ProductsPage navigateToProductPage(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+ productPageEndpoint);
        return this;
    }

    @Step("Search for product : {productName}")
    public ProductsPage searchForProduct(String productName){
        driver.element().type(searchField,productName)
                .click(searchButton);
        driver.element().hover(hoverOnProduct(productName));
        return this;
    }

    @Step("CLick on Add to Cart button for product : {productName}")
    public ProductsPage clickOnAddToCartButton(String productName){
        driver.element().hover(hoverOnProduct(productName))
                .click(addToCartButton(productName));
        return this;
    }

    @Step("Click on View Product button for product : {productName}")
    public ProductDetailsPage clickOnViewProductButton(String productName){

        driver.element().click(viewProductButton(productName));
        return new ProductDetailsPage(driver);
    }


    @Step("Click on View Cart button")
    public CartPage clickOnViewCartButton(){
        driver.element().click(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Click on Continue Shopping button")
    public ProductsPage clickOnContinueShoppingButton(){
        driver.element().click(continueShoppingButton);
        return this;
    }



    // validations

    @Step("Validate product details for product : {productName} with price : {productPrice}")
    public ProductsPage validateProductDetails(String productName, String productPrice){
        String actualProductName = driver.element().getText(productName(productName));
        String actualProductPrice = driver.element().getText(this.productPrice(productName));
        LogsManager.info("Validating product details for product: " + actualProductName + " with price: " + actualProductPrice);
        driver.validation().Equals(actualProductName,productName,"Product name does not match ");
        driver.validation().Equals(actualProductPrice,productPrice,"Product price does not match");
        return this;


    }

    @Step("Validate item added label contains  text: {expectedText}")
    public ProductsPage validateItemAddedLabel(String expectedText){
        String actualText = driver.element().getText(itemAddedToCartLabel);
        LogsManager.info("Validating item added label text. Expected: " + expectedText + ", Actual: " + actualText);
       driver.verification().Equals(actualText,expectedText,"Item added label text does not match expected text");
        return this;
    }


}
