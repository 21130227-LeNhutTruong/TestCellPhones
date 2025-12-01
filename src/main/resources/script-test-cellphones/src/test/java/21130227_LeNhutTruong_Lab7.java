package com.example;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class LeNhutTruong_21130227_Lab7 {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Wait chung cho toàn bộ test
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ==============================
    // Helper hàm
    // ==============================

    private void searchProduct(String name) {
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("inp$earch")));
        searchBox.clear();
        searchBox.sendKeys(name);
        searchBox.sendKeys(Keys.ENTER);
    }

    private void clickCompareButton() {
        WebElement compareBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("So sánh")));
        compareBtn.click();
    }

    // ==============================
    // TEST CASE 1: Mở trang, chọn sản phẩm
    // ==============================

    @Test
    public void TC01_openProductAndCompare() {
        driver.get("https://cellphones.com.vn/mobile.html");

        // Chờ ô tìm kiếm
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@type='text' and @placeholder='Bạn muốn mua gì hôm nay?']")
        ));

        searchProduct("iphone");

        // Chọn sản phẩm
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@alt='iPhone Air 256GB | Chính hãng']")
        ));
        product.click();
        // Nhấn So sánh
        WebElement compareBtn1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("So sánh")
        ));
        compareBtn1.click();
    }

    // ==============================
    // TEST CASE 2: So sánh sản phẩm thứ 2
    // ==============================
    @Test
    public void TC02_addSecondProductToCompare() {

        // ============================
        // SẢN PHẨM 1
        // ============================

        driver.get("https://cellphones.com.vn/mobile.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Tìm kiếm sản phẩm đầu tiên
        searchProduct("iphone air");

        // Chọn sản phẩm 1
        WebElement product1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@alt='iPhone Air 256GB | Chính hãng']")
        ));
        product1.click();

        // Nhấn So sánh
        WebElement compareBtn1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("So sánh")
        ));
        compareBtn1.click();


        // ============================
        // SẢN PHẨM 2
        // ============================

        driver.get("https://cellphones.com.vn/mobile.html");

        searchProduct("iPhone 15");
        // Chọn sản phẩm 2
        WebElement product2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@alt='iPhone 15 128GB | Chính hãng VN/A']")
        ));
        product2.click();

        // Nhấn So sánh
        WebElement compareBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("So sánh")
        ));
        compareBtn2.click();


        // ============================
        // KIỂM TRA ĐÃ VÀO TRANG SO SÁNH (2 sản phẩm)
        // ============================

//        Assert.assertTrue(driver.getCurrentUrl().contains("so-sanh"),
//                "Không mở trang so sánh sau khi thêm 2 sản phẩm!");

//        // Kiểm tra có ít nhất 2 sản phẩm trên bảng so sánh
//        int productCount = driver.findElements(By.cssSelector(".item.item-compare")).size();
//        Assert.assertTrue(productCount >= 2, "Chưa có đủ 2 sản phẩm trong bảng so sánh!");
    }


    // ==============================
    // TEST CASE 3: Xóa 1 sản phẩm khỏi danh sách so sánh
    // ==============================

    @Test
    public void TC03_removeOneProductFromCompare() {
        driver.get("https://cellphones.com.vn/so-sanh/"
                + "chuot-choi-game-co-day-logitech-g102-lightsync-8000dpi-vs-"
                + "chuot-khong-day-logitech-mx-master-2s-vs-"
                + "chuot-khong-day-logitech-signature-m650");

        WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a .icon svg")));
        removeBtn.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("so-sanh"),
                "Không còn ở trang so sánh sau khi xóa!");
    }


    // ==============================
    // TEST CASE 4: Xóa toàn bộ -> quay lại trang gốc so sánh
    // ==============================

    @Test
    public void TC04_clearAllComparisons() {
        driver.get("https://cellphones.com.vn/so-sanh/"
                + "chuot-khong-day-logitech-mx-master-2s-vs-"
                + "chuot-khong-day-logitech-signature-m650");

        WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a .icon svg")));
        removeBtn.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("so-sanh"),
                "Trang không trở về danh sách so sánh!");
    }


    // ==============================
    // TEST CASE 5: Thêm sản phẩm từ trang chi tiết → vào bảng so sánh
    // ==============================

    @Test
    public void TC05_compareFromProductDetail() {
        // ============================
        // SẢN PHẨM 1
        // ============================

        driver.get("https://cellphones.com.vn/mobile.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Tìm kiếm sản phẩm đầu tiên
        searchProduct("iphone air");

        // Chọn sản phẩm 1
        WebElement product1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@alt='iPhone Air 256GB | Chính hãng']")
        ));
        product1.click();

        // Nhấn So sánh
        WebElement compareBtn1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("So sánh")
        ));
        compareBtn1.click();


        // ============================
        // SẢN PHẨM 2
        // ============================

        driver.get("https://cellphones.com.vn/mobile.html");

        searchProduct("iPhone 15");
        // Chọn sản phẩm 2
        WebElement product2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@alt='iPhone 15 128GB | Chính hãng VN/A']")
        ));
        product2.click();

        // Nhấn So sánh
        WebElement compareBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("So sánh")
        ));
        compareBtn2.click();
        clickCompareButton();
    }

    // ==============================
    // AFTER CLASS
    // ==============================

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
