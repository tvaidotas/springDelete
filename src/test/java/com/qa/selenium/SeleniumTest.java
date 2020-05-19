package com.qa.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertTrue;

public class SeleniumTest {

    private static WebDriver driver;

    @Before
    public void setup() {
        if (isWindows()){
            System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", "./chromedriver"); // if you want to run chrome
            // System.setProperty("webdriver.chrome.driver", "/snap/bin/chromium.chromedriver"); // if you want to run chromium from snap store
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // headless requires window size to be set as the default 800:600 doesn't work
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox"); // some guides said jenkins needs it
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-debugging-port=9222");
        driver = new ChromeDriver(options);
    }

    public boolean isWindows(){
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    @Test
    public void exampleSelenium() throws InterruptedException {
        driver.get("http://localhost:8181/homepage.html");
        Thread.sleep(1000);
        WebElement paragraph = driver.findElement(By.id("hello_world_message"));
        assertTrue(paragraph.isDisplayed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
