package locators;

import org.openqa.selenium.By;

public class Locators {

    public static class HomePage {
        // Header Elements
        public static final By logoLink = By.xpath("//img[contains(@class, 'tira-logo') or contains(@alt, 'Tira') or contains(@title, 'Tira')] | //a[contains(@href, 'tira') or contains(@class, 'logo')] | //*[contains(@class, 'logo')]");
        public static final By searchInput = By.xpath("//input[@id='search' or @placeholder='Search' or contains(@class, 'search')]");
        public static final By profileIcon = By.xpath("//div[contains(@class, 'profile') and contains(@class, 'logout')] | //a[contains(@href, 'login')] | //img[@alt='Profile' or @title='Profile']");
        public static final By cartIcon = By.xpath("//img[@title='Cart' or @alt='Cart'] | //a[contains(@href, 'cart')]");

        // Navigation Menu Links - Updated to be more flexible and reliable
        public static final By tiraRedNavLink = By.xpath("//div[1]/a[text()=\" Tira Red \"]");
        public static final By offersNavLink = By.xpath("//div[1]/a[text()=\" Offers \"]");
        public static final By topShelfNavLink = By.xpath("//div[1]/div/div[1]/div[4]/div/a[text()=\" Top Shelf \"]");
        public static final By forYouNavLink = By.xpath("//div[1]/div/div[1]/div[5]/div/a[text()=\" For You \"]");
        public static final By whatsNewNavLink = By.xpath("//div/a[text()=\"What's New\"]");
        public static final By makeupNavLink = By.xpath("//div/a[text()='Makeup']");
        public static final By skinNavLink = By.xpath("//div/a[text()='Skin']");
        public static final By hairNavLink = By.xpath("//div/a[text()='Hair']");
        public static final By fragranceNavLink = By.xpath("//div/a[text()='Fragrance']");
        public static final By menNavLink = By.xpath("//div/a[text()='Men']");
        public static final By bathBodyNavLink = By.xpath("//div/a[text()='Bath & Body']");
        public static final By toolsAppliancesNavLink = By.xpath("//div/a[text()=\"Tools & Appliances\"]");
        public static final By momBabyNavLink = By.xpath("//div/a[text()=\"Mom & Baby\"]");
        public static final By wellnessNavLink = By.xpath("//div/a[text()=\"Wellness\"]");
        public static final By minisNavLink = By.xpath("//div/a[text()=\"Minis\"]");
        public static final By homegrownNavLink = By.xpath("//div/a[text()=\"Homegrown\"]");
        public static final By giftsNavLink = By.xpath("//div/a[text()=\"Gifts\"]");

        // Makeup hover Subcategories face
        public static final By makeupSubcategoryface = By.xpath("//div/a[text()='Face']");
        public static final By makeupSubcategoryBlush = By.xpath("//div/a[text()=' Blush ']");
        public static final By makeupSubcategoryBronzer = By.xpath("//div/a[text()=' Bronzer ']");
        public static final By makeupSubcategoryCOmpact = By.xpath("//div/a[text()=' Compact ']");
        public static final By makeupSubcategoryConcealers = By.xpath("//div/a[text()=' Concealers & Correctors ']");
        public static final By makeupSubcategoryContour= By.xpath("//div/a[text()=' Contour ']");
        public static final By makeupSubcategoryFoundation = By.xpath("//div/a[text()=' Foundation ']");
        public static final By makeupSubcategoryHighlighters = By.xpath("//div/a[text()=' Highlighters & Illuminators ']");
        public static final By makeupSubcategorySettingPowder = By.xpath("//div/a[text()=' Setting Powder ']");
        public static final By makeupSubcategoryMakeupRemover = By.xpath("//div/a[text()=' Makeup Remover ']");
        public static final By makeupSubcategoryPrimer = By.xpath("//div/a[text()=' Primer ']");
        public static final By makeupSubcategorySettingSpray = By.xpath("//div/a[text()=' Setting Spray ']");
        public static final By makeupSubcategoryBB = By.xpath("//div/a[text()=' BB & CC Creams ']");
        public static final By makeupSubcategoryLoosePowder = By.xpath("//div/a[text()=' Loose Powder ']");

        // Makeup hover Subcategories Eye
        public static final By makeupSubcategoryEye = By.xpath("//div/a[text()='Eye']");
        public static final By makeupSubcategoryEyeMakeupRemover = By.xpath("//div/a[text()=' Eye Makeup Remover ']");
        public static final By makeupSubcategoryEyebrowEnhancer = By.xpath("//div/a[text()=' Eyebrow Enhancer ']");
        public static final By makeupSubcategoryFalseEyelashes = By.xpath("//div/a[text()=' False Eyelashes ']");
        public static final By makeupSubcategoryEyeliner = By.xpath("//div/a[text()=' Eyeliner ']");
        public static final By makeupSubcategoryEyeShadow= By.xpath("//div/a[text()=' Eye Shadow ']");
        public static final By makeupSubcategoryKajal = By.xpath("//div/a[text()=' Kajal & Kohls ']");
        public static final By makeupSubcategoryMascara = By.xpath("//div/a[text()=' Mascara ']");
        public static final By makeupSubcategorySettingUnderEyeConcealer = By.xpath("//div/a[text()=' Under Eye Concealer ']");
        public static final By makeupSubcategoryEyeBases = By.xpath("//div/a[text()=' Eye Bases & Primers ']");

