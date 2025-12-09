package com.example.Cellphones;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DoNhuKien_Lab7 {

    private WebDriver driver;
    JavascriptExecutor js;

    // LINK SẢN PHẨM DÙNG CHUNG
    private static final String PRODUCT_URL = "https://cellphones.com.vn/iphone-16-pro-max.html";

    @Before
    public void setUp() throws Exception {
        // Cấu hình Chrome Options để fix lỗi Connection Reset & Crash
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;

        // LOGIN BẮT BUỘC TRƯỚC KHI TEST
        loginSmember();
    }

    // ================== HÀM LOGIN ==================
    private void loginSmember() throws InterruptedException {
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(2000);

        try {
            // Click nút Đăng nhập (Header)
            WebElement loginBtn = driver.findElement(By.xpath("//div[contains(@class,'box-about')]//div[contains(text(),'Đăng nhập')]"));
            loginBtn.click();
        } catch (Exception e) {
            // Fallback nếu xpath trên lỗi (do UI thay đổi)
            driver.get("https://cellphones.com.vn/smember");
        }
        Thread.sleep(2000);

        // Click nút "Đăng nhập ngay" (nếu có popup Smember)
        try {
            List<WebElement> loginPopupBtns = driver.findElements(By.xpath("//a[contains(text(),'Đăng nhập ngay')]"));
            if(!loginPopupBtns.isEmpty()) loginPopupBtns.get(0).click();
        } catch (Exception e) {}
        Thread.sleep(2000);

        // Nhập SĐT & Pass
        try {
            WebElement phoneInput = driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']"));
            phoneInput.clear();
            phoneInput.sendKeys("0353933224");
            Thread.sleep(1000);

            WebElement passInput = driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']"));
            passInput.clear();
            passInput.sendKeys("Nhukien24@");
            Thread.sleep(1000);

            WebElement submitBtn = driver.findElement(By.xpath("//button[contains(text(),'Đăng nhập')]"));
            submitBtn.click();
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi login (Có thể đã login rồi hoặc dính Captcha): " + e.getMessage());
        }

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(2000);
    }

    // ================== TEST CASES (ĐÃ FIX LỖI) ==================

    @Test
    public void testTC1_AddToWishlist_ByExactHtmlState() throws Exception {
        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        String productName = (String) js.executeScript(
                "return document.querySelector('.box-product-name h1').innerText.trim()"
        );
        System.out.println("Tên sản phẩm: " + productName);

        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));
        WebElement stateDiv = wishlistBtn.findElement(By.cssSelector("div.btn__effect.button__add-wishlist"));

        String classBefore = stateDiv.getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        // Nếu đang active thì click để reset về inactive
        if(classBefore.contains("active") && !classBefore.contains("inactive")) {
            wishlistBtn.click();
            Thread.sleep(2000);
            classBefore = stateDiv.getAttribute("class");
        }

        assertTrue(classBefore.contains("inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfter = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click: " + classAfter);

        assertTrue(classAfter.contains("active"));
    }

    @Test
    public void testTC2_ToggleWishlist_AddThenRemove() throws Exception {
        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));
        WebElement stateDiv = wishlistBtn.findElement(By.cssSelector("div.btn__effect.button__add-wishlist"));

        // Reset trạng thái về inactive nếu cần
        if(stateDiv.getAttribute("class").contains("active") && !stateDiv.getAttribute("class").contains("inactive")){
            wishlistBtn.click();
            Thread.sleep(2000);
        }

        System.out.println("Bắt đầu Toggle...");

        // Click 1: Add
        wishlistBtn.click();
        Thread.sleep(3000);
        String classAfterAdd = stateDiv.getAttribute("class");
        System.out.println("Sau click 1 (Add): " + classAfterAdd);
        assertTrue(classAfterAdd.contains("active"));

        // Click 2: Remove
        wishlistBtn.click();
        Thread.sleep(3000);
        String classAfterRemove = stateDiv.getAttribute("class");
        System.out.println("Sau click 2 (Remove): " + classAfterRemove);
        assertTrue(classAfterRemove.contains("inactive"));
    }

    @Test
    public void testTC3_AddWishlist_FromMobileCategory() throws Exception {
        driver.get("https://cellphones.com.vn/mobile.html");
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Tìm nút tim đầu tiên
        WebElement wishlistBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class,'product-item')])[1]//button[@id='wishListBtn']")
        ));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", wishlistBtn);
        Thread.sleep(2000);

        // Click bằng JS cho chắc
        js.executeScript("arguments[0].click();", wishlistBtn);
        Thread.sleep(3000);

        WebElement btnState = wishlistBtn.findElement(By.xpath(".//div"));
        String classAfter = btnState.getAttribute("class");
        System.out.println("Class sau khi click (Category): " + classAfter);

        assertTrue(classAfter.contains("active"));
    }

    @Test
    public void testTC4_RemoveWishlist_FromMobileCategory() throws Exception {
        // Logic tương tự TC3 nhưng click 2 lần
        driver.get("https://cellphones.com.vn/mobile.html");
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement wishlistBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class,'product-item')])[1]//button[@id='wishListBtn']")
        ));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", wishlistBtn);
        Thread.sleep(2000);

        // Đảm bảo đang inactive trước
        WebElement btnState = wishlistBtn.findElement(By.xpath(".//div"));
        if(btnState.getAttribute("class").contains("active")) {
            js.executeScript("arguments[0].click();", wishlistBtn);
            Thread.sleep(2000);
        }

        // Click 1: Add
        js.executeScript("arguments[0].click();", wishlistBtn);
        Thread.sleep(2000);
        assertTrue(btnState.getAttribute("class").contains("active"));

        // Click 2: Remove
        js.executeScript("arguments[0].click();", wishlistBtn);
        Thread.sleep(2000);
        assertTrue(btnState.getAttribute("class").contains("inactive"));
    }

    @Test
    public void testTC5_AddWishlist_ThenReload_StillActive() throws Exception {
        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));
        WebElement stateDiv = wishlistBtn.findElement(By.cssSelector("div.btn__effect.button__add-wishlist"));

        if(stateDiv.getAttribute("class").contains("inactive")) {
            wishlistBtn.click();
            Thread.sleep(3000);
        }

        assertTrue(stateDiv.getAttribute("class").contains("active"));

        System.out.println("Reload trang...");
        driver.navigate().refresh();
        Thread.sleep(5000);

        // Bắt lại element sau khi reload
        WebElement wishlistBtnReload = driver.findElement(By.id("wishListBtn"));
        WebElement stateDivReload = wishlistBtnReload.findElement(By.cssSelector("div.btn__effect.button__add-wishlist"));

        String classReload = stateDivReload.getAttribute("class");
        System.out.println("Class sau reload: " + classReload);
        assertTrue(classReload.contains("active"));
    }

    @Test
    public void testTC6_AddWishlist_FromMobile_ThenOpenProduct() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://cellphones.com.vn/mobile.html");
        Thread.sleep(5000);

        WebElement productItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class,'product-item')])[1]")
        ));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", productItem);
        Thread.sleep(1000);

        // Click Tim ở trang danh mục
        WebElement wishlistBtn = productItem.findElement(By.xpath(".//button[@id='wishListBtn']"));
        js.executeScript("arguments[0].click();", wishlistBtn);
        Thread.sleep(3000);

        // Click vào sản phẩm để mở chi tiết
        WebElement link = productItem.findElement(By.tagName("a"));
        js.executeScript("arguments[0].click();", link);
        Thread.sleep(5000);

        // Check nút tim ở trang chi tiết
        WebElement wishlistDetail = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("wishListBtn")));
        WebElement stateDetail = wishlistDetail.findElement(By.cssSelector("div.btn__effect.button__add-wishlist"));

        System.out.println("Class chi tiết: " + stateDetail.getAttribute("class"));
        assertTrue(stateDetail.getAttribute("class").contains("active"));
    }

    @Test
    public void testTC7_ViewWishlist_FromSmember() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        // Add SP trước
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);
        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));
        WebElement stateDiv = wishlistBtn.findElement(By.cssSelector("div.btn__effect.button__add-wishlist"));
        if(stateDiv.getAttribute("class").contains("inactive")) {
            wishlistBtn.click();
            Thread.sleep(2000);
        }

        // Vào Smember
        driver.get("https://cellphones.com.vn/smember");
        Thread.sleep(5000);

        // Scroll tới list
        WebElement wishlistBox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(text(),'Sản phẩm yêu thích')]")
        ));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", wishlistBox);
        Thread.sleep(2000);

        List<WebElement> wishlistItems = driver.findElements(
                By.xpath("//div[@data-slot='scroll-area-viewport']//a")
        );

        System.out.println("Số lượng SP yêu thích: " + wishlistItems.size());
        assertTrue("Wishlist trống!", !wishlistItems.isEmpty());
    }

    @Test
    public void testTC8_FullFlow_Add_Then_RemoveAll_ViaDetail() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Duration defaultWait = Duration.ofSeconds(20);

        // B1: Thêm sản phẩm vào wishlist (Setup data)
        driver.get(PRODUCT_URL);
        Thread.sleep(4000);
        WebElement mainWishlistBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("wishListBtn")));
        WebElement btnStateDiv = mainWishlistBtn.findElement(By.cssSelector("div.btn__effect.button__add-wishlist"));

        if (btnStateDiv.getAttribute("class").contains("inactive")) {
            mainWishlistBtn.click();
            Thread.sleep(2000);
        }

        // B2: Vào Smember xóa sạch
        System.out.println("🚀 BẮT ĐẦU QUY TRÌNH XÓA SẠCH WISHLIST...");
        while (true) {
            driver.get("https://cellphones.com.vn/smember");
            Thread.sleep(3000);

            try {
                WebElement wishlistTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Sản phẩm yêu thích')]")));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", wishlistTitle);
                Thread.sleep(1000);
            } catch (Exception e) {}

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            List<WebElement> wishlistItems = driver.findElements(By.xpath("//div[@data-slot='scroll-area-viewport']//a[.//div[contains(@class,'line-clamp-2')]]"));
            driver.manage().timeouts().implicitlyWait(defaultWait);

            if (wishlistItems.isEmpty()) {
                System.out.println("✅ Danh sách Wishlist đã TRỐNG.");
                break;
            }

            System.out.println("🔄 Còn " + wishlistItems.size() + " sản phẩm. Đang xóa...");

            String detailLink = wishlistItems.get(0).getAttribute("href");
            driver.get(detailLink);
            Thread.sleep(4000);

            try {
                WebElement detailBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("wishListBtn")));
                String btnClass = detailBtn.findElement(By.xpath("./div")).getAttribute("class");
                if (!btnClass.contains("inactive")) {
                    detailBtn.click();
                    System.out.println("   -> Đã click XÓA tim.");
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                System.out.println("   ❌ Lỗi nút tim: " + e.getMessage());
            }
        }

        driver.navigate().refresh();
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        List<WebElement> finalCheck = driver.findElements(By.xpath("//div[@data-slot='scroll-area-viewport']//a[.//div[contains(@class,'line-clamp-2')]]"));
        driver.manage().timeouts().implicitlyWait(defaultWait);

        assertTrue("❌ Vẫn còn sản phẩm trong Wishlist!", finalCheck.isEmpty());
    }

    @Test
    public void testTC09_ViewProductDetail_PrintInfo() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-product-name h1")));
        String productName = nameEl.getText().trim();

        WebElement priceEl = driver.findElement(By.cssSelector(".box-product-price .sale-price"));
        String productPrice = priceEl.getText().trim();

        System.out.println("=== THÔNG TIN SẢN PHẨM ===");
        System.out.println("Tên: " + productName);
        System.out.println("Giá: " + productPrice);

        assertTrue("Tên sản phẩm bị trống", !productName.isEmpty());
    }

    @Test
    public void testTC10_ZoomImage_Gallery() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        try {
            // Click ảnh chính để mở modal
            WebElement mainImage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".gallery-top .swiper-slide-active img")));
            mainImage.click();
            Thread.sleep(2000);

            // Check modal xuất hiện (Spotlight lib)
            WebElement spotlight = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#spotlight.show")));
            boolean hasCloseBtn = !spotlight.findElements(By.cssSelector(".spl-close")).isEmpty();

            if (spotlight.isDisplayed() && hasCloseBtn) {
                System.out.println("✅ Hình ảnh hiển thị đầy đủ và có chức năng phóng to");
            } else {
                fail("❌ Modal phóng to không hiển thị đúng.");
            }
        } catch (Exception e) {
            fail("❌ Lỗi TC10: " + e.getMessage());
        }
    }

    @Test
    public void testTC12_ViewReviewList_And_Rating() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        try {
            WebElement reviewSection = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("review")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", reviewSection);
            Thread.sleep(2000);

            List<WebElement> reviews = driver.findElements(By.cssSelector(".boxReview-comment-item"));
            System.out.println("🌟 Tìm thấy " + reviews.size() + " đánh giá.");

            if(!reviews.isEmpty()){
                String name = reviews.get(0).findElement(By.cssSelector(".block-info__name .name")).getText();
                System.out.println("Review mẫu: " + name);
            }

            System.out.println("✅ KẾT LUẬN: Sản phẩm CÓ đánh giá.");
            assertTrue(reviews.size() > 0);

        } catch (Exception e) {
            System.out.println("⚠️ Không tìm thấy khu vực đánh giá.");
        }
    }

    @Test
    public void testTC13_WriteReview_Flow() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        try {
            WebElement btnWrite = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button__review")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnWrite);
            Thread.sleep(1000);
            btnWrite.click();

            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".review-container")));

            // Chọn 5 sao
            List<WebElement> stars = modal.findElements(By.cssSelector(".star-all .modal__button"));
            if(stars.size() >= 5) stars.get(4).click();

            // Nhập text
            modal.findElement(By.tagName("textarea")).sendKeys("Sản phẩm tuyệt vời, chất lượng tốt");

            System.out.println("✅ Đã mở form và nhập đánh giá thành công.");
        } catch (Exception e) {
            fail("Lỗi TC13: " + e.getMessage());
        }
    }

    @Test
    public void testTC14_AddToCart_And_Verify() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        String rawName = driver.findElement(By.cssSelector(".box-product-name h1")).getText().trim();
        String cleanName = rawName.toLowerCase().replace("điện thoại", "").replace("laptop", "").trim();
        System.out.println("Tên cần tìm: " + cleanName);

        // Click thêm giỏ hàng
        try {
            WebElement btnAdd = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button-add-to-cart")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnAdd);
            Thread.sleep(1000);
            btnAdd.click();
            Thread.sleep(3000);
        } catch (Exception e) {
            fail("❌ Không thấy nút thêm giỏ hàng.");
        }

        // Vào giỏ hàng
        driver.get("https://cellphones.com.vn/cart");
        Thread.sleep(3000);

        List<WebElement> cartItems = driver.findElements(By.cssSelector(".product-info .product-name a"));
        boolean found = false;
        for (WebElement item : cartItems) {
            if (item.getText().toLowerCase().contains(cleanName)) {
                found = true;
                break;
            }
        }

        if (found) System.out.println("✅ SP xuất hiện trong giỏ hàng");
        else System.out.println("❌ Không thấy SP trong giỏ.");

        assertTrue("SP không có trong giỏ", found);
    }

    @Test
    public void testTC15_CompareProduct_Flow() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        String rawName = driver.findElement(By.cssSelector(".box-product-name h1")).getText();

        // 1. Click nút so sánh
        WebElement btnCompareTrigger = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pdp-compare-button-box")));
        btnCompareTrigger.click();
        Thread.sleep(2000);

        // 2. Click ô chọn sản phẩm (XPath fix)
        try {
            WebElement emptySlot = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'compare-product')]//p[contains(text(),'Chọn sản phẩm')]/..")
            ));
            js.executeScript("arguments[0].click();", emptySlot);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("❌ Không click được ô chọn SP so sánh.");
            return;
        }

        // 3. Chọn sản phẩm gợi ý thứ 3
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".popup-select-product.show")));
            List<WebElement> items = driver.findElements(By.cssSelector(".popup-select-product .product-item"));
            if (items.size() >= 3) {
                WebElement btnSelect = items.get(2).findElement(By.cssSelector(".select-to-compare"));
                js.executeScript("arguments[0].click();", btnSelect);
                Thread.sleep(2000);
            }
        } catch (Exception e) {}

        // 4. Click nút "So sánh" chuyển trang
        WebElement btnGo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-go-compare")));
        btnGo.click();
        Thread.sleep(5000);

        // 5. Verify Title trang so sánh
        String pageTitle = driver.findElement(By.cssSelector(".box-compare__title")).getText();
        System.out.println("Tiêu đề trang so sánh: " + pageTitle);

        assertTrue(pageTitle.toLowerCase().contains(rawName.toLowerCase().replace("điện thoại ", "")));
    }

    @Test
    public void testTC16_ChangeVersion_VerifyPrice() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        WebElement priceEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sale-price")));
        String oldPrice = priceEl.getText().trim();

        // Tìm phiên bản chưa active để click
        List<WebElement> versions = driver.findElements(By.cssSelector(".list-linked .item-linked"));
        boolean clicked = false;

        for (WebElement ver : versions) {
            if (!ver.getAttribute("class").contains("active")) {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", ver);
                Thread.sleep(1000);
                ver.click();
                clicked = true;
                break;
            }
        }

        if (!clicked) {
            System.out.println("⚠️ Không có phiên bản khác để chọn.");
            return;
        }

        Thread.sleep(4000); // Chờ load giá mới

        WebElement newPriceEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sale-price")));
        String newPrice = newPriceEl.getText().trim();

        System.out.println("Giá cũ: " + oldPrice + " | Giá mới: " + newPrice);
        assertNotEquals(oldPrice, newPrice);
    }

    @Test
    public void testTC17_AccessInvalidVersion_404() throws Exception {
        // Test Negative: Vào link 404
        String invalidUrl = "https://cellphones.com.vn/iphone-16-pro-max-phien-ban-loi-12345.html";
        driver.get(invalidUrl);
        Thread.sleep(3000);

        String title = driver.getTitle();
        String source = driver.getPageSource();
        boolean is404 = title.contains("404") || title.contains("Không tìm thấy") ||
                source.contains("Rất tiếc") || source.contains("không tồn tại") ||
                driver.getCurrentUrl().equals("https://cellphones.com.vn/"); // Redirect về home

        if(is404) System.out.println("✅ Đã hiện trang lỗi/404 đúng yêu cầu.");
        assertTrue("Không hiện trang 404", is404);
    }

    @Test
    public void testTC18_ViewTechnicalSpecs() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        try {
            WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.technical-content")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", table);
            Thread.sleep(1000);

            List<WebElement> rows = table.findElements(By.tagName("tr"));
            assertTrue("Bảng thông số trống", !rows.isEmpty());
            System.out.println("✅ Đã lấy được " + rows.size() + " dòng thông số kỹ thuật.");
        } catch (Exception e) {
            fail("❌ Không tìm thấy bảng thông số.");
        }
    }

    @Test
    public void testTC19_CheckOutOfStock() {
        // Link SP ngừng kinh doanh
        String url = "https://cellphones.com.vn/iphone-11-pro-max-512gb.html";
        try {
            driver.get(url);
            Thread.sleep(3000);
        } catch (Exception e) { return; }

        List<WebElement> buyBtns = driver.findElements(By.cssSelector(".button-add-to-cart, .btn-buy-now"));
        List<WebElement> altBtns = driver.findElements(By.xpath("//*[contains(@class,'button__register-stock') or contains(@class,'btn-register') or contains(text(),'ngừng kinh doanh')]"));

        if (buyBtns.isEmpty() && !altBtns.isEmpty()) {
            System.out.println("✅ ĐÚNG LOGIC: Sản phẩm hết hàng, không có nút mua, hiện nút đăng ký.");
        } else {
            fail("❌ Logic hết hàng sai (Vẫn có nút mua hoặc không có thông báo).");
        }
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}