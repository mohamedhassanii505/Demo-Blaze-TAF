package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.listeners.TestNGListeners;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.pages.components.ProductDetailsPage;
import com.automationexercise.pages.components.ProductsPage;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.*;
import org.testng.annotations.*;


@Listeners(TestNGListeners.class)

@Epic("Automation Exercise")
@Feature("UI Products Management")
@Story("Product Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohamed Hassan")
@UITest
public class ProductDetailsPageTest  extends BaseTest {



@Step("Navigate to Product Details Page and validate its details")
    @Test
    public void navigateToProductDetailsPageWithoutLoginTC(){
        new ProductsPage(driver).navigateToProductPage()
                .clickOnViewProductButton(testData.getJsonData("product1.name"))
                .validateProductDetails(testData.getJsonData("product1.name"),
                        testData.getJsonData("product1.price"));

    }


@Step("Add a product to the cart from Product Details Page and validate the success message")
    @Test
    public void addProductToCartSuccessfullyWithoutLoginTC(){
        new ProductsPage(driver).clickOnViewProductButton(testData.getJsonData("product1.name"))
                .setProductQuantity(testData.getJsonData("product1.quantity"))
                .clickOnAddToCartButton()
                .validateItemAddedLabel(testData.getJsonData("messages.addedToCart"));
    }





    @Step("Submit a review for a product and validate the success message")
    @Test
    public void verifyReviewMessageWithoutLoginTC(){
          new ProductsPage(driver).navigateToProductPage()
                  .clickOnViewProductButton(testData.getJsonData("product1.name"))
                  .submitReview(testData.getJsonData("review.name"),testData.getJsonData("review.email"),testData.getJsonData("review.reviewText"))
                  .validateReviewSubmissionSuccessMessage(testData.getJsonData("messages.reviewSuccessMessage"));

        }














    // configuration methods

    @BeforeClass
    public void preCondition() {

        testData = new JsonReader("products-details-data");
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