        // Makeup hover Subcategories Lip
        public static final By makeupSubcategoryLip = By.xpath("//div/a[text()='Lip']");
        public static final By makeupSubcategoryLipBalms = By.xpath("//div/a[text()=' Lip Balm ']");
        public static final By makeupSubcategoryLipGloss = By.xpath("//div/a[text()=' Lip Gloss ']");
        public static final By makeupSubcategoryLipLiner = By.xpath("//div/a[text()=' Lip Liner ']");
        public static final By makeupSubcategoryLipstick = By.xpath("//div/a[text()=' Lipstick ']");
        public static final By makeupSubcategoryLiquidLipstick = By.xpath("//div/a[text()=' Liquid Lipstick ']");

        // Makeup hover Subcategories Tools & Brushes
        public static final By makeupSubcategoryToolsBrushes = By.xpath("//div/a[text()='Tools & Brushes']");
        public static final By makeupSubcategoryBrushSets = By.xpath("//div/a[text()=' Brush Sets ']");
        public static final By makeupSubcategoryEyeBrushes = By.xpath("//div/a[text()=' Eye Brushes & Eyelash Curlers ']");
        public static final By makeupSubcategoryFaceBrushes = By.xpath("//div/a[text()=' Face Brush ']");
        public static final By makeupSubcategoryLipBrushes = By.xpath("//div/a[text()=' Lip Brush ']");
        public static final By makeupSubcategoryMakeupMakeupPouch = By.xpath("//div/a[text()=' Makeup Pouch ']");
        public static final By makeupSubcategoryMakeupSharpeners = By.xpath("//div/a[text()=' Sharpeners & Tweezers ']");
        public static final By makeupSubcategoryMakeupSponges = By.xpath("//div/a[text()=' Sponges & Blenders ']");
        public static final By makeupSubcategoryMirrors = By.xpath("//div/a[text()=' Mirror ']");

        // Makeup hover Subcategories Kits & Palettes
        public static final By makeupSubcategoryKitsPalettes = By.xpath("//div/a[text()='Kits & Palettes']");
        public static final By makeupSubcategoryEyePalettes = By.xpath("//div/a[text()=' Eyeshadow Palettes ']");
        public static final By makeupSubcategoryFacePalettes = By.xpath("//div/a[text()=' Face Makeup Palettes ']");
        public static final By makeupSubcategoryMakeupKits = By.xpath("//div/a[text()=' Makeup Kits & Sets ']");

        // Skin hover Subcategories Face Care
        public static final By skinSubcategorySkincare = By.xpath("//div/a[text()='Cleansers & Exfoliators']");
        public static final By skinSubcategoryFaceWash = By.xpath("//div/a[text()=' Face Washes & Cleansers ']");
        public static final By skinSubcategoryToner = By.xpath("//div/a[text()=' Scrubs & Exfoliators ']");
        public static final By skinSubcategorySerum = By.xpath("//div/a[text()=' Face Wipes ']");
        public static final By skinSubcategoryMoisturizer = By.xpath("//div/a[text()=' Makeup Remover ']");

        // Skin hover Subcategories Eye Care
        public static final By skinSubcategoryEyeCare = By.xpath("//div/a[text()='Eye Care']");
        public static final By skinSubcategoryEyeCream = By.xpath("//div/a[text()=' Eye Creams & Serums ']");
        public static final By skinSubcategoryEyeSerum = By.xpath("//div/a[text()=' Eye Mask ']");
        public static final By skinSubcategoryEyeMask = By.xpath("//div/a[text()=' Eye Makeup Remover ']");

        // Skin hover Subcategories Sets & Bundles
        public static final By skinSubcategorySetsAndBundles = By.xpath("//div/a[text()='Sets & Bundles']");
        public static final By skinSubcategoryFacialSets = By.xpath("//div/a[text()=' Facial Sets ']");
        public static final By skinSubcategoryGiftSets = By.xpath("//div/a[text()=' Gift Sets ']");

