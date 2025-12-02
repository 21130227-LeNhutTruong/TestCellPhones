package com.example;
import io.github.bonigarcia.wdm.WebDriverManager;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

import static org.junit.Assert.fail;

public class NguyenDucAnhTuan_21130235_Lab7 {
    private static WebDriver driver;
    private static String baseUrl;
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
    public void testTC01FindLocatorNear() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='topInfoBar']/div[2]/a/span")).click();
        Thread.sleep(3000);
    }

    //Find_country
    @Test
    public void testTC02FindCountry() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='topInfoBar']/div[2]/a/span")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/input")).click();
        driver.findElement(By.id("boxSearchProvince")).click();
        new Select(driver.findElement(By.id("boxSearchProvince"))).selectByVisibleText("Bình Định");
    }

    //Find_No_Brand
    @Test
    public void testTC03FindNoBranch() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='topInfoBar']/div[2]/a/span")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/input")).click();
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/input")).clear();
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/input")).sendKeys("kon tom");
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/input")).sendKeys(Keys.ENTER);
    }

    //See_Information
    @Test
    public void testTC04SeeInformation() throws Exception {
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//div[@id='topInfoBar']/div[2]/a/span")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/input")).click();
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/input")).clear();
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/input")).sendKeys("thủ đức");
        driver.findElement(By.xpath("//div[@id='boxOptions']/div/div/div/ul/li")).click();
        driver.findElement(By.xpath("//div[@id='boxMap']/div/div[3]/p")).click();
        driver.findElement(By.xpath("//div[@id='boxMap']/div[2]/div/div/canvas")).click();
    }

    //fillter_By_Brand
    @Test
    public void testTC01FilterByBrand() throws Exception {
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(3000);
        driver.findElement(By.linkText("Samsung")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy S24 Plus 12GB 256GB']")).click();
    }

    //Filter_By_Brand_No_Product
    @Test
    public void testTC02FilterByBrandNoProduct() throws Exception {
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@id='layout-provider']/main/div/div/div/div/div/a/p")).click();
        driver.findElement(By.xpath("//img[@alt='Apple MacBook Air M2 2024 8CPU 8GPU 16GB 256GB I Chính hãng Apple Việt Nam']")).click();
    }

    //Filter_By_Price
    @Test
    public void testTC03FilterByPrice() throws Exception {
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(3000);
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.linkText("Samsung")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/div/div/div[2]/div/div[3]/div")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/div/div[2]/button[2]")).click();
        driver.findElement(By.xpath("//img[@alt='Samsung Galaxy A16 5G 8GB 128GB']")).click();
    }

    //Filter_By_Price_No_Product
    @Test
    public void testTC04FilterByPriceNoProduct() throws Exception {
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(3000);
        driver.get("https://cellphones.com.vn/");
        driver.findElement(By.xpath("//header[@id='header']/div[3]/button")).click();
        driver.findElement(By.xpath("//div[@id='layout-provider']/div[2]/div/div/div/div/div")).click();
        driver.findElement(By.xpath("//div[@id='layout-provider']/div[2]/div/div/div/div[2]/div/div/div[2]/div[2]/a/span")).click();
    }

    //Filter_By_Ram
    @Test
    public void testTC05FilterByRam() throws Exception {
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(3000);
        driver.findElement(By.linkText("Laptop")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[5]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[5]/div/ul/li[12]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[5]/div/div/button[2]")).click();
        driver.findElement(By.xpath("//img[@alt='Surface Laptop Go Core i5 / 8GB / 128 GB / 12.4 inch Nhập Khẩu Chính Hãng']")).click();
    }

    //Filter_By_More_Option
    @Test
    public void testTC06FilterByMoreOption() throws Exception {
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(3000);
        driver.findElement(By.linkText("Laptop")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[5]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[5]/div/ul/li[8]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[5]/div/div/button[2]")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[7]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[7]/div/ul/li[3]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[7]/div/div/button[2]")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[10]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[10]/div/ul/li[12]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[10]/div/div/button[2]")).click();
    }

    //Arrange_Price
    @Test
    public void testTC07ArrangePrice() throws Exception {
        driver.get("https://cellphones.com.vn/");
        Thread.sleep(3000);
        driver.findElement(By.linkText("Laptop")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/div/div/div[2]/div/div[3]/div")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/div/div[2]/button[2]")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/div/div/div[2]/div")).click();
        driver.findElement(By.xpath("//div[@id='filterModule']/div/div[2]/div/div[2]/button[2]")).click();
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
