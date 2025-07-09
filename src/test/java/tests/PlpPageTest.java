package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.PlpPage;

@Listeners(TestListener.class)
public class PlpPageTest extends BaseTest {


    @Test
    public void plpPageTestCase() throws InterruptedException {

        PlpPage plp=new PlpPage(driver);
        plp.validatePlpPage();
        plp.sortBy();
        plp.clickOnProduct();

    }
}