package locators;

import org.openqa.selenium.By;

public class Locators {

    public static class HomePage {
        // Header Elements
        public static final By logoLink = By.xpath("//img[@alt='Tira' or @alt='TIRA' or contains(@alt, 'tira')]");
        public static final By searchInput = By.xpath("//input[@id='search']");
        public static final By profileIcon = By.xpath("//div[contains(@class, 'profile-icons profile-logout')]");
        public static final By cartIcon = By.xpath("//img[@title='Cart']");

        // Navigation Menu Links
        public static final By tiraRedNavLink = By.xpath("//a[contains(text(), 'Tira Red')]");
        public static final By offersNavLink = By.xpath("//a[contains(text(), 'Offers')]");
        public static final By topShelfNavLink = By.xpath("//a[contains(text(), 'Top Shelf')]");
        public static final By forYouNavLink = By.xpath("//a[contains(text(), 'For You')]");
        public static final By whatsNewNavLink = By.xpath("//a[contains(text(), \"What's New\")]");
        public static final By makeupNavLink = By.xpath("//a[contains(text(), 'Makeup')]");
        public static final By skinNavLink = By.xpath("//a[contains(text(), 'Skin')]");
        public static final By hairNavLink = By.xpath("//a[contains(text(), 'Hair')]");
        public static final By fragranceNavLink = By.xpath("//a[contains(text(), 'Fragrance')]");
        public static final By menNavLink = By.xpath("//a[contains(text(), 'Men')]");
        public static final By bathBodyNavLink = By.xpath("//a[contains(text(), 'Bath & Body') or contains(text(), 'Bath and Body')]");
        public static final By toolsAppliancesNavLink = By.xpath("//a[contains(text(), 'Tools & Appliances') or contains(text(), 'Tools and Appliances')]");
        public static final By momBabyNavLink = By.xpath("//a[contains(text(), 'Mom & Baby') or contains(text(), 'Mom and Baby')]");
        public static final By wellnessNavLink = By.xpath("//a[contains(text(), 'Wellness')]");
        public static final By minisNavLink = By.xpath("//a[contains(text(), 'Minis')]");
        public static final By homegrownNavLink = By.xpath("//a[contains(text(), 'Homegrown')]");
        public static final By giftsNavLink = By.xpath("//a[contains(text(), 'Gifts') or contains(text(), 'Gifting')]");
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
    public static class WishlistPage
    {
        public static final By clickOnWishlistIconFromHome = By.xpath("//img[@title='Wishlist']");
        public static final By removeProductfromWishlist = By.xpath("//div/a/div/div[1]/div[1]/div[1]/div/div/div/img[1][@alt='remove from wishlist icon']");
        public static final By moveToBag= By.xpath("//div/button[text()=' Move to Bag ']");
        public static final By blankWishlistPage= By.xpath("//p[contains(text(),' Your wishlist is empty! ')]");
        public static final By shopNowClick= By.xpath("//span[text()='Shop now']");
        public static final By hoveredOnProduct= By.xpath("//div[contains(@class, 'product-card')]");
        public static final By productRemoveToastMsg= By.xpath("//div[contains(text(),'Product has been')]");
        public static final By productMoveToBagFromWishlist= By.xpath("//div[contains(text(),'Product has been added to cart')]");



        // May need better locator
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
        public static final By sortByDropdown = By.xpath("//div[@class='selected-options']");
       // public static final By sortByDropdown1 = By.xpath("//span/img");
        public static final By priceHighToLow = By.xpath("//span[text()='Price High to Low']");
        public static final By clickOnProduct = By.xpath("//div[1]/a/div/div[1][@class='product-card']");
        public static final By validatePlpPage= By.xpath("//div[2]/div/div[1]/div[1]/div/div/p[contains(text(),'Delivering to')]");
        public static final By clickOnWishlistIconFromPlp= By.xpath("//div[1]/a/div/div[1]/div[1]/div[1]/div/div/div/img");
        public static final By productAddToWishlistToastMsg= By.xpath("//div[contains(text(),'Product has been')]");
//        public static By optionXpath(int value) {
//            return By.xpath("(//div[contains(@class,'dropdown-options')])[\" + value + \"]");
//        }
        public static final By sortOptionList= By.xpath("//div[contains(@class,'dropdown-options')]");
        public static final By allOptions= By.xpath("//li[@role='option']");
        public static final By selectedSortText = By.xpath("//span[@class='text-line-clamp']");

        // Pincode related locators
        public static final By pincodeButton = By.xpath("//div[2]/div/div[1]/div[1]/div/button/span[@class='postal-code']");
        public static final By pincodeSidebar = By.xpath("//div[text()='Enter your location']");
        public static final By pincodeCloseButton = By.xpath("//div/img[@alt='cross icon']");

        public static By sortOptionList(int index) {
            return By.xpath("(//li[@role='option'])[" + index + "]");
        }



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
