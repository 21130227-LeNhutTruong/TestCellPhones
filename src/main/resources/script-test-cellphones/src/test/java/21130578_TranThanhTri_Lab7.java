package com.example.untitledTestSuite;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.time.Duration;

public class TranThanhTri_lab7 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver",
                "E:\\Download\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }


    private void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href='/cart']"))).click();
    }

    private void addFirstProductToCart() {
        driver.get("https://cellphones.com.vn/");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".product-item a"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn-add-to-cart"))).click();

        // chuyển sang giỏ hàng
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href='/cart']"))).click();
    }

    @Test
    public void testTC01CartHaveProduct() throws Exception {
        addFirstProductToCart();
        assertTrue(driver.getCurrentUrl().contains("cart"));
    }

    @Test
    public void testTC02CartEmptyCart() throws Exception {
        driver.get("https://cellphones.com.vn/cart");
        assertTrue(driver.getPageSource().contains("Giỏ hàng trống"));
    }

    @Test
    public void testTC03CartIncreaseQuantity() throws Exception {
        addFirstProductToCart();

        WebElement plusBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.qty-increase")));
        plusBtn.click();
        plusBtn.click();
    }

    @Test
    public void testTC04CartDecreaseQuantity() throws Exception {
        addFirstProductToCart();

        WebElement minusBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.qty-decrease")));
        minusBtn.click();
    }

    @Test
    public void testTC05CartRemoveProduct() throws Exception {
        addFirstProductToCart();

        WebElement remove = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".remove-cart-item")));
        remove.click();
    }


    @Test
    public void testTC07CartApplyValidCoupon() throws Exception {
        addFirstProductToCart();

        WebElement couponInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='coupon']")));
        couponInput.sendKeys("CPN10");

        WebElement applyBtn = driver.findElement(
                By.cssSelector("button.apply-coupon"));
        applyBtn.click();
    }

    @Test
    public void testTC08CartApplyInvalidCoupon() throws Exception {
        addFirstProductToCart();

        WebElement couponInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='coupon']")));
        couponInput.sendKeys("ABCXYZ");

        WebElement applyBtn = driver.findElement(
                By.cssSelector("button.apply-coupon"));
        applyBtn.click();

        assertTrue(driver.getPageSource().contains("không hợp lệ"));
    }


    @Test
    public void testTC09CartSelectShipmentMethod() throws Exception {
        addFirstProductToCart();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.checkout"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[name='shippingMethod']"))).click();
    }


    @Test
    public void testTC01PromotionCheck() throws Exception {
        driver.get("https://cellphones.com.vn/");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href='/khuyen-mai']"))).click();
    }

    @Test
    public void testTC02DealCheck() throws Exception {
        driver.get("https://cellphones.com.vn/danh-sach-khuyen-mai");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("img[alt='boxPromotion']"))).click();
    }

    @Test
    public void testTC03TabCheck() throws Exception {
        driver.get("https://cellphones.com.vn/danh-sach-khuyen-mai");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".promotion-tab:nth-child(2)"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".promotion-tab:nth-child(3)"))).click();
    }

    @Test
    public void testTC05PriceProductPromotion() throws Exception {
        driver.get("https://cellphones.com.vn/");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[contains(@alt,'StarGO')]"))).click();
    }

    @Test
    public void testTC06CategoryProductPromotion() throws Exception {
        driver.get("https://cellphones.com.vn/");

        wait.until(ExpectedConditions.elementToBeClickable(
                        By.linkText("Điện thoại")))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(
                        By.linkText("Khuyến mãi HOT")))
                .click();
    }

}
