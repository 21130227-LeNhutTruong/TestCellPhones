package com.example.Cellphones;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class TranThiThuyLinh_Lab7 {

    private WebDriver driver;
    private String baseUrl;
    private final StringBuffer verificationErrors = new StringBuffer();


    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        baseUrl = "https://cellphones.com.vn";
        loginSmember();
    }

    public void loginSmember() throws InterruptedException {
        driver.get(baseUrl);
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[normalize-space()='Đăng nhập']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[contains(@class,'bg-gradient') and text()='Đăng nhập']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']")).sendKeys("0905257213");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']")).sendKeys("thuylinh20");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(4000);

        driver.get(baseUrl);
        Thread.sleep(2000);
    }

    // =============================
    // TC02: Tìm kiếm & thêm vào giỏ
    // =============================
    @Test
    public void testTC02() throws Exception {
        driver.get(baseUrl);

        WebElement searchBox = driver.findElement(
                By.cssSelector("input[placeholder='Bạn muốn mua gì hôm nay?']")
        );
        searchBox.sendKeys("tai nghe", Keys.ENTER);
        Thread.sleep(3000);

        driver.findElement(By.cssSelector("a.product__link")).click();
        Thread.sleep(3000);

        driver.findElement(By.cssSelector("button.add-to-cart-button")).click();
    }
    // =============================
    // TC03: Giỏ hàng → Mua ngay
    // =============================
    @Test(dependsOnMethods = "testTC02")
    public void testTC03() throws Exception {

        // Click nút Giỏ hàng
        driver.findElement(
                By.cssSelector("a.navbar__item.button__cart")
        ).click();
        Thread.sleep(3000);

        // Click nút Mua ngay
        driver.findElement(
                By.xpath("//button[contains(text(),'Mua ngay')]")
        ).click();
        Thread.sleep(3000);
    }
    // =============================
// TC04: Payment Info
// =============================
    @Test(dependsOnMethods = "testTC03")
    public void testTC04() throws Exception {

        System.out.println("[TC04] Điền thông tin giao hàng");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =============================
        // STEP 0: Đảm bảo đang ở trang payment-info
        // =============================
        wait.until(ExpectedConditions.urlContains("payment-info"));
        System.out.println("✔ Đang ở trang Payment Info");

        // =============================
        // STEP 1: Click chọn Quận / Huyện
        // =============================
        By districtInput = By.xpath("//input[@placeholder='Chọn quận/huyện']");
        WebElement districtBox = wait.until(
                ExpectedConditions.elementToBeClickable(districtInput)
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                districtBox
        );

        // =============================
        // STEP 2: Chọn Quận 6
        // =============================
        By districtOption = By.xpath("//span[normalize-space()='Quận 6']");
        WebElement quan6 = wait.until(
                ExpectedConditions.presenceOfElementLocated(districtOption)
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                quan6
        );
        System.out.println("✔ Đã chọn Quận 6");

        // =============================
        // STEP 3: Click chọn Địa chỉ cửa hàng
        // =============================
        By storeInput = By.xpath("//input[@placeholder='Chọn địa chỉ cửa hàng']");
        WebElement storeBox = wait.until(
                ExpectedConditions.elementToBeClickable(storeInput)
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                storeBox
        );

        // =============================
        // STEP 4: Chọn cửa hàng 458 - 460 Hậu Giang, P.12, Q.6, TP. HCM
        // =============================
        By storeOption = By.xpath(
                "//span[normalize-space()='458 - 460 Hậu Giang, P.12, Q.6, TP. HCM']"
        );
        WebElement store = wait.until(
                ExpectedConditions.presenceOfElementLocated(storeOption)
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                store
        );
        System.out.println("✔ Đã chọn cửa hàng Hậu Giang Q.6");

        // =============================
        // STEP 5: Chọn Không xuất hóa đơn
        // =============================
        By noVatLabel = By.xpath(
                "//label[@for='VAT-No' and normalize-space()='Không']"
        );
        WebElement noVat = wait.until(
                ExpectedConditions.elementToBeClickable(noVatLabel)
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                noVat
        );
        System.out.println("✔ Đã chọn Không xuất hóa đơn");

        // =============================
        // STEP 6: Nhấn nút Tiếp tục
        // =============================
        By continueBtn = By.xpath(
                "//button[contains(@class,'button__go-next') and contains(text(),'Tiếp tục')]"
        );
        WebElement btnContinue = wait.until(
                ExpectedConditions.elementToBeClickable(continueBtn)
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                btnContinue
        );

        System.out.println("✔ TC04 hoàn tất – chuyển sang trang chọn thanh toán");
    }
    // =============================
// TC05: Thanh toán MoMo
// =============================
    @Test(dependsOnMethods = "testTC04")
    public void testTC05() throws Exception {
        System.out.println("[TC05] Thanh toán bằng Momo");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Mở khối chọn phương thức thanh toán
        WebElement paymentBox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div.payment-quote__main--content")
                )
        );
        System.out.println("✔ Đang ở trang chọn phương thức thanh toán");
        js.executeScript("arguments[0].click();", paymentBox);

        // Chọn MoMo Wallet
        WebElement momo = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div.list-payment__item-momo_wallet")
                )
        );
        js.executeScript("arguments[0].click();", momo);
        System.out.println("Chọn phương thức Momo");

        // Xác nhận phương thức thanh toán
        WebElement confirm = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[contains(text(),'Xác nhận')]")
                )
        );
        System.out.println("Xác nhận phương thức thanh toán");
        js.executeScript("arguments[0].click();", confirm);

        // Nhấn Thanh toán
        WebElement pay = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[contains(text(),'Thanh toán')]")
                )
        );
        System.out.println("Nhấn Thanh toán");
        js.executeScript("arguments[0].click();", pay);
        Thread.sleep(3000);
        // Quay lại trang trước (tránh redirect sang MoMo thật)
        driver.navigate().back();

