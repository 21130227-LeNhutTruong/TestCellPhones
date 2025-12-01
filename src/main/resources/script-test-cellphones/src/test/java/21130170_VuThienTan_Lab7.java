package com.example.Cellphones;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class AllTestCases {
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
  public void testTC1() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.xpath("//div[@id='radix-«Rmjb»']/div[2]/div/button[2]")).click();
    driver.get("https://smember.com.vn/login?action=login&client_id=cps&redirect_uri=https%3A%2F%2Fcellphones.com.vn%2F&response_type=authorization_code&state=lwdK422WOUZLX7OHA7AyhQVotgFriozPkkQtbFjq");
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='0901489303']")).clear();
    driver.findElement(By.xpath("//input[@value='0901489303']")).sendKeys("0901489303");
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='Tan@042003']")).clear();
    driver.findElement(By.xpath("//input[@value='Tan@042003']")).sendKeys("Tan@042003");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.get("https://cellphones.com.vn/?state=lwdK422WOUZLX7OHA7AyhQVotgFriozPkkQtbFjq&code=Wbas2GDAg2Tfguw9QDenSxROD2V0LUf2UlcMHx2plKmQoqcwYIvijW5RP8ogcwyh");
    driver.get("https://cellphones.com.vn/");
  }

  @Test
  public void testTC2() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.xpath("//div[@id='radix-«Rmjb»']/div[2]/div/button[2]")).click();
    driver.get("https://smember.com.vn/login?action=login&client_id=cps&redirect_uri=https%3A%2F%2Fcellphones.com.vn%2F&response_type=authorization_code&state=SXeuw1CrkaBPOwep6VBfqNw0qRmPbAcFkWLCKcu4");
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='0901489303']")).clear();
    driver.findElement(By.xpath("//input[@value='0901489303']")).sendKeys("0901489303");
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='Tan@042003']")).clear();
    driver.findElement(By.xpath("//input[@value='Tan@042003']")).sendKeys("Tan@123");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @Test
  public void testTC3() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Đăng nhập'])[1]//*[name()='svg'][1]")).click();
    driver.findElement(By.xpath("//div[@id='radix-«Rmjb»']/div[2]/div/button[2]")).click();
    driver.get("https://smember.com.vn/login?action=login&client_id=cps&redirect_uri=https%3A%2F%2Fcellphones.com.vn%2F&response_type=authorization_code&state=ZpAELCy5a9NWADNk7C65i4MrMkCoUvzGEhpsSJ2V");
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='0901489303']")).clear();
    driver.findElement(By.xpath("//input[@value='0901489303']")).sendKeys("0901489303");
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='123123123']")).clear();
    driver.findElement(By.xpath("//input[@value='123123123']")).sendKeys("123123123");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @Test
  public void testTC4() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.xpath("//div[@id='radix-«Rmjb»']/div[2]/div/button[2]")).click();
    driver.get("https://smember.com.vn/login?action=login&client_id=cps&redirect_uri=https%3A%2F%2Fcellphones.com.vn%2F&response_type=authorization_code&state=sCkUTMYM5JscBX3ADDAa4Yp4YmLvchbqTtAiFJ9j");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Hoặc đăng nhập bằng'])[1]/following::span[1]")).click();
    driver.get("https://accounts.google.com/v3/signin/accountchooser?client_id=584649759634-lps9s775eods5sjuiohjm0s68e8jhl57.apps.googleusercontent.com&redirect_uri=https%3A%2F%2Fsmember.com.vn%2Fsocial%2Fgoogle%2Fcallback&response_type=code&scope=openid+profile+email&dsh=S1026143974%3A1764565022887089&o2v=1&service=lso&flowName=GeneralOAuthFlow&opparams=%253F&continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Foauth%2Fconsent%3Fauthuser%3Dunknown%26part%3DAJi8hAP-bD8L4yoy6X6QCqYI9q1jYooQD2z5yo0DiHYVXTDv0D0hLvisIl2wiFcUozjj82_zS3fMZGMJi5licgipxPATuUYJnpkCGbT-dRIU2pyTP_FzN_LTenOha5Un4ZHK_pNqOUDvt9NY8Ou2gnWNAlEbngc0yujEmgcQeS1Yhhbf0Wprup6GFQk5qun7YiAR0biNab8X1BqGWxBhG9KKvdZiu6_ckXNpXiQMa2phkV-r8kKOdQ-GIj0nvmhcF-h_4wfggqrFwlyI7HscthObpt3LD0uP3MpXsx_1em_r-LqFm7qjgOr4-FGMZLbYpcaRpBzeIWaOenPid69mgMimkIcRbCsu8ghU-xugUUdsbkhelofQTuY2C0-LKt7KCP4kNZ61SgZAcOQiNE0ENDSt38BhCa46nsRl2m0aw9Xl8hUDXMg90pRAtv_RqlN2rsH7yPXLIvqbJAR4PAJWD_XBwMDArsh6fQ%26flowName%3DGeneralOAuthFlow%26as%3DS1026143974%253A1764565022887089%26client_id%3D584649759634-lps9s775eods5sjuiohjm0s68e8jhl57.apps.googleusercontent.com%26requestPath%3D%252Fsignin%252Foauth%252Fconsent%23&app_domain=https%3A%2F%2Fsmember.com.vn");
    driver.findElement(By.xpath("//div[@id='yDmH0d']/c-wiz/main/div[2]/div/div/div/form/span/section/div/div/div/div/ul/li[2]/div")).click();
    driver.get("https://smember.com.vn/social/google/callback?code=4%2F0Ab32j92XvAKWuIXksD5WdZtH12mvkI8j2q5rRfL27oob_OXb1xCF1RWFiaCjWs4UQgXPkw&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+openid&authuser=1&prompt=none");
    driver.get("https://cellphones.com.vn/?state=sCkUTMYM5JscBX3ADDAa4Yp4YmLvchbqTtAiFJ9j&code=iH3jtGyD9sDvuQyE2sfqMzqMDPbrESl4Y7ZMuIlfRI42bOOow3OWxvmySXXageoF");
    driver.get("https://cellphones.com.vn/");
  }

  @Test
  public void testTC5() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.xpath("//div[@id='radix-«Rmjb»']/div[2]/div/button[2]")).click();
    driver.get("https://smember.com.vn/login?action=login&client_id=cps&redirect_uri=https%3A%2F%2Fcellphones.com.vn%2F&response_type=authorization_code&state=oaMrPrFizfHQNkg9Yk0nLQiKOQa1B7iOhj2Efu5V");
    driver.findElement(By.linkText("Quên mật khẩu?")).click();
    driver.get("https://smember.com.vn/restore-password");
    driver.findElement(By.name("phone")).click();
    driver.findElement(By.name("phone")).clear();
    driver.findElement(By.name("phone")).sendKeys("0123456789");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @Test
  public void testTC6() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.xpath("//div[@id='radix-«Rmjb»']/div[2]/div/button[2]")).click();
    driver.get("https://smember.com.vn/login?action=login&client_id=cps&redirect_uri=https%3A%2F%2Fcellphones.com.vn%2F&response_type=authorization_code&state=oaMrPrFizfHQNkg9Yk0nLQiKOQa1B7iOhj2Efu5V");
    driver.findElement(By.linkText("Quên mật khẩu?")).click();
    driver.get("https://smember.com.vn/restore-password");
    driver.findElement(By.name("phone")).click();
    driver.findElement(By.name("phone")).clear();
    driver.findElement(By.name("phone")).sendKeys("0906123123");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    //ERROR: Caught exception [unknown command []]
  }

  @Test
  public void testTC7() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.linkText("Truy cập Smember")).click();
    driver.get("https://smember.com.vn/?company_id=cellphones&_gl=1*22ij6u*_gcl_au*MjEwMzg3MTAxMS4xNzYzMDA1ODY5*_ga*MjkyMDM0ODAzLjE3NjMwMDU4NzA.*_ga_QLK8WFHNK9*czE3NjQ1NjMzNTgkbzYkZzEkdDE3NjQ1NjU0ODgkajQxJGwwJGgyODY2NzAzMTY.");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Điều khoản sử dụng'])[1]/following::div[4]")).click();
    driver.findElement(By.xpath("//div[@id='radix-«Rnlb»']/div[2]/button[2]")).click();
    driver.get("https://smember.com.vn/login");
  }

  @Test
  public void testTC8() throws Exception {
    driver.get("https://cellphones.com.vn/");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.linkText("Truy cập Smember")).click();
    driver.get("https://smember.com.vn/?company_id=cellphones&_gl=1*1rbgcev*_gcl_au*MjEwMzg3MTAxMS4xNzYzMDA1ODY5*_ga*MjkyMDM0ODAzLjE3NjMwMDU4NzA.*_ga_QLK8WFHNK9*czE3NjQ1NjMzNTgkbzYkZzEkdDE3NjQ1NjYxODYkajUxJGwwJGgyODY2NzAzMTY.");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Mới'])[1]/following::span[1]")).click();
    driver.get("https://smember.com.vn/user-info");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Thông tin cá nhân'])[1]/following::*[name()='svg'][1]")).click();
    driver.findElement(By.xpath("//div[@id='radix-«rs»']/div/div[2]/form/div/div[2]/div/div/div/input")).click();
    driver.findElement(By.id("radix-«rs»")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
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

