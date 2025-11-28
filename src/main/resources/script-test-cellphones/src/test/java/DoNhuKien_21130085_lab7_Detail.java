package com.example.UntitledTestSuite;

import java.time.Duration;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class DoNhuKien_21130085_lab7_Detail {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "");
        driver = new ChromeDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
    }

    // ========================== PRODUCT DETAIL 0 ==========================
    @Test
    public void testProductDetail0() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Máy lạnh Mijia Pro Eco Star 1.0 HP Inverter 2025 (9K)']")).click();
        driver.get("https://cellphones.com.vn/may-lanh-xiaomi-mijia-pro-eco-asc-09wo-nic5-my-1hp-2025.html");
        String productName = (String) js.executeScript("return document.querySelector('.box-product-name h1').innerText.trim()");
        String productPrice = (String) js.executeScript("return document.querySelector('.sale-price').innerText.trim()");
        System.out.println("Sản phẩm wishlist: " + productName + " - " + productPrice);
    }

    // ========================== PRODUCT DETAIL 1 ==========================
    @Test
    public void testProductDetail1() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.linkText("Bao da iPad Gen 9 ESR Ascend Trifold with Clasp585.000đ650.000đGiảm 10%")).click();
        driver.get("https://cellphones.com.vn/but-apple-pencil-1-cu-tray-xuoc.html");
        driver.findElement(By.xpath("//img[@alt='Bút Apple Pencil 1 Cũ - Trầy xước - 1']")).click();
    }

    // ========================== PRODUCT DETAIL 2 ==========================
    @Test
    public void testProductDetail2() throws Exception {
        driver.get("https://cellphones.com.vn/but-apple-pencil-1-cu-tray-xuoc.html");
        driver.findElement(By.xpath("//div[@id='review']/div/div[2]/div[3]")).click();
        driver.findElement(By.xpath("//div[@id='review']/div/div[2]/div[4]/p")).click();
        driver.findElement(By.xpath("//div[@id='review']/div/div[2]/div[5]/p")).click();
    }

    // ========================== PRODUCT DETAIL 3 ==========================
    @Test
    public void testProductDetail3() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div[3]/div/div[2]/div[2]/div/div[2]/div/div[3]/a/h3")).click();
        driver.findElement(By.xpath("//button[@type='button']")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div[4]/div[4]/div[2]/div/form/textarea")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div[4]/div[4]/div[2]/div/form/div[8]/button")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div[4]/div[4]/div[2]/div/form/textarea")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div[4]/div[4]/div[2]/div/form/div[8]/button")).click();
    }

    // ========================== PRODUCT DETAIL 4 ==========================
    @Test
    public void testProductDetail4() throws Exception {
        driver.get("https://cellphones.com.vn/cu-sac-nhanh-samsung-25w-ep-t2510nbegww.html");
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div[3]/div/div[2]/div[2]/div/div[2]/div/div[4]/a/h3")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[10]/div/div/div/button[2]/strong")).click();
    }

    // ========================== PRODUCT DETAIL 5 ==========================
    @Test
    public void testProductDetail5() throws Exception {
        driver.get("https://cellphones.com.vn/");
        // file gốc chỉ có comment //
    }

    // ========================== PRODUCT DETAIL 6 ==========================
    @Test
    public void testProductDetail6() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div[5]/div/div[3]/div/button[2]/h2")).click();
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div[4]/div/div/div/div[2]/div/div/div[6]/div/div/a/h3")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Thông số'])[1]/following::*[name()='svg'][1]")).click();
        driver.findElement(By.linkText("Chọn")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/div[4]/div/div/div[2]/div[3]/img")).click();
        driver.findElement(By.xpath("//div[@id='select-product-to-compare']/div[2]/div[3]/a")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/div[4]/div/div/div[2]/div[4]/div/a")).click();
    }

    // ========================== PRODUCT DETAIL 7 ==========================
    @Test
    public void testProductDetail7() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div[5]/div/div[3]/div/button[2]/h2")).click();
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div[4]/div/div/div/div[2]/div/div/div[6]/div/div/a/h3")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Thông số'])[1]/following::*[name()='svg'][1]")).click();
        driver.findElement(By.linkText("Chọn")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/div[4]/div/div/div[2]/div[3]/img")).click();
        driver.findElement(By.xpath("//div[@id='select-product-to-compare']/div[2]/div[3]/a")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/div[4]/div/div/div[2]/div[4]/div/a")).click();
        driver.get("https://cellphones.com.vn/bao-da-ipad-gen-9-esr-ascend-trifold-with-clasp.html");
    }

    // ========================== PRODUCT DETAIL 8 ==========================
    @Test
    public void testProductDetail8() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div[5]/div/div[3]/div[2]/div[3]/div/div/div[2]/div/div/a/h3")).click();
        driver.get("https://cellphones.com.vn/iphone-16-pro-max.html");
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[4]/div[2]/ul/li[4]/a/div/strong")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[4]/div[2]/ul/li[2]/a/div/strong")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[4]/div[2]/ul/li/a/div/strong")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[3]/div[2]/a[2]/strong")).click();
        driver.get("https://cellphones.com.vn/iphone-16-pro-max-512gb.html");
    }

    // ========================== PRODUCT DETAIL 9 ==========================
    @Test
    public void testProductDetail9() throws Exception {
        driver.get("https://cellphones.com.vn/");
        // gốc không có lệnh hợp lệ
    }

    // ========================== PRODUCT DETAIL 10 ==========================
    @Test
    public void testProductDetail10() throws Exception {
        driver.get("https://cellphones.com.vn/");
        // file gốc bạn up không có bước gì thêm
    }

    // ========================= TEARDOWN ===========================
    @AfterClass(alwaysRun = true)
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
