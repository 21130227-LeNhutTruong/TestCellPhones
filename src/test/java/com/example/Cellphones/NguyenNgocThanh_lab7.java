package com.example.Cellphones;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

public class NguyenNgocThanh_lab7 {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("=== Setup hoàn tất ===");
    }

    @Test
    public void TC1Login() throws Exception {

        System.out.println("=== TC1Login BẮT ĐẦU ===");

        driver.get("https://cellphones.com.vn/");
        System.out.println("✔ Mở trang Cellphones");

        System.out.println("➡ Bước 1: Click nút đăng nhập đầu tiên");
        WebElement btnLogin1 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Đăng nhập')]"))
        );
        btnLogin1.click();
        System.out.println("✔ Đã click nút đăng nhập đầu tiên");

        System.out.println("➡ Chờ overlay đăng nhập biến mất...");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'dialog-overlay')]")
        ));
        System.out.println("✔ Overlay biến mất");

        System.out.println("➡ Bước 2: Click nút đăng nhập thứ 2 trong popup");
        WebElement btnLogin2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Đăng nhập')]"))
        );
        btnLogin2.click();
        System.out.println("✔ Đã click nút đăng nhập thứ 2");

        System.out.println("➡ Bước 3: Nhập số điện thoại");
        WebElement phoneInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']"))
        );
        phoneInput.sendKeys("0523713512");
        System.out.println("✔ Đã nhập số điện thoại");

        System.out.println("➡ Bước 4: Nhập mật khẩu");
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']"));
        passwordInput.sendKeys("Thanh123123");
        System.out.println("✔ Đã nhập mật khẩu");

        System.out.println("➡ Bước 5: Click nút đăng nhập hoàn tất");
        WebElement btnSubmit = driver.findElement(By.xpath("//button[contains(., 'Đăng nhập')]"));
        btnSubmit.click();
        System.out.println("✔ Đã click đăng nhập");

        Thread.sleep(3000);
        System.out.println("=== TC1Login - Completed ===");
    }

    @Test
    public void TC2XemDiemThuong() throws Exception {
        try {
            System.out.println("=== TC2XemDiemThuong BẮT ĐẦU ===");

            driver.get("https://cellphones.com.vn/");
            System.out.println("✔ Mở trang Cellphones");

            System.out.println("➡ Click button[@type='button']");
            WebElement btnTypeButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='button']"))
            );
            btnTypeButton.click();
            System.out.println("✔ Đã click nút menu");

            System.out.println("➡ Tìm và click 'Truy cập Smember'");
            try {
                WebElement linkSmember = wait.until(
                        ExpectedConditions.elementToBeClickable(By.linkText("Truy cập Smember"))
                );
                linkSmember.click();
            } catch (TimeoutException te) {
                WebElement linkSmember2 = wait.until(
                        ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(normalize-space(.), 'Truy cập Smember')]"))
                );
                linkSmember2.click();
            }
            System.out.println("✔ Đã click Truy cập Smember");

            String originalHandle = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            if (handles.size() > 1) {
                ArrayList<String> list = new ArrayList<>(handles);
                String newTab = list.get(list.size() - 1);
                driver.switchTo().window(newTab);
                System.out.println("✔ Đã chuyển tab: " + driver.getCurrentUrl());
            } else {
                System.out.println("➡ Chờ URL chuyển sang Smember...");
                wait.until(ExpectedConditions.urlContains("smember"));
            }

            System.out.println("➡ Tìm và click 'Hạng thành viên và ưu đãi'");
            String targetXpath = "//*[normalize-space(text())='Hạng thành viên và ưu đãi'][1]/following::div[2]";
            WebElement target = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(targetXpath))
            );
            target.click();
            System.out.println("✔ Đã click Hạng thành viên & ưu đãi");

            Thread.sleep(3000);

            System.out.println("=== TC2XemDiemThuong - Completed ===");

        } catch (Exception e) {
            System.err.println("Error in TC2XemDiemThuong: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void TC3XemLichSuMuaHang() throws Exception {
        try {
            System.out.println("=== TC3XemLichSuMuaHang BẮT ĐẦU ===");

            driver.get("https://cellphones.com.vn/");
            System.out.println("✔ Mở trang Cellphones");

            System.out.println("➡ Click button[@type='button']");
            WebElement btn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='button']"))
            );
            btn.click();
            System.out.println("✔ Đã click nút menu");

            System.out.println("➡ Click 'Truy cập Smember'");
            try {
                WebElement linkSmember = wait.until(
                        ExpectedConditions.elementToBeClickable(By.linkText("Truy cập Smember"))
                );
                linkSmember.click();
            } catch (Exception e) {
                WebElement linkSmember2 = wait.until(
                        ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Truy cập Smember')]"))
                );
                linkSmember2.click();
            }
            System.out.println("✔ Đã click Truy cập Smember");

            String originalHandle = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            if (handles.size() > 1) {
                ArrayList<String> list = new ArrayList<>(handles);
                driver.switchTo().window(list.get(1));
            }
            System.out.println("✔ Đã chuyển qua tab Smember");

            System.out.println("➡ Click 'Tổng quan' → span theo ảnh");
            WebElement tongQuan = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Tổng quan'])[1]/following::span[1]")
            ));
            tongQuan.click();
            System.out.println("✔ Đã click Tổng quan");

            System.out.println("➡ Mở trang lịch sử mua hàng");
            driver.get("https://smember.com.vn/order");

            System.out.println("➡ Click icon SVG Lịch sử mua hàng");
            WebElement historyIcon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(.//*[normalize-space(text())='Lịch sử mua hàng'])[4]/following::*[name()='svg'][2]")
            ));
            historyIcon.click();
            System.out.println("✔ Đã click icon lịch sử");

            Thread.sleep(2000);
            System.out.println("=== TC3XemLichSuMuaHang - Completed ===");

        } catch (Exception e) {
            System.err.println("Error in TC3XemLichSuMuaHang: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void TC4_DSTinTuc() throws Exception {
        try {
            System.out.println("=== TC4_DSTinTuc BẮT ĐẦU ===");

            driver.get("https://cellphones.com.vn/");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            System.out.println("✔ Mở trang Cellphones");

            System.out.println("➡ Scroll xuống cuối trang...");
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(2000);

            try {
                System.out.println("➡ Nếu có popup thì đóng...");
                WebElement popup = driver.findElement(By.cssSelector("div.cps-popup-close, button.cps-popup-close"));
                popup.click();
                System.out.println("✔ Popup đã đóng");
            } catch (Exception ignored) {
                System.out.println("✔ Không có popup");
            }

            System.out.println("➡ Tìm element 'Tin tức'");
            WebElement btnTinTuc = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@id='layout-provider']/main/div/div[15]/section/div/a")
                    )
            );

            System.out.println("➡ Scroll tới nút Tin tức");
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", btnTinTuc);
            Thread.sleep(800);

            System.out.println("➡ Click nút Tin tức");
            wait.until(ExpectedConditions.elementToBeClickable(btnTinTuc));
            js.executeScript("arguments[0].click();", btnTinTuc);

            Thread.sleep(1500);

            System.out.println("➡ Chuyển sang tab mới SForum");
            String mainTab = driver.getWindowHandle();
            for (String tab : driver.getWindowHandles()) {
                if (!tab.equals(mainTab)) {
                    driver.switchTo().window(tab);
                }
            }
            System.out.println("✔ Đã vào SForum");

            wait.until(ExpectedConditions.urlContains("sforum"));
            Thread.sleep(1500);

            System.out.println("➡ Click bài viết Coros Apex 4");
            WebElement baiViet = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//img[@alt='Trên tay Coros Apex 4: Thiết kế Titan grade 5 siêu nhẹ, đối thủ của Fenix 8, giá 13.2 triệu đồng']")
            ));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", baiViet);
            baiViet.click();
            System.out.println("✔ Đã mở bài Coros Apex 4");

        } catch (Exception e) {
            System.err.println("Lỗi TC4_DSTinTuc: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void TC5New() throws Exception {
        try {
            System.out.println("=== TC5New BẮT ĐẦU ===");

            driver.get("https://cellphones.com.vn/");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            System.out.println("✔ Mở trang Cellphones");

            System.out.println("➡ Scroll xuống cuối trang...");
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(2000);

            try {
                System.out.println("➡ Kiểm tra popup...");
                WebElement closePopup = driver.findElement(By.cssSelector(".cps-popup-close"));
                closePopup.click();
                System.out.println("✔ Đã đóng popup");
                Thread.sleep(500);
            } catch (Exception ignore) {
                System.out.println("✔ Không có popup");
            }

            System.out.println("➡ Click Tin tức");
            WebElement btnTinTuc = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[@id='layout-provider']/main/div/div[15]/section/div/a")
                    )
            );
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", btnTinTuc);
            js.executeScript("arguments[0].click();", btnTinTuc);
            System.out.println("✔ Đã click Tin tức");

            Thread.sleep(1500);

            System.out.println("➡ Chuyển sang tab SForum");
            String mainTab = driver.getWindowHandle();
            for (String tab : driver.getWindowHandles()) {
                if (!tab.equals(mainTab)) {
                    driver.switchTo().window(tab);
                }
            }
            System.out.println("✔ Đã vào SForum");

            wait.until(ExpectedConditions.urlContains("sforum"));
            Thread.sleep(1000);

            System.out.println("➡ Click mục Đánh giá");
            WebElement danhGia = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//*[normalize-space(text())='Đánh giá'])[2]/following::h3[1]")
            ));
            danhGia.click();
            System.out.println("✔ Đã click Đánh giá");

            System.out.println("➡ Click span Đánh giá tiếp theo");
            WebElement danhGiaSpan = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//*[normalize-space(text())='Đánh giá'])[3]/following::span[1]")
            ));
            danhGiaSpan.click();
            System.out.println("✔ Đã click Đánh giá (span)");

            System.out.println("➡ Click Trang chủ");
            WebElement trangChu = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//*[normalize-space(text())='Trang chủ'])[1]/following::div[5]")
            ));
            trangChu.click();
            System.out.println("✔ Đã click Trang chủ");

            System.out.println("➡ Click Gia dụng");
            WebElement giaDung1 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//*[normalize-space(text())='Gia dụng'])[1]/following::div[6]")
            ));
            js.executeScript("arguments[0].click();", giaDung1);
            System.out.println("✔ Đã click Gia dụng");

            System.out.println("➡ Click Gaming Gear");
            WebElement gamingGear = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//*[normalize-space(text())='Gaming Gear'])[1]/following::div[6]")
            ));
            gamingGear.click();
            System.out.println("✔ Đã click Gaming Gear");

            System.out.println("=== TC5New - Completed ===");

        } catch (Exception e) {
            System.err.println("Error in TC5New: " + e.getMessage());
            throw e;
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println("=== Đóng browser ===");
        if (driver != null) driver.quit();
    }
}