        // Skin hover Subcategories Lip Care
        public static final By skinSubcategoryLipCare = By.xpath("//div/a[text()='Lip Care']");
        public static final By skinSubcategoryLipBalm = By.xpath("//div/a[text()=' Lip Balm ']");
        public static final By skinSubcategoryLipScrub = By.xpath("//div/a[text()=' Lip Scrub ']");
        public static final By skinSubcategoryLipMask = By.xpath("//div/a[text()=' Lip Mask ']");
        public static final By skinSubcategoryLipOils = By.xpath("//div/a[text()=' Lip Oils ']");

        // Skin hover Subcategories Mask
        public static final By skinSubcategoryBodyCare = By.xpath("//div/a[text()='Mask']");
        public static final By skinSubcategoryBodyLotion = By.xpath("//div/a[text()=' Masks & Peels ']");
        public static final By skinSubcategoryBodyWash = By.xpath("//div/a[text()=' Sheet Mask ']");

        // Skin hover Subcategories Moisturizer
        public static final By skinSubcategoryMoisturizers = By.xpath("//div/a[text()='Moisturizer']");
        public static final By skinSubcategoryFaceMoisturizer = By.xpath("//div/a[text()=' Face Moisturizer ']");
        public static final By skinSubcategoryNightCream = By.xpath("//div/a[text()=' Night Cream ']");
        public static final By skinSubcategoryFaceOil = By.xpath("//div/a[text()=' Face Oil ']");


        // Skin hover Subcategories Toners & Mist
        public static final By skinSubcategoryTonersAndMist = By.xpath("//div/a[text()='Toners & Mist']");
        public static final By skinSubcategoryToners = By.xpath("//div/a[text()=' Toner ']");
        public static final By skinSubcategoryMist = By.xpath("//div/a[text()=' Mist ']");

        // Skin hover Subcategories Sun Care
        public static final By skinSubcategorySunCare = By.xpath("//div/a[text()='Sun Care']");
        public static final By skinSubcategorySunscreen = By.xpath("//div/a[text()=' Sunscreen ']");


        // Hair hover Subcategories Hair Care
        public static final By hairSubcategoryHairCare = By.xpath("//div/a[text()='Hair Care']");
        public static final By hairSubcategoryConditioner = By.xpath("//div/a[text()=' Conditioner ']");
        public static final By hairSubcategoryDryShampoo = By.xpath("//div/a[text()=' Dry Shampoo ']");
        public static final By hairSubcategoryHairOil = By.xpath("//div/a[text()=' Hair Oil ']");
        public static final By hairSubcategoryHairSerum = By.xpath("//div/a[text()=' Hair Serum ']");
        public static final By hairSubcategoryShampoo = By.xpath("//div/a[text()=' Shampoo ']");
        public static final By hairSubcategoryHairCreamsLeaveIns = By.xpath("//div/a[text()=' Hair Creams & Leave-ins ']");
        public static final By hairSubcategoryHairMask = By.xpath("//div/a[text()=' Hair Mask ']");
        public static final By hairSubcategoryHairScalpTreatments = By.xpath("//div/a[text()=' Hair & Scalp Treatments ']");

        // Hair hover Subcategories Hair Styling
        public static final By hairSubcategoryHairStyling = By.xpath("//div/a[text()='Hair Styling']");
        public static final By hairSubcategoryHairGelsWaxes = By.xpath("//div/a[text()=' Hair Gels & Waxes ']");
        public static final By hairSubcategoryHairSpraysMists = By.xpath("//div/a[text()=' Hair Sprays & Mists ']");
        public static final By hairSubcategoryHairColour = By.xpath("//div/a[text()=' Hair Colour ']");

        // Hair hover Subcategories Tools & Accessories
        public static final By hairSubcategoryToolsAccessories = By.xpath("//div/a[text()='Tools & Accessories']");
        public static final By hairSubcategoryHairBrush = By.xpath("//div/a[text()=' Hair Brush ']");
        public static final By hairSubcategoryHairAccessories = By.xpath("//div/a[text()=' Hair Accessories ']");
        public static final By hairSubcategoryHairComb = By.xpath("//div/a[text()=' Hair Comb ']");
        public static final By hairSubcategoryRollersCurlers = By.xpath("//div/a[text()=' Rollers & Curlers ']");
        public static final By hairSubcategoryHairDryersStylers = By.xpath("//div/a[text()=' Hair Dryers & Stylers ']");
        public static final By hairSubcategoryStraightener = By.xpath("//div/a[text()=' Straightener ']");

        // Hair hover Subcategories Shop By Hair Type
       // public static final By hairSubcategoryShopByHairType = By.xpath("//div[text()='Shop By Hair Type']");
        public static final By hairSubcategoryStraight = By.xpath("//div/a[text()=' Straight ']");
        public static final By hairSubcategoryCurlyWavy = By.xpath("//div/a[text()=' Curly & Wavy ']");

