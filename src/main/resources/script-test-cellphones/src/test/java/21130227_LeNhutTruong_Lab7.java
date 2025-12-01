import java.time.Duration;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class CompareProductsTest {
    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testCompareProducts0() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//img[@alt='Chuột không dây Logitech M221']")).click();
        driver.findElement(By.linkText("So sánh")).click();
    }

    @Test
    public void testCompareProducts1() throws Exception {
        driver.get("https://cellphones.com.vn/chuot-khong-day-logitech-m221.html");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Thông số'])[1]/following::*[name()='svg'][1]")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/div[4]/div/div/div[2]/div[2]/img")).click();
        WebElement searchBox = driver.findElement(By.id("inp$earch"));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("Chuột");
        searchBox.sendKeys(Keys.ENTER);
        driver.get("https://cellphones.com.vn/catalogsearch/result?q=Chu%E1%BB%99t");
        driver.findElement(By.xpath("//div[@id='search_autocomplete']/div[2]/a/div/p")).click();
        driver.get("https://cellphones.com.vn/chuot-khong-day-logitech-mx-master-2s.html");
        driver.findElement(By.linkText("So sánh")).click();
    }

    @Test
    public void testCompareProducts2() throws Exception {
        driver.get("https://cellphones.com.vn/so-sanh/chuot-choi-game-co-day-logitech-g102-lightsync-8000dpi-vs-chuot-khong-day-logitech-mx-master-2s-vs-chuot-khong-day-logitech-signature-m650");
        driver.findElement(By.cssSelector("a > div.icon > svg > path")).click();
        driver.get("https://cellphones.com.vn/so-sanh/chuot-khong-day-logitech-mx-master-2s-vs-chuot-khong-day-logitech-signature-m650");
    }

    @Test
    public void testCompareProducts3() throws Exception {
        driver.get("https://cellphones.com.vn/so-sanh/chuot-khong-day-logitech-mx-master-2s-vs-chuot-khong-day-logitech-signature-m650");
        driver.findElement(By.cssSelector("a > div.icon > svg > path")).click();
        driver.get("https://cellphones.com.vn/so-sanh/chuot-khong-day-logitech-signature-m650");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Mua ngay'])[1]/following::*[name()='svg'][1]")).click();
        driver.get("https://cellphones.com.vn/so-sanh");
    }

    @Test
    public void testCompareProducts4() throws Exception {
        driver.get("https://cellphones.com.vn/chuot-khong-day-logitech-mx-master-2s.html");
        driver.findElement(By.linkText("So sánh")).click();
        driver.findElement(By.xpath("//div[@id='productDetailV2']/div[4]/div/div/div[2]/div[4]/div/a")).click();
        driver.get("https://cellphones.com.vn/so-sanh/chuot-choi-game-co-day-logitech-g102-lightsync-8000dpi-vs-chuot-khong-day-logitech-mx-master-2s-vs-chuot-khong-day-logitech-signature-m650");
    }

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
