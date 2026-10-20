package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.listeners.TestNGListeners;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.pages.components.ProductsPage;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;


@Listeners(TestNGListeners.class)

@Epic("Automation Exercise")
@Feature("UI Cart Details Management")
@Story("Cart Details")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohamed Hassan")
@UITest

public class CartPageTest extends BaseTest {
 


    @Step("add to cart from product details page and validate the product details in view cart page (product name, unit price, quantity, total price)")
    @Test
    public void validateProductDetailsInViewCartPageWithoutLoginTC() {
        new ProductsPage(driver).clickOnViewProductButton(testData.getJsonData("product1.name"))
                .setProductQuantity(testData.getJsonData("product1.quantity"))
                .clickOnAddToCartButton()
                .clickOnViewCartButton()
                .validateProductOfFullDetailsInViewCart(
                        testData.getJsonData("product1.name"),
                        testData.getJsonData("product1.price"),
                        testData.getJsonData("product1.quantity"),
                        testData.getJsonData("product1.totalPrice")
                );


    }

    @Step("Verify product details in cart")
    @Test
    public void verifyProductDetailsInCartWithoutLoginTC() {
       new ProductsPage(driver).navigateToProductPage()
               .clickOnAddToCartButton(testData.getJsonData("product1.name"))
               .validateItemAddedLabel(testData.getJsonData("messages.addedToCart"))
               .clickOnViewCartButton()
               .validateProductOfFullDetailsInViewCart(testData.getJsonData("product1.name")
               ,testData.getJsonData("product1.price")
               ,testData.getJsonData("product1.quantity")
               ,testData.getJsonData("product1.totalPrice"));
    }















    // configuration methods

    @BeforeClass
    public void preCondition() {

        testData = new JsonReader("cart-data");
    }

    @BeforeMethod
    public void setup(){
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();

        /* driver.browser().closeExtensionTab();*/



    }
    @AfterMethod
    public void tearDown(){
        driver.quitDriver();

    }
}
