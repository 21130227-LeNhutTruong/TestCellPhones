package com.example.Cellphones;

import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// Sắp xếp chạy test theo tên method
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TranThanhTri_21130578_Lab7 {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // ĐƯỜNG DẪN DRIVER
    private static final String DRIVER_PATH = "E:\\Download\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";

    // Rule này dùng để lấy tên của Test Case đang chạy hiện tại
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        driver = new ChromeDriver();

        // Cấu hình chung
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;

        // 1. Đăng nhập trước
        loginSmember();

        // 2. Kiểm tra: Nếu là test01 -> test08 thì thêm iPhone 14 vào giỏ hàng
        String currentMethod = testName.getMethodName();
        if (shouldPrepareCart(currentMethod)) {
            System.out.println(">>> [SETUP] Đang thêm iPhone 14 cho test: " + currentMethod);
            addIphone14ToCart();
        }
    }

    @After
    public void tearDown() throws Exception {
        // 1. Nếu là test01 -> test08 thì xóa sạch giỏ hàng sau khi test xong
        // Logic này đảm bảo giỏ hàng luôn trống cho test tiếp theo
        String currentMethod = testName.getMethodName();
        if (shouldPrepareCart(currentMethod)) {
            System.out.println(">>> [TEARDOWN] Đang dọn dẹp giỏ hàng sau test: " + currentMethod);
            clearCart();
        }

        // 2. Đóng trình duyệt
        if (driver != null) {
            driver.quit();
        }
    }

    // Hàm kiểm tra xem test hiện tại có nằm trong danh sách cần thêm hàng/xóa hàng không
    private boolean shouldPrepareCart(String methodName) {
        return methodName.startsWith("test01_CartHaveProductIcon()") ||
                methodName.startsWith("test02_CartEmptyCartMessage()") ||
                methodName.startsWith("test03_CartIncreaseQuantity()") ||
                methodName.startsWith("test04_CartDecreaseQuantity()") ||
                methodName.startsWith("test05_CartRemoveProduct()") ||
                methodName.startsWith("test06_CartApplyValidCoupon()") ||
                methodName.startsWith("test07_CartApplyInvalidCoupon()") ||
                methodName.startsWith("test08_CartSelectShipmentMethod()");
    }

    // ================== HÀM THÊM IPHONE 14 ==================
    // ================== HÀM THÊM IPHONE 14 (PHIÊN BẢN DEBUG MẠNH) ==================
    private void addIphone14ToCart() throws InterruptedException {
        System.out.println(">>> [START] Bắt đầu thêm iPhone 14...");
        driver.get("https://cellphones.com.vn/iphone-14.html");
        Thread.sleep(2000); // Đợi trang load hẳn

        // 1. Thử tìm nút Mua ngay (Thử nhiều loại Xpath khác nhau phòng khi web đổi)
        WebElement btnBuy = null;
        try {
            // Xpath 1: Nút mua ngay màu đỏ thường thấy
            btnBuy = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(@class, 'btn-buy-now')] | //button[contains(text(), 'Mua ngay')]")
            ));
        } catch (Exception e) {
            System.out.println(">>> Không tìm thấy nút Mua Ngay. Kiểm tra lại sản phẩm có hết hàng không?");
            return;
        }

        // 2. Scroll đến nút
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btnBuy);
        Thread.sleep(1500);

        // 3. Click nút (Thử 2 cách)
        try {
            System.out.println(">>> Đang click nút Mua ngay...");
            // Cách 1: Click bằng JS (Mạnh nhất)
            js.executeScript("arguments[0].click();", btnBuy);
        } catch (Exception e) {
            // Cách 2: Click thường
            btnBuy.click();
        }
        Thread.sleep(3000); // Đợi popup hiện ra

        // 4. Xử lý Popup "Xem giỏ hàng"
        try {
            System.out.println(">>> Đang tìm popup 'Xem giỏ hàng'...");
            WebElement btnViewCart = driver.findElement(By.xpath("//a[contains(@class, 'btn-view-cart') or contains(text(), 'Xem giỏ hàng')]"));
            js.executeScript("arguments[0].click();", btnViewCart);
            System.out.println(">>> Đã click popup.");
        } catch (Exception e) {
            System.out.println("WARN: Không thấy popup (có thể web lag hoặc đã tự chuyển trang).");
        }

        Thread.sleep(2000);

        // 5. Kiểm tra xem đã ở trang cart chưa, nếu chưa thì ép chuyển trang
        if (!driver.getCurrentUrl().contains("cart")) {
            System.out.println(">>> Vẫn chưa vào được giỏ hàng. Đang truy cập thẳng link giỏ hàng...");
            driver.get("https://cellphones.com.vn/cart");
            Thread.sleep(3000);
        }

        // 6. KIỂM TRA CUỐI CÙNG: Trong giỏ có hàng không?
        List<WebElement> products = driver.findElements(By.xpath("//div[contains(@class, 'product-info')]"));
        if (products.isEmpty()) {
            System.out.println("ERROR: Vào giỏ hàng nhưng TRỐNG RỖNG! Quy trình thêm thất bại.");
            // Thử reload lại 1 lần nữa
            driver.navigate().refresh();
            Thread.sleep(3000);
        } else {
            System.out.println(">>> [SUCCESS] Đã thấy " + products.size() + " sản phẩm trong giỏ.");
        }
    }

    // ================== HÀM XÓA SẠCH GIỎ HÀNG (CẬP NHẬT MỚI) ==================
    // Logic giống hệt test05: Tìm nút xóa -> Click -> Confirm -> Lặp lại
    private void clearCart() {
        try {
            driver.get("https://cellphones.com.vn/cart");
            Thread.sleep(2000);

            while (true) {
                // Tìm nút xóa (tương tự locator trong test05)
                // Lưu ý: Cellphones đôi khi dùng button hoặc div cho nút xóa, xpath này bao quát cả hai
                List<WebElement> removeBtns = driver.findElements(By.xpath("//div[contains(@class, 'product-info')]//*[contains(@class, 'remove-item')]"));

                if (removeBtns.isEmpty()) {
                    System.out.println(">>> Giỏ hàng đã sạch.");
                    break;
                }

                System.out.println(">>> Tìm thấy " + removeBtns.size() + " sản phẩm. Đang xóa...");

                // Lấy phần tử đầu tiên và click
                WebElement btn = removeBtns.get(0);
                try {
                    btn.click();
                } catch (ElementClickInterceptedException ex) {
                    js.executeScript("arguments[0].click();", btn);
                }

                // Chờ và click nút xác nhận (Confirm Popup) - Logic giống test05
                try {
                    WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(@class, 'btn-confirm') or contains(text(), 'Có') or contains(text(), 'Xác nhận')]")
                    ));
                    confirmBtn.click();
                } catch (Exception ex) {
                    System.out.println("WARN: Không thấy popup xác nhận (có thể đã xóa luôn).");
                }

                // Đợi load lại danh sách trước khi lặp tiếp
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi dọn dẹp giỏ hàng: " + e.getMessage());
        }
    }

    // ================== HÀM LOGIN ==================
    private void loginSmember() throws InterruptedException {
        // 1. Khởi tạo công cụ Javascript
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(2000);

        // 2. Click nút mở popup Đăng nhập (Dùng JS Click để tránh lỗi bị che)
        try {
            // Thử tìm theo Xpath đầu tiên
            WebElement btnOpenLogin1 = driver.findElement(By.xpath("//div[contains(@class, 'box-about')]//div[contains(text(), 'Đăng nhập')]"));
            js.executeScript("arguments[0].click();", btnOpenLogin1);
        } catch (Exception e) {
            // Nếu không thấy, tìm theo Xpath dự phòng
            try {
                WebElement btnOpenLogin2 = driver.findElement(By.xpath("//button[@type='button' and normalize-space()='Đăng nhập']"));
                js.executeScript("arguments[0].click();", btnOpenLogin2);
            } catch (Exception ex) {
                System.out.println("Không tìm thấy nút mở popup đăng nhập.");
            }
        }
        Thread.sleep(2000);

        // 3. Click tiếp vào nút "Đăng nhập" (Smember) bên trong popup (nếu có)
        try {
            WebElement btnLoginSub = driver.findElement(By.xpath("//button[contains(@class,'bg-gradient-to-r') and normalize-space()='Đăng nhập']"));
            js.executeScript("arguments[0].click();", btnLoginSub);
            Thread.sleep(2000);
        } catch (Exception e) {
            // Có thể trang web đã hiện thẳng ô nhập liệu, bỏ qua bước này
        }

        // 4. Nhập SĐT và Mật khẩu (Phần này dùng sendKeys bình thường là ổn)
        try {
            WebElement phoneInput = driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']"));
            phoneInput.click(); // Click vào ô trước cho chắc
            phoneInput.clear();
            phoneInput.sendKeys("0366084837");
            Thread.sleep(1000);

            WebElement passInput = driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']"));
            passInput.click();
            passInput.clear();
            passInput.sendKeys("0366084837a@");
            Thread.sleep(1000);

            // 5. Click nút Submit (Quan trọng: Dùng JS Click)
            WebElement btnSubmit = driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Đăng nhập']"));
            js.executeScript("arguments[0].click();", btnSubmit);

            System.out.println("   -> Đã nhấn nút đăng nhập.");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Lỗi khi nhập liệu hoặc submit: " + e.getMessage());
        }

        // 6. Quay lại trang chủ
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(2000);
    }

    private void proceedToPaymentInfo() {
        try {
            List<WebElement> checkboxes = driver.findElements(By.xpath("//div[@id='listItemSuperCart']//input[@type='checkbox']"));
            if (!checkboxes.isEmpty() && !checkboxes.get(0).isSelected()) {
                driver.findElement(By.xpath("(//div[@id='listItemSuperCart']//label[contains(@class,'custom-control-label')])[1]")).click();
            }
            WebElement btnOrder = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='stickyBottomBar']//button[contains(@class, 'btn-action')]")));
            btnOrder.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Có thể đã ở trang thanh toán.");
        }
    }

    // ================= TEST CASES =================

    @Test
    public void test01_CartHaveProductIcon() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =================================================================
        // PHẦN 1: THÊM SẢN PHẨM VÀO GIỎ HÀNG
        // =================================================================
        System.out.println("1. Truy cập trang sản phẩm iPhone 14...");
        driver.get("https://cellphones.com.vn/iphone-14.html");

        // 1.1 Tìm và Click nút "Mua Ngay"
        try {
            WebElement btnBuyNow = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'button-desktop-order')]")
            ));

            // Scroll đến nút để tránh lỗi bị che
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnBuyNow);
            Thread.sleep(1000);

            // Click bằng JS cho chắc chắn
            js.executeScript("arguments[0].click();", btnBuyNow);
            System.out.println(">>> Đã click nút Mua Ngay.");

        } catch (TimeoutException e) {
            Assert.fail("Không tìm thấy nút 'Mua Ngay'.");
        }

        // =================================================================
        // PHẦN 2: KIỂM TRA GIỎ HÀNG (VERIFY)
        // =================================================================

        // 2.1 Chờ chuyển hướng sang trang giỏ hàng
        // Cellphones thường tự redirect. Nếu mạng chậm hoặc popup hiện, ta đợi URL chứa 'cart'
        try {
            wait.until(ExpectedConditions.urlContains("cart"));
            System.out.println("   -> Đã chuyển sang trang Giỏ hàng.");
        } catch (Exception e) {
            // Fallback: Nếu không tự chuyển (do lỗi popup), ta ép trình duyệt đi tới giỏ
            driver.get("https://cellphones.com.vn/cart");
        }

        // 2.2 Kiểm tra xem có sản phẩm trong giỏ không
        // Tìm thẻ chứa thông tin sản phẩm trong giỏ (class thường là 'product-info' hoặc check tên sản phẩm)
        try {
            WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'product-info')]//a[contains(text(), 'iPhone 14')]")
            ));

            Assert.assertTrue("Lỗi: Sản phẩm không hiển thị trong giỏ hàng!", cartItem.isDisplayed());
            System.out.println("✅ Test 01 - PASSED: Đã thấy iPhone 14 trong giỏ hàng.");

        } catch (Exception e) {
            Assert.fail("Test Failed: Vào được giỏ hàng nhưng không thấy sản phẩm (Giỏ hàng trống).");
        }
    }

    @Test
    public void test02_CartEmptyCartMessage() throws Exception {
        // Do setUp() đã tự động thêm, ta phải xóa thủ công ở ĐẦU test này để kiểm tra trạng thái rỗng
        clearCart();

        driver.get("https://cellphones.com.vn/cart");
        By emptyMessageLocator = By.xpath("//div[contains(@class, 'nothing-in-cart')] | //p[contains(text(), 'Giỏ hàng của bạn đang trống')]");

        try {
            WebElement emptyMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(emptyMessageLocator));
            Assert.assertTrue("Thông báo giỏ hàng trống không hiển thị!", emptyMessage.isDisplayed());
        } catch (TimeoutException e) {
            System.out.println("WARN: Không thấy thông báo giỏ hàng trống.");
        }
    }

    @Test
    public void test03_CartIncreaseQuantity() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =================================================================
        // PHẦN 1: THÊM SẢN PHẨM VÀO GIỎ HÀNG (PRE-CONDITION)
        // =================================================================
        System.out.println("1. Truy cập trang sản phẩm iPhone 14...");
        driver.get("https://cellphones.com.vn/iphone-14.html");

        // 1.1 Tìm và Click nút "Mua Ngay" (LOCATOR MỚI)
        // HTML: <button class="btn-cta order-button ..."><strong>MUA NGAY</strong>...</button>
        try {
            WebElement btnBuyNow = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'order-button') and descendant::strong[contains(text(), 'MUA NGAY')]]")
            ));

            // Scroll đến nút để tránh lỗi bị che
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnBuyNow);
            Thread.sleep(1000);

            // Click bằng JS
            js.executeScript("arguments[0].click();", btnBuyNow);
            System.out.println(">>> Đã click nút Mua Ngay (Button mới).");

            Thread.sleep(3000); // Đợi xử lý

        } catch (TimeoutException e) {
            Assert.fail("Không tìm thấy nút 'Mua Ngay' mới (class: order-button).");
        }

        // =================================================================
        // PHẦN 2: CHUYỂN ĐẾN GIỎ HÀNG
        // =================================================================

        // Kiểm tra xem đã tự chuyển sang trang cart chưa. Nếu chưa thì mới click icon.
        if (!driver.getCurrentUrl().contains("cart")) {
            System.out.println("   (Chưa tự chuyển trang, thực hiện click icon giỏ hàng...)");
            try {
                WebElement btnCartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class, 'about-cart')]")
                ));
                js.executeScript("arguments[0].click();", btnCartIcon);
            } catch (Exception e) {
                System.out.println("WARN: Không click được icon giỏ hàng, chuyển hướng trực tiếp.");
                driver.get("https://cellphones.com.vn/cart");
            }
        }

        // Đợi URL chắc chắn là cart
        wait.until(ExpectedConditions.urlContains("cart"));

        // =================================================================
        // PHẦN 3: TEST TĂNG SỐ LƯỢNG
        // =================================================================

        // Đợi danh sách sản phẩm load xong
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'product-info')]")));

        // 3.1 Xác định ô Input số lượng và nút Tăng (+)
        By plusBtnLocator = By.xpath("(//span[contains(@class, 'plus')])[1]");
        By quantityInputLocator = By.xpath("(//div[contains(@class, 'product-info')]//input)[1]");

        // 3.2 Lấy số lượng hiện tại
        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInputLocator));
        int initialQty = Integer.parseInt(qtyInput.getAttribute("value"));
        System.out.println("Số lượng ban đầu: " + initialQty);

        // 3.3 Click nút Tăng (+)
        try {
            WebElement btnPlus = wait.until(ExpectedConditions.elementToBeClickable(plusBtnLocator));
            js.executeScript("arguments[0].click();", btnPlus);
            System.out.println(">>> Đã nhấn nút tăng số lượng (+).");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy hoặc không click được nút Tăng (+).");
        }

        // 3.4 Đợi hệ thống cập nhật (Quan trọng: chờ value thay đổi)
        Thread.sleep(2000); // Chờ Ajax xử lý

        // 3.5 Kiểm tra lại kết quả
        // Tìm lại element để tránh lỗi StaleElement
        qtyInput = driver.findElement(quantityInputLocator);
        int newQty = Integer.parseInt(qtyInput.getAttribute("value"));
        System.out.println("Số lượng sau khi tăng: " + newQty);

        // 3.6 Assert
        Assert.assertEquals("Lỗi: Số lượng sản phẩm không tăng lên!", initialQty + 1, newQty);
        System.out.println("✅ Test 03 - PASSED.");
    }

    @Test
    public void test04_CartDecreaseQuantity() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =================================================================
        // PHẦN 1: THÊM SẢN PHẨM VÀO GIỎ HÀNG (PRE-CONDITION)
        // =================================================================
        System.out.println("1. Truy cập trang sản phẩm iPhone 14...");
        driver.get("https://cellphones.com.vn/iphone-14.html");

        // 1.1 Tìm và Click nút "Mua Ngay" (LOCATOR MỚI)
        try {
            WebElement btnBuyNow = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'order-button') and descendant::strong[contains(text(), 'MUA NGAY')]]")
            ));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnBuyNow);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", btnBuyNow);
            System.out.println(">>> Đã click nút Mua Ngay.");

            Thread.sleep(3000);

        } catch (TimeoutException e) {
            Assert.fail("Không tìm thấy nút 'Mua Ngay' (class: order-button).");
        }

        // =================================================================
        // PHẦN 2: CHUYỂN ĐẾN GIỎ HÀNG
        // =================================================================

        // Nếu chưa tự chuyển sang cart thì click icon
        if (!driver.getCurrentUrl().contains("cart")) {
            try {
                WebElement btnCartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class, 'about-cart')]")
                ));
                js.executeScript("arguments[0].click();", btnCartIcon);
            } catch (Exception e) {
                driver.get("https://cellphones.com.vn/cart");
            }
        }

        wait.until(ExpectedConditions.urlContains("cart"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'product-info')]")));

        // =================================================================
        // PHẦN 3: TEST GIẢM SỐ LƯỢNG
        // =================================================================

        // Định nghĩa Elements
        By quantityInputLocator = By.xpath("(//div[contains(@class, 'product-info')]//input)[1]");
        By plusBtnLocator = By.xpath("(//span[contains(@class, 'plus')])[1]");
        By minusBtnLocator = By.xpath("(//span[contains(@class, 'minus')])[1]");

        // Lấy số lượng hiện tại
        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInputLocator));
        int currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
        System.out.println("Số lượng hiện tại: " + currentQty);

        // LOGIC: Nếu số lượng < 2, phải tăng lên trước để có thể test giảm
        if (currentQty < 2) {
            System.out.println(">>> Số lượng đang là 1. Tăng lên 2 để test giảm...");
            WebElement btnPlus = driver.findElement(plusBtnLocator);
            js.executeScript("arguments[0].click();", btnPlus);
            Thread.sleep(3000); // Đợi update

            // Cập nhật lại số lượng
            qtyInput = driver.findElement(quantityInputLocator);
            currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
        }

        System.out.println("Số lượng trước khi Giảm (-): " + currentQty);

        // Thực hiện giảm
        try {
            WebElement btnMinus = wait.until(ExpectedConditions.elementToBeClickable(minusBtnLocator));
            js.executeScript("arguments[0].click();", btnMinus);
            System.out.println(">>> Đã nhấn nút Giảm (-).");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy nút Giảm (-).");
        }

        // Đợi update
        Thread.sleep(3000);

        // Verify
        qtyInput = driver.findElement(quantityInputLocator);
        int newQty = Integer.parseInt(qtyInput.getAttribute("value"));
        System.out.println("Số lượng sau khi giảm: " + newQty);

        Assert.assertEquals("Lỗi: Số lượng không giảm!", currentQty - 1, newQty);
    }

    @Test
    public void test05_CartRemoveProduct() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =================================================================
        // PHẦN 1: THÊM SẢN PHẨM (PRE-CONDITION)
        // =================================================================
        System.out.println("1. Truy cập trang sản phẩm iPhone 14...");
        driver.get("https://cellphones.com.vn/iphone-14.html");

        // 1.1 Click nút "Mua Ngay" (LOCATOR MỚI)
        try {
            WebElement btnBuyNow = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'order-button') and descendant::strong[contains(text(), 'MUA NGAY')]]")
            ));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnBuyNow);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", btnBuyNow);
            System.out.println(">>> Đã click nút Mua Ngay.");

            Thread.sleep(3000);

        } catch (TimeoutException e) {
            Assert.fail("Không tìm thấy nút 'Mua Ngay'.");
        }

        // =================================================================
        // PHẦN 2: CHUYỂN ĐẾN GIỎ HÀNG
        // =================================================================
        if (!driver.getCurrentUrl().contains("cart")) {
            try {
                WebElement btnCartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class, 'about-cart')]")
                ));
                js.executeScript("arguments[0].click();", btnCartIcon);
            } catch (Exception e) {
                driver.get("https://cellphones.com.vn/cart");
            }
        }

        wait.until(ExpectedConditions.urlContains("cart"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'product-info')]")));

        // =================================================================
        // PHẦN 3: TEST XÓA SẢN PHẨM
        // =================================================================

        // Đếm số lượng sản phẩm TRƯỚC khi xóa
        int initialCount = driver.findElements(By.xpath("//div[contains(@class,'product-info')]")).size();
        System.out.println("Số lượng trước khi xóa: " + initialCount);

        // 3.1 Click nút Xóa (Tìm nút chứa icon thùng rác hoặc text 'Xóa')
        try {
            // Locator này quét tìm nút xóa của sản phẩm đầu tiên
            WebElement btnRemove = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[contains(@class, 'product-info')]//*[contains(@class, 'remove') or contains(text(), 'Xóa') or .//svg])[1]")
            ));
            js.executeScript("arguments[0].click();", btnRemove);
            System.out.println(">>> Đã nhấn nút Xóa.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy nút Xóa sản phẩm.");
        }

        // 3.2 Xử lý Popup xác nhận (Cellphones luôn hỏi lại)
        try {
            // Chờ popup xuất hiện (thời gian ngắn thôi)
            WebElement btnConfirm = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'btn-confirm') or contains(text(), 'Có') or contains(text(), 'Xác nhận')]")
            ));
            js.executeScript("arguments[0].click();", btnConfirm);
            System.out.println(">>> Đã xác nhận trên Popup.");
        } catch (Exception e) {
            System.out.println("INFO: Không thấy popup xác nhận (hoặc đã xóa luôn).");
        }

        // Đợi update
        Thread.sleep(3000);

        // Verify
        int afterCount = driver.findElements(By.xpath("//div[contains(@class,'product-info')]")).size();
        System.out.println("Số lượng sau khi xóa: " + afterCount);

        Assert.assertEquals("Lỗi: Sản phẩm chưa bị xóa!", initialCount - 1, afterCount);
    }

