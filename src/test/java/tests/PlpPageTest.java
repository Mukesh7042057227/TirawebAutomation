package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.PlpPage;

@Listeners(TestListener.class)
public class PlpPageTest extends BaseTest {


    @Test
    public void testLoginToOrderFlow() throws InterruptedException {

        PlpPage plp=new PlpPage(driver);



        plp.sortBy();
        plp.clickOnProduct();

    }
}