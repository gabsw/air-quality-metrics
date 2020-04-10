package tqs.air.quality.metrics.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ResultsPageIT {
    private String baseUrl = "http://localhost:8080/air-quality";
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
    void whenTryingToGenerateResultsWithoutValidFormSubmission_thenErrorIsReturned() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);

        String actualTitle = driver.getTitle();
        String expectedTitle = "Error";
        String actualHeader = driver.findElement(By.tagName("h2")).getText();
        String expectedHeader = "Error";

        String expectedRowErrorTitle = "Error";
        String actualRowErrorTitle = driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]")).getText();
        String expectedRowErrorContent = "Method Not Allowed";
        String actualRowErrorContent = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();

        String expectedRowStatusTitle = "Status";
        String actualRowStatusTitle = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]")).getText();
        String expectedRowStatusContent = "405";
        String actualRowStatusContent = driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]")).getText();

        String expectedRowMessageTitle = "Message";
        String actualRowMessageTitle = driver.findElement(By.xpath("//table/tbody/tr[3]/td[1]")).getText();
        String expectedRowMessageContent = "Request method 'GET' not supported";
        String actualRowMessageContent = driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]")).getText();

        String expectedRowDateTitle = "Date";
        String actualRowDateTitle = driver.findElement(By.xpath("//table/tbody/tr[4]/td[1]")).getText();
        String actualRowDateContent = driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]")).getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US);
        LocalDateTime ldt = LocalDateTime.parse(actualRowDateContent, formatter);
        LocalDateTime currentLdt = LocalDateTime.now();

        assertAll("error contents",
                () -> assertEquals(expectedTitle, actualTitle),
                () -> assertEquals(expectedHeader, actualHeader),
                () -> assertEquals(expectedRowErrorTitle, actualRowErrorTitle),
                () -> assertEquals(expectedRowErrorContent, actualRowErrorContent),
                () -> assertEquals(expectedRowStatusTitle, actualRowStatusTitle),
                () -> assertEquals(expectedRowStatusContent, actualRowStatusContent),
                () -> assertEquals(expectedRowMessageTitle, actualRowMessageTitle),
                () -> assertEquals(expectedRowMessageContent, actualRowMessageContent),
                () -> assertEquals(expectedRowDateTitle, actualRowDateTitle),
                () -> assertTrue(ldt.isBefore(currentLdt) || ldt.isEqual(currentLdt))
        );
    }

    @Test
    void checkHomePageHyperlink() {
        WebDriver driver = environment.getDriver();
        driver.get(baseUrl);
        driver.findElement(By.linkText("home page")).click();
        String actualUrl = driver.getCurrentUrl();
        String expectedHomePageUrl = "http://localhost:8080/";
        assertEquals(expectedHomePageUrl, actualUrl);
    }
}