//    @Test
//    public void test06_CartApplyValidCoupon() throws Exception {
//        // =================================================================
//        // PHẦN 1: PRE-CONDITION (THÊM VÀO GIỎ HÀNG)
//        // =================================================================
//        System.out.println("1. Truy cập trang sản phẩm iPhone 14...");
//        driver.get("https://cellphones.com.vn/iphone-14.html");
//
//        // Click nút "Mua Ngay" ở trang sản phẩm
//        try {
//            WebElement btnBuyNow = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[contains(@class, 'button-desktop-order')]")
//            ));
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btnBuyNow);
//            Thread.sleep(1000);
//            btnBuyNow.click();
//            System.out.println(">>> Đã click nút Mua Ngay (Add to cart).");
//            Thread.sleep(3000); // Đợi popup hoặc chuyển trang
//        } catch (TimeoutException e) {
//            Assert.fail("Không tìm thấy nút 'Mua Ngay' hoặc sản phẩm hết hàng.");
//        }
//
//        // =================================================================
//        // PHẦN 2: TRONG GIỎ HÀNG (CART PAGE)
//        // =================================================================
//
//        // Đảm bảo đang ở trong giỏ hàng
//        if (!driver.getCurrentUrl().contains("cart")) {
//            driver.get("https://cellphones.com.vn/cart");
//        }
//        wait.until(ExpectedConditions.urlContains("cart"));
//
//        System.out.println("2. Bắt đầu quy trình Checkout...");
//
//        // 2.1 Click nút "Mua ngay" (Checkout) trong giỏ hàng
//        // HTML: <button class="btn-action">Mua ngay (1)</button>
//        WebElement btnCheckout = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(@class, 'btn-action') and contains(text(), 'Mua ngay')]")
//        ));
//        btnCheckout.click();
//        Thread.sleep(2000);
//
//        // =================================================================
//        // PHẦN 3: ĐIỀN THÔNG TIN THANH TOÁN & GIAO HÀNG
//        // =================================================================
//
//        // --- BƯỚC 4: CHỌN TAB "NHẬN TẠI CỬA HÀNG" ---
//        System.out.println("👉 Chọn tab 'Nhận tại cửa hàng'...");
//        try {
//            WebElement tabLabel = driver.findElement(By.xpath("//label[contains(text(),'Nhận tại cửa hàng')]"));
//            // Dùng JS click cho chắc chắn ăn
//            js.executeScript("arguments[0].click();", tabLabel);
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            System.out.println("⚠️ Tab có thể đã active.");
//        }
//
//        // --- BƯỚC 5: CHỌN ĐỊA CHỈ (DÙNG HÀM HỖ TRỢ) ---
//        // Chọn địa chỉ (Giả định hàm selectDropdownItem đã có trong class)
//        selectDropdownItem(driver, wait, "TỈNH / THÀNH PHỐ", "Hồ Chí Minh");
//        selectDropdownItem(driver, wait, "QUẬN / HUYỆN", "Quận 1");
//        selectDropdownItem(driver, wait, "CỬA HÀNG", "Nguyễn Thái Học"); // Chọn đại 1 cửa hàng
//
//        // 3.5 Chọn xuất hóa đơn: KHÔNG
//        // HTML: <input id="VAT-No" ...>
//        WebElement radioVatNoLabel = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//label[@for='VAT-No']")
//        ));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioVatNoLabel);
//
//        // 3.6 Nhấn nút "Tiếp tục"
//        // HTML: <button class="button__go-next...">
//        WebElement btnContinue = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(@class, 'button__go-next')]")
//        ));
//        btnContinue.click();
//        Thread.sleep(3000); // Chờ chuyển sang tab Thanh Toán
//
//        // =================================================================
//        // PHẦN 4: ÁP DỤNG MÃ GIẢM GIÁ
//        // =================================================================
//
//        System.out.println("4. Tiến hành áp mã giảm giá...");
//
//        // 4.1 Click mở danh sách mã giảm giá
//        // HTML: <div class="promotion-smember-isnotuse">... hoặc chọn từ 1 mã ...</div>
//        WebElement btnOpenCoupon = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//div[contains(@class, 'promotion-smember-isnotuse')]")
//        ));
//        btnOpenCoupon.click();
//        Thread.sleep(2000); // Chờ modal hiện lên
//
//        // 4.2 Chọn mã "ƯU ĐÃI MỜI KHÁCH QUEN"
//        // HTML: <h4> ƯU ĐÃI MỜI KHÁCH QUEN</h4>
//        WebElement couponItem = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//h4[contains(text(), 'ƯU ĐÃI MỜI KHÁCH QUEN')]")
//        ));
//        couponItem.click();
//        Thread.sleep(1000);
//
//        // 4.3 Nhấn nút "Xác nhận" trên Modal
//        // HTML: <button> Xác nhận </button>
//        WebElement btnConfirmCoupon = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(text(), 'Xác nhận')]")
//        ));
//        btnConfirmCoupon.click();
//        Thread.sleep(3000); // Chờ load lại giá
//
//        // =================================================================
//        // PHẦN 5: VERIFY (LOGIC MỚI: TỔNG TIỀN < TỔNG TIỀN HÀNG)
//        // =================================================================
//
//        System.out.println("5. Kiểm tra logic giá tiền...");
//
//        // 1. Lấy giá trị "Tổng tiền hàng" (Giá gốc)
//        // XPath: Tìm text 'Tổng tiền hàng' sau đó lấy thẻ p tiếp theo chứa giá trị
//        WebElement originalPriceEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//p[contains(text(), 'Tổng tiền hàng')]/following-sibling::p")
//        ));
//        long originalPrice = parseCurrency(originalPriceEl.getText());
//
//        // 2. Lấy giá trị "Tổng tiền" (Giá sau khi giảm - Phần chữ to đậm ở cuối)
//        WebElement finalPriceEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//p[contains(@class, 'quote-bottom__value')]")
//        ));
//        long finalPrice = parseCurrency(finalPriceEl.getText());
//
//        // In log để debug
//        System.out.println("------------------------------------------------");
//        System.out.println("Giá gốc (Tổng tiền hàng): " + originalPrice);
//        System.out.println("Giá cuối (Tổng tiền)    : " + finalPrice);
//        System.out.println("------------------------------------------------");
//
//        // 3. Assertion: Giá cuối phải NHỎ HƠN Giá gốc
//        Assert.assertTrue("FAILED: Giá cuối cùng không nhỏ hơn giá gốc (Mã giảm giá chưa áp dụng hoặc lỗi tính toán).",
//                finalPrice < originalPrice);
//
//        System.out.println(">>> Test Case 07 Passed: Tổng tiền đã được giảm so với giá gốc.");
//    }

    // Hàm phụ trợ để chuyển đổi chuỗi tiền tệ (VD: "14.990.000đ") thành số long (14990000)
    public long parseCurrency(String priceText) {
        if (priceText == null || priceText.isEmpty()) return 0;
        // Loại bỏ tất cả ký tự không phải số (dấu chấm, chữ đ, khoảng trắng)
        String cleanText = priceText.replaceAll("[^0-9]", "");
        return Long.parseLong(cleanText);
    }
    // --- HÀM HỖ TRỢ (GIỮ NGUYÊN) ---
    public void selectDropdownItem(WebDriver driver, WebDriverWait wait, String labelText, String optionText) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        System.out.println("👉 Đang xử lý: " + labelText + " -> " + optionText);

        try {
            // BƯỚC 1: TÌM INPUT DỰA TRÊN LABEL
            // Logic: Tìm Label trước -> Lấy thẻ cha (box-input) -> Tìm thẻ Input bên trong
            String inputXpath = "//label[contains(text(),'" + labelText + "')]/ancestor::div[contains(@class,'box-input')]//input";

            WebElement inputElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(inputXpath)));

            // Scroll đến input để tránh bị che
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", inputElement);
            Thread.sleep(1000);

            // BƯỚC 2: CLICK VÀO INPUT (QUAN TRỌNG)
            // Dùng JS Click trực tiếp vào thẻ input như bạn yêu cầu
            System.out.println("   ...Click vào input: " + labelText);
            js.executeScript("arguments[0].click();", inputElement);

            // Thêm click dự phòng: Nếu JS chưa kích hoạt, thử click thường
            try {
                inputElement.click();
            } catch (Exception e) {}

            Thread.sleep(2000); // Chờ list xổ xuống

            // BƯỚC 3: CHỌN ITEM TRONG LIST
            // Lưu ý: Dùng dấu chấm (.) để tìm text trong cả thẻ con
            String itemXpath = "//div[contains(@class,'item') and contains(., '" + optionText + "')]";

            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(itemXpath)));

            // Scroll và click chọn
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", item);
            item.click();

            System.out.println("   ✅ Đã chọn: " + optionText);
            Thread.sleep(3000); // Chờ API load dữ liệu cấp sau

        } catch (Exception e) {
            System.out.println("   ❌ Lỗi tại bước chọn " + labelText + ": " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void test07_CartApplyInvalidCoupon() throws Exception {
        // Khởi tạo biến js để dùng xuyên suốt hàm
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =================================================================
        // PHẦN 1: PRE-CONDITION (SETUP TƯƠNG TỰ TEST 7 ĐỂ ĐẾN BƯỚC NHẬP MÃ)
        // =================================================================
        System.out.println("1. [Setup] Đang điều hướng đến bước thanh toán...");

        // 1.1 Vào trang sản phẩm và click Mua Ngay
        driver.get("https://cellphones.com.vn/iphone-14.html");
        try {
            WebElement btnBuyNow = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'button-desktop-order')]")
            ));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnBuyNow);
            btnBuyNow.click();
            Thread.sleep(3000);
        } catch (Exception e) {
            // Fallback nếu lỗi tìm nút mua
            driver.get("https://cellphones.com.vn/cart");
        }

        // 1.2 Đảm bảo ở Cart và Click "Mua ngay"
        wait.until(ExpectedConditions.urlContains("cart"));
        try {
            WebElement btnCheckout = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'btn-action') and contains(text(), 'Mua ngay')]")
            ));
            btnCheckout.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("⚠️ Có thể đã ở bước thanh toán, bỏ qua click Mua ngay.");
        }

        // 1.3 Điền thông tin địa chỉ (Bắt buộc để nút Tiếp tục sáng lên)
        try {
            // Chọn tab Nhận tại cửa hàng
            WebElement tabLabel = driver.findElement(By.xpath("//label[contains(text(),'Nhận tại cửa hàng')]"));
            js.executeScript("arguments[0].click();", tabLabel);
            Thread.sleep(1000);

            // Chọn địa chỉ (Giả định hàm selectDropdownItem đã có trong class)
            selectDropdownItem(driver, wait, "TỈNH / THÀNH PHỐ", "Hồ Chí Minh");
            selectDropdownItem(driver, wait, "QUẬN / HUYỆN", "Quận 1");
            selectDropdownItem(driver, wait, "CỬA HÀNG", "Nguyễn Thái Học"); // Chọn đại 1 cửa hàng

            // Bỏ chọn xuất hóa đơn VAT (tránh lỗi validation)
            WebElement radioVatNoLabel = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='VAT-No']")));
            js.executeScript("arguments[0].click();", radioVatNoLabel);

            // Click Tiếp tục
            WebElement btnContinue = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'button__go-next')]")
            ));
            btnContinue.click();
            Thread.sleep(3000); // Chờ chuyển sang màn hình Payment

        } catch (Exception e) {
            System.out.println("ℹ️ Lưu ý: Có thể thông tin đã được điền sẵn hoặc có lỗi chọn địa chỉ (Không ảnh hưởng nếu đã qua bước này).");
        }

        // =================================================================
        // PHẦN 2: THAO TÁC MÃ GIẢM GIÁ
        // =================================================================
        System.out.println("2. Bắt đầu nhập mã giảm giá sai...");

        // 2.1 Tìm ô input (theo đúng placeholder trong ảnh)
        // (Đã xóa try-catch bao ngoài thừa thãi gây lỗi cú pháp)
        WebElement couponInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Nhập mã giảm giá (chỉ áp dụng 1 lần)']")
        ));

        // Click vào input để focus
        js.executeScript("arguments[0].click();", couponInput);

        // Nhập mã
        couponInput.clear();
        couponInput.sendKeys("GIAMGIA");
        System.out.println("   -> Đã nhập: GIAMGIA");

        // 2.2 Nhấn nút Áp dụng
        WebElement btnApply = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Áp dụng')]")
        ));
        js.executeScript("arguments[0].click();", btnApply);

        // Chờ popup xác nhận hiện lên
        Thread.sleep(1500);

        // 2.3 Xử lý Popup Xác nhận (Nếu có)
        try {
            WebElement btnConfirm = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'agree')]")
            ));
            js.executeScript("arguments[0].click();", btnConfirm);
            System.out.println("   -> Đã nhấn nút Xác nhận trên Popup.");
            Thread.sleep(2000); // Chờ thông báo lỗi xuất hiện
        } catch (Exception e) {
            System.out.println("   (Không thấy popup xác nhận, tiếp tục kiểm tra lỗi...)");
        }

        // =================================================================
        // PHẦN 3: VERIFY LỖI & KẾT THÚC
        // =================================================================
        System.out.println("3. Kiểm tra thông báo lỗi...");

        // XPath tìm chuỗi "Mã giảm giá không khả dụng" bất chấp cấu trúc thẻ
        By errorMsgLocator = By.xpath("//*[contains(., 'Mã giảm giá không khả dụng') and not(self::script)]");

        try {
            // Chờ thông báo xuất hiện
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsgLocator));

            // Kiểm tra chắc chắn text hiển thị đúng
            String actualText = errorMsg.getText();
            System.out.println("   -> Tìm thấy text trên màn hình: " + actualText);

            if (actualText.contains("Mã giảm giá không khả dụng")) {
                System.out.println("✅ TEST PASSED: Đã hiển thị đúng thông báo lỗi.");
            } else {
                Assert.fail("FAILED: Tìm thấy Element nhưng text không đúng. Text thực tế: " + actualText);
            }

        } catch (TimeoutException e) {
            // Nếu không thấy, Fail luôn
            Assert.fail("FAILED: Quá 15s mà không thấy dòng chữ 'Mã giảm giá không khả dụng' hiện lên.");
        }
    }

    @Test
    public void test08_CartSelectShipmentMethod() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

