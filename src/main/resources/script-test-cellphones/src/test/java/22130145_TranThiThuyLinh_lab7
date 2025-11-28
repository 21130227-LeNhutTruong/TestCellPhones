package com.example;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class UntitledTestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testUntitledTestCase() throws Exception {
    driver.get("https://cellphones.com.vn/thiet-bi-am-thanh/tai-nghe/headphones.html");
    driver.findElement(By.xpath("//div[@id='blockFilterSort']/div[3]/div/div/div[19]/div/a/div[2]/h3")).click();
    driver.get("https://cellphones.com.vn/tai-nghe-chup-tai-havit-h619bt.html");
    driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[2]/div[2]/ul/li[2]/a")).click();
    driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[7]")).click();
    driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[9]/div/div/div/button[2]/strong")).click();
    driver.findElement(By.linkText("Đăng nhập")).click();
    driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[3]/div[2]/ul/li[2]/a/div/strong")).click();
    driver.findElement(By.xpath("//div[@id='productDetailV2']/section/div/div[2]/div[9]/div/div/div/button[2]/strong")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Giỏ hàng'])[1]/following::*[name()='svg'][1]")).click();
    driver.get("https://cellphones.com.vn/cart/");
    driver.findElement(By.xpath("//div[@id='stickyBottomBar']/button")).click();
    driver.findElement(By.xpath("//div[@id='listItemSuperCart']/div[2]/div/div[2]/div[2]/div[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='listItemSuperCart']/div[2]/div/div[2]/div[2]/div[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='listItemSuperCart']/div[2]/div/div/div/label")).click();
    driver.findElement(By.xpath("//div[@id='stickyBottomBar']/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div/div[2]/label")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div[2]/div/div/div[2]/div/div[2]/div/input")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div[2]/div/div/div[2]/div/div[2]/div/div[3]/div/div[22]/span")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div[2]/div/div/div[2]/div/div[2]/div[2]/input")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div[2]/div/div/div[2]/div/div[2]/div[2]/div[3]/div/div[8]/span")).click();
    driver.findElement(By.id("VAT-No")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[4]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div[2]/div/div/div[2]/div[2]/input")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div[2]/div/div/div[2]/div[2]/input")).clear();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div[2]/div/div/div[2]/div[2]/input")).sendKeys("220 đường số 8");
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[4]/div/div[2]/div/div/div[2]/div[2]/div[3]/div/div[2]/div[2]/i")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[4]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div/div[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div[4]/div[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[3]/div/div[2]/button")).click();
    driver.get("https://payment.momo.vn/v2/gateway/pay?t=TU9NT0pEU1EyMDIzMDgwN3wzMDM4NzYwNjM&s=7835939a37bbe86e6b3bb32aae74f51af3cfd087420ab3a36e0b2e8de3d9fca0");
    driver.get("https://cellphones.com.vn/cart/payment");
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div/div[2]/p")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div[3]")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[3]/div/div[2]/button")).click();
    driver.get("https://pay.vnpay.vn/Transaction/PaymentMethod.html?token=00869f4c8268403583243a75086f2648");
    driver.get("https://cellphones.com.vn/cart/payment");
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div/div[2]/p")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div/div[3]/span")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div[4]/div[2]/span")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[3]/div/div[2]/button")).click();
    driver.get("https://payment.momo.vn/v2/gateway/pay?t=TU9NT0pEU1EyMDIzMDgwN3wzMDM4NzYwODM&s=cc791ef926881399ccd237f9aa4b9bb6ec3a55f16a24d7adbe861040b0dc71b1");
    driver.get("https://cellphones.com.vn/cart/payment");
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div/div[3]/span")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div[3]/div[2]/p")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[3]/div/div[2]/button")).click();
    driver.get("https://payment.momo.vn/v2/gateway/pay?t=TU9NT0pEU1EyMDIzMDgwN3wzMDM4NzYxMDU&s=b5b434cf0aac2ed5c0a28da5b888e4e56c156bb5e3ed354853a6c58a316d6792");
    driver.get("https://cellphones.com.vn/cart/payment");
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div[8]")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[2]/div[2]/div/div")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[3]/div/div[2]/button")).click();
    driver.get("https://gateway.zalopay.vn/pay/v2/?order=eyJ6cHRyYW5zdG9rZW4iOiJBQ3N2dVBXT2ZuMF9VQUd6YTBCX3R5a2ciLCJhcHBpZCI6NDQ2M30=");
    driver.get("https://gateway.zalopay.vn/pay/v2?order=eyJ6cHRyYW5zdG9rZW4iOiJBQ3N2dVBXT2ZuMF9VQUd6YTBCX3R5a2ciLCJhcHBpZCI6NDQ2M30=");
    driver.get("https://cellphones.com.vn/cart/payment");
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div/div[2]/p")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div[3]/div[2]/p")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[3]/div/div[2]/button")).click();
    driver.get("https://gateway.zalopay.vn/pay/v2/?order=eyJ6cHRyYW5zdG9rZW4iOiJBQ0Z2eTQyakN4Qklsc0VObmVaT1FiTFEiLCJhcHBpZCI6NDQ2M30=");
    driver.get("https://gateway.zalopay.vn/pay/v2?order=eyJ6cHRyYW5zdG9rZW4iOiJBQ0Z2eTQyakN4Qklsc0VObmVaT1FiTFEiLCJhcHBpZCI6NDQ2M30=");
    driver.get("https://cellphones.com.vn/cart/payment");
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div/div[3]/span")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div[6]")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='__layout']/div/div[2]/div[3]/div/div[2]/button")).click();
    driver.get("https://payment.momo.vn/v2/gateway/pay?t=TU9NT0pEU1EyMDIzMDgwN3wzMDM4NzYxMjg&s=d00bbe15932577745cb6b60867bbcc15b119198b2ce6acb1d4d340a86daac32e");
    driver.get("https://cellphones.com.vn/cart/payment");
  }

  @After
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
