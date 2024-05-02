package com.example;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest {
    WebDriver driver;
    static ExtentTest test;
    static ExtentReports report;

    @BeforeMethod
    public static void startTest() {
        ExtentSparkReporter spark = new ExtentSparkReporter(
                "D:\\reportmayo.html");
        report = new ExtentReports();
        report.attachReporter(spark);
        test = report.createTest("ExtentDemo");
    }

    @Test
    public void testCase() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.mayoclinic.org/");
        Thread.sleep(3000);
        test.log(Status.PASS, "Open Website : TestCase passed");
        driver.findElement(
                By.xpath("//*[@id='header__content-inner-container']/div[1]/ul/li[5]/div[1]/div/button/span/span[1]"))
                .click();

        driver.findElement(By.xpath("//*[@id=\"button-d87139392b\"]/span/span/span[1]/span")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"amounts\"]/label[3]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"designation\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"designation\"]/option[8]")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"adfWrapper\"]/fieldset[2]/div[1]/label")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"personalTitle\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"personalTitle\"]/option[2]")).click();
        Thread.sleep(2000);

        FileInputStream fi = new FileInputStream("D:\\Loginmayo.xlsx");
        Workbook wb = new XSSFWorkbook(fi);
        Sheet sheet = wb.getSheet("Login");

        Row r = sheet.getRow(1);
        String firstname = r.getCell(0).getStringCellValue();
        String lastname = r.getCell(1).getStringCellValue();

        String address = r.getCell(2).toString();
        String city = r.getCell(3).getStringCellValue();
        String email = r.getCell(4).getStringCellValue();

        driver.findElement(By.xpath("//*[@id=\"personalFirstName\"]")).sendKeys(firstname);
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"personalLastName\"]")).sendKeys(lastname);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"personalCountry\"]")).click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"personalCountry\"]/option[80]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"personalState\"]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"personalState\"]/option[2]")).click();
        Thread.sleep(3000);
        // String address = String.valueOf((int)r.getCell(2).getNumericCellValue());

        driver.findElement(By.xpath("//*[@id=\"personalAddress\"]")).sendKeys(address);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"personalCity\"]")).sendKeys(city);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"personalZip\"]")).sendKeys("635126");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"personalPhone\"]")).sendKeys("1234567890");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"personalEmail\"]")).sendKeys(email);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"adfSubmit\"]")).click();
        Thread.sleep(10000);
        test.log(Status.PASS, "Excel Read the Data : TestCase passed");
        Thread.sleep(3000);
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        String path = "./Seleniumss/screen.png";
        FileUtils.copyFile(source, new File(path));
        test.log(Status.PASS, "Successfully ss : TestCase passed");
        driver.quit();
    }

    @AfterMethod
    public static void endTest() {
        report.flush();

    }
}