package locators;

import org.openqa.selenium.By;

public class Locators {

    public static class HomePage {

        public static final By categoryLink = By.xpath("//div[@class='category-navigation']//a");
    }
    public static class LoginPage {
        public static final By loginIcon = By.xpath("//a/div[@class='profile-icons profile-logout']");
        public static final By mobileInput = By.xpath("//input[@name='mobile-number']"); // replace with correct locator
        public static final By selectCheckbox = By.xpath("//img[@alt='checkbox']");
        public static final By clickSendOtpButton = By.xpath("//button[text()=' Send OTP ']");
        public static final By enterOtp= By.xpath("//div/input[@class='otp-input']");
        public static final By verifyOtp = By.xpath("//button[text()=' Verify OTP ']");
        public static final By validateLoginPage=By.xpath("//p[contains(text(),'Sign up now')]");
        public static final By validateInvalidPhone=By.xpath("//span[contains(text(),' Invalid Phone Number ')]");
        public static final By validateInvalidOtp=By.xpath("//span[contains(text(),'Entered OTP is invalid, please retry')]");
        public static final By outSideClickOnLoginPage=By.xpath("//input[contains(@class, 'common-x-input') and contains(@class, 'country-code')]");
        public static final By clickOnEditMobile=By.xpath("//button[contains(text(),'Edit Number')]");
        public static final By wishlistIcon=By.xpath("//div[@class=\"profile-icons hover-profile\"]");

    }
    public static class CategoryPage {
        public static final By menuMakeup = By.xpath("//div[2]/div[2]/div/div/div[2]/div/div/a"); // May need better locator
        public static final By subCategoryNail = By.partialLinkText("Nail");
    }
    public static class CheckoutPage {
        // Locator for review order / payment button
        public static final By reviewOrder = By.xpath("//button[text()=' Select Payment Method ']");
        public static final By sidebarContainer = By.cssSelector("div.sidebar-container");
        public static final By buyNowClick=By.xpath("//div[1]/button[text()=\" Buy Now \"]");

        // Element to scroll into view, for example: Buy Now button
        public static final By scrollToElement = By.xpath("//span/div[text()=\"Cash on Delivery\"]");
        public static final By validateCheckoutPage= By.xpath("//div[contains(text(),\" Review Order Details \")]");


    }
    public static class CartPage {
        public static final By openCart=By.xpath("//div/img[@title='Cart']");
        public static final By proceedToCheckout = By.xpath("//button[text()=' Checkout ']");
        public static final By validateCartPage = By.xpath("//div[contains(text(),\"Coupons & Bank Offers\")]");


    }
    public static class PlpPage {
        public static final By sortByDropdown = By.xpath("//span[text()='Relevance']");
        public static final By priceHighToLow = By.xpath("//span[text()='Price High to Low']");
        public static final By clickOnProduct = By.xpath("//div[1]/a/div/div[1][@class='product-card']");
        public static final By validatePlpPage= By.xpath("//div[2]/div/div[1]/div[1]/div/div/p[contains(text(),'Delivering to')]");


    }
    public static class ProductPage {
        public static final By addToCartBtn = By.xpath("//span[text()='Add to Bag']");
        public static final By validatePdpPage= By.xpath("//span[contains(text(),\"Add to Bag\")]");

    }
    public static class MyOrderPage {
        public static final By shipmentClick = By.xpath("//details[1]/div/div/div[1]/p");
        public static final By getOrderId = By.xpath("//details[1]/summary/h3");
        public static final By cancelButtonClick = By.xpath("//button[text()=' Cancel  ']");
        public static final By cancelReason = By.xpath("//div[text()=' Cancel Reason']");
        public static final By selectCancelReason = By.xpath("//span[text()=\"Delivery Time Too Long\"]");
        public static final By cancelShipment = By.xpath("//button[text()=\" cancel \"]");
        public static final By confirmationPopUp= By.xpath("//div[contains(text(),'Are you sure you want to cancel the product?')]");
        public static final By clickYesButton = By.xpath("//button[text()='Yes']");
        public static final By validateOrderPage = By.xpath("//h1[contains(text(),\"My Account\")]");
        public static final By validateShipmentDetailsPage=By.xpath("//div[contains(text(),\"Shipment Details\")]");

    }
    public static class ThankYouPage {
        public static final By getOrderId=By.xpath("//div[contains(text(),'OrderID')]");
        public static final By myOrderClick=By.xpath("//div[text()=\"Go to My Orders\"]");
    }
    public static class SearchPage {
        public static final By searchInput = By.id("search"); // update as per DOM
        public static final By resultItems = By.xpath("//div[1]/a/div/div[1][@class='product-card']");
        public static final By noResultsMessage = By.xpath("//p[contains(text(),' Nothing to find here ')]");
    }


}
