package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PlpPage;
import pages.WishlistPage;

public class WishlistPageTest extends BaseTest
{
    @Test(priority = 1)
    public void noItemIntoWishlist() throws InterruptedException {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        WishlistPage wishlist = new WishlistPage(driver);
        home.assertHomePageLoaded();
        home.clickLoginIcon();
        login.loginWithValidDetail();
        home.clickWishlistIcon();
        wishlist.clearWishlistIfNotEmpty();
        //wishlist.valiadationOnNoItemInWishlistpage();

    }
    @Test(priority = 3)
    public void shopNowclickOnWishlist() throws InterruptedException {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        home.assertHomePageLoaded();
        home.clickLoginIcon();
        login.loginWithValidDetail();
        home.clickWishlistIcon();
        WishlistPage wishlist = new WishlistPage(driver);
        wishlist.clearWishlistIfNotEmpty();
        //wishlist.validationAftershopNowCLick();
    }
    @Test(priority = 4)
    public void clickOnWishlistIconFromPlp() throws InterruptedException
    {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        WishlistPage wishlist = new WishlistPage(driver);
        home.assertHomePageLoaded();
        home.clickLoginIcon();
        login.loginWithValidDetail();
        home.clickWishlistIcon();
        wishlist.clearWishlistIfNotEmpty();
        PlpPage plp = new PlpPage(driver);
        plp.clickOnWishlistIconOnPlp();
        home.clickWishlistIcon();
        plp.validationProductAddToWishlistFromPlp();
    }
    @Test(priority = 5)
    public void addToBagFromWishlist() throws InterruptedException {

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        WishlistPage wishlist = new WishlistPage(driver);
        home.assertHomePageLoaded();
        home.clickLoginIcon();
        login.loginWithValidDetail();
        home.clickWishlistIcon();
       wishlist.addToBagFromWishlist();

    }
    @Test(priority = 6)
    public void removeItemFromWishlist() throws InterruptedException {
        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        WishlistPage wishlist = new WishlistPage(driver);
        home.assertHomePageLoaded();
        home.clickLoginIcon();
        login.loginWithValidDetail();
        home.clickWishlistIcon();
        wishlist.clearWishlistIfNotEmpty();

    }

}
