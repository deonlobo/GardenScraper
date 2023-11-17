package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        // Set the path to the location where ChromeDriver executable is stored
        System.setProperty("webdriver.chrome.driver", "C:/Users/deonv/Desktop/ACC/Assignment3/chromedriver-win64/chromedriver.exe");

        // Create ChromeOptions
        ChromeOptions options = new ChromeOptions();

        // Add the start-maximized argument
        options.addArguments("--start-maximized");

        // Create a new instance of the Chrome driver with the configured ChromeOptions
        WebDriver driver = new ChromeDriver(options);

        // Navigate to the website
        driver.get("https://www.bhg.com/gardening/plant-dictionary/");
        Thread.sleep(5000);

        // Find and interact with various elements on the page

        // Phase 1: Click on a link
        WebElement learnMoreLink = driver.findElement(By.linkText("Baby's Breath"));
        learnMoreLink.click();
        Thread.sleep(5000);

        // Phase 2: Interact with dropdown menu
        // Find the "Garden" Dropdown menu using its XPath
        WebElement decoratingLink = driver.findElement(By.xpath("//*[@id=\"header-nav_1-0\"]/div[1]/ul/li[3]/a"));

        // Create an instance of the Actions class
        Actions actions = new Actions(driver);

        // Perform the mouse hover over the "Garden" dropdown menu
        actions.moveToElement(decoratingLink).perform();
        Thread.sleep(5000);

        // Clicking on the "Plant Encyclopedia" link under "Garden" dropdown menu link this will take us to the starting page "https://www.bhg.com/gardening/plant-dictionary/"
        WebElement subMenueLink = driver.findElement(By.xpath("//*[@id=\"header-nav_1-0\"]/div[1]/ul/li[3]/ul/li[11]/a"));
        subMenueLink.click();
        Thread.sleep(5000);

        // Phase 3: Fill in a text box
        WebElement searchBox = driver.findElement(By.id("search-box__search-input"));
        searchBox.sendKeys("Roses");
        Thread.sleep(5000);

        // Phase 4: Click on the "Go" button which gives results for "Roses"
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"search-box_1-0\"]/form/div/button[1]"));
        searchButton.click();
        Thread.sleep(5000);

        // Phase 5: Extract text from an element
        // Go to plant dictionary url
        driver.get("https://www.bhg.com/gardening/plant-dictionary/");
        // Find all the <a> elements within the specified div
        // Find all elements with the class name "alphabetical-list__group"
        List<WebElement> divElements = driver.findElements(By.className("alphabetical-list__group"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Task1_Plant_NamesAndLink.csv"))) {
            // Write CSV header
            writer.write("Text,Href");
            writer.newLine();
            for (WebElement divElement : divElements) {
                List<WebElement> aElements = divElement.findElements(By.tagName("a"));

                // Iterate through each <a> element
                for (WebElement aElement : aElements) {
                    // Get the text and href value
                    String text = aElement.getText();
                    String hrefLink = aElement.getAttribute("href");

                    // Write data to CSV
                    writer.write(text + "," + hrefLink);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the browser
        driver.quit();
    }
}
