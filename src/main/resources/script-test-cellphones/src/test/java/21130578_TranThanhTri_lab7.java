package com.example.UntitledTestSuite;

import java.time.Duration;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TranThanhTri_21130578_lab7 {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://cellphones.com.vn/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testTranThanhTri_21130578_lab7() throws Exception {
        driver.get(baseUrl);
        try {
            WebElement closePopup = driver.findElement(By.xpath("//button[@type='button']"));
            closePopup.click();
        } catch (NoSuchElementException e) {
        }
        driver.findElement(By.xpath("//span[contains(.,'Điện thoại')]")).click();
        driver.findElement(By.cssSelector(".product-item a")).click();
        try {
            driver.findElement(By.xpath("//button[contains(.,'Mua ngay')]")).click();
        } catch (NoSuchElementException e) {
            driver.findElement(By.xpath("//button[contains(.,'Thêm vào giỏ')]")).click();
        }
        Thread.sleep(3000);
        driver.get(baseUrl + "cart/");
        try {
            driver.findElement(By.cssSelector("div.cart-item, div[class*='CartItem']"));
        } catch (NoSuchElementException e) {
        }
        try {
            WebElement removeAll =
                    driver.findElement(By.xpath("//*[contains(text(),'Xóa tất cả') or contains(text(),'Xoá giỏ hàng')]"));
            removeAll.click();
            Thread.sleep(2000);
        } catch (NoSuchElementException e) {
        }

        driver.get(baseUrl + "cart/");
        driver.get(baseUrl);
        try {
            WebElement closePopup2 = driver.findElement(By.xpath("//button[@type='button']"));
            closePopup2.click();
        } catch (NoSuchElementException e) {}

        driver.findElement(By.xpath("//span[contains(.,'Điện thoại')]")).click();
        driver.findElement(By.cssSelector(".product-item a")).click();
        try {
            driver.findElement(By.xpath("//button[contains(.,'Mua ngay')]")).click();
        } catch (NoSuchElementException e) {
            driver.findElement(By.xpath("//button[contains(.,'Thêm vào giỏ')]")).click();
        }
        Thread.sleep(3000);
        driver.get(baseUrl + "cart/");
        try {
            WebElement plusBtn = driver.findElement(
                    By.xpath("//button[contains(@class,'increment') or contains(.,'+')]"));
            plusBtn.click();
            Thread.sleep(1000);
        } catch (NoSuchElementException e) { }
        try {
            WebElement minusBtn = driver.findElement(
                    By.xpath("//button[contains(@class,'decrement') or contains(.,'-')]"));
            minusBtn.click();
            Thread.sleep(1000);
        } catch (NoSuchElementException e) { }
        try {
            WebElement minusBtnToZero = driver.findElement(
                    By.xpath("//button[contains(@class,'decrement') or contains(.,'-')]"));
            for (int i = 0; i < 3; i++) {
                minusBtnToZero.click();
                Thread.sleep(500);
            }
        } catch (NoSuchElementException e) { }
        driver.get(baseUrl);
        try {
            WebElement closePopup3 = driver.findElement(By.xpath("//button[@type='button']"));
            closePopup3.click();
        } catch (NoSuchElementException e) {}

        driver.findElement(By.xpath("//span[contains(.,'Điện thoại')]")).click();
        driver.findElement(By.cssSelector(".product-item a")).click();
        try {
            driver.findElement(By.xpath("//button[contains(.,'Mua ngay')]")).click();
        } catch (NoSuchElementException e) {
            driver.findElement(By.xpath("//button[contains(.,'Thêm vào giỏ')]")).click();
        }
        Thread.sleep(3000);
        driver.get(baseUrl + "cart/");
        try {
            WebElement deleteItemBtn = driver.findElement(
                    By.xpath("//button[contains(.,'Xóa') or contains(.,'Xoá')] | //*[name()='svg' and @data-icon='trash']"));
            deleteItemBtn.click();
            Thread.sleep(2000);
        } catch (NoSuchElementException e) { }
        driver.get(baseUrl);
        try {
            WebElement closePopup4 = driver.findElement(By.xpath("//button[@type='button']"));
            closePopup4.click();
        } catch (NoSuchElementException e) {}

        driver.findElement(By.xpath("//span[contains(.,'Điện thoại')]")).click();
        driver.findElement(By.cssSelector(".product-item a")).click();
        try {
            driver.findElement(By.xpath("//button[contains(.,'Mua ngay')]")).click();
        } catch (NoSuchElementException e) {
            driver.findElement(By.xpath("//button[contains(.,'Thêm vào giỏ')]")).click();
        }
        Thread.sleep(3000);
        driver.get(baseUrl + "cart/");
        try {
            WebElement checkoutBtn = driver.findElement(
                    By.xpath("//a[contains(.,'Thanh toán') or //button[contains(.,'Thanh toán')]]"));
            checkoutBtn.click();
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        try {
            WebElement couponInput = driver.findElement(By.xpath("//input[contains(@placeholder,'mã giảm giá') or contains(@placeholder,'Mã giảm giá')]"));
            couponInput.clear();
            couponInput.sendKeys("VALIDCODE");
            WebElement applyBtn = driver.findElement(By.xpath("//button[contains(.,'Áp dụng') or contains(.,'Áp mã')]"));
            applyBtn.click();
            Thread.sleep(2000);
        } catch (NoSuchElementException e) { }
        try {
            WebElement couponInput2 = driver.findElement(By.xpath("//input[contains(@placeholder,'mã giảm giá') or contains(@placeholder,'Mã giảm giá')]"));
            couponInput2.clear();
            couponInput2.sendKeys("MA_SAI_KHONG_HOP_LE");
            WebElement applyBtn2 = driver.findElement(By.xpath("//button[contains(.,'Áp dụng') or contains(.,'Áp mã')]"));
            applyBtn2.click();
            Thread.sleep(2000);
        } catch (NoSuchElementException e) { }
        try {
            // Ví dụ radiobutton "Giao hàng tận nơi"
            WebElement shipToHome = driver.findElement(
                    By.xpath("//*[contains(text(),'Giao hàng tận nơi')]/preceding::input[1]"));
            shipToHome.click();
            Thread.sleep(1000);
        } catch (NoSuchElementException e) { }

        try {
            // Ví dụ radiobutton "Nhận tại cửa hàng"
            WebElement pickAtStore = driver.findElement(
                    By.xpath("//*[contains(text(),'Nhận tại cửa hàng')]/preceding::input[1]"));
            pickAtStore.click();
            Thread.sleep(1000);
        } catch (NoSuchElementException e) { }
        driver.get(baseUrl);
        try {
            WebElement menuPromo = driver.findElement(
                    By.xpath("//a[contains(.,'Khuyến mãi') or contains(@href,'khuyen-mai')]"));
            menuPromo.click();
            Thread.sleep(3000);
        } catch (NoSuchElementException e) { }
        try {
            WebElement nearlyEndTab = driver.findElement(
                    By.xpath("//*[contains(.,'Sắp kết thúc') and (self::a or self::button or self::span)]"));
            nearlyEndTab.click();
            Thread.sleep(3000);
        } catch (NoSuchElementException e) { }
        try {
            WebElement firstPromo = driver.findElement(
                    By.cssSelector("a[href*='khuyen-mai'], a[href*='uu-dai']"));
            firstPromo.click();
            Thread.sleep(3000);
        } catch (NoSuchElementException e) { }
        driver.get(baseUrl + "checkout/");
        try {
            WebElement couponInput3 = driver.findElement(By.xpath("//input[contains(@placeholder,'mã giảm giá') or contains(@placeholder,'Mã giảm giá')]"));
            couponInput3.clear();
            couponInput3.sendKeys("PROMO_SAI");
            WebElement applyBtn3 = driver.findElement(By.xpath("//button[contains(.,'Áp dụng') or contains(.,'Áp mã')]"));
            applyBtn3.click();
            Thread.sleep(2000);
        } catch (NoSuchElementException e) { }
        try {
            WebElement couponInput4 = driver.findElement(By.xpath("//input[contains(@placeholder,'mã giảm giá') or contains(@placeholder,'Mã giảm giá')]"));
            couponInput4.clear();
            couponInput4.sendKeys("MA_CAN_DIEU_KIEN");
            WebElement applyBtn4 = driver.findElement(By.xpath("//button[contains(.,'Áp dụng') or contains(.,'Áp mã')]"));
            applyBtn4.click();
            Thread.sleep(2000);
        } catch (NoSuchElementException e) { }
        driver.get(baseUrl);
        try {
            driver.findElement(By.xpath("//span[contains(.,'Điện thoại')]")).click();
            driver.findElement(By.cssSelector(".product-item a")).click();
            Thread.sleep(3000);
            WebElement promoSection = driver.findElement(
                    By.xpath("//*[contains(.,'Ưu đãi') or contains(.,'Khuyến mãi')]"));
            js.executeScript("arguments[0].scrollIntoView(true);", promoSection);
            Thread.sleep(2000);
        } catch (NoSuchElementException e) { }
    }

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
