package tqs.air.quality.metrics.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tqs.air.quality.metrics.mocks.FutureMocks.futureLocalDateTime;
import static tqs.air.quality.metrics.mocks.MockBase.latitude;
import static tqs.air.quality.metrics.mocks.MockBase.longitude;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HomePageIT {
    private String baseUrl = "http://localhost:8080/";
    private Environment environment;

    @BeforeEach
    void startBrowser() {
        environment = new Environment();
    }

    @AfterEach
    void tearDown() {
        environment.shutDownDriver();
    }

    @Test
    void checkTitle() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);
        String actualTitle = driver.getTitle();
        String expectedTitle = "Home Page";
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    void checkHeader() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);
        String actualHeader = driver.findElement(By.tagName("h1")).getText();
        String expectedHeader = "Air Quality Metrics";
        assertEquals(expectedHeader, actualHeader);
    }

    @Test
    void checkFailedSubmissionOfInvalidLatitude() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);
        fillForm(driver, -200, longitude, futureLocalDateTime);
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        // Submission should fail and the user should remain on the same page for a new attempt
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = baseUrl;

        // Should retrieve a list with 1 invalid element (latitude)
        List<WebElement> invalidElements = driver.findElements(By.cssSelector("input:invalid"));
        WebElement latElement = driver.findElement(By.id("lat"));

        // Should retrieve a list with 3 valid elements (longitude, datetime, submit)
        List<WebElement> validElements = driver.findElements(By.cssSelector("input:valid"));
        WebElement longElement = driver.findElement(By.id("long"));
        WebElement datetimeElement = driver.findElement(By.id("datetime"));
        WebElement submitElement = driver.findElement(By.id("submit"));


        assertAll("failed form submission due to latitude",
                () -> assertEquals(expectedUrl, actualUrl),
                () -> assertEquals(1, invalidElements.size()),
                () -> assertEquals(3, validElements.size()),
                () -> assertTrue(validElements.contains(longElement)),
                () -> assertTrue(validElements.contains(datetimeElement)),
                () -> assertTrue(validElements.contains(submitElement)),
                () -> assertTrue(invalidElements.contains(latElement))
        );
    }

    @Test
    void checkFailedSubmissionOfInvalidLongitude() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);
        fillForm(driver, latitude, -300, futureLocalDateTime);
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        // Submission should fail and the user should remain on the same page for a new attempt
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = baseUrl;

        // Should retrieve a list with 1 invalid element (longitude)
        List<WebElement> invalidElements = driver.findElements(By.cssSelector("input:invalid"));
        WebElement longElement = driver.findElement(By.id("long"));

        // Should retrieve a list with 3 valid elements (latitude, datetime, submit)
        List<WebElement> validElements = driver.findElements(By.cssSelector("input:valid"));
        WebElement latElement = driver.findElement(By.id("lat"));
        WebElement datetimeElement = driver.findElement(By.id("datetime"));
        WebElement submitElement = driver.findElement(By.id("submit"));


        assertAll("failed form submission due to longitude",
                () -> assertEquals(expectedUrl, actualUrl),
                () -> assertEquals(1, invalidElements.size()),
                () -> assertEquals(3, validElements.size()),
                () -> assertTrue(validElements.contains(latElement)),
                () -> assertTrue(validElements.contains(datetimeElement)),
                () -> assertTrue(validElements.contains(submitElement)),
                () -> assertTrue(invalidElements.contains(longElement))
        );
    }


    @Test
    void checkFailedSubmissionOfInvalidLocalDateTime() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);

        WebElement latitudeFormText = driver.findElement(By.id("lat"));
        latitudeFormText.clear();
        latitudeFormText.sendKeys(String.valueOf(latitude));
        WebElement longitudeFormText = driver.findElement(By.id("long"));
        longitudeFormText.clear();
        longitudeFormText.sendKeys(String.valueOf(longitude));
        WebElement datetimeText = driver.findElement(By.id("datetime"));
        datetimeText.sendKeys("11-04-2020"); // Wrong format


        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        // Submission should fail and the user should remain on the same page for a new attempt
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = baseUrl;

        // Should retrieve a list with 1 invalid element (datetime)
        List<WebElement> invalidElements = driver.findElements(By.cssSelector("input:invalid"));

        // Should retrieve a list with 3 valid elements (latitude, longitude, submit)
        List<WebElement> validElements = driver.findElements(By.cssSelector("input:valid"));

        assertAll("failed form submission due to datetime",
                () -> assertEquals(expectedUrl, actualUrl),
                () -> assertEquals(1, invalidElements.size()),
                () -> assertEquals(3, validElements.size()),
                () -> assertTrue(validElements.contains(latitudeFormText)),
                () -> assertTrue(validElements.contains(longitudeFormText)),
                () -> assertTrue(validElements.contains(submitButton)),
                () -> assertTrue(invalidElements.contains(datetimeText))
        );
    }

    @Test
    void checkFailedSubmissionOfEmptyFields() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);

        WebElement latitudeFormText = driver.findElement(By.id("lat"));
        latitudeFormText.clear();
        WebElement longitudeFormText = driver.findElement(By.id("long"));
        longitudeFormText.clear();

        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        // Submission should fail and the user should remain on the same page for a new attempt
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = baseUrl;

        // Should retrieve a list with 2 invalid elements (latitude and longitude)
        List<WebElement> invalidElements = driver.findElements(By.cssSelector("input:invalid"));

        // Should retrieve a list with 2 valid elements (datetime and submit)
        List<WebElement> validElements = driver.findElements(By.cssSelector("input:valid"));

        WebElement datetimeElement = driver.findElement(By.id("datetime"));

        assertAll("failed form submission due to empty latitude and longitude",
                () -> assertEquals(expectedUrl, actualUrl),
                () -> assertEquals(2, invalidElements.size()),
                () -> assertEquals(2, validElements.size()),
                () -> assertTrue(invalidElements.contains(latitudeFormText)),
                () -> assertTrue(invalidElements.contains(longitudeFormText)),
                () -> assertTrue(validElements.contains(submitButton)),
                () -> assertTrue(validElements.contains(datetimeElement))
        );
    }

    @Test
    void checkValidFormSubmission_generatesResultsPage() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);

        fillForm(driver, latitude, longitude, futureLocalDateTime);
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        // After form submission, the user should be taken to the results page
        String actualResultsUrl = driver.getCurrentUrl();
        String expectedResultsUrl = baseUrl + "air-quality";

        String latitudeSpanText = driver.findElement(By.id("lat_result")).getText();
        String longitudeSpanText = driver.findElement(By.id("long_result")).getText();
        String datetimeSpanText = driver.findElement(By.id("ldt_result")).getText();

        LocalDateTime ldt = LocalDateTime.parse(datetimeSpanText, DateTimeFormatter.ISO_DATE_TIME);

        // Check static contents of results page
        String actualResultsTitle = driver.getTitle();
        String expectedResultsTitle = "Results";
        String actualResultsHeader = driver.findElement(By.tagName("h2")).getText();
        String expectedResultsHeader = "Air Quality Metrics";
        String actualHeaderInitials = driver.findElement(By.xpath("//table/tbody/tr[1]/th[1]")).getText();
        String expectedHeaderInitials = "Initials";
        String actualHeaderName = driver.findElement(By.xpath("//table/tbody/tr[1]/th[2]")).getText();
        String expectedHeaderName = "Description";
        String actualHeaderAmount = driver.findElement(By.xpath("//table/tbody/tr[1]/th[3]")).getText();
        String expectedHeaderAmount = "Quantity";
        String actualHeaderUnits = driver.findElement(By.xpath("//table/tbody/tr[1]/th[4]")).getText();
        String expectedHeaderUnits = "Units";

        // Check that actual results have been sent by processing the form data
        String firstRowInitialsResults = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]")).getText();
        String firstRowNameResults = driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]")).getText();
        String firstRowAmountResults = driver.findElement(By.xpath("//table/tbody/tr[2]/td[3]")).getText();
        String firstRowUnitsResults = driver.findElement(By.xpath("//table/tbody/tr[2]/td[4]")).getText();

        assertAll("successful form submission",
                () -> assertEquals(expectedResultsUrl, actualResultsUrl),
                () -> assertEquals(String.valueOf(latitude), latitudeSpanText),
                () -> assertEquals(String.valueOf(longitude), longitudeSpanText),
                () -> assertTrue(ldt.isBefore(futureLocalDateTime) || ldt.isEqual(futureLocalDateTime)),
                () -> assertNotNull(firstRowInitialsResults),
                () -> assertNotNull(firstRowNameResults),
                () -> assertNotNull(firstRowAmountResults),
                () -> assertNotNull(firstRowUnitsResults),
                () -> assertEquals(expectedResultsTitle, actualResultsTitle),
                () -> assertEquals(expectedResultsHeader, actualResultsHeader),
                () -> assertEquals(expectedHeaderInitials, actualHeaderInitials),
                () -> assertEquals(expectedHeaderName, actualHeaderName),
                () -> assertEquals(expectedHeaderAmount, actualHeaderAmount),
                () -> assertEquals(expectedHeaderUnits, actualHeaderUnits)
        );
    }

    @Test
    void checkFormReset() throws InterruptedException {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);

        WebElement latitudeFormText = driver.findElement(By.id("lat"));
        latitudeFormText.clear();
        latitudeFormText.sendKeys(String.valueOf(50));

        WebElement longitudeFormText = driver.findElement(By.id("long"));
        longitudeFormText.clear();
        longitudeFormText.sendKeys(String.valueOf(50));

        WebElement datetimeText = driver.findElement(By.id("datetime"));
        datetimeText.sendKeys(futureLocalDateTime.format(DateTimeFormatter.ISO_DATE_TIME));

        WebElement resetButton = driver.findElement(By.id("reset"));
        resetButton.click();

        assertAll("reset button",
                () -> assertEquals(String.valueOf(latitude), latitudeFormText.getAttribute("value")),
                () -> assertEquals(String.valueOf(longitude), longitudeFormText.getAttribute("value")),
                () -> assertEquals("", datetimeText.getAttribute("value"))
        );

    }

    private void fillForm(WebDriver driver, double latitude, double longitude, LocalDateTime localDateTime) {
        WebElement latitudeFormText = driver.findElement(By.id("lat"));
        latitudeFormText.clear();
        latitudeFormText.sendKeys(String.valueOf(latitude));
        WebElement longitudeFormText = driver.findElement(By.id("long"));
        longitudeFormText.clear();
        longitudeFormText.sendKeys(String.valueOf(longitude));
        WebElement datetimeText = driver.findElement(By.id("datetime"));
        datetimeText.sendKeys(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
