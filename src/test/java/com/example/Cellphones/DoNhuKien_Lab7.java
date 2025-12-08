package com.example.Cellphones;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DoNhuKien_Lab7 {

    private WebDriver driver;
    JavascriptExecutor js;

    // LINK SẢN PHẨM DÙNG CHUNG
    private static final String PRODUCT_URL = "https://cellphones.com.vn/iphone-16-pro-max.html";
    private static final String PRODUCT_SLUG = "iphone-16-pro-max.html";

    @Before
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;

        // LOGIN BẮT BUỘC TRƯỚC KHI TEST
        loginSmember();
    }

    // ================== HÀM LOGIN ==================
    private void loginSmember() throws InterruptedException {


    }

    // ================== TEST CASE ==================

    @Test
    public void testTC1_AddToWishlist_ByExactHtmlState() throws Exception {

        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        String productName = (String) js.executeScript(
                "return document.querySelector('.box-product-name h1').innerText.trim()"
        );
        System.out.println("Tên sản phẩm: " + productName);

        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));

        WebElement stateDiv = wishlistBtn.findElement(
                By.cssSelector("div.btn__effect.button__add-wishlist")
        );

        String classBefore = stateDiv.getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        assertTrue(classBefore.contains("inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfter = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click: " + classAfter);

        assertTrue(classAfter.contains("active"));
        assertFalse(classAfter.contains("inactive"));
    }



    @Test
    public void testTC2_ToggleWishlist_AddThenRemove_ByExactHtmlState() throws Exception {

        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));
        WebElement stateDiv = wishlistBtn.findElement(
                By.cssSelector("div.btn__effect.button__add-wishlist")
        );

        String classBefore = stateDiv.getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        assertTrue(isHasClass(classBefore, "inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfterAdd = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click (ADD): " + classAfterAdd);

        assertTrue(isHasClass(classAfterAdd, "active"));
        assertFalse(isHasClass(classAfterAdd, "inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfterRemove = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click tiếp (REMOVE): " + classAfterRemove);

        assertTrue(isHasClass(classAfterRemove, "inactive"));
        assertFalse(isHasClass(classAfterRemove, "active"));
    }



    @Test
    public void testTC3_AddWishlist_FromMobileCategory() throws Exception {

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@href='/mobile.html']")).click();
        Thread.sleep(6000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement wishlistBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        ));

        WebElement productName = wishlistBtn.findElement(
                By.xpath("./ancestor::div[contains(@class,'product-item')]//div[contains(@class,'product__name')]//h3")
        );

        WebElement productPrice = wishlistBtn.findElement(
                By.xpath("./ancestor::div[contains(@class,'product-item')]//p[contains(@class,'product__price--show')]")
        );

        String name = productName.getText().trim();
        String price = productPrice.getText().trim();

        System.out.println("Tên sản phẩm: " + name);
        System.out.println("Giá sản phẩm: " + price);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", wishlistBtn);
        Thread.sleep(2000);

        String classBefore = wishlistBtn.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        js.executeScript("arguments[0].click();", wishlistBtn);
        Thread.sleep(3000);

        WebElement wishlistBtnAfter = driver.findElement(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        );

        String classAfter = wishlistBtnAfter.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class sau khi click: " + classAfter);

        assertTrue(classAfter.contains("active"));
    }




    @Test
    public void testTC4_RemoveWishlist_FromMobileCategory() throws Exception {

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@href='/mobile.html']")).click();
        Thread.sleep(6000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement btnAdd = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        ));

        WebElement productName = btnAdd.findElement(
                By.xpath("./ancestor::div[contains(@class,'product-item')]//div[contains(@class,'product__name')]//h3")
        );

        WebElement productPrice = btnAdd.findElement(
                By.xpath("./ancestor::div[contains(@class,'product-item')]//p[contains(@class,'product__price--show')]")
        );

        String name = productName.getText().trim();
        String price = productPrice.getText().trim();

        System.out.println("Tên sản phẩm: " + name);
        System.out.println("Giá sản phẩm: " + price);

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnAdd);
        Thread.sleep(2000);

        String classBefore = btnAdd.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        js.executeScript("arguments[0].click();", btnAdd);
        Thread.sleep(3000);

        WebElement btnAfterAdd = driver.findElement(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        );

        String classAfterAdd = btnAfterAdd.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class sau khi click (ADD): " + classAfterAdd);
        assertTrue(classAfterAdd.contains("active"));

        js.executeScript("arguments[0].click();", btnAfterAdd);
        Thread.sleep(3000);

        WebElement btnAfterRemove = driver.findElement(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        );

        String classAfterRemove = btnAfterRemove.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class sau khi click (REMOVE): " + classAfterRemove);

        assertTrue(classAfterRemove.contains("inactive"));
    }


