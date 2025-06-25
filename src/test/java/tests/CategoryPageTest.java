package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;

@Listeners(TestListener.class)
public class CategoryPageTest extends BaseTest {


    @Test
    public void testLoginToOrderFlow() throws InterruptedException {

        CategoryPage category = new CategoryPage(driver);



        category.navigateToLipstickCategory();

    }
}