        // Hair hover Subcategories Shop By
        //public static final By hairSubcategoryShopBy = By.xpath("//div[text()='Shop By']");
        public static final By hairSubcategoryWhatsNew = By.xpath("//div/a[text()=\" What's New \"]");
        public static final By hairSubcategoryBestsellers = By.xpath("//div/a[text()=' Bestsellers ']");
        public static final By hairSubcategoryMinis = By.xpath("//div/a[text()=' Minis ']");
        public static final By hairSubcategorySetsBundles = By.xpath("//div/a[text()=' Sets & Bundles ']");
        public static final By hairSubcategoryTiraLoves = By.xpath("//div/a[text()=' Tira Loves ']");
        public static final By hairSubcategoryHomegrown = By.xpath("//div/a[text()=' Homegrown ']");
        public static final By hairSubcategoryBudgetBuys = By.xpath("//div/a[text()=' Budget Buys ']");

        // Hair hover Subcategories Brands To Know
        //public static final By hairSubcategoryBrandsToKnow = By.xpath("//div[text()='Brands To Know']");
        public static final By hairSubcategoryMilkShake = By.xpath("//div/a[text()=' Milk Shake ']");
        public static final By hairSubcategoryForestEssentials = By.xpath("//div/a[text()=' Forest Essentials ']");
        public static final By hairSubcategoryCOTRIL = By.xpath("//div/a[text()=' COTRIL ']");
        public static final By hairSubcategoryLOrealProfessionnel = By.xpath("//div/a[text()=\" L'OREAL PROFESSIONNEL \"]");

        // Hair hover Subcategories Shop By Concern
        public static final By hairSubcategoryShopByConcern = By.xpath("//div/a[text()='Shop By Concern']");
        public static final By hairSubcategoryHairfallThinning = By.xpath("//div/a[text()=' Hairfall & Hair Thinning ']");
        public static final By hairSubcategoryDandruffBuildUps = By.xpath("//div/a[text()=\" Dandruff Build-up \"]");
        public static final By hairSubcategoryDryFrizzyHair = By.xpath("//div/a[text()=' Dry & Frizzy Hair ']");
        public static final By hairSubcategorySplitEnds = By.xpath("//div/a[text()=' Split Ends ']");
        public static final By hairSubcategoryColourProtection = By.xpath("//div/a[text()=' Colour Protection ']");
        public static final By hairSubcategoryBreakageProneHair = By.xpath("//div/a[text()=' Breakage-Prone Hair ']");
        public static final By hairSubcategoryCurlCare = By.xpath("//div/a[text()=' Curl Care ']");
        public static final By hairSubcategoryVolume = By.xpath("//div/a[text()=' Volume ']");

        // Hair hover Subcategories Tira Red
        public static final By hairSubcategoryTiraRed = By.xpath("//div/a[text()='Tira Red']");
        public static final By hairSubcategoryOlaplex = By.xpath("//div/a[text()=' Olaplex ']");
        public static final By hairSubcategoryKevinMurphy = By.xpath("//div/a[text()=' Kevin Murphy ']");
        public static final By hairSubcategoryK18 = By.xpath("//div/a[text()=' K18 ']");
        public static final By hairSubcategoryRootDeep = By.xpath("//div/a[text()=' Root Deep ']");
        public static final By hairSubcategoryMoroccanoil = By.xpath("//div/a[text()=\" Morroccanoil \"]");

        // Hair hover Subcategories Explore
        public static final By hairSubcategoryExplore = By.xpath("//div/a[text()='Explore']");
        public static final By hairSubcategoryDermocosmetics = By.xpath("//div/a[text()=' Dermocosmetics ']");
        public static final By hairSubcategoryProfessionalHairCare = By.xpath("//div/a[text()=' Professional Hair Care ']");

        // Fragrance hover Subcategories - Women's Fragrance
        public static final By fragranceSubcategoryWomensPerfume = By.xpath("//div/a[text()=' Perfume (EDT & EDP) ']");
        public static final By fragranceSubcategoryWomensBodyMists = By.xpath("//div/a[text()=' Body Mists & Sprays ']");
        public static final By fragranceSubcategoryWomensDeodorants = By.xpath("//div/a[text()=' Deodorants & Roll-Ons ']");

        // Fragrance hover Subcategories - Men's Fragrance
        public static final By fragranceSubcategoryMensPerfume = By.xpath("//div/a[text()=' Perfume (EDT & EDP) ']");
        public static final By fragranceSubcategoryMensBodyMists = By.xpath("//div/a[text()=' Body Mists & Sprays ']");
        public static final By fragranceSubcategoryMensDeodorants = By.xpath("//div/a[text()=' Deodorants & Roll-Ons ']");
        public static final By fragranceSubcategoryMensColognes = By.xpath("//div/a[text()=\" Colognes & After Shaves \"]");

