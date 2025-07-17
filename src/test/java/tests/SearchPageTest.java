package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.SearchPage;

import java.util.Arrays;
import java.util.List;

@Listeners(TestListener.class)
public class SearchPageTest extends BaseTest {


    @Test(description = "Verify search returns valid results")
    public void testSearchWithResults() {
        SearchPage searchPage = new SearchPage(driver);

        searchPage.search("lipstick");
        Assert.assertTrue(searchPage.isResultPresent(), "❌ Expected search results but found none.");
    }

    @Test(description = "Verify no results message for invalid search")
    public void testSearchWithNoResults() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.search("invalid-search-123");
        Assert.assertTrue(searchPage.isNoResultMessageDisplayed(), "❌ No result message not displayed.");
    }
    @Test
    public void testSearchFunctionality() {
        List<String> keywords = Arrays.asList("lipstick", "moisturizer", "kajal", "foundation");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchMultipleKeywords(keywords);
    }

}