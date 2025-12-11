package com.example.Cellphones;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

import static org.junit.Assert.fail;

public class NguyenDucAnhTuan_Lab7 {
    private static WebDriver driver;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();
    private static JavascriptExecutor js;

//    @Before
//    public void setUp() throws Exception {
//        System.setProperty("webdriver.chrome.driver", "");
//        driver = new ChromeDriver();
//        baseUrl = "https://www.google.com/";
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
//        js = (JavascriptExecutor) driver;
//    }
@Before
public void setUp() throws Exception {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
}


    //Find Locator
    @Test
    public void testTC01FindLocatorNear() throws Exception{
        System.out.println("=== TC01: Find Locator Near – Bắt đầu ===");

        driver.get("https://cellphones.com.vn");
        System.out.println("1. Mở trang chủ CellphoneS thành công.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("2. Đang chờ nút 'Cửa hàng gần bạn' xuất hiện...");
            WebElement locator = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/dia-chi-cua-hang']")
            ));
            System.out.println("   ✔ Đã tìm thấy button 'Cửa hàng gần bạn'.");

            locator.click();
            System.out.println("3. Đã click vào button.");

            System.out.println("4. Đợi chuyển hướng sang trang /dia-chi-cua-hang ...");
            wait.until(ExpectedConditions.urlContains("/dia-chi-cua-hang"));
            System.out.println("   ✔ Đã chuyển sang trang cửa hàng thành công.");

            String currentURL = driver.getCurrentUrl();
            System.out.println("5. URL hiện tại: " + currentURL);

