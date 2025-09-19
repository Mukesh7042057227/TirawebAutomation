package locators;

import org.openqa.selenium.By;

public class Locators {

    public static class HomePage {
        // Header Elements
        public static final By logoLink = By.xpath("//a[@href='/' or @href='/home']//img[contains(@alt, 'ira') or contains(@src, 'logo')]");
        public static final By searchInput = By.xpath("//input[@id='search' or @placeholder='Search' or contains(@class, 'search')]");
        public static final By profileIcon = By.xpath("//div[contains(@class, 'profile') and contains(@class, 'logout')] | //a[contains(@href, 'login')] | //img[@alt='Profile' or @title='Profile']");
        public static final By cartIcon = By.xpath("//img[@title='Cart' or @alt='Cart'] | //a[contains(@href, 'cart')]");

        // Navigation Menu Links - Updated to be more flexible and reliable
        public static final By tiraRedNavLink = By.xpath("//nav//a[normalize-space(text())='Tira Red' or contains(@href, 'tira-red')]");
        public static final By offersNavLink = By.xpath("//nav//a[normalize-space(text())='Offers' or contains(@href, 'offers')]");
        public static final By topShelfNavLink = By.xpath("//nav//a[normalize-space(text())='Top Shelf' or contains(@href, 'top-shelf')]");
        public static final By forYouNavLink = By.xpath("//nav//a[normalize-space(text())='For You' or contains(@href, 'for-you')]");
        public static final By whatsNewNavLink = By.xpath("//nav//a[normalize-space(text())=\"What's New\" or contains(@href, 'whats-new')]");
        public static final By makeupNavLink = By.xpath("//div//a[text()='Makeup']");
        public static final By skinNavLink = By.xpath("//div//a[text()='Skin']");
        public static final By hairNavLink = By.xpath("//nav//a[normalize-space(text())='Hair' or contains(@href, 'hair')]");
        public static final By fragranceNavLink = By.xpath("//nav//a[normalize-space(text())='Fragrance' or contains(@href, 'fragrance')]");
        public static final By menNavLink = By.xpath("//nav//a[normalize-space(text())='Men' or contains(@href, 'men')]");
        public static final By bathBodyNavLink = By.xpath("//nav//a[normalize-space(text())='Bath & Body' or normalize-space(text())='Bath and Body' or contains(@href, 'bath')]");
        public static final By toolsAppliancesNavLink = By.xpath("//nav//a[normalize-space(text())='Tools & Appliances' or contains(@href, 'tools')]");
        public static final By momBabyNavLink = By.xpath("//nav//a[normalize-space(text())='Mom & Baby' or contains(@href, 'mom')]");
        public static final By wellnessNavLink = By.xpath("//nav//a[normalize-space(text())='Wellness' or contains(@href, 'wellness')]");
        public static final By minisNavLink = By.xpath("//nav//a[normalize-space(text())='Minis' or contains(@href, 'minis')]");
        public static final By homegrownNavLink = By.xpath("//nav//a[normalize-space(text())='Homegrown' or contains(@href, 'homegrown')]");
        public static final By giftsNavLink = By.xpath("//nav//a[normalize-space(text())='Gifts' or normalize-space(text())='Gifting' or contains(@href, 'gift')]");

        // Makeup hover Subcategories face
        public static final By makeupSubcategoryface = By.xpath("//div//a[text()='Face']");
        public static final By makeupSubcategoryBlush = By.xpath("//div//a[text()=' Blush ']");
        public static final By makeupSubcategoryBronzer = By.xpath("//div//a[text()=' Bronzer ']");
        public static final By makeupSubcategoryCOmpact = By.xpath("//div//a[text()=' Compact ']");
        public static final By makeupSubcategoryConcealers = By.xpath("//div//a[text()=' Concealers & Correctors ']");
        public static final By makeupSubcategoryContour= By.xpath("//div//a[text()=' Contour ']");
        public static final By makeupSubcategoryFoundation = By.xpath("//div//a[text()=' Foundation ']");
        public static final By makeupSubcategoryHighlighters = By.xpath("//div//a[text()=' Highlighters & Illuminators ']");
        public static final By makeupSubcategorySettingPowder = By.xpath("//div//a[text()=' Setting Powder ']");
        public static final By makeupSubcategoryMakeupRemover = By.xpath("//div//a[text()=' Makeup Remover ']");
        public static final By makeupSubcategoryPrimer = By.xpath("//div//a[text()=' Primer ']");
        public static final By makeupSubcategorySettingSpray = By.xpath("//div//a[text()=' Setting Spray ']");
        public static final By makeupSubcategoryBB = By.xpath("//div//a[text()=' BB & CC Creams ']");
        public static final By makeupSubcategoryLoosePowder = By.xpath("//div//a[text()=' Loose Powder ']");