// Chờ trang thanh toán load lại


    }
    // =============================
// TC06: Thanh toán VNPAY
// =============================
    @Test(dependsOnMethods = "testTC04")
    public void testTC06() throws Exception {

        System.out.println("[TC06] Thanh toán bằng VNPAY");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =============================
        // STEP 0: Đảm bảo đang ở trang chọn phương thức thanh toán
        // =============================
        WebElement paymentInfo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.payment-quote__main--content")
                )
        );
        System.out.println("✔ Đang ở trang chọn phương thức thanh toán");

        // =============================
        // STEP 1: Click khối "Thông tin thanh toán"
        // =============================
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                paymentInfo
        );

        // =============================
        // STEP 2: Chọn phương thức VNPAY
        // =============================
        WebElement vnpayItem = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div.list-payment__item-vnpay")
                )
        );
        System.out.println("Chọn phương thức VNPAY");
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                vnpayItem
        );

        // Verify VNPAY active
        wait.until(
                ExpectedConditions.attributeContains(
                        vnpayItem,
                        "class",
                        "list-payment__item--active"
                )
        );
        System.out.println("✔ Đã chọn VNPAY");

        // =============================
        // STEP 3: Nhấn nút "Xác nhận"
        // =============================
        WebElement confirmBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Xác nhận']")
                )
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                confirmBtn
        );

        // =============================
        // STEP 4: Nhấn nút "Thanh toán"
        // =============================
        WebElement payBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.button__go-next")
                )
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                payBtn
        );
        // =============================
// STEP 5: Chọn "App Ngân hàng và Ví điện tử (VNPAY QR)"
// =============================
        WebElement vnpayQRMethod = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class,'title') and contains(.,'VNPAY')]")
                )
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                vnpayQRMethod
        );

// =============================
// STEP 6: Đợi màn hình QR VNPAY hiển thị
// =============================
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("vnpay"),
                        ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//img[contains(@src,'vnpay')]")
                        )
                )
        );

        System.out.println("✔ Đã hiển thị QR VNPAY");

        Thread.sleep(3000);
        // Quay lại trang trước (tránh redirect sang VNPAY thật)
        driver.navigate().back();

// Chờ trang thanh toán load lại

    }
    // =============================
// TC07: Thanh toán tại cửa hàng
// =============================
    @Test(dependsOnMethods = "testTC06")
    public void testTC07() {

        System.out.println("[TC07] Thanh toán tại cửa hàng");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =============================
        // STEP 0: Đảm bảo đang ở trang chọn phương thức thanh toán
        // =============================
        WebElement paymentInfo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.payment-quote__main--content")
                )
        );
        System.out.println("✔ Đang ở trang chọn phương thức thanh toán");

        // =============================
        // STEP 1: Click khối "Thông tin thanh toán"
        // =============================
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                paymentInfo
        );


        // =============================
        // STEP 2: Chọn "Thanh toán tại cửa hàng"
        // =============================
        WebElement storePayment = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//p[normalize-space()='Thanh toán tại cửa hàng']")
                )
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                storePayment
        );

        // Verify trạng thái active
        wait.until(
                ExpectedConditions.attributeContains(
                        storePayment.findElement(
                                By.xpath("./ancestor::div[contains(@class,'list-payment__item')]")
                        ),
                        "class",
                        "list-payment__item--active"
                )
        );
        System.out.println("✔ Đã chọn Thanh toán tại cửa hàng");

        // =============================
        // STEP 3: Nhấn nút "Xác nhận"
        // =============================
        WebElement confirmBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Xác nhận']")
                )
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                confirmBtn
        );
        System.out.println("✔ Đã nhấn Xác nhận");

        // =============================
        // STEP 4: Nhấn nút "Thanh toán"
        // =============================
        WebElement payBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@class,'button__go-next') and normalize-space()='Thanh toán']")
                )
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                payBtn
        );
        System.out.println("✔ Đã nhấn Thanh toán tại cửa hàng");

        // =============================
        // STEP 5: Verify trang hoàn tất / giữ đơn
        // =============================
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("success"),
                        ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//*[contains(text(),'giữ sản phẩm')]")
                        )
                )
        );

        System.out.println("✔ [TC07] Hoàn tất thanh toán tại cửa hàng");
    }
    // =============================
// TC08: Kiểm tra chi tiết đơn hàng
// =============================
    @Test(dependsOnMethods = "testTC07")
    public void testTC08() {

        System.out.println("[TC08] Kiểm tra chi tiết đơn hàng");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =============================
        // STEP 1: Nhấn nút "Kiểm tra đơn hàng"
        // =============================
        WebElement checkOrderBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class,'button__go-home') and contains(text(),'Kiểm tra đơn hàng')]")
                )
        );
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                checkOrderBtn
        );
        System.out.println("✔ Đã nhấn Kiểm tra đơn hàng");

        // =============================
        // STEP 2: Đợi chuyển sang trang chi tiết đơn hàng
        // =============================
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("order-detail"),
                        ExpectedConditions.urlContains("smember.com.vn")
                )
        );
        System.out.println("✔ Đã chuyển sang trang chi tiết đơn hàng");

        System.out.println("✔ Hiển thị chi tiết đơn hàng thành công");

        System.out.println("✅ [TC08] Kiểm tra đơn hàng thành công");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        if (driver != null) driver.quit();
        if (!verificationErrors.toString().isEmpty()) {
            fail(verificationErrors.toString());
        }
    }
}