        // Fragrance hover Subcategories - Unisex Fragrance
        public static final By fragranceSubcategoryUnisexPerfumes = By.xpath("//div/a[text()=' Unisex Perfumes ']");
        public static final By fragranceSubcategoryUnisexMists = By.xpath("//div/a[text()=' Unisex Mists & Sprays ']");
        public static final By fragranceSubcategoryUnisexDeodorants = By.xpath("//div/a[text()=' Unisex Deodorants & Roll-Ons ']");

        // Fragrance hover Subcategories - Fragrance Family
        //public static final By fragranceSubcategoryFragranceFamily = By.xpath("//div/a[text()='Fragrance Family']");
        public static final By fragranceSubcategoryFloral = By.xpath("//div/a[text()=' Floral ']");
        public static final By fragranceSubcategoryFruity = By.xpath("//div/a[text()=' Fruity ']");
        public static final By fragranceSubcategorySpicy = By.xpath("//div/a[text()=' Spicy ']");
        public static final By fragranceSubcategoryWoody = By.xpath("//div/a[text()=' Woody ']");
        public static final By fragranceSubcategoryFresh = By.xpath("//div/a[text()=' Fresh ']");
        public static final By fragranceSubcategoryAqua = By.xpath("//div/a[text()=' Aqua ']");
        public static final By fragranceSubcategoryCitrus = By.xpath("//div/a[text()=' Citrus ']");
        public static final By fragranceSubcategoryMusky = By.xpath("//div/a[text()=' Musky ']");

        // Fragrance hover Subcategories - Home Fragrance
        public static final By fragranceSubcategoryCandle = By.xpath("//div/a[text()=' Candle ']");
        public static final By fragranceSubcategoryDiffuser = By.xpath("//div/a[text()=' Diffuser ']");

        // Fragrance hover Subcategories - Shop By
        public static final By fragranceSubcategoryWhatsNew = By.xpath("//div/a[text()=\" What's New \"]");
        public static final By fragranceSubcategoryBestsellers = By.xpath("//div/a[text()=' Bestsellers ']");
        public static final By fragranceSubcategoryGiftSets = By.xpath("//div/a[text()=' Gift Sets ']");
        public static final By fragranceSubcategorySetsBundles = By.xpath("//div/a[text()=' Sets & Bundles ']");
        public static final By fragranceSubcategoryTiraLoves = By.xpath("//div/a[text()=' Tira Loves ']");

        // Fragrance hover Subcategories - Tira Red
        public static final By fragranceSubcategoryYvesSaintLaurent = By.xpath("//div/a[text()=' Yves Saint Laurent ']");
        public static final By fragranceSubcategoryBurberry = By.xpath("//div/a[text()=' Burberry ']");
        public static final By fragranceSubcategoryTomFord = By.xpath("//div/a[text()=' Tom Ford ']");
        public static final By fragranceSubcategoryPrada = By.xpath("//div/a[text()=' Prada ']");
        public static final By fragranceSubcategoryVersace = By.xpath("//div/a[text()=' Versace ']");

        // Fragrance hover Subcategories - Brands To Know
        public static final By fragranceSubcategoryGucci = By.xpath("//div/a[text()=' Gucci ']");
        public static final By fragranceSubcategoryJoMaloneLondon = By.xpath("//div/a[text()=' Jo Malone London ']");
        public static final By fragranceSubcategoryElizabethArden = By.xpath("//div/a[text()=' Elizabeth Arden ']");
        public static final By fragranceSubcategoryJimmyChoo = By.xpath("//div/a[text()=' Jimmy Choo ']");
        public static final By fragranceSubcategoryGiorgioArmani = By.xpath("//div/a[text()=' Giorgio Armani ']");
        public static final By fragranceSubcategoryCalvinKlein = By.xpath("//div/a[text()=' Calvin Klein ']");
        public static final By fragranceSubcategoryNarcisoRodriguez = By.xpath("//div/a[text()=' Narciso Rodriguez ']");
        public static final By fragranceSubcategoryDolceGabbana = By.xpath("//div/a[text()=' Dolce&Gabbana ']");
        public static final By fragranceSubcategorySalvatoreFerragamo = By.xpath("//div/a[text()=' Salvatore Ferragamo ']");

        // Men hover Subcategories - Beard Care
        public static final By menSubcategoryBeardMoustacheOil = By.xpath("//div/a[text()=' Beard & Moustache Oil ']");
        public static final By menSubcategoryBeardWax = By.xpath("//div/a[text()=' Beard Wax & Softeners ']");
        public static final By menSubcategoryBeardComb = By.xpath("//div/a[text()=' Beard Comb ']");
        public static final By menSubcategoryBeardCream = By.xpath("//div/a[text()=' Beard Cream, Serum & Balm ']");
        public static final By menSubcategoryBeardWash = By.xpath("//div/a[text()=' Beard Wash & Shampoos ']");

