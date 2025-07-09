package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.MyOrderPage;

@Listeners(TestListener.class)
public class MyOrderPageTest extends BaseTest {

    @Test
    public void myOrderPageTestCase() throws InterruptedException {
        MyOrderPage orderlist = new MyOrderPage(driver);
        orderlist.validateMyOrderPage();
        orderlist.shipmentClick();
        orderlist.validateShipmentDetailPage();
        System.out.println("clicked on shipment");
        orderlist.cancelButtonClick();
    }
}
