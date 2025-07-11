package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CartPage;

@Listeners(TestListener.class)
public class CartPageTest extends BaseTest {


    @Test
    public void cartPageTestCase() throws InterruptedException {


        CartPage cart = new CartPage(driver);

        cart.openCart();
        cart.validateCartPage();
        cart.proceedToCheckout();

    }
}