//
//    @Test
//    public void testTC5() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        String productName = (String) js.executeScript(
//                "return document.querySelector('.box-product-name h1').innerText.trim()");
//        String productPrice = (String) js.executeScript(
//                "return document.querySelector('.sale-price').innerText.trim()");
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        String wishlistTitle = (String) js.executeScript("return document.title");
//        assertTrue(wishlistTitle.contains("Wishlist"));
//
//        String wishlistProductName = (String) js.executeScript(
//                "return document.querySelector('.product-name').innerText.trim()");
//        String wishlistProductPrice = (String) js.executeScript(
//                "return document.querySelector('.woocommerce-Price-amount.amount').innerText.trim()");
//
//        assertEquals(productName, wishlistProductName);
//        assertEquals(productPrice, wishlistProductPrice);
//    }
//
//    @Test
//    public void testTC6() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        int productCount = driver.findElements(
//                By.cssSelector(".wishlist_table .product-name")).size();
//        assertTrue(productCount >= 1);
//    }
//
//    @Test
//    public void testTC7() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        boolean isWishlistPage = driver.getCurrentUrl().contains("wishlist");
//        assertTrue(isWishlistPage);
//    }
//
//    @Test
//    public void testTC8() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        driver.findElement(By.xpath("//a[contains(@class,'wishlist-empty')]")).click();
//        int productCount = driver.findElements(
//                By.cssSelector(".wishlist_table .product-name")).size();
//        assertEquals(0, productCount);
//    }
//
//    @Test
//    public void testTC9() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//
//        String productName = (String) js.executeScript(
//                "return document.querySelector('.box-product-name h1').innerText.trim()");
//        String productPrice = (String) js.executeScript(
//                "return document.querySelector('.sale-price').innerText.trim()");
//        System.out.println("Sản phẩm wishlist: " + productName);
//        System.out.println("Giá sản phẩm wishlist: " + productPrice);
//
//        driver.get("https://cellphones.com.vn/wishlist/");
//        String wishlistProductName = (String) js.executeScript(
//                "return document.querySelector('.product-name').innerText.trim()");
//        String wishlistProductPrice = (String) js.executeScript(
//                "return document.querySelector('.woocommerce-Price-amount.amount').innerText.trim()");
//
//        System.out.println("Sản phẩm trong wishlist: " + wishlistProductName);
//        System.out.println("Giá sản phẩm trong wishlist: " + wishlistProductPrice);
//
//        assertEquals(productName, wishlistProductName);
//        assertEquals(productPrice, wishlistProductPrice);
//    }
//
//    @Test
//    public void testTC10() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        String productName = (String) js.executeScript(
//                "return document.querySelector('.box-product-name h1').innerText.trim()");
//        String productPrice = (String) js.executeScript(
//                "return document.querySelector('.sale-price').innerText.trim()");
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        String wishlistProductName = (String) js.executeScript(
//                "return document.querySelector('.product-name').innerText.trim()");
//        String wishlistProductPrice = (String) js.executeScript(
//                "return document.querySelector('.woocommerce-Price-amount.amount').innerText.trim()");
//
//        assertEquals(productName, wishlistProductName);
//        assertEquals(productPrice, wishlistProductPrice);
//    }
//
//    @Test
//    public void testTC11() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        driver.findElement(By.xpath("//a[contains(@href,'" + IPHONE16_SLUG + "')]/following::a[contains(@class,'remove_from_wishlist')]")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        boolean isProductRemoved = driver.findElements(
//                By.xpath("//a[contains(@href,'" + IPHONE16_SLUG + "')]")).isEmpty();
//        assertTrue(isProductRemoved);
//    }
//
//    @Test
//    public void testTC12() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        driver.findElement(By.xpath("//a[contains(@href,'" + IPHONE16_SLUG + "')]")).click();
//        String wishlistProductUrl = driver.getCurrentUrl();
//        assertEquals(IPHONE16_URL, wishlistProductUrl);
//    }
//
//    @Test
//    public void testTC13() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        driver.findElement(By.xpath("//a[contains(@href,'" + IPHONE16_SLUG + "')]/following::a[contains(@class,'add_to_cart')]")).click();
//        driver.get("https://cellphones.com.vn/cart/");
//
//        boolean isInCart = !driver.findElements(
//                By.xpath("//a[contains(@href,'" + IPHONE16_SLUG + "')]")).isEmpty();
//        assertTrue(isInCart);
//    }
//
//    @Test
//    public void testTC14() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        String wishlistTitle = (String) js.executeScript("return document.title");
//        assertTrue(wishlistTitle.contains("Wishlist"));
//    }
//
//    @Test
//    public void testTC15() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        int productCount = driver.findElements(
//                By.cssSelector(".wishlist_table .product-name")).size();
//        assertTrue(productCount >= 1);
//    }
//
//    @Test
//    public void testTC16() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        boolean isWishlistPage = driver.getCurrentUrl().contains("wishlist");
//        assertTrue(isWishlistPage);
//    }
//
//    @Test
//    public void testTC17() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        driver.findElement(By.xpath("//a[contains(@class,'wishlist-empty')]")).click();
//        int productCount = driver.findElements(
//                By.cssSelector(".wishlist_table .product-name")).size();
//        assertEquals(0, productCount);
//    }
//
//    @Test
//    public void testTC18() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        driver.findElement(By.xpath("//a[contains(@href,'" + IPHONE16_SLUG + "')]/following::a[contains(@class,'remove_from_wishlist')]")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        boolean isProductRemoved = driver.findElements(
//                By.xpath("//a[contains(@href,'" + IPHONE16_SLUG + "')]")).isEmpty();
//        assertTrue(isProductRemoved);
//    }
//
//    @Test
//    public void testTC19() throws Exception {
//        openIphone16FromHome();
//        driver.get(IPHONE16_URL);
//
//        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
//        driver.get("https://cellphones.com.vn/wishlist/");
//
//        String wishlistProductName = (String) js.executeScript(
//                "return document.querySelector('.product-name').innerText.trim()");
//        assertNotNull(wishlistProductName);
//    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
    private boolean isHasClass(String classText, String className) {
        for (String c : classText.split("\\s+")) {
            if (c.equals(className)) return true;
        }
        return false;
    }

}