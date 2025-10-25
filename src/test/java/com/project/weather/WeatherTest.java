package com.project.weather;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class WeatherTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ---------- Test Case 1: Search City - Valid Input ----------
    @Test
    public void testSearchValidCity() {
        driver.get("https://www.accuweather.com/");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.search-input[name='query']")));
        searchBox.sendKeys("Jakarta");

        // Klik suggestion pertama
        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.search-bar-result.search-result.source-radar")));
        firstSuggestion.click();

        // Assert: Nama kota muncul di header
        WebElement cityHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h1.header-loc")));
        Assert.assertTrue(cityHeader.getText().contains("Jakarta"));
    }

    // ---------- Test Case 2: Search City - Invalid Input ----------
    @Test
    public void testSearchInvalidCity() {
        driver.get("https://www.accuweather.com/");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.search-input[name='query']")));
        searchBox.sendKeys("InvalidCityXYZ");

        // Tunggu suggestion muncul (atau tidak)
        List<WebElement> suggestions = driver.findElements(
                By.cssSelector("div.search-bar-result.search-result.source-radar"));
        Assert.assertTrue(suggestions.isEmpty(), "No suggestions should appear for invalid city");
    }

    // ---------- Test Case 3: Temperature Display ----------
    @Test
    public void testTemperatureDisplay() {
        driver.get("https://www.accuweather.com/");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.search-input[name='query']")));
        searchBox.sendKeys("Jakarta");
        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.search-bar-result.search-result.source-radar")));
        firstSuggestion.click();

        WebElement temperature = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.temp-container div.temp")));
        String tempText = temperature.getText();
        Assert.assertFalse(tempText.isEmpty(), "Temperature should not be empty");
    }


    // ---------- Test Case 4: Weather Icon Display ----------
    @Test
    public void testWeatherIconDisplay() {
        driver.get("https://www.accuweather.com/");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.search-input[name='query']")));
        searchBox.sendKeys("Jakarta");
        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.search-bar-result.search-result.source-radar")));
        firstSuggestion.click();

        WebElement icon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.forecast-container svg.weather-icon")));
        Assert.assertTrue(icon.isDisplayed(), "Weather icon should be visible");
    }



    // ---------- Test Case 5: Current Weather Details ----------
    @Test
    public void testCurrentWeatherDetails() {
        driver.get("https://www.accuweather.com/");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.search-input[name='query']")));
        searchBox.sendKeys("Jakarta");
        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.search-bar-result.search-result.source-radar")));
        firstSuggestion.click();

        WebElement moreDetailsBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("span.cur-con-weather-card__cta")));

        moreDetailsBtn.click();

        WebElement windElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'current-weather-details')]//div[div[text()='Wind']]/div[2]")));
        WebElement pressureElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'current-weather-details')]//div[div[text()='Pressure']]/div[2]")));
        WebElement humidityElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'current-weather-details')]//div[div[text()='Humidity']]/div[2]")));

        Assert.assertFalse(humidityElement.getText().isEmpty());
        Assert.assertFalse(windElement.getText().isEmpty());
        Assert.assertFalse(pressureElement.getText().isEmpty());
    }

    // ---------- Test Case 6: Footer Links ----------
    @Test
    public void testFooterLinks() {
        driver.get("https://www.accuweather.com/");
        List<WebElement> links = driver.findElements(By.cssSelector("footer a"));
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            Assert.assertNotNull(url);
            Assert.assertTrue(url.startsWith("http"));
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