        // Men hover Subcategories - Hair Care
        public static final By menSubcategoryShampoo = By.xpath("//div/a[text()=' Shampoo ']");
        public static final By menSubcategoryConditioner = By.xpath("//div/a[text()=' Conditioner ']");
        public static final By menSubcategoryHairOil = By.xpath("//div/a[text()=' Hair Oil ']");
        public static final By menSubcategoryHairStyling = By.xpath("//div/a[text()=' Hair Styling ']");
        public static final By menSubcategoryHairColour = By.xpath("//div/a[text()=' Hair Colour ']");

        // Men hover Subcategories - Fragrance
        public static final By menSubcategoryPerfume = By.xpath("//div/a[text()=' Perfume (EDT & EDP) ']");
        public static final By menSubcategoryDeodorants = By.xpath("//div/a[text()=' Deodorants & Roll-Ons ']");
        public static final By menSubcategoryBodyMists = By.xpath("//div/a[text()=' Body Mists & Sprays ']");
        public static final By menSubcategoryColognes = By.xpath("//div/a[text()=' Colognes & Aftershaves ']");

        // Men hover Subcategories - Shaving
        public static final By menSubcategoryRazors = By.xpath("//div/a[text()=' Razors & Cartridges ']");
        public static final By menSubcategoryShavers = By.xpath("//div/a[text()=' Shavers & Trimmers ']");
        public static final By menSubcategoryShavingCream = By.xpath("//div/a[text()=' Shaving Cream, Foam & Gel ']");
        public static final By menSubcategoryPrePostShaves = By.xpath("//div/a[text()=' Pre & Post Shaves ']");
        public static final By menSubcategoryShavingBrush = By.xpath("//div/a[text()=' Shaving Brush ']");
        public static final By menSubcategoryGroomingKits = By.xpath("//div/a[text()=' Grooming Kits ']");

        // Men hover Subcategories - Skincare
        public static final By menSubcategoryFaceWash = By.xpath("//div/a[text()=' Face Wash ']");
        public static final By menSubcategoryScrubs = By.xpath("//div/a[text()=' Scrubs & Exfoliators ']");
        public static final By menSubcategoryFaceMoisturizer = By.xpath("//div/a[text()=' Face Moisturizer ']");
        public static final By menSubcategorySunscreen = By.xpath("//div/a[text()=' Sunscreen ']");
        public static final By menSubcategoryMasks = By.xpath("//div/a[text()=' Masks & Peels ']");

        // Men hover Subcategories - Bath & Body
        public static final By menSubcategoryShowerGel = By.xpath("//div/a[text()=' Shower Gel ']");
        public static final By menSubcategorySoap = By.xpath("//div/a[text()=' Soap ']");
        public static final By menSubcategoryBodyScrub = By.xpath("//div/a[text()=' Body Scrub ']");
        public static final By menSubcategoryBodyLotion = By.xpath("//div/a[text()=' Body Lotion ']");
        public static final By menSubcategoryIntimateCare = By.xpath("//div/a[text()=' Intimate Care ']");
        public static final By menSubcategoryTalc = By.xpath("//div/a[text()=' Talc ']");
        public static final By menSubcategorySets = By.xpath("//div/a[text()=' Sets & Bundles ']");

        // Men hover Subcategories - Shop By
        public static final By menSubcategoryWhatsNew = By.xpath("//div/a[text()=' What's New ']");
        public static final By menSubcategoryBestsellers = By.xpath("//div/a[text()=' Bestsellers ']");
        public static final By menSubcategoryMinis = By.xpath("//div/a[text()=' Minis ']");
        public static final By menSubcategorySetsBundles = By.xpath("//div/a[text()=' Sets & Bundles ']");
        public static final By menSubcategoryBudgetBuys = By.xpath("//div/a[text()=' Budget Buys ']");

        // Men hover Subcategories - Tira Red
        public static final By menSubcategoryCaptainFawcett = By.xpath("//div/a[text()=' Captain Fawcett ']");
        public static final By menSubcategoryTruefittHill = By.xpath("//div/a[text()=' Truefitt & Hill ']");
        public static final By menSubcategoryVersace = By.xpath("//div/a[text()=' Versace ']");
        public static final By menSubcategoryBeardburys = By.xpath("//div/a[text()=' Beardburys ']");
        public static final By menSubcategoryCalvinKlein = By.xpath("//div/a[text()=' Calvin Klein ']");

