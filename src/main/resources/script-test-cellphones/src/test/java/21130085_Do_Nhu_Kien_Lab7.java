package com.example.Cellphones;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.Duration;

public class DoNhuKien_Lab7 {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "");
        driver = new ChromeDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testTC1() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        String productName = (String)js.executeScript(" return " + "document.querySelector('.box-product-name h1').innerText.trim()" + "");
        String productPrice = (String)js.executeScript(" return " + "document.querySelector('.sale-price').innerText.trim()" + "");
        System.out.println("Sản phẩm wishlist: " + productName);
        System.out.println("Giá sản phẩm wishlist: " + productPrice);
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        String wishlistProductName = (String)js.executeScript(" return " + "document.querySelector('.product-name').innerText.trim()" + "");
        String wishlistProductPrice = (String)js.executeScript(" return " + "document.querySelector('.woocommerce-Price-amount.amount').innerText.trim()" + "");
        System.out.println("Sản phẩm trong wishlist: " + wishlistProductName);
        System.out.println("Giá sản phẩm trong wishlist: " + wishlistProductPrice);
        assertEquals(productName, wishlistProductName);
        assertEquals(productPrice, wishlistProductPrice);
    }

    @Test
    public void testTC2() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@href, 'may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html')]/following::a[contains(@class,'remove_from_wishlist')]")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        boolean isProductRemoved = driver.findElements(By.xpath("//a[contains(@href, 'may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html')]")).isEmpty();
        assertTrue(isProductRemoved);
    }

    @Test
    public void testTC3() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@href, 'may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html')]")).click();
        String wishlistProductUrl = driver.getCurrentUrl();
        assertEquals("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html", wishlistProductUrl);
    }

    @Test
    public void testTC4() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@href, 'may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html')]/following::a[contains(@class,'add_to_cart')]")).click();
        driver.get("https://cellphones.com.vn/cart/");
        boolean isInCart = !driver.findElements(By.xpath("//a[contains(@href,'may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html')]")).isEmpty();
        assertTrue(isInCart);
    }

    @Test
    public void testTC5() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        String productName = (String)js.executeScript(" return " + "document.querySelector('.box-product-name h1').innerText.trim()" + "");
        String productPrice = (String)js.executeScript(" return " + "document.querySelector('.sale-price').innerText.trim()" + "");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        String wishlistTitle = (String)js.executeScript(" return " + "document.title" + "");
        assertTrue(wishlistTitle.contains("Wishlist"));
        String wishlistProductName = (String)js.executeScript(" return " + "document.querySelector('.product-name').innerText.trim()" + "");
        String wishlistProductPrice = (String)js.executeScript(" return " + "document.querySelector('.woocommerce-Price-amount.amount').innerText.trim()" + "");
        assertEquals(productName, wishlistProductName);
        assertEquals(productPrice, wishlistProductPrice);
    }

    @Test
    public void testTC6() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        int productCount = driver.findElements(By.cssSelector(".wishlist_table .product-name")).size();
        assertTrue(productCount >= 1);
    }

    @Test
    public void testTC7() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        boolean isWishlistPage = driver.getCurrentUrl().contains("wishlist");
        assertTrue(isWishlistPage);
    }

    @Test
    public void testTC8() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@class,'wishlist-empty')]")).click();
        int productCount = driver.findElements(By.cssSelector(".wishlist_table .product-name")).size();
        assertEquals(0, productCount);
    }

    @Test
    public void testTC9() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        String productName = (String)js.executeScript(" return " + "document.querySelector('.box-product-name h1').innerText.trim()" + "");
        String productPrice = (String)js.executeScript(" return " + "document.querySelector('.sale-price').innerText.trim()" + "");
        System.out.println("Sản phẩm wishlist: " + productName);
        System.out.println("Giá sản phẩm wishlist: " + productPrice);
        driver.get("https://cellphones.com.vn/wishlist/");
        String wishlistProductName = (String)js.executeScript(" return " + "document.querySelector('.product-name').innerText.trim()" + "");
        String wishlistProductPrice = (String)js.executeScript(" return " + "document.querySelector('.woocommerce-Price-amount.amount').innerText.trim()" + "");
        System.out.println("Sản phẩm trong wishlist: " + wishlistProductName);
        System.out.println("Giá sản phẩm trong wishlist: " + wishlistProductPrice);
        assertEquals(productName, wishlistProductName);
        assertEquals(productPrice, wishlistProductPrice);
    }

    @Test
    public void testTC10() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html");
        String productName = (String)js.executeScript(" return " + "document.querySelector('.box-product-name h1').innerText.trim()" + "");
        String productPrice = (String)js.executeScript(" return " + "document.querySelector('.sale-price').innerText.trim()" + "");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        String wishlistProductName = (String)js.executeScript(" return " + "document.querySelector('.product-name').innerText.trim()" + "");
        String wishlistProductPrice = (String)js.executeScript(" return " + "document.querySelector('.woocommerce-Price-amount.amount').innerText.trim()" + "");
        assertEquals(productName, wishlistProductName);
        assertEquals(productPrice, wishlistProductPrice);
    }

    @Test
    public void testTC11() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@href,'samsung-galaxy-a15-lte-8gb-256gb-cu.html')]/following::a[contains(@class,'remove_from_wishlist')]")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        boolean isProductRemoved = driver.findElements(By.xpath("//a[contains(@href,'samsung-galaxy-a15-lte-8gb-256gb-cu.html')]")).isEmpty();
        assertTrue(isProductRemoved);
    }

    @Test
    public void testTC12() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@href,'samsung-galaxy-a15-lte-8gb-256gb-cu.html')]")).click();
        String wishlistProductUrl = driver.getCurrentUrl();
        assertEquals("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html", wishlistProductUrl);
    }

    @Test
    public void testTC13() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@href,'samsung-galaxy-a15-lte-8gb-256gb-cu.html')]/following::a[contains(@class,'add_to_cart')]")).click();
        driver.get("https://cellphones.com.vn/cart/");
        boolean isInCart = !driver.findElements(By.xpath("//a[contains(@href,'samsung-galaxy-a15-lte-8gb-256gb-cu.html')]")).isEmpty();
        assertTrue(isInCart);
    }

    @Test
    public void testTC14() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        String wishlistTitle = (String)js.executeScript(" return " + "document.title" + "");
        assertTrue(wishlistTitle.contains("Wishlist"));
    }

    @Test
    public void testTC15() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        int productCount = driver.findElements(By.cssSelector(".wishlist_table .product-name")).size();
        assertTrue(productCount >= 1);
    }

    @Test
    public void testTC16() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        boolean isWishlistPage = driver.getCurrentUrl().contains("wishlist");
        assertTrue(isWishlistPage);
    }

    @Test
    public void testTC17() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-lte-8gb-256gb-cu.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@class,'wishlist-empty')]")).click();
        int productCount = driver.findElements(By.cssSelector(".wishlist_table .product-name")).size();
        assertEquals(0, productCount);
    }

    @Test
    public void testTC18() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        driver.findElement(By.xpath("//a[contains(@href,'may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html')]/following::a[contains(@class,'remove_from_wishlist')]")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        boolean isProductRemoved = driver.findElements(By.xpath("//a[contains(@href,'may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html')]")).isEmpty();
        assertTrue(isProductRemoved);
    }

    @Test
    public void testTC19() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.get("https://cellphones.com.vn/wishlist/");
        String wishlistProductName = (String)js.executeScript(" return " + "document.querySelector('.product-name').innerText.trim()" + "");
        assertNotNull(wishlistProductName);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
