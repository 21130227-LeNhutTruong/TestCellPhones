package com.example.UntitledTestSuite;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class NguyennNgocThanh_21130178_lab7 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private JavascriptExecutor js;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testNguyennNgocThanh_21130178_lab7() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.xpath("//div[@id='radix-«Rmjb»']/div[2]/div/button[2]")).click();
    driver.get("https://smember.com.vn/login?action=login&client_id=cps&redirect_uri=https%3A%2F%2Fcellphones.com.vn%2F&response_type=authorization_code&state=eyYweoIKCvcAMzrsER5C0gTK5UT37NfKn5KR4L1z");
    driver.findElement(By.xpath("//input[@value='0523713512']")).clear();
    driver.findElement(By.xpath("//input[@value='0523713512']")).sendKeys("0523713512");
    driver.findElement(By.xpath("//input[@value='Thanh1231']")).clear();
    driver.findElement(By.xpath("//input[@value='Thanh1231']")).sendKeys("Thanh123");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//input[@value='Thanh123123']")).clear();
    driver.findElement(By.xpath("//input[@value='Thanh123123']")).sendKeys("Thanh123123");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.get("https://cellphones.com.vn/?state=eyYweoIKCvcAMzrsER5C0gTK5UT37NfKn5KR4L1z&code=XBz5qCZDHpG5PcSYOkUqnpzCdeMQdSHozQTj47nnUPAv8fk6qvx2nHWIdeFQvN5d");
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.linkText("Truy cập Smember")).click();
    driver.get("https://smember.com.vn/?company_id=cellphones&_gl=1*t319mw*_gcl_au*MTE5MDM3OTQyMy4xNzY0MzMyNDQ5*_ga*MzY0MjMzNjg3LjE3NjQzMzI0NDk.*_ga_QLK8WFHNK9*czE3NjQzMzI0NDkkbzEkZzEkdDE3NjQzMzc0MjQkajMzJGwwJGgxMjYxMDk5NTI2");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Hạng thành viên'])[1]/following::div[2]")).click();
    driver.get("https://smember.com.vn/promotion");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Tổng quan'])[1]/following::span[1]")).click();
    driver.get("https://smember.com.vn/order");
    driver.findElement(By.cssSelector("rect")).click();
    driver.findElement(By.xpath("//div[@id='radix-«rf»']/div/div/div/table/tbody/tr/td[4]/button")).click();
    driver.findElement(By.xpath("//div[@id='radix-«rf»']/div/div/div/table/tbody/tr/td[5]/button")).click();
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//header[@id='header']/div[3]/button")).click();
    driver.findElement(By.xpath("//div[@id='layout-provider']/div[2]/div/div/div/div/a[6]/p")).click();
    driver.findElement(By.xpath("//img[@alt='Hướng dẫn cách tải game Tiệm Phở Của Anh Hai cho điện thoại và máy tính']")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Thủ thuật'])[2]/following::article[1]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Hướng dẫn cách tải game Tiệm Phở Của Anh Hai cho điện thoại và máy tính'])[3]/following::div[1]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='điện thoại'])[1]/following::p[1]")).click();
    driver.findElement(By.id("input-comment-0")).click();
    driver.findElement(By.id("input-comment-0")).clear();
    driver.findElement(By.id("input-comment-0")).sendKeys("aaaaaa");
    driver.findElement(By.xpath("//div[@id='sfComments']/div/div/div/button")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gia dụng'])[2]/following::div[6]")).click();
    driver.findElement(By.xpath("//div/div/div/div/div/div/a/img")).click();
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
