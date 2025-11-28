package com.example.UntitledTestSuite;

import java.time.Duration;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class DoNhuKien_21130085_lab7_Wishlist {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        // Điền đường dẫn chromedriver nếu cần
        System.setProperty("webdriver.chrome.driver", "");
        driver = new ChromeDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
    }

    // ====================== WISHLIST0 ======================
    @Test
    public void testWishlist0() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A15 LTE 8GB 256GB - Cũ Trầy Xước']")).click();
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        String productName = (String) js.executeScript(
                " return " +
                        "document.querySelector('.box-product-name h1').innerText.trim()" +
                        ""
        );
        String productPrice = (String) js.executeScript(
                "var productName = \"" + productName + "\";" +
                        "var storedVars = { 'productName': productName }; return " +
                        "document.querySelector('.sale-price').innerText.trim()" +
                        ""
        );
        System.out.println("Sản phẩm wishlist: " + productName + " - " + productPrice);
    }

    // ====================== WISHLIST1 ======================
    @Test
    public void testWishlist1() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div/div/div/div/a/p")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)=':'])[2]/following::span[2]")).click();
        String productName = (String) js.executeScript(
                " return " +
                        "document.querySelector('.box-product-name h1').innerText.trim()" +
                        ""
        );
        String productPrice = (String) js.executeScript(
                "var productName = \"" + productName + "\";" +
                        "var storedVars = { 'productName': productName }; return " +
                        "document.querySelector('.sale-price').innerText.trim()" +
                        ""
        );
        System.out.println("Sản phẩm wishlist: " + productName + " - " + productPrice);
    }

    // ====================== WISHLIST2 ======================
    @Test
    public void testWishlist2() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.get("https://cellphones.com.vn/samsung-galaxy-a15-8gb-256gb-cu-tray-xuoc.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        String productName = (String) js.executeScript(
                " return " +
                        "document.querySelector('.box-product-name h1').innerText.trim()" +
                        ""
        );
        String productPrice = (String) js.executeScript(
                "var productName = \"" + productName + "\";" +
                        "var storedVars = { 'productName': productName }; return " +
                        "document.querySelector('.sale-price').innerText.trim()" +
                        ""
        );
        System.out.println("Sản phẩm wishlist: " + productName + " - " + productPrice);
    }

    // ====================== WISHLIST3 ======================
    @Test
    public void testWishlist3() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div/div/div/div/a/p")).click();
        driver.get("https://cellphones.com.vn/laptop.html");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)=':'])[3]/following::span[2]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)=':'])[3]/following::span[2]")).click();
    }

    // ====================== WISHLIST4 ======================
    @Test
    public void testWishlist4() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.linkText("Tai nghe bluetooth Bose Quietcomfort Earbuds3.500.000đ4.400.000đĐã bán 0/20 suất")).click();
        driver.get("https://cellphones.com.vn/loa-bluetooth-sony-ult-field-1.html");
        driver.findElement(By.xpath("//button[@id='wishListBtn']/div/span")).click();
        driver.navigate().refresh();
    }

    // ====================== WISHLIST5 ======================
    @Test
    public void testWishlist5() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div/div/div/div/a/p")).click();
        driver.get("https://cellphones.com.vn/laptop.html");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)=':'])[3]/following::span[2]")).click();
        driver.findElement(By.xpath("//div[@id='boxHotSale']/div/div[2]/div/div/div/div[3]/div/div/a/div[2]/h3")).click();
        driver.get("https://cellphones.com.vn/laptop-asus-gaming-v16-v3607vu-rp343w.html");
        String wlClass = (String) js.executeScript(
                " return " +
                        "document.querySelector('#wishListBtn').className" +
                        ""
        );
        System.out.println("Class hiện tại: " + wlClass);
        String wlStatus = (String) js.executeScript(
                "var wlClass = \"" + wlClass + "\";" +
                        "var storedVars = { 'wlClass': wlClass }; return " +
                        "document.querySelector('#wishListBtn').classList.contains('active') ? 'ĐÃ TICK' : 'CHƯA TICK'" +
                        ""
        );
        System.out.println("Trạng thái wishlist: " + wlStatus);
    }

    // ====================== WISHLIST6 ======================
    @Test
    public void testWishlist6() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//button[@type='button']")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Truy cập Smember'])[1]//*[name()='svg'][1]")).click();
        driver.get("https://smember.com.vn/?company_id=cellphones&_gl=1*1ah210i*_gcl_au*MTY2Mzg2MzgzOS4xNzU3MjIzNTQw*_ga*MTU2MDA5NzM1OS4xNzY0MzM4MDM0*_ga_QLK8WFHNK9*czE3NjQzMzgwMDAkbzUkZzEkdDE3NjQzNDMyMDEkajU2JGwwJGgxNDY5NzM4MDUz");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Sản phẩm yêu thích'])[1]/following::button[1]")).click();
        String wishlistInfo = (String) js.executeScript(
                " return " +
                        "(function () {   const container = document.querySelector('div.w-full.h-full.overflow-y-auto');" +
                        "   if (!container) return 'Không tìm thấy danh sách wishlist';" +
                        "   const items = container.querySelectorAll('a.flex.items-center');" +
                        "   if (!items.length) return 'Wishlist đang trống';" +
                        "   const lines = [];" +
                        "   items.forEach((a, idx) => { " +
                        "     const name = a.querySelector('.text-base.font-bold')?.innerText.trim() || '';" +
                        "     const price = a.querySelector('.text-small.font-bold')?.innerText.trim() || '';" +
                        "     const oldPrice = a.querySelector('.text-1x-small.line-through')?.innerText.trim() || '';" +
                        "     const url = a.getAttribute('href') || '';" +
                        "     lines.push((idx + 1) + '. ' + name + ' | ' + price + ' | ' + oldPrice + ' | ' + url);" +
                        "   });" +
                        "   return lines.join('\\n'); })()" +
                        ""
        );
        System.out.println(wishlistInfo);
    }

    // ====================== WISHLIST7 ======================
    @Test
    public void testWishlist7() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Kiên'])[1]//*[name()='svg'][1]")).click();
        driver.findElement(By.linkText("Truy cập Smember")).click();
        driver.get("https://smember.com.vn/?company_id=cellphones&_gl=1*uyq367*_gcl_au*MTY2Mzg2MzgzOS4xNzU3MjIzNTQw*_ga*MTU2MDA5NzM1OS4xNzY0MzM4MDM0*_ga_QLK8WFHNK9*czE3NjQzMzgwMDAkbzUkZzEkdDE3NjQzNDM0ODIkajYwJGwwJGgxNDY5NzM4MDUz");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Sản phẩm yêu thích'])[1]/following::button[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Xiaomi Pad Mini 8GB 256GB'])[2]/following::*[name()='svg'][1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Loa Bluetooth Sony ULT Field 1'])[2]/following::*[name()='svg'][1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Quay lại'])[1]/following::*[name()='svg'][1]")).click();
        driver.navigate().refresh();
    }

    // ====================== TEARDOWN ======================
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
