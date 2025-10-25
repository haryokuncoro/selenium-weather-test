
# AccuWeather Selenium Automation

Simple Selenium Java project to test the AccuWeather website.  
Covers basic city search, current weather, and website elements.

---

## Test Cases

1. **Search City – Valid Input**  
2. **Search City – Invalid Input**  
3. **Temperature Display**  
4. **Weather Icon Display**  
5. **Current Weather Details** (click More Details → Humidity, Wind, Pressure)  
6. **Footer Links**

---

## Project Structure

```

AccuWeatherSelenium/
│
├─ pom.xml
└─ src/
└─ test/
└─ java/
└─ com/project/weather/
└─ WeatherTest.java

````

---

## Requirements

- Java 17+  
- Maven 3.8+  
- Chrome browser (WebDriverManager handles ChromeDriver)  

---

## How to Run

1. Build project:

```bash
mvn clean install
```

2. Run tests:

```bash
mvn test
```

---

## Notes

* Uses **TestNG** for testing.
* Uses **WebDriverWait** for dynamic elements.
* Easy to extend with more test cases or extract more weather details.
