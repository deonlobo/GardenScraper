package selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Task2 {

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

        List<WebElement> divElements = driver.findElements(By.className("alphabetical-list__group"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Task2_Plants_Scrapping.csv"))) {
            // Write CSV header
            writer.write("Text,Href,Genus Name,Common Name,Plant Type,Light,Height,Width,Propagation,Soil and Water,Temperature and Humidity,Fertilizer,Pruning,Pests and Problems");
            writer.newLine();
            for (WebElement divElement : divElements) {
                List<WebElement> aElements = divElement.findElements(By.tagName("a"));

                // Iterate through each <a> element
                for (WebElement aElement : aElements) {
                    // Get the text and href value
                    String plantName = aElement.getText();
                    String hrefLink = aElement.getAttribute("href");
                    extractDataFromMultiplePlants(plantName,hrefLink,writer,driver);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    private static void extractDataFromMultiplePlants(String plantName, String hrefLink, BufferedWriter writer, WebDriver driver) throws IOException {
        // Open the link in a new tab
        openLinkInNewTab(driver, hrefLink);

        // Switch to the new tab
        switchToNewTab(driver);

        //First Column is the plant name
        writer.write(plantName);

        //Second column is the link for the page
        writer.write(","+hrefLink);

        // Find the row with "Genus Name" and handle NoSuchElementException
        for (String fieldName : Arrays.asList("Genus Name","Common Name","Plant Type","Light","Height","Width","Propagation")) {
            String valueFound = "";
            try {
                WebElement valueNameRow = driver.findElement(By.xpath("//td[text()='"+fieldName+"']/following-sibling::td"));
                valueFound = valueNameRow.getText();
            } catch (NoSuchElementException e) {
                // Handle the case where the element is not found
                System.out.println("Element with "+fieldName+" not found for : " + plantName);
            }
            // Print or write the result
            writer.write(",\"" + valueFound+"\"");
        }

        //Soil and water the plant needs
        // Find the h3 element with the specified conditions
        for (String description : Arrays.asList("Soil and Water","Temperature and Humidity","Fertilizer","Pruning","Pests and Problems")) {
            try {
                WebElement h3Element = driver.findElement(By.xpath("//h3[contains(@class, 'mntl-sc-block-subheading') and .//span[normalize-space()='"+description+"']]"));

                WebElement choosenElement = h3Element.findElement(By.xpath("following-sibling::p"));
                // Iterate through the p elements and print their text
                while (!Objects.equals(choosenElement.getTagName(), "h3")) {
                    if (Objects.equals(choosenElement.getTagName(), "p")) {
                        String paragraphText = choosenElement.getText();
                        writer.write(",\"" + paragraphText + "\"");
                    }
                    choosenElement = choosenElement.findElement(By.xpath("following-sibling::*[1]"));

                }
            } catch (NoSuchElementException e) {
                // Handle the case where the element is not found
                System.out.println("Element not found for : " + plantName);
            }
        }
        writer.newLine();

        // Close the new tab
        closeCurrentTab(driver);
    }

    private static void openLinkInNewTab(WebDriver driver, String linkToOpen) {
        // Open the link in a new tab using JavaScript
        ((ChromeDriver) driver).executeScript("window.open('" + linkToOpen + "','_blank');");
    }

    private static void switchToNewTab(WebDriver driver) {
        // Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    private static void closeCurrentTab(WebDriver driver) {
        // Close the current tab
        driver.close();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        //Go Back to the main tab
        driver.switchTo().window(tabs.get(0));
    }
}
