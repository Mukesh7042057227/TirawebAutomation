package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ProductPage;

@Listeners(TestListener.class)
public class ProductPageTest extends BaseTest {


    @Test
    public void productPageTestCase() throws InterruptedException {

        ProductPage product = new ProductPage(driver);
        product.validatePdpPage();
        product.setAddToCartBtn();

    }
}