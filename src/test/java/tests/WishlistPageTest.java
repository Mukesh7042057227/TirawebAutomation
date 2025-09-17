package tests;

import base.BaseTest;
import listeners.ExtentReportListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PlpPage;
import pages.WishlistPage;

@Listeners({ExtentReportListener.class, TestListener.class})
public class WishlistPageTest extends BaseTest
{
    @Test(priority = 1)
    public void noItemIntoWishlist() {
        System.out.println("🔍 Starting test: noItemIntoWishlist");
        try {
            HomePage home = new HomePage(driver);
            LoginPage login = new LoginPage(driver);
            WishlistPage wishlist = new WishlistPage(driver);
            home.assertHomePageLoaded();
            home.clickLoginIcon();
            login.loginWithValidDetail();
            home.clickWishlistIcon();
            wishlist.clearWishlistIfNotEmpty();
            wishlist.valiadationOnNoItemInWishlistpage();

            System.out.println("✅ Test PASSED: noItemIntoWishlist");
        } catch (Exception e) {
            System.out.println("❌ Test FAILED: noItemIntoWishlist - " + e.getMessage());
            throw e;
        }
    }
    @Test(priority = 3)
    public void shopNowclickOnWishlist() {
        System.out.println("🔍 Starting test: shopNowclickOnWishlist");
        try {
            HomePage home = new HomePage(driver);
            LoginPage login = new LoginPage(driver);
            home.assertHomePageLoaded();
            home.clickLoginIcon();
            login.loginWithValidDetail();
            home.clickWishlistIcon();
            WishlistPage wishlist = new WishlistPage(driver);
            wishlist.clearWishlistIfNotEmpty();
            wishlist.validationAftershopNowCLick();

            System.out.println("✅ Test PASSED: shopNowclickOnWishlist");
        } catch (Exception e) {
            System.out.println("❌ Test FAILED: shopNowclickOnWishlist - " + e.getMessage());
            throw e;
        }
    }
    @Test(priority = 4)
    public void clickOnWishlistIconFromPlp()
    {
        System.out.println("🔍 Starting test: clickOnWishlistIconFromPlp");
        try {
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

            System.out.println("✅ Test PASSED: clickOnWishlistIconFromPlp");
        } catch (Exception e) {
            System.out.println("❌ Test FAILED: clickOnWishlistIconFromPlp - " + e.getMessage());
            throw e;
        }
    }
    @Test(priority = 5)
    public void addToBagFromWishlist() {
        System.out.println("🔍 Starting test: addToBagFromWishlist");
        try {
            HomePage home = new HomePage(driver);
            LoginPage login = new LoginPage(driver);
            WishlistPage wishlist = new WishlistPage(driver);
            home.assertHomePageLoaded();
            home.clickLoginIcon();
            login.loginWithValidDetail();
            home.clickWishlistIcon();
            wishlist.addToBagFromWishlist();

            System.out.println("✅ Test PASSED: addToBagFromWishlist");
        } catch (Exception e) {
            System.out.println("❌ Test FAILED: addToBagFromWishlist - " + e.getMessage());
            throw e;
        }
    }
    @Test(priority = 6)
    public void removeItemFromWishlist() {
        System.out.println("🔍 Starting test: removeItemFromWishlist");
        try {
            HomePage home = new HomePage(driver);
            LoginPage login = new LoginPage(driver);
            WishlistPage wishlist = new WishlistPage(driver);
            home.assertHomePageLoaded();
            home.clickLoginIcon();
            login.loginWithValidDetail();
            home.clickWishlistIcon();
            wishlist.clearWishlistIfNotEmpty();

            System.out.println("✅ Test PASSED: removeItemFromWishlist");
        } catch (Exception e) {
            System.out.println("❌ Test FAILED: removeItemFromWishlist - " + e.getMessage());
            throw e;
        }
    }


}