        // Makeup hover Subcategories Eye
        public static final By makeupSubcategoryEye = By.xpath("//div//a[text()='Eye']");
        public static final By makeupSubcategoryEyeMakeupRemover = By.xpath("//div//a[text()=' Eye Makeup Remover ']");
        public static final By makeupSubcategoryEyebrowEnhancer = By.xpath("//div//a[text()=' Eyebrow Enhancer ']");
        public static final By makeupSubcategoryFalseEyelashes = By.xpath("//div//a[text()=' False Eyelashes ']");
        public static final By makeupSubcategoryEyeliner = By.xpath("//div//a[text()=' Eyeliner ']");
        public static final By makeupSubcategoryEyeShadow= By.xpath("//div//a[text()=' Eye Shadow ']");
        public static final By makeupSubcategoryKajal = By.xpath("//div//a[text()=' Kajal & Kohls ']");
        public static final By makeupSubcategoryMascara = By.xpath("//div//a[text()=' Mascara ']");
        public static final By makeupSubcategorySettingUnderEyeConcealer = By.xpath("//div//a[text()=' Under Eye Concealer ']");
        public static final By makeupSubcategoryEyeBases = By.xpath("//div//a[text()=' Eye Bases & Primers ']");

        // Makeup hover Subcategories Lip
        public static final By makeupSubcategoryLip = By.xpath("//div//a[text()='Lip']");
        public static final By makeupSubcategoryLipBalms = By.xpath("//div//a[text()=' Lip Balm ']");
        public static final By makeupSubcategoryLipGloss = By.xpath("//div//a[text()=' Lip Gloss ']");
        public static final By makeupSubcategoryLipLiner = By.xpath("//div//a[text()=' Lip Liner ']");
        public static final By makeupSubcategoryLipstick = By.xpath("//div//a[text()=' Lipstick ']");
        public static final By makeupSubcategoryLiquidLipstick = By.xpath("//div//a[text()=' Liquid Lipstick ']");

        // Makeup hover Subcategories Tools & Brushes
        public static final By makeupSubcategoryToolsBrushes = By.xpath("//div//a[text()='Tools & Brushes']");
        public static final By makeupSubcategoryBrushSets = By.xpath("//div//a[text()=' Brush Sets ']");
        public static final By makeupSubcategoryEyeBrushes = By.xpath("//div//a[text()=' Eye Brushes & Eyelash Curlers ']");
        public static final By makeupSubcategoryFaceBrushes = By.xpath("//div//a[text()=' Face Brush ']");
        public static final By makeupSubcategoryLipBrushes = By.xpath("//div//a[text()=' Lip Brush ']");
        public static final By makeupSubcategoryMakeupMakeupPouch = By.xpath("//div//a[text()=' Makeup Pouch ']");
        public static final By makeupSubcategoryMakeupSharpeners = By.xpath("//div//a[text()=' Sharpeners & Tweezers ']");
        public static final By makeupSubcategoryMakeupSponges = By.xpath("//div//a[text()=' Sponges & Blenders ']");
        public static final By makeupSubcategoryMirrors = By.xpath("//div//a[text()=' Mirror ']");

        // Makeup hover Subcategories Kits & Palettes
        public static final By makeupSubcategoryKitsPalettes = By.xpath("//div//a[text()='Kits & Palettes']");
        public static final By makeupSubcategoryEyePalettes = By.xpath("//div//a[text()=' Eyeshadow Palettes ']");
        public static final By makeupSubcategoryFacePalettes = By.xpath("//div//a[text()=' Face Makeup Palettes ']");
        public static final By makeupSubcategoryMakeupKits = By.xpath("//div//a[text()=' Makeup Kits & Sets ']");

        // Skin hover Subcategories Face Care
        public static final By skinSubcategorySkincare = By.xpath("//div//a[text()='Cleansers & Exfoliators']");
        public static final By skinSubcategoryFaceWash = By.xpath("//div//a[text()=' Face Washes & Cleansers ']");
        public static final By skinSubcategoryToner = By.xpath("//div//a[text()=' Scrubs & Exfoliators ']");
        public static final By skinSubcategorySerum = By.xpath("//div//a[text()=' Face Wipes ']");
        public static final By skinSubcategoryMoisturizer = By.xpath("//div//a[text()=' Makeup Remover ']");

        // Skin hover Subcategories Eye Care
        public static final By skinSubcategoryEyeCare = By.xpath("//div//a[text()='Eye Care']");
        public static final By skinSubcategoryEyeCream = By.xpath("//div//a[text()=' Eye Creams & Serums ']");
        public static final By skinSubcategoryEyeSerum = By.xpath("//div//a[text()=' Eye Mask ']");
        public static final By skinSubcategoryEyeMask = By.xpath("//div//a[text()=' Eye Makeup Remover ']");

        // Skin hover Subcategories Eye Care
        public static final By skinSubcategorySetsAndBundles = By.xpath("//div//a[text()='Sets & Bundles']");
        public static final By skinSubcategoryFacialSets = By.xpath("//div//a[text()=' Facial Sets ']");
        public static final By skinSubcategoryGiftSets = By.xpath("//div//a[text()=' Gift Sets ']");