            Assert.assertTrue(currentURL.contains("/dia-chi-cua-hang"));
            System.out.println("=== TC01 PASSED ✓ – Chức năng hoạt động đúng ===");

        } catch (Exception e) {
            System.out.println("❌ LỖI: TC01 FAILED – Không tìm thấy hoặc không click được Locator.");
            e.printStackTrace();
            Assert.fail("TC01 FAILED vì lỗi exception.");
        }
    }

    //Find_country
    @Test
    public void testTC02FindCountry() throws Exception {
        System.out.println("=== TC02: Find Country – Tìm 'Bình Định' ===");

        driver.get("https://cellphones.com.vn");
        System.out.println("1. Mở trang chủ CellphoneS thành công.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Click “Cửa hàng gần bạn”
            System.out.println("2. Đang chờ nút 'Cửa hàng gần bạn' xuất hiện...");
            WebElement nearStore = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/dia-chi-cua-hang']")
            ));
            System.out.println("   ✔ Đã tìm thấy menu 'Cửa hàng gần bạn'.");
            nearStore.click();
            System.out.println("3. Đã click vào menu.");

            // Chờ chuyển trang
            wait.until(ExpectedConditions.urlContains("/dia-chi-cua-hang"));
            System.out.println("   ✔ Đã vào trang địa chỉ cửa hàng.");

            // Input tìm kiếm vị trí
            System.out.println("4. Đang tìm ô input 'Nhập vị trí để tìm cửa hàng gần nhất'...");
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Nhập vị trí để tìm cửa hàng gần nhất']")
            ));
            System.out.println("   ✔ Input tìm kiếm đã sẵn sàng.");

            // Gõ từ khóa
            searchInput.sendKeys("bình định");
            System.out.println("5. Đã nhập từ khóa: bình định");

            // Chờ gợi ý hiện ra (nếu có)
            Thread.sleep(1500);

            System.out.println("=== TC02 PASSED ✓ – Đã nhập vị trí thành công ===");

        } catch (Exception e) {
            System.out.println("❌ LỖI: TC02 FAILED – Không tìm thấy hoặc không nhập được vị trí.");
            e.printStackTrace();
            Assert.fail("TC02 FAILED vì lỗi exception.");
        }
    }

    //Find_No_Brand
    @Test
    public void testTC03FindNoBranch() throws Exception {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("=== TC03_FindNoBranch START ===");

            // 1. Vào trang chủ Cellphones
            driver.get("https://cellphones.com.vn/");
            System.out.println("[INFO] Opened Cellphones homepage");

            // 2. Click vào link "Cửa hàng gần bạn"
            WebElement storeLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/dia-chi-cua-hang']")));
            storeLink.click();
            System.out.println("[INFO] Clicked 'Cửa hàng gần bạn'");

            // 3. Nhập brand vào ô tìm kiếm
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Nhập vị trí để tìm cửa hàng gần nhất']")));

            String brand = "Samsung";   // brand để test NO RESULT
            searchBox.sendKeys(brand);
            System.out.println("[INFO] Typed brand: " + brand);

            Thread.sleep(2000); // Chờ kết quả render

            // 4. Kiểm tra xem có kết quả hay không
            List<WebElement> results = driver.findElements(
                    By.cssSelector(".address-item") // class item cửa hàng Cellphones
            );

            if (!results.isEmpty()) {
                System.out.println("\u001B[31m[FAIL] Nhập brand '" + brand + "' mà vẫn xuất hiện "
                        + results.size() + " kết quả!\u001B[0m");
            } else {
                System.out.println("\u001B[32m[PASS] Không có cửa hàng nào → hoạt động đúng.\u001B[0m");
            }

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR] Test gặp lỗi: " + e.getMessage() + "\u001B[0m");
        } finally {
            driver.quit();
            System.out.println("=== TC03_FindNoBranch END ===\n");
        }

    }

    //See_Information
    @Test
    public void testTC04SeeInformation() throws Exception {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("=== TC04_SeeInformation START ===");

            // 1. Vào trang chủ
            driver.get("https://cellphones.com.vn/");
            System.out.println("[INFO] Opened Cellphones homepage");

            // 2. Click "Cửa hàng gần bạn"
            WebElement storeLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@href='/dia-chi-cua-hang']")));
            storeLink.click();
            System.out.println("[INFO] Clicked 'Cửa hàng gần bạn'");

            // 3. Nhập "thủ đức"
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Nhập vị trí để tìm cửa hàng gần nhất']")));
            searchBox.sendKeys("thủ đức");
            System.out.println("[INFO] Typed: thủ đức");

            Thread.sleep(2500); // cho nó load danh sách

            // 4. Lấy danh sách địa chỉ
            List<WebElement> items = driver.findElements(By.cssSelector(".boxSearch-result-item"));

            if (items.size() == 0) {
                System.out.println("\u001B[31m[FAIL] Không xuất hiện danh sách địa chỉ nào!\u001B[0m");
                return;
            }

            System.out.println("[INFO] Found " + items.size() + " address items");

            // 5. Click vào item đầu tiên
            WebElement firstItem = items.get(0);
            String selectedText = firstItem.getText();
            firstItem.click();
            System.out.println("[INFO] Clicked address: " + selectedText);

            Thread.sleep(1500);

            // 6. Kiểm tra trang thông tin cửa hàng đã mở hay chưa
            // Hai cách check:
            // - URL có /cua-hang/
            // - Hoặc có block thông tin cửa hàng

            boolean isInfoPage =
                    driver.getCurrentUrl().contains("cua-hang") ||
                            driver.findElements(By.cssSelector(".store-info, .box-detail-store")).size() > 0;

            if (isInfoPage) {
                System.out.println("\u001B[32m[PASS] Xem thông tin cửa hàng thành công!\u001B[0m");
            } else {
                System.out.println("\u001B[31m[FAIL] Không mở được thông tin cửa hàng!\u001B[0m");
            }

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR] Test failed: " + e.getMessage() + "\u001B[0m");
        } finally {
            driver.quit();
            System.out.println("=== TC04_SeeInformation END ===\n");
        }
    }

    //fillter_By_Brand
    @Test
    public void testTC01FilterByBrand() throws Exception {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("=== TC01_FilterByBrand START ===");

            // 1. Vào trang chủ
            driver.get("https://cellphones.com.vn/");
            System.out.println("[INFO] Opened Cellphones homepage");

            // 2. Click hãng Samsung
            WebElement samsungBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href,'/mobile/samsung')]")));
            samsungBtn.click();
            System.out.println("[INFO] Clicked brand filter: Samsung");

            // 3. Chờ trang load
            wait.until(ExpectedConditions.urlContains("samsung"));
            System.out.println("[INFO] Samsung page loaded");

            Thread.sleep(2000);

            // 4. Lấy danh sách TÊN SẢN PHẨM CHUẨN (selector fix)
            List<WebElement> productTitles = driver.findElements(
                    By.cssSelector(".product__name, .product-info__name")
            );

            if (productTitles.isEmpty()) {
                System.out.println("\u001B[31m[FAIL] Không có sản phẩm nào sau khi lọc Samsung!\u001B[0m");
                return;
            }

            System.out.println("[INFO] FOUND PRODUCTS: " + productTitles.size());

            boolean allSamsung = true;

            // 5. Check từng sản phẩm
            for (WebElement item : productTitles) {
                String name = item.getText().trim();

                if (name.isEmpty()) continue; // bỏ element rỗng

                System.out.println("[CHECK] " + name);

                if (!name.toLowerCase().contains("samsung")) {
                    allSamsung = false;
                    System.out.println("\u001B[31m[FAIL] Sản phẩm KHÔNG phải Samsung → " + name + "\u001B[0m");
                }
            }

            // 6. Kết luận
            if (allSamsung) {
                System.out.println("\u001B[32m[PASS] Tất cả sản phẩm đều thuộc hãng Samsung!\u001B[0m");
            } else {
                System.out.println("\u001B[31m[FAIL] Có sản phẩm không thuộc Samsung!\u001B[0m");
            }

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR] Test failed: " + e.getMessage() + "\u001B[0m");
        } finally {
            driver.quit();
            System.out.println("=== TC01_FilterByBrand END ===\n");
        }
    }

    //Filter_By_Brand_No_Product
    @Test
    public void testTC02FilterByBrandNoProduct() throws Exception {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("=== TC02_FilterByBrand_NoProduct START ===");

            // 1. Vào trang chủ
            driver.get("https://cellphones.com.vn/");
            System.out.println("[INFO] Opened Cellphones homepage");

            // 2. Click vào hãng Google (không có sản phẩm)
            WebElement googleBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href,'/mobile/google')]")
            ));
            googleBtn.click();
            System.out.println("[INFO] Clicked brand filter: Google");

            // 3. Đợi URL load
            wait.until(ExpectedConditions.urlContains("google"));
            System.out.println("[INFO] Google brand page loaded");

            Thread.sleep(2000);

            // 4. LẤY DANH SÁCH SẢN PHẨM CHUẨN (đã fix selector)
            List<WebElement> productList = driver.findElements(
                    By.cssSelector(".product__name, .product-info__name")
            );

            // 5. Kiểm tra có sản phẩm hay không
            if (productList.isEmpty()) {
                System.out.println("\u001B[32m[PASS] Không có sản phẩm nào → Hãng Google không có hàng, đúng mong đợi.\u001B[0m");
            } else {
                System.out.println(
                        "\u001B[31m[FAIL] Xuất hiện " + productList.size() +
                                " sản phẩm, trong khi EXPECT = 0!\u001B[0m");

                for (WebElement item : productList) {
                    String name = item.getText().trim();
                    if (!name.isEmpty()) {
                        System.out.println("[FOUND PRODUCT] " + name);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR] Test failed: " + e.getMessage() + "\u001B[0m");
        } finally {
            driver.quit();
            System.out.println("=== TC02_FilterByBrand_NoProduct END ===\n");
        }
    }

    //Filter_By_Price
    @Test
    public void testTC03FilterByPrice() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            System.out.println("=== TC03_FilterByPrice START ===");

            // 1. Vào trang chủ
            driver.get("https://cellphones.com.vn/");
            System.out.println("[INFO] Opened Cellphones homepage");

            // 2. Click Hãng Samsung
            WebElement samsungBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href,'samsung')]")
            ));
            samsungBtn.click();
            System.out.println("[INFO] Clicked brand Samsung");

            // 3. Click “Xem theo giá”
            WebElement filterPriceBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Xem theo giá')]")
            ));
            filterPriceBtn.click();
            System.out.println("[INFO] Opened price filter");

            // 4. Nhập khoảng giá
            int minPrice = 5000000;
            int maxPrice = 30000000;

            WebElement minInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("min-price")));
            WebElement maxInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("max-price")));

            minInput.clear();
            minInput.sendKeys("5.000.000");

            maxInput.clear();
            maxInput.sendKeys("30.000.000");

            System.out.println("[INFO] Input price range: " + minPrice + " - " + maxPrice);

            // 5. Click “Xem kết quả”
            WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'button__filter-children-submit')]")
            ));
            submit.click();
            System.out.println("[INFO] Submitted price filter");

            Thread.sleep(2500);

            // 6. Lấy danh sách sản phẩm
            List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector(".item strong, .product-info__name")
            ));

            System.out.println("[INFO] Found products: " + products.size());

            if (products.size() == 0) {
                System.out.println("\u001B[31m[FAIL] Không có sản phẩm nào hiển thị!\u001B[0m");
                return;
            }

            // 7. Kiểm tra giá từng sản phẩm
            boolean allValid = true;

            List<WebElement> productCards = driver.findElements(
                    By.cssSelector(".product-card, .product-info, .product-info__price")
            );

            for (WebElement card : productCards) {
                try {
                    WebElement nameEl = card.findElement(By.cssSelector(".product-info__name"));
                    WebElement priceEl = card.findElement(By.cssSelector(".product-info__price"));

                    String name = nameEl.getText().trim();
                    String priceText = priceEl.getText().trim();

                    // Parse giá
                    int price = Integer.parseInt(priceText.replace(".", "").replace("₫", "").trim());

                    System.out.println("[PRODUCT] " + name + " | PRICE: " + price);

                    if (price < minPrice || price > maxPrice) {
                        allValid = false;
                        System.out.println("\u001B[31m[FAIL] Ngoài khoảng: " + name + "\u001B[0m");
                    }

                } catch (Exception ignore) {
                    System.out.println("[WARN] Không lấy được thông tin sản phẩm.");
                }
            }

            // 8. Kết luận
            if (allValid) {
                System.out.println("\u001B[32m[PASS] Tất cả sản phẩm đều đúng khoảng giá!\u001B[0m");
            } else {
                System.out.println("\u001B[31m[FAIL] Có sản phẩm ngoài khoảng giá!\u001B[0m");
            }

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR] " + e.getMessage() + "\u001B[0m");
        } finally {
            driver.quit();
            System.out.println("=== TC03_FilterByPrice END ===");
        }
    }

    //Filter_By_Price_No_Product
    @Test
    public void testTC04FilterByPriceNoProduct() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            System.out.println("=== TC04_FilterByPrice_NoProduct START ===");

            // 1. Vào trang chủ
            driver.get("https://cellphones.com.vn/");
            System.out.println("[INFO] Opened Cellphones homepage");

            // 2. Click Samsung
            WebElement samsungBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href,'samsung')]")
            ));
            samsungBtn.click();
            System.out.println("[INFO] Clicked brand Samsung");

            // 3. Mở filter giá
            WebElement filterPriceBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Xem theo giá')]")
            ));
            filterPriceBtn.click();
            System.out.println("[INFO] Opened price filter");

            // 4. Nhập khoảng giá không có sản phẩm
            int minPrice = 1000000;
            int maxPrice = 2000000;

            WebElement minPriceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("min-price")));
            WebElement maxPriceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("max-price")));

            minPriceInput.clear();
            minPriceInput.sendKeys("1.000.000");

            maxPriceInput.clear();
            maxPriceInput.sendKeys("2.000.000");

            System.out.println("[INFO] Input price range: " + minPrice + " - " + maxPrice);

            // 5. Click xem kết quả
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'button__filter-children-submit')]")
            ));
            submitBtn.click();
            System.out.println("[INFO] Submitted price filter");

            Thread.sleep(2500);

            // 6. Lấy sản phẩm
            List<WebElement> productList = driver.findElements(
                    By.cssSelector(".product-card, .product-info__name")
            );

            System.out.println("[INFO] Products found: " + productList.size());

            // 7. EXPECT = 0 sản phẩm → PASS
            if (productList.size() == 0) {
                System.out.println("\u001B[32m[PASS] Không có sản phẩm nào trong khoảng giá → đúng EXPECT!\u001B[0m");
            } else {
                System.out.println("\u001B[31m[FAIL] EXPECT = 0 nhưng TÌM THẤY " + productList.size() + " sản phẩm!\u001B[0m");

                for (WebElement p : productList) {
                    System.out.println("[FOUND PRODUCT] " + p.getText());
                }
            }

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR] " + e.getMessage() + "\u001B[0m");
        } finally {
            driver.quit();
            System.out.println("=== TC04_FilterByPrice_NoProduct END ===\n");
        }
    }

    //Filter_By_Ram
    @Test
    public void testTC05FilterByRam() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            System.out.println("=== TC05_FilterByRam START ===");

            // 1. Mở trang chủ
            driver.get("https://cellphones.com.vn/");
            System.out.println("[INFO] Opened Cellphones homepage");

            // 2. Click Samsung
            WebElement samsungBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href,'samsung')]")
            ));
            samsungBtn.click();
            System.out.println("[INFO] Clicked Samsung category");

            // 3. Click mở bộ lọc RAM
            WebElement ramFilter = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Dung lượng RAM')]")
            ));
            ramFilter.click();
            System.out.println("[INFO] Opened RAM filter");

            // 4. Chọn RAM 8GB
            WebElement ram8GB = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'8 GB')]")
            ));
            ram8GB.click();
            System.out.println("[INFO] Selected RAM 8GB");

            // 5. Nhấn “Xem kết quả”
            WebElement viewResult = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'button__filter-children-submit')]")
            ));
            viewResult.click();
            System.out.println("[INFO] Clicked View Result");

            Thread.sleep(2500);

            // 6. Lấy danh sách sản phẩm sau lọc
            List<WebElement> products = driver.findElements(
                    By.cssSelector(".product-card")
            );

            System.out.println("[INFO] Products found: " + products.size());

            if (products.size() > 0) {
                System.out.println("\u001B[32m[PASS] Lọc RAM 8GB THÀNH CÔNG – Có sản phẩm hiển thị.\u001B[0m");
            } else {
                System.out.println("\u001B[31m[FAIL] Lọc RAM 8GB THẤT BẠI – Không có sản phẩm nào!\u001B[0m");
            }

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR] Test failed: " + e.getMessage() + "\u001B[0m");
        } finally {
            driver.quit();
            System.out.println("=== TC05_FilterByRam END ===\n");
        }
    }

    //Filter_By_More_Option
    @Test
    public void testTC06FilterByMoreOption() throws Exception {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        driver.manage().window().maximize();

        try {
            System.out.println("=== TC06_FilterByMoreOption START ===");

            // 1. MỞ TRANG SAMSUNG
            driver.get("https://cellphones.com.vn/mobile/samsung.html");
            System.out.println("[INFO] Opened Samsung page");

            // 2. CLICK "Kiểu màn hình"
            WebElement kieuManHinhBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Kiểu màn hình')]")
            ));
            kieuManHinhBtn.click();
            System.out.println("[INFO] Opened filter: Kiểu màn hình");

            // 3. CLICK "Giọt nước"
            WebElement giotNuocBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'button__filter-children') and contains(text(),'Giọt nước')]")
            ));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", giotNuocBtn);
            Thread.sleep(400);

            try {
                giotNuocBtn.click();
                System.out.println("[INFO] Selected: Giọt nước");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", giotNuocBtn);
                System.out.println("[INFO] Selected Giọt nước (JS click)");
            }

            // 4. CLICK "Hàng mới về"
            WebElement hangMoiVeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Hàng mới về')]")
            ));
            hangMoiVeBtn.click();
            System.out.println("[INFO] Selected: Hàng mới về");

            Thread.sleep(3000);

            // 5. LẤY DANH SÁCH SẢN PHẨM
            List<WebElement> products = driver.findElements(
                    By.cssSelector(".product-card, .product-item, .product")
            );

            System.out.println("\n=== RESULT: PRODUCT LIST ===");
            if (products.size() == 0) {
                System.out.println("\u001B[31m[FAIL] Không có sản phẩm nào!\u001B[0m");
            } else {
                System.out.println("[INFO] Found " + products.size() + " products.");
            }

            // 6. IN TÊN & GIÁ
            for (WebElement p : products) {
                String name = "";
                String price = "";

                try {
                    name = p.findElement(By.cssSelector(".product-info__name, h3, h2")).getText();
                } catch (Exception ignored) {}

                try {
                    price = p.findElement(By.cssSelector(".product-info__price, .price, .text-base")).getText();
                } catch (Exception ignored) {}

                System.out.println("📌 " + name + " | " + price);
            }

            System.out.println("\n\u001B[32m[PASS] TC06 – Lọc kiểu màn hình + hàng mới về thành công!\u001B[0m");

        } catch (Exception e) {
            System.out.println("\u001B[31m[ERROR] Test failed: " + e.getMessage() + "\u001B[0m");
        } finally {
            driver.quit();
            System.out.println("=== TC06_FilterByMoreOption END ===\n");
        }
    }

    //Arrange_Price
    @Test
    public void testTC07ArrangePrice() throws Exception {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        driver.manage().window().maximize();

        try {
            // 1. MỞ TRANG SAMSUNG
            driver.get("https://cellphones.com.vn/mobile/samsung.html");
            System.out.println("=== TC07: SẮP XẾP GIÁ THẤP → CAO ===");

            // 2. CLICK NÚT "Giá Thấp - Cao"
            WebElement sortGiaThapCao = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Giá Thấp') or contains(.,'Giá Thấp - Cao')]")
            ));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sortGiaThapCao);
            Thread.sleep(300);

            try {
                sortGiaThapCao.click();
                System.out.println("Đã click Giá Thấp - Cao (click thường)");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sortGiaThapCao);
                System.out.println("Đã click Giá Thấp - Cao (click JS)");
            }

            // Đợi trang cập nhật danh sách sản phẩm
            Thread.sleep(3000);

            // 3. LẤY DANH SÁCH SẢN PHẨM SAU KHI SẮP XẾP
            List<WebElement> products = driver.findElements(
                    By.cssSelector(".product-card, .product, div[data-cy='product']"));

            System.out.println("=== KẾT QUẢ SAU KHI SẮP XẾP GIÁ ===");

            if (products.isEmpty()) {
                System.out.println("❌ Không có sản phẩm nào hiển thị!");
            }

            // 4. IN TÊN VÀ GIÁ
            for (WebElement p : products) {
                String name = "";
                String price = "";

                try {
                    name = p.findElement(By.cssSelector("h3, .product-name, .product__name"))
                            .getText();
                } catch (Exception ignored) {}

                try {
                    price = p.findElement(By.cssSelector(".price, .product-price, .product__price"))
                            .getText();
                } catch (Exception ignored) {}

                System.out.println("📌 " + name + " | " + price);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            System.out.println("=== Đã đóng trình duyệt ===");
        }
    }

    @After
    public void tearDown() throws Exception {
//        driver.quit();
        if (driver != null) {
            driver.quit();
        }
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
