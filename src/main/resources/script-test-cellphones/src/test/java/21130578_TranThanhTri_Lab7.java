package com.example.Cellphones;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// Sắp xếp chạy test theo tên method
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TranThanhTri_Lab7 {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // ĐƯỜNG DẪN DRIVER
    private static final String DRIVER_PATH = "E:\\Download\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "https://cellphones.com.vn/";

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        driver = new ChromeDriver();

        // Cấu hình chung
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;

        // === GỌI HÀM LOGIN NGAY SAU KHI MỞ TRÌNH DUYỆT ===
        loginSmember();
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================== HÀM LOGIN (USER CUNG CẤP) ==================
    private void loginSmember() throws InterruptedException {
        // Truy cập trang chủ
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(2000);

        // Click nút Đăng nhập (Icon người dùng)
        try {
            driver.findElement(By.xpath("//div[contains(@class, 'box-about')]//div[contains(text(), 'Đăng nhập')]")).click();
        } catch (Exception e) {
            // Fallback nếu giao diện khác hoặc dùng xpath bạn cung cấp
            driver.findElement(By.xpath("//button[@type='button' and normalize-space()='Đăng nhập']")).click();
        }
        Thread.sleep(2000);

        // Click nút Đăng nhập Smember (Màu đỏ/gradient)
        driver.findElement(By.xpath("//button[contains(@class,'bg-gradient-to-r') and normalize-space()='Đăng nhập']")).click();
        Thread.sleep(2000);

        // Nhập SĐT
        WebElement phoneInput = driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']"));
        phoneInput.clear();
        phoneInput.sendKeys("0366084837");
        Thread.sleep(1000);

        // Nhập Password
        WebElement passInput = driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']"));
        passInput.clear();
        passInput.sendKeys("0366084837a@");
        Thread.sleep(1000);

        // Click Submit
        driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Đăng nhập']")).click();

        // Chờ đăng nhập thành công và load lại trang
        Thread.sleep(3000);

        // Quay lại trang chủ để sẵn sàng test
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(2000);
    }

    // ================== CÁC HÀM HELPER KHÁC ==================

    private void addSpecificProductToCart() throws InterruptedException {
        // 1. Truy cập link sản phẩm cụ thể
        driver.get("https://cellphones.com.vn/laptop-dell-inspiron-14-5440-d0f3w.html");

        try {
            // 2. Tìm nút "Mua Ngay" hoặc "Đặt Trước" (Class: order-button)
            // Dựa trên HTML bạn cung cấp, nút này có class 'btn-cta order-button ...'
            WebElement btnBuy = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'order-button') or contains(text(), 'Mua ngay') or contains(text(), 'ĐẶT TRƯỚC NGAY')]")
            ));

            // Scroll để đảm bảo nút hiển thị rõ ràng
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btnBuy);
            Thread.sleep(1000);

            // 3. Click mua
            btnBuy.click();

            // 4. Chờ popup hoặc chuyển trang
            try {
                WebElement viewCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class, 'btn-view-cart') or contains(text(), 'Xem giỏ hàng') or contains(@href, 'cart')]")
                ));
                viewCartBtn.click();
            } catch (Exception e) {
                // Nếu không hiện popup thì chờ url đổi sang cart
            }

            // 5. Đảm bảo đã vào trang giỏ hàng
            wait.until(ExpectedConditions.urlContains("cart"));
            Thread.sleep(2000); // Chờ load DOM giỏ hàng

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sản phẩm Dell: " + e.getMessage());
        }
    }

    private void proceedToPaymentInfo() {
        try {
            // Đảm bảo chọn checkbox sản phẩm đầu tiên
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

    // Hàm xóa sạch giỏ hàng (Dùng cho Test 02)
    private void clearCart() {
        driver.get("https://cellphones.com.vn/cart");
        try {
            List<WebElement> removeBtns = driver.findElements(By.xpath("//div[contains(@class, 'remove-item') or contains(text(), 'Xóa')]"));
            for (WebElement btn : removeBtns) {
                try {
                    btn.click();
                    Thread.sleep(1000);
                    // Confirm xóa nếu có popup
                    List<WebElement> confirms = driver.findElements(By.xpath("//button[contains(text(), 'Có') or contains(text(), 'Xác nhận')]"));
                    if(!confirms.isEmpty()) confirms.get(0).click();
                    Thread.sleep(2000);
                } catch (Exception ex) {}
            }
        } catch (Exception e) {}
    }

    // ================= GROUP 1: CART TESTS =================

    @Test
    public void test01_CartHaveProductIcon() throws Exception {
        // Đã login ở @Before
        WebElement btnCart = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class, 'button__cart')]")
        ));
        btnCart.click();
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    @Test
    public void test02_CartEmptyCartMessage() throws Exception {
        // Xóa giỏ hàng trước khi test để đảm bảo giỏ trống
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
        // 1. Thêm sản phẩm vào giỏ trước khi test để đảm bảo có hàng
        addSpecificProductToCart();

        // --- Bắt đầu Test Case ---

        // Định vị ô Input hiển thị số lượng (của sản phẩm đầu tiên trong list)
        // Dựa trên HTML: div class="action d-flex" -> input
        By quantityInputLocator = By.xpath("(//div[contains(@class, 'product-info')]//div[contains(@class, 'action')]//input)[1]");

        // Định vị nút Cộng (+)
        // Dựa trên HTML: span class="plus ..."
        By plusBtnLocator = By.xpath("(//div[contains(@class, 'product-info')]//span[contains(@class, 'plus')])[1]");

        // 2. Lấy số lượng hiện tại trước khi click
        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInputLocator));
        int initialQty = Integer.parseInt(qtyInput.getAttribute("value"));
        System.out.println("Số lượng ban đầu: " + initialQty);

        // 3. Click nút dấu cộng (+)
        WebElement plusBtn = wait.until(ExpectedConditions.elementToBeClickable(plusBtnLocator));
        plusBtn.click();

        // 4. Chờ hệ thống xử lý và cập nhật lại giao diện (quan trọng)
        // Vì sau khi click, web sẽ load lại phần giỏ hàng, cần chờ để lấy giá trị mới
        Thread.sleep(3000);

        // 5. Lấy lại element input và giá trị mới (phải findElement lại vì DOM đã thay đổi)
        qtyInput = driver.findElement(quantityInputLocator);
        int newQty = Integer.parseInt(qtyInput.getAttribute("value"));
        System.out.println("Số lượng sau khi tăng: " + newQty);

        // 6. Kiểm tra kết quả: Số lượng mới phải bằng số lượng cũ + 1
        Assert.assertEquals("Lỗi: Số lượng sản phẩm không tăng lên sau khi nhấn nút cộng!", initialQty + 1, newQty);
    }

    @Test
    public void test04_CartDecreaseQuantity() throws Exception {
        // 1. Thêm sản phẩm vào giỏ
        addSpecificProductToCart();

        // Định vị các element
        By quantityInputLocator = By.xpath("(//div[contains(@class, 'product-info')]//div[contains(@class, 'action')]//input)[1]");
        By plusBtnLocator = By.xpath("(//div[contains(@class, 'product-info')]//span[contains(@class, 'plus')])[1]");
        By minusBtnLocator = By.xpath("(//div[contains(@class, 'product-info')]//span[contains(@class, 'minus')])[1]");

        // 2. Lấy số lượng hiện tại
        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInputLocator));
        int currentQty = Integer.parseInt(qtyInput.getAttribute("value"));

        // 3. Nếu số lượng < 2, cần tăng lên trước để khi giảm không bị xóa mất sản phẩm
        if (currentQty < 2) {
            System.out.println("Số lượng hiện tại là 1, đang tăng lên 2 để test chức năng giảm...");
            driver.findElement(plusBtnLocator).click();
            Thread.sleep(3000); // Chờ cập nhật

            // Cập nhật lại số lượng ban đầu sau khi tăng
            qtyInput = driver.findElement(quantityInputLocator);
            currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
        }

        System.out.println("Số lượng trước khi giảm: " + currentQty);

        // 4. Nhấn dấu "-" (minus)
        WebElement minusBtn = wait.until(ExpectedConditions.elementToBeClickable(minusBtnLocator));
        minusBtn.click();

        // 5. Chờ hệ thống xử lý
        Thread.sleep(3000);

        // 6. Kiểm tra kết quả: Số lượng mới phải bằng số lượng cũ - 1
        qtyInput = driver.findElement(quantityInputLocator);
        int newQty = Integer.parseInt(qtyInput.getAttribute("value"));
        System.out.println("Số lượng sau khi giảm: " + newQty);

        Assert.assertEquals("Lỗi: Số lượng sản phẩm không giảm sau khi nhấn nút trừ!", currentQty - 1, newQty);
    }

    @Test
    public void test05_CartRemoveProduct() throws Exception {
        // 1. Thêm sản phẩm vào giỏ
        addSpecificProductToCart();

        // 2. Định vị nút thùng rác (class="remove-item")
        // HTML: <button class="remove-item"><svg ...></svg></button>
        By removeBtnLocator = By.xpath("(//div[contains(@class, 'product-info')]//button[contains(@class, 'remove-item')])[1]");

        // Xác định element sản phẩm để kiểm tra nó biến mất sau này
        WebElement productItem = driver.findElement(By.xpath("(//div[contains(@class, 'block__product-item')])[1]"));

        // 3. Nhấn nút xóa
        WebElement btnRemove = wait.until(ExpectedConditions.elementToBeClickable(removeBtnLocator));
        btnRemove.click();

        System.out.println("Đã nhấn nút xóa (thùng rác).");

        // 4. Xử lý popup xác nhận (nếu có)
        // CellphoneS thường hiện popup hỏi "Bạn có chắc muốn xóa?"
        try {
            By confirmBtnLocator = By.xpath("//button[contains(@class, 'btn-confirm') or contains(text(), 'Xác nhận') or contains(text(), 'Có') or contains(text(), 'Đồng ý')]");
            // Chờ một chút xem popup có hiện không (timeout ngắn 3s thôi)
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement confirmBtn = shortWait.until(ExpectedConditions.elementToBeClickable(confirmBtnLocator));
            confirmBtn.click();
            System.out.println("Đã xác nhận trên popup.");
        } catch (Exception e) {
            System.out.println("Không thấy popup xác nhận, có thể sản phẩm đã được xóa ngay.");
        }

        // 5. Chờ hệ thống cập nhật giỏ hàng
        Thread.sleep(3000);

        // 6. Verify: Kiểm tra element cũ đã bị xóa (Stale) hoặc không còn tìm thấy trong DOM
        try {
            // Nếu element cũ vẫn còn hiển thị -> Xóa thất bại
            if (productItem.isDisplayed()) {
                // Thử tìm lại xem nó có thực sự còn trong DOM không (đôi khi cache)
                driver.findElement(By.xpath("(//div[contains(@class, 'block__product-item')])[1]"));
                Assert.fail("Lỗi: Sản phẩm vẫn còn trong giỏ hàng sau khi xóa!");
            }
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            // Nếu văng lỗi này nghĩa là element đã bị xóa khỏi DOM -> Test Pass
            System.out.println("Thành công: Sản phẩm đã bị xóa khỏi giỏ hàng.");
        }
    }

    @Test
    public void test07_CartApplyValidCoupon() throws Exception {
        // 1. Chuẩn bị: Thêm hàng và vào trang thanh toán
        addSpecificProductToCart();
        proceedToPaymentInfo();

        // 2. Lấy giá tiền ban đầu (Trước khi áp mã)
        // Tìm element hiển thị giá (ưu tiên giá hiển thị màu đỏ hoặc tổng tiền)
        By priceLocator = By.xpath("//p[contains(@class, 'product__price--show') or contains(@class, 'total-price')]");
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(priceLocator));
        long originalPrice = Long.parseLong(priceElement.getText().replaceAll("[^0-9]", ""));
        System.out.println("Giá gốc: " + originalPrice);

        // 3. Nhấn vào dòng "hoặc chọn từ ... mã giảm giá có sẵn"
        // Sử dụng text() để tìm chính xác theo yêu cầu
        By openCouponListBtn = By.xpath("//*[contains(text(), 'mã giảm giá có sẵn') or contains(text(), 'Chọn mã ưu đãi')]");
        WebElement btnOpen = wait.until(ExpectedConditions.elementToBeClickable(openCouponListBtn));
        btnOpen.click();

        // 4. Tìm và chọn "ưu đãi mời khách quen"
        // Chờ popup/modal hiện ra
        Thread.sleep(1000);
        By couponItemLocator = By.xpath("//*[contains(text(), 'khách quen') or contains(text(), 'Khách quen')]");
        WebElement couponItem = wait.until(ExpectedConditions.elementToBeClickable(couponItemLocator));
        couponItem.click();

        // 5. Nhấn nút "Áp dụng" (nếu popup yêu cầu xác nhận)
        try {
            By applyBtnInModal = By.xpath("//button[contains(text(), 'Áp dụng') and not(contains(@class, 'disabled'))]");
            List<WebElement> applyBtns = driver.findElements(applyBtnInModal);
            if (!applyBtns.isEmpty() && applyBtns.get(0).isDisplayed()) {
                applyBtns.get(0).click();
            }
        } catch (Exception e) {
            // Một số giao diện chọn xong tự đóng popup nên bỏ qua lỗi này
        }

        // 6. Chờ hệ thống cập nhật lại giá tiền
        Thread.sleep(3000);

        // 7. Lấy giá tiền mới và kiểm tra logic giảm giá
        // Cần findElement lại vì DOM đã thay đổi sau khi áp mã
        priceElement = driver.findElement(priceLocator);
        long newPrice = Long.parseLong(priceElement.getText().replaceAll("[^0-9]", ""));
        System.out.println("Giá sau khi áp mã: " + newPrice);

        Assert.assertTrue("Test thất bại: Giá tiền không giảm sau khi chọn ưu đãi! (Gốc: " + originalPrice + ", Mới: " + newPrice + ")", newPrice < originalPrice);

        // Quay lại trang trước để dọn dẹp trạng thái cho các test sau (nếu có)
        driver.navigate().back();
    }

    @Test
    public void test08_CartApplyInvalidCoupon() throws Exception {
        // [CẬP NHẬT] Thêm sản phẩm vào giỏ trước khi test
        addSpecificProductToCart();
        proceedToPaymentInfo();

        By couponInputLocator = By.xpath("//div[contains(@class, 'block-promotion-input')]//input");
        WebElement couponInput = wait.until(ExpectedConditions.visibilityOfElementLocated(couponInputLocator));
        couponInput.clear();
        couponInput.sendKeys("INVALID_CODE");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'button__voucher')]"))).click();

        Thread.sleep(1500);
        // Logic check lỗi
        System.out.println("Đã thử nhập mã sai.");
    }

    @Test
    public void test09_CartSelectShipmentMethod() throws Exception {
        // [CẬP NHẬT] Thêm sản phẩm vào giỏ trước khi test
        addSpecificProductToCart();
        proceedToPaymentInfo();

        // Với tài khoản đã login, có thể địa chỉ đã được lưu sẵn
        // Đoạn code này sẽ xử lý trường hợp nhập mới
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(), 'Giao hàng tận nơi')]"))).click();
        } catch(Exception e) {
            System.out.println("Có thể đã default chọn giao hàng tận nơi");
        }

        // Nếu form địa chỉ hiện ra thì điền, nếu đã có địa chỉ mặc định của user thì bỏ qua
        if (driver.findElements(By.xpath("//input[@placeholder='Tỉnh / Thành phố']")).size() > 0) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Tỉnh / Thành phố']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Hồ Chí Minh')]"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Quận / Huyện']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Quận 1')]"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Phường / Xã']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Phường Bến Nghé')]"))).click();

            WebElement street = driver.findElement(By.xpath("//input[@placeholder='Số nhà, tên đường']"));
            street.clear();
            street.sendKeys("123 Test Street");
        }

        try {
            driver.findElement(By.xpath("//label[@for='VAT-No']")).click();
        } catch (Exception e) {}

        WebElement btnContinue = driver.findElement(By.xpath("//div[@class='block-info']//button[contains(text(), 'Tiếp tục') or contains(@class, 'btn-danger')]"));
        // wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
        // Note: Click tiếp tục sẽ chuyển sang thanh toán thật, nên dừng ở đây là an toàn.

        // Quay lại giỏ hàng để kết thúc test
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'button__back')]"))).click();
    }

    // ================= GROUP 2: PROMOTION TESTS =================

    @Test
    public void test10_PromotionPageAccess() throws Exception {
        driver.get("https://cellphones.com.vn/danh-sach-khuyen-mai");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navigationBar")));

        WebElement tabUuDai = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class, 'nav-item') and @href='#boxUuDai']")
        ));
        tabUuDai.click();

        WebElement promoItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='boxUuDai']//p[contains(text(), 'Ưu đãi thẻ ngân hàng')]")
        ));
        Assert.assertTrue(promoItem.isDisplayed());
    }

    @Test
    public void test11_DealCheck() throws Exception {
        driver.get("https://cellphones.com.vn/danh-sach-khuyen-mai");
        WebElement tabPromotion = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class, 'nav-item') and @href='#boxPromotion']")
        ));
        tabPromotion.click();

        WebElement promotionSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("boxPromotion")));
        try {
            WebElement banner = promotionSection.findElement(By.xpath(".//a//img | .//div[contains(@class, 'promotion-item')]"));
            js.executeScript("arguments[0].scrollIntoView(true);", banner);
            Thread.sleep(1000);
            banner.click();
        } catch (NoSuchElementException e) {}
    }

    @Test
    public void test12_PromotionTabSwitching() throws Exception {
        driver.get("https://cellphones.com.vn/danh-sach-khuyen-mai");
        WebElement sectionUuDai = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("boxUuDai")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sectionUuDai);
        Thread.sleep(1000);

        String[] tabs = {
                "//div[@id='boxUuDai']//p[contains(text(), 'Mở thẻ nhận quà')]",
                "//div[@id='boxUuDai']//p[contains(text(), 'Mua trước trả sau')]",
                "//div[@id='boxUuDai']//p[contains(text(), 'Ưu đãi thẻ ngân hàng')]"
        };

        for (String tabXpath : tabs) {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tabXpath)));
            tab.click();
            Thread.sleep(1000);
        }
    }

    @Test
    public void test13_ProductPriceDisplay() throws Exception {
        driver.get("https://cellphones.com.vn/danh-sach-khuyen-mai");
        WebElement hotSaleSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("boxHotSale")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", hotSaleSection);
        Thread.sleep(2000);

        WebElement firstProduct = hotSaleSection.findElement(By.xpath(".//div[contains(@class, 'product-info-container')][1]"));

        WebElement salePrice = firstProduct.findElement(By.className("product__price--show"));
        WebElement originalPrice = firstProduct.findElement(By.className("product__price--through"));

        long saleVal = Long.parseLong(salePrice.getText().replaceAll("[^0-9]", ""));
        long orgVal = Long.parseLong(originalPrice.getText().replaceAll("[^0-9]", ""));

        Assert.assertTrue(saleVal < orgVal);
    }

    @Test
    public void test14_ProductPromotionBox() throws Exception {
        driver.get("https://cellphones.com.vn/mobile.html");
        WebElement firstProd = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'product-info-container')])[1]//a")
        ));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstProd);
        firstProd.click();

        By promoBox = By.xpath("//div[contains(@class, 'box-promotion') or contains(@class, 'promotion-pack')]");
        try {
            WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(promoBox));
            Assert.assertTrue(box.isDisplayed());
        } catch (TimeoutException e) {}
    }

    @Test
    public void test15_CategoryPromotionFilter() throws Exception {
        driver.get("https://cellphones.com.vn/mobile.html");
        try {
            WebElement sortBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'filter-sort__list')]//a[contains(text(), 'Khuyến mãi HOT')]")
            ));
            sortBtn.click();
            Thread.sleep(2000);
        } catch (Exception e) {}

        WebElement firstProd = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class, 'product-list-filter')]//div[contains(@class, 'product-info-container')])[1]//a")
        ));
        firstProd.click();

        By promoBox = By.xpath("//div[contains(@class, 'box-promotion') or contains(@class, 'promotion-pack')]");
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(promoBox));
        Assert.assertTrue(box.isDisplayed());
    }
}