        // Skin hover Subcategories Lip Care
        public static final By skinSubcategoryLipCare = By.xpath("//div//a[text()='Lip Care']");
        public static final By skinSubcategoryLipBalm = By.xpath("//div//a[text()=' Lip Balm ']");
        public static final By skinSubcategoryLipScrub = By.xpath("//div//a[text()=' Lip Scrub ']");
        public static final By skinSubcategoryLipMask = By.xpath("//div//a[text()=' Lip Mask ']");
        public static final By skinSubcategoryLipOils = By.xpath("//div//a[text()=' Lip Oils ']");

        // Skin hover Subcategories Mask
        public static final By skinSubcategoryBodyCare = By.xpath("//div//a[text()='Mask']");
        public static final By skinSubcategoryBodyLotion = By.xpath("//div//a[text()=' Masks & Peels ']");
        public static final By skinSubcategoryBodyWash = By.xpath("//div//a[text()=' Sheet Mask ']");

        // Skin hover Subcategories Moisturizer
        public static final By skinSubcategoryMoisturizers = By.xpath("//div//a[text()='Moisturizer']");
        public static final By skinSubcategoryFaceMoisturizer = By.xpath("//div//a[text()=' Face Moisturizer ']");
        public static final By skinSubcategoryNightCream = By.xpath("//div//a[text()=' Night Cream ']");
        public static final By skinSubcategoryFaceOil = By.xpath("//div//a[text()=' Face Oil ']");


        // Skin hover Subcategories Toners & Mist
        public static final By skinSubcategoryTonersAndMist = By.xpath("//div//a[text()='Toners & Mist']");
        public static final By skinSubcategoryToners = By.xpath("//div//a[text()=' Toner ']");
        public static final By skinSubcategoryMist = By.xpath("//div//a[text()=' Mist ']");

        // Skin hover Subcategories Sun Care
        public static final By skinSubcategorySunCare = By.xpath("//div//a[text()='Sun Care']");
        public static final By skinSubcategorySunscreen = By.xpath("//div//a[text()=' Sunscreen ']");


        // Hair hover Subcategories Hair Care
        public static final By hairSubcategoryHairCare = By.xpath("//div//a[text()='Hair Care']");
        public static final By hairSubcategoryConditioner = By.xpath("//div//a[text()='Conditioner']");
        public static final By hairSubcategoryDryShampoo = By.xpath("//div//a[text()='Dry Shampoo']");
        public static final By hairSubcategoryHairOil = By.xpath("//div//a[text()='Hair Oil']");
        public static final By hairSubcategoryHairSerum = By.xpath("//div//a[text()='Hair Serum']");
        public static final By hairSubcategoryShampoo = By.xpath("//div//a[text()='Shampoo']");
        public static final By hairSubcategoryHairCreamsLeaveIns = By.xpath("//div//a[text()='Hair Creams & Leave-Ins']");
        public static final By hairSubcategoryHairMask = By.xpath("//div//a[text()='Hair Mask']");
        public static final By hairSubcategoryHairScalpTreatments = By.xpath("//div//a[text()='Hair & Scalp Treatments']");

        // Hair hover Subcategories Hair Styling
        public static final By hairSubcategoryHairStyling = By.xpath("//div//a[text()='Hair Styling']");
        public static final By hairSubcategoryHairGelsWaxes = By.xpath("//div//a[text()='Hair Gels & Waxes']");
        public static final By hairSubcategoryHairSpraysMists = By.xpath("//div//a[text()='Hair Sprays & Mists']");
        public static final By hairSubcategoryHairColour = By.xpath("//div//a[text()='Hair Colour']");

        // Hair hover Subcategories Tools & Accessories
        public static final By hairSubcategoryToolsAccessories = By.xpath("//div//a[text()='Tools & Accessories']");
        public static final By hairSubcategoryHairBrush = By.xpath("//div//a[text()='Hair Brush']");
        public static final By hairSubcategoryHairAccessories = By.xpath("//div//a[text()='Hair Accessories']");
        public static final By hairSubcategoryHairComb = By.xpath("//div//a[text()='Hair Comb']");
        public static final By hairSubcategoryRollersCurlers = By.xpath("//div//a[text()='Rollers & Curlers']");
        public static final By hairSubcategoryHairDryersStylers = By.xpath("//div//a[text()='Hair Dryers & Stylers']");
        public static final By hairSubcategoryStraightener = By.xpath("//div//a[text()='Straightener']");

        // Hair hover Subcategories Shop By Hair Type
        public static final By hairSubcategoryShopByHairType = By.xpath("//div//a[text()='Shop By Hair Type']");
        public static final By hairSubcategoryStraight = By.xpath("//div//a[text()='Straight']");
        public static final By hairSubcategoryCurlyWavy = By.xpath("//div//a[text()='Curly & Wavy']");

        // Dynamic subcategory locator method
        public static By makeupSubcategoryByText(String subcategoryName) {
            return By.xpath("//a[text()='" + subcategoryName + "']");
        }

        public static By skinSubcategoryByText(String subcategoryName) {
            return By.xpath("//a[text()='" + subcategoryName + "']");
        }

        public static By hairSubcategoryByText(String subcategoryName) {
            return By.xpath("//a[text()='" + subcategoryName + "']");
        }
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