        // Men hover Subcategories - Shop By Concern
        public static final By menSubcategoryFineLinesWrinkles = By.xpath("//div/a[text()=' Fine Lines & Wrinkles ']");
        public static final By menSubcategoryAcne = By.xpath("//div/a[text()=' Acne ']");
        public static final By menSubcategoryDullness = By.xpath("//div/a[text()=' Dullness ']");
        public static final By menSubcategoryPigmentationDarkSpots = By.xpath("//div/a[text()=' Pigmentation & Dark Spots ']");
        public static final By menSubcategoryPoresBlemishes = By.xpath("//div/a[text()=' Pores & Blemishes ']");
        public static final By menSubcategoryDryness = By.xpath("//div/a[text()=' Dryness ']");
        public static final By menSubcategoryDarkCircles = By.xpath("//div/a[text()=' Dark Circles ']");
        public static final By menSubcategoryPuffiness = By.xpath("//div/a[text()=' Puffiness ']");
        public static final By menSubcategoryHairThinningHairLoss = By.xpath("//div/a[text()=' Hair Thinning & Hair Loss ']");

        // Men hover Subcategories - Brands To Know
        public static final By menSubcategoryUrbanGabru = By.xpath("//div/a[text()=' Urban Gabru ']");
        public static final By menSubcategoryBombayShavingCompany = By.xpath("//div/a[text()=' Bombay Shaving Company ']");
        public static final By menSubcategoryTheManCompany = By.xpath("//div/a[text()=' The Man Company ']");
        public static final By menSubcategoryBeardo = By.xpath("//div/a[text()=' Beardo ']");
        public static final By menSubcategoryBigen = By.xpath("//div/a[text()=' Bigen ']");
        public static final By menSubcategoryLetsShave = By.xpath("//div/a[text()=' LetsShave ']");

        // Bath & Body hover Subcategories - Bath & Shower
        public static final By bathBodySubcategoryBathSalts = By.xpath("//div/a[text()=' Bath Salts ']");
        public static final By bathBodySubcategoryBodyScrubsExfoliants = By.xpath("//div/a[text()=' Body Scrubs & Exfoliants ']");
        public static final By bathBodySubcategoryBodyWashesShowerGels = By.xpath("//div/a[text()=' Body Washes & Shower Gels ']");
        public static final By bathBodySubcategorySoap = By.xpath("//div/a[text()=' Soap  ']");
        public static final By bathBodySubcategoryBathKitsSets = By.xpath("//div/a[text()=' Bath Kits & Sets ']");

        // Bath & Body hover Subcategories - Body Care
        public static final By bathBodySubcategoryBodyButter = By.xpath("//div/a[text()=' Body Butter ']");
        public static final By bathBodySubcategoryBodyLotionsMoisturizers = By.xpath("//div/a[text()=' Body Lotions & Moisturizers ']");
        public static final By bathBodySubcategoryMassageOil = By.xpath("//div/a[text()=' Massage Oil ']");
        public static final By bathBodySubcategoryTalc = By.xpath("//div/a[text()=' Talc ']");
        public static final By bathBodySubcategoryEssentialOil = By.xpath("//div/a[text()=' Essential Oil ']");

        // Bath & Body hover Subcategories - Hands & Feet
        public static final By bathBodySubcategoryHandWash = By.xpath("//div/a[text()=' Hand Wash ']");
        public static final By bathBodySubcategoryHandCreamsMasks = By.xpath("//div/a[text()=' Hand Creams & Masks ']");
        public static final By bathBodySubcategoryFootCare = By.xpath("//div/a[text()=' Foot Care ']");
        public static final By bathBodySubcategoryManiPediTools = By.xpath("//div/a[text()=' Mani-Pedi Tools & Kits ']");

        // Bath & Body hover Subcategories - Hygiene Essentials
        public static final By bathBodySubcategoryHandSanitizer = By.xpath("//div/a[text()=' Hand Sanitizer ']");
        public static final By bathBodySubcategoryIntimateCare = By.xpath("//div/a[text()=' Intimate Care ']");

        // Bath & Body hover Subcategories - Shaving & Hair Removal
        public static final By bathBodySubcategoryBodyRazorsCartridges = By.xpath("//div/a[text()=' Body Razors & Cartridges ']");
        public static final By bathBodySubcategoryFaceEyebrowRazors = By.xpath("//div/a[text()=' Face & Eyebrow Razors ']");
        public static final By bathBodySubcategoryEpilatorsTrimmers = By.xpath("//div/a[text()=' Epilators & Trimmers ']");
        public static final By bathBodySubcategoryWaxEssentials = By.xpath("//div/a[text()=' Wax Essentials ']");
        public static final By bathBodySubcategoryHairRemovalCreams = By.xpath("//div/a[text()=' Hair Removal Creams ']");

