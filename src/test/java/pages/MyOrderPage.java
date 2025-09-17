package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static locators.Locators.MyOrderPage.*;
import static locators.Locators.PlpPage.validatePlpPage;

public class MyOrderPage {

    WebDriver driver;
    WebDriverWait wait;


    public MyOrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void shipmentClick()
    {
        WebElement order_id = driver.findElement(getOrderId);
        String getOrder_id = order_id.getText();
        System.out.println("OrderId is : " +getOrder_id);

        WebElement clickonshipment = wait.until(ExpectedConditions.visibilityOfElementLocated(shipmentClick));
        clickonshipment.click();
    }
    public void cancelButtonClick() {
        WebElement clickCancelButton = wait.until(ExpectedConditions.visibilityOfElementLocated(cancelButtonClick));
        clickCancelButton.click();
        System.out.println("clicked on cancel button");
        //click on cancellation reason
        WebElement clickCancelReason = wait.until(ExpectedConditions.visibilityOfElementLocated(cancelReason));
        clickCancelReason.click();
        System.out.println("clicked on cancel reason");
        //select cancellation reason
        WebElement selectCancellationReason = wait.until(ExpectedConditions.visibilityOfElementLocated(selectCancelReason));
        selectCancellationReason.click();
        System.out.println("clicked on cancel reason");
        //Click on cancel button after select the reason

        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(cancelShipment));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        wait.until(ExpectedConditions.elementToBeClickable(button)); // wait for any animation or overlay

        button.click();
        System.out.println("✅ Clicked on 'cancel' button after scroll.");

        //con
        // Wait for popup to be visible
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationPopUp));
        System.out.println("✅ Confirmation popup appeared.");

        WebElement yesButton = driver.findElement(clickYesButton);
        yesButton.click();
        System.out.println("✅ Clicked Yes to confirm cancellation.");

    }
    public void validateMyOrderPage(){

        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateOrderPage));
        String expectedText = "My Account";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }
    public void validateShipmentDetailPage(){

        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validateShipmentDetailsPage));
        String expectedText = "Shipment Details";
        String actualText = messageElement.getText();
        Assert.assertTrue(actualText.contains(expectedText), "❌ Expected text not found.");
        System.out.println("✅ Verified message: " + actualText);
    }
}
