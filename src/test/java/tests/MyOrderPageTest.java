package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.MyOrderPage;

@Listeners(TestListener.class)
public class MyOrderPageTest extends BaseTest {

    @Test
    public void testLoginToOrderFlow() throws InterruptedException {
        MyOrderPage orderlist = new MyOrderPage(driver);
        orderlist.shipmentClick();
        System.out.println("clicked on shipment");
        orderlist.cancelButtonClick();
    }
}
