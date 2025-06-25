package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners(TestListener.class)
public class ProductPageTest extends BaseTest {


    @Test
    public void testLoginToOrderFlow() throws InterruptedException {

        ProductPage product = new ProductPage(driver);


        product.setAddToCartBtn();

    }
}