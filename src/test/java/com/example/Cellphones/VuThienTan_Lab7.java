package com.example.Cellphones;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class AllTestCases_Rewrite {

    private static final String BASE_URL = "https://cellphones.com.vn/";
    private static final Duration TIMEOUT = Duration.ofSeconds(20);

    private WebDriver driver;
    private WebDriverWait wait;
    private PrintWriter logWriter;

    // ===============================
    // LOG helper
    // ===============================
    private void log(String msg) {
        System.out.println("[INFO] " + msg);
        if (logWriter != null) {
            logWriter.println("[INFO] " + msg);
            logWriter.flush();
        }
    }

    private void logError(String msg) {
        System.out.println("[ERROR] " + msg);
        if (logWriter != null) {
            logWriter.println("[ERROR] " + msg);
            logWriter.flush();
        }
    }

    private WebElement waitAndClick(By locator, String action) {
        log("Click: " + action + " -> " + locator);
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        el.click();
        return el;
    }

    private WebElement waitAndType(By locator, String value, String action) {
        log("Nhập '" + value + "' vào: " + action);
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        el.clear();
        el.sendKeys(value);
        return el;
    }

    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("CHROME_DRIVER_PATH", "D:\\chromedriver-win64\\chromedriver.exe"));

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, TIMEOUT);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        log("=== BẮT ĐẦU TEST ===");
    }

    // =================================================================
    // TC1 – Đăng nhập đúng mật khẩu
    // =================================================================
    @Test
    public void TC1_LoginSuccess() {
        driver.get(BASE_URL);

        waitAndClick(By.xpath("//button[@type='button']"), "Đóng popup");
        waitAndClick(By.xpath("//div[contains(@id,'radix')]/div[2]/div/button[2]"), "Đăng nhập");

        driver.get("https://smember.com.vn/login?action=login&client_id=cps&redirect_uri=https%3A%2F%2Fcellphones.com.vn%2F");

        waitAndType(By.xpath("(//input)[1]"), "0901489303", "Nhập SĐT");
        waitAndType(By.xpath("(//input)[2]"), "Tan@042003", "Nhập mật khẩu");

        waitAndClick(By.xpath("//button[@type='submit']"), "Submit");

        driver.get(BASE_URL);
    }

    // =================================================================
    // TC2 – Sai mật khẩu
    // =================================================================
    @Test
    public void TC2_WrongPassword() {
        driver.get(BASE_URL);
        waitAndClick(By.xpath("//button[@type='button']"), "Đóng popup");
        waitAndClick(By.xpath("//div[contains(@id,'radix')]/div[2]/div/button[2]"), "Đăng nhập");

        driver.get("https://smember.com.vn/login?action=login");

        waitAndType(By.xpath("(//input)[1]"), "0901489303", "Nhập SĐT");
        waitAndType(By.xpath("(//input)[2]"), "Tan@123", "Nhập mật khẩu sai");

        waitAndClick(By.xpath("//button[@type='submit']"), "Đăng nhập");
    }

    // =================================================================
    // TC3 – Nhập sai mật khẩu nhiều lần
    // =================================================================
    @Test
    public void TC3_WrongPass_ManyTimes() {
        driver.get(BASE_URL);
        waitAndClick(By.xpath("(//*[text()='Đăng nhập'])[1]//*[name()='svg']"), "Click icon đăng nhập");
        waitAndClick(By.xpath("//div[contains(@id,'radix')]/div[2]/div/button[2]"), "Đăng nhập");

        driver.get("https://smember.com.vn/login");

        waitAndType(By.xpath("(//input)[1]"), "0901489303", "Nhập SĐT");
        waitAndType(By.xpath("(//input)[2]"), "123123123", "Nhập sai mật khẩu");

        for (int i = 0; i < 5; i++) {
            waitAndClick(By.xpath("//button[@type='submit']"), "Thử đăng nhập lần " + (i + 1));
        }
    }

    // =================================================================
    // TC4 – Đăng nhập bằng Google
    // =================================================================
    @Test
    public void TC4_LoginGoogle() {
        driver.get(BASE_URL);

        waitAndClick(By.xpath("//button[@type='button']"), "Đóng popup");
        waitAndClick(By.xpath("//div[contains(@id,'radix')]/div[2]/div/button[2]"), "Đăng nhập");

        driver.get("https://smember.com.vn/login");
        waitAndClick(By.xpath("//*[contains(text(),'Google')]"), "Chọn đăng nhập Google");

        // Không auto đăng nhập Google được → Test manual
        log("⚠️ Google Login cần xử lý bằng tài khoản thật → Test manual");
    }

    // =================================================================
    // TC5 – Quên mật khẩu với SĐT sai
    // =================================================================
    @Test
    public void TC5_ForgotPassword_WrongPhone() {
        driver.get(BASE_URL);

        waitAndClick(By.xpath("//button[@type='button']"), "Đóng popup");
        waitAndClick(By.xpath("//div[contains(@id,'radix')]/div[2]/div/button[2]"), "Đăng nhập");

        driver.get("https://smember.com.vn/login");
        waitAndClick(By.linkText("Quên mật khẩu?"), "Quên mật khẩu");

        waitAndType(By.name("phone"), "0123456789", "Nhập SĐT sai");
        waitAndClick(By.xpath("//button[@type='submit']"), "Gửi");
    }

    // =================================================================
    // TC6 – Quên mật khẩu với SĐT hợp lệ
    // =================================================================
    @Test
    public void TC6_ForgotPassword_ValidPhone() {
        driver.get(BASE_URL);

        waitAndClick(By.xpath("//button[@type='button']"), "Đóng popup");
        waitAndClick(By.xpath("//div[contains(@id,'radix')]/div[2]/div/button[2]"), "Đăng nhập");

        driver.get("https://smember.com.vn/login");
        waitAndClick(By.linkText("Quên mật khẩu?"), "Quên mật khẩu");

        waitAndType(By.name("phone"), "0906123123", "Nhập SĐT hợp lệ");
        waitAndClick(By.xpath("//button[@type='submit']"), "Gửi OTP");
    }

    // =================================================================
    // TC7 – Truy cập Smember từ Cellphones
    // =================================================================
    @Test
    public void TC7_OpenSmember() {
        driver.get(BASE_URL);

        waitAndClick(By.xpath("//button[@type='button']"), "Đóng popup");
        waitAndClick(By.linkText("Truy cập Smember"), "Mở Smember");

        driver.get("https://smember.com.vn/");
        waitAndClick(By.xpath("(//*[contains(text(),'Điều khoản sử dụng')])[1]/following::div[4]"),
                "Click popup");

        waitAndClick(By.xpath("//div[contains(@id,'radix')]/div[2]/button[2]"), "Chọn tiếp tục");
    }

    // =================================================================
    // TC8 – Chỉnh sửa thông tin cá nhân
    // =================================================================
    @Test
    public void TC8_EditProfile() {
        driver.get(BASE_URL);

        waitAndClick(By.xpath("//button[@type='button']"), "Đóng popup");
        waitAndClick(By.linkText("Truy cập Smember"), "Mở Smember");

        driver.get("https://smember.com.vn/");
        waitAndClick(By.xpath("(//*[text()='Mới'])[1]/following::span[1]"), "Đi tới User info");

        driver.get("https://smember.com.vn/user-info");

        waitAndClick(By.xpath("(//*[text()='Thông tin cá nhân'])[1]/following::*[name()='svg'][1]"),
                "Mở chỉnh sửa");

        waitAndClick(By.xpath("//div[contains(@id,'radix')]//input"), "Click input");

        waitAndClick(By.xpath("//button[@type='submit']"), "Lưu");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
        log("=== KẾT THÚC TEST ===");
    }
}
