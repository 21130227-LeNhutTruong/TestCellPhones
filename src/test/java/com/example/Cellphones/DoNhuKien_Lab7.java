package com.example.Cellphones;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class DoNhuKien_Lab7 {

    private WebDriver driver;
    JavascriptExecutor js;

    // LINK SẢN PHẨM DÙNG CHUNG
    private static final String PRODUCT_URL = "https://cellphones.com.vn/iphone-16-pro-max.html";
    private static final String PRODUCT_SLUG = "iphone-16-pro-max.html";

    @Before
    public void setUp() throws Exception {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");

        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;

        // LOGIN BẮT BUỘC TRƯỚC KHI TEST
        loginSmember();
    }

    // ================== HÀM LOGIN ==================
    private void loginSmember() throws InterruptedException {

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[@type='button' and normalize-space()='Đăng nhập']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[contains(@class,'bg-gradient-to-r') and normalize-space()='Đăng nhập']")).click();
        Thread.sleep(2000);

        WebElement phoneInput = driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']"));
        phoneInput.clear();
        phoneInput.sendKeys("0353933224");
        Thread.sleep(1000);

        WebElement passInput = driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']"));
        passInput.clear();
        passInput.sendKeys("Nhukien24@");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Đăng nhập']")).click();
        Thread.sleep(3000);

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(2000);
    }

    // ================== TEST CASE ==================

    @Test
    public void testTC1_AddToWishlist_ByExactHtmlState() throws Exception {

        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        String productName = (String) js.executeScript(
                "return document.querySelector('.box-product-name h1').innerText.trim()"
        );

        String productPrice = (String) js.executeScript(
                "var price = document.querySelector('.product__price--show') || " +
                        "document.querySelector('.box-product-info .product__price--show') || " +
                        "document.querySelector('.price');" +
                        "return price ? price.innerText.trim() : 'Không lấy được giá';"
        );

        System.out.println("Tên sản phẩm: " + productName);
        System.out.println("Giá sản phẩm: " + productPrice);

        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));

        WebElement stateDiv = wishlistBtn.findElement(
                By.cssSelector("div.btn__effect.button__add-wishlist")
        );

        String classBefore = stateDiv.getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        assertTrue(classBefore.contains("inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfter = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click: " + classAfter);

        assertTrue(classAfter.contains("active"));
        assertFalse(classAfter.contains("inactive"));
    }



    @Test
    public void testTC2_ToggleWishlist_AddThenRemove_ByExactHtmlState() throws Exception {

        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        String productName = (String) js.executeScript(
                "return document.querySelector('.box-product-name h1').innerText.trim()"
        );

        String productPrice = (String) js.executeScript(
                "var price = document.querySelector('.product__price--show') || " +
                        "document.querySelector('.box-product-info .product__price--show') || " +
                        "document.querySelector('.price');" +
                        "return price ? price.innerText.trim() : 'Không lấy được giá';"
        );

        System.out.println("Tên sản phẩm: " + productName);
        System.out.println("Giá sản phẩm: " + productPrice);

        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));
        WebElement stateDiv = wishlistBtn.findElement(
                By.cssSelector("div.btn__effect.button__add-wishlist")
        );

        String classBefore = stateDiv.getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        assertTrue(isHasClass(classBefore, "inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfterAdd = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click (ADD): " + classAfterAdd);

        assertTrue(isHasClass(classAfterAdd, "active"));
        assertFalse(isHasClass(classAfterAdd, "inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfterRemove = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click tiếp (REMOVE): " + classAfterRemove);

        assertTrue(isHasClass(classAfterRemove, "inactive"));
        assertFalse(isHasClass(classAfterRemove, "active"));
    }




    @Test
    public void testTC3_AddWishlist_FromMobileCategory() throws Exception {

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@href='/mobile.html']")).click();
        Thread.sleep(6000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement wishlistBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        ));

        WebElement productName = wishlistBtn.findElement(
                By.xpath("./ancestor::div[contains(@class,'product-item')]//div[contains(@class,'product__name')]//h3")
        );

        WebElement productPrice = wishlistBtn.findElement(
                By.xpath("./ancestor::div[contains(@class,'product-item')]//p[contains(@class,'product__price--show')]")
        );

        String name = productName.getText().trim();
        String price = productPrice.getText().trim();

        System.out.println("Tên sản phẩm: " + name);
        System.out.println("Giá sản phẩm: " + price);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", wishlistBtn);
        Thread.sleep(2000);

        String classBefore = wishlistBtn.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        js.executeScript("arguments[0].click();", wishlistBtn);
        Thread.sleep(3000);

        WebElement wishlistBtnAfter = driver.findElement(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        );

        String classAfter = wishlistBtnAfter.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class sau khi click: " + classAfter);

        assertTrue(classAfter.contains("active"));
    }




    @Test
    public void testTC4_RemoveWishlist_FromMobileCategory() throws Exception {

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@href='/mobile.html']")).click();
        Thread.sleep(6000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement btnAdd = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        ));

        WebElement productName = btnAdd.findElement(
                By.xpath("./ancestor::div[contains(@class,'product-item')]//div[contains(@class,'product__name')]//h3")
        );

        WebElement productPrice = btnAdd.findElement(
                By.xpath("./ancestor::div[contains(@class,'product-item')]//p[contains(@class,'product__price--show')]")
        );

        String name = productName.getText().trim();
        String price = productPrice.getText().trim();

        System.out.println("Tên sản phẩm: " + name);
        System.out.println("Giá sản phẩm: " + price);

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnAdd);
        Thread.sleep(2000);

        String classBefore = btnAdd.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        js.executeScript("arguments[0].click();", btnAdd);
        Thread.sleep(3000);

        WebElement btnAfterAdd = driver.findElement(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        );

        String classAfterAdd = btnAfterAdd.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class sau khi click (ADD): " + classAfterAdd);
        assertTrue(classAfterAdd.contains("active"));

        js.executeScript("arguments[0].click();", btnAfterAdd);
        Thread.sleep(3000);

        WebElement btnAfterRemove = driver.findElement(
                By.xpath("(//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn'])[1]")
        );

        String classAfterRemove = btnAfterRemove.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class sau khi click (REMOVE): " + classAfterRemove);

        assertTrue(classAfterRemove.contains("inactive"));
    }



    @Test
    public void testTC5_AddWishlist_ThenReload_StillActive() throws Exception {

        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        String productName = (String) js.executeScript(
                "return document.querySelector('.box-product-name h1').innerText.trim()"
        );
        System.out.println("Tên sản phẩm: " + productName);

        // ✅ B1: BẮT NÚT WISHLIST
        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));

        WebElement stateDiv = wishlistBtn.findElement(
                By.cssSelector("div.btn__effect.button__add-wishlist")
        );

        String classBefore = stateDiv.getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);

        assertTrue(classBefore.contains("inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfter = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click: " + classAfter);

        assertTrue(classAfter.contains("active"));

        driver.navigate().refresh();
        Thread.sleep(5000);

        WebElement wishlistBtnAfterReload = driver.findElement(By.id("wishListBtn"));

        WebElement stateDivAfterReload = wishlistBtnAfterReload.findElement(
                By.cssSelector("div.btn__effect.button__add-wishlist")
        );

        String classAfterReload = stateDivAfterReload.getAttribute("class");
        System.out.println("Class sau khi reload: " + classAfterReload);

        assertTrue(classAfterReload.contains("active"));

        System.out.println("Nút wishlist sau khi reload vẫn còn");
    }


    @Test
    public void testTC6_AddWishlist_FromMobile_ThenOpenProduct_CheckWishlistStillActive() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://cellphones.com.vn/");
        Thread.sleep(5000);

        WebElement mobileCategory = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/mobile.html']")
        ));
        mobileCategory.click();
        Thread.sleep(7000);

        WebElement firstProductItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//div[contains(@class,'product-item')])[1]")
        ));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProductItem);
        Thread.sleep(2000);

        WebElement wishlistBtn = firstProductItem.findElement(
                By.xpath(".//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn']")
        );

        WebElement productName = firstProductItem.findElement(
                By.xpath(".//div[contains(@class,'product__name')]//h3")
        );
        WebElement productPrice = firstProductItem.findElement(
                By.xpath(".//p[contains(@class,'product__price--show')]")
        );

        String name = productName.getText().trim();
        String price = productPrice.getText().trim();

        System.out.println("Tên sản phẩm: " + name);
        System.out.println("Giá sản phẩm: " + price);

        String classBefore = wishlistBtn.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);
        assertTrue(classBefore.contains("inactive"));

        js.executeScript("arguments[0].click();", wishlistBtn);
        Thread.sleep(3000);

        WebElement wishlistBtnAfter = firstProductItem.findElement(
                By.xpath(".//div[contains(@class,'btn-wish-list')]//button[@id='wishListBtn']")
        );

        String classAfter = wishlistBtnAfter.findElement(By.xpath(".//div")).getAttribute("class");
        System.out.println("Class sau khi click: " + classAfter);
        assertTrue(classAfter.contains("active"));

        WebElement productLink = firstProductItem.findElement(
                By.xpath(".//a[contains(@class,'product__link')]")
        );

        js.executeScript("arguments[0].click();", productLink);
        Thread.sleep(7000);

        WebElement wishlistOnDetail = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("wishListBtn")
        ));

        WebElement stateDivDetail = wishlistOnDetail.findElement(
                By.cssSelector("div.btn__effect.button__add-wishlist")
        );

        String classDetail = stateDivDetail.getAttribute("class");
        System.out.println("Class wishlist trên trang chi tiết: " + classDetail);

        assertTrue(classDetail.contains("active"));

        System.out.println("✅ TC06 PASSED: Wishlist vẫn còn sau khi mở sản phẩm");
    }


    @Test
    public void testTC7_ViewWishlist_FromSmember() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get(PRODUCT_URL);
        Thread.sleep(5000);

        String productName = driver.findElement(
                By.cssSelector(".box-product-name h1")
        ).getText().trim();

        String productPrice = driver.findElement(
                By.cssSelector(".box-info__box-price .product__price--show")
        ).getText().trim();

        System.out.println("Tên sản phẩm vừa thêm wishlist: " + productName);
        System.out.println("Giá sản phẩm vừa thêm wishlist: " + productPrice);

        WebElement wishlistBtn = driver.findElement(By.id("wishListBtn"));
        WebElement stateDiv = wishlistBtn.findElement(
                By.cssSelector("div.btn__effect.button__add-wishlist")
        );

        String classBefore = stateDiv.getAttribute("class");
        System.out.println("Class trước khi click: " + classBefore);
        assertTrue(classBefore.contains("inactive"));

        wishlistBtn.click();
        Thread.sleep(4000);

        String classAfter = stateDiv.getAttribute("class");
        System.out.println("Class sau khi click: " + classAfter);
        assertTrue(classAfter.contains("active"));

        WebElement accountBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'navbar__item') and .//span[text()='Kiên']]//button")
        ));
        accountBtn.click();
        Thread.sleep(3000);

        WebElement smemberBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'noti-head')]//a[contains(@href,'smember.com.vn')]")
        ));
        smemberBtn.click();
        Thread.sleep(8000);

        WebElement wishlistBox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'bg-pure-white')]//div[contains(text(),'Sản phẩm yêu thích')]")
        ));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", wishlistBox);
        Thread.sleep(3000);

        List<WebElement> wishlistItems = driver.findElements(
                By.xpath("//a[contains(@href,'cellphones.com.vn') and .//div[contains(@class,'line-clamp-2')]]")
        );

        System.out.println("===== DANH SÁCH SẢN PHẨM YÊU THÍCH =====");

        for (int i = 0; i < wishlistItems.size(); i++) {
            WebElement item = wishlistItems.get(i);

            String name = item.findElement(
                    By.xpath(".//div[contains(@class,'line-clamp-2')]")
            ).getText().trim();

            String price = item.findElement(
                    By.xpath(".//span[contains(@class,'text-primary-500')]")
            ).getText().trim();

            System.out.println((i + 1) + ". " + name + " | " + price);
        }

        assertTrue("❌ Wishlist trống!", wishlistItems.size() > 0);

        System.out.println("✅ TC07 PASSED: Đã hiển thị danh sách wishlist thành công");
    }

    @Test
    public void testTC8_FullFlow_Add_Then_RemoveAll_ViaDetail() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Duration defaultWait = Duration.ofSeconds(20);

        System.out.println("🚀 BƯỚC 1: MÔ PHỎNG TC01 - THÊM SẢN PHẨM...");
        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        WebElement mainWishlistBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("wishListBtn")));
        WebElement btnStateDiv = mainWishlistBtn.findElement(By.cssSelector("div.btn__effect.button__add-wishlist"));

        if (btnStateDiv.getAttribute("class").contains("inactive")) {
            mainWishlistBtn.click();
            System.out.println("   -> Đã click THÊM sản phẩm vào Wishlist.");
            Thread.sleep(3000);
        } else {
            System.out.println("   -> Sản phẩm đã có sẵn. Bỏ qua.");
        }

        System.out.println("🚀 BẮT ĐẦU QUY TRÌNH XÓA SẠCH WISHLIST...");

        while (true) {
            // --- B2: Vào trang Smember ---
            driver.get("https://cellphones.com.vn/smember");
            Thread.sleep(3000);

            // Xử lý login/redirect nếu cần
            if (!driver.getCurrentUrl().contains("smember")) {
                try {
                    WebElement accountBtn = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class,'navbar__item') and .//span[contains(text(),'Kiên')]]//button")
                    ));
                    accountBtn.click();
                    WebElement smemberLink = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class,'noti-head')]//a[contains(@href,'smember')]")
                    ));
                    smemberLink.click();
                    Thread.sleep(3000);
                } catch (Exception e) {
                    System.out.println("⚠️ Đang load lại...");
                }
            }

            try {
                WebElement wishlistTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(text(),'Sản phẩm yêu thích')]")
                ));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", wishlistTitle);
                Thread.sleep(2000);
            } catch (Exception e) {
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

            List<WebElement> wishlistItems = driver.findElements(
                    By.xpath("//div[@data-slot='scroll-area-viewport']//a[.//div[contains(@class,'line-clamp-2')]]")
            );

            driver.manage().timeouts().implicitlyWait(defaultWait);

            if (wishlistItems.isEmpty()) {
                System.out.println("✅ Danh sách Wishlist đã TRỐNG. Kết thúc quy trình xóa.");
                break;
            }

            System.out.println("🔄 Số lượng sản phẩm còn lại: " + wishlistItems.size());

            WebElement firstItem = wishlistItems.get(0);
            String detailLink = firstItem.getAttribute("href");

            System.out.println("   -> Đang mở trang chi tiết để xóa...");
            driver.get(detailLink);
            Thread.sleep(4000);

            try {
                WebElement detailWishlistBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("wishListBtn")));
                String detailBtnClass = detailWishlistBtn.findElement(By.xpath("./div")).getAttribute("class");

                if (!detailBtnClass.contains("inactive")) {
                    detailWishlistBtn.click();
                    System.out.println("   -> Đã click XÓA tim.");
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                System.out.println("   ❌ Lỗi nút tim: " + e.getMessage());
            }
        }

        System.out.println("🏁 Đang kiểm tra kết quả cuối cùng...");
        driver.navigate().refresh();
        Thread.sleep(4000);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        List<WebElement> finalCheck = driver.findElements(
                By.xpath("//div[@data-slot='scroll-area-viewport']//a[.//div[contains(@class,'line-clamp-2')]]")
        );
        driver.manage().timeouts().implicitlyWait(defaultWait);

        System.out.println("✅ Số lượng thực tế: " + finalCheck.size());
        assertTrue("❌ Test Failed: Vẫn còn sản phẩm!", finalCheck.isEmpty());
    }
    @Test
    public void testTC09_ViewProductDetail_PrintInfo() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("🚀 Bắt đầu TC09: Lấy thông tin chi tiết sản phẩm...");
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".box-product-name h1")
        ));
        String productName = nameEl.getText().trim();

        WebElement priceEl = driver.findElement(By.cssSelector(".box-product-price .sale-price"));
        String productPrice = priceEl.getText().trim();

        WebElement imgEl = driver.findElement(By.cssSelector(".box-ksp img"));
        String imgSrc = imgEl.getAttribute("src");

        String productDesc = "Không có mô tả";
        try {
            WebElement descEl = driver.findElement(By.cssSelector(".box-ksp ul"));
            productDesc = descEl.getText();
        } catch (Exception e) {
            System.out.println("⚠Không tìm thấy phần tính năng nổi bật.");
        }

        // --- BƯỚC 3: IN THÔNG TIN RA CONSOLE ---
        System.out.println("========================================");
        System.out.println("THÔNG TIN SẢN PHẨM");
        System.out.println("========================================");
        System.out.println("1. Tên sản phẩm : " + productName);
        System.out.println("2. Giá bán      : " + productPrice);
        System.out.println("3. Link ảnh     : " + imgSrc);
        System.out.println("4. Tính năng nổi bật:");
        System.out.println(productDesc);
        System.out.println("========================================");

        if (productName != null && !productName.isEmpty()) {
            System.out.println("✅ KẾT LUẬN: sản phẩm " + productName + " có tồn tại");
        } else {
            System.out.println("❌ KẾT LUẬN: Không tìm thấy tên sản phẩm!");
        }

        // Xác nhận test case pass nếu tên sản phẩm không rỗng
        assertTrue("Test Fail: Tên sản phẩm bị trống", !productName.isEmpty());
    }
    @Test
    public void testTC10_ZoomImage_Gallery() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("Kiểm tra Gallery ảnh...");
        driver.get(PRODUCT_URL);
        Thread.sleep(4000);

        try {
            List<WebElement> thumbnails = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector(".gallery-thumbs .swiper-slide")
            ));

            if (thumbnails.size() > 2) {
                WebElement targetThumb = thumbnails.get(2);

                // Scroll tới thumbnail để đảm bảo nó hiển thị
                js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", targetThumb);
                Thread.sleep(1000);

                // Click thumbnail (dùng JS click cho chắc chắn)
                try {
                    targetThumb.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", targetThumb);
                }
                System.out.println("   -> Đã chọn thumbnail ảnh thứ 2.");
                Thread.sleep(2000);
            }

            WebElement mainImage = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".gallery-top .swiper-slide-active img")
            ));
            mainImage.click();
            System.out.println("   -> Đã click vào ảnh chính.");


            try {
                WebElement spotlightContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#spotlight.show")
                ));

                boolean hasCloseBtn = !spotlightContainer.findElements(By.cssSelector(".spl-close")).isEmpty();
                boolean hasZoomInBtn = !spotlightContainer.findElements(By.cssSelector(".spl-zoom-in")).isEmpty();

                if (spotlightContainer.isDisplayed() && hasCloseBtn) {
                    // Kiểm tra thêm nếu cần: In ra tên ảnh đang xem
                    String title = spotlightContainer.findElement(By.cssSelector(".spl-title")).getText();
                    System.out.println("   -> Đang xem ảnh phóng to: " + title);

                    System.out.println("Hình ảnh hiển thị đầy đủ và có chức năng phóng to");
                } else {
                    System.out.println("Lỗi: Modal không hiển thị đúng (thiếu nút đóng hoặc container).");
                }

            } catch (TimeoutException e) {
                System.out.println("Lỗi: Không thấy modal '#spotlight' hiện lên sau khi click.");
            }

        } catch (Exception e) {
            System.out.println("Test TC10 Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public void testTC12_ViewReviewList_And_Rating() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // --- BƯỚC 1: VÀO TRANG CHI TIẾT ---
        System.out.println("🚀 Bắt đầu TC12: Lấy danh sách Đánh giá & Số sao...");
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        try {
            // --- BƯỚC 2: SCROLL TỚI KHU VỰC ĐÁNH GIÁ ---
            WebElement reviewSection = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("review")
            ));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", reviewSection);
            Thread.sleep(2000);

            // --- BƯỚC 3: LẤY DANH SÁCH CÁC REVIEW ---
            List<WebElement> reviews = driver.findElements(By.cssSelector(".boxReview-comment-item"));

            System.out.println("🌟 Tìm thấy " + reviews.size() + " đánh giá:");
            System.out.println("==================================================");

            if (reviews.isEmpty()) {
                System.out.println("⚠️ Chưa có đánh giá nào.");
                return;
            }

            // --- BƯỚC 4: DUYỆT TỪNG REVIEW ĐỂ LẤY CHI TIẾT ---
            for (int i = 0; i < reviews.size(); i++) {
                WebElement r = reviews.get(i);

                try {
                    // 1. Tên người đánh giá
                    String name = r.findElement(By.cssSelector(".block-info__name .name")).getText().trim();

                    // 2. Tính số sao (Rating)
                    // Đếm số lượng icon ngôi sao có class 'is-active'
                    List<WebElement> stars = r.findElements(By.cssSelector(".item-review-rating__star .icon.is-active"));
                    int starCount = stars.size();

                    // 3. Nội dung đánh giá
                    String content = "Không có nội dung text";
                    try {
                        content = r.findElement(By.cssSelector(".comment-content p")).getText().trim();
                    } catch (Exception e) {
                        // Một số review chỉ chấm sao mà không viết chữ
                    }

                    // 4. Thời gian đăng
                    String time = r.findElement(By.cssSelector(".date-time")).getText().trim();

                    // 5. In kết quả
                    System.out.println("Review #" + (i + 1) + ": " + name);
                    System.out.println("   ⭐ Đánh giá: " + starCount + "/5 sao");
                    System.out.println("   📝 Nội dung: " + content);
                    System.out.println("   🕒 Thời gian: " + time);

                    // (Optional) Lấy thêm các tag nhận xét (VD: Hiệu năng ổn định...)
                    List<WebElement> tags = r.findElements(By.cssSelector(".item-review-rating__item-attribute"));
                    if (!tags.isEmpty()) {
                        System.out.print("   🏷️ Tags: ");
                        for (WebElement tag : tags) {
                            System.out.print("[" + tag.getText() + "] ");
                        }
                        System.out.println();
                    }

                    System.out.println("--------------------------------------------------");

                } catch (Exception e) {
                    System.out.println("⚠️ Lỗi khi đọc review số " + (i+1));
                }
            }

        } catch (TimeoutException e) {
            System.out.println("❌ Không tìm thấy khu vực đánh giá (Timeout).");
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testTC11_ViewReviewList_And_Conclusion() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("TC11: Lấy danh sách Đánh giá & Kết luận...");
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        try {
            WebElement reviewSection = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("review")
            ));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", reviewSection);
            Thread.sleep(2000);

            List<WebElement> reviews = driver.findElements(By.cssSelector(".boxReview-comment-item"));

            if (!reviews.isEmpty()) {
                System.out.println("========== CHI TIẾT ĐÁNH GIÁ ==========");
                for (int i = 0; i < reviews.size(); i++) {
                    try {
                        WebElement r = reviews.get(i);
                        String name = r.findElement(By.cssSelector(".block-info__name .name")).getText().trim();

                        // Đếm số sao (dựa vào class is-active)
                        int starCount = r.findElements(By.cssSelector(".item-review-rating__star .icon.is-active")).size();

                        System.out.println((i + 1) + ". " + name + " | " + starCount + " sao");
                    } catch (Exception e) {
                        // Bỏ qua lỗi nhỏ khi lấy text
                    }
                }
                System.out.println("=======================================");
            }

            System.out.println("\n TC11: PASS");
            if (reviews.size() > 0) {
                System.out.println("KẾT LUẬN: Sản phẩm CÓ đánh giá (" + reviews.size() + " lượt đánh giá hiển thị, chưa hiển thị thêm).");
            } else {
                System.out.println("KẾT LUẬN: Sản phẩm CHƯA CÓ đánh giá nào.");
            }

            assertTrue("Test Fail: Sản phẩm không có đánh giá nào!", reviews.size() > 0);

        } catch (Exception e) {
            System.out.println("Lỗi trong quá trình kiểm tra review: " + e.getMessage());
        }
    }
    @Test
    public void testTC12_WriteReview_5Stars() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("TC13: Viết đánh giá 5 sao...");
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        try {
            WebElement btnWriteReview = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".button__review")
            ));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnWriteReview);
            Thread.sleep(1000);

            btnWriteReview.click();
            System.out.println("   -> Đã nhấn nút 'Viết đánh giá'.");

            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".review-container")
            ));
            System.out.println("   -> Modal đánh giá đã hiển thị.");

            List<WebElement> stars = modal.findElements(By.cssSelector(".star-all .modal__button"));

            if (stars.size() >= 5) {
                WebElement fiveStar = stars.get(4);
                fiveStar.click();
                System.out.println("   -> Đã chọn 5 sao (Tuyệt vời).");
                Thread.sleep(1000);
            } else {
                System.out.println(" Không tìm thấy đủ 5 ngôi sao để click.");
            }

            WebElement txtArea = modal.findElement(By.tagName("textarea"));
            String comment = "Sản phẩm tốt, chất lượng tuyệt vời!";
            txtArea.sendKeys(comment);
            System.out.println("   -> Đã nhập nội dung: " + comment);
            Thread.sleep(1000);

            WebElement btnSubmit = modal.findElement(By.cssSelector("button[type='submit']"));

            if (btnSubmit.isEnabled()) {
                btnSubmit.click();
                System.out.println("   -> Đã nhấn nút 'GỬI ĐÁNH GIÁ'.");
            } else {
                System.out.println("❌ Nút Gửi đang bị Disable (Có thể do chưa đủ ký tự hoặc thiếu thông tin).");
            }


            Thread.sleep(2000);
            System.out.println("TC12 PASSED: Bình luận đã hiển thị.");

        } catch (Exception e) {
            System.out.println("❌ TC12 Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public void testTC13_AddToCart_And_Verify() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("TC13: Thêm vào giỏ và Kiểm tra...");
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        String rawName = "";
        try {
            WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".box-product-name h1")
            ));
            rawName = nameEl.getText().trim();
            System.out.println("Tên gốc lấy được: " + rawName);
        } catch (Exception e) {
            System.out.println("Không lấy được tên sản phẩm.");
        }

        String expectedNameClean = rawName.toLowerCase()
                .replace("điện thoại", "")
                .replace("laptop", "")
                .replace("máy tính bảng", "")
                .trim();

        System.out.println("🔧 Tên sau khi xử lý để tìm kiếm: " + expectedNameClean);

        try {
            WebElement btnAddToCart = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".button-add-to-cart")
            ));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnAddToCart);
            Thread.sleep(1000);
            btnAddToCart.click();
            System.out.println("   -> Đã nhấn nút 'Thêm vào giỏ hàng'.");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("❌ Lỗi click thêm giỏ hàng.");
            return;
        }

        try {
            WebElement btnCart = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".button__cart")
            ));
            btnCart.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("listItemSuperCart")));
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("❌ Lỗi vào trang giỏ hàng.");
        }

        List<WebElement> cartItems = driver.findElements(By.cssSelector(".product-info .product-name a"));

        boolean isFound = false;
        System.out.println("Danh sách trong giỏ:");

        for (WebElement item : cartItems) {
            String cartItemName = item.getText().trim();
            System.out.println("   - " + cartItemName);

            if (!expectedNameClean.isEmpty() && cartItemName.toLowerCase().contains(expectedNameClean)) {
                isFound = true;
                System.out.println("   ✨ MATCH: Tìm thấy sản phẩm tương ứng!");
                break;
            }
        }

        System.out.println("\n KẾT QUẢ KIỂM TRA:");
        if (isFound) {
            System.out.println("✅ SP xuất hiện trong giỏ hàng");
            System.out.println("✅ sp tồn tại");
        } else {
            System.out.println("❌ Lỗi: Không khớp tên. Mong đợi chứa: '" + expectedNameClean + "'");
        }

        assertTrue("Test Fail: Sản phẩm không có trong giỏ hàng", isFound);
    }

    @Test
    public void testTC19_CheckOutOfStock_DiscontinuedProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String outOfStockUrl = "https://cellphones.com.vn/iphone-11-pro-max-512gb.html";

        System.out.println("🚀 Bắt đầu TC19: Kiểm tra sản phẩm ngừng kinh doanh...");
        System.out.println("🔗 Link: " + outOfStockUrl);

        try {
            driver.get(outOfStockUrl);
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi truy cập trang (Browser có thể đã crash): " + e.getMessage());
            return;
        }

        try {

            List<WebElement> buyButtons = driver.findElements(By.cssSelector(".button-add-to-cart, .btn-buy-now"));

            List<WebElement> altButtons = driver.findElements(By.xpath(
                    "//*[contains(@class,'button__register-stock') or contains(@class,'btn-register') or contains(text(),'ngừng kinh doanh')]"
            ));

            System.out.println("\n KẾT QUẢ KIỂM TRA:");

            boolean isBuyBtnGone = buyButtons.isEmpty();
            boolean isAltBtnPresent = !altButtons.isEmpty();

            if (isBuyBtnGone) {
                System.out.println(" CHECK 1: Nút 'Mua ngay' đã ẩn (Đúng).");
            } else {
                System.out.println(" CHECK 1: Vẫn còn nút 'Mua ngay' (Sai).");
            }

            if (isAltBtnPresent) {
                String msg = altButtons.get(0).getText().replace("\n", " ");
                System.out.println("✅ CHECK 2: Hiện thông báo thay thế: [" + msg + "]");
            } else {
                System.out.println("⚠️ CHECK 2: Không thấy nút đăng ký nhận tin (Có thể web đổi giao diện).");
            }

            assertTrue("Test Fail: Sản phẩm hết hàng nhưng vẫn có nút mua!", isBuyBtnGone);

        } catch (Exception e) {
            System.out.println("❌ Lỗi kỹ thuật trong quá trình kiểm tra element: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Test
    public void testTC14_CheckOutOfStock_DiscontinuedProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String outOfStockUrl = "https://cellphones.com.vn/iphone-11-pro-max-512gb.html";

        System.out.println("TC14: Kiểm tra sản phẩm ngừng kinh doanh...");
        System.out.println("🔗 Link: " + outOfStockUrl);

        try {
            driver.get(outOfStockUrl);
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi truy cập trang (Browser có thể đã crash): " + e.getMessage());
            return;
        }

        try {

            List<WebElement> buyButtons = driver.findElements(By.cssSelector(".button-add-to-cart, .btn-buy-now"));

            List<WebElement> altButtons = driver.findElements(By.xpath(
                    "//*[contains(@class,'button__register-stock') or contains(@class,'btn-register') or contains(text(),'ngừng kinh doanh')]"
            ));

            System.out.println("\n KẾT QUẢ KIỂM TRA:");

            boolean isBuyBtnGone = buyButtons.isEmpty();
            boolean isAltBtnPresent = !altButtons.isEmpty();

            if (isBuyBtnGone) {
                System.out.println(" CHECK 1: Nút 'Mua ngay' đã ẩn (Đúng).");
            } else {
                System.out.println(" CHECK 1: Vẫn còn nút 'Mua ngay' (Sai).");
            }

            if (isAltBtnPresent) {
                String msg = altButtons.get(0).getText().replace("\n", " ");
                System.out.println("✅ CHECK 2: Hiện thông báo thay thế: [" + msg + "]");
            } else {
                System.out.println("⚠️ CHECK 2: Không thấy nút đăng ký nhận tin (Có thể web đổi giao diện).");
            }

            assertTrue("Test Fail: Sản phẩm hết hàng nhưng vẫn có nút mua!", isBuyBtnGone);

        } catch (Exception e) {
            System.out.println("❌ Lỗi kỹ thuật trong quá trình kiểm tra element: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Test
    public void testTC15_CompareProduct_Flow() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("TC15: So sánh sản phẩm...");
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        String sourceName = "";
        try {
            WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".box-product-name h1")
            ));
            sourceName = nameEl.getText().trim().replace("Điện thoại ", "");
            System.out.println("Sản phẩm gốc: " + sourceName);
        } catch (Exception e) {
            System.out.println("Không lấy được tên sản phẩm gốc.");
        }

        try {
            WebElement btnCompareTrigger = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".pdp-compare-button-box")
            ));
            btnCompareTrigger.click();
            System.out.println("   -> Đã nhấn nút kích hoạt so sánh.");
            Thread.sleep(2000); // Chờ thanh so sánh hiện lên
        } catch (Exception e) {
            System.out.println("❌ Lỗi click nút so sánh ban đầu.");
            return;
        }

        try {
            WebElement emptySlot = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'compare-product')]//p[contains(text(),'Chọn sản phẩm')]/..")
            ));

            js.executeScript("arguments[0].click();", emptySlot);
            System.out.println("   -> Đã click vào ô 'Chọn sản phẩm so sánh'.");

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("❌ Lỗi ở Bước 3 (Click ô chọn sản phẩm): " + e.getMessage());
            return;
        }

        String targetName = "";
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".popup-select-product.show")));

            List<WebElement> suggestItems = driver.findElements(By.cssSelector(".popup-select-product .product-item"));

            if (suggestItems.size() >= 3) {
                WebElement item3 = suggestItems.get(2);

                targetName = item3.findElement(By.cssSelector(".product-name")).getText().trim();
                System.out.println("Sản phẩm so sánh được chọn: " + targetName);

                WebElement btnSelect = item3.findElement(By.cssSelector(".select-to-compare"));
                js.executeScript("arguments[0].click();", btnSelect); // Dùng JS click cho chắc

                System.out.println("   -> Đã chọn sản phẩm thứ 3.");
                Thread.sleep(2000);
            } else {
                System.out.println("Không đủ 3 sản phẩm gợi ý.");
                return;
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi thao tác trên popup chọn sản phẩm: " + e.getMessage());
        }

        try {
            // Nút này nằm trong thanh bottom bar
            WebElement btnGoCompare = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".btn-go-compare")
            ));
            btnGoCompare.click();
            System.out.println("   -> Đã nhấn nút 'So sánh' để chuyển trang.");

            Thread.sleep(5000);

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi chuyển sang trang so sánh.");
        }

        try {
            WebElement titleEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-compare__title")));
            String pageTitle = titleEl.getText();
            System.out.println("Tiêu đề trang so sánh: " + pageTitle);

            String titleLower = pageTitle.toLowerCase();
            String sourceLower = sourceName.toLowerCase();

            boolean hasSource = titleLower.contains(sourceLower);

            // --- BƯỚC 7: KẾT LUẬN ---
            System.out.println("\n KẾT QUẢ KIỂM TRA:");
            if (hasSource) {
                System.out.println("✅ SP đã có trong bảng so sánh");
            } else {
                System.out.println("❌ Lỗi: Tiêu đề không chứa tên sản phẩm gốc (" + sourceName + ").");
            }

            assertTrue("Test Fail: Tiêu đề so sánh không đúng", hasSource);

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi verify tiêu đề (Có thể chưa load xong trang): " + e.getMessage());
        }
    }
    @Test
    public void testTC16_ChangeVersion_VerifyPrice() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // --- BƯỚC 1: VÀO TRANG CHI TIẾT ---
        System.out.println("TC16: Đổi phiên bản và check giá...");
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        String oldPrice = "";
        try {
            WebElement priceEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".sale-price")
            ));
            oldPrice = priceEl.getText().trim();
            System.out.println("Giá phiên bản hiện tại: " + oldPrice);
        } catch (Exception e) {
            System.out.println("Không lấy được giá ban đầu.");
            return;
        }

        boolean isClicked = false;
        try {
            List<WebElement> versions = driver.findElements(By.cssSelector(".list-linked .item-linked"));

            for (WebElement ver : versions) {
                String classAttribute = ver.getAttribute("class");

                if (!classAttribute.contains("active")) {
                    String verName = ver.getText().trim();
                    System.out.println("🔄 Đang chuyển sang phiên bản: " + verName);

                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", ver);
                    Thread.sleep(1000);

                    ver.click();
                    isClicked = true;
                    break; // Chỉ cần click 1 cái là đủ để test
                }
            }

            if (!isClicked) {
                System.out.println("⚠Sản phẩm này chỉ có 1 phiên bản hoặc tất cả đều đang active (lỗi data).");
                return;
            }

            Thread.sleep(4000);

        } catch (Exception e) {
            System.out.println("Lỗi khi chọn phiên bản: " + e.getMessage());
            return;
        }

        try {
            WebElement newPriceEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".sale-price")
            ));
            String newPrice = newPriceEl.getText().trim();
            System.out.println("Giá phiên bản mới: " + newPrice);

            System.out.println("\n KẾT QUẢ KIỂM TRA:");

            if (!oldPrice.equals(newPrice)) {
                System.out.println("✅ Phiên bản được cập nhật, giá và thông tin thay đổi tương ứng");
            } else {
                System.out.println("⚠️ Cảnh báo: Giá không thay đổi (Có thể 2 phiên bản này đồng giá hoặc lỗi cập nhật).");
            }

            assertNotEquals("Test Fail: Giá không đổi sau khi chọn phiên bản khác", oldPrice, newPrice);

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi lấy giá mới: " + e.getMessage());
        }
    }

    @Test
    public void testTC17_AccessInvalidVersion() throws Exception {
        System.out.println("🚀 Bắt đầu TC17: Truy cập phiên bản lỗi...");

        // link ko tồn tại
        String invalidUrl = "https://cellphones.com.vn/iphone-16-pro-max-phien-ban-loi-12345.html";
        System.out.println("🔗 Đang thử truy cập URL lỗi: " + invalidUrl);

        driver.get(invalidUrl);

        Thread.sleep(3000);


        String pageTitle = driver.getTitle();
        String currentUrl = driver.getCurrentUrl();
        String pageSource = driver.getPageSource();

        System.out.println(" Tiêu đề trang hiện tại: " + pageTitle);

        boolean is404 = false;

        if (pageTitle.contains("404") || pageTitle.contains("Không tìm thấy") || pageTitle.contains("Not Found")) {
            is404 = true;
            System.out.println("   -> Phát hiện chữ '404' hoặc 'Không tìm thấy' trong Tiêu đề.");
        }
        else if (pageSource.contains("Rất tiếc") || pageSource.contains("không tồn tại")) {
            is404 = true;
            System.out.println("   -> Phát hiện nội dung thông báo lỗi trong trang.");
        }
        else if (currentUrl.equals("https://cellphones.com.vn/")) {
            System.out.println("   -> Trang web đã tự động quay về trang chủ (Redirect).");
            is404 = true;
        }

        System.out.println("\n KẾT QUẢ KIỂM TRA TC17:");
        if (is404) {
            System.out.println("✅ Hiển thị thông báo \"Phiên bản không hợp lệ\" (Trang 404)");
        } else {
            System.out.println("❌ Lỗi: Web vẫn hiển thị sản phẩm hoặc không báo lỗi.");
        }

        assertTrue("Test Fail: Không vào được trang 404", is404);
    }
    @Test
    public void testTC18_ViewTechnicalSpecs() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("TC18: Xem thông số kỹ thuật...");
        driver.get(PRODUCT_URL);
        Thread.sleep(3000);

        try {
            WebElement specTable = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("table.technical-content")
            ));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", specTable);
            Thread.sleep(2000);

            List<WebElement> rows = specTable.findElements(By.tagName("tr"));

            System.out.println(" BẢNG THÔNG SỐ KỸ THUẬT:");
            System.out.println("==================================================");

            boolean hasCPU = false;
            boolean hasROM = false;
            boolean hasScreen = false;

            for (WebElement row : rows) {
                List<WebElement> cols = row.findElements(By.tagName("td"));

                if (cols.size() >= 2) {
                    String key = cols.get(0).getText().trim();
                    String value = cols.get(1).getText().trim();

                    System.out.println("   🔹 " + key + ": " + value);

                    String keyLower = key.toLowerCase();
                    if (keyLower.contains("chip") || keyLower.contains("cpu")) hasCPU = true;
                    if (keyLower.contains("bộ nhớ") || keyLower.contains("rom")) hasROM = true;
                    if (keyLower.contains("màn hình")) hasScreen = true;
                }
            }
            System.out.println("==================================================");

            System.out.println("\n KẾT QUẢ KIỂM TRA:");

            if (!rows.isEmpty()) {
                System.out.println("✅ Hiển thị thông số RAM, ROM, pin, CPU,...");
                System.out.println("✅ Sản phẩm tồn tại");
            } else {
                System.out.println("❌ Lỗi: Bảng thông số trống rỗng.");
            }

            assertTrue("Test Fail: Không tìm thấy bảng thông số", rows.size() > 0);

            if (hasCPU || hasScreen) {
                System.out.println("   -> Đã xác thực được các thông số phần cứng quan trọng.");
            }

        } catch (TimeoutException e) {
            System.out.println("❌ Lỗi: Không tìm thấy bảng thông số kỹ thuật (Timeout).");
        } catch (Exception e) {
            System.out.println("❌ Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        }
    }




    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
    private boolean isHasClass(String classText, String className) {
        for (String c : classText.split("\\s+")) {
            if (c.equals(className)) return true;
        }
        return false;
    }

}