        // Bath & Body hover Subcategories - Brands To Know
        public static final By bathBodySubcategoryTheBodyShop = By.xpath("//div/a[text()=' The Body Shop ']");
        public static final By bathBodySubcategoryMcaffeine = By.xpath("//div/a[text()=' Mcaffeine ']");
        public static final By bathBodySubcategoryLoccitane = By.xpath("//div/a[text()=' Loccitane ']");
        public static final By bathBodySubcategoryKimirica = By.xpath("//div/a[text()=' Kimirica ']");
        public static final By bathBodySubcategoryYvesRocher = By.xpath("//div/a[text()=' Yves Rocher ']");
        public static final By bathBodySubcategoryMarksSpencers = By.xpath("//div/a[text()=' Marks & Spencers ']");
        public static final By bathBodySubcategoryMamaearth = By.xpath("//div/a[text()=' Mamaearth ']");
        public static final By bathBodySubcategorySoulflower = By.xpath("//div/a[text()=' Soulflower ']");
        public static final By bathBodySubcategoryFindYourHappyPlace = By.xpath("//div/a[text()=' Find your happy place ']");
        public static final By bathBodySubcategoryPlum = By.xpath("//div/a[text()=' Plum ']");
        public static final By bathBodySubcategoryDove = By.xpath("//div/a[text()=' Dove ']");
        public static final By bathBodySubcategoryNivea = By.xpath("//div/a[text()=' Nivea ']");
        public static final By bathBodySubcategoryVaseline = By.xpath("//div/a[text()=' Vaseline ']");

        // Bath & Body hover Subcategories - Derm Approved
        public static final By bathBodySubcategoryDermApproved = By.xpath("//div/a[text()='Derm Approved']");

        // Bath & Body hover Subcategories - Shop By
        public static final By bathBodySubcategoryWhatsNew = By.xpath("//div/a[text()=\" What's New \"]");
        public static final By bathBodySubcategoryBestsellers = By.xpath("//div/a[text()=' Bestsellers ']");
        public static final By bathBodySubcategoryMinis = By.xpath("//div/a[text()=' Minis ']");
        public static final By bathBodySubcategorySetsAndBundles = By.xpath("//div/a[text()=' Sets & Bundles ']");

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

        // Page loading verification elements
        public static final By pageLoadingIndicator = By.xpath("//*[contains(@class, 'loading') or contains(@class, 'spinner') or contains(@class, 'loader')]");
        public static final By bodyElement = By.tagName("body");
        public static final By anyVisibleElement = By.xpath("//*[normalize-space(text())!='' and not(contains(@style,'display:none')) and not(contains(@style,'visibility:hidden'))]");

        // Empty category page detection locators
        public static final By nothingToFindHereText = By.xpath("//*[contains(text(), 'Nothing to find here')]");
        public static final By noResultsFoundText = By.xpath("//*[contains(text(), 'No results found for your search')]");
        public static final By shopNowButton = By.xpath("//button[text()='Shop Now' or contains(@class, 'shop-now') or contains(text(), 'Shop Now')]");
        public static final By emptySearchResultsContainer = By.xpath("//*[contains(@class, 'empty-results') or contains(@class, 'no-results') or contains(@class, 'empty-category')]");
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
        public static final By toastNotification=By.cssSelector(".toasted.toasted-primary.success");

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
        public static final By productAddedFromCart= By.xpath("//div[contains(text(),'//div[1]/a/div/div/div[2]/div/div/button[text()=' Add to Bag ']')]");
        public static final By emptyCartPage= By.xpath("//a/button[text()=' Shop Now ']");

        // May need better locator
    }
    public static class CategoryPage {
        public static final By menuMakeup = By.xpath("//div/a[text()=\"Makeup\"]"); // May need better locator
        public static final By subCategoryNail = By.xpath("//div/a[text()=\"Nail\"]");
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
        public static final By validateCartPageWithLogin = By.xpath("//div[contains(text(),\"Coupons & Bank Offers\")]");
        public static final By validateCartPageWithoutLogin = By.xpath("//div[text()=\" Login to Apply Coupons & Bank Offers \"]");
        public static final By IncreaseQty = By.xpath("//button[2][@class=\"operator\"]");
        public static final By DicreaseQty = By.xpath("//button[1][@class=\"operator\"]");
        public static final By QtyDisplay = By.xpath("//div[@class='qty-amount']");
        public static final By RemoveFromCart = By.xpath("//div/button[1][text()=\"Remove\"]");
        public static final By MoveToWishlist = By.xpath("//div/button[2][text()=\" Move to Wishlist \"]");
        public static final By RemoveMoveSidebar = By.xpath("//div[contains(@class,'sidebar') or contains(@class,'modal')] | //div[contains(text(),'Remove') or contains(text(),'Move to Wishlist')]");
        public static final By RemoveToastMessage = By.xpath("//div[contains(text(),'removed') or contains(text(),'deleted')] | //div[contains(@class,'toast')] | //div[contains(@class,'notification')]");
        public static final By YouMayAlsoLIke = By.xpath("//div/h1[text()=' You May Also Like ']");
        public static final By AddToBagFromCart = By.xpath("//div[1]/a/div/div/div[2]//div/button[1][text()=' Add to Bag ']");
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
