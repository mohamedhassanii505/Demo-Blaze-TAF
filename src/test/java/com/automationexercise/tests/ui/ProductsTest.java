package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.listeners.TestNGListeners;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.pages.components.ProductsPage;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;


@Listeners(TestNGListeners.class)

@Epic("Automation Exercise")
@Feature("UI Products Management")
@Story("Products Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohamed Hassan")
@UITest
public class ProductsTest extends BaseTest {



    // tests



    @Step("Search for a product and validate its details")
    @Test
    public void searchForProductWithoutLoginTC(){
        new ProductsPage(driver).navigateToProductPage()
                .searchForProduct(testData.getJsonData("searchedProduct.name"))
                .validateProductDetails(
                                        testData.getJsonData("searchedProduct.name")
                                       ,testData.getJsonData("searchedProduct.price")
                );

    }

    @Step("Add a product to the cart without logging in ")
   @Test
    public void addProductToCartWithoutLoginTC(){
       new ProductsPage(driver).navigateToProductPage()
                .clickOnAddToCartButton(testData.getJsonData("product1.name"))
                .validateItemAddedLabel(testData.getJsonData("messages.addedToCart"));
    }











    // configuration methods

    @BeforeClass
    public void preCondition() {

        testData = new JsonReader("products-data");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();

        /* driver.browser().closeExtensionTab();*/



    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quitDriver();

    }
}