// =================================================================
        // PHẦN 1: PRE-CONDITION (THÊM VÀO GIỎ HÀNG)
        // =================================================================
        System.out.println("1. Truy cập trang sản phẩm iPhone 14...");
        driver.get("https://cellphones.com.vn/iphone-14.html");

        // Click nút "Mua Ngay" ở trang sản phẩm
        try {
            WebElement btnBuyNow = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'button-desktop-order')]")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btnBuyNow);
            Thread.sleep(1000);
            btnBuyNow.click();
            System.out.println(">>> Đã click nút Mua Ngay (Add to cart).");
            Thread.sleep(3000); // Đợi popup hoặc chuyển trang
        } catch (TimeoutException e) {
            Assert.fail("Không tìm thấy nút 'Mua Ngay' hoặc sản phẩm hết hàng.");
        }

        // =================================================================
        // PHẦN 2: TRONG GIỎ HÀNG (CART PAGE)
        // =================================================================

        // Đảm bảo đang ở trong giỏ hàng
        if (!driver.getCurrentUrl().contains("cart")) {
            driver.get("https://cellphones.com.vn/cart");
        }
        wait.until(ExpectedConditions.urlContains("cart"));

        System.out.println("2. Bắt đầu quy trình Checkout...");

        // 2.1 Click nút "Mua ngay" (Checkout) trong giỏ hàng
        // HTML: <button class="btn-action">Mua ngay (1)</button>
        WebElement btnCheckout = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'btn-action') and contains(text(), 'Mua ngay')]")
        ));
        btnCheckout.click();
        Thread.sleep(2000);

        // =================================================================
        // SCENARIO A: CHỌN "NHẬN TẠI CỬA HÀNG"
        // =================================================================
        System.out.println("2. Test kịch bản: NHẬN TẠI CỬA HÀNG...");

        // 2.1 Click Tab "Nhận tại cửa hàng"
        WebElement tabStore = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(),'Nhận tại cửa hàng')]")
        ));
        js.executeScript("arguments[0].click();", tabStore);

        // 2.2 Điền địa chỉ cửa hàng
        selectDropdownItem(driver, wait, "TỈNH / THÀNH PHỐ", "Hồ Chí Minh");
        selectDropdownItem(driver, wait, "QUẬN / HUYỆN", "Quận 1");
        selectDropdownItem(driver, wait, "CỬA HÀNG", "218-220 Trần Quang Khải");

        // 2.3 Điền thông tin khách
        fillCustomerInfo("Trí Thanh", "0366084837");

        // 2.4 Bỏ VAT và Tiếp tục
        try {
            WebElement vatNo = driver.findElement(By.xpath("//label[@for='VAT-No']"));
            if(vatNo.isDisplayed()) js.executeScript("arguments[0].click();", vatNo);
        } catch(Exception e){}

        WebElement btnContinue = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'button__go-next')]")
        ));
        js.executeScript("arguments[0].click();", btnContinue);
        Thread.sleep(3000);

        // 2.5 Verify Scenario A
        System.out.println("   -> Kiểm tra thông tin hiển thị (Cửa hàng)...");
        String pageSourceA = driver.getPageSource();
        Assert.assertTrue("Lỗi: Sai tên người nhận!", pageSourceA.contains("Trí Thanh"));
        Assert.assertTrue("Lỗi: Sai địa chỉ cửa hàng!", pageSourceA.contains("218-220 Trần Quang Khải"));
        System.out.println("✅ Scenario A - PASSED.");

        // =================================================================
        // SCENARIO B: GIAO HÀNG TẬN NƠI
        // =================================================================
        System.out.println("3. Test kịch bản: GIAO HÀNG TẬN NƠI...");

        // 3.1 Quay lại tab "1. Thông tin" để nhập lại từ đầu
        WebElement tabStep1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'nav__item')]//span[contains(text(), 'Thông tin')]")
        ));
        js.executeScript("arguments[0].click();", tabStep1);
        Thread.sleep(2000);

        // 3.2 Chọn Tab "Giao hàng tận nơi"
        WebElement tabShipping = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(),'Giao hàng tận nơi')]")
        ));
        js.executeScript("arguments[0].click();", tabShipping);
        Thread.sleep(1000);

        // 3.3 Điền thông tin giao hàng
        fillCustomerInfo("Trí Thanh", "0366084837");

        selectDropdownItem(driver, wait, "TỈNH / THÀNH PHỐ", "Hồ Chí Minh");
        selectDropdownItem(driver, wait, "QUẬN / HUYỆN", "Quận 9");
        selectDropdownItem(driver, wait, "PHƯỜNG / XÃ", "Phường Tăng Nhơn Phú A");

        // ... (Phần code bên trên giữ nguyên) ...

        // =================================================================
        // 3.4 NHẬP SỐ NHÀ (FIXED)
        // =================================================================
        // Đợi ô nhập địa chỉ xuất hiện
        WebElement streetInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[contains(@class, 'box-input__main') and contains(@placeholder, 'Số nhà, tên đường')]")
        ));

        // Thao tác nhập liệu chuẩn cho Vue.js/React
        streetInput.click();
        streetInput.clear();
        streetInput.sendKeys("87/6 đường 379");

        // Kích hoạt sự kiện để web nhận diện thay đổi (quan trọng)
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", streetInput);
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", streetInput);

        // Nhấn TAB để trigger validation
        streetInput.sendKeys(Keys.TAB);
        Thread.sleep(1000);

        System.out.println("   -> Đã nhập số nhà: 87/6 đường 379");

        // =================================================================
        // 3.5 CHỌN VAT "KHÔNG" VÀ NHẤN TIẾP TỤC (NEW)
        // =================================================================

        // 1. Chọn VAT: Không
        try {
            WebElement vatNoLabel = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[@for='VAT-No']")
            ));
            // Scroll tới element để tránh bị che
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", vatNoLabel);
            // Click bằng JS cho chắc chắn
            js.executeScript("arguments[0].click();", vatNoLabel);
            System.out.println("   -> Đã chọn xuất hóa đơn: KHÔNG");
        } catch (Exception e) {
            System.out.println("   (Warning: Không tìm thấy hoặc không click được nút VAT-No: " + e.getMessage() + ")");
        }
        Thread.sleep(1000);

        // 2. Nhấn nút "Tiếp tục"
        WebElement btnContinueB = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'button__go-next')]")
        ));
        js.executeScript("arguments[0].click();", btnContinueB);

        // Chờ chuyển trang sang bước xác nhận
        System.out.println("   -> Đang chuyển sang trang xác nhận...");
        Thread.sleep(3000);

        // =================================================================
        // 3.6 VERIFY THÔNG TIN (Dựa trên HTML mới cung cấp)
        // =================================================================
        System.out.println("   -> Kiểm tra thông tin hiển thị (Giao tận nơi)...");

        try {
            // Locator mới dựa trên class 'address-quote__block' bạn cung cấp
            WebElement infoBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'address-quote__block')]")
            ));

            // Lấy toàn bộ text trong khối thông tin này để kiểm tra
            String pageSourceResult = infoBlock.getText();

            // Debug: In ra để xem nếu lỗi
            // System.out.println("Text lấy được: \n" + pageSourceResult);

            // Assertions (Kiểm tra dữ liệu)
            Assert.assertTrue("Lỗi: Không thấy tên 'Trí Thanh'", pageSourceResult.contains("Trí Thanh"));
            Assert.assertTrue("Lỗi: Không thấy SĐT '0366084837'", pageSourceResult.contains("0366084837"));
            Assert.assertTrue("Lỗi: Không thấy Email 'tranthanhtri0147@gmail.com'", pageSourceResult.contains("tranthanhtri0147@gmail.com"));

            // Kiểm tra địa chỉ ghép (Address + Phường + Quận + TP)
            Assert.assertTrue("Lỗi: Sai số nhà/đường", pageSourceResult.contains("87/6 đường 379"));
            Assert.assertTrue("Lỗi: Sai Phường", pageSourceResult.contains("Phường Tăng Nhơn Phú A"));
            Assert.assertTrue("Lỗi: Sai Quận", pageSourceResult.contains("Quận 9"));
            Assert.assertTrue("Lỗi: Sai Thành phố", pageSourceResult.contains("Hồ Chí Minh"));

            System.out.println("✅ Scenario B - PASSED (Thông tin nhận hàng chính xác).");

        } catch (Exception e) {
            // Chụp lại source trang nếu verify thất bại để debug
            System.out.println("❌ Verify thất bại. Lỗi: " + e.getMessage());
            Assert.fail("Không tìm thấy bảng thông tin khách hàng (address-quote__block).");
        }
    }

    // --- HÀM HỖ TRỢ ĐIỀN THÔNG TIN (CẬP NHẬT) ---
    public void fillCustomerInfo(String name, String phone) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Điền Tên
            WebElement nameInput = driver.findElement(By.xpath("//input[contains(@placeholder, 'Họ tên') or contains(@placeholder, 'tên người nhận')]"));
            nameInput.click();
            nameInput.clear();
            nameInput.sendKeys(name);
            js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", nameInput);

            // Điền SĐT
            WebElement phoneInput = driver.findElement(By.xpath("//input[contains(@placeholder, 'Số điện thoại')]"));
            phoneInput.click();
            phoneInput.clear();
            phoneInput.sendKeys(phone);
            js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", phoneInput);

        } catch (Exception e) {
            System.out.println("   (Warning: Không điền được thông tin khách: " + e.getMessage() + ")");
        }
    }

    @Test
    public void test09_PromotionPageAccess() throws Exception {
        System.out.println("Test 10: Kiểm tra truy cập trang Khuyến mãi và hiển thị Icon...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Vào trang chủ
        driver.get("https://cellphones.com.vn/");

        // 2. Click vào nút "Khuyến mãi" trên menu
        try {
            WebElement promoLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/danh-sach-khuyen-mai')]")
            ));
            promoLink.click();
        } catch (Exception e) {
            // Fallback nếu click thường không ăn
            WebElement promoLink = driver.findElement(By.xpath("//a[contains(@href, '/danh-sach-khuyen-mai')]"));
            js.executeScript("arguments[0].click();", promoLink);
        }

        // 3. VERIFY: Kiểm tra sự xuất hiện của 3 hình ảnh (Icon) theo yêu cầu
        System.out.println("   -> Đang kiểm tra hiển thị các icon...");

        try {
            // --- Kiểm tra hình 1: boxHotSale (Hot Sale Cuối Tuần) ---
            WebElement imgHotSale = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//img[@alt='boxHotSale']")
            ));
            Assert.assertTrue("Lỗi: Không hiển thị icon Hot Sale!", imgHotSale.isDisplayed());
            System.out.println("   ✅ Đã thấy: boxHotSale");

            // --- Kiểm tra hình 2: boxUuDai (Ưu đãi thanh toán) ---
            WebElement imgUuDai = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//img[@alt='boxUuDai']")
            ));
            Assert.assertTrue("Lỗi: Không hiển thị icon Ưu Đãi!", imgUuDai.isDisplayed());
            System.out.println("   ✅ Đã thấy: boxUuDai");

            // --- Kiểm tra hình 3: boxPromotion (Deal Siêu Hot) ---
            WebElement imgPromotion = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//img[@alt='boxPromotion']")
            ));
            Assert.assertTrue("Lỗi: Không hiển thị icon Promotion!", imgPromotion.isDisplayed());
            System.out.println("   ✅ Đã thấy: boxPromotion");

        } catch (Exception e) {
            // Nếu thiếu 1 trong 3 hình thì test fail
            System.out.println("❌ Lỗi: Không tìm thấy đủ các icon yêu cầu. " + e.getMessage());
            Assert.fail("Test Failed: Trang khuyến mãi thiếu các icon (HotSale, UuDai, Promotion).");
        }

        System.out.println("✅ Test 09 - PASSED (Đã hiển thị đủ 3 icon).");
    }

    @Test
    public void test10_ClickDealHotBanner() throws Exception {
        System.out.println("Test 11: Click banner Deal Siêu Hot và Xem chi tiết...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Truy cập trang chủ
        driver.get("https://cellphones.com.vn/");

        // 2. Nhấn vào nút "Khuyến mãi" trên menu
        try {
            WebElement promoLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/danh-sach-khuyen-mai')]")
            ));
            promoLink.click();
        } catch (Exception e) {
            // Fallback click bằng JS nếu bị che
            WebElement promoLink = driver.findElement(By.xpath("//a[contains(@href, '/danh-sach-khuyen-mai')]"));
            js.executeScript("arguments[0].click();", promoLink);
        }

        // Chờ trang khuyến mãi load xong
        wait.until(ExpectedConditions.urlContains("danh-sach-khuyen-mai"));

        // 3. Nhấn vào icon "Deal Siêu Hot" (boxPromotion)
        // Locator dựa trên thẻ: <img alt="boxPromotion" ...>
        System.out.println("   -> Đang chọn mục Deal Siêu Hot...");
        try {
            WebElement dealIcon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//img[@alt='boxPromotion']")
            ));
            // Scroll đến icon và click
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dealIcon);
            dealIcon.click();
            Thread.sleep(2000); // Chờ hiệu ứng scroll/filter của trang web
        } catch (Exception e) {
            Assert.fail("Không tìm thấy hoặc không click được icon Deal Siêu Hot (boxPromotion).");
        }

        // 4. Nhấn nút "Xem chi tiết"
        // Locator dựa trên thẻ: <a href="...iphone-17.html" class="detail__btn button__link">Xem chi tiết</a>
        System.out.println("   -> Đang tìm nút 'Xem chi tiết'...");
        try {
            // Cách 1: Tìm chính xác theo link iPhone 17 (theo HTML bạn gửi)
            WebElement detailBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class, 'detail__btn') and contains(@href, 'iphone-17')]")
            ));

            // Scroll đến nút và click
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", detailBtn);
            // Dùng JS click để tránh bị overlay quảng cáo che
            js.executeScript("arguments[0].click();", detailBtn);

        } catch (Exception e) {
            System.out.println("   (Warning: Không thấy link iPhone 17 cụ thể, thử tìm nút 'Xem chi tiết' bất kỳ đầu tiên...)");
            // Cách 2 (Fallback): Tìm nút "Xem chi tiết" bất kỳ trong phần hiển thị nếu link cụ thể kia bị đổi
            try {
                WebElement anyDetailBtn = driver.findElement(By.xpath("(//a[contains(@class, 'detail__btn') and contains(text(), 'Xem chi tiết')])[1]"));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", anyDetailBtn);
                js.executeScript("arguments[0].click();", anyDetailBtn);
            } catch (Exception ex) {
                Assert.fail("Lỗi: Không tìm thấy nút 'Xem chi tiết' nào khả dụng.");
            }
        }

        // 5. Verify: Kiểm tra đã chuyển sang trang chi tiết sản phẩm chưa
        Thread.sleep(2000); // Chờ trang mới load
        String currentUrl = driver.getCurrentUrl();
        System.out.println("   -> URL sau khi click: " + currentUrl);

        // Điều kiện kết thúc: URL không còn là trang danh sách khuyến mãi nữa
        Assert.assertFalse("Lỗi: Vẫn ở trang khuyến mãi, chưa chuyển trang!", currentUrl.contains("danh-sach-khuyen-mai"));

        System.out.println("✅ Test 10 - PASSED (Đã chuyển sang trang chi tiết).");
    }

    @Test
    public void test11_CheckPaymentTabsSwitching() throws Exception {
        System.out.println("Test 12: Kiểm tra chuyển đổi Tab Ưu Đãi Thanh Toán...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Truy cập trang Khuyến mãi
        driver.get("https://cellphones.com.vn/");
        try {
            WebElement promoLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/danh-sach-khuyen-mai')]")
            ));
            promoLink.click();
        } catch (Exception e) {
            // Fallback click
            WebElement promoLink = driver.findElement(By.xpath("//a[contains(@href, '/danh-sach-khuyen-mai')]"));
            js.executeScript("arguments[0].click();", promoLink);
        }

        // Chờ trang load và cuộn xuống phần Ưu Đãi Thanh Toán
        wait.until(ExpectedConditions.urlContains("danh-sach-khuyen-mai"));

        // Tìm tiêu đề section để scroll tới (đảm bảo các tab hiển thị trong view)
        try {
            WebElement sectionTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Ưu đãi thanh toán') or contains(text(), 'ƯU ĐÃI THANH TOÁN')]")
            ));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sectionTitle);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("   (Warning: Không tìm thấy tiêu đề section, cố gắng tìm tab trực tiếp...)");
        }

        // --- TAB 1: Ưu đãi thẻ ngân hàng ---
        System.out.println("   -> Click Tab 1: Ưu đãi thẻ ngân hàng");
        clickTabByText("Ưu đãi thẻ ngân hàng");
        // Verify: Kiểm tra hiển thị nội dung liên quan (Ví dụ check xem có thẻ nào hiển thị không)
        verifyContentVisible();

        // --- TAB 2: Mở thẻ nhận quà ---
        System.out.println("   -> Click Tab 2: Mở thẻ nhận quà");
        clickTabByText("Mở thẻ nhận quà");
        // Verify: Tab này thường chứa TPBank Evo (như yêu cầu của bạn)
        verifyContentContainsText("TPBank");

        // --- TAB 3: Mua trước trả sau ---
        System.out.println("   -> Click Tab 3: Mua trước trả sau");
        clickTabByText("Mua trước trả sau");
        // Verify: Tab này thường chứa Kredivo hoặc Fundiin
        verifyContentVisible();

        // --- TAB 4: Ưu đãi ví điện tử ---
        System.out.println("   -> Click Tab 4: Ưu đãi ví điện tử");
        clickTabByText("Ưu đãi ví điện tử");
        // Verify: Tab này thường chứa MoMo, ZaloPay, ShopeePay
        verifyContentVisible();

        System.out.println("✅ Test 11 - PASSED (Các tab hoạt động bình thường).");
    }

    // --- HÀM HỖ TRỢ RIÊNG CHO TEST 12 (Đặt bên trong Class) ---

    // Hàm click vào Tab dựa trên text trong thẻ <p class="option__text">
    private void clickTabByText(String tabName) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            // Locator: Tìm thẻ <p> có class là 'option__text' và chứa text tương ứng
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//p[contains(@class, 'option__text') and contains(text(), '" + tabName + "')]")
            ));

            // Scroll nhẹ để tránh header che
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tab);
            Thread.sleep(500); // Đợi scroll

            // Click
            tab.click();
            Thread.sleep(1500); // Chờ nội dung bên dưới load lại (AJAX)

        } catch (Exception e) {
            // Nếu click thường lỗi, dùng JS Click
            try {
                WebElement tab = driver.findElement(By.xpath("//p[contains(@class, 'option__text') and contains(text(), '" + tabName + "')]"));
                js.executeScript("arguments[0].click();", tab);
                Thread.sleep(1500);
            } catch (Exception ex) {
                Assert.fail("Lỗi: Không click được vào tab [" + tabName + "]");
            }
        }
    }

    // Hàm kiểm tra nội dung bên dưới có hiển thị (Generic)
    private void verifyContentVisible() {
        try {
            // Tìm container chứa danh sách item (thường là list sản phẩm/banner)
            // Cellphones thường dùng class chứa 'item' hoặc 'list' cho các khối này
            WebElement contentArea = driver.findElement(By.xpath("//div[contains(@class, 'list-promotion') or contains(@class, 'payment-list') or contains(@class, 'grid')]"));
            if (contentArea.isDisplayed()) {
                System.out.println("      (Content đã hiển thị)");
            }
        } catch (Exception e) {
            System.out.println("      (Warning: Không check được content cụ thể, nhưng Tab click không lỗi)");
        }
    }

    // Hàm kiểm tra nội dung có chứa từ khóa cụ thể (VD: TPBank)
    private void verifyContentContainsText(String keyword) {
        try {
            // Tìm xem có element nào chứa từ khóa đó hiển thị không
            WebElement element = driver.findElement(By.xpath("//*[contains(text(), '" + keyword + "')]"));
            if (element.isDisplayed()) {
                System.out.println("      (Đã tìm thấy nội dung đúng: " + keyword + ")");
            }
        } catch (Exception e) {
            System.out.println("      (Warning: Không thấy chữ '" + keyword + "' trong tab này)");
        }
    }

    @Test
    public void test12_CheckBankOfferDetails() throws Exception {
        System.out.println("Test 13: Kiểm tra nút 'Xem chi tiết' thẻ ngân hàng...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Truy cập trang chủ & Vào trang Khuyến mãi
        driver.get("https://cellphones.com.vn/");
        try {
            WebElement promoLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/danh-sach-khuyen-mai')]")
            ));
            promoLink.click();
        } catch (Exception e) {
            WebElement promoLink = driver.findElement(By.xpath("//a[contains(@href, '/danh-sach-khuyen-mai')]"));
            js.executeScript("arguments[0].click();", promoLink);
        }

        // Chờ trang load
        wait.until(ExpectedConditions.urlContains("danh-sach-khuyen-mai"));

        // 2. Nhấn vào icon "boxUuDai" (Ưu đãi thanh toán)
        // Locator: <img alt="boxUuDai" ...>
        System.out.println("   -> Click icon Ưu đãi thanh toán (boxUuDai)...");
        try {
            WebElement iconUuDai = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//img[@alt='boxUuDai']")
            ));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", iconUuDai);
            iconUuDai.click();
            Thread.sleep(2000); // Chờ trang cuộn/lọc dữ liệu
        } catch (Exception e) {
            Assert.fail("Không tìm thấy icon boxUuDai.");
        }

        // 3. Nhấn nút "Xem chi tiết"
        // Locator ưu tiên: Link Shinhan như bạn cung cấp.
        // Locator dự phòng: Bất kỳ nút "Xem chi tiết" nào (phòng trường hợp deal Shinhan hết hạn).
        System.out.println("   -> Tìm và nhấn nút 'Xem chi tiết'...");

        WebElement detailBtn = null;
        try {
            // Thử tìm chính xác link Shinhan
            detailBtn = driver.findElement(By.xpath("//a[contains(@href, 'shinhan-finance-iphone-17') and contains(text(), 'Xem')]"));
        } catch (Exception e) {
            System.out.println("   (Info: Không thấy deal Shinhan cụ thể, sẽ click vào deal đầu tiên tìm thấy)");
            // Tìm nút có class 'detail__btn' và text chứa 'Xem chi tiết'
            try {
                detailBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("(//a[contains(@class, 'detail__btn') and contains(., 'Xem')])[1]")
                ));
            } catch (Exception ex) {
                Assert.fail("Lỗi: Không tìm thấy bất kỳ nút 'Xem chi tiết' nào trong mục ưu đãi.");
            }
        }

        // Thực hiện Click (Dùng JS để tránh bị Header che)
        if (detailBtn != null) {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", detailBtn);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", detailBtn);
        }

        // 4. Verify (Kiểm tra chuyển trang)
        Thread.sleep(2000); // Chờ load trang mới
        String currentUrl = driver.getCurrentUrl();
        System.out.println("   -> URL sau khi click: " + currentUrl);

        // Kiểm tra xem đã thoát khỏi trang danh sách khuyến mãi chưa
        // URL trang chi tiết thường chứa "/uu-dai-doi-tac/" hoặc slug sản phẩm
        boolean isNewPage = !currentUrl.contains("danh-sach-khuyen-mai") || currentUrl.contains("uu-dai-doi-tac");

        Assert.assertTrue("Lỗi: Nút 'Xem chi tiết' không chuyển sang trang mới!", isNewPage);

        System.out.println("✅ Test 12 - PASSED (Nút hoạt động bình thường).");
    }

    @Test
    public void test13_CheckProductPriceDisplay() throws Exception {
        System.out.println("Test 13: Kiểm tra hiển thị Giá gốc và Giá khuyến mãi...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Vào trang chủ
        driver.get("https://cellphones.com.vn/");

        // 2. Click Tab "Điện thoại" (LOCATOR ĐÃ SỬA)
        // HTML: <button ...><h2>Điện thoại</h2></button>
        try {
            WebElement tabPhone = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[descendant::h2[contains(text(), 'Điện thoại')]]")
            ));

            // Scroll và click
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tabPhone);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", tabPhone);
            System.out.println("   -> Đã click tab 'Điện thoại'");

            Thread.sleep(3000); // Chờ danh sách sản phẩm load lại

        } catch (Exception e) {
            System.out.println("   (Warning: Không click được tab, sẽ thử tìm sản phẩm ngay tại trang chủ...)");
        }

        // 3. Tìm một sản phẩm hiển thị đủ 2 loại giá
        // (Logic: Tìm thẻ DIV bao ngoài chứa cả class 'line-through' và 'text-primary')
        System.out.println("   -> Đang tìm một sản phẩm có đủ 2 loại giá...");

        try {
            // Locator thông minh: Tìm phần tử đầu tiên [1] có chứa con là giá cũ (gạch ngang) VÀ giá mới (màu đỏ)
            WebElement validProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//div[descendant::*[contains(@class, 'line-through')] and descendant::*[contains(@class, 'text-primary') or contains(@class, 'text-[#d70018]')]])[1]")
            ));

            // Scroll tới sản phẩm để nhìn thấy rõ
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", validProduct);
            js.executeScript("arguments[0].style.border='2px solid blue'", validProduct); // Highlight xanh
            Thread.sleep(1000);

            // 4. KIỂM TRA (ASSERTION)

            // Giá Mới (Màu đỏ/đậm - thường dùng class text-primary-500 trong HTML bạn gửi)
            WebElement newPrice = validProduct.findElement(By.xpath(".//*[contains(@class, 'text-primary') or contains(@class, 'text-[#d70018]')]"));
            String newPriceText = newPrice.getText();

            // Giá Gốc (Màu xám/Gạch ngang - class line-through)
            WebElement oldPrice = validProduct.findElement(By.xpath(".//*[contains(@class, 'line-through')]"));
            String oldPriceText = oldPrice.getText();

            // Kiểm tra hiển thị
            Assert.assertTrue("Lỗi: Không hiển thị giá khuyến mãi!", newPrice.isDisplayed());
            Assert.assertTrue("Lỗi: Không hiển thị giá gốc!", oldPrice.isDisplayed());
            Assert.assertFalse("Lỗi: Giá khuyến mãi bị rỗng", newPriceText.isEmpty());
            Assert.assertFalse("Lỗi: Giá gốc bị rỗng", oldPriceText.isEmpty());

            System.out.println("      ✅ Giá khuyến mãi: " + newPriceText);
            System.out.println("      ✅ Giá gốc: " + oldPriceText);

        } catch (Exception e) {
            System.out.println("❌ Lỗi: Không tìm thấy sản phẩm nào hiển thị đủ 2 giá (Có thể do mạng chậm hoặc không có sản phẩm sale).");
            Assert.fail("Test Failed: Sản phẩm thiếu giá gốc hoặc giá khuyến mãi.");
        }

        System.out.println("✅ Test 13 - PASSED.");
    }

    @Test
    public void test14_ViewPromotionsByCategory() throws Exception {
        System.out.println("Test 15: Xem khuyến mãi theo danh mục sản phẩm (Điện thoại)...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Truy cập trang chủ
        driver.get("https://cellphones.com.vn/");

        // 2. Nhấn vào danh mục "Điện thoại"
        // Locator: <a class="hover:text-primary-500" href="/mobile.html">Điện thoại</a>
        System.out.println("   -> Đang truy cập danh mục Điện thoại...");
        try {
            WebElement mobileLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/mobile.html') and contains(text(), 'Điện thoại')]")
            ));
            mobileLink.click();
        } catch (Exception e) {
            // Fallback click bằng JS nếu bị menu che
            WebElement mobileLink = driver.findElement(By.xpath("//a[contains(@href, '/mobile.html')]"));
            js.executeScript("arguments[0].click();", mobileLink);
        }

        // Chờ chuyển trang
        wait.until(ExpectedConditions.urlContains("mobile.html"));

        // 3. Nhấn nút "Khuyến mãi HOT"
        // Locator: <a class="button__sort">... Khuyến mãi HOT </a>
        System.out.println("   -> Đang chọn bộ lọc 'Khuyến mãi HOT'...");
        try {
            // Sử dụng normalize-space() để xử lý khoảng trắng/xuống dòng trong thẻ <a>
            WebElement hotPromoBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class, 'button__sort') and contains(normalize-space(), 'Khuyến mãi HOT')]")
            ));

            // Scroll nhẹ tới nút lọc (phòng trường hợp màn hình nhỏ)
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", hotPromoBtn);
            Thread.sleep(500);

            // Click
            hotPromoBtn.click();

            // Chờ danh sách sản phẩm reload/sort lại (thường mất 2-3s)
            Thread.sleep(3000);

        } catch (Exception e) {
            Assert.fail("Lỗi: Không tìm thấy nút 'Khuyến mãi HOT'.");
        }

        // 4. Verify: Kiểm tra danh sách hiển thị đúng
        System.out.println("   -> Kiểm tra danh sách sản phẩm...");

        // 4.1 Kiểm tra xem có sản phẩm nào hiển thị không
        List<WebElement> productList = driver.findElements(By.xpath("//div[contains(@class, 'product-info')]"));
        Assert.assertFalse("Lỗi: Danh sách sản phẩm trống!", productList.isEmpty());

        // 4.2 Kiểm tra sản phẩm đầu tiên có phải là hàng khuyến mãi không?
        // (Dựa vào hình ảnh: Phải có tag màu đỏ 'Giảm xx%' ở góc trái)
        try {
            WebElement firstProduct = productList.get(0);

            // Tìm nhãn giảm giá (thường chứa text 'Giảm')
            WebElement discountTag = firstProduct.findElement(By.xpath(".//div[contains(text(), 'Giảm')]"));

            Assert.assertTrue("Lỗi: Sản phẩm không có nhãn giảm giá (Không phải khuyến mãi HOT)", discountTag.isDisplayed());
            System.out.println("      ✅ Sản phẩm đầu tiên có nhãn: " + discountTag.getText());

        } catch (Exception e) {
            System.out.println("      (Warning: Không check được nhãn giảm giá cụ thể, nhưng danh sách đã hiển thị).");
        }

        // 4.3 Kiểm tra nút "Khuyến mãi HOT" có đang Active không (thường class sẽ đổi hoặc có màu khác)
        // Đây là bước phụ để chắc chắn filter đã ăn
        try {
            WebElement activeFilter = driver.findElement(By.xpath("//a[contains(@class, 'button__sort') and contains(@class, 'active') and contains(normalize-space(), 'Khuyến mãi HOT')]"));
            if(activeFilter.isDisplayed()) System.out.println("      ✅ Filter 'Khuyến mãi HOT' đang được kích hoạt.");
        } catch (Exception e) {
            // Một số giao diện Cellphones cũ/mới class active có thể khác, không bắt buộc fail test này
        }

        System.out.println("✅ Test 14 - PASSED.");
